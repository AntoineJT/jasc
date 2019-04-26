package com.github.antoinejt.jasc.calculator;

import java.util.EmptyStackException;

public final class Stack<T> {
    private int size = 0;
    private java.util.Stack<T> stack = new java.util.Stack<>();

    public T pop(){
        if (size > 0){
            size--;
            return stack.pop();
        } else {
            throw new EmptyStackException();
        }
    }

    public void push(T item){
        size++;
        stack.push(item);
    }

    public void clear(){
        size = 0;
        stack = new java.util.Stack<>();
    }

    public int getSize(){
        return size;
    }
}
