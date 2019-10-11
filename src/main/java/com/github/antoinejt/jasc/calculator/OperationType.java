package com.github.antoinejt.jasc.calculator;

import java.util.function.BiFunction;

public enum OperationType {
    ADDITION(Float::sum),
    SUBSTRACTION((a, b) -> b - a),
    MULTIPLICATION((a, b) -> b * a),
    DIVISION((a, b) -> b / a),
    MODULO((a, b) -> b % a),
    POWER(OperationType::pow);

    private final BiFunction<Float, Float, Float> operation;

    OperationType(BiFunction<Float, Float, Float> operation) {
        this.operation = operation;
    }

    private static float pow(float a, float b) {
        return (float) Math.pow(b, a);
    }

    public float apply(float a, float b) {
        return operation.apply(a, b);
    }
}
