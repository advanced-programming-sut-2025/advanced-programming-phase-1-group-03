package phi.ap.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ChoosingMapMenuCommands implements Command{
    ChooseGameMap("\\s*game\\s+map\\s+(?<mapNumber>\\d+)\\s*")
    ;
    private String command;

    ChoosingMapMenuCommands(String command) {
        this.command = command;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(command).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
