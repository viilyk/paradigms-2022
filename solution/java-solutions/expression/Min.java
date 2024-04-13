package expression;

public class Min extends AbstractOperations {
    public Min(UnitedExpressions ex1, UnitedExpressions ex2) {
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
