package phi.ap.model.items.relations;

import phi.ap.model.Player;

public class Friendship {
    private final Player player1;
    private final Player player2;
    private int xp;

    public Friendship(Player player1, Player player2, int xp) {
        this.player1 = player1;
        this.player2 = player2;
        this.xp = xp;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
