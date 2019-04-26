package com.github.antoinejt.jasc.calculator;

import java.util.EmptyStackException;

final class Stack<T> {
    private int size = 0;
    private java.util.Stack<T> stack = new java.util.Stack<>();

    T pop(){
        if (size > 0){
            size--;
            return stack.pop();
        } else {
            throw new EmptyStackException();
        }
    }

    void push(T item){
        size++;
        stack.push(item);
    }

    void clear(){
        size = 0;
        stack = new java.util.Stack<>();
    }

    int getSize(){
        return size;
    }
}
