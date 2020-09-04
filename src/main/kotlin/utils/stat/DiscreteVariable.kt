package utils.stat

import utils.Rational

class DiscreteVariable(
    private val entries: List<Entry>
) : Variable {

    init {
        require(entries.fold(Rational.ZERO) { acc, it -> acc + it.probability } == Rational(1))
    }

    override fun expectedValue(filter: (Entry) -> Boolean): Rational = entries
        .filter(filter)
        .fold(Rational.ZERO) { acc, it ->
            acc + (it.value * it.probability)
        }

    private fun expectedValueSquared(filter: (Entry) -> Boolean): Rational = entries
        .filter(filter)
        .fold(Rational.ZERO) { acc, it ->
            acc + (it.value * it.value * it.probability)
        }

    override fun variance(filter: (Entry) -> Boolean): Rational = expectedValue(filter).let {
        expectedValueSquared(filter) - it * it
    }

    override fun toString() = entries.joinToString("\n") {
        "${it.value} -> ${it.probability}"
    }

    data class Entry(val value: Rational, val probability: Rational)
}
