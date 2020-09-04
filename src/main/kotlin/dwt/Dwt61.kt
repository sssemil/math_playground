package dwt

import com.marcinmoskala.math.permutations
import com.marcinmoskala.math.pow
import utils.Rational
import utils.factorial
import utils.rencontresNumber
import utils.stat.DiscreteVariable
import utils.toRational

class Dwt61 {

    private fun calc(n: Int) {
        val discreteVariable = DiscreteVariable((0 until n).toList().permutations().let { permutations ->
            permutations.groupBy {
                var count = 0
                it.forEachIndexed { i, x ->
                    if (i == x) {
                        count++
                    }
                }
                count
            }.map {
                DiscreteVariable.Entry(
                    it.key.toRational(),
                    Rational(it.value.size, permutations.size)
                )
            }
        })

        val expectedValue = discreteVariable.expectedValue { it.value.toInt() >= n / 2 }
        val variance = discreteVariable.variance { it.value.toInt() >= n / 2 }

        val testExpectedValue = Rational(2.pow(n - 1), factorial((n - 1).toLong()))

        println("---------------------------------------------------------------")
        println(discreteVariable)
        println("---------------------------------------------------------------")
        println("E[X_$n >= ${n / 2}] = $expectedValue = ${expectedValue.toDouble()}")
        println("Var[X_$n >= ${n / 2}] = $variance = ${variance.toDouble()}")
        println("---------------------------------------------------------------")
        println("F = $testExpectedValue = ${testExpectedValue.toDouble()}")
        println("---------------------------------------------------------------")
    }

    private fun calcd(n: Int) {
        val values = (0 until n).toList().permutations().groupBy {
            var count = 0
            it.forEachIndexed { i, x ->
                if (i == x) {
                    count++
                }
            }
            count
        }.toSortedMap()

        println("${values}")
    }

    fun printRencontresNumbers() {
        for (n in 0L..7L) {
            for (k in 0L..n) {
                val dnk = rencontresNumber(n, k)
                print("${dnk.toInt()} ")
            }
            println()
        }
    }

    fun main() {
        (1..8).forEach {
            calcd(it)
        }
    }
}

fun main() {
    Dwt61().printRencontresNumbers()
}