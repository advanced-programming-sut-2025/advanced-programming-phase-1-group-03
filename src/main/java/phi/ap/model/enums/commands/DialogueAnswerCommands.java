package phi.ap.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum DialogueAnswerCommands implements Command{
    AnswerYesNo("(?i)\\s*(?<answer>(yes|no))\\s*"),
    Enter(".*")
    ;

    private final String command;
    DialogueAnswerCommands(String command) {
        this.command = command;
    }
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(command).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
