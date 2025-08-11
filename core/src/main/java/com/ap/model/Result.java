package com.ap.model;

public class Result<T> {
    private final boolean success;
    private final T data;
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
    public boolean isSuccess() {
        return success;
    }
    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
