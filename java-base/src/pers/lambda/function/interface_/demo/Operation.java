package pers.lambda.function.interface_.demo;

import java.util.function.DoubleBinaryOperator;

/**
 * 预先定好各种处理方法
 * @author cck
 * @date 2020/11/7 15:12
 */
public enum Operation {

    PLUS("+", (x, y) -> x + y),
    MINUS("-", (x, y) -> x - y),
    TIMES("*", (x, y) -> x * y),
    DIVIDE("/", (x, y) -> x / y);

    private final String symbol;
    private final DoubleBinaryOperator op;

    Operation(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }

    public double apply(double x, double y) {
        return op.applyAsDouble(x, y);
    }

    public static void main(String[] args) {
        System.out.println(Operation.PLUS.apply(1.0, 2.0));
        System.out.println(Operation.MINUS.apply(1.0, 2.0));
        System.out.println(Operation.TIMES.apply(1.0, 2.0));
        System.out.println(Operation.DIVIDE.apply(1.0, 2.0));
    }

}
