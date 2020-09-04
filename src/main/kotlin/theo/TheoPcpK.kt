package theo

import kotlin.math.abs

class TheoPcpK {

    private fun visit(
        instances: List<Instance>,
        visitedStates: MutableList<Instance>,
        initialState: Instance,
        k: Int,
        trace: List<Instance>
    ): List<List<Instance>?> {
        val currentState = initialState.reduce()

        // check if solved
        if (currentState.xs.isEmpty() && currentState.ys.isEmpty()) {
            return listOf(trace)
        }

        // check if its still k-bound
        if (currentState.distance() > k) {
            return emptyList()
        }

        // check if this state was visited before
        if (visitedStates.contains(currentState)) {
            return emptyList()
        }
        visitedStates.add(currentState)

        // continue visiting other states
        return instances.filter {
            (currentState + it).reduce().initial()
        }.flatMap {
            visit(instances, visitedStates, currentState + it, k, trace + it)
        }
    }

    fun findKBoundSolutions(instances: List<Instance>, k: Int): List<List<Instance>?> {
        val visitedStates = mutableListOf<Instance>()

        return instances.filter {
            it.initial()
        }.flatMap { initialState ->
            visit(instances, visitedStates, initialState, k, listOf(initialState))
        }
    }

    data class Instance(val xs: String, val ys: String) {

        fun reduce() = when {
            xs.startsWith(ys) -> {
                Instance(xs.substring(ys.length), "")
            }
            ys.startsWith(xs) -> {
                Instance("", ys.substring(xs.length))
            }
            else -> this
        }

        fun distance() = abs(xs.length - ys.length)

        operator fun plus(other: Instance) = Instance(xs + other.xs, ys + other.ys)

        fun initial(): Boolean = xs.startsWith(ys) || ys.startsWith(xs)
    }
}

fun main() {
    TheoPcpK().apply {
        println(
            findKBoundSolutions(
                instances = listOf(
                    TheoPcpK.Instance("bb", "b"),
                    TheoPcpK.Instance("ab", "ba"),
                    TheoPcpK.Instance("c", "bc")
                ), k = 5
            )
        )
        println(
            findKBoundSolutions(
                instances = listOf(
                    TheoPcpK.Instance("ab", "a"),
                    TheoPcpK.Instance("b", "a"),
                    TheoPcpK.Instance("a", "b")
                ), k = 5
            )
        )
        println(
            findKBoundSolutions(
                instances = listOf(
                    TheoPcpK.Instance("1", "111"),
                    TheoPcpK.Instance("10111", "10"),
                    TheoPcpK.Instance("10", "0")
                ), k = 5
            )
        )
        println(
            findKBoundSolutions(
                instances = listOf(
                    TheoPcpK.Instance("a", "baa"),
                    TheoPcpK.Instance("ab", "aa"),
                    TheoPcpK.Instance("bba", "bb")
                ), k = 5
            )
        )
    }
}