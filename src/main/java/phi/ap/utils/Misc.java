package phi.ap.utils;

import phi.ap.model.Coordinate;

public class Misc {
    /*
    0 1 2
    3   4
    5 6 7
     */
    public static Coordinate getDiffFromDirection(int direction){
        int[] dx = {-1, 0, +1, -1, +1, -1, 0, +1};
        int[] dy = {-1, -1, -1, 0, 0, +1, +1, +1};
        return new Coordinate(dy[direction], dx[direction]);
    }
}