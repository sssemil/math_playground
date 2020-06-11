package theo

class Theo32 {

    fun main() {
        val alphabet = listOf('a', 'b')

        val state0 = State("q0", isStart = true)
        val state1 = State("q1", isFinal = true)
        val state2 = State("q2", isFinal = true)
        val state3 = State("q3")
        val state4 = State("q4")
        val state5 = State("q5")

        val states = listOf(state0, state1, state2, state3, state4, state5)

        val transitions = listOf(
            Transition(state0, state1, 'a'),
            Transition(state0, state0, 'b'),
            Transition(state1, state4, 'a'),
            Transition(state1, state2, 'b'),
            Transition(state2, state3, 'a'),
            Transition(state2, state1, 'b'),
            Transition(state3, state5, 'a'),
            Transition(state3, state1, 'b'),
            Transition(state4, state5, 'a'),
            Transition(state4, state2, 'b'),
            Transition(state5, state1, 'a'),
            Transition(state5, state5, 'b')
        )

        fun d(fromState: State, withLetter: Char) = transitions.first {
            it.fromState == fromState && it.withLetter == withLetter
        }.toState

        // 1
        val u = mutableSetOf<Set<State>>()

        states.filter { it.isFinal }.forEach { p ->
            states.filter { !it.isFinal }.forEach { q ->
                u.add(setOf(p, q))
            }
        }

        // 2
        var didWork: Boolean
        do {
            didWork = false
            states.forEach { p ->
                states.forEach { q ->
                    if (!u.contains(setOf(p, q))) {
                        alphabet.forEach { a ->
                            if (u.contains(setOf(d(p, a), d(q, a)))) {
                                didWork = false
                                u.add(setOf(p, q))
                            }
                        }
                    }
                }
            }
        } while (didWork)

        println(u)
    }

    data class State(val label: String, val isFinal: Boolean = false, val isStart: Boolean = false)

    data class Transition(val fromState: State, val toState: State, val withLetter: Char)
}
