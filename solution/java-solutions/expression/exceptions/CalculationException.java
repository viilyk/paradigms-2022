package expression.exceptions;

public class CalculationException extends ExpressionException {
    public CalculationException(String message) {
        super(message);
    }
    public CalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
