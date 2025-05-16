package phi.ap.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileManuCommands {
    ChangeUsername("\\s*change\\s+username\\s+-u\\s*(?<username>.*)"),
    ChangeNickname("\\s*change\\s+nickname\\s+-u\\s*(?<nickname>.*)"),
    ChangeEmail("\\s*change\\s+email\\s+-e\\s*(?<email>.*)"),
    ChangePassword("\\s*change\\s+password\\s+-p\\s*(?<newPassword>.*)\\s*-o\\s*(?<oldPassword>.*)\\s*"),
    ShowUserInfo("\\s*user\\s+info\\s*")
    ;
    private String command;

    ProfileManuCommands(String command) {
        this.command = command;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(command).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
