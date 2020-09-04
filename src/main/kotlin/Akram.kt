import com.marcinmoskala.math.permutations

class Akram {

    fun main() {
        val rxs = listOf(
            Regex(".1....0."),
            Regex(".0...1.."),
            Regex("..0....0"),
            Regex("......0."),
            Regex("0.0.0.0."),
            Regex("00......"),
            Regex("........"),
            Regex("....00..")
        )

        val xs = listOf('0', '0', '0', '0', '1', '1', '1', '1')
        val ps = xs.permutations().map { it.joinToString("") }
        val fss = ps.filter { fs ->
            !fs.contains("111") && !fs.contains("000")
        }

        val gs = rxs.map { rx ->
            fss.filter { it.matches(rx) }
        }

        gs.forEachIndexed { index, list ->
            println("[${('a'.toInt() + index).toChar()}]: $list")
        }
    }
}

fun main() {
    Akram().main()
}