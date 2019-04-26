package com.github.antoinejt.jasc;

import java.util.ArrayList;
import java.util.List;

public class Stack<T> {
    private List<T> stack = new ArrayList<>();

    /*
    public Stack(){}

    public Stack(Stack original){
        original.stack.forEach(val -> stack.add((T)val));
    }
    */

    public T pop(){
        int index = stack.size() - 1;
        T item = stack.get(index);
        stack.remove(index);
        return item;
    }

    public void push(T item){
        stack.add(item);
    }

    public void clear(){
        stack = new ArrayList<>();
    }

    public int getSize() {
        return stack.size();
    }
}
