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

package com.github.antoinejt.jasc.util;

public class TextFormatter {
    public static final class FormattedText {
        private final String formattedText;

        private FormattedText(String formattedText) {
            this.formattedText = formattedText;
        }

        @Override
        public String toString() {
            return formattedText;
        }

        public void print() {
            System.out.print(formattedText);
        }
    }

    public static FormattedText formatLines(String prefix, String separator, String[] lines) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String line : lines) {
            stringBuilder
                    .append(prefix)
                    .append(line)
                    .append(separator);
        }
        return new FormattedText(stringBuilder.toString());
    }

    public static FormattedText formatLines(String prefix, String[] lines) {
        return formatLines(prefix, "\n", lines);
    }

    public static void printLines(String... lines) {
        System.out.print(formatLines("", lines));
    }

    public static FormattedText listThings(String label, String... lines) {
        FormattedText formattedText = formatLines("\t- ", "\n", lines);
        return new FormattedText(label + "\n" + formattedText);
    }
}
