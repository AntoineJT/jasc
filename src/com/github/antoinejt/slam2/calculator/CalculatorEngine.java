package com.github.antoinejt.slam2.calculator;

public class CalculatorEngine {
    // Singleton, but not restrictive
    private static CalculatorEngine instance = null;
    public static CalculatorEngine getInstance(){
        return instance != null ? instance : new CalculatorEngine();
    }
    public CalculatorEngine(){
        instance = this;
    }

    private Stack stack = new Stack<Float>();

    public void addNumber(float number){
        Float f = new Float(number);
        stack.push(f);
    }

    public enum Operation {
        ADDITION,
        SUBSTRACTION,
        MULTIPLICATION,
        DIVISION
    }
    static class OperandException extends Exception {
        public OperandException(String msg){
            super(msg);
        }
    }

    private float[] getOperands(){
        float ret[] = new float[2];
        for (int i = 0; i < 2; i++){
            ret[i] = ((Float)stack.pop()).floatValue();
        }
        return ret;
    }

    private void op_add(){
        float operand[] = getOperands();
        float calc = operand[0] + operand[1];
        stack.push(calc);
    }

    private void op_substract(){
        float operand[] = getOperands();
        float calc = operand[0] - operand[1];
        stack.push(calc);
    }

    private void op_multiplicate(){
        float operand[] = getOperands();
        float calc = operand[0] * operand[1];
        stack.push(calc);
    }

    private void op_divide() throws ArithmeticException {
        float operand[] = getOperands();
        if (operand[1] == 0.0f){
            throw new ArithmeticException("Division by zero!");
        }
        float calc = operand[0] / operand[1];
        stack.push(calc);
    }

    public void operate(Operation op) throws OperandException, ArithmeticException {
        if (stack.getSize() > 1){
            switch(op){
                case ADDITION: op_add(); break;
                case SUBSTRACTION: op_substract(); break;
                case MULTIPLICATION: op_multiplicate(); break;
                case DIVISION: op_divide(); break;
            }
        } else {
            throw new OperandException("Can't operate without 2 operands!");
        }
    }
}
