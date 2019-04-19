package com.github.antoinejt.slam2.calculator.ui;

import javax.swing.*;

public class CalculatorFrame extends JFrame {
    public CalculatorFrame(int width, int height){
        this.setTitle("Calculator");
        this.setSize(width, height);
        this.setContentPane(new CalculatorPanel(this));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
