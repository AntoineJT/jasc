package com.github.antoinejt.jasc.test;

import com.github.antoinejt.jasc.CalculatorEngine;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    /*
    private static final int WIDTH = 350;
    private static final int HEIGHT = 500;

    public static void main(String[] args){
        new CalculatorFrame(WIDTH, HEIGHT);
    }
     */

    public static void main(String[] args){
        try {
            useConsole();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void useConsole() throws Exception {
        System.out.println("This is Just Another Stack Calculator");
        CalculatorEngine calculatorEngine = new CalculatorEngine();
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true){
            input = scanner.next();
            List<String> commands = Arrays.asList(new String[]{"+","-","*", "/","=", "quit"});
            boolean hasInputNumber = !commands.contains(input);
            if (hasInputNumber){
                try {
                    float number = Float.parseFloat(input);
                    calculatorEngine.addNumber(number);
                } catch(NumberFormatException unused){
                    System.err.println("Your input is invalid!");
                }
            } else {
                CalculatorEngine.OperationType operationType = null;
                boolean canOperate = true;
                switch(input) {
                    case "+": operationType = CalculatorEngine.OperationType.ADDITION; break;
                    case "-": operationType = CalculatorEngine.OperationType.SUBSTRACTION; break;
                    case "*": operationType = CalculatorEngine.OperationType.MULTIPLICATION; break;
                    case "/": operationType = CalculatorEngine.OperationType.DIVISION; break;
                    case "=":
                        calculatorEngine.getNumbers().forEach(val -> System.out.println(val));
                        canOperate = false;
                        break;
                    case "quit": System.exit(0);
                    default: throw new Exception("This is not good to corrupt my list, hack3rm4n!");
                }
                if (canOperate){
                    try {
                        calculatorEngine.operate(operationType);
                    } catch(CalculatorEngine.OperandException unused){
                        System.err.println("You need to specify at least 2 operands before you can make some calculation!");
                    }
                }
            }
        }
    }
}
