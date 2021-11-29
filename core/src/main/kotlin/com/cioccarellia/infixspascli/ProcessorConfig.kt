package com.cioccarellia.infixspascli

data class ProcessorConfig(
    var compileOnly: Boolean = false,
    var silentMode: Boolean = false,
    var analyzeResult: Boolean = true,
    var lineNumbers: Boolean = true
)