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

    private double getFunctionResult(FunctionType functionType) throws OperandException {
        if (stack.getSize() > 0){
            float number = stack.pop();
            switch(functionType){ // TODO Implement that on ConsoleUI (why?)
                case SQRT: return Math.sqrt(number);
                case LOG10: return Math.log10(number);
                case LN: return Math.log(number);
                // TODO Put that in a dedicated function
                case LOGB: return (int)(Math.log(number)/Math.log(2)+1e-10); // https://stackoverflow.com/questions/3305059/how-do-you-calculate-log-base-2-in-java-for-integers
                case COS: return Math.cos(number);
                case SIN: return Math.sin(number);
                case TAN: return Math.tan(number);
                case ARCCOS: return Math.acos(number);
                case ARCSIN: return Math.asin(number);
                case ARCTAN: return Math.atan(number);
                case EXP: return Math.exp(number);
            }
        } else {
            throw new OperandException("Stack is empty!");
        }
        return Double.NaN;
    }

    public void applyFunction(FunctionType functionType) throws CalculatorException {
        try {
            double result = getFunctionResult(functionType);
            if (!Double.isNaN(result)){
                stack.push((float) result);
            } else {
                throw new CalculatorException("Some weird error occurred when applying function. Please contact the application maintainer!");
            }
        } catch(OperandException unused) {
            System.err.println("No operand left to apply this function to!");
        }
    }

    public void operate(OperationType operation) throws OperandException, CalculatorException {
        if (stack.getSize() > 1) {
            float[] operands = getOperands();
            float result;
            if (operation == OperationType.DIVISION && operands[0] == 0.0f){
                System.err.println("Division by zero!");
                for(int i = 0; i < 2; i++){
                    stack.push(operands[1-i]); // Reinject operands into the stack!
                }
                return;
            }
            switch(operation){
                case ADDITION: result = operands[1] + operands[0]; break;
                case SUBSTRACTION: result = operands[1] - operands[0]; break;
                case MULTIPLICATION: result = operands[1] * operands[0]; break;
                case DIVISION: result = operands[1] / operands[0]; break;
                case MODULO: result = operands[1] % operands[0]; break;
                case POWER: result = (float) Math.pow(operands[1], operands[0]); break;
                default: throw new CalculatorException("The provided operation is not handled!");
            }
            // I can't imagine how this can happened
            // if (Float.isNaN(result)) return;
            stack.push(result);
        } else {
            throw new OperandException("Can't operate without at least 2 operands!");
        }
    }
}
