package expression;

public class Max extends AbstractOperations {
    public Max(UnitedExpressions ex1, UnitedExpressions ex2) {
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

