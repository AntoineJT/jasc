package com.github.antoinejt.jasc;

import com.github.antoinejt.jasc.calculator.CalculatorEngine;
import com.github.antoinejt.jasc.calculator.FunctionType;
import com.github.antoinejt.jasc.calculator.OperationType;
import com.github.antoinejt.jasc.util.TextFormatter;

import java.util.*;

class ConsoleUI {
    private static final Map<String, FunctionType> functions = new HashMap<String, FunctionType>() {{
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
    }};
    private static final Map<String, OperationType> operators = new HashMap<String, OperationType>() {{
        put("+", OperationType.ADDITION);
        put("-", OperationType.SUBSTRACTION);
        put("*", OperationType.MULTIPLICATION);
        put("/", OperationType.DIVISION);
        put("%", OperationType.MODULO);
        put("^", OperationType.POWER);
    }};
    private static final List<String> commands = new ArrayList<>(
            Arrays.asList("=", "help", "clear", "pop", "quit")
    );

    // TODO Replace that by some txt templates (use of MVC)
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

        if (stackContent.size() > 0) {
            stackContent.forEach(System.out::println);
            return;
        }
        System.err.println("Stack is empty!");
    }

    private static void inputLoop(CalculatorEngine calculatorEngine, Scanner scanner) {
        String input = scanner.next();

        if (operators.containsKey(input)) {
            tryToApplyOperation(calculatorEngine, input);
            inputLoop(calculatorEngine, scanner);
        }
        if (functions.containsKey(input)) {
            FunctionType functionType = functions.get(input);
            calculatorEngine.applyFunction(functionType);
            inputLoop(calculatorEngine, scanner);
        }
        if (commands.contains(input)) {
            executeCommand(calculatorEngine, input);
            inputLoop(calculatorEngine, scanner);
        }

        tryToAddNumberToTheStack(calculatorEngine, input);
        inputLoop(calculatorEngine, scanner);
    }

    private static void executeCommand(CalculatorEngine calculatorEngine, String input) {
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
        }
    }

    private static void tryToApplyOperation(CalculatorEngine calculatorEngine, String input) {
        try {
            OperationType operationType = operators.get(input);
            calculatorEngine.applyOperation(operationType);
        } catch (IllegalStateException unused) {
            System.err.println("You need to specify at least 2 operands before you can make some calculation!");
        }
    }

    private static void tryToAddNumberToTheStack(CalculatorEngine calculatorEngine, String input) {
        try {
            float number = Float.parseFloat(input);
            calculatorEngine.addNumber(number);
        } catch (NumberFormatException unused) {
            System.err.println("Your input is invalid!");
        }
    }

    static void useConsole() throws UnsupportedOperationException {
        displayIntro();

        CalculatorEngine calculatorEngine = new CalculatorEngine();
        Scanner scanner = new Scanner(System.in);

        inputLoop(calculatorEngine, scanner);
    }
}
