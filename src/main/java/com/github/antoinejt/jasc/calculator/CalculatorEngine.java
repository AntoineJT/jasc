package com.github.antoinejt.jasc.calculator;

import com.github.antoinejt.jasc.util.ReflectUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public final class CalculatorEngine {
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
    private final Stack<Float> stack = new Stack<>();

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

    public void applyFunction(FunctionType functionType) {
        try {
            float result = (float) getFunctionResult(functionType);

            stack.push(result);
        } catch (OperandException unused) {
            System.err.println("No operand left to apply this function to!");
        }
    }

    private double getFunctionResult(FunctionType functionType) throws OperandException {
        int stackSize = stack.getSize();

        if (stackSize == 0) {
            throw new OperandException("Stack is empty!");
        }

        float number = stack.pop();

        return functionType.apply(number);
    }

    public void removeLastNumber() {
        stack.pop();
    }

    public void applyOperation(OperationType operation) throws OperandException, CalculatorException {
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

        float result = getOperationResult(operation, operands);

        stack.push(result);
    }

    private float[] getOperands() {
        float[] operands = new float[2];

        for (int i = 0; i < 2; i++) {
            operands[i] = stack.pop();
        }
        return operands;
    }

    private void reinjectOperandsIntoTheStack(float[] operands) {
        assert operands.length == 2;
        for (int i = 0; i < 2; i++) {
            stack.push(operands[1 - i]);
        }
    }

    private float getOperationResult(OperationType operation, float[] operands) throws CalculatorException {
        assert operands.length >= 2;
        float a = operands[0];
        float b = operands[1];

        if (!operationFunctions.containsKey(operation)) {
            throw new CalculatorException("The provided operation is not handled!");
        }
        return operationFunctions.get(operation).apply(a, b);
    }
}
