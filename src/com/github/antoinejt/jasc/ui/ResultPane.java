package com.github.antoinejt.jasc.ui;

import javax.swing.*;

class ResultPane {
    private JTextPane pane = new JTextPane();

    ResultPane(int width, int height){
        pane.setEditable(false);
        pane.setSize(width, height);
        pane.setFont(pane.getFont().deriveFont(32.0f));
    }

    JTextPane getPane(){
        return pane;
    }

    void setResult(String text) {
        pane.setText(text);
        pane.repaint();
    }

    void concatResult(String text){
        setResult(pane.getText() + text);
    }
}
