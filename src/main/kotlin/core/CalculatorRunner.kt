package spartacodingclub.nbcamp.kotlinspring.assignment.core

import spartacodingclub.nbcamp.kotlinspring.assignment.core.exception.*

object CalculatorRunner {
    private var memory: Double = 0.0

    fun clearMemory() { memory = 0.0 }

    private fun getFullExpression(expression: String): String {
        return when (expression.split(" ").size) {
            2 -> "$memory $expression"
            3 -> expression
            else -> throw UnparsableExpressionException(expression)
        }
    }

    private fun getSimplifiedNumber(value: Double): Number = if (value % 1.0 == 0.0) value.toLong() else value
    private fun getFullExpressionWithResultSimplified(expression: String): String {
        val parts = expression.split(" ")
        return "${getSimplifiedNumber(parts[0].toDouble())} ${parts[1]} ${getSimplifiedNumber(parts[2].toDouble())} ${parts[3]} ${getSimplifiedNumber(parts[4].toDouble())}"
    }

    fun execute(expressionInput: String) {
        try {
            var expression = getFullExpression(expressionInput)
            val calculator = Calculator(expression)

            calculator.calculate()
            memory = calculator.result

            expression = getFullExpressionWithResultSimplified("$expression = $memory")
            println(expression)

        } catch (e: UnparsableExpressionException) {
            println("Wrong Expression!")
            println("Cannot understand expression \"${e.message}\"")
        } catch (e: NumberFormatException) {
            println("Wrong Expression!")
            println("Cannot parse into double ${e.message}")
        } catch (e: InvalidOperatorException) {
            println("Wrong Expression!")
            println("Invalid Operator given: \"${e.message}\"")
        } catch (e: DivisionByZeroException) {
            println("Wrong Expression!")
            println("Division by zero is forbidden")
        }
    }
}
