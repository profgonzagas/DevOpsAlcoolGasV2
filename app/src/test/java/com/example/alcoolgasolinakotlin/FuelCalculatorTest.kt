package com.example.alcoolgasolinakotlin
import org.junit.Test
import org.junit.Assert.*
import kotlin.math.abs

class FuelCalculatorTest {

    @Test
    fun `should return ALCOHOL when ratio is less than 0 dot 7`() {
        val result = MainActivity.calculateBestFuel(0.69)
        assertEquals("ÁLCOOL", result)
    }

    @Test
    fun `should return GASOLINE when ratio is greater than or equal to 0 dot 7`() {
        val result = MainActivity.calculateBestFuel(0.7)
        assertEquals("GASOLINA", result)
    }

    @Test
    fun `should calculate cost per km correctly`() {
        val result1 = MainActivity.calculateCostPerKm(3.0, 10.0)
        assertTrue(abs(result1 - 0.3) < 0.001)

        val result2 = MainActivity.calculateCostPerKm(5.0, 12.5)
        assertTrue(abs(result2 - 0.4) < 0.001)
    }

    @Test
    fun `should calculate ratio correctly`() {
        val result = MainActivity.calculateRatio(3.0, 5.0)
        assertEquals(0.6, result, 0.001)
    }
}