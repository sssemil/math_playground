package utils

import org.junit.Assert.assertEquals
import org.junit.Test

class MathTest {

    @Test
    fun testFactorial() {
        assertEquals(1, factorial(0))
        assertEquals(1, factorial(1))
        assertEquals(2, factorial(2))
        assertEquals(6, factorial(3))
        assertEquals(24, factorial(4))
    }

    @Test
    fun testFactorialFastWithNaive() {
        (0L..15L).toList().shuffled().forEach {
            assertEquals(factorialNaive(it), factorialDynamic(it))
        }
    }
}