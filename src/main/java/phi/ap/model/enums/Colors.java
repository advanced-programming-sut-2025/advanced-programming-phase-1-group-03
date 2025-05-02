package phi.ap.model.enums;

public enum Colors {
    // Foreground (text) colors
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),

    // Bright foreground colors
    BRIGHT_BLACK("\u001B[90m"),
    BRIGHT_RED("\u001B[91m"),
    BRIGHT_GREEN("\u001B[92m"),
    BRIGHT_YELLOW("\u001B[93m"),
    BRIGHT_BLUE("\u001B[94m"),
    BRIGHT_PURPLE("\u001B[95m"),
    BRIGHT_CYAN("\u001B[96m"),
    BRIGHT_WHITE("\u001B[97m"),

    // Background colors
    BLACK_BACKGROUND("\u001B[40m"),
    RED_BACKGROUND("\u001B[41m"),
    GREEN_BACKGROUND("\u001B[42m"),
    YELLOW_BACKGROUND("\u001B[43m"),
    BLUE_BACKGROUND("\u001B[44m"),
    PURPLE_BACKGROUND("\u001B[45m"),
    CYAN_BACKGROUND("\u001B[46m"),
    WHITE_BACKGROUND("\u001B[47m"),

    // Bright background colors
    BRIGHT_BLACK_BACKGROUND("\u001B[100m"),
    BRIGHT_RED_BACKGROUND("\u001B[101m"),
    BRIGHT_GREEN_BACKGROUND("\u001B[102m"),
    BRIGHT_YELLOW_BACKGROUND("\u001B[103m"),
    BRIGHT_BLUE_BACKGROUND("\u001B[104m"),
    BRIGHT_PURPLE_BACKGROUND("\u001B[105m"),
    BRIGHT_CYAN_BACKGROUND("\u001B[106m"),
    BRIGHT_WHITE_BACKGROUND("\u001B[107m"),

    // Reset
    RESET("\u001B[0m");
    //In game Colors

    public static String fg(int code) {
        return "\u001B[38;5;" + code + "m";
    }

    public static String bg(int code) {
        return "\u001B[48;5;" + code + "m";
    }

    private final String color;

    Colors(String color) {
        this.color = color;
    }

    public String toString() {
        return color;
    }
}

