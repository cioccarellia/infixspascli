package com.cioccarellia.infixspascli

import com.cioccarellia.infixspascli.log.debug.DfuLogger
import com.cioccarellia.infixspascli.log.red
import com.cioccarellia.infixspascli.log.redBackground
import com.cioccarellia.infixspascli.log.yellow
import com.cioccarellia.infixspascli.spas.Problem
import com.cioccarellia.infixspascli.web.SpasRequestComposer
import com.cioccarellia.infixspascli.web.SpasScraper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SpasProcessor {
    fun submit(problem: Problem) {
        val spasText = problem.toString()

        DfuLogger.i("Spas Text:")
        printContentWithLineNumber(spasText)

        DfuLogger.d("Sending data to spas web server for analysis")
        val spasResponseHtml = SpasRequestComposer.execute(spasText)
        val output = SpasScraper.extractText(spasResponseHtml)

        DfuLogger.d("Received response")
        println(output)

        when {
            output.contains("SPASS beiseite: Proof found.") -> DfuLogger.d("Proof found", prefix = "R")
            output.contains("SPASS beiseite: Completion found.") -> DfuLogger.w("Proof not found", prefix = "R")
            else -> {
                // We can assume an error has occurred
                // Output is going to look like this
                // In file /tmp/webspass-webform_2021-11-26_00:40:50_28393l.txt at line 17,column 22:  symbols with arbitrary arity are not allowed.

                val errorRawString = output.split(".txt")[1]


                // Line and Colon
                val lineAndColumnDetails = errorRawString.split(":")[0]

                val lineRaw = lineAndColumnDetails.split(",")[0].replace("at line", "").trim().toInt()
                val column = lineAndColumnDetails.split(",")[1].replace("column", "").trim().toInt()


                // We pray for god we don't get a IOOBE
                println()
                printLineWithNumberError(spasText.split("\n")[lineRaw - 1], lineRaw)
                printErrorMarker(column)

                // Error semantic
                val errorDetails = errorRawString.substring(lineAndColumnDetails.length).trim()
                DfuLogger.e("An error has occurred: ${errorDetails.trim().removePrefix(":").trim()}", prefix = "E")
            }
        }

    }

    private fun printContentWithLineNumber(str: String) = str.split("\n").forEachIndexed { i, it -> printLineWithNumber(it, i + 1) }

    private fun printLineWithNumber(line: String, number: Int) {
        System.out.printf("%-4d|  ".yellow(), number)
        println(line)
    }
    private fun printLineWithNumberError(line: String, number: Int) {
        System.out.printf("%-4d".redBackground() + "|  ", number)
        println(line.red())
    }

    private fun printErrorMarker(column: Int) {
        print(" " spaced (column - 1 + 7))
        print("^".redBackground())
        println()
    }
}

private infix fun String.spaced(i: Int): String = buildString { repeat(i) { append(" ") } }
