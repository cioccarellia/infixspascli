package com.cioccarellia.infixspascli.spas.model.components.logicparts

import com.cioccarellia.infixspascli.spas.dsl.SpasDslMarker
import com.cioccarellia.infixspascli.spas.model.components.LogicalPart

@SpasDslMarker
class Formulae(
    formulaeQualifier: String, // axioms | conjectures
    var logicFormula: String = ""
) : LogicalPart("formulae", formulaeQualifier) {
    override fun spasText() = buildString {
        appendLine("formula($logicFormula).")
    }

    fun formula(formula: String) {
        logicFormula = formula
    }
}