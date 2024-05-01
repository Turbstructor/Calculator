package spartacodingclub.nbcamp.kotlinspring.assignment.core

import spartacodingclub.nbcamp.kotlinspring.assignment.core.exception.*
import spartacodingclub.nbcamp.kotlinspring.assignment.core.operation.*

class Calculator(expression: String) {
    private var operand: DoubleArray = DoubleArray(2)
    var result: Double = 0.0

    private var operation: AbstractOperation

    init {
        val parts = expression.split(" ")

        operand[0] = parts[0].toDouble()
        operand[1] = parts[2].toDouble()

        operation = when (parts[1]) {
            "+" -> AddOperation()
            "-" -> SubtractOperation()
            "*" -> MultiplyOperation()
            "/" -> DivideOperation()
            "%" -> RemainderOperation()
            else -> throw InvalidOperatorException(parts[1])
        }
    }

    fun calculate() {
        result = operation.operate(operand[0], operand[1])
    }
}
