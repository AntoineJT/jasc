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

package com.github.antoinejt.jasc.parser;

import java.util.Map;

public class MiniViewParser {
    private final View view;

    public MiniViewParser(View view) {
        this.view = view;
    }

    public String parse(Map<String, String> data) throws IllegalStateException {
        String viewContent = view.toString();

        if (data == null
                || !viewContent.contains("{{ ")
                || !viewContent.contains(" }}")) {
            return viewContent;
        }
        checkViewValidity();
        return fillView(data);
    }

    private int countOccurrences(String regex) {
        return view.toString().split(regex).length - 1;
    }

    private void checkViewValidity() throws IllegalStateException {
        int beginCount = countOccurrences("\\{\\{ ");
        int endCount = countOccurrences(" }}");

        if (beginCount != endCount) {
            throw new IllegalStateException("View is invalid! Please fix it!");
        }
    }

    private String fillView(Map<String, String> data) {
        String result = view.toString();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            result = result.replaceAll("\\{\\{ " + entry.getKey() + " }}", entry.getValue());
        }
        return result;
    }
}
