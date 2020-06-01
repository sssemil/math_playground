package utils

import org.junit.Test
import kotlin.test.assertTrue
import kotlin.test.expect

class RationalTest {

    @Test
    fun testFactorial() {
    }

    @Test
    fun add2RationalsTest() {
        expect(Rational(3, 4)) {
            Rational(1, 2) + Rational(1, 4)
        }
    }

    @Test
    fun subtract2RationalsTest() {
        expect(Rational(3, 4)) {
            Rational(1, 1) - Rational(1, 4)
        }
    }

    @Test
    fun multiply2RationalsTest() {
        expect(Rational(1, 4)) {
            Rational(1, 2) * Rational(1, 2)
        }
    }

    @Test
    fun divide2RationalsTest() {
        expect(Rational(1, 4)) {
            Rational(1, 2) / Rational(2, 1)
        }
    }

    @Test
    fun addToDoubleTest() {
        expect(Rational(1, 1)) { Rational(1, 2) + 0.5 }
    }

    @Test
    fun multByDoubleTest() {
        expect(Rational(2, 1)) { Rational(1, 2) * 4.0 }
    }

    @Test
    fun divByDoubleTest() {
        expect(Rational(1, 4)) { Rational(1, 2) / 2.0 }
    }

    /*@Test
    fun stringTest() {
        expect(Rational(1, 2)) { Rational("0.5") }
        expect(Rational(3, 5)) { Rational("0.6") }

    }*/

    @Test
    fun simplifyTest() {
        expect(Rational(1, 2)) { Rational(2, 4) }
    }

    @Test
    fun doubleConvertTest() {
        expect(Rational(1, 2)) { Rational(0.5) }
        expect(Rational(1, 4)) { Rational(0.25) }
    }

    @Test
    fun greaterThanTest() {
        assertTrue(Rational(1, 2) > Rational(1, 3))
    }
}