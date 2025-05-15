package phi.ap.model.items.relations;

import phi.ap.model.ItemStack;
import phi.ap.model.Player;

import java.util.ArrayList;

public class TradeRequest {

    private static ArrayList<TradeRequest> trades;
    private Player sender;
    private Player getter;
    private static int lastId = 0;
    private int id;
    private String type;
    private ItemStack item;
    private ItemStack targetItem;
    private Integer price = null;
    private Boolean seen = false;


    private String answer = null;

    public TradeRequest(String type, ItemStack item, ItemStack targetItem, Integer price, Player sender, Player getter) {
        this.type = type;
        this.item = item;
        this.targetItem = targetItem;
        this.price = price;
        ++lastId;
        this.id = lastId;
        this.sender = sender;
        this.getter = getter;
    }

    public static String getUnSeenTradeList(Player player) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not answered trade list: \n");
        for(TradeRequest tradeRequest : trades) {
            if(tradeRequest.getAnswer() == null) {
                if(tradeRequest.getGetter().equals(player) && tradeRequest.seen == false) {
                    stringBuilder.append(printTrade(tradeRequest) + "\n");
                    tradeRequest.seen = true;
                }
            }
        }
        return stringBuilder.toString();
    }

    public static String printTradeHistory(Player player) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not answered trade list: \n");
        for(TradeRequest tradeRequest : trades) {
            if(tradeRequest.getGetter().equals(player)) {
                stringBuilder.append(printTrade(tradeRequest) + "Answer: " + tradeRequest.getAnswer());
            }
        }
        return stringBuilder.toString();
    }

    public static String getTradeList(Player player) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not answered trade list: \n");
        for(TradeRequest tradeRequest : trades) {
            if(tradeRequest.getAnswer() == null) {
                if(tradeRequest.getGetter().equals(player)) {
                    stringBuilder.append(printTrade(tradeRequest) + "\n");
                }
            }
        }
        return stringBuilder.toString();
    }

    public static String printTrade(TradeRequest tradeRequest) {
        StringBuilder stringBuilder = new StringBuilder();
        if(tradeRequest.getPrice() == null)
            stringBuilder.append("Trade id: " + tradeRequest.getId() + " From: " + tradeRequest.getSender() +
                    "\n" + tradeRequest.getType() + "s Item :" +
                    tradeRequest.getItem().getItem().getName() + " Amount: " +
                    tradeRequest.getItem().getAmount() + "\n for Item: " +
                    tradeRequest.getTargetItem().getItem().getName() +
                    tradeRequest.getTargetItem().getAmount());
        else
            stringBuilder.append("Trade id: " + tradeRequest.getId() + " From: " + tradeRequest.getSender() +
                    "\n" + tradeRequest.getType() + "s Item :" +
                    tradeRequest.getItem().getItem().getName() + " Amount: " +
                    tradeRequest.getItem().getAmount() + "\n for " + tradeRequest.getPrice());
        return stringBuilder.toString();
    }

    public static TradeRequest getTradeById(int id) {
        for(TradeRequest tradeRequest : trades) {
            if(id == tradeRequest.getId())
                return tradeRequest;
        }
        return null;
    }

    public Player getSender() {
        return sender;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Player getGetter() {
        return getter;
    }

    public void setGetter(Player getter) {
        this.getter = getter;
    }

    public static ArrayList<TradeRequest> getTrades() {
        return trades;
    }

    public static void setTrades(ArrayList<TradeRequest> trades) {
        TradeRequest.trades = trades;
    }

    public static int getLastId() {
        return lastId;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemStack getTargetItem() {
        return targetItem;
    }

    public Integer getPrice() {
        return price;
    }

    public String getAnswer() {
        return answer;
    }
}
