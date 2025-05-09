package phi.ap.model;

import phi.ap.model.enums.FarmTypes;
import phi.ap.model.items.buildings.Farm;
import phi.ap.model.items.producers.Animal;
import phi.ap.model.items.products.Recipe;
import phi.ap.model.items.relations.MarriageRequest;
import phi.ap.model.items.relations.Quest;
import phi.ap.model.items.relations.TradeRequest;
import phi.ap.model.items.tools.Tool;

import java.util.ArrayList;

public class Player extends Human {
    private User user;
    private int gold;
    private int Energy = Integer.MAX_VALUE;
    private ToolManager toolManager = new ToolManager();
    private ArrayList<Recipe> craftingRecipes;
    private ArrayList<Recipe> cookingRecipes;
    private ArrayList<Animal> animals;
    private ArrayList<Gift> giftsReceived;
    private ArrayList<MarriageRequest> marriageRequests;
    private ArrayList<TradeRequest> tradeRequests;
    private ArrayList<Quest> activatedQuests;
    private FarmTypes farmType = null;
    private Farm farm = null;
    private Location location;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Player(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getEnergy() {
        return Energy;
    }

    public InventoryManager getInventoryManager() {
        return new InventoryManager(getToolManager().getBackpack(), getToolManager().getTrashCan());
    }

    public ArrayList<Recipe> getCraftingRecipes() {
        return craftingRecipes;
    }

    public ArrayList<Recipe> getCookingRecipes() {
        return cookingRecipes;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public ArrayList<Gift> getGiftsReceived() {
        return giftsReceived;
    }

    public ArrayList<MarriageRequest> getMarriageRequests() {
        return marriageRequests;
    }

    public ArrayList<TradeRequest> getTradeRequests() {
        return tradeRequests;
    }

    public ArrayList<Quest> getActivatedQuests() {
        return activatedQuests;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEnergy(int energy) {
        Energy = energy;
    }


    public void setCraftingRecipes(ArrayList<Recipe> craftingRecipes) {
        this.craftingRecipes = craftingRecipes;
    }

    public void setCookingRecipes(ArrayList<Recipe> cookingRecipes) {
        this.cookingRecipes = cookingRecipes;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public void setGiftsReceived(ArrayList<Gift> giftsReceived) {
        this.giftsReceived = giftsReceived;
    }

    public void setMarriageRequests(ArrayList<MarriageRequest> marriageRequests) {
        this.marriageRequests = marriageRequests;
    }

    public void setTradeRequests(ArrayList<TradeRequest> tradeRequests) {
        this.tradeRequests = tradeRequests;
    }

    public void setActivatedQuests(ArrayList<Quest> activatedQuests) {
        this.activatedQuests = activatedQuests;
    }

    public void feint() {

    }

    public FarmTypes getFarmType() {
        return farmType;
    }

    public void setFarmType(FarmTypes farmType) {
        this.farmType = farmType;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Player player))
            return false;
        return player.getUser().equals(user);
    }

    @Override
    public String toString() {
        return getUser().getUsername();
    }

    public ToolManager getToolManager() {
        return toolManager;
    }
}