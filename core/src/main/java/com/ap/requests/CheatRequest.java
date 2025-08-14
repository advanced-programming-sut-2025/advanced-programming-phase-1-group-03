package com.ap.requests;

public class CheatRequest {
    public String command;
    public int number;

    public CheatRequest(){}

    public CheatRequest(String name, int number) {
        this.command = name;
        this.number = number;
    }
}
