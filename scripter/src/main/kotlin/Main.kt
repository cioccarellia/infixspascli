import com.cioccarellia.infixspascli.SpasProcessor
import com.cioccarellia.infixspascli.logic.Leftrightarrow
import com.cioccarellia.infixspascli.logic.forall
import com.cioccarellia.infixspascli.spas.problem

val spasProblem = problem {
    beginProblem {}
    description {
        author = "Andrea Cioccarelli"
        name = "SPAS Sample project"
        description = "Gira la Rotella"
    }

    logic {
        val A = "Agata"
        val pi_greco = "PI"

        symbols {
            symbolGroup("functions", listOf(
                pi_greco to 0,
            ))
            symbolGroup("predicates", listOf(
                A to 0
            ))
        }

        formulae("axioms") {
            // No axioms
        }

        formulae("conjectures") {
            val xVarLongName = "X"

            formula(
                forall(xVarLongName, A Leftrightarrow A)
            )
        }
    }

    settings {
        flagShowProblem = true
        flagShowSolution = true
    }
    endProblem {
    }
}

fun main(args: Array<String>) {
    SpasProcessor.submit(spasProblem, silentMode = false, analyzeResult = true)
}