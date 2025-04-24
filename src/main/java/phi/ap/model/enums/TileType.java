package phi.ap.model.enums;

public enum TileType {
    Farm('#'),
    Lake('~'),
    Cottage('^'),
    Greenhouse('G'),
    Quarry('Q'),
    Ground('.');
    private final Character symbol;

    TileType(Character symbol) {
        this.symbol = symbol;
    }

    public Character getSymbol() {
        return symbol;
    }
}
