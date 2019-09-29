package com.github.antoinejt.jasc.calculator;

import com.github.antoinejt.jasc.util.ReflectUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class CalculatorEngine {
    private final Stack<Float> stack = new Stack<>();
    private static final Map<FunctionType, Function<Float, Double>> computatingFunctions = Collections.unmodifiableMap(new HashMap<FunctionType, Function<Float, Double>>() {
        {
            put(FunctionType.SQRT, Math::sqrt);
            put(FunctionType.LOG10, Math::log10);
            put(FunctionType.LN, Math::log);
            put(FunctionType.LOGB, number -> (double) computeBinaryLog(number));
            put(FunctionType.COS, Math::cos);
            put(FunctionType.SIN, Math::sin);
            put(FunctionType.TAN, Math::tan);
            put(FunctionType.ARCCOS, Math::acos);
            put(FunctionType.ARCSIN, Math::asin);
            put(FunctionType.ARCTAN, Math::atan);
            put(FunctionType.EXP, Math::exp);
        }

        private int computeBinaryLog(float number) {
            // https://stackoverflow.com/questions/3305059/how-do-you-calculate-log-base-2-in-java-for-integers
            double logNumber = Math.log(number);
            double log2 = Math.log(2);

            return (int) (logNumber / log2 + 1e-10);
        }
    });
    private static final Map<OperationType, BiFunction<Float, Float, Float>> operationFunctions = Collections.unmodifiableMap(new HashMap<OperationType, BiFunction<Float, Float, Float>>() {
        {
            put(OperationType.ADDITION, Float::sum);
            put(OperationType.SUBSTRACTION, (a, b) -> b - a);
            put(OperationType.MULTIPLICATION, (a, b) -> b * a);
            put(OperationType.DIVISION, (a, b) -> b / a);
            put(OperationType.MODULO, (a, b) -> b % a);
            put(OperationType.POWER, this::pow);
        }

        private float pow(float a, float b) {
            return (float) Math.pow(b, a);
        }
    });

    public void addNumber(float number) {
        stack.push(number);
    }

    public void clear() {
        stack.clear();
    }

    public List getNumbers() {
        List stackContent = null;

        try {
            stackContent = (List) ReflectUtil.getPrivateField(stack, "stack");
        } catch (IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
        return stackContent;
    }

    private float[] getOperands() {
        float[] operands = new float[2];

        for (int i = 0; i < 2; i++) {
            operands[i] = stack.pop();
        }
        return operands;
    }

    private double getFunctionResult(FunctionType functionType) throws OperandException, CalculatorException {
        int stackSize = stack.getSize();

        if (stackSize > 0) {
            float number = stack.pop();

            if (!computatingFunctions.containsKey(functionType)){
                throw new CalculatorException("The provided function is not handled!");
            }
            return computatingFunctions.get(functionType).apply(number);
        }
        throw new OperandException("Stack is empty!");
    }

    public void applyFunction(FunctionType functionType) throws CalculatorException {
        try {
            float result = (float) getFunctionResult(functionType);

            stack.push(result);
        } catch (OperandException unused) {
            System.err.println("No operand left to apply this function to!");
        }
    }

    public void removeLastNumber(){
        stack.pop();
    }

    public void operate(OperationType operation) throws OperandException, CalculatorException {
        int stackSize = stack.getSize();

        if (stackSize < 2) {
            throw new OperandException("Can't operate without at least 2 operands!");
        }

        float[] operands = getOperands();

        if (operation == OperationType.DIVISION && operands[0] == 0.0f) {
            System.err.println("Division by zero!");
            reinjectOperandsIntoTheStack(operands);
            return;
        }

        float result = computate(operation, operands);

        stack.push(result);
    }

    private void reinjectOperandsIntoTheStack(float[] operands) {
        // assert operands.length == 2;
        for (int i = 0; i < 2; i++) {
            stack.push(operands[1 - i]);
        }
    }

    private float computate(OperationType operation, float[] operands) throws CalculatorException {
        float a = operands[0];
        float b = operands[1];

        if (!operationFunctions.containsKey(operation)) {
            throw new CalculatorException("The provided operation is not handled!");
        }
        return operationFunctions.get(operation).apply(a, b);
    }
}
