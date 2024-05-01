package spartacodingclub.nbcamp.kotlinspring.assignment

import spartacodingclub.nbcamp.kotlinspring.assignment.core.CalculatorRunner

fun main() {
    var expression: String
//    val calculatorRunner = CalculatorRunner()

    while (true){
        print("Enter expression: ")

        expression = readln()
        when (expression.lowercase()) {
            "exit" -> break
            "clear" -> {
                CalculatorRunner.clearMemory()
                println("Calculator memory has been reset.")
            }
            else -> CalculatorRunner.execute(expression)
        }
        println()
    }

    println("Shutting down...")
}