package com.training;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepeatedTest {

    Calculator calculator;

    @BeforeEach
    void beforeEach() {
        calculator = new Calculator();
    }

    @org.junit.jupiter.api.RepeatedTest(3)
    @DisplayName("Integer Division")
     void testIntergerDivision_WhenFourIsDividedByTwo_ShouldReturnTwo() {

        // Arrange  // Given
        int dividend = 4;
        int divisor = 2;
        int expectedResult = 2;

        // Act      // When
        int actualResult = calculator.intDivision(dividend, divisor);

        // Assert   // Then
        assertEquals(expectedResult, actualResult, () -> "4/2 did not produce 2");
    }

    @org.junit.jupiter.api.RepeatedTest(value = 3, name ="{displayName}. Repetition {currentRepetition} of " +
            "{totalRepetitions}")
    @DisplayName("Integer Division")
    void testIntergerDivision_WhenFourIsDividedByTwo_Parametirized(
            RepetitionInfo repetitionInfo, TestInfo testInfo
    ) {

        System.out.println("Running " + testInfo.getTestMethod().get().getName());
        System.out.println("Repetition # " + repetitionInfo.getCurrentRepetition());
        // Arrange  // Given
        int dividend = 4;
        int divisor = 2;
        int expectedResult = 2;

        // Act      // When
        int actualResult = calculator.intDivision(dividend, divisor);

        // Assert   // Then
        assertEquals(expectedResult, actualResult, () -> "4/2 did not produce 2");
    }
}
