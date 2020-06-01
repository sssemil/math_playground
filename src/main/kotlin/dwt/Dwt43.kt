package dwt

import com.marcinmoskala.math.permutations
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import utils.Rational
import kotlin.math.roundToInt
import kotlin.random.Random

class Dwt43 {

    /**
     * This function simulates taking balls.
     *
     * @param maxN number of balls.
     * @param iterations number of experiments to perform.
     */
    private fun simAsync(maxN: Int, iterations: Int) = GlobalScope.async {
        (1..iterations).map {
            val balls = (1..maxN).toMutableList()

            var count = 0
            var prev = 0

            while (balls.isNotEmpty()) {
                val ri = if (balls.size <= 1) 0 else Random.nextInt(balls.size - 1)
                val curr = balls[ri]
                balls.removeAt(ri)

                count++
                if (prev > curr) {
                    break
                }
                prev = curr
            }

            count
        }.groupBy {
            it
        }.toSortedMap()
    }

    private fun calcAsync(maxN: Int) = GlobalScope.async {
        val perms = Array(maxN) { it + 1 }.toSet().permutations()
        val shortPerms = perms.map { perm ->
            var count = 0
            var prev = 0
            for (curr in perm) {
                count++
                if (prev > curr) {
                    break
                }
                prev = curr
            }

            perm.subList(0, count)
        }

        shortPerms.map { it.size }.groupBy { it }.toSortedMap()
    }

    fun main() = runBlocking {
//        val x = (1..20L).map { k ->
//            k / ((k + 1) * factorialDynamic(k - 1)).toDouble()
//        }.sum()
//        val y = (1..20L).map { k ->
//            1 / factorial(k - 1).toDouble()
//        }.sum()
        //------------------------------------------------------------

        val iterations = 10_000_000

        val minMaxN = 10
        val maxMaxN = 10

        val resSim = (minMaxN..maxMaxN).map {
            Pair(it, simAsync(it, iterations))
        }

        resSim.forEach { (n, rsDeferred) ->
            rsDeferred.await().let { rs ->
                check(rs.toList().sumBy { it.second.size } == iterations)
                println("--------------------------------------------------------------------------------")
                rs.forEach { (k, results) ->
                    val r = Rational(results.size, rs.values.sumBy { it.size })
                    println("f(n = $n, k = $k) = $r = ${r.toDouble()}; inv = ${1 / r.toDouble()} = ${(1 / r.toDouble()).roundToInt()}")
                }
                println("--------------------------------------------------------------------------------")
            }
        }

        val resCalc = (minMaxN..maxMaxN).map {
            Pair(it, calcAsync(it))
        }

        resCalc.forEach { (n, rsDeferred) ->
            rsDeferred.await().let { rs ->
                println("--------------------------------------------------------------------------------")
                rs.forEach { (k, results) ->
                    val r = Rational(results.size, rs.values.sumBy { it.size })
                    println("f(n = $n, k = $k) = $r = ${r.toDouble()}; inv = ${1 / r.toDouble()} = ${(1 / r.toDouble()).roundToInt()}")
                }
                println("--------------------------------------------------------------------------------")
            }
        }
    }
}

fun main() {
    Dwt43().main()
}