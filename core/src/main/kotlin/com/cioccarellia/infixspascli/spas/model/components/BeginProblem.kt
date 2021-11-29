package com.cioccarellia.infixspascli.spas.model.components

import com.cioccarellia.infixspascli.spas.dsl.SpasDslMarker
import com.cioccarellia.infixspascli.spas.model.ProblemComponent
import com.cioccarellia.infixspascli.spas.model.Token

@SpasDslMarker
class BeginProblem(
    var identifier: String
) : ProblemComponent() {
    override val token = Token.BeginProblem(qualifier = identifier)

    override fun spasText() = buildString {
        appendLine(startToken())
        appendLine(endToken())
    }
}