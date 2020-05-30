package dwt

import com.marcinmoskala.math.permutations
import utils.Rational

fun main() {
    val digits = listOf(1, 3, 4, 4, 7, 8)
    val perms = digits.permutations()

    /*val case4sInSame = perms
        .filter { ls ->
            val indf = ls.indexOfFirst { it == 4 }
            val indl = ls.indexOfLast { it == 4 }

            (indf < 3 && indl < 3) || (indf >= 3 && indl >= 3)
        }*/

    val case4And8InFirst = perms
        .filter { ls ->
            val ind4f = ls.indexOfFirst { it == 4 }
            val ind8f = ls.indexOfLast { it == 8 }
            ind4f < 3 && ind8f < 3
        }

    val caseUpperSumLtLowerSumConditional = case4And8InFirst
        .filter {
            it.subList(0, 3).sum() < it.subList(3, 6).sum()
        }

    val caseUpperSumLtLowerSum = perms
        .filter {
            it.subList(0, 3).sum() < it.subList(3, 6).sum()
        }

    println("E - upper sum < lower sum")
    println("T - upper line contains 4 and 8")

    val re = Rational(caseUpperSumLtLowerSum.size.toLong(), perms.size.toLong())
    val rt = Rational(
        caseUpperSumLtLowerSumConditional.size.toLong(),
        case4And8InFirst.size.toLong()
    )

    println("Pr[E] = $re = ${re.simplify()} = ${re.toDouble()}")
    println("Pr[E|T] = $rt = ${rt.simplify()} = ${rt.toDouble()}")
}
