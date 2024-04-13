package expression.exceptions;

import expression.UnitedExpressions;

public class CheckedNegate extends UnaryOperation {

    public CheckedNegate(UnitedExpressions a) {
        super(a, "-");
    }

    @Override
    int evaluateOperation(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("overflow: " + this);
        }
        return -x;
    }
}
