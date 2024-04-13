package expression.exceptions;

import expression.UnitedExpressions;

public class CheckedMin extends AbstractOperations {
    public CheckedMin(UnitedExpressions ex1, UnitedExpressions ex2) {
        super(ex1, ex2, "min");
    }

    @Override
    protected int evaluateOperation(int ex1, int ex2) {
        if (ex1 < ex2) {
            return ex1;
        } else {
            return ex2;
        }
    }

}
