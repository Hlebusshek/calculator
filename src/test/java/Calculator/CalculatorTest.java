package Calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

public class CalculatorTest {

    @Test
    @DisplayName("Simple addition")
    void testAddition() {
        assertEquals(5.0, Calculator.evaluate("2 + 3"), 0.001);
    }

    @Test
    @DisplayName("Subtraction and parentheses")
    void testSubtractionWithParentheses() {
        assertEquals(1.0, Calculator.evaluate("(5 - 3) + (1 - 2)"), 0.001);
    }

    @Test
    @DisplayName("Multiplication and division precedence")
    void testPrecedence() {
        assertEquals(14.0, Calculator.evaluate("2 + 3 * 4"), 0.001);
        assertEquals(2.0, Calculator.evaluate("8 / 4 / 2"), 0.001);
    }

    @Test
    @DisplayName("Exponentiation (right-associative)")
    void testExponentiation() {
        assertEquals(512.0, Calculator.evaluate("2 ^ 3 ^ 2"), 0.001);
    }

    @Test
    @DisplayName("Complex expression")
    void testComplex() {
        assertEquals(28.0, Calculator.evaluate("3 + 5 * (2 + 4) - 2"), 0.001);
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