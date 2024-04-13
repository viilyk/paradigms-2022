package expression;

public class Negate implements UnitedExpressions{
    private final UnitedExpressions a;

    public Negate(UnitedExpressions a) {
        this.a = a;
    }

    @Override
    public int evaluate(int x) {
        return a.evaluate(x)*(-1);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return a.evaluate(x, y, z)*(-1);
    }

    @Override
    public String toString() {
        return "-(" + a.toString() + ")";
    }

    @Override
    public boolean equals(Object ex) {
        if (ex instanceof final Negate operation) {
            return a.equals(operation.a);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return a.hashCode() * 17 + "-".hashCode();
    }
}
