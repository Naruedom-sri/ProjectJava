package com.example.domain;

public class MyException extends RuntimeException {

    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(Throwable err) {
        super(err);
    }

    public MyException(String Message, Throwable err) {
        super(Message, err);
    }

}
