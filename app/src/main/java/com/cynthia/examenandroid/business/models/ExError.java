package com.cynthia.examenandroid.business.models;

public class ExError {

    private String title;
    private String message;
    private String context;
    private Throwable throwable;

    public ExError(String title, String message) {
        this(title, message, null);
    }

    public ExError(String title, Throwable throwable) {
        this(title, null, throwable);
    }

    public ExError(String title, String message, Throwable throwable) {
        this.title = title;
        this.message = message;
        this.throwable = throwable;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Throwable getThrowable() {
        return throwable;
    }

}
