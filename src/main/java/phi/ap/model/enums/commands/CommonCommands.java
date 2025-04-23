package phi.ap.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CommonCommands implements Command{
    ShowCurrentMenu("\\s*show current menu\\s*");

    private String command;

    CommonCommands(String command) {
        this.command = command;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(command).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
