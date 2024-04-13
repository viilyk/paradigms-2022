package expression.exceptions;

import expression.UnitedExpressions;

public class Const implements UnitedExpressions {
    private final int a;

    public Const(int a) {
        this.a = a;
    }

    @Override
    public int evaluate(int x) {
        return a;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return a;
    }

    @Override
    public String toString() {
        return String.valueOf(a);
    }

    @Override
    public boolean equals(Object ex) {
        if (ex instanceof final Const operation) {
            return a == operation.a;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return a;
    }

}
