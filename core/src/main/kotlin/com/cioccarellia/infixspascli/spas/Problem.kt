package com.cioccarellia.infixspascli.spas

import com.cioccarellia.infixspascli.spas.dsl.LogicalParts
import com.cioccarellia.infixspascli.spas.model.components.*
import com.cioccarellia.infixspascli.spas.model.components.logicparts.Formulae
import com.cioccarellia.infixspascli.spas.model.components.logicparts.Symbols

class Problem {
    /**
     * Spas problem header
     * */
    private var beginProblem: BeginProblem = BeginProblem("GiraLaRotella")

    fun beginProblem(lambda: BeginProblem.() -> Unit) {
        beginProblem = beginProblem.apply(lambda)
    }


    /**
     * Spas problem description
     * */
    private var description: Description = Description()

    fun description(lambda: Description.() -> Unit) {
        description = description.apply(lambda)
    }


    /**
     * All the logical components which create a problem. Axioms, conjectures and formulaes.
     * */
    private var logicalParts: LogicalParts = LogicalParts()

    fun logic(lambda: LogicalParts.() -> Unit) {
        logicalParts.addAll(LogicalParts().apply(lambda))
    }


    /**
     * Spas problem flags and configuration
     * */
    private var settings: Settings = Settings()

    fun settings(lambda: Settings.() -> Unit) {
        settings = settings.apply(lambda)
    }


    /**
     * Spas problem footer
     * */
    private var endProblem: EndProblem = EndProblem()

    fun endProblem(lambda: EndProblem.() -> Unit) {
        endProblem = endProblem.apply(lambda)
    }

    override fun toString(): String {
        return buildString {
            appendLine("% BeginProblem")
            appendLine(beginProblem.spasText())


            appendLine("% Description")
            appendLine(description.spasText())


            appendLine("\n% Symbols")
            logicalParts.filterIsInstance<Symbols>().forEach {
                appendLine(it.spasText())
            }


            appendLine("\n% Formulae")
            logicalParts.filterIsInstance<Formulae>().forEach {
                appendLine(it.startToken())

                if (it.logicFormula.isNotEmpty()) {
                    append(it.spasText())
                }

                appendLine(it.endToken())
                appendLine()
            }


            appendLine("\n\n% Settings")
            appendLine(settings.spasText())


            appendLine("% EndProblem")
            append(endProblem.spasText())
        }
    }
}

fun problem(
    lambda: Problem.() -> Unit
): Problem = Problem().apply(lambda)