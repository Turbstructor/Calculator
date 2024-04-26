package assignment.kotlinspring.nbcamp.spartacodingclub

import spartacodingclub.nbcamp.kotlinspring.assignment.core.Calculator

fun main() {
    var expression: String
    val calculator = Calculator()

    while (true){
        print("Enter expression: ")
        expression = readln()

        if (expression.lowercase() == "exit") break
        if (expression.lowercase() == "clear"){ calculator.clearMemory(); println("Calculator memory has been reset.\n"); continue }

        calculator.calculate(expression)

        println(calculator.getResult())
        println()
    }

    println("Shutting down...")
}