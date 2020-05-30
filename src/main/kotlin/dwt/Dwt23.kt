package dwt

import utils.Rational

private val Int.isPrime: Boolean
    get() {
        if (this == 1) return false
        for (i in 2..this / 2) {
            if (this % i == 0) return false
        }
        return true
    }

fun main() {
    // generate boxes
    val xs = (1..45).toList()
    val boxes = mutableListOf(
        xs.filter { it % 3 == 0 },
        xs.filter { it % 3 != 0 && it % 2 == 0 },
        xs.filter { it % 3 != 0 && it % 2 != 0 && it.isPrime }
    )
    boxes.add(xs.minus(boxes.flatten()))

    // simulate random selection
    val simLt10Prob = play(boxes, 10_000_000L) { it < 10 }.simplify()
    val simPrimeProb = play(boxes, 10_000_000L) { it.isPrime }.simplify()
    val simLt10AndPrimeProb = play(boxes, 10_000_000L) { it < 10 && it.isPrime }.simplify()
    val simLt10CondPrimeProb = simLt10AndPrimeProb / simLt10Prob
    println("----------------------------------------------------------------------------------------------------")
    println("(simulated)\t\tPr[E] = $simLt10Prob = ${simLt10Prob.toDouble()}")
    println("(simulated)\t\tPr[F] = $simPrimeProb = ${simPrimeProb.toDouble()}")
    println("(simulated)\t\tPr[F and E] = $simLt10AndPrimeProb = ${simLt10AndPrimeProb.toDouble()}")
    println("(simulated)\t\tPr[F | E] = $simLt10CondPrimeProb = ${simLt10CondPrimeProb.toDouble()}")
    println("----------------------------------------------------------------------------------------------------")

    // build probabilities
    val partOfProbabilityPerBox = Rational(1, boxes.size.toLong())
    val counts = boxes.flatMap { bxs ->
        val partOfProbabilityPerItemPerBox = partOfProbabilityPerBox / bxs.count()
        bxs.map { item ->
            Pair(item, partOfProbabilityPerItemPerBox)
        }
    }

    // sanity check
    counts.fold(
        Rational(0, 0),
        { acc, (_, pr) ->
            acc + pr
        }).let { sumTotal ->

        check(sumTotal.isOne())
    }

    val calcLt10Prob = calc(counts) { it < 10 }.simplify()
    val calcPrimeProb = calc(counts) { it.isPrime }.simplify()
    val calcLt10AndPrimeProb = calc(counts) { it < 10 && it.isPrime }.simplify()
    val calcLt10CondPrimeProb = calcLt10AndPrimeProb / calcLt10Prob
    println("----------------------------------------------------------------------------------------------------")
    println("(calculated)\tPr[E] = $calcLt10Prob = ${calcLt10Prob.toDouble()}")
    println("(calculated)\tPr[F] = $calcPrimeProb = ${calcPrimeProb.toDouble()}")
    println("(calculated)\tPr[F and E] = $calcLt10AndPrimeProb = ${calcLt10AndPrimeProb.toDouble()}")
    println("(calculated)\tPr[F | E] = $calcLt10CondPrimeProb = ${calcLt10CondPrimeProb.toDouble()}")
    println("----------------------------------------------------------------------------------------------------")
}

fun calc(counts: List<Pair<Int, Rational>>, condition: (Int) -> Boolean): Rational {
    return counts.fold(
        Rational(0, 0),
        { acc, (element, pr) ->
            if (condition(element)) {
                acc + pr
            } else {
                acc
            }
        })
}

fun play(boxes: List<List<Int>>, totalCount: Long, condition: (Int) -> Boolean): Rational {
    var lt10Count = 0L

    for (i in 0 until totalCount) {
        boxes.random().let { box ->
            box.random().let { element ->
                if (condition(element)) {
                    lt10Count++
                }
            }
        }
    }

    return Rational(lt10Count, totalCount)
}
