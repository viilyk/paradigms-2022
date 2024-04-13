package expression.exceptions;

import expression.TripleExpression;
import expression.UnitedExpressions;

public class TZeroes extends UnaryOperation {
    public TZeroes(UnitedExpressions a) {
        super(a, "t0");
    }

    @Override
    int evaluateOperation(int x) {
        int k = 2;
        int n = 0;
        if (x == 0) {
            return 32;
        }
        if (x == -2147483648) {
            return 31;
        }
        if (x < 0) {
            x = x + 2147483647 + 1;
        }
        while (x % k == 0) {
            k *= 2;
            n++;
        }

        return n;
    }
}
