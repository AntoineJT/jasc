package com.github.antoinejt.jasc.tests.ui;

import com.github.antoinejt.jasc.calculator.CalculatorException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class NumberList { // TODO Maybe use Stack here instead of an ArrayList (bad idea?)
    private JFrame parent;
    private List<Integer> list = new ArrayList<>();
    private int dot_pos = -1; // dot pos to indicate where float is

    public NumberList(JFrame parent){
        this.parent = parent;
    }

    public void addNumber(int number) {
        list.add(Integer.valueOf(number));
    }

    public void makeFloat(){
        dot_pos = list.size();
    }

    public float getNumber() throws CalculatorException {
        if (list.size() != 0) {
            float number;
            if (dot_pos == -1){
                StringBuilder concat = new StringBuilder(list.size());
                for(Integer num : list) {
                    concat.append(num.intValue());
                }
                number = (float) Integer.valueOf(concat.toString());
            } else {
                StringBuilder concat = new StringBuilder(list.size()+1);
                int i = 0;
                for(Integer num : list){
                    if (dot_pos == i) {
                        concat.append(".");
                    }
                    concat.append(num.intValue());
                    i++;
                }
                System.out.println(concat.toString());
                number = Float.valueOf(concat.toString());
            }
            return number;
        } else {
            JOptionPane.showInternalMessageDialog(parent, "You don't have enter any number, so it's impossible to calculate!", "Number stack is empty!", JOptionPane.WARNING_MESSAGE);
            throw new CalculatorException("Empty number set!");
        }
    }

    public void clear(){
        list = new ArrayList<>();
    }
}
