package com.cioccarellia.infixspascli.spas.model.components

import com.cioccarellia.infixspascli.spas.model.ProblemComponent
import com.cioccarellia.infixspascli.spas.model.Token

enum class Status {
    SATISFIABLE, UNSATISFIABLE, UNKNOWN;

    override fun toString(): String {
        return super.toString().lowercase()
    }
}

data class Description(
    val name: String = "InfixSpasCliProblem",
    val author: String = "Roda77",
    val status: Status = Status.UNSATISFIABLE,
    val description: String = "Inutile",
) : ProblemComponent() {
    override val token = Token.List(beginToken = "descriptions", null)

    override fun spasText() = buildString {
        appendLine(startToken())
        appendLine("name({*$name*}).")
        appendLine("author({*$author*}).")
        appendLine("status($status).")
        appendLine("description({*$description*}).")
        appendLine(endToken())
    }
}

