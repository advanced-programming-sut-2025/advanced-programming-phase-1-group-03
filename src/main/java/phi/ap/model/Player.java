package phi.ap.model;

import phi.ap.model.items.producers.Animal;
import phi.ap.model.items.products.Recipe;
import phi.ap.model.items.relations.MarriageRequest;
import phi.ap.model.items.relations.TradeRequest;
import phi.ap.model.items.tools.Tool;

import java.util.ArrayList;

public class Player {
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
}
