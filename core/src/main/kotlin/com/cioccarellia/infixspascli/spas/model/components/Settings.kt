package com.cioccarellia.infixspascli.spas.model.components

import com.cioccarellia.infixspascli.spas.dsl.SpasDslMarker
import com.cioccarellia.infixspascli.spas.model.ProblemComponent
import com.cioccarellia.infixspascli.spas.model.Token

/**
list_of_settings(SPASS).
{*
set_flag(PGiven,1).
set_flag(PProblem,0).
*}
end_of_list.
 * */
@SpasDslMarker
data class Settings(
    var identifier: String = "SPASS",
    var flagShowSolution: Boolean = true,   // PGiven
    var flagShowProblem: Boolean = true     // PProblem
) : ProblemComponent() {
    override val token = Token.ListComponent(beginToken = "settings", qualifier = identifier)

    override fun spasText() = buildString {
        appendLine(startToken())
        val flag1 = "set_flag(PGiven,${flagShowSolution.toBit()})."
        val flag2 = "set_flag(PProblem,${flagShowProblem.toBit()})."
        appendLine(
            "{*\n$flag1\n$flag2\n*}"
        )
        appendLine(endToken())
    }
}

internal fun Boolean.toBit() = if (this) "1" else "0"