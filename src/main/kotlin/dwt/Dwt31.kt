package dwt

import utils.Rational

class Dwt31 {

    fun fitnessFunction0(input: Array<Rational>): Rational {
        return input.fold(Rational(0, 0)) { acc, rational -> acc + rational }
    }

    fun main() {
        var p0 = Rational(1, 4)
        var p1 = Rational(1, 4)
        var p2 = Rational(1, 4)
        var p3 = Rational(1, 4)


    }
}

fun main() {
    Dwt31().main()
}