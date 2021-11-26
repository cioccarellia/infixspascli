package com.cioccarellia.infixspascli.spas.dsl

import com.cioccarellia.infixspascli.spas.model.components.LogicalPart
import com.cioccarellia.infixspascli.spas.model.components.logicparts.Formulae
import com.cioccarellia.infixspascli.spas.model.components.logicparts.Symbols

@SpasDslMarker
class LogicalParts : ArrayList<LogicalPart>() {
    fun formulae(qualifier: String, builder: Formulae.() -> Unit) {
        add(Formulae(qualifier).apply(builder))
    }

    fun symbols(builder: Symbols.() -> Unit) {
        add(Symbols().apply(builder))
    }
}