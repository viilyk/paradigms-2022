package expression;

public class Divide extends AbstractOperations {
    public Divide(UnitedExpressions ex1, UnitedExpressions ex2) {
        super(ex1, ex2, "/");
    }

    @Override
    protected int evaluateOperation(int ex1, int ex2) {
        return ex1 / ex2;
    }

}

