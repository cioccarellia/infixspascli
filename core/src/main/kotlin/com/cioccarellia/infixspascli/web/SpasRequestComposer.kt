package com.cioccarellia.infixspascli.web

import org.jsoup.Connection
import org.jsoup.Jsoup

object SpasRequestComposer {
    private const val baseUrl = "https://webspass.spass-prover.org/cgi-bin/webspass"
    private const val spasContentKey = "spassinput"

    private val headers: Map<String, String> = mapOf(
        "Connection" to "keep-alive",
        "Pragma" to "no-cache",
        "Cache-Control" to "no-cache",
        "Content-Type" to "application/x-www-form-urlencoded",
        "Upgrade-Insecure-Requests" to "1",
        "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
        "Sec-Fetch-Site" to "same-origin",
        "Sec-Fetch-Mode" to "navigate",
        "Sec-Fetch-User" to "?1",
        "Sec-Fetch-Dest" to "document",
        "Origin" to "https://webspass.spass-prover.org",
        "Referer" to "https://webspass.spass-prover.org/index.html",
        "Accept-Language" to "en-GB,en-US;q=0.9,en;q=0.8,it;q=0.7"
    )

    fun execute(content: String): String {
        val request = Jsoup.connect(baseUrl)
            .method(Connection.Method.POST)
            .headers(headers)
            .userAgent(USER_AGENTS.random())
            .data(spasContentKey, content)
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .sslSocketFactory(InsecureSSLFactory.socketFactory())

        return request.execute().body()
    }
}