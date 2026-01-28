package org.isf.utils

import org.slf4j.LoggerFactory

inline fun <reified T : Any> T.slf4j() = lazy {
    LoggerFactory.getLogger(T::class.java)
}