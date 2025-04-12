package phi.ap.model.Actions;

import phi.ap.model.enums.Weather;

public class Action {
    private static Action instance = null;

    private Action() {
    }

    public static Action getInstance() {
        if (instance == null) {
            instance = new Action();
        }
        return instance;
    }

    public void doThunderStorm(int x, int y){

    }
    public void randomThunderStorm(){

    }
    public Weather weatherForecast(){
        return null;
    }

}
