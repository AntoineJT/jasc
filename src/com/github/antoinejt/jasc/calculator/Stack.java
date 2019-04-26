package com.github.antoinejt.jasc.calculator;

import java.util.EmptyStackException;

public final class Stack<T> { // NOTE : Stack has been implemented since Java 7 but it's slightly different from my version
    /*
    private List<T> stack = new Vector<>();

    public T pop(){
        final int index = stack.size() - 1;
        T item = stack.get(index);
        stack.remove(index);
        return item;
    }

    public void push(T item){
        stack.add(item);
    }

    public void clear(){
        stack = new Vector<>();
    }

    public int getSize() {
        return stack.size();
    }
    */

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
        stack = new java.util.Stack<T>();
    }

    public int getSize(){
        return size;
    }
}
