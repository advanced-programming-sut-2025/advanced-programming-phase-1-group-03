package phi.ap.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements Command{
    NewGame("(\\s*game\\s+new\\s+|\\s*new\\s+game\\s+)-u\\s*" +
            "(?<usernames>.*)\\s*"),
    GameMap("\\s*game\\s+map\\s+(?<mapNumber>\\d+)\\s*"),
    PrintMap("\\s*print\\s+map\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s+-s\\s+(?<size>\\d+)\\s*"),
    PrintMapComplete("(\\s*print\\s+map\\s+complete\\s*|pmc)"),
    Walk("\\s*walk\\s+-l\\s+(?<x>-?\\d+)(\\s+|\\s*,\\s*)(?<y>-?\\d+)\\s*"),
    WalkDiff("\\s*walk\\s+-diff\\s+(?<x>-?\\d+)(\\s+|\\s*,\\s*)(?<y>-?\\d+)\\s*"),
    NextTurn("\\s*next\\s+turn\\s*"),
    Time("\\s*time\\s*"),
    Date("\\s*date\\s*"),
    Season("\\s*season\\s*"),
    DateTime("\\s*datetime\\s*"),
    DayOfWeek("\\s*day\\s*of\\s*the\\s*week\\s*"),
    CheatAdvanceTime("\\s*cheat\\s*advance\\s*time\\s*(?<hour>\\d+)\\s*h\\s*"),
    CheatAdvanceDate("\\s*cheat\\s*advance\\s*date\\s*(?<day>\\d+)\\s*d\\s*"),
    showAllProducts("\\s*show\\s+all\\s+products\\s*"),
    showAvailableProducts("\\s*show\\s+all\\s+available\\s+products\\s*"),
    purchase("\\s*purchase\\s+(?<productName>.*)\\s*(-n\\s+(?<amount>\\d+))?\\s*"),
    Build("\\s*build\\s+-a\\s+(?<name>.*)\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*"),
    ShowCurrentTool("\\s*tools\\s+show\\s+current\\s*"),
    ShowAvailableTools("\\s*tools\\s+show\\s+available\\s*"),
    EquipTool("\\s*tools\\s+equip\\s*(?<toolName>.*)\\s*"),
    UseTool("\\s*tools\\s+use\\s+-d\\s+(?<direction>\\d)\\s*"),
    BuyAnimal("^\\s*buy\\s+animal\\s+-a\\s+(?<animalName>\\S+)\\s+-n\\s+(?<name>\\S+)\\s*$"),
    Pet("\\s*pet\\s+-n\\s+(?<name>.*)\\s*"),
    Animals("\\s*animals\\s*"),
    FeedHay("\\s*feed\\s+hay\\s+-n\\s+(?<name>.*)\\s*"),
    Produces("\\s*produces\\s*"),
    CollectProduce("\\s*collect\\s+produce\\s+-n\\s+(?<name>.*)\\s*"),
    SellAnimal("\\s*sell\\s+animal\\s+-n\\s+(?<name>.*)\\s*"),
    test("\\s*test(\\s*|\\s+.*)"),
    test1("\\s*test1\\s*(\\s*|\\s+.*)"),
    WalkOne("\\s*walk\\s+-one\\s+(?<x>-?\\d+)(\\s+|\\s*,\\s*)(?<y>-?\\d+)\\s*"),
    PutRefrigerator("\\s*cooking\\s+refrigerator\\s+put\\s+(?<name>.*)\\s+(?<amount>\\d+)\\s*"),
    PickRefrigerator("\\s*cooking\\s+refrigerator\\s+pick\\s+(?<name>.*)\\s+(?<amount>\\d+)\\s*"),
    ShowCookingRecipes("\\s*cooking\\s+show\\s+recipes\\s*"),
    PrepareCooking("\\s*cooking\\s+prepare\\s+(?<name>.*)\\s*"),
    EatFood("\\s*eat\\s+(?<name>.*)\\s*"),
    Weather("\\s*weather\\s*"),
    WeatherForecast("\\s*weather\\s*forecast\\s*"),
    CheatWeatherSet("\\s*cheat\\s*weather\\s*set\\s+(?<weatherName>\\S+)\\s*"),
    ShowCraftingRecipes("\\s*crafting\\s+show\\s+recipes\\s*"),
    Craft("\\s*crafting\\s+craft\\s+(?<name>.*)\\s*"),
    CraftInfo("\\s*craft\\s*info\\s+-n\\s+(?<name>.*)\\s*"),
    UseArtisan("\\s*artisan\\s+use\\s+(?<name>\\S+)\\s+(?<itemName>\\S+)\\s*(?<ingredientName>\\S+)?\\s*"),
    GetArtisan("\\s*artisan\\s+get\\s+(?<name>\\S+)\\s*"),
    CheatAddItem("\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<name>.*)\\s+-c\\s+(?<amount>\\d+)\\s*"),
    Plant("\\s*plant\\s+-s\\s+(?<source>.*)\\s+-d\\s+(?<direction>\\d)\\s*"),
    ShowPlant("\\s*show\\s*plant\\s+-l\\s+(?<x>-?\\d+)(\\s+|\\s*,\\s*)(?<y>-?\\d+)\\s*"),
    Fertilize("\\s*fertilize\\s+-f\\s+(?<name>.*)\\s+-d\\s+(?<direction>\\d)\\s*"),
    HowMuchWater("\\s*how\\s+much\\s+water\\s*"),

    ;
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
