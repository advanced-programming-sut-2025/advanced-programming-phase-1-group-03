package phi.ap.model.items.relations;

import phi.ap.model.*;
import phi.ap.model.enums.ProductNames;
import phi.ap.model.items.products.Product;

import java.util.ArrayList;

public class Friendship {
    private static ArrayList<Friendship> friendships = new ArrayList<>();
    private ArrayList<Talk> talks = new ArrayList<>();
    private ArrayList<Gift> player1Gift = new ArrayList<>();
    private ArrayList<Gift> player2Gift = new ArrayList<>();
    private final Player player1;
    private final Player player2;
    private int xp;
    private int level;


    private MarriageRequest marriageRequest = null;
    Boolean isHugged = false;


    Boolean haveTalked = false;
    Boolean flower = false;


    Boolean married = false;

    public Friendship(Player player1, Player player2, int xp) {
        this.player1 = player1;
        this.player2 = player2;
        this.xp = xp;
        friendships.add(this);
    }

    public Boolean getMarried() {
        return married;
    }

    public void askMarriage(Player applicant, Player respondent, Product ring) {
        if(applicant == null || respondent == null)
            return;
        marriageRequest = new MarriageRequest(applicant, respondent, ring);
    }

    public void respondMarriage(Player applicant, Player respondent, Boolean accept) {
        if(applicant == null || respondent == null)
            return;
        if(accept) {
            applicant.setPartner(respondent);
            respondent.setPartner(applicant);
            applicant.getInventoryManager().removeItem(new Product(ProductNames.WeddingRing), 1);
            respondent.getInventoryManager().addItem(new Product(ProductNames.WeddingRing), 1);
            married = true;
            checkLevel();
        }
        else {
            setLevel(0);
            setXp(0);
            // TODO energy pesar baraye 7 rooz nesf she.
        }
        marriageRequest = null;
    }

    public MarriageRequest getMarriageRequest() {
        return marriageRequest;
    }

    public void setMarriageRequest(MarriageRequest marriageRequest) {
        this.marriageRequest = marriageRequest;
    }

    public ArrayList<Gift> getPlayer1Gift() {
        return player1Gift;
    }

    public void setPlayer1Gift(ArrayList<Gift> player1Gift) {
        this.player1Gift = player1Gift;
    }

    public ArrayList<Gift> getPlayer2Gift() {
        return player2Gift;
    }

    public void setHaveTalked(Boolean haveTalked) {
        this.haveTalked = haveTalked;
    }

    public void setFlower(Boolean flower) {
        this.flower = flower;
    }

    public void setHugged(Boolean hugged) {
        isHugged = hugged;
    }

    public void setPlayer2Gift(ArrayList<Gift> player2Gift) {
        this.player2Gift = player2Gift;
    }

    public static ArrayList<Friendship> getFriends(Player player) {
        ArrayList<Friendship> friendships1 = new ArrayList<>();
        for(Friendship friendship : friendships) {
            if(friendship.getPlayer1().equals(player) || friendship.getPlayer2().equals(player))
                friendships1.add(friendship);
        }
        return friendships1;
    }

    public static Friendship getFriendShip(Player firstPlayer, Player secondPlayer) {
        if(firstPlayer == null || secondPlayer == null)
            return null;
        for(Friendship friendship : friendships) {
            if((friendship.getPlayer1().equals(firstPlayer) || friendship.getPlayer1().equals(secondPlayer))
            && (friendship.getPlayer2().equals(firstPlayer) || friendship.getPlayer2().equals(secondPlayer))) {
                return friendship;
            }
        }
        return null;
    }

    public void GiftPlayer(Player senderPlayer, Player getterPlayer, ItemStack itemStack) {
        Friendship friendship = getFriendShip(senderPlayer, getterPlayer);
        if(friendship == null)
            return;
        String notification = "You have received " + itemStack.getItem().getName() + " with amount " + itemStack.getAmount() + " from " + senderPlayer.getUser().getUsername();
        Gift gift = new Gift(itemStack, notification, senderPlayer, getterPlayer);
        if(friendship.getPlayer1().equals(getterPlayer))
            player1Gift.add(gift);
        if(friendship.getPlayer2().equals(getterPlayer))
            player2Gift.add(gift);
        //System.out.println("!!!");
        Gift.getGifts().add(gift);
        getterPlayer.getInventoryManager().addItem(itemStack);
        senderPlayer.getInventoryManager().removeItem(itemStack.getItem(), itemStack.getAmount());
    }

    public static void AddXp(Player firstPlayer, Player secondPlayer, int amount) {
        if(firstPlayer == null || secondPlayer == null)
            return;
        Friendship friendship = Friendship.getFriendShip(firstPlayer, secondPlayer);
        if(friendship == null)
            return;
        friendship.friendShipAddXp(amount);
    }

    public void friendShipAddXp(int amount) {
        this.xp += amount;
        if(xp < 0) {
            level = Math.min(0, level - 1);
            xp = (level + 1)*100 + xp;
        }
        checkLevel();
    }

    private void checkLevel() {
        if(level == 3) {
            if(xp >= (level + 1) * 100 && married)
                level++;
        }
        else if(level == 2) {
            if(xp >= (level + 1) * 100 && flower)
                level++;
        }
        else if(xp >= (level + 1) * 100) {
            xp -= (level + 1) * 100;
            level++;
        }
    }

    public void addTalk(Player sender, Friendship friendship, String message) {
        talks.add(new Talk(message, friendship, sender));
        if(!haveTalked)
            friendShipAddXp(20);
        haveTalked = true;
    }

    public void hug() {
        if(!isHugged)
            friendShipAddXp(60);
        isHugged = true;
    }

    public void giveFlower() {
        flower = true;
        checkLevel();
    }
    public ArrayList<Talk> getTalk() {
        return this.talks;
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
