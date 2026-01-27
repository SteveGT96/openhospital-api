package org.isf.utils

import jakarta.annotation.PostConstruct
import org.isf.config.ExternalJarsConfig
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.io.File
import java.io.IOException
import kotlin.concurrent.thread

@Component
class MultiExternalJarStarter(
    private val config: ExternalJarsConfig
) {

    private val logger = LoggerFactory.getLogger(MultiExternalJarStarter::class.java)


    @EventListener(ApplicationReadyEvent::class)
    fun startAllProcesses() {
        if (config.starters.isEmpty()) {
            logger.info("No external JAR starters configured")
            return
        }

        logger.info("Starting ${config.starters.size} external JAR process(es)")

        config.starters
            .filter { it.enabled }
            .forEach(::startSingleProcess)
    }

    private fun startSingleProcess(starter: ExternalJarsConfig.StarterConfig) {
        val jarFile = File(starter.path)
        if (!jarFile.exists() || !jarFile.isFile || !jarFile.canRead()) {
            logger.error("Cannot start '${starter.name}' → JAR not found/readable: ${starter.path}")
            return
        }

        // Determine which java to use: per-starter > global > fallback to "java"
        val javaExecutable = starter.javaPath
            ?: config.javaPath
            ?: "java"

        logger.debug("Using Java executable for '${starter.name}': $javaExecutable")

        val command = buildList {
            add(javaExecutable)
            add("-jar")
            add(jarFile.absolutePath)

            starter.port?.let { add("--server.port=$it") }

            starter.args?.trim()?.takeIf { it.isNotBlank() }?.let { argStr ->
                addAll(argStr.split("\\s+".toRegex()))
            }
        }

        logger.info("Launching '${starter.name}' → Command: ${command.joinToString(" ")}")

        val processBuilder = ProcessBuilder(command).apply {
            redirectOutput(ProcessBuilder.Redirect.INHERIT)
            redirectError(ProcessBuilder.Redirect.INHERIT)

            starter.workingDir?.let { wdPath ->
                val dir = File(wdPath)
                if (dir.isDirectory) {
                    directory(dir)
                    logger.debug("Working dir for '${starter.name}': $wdPath")
                } else {
                    logger.warn("Invalid working dir for '${starter.name}': $wdPath → skipped")
                }
            }
        }

        try {
            val process = processBuilder.start()
            val pid = process.pid()
            logger.info("'${starter.name}' started (PID: $pid)")

            // Graceful shutdown
            Runtime.getRuntime().addShutdownHook(thread(start = false) {
                logger.info("Shutting down '${starter.name}' (PID: $pid)...")
                process.destroyForcibly()
                try {
                    process.waitFor()
                    logger.info("'${starter.name}' terminated")
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            })

        } catch (e: IOException) {
            logger.error("Failed to launch '${starter.name}'", e)
        }
    }
}