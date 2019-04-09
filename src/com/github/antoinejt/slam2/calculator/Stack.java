package com.github.antoinejt.slam2.calculator;

import java.util.ArrayList;
import java.util.List;

public class Stack<T> {
    private List stack = new ArrayList<T>();

    public T pop(){
        int index = stack.size()-1;
        T item = (T)stack.get(index);
        stack.remove(index);
        return item;
    }

    public void push(T item){
        stack.add(item);
    }

    public void clear(){
        stack = new ArrayList<T>();
    }

    public int getSize() {
        return stack.size();
    }
}
