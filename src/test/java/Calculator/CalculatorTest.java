package Calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

class CalculatorTest {

    @Test
    @DisplayName("Simple addition")
    void testAddition() {
        double res = Calculator.evaluate("2 + 3");
        assertEquals(5.0, res, 0.001, "Expected: 5.0, got: " + res);
    }

    @Test
    @DisplayName("Subtraction and parentheses")
    void testSubtractionWithParentheses() {
        double res = Calculator.evaluate("(5 - 3) + (1 - 2)");
        assertEquals(1.0, res, 0.001, "Expected: 1.0, got: " + res);
    }

    @Test
    @DisplayName("Multiplication and division precedence")
    void testMultiplicationPrecedence() {
        double res = Calculator.evaluate("2 + 3 * 4");
        assertEquals(14.0, res, 0.001, "Expected: 14.0, got: " + res);
    }

    @Test
    @DisplayName("Multiplication and division precedence")
    void testPrecedence() {
        double res =  Calculator.evaluate("8 / 4 / 2");
        assertEquals(1.0, res, 0.001, "Expected: 1.0, got: " + res);
    }

    @Test
    @DisplayName("Exponentiation (right-associative)")
    void testExponentiation() {
        double res = Calculator.evaluate("2 ^ 3 ^ 2");
        assertEquals(512.0, res, 0.001, "Expected: 512.0, got: " + res);
    }

    @Test
    @DisplayName("Complex expression")
    void testComplex() {
        double res = Calculator.evaluate("3 + 5 * (2 + 4) - 2");
        assertEquals(31.0, res, 0.001, "Expected: 31.0, got: " + res);
    }

    @Test
    @DisplayName("Division by zero throws exception")
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> Calculator.evaluate("1 / 0"));
    }

    @Test
    @DisplayName("Mismatched parentheses")
    void testMismatchedParentheses() {
        assertThrows(IllegalArgumentException.class, () -> Calculator.evaluate("2 + (3"));
    }

    @Test
    @DisplayName("Empty input")
    void testEmptyExpression() {
        assertThrows(NumberFormatException.class, () -> Calculator.evaluate(""));
    }
}