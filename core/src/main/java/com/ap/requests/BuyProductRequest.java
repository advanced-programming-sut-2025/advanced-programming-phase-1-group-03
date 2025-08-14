package com.ap.requests;

public class BuyProductRequest {
    public String name;
    public int number;

    public BuyProductRequest() {

    }

    public BuyProductRequest(String name, int number) {
        this.name = name;
        this.number = number;
    }
}
