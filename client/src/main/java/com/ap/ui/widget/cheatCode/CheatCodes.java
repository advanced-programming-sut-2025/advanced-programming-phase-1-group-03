package com.ap.ui.widget.cheatCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CheatCodes {
    TimeSpeed("\\s*set\\s+time\\s+speed\\s+(?<speed>\\d+)\\s*"),
    Thor("\\s*summon\\s+thor\\s+at\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*"),
    Energy("\\s*set\\s+energy\\s+(?<energy>\\d+)\\s*"),
    AddGold("\\s*set\\s+gold\\s+(?<gold>\\d+)\\s*"),
    SendMessage("\\s*send\\s*message\\s*to\\s*(?<user>\\S+)\\s+(?<message>.*)\\s*"),
    Vote("\\s*vote\\s+for\\s+(?<user>.+)\\s*"),
    Thunder("\\s*thunder\\s*")
    ;
    private String pattern;

    CheatCodes(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String command) {
        Matcher matcher = Pattern.compile(pattern).matcher(command);
        if (matcher.matches()) return matcher;
        return null;
    }
}
