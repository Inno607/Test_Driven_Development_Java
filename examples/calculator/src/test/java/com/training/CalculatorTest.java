package com.training;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Math operations in Calculator class")
class CalculatorTest {

    Calculator calculator;

    @BeforeAll
    static void beforeAll() {
        /**
         * used for setting up thing before running the test
         * e.g database setup
         * wed, thur, fri (30min)
         */
        System.out.println(" Executing @BeforeAll method to setup resources.");
    }

    @AfterAll
    static void afterAll() {
        /**
         *  used to clean up resources after all unit test
         *  e.g closing the database after executing the all tests run
         */
        System.out.println("Executing @AfterAll method to cleanup resources");
    }

    @BeforeEach
    void beforeEachTestMethod () {
        calculator = new Calculator();
        System.out.println("Executing @BeforeEach method");
    }

    @BeforeEach
    void setUp() {
        /**
         * executed before each unit test
         */
    }

    @AfterEach
    void afterEachTestMethodTearDown() {
        /**
         * used for cleaning up resources after running the test
         * e.g closing the database
         */
        System.out.println("Executing @AfterEach method");
    }



    /**
     * Naming convention for test methods
     *
     *  :: test<System Under Test>_<Condition or state Change>_<Expected Result>
     */


    @MethodSource()
    @ParameterizedTest
    @DisplayName("Integer Division")
    void testIntergerDivision_WhenFourIsDividedByTwo_ShouldReturnTwo(
            int dividend, int divisor, int expectedResult ) {

        // Arrange  // Given
//        int dividend = 4;
//        int divisor = 2;
//        int expectedResult = 2;

        // Act      // When
        int actualResult = calculator.intDivision(dividend, divisor);

        // Assert   // Then
        assertEquals(expectedResult, actualResult, () -> "4/2 did not produce 2");

    }

    private static Stream<Arguments> testIntergerDivision_WhenFourIsDividedByTwo_ShouldReturnTwo(){
        return Stream.of(
                Arguments.of(4, 2, 2),
                Arguments.of(16, 4, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("integerSubtractionInputParameters")
    @DisplayName("IntegerSubtration [minuend, substrahend, expectedResult]")
    void testIntegerSubtraction_WhenTwoIsSubtractedFromThirtyThree_ShouldReturnThirtyOne(
            int minuend, int substrahend, int expectedResult ) {

        int actualResult = calculator.integerSubtraction(minuend, substrahend);
        assertEquals(actualResult, expectedResult, () -> minuend + "minus (-) " + substrahend + " did not produce " + expectedResult);
    }

    private static Stream<Arguments> integerSubtractionInputParameters() {
        return Stream.of(
                Arguments.of(33, 1, 32),
                Arguments.of(54, 1, 53),
                Arguments.of(24, 1, 23)
        );
    }

    @Test
    //@Disabled("")
    @DisplayName("Division by zero :: should fail")
    void testIntergerDivision_WhenDividendIsDividedByZero_ShouldThrowArithmeticException() {
        System.out.println(" Running division by zero");
        // Arrange
        int dividend = 4;
        int divisor = 0;
        String expectedExceptionMessage = "/ by zero";


        // Act && Assert
        ArithmeticException actualEception = assertThrows( ArithmeticException.class, () -> {
            //Act
            calculator.intDivision(dividend, divisor);
        }, "Division by zero should have thrown an Arithmetic Exception" );

        assertEquals(expectedExceptionMessage, actualEception.getMessage(), "Unexpected exception message");
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "16, 3, 19"
    })
    @DisplayName("Paramaterized IntegerAddition  using CSVSource")
    void integerAddition(int num1, int num2, int expectedResult) {
        System.out.println(" Running paramaterized IntegerAddition  using CSVSource");

        int actualResult = calculator.integerAddition(num1, num2);
        assertEquals(expectedResult, actualResult,
                ()->num1 + " + " + num2 + " did not produce " + expectedResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/integerAddition.csv")
    @DisplayName("integerAddition_WhenValuesArePassedFromCsvFile")
    void integerAddition_WhenValuesArePassedFromCsvFile(
            int num1, int num2, int expectedResult ) {

        int actualResult = calculator.integerAddition(num1, num2);
        assertEquals(expectedResult, actualResult,
                ()->num1 + " + " + num2 + " did not produce " + expectedResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"John", "Kate", "Alice"})
    @DisplayName(" Running Value Sorce Test Method")
    void valueSourceDemonstration( String firstname)  {
        System.out.println(firstname);
        assertNotNull(firstname);
    }
}