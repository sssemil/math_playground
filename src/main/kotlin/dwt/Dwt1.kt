package dwt

import utils.Rational

fun derangement(n: Int): List<Rational> {
    val ds = arrayListOf(0L, 1L)
    val fs = arrayListOf(1L, 2L)

    for (i in 3..n) {
        fs.add(fs.last() * i)
        ds.add((i - 1) * (ds[i - 1 - 1] + ds[i - 2 - 1]))
    }

    return ds.zip(fs) { dss, fac ->
        Rational(dss, fac)
    }
}

fun main() {
    derangement(12).forEachIndexed { index, rational ->
        println("\$!${index + 1}/${index + 1}! = $rational = ${rational.apply { simplify() }} = ${rational.toDouble()}\$")
    }
}
