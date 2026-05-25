package finexos.frameworks.context.core;

public class Context {
    private static ThreadLocal<Operator> operator = new ThreadLocal<>();

    public static void resetOperator(Operator operator) {
        Context.operator.set(operator);
    }

    public static Operator operator() {
        return operator.get();
    }
}
