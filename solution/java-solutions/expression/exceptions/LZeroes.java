package expression.exceptions;

import expression.UnitedExpressions;

public class LZeroes extends UnaryOperation {
    public LZeroes(UnitedExpressions a) {
        super(a, "l0");
    }

    @Override
    int evaluateOperation(int x) {
        if (x < 0) {
            return 0;
        }
        int n = 0;
        while (x > 0) {
            x /= 2;
            n++;
        }
        return 32 - n;
    }
}