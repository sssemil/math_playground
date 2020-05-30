package dwt

import utils.Rational
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.random.Random

class Dwt34 {

    private fun sim(
        aOffset: Int,
        bOffset: Int,
        winningDistance: Int = 4,
        iterations: Int = 1_000_000
    ): Rational {
        return Rational((0 until iterations).map {
            var aPath = aOffset
            var bPath = bOffset

            while (abs(aPath - bPath) < winningDistance) {
                if (Random.nextBoolean()) aPath++
                else bPath++
            }

            Pair(aPath, bPath)
        }.sumBy { max(it.first, it.second) }, iterations)
    }

    fun main() {
        sim(1, 1, 4).let { sim ->
            println(
                "E[winningDistance = 4, aOffset = 1, bOffset = 1] = ${sim.toDouble()} = ${sim.toDouble().roundToInt()}"
            )
        }
        sim(0, 0, 4).let { sim ->
            println(
                "E[winningDistance = 4, aOffset = 0, bOffset = 0] = ${sim.toDouble()} = ${sim.toDouble().roundToInt()}"
            )
        }
        sim(1, 0, 4).let { sim ->
            println(
                "E[winningDistance = 4, aOffset = 1, bOffset = 0] = ${sim.toDouble()} = ${sim.toDouble().roundToInt()}"
            )
        }
        sim(2, 0, 4).let { sim ->
            println(
                "E[winningDistance = 4, aOffset = 2, bOffset = 0] = ${sim.toDouble()} = ${sim.toDouble().roundToInt()}"
            )
        }
        sim(3, 0, 4).let { sim ->
            println(
                "E[winningDistance = 4, aOffset = 3, bOffset = 0] = ${sim.toDouble()} = ${sim.toDouble().roundToInt()}"
            )
        }
        sim(4, 0, 4).let { sim ->
            println(
                "E[winningDistance = 4, aOffset = 4, bOffset = 0] = ${sim.toDouble()} = ${sim.toDouble().roundToInt()}"
            )
        }

//        (1..10).forEach { i ->
//            sim(0, 0, i).let { sim ->
//                println(
//                    "E[winningDistance = $i, aOffset = 0, bOffset = 0] = $sim = ${sim.toDouble()} = ${sim.toDouble()
//                        .roundToInt()}"
//                )
//            }
//        }
//        (1..10).forEach { i ->
//            sim(1, 1, i).let { sim ->
//                println(
//                    "E[winningDistance = $i, aOffset = 1, bOffset = 1] = $sim = ${sim.toDouble()} = ${sim.toDouble()
//                        .roundToInt()}"
//                )
//            }
//        }
    }
}

fun main() {
    Dwt34().main()
}