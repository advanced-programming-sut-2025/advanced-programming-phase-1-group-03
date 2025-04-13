package phi.ap.model.items.relations;

import phi.ap.model.ItemStack;

public class TradeRequest {
    private static int lastId = 0;
    int id;
    String type;
    ItemStack item;
    ItemStack targetItem;
    int price;
    String answer;

    public TradeRequest(String type, ItemStack item, ItemStack targetItem, int price) {
        this.type = type;
        this.item = item;
        this.targetItem = targetItem;
        this.price = price;
        ++lastId;
        this.id = lastId;
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

    public int getPrice() {
        return price;
    }

    public String getAnswer() {
        return answer;
    }
}
