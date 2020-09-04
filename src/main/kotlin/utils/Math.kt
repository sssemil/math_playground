package utils

val fastLookupFactorial = hashMapOf<Long, Long>()

fun factorialNaive(n: Long): Long {
    if (n <= 1) return 1
    return n * factorialNaive(n - 1)
}

fun factorialDynamic(n: Long): Long {
    if (n <= 1) return 1
    if (!fastLookupFactorial.containsKey(n)) {
        fastLookupFactorial[n] = n * factorialDynamic(n - 1)
    }
    return fastLookupFactorial[n]!!
}

fun factorial(n: Long): Rational = Rational(factorialDynamic(n), 1)

// broken for large values
fun binom0(n: Long, k: Long): Rational {
    check(k in 0..n && n >= 0)
    if ((n == k) || (k == 0L)) return Rational(1, 1)
    return Rational(factorial(n), (factorial(k) * factorial(n - k)))
}

fun binom1(n: Long, k: Long): Rational {
    check(k in 0..n && n >= 0)
    if ((n == k) || (k == 0L)) return Rational(1, 1)
    return binom1(n - 1, k - 1) + binom1(n - 1, k)
}

fun binom(n: Long, k: Long) = binom1(n, k)

/**
 * Partial derangements.
 *
 * [https://en.wikipedia.org/wiki/Rencontres_numbers]
 *
 */
fun rencontresNumber(n: Long, m: Long): Rational {
    val a = Rational(factorial(n), factorial(m))
    val sum = (0..n - m).map { k ->
        if (k % 2 == 0L) {
            Rational(1, factorial(k))
        } else {
            Rational(-1, factorial(k))
        }
    }.sum()
    return a*sum
}