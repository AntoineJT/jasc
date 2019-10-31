package com.github.antoinejt.jasc.calculator;

import java.util.List;
import java.util.Stack;

/**
 * MIT License
 *
 * Copyright (c) 2019 Antoine James Tournepiche
 *
 * This source file come from Just another Stack Calculator
 * Repository : https://github.com/AntoineJT/jasc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class CalculatorEngine {
    private Stack<Float> stack = new Stack<>(); // TODO MrMicky says that it's better to use Deque instead of Stack

    public void addNumber(float number) {
        stack.push(number);
    }

    public void clear() {
        stack = new Stack<>();
    }

    public List getNumbers() {
        return stack;
    }

    public void applyFunction(FunctionType functionType) {
        try {
            float result = (float) getFunctionResult(functionType);
            stack.push(result);
        } catch (IllegalStateException unused) {
            System.err.println("No operand left to apply this function to!");
        }
    }

    private double getFunctionResult(FunctionType functionType) throws IllegalStateException {
        if (stack.size() == 0) {
            throw new IllegalStateException("Stack is empty!");
        }

        float number = stack.pop();
        return functionType.apply(number);
    }

    public void removeLastNumber() {
        stack.pop();
    }

    public void applyOperation(OperationType operation) throws IllegalStateException {
        if (stack.size() < 2) {
            throw new IllegalStateException("Can't operate without at least 2 operands!");
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

    private float getOperationResult(OperationType operation, float[] operands) {
        assert operands.length >= 2;

        float a = operands[0];
        float b = operands[1];

        return operation.apply(a, b);
    }
}
