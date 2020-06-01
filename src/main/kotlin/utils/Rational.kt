package utils

import kotlin.math.max

/**
 * Some ideas and some code part ~taken~ from [https://github.com/sanity/kotlin-rational/] and from
 * [https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/util/Rational.java].
 */
@PublicApi
class Rational() : Number(), Comparable<Rational> {

    var numerator: Int = 0
        @PublicApi
        private set

    var denominator: Int = 0
        @PublicApi
        private set

    constructor(numerator: Number, denominator: Number) : this() {
        this.numerator = numerator.toInt()
        this.denominator = denominator.toInt()

        reduce()
    }

    constructor(rational: Rational) : this(rational.numerator, rational.denominator)

    constructor(number: Number) : this(number.toRational())

    private fun reduce() {
        if (this.denominator < 0) {
            this.numerator = -this.numerator
            this.denominator = -this.denominator
        }

        if (this.denominator == 0 && this.numerator > 0) {
            // +Infinity
            this.numerator = 1
            this.denominator = 0
        } else if (this.denominator == 0 && this.numerator < 0) {
            // -Infinity
            this.numerator = 1
            this.denominator = 0
        } else if (this.denominator == 0 && this.numerator == 0) {
            // NaN
            this.numerator = 0
            this.denominator = 0
        } else if (this.numerator == 0) {
            this.numerator = 0
            this.denominator = 1
        } else {
            simplify()
        }
    }

    @PublicApi
    fun simplify(): Rational {
        for (i in 2..max(numerator, denominator)) {
            while (numerator % i == 0 && denominator % i == 0) {
                numerator /= i
                denominator /= i
            }
        }

        return this
    }

    @PublicApi
    val isNaN: Boolean
        get() = denominator == 0 && numerator == 0

    @PublicApi
    val isInfinite: Boolean
        get() = numerator != 0 && denominator == 0

    @PublicApi
    val isFinite: Boolean
        get() = denominator != 0

    @PublicApi
    val isZero: Boolean
        get() = isFinite && numerator == 0

    @PublicApi
    val isPosInf: Boolean
        get() = denominator == 0 && numerator > 0

    @PublicApi
    val isNegInf: Boolean
        get() = denominator == 0 && numerator < 0

    @PublicApi
    val isOne: Boolean
        get() = !isZero && numerator == denominator

    override fun toString(): String = when {
        isNaN -> "NaN"
        isPosInf -> "Infinity"
        isNegInf -> "-Infinity"
        else -> "$numerator/$denominator"
    }

    override fun toDouble(): Double = numerator.toDouble() / denominator.toDouble()

    override fun toFloat(): Float = toDouble().toFloat()

    override fun toByte(): Byte = toDouble().toByte()

    override fun toChar(): Char = toDouble().toChar()

    override fun toShort(): Short = when {
        isPosInf -> Short.MAX_VALUE
        isNegInf -> Short.MIN_VALUE
        isNaN -> 0
        else -> (numerator / denominator).toShort()
    }

    override fun toInt(): Int = when {
        isPosInf -> Int.MAX_VALUE
        isNegInf -> Int.MIN_VALUE
        isNaN -> 0
        else -> numerator / denominator
    }

    override fun toLong(): Long = when {
        isPosInf -> Long.MAX_VALUE
        isNegInf -> Long.MIN_VALUE
        isNaN -> 0
        else -> (numerator / denominator).toLong()
    }

    operator fun plus(other: Number): Rational {
        if (other is Rational) {
            if (this.isNaN || other.isNaN) return NaN
            if (this.isZero) return other
            if (this.isPosInf && other.isPosInf) return POSITIVE_INFINITY
            if (this.isNegInf && other.isNegInf) return NEGATIVE_INFINITY
            if (this.isInfinite && other.isInfinite && this.isPosInf != other.isNegInf) return NaN
            if (this.isPosInf || other.isPosInf) return POSITIVE_INFINITY
            if (this.isNegInf || other.isNegInf) return NEGATIVE_INFINITY

            return Rational(
                (this.numerator * other.denominator) + (other.numerator * this.denominator),
                this.denominator * other.denominator
            )
        } else {
            return this + other.toRational()
        }
    }

