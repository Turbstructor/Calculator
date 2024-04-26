package spartacodingclub.nbcamp.kotlinspring.assignment.core.exception

class DivisionByZeroException(message: String? = null, cause: Throwable? = null): IllegalArgumentException(message, cause)
class InvalidOperatorException(message: String? = null, cause: Throwable? = null): IllegalArgumentException(message, cause)
class UnparsableExpressionException(message: String? = null, cause: Throwable? = null): IllegalArgumentException(message, cause)
