package Calculator;

import java.util.*;

public class Calculator {
    private static final int MAX_STACK_SIZE = 2;
    private static final int VALID_STACK_SIZE = 1;
    private static final Map<String, Integer> PRECEDENCE = new HashMap<>();
    private static final Set<String> OPERATORS = new HashSet<>();

    static {
        OPERATORS.add("+");
        OPERATORS.add("-");
        OPERATORS.add("*");
        OPERATORS.add("/");
        OPERATORS.add("^");

        PRECEDENCE.put("+", Integer.valueOf(1));
        PRECEDENCE.put("-", Integer.valueOf(1));
        PRECEDENCE.put("*", Integer.valueOf(2));
        PRECEDENCE.put("/", Integer.valueOf(2));
        PRECEDENCE.put("^", Integer.valueOf(3));
    }

    public static double evaluate(String expression) {
        List<String> tokens = tokenize(expression);
        List<String> rpn = toRpn(tokens);
        return evaluateRpn(rpn);
    }

    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder number = new StringBuilder();

        for (char c : expression.toCharArray()) {
            if (Character.isWhitespace(c)) continue;

            if (Character.isDigit(c) || c == '.') {
                number.append(c);
            } else {
                if (!number.isEmpty()) {
                    tokens.add(number.toString());
                    number = new StringBuilder();
                }
                tokens.add(String.valueOf(c));
            }
        }
        if (!number.isEmpty()) {
            tokens.add(number.toString());
        }
        return tokens;
    }

    private static List<String> toRpn(List<String> tokens) {
        Deque<String> output = new ArrayDeque<>();
        Deque<String> operators = new ArrayDeque<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                output.addLast(token);
            } else if ("(".equals(token)) {
                operators.push(token);
            } else if (")".equals(token)) {
                while (!operators.isEmpty() && !"(".equals(operators.peek())) {
                    output.addLast(operators.pop());
                }
                if (operators.isEmpty()) {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                operators.pop();
            } else if (OPERATORS.contains(token)) {
                while (!operators.isEmpty()
                        && !"(".equals(operators.peek())
                        && (PRECEDENCE.get(operators.peek()) > PRECEDENCE.get(token)
                        || (PRECEDENCE.get(operators.peek()).equals(PRECEDENCE.get(token))
                        && !"^".equals(token)))) {
                    output.addLast(operators.pop());
                }
                operators.push(token);
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        while (!operators.isEmpty()) {
            String op = operators.pop();
            if ("(".equals(op) || ")".equals(op)) {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            output.addLast(op);
        }

        return new ArrayList<>(output);
    }

    private static boolean isNumber(String token) {
        try {
            Double.valueOf(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static double evaluateRpn(List<String> rpn) {
        Deque<Double> stack = new ArrayDeque<>();

        for (String token : rpn) {
            if (isNumber(token)) {
                stack.push(Double.valueOf(token));
            } else {
                if (stack.size() < MAX_STACK_SIZE) {
                    throw new IllegalArgumentException("Invalid RPN expression");
                }
                double b = stack.pop();
                double a = stack.pop();
                double result = switch (token) {
                    case "+" -> a + b;
                    case "-" -> a - b;
                    case "*" -> a * b;
                    case "/" -> {
                        if (b == 0) throw new ArithmeticException("Division by zero");
                        yield a / b;
                    }
                    case "^" -> Math.pow(a, b);
                    default -> throw new IllegalArgumentException("Unknown operator: " + token);
                };
                stack.push(Double.valueOf(result));
            }
        }

        if (stack.size() != VALID_STACK_SIZE) {
            throw new NumberFormatException("Invalid RPN expression");
        }
        return stack.pop();
    }
}