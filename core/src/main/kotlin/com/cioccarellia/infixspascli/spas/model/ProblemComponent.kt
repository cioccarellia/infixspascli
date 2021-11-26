package com.cioccarellia.infixspascli.spas.model

abstract class ProblemComponent {

    abstract val token: Token

    fun startToken(): String = when (token) {
        is Token.BeginProblem -> "begin_${token.beginToken}"
        is Token.EndProblem -> "end_problem"
        is Token.List -> "list_of_${token.beginToken}"
    } + if (token.qualifier.isNullOrEmpty()) "" else { "(${token.qualifier})" } + "."

    fun endToken(): String = when (token) {
        is Token.BeginProblem -> ""
        is Token.EndProblem -> ""
        is Token.List -> "end_of_list" + "."
    }

    abstract fun spasText(): String

    override fun toString(): String {
        return spasText()
    }

    companion object {
        internal const val emptyQualifier = ""
    }
}