package dwt

import utils.*

class Dwt41 {

    private fun hypergeometricMass(r: Long = 3, a: Long = 52 - 13, b: Long = 13) =
        { x: Long ->
            binom(b, x) * binom(a, r - x) / binom(a + b, r)
        }

    fun test13() {
        val hypergeometricMassFunction = hypergeometricMass(r = 3, a = 52 - 13, b = 13)
        (0..3L).forEach {
            val r = hypergeometricMassFunction(it)
            println("[T13][$it] = $r = ${r.toDouble()}")
        }
    }

    fun test4() {
        val hypergeometricMassFunction = hypergeometricMass(r = 3, a = 52 - 4, b = 4)
        (0..3L).forEach {
            val r = hypergeometricMassFunction(it)
            println("[T4][$it] = $r = ${r.toDouble()}")
        }
    }

    fun testX() {
        val r1 = Rational(
            binom(13, 3) * binom(13, 0) * binom(13, 0) * binom(13, 0)
                    * factorial(4) / factorial(3),
            binom(52, 3)
        )

        println("testX: r1 = $r1 = ${r1.toDouble()}")

        val r2 = Rational(
            binom(13, 2) * binom(13, 1) * binom(13, 0) * binom(13, 0)
                    * factorial(4) / factorial(2),
            binom(52, 3)
        )

        println("testX: r2 = $r2 = ${r2.toDouble()}")

        val r3 = Rational(
            binom(13, 1) * binom(13, 1) * binom(13, 1) * binom(13, 0)
                    * factorial(4) / factorial(3),
            binom(52, 3)
        )

        println("testX: r3 = $r3 = ${r3.toDouble()}")
    }

    fun testY() {
        val r1 = Rational(
            binom(4, 3) * binom(4, 0) * binom(4, 0) * binom(4, 0) * binom(4, 0)
                    * binom(4, 0) * binom(4, 0) * binom(4, 0) * binom(4, 0) * binom(4, 0)
                    * binom(4, 0) * binom(4, 0) * binom(4, 0)
                    * factorial(13) / factorial(12),
            binom(52, 3)
        )

        println("testY: r1 = $r1 = ${r1.toDouble()}")

        val r2 = Rational(
            binom(4, 2) * binom(4, 1) * binom(4, 0) * binom(4, 0) * binom(4, 0)
                    * binom(4, 0) * binom(4, 0) * binom(4, 0) * binom(4, 0) * binom(4, 0)
                    * binom(4, 0) * binom(4, 0) * binom(4, 0)
                    * factorial(13) / factorial(11),
            binom(52, 3)
        )

        println("testY: r2 = $r2 = ${r2.toDouble()}")

        val r3 = Rational(
            binom(4, 1) * binom(4, 1) * binom(4, 1) * binom(4, 0) * binom(4, 0)
                    * binom(4, 0) * binom(4, 0) * binom(4, 0) * binom(4, 0) * binom(4, 0)
                    * binom(4, 0) * binom(4, 0) * binom(4, 0)
                    * factorial(13) / (factorial(10) * factorial(3)),
            binom(52, 3)
        )

        println("testY: r3 = $r3 = ${r3.toDouble()}")
    }

    fun testXY() {
        val r1_3 = Rational(
            binom(13, 3) * binom(13, 0) * binom(13, 0) * binom(13, 0)
                    * factorial(4) / factorial(3),
            binom(52, 3)
        )

        println("testXY: r1_3 = $r1_3 = ${r1_3.toDouble()}")

        val r2_2 = Rational(
            binom(13, 2) * binom(13, 1) * binom(13, 0) * binom(13, 0) * binom(13 - 2, 2)
                    * factorial(4) / factorial(2),
            binom(52, 3)
        )

        println("testXY: r2_2 = $r2_2 = ${r2_2.toDouble()}")
    }

    fun main() {
        testX()
        testY()
        testXY()
        test13()
        test4()
        //------------------------------------------------------------
        val colors = setOf("♦", "♥", "♠", "♣")
        val values = setOf("B", "D", "K", "A") + Array(9) { (it + 2).toString() }
        //val values: List<String> = List(13) { "0" }

        val cards = colors * values

        val cardCombinations = cards.combinationsTriple().sortedBy { tripleSet ->
            tripleSet.sortedBy {
                it.toString()
            }.toString()
        }

        check(cardCombinations.size.toDouble() == binom(colors.size * values.size.toLong(), 3L).toDouble())

        val counts = cardCombinations.map { triple ->
            Pair(
                triple.groupBy {
                    it.first
                }.size,
                triple.groupBy {
                    it.second
                }.size
            )
        }

        val countsByColor = counts.groupBy {
            it.first
        }.toSortedMap()

        val countsByValue = counts.groupBy {
            it.second
        }.toSortedMap()

        val countsByColorAndValue = counts.groupBy {
            it
        }

        countsByColor.forEach { (groupsCount, ls) ->
            val r = Rational(ls.size, counts.size)
            println("[COLOR][GC: $groupsCount] = $r = ${r.toDouble()}")
        }

        countsByValue.forEach { (groupsCount, ls) ->
            val r = Rational(ls.size, counts.size)
            println("[VALUE][GC: $groupsCount] = $r = ${r.toDouble()}")
        }

        countsByColorAndValue.forEach { (groupsCount, ls) ->
            val r = Rational(ls.size, counts.size)
            println("[C_AND_V][GC: $groupsCount] = $r = ${r.toDouble()}")
        }
    }
}

fun main() {
    Dwt41().main()
}