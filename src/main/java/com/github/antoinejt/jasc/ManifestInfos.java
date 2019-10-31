package com.github.antoinejt.jasc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
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

public enum ManifestInfos {
    LAST_UPDATE("Last-Update"),
    VERSION("Version");

    private static Attributes attributes = null;

    // Load manifest attributes just once
    static {
        URL resource = ManifestInfos.class.getClassLoader().getResource("META-INF/MANIFEST.MF");
        try {
            InputStream inputStream = Objects.requireNonNull(resource, "MANIFEST.MF doesn't exists!").openStream();
            Manifest manifest = new Manifest(inputStream);
            ManifestInfos.attributes = manifest.getMainAttributes();
        } catch (IOException ignored) {
        }
    }

    private final String valueName;

    ManifestInfos(String valueName) {
        this.valueName = valueName;
    }

    @Override
    public String toString() {
        return ManifestInfos.attributes.getValue(valueName);
    }
}
