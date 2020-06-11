package dwt

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class Dwt51 {

    private fun simAsync(id: Int) = GlobalScope.async {
        //println("[SIM#$id] START")
        val n = 1_000_000_000
        val p = 0.000000003

        var counter = 0

        for (i in 1..n) {
            if (Random.nextDouble() <= p) counter++
        }

        //println("[SIM#$id] END")
        return@async counter
    }

    fun main() = runBlocking {
        var i = 1
        val statsDeferred = mutableListOf(simAsync(i))
        var avr: Double
        var statsSuccCount = 0

        while ((statsSuccCount.toDouble() / i) < 0.95) {
            avr = statsDeferred.sumBy { it.await() } / i.toDouble()
            statsSuccCount = statsDeferred.subList(0, i).count { it.await() >= 5 }

            println("[$i][AVR]: $avr")
            println("[$i][PSC]: ${statsSuccCount.toDouble() / i}")

            repeat(Runtime.getRuntime().availableProcessors()) {
                statsDeferred.add(simAsync(++i))
            }
        }
    }
}

fun main() {
    Dwt51().main()
}