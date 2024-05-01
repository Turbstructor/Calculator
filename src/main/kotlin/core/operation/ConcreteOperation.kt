package spartacodingclub.nbcamp.kotlinspring.assignment.core.operation

import spartacodingclub.nbcamp.kotlinspring.assignment.core.exception.DivisionByZeroException

class AddOperation: AbstractOperation {
    override fun operate(a: Double, b: Double): Double = (a + b)
}

class SubtractOperation: AbstractOperation {
    override fun operate(a: Double, b: Double): Double = (a - b)
}

class MultiplyOperation: AbstractOperation {
    override fun operate(a: Double, b: Double): Double = (a * b)
}

class DivideOperation: AbstractOperation {
    override fun operate(a: Double, b: Double): Double {
        when (b.compareTo(0)) {
            0 -> throw DivisionByZeroException()
            else -> return (a / b)
        }
    }
}

class RemainderOperation: AbstractOperation {
    override fun operate(a: Double, b: Double): Double {
        when (b.compareTo(0)) {
            0 -> throw DivisionByZeroException()
            else -> return (a % b)
        }
    }
}
