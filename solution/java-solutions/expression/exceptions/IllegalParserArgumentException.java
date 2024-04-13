package expression.exceptions;

public class IllegalParserArgumentException extends ExpressionException {
    public IllegalParserArgumentException(String message) {
        super(message);
    }
    public IllegalParserArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
