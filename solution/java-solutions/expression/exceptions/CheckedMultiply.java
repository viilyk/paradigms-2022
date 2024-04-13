package expression.exceptions;

import expression.UnitedExpressions;

public class CheckedMultiply extends AbstractOperations {
    public CheckedMultiply(UnitedExpressions ex1, UnitedExpressions ex2) {
        super(ex1, ex2, "*");
    }

    @Override
    protected int evaluateOperation(int ex1, int ex2) {
        if ((ex1 == -1 && ex2 == Integer.MIN_VALUE || ex2 == -1 && ex1 == Integer.MIN_VALUE) ||
                ex1 != 0 && ex2 != 0 && ex1 != ex1 * ex2 / ex2 && ex2 != ex1 * ex2 / ex1) {
            throw new OverflowException("overflow: " + this);
        }
        return ex1 * ex2;
    }

}

