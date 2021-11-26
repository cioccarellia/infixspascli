package com.cioccarellia.infixspascli.spas.model.components

import com.cioccarellia.infixspascli.spas.dsl.SpasDslMarker
import com.cioccarellia.infixspascli.spas.model.ProblemComponent
import com.cioccarellia.infixspascli.spas.model.Token

@SpasDslMarker
class EndProblem : ProblemComponent() {
    override val token = Token.EndProblem

    override fun spasText() = buildString {
        append(startToken())
        append(endToken())
    }
}