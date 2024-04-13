package expression.exceptions;

import expression.UnitedExpressions;

public class CheckedMax extends AbstractOperations {
    public CheckedMax(UnitedExpressions ex1, UnitedExpressions ex2) {
        super(ex1, ex2, "max");
    }

    @Override
    protected int evaluateOperation(int ex1, int ex2) {
        if (ex1 > ex2) {
            return ex1;
        } else {
            return ex2;
        }
    }

}

