package com.ap.requests;

import com.ap.model.Menus;

public class BuyItemRequest {
    public String name;
    public Menus menu;

    public BuyItemRequest() {
    }

    public BuyItemRequest(String name, Menus menu) {
        this.name = name;
        this.menu = menu;
    }
}
