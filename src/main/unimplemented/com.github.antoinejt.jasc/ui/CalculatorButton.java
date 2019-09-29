package com.github.antoinejt.jasc.unimplemented.ui;

import javax.swing.*;
import java.awt.*;

class CalculatorButton extends JButton {
    {
        this.setBackground(Color.decode("#333333"));
        this.setForeground(Color.WHITE);
    }

    public CalculatorButton(String text) {
        super(text);
    }
}
