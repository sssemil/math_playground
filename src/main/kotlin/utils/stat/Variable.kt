package utils.stat

import utils.Rational

interface Variable {

    fun expectedValue(filter: (DiscreteVariable.Entry) -> Boolean): Rational

    fun variance(filter: (DiscreteVariable.Entry) -> Boolean): Rational
}