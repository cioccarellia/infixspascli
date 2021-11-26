# Infix Spas CLI (With LaTeX notation)
This project is a Kotlin CLI tool used to access the SPAS [web interface](https://webspass.spass-prover.org/) APIs using Kotlin idiomatic and modern language features, and remapping the syntax to match LaTeX actual binary operator naming conventions.

This project includes:
- A Kotlin DSL to create propositional logic / first-order logic constants, functions and predicates to compose theorems and formulaes;
- A (partial) model of [SPAS syntax](https://webspass.spass-prover.org/help/spass-input-syntax15.pdf) for problems in the SPAS input syntax;
- A module for sending HTTP requests;
- An HTML scraper to extract the response output.

Main goals of this project are:
- Increase productivity by providing Kotlin language structure and idiomaticity instead of a plain web form text editor;
- Reduce syntax errors as they are found and highlighted by the IDE;
- Reduce semantic errors as you use a fully-fledged programming language to encode theorems;
- Provide complete customizability of every field detailed in the SPAS documentation: Setting flags, Name, Author, Description, State;
- Show line numbers to spas text (which isn't seen until it sent to the server but is needed only for debugging purposes);
- Output formatted and colored output in the terminal;
- Include automatic proof completion & error detection, filtering and highlighting at column precision;
- Use Kotlin `infix` functions and extension functions to use a more natural _infix_ or _postfix_ binary relation notation, instead of defaulting to _prefix_ which is hardcoded in spas;

# Problem Anatomy
A Problem is defined by 5 components:
- Begin of problem
- Description
- Logic
- Settings
- End of problem

Each block is mandatory in SPAS language and has to be filled in.

We can define a `Problem` object by using the DSL-access function `problem {}`. This pre-populates all the default components to their default values (which, apart from logic which is where the actual work resides, is not relevant to the final problem solution).

So a complete declaration would look like this (even though `beginProblem`, `description`, `settings` and `endProblem` can be removed if not changed):
```kotlin
problem {
        beginProblem {}
        description {}

        logic {
            symbols {  }

            formulae("axioms") {  }

            formulae("conjectures") {  }
        }

        settings {}
        endProblem {}
    }
```


The logic block is where you can define the theorem properties:
- Symbols: Language constants, functions and predicates, divided in 2 groups, and represented by `Pair<String,Int>` (pairs of identifying letter and its arity):
  - `functions`: Lettere funzionali (e costanti)
  - `predicates`: Lettere predicative
- Axioms (Formulae): Language Axioms
- Conjectures (Formulae): What be are trying to prove

```kotlin
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
```


Putting it all together (as you can see in scripter/.../Main.kt):
```kotlin
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
```

# Logic Functions
All more-than-binary relations support vararg inputs in prefix form.

All binary relation functions support infix notation.

All unary operators support prefix and postfix notation.

```kotlin
// ∧ (LOGICAL AND)
"A" wedge "B"
wedge("A", "B", "C", ...)


// ∨ (LOGICAL OR)
"A" vee "B"
vee("A", "B", "C", ...)


// ¬ (LOGICAL NOT)
lnot("A")
"A".lnot()


// ⇒ (IMPLIES)
"A" Rightarrow "B"
Rightarrow("A", "B")


// ⇐ (IS IMPLIED)
"A" Leftarrow "B"
Leftarrow("A", "B")


// ⇔ (IF AND ONLY IF)
"A" Leftrightarrow "B"
Leftrightarrow("A", "B")


// = (EQUALS)
"A" eq "B"
eq("A", "B")


// ∀ UNIVERSAL QUANTIFIER
forall(listOf("A", "B", "C", ...), E)
forall("A", "B", "C", ..., matrix = E)
forall("X", E)


// ∃ (EXISTENCE QUANTIFIER)
exists(listOf("A", "B", "C", ...), E)
exists("A", "B", "C", ..., matrix = E)
exists("X", E)
```

# Usage
Just clone the project, open it in IntelliJ Idea and let it sync. Under `scripter/src/main/kotlin` you will find a `Main.kt` file which has an example project. You can use the "Run SPAS" config to compile and execute your code.

# Screenshots
### generated spas + parsed
<img src="https://i.imgur.com/s66jv8C.png" />

### generated spas + output + parsed
<img src="https://i.imgur.com/QJ46qou.png" />

### generates spas + output + error highlighting
<img src="https://i.imgur.com/TSwNuP1.png" />

# Why
Why not?