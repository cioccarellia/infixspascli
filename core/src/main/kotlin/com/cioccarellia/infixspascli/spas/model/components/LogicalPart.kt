package com.cioccarellia.infixspascli.spas.model.components

import com.cioccarellia.infixspascli.spas.model.ProblemComponent
import com.cioccarellia.infixspascli.spas.model.Token

/**
 * This class is just used to restrict other logical parts like
 * Symbols and Formulaes, but has no actual purpose since the
 * DSL-construction happens under another package, and this
 * class isn't contained (directly) in [Problem]
 * */
abstract class LogicalPart(
    beginToken: String,
    qualifier: String?,
) : ProblemComponent() {
    override val token = Token.List(beginToken = beginToken, qualifier = qualifier)
}