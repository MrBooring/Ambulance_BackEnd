package com.rapidrescue.ambulancewale.models.response;

public class ExceptionRes {
    private String message;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ExceptionRes(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
