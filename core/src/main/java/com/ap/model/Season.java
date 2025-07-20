package com.ap.model;

import java.util.ArrayList;

public enum Season {
    Spring(),
    Summer(),
    Fall(),
    Winter(),
    ;

    public ArrayList<Weather> getPossibleWeathers() {
        ArrayList<Weather> res = new ArrayList<>();
        switch (this) {
            case Spring:
                for (int i = 0; i < 3; i++) res.add(Weather.Sunny);
                for (int i = 0; i < 1; i++) res.add(Weather.Rain);
                break;
            case Summer:
                for (int i = 0; i < 5; i++) res.add(Weather.Sunny);
                break;
            case Fall:
                for (int i = 0; i < 3; i++) res.add(Weather.Rain);
                for (int i = 0; i < 2; i++) res.add(Weather.Storm);
            case Winter:
                for (int i = 0; i < 5; i++) res.add(Weather.Snow);
                break;
        }
        return res;
    }

}
