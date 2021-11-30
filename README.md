# Infix SPAS CLI (With LaTeX notation)
This project is a Kotlin CLI used to access the [SPAS web APIs](https://webspass.spass-prover.org/) using Kotlin idiomatic and modern language features, while simultaneously mapping functions to match LaTeX actual operator naming conventions.


This repository includes:
- A Kotlin DSL to create propositional logic / first-order logic constants, functions and predicates to compose theorems and formulaes;
- A (partial) model of [SPAS syntax](https://webspass.spass-prover.org/help/spass-input-syntax15.pdf) for problems in the SPAS input syntax;
- A module for sending HTTP requests;
- An HTML scraper to extract the SPAS response output.


Main goals of this project are:
- Increase productivity by providing Kotlin language structure, safety and idiomaticity instead of a plain web form text editor;
- Minimize syntax errors (as they are detected by the IDE with statical analysis and compiler at compile time);
- Minimize semantic errors (as you dispose of a fully fledged, IDE-supported and syntax-highlighted programming language, to express relatively simple logic statements);
- Provide complete customizability of every field detailed in the SPAS documentation, including Setting flags, Name, Author, Description, State;
- Show line numbers to spas text (which isn't seen until it sent to the server but is needed only for debugging purposes);
- Output formatted and colored output in the terminal;
- Include automatic proof completion & error detection, filtering and highlighting;
- Provide syntactic flexibility, by allowing the relation notation type to be _prefix_, _infix_ or _postfix_, plus disabling explicit fully-parenthesized expressions (SPAS defaults to _prefix_ notation + fpe);


The source code in this repository can be used to model a SPAS problem using Kotlin classes & DSL construction. 
The created model can then be compiled to SPAS syntax, sent to the webserver, processed, and the response is parsed and analyzed back by the program.


There are 2 modules:
- `:core`: Contains the logic, scraping engine and models;
- `:scripter`: Contains `Main.kt` which can be used to create and execute SPAS models, depending on `:core`.

# Problem Anatomy & Kotlin Model
A Problem in SPAS syntax is defined by 5 components:
- Begin of problem
- Description
- Logic
- Settings
- End of problem

Each block is mandatory and has to be filled in. This is what makes its syntax tedious and error-prone.

We can define a `Problem` object by using the entry DSL-access function `problem {}`. 

This repopulates *all* the default problem components (`bop`, `desc`, `logic`, `settings`, `eop`) to their default values (which, apart from `logic` which is where the actual code has to be written, is not relevant to the final problem solution).

This makes it really fast to get started with the logic, since all the other 4 components can be left at their default value, or tailored later on.

Therefore, a complete declaration would look like this:

```kotlin
problem {
    beginProblem {}
    description {}

    logic {
        symbols {}

        formulae("axioms") {}

        formulae("conjectures") {}
    }

    settings {}
    endProblem {}
}
```

Which would compile to the same SPAS-code as this (since no code is added on the other blocks):

```kotlin
problem {
    logic {
        symbols {}
        
        formulae("axioms") {}
        
        formulae("conjectures") {}
    }
}
```

### Begin Problem
This is the first block in a SPAS file. 
It must have the following structure:

```text
begin_problem(GiraLaRotella).
```

The above SPAS text is generated by the following kotlin code:

```kotlin
beginProblem {
    identifier = "MyProblemId"
}
```

Where `GiraLaRotella` is the problem identifier.

### Description
This field is used to provide generic descriptive information about a problem.
It must have the following structure:
```
list_of_descriptions.
name({*SPAS Sample project*}).
author({*Andrea Cioccarelli*}).
status(unsatisfiable).
description({*Gira la Rotella*}).
end_of_list.
```

The above SPAS text is generated by the following kotlin code:

```kotlin
description {
    author = "Andrea Cioccarelli"
    name = "SPAS Sample project"
    description = "Gira la Rotella"
    status = ProbelmStatus.UNSATISFIABLE
}
```

Where:
- `name` is the project name;
- `author` is the project creator;
- `description` is the project description;
- `status` I have no damn clue about this one.

### Logic
The logic block is where you can define the theorem properties:

```
list_of_symbols.
    functions[(PI,0)].
    predicates[(Agata,0)].
end_of_list.


list_of_formulae(axioms).
end_of_list.

list_of_formulae(conjectures).
    formula(forall([X],equiv(Agata, Agata))).
end_of_list.
```

The above SPAS text is generated by the following kotlin code:

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

Where:
- Symbols: Defines the language constants, functions and predicates, divided in 2 groups, and represented by `Pair<String,Int>` (pairs of language letter and its arity):
  - `functions`: Functional letters (and constants);
  - `predicates`: Predicative letters.
- Axioms (Formulae): Language Axioms;
- Conjectures (Formulae): What be are trying to prove.

The data on `symbols` is generated by `symbolGroup`.
The data on `formulae` is generated by `formula`.

Putting it all together (as you can check in `scripter/.../Main.kt`):
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
            // Nessun assioma per il problema
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
Notably, declaring variables or function within the DSL does not alter the DSL data itself, as it is the case for `A`, `pi_greco` or `xVarLongName`.
They are used to simplifying the problem declaration process.


### Settings
This field is used to explicit flags for the SPAS interpreter.
It must have the following structure:
```
list_of_settings(SPASS).
{*
set_flag(PGiven,1).
set_flag(PProblem,1).
*}
end_of_list.
```

The above SPAS text is generated by the following kotlin code:

```kotlin
settings {
    flagShowProblem = true
    flagShowSolution = true
}
```

Where:
- `flagShowProblem` matches `PGiven` and will result in the interpreter analyzing and printing out the given problem;
- `flagShowSolution` matches `PProblem` and will result in the interpreter printing out the problem result;

### End Problem
This is the last block in a SPAS file.
It must have the following structure:

```text
end_problem.
```

The above SPAS text is generated by the following kotlin code:

```kotlin
endProblem {}
```

# Primitive Logic Functions
SPAS language offers a set of "primitive" (basic) logic functions, which have been individually mapped to kotlin using their matching LaTeX syntax.

| LaTeX Symbol | LaTeX Syntax      | Kotlin function       | SPAS equivalent   |
|--------------|-------------------|-----------------------|-------------------|
| ∧            | `\wedge`          | `wedge(X,Y)`          | `and(X,Y)`        |
| ∨            | `\vee`            | `vee(X,Y)`            | `or(X,Y)`         |
| ¬            | `\lnot`           | `lnot(X,Y)`           | `not(X,Y)`        |
| ⇒            | `\Rightarrow`     | `Rightarrow(X,Y)`     | `implies(X,Y)`    |
| ⇐            | `\Leftarrow`      | `Leftarrow(X,Y)`      | `implied(X,Y)`    |
| ⇔            | `\Leftrightarrow` | `Leftrightarrow(X,Y)` | `equiv(X,Y)`      |
| ∀            | `\forall`         | `forall(X,Y)`         | `forall([X],Y)`   |
| ∃            | `\exists`         | `exists(X,Y)`         | `exists([X],Y)`   |

Those are the alternative code syntaxes available:
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
- No universal quantifiers infix functions have been created, because it doesn't make intuitive sense to type constructs with syntaxes like `X forall (X Rightarrow P)`.

# Extended logic functions
Having the ability to compile to SPAS, we can take the liberty to create other useful logic functions on top of all the primitive ones, which acts as syntactic sugar.

| LaTeX Symbol | LaTeX Syntax    | Kotlin function       | SPAS equivalent                       |
|--------------|-----------------|-----------------------|---------------------------------------|
| ↑            | N.A. (NOR)      | `nor(X,Y)`            | `not(or(X,Y))`                        |
| ↓            | N.A. (NAND)     | `nand(X,Y)`           | `nor(and(X,Y))`                       |
| ⊕            | N.A. (XOR)      | `xor(X,Y)`            | `or(and(X, not(Y)), and(not(X), Y)))` |
| ⊙            | N.A. (XNOR)     | `xnor(X,Y)`           | `or(and(X,Y), and(not(X), not(Y)))`   |

### Processor parameters
The `SpasProcessor` is parameterizable (mainly for testing purposes) as follows:

````kotlin
SpasProcessor.submit(spasProblem) {
    compileOnly = true
    silentMode = false
    analyzeResult = true
    lineNumbers = false
}
````

# Usage & Build Details
This project is optimized (and recommended) for IntelliJ Idea.
Just clone the project and import it using the IDE. It will automatically prepare, download and index the required dependencies.

Under `scripter/src/main/kotlin` you will find a `Main.kt` file which has an example problem. 
You can use the "Run SPAS" configuration to compile and execute your code from the Run/Debug configuration menu.

This project targeted sdk version is 1.8 (Java 8 language features)

# Screenshots
### generated SPAS + parsed
<img src="https://i.imgur.com/s66jv8C.png" />

### generated SPAS + output + parsed
<img src="https://i.imgur.com/QJ46qou.png" />

### generates SPAS + output + error highlighting
<img src="https://i.imgur.com/TSwNuP1.png" />

# Why
Why not?
