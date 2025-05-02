package phi.ap.model.enums;

public enum TileType {
    Farm(Colors.bg(3) + "."),
    Lake(Colors.bg(19) + "~"),
    Cottage(Colors.bg(22) + "^"),
    Greenhouse(Colors.bg(72) + "G"),
    Quarry(Colors.bg(240) + "Q"),
    Ground(".");
    private final String symbol;

    TileType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return Colors.BLACK_BACKGROUND + symbol + Colors.RESET;
    }
}
