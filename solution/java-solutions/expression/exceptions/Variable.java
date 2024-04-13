package expression.exceptions;

import expression.UnitedExpressions;

public class Variable implements UnitedExpressions {
    private final String s;

    public Variable(String s) {
        this.s = s;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (s) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
        }
        throw new IllegalArgumentException("Unknown variable. Waited for x, y or z, got " + s);
    }

    @Override
    public String toString() {
        return s;
    }

    @Override
    public boolean equals(Object ex) {
        if (ex instanceof final Variable operation) {
            return s.equals(operation.s);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return s.hashCode();
    }
}
