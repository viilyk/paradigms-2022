package expression.exceptions;

import expression.UnitedExpressions;

public abstract class UnaryOperation implements UnitedExpressions {
    private final UnitedExpressions a;
    protected String s;

    public UnaryOperation(UnitedExpressions a, String s) {
        this.a = a;
        this.s = s;
    }

    @Override
    public int evaluate(int x) {
            return evaluateOperation(a.evaluate(x));
    }

    abstract int evaluateOperation(int x);

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluateOperation(a.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return s + "(" + a.toString() + ")";
    }

    @Override
    public boolean equals(Object ex) {
        if (ex instanceof final UnaryOperation operation) {
            return s.equals(operation.s) && a.equals(operation.a);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return a.hashCode() * 17 + s.hashCode();
    }
}
