package dwt

import com.marcinmoskala.math.combinations
import utils.Rational
import kotlin.math.max
import kotlin.math.min

class Dwt33 {

    fun main() {
        val nodes = setOf(1, 2, 3, 4)
        val edges = mutableSetOf<Pair<Int, Int>>()

        nodes.forEach { a ->
            nodes.forEach { b ->
                //if (a != b) {
                edges.add(Pair(min(a, b), max(a, b)))
                //}
            }
        }

        val pss = (0..edges.size).flatMap {
            edges.combinations(it)
        }

        val bro = pss.map { list: Set<Pair<Int, Int>> ->
            var buckets = nodes.map { mutableSetOf(it) }
            list.forEach { (a, b) ->
                val theBucket = buckets.filter {
                    it.contains(a) || it.contains(b)
                }.fold(mutableSetOf<Int>(), { acc, mutableSet ->
                    acc.addAll(mutableSet)
                    acc
                })
                buckets = buckets.filter { !it.contains(a) && !it.contains(b) }

                theBucket.add(a)
                theBucket.add(b)

                buckets = buckets.toMutableList().also { it.add(theBucket) }
            }
            Pair(buckets.size, list)
        }.groupBy { it.first }

        val e1 = Rational(bro.entries.fold(0, { acc, entry ->
            acc + entry.key * entry.key * entry.value.size
        }).toLong(), bro.entries.sumBy { it.value.size }.toLong())

        val e2 = Rational(bro.entries.fold(0, { acc, entry ->
            acc + entry.key * entry.value.size
        }).toLong(), bro.entries.sumBy { it.value.size }.toLong())

        println("E[X^2] = $e1 = ${e1.toDouble()}")
        println("E[X] = $e2 = ${e2.toDouble()}")
        println("E[X]^2 = ${e2 * e2} = ${(e2 * e2).toDouble()}")
    }
}

fun main() {
    Dwt33().main()
}