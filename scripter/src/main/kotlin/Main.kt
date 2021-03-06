import com.cioccarellia.infixspascli.SpasProcessor
import com.cioccarellia.infixspascli.logic.Leftrightarrow
import com.cioccarellia.infixspascli.logic.forall
import com.cioccarellia.infixspascli.spas.model.components.ProbelmStatus
import com.cioccarellia.infixspascli.spas.problem

/*
 * This is your problem object.
 * */
val spasProblem = problem {
    beginProblem {
        identifier = "MYPID"
    }
    description {
        author = "Andrea Cioccarelli"
        name = "SPAS Sample project"
        description = "Gira la Rotella"
        status = ProbelmStatus.UNSATISFIABLE
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
}

fun main(args: Array<String>) {
    SpasProcessor.submit(spasProblem)
}