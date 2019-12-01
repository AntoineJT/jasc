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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class View {
    private final String text;

    public View(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        this.text = reader.lines()
                .collect(Collectors.joining("\n"));
        reader.close();
    }

    public View(String viewContent) {
        this.text = viewContent;
    }

    private static String getFileContentFromInside(String pathToFile) {
        InputStream stream = View.class.getResourceAsStream(pathToFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .collect(Collectors.joining("\n"));
    }

    public static View getViewFromInside(String pathToFile) {
        String viewContent = getFileContentFromInside(pathToFile);
        return new View(viewContent);
    }

    @Override
    public String toString() {
        return text;
    }
}
