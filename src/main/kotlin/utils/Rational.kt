package utils

import kotlin.math.max

class Rational(
    private var numerator: Long,
    private var denominator: Long
) : Number() {

    constructor(numerator: Number, denominator: Number) : this(numerator.toLong(), denominator.toLong())

    fun simplify(): Rational {
        for (i in 2..max(numerator, denominator)) {
            while (numerator % i == 0L && denominator % i == 0L) {
                numerator /= i
                denominator /= i
            }
        }

        return this
    }

    fun isZero() = numerator == 0L

    fun isOne() = !isZero() && numerator == denominator

    override fun toString(): String = "$numerator/$denominator"

    override fun toDouble(): Double = numerator.toDouble() / denominator.toDouble()

    override fun toByte(): Byte = toDouble().toByte()

    override fun toChar(): Char = toDouble().toChar()

    override fun toFloat(): Float = toDouble().toFloat()

    override fun toInt(): Int = toDouble().toInt()

    override fun toLong(): Long = toDouble().toLong()

    override fun toShort(): Short = toDouble().toShort()

    operator fun div(other: Int): Rational {
        return Rational(this.numerator, this.denominator * other)
    }

    operator fun plus(other: Rational): Rational {
        if (this.isZero()) return other
        return Rational(
            (this.numerator * other.denominator) + (other.numerator * this.denominator),
            this.denominator * other.denominator
        ).simplify()
    }

    operator fun div(other: Rational): Rational {
        return Rational(this.numerator * other.denominator, this.denominator * other.numerator)
    }

    operator fun times(other: Rational): Rational {
        return Rational(this.numerator * other.numerator, this.denominator * other.denominator)
    }
}