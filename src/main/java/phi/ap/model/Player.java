package phi.ap.model;

import phi.ap.model.enums.AbilityType;
import phi.ap.model.enums.CraftingTypes;
import phi.ap.model.enums.FarmTypes;
import phi.ap.model.enums.FoodTypes;
import phi.ap.model.enums.npcStuff.Quests;
import phi.ap.model.items.buildings.AnimalHouse;
import phi.ap.model.items.buildings.Farm;
import phi.ap.model.items.Animal;
import phi.ap.model.items.products.Product;
import phi.ap.model.items.products.Recipe;
import phi.ap.model.items.relations.MarriageRequest;
import phi.ap.model.items.relations.TradeRequest;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private User user;
    private int gold = 0;
    private final EnergyManager energy = new EnergyManager(this);
    private ToolManager toolManager = new ToolManager();
    private String color = "";

    private ArrayList<Product> artisanItems = new ArrayList<>();
    private ArrayList<Ability> abilities = new ArrayList<>();
    private ArrayList<AnimalHouse> ownedAnimalHouse;
    private ArrayList<Recipe> craftingRecipes = new ArrayList<>();
    private ArrayList<Recipe> cookingRecipes = new ArrayList<>();
    private ArrayList<Animal> animals = new ArrayList<>();
    private ArrayList<Gift> giftsReceived;
    private ArrayList<MarriageRequest> marriageRequests;
    private ArrayList<TradeRequest> tradeRequests;
    private FarmTypes farmType = null;
    private Farm farm = null;
    private Location location;
    private Player partner = null;
    private final ArrayList<Quests> activeQuests = new ArrayList<>();
    private final ArrayList<Quests> doneQuests = new ArrayList<>();
    private final Buff lastFoodBuff = new Buff(0, 0);
    private final Buff feintBuff = new Buff(1, 0.75);
    private final Buff marrageBuff = new Buff(7, 0.5);

    public boolean isPlayerFeinted() {
        return Game.getInstance().getCurrentPlayer().getFeintBuff().isActive() &&
                Game.getInstance().getCurrentPlayer().getFeintBuff().getStartTime().getRawDay() ==
                        Game.getInstance().getDate().getRawDay();
    }

    public void setFoodBuff(Buff buff) {
        lastFoodBuff.copy(buff);
    }

    public Buff getLastFoodBuff() {
        return lastFoodBuff;
    }

    public Buff getFeintBuff() {
        return feintBuff;
    }

    public Buff getMarrageBuff() {
        return marrageBuff;
    }

    public ArrayList<Quests> getActiveQuests() {
        return activeQuests;
    }

    public ArrayList<Quests> getDoneQuests() {
        return doneQuests;
    }

    public Player getPartner() {
        return partner;
    }

    public void setPartner(Player partner) {
        this.partner = partner;
    }


    public ArrayList<Product> getArtisanItems() {
        return artisanItems;
    }

    public void setArtisanItems(ArrayList<Product> artisanItems) {
        this.artisanItems = artisanItems;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public int getSelfGold() {
        return this.gold;
    }
    public int getGold() {
        if(partner == null)
            return this.gold;
        else
            return this.gold + partner.getSelfGold();
    }

    public void setSelfGold(int gold) {
        this.gold = gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
        partner.setSelfGold(0);
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
    public void InitializePlayer() {
        cookingRecipes.add(FoodTypes.FriedEgg.getRecipe());
        cookingRecipes.add(FoodTypes.Salad.getRecipe());
        cookingRecipes.add(FoodTypes.BakedFish.getRecipe());
        craftingRecipes.add(CraftingTypes.Furnace.getRecipe());
        craftingRecipes.add(CraftingTypes.Scarecrow.getRecipe());
        craftingRecipes.add(CraftingTypes.MayonnaiseMachine.getRecipe());
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



    public void setUser(User user) {
        this.user = user;
    }

    public void addCraftingRecipe(Recipe recipe) {
        craftingRecipes.add(recipe);
    }

    public void addCookingRecipe(Recipe recipe) {
        cookingRecipes.add(recipe);
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


    public void feint() {
        feintBuff.activate();
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
        this.getToolManager().addBackpack();
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
    public Ability getAbility(AbilityType abilityType){
        for(Ability ability : abilities)
            if(ability.getAbilityType() == abilityType)
                return ability;
        return null; // this can not happen
    }
    public boolean isAbilityMax(AbilityType abilityType) {
        return getAbilityLevel(abilityType) == AbilityType.MAX_VALUE;
    }

    public boolean CheckExistenceCraftingRecipe(Recipe rec) {
        for(Recipe recipe : craftingRecipes){
            if(recipe.getRecipeType().equals(rec.getRecipeType()))
                return true;
        }
        return false;
    }
    public boolean CheckExistenceCookingRecipe(Recipe rec) {
        for(Recipe recipe : cookingRecipes){
            if(recipe.getRecipeType().equals(rec.getRecipeType()))
                return true;
        }
        return false;
    }

    public boolean isQuestActive(Quests quest) {
        return activeQuests.contains(quest);
    }
    public boolean isQuestDone(Quests quest) {
        return doneQuests.contains(quest);
    }
    public boolean isQuestActivatedSoFar(Quests quest) {
        return isQuestActive(quest) || isQuestDone(quest);
    }
    public void activateQuest(Quests quest) {
        activeQuests.add(quest);
    }
}