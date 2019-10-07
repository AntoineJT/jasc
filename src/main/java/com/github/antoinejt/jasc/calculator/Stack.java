package com.github.antoinejt.jasc.calculator;

import java.util.EmptyStackException;

final class Stack<T> {
    private java.util.Stack<T> stack = new java.util.Stack<>();

    T pop() {
        int stackSize = stack.size();

        if (stackSize > 0) {
            return stack.pop();
        }
        throw new EmptyStackException();
    }

    void push(T item) {
        stack.push(item);
    }

    void clear() {
        stack = new java.util.Stack<>();
    }

    int getSize() {
        return stack.size();
    }
}
