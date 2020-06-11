package dwt

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
class Dwt53 {

    fun main0() {
        var sum = 0.0
        measureTime {
            for (y in 1..1_000_000_000_000) {
                sum += (y - 1.0) / (y * (y + 1.0))
                //println("\\[\\sum_{y \\geq 1}^{$y} = ${sum.toDouble()}\\]\n")
            }
        }.let {
            println("finished after: $it")
        }
        println("\\[\\sum_{y \\geq 1}^{y} = ${sum}\\]\n")
    }

    private fun evAtI(i: Int) = (i - 1.0) / (i * (i + 1.0))

    private fun harmonicAtI(i: Int) = 1.0 / i

    fun main1() {
        for (y in 1..1_000) {
            val evElement = evAtI(y)
            val harmonicElement = harmonicAtI(y)
            println("ev = $evElement; harmonic = $harmonicElement; diff = ${evElement - harmonicElement}")
        }
    }
}

@ExperimentalTime
fun main() {
    Dwt53().main1()
}