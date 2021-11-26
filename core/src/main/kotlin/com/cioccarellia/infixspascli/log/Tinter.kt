package com.cioccarellia.infixspascli.log

internal const val BG_JUMP = 10

object Tinter {
    private const val ESCAPE = '\u001B'
    private const val RESET = "$ESCAPE[0m"

    fun f(string: String, color: TermColor) = tint(string, color.foreground)
    fun b(string: String, color: TermColor) = tint(string, color.background)

    private fun tint(string: String, ansiString: String) = "$ansiString$string$RESET"
}