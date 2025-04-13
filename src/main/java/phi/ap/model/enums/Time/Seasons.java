package phi.ap.model.enums.Time;

import java.util.ArrayList;
import java.util.Arrays;

public enum Seasons {
    Spring("Spring"),
    Summer("Summer"),
    Fall("Fall"),
    Winter("Winter"),
    ;

    public String name;
    Seasons(String name) {
        this.name = name;
    }
    public final static int days = 28;

    public static ArrayList<Seasons> buildList(Seasons... seasons){
        return new ArrayList<Seasons>(Arrays.asList(seasons));
    }
    public static ArrayList<Seasons> getAll(){
        return new ArrayList<Seasons>(Arrays.asList(Seasons.values()));
    }
}
