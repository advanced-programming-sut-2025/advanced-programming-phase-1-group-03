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

    public Result ProcessCommand(String command) {
        Matcher matcher;
        System.out.println(command);
       if ((matcher = CheatCodes.TimeSpeed.getMatcher(command)) != null) {
            return changeTimeSpeed(matcher.group("speed"), matcher.group("unit"));
       }
       return new Result(false, "Invalid command");
    }

    public Result changeTimeSpeed(String speedStr, String unitStr) {
        unitStr = unitStr.toLowerCase();
        unitStr = unitStr.trim();
        float speed;
        try {
            speed = (float) Double.parseDouble(speedStr);
        } catch (Exception e) {
            return new Result(false, "value is not parseable!");
        }

        switch (unitStr) {
            case "h", "hour" -> speed = speed * 3600;
            case "d", "day" -> speed = speed * 86400;
            case "m", "minute" -> speed = speed * 60;
            case "s", "second", "" -> speed = speed;
            default -> {
                return new Result(false, "unknown unit!");
            }
        }
        speed = MathUtils.clamp(speed, Constraints.GAME_SPEED_MIN, Constraints.GAME_SPEED_MAX);
        TimeSystem.setGameSpeed(speed);
        return new Result(true, "game speed successfully changed to " + speed + " s.");
    }

}
