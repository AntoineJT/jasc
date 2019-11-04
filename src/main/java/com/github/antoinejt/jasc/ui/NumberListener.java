package com.github.antoinejt.jasc.ui;

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
            parent.getResultPane().setResult(String.valueOf(numberList.getNumber()));
        };
    }
}
