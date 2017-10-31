package com.stephen.springgenerator.base;

public class FormatException extends IllegalArgumentException {

    public FormatException(String s) {
        super(s);
    }

    public FormatException() {
        super();
    }

    public static FormatException forInputString(String s) {
        return new FormatException("For input string: \"" + s + "\"");
    }
}
