package com.github.antoinejt.jasc;

public class Main {
    public static void main(String[] args) {
        try {
            ConsoleUI.useConsole();
        } catch (UnsupportedOperationException exception) {
            exception.printStackTrace();
        }
    }
}
