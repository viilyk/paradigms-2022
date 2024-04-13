package expression.exceptions;

import expression.TripleExpression;
import expression.UnitedExpressions;

import java.util.HashMap;
import java.util.Map;

public class ExpressionParser implements TripleParser {
    public ExpressionParser() {
    }

    public TripleExpression parse(final String source) {
        //System.err.println("GOT: " + source);
        ExpressionBaseParser expressionBaseParser = new ExpressionBaseParser(new StringSource(source));
        UnitedExpressions result = expressionBaseParser.parseExpression();
        expressionBaseParser.skipWhitespace();
        if (!expressionBaseParser.eof()) {
            throw expressionBaseParser.error("EOF expected");
        }
        return result;
    }

    private static class ExpressionBaseParser extends BaseParser {
        private final Map<String, Integer> prioritize = new HashMap<>(Map.of("min", 0, "max", 0,
                "+", 1, "-", 1, "*", 2, "/", 2));


        protected ExpressionBaseParser(final CharSource source) {
            super(source);
        }


        public UnitedExpressions parseExpression() {
            skipWhitespace();
            UnitedExpressions result = parseOperand();
            boolean k = skipWhitespace();
            while (!eof() && !test(')')) {
                result = parseOperation(result, k);
                skipWhitespace();
            }
            return result;
        }

        private UnitedExpressions parseOperation(UnitedExpressions firstOperand, boolean k) {
            if (take('m')) {
                if (!k && (firstOperand instanceof Const)) {
                    throw new IllegalParserArgumentException("Expected for whitespace");
                }
                if (take('i') && take('n')) {
                    return new CheckedMin(firstOperand, parseSecondOperand("min"));
                } else if (take('a') && take('x')) {
                    return new CheckedMax(firstOperand, parseSecondOperand("max"));
                }
            }
            if (take('+')) {
                return new CheckedAdd(firstOperand, parseSecondOperand("+"));
            }
            if (take('-')) {
                return new CheckedSubtract(firstOperand, parseSecondOperand("-"));
            }
            if (take('*')) {
                return new CheckedMultiply(firstOperand, parseSecondOperand("*"));
            }
            if (take('/')) {
                return new CheckedDivide(firstOperand, parseSecondOperand("/"));
            }
            throw error("Operation expected, find " + get() + ".");
        }

        private UnitedExpressions parseSecondOperand(String operation) {
            skipWhitespace();
            UnitedExpressions secondOperand = parseOperand();
            boolean k = skipWhitespace();
            while (!eof() && !test(')') && prioritize.containsKey(String.valueOf(get()))
                    && prioritize.get(operation) < prioritize.get(String.valueOf(get()))) {
                secondOperand = parseOperation(secondOperand, k);
                k = skipWhitespace();
            }
            return secondOperand;
        }

        private UnitedExpressions parseOperand() {
            boolean negative = false;
            if (take('-')) {
                if (between('0', '9')) {
                    negative = true;
                } else {
                    skipWhitespace();
                    return new CheckedNegate(parseOperand());
                }
            }

            if (take('l')) {
                if (take('0')) {
                    boolean k = skipWhitespace();
                    if (!k && (test('x') || test('y') || test('z') || between('0', '9'))) {
                        throw new IllegalParserArgumentException("Expected for whitespace");
                    }
                    return new LZeroes(parseOperand());
                }
            }

            if (take('t')) {
                if (take('0')) {
                    boolean k = skipWhitespace();
                    if (!k && (test('x') || test('y') || test('z') || between('0', '9'))) {
                        throw new IllegalParserArgumentException("Expected for whitespace");
                    }
                    return new TZeroes(parseOperand());
                }
            }

            if (take('(')) {
                UnitedExpressions res = parseExpression();
                expect(')');
                return res;
            }
            if (between('0', '9')) {
                return parseConst(negative);
            }
            if (test('x') || test('y') || test('z')) {
                return parseVariable();
            }
            throw error("Expected operand, find " + get() + ".");
        }

        private UnitedExpressions parseConst(boolean negative) {
            StringBuilder sb = new StringBuilder();
            if (negative) {
                sb.append('-');
            }
            while (between('0', '9')) {
                sb.append(take());
            }
            return new Const(Integer.parseInt(sb.toString()));

        }

        private UnitedExpressions parseVariable() {
            return new Variable(Character.toString(take()));
        }

        protected boolean skipWhitespace() {
            boolean k = false;
            while (Character.isWhitespace(get())) {
                take();
                k = true;
            }
            return k;
        }

    }
}
