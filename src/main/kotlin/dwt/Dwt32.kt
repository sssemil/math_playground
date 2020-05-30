package dwt

import com.marcinmoskala.math.permutations

enum class Ball {
    N,
    S
}

fun main() {
    val name = listOf(
        Ball.S,
        Ball.S,
        Ball.N,
        Ball.N,
        Ball.N,
        Ball.N,
        Ball.N
    )
    val namePermutations = name.permutations()

    val bruh = namePermutations.map { list ->
        list.subList(0, list.indexOfLast { char -> char == Ball.S } + 1).joinToString("")
    }.sorted().toSet().groupBy { it.length }

    val bruv = bruh[4]?.map { it.substring(0, it.length - 1) }?.toSet()
}