package com.cioccarellia.infixspascli.logic

/**
 * ↑ NOR
 * */
fun nor(vararg s: Any) = "not(or((${s.joinToString(separator = ", ")}))"
infix fun Any.nor(rhs: Any) = "not(or(($this, $rhs))"


/**
 * ↓ NAND
 * */
fun nand(vararg s: Any) = "not(and((${s.joinToString(separator = ", ")}))"
infix fun Any.nand(rhs: Any) = "not(and(($this, $rhs))"

/**
 * ⊕ XOR
 * */
// fun xor(vararg s: Any) = TODO()
infix fun Any.xor(rhs: Any) = "or(and($this, not($rhs)), and(not($this), $rhs)))"


/**
 * ⊙ XNOR
 * */
// fun xnor(vararg s: Any) = TODO()
infix fun Any.xnor(rhs: Any) = "not(${this.xor(rhs)})"