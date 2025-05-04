package phi.ap.model.enums;

import static java.util.Collections.swap;

public enum FaceWay {
    Up,
    Right,
    Down,
    Left
    ;

    public static final int turningConst = 10;
    public int getDistance(FaceWay faceWay) {
        int mn = Math.min(this.ordinal(), faceWay.ordinal());
        int mx = Math.max(this.ordinal(), faceWay.ordinal());
        return Math.min(Math.abs(mx - mn), Math.abs(mx - mn - FaceWay.values().length));
    }
}
