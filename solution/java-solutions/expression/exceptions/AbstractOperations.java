package expression.exceptions;

import expression.UnitedExpressions;

public abstract class AbstractOperations implements UnitedExpressions {
    protected UnitedExpressions ex1;
    protected UnitedExpressions ex2;
    protected String op;

    protected AbstractOperations(UnitedExpressions ex1, UnitedExpressions ex2, String op) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.op = op;
    }

    @Override
    public int evaluate(int x) {
        return evaluateOperation(ex1.evaluate(x), ex2.evaluate(x));
    }

    abstract int evaluateOperation(int ex1, int ex2);

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluateOperation(ex1.evaluate(x, y, z), ex2.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "(" + ex1.toString() + " " + op + " " + ex2.toString() + ")";
    }

    @Override
    public boolean equals(Object ex) {
        if (ex instanceof final AbstractOperations operation) {
            return op.equals(operation.op) && ex1.equals(operation.ex1) && ex2.equals(operation.ex2);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (ex1.hashCode() * 17 + ex2.hashCode()) * 17 + op.hashCode();
    }
}
