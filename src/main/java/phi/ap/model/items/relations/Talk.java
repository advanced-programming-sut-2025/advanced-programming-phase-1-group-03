package phi.ap.model.items.relations;

import phi.ap.model.Player;

public class Talk {

    private final String message;
    private Friendship friendship;


    private Player sender;
    Talk(String message, Friendship friendship, Player player) {
        this.message = message;
        this.friendship = friendship;
        this.sender = player;
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