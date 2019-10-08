package com.github.antoinejt.jasc.calculator;

import java.util.function.Function;

public enum FunctionType {
    SQRT(Math::sqrt),
    LOG10(Math::log10),
    LN(Math::log),
    LOGB(number -> (double) computeBinaryLog(number)),
    COS(Math::cos),
    SIN(Math::sin),
    TAN(Math::tan),
    ARCCOS(Math::acos),
    ARCSIN(Math::asin),
    ARCTAN(Math::atan),
    EXP(Math::exp);

    private final Function<Float, Double> function;

    FunctionType(Function<Float, Double> function) {
        this.function = function;
    }

    private static int computeBinaryLog(float number) {
        // https://stackoverflow.com/questions/3305059/how-do-you-calculate-log-base-2-in-java-for-integers
        double logNumber = Math.log(number);
        double log2 = Math.log(2);

        return (int) (logNumber / log2 + 1e-10);
    }

    public double apply(float number) {
        return function.apply(number);
    }
}
