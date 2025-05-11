package phi.ap.model.items;

public class Water extends Item {
    private int capacity = Integer.MAX_VALUE;

    public Water(){
        super();
        setName("Water");
        setMaxStackSize(0);
    }
    @Override
    public void doTask() {

    }

    public int getCapacity() {
        return capacity;
    }
    public void reduceCapacity(int amount) {
        capacity = capacity - amount;
        if(capacity == 0)
            getFather().removeItem(this);
    }

}
