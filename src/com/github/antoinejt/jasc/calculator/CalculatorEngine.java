package com.github.antoinejt.jasc.calculator;

import com.github.antoinejt.jasc.util.ReflectUtil;

import java.util.List;

public final class CalculatorEngine {
    private Stack stack = new Stack<Float>();

    public void addNumber(float number){
        Float f = new Float(number);
        stack.push(f);
    }

    public List<Float> getNumbers(){
        List<Float> list = null;
        try {
            list = (List) ReflectUtil.getPrivateField(stack, "stack");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return list;
    }

    private float[] getOperands(){
        float[] ret = new float[2];
        for (int i = 0; i < 2; i++){
            ret[i] = (Float) stack.pop();
        }
        return ret;
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
