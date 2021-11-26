package com.cioccarellia.infixspascli.spas.model.components

import com.cioccarellia.infixspascli.spas.dsl.SpasDslMarker
import com.cioccarellia.infixspascli.spas.model.ProblemComponent
import com.cioccarellia.infixspascli.spas.model.Token

enum class Status {
    SATISFIABLE, UNSATISFIABLE, UNKNOWN;

    override fun toString(): String {
        return super.toString().lowercase()
    }
}

@SpasDslMarker
data class Description(
    var name: String = "InfixSpasCliProblem",
    var author: String = "Roda77",
    var status: Status = Status.UNSATISFIABLE,
    var description: String = "Inutile",
) : ProblemComponent() {
    override val token = Token.ListComponent(beginToken = "descriptions", null)

    override fun spasText() = buildString {
        appendLine(startToken())
        appendLine("name({*$name*}).")
        appendLine("author({*$author*}).")
        appendLine("status($status).")
        appendLine("description({*$description*}).")
        appendLine(endToken())
    }
}

