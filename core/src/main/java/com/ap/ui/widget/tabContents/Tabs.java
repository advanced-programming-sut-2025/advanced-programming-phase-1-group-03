package com.ap.ui.widget.tabContents;

public enum Tabs {
    Inventory("inventory"),
    Social("social"),
    Skill("blank"),
    Options("options"),
    Map("map"),
    Exit("exit")
    ;

    public final String path;

    Tabs(String path) {
        this.path = path;
    }
}
