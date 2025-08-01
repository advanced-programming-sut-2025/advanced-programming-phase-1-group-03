package com.ap.ui.widget.cheatCode;

import com.ap.Constraints;
import com.ap.screen.GameScreen;
import com.ap.system.universal.TimeSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.regex.Matcher;

public class CheatCodeController {
    private GameScreen gameScreen;


    public CheatCodeController(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void ProcessCommand(String command) {
        Matcher matcher;
        System.out.println(command);
       if ((matcher = CheatCodes.TimeSpeed.getMatcher(command)) != null) {
           System.out.println(changeTimeSpeed(matcher.group("speed"), matcher.group("unit")));
       }
    }

    public String changeTimeSpeed(String speedStr, String unitStr) {
        unitStr = unitStr.toLowerCase();
        unitStr = unitStr.trim();
        float speed;
        try {
            speed = (float) Double.parseDouble(speedStr);
        } catch (Exception e) {
            return "not parseable!";
        }

        switch (unitStr) {
            case "h", "hour" -> speed = speed * 3600;
            case "d", "day" -> speed = speed * 86400;
            case "m", "minute" -> speed = speed * 60;
        }
        speed = MathUtils.clamp(speed, Constraints.GAME_SPEED_MIN, Constraints.GAME_SPEED_MAX);
        TimeSystem.setGameSpeed(speed);
        return "changed speed to " + speed;
    }
}