    operator fun minus(other: Number): Rational {
        return if (other is Rational) {
            this + (-other)
        } else {
            this - other.toRational()
        }
    }

    operator fun times(other: Number): Rational {
        if (other is Rational) {
            if (this.isNaN || other.isNaN) return NaN
            if (this.isZero || other.isZero) return ZERO

            return Rational(
                this.numerator * other.numerator,
                this.denominator * other.denominator
            )
        } else {
            return this * other.toRational()
        }
    }

    operator fun div(other: Number): Rational {
        return if (other is Rational) {
            this * Rational(other.denominator, other.numerator)
        } else {
            this / other.toRational()
        }
    }

    private operator fun unaryMinus(): Rational {
        if (!this.isNaN && !this.isZero) {
            return Rational(-this.numerator, this.denominator)
        }
        return this
    }

    override fun compareTo(other: Rational): Int {
        if (equals(other)) {
            return 0
        } else if (this.isNaN) {
            return 1
        } else if (other.isNaN) {
            return -1
        } else if (this.isPosInf || this.isNegInf) {
            return 1
        } else if (this.isNegInf || other.isPosInf) {
            return -1
        }

        // using long to avoid overflow
        val thisNumerator: Long = this.numerator.toLong() * other.denominator.toLong()
        val otherNumerator: Long = other.numerator.toLong() * this.denominator.toLong()

        return when {
            thisNumerator < otherNumerator -> -1
            thisNumerator > otherNumerator -> 1
            else -> 0
        }
    }

    override fun equals(other: Any?): Boolean {
        return (other as? Rational?)?.let {
            if (this.isNaN && !other.isNaN) {
                false
            } else if (this.isNegInf && !other.isNegInf) {
                false
            } else if (this.isPosInf && !other.isPosInf) {
                false
            } else if (this.isZero && !other.isZero) {
                false
            } else {
                this.denominator.toLong() * other.numerator.toLong() ==
                        other.denominator.toLong() * this.numerator.toLong()
            }
        } ?: false
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }

    companion object {

        @PublicApi
        val NaN = Rational(0, 0)

        @PublicApi
        val POSITIVE_INFINITY = Rational(1, 0)

        @PublicApi
        val NEGATIVE_INFINITY = Rational(-1, 0)

        @PublicApi
        val ZERO = Rational(0, 1)
    }
}

private fun Number.toRational(): Rational {
    if (this is Int || this is Long || this is Short || this is Byte) {
        return Rational(this, 1)
    } else {
        val numberAsDouble = this.toDouble()

        if (this is Double || this is Float) {
            if (numberAsDouble.isInfinite() && numberAsDouble > 0) {
                return Rational.POSITIVE_INFINITY
            } else if (numberAsDouble.isInfinite() && numberAsDouble < 0) {
                return Rational.NEGATIVE_INFINITY
            } else if (numberAsDouble.isNaN()) {
                return Rational.NaN
            } else if (numberAsDouble == 0.0) {
                return Rational.ZERO
            }
        }

        // covert double to rational otherwise
        // stolen from https://stackoverflow.com/questions/13222664/convert-floating-point-number-into-a-rational-number-in-java#comment18013818_13222664

        // [1 bit sign | 11 bits exponent | 52 bits fraction]

        val bits = java.lang.Double.doubleToLongBits(numberAsDouble)
        val sign = bits.ushr(63)
        val exponent = (((bits.ushr(52)) xor (sign shl 11)) - 1023)
        val fraction = (bits shl 12)
        var a = 1.toLong()
        var b = 1.toLong()
        var i = 63

        while (i >= 12) {
            a = a * 2.toLong() + ((fraction.ushr(i)) and 1)
            b *= 2.toLong()
            i--
        }

        if (exponent > 0) {
            a *= 1 shl exponent.toInt()
        } else {
            b *= 1 shl -exponent.toInt()
        }

        if (sign == 1.toLong()) {
            a *= -1
        }

        return Rational(a, b)
    }
}
