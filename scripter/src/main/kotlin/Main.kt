import com.cioccarellia.infixspascli.SpasProcessor
import com.cioccarellia.infixspascli.logic.Leftrightarrow
import com.cioccarellia.infixspascli.logic.forall
import com.cioccarellia.infixspascli.spas.problem

fun main(args: Array<String>) = SpasProcessor.submit(
    problem {
        beginProblem {}
        description {}

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
                // Nessun assioma
            }

            formulae("conjectures") {
                val xVarLongName = "X"

                formula(
                    forall(xVarLongName, A Leftrightarrow A)
                )
            }
        }

        settings {}
        endProblem {}
    }
)