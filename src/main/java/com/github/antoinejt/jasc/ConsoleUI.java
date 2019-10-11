package com.github.antoinejt.jasc;

import com.github.antoinejt.jasc.calculator.CalculatorEngine;
import com.github.antoinejt.jasc.calculator.FunctionType;
import com.github.antoinejt.jasc.calculator.OperationType;
import com.github.antoinejt.jasc.util.TextFormatter;

import java.util.*;

class ConsoleUI {
    private static final Map<String, FunctionType> functions = Collections.unmodifiableMap(new HashMap<String, FunctionType>() {{
        put("sqrt", FunctionType.SQRT);
        put("log", FunctionType.LOG10);
        put("ln", FunctionType.LN);
        put("lb", FunctionType.LOGB);
        put("cos", FunctionType.COS);
        put("sin", FunctionType.SIN);
        put("tan", FunctionType.TAN);
        put("arccos", FunctionType.ARCCOS);
        put("arcsin", FunctionType.ARCSIN);
        put("arctan", FunctionType.ARCTAN);
        put("exp", FunctionType.EXP);
    }});
    private static final Map<String, OperationType> operators = Collections.unmodifiableMap(new HashMap<String, OperationType>() {{
        put("+", OperationType.ADDITION);
        put("-", OperationType.SUBSTRACTION);
        put("*", OperationType.MULTIPLICATION);
        put("/", OperationType.DIVISION);
        put("%", OperationType.MODULO);
        put("^", OperationType.POWER);
    }});
    private static final List<String> commands = Collections.unmodifiableList(new ArrayList<String>() {{
        addAll(functions.keySet()); // functions are added here
        addAll(operators.keySet()); // operators are added here
        addAll(Arrays.asList("=", "help", "clear", "pop", "quit")); // Commands
    }});

    // TODO Replace that by some txt templates (use of MVC
    private static void displayHelp() {
        TextFormatter.listThings("Available operators (acts on 2 operands) : ",
                "+ : Addition operator",
                "- : Substraction operator",
                "* : Multiplication operator",
                "/ : Division operator",
                "% : Modulo operator",
                "^ : Power operator").print();
        TextFormatter.listThings("Available functions (acts on 1 operand) : ",
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
        TextFormatter.listThings("Available commands : ",
                "= : Print the content of the stack",
                "help : Show the list of available commands",
                "clear : Reset stack content",
                "pop : Remove last number from the stack",
                "quit : Allows to quit").print();
    }

    private static void displayIntro() {
        TextFormatter.printLines(
                "Just Another Stack Calculator",
                "-------------------------------",
                "Created by Antoine James Tournepiche",
                "Repository link : https://github.com/AntoineJT/jasc",
                "-----------------------------------------------------",
                "Version : " + ManifestInfos.VERSION,
                "Last update : " + ManifestInfos.LAST_UPDATE,
                "--------------------------------",
                "This calculator uses a stack, so you must define at least 2 numbers before using some calculation operator",
                "You must type numbers with or without a dot, not a comma",
                "To know available commands, you can type help");
    }

    private static void printStackContent(CalculatorEngine calculatorEngine) {
        List stackContent = calculatorEngine.getNumbers();
        int stackContentSize = stackContent.size();

        if (stackContentSize > 0) {
            stackContent.forEach(System.out::println);
            return;
        }
        System.err.println("Stack is empty!");
    }

    // TODO Refactor it!
    static void useConsole() throws Exception {
        displayIntro();

        CalculatorEngine calculatorEngine = new CalculatorEngine();
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.next();
            if (!commands.contains(input)) { // if it's a number
                try {
                    float number = Float.parseFloat(input);

                    calculatorEngine.addNumber(number);
                } catch (NumberFormatException unused) {
                    System.err.println("Your input is invalid!");
                }
                continue;
            }
            if (operators.containsKey(input)) {
                try {
                    OperationType operationType = operators.get(input);

                    calculatorEngine.applyOperation(operationType);
                } catch (IllegalStateException unused) {
                    System.err.println("You need to specify at least 2 operands before you can make some calculation!");
                }
                continue;
            }

            boolean isFunction = false;

            switch (input) {
                case "=":
                    printStackContent(calculatorEngine);
                    break;
                case "help":
                    displayHelp();
                    break;
                case "clear":
                    calculatorEngine.clear();
                    break;
                case "pop":
                    calculatorEngine.removeLastNumber();
                    break;
                case "quit":
                    System.exit(0);
                default:
                    isFunction = true;
            }
            if (isFunction) {
                if (functions.containsKey(input)) {
                    FunctionType functionType = functions.get(input);

                    calculatorEngine.applyFunction(functionType);
                    continue;
                }
                throw new UnsupportedOperationException("This is not good to corrupt my list, hack3rm4n!");
            }
        }
    }
}
