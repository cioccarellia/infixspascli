package com.cioccarellia.infixspascli

import com.cioccarellia.infixspascli.log.debug.DfuLogger
import com.cioccarellia.infixspascli.log.red
import com.cioccarellia.infixspascli.log.redBackground
import com.cioccarellia.infixspascli.log.yellow
import com.cioccarellia.infixspascli.spas.Problem
import com.cioccarellia.infixspascli.web.SpasRequestComposer
import com.cioccarellia.infixspascli.web.SpasScraper

object SpasProcessor {
    fun submit(
        problem: Problem,
        config: ProcessorConfig.() -> Unit = {}
    ) {
        val spasText = problem.compileToSpas()
        val (compileOnly, silentMode, analyzeResult, lineNumbers) = ProcessorConfig().apply(config)
        val verbose = !silentMode

        if (verbose)
            DfuLogger.i("Generated SPAS Text:")

        printContentWithLineNumber(spasText, lineNumbers)

        if (compileOnly)
            return

        if (verbose)
            DfuLogger.d("Sending SPAS text to web server for analysis...")

        val spasResponseHtml = SpasRequestComposer.execute(spasText)
        val output = SpasScraper.extractText(spasResponseHtml)

        if (verbose)
            DfuLogger.d("Received response from web server:")

        println(output)

        if (analyzeResult) {
            when {
                output.contains(PROOF_FOUND) -> DfuLogger.d("Proof found", prefix = "R")
                output.contains(PROOF_NOT_FOUND) -> DfuLogger.w("Proof not found", prefix = "R")
                else -> {
                    // We can assume an error has occurred
                    // Output is going to look like this
                    // In file /tmp/webspass-webform_2021-11-26_00:40:50_28393l.txt at line 17,column 22:  symbols with arbitrary arity are not allowed.

                    val errorRawString = output.split(".txt")[1]

                    // Line and Colon
                    val lineAndColonDetails = errorRawString.split(":")[0]

                    val lineRaw = lineAndColonDetails.split(",")[0].replace("at line", "").trim().toInt()
                    val colon = lineAndColonDetails.split(",")[1].replace("column", "").trim().toInt()

                    // We pray for god we don't get a IOOBE
                    println()
                    printLineWithNumberError(spasText.split("\n")[lineRaw - 1], lineRaw, lineNumbers)
                    printErrorMarker(colon, lineNumbers)

                    // Error semantic
                    val errorDetails = errorRawString.substring(lineAndColonDetails.length).trim()
                    DfuLogger.e("An error has occurred: ${errorDetails.trim().removePrefix(":").trim()}", prefix = "E")
                }
            }
        }
    }

    private fun printContentWithLineNumber(str: String, lineNumbers: Boolean) = str.split("\n").forEachIndexed { i, it -> printLineWithNumber(it, i + 1, lineNumbers) }

    private fun printLineWithNumber(line: String, number: Int, showLineNumbers: Boolean) {
        if (showLineNumbers)
            System.out.printf("%-4d|  ".yellow(), number)
        println(line)
    }
    private fun printLineWithNumberError(line: String, number: Int, showLineNumbers: Boolean) {
        if (showLineNumbers)
            System.out.printf("%-4d".redBackground() + "|  ", number)
        println(line.red())
    }

    private fun printErrorMarker(column: Int, showLineNumbers: Boolean) {
        if (showLineNumbers) {
            print(" " spaced (column - 1 + 7))
        } else {
            print(" " spaced (column - 1))
        }

        print("^".redBackground() + "\n")
    }

    private const val PROOF_FOUND = """SPASS beiseite: Proof found"""
    private const val PROOF_NOT_FOUND = """SPASS beiseite: Completion found"""
}

private infix fun String.spaced(i: Int): String = buildString { repeat(i) { append(" ") } }
