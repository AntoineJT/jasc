package com.github.antoinejt.jasc.calculator;

import com.github.antoinejt.jasc.util.ReflectUtil;

import java.util.List;

public final class CalculatorEngine {
    private final Stack<Float> stack = new Stack<>();

    public void addNumber(float number){
        stack.push(number);
    }

    public void clear(){
        stack.clear();
    }

    public List getNumbers(){
        List stackContent = null;

        try {
            stackContent = (List) ReflectUtil.getPrivateField(stack, "stack");
        } catch (IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
        return stackContent;
    }

    private float[] getOperands(){
        float[] operands = new float[2];

        for (int i = 0; i < 2; i++){
            operands[i] = stack.pop();
        }
        return operands;
    }

    private int computeBinaryLog(float number){
        // https://stackoverflow.com/questions/3305059/how-do-you-calculate-log-base-2-in-java-for-integers
        double logNumber = Math.log(number);
        double log2 = Math.log(2);

        return (int) (logNumber / log2 + 1e-10);
    }

    private double getFunctionResult(FunctionType functionType) throws OperandException {
        int stackSize = stack.getSize();

        if (stackSize > 0){
            float number = stack.pop();

            // TODO Refactor that
            // enum with consumer to refactor?
            switch(functionType){ // TODO Implement that on ConsoleUI (why?)
                case SQRT: return Math.sqrt(number);
                case LOG10: return Math.log10(number);
                case LN: return Math.log(number);
                case LOGB: return computeBinaryLog(number);
                case COS: return Math.cos(number);
                case SIN: return Math.sin(number);
                case TAN: return Math.tan(number);
                case ARCCOS: return Math.acos(number);
                case ARCSIN: return Math.asin(number);
                case ARCTAN: return Math.atan(number);
                case EXP: return Math.exp(number);
            }
        }
        throw new OperandException("Stack is empty!");
    }

    public void applyFunction(FunctionType functionType) {
        try {
            float result = (float) getFunctionResult(functionType);

            stack.push(result);
        } catch(OperandException unused) {
            System.err.println("No operand left to apply this function to!");
        }
    }

    public void operate(OperationType operation) throws OperandException, CalculatorException {
        int stackSize = stack.getSize();

        if (stackSize < 2){
            throw new OperandException("Can't operate without at least 2 operands!");
        }

        float[] operands = getOperands();

        if (operation == OperationType.DIVISION && operands[0] == 0.0f){
            System.err.println("Division by zero!");
            reinjectOperandsIntoTheStack(operands);
            return;
        }

        float result = computate(operation, operands);

        stack.push(result);
    }

    private void reinjectOperandsIntoTheStack(float[] operands){
        for(int i = 0; i < 2; i++){
            stack.push(operands[1-i]);
        }
    }

    private float computate(OperationType operation, float[] operands) throws CalculatorException {
        float a = operands[0];
        float b = operands[1];

        switch(operation){
            case ADDITION: return b + a;
            case SUBSTRACTION: return b - a;
            case MULTIPLICATION: return b * a;
            case DIVISION: return b / a;
            case MODULO: return b % a;
            case POWER: return (float) Math.pow(b, a);
            default: throw new CalculatorException("The provided operation is not handled!");
        }
    }
}
