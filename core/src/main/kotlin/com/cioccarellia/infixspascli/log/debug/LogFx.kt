package com.cioccarellia.infixspascli.log.debug

import com.cioccarellia.infixspascli.log.blue
import com.cioccarellia.infixspascli.log.green
import com.cioccarellia.infixspascli.log.red
import com.cioccarellia.infixspascli.log.yellow

private inline fun onLogEnabled(logLevels: List<LogLevel>, level: LogLevel, block: () -> Unit) = if (logLevels.contains(level)) block() else Unit
private fun tagify(tag: String) = if (tag.isEmpty()) "" else "::[${tag}]"

fun CustomLogger.d(vararg values: Any?, prefix: String = "D") = onLogEnabled(overriddenLogLevels, LogLevel.DEBUG) {
    values.forEach {
        println("[${prefix.green()}]" + tagify(tag) + " " + it.toString())
    }
}

fun CustomLogger.i(vararg values: Any?, prefix: String = "I") = onLogEnabled(overriddenLogLevels, LogLevel.INFO) {
    values.forEach {
        println("[${prefix.blue()}]" + tagify(tag) + " " + it.toString())
    }
}

fun CustomLogger.w(vararg values: Any?, prefix: String = "W") = onLogEnabled(overriddenLogLevels, LogLevel.WARNING) {
    values.forEach {
        println("[${prefix.yellow()}]" + tagify(tag) + " " + it.toString())
    }
}

fun CustomLogger.e(vararg values: Any?, prefix: String = "E") = onLogEnabled(overriddenLogLevels, LogLevel.ERROR) {
    values.forEach {
        println("[${prefix.red()}]" + tagify(tag) + " " + it.toString())
    }
}