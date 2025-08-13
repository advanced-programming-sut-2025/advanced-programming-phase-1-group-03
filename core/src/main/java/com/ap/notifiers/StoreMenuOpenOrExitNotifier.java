package com.ap.notifiers;

import com.ap.model.Menus;


public class StoreMenuOpenOrExitNotifier {
    public Menus menu;
    public boolean open;

    public StoreMenuOpenOrExitNotifier() {
    }

    public StoreMenuOpenOrExitNotifier(Menus menu, boolean open) {
        this.menu = menu;
        this.open = open;
    }
}
