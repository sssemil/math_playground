package dwt

import com.marcinmoskala.math.permutations
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import utils.Rational
import kotlin.math.roundToInt
import kotlin.random.Random

class Dwt43 {

    private fun sim(maxN: Int, iterations: Int): Pair<Int, Deferred<Map<Int, List<Int>>>> =
        Pair(maxN, GlobalScope.async {
            val stats = mutableListOf<Int>()
            repeat(iterations) {
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

                stats.add(count)
            }
            stats.groupBy { it }.toSortedMap()
        })

    private fun calc(maxN: Int): Pair<Int, Deferred<Map<Int, List<Int>>>> {
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

        return Pair(maxN, GlobalScope.async { shortPerms.map { it.size }.groupBy { it }.toSortedMap() })
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
        val maxMaxN = 9
        val resSim = mutableListOf<Pair<Int, Deferred<Map<Int, List<Int>>>>>()
        val resCalc = mutableListOf<Pair<Int, Deferred<Map<Int, List<Int>>>>>()
        var i = 9
        while (i <= maxMaxN) {
            resSim.add(sim(i, iterations))
            resCalc.add(calc(i))
            i++
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