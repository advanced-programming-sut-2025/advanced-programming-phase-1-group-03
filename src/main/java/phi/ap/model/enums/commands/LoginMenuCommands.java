package phi.ap.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands implements Command {
    Register("register -u (?<username>.*) -p (?<password>\\S*) (?<passwordConfirm>\\S*) -n (?<nickname>.*) -e" +
            "(?<email>.*) -g (?<gender>.*)"),
    PickQuestion("pick question -q (?<questionNumber>.*) -a (?<answer>.*) -c (?<answerConfirm>.*)"),
    Login("login\\s*-u\\s*(?<username>\\S*)\\s*-p\\s*(?<password>\\S*)(?<stay>\\s*–stay-logged-in)?");
    private String command;

    LoginMenuCommands(String command) {
        this.command = command;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(command).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
