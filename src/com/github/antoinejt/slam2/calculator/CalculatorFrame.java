package com.github.antoinejt.slam2.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CalculatorFrame extends JFrame {
    private static final int WIDTH = 350;
    private static final int HEIGHT = 500;

    private static CalculatorFrame instance;
    public static CalculatorFrame getInstance(){
        return instance != null ? instance : new CalculatorFrame();
    }

    public CalculatorFrame(){
        instance = this;
        this.setTitle("Calculator");
        this.setSize(WIDTH,HEIGHT);
        this.setContentPane(new Panel());
        this.setVisible(true);
    }

    static class CalculatorException extends Exception {
        public CalculatorException(String msg){
            super(msg);
        }
    }

    static class Panel extends JPanel {
        // Singleton, but not restrictive
        private static Panel instance = null;
        public static Panel getInstance(){
            return instance != null ? instance : new Panel();
        }

        static class NumberPanel extends JPanel {
            static class NumberList {
                // Singleton, but not restrictive
                private static NumberList instance = null;
                public static NumberList getInstance(){
                    return instance != null ? instance : new NumberList();
                }
                public NumberList(){
                    instance = this;
                }

                private List<Integer> list = new ArrayList<>();
                private int dot_pos = -1; // dot pos to indicate where float is

                public void addNumber(int number) {
                    list.add(Integer.valueOf(number));
                }

                public void makeFloat(){
                    dot_pos = list.size()-1;
                }

                public float getNumber(boolean clear) throws CalculatorException {
                    if (list.size() != 0) {
                        float number;
                        if (dot_pos == -1){
                            StringBuilder concat = new StringBuilder(list.size());
                            for(Integer num : list) {
                                concat.append(num.intValue());
                            }
                            number = (float)Integer.valueOf(concat.toString()).intValue();
                        } else {
                            StringBuilder concat = new StringBuilder(list.size()+1);
                            int i = 0;
                            for(Integer num : list){
                                if (dot_pos == i) {
                                    concat.append(".");
                                }
                                concat.append(num.intValue());
                            }
                            number = Float.valueOf(concat.toString()).floatValue();
                        }
                        if (clear){
                            clear();
                        }
                        return number;
                    } else {
                        JOptionPane.showInternalMessageDialog(CalculatorFrame.getInstance(), "You don't have input any number, so it's impossible to calculate!", "Number stack is empty!", JOptionPane.WARNING_MESSAGE);
                        throw new CalculatorException("Empty number set!");
                    }
                }

                public void clear(){
                    list = new ArrayList<>();
                }
            }

            public NumberPanel(){
                this.setLayout(new GridLayout(3,3));
                for (int y=0; y<3; y++){
                    for (int x=0; x<3; x++){
                        final int number = (x+1)+(y*3);
                        JButton button = new JButton(String.valueOf(number));
                        button.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent unused) {
                                NumberList nl = NumberList.getInstance();
                                nl.addNumber(number);
                                try {
                                    Panel.getInstance().setOutText(String.valueOf(nl.getNumber(false)));
                                } catch (CalculatorException ignored) {}
                            }
                        });
                        this.add(button);
                    }
                }
            }
        }

        private JTextPane output = new JTextPane();
        {
            output.setEditable(false);
            output.setSize(WIDTH,HEIGHT/5);
        }
        protected void setOutText(String text){
            output.setText(text);
        }

        public Panel(){
            instance = this;
            this.setLayout(new BorderLayout());
            this.add(output, BorderLayout.NORTH);
            this.add(new NumberPanel(), BorderLayout.CENTER);
        }
    }
}
