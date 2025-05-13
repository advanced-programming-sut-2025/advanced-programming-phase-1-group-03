package phi.ap.model;

import phi.ap.model.enums.AbilityType;
import phi.ap.model.enums.FarmTypes;
import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.AnimalHouse;
import phi.ap.model.items.buildings.Farm;
import phi.ap.model.items.Animal;
import phi.ap.model.items.products.Product;
import phi.ap.model.items.products.Recipe;
import phi.ap.model.items.relations.MarriageRequest;
import phi.ap.model.items.relations.Quest;
import phi.ap.model.items.relations.TradeRequest;

import java.util.ArrayList;
import java.util.List;

public class Player extends Human {
    private User user;
    private int gold = Integer.MAX_VALUE; //TODO is it actually needed?
    private final EnergyManager energy = new EnergyManager();
    private ToolManager toolManager = new ToolManager();


    private ArrayList<Product> artisanItems = new ArrayList<>();
    private ArrayList<Ability> abilities = new ArrayList<>();
    private ArrayList<AnimalHouse> ownedAnimalHouse;
    private ArrayList<Recipe> craftingRecipes = new ArrayList<>();
    private ArrayList<Recipe> cookingRecipes = new ArrayList<>();
    private ArrayList<Animal> animals = new ArrayList<>();
    private ArrayList<Gift> giftsReceived;
    private ArrayList<MarriageRequest> marriageRequests;
    private ArrayList<TradeRequest> tradeRequests;
    private ArrayList<Quest> activatedQuests;
    private FarmTypes farmType = null;
    private Farm farm = null;
    private Location location;

    public ArrayList<Product> getArtisanItems() {
        return artisanItems;
    }

    public void setArtisanItems(ArrayList<Product> artisanItems) {
        this.artisanItems = artisanItems;
    }

    public Recipe getRecipe(String name) {
        for(Recipe recipe : Game.getInstance().getCurrentPlayer().getCookingRecipes()) {
            if(recipe.getName().equals(name)) {
                return recipe;
            }
        }
        return null;
    }

    public Animal getAnimalByName(String name) {
        for(Animal animal : animals) {
            if(animal.getName().equals(name))
                return animal;
        }
        return null;
    }

    public ArrayList<AnimalHouse> getOwnedAnimalHouse() {
        return ownedAnimalHouse;
    }

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

    public InventoryManager getInventoryManager() {
        return new InventoryManager(getToolManager().getBackpack(), getToolManager().getTrashCan());
    }

    public EnergyManager getEnergy() {
        return energy;
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
        //TODO feint
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

    //This method create default tools
    public void init() {
        this.getToolManager().createDefaultTools();
        this.getToolManager().addDefaultTools(this.getInventoryManager());
        this.ownedAnimalHouse = new ArrayList<>();
        this.abilities = new ArrayList<>(List.of(new Ability(AbilityType.Farming), new Ability(AbilityType.Extraction),
                new Ability(AbilityType.Foraging),new Ability(AbilityType.Fishing)));
    }

    public int getAbilityLevel(AbilityType abilityType) {
        for(Ability ability : abilities)
            if(ability.getAbilityType() == abilityType)
                return ability.getLevel();
        return -1; // this can not happen
    }
    public boolean isAbilityMax(AbilityType abilityType) {
        return getAbilityLevel(abilityType) == AbilityType.MAX_VALUE;
    }
}