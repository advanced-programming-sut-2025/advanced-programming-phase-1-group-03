package phi.ap.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements Command{
    NewGame("(\\s*game\\s+new\\s+|\\s*new\\s+game\\s+)-u\\s*" +
            "(?<usernames>.*)\\s*"),
    GameMap("\\s*game\\s+map\\s+(?<mapNumber>\\d+)\\s*"),
    PrintMap("\\s*print\\s+map\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s+-s\\s+(?<size>\\d+)\\s*"),
    PrintMapComplete("\\s*print\\s+map\\s+complete\\s*");
    private final String command;
    GameMenuCommands(String command) {
        this.command = command;
    }
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(command).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
