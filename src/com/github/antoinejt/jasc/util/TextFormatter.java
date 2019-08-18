package com.github.antoinejt.jasc.util;

import java.io.PrintStream;

public final class TextFormatter {
    private static final PrintStream sysout = System.out;

    public static final class FormattedText {
        private final String formattedText;

        private FormattedText(String formattedText){
            this.formattedText = formattedText;
        }

        public String toString(){
            return formattedText;
        } // It's not needed to call that, implicit call will be done when you print the object (?)

        public void print(){
            sysout.print(formattedText);
        }
    }

    public static FormattedText formatLines(String prefix, String separator, String[] lines){
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lines){
            stringBuilder
                    .append(prefix)
                    .append(line)
                    .append(separator);
        }
        String text = stringBuilder.toString();
        return new FormattedText(text);
    }

    public static FormattedText formatLines(String prefix, String[] lines){
        return formatLines(prefix, "\n", lines);
    }

    public static void printLines(String... lines){
        sysout.print(formatLines("", lines));
    }

    public static FormattedText listThings(String label, String... lines){
        FormattedText formattedText = formatLines("\t- ", "\n", lines);
        return new FormattedText(label + "\n" + formattedText);
    }
}
