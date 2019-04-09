package com.github.antoinejt.slam2.calculator;

public class Main {
    /*
    private static final Main instance = new Main(); // Restrictive Singleton
    private CalculatorFrame window;

    public static Main getInstance(){
        return instance != null ? instance : new Main();
    }

    public CalculatorFrame getWindow(){
        return window;
    }
    */

    public static void main(String[] args){
       // Main.getInstance().window = new CalculatorFrame();
        new CalculatorFrame();
    }
}
