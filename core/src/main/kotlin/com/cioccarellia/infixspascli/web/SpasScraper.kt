package com.cioccarellia.infixspascli.web

import org.jsoup.Jsoup

object SpasScraper {
    private const val cssSelectionQuery = "pre"

    fun extractText(htmlPage: String): String = Jsoup.parse(htmlPage).select(cssSelectionQuery).text()
}