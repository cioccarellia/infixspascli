package com.cioccarellia.infixspascli.log.debug

val LOG_LEVELS = listOf(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
data class CustomLogger(val tag: String, val overriddenLogLevels: List<LogLevel> = LOG_LEVELS)