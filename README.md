# Infix Spas CLI (With LaTeX notation)
This project is a Kotlin CLI project used to access the [SPAS web](https://webspass.spass-prover.org/) APIs using Kotlin idiomatic and modern language features, and remapping the syntax to match LaTeX actual binary operator naming conventions for the theorem prover.

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

The code in this repository models a spas problem using Kotlin classes. 
This model is then compiled to spas syntax, sent to the webserver, processed, and the response is parsed and analyzed back by the program.

There are 2 modules:
- `:core`: Contains the logic, scraping engine and models;
- `:scripter`: Contains `Main.kt` file which can be used to create and execute spas models.


This model is then compiled to spas syntax, sent to the webserver, processed, and the response is parsed and analyzed back by the program.

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
forall(listOf("A", "B", "C", ...), M)
forall("A", "B", "C", ..., matrix = M)
forall("X", M)


// ∃ (EXISTENCE QUANTIFIER)
exists(listOf("A", "B", "C", ...), M)
exists("A", "B", "C", ..., matrix = M)
exists("X", M)
```

Note: the universal quantifiers receive `vararg` input + 1 string, so in case you are working with 2+ variables, the actual matrix has to be specified if you want to use linear parameters, or a list of variables can be supplied.

# Usage
Just clone the project and open it in IntelliJ Idea. 
Under `scripter/src/main/kotlin` you will find a `Main.kt` file which has an example problem. 
You can use the "Run SPAS" configuration to compile and execute your code from the Run/Debug configuration menu.

# Screenshots
### generated spas + parsed
<img src="https://i.imgur.com/s66jv8C.png" />

### generated spas + output + parsed
<img src="https://i.imgur.com/QJ46qou.png" />

### generates spas + output + error highlighting
<img src="https://i.imgur.com/TSwNuP1.png" />

# Why
Why not?
