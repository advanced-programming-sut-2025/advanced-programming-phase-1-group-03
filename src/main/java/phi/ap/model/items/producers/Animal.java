package phi.ap.model.items.producers;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.AnimalTypes;
import phi.ap.model.enums.LevelName;
import phi.ap.model.items.products.AnimalProduct;
import phi.ap.model.items.relations.Friendship;

import java.util.ArrayList;

public class Animal extends Producer {
    private AnimalTypes type;
    private int friendShipAmount = 0;



    private int remainingDayToProduce;
    private boolean beenPet = false;
    private boolean inHome = true;
    private boolean feeded = false;
    private ArrayList<AnimalProduct> animalProducts;

    public Animal(AnimalTypes animalType, int height, int width) {
        super(height, width);
        this.type = animalType;
        this.remainingDayToProduce = this.type.getDayToProduce();
    }

    public void setRemainingDayToProduce() {
        this.remainingDayToProduce = type.getDayToProduce();
    }

    public void reduceRemainingDayToProduce() {
        this.remainingDayToProduce--;
    }

    public int getRemainingDayToProduce() {
        return remainingDayToProduce;
    }

    public int getFriendShipAmount() {
        return friendShipAmount;
    }

    public ArrayList<AnimalProduct> getAnimalProducts() {
        return animalProducts;
    }

    public void addFriendShip(int amount) {
        friendShipAmount += amount;
    }

    public boolean getIsFeeded() {
        return feeded;
    }

    public boolean getIsInHome() {
        return inHome;
    }

    public boolean getIsBeenPet() {
        return beenPet;
    }

    public void setFeeded(boolean feeded) {
        this.feeded = feeded;
    }

    public void setBeenPet(boolean beenPet) {
        this.beenPet = beenPet;
    }

    public void setInHome(boolean inHome) {
        this.inHome = inHome;
    }

    public ItemStack feedAnimal(ItemStack stack) {
        return null;
    }


    public AnimalTypes getType() {
        return type;
    }

    public void setType(AnimalTypes type) {
        this.type = type;
    }
    @Override
    public void produce() {

    }

    public AnimalProduct produceProduct() {
        if(!getIsFeeded())
            return null;
        double randomDouble = 0.5 + Math.random();
        double quality = (friendShipAmount/1000)*(0.5 + 0.5 * Math.random());
        LevelName levelName;
        double productNum;
        if(quality <= 0.5) {
            levelName = LevelName.normal;
            productNum = 1;
        }
        else if(quality <= 0.7) {
            levelName = LevelName.silver;
            productNum = 1.25;
        }
        else if(quality <= 0.9) {
            levelName = LevelName.golden;
            productNum = 1.5;
        }
        else {
            levelName = LevelName.iridium;
            productNum = 2;
        }
        double amount = (friendShipAmount + 150 * randomDouble)/1500;
        if(amount >= 0.5 && friendShipAmount >= 100 && animalProducts.size() >= 2)
            return new AnimalProduct(1, 1, type.getAnimalProductTypes().get(1), levelName, productNum, this);
        else
            return new AnimalProduct(1, 1, type.getAnimalProductTypes().get(0), levelName, productNum, this);
    }

    public double getSellPrice() {
        return type.getPrice() * (friendShipAmount/1000 + 0.3);
    }

    @Override
    public void doTask() {

    }
}
