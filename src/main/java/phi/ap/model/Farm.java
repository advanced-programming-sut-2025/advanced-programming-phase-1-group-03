package phi.ap.model;

import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.*;
import phi.ap.model.items.producers.Tree;

import java.util.ArrayList;

public class Farm extends Ground{
    private final int number;
    private final ArrayList<Cottage> cottages;
    private final ArrayList<Lake> lakes;
    private final ArrayList<Greenhouse> greenhouses;
    private final ArrayList<Quarry> quarries;
    private final ArrayList<Building> buildings;
    private final ArrayList<Item> items;

    public Farm(int height, int width, int number) {
        super(height, width);
        cottages = new ArrayList<>();
        lakes = new ArrayList<>();
        greenhouses = new ArrayList<>();
        quarries = new ArrayList<>();
        buildings = new ArrayList<>();
        items = new ArrayList<>();
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public ArrayList<Cottage> getCottages() {
        return cottages;
    }

    public ArrayList<Lake> getLakes() {
        return lakes;
    }

    public ArrayList<Greenhouse> getGreenhouses() {
        return greenhouses;
    }

    public ArrayList<Quarry> getQuarries() {
        return quarries;
    }



}
