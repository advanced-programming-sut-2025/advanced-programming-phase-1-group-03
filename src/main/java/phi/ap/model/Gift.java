package phi.ap.model;

import phi.ap.model.items.Item;

public class Gift {
    private static int lastId = 0;
    private final int id;
    private int rate;
    private Player sender;
    private ItemStack gift;

    public Gift(int rate, Player sender, ItemStack gift) {
        this.rate = rate;
        this.sender = sender;
        this.gift = gift;
        lastId++;
        this.id = lastId;
    }

    public static int getLastId() {
        return lastId;
    }

    public int getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public Player getSender() {
        return sender;
    }

    public ItemStack getGift() {
        return gift;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public void setGift(ItemStack gift) {
        this.gift = gift;
    }
}
