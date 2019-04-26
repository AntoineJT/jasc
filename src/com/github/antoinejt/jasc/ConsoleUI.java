package com.github.antoinejt.jasc;

import com.github.antoinejt.jasc.calculator.CalculatorEngine;
import com.github.antoinejt.jasc.calculator.FunctionType;
import com.github.antoinejt.jasc.calculator.OperandException;
import com.github.antoinejt.jasc.calculator.OperationType;
import com.github.antoinejt.jasc.util.TextFormat;

import java.util.*;

public final class ConsoleUI {
    private static void displayHelp(){
        TextFormat.listThings("Available operators (acts on 2 operands) : ",
                "+ : Addition operator",
                "- : Substraction operator",
                "* : Multiplication operator",
                "/ : Division operator").print();
        TextFormat.listThings("Available functions (acts on 1 operand) : ",
                "sqrt : Perform a square root",
                "log : Perform a decimal logarithm",
                "ln : Perform a napierian logarithm",
                "lb : Perform a binary logarithm",
                "cos : Perform a cosine",
                "sin : Perform a sine",
                "tan : Perform a tangent",
                "arccos : Perform an arc cosine",
                "arcsin : Perform an arc sine",
                "arctan : Perform an arc tan",
                "exp : Make it exponent").print();
        TextFormat.listThings("Available commands : ",
                "help : Show the list of available commands",
                "clear : Reset stack content",
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
            List<String> commands = Arrays.asList(
                    "+","-","*", "/","=", // Operators
                    "sqrt", "log", "ln", "lb", "cos", "sin", "tan", "arccos", "arcsin", "arctan", "exp", // Functions
                    "help","clear","quit" // Commands
            );
            boolean hasInputNumber = !commands.contains(input);
            if (hasInputNumber){
                try {
                    float number = Float.parseFloat(input);
                    calculatorEngine.addNumber(number);
                } catch(NumberFormatException unused){
                    System.err.println("Your input is invalid!");
                }
            } else {
                Map<String, OperationType> operators = new HashMap<>();
                operators.put("+", OperationType.ADDITION);
                operators.put("-", OperationType.SUBSTRACTION);
                operators.put("*", OperationType.MULTIPLICATION);
                operators.put("/", OperationType.DIVISION);

                if (operators.containsKey(input)){
                    try {
                        calculatorEngine.operate(operators.get(input));
                    } catch(OperandException unused){
                        System.err.println("You need to specify at least 2 operands before you can make some calculation!");
                    }
                } else {
                    boolean isFunction = false;
                    switch(input){
                        case "=": calculatorEngine.getNumbers().forEach(val -> System.out.println(val)); break;
                        case "help": displayHelp(); break;
                        case "clear": calculatorEngine.clear(); break;
                        case "quit": System.exit(0);
                        default: isFunction = true;
                    }
                    if (isFunction){
                        FunctionType functionType = null;
                        switch(input){ // TODO Maybe implement that with a simple private enum
                            case "sqrt": functionType = FunctionType.SQRT; break;
                            case "log": functionType = FunctionType.LOG10; break;
                            case "ln": functionType = FunctionType.LN; break;
                            case "lb": functionType = FunctionType.LOGB; break;
                            case "cos": functionType = FunctionType.COS; break;
                            case "sin": functionType = FunctionType.SIN; break;
                            case "tan": functionType = FunctionType.TAN; break;
                            case "arccos": functionType = FunctionType.ARCCOS; break;
                            case "arcsin": functionType = FunctionType.ARCSIN; break;
                            case "arctan": functionType = FunctionType.ARCTAN; break;
                            case "exp": functionType = FunctionType.EXP; break;
                            default: throw new Exception("This is not good to corrupt my list, hack3rm4n!");
                        }
                        calculatorEngine.applyFunction(functionType);
                    }
                }
            }
        }
    }
}
