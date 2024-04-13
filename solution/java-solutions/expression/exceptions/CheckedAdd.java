package expression.exceptions;

import expression.UnitedExpressions;

public class CheckedAdd extends AbstractOperations {
    public CheckedAdd(UnitedExpressions ex1, UnitedExpressions ex2) {
        super(ex1, ex2, "+");
    }

    @Override
    protected int evaluateOperation(int ex1, int ex2) {
        if ((ex1 < 0 && ex2 < 0 && (ex1 < ex1+ex2)) || (ex1 > 0 && ex2 >0 && (ex1 > ex1 +ex2))) {
            throw new OverflowException("overflow: " + this);
        }
        return ex1 + ex2;
    }

}
