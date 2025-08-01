package com.ap.ui.widget.cheatCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CheatCodes {
    TimeSpeed("\\s*set\\s+time\\s+speed\\s+(?<speed>\\d+)\\s*(?<unit>.*)\\s*"),
    Thor("\\s*summon\\s+thor\\s*"),
    Energy("\\s*set\\s+energy\\s+(?<energy>\\d+)\\s*"),
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
