package com.github.antoinejt.jasc;

import com.github.antoinejt.jasc.calculator.CalculatorEngine;
import com.github.antoinejt.jasc.calculator.OperandException;
import com.github.antoinejt.jasc.calculator.OperationType;
import com.github.antoinejt.jasc.util.TextFormat;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class ConsoleUI {
    private static void displayHelp(){
        TextFormat.listThings("Available operators : ",
                "+ : Addition operator",
                "- : Substraction operator",
                "* : Multiplication operator",
                "/ : Division operator").print();
        TextFormat.listThings("Available commands : ",
                "help : show the list of available commands",
                "quit : Allows to quit").print();
    }

    private static void displayIntro(){
        TextFormat.printLines(
                "Just Another Stack Calculator",
                "-------------------------------",
                "Created by Antoine James Tournepiche",
                "Repository link : https://github.com/AntoineJT/jasc",
                "-----------------------------------------------------",
                "Version : 0.1",
                "Last update : April 26th 2019",
                "--------------------------------",
                "This calculator uses a stack, so you must defines at least 2 numbers before using some calculation operator",
                "You must type numbers with or without a dot, not a comma",
                "To know available commands, you can type help");
    }

    public static void useConsole() throws Exception {
        displayIntro();
        CalculatorEngine calculatorEngine = new CalculatorEngine();
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true){
            input = scanner.next();
            List<String> commands = Arrays.asList(new String[]{"+","-","*", "/","=","help","quit"});
            boolean hasInputNumber = !commands.contains(input);
            if (hasInputNumber){
                try {
                    float number = Float.parseFloat(input);
                    calculatorEngine.addNumber(number);
                } catch(NumberFormatException unused){
                    System.err.println("Your input is invalid!");
                }
            } else {
                OperationType operationType = null;
                boolean canOperate = true;
                switch(input) {
                    case "+": operationType = OperationType.ADDITION; break;
                    case "-": operationType = OperationType.SUBSTRACTION; break;
                    case "*": operationType = OperationType.MULTIPLICATION; break;
                    case "/": operationType = OperationType.DIVISION; break;
                    case "=":
                        calculatorEngine.getNumbers().forEach(val -> System.out.println(val));
                        canOperate = false;
                        break;
                    case "help": displayHelp(); canOperate = false; break;
                    case "quit": System.exit(0);
                    default: throw new Exception("This is not good to corrupt my list, hack3rm4n!");
                }
                if (canOperate){
                    try {
                        calculatorEngine.operate(operationType);
                    } catch(OperandException unused){
                        System.err.println("You need to specify at least 2 operands before you can make some calculation!");
                    }
                }
            }
        }
    }
}
