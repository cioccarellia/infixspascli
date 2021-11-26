package com.cioccarellia.infixspascli.spas.model

/**
 * Represents a problem stage
 * */
abstract class ProblemComponent {

    /**
     * Identifies the component for spas syntax
     * */
    internal abstract val token: Token

    /**
     * spas begin block
     * */
    internal fun startToken(): String = when (token) {
        is Token.BeginProblem -> "begin_${token.beginToken}"
        is Token.EndProblem -> "end_problem"
        is Token.ListComponent -> "list_of_${token.beginToken}"
    } + qualifier() + "."

    private fun qualifier() = if (token.qualifier.isNullOrEmpty()) "" else { "(${token.qualifier})" }

    /**
     * spas end block
     * */
    internal fun endToken(): String = when (token) {
        is Token.BeginProblem -> ""
        is Token.EndProblem -> ""
        is Token.ListComponent -> "end_of_list" + "."
    }

    internal abstract fun spasText(): String

    override fun toString(): String {
        return spasText()
    }
}