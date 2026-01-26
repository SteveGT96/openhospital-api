package org.isf.plugins.nxgt

import kotlinx.serialization.json.Json

object JSON {
    val json = Json { prettyPrint = true }
    @JvmStatic
    inline fun <reified T> fromJson(value: String): T {
        return  json.decodeFromString<T>(value)
    }
    @JvmStatic
    inline fun <reified T> toJson(value: T): String {
        return ""
    }
    @JvmStatic
    fun getGson(): JSON {
        return this
    }
}