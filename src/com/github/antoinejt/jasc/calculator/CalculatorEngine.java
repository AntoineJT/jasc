package com.github.antoinejt.jasc.calculator;

import com.github.antoinejt.jasc.util.ReflectUtil;

import java.io.PrintStream;
import java.util.List;

public final class CalculatorEngine {
    private static final PrintStream syserr = System.err;

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

    public void applyFunction(FunctionType functionType) throws CalculatorException {
        try {
            double result = getFunctionResult(functionType);

            stack.push((float) result);
        } catch(OperandException unused) {
            syserr.println("No operand left to apply this function to!");
        }
    }

    public void operate(OperationType operation) throws OperandException, CalculatorException {
        int stackSize = stack.getSize();

        if (stackSize < 2){
            throw new OperandException("Can't operate without at least 2 operands!");
        }

        float[] operands = getOperands();
        float result;

        if (operation == OperationType.DIVISION && operands[0] == 0.0f){
            syserr.println("Division by zero!");
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
        stack.push(result);
    }
}
