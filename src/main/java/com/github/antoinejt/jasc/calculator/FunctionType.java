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

package com.github.antoinejt.jasc.calculator;

import java.util.function.Function;

public enum FunctionType {
    SQRT(Math::sqrt),
    LOG10(Math::log10),
    LN(Math::log),
    LOGB(number -> (double) computeBinaryLog(number)),
    COS(Math::cos),
    SIN(Math::sin),
    TAN(Math::tan),
    ARCCOS(Math::acos),
    ARCSIN(Math::asin),
    ARCTAN(Math::atan),
    EXP(Math::exp);

    private final Function<Float, Double> function;

    FunctionType(Function<Float, Double> function) {
        this.function = function;
    }

    private static int computeBinaryLog(float number) {
        // https://stackoverflow.com/questions/3305059/how-do-you-calculate-log-base-2-in-java-for-integers
        double logNumber = Math.log(number);
        double log2 = Math.log(2);

        return (int) (logNumber / log2 + 1e-10);
    }

    public double apply(float number) {
        return function.apply(number);
    }
}
