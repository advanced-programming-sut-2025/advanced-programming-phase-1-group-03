package phi.ap.model;

import phi.ap.model.items.buildings.Cottage;
import phi.ap.model.items.buildings.Greenhouse;
import phi.ap.model.items.buildings.Lake;
import phi.ap.model.items.buildings.Quarry;

import java.util.ArrayList;

public class Farm extends Ground{
    private int number;
    private ArrayList<Cottage> cottages;
    private ArrayList<Lake> lakes;
    private ArrayList<Greenhouse> greenhouses;
    private ArrayList<Quarry> quarries;

    public Farm(int height, int width) {
        super(height, width);
    }
}
