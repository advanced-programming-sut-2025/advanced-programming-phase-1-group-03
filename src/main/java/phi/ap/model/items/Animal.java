package phi.ap.model.items;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.AnimalTypes;
import phi.ap.model.enums.Tool.BasicToolLevels;
import phi.ap.model.enums.TileType;
import phi.ap.model.items.products.AnimalProduct;

import java.util.ArrayList;

public class Animal extends Item {
    private AnimalTypes type;
    private int friendShipAmount = 0;
    private int remainingDayToProduce;
    private boolean beenPet = false;
    private boolean inHome = true;
    private boolean feeded = false;


    private ArrayList<AnimalProduct> animalProducts = new ArrayList<>();

    public Animal(AnimalTypes animalType, int height, int width) {
        super(height, width);
        this.type = animalType;
        this.remainingDayToProduce = this.type.getDayToProduce();
        setName(animalType.name());
        setSellable(true);
        fillTile(TileType.A.getTile());
        setSellPrice(animalType.getPrice());
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

    public void setFriendShipAmount(int friendShipAmount) {
        this.friendShipAmount = friendShipAmount;
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

    public AnimalProduct produceProduct() {
        if(!getIsFeeded())
            return null;
        double randomDouble = 0.5 + Math.random();
        double quality = ((double)(friendShipAmount/1000))*(0.5 + 0.5 * Math.random()); // TODO : cast to double?
        Tool.BasicToolLevels Tool.BasicToolLevels;
        double productNum;
        if(quality <= 0.5) {
            Tool.BasicToolLevels = Tool.BasicToolLevels.normal;
            productNum = 1;
        }
        else if(quality <= 0.7) {
            Tool.BasicToolLevels = Tool.BasicToolLevels.silver;
            productNum = 1.25;
        }
        else if(quality <= 0.9) {
            Tool.BasicToolLevels = Tool.BasicToolLevels.golden;
            productNum = 1.5;
        }
        else {
            Tool.BasicToolLevels = Tool.BasicToolLevels.iridium;
            productNum = 2;
        }
        double amount = (friendShipAmount + 150 * randomDouble)/1500;
        if(amount >= 0.5 && friendShipAmount >= 100 && animalProducts.size() >= 2)
            return new AnimalProduct(1, 1, type.getAnimalProductTypes().get(1), Tool.BasicToolLevels, productNum, this);
        else
            return new AnimalProduct(1, 1, type.getAnimalProductTypes().get(0), Tool.BasicToolLevels, productNum, this);
    }

    public int getSellPrice() {
        return (int) (type.getPrice() * ((double) friendShipAmount /1000 + 0.3)); // TODO : bug possible
    }

    @Override
    public void doTask() {

    }
}
