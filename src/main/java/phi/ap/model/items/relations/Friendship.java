package phi.ap.model.items.relations;

import phi.ap.model.Human;
import phi.ap.model.Human;

import java.util.ArrayList;

public class Friendship {
    private static ArrayList<Friendship> friendships;
    private final Human human1;
    private final Human human2;
    private int xp;
    private int level;

    public Friendship(Human human1, Human human2, int xp) {
        this.human1 = human1;
        this.human2 = human2;
        this.xp = xp;
        friendships.add(this);
    }

    public Human getHuman1() {
        return human1;
    }

    public Human getHuman2() {
        return human2;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public static ArrayList<Friendship> getFriendships() {
        return friendships;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
