package theo

import java.util.*

class PipePda<A, KA>(
    private val startingState: State,
    private val startingKeller: KA,
    private val transitions: List<Transition<A, KA>>
) {

    var currentState: State = startingState
    var keller: LinkedList<KA> = LinkedList(listOf(startingKeller))

    fun execute(word: List<A>) {
        currentState = startingState
        keller.clear()
        keller.add(startingKeller)

        val mutableWord = LinkedList(word)
        while (true) {
            transitions.filter {
                it.fromSate == currentState
                        && it.letter == mutableWord.first()
                        && it.fromKeller == keller.last()
            }.let {
                check(it.size == 1)
                val t = it.first()

                mutableWord.removeFirst()
                keller.removeLast()
                keller = LinkedList(t.toKeller + keller)
            }
        }
    }

    data class Transition<A, KA>(
        val fromSate: State,
        val toState: State,
        val letter: A,
        val fromKeller: KA,
        val toKeller: List<KA>
    )

    data class State(val name: String)
}

fun main() {
    val sigma = "abcd#".toList()
    val gamma = "abcdS#".toList()

    val states = listOf(
        PipePda.State("0"),
        PipePda.State("1"),
        PipePda.State("2"),
        PipePda.State("3")
    )

    val sTs = sigma.subList(0, sigma.size - 1).map {
        PipePda.Transition(states[0], states[0], it, '#', listOf('#', it))
    }

    val eTs = sigma.subList(0, sigma.size - 1).map {
        PipePda.Transition(states[0], states[0], it, 'a', listOf('a'))
    }

    val etTs = listOf(PipePda.Transition(states[0], states[1], 'ε', '#', emptyList()))

    val stTs = sigma.subList(0, sigma.size - 1).map {
        PipePda.Transition(states[1], states[1], it, it, emptyList())
    }

    val ppda = PipePda(
        states.first(),
        '#',
        sTs + eTs + etTs + stTs
    )

    ppda.execute(
        listOf(
            'a',
            'b',
            'c',
            'd'
        )
    )
}