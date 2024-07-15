/*
 * Copyright 2015-2024 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package com.example.project

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.random.Random

class CalculatorTests {

    @Test
    fun `1 + 1 = 2`() {
        val calculator = Calculator()
        assertEquals(4, calculator.add(1, 1), "1 + 1 should equal 2")
    }


    companion object {
        @JvmStatic
        fun provideTestCases(): Stream<Arguments> {
            val random = Random(0) // Use fixed seed for reproducibility
            return Stream.generate {
                val first = random.nextInt(0, 100)
                val second = random.nextInt(0, 100)
                val hoge = random.nextInt(0, 5)
                val sum = if(hoge == 1) { // 20% success rate
                    first + second
                } else {
                    first + second + 1
                }
                Arguments.of(first, second, sum)
            }.limit(100)
        }
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @MethodSource("provideTestCases")
    fun add(first: Int, second: Int, expectedResult: Int) {
        val calculator = Calculator()
        assertEquals(expectedResult, calculator.add(first, second)) {
            "$first + $second should equal $expectedResult"
        }
    }

    @Test
    fun divisionByZeroError() {
        val calculator = Calculator()
        val exception = assertThrows<AssertionError> {
            calculator.div(1, 0)
        }
        assertEquals("Division by Zero", exception.message)
    }
}
