package com.github.antoinejt.slam2.calculator.ui;

import com.github.antoinejt.slam2.calculator.CalculatorException;

import java.awt.event.ActionListener;

class NumberListener {
    private CalculatorPanel parent;
    private NumberList numberList;

    NumberListener(CalculatorPanel parent, NumberList numberList){
        this.parent = parent;
        this.numberList = numberList;
    }

    ActionListener getActionListener(int number){
        return unused -> {
            numberList.addNumber(number);
            try {
                parent.getResultPane().setResult(String.valueOf(numberList.getNumber()));
            } catch (CalculatorException ignored) {}
        };
    }
}
