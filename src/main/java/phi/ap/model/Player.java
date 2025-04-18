package phi.ap.model;

import phi.ap.model.items.producers.Animal;
import phi.ap.model.items.products.Recipe;
import phi.ap.model.items.relations.Friendship;
import phi.ap.model.items.relations.MarriageRequest;
import phi.ap.model.items.relations.Quest;
import phi.ap.model.items.relations.TradeRequest;
import phi.ap.model.items.tools.Tool;

import java.util.ArrayList;

public class Player extends Human {
    private User user;
    private int Energy;
    private Coordinate Coordinate;
    private Tool tool;
    private InventoryManager inventoryManager;
    private ArrayList<Recipe> craftingRecipes;
    private ArrayList<Recipe> cookingRecipes;
    private ArrayList<Animal> animals;
    private ArrayList<Gift> giftsReceived;
    private ArrayList<MarriageRequest> marriageRequests;
    private ArrayList<TradeRequest> tradeRequests;
    private ArrayList<Quest> activatedQuests;

    public Player(User user, int energy, Coordinate coordinate, Tool tool, InventoryManager inventoryManager,
                  ArrayList<Recipe> craftingRecipes, ArrayList<Recipe> cookingRecipes, ArrayList<Animal> animals,
                  ArrayList<Gift> giftsReceived, ArrayList<MarriageRequest> marriageRequests, ArrayList<TradeRequest>
                          tradeRequests, ArrayList<Quest> activatedQuests) {
        this.user = user;
        Energy = energy;
        Coordinate = coordinate;
        this.tool = tool;
        this.inventoryManager = inventoryManager;
        this.craftingRecipes = craftingRecipes;
        this.cookingRecipes = cookingRecipes;
        this.animals = animals;
        this.giftsReceived = giftsReceived;
        this.marriageRequests = marriageRequests;
        this.tradeRequests = tradeRequests;
        this.activatedQuests = activatedQuests;
    }

    public User getUser() {
        return user;
    }

    public int getEnergy() {
        return Energy;
    }

    public Coordinate getCoordinate() {
        return Coordinate;
    }

    public Tool getTool() {
        return tool;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
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

    public void setCoordinate(Coordinate coordinate) {
        Coordinate = coordinate;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
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
}
