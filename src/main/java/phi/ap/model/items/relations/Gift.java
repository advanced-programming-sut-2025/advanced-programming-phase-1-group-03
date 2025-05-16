package phi.ap.model.items.relations;

import phi.ap.model.ItemStack;
import phi.ap.model.Player;

import java.util.ArrayList;

public class Gift {

    private static ArrayList<Gift> gifts;
    private static Integer allId = 0;
    private Integer id;
    private Integer rate = null;
    private ItemStack itemStack;
    private String notification;
    private Player sender;
    private Player getter;
    private Boolean haveSeen;

    Gift(ItemStack itemStack, String notification, Player sender, Player getter) {
        this.itemStack = itemStack;
        this.notification = notification;
        this.sender = sender;
        this.getter = getter;
        this.haveSeen = false;
        this.id = allId++;
    }

    public static ArrayList<Gift> getReceivedGifts(Player player) {
        ArrayList<Gift> tempGift = new ArrayList<>();
        for(Gift gift : gifts) {
            if(gift.getGetter().equals(player))
                tempGift.add(gift);
        }
        return tempGift;
    }

    public static ArrayList<Gift> getSentGifts(Player player) {
        ArrayList<Gift> tempGift = new ArrayList<>();
        for(Gift gift : gifts) {
            if(gift.getSender().equals(player))
                tempGift.add(gift);
        }
        return tempGift;
    }

    public String printGift() {
        StringBuilder stringBuilder = new StringBuilder();
        if(rate == null)
            stringBuilder.append("Sender: " + sender.getUser().getUsername() + " Item: " + itemStack.getItem().getName() + " Amount: " + itemStack.getAmount() + " Rate: Don't have rate.");
        else
            stringBuilder.append("Sender: " + sender.getUser().getUsername() + " Item: " + itemStack.getItem().getName() + " Amount: " + itemStack.getAmount() + " Rate: " + rate);
        return stringBuilder.toString();
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
        haveSeen = true;
        Friendship.AddXp(sender, getter, (rate - 3) * 30 + 15);
    }

    public static Gift getGiftById(int id) {
        for(Gift gift : gifts) {
            if(gift.getId() == id)
                return gift;
        }
        return null;
    }

    public static ArrayList<Gift> getGifts() {
        return gifts;
    }

    public static void setGifts(ArrayList<Gift> gifts) {
        Gift.gifts = gifts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getGetter() {
        return getter;
    }

    public void setGetter(Player getter) {
        this.getter = getter;
    }

    public Boolean getHaveSeen() {
        return haveSeen;
    }

    public void setHaveSeen(Boolean haveSeen) {
        this.haveSeen = haveSeen;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

}
