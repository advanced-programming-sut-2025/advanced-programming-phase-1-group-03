package phi.ap.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    StartTrade("\\s*start\\s+trade\\s*"),
    Trade("\\s*trade\\s+-u\\s+(?<userName>.*)\\s+-t\\s+(?<type>.*)\\s+-i\\s+(?<item>.*)\\s+-a\\s+(?<amount>\\d+)\\s*(\\s+-p\\s+(?<price>.*))?(\\s+-ti\\s+(?<targetItem>.*)\\s+-ta\\s+(?<targetAmount>.*))?\\s*"),
    TradeList("\\s*trade\\s+list\\s*"),
    TradeResponse("\\s*trade\\s+response\\s+-(?<condition>accept|reject)\\s+-i\\s+(?<id>\\d+)\\s*"),
    TradeHistory("\\s*trade\\s+history\\s*"),
    exit("\\s*exit\\s*")
    ;

    private final String command;
    TradeMenuCommands(String command) {
        this.command = command;
    }
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(command).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
