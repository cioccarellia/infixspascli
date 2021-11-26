package com.cioccarellia.infixspascli.spas.model

/**
 * Represents a formal heading and closing of the data entity represented by the class instanciating the [Token]
 * */
sealed class Token(
    /**
     * list_of_formulae(axioms).
     * ^^^^^^^^^^^^^^^^
     * */
    val beginToken: String,

    /**
     * list_of_formulae(axioms).
     *                 ^^^^^^^^
     * */
    val qualifier: String?,
) {
    class BeginProblem(qualifier: String) : Token(beginToken = "problem", qualifier = qualifier)
    object EndProblem : Token(beginToken = "", qualifier = null)
    class ListComponent(beginToken: String, qualifier: String?) : Token(beginToken, qualifier)
}