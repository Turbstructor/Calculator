package assignment.kotlinspring.nbcamp.spartacodingclub

import spartacodingclub.nbcamp.kotlinspring.assignment.core.Calculator

fun main() {
    var expression: String
    val calculator = Calculator()

    while (true){
        print("Enter expression: ")
        expression = readln()

        if (expression == "exit") break
        if (expression == "clear"){ calculator.clearMemory(); continue }

        calculator.calculate(expression)
        expression = calculator.getFullExpression()

        println(if (calculator.isExpressionValid) expression else calculator.errorMessage)
        println()
    }

    println("Shutting down...")
}