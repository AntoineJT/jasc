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
        List list = null;
        try {
            list = (List) ReflectUtil.getPrivateField(stack, "stack");
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return list;
    }

    private float[] getOperands(){
        float[] ret = new float[2];
        for (int i = 0; i < 2; i++){
            ret[i] = stack.pop();
        }
        return ret;
    }

    private double getFunctionResult(FunctionType functionType) throws OperandException {
        if (stack.getSize() > 0){
            float calc = stack.pop();
            switch(functionType){ // TODO Implement that on ConsoleUI
                case SQRT: return Math.sqrt(calc);
                case LOG10: return Math.log10(calc);
                case LN: return Math.log(calc);
                case LOGB: return (int)(Math.log(calc)/Math.log(2)+1e-10); // https://stackoverflow.com/questions/3305059/how-do-you-calculate-log-base-2-in-java-for-integers
                case COS: return Math.cos(calc);
                case SIN: return Math.sin(calc);
                case TAN: return Math.tan(calc);
                case ARCCOS: return Math.acos(calc);
                case ARCSIN: return Math.asin(calc);
                case ARCTAN: return Math.atan(calc);
                case EXP: return Math.exp(calc);
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
                stack.push(Double.valueOf(result).floatValue());
            } else {
                throw new CalculatorException("Some weird error occurred when applying function. Please contact the application maintainer!");
            }
        } catch(OperandException unused) {
            System.err.println("No operand left to apply this function to!");
        }
    }

    public void operate(OperationType operation) throws ArithmeticException, OperandException, CalculatorException {
        if (stack.getSize() > 1) {
            float[] operand = getOperands();
            float calc;
            if (operation == OperationType.DIVISION && operand[0] == 0.0f){
                throw new ArithmeticException("Division by zero!");
            }
            switch(operation){
                case ADDITION: calc = operand[1] + operand[0]; break;
                case SUBSTRACTION: calc = operand[1] - operand[0]; break;
                case MULTIPLICATION: calc = operand[1] * operand[0]; break;
                case DIVISION: calc = operand[1] / operand[0]; break;
                case MODULO: calc = operand[1] % operand[0]; break;
                case POWER: calc = Double.valueOf(Math.pow(operand[1], operand[0])).floatValue(); break;
                default: throw new CalculatorException("The provided operation is not handled!");
            }
            // I can't imagine how this can happened
            // if (Float.isNaN(calc)) return;
            stack.push(calc);
        } else {
            throw new OperandException("Can't operate without at least 2 operands!");
        }
    }
}
