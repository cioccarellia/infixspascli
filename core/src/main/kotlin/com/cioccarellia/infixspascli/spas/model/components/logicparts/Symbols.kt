package com.cioccarellia.infixspascli.spas.model.components.logicparts

import com.cioccarellia.infixspascli.spas.dsl.SpasDslMarker
import com.cioccarellia.infixspascli.spas.model.components.LogicalPart

@SpasDslMarker
class Symbols(
    /**
     * Actual language symbol definition
     * */
    private val symbolList: MutableList<Pair<String, Pair<String, Int>>> = mutableListOf()
) : LogicalPart(beginToken = "symbols", qualifier = null) {

    override fun spasText() = buildString {
        appendLine(startToken())

        if (symbolList.isNotEmpty()) {
            // All different group names (unique)
            val symbolGroupNames = symbolList
                .map { it.first }
                .toSet()

            symbolGroupNames.forEach { symbolGroupName ->
                //
                val joined = symbolList
                    // We filter the list elements for the given group name
                    .filter { it.first == symbolGroupName }
                    // Map toa list of Pair<String, Int>
                    .map { it.second }
                    // Clean empty items
                    .filter { it.first.isNotEmpty() }
                    // Stringify
                    .joinToString(separator = ",") { symbol -> "(${symbol.first},${symbol.second})" }

                appendLine("$symbolGroupName[$joined].")
            }
        }

        appendLine(endToken())
    }

    fun symbolGroup(newGroupName: String, addedSymbols: List<Pair<String, Int>>) {
        this.symbolList.addAll(addedSymbols.map { newGroupName to it })
    }
}