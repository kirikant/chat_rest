package com.chat.chat.exception;

public class WrongParametersException extends Exception{
    public WrongParametersException() {
    }

    public WrongParametersException(String message) {
        super(message);
    }
}
