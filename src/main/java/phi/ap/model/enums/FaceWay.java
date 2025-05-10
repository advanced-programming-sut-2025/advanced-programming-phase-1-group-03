package phi.ap.model.enums;

import static java.util.Collections.swap;

public enum FaceWay {
    Up("▲"),
    Right("►"),
    Down("▼"),
    Left("◄")
    ;

    private final String symbol;

    FaceWay(String symbol) {
        this.symbol = symbol;
    }

    public int getDistance(FaceWay faceWay) {
        int mn = Math.min(this.ordinal(), faceWay.ordinal());
        int mx = Math.max(this.ordinal(), faceWay.ordinal());
        return Math.min(Math.abs(mx - mn), Math.abs(mx - mn - FaceWay.values().length));
    }

    public String getSymbol() {
        return symbol;
    }
}
