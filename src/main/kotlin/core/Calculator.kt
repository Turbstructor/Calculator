package spartacodingclub.nbcamp.kotlinspring.assignment.core

import spartacodingclub.nbcamp.kotlinspring.assignment.core.exception.*
import spartacodingclub.nbcamp.kotlinspring.assignment.core.operation.*

class Calculator {
    private var operand: DoubleArray = DoubleArray(2)
    private var arithmeticOperator: String = ""

    private var result: Double = 0.0
    private var memory: Double = 0.0
    private var expression: String = ""

    private lateinit var operation: AbstractOperation

    private var isExpressionValid = true
    private var isExpressionUnparsable = false
    private var isOperatorWrong = false
    private var isDividingByZero = false

    private var isExpressionAccumulative = false

    fun clearMemory() { result = 0.0; memory = 0.0 }

    private fun parseExpression(expressionInput: String) {
        isExpressionUnparsable = false
        isOperatorWrong = false
        isDividingByZero = false
        isExpressionAccumulative = false

        expression = expressionInput

        try {
            val parts: List<String> = expression.split(" ")
            if (parts.size !in 2..3) throw UnparsableExpressionException(expression)

            isExpressionAccumulative = (parts.size == 2)


            operand[0] = if (isExpressionAccumulative) result else parts[0].toDouble()
            arithmeticOperator = if (isExpressionAccumulative) parts[0] else parts[1]
            operand[1] = (if (isExpressionAccumulative) parts[1] else parts[2]).toDouble()

            operation = when (arithmeticOperator) {
                "+" -> AddOperation()
                "-" -> SubtractOperation()
                "*" -> MultiplyOperation()
                "/" -> {
                    if (operand[1].compareTo(0) == 0) throw DivisionByZeroException()
                    else DivideOperation()
                }
                "%" -> {
                    if (operand[1].compareTo(0) == 0) throw DivisionByZeroException()
                    else RemainderOperation()
                }
                else -> throw InvalidOperatorException(arithmeticOperator)
            }
        } catch(e: NumberFormatException) {
            isExpressionUnparsable = true
        } catch(e: UnparsableExpressionException) {
            isExpressionUnparsable = true
        } catch(e: InvalidOperatorException) {
            isOperatorWrong = true
        } catch(e: DivisionByZeroException) {
            isDividingByZero = true
        }
    }

    private fun simplify(value: Double): Any = if (value % 1.0 == 0.0) value.toLong() else value

    private fun getFullExpression(): String = (
            "${simplify(if (isExpressionAccumulative) memory else operand[0])} " +
                    "$arithmeticOperator ${simplify(operand[1])} = ${simplify(result)}"
            )

    private fun getErrorMessage(): String = (
            "Wrong expression!" +
                    (if (isExpressionUnparsable) "\nUnparsable expression: $expression" else "") +
                    (if (isOperatorWrong) "\nWrong operator: $arithmeticOperator" else "") +
                    (if (isDividingByZero) "\nInvalid division: cannot divide by zero" else "")
            )

    fun calculate(expressionInput: String) {
        parseExpression(expressionInput)

        isExpressionValid = !(isExpressionUnparsable || isOperatorWrong || isDividingByZero) // multiple flags can exist

        if (isExpressionValid) {
            memory = result
            result = operation.operate(operand[0], operand[1])
        }
    }

    fun getResult(): String = if(isExpressionValid) getFullExpression() else getErrorMessage()
}
