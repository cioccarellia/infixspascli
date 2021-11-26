package com.cioccarellia.infixspascli.spas.model.components

import com.cioccarellia.infixspascli.spas.model.ProblemComponent
import com.cioccarellia.infixspascli.spas.model.Token

/**
begin_problem(Pelletier54).
 * */
data class BeginProblem(
    val identifier: String
) : ProblemComponent() {
    override val token = Token.BeginProblem(qualifier = identifier)

    override fun spasText() = buildString {
        appendLine(startToken())
        appendLine(endToken())
    }
}