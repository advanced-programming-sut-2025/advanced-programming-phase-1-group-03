package phi.ap.model.items.relations;

import phi.ap.model.Player;

public class Talk {
    private final String message;
    private Friendship friendship;
    private boolean haveSeen;
    private Player sender;

    Talk(String message, Friendship friendship, Player player) {
        this.message = message;
        this.friendship = friendship;
        this.sender = player;
        haveSeen = false;
    }

    public Boolean getHaveSeen() {
        return haveSeen;
    }

    public void setHaveSeen(boolean haveSeen) {
        this.haveSeen = haveSeen;
    }

    public String getMessage() {
        return message;
    }

    public Friendship getFriendship() {
        return friendship;
    }

    public Player getSender() {
        return sender;
    }

}