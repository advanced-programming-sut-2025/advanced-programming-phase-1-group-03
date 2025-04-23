package phi.ap.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CommonCommands implements Command{
    ShowCurrentMenu("\\s*show current menu\\s*"),
    MenuExit("\\s*menu\\s+exit\\s*"),
    MenuEnter("\\s*menu\\s+enter (?<menu>.*)"),
    ;

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
