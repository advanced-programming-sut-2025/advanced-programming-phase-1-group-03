package phi.ap.model.enums.commands;

import java.util.regex.Matcher;

public interface Command {
    Matcher getMatcher(String input);
}
