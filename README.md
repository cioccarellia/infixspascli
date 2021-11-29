# Infix Spas CLI (With LaTeX notation)
This project is a Kotlin CLI used to access the [SPAS web](https://webspass.spass-prover.org/) APIs using Kotlin idiomatic and modern language features, and remapping the syntax to match LaTeX actual binary operator naming conventions for the theorem prover.

This repository includes:
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

# Problem Anatomy & Kotlin Model
A Problem in SPAS syntax is defined by 5 components:
- Begin of problem
- Description
- Logic
- Settings
- End of problem

Each block is mandatory and has to be filled in. This is what makes its syntax tedious and error-prone.

We can define a `Problem` object by using the entry DSL-access function `problem {}`. 

This pre-populates *all* the default problem components (bop, desc, logic, settings, eop) to their default values (which, apart from `logic` which is where the actual code has to be written, is not relevant to the final problem solution).

This makes it really fast to get started with the logic, since all the other 4 components can be left as default or customized later on.

Therefore, a complete declaration would look like this:
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

Which compiled to the same spas-code as this:

```kotlin
problem {
    logic {
        symbols {  }
        
        formulae("axioms") {  }
        
        formulae("conjectures") {  }
    }
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
val spasProblem = problem {
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

fun main(args: Array<String>) { 
    SpasProcessor.submit(spasProblem)
}
```

# Primitive Logic Functions
SPAS language offers a set of "primitive" (basic) logic functions, which have been individually mapped to kotlin using LaTeX syntax.

They are mapped as follows:

| LaTeX Symbol | LaTeX Syntax    | Kotlin function     | Spas equivalent |
|--------------|-----------------|---------------------|-----------------|
| ∧            | \wedge          | wedge(X,Y)          | and(X,Y)        |
| ∨            | \vee            | ∨ee(X,Y)            | or(X,Y)         |
| ¬            | \lnot           | lnot(X,Y)           | not(X,Y)        |
| ⇒            | \Rightarrow     | Rightarrow(X,Y)     | implies(X,Y)    |
| ⇐            | \Leftarrow      | Leftarrow(X,Y)      | implied(X,Y)    |
| ⇔            | \Leftrightarrow | Leftrightarrow(X,Y) | equiv(X,Y)      |
| ∀            | \forall         | forall(X,Y)         | forall([X],Y)   |
| ∃            | \exists         | exists(X,Y)         | exists([X],Y)   |

Those are the alternative code syntaxes available on infixspascli:
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
forall("X", M)
forall("A", "B", "C", ..., matrix = M)
forall(listOf("A", "B", "C", ...), M)


// ∃ (EXISTENCE QUANTIFIER)
exists("X", M)
exists("A", "B", "C", ..., matrix = M)
exists(listOf("A", "B", "C", ...), M)
```

Notes:
- All more-than-binary relations support vararg inputs in prefix form.
- All binary relation functions support infix notation.
- All unary operators support prefix and postfix notation.
- The universal quantifiers receive `vararg` inputs + 1 string, so in case you are working with 2+ variables, the actual construct matrix has to be specified (if you want to use linear parameters), otherwise a list of variables can be supplied.
- No universal quantifiers infix functions have been created, because it doesn't make intuitive sense to type constructs with syntaxes like "X forall (X Rightarrow P)".

# Extended logic functions
Having the ability to compile to spas, we can take the liberty to create other useful logic functions on top of all the primitive ones, which acts as syntactic sugar.

| LaTeX Symbol | LaTeX Syntax    | Kotlin function     | Spas equivalent                     |
|--------------|-----------------|---------------------|-------------------------------------|
| ↑            | N.A. (NOR)      | nor(X,Y)            | not(or(X,Y))                        |
| ↓            | N.A. (NAND)     | nand(X,Y)           | nor(and(X,Y))                       |
| ⊕            | N.A. (XOR)      | xor(X,Y)            | or(and(X, not(Y)), and(not(X), Y))) |
| ⊙            | N.A. (XNOR)     | xnor(X,Y)           | or(and(X,Y), and(not(X), not(Y)))   |


# Usage & Build Details
This project is optimized (and recommended) for IntelliJ Idea.
Just clone the project and import it using the IDE. It will automatically prepare, download and index the required dependencies.

Under `scripter/src/main/kotlin` you will find a `Main.kt` file which has an example problem. 
You can use the "Run SPAS" configuration to compile and execute your code from the Run/Debug configuration menu.

This project targeted sdk version is 1.8 (Java 8 language features)

# Screenshots
### generated spas + parsed
<img src="https://i.imgur.com/s66jv8C.png" />

### generated spas + output + parsed
<img src="https://i.imgur.com/QJ46qou.png" />

### generates spas + output + error highlighting
<img src="https://i.imgur.com/TSwNuP1.png" />

# Why
Why not?
