package org.isf.config

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "external.jars")
data class ExternalJarsConfig(
    val starters: List<StarterConfig> = emptyList(),
    val javaPath: String? = null
) {
    data class StarterConfig(
        @field:NotBlank
        val name: String,
        @field:NotBlank
        val path: String,
        val port: String? = null,
        val args: String? = null,
        val workingDir: String? = null,
        val enabled: Boolean = true,
        val javaPath: String? = null
    )
}