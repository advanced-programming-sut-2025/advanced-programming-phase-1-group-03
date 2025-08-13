package com.ap.requests;

public class IngredientRequest {
    public String recipeName;
    public int number;

    public IngredientRequest(){}

    public IngredientRequest(String recipeName, int number) {
        this.recipeName = recipeName;
        this.number = number;
    }
}
