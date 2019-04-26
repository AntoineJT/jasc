package com.github.antoinejt.jasc.tests.ui;

import javax.swing.*;
import java.awt.*;

class CalculatorPanel extends JPanel {
    private final JFrame parent;
    private ResultPane resultPane;

    JFrame getParentFrame(){
        return parent;
    }

    ResultPane getResultPane(){
        return resultPane;
    }

    CalculatorPanel(JFrame parent){
        this.parent = parent;

        draw();
    }

    private void draw(){
        this.setLayout(new BorderLayout());
        resultPane = new ResultPane(parent.getWidth(), parent.getHeight()/5);
        this.add(resultPane.getPane(), BorderLayout.NORTH);
        this.add(new NumberPanel(this), BorderLayout.CENTER);
    }
}
