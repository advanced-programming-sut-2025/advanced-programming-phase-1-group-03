package phi.ap.model.items.products;

import phi.ap.model.Eatable;
import phi.ap.model.enums.FruitTypes;

public class Fruit extends Product {
//    private Producer source;
    private final FruitTypes fruitType;


    public Fruit(int height, int width, FruitTypes fruitType) {
        super(height, width);
        this.fruitType = fruitType;
        setName(fruitType.getName());
        setSellable(true);
        setSellPrice(fruitType.getBaseSellPrice());
        setEatable(new Eatable(fruitType.getEatable()));
    }

    public FruitTypes getFruitType() {
        return fruitType;
    }

    @Override
    public void doTask() {

    }
}
