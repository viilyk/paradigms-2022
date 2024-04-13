package expression.exceptions;

import expression.UnitedExpressions;

public class CheckedDivide extends AbstractOperations {
    public CheckedDivide(UnitedExpressions ex1, UnitedExpressions ex2) {
        super(ex1, ex2, "/");
    }

    @Override
    protected int evaluateOperation(int ex1, int ex2) {
        if (ex2 == 0) {
            throw new DBZException("division by zero: " + this);
        }
        if (ex1 == Integer.MIN_VALUE && ex2 == -1) {
            throw new OverflowException("overflow: " + this);
        }
        return ex1 / ex2;
    }

}
