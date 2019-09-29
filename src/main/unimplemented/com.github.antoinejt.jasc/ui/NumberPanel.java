package com.github.antoinejt.jasc.unimplemented.ui;

import javax.swing.*;
import java.awt.*;

class NumberPanel extends JPanel {
    private CalculatorPanel parent;
    private NumberList numberList;

    NumberPanel(CalculatorPanel parent){
        this.parent = parent;

        numberList = new NumberList(parent.getParentFrame());
        this.setLayout(new GridLayout(4,3));
        addButtons();
    }

    private JButton getDotButton(){
        JButton button = new CalculatorButton("");
        button.addActionListener(unused -> numberList.makeFloat());
        return button;
    }

    private void addButtons(){
        NumberListener listener = new NumberListener(parent, numberList);
        for (int y=0; y<3; y++){
            for (int x=0; x<3; x++){
                final int number = (x+1)+(y*3);
                JButton button = new CalculatorButton(String.valueOf(number));
                button.addActionListener(listener.getActionListener(number));
                this.add(button);
            }
        }
        this.add(getDotButton());
        JButton button0 = new CalculatorButton("0");
        button0.addActionListener(listener.getActionListener(0));
        this.add(button0);
        this.add(new JPanel());
    }
}
