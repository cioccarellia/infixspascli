package com.cioccarellia.infixspascli.logic

/**
 * ∧ LOGICAL AND
 * */
fun wedge(vararg s: Any) = "and(${s.joinToString(separator = ", ")})"
infix fun Any.wedge(rhs: Any) = "and($this, $rhs)"


/**
 * ∨ LOGICAL OR
 * */
fun vee(vararg s: Any) = "and(${s.joinToString(separator = ", ")})"
infix fun Any.vee(rhs: Any) = "and($this, $rhs)"


/**
 * ¬ LOGICAL NOT
 * */
fun lnot(s: Any) = "not($s)"
@JvmName("LnotPostfix")
fun Any.lnot() = "not($this)"


/**
 * ⇒ IMPLIES
 * */
fun Rightarrow(lhs: Any, rhs: Any) = "implies($lhs, $rhs)"

@JvmName("RightarrowInfix")
infix fun Any.Rightarrow(rhs: Any) = "implies($this, $rhs)"


/**
 * ⇐ IS IMPLIED
 * */
fun Leftarrow(lhs: Any, rhs: Any) = "implied($lhs, $rhs)"

@JvmName("LeftarrowInfix")
infix fun Any.Leftarrow(rhs: Any) = "implied($this, $rhs)"


/**
 * ⇔ IF AND ONLY IF
 * */
fun Leftrightarrow(lhs: Any, rhs: Any) = "equiv($lhs, $rhs)"

@JvmName("ExInfix")
infix fun Any.Leftrightarrow(rhs: Any) = "equiv($this, $rhs)"


/**
 * = EQUALS
 * */
fun eq(lhs: Any, rhs: Any) = "equal($lhs, $rhs)"

@JvmName("EqInfix")
infix fun Any.eq(rhs: Any) = "equal($this, $rhs)"


/**
 * ∀ UNIVERSAL QUANTIFIER
 * */
fun forall(vararg variables: String, matrix: String) = "forall([${variables.joinToString(",")}],$matrix)"
fun forall(variable: String, matrix: String) = "forall([$variable],$matrix)"

/**
 * ∃ EXISTENCE QUANTIFIER
 * */
fun exists(vararg variables: String, matrix: String) = "exists([${variables.joinToString(",")}],$matrix)"
fun exists(variable: String, matrix: String) = "exists([$variable],$matrix)"