/*
 * MIT License
 *
 * Copyright (c) 2019 Antoine James Tournepiche
 *
 * This source file come from Just another Stack Calculator
 * Repository : https://github.com/AntoineJT/jasc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.antoinejt.jasc;

import com.github.antoinejt.jasc.calculator.CalculatorEngine;
import com.github.antoinejt.jasc.calculator.FunctionType;
import com.github.antoinejt.jasc.calculator.OperationType;
import com.github.antoinejt.jasc.parser.MiniViewParser;
import com.github.antoinejt.jasc.parser.View;
import com.github.antoinejt.jasc.util.HashMapBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class ConsoleUI {
    private static final Map<String, FunctionType> functions = new HashMapBuilder<String, FunctionType>()
            .put("sqrt", FunctionType.SQRT)
            .put("log", FunctionType.LOG10)
            .put("ln", FunctionType.LN)
            .put("lb", FunctionType.LOGB)
            .put("cos", FunctionType.COS)
            .put("sin", FunctionType.SIN)
            .put("tan", FunctionType.TAN)
            .put("arccos", FunctionType.ARCCOS)
            .put("arcsin", FunctionType.ARCSIN)
            .put("arctan", FunctionType.ARCTAN)
            .put("exp", FunctionType.EXP).build();
    private static final Map<String, OperationType> operators = new HashMapBuilder<String, OperationType>()
            .put("+", OperationType.ADDITION)
            .put("-", OperationType.SUBSTRACTION)
            .put("*", OperationType.MULTIPLICATION)
            .put("/", OperationType.DIVISION)
            .put("%", OperationType.MODULO)
            .put("^", OperationType.POWER).build();
    private static final Set<String> commands = new HashSet<>(
            Arrays.asList("=", "help", "clear", "pop", "quit")
    );

    private static void displayViewFromInside(String pathToFile, Map<String, String> data) {
        View view = View.getViewFromInside(pathToFile);
        MiniViewParser viewParser = new MiniViewParser(view);
        String viewContent = viewParser.parse(data);
        System.out.println(viewContent);
    }

    private static void displayHelp() {
        displayViewFromInside("/views/cli/help.txt", null);
    }
    
    private static void displayIntro() {
        Map<String, String> data = new HashMapBuilder<String, String>()
                .put("VERSION", ManifestInfos.VERSION.toString())
                .put("LAST_UPDATE", ManifestInfos.LAST_UPDATE.toString()).build();
        displayViewFromInside("/views/cli/intro.txt", data);
    }

    private static void printStackContent(CalculatorEngine calculatorEngine) {
        List stackContent = calculatorEngine.getNumbers();

        if (stackContent.size() > 0) {
            stackContent.forEach(System.out::println);
            return;
        }
        System.err.println("Stack is empty!");
    }

    private static void applyFunction(CalculatorEngine calculatorEngine, String input) {
        FunctionType functionType = functions.get(input);
        calculatorEngine.applyFunction(functionType);
    }

    // TODO Rewrite it
    // TODO Explain $ feature into help
    private static void parseInput(CalculatorEngine calculatorEngine, String input) {
        if (input.startsWith("$")) {
            String truncatedInput = input.substring(1);
            if (commands.contains(truncatedInput)) {
                System.err.println("You can't recursively execute commands!");
                return;
            }
            if (operators.containsKey(truncatedInput)) {
                while(calculatorEngine.getNumbers().size() > 1) {
                    tryToApplyOperation(calculatorEngine, truncatedInput);
                }
                return;
            }
            if (functions.containsKey(truncatedInput)) {
                System.err.println("This is not implemented yet and seems really error prone!");
                /*
                while(calculatorEngine.getNumbers().size() > 2) {
                    applyFunction(calculatorEngine, input);
                }
                 */
            }
        }
        if (operators.containsKey(input)) {
            tryToApplyOperation(calculatorEngine, input);
            return;
        }
        if (functions.containsKey(input)) {
            applyFunction(calculatorEngine, input);
            return;
        }
        if (commands.contains(input)) {
            executeCommand(calculatorEngine, input);
            return;
        }

        tryToAddNumberToTheStack(calculatorEngine, input);
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
                break;
            default: break;
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

    @SuppressWarnings({"InfiniteLoopStatement", "WeakerAccess"})
    public static void useConsole() throws UnsupportedOperationException {
        displayIntro();

        CalculatorEngine calculatorEngine = new CalculatorEngine();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.next();
            parseInput(calculatorEngine, input);
        }
    }
}
