package com.ap.requests;

public class ReduceIngredientRequest {
    public String name;
    public int number;

    public ReduceIngredientRequest(){}

    public ReduceIngredientRequest(String name, int number) {
        this.name = name;
        this.number = number;
    }
}
