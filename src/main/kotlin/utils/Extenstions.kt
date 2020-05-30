package utils

operator fun <E, T> Set<E>.times(other: Set<T>): Set<Pair<E, T>> {
    return this.flatMap { first ->
        other.map { second ->
            Pair(first, second)
        }
    }.toSet()
}

fun <E> Collection<E>.combinationsTriple(): Set<Set<E>> {
    return this.flatMap { first ->
        this.flatMap { second ->
            this.map { third ->
                setOf(first, second, third)
            }
        }
    }.filter { it.size == 3 }.toSet()
}