package com.blogapp.web.exceptions;

public class NullPostObjectException extends Exception{
    public NullPostObjectException() {
    }

    public NullPostObjectException(String message) {
        super(message);
    }

    public NullPostObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullPostObjectException(Throwable cause) {
        super(cause);
    }


}
