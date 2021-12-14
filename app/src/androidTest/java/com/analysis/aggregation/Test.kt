package com.analysis.aggregation

import org.junit.Test

class Test {
    @Test
    fun test_kotlin_grammar() {
        var str: String = "    jalggn dadfkja ald  "
        println(str)
        println("*".repeat(10))
        println(str?.replace("\\s".toRegex(), ""))
    }
}
