package com.ap.ui.widget.cheatCode;

import com.ap.Constraints;
import com.ap.model.GameData;
import com.ap.network.Sender;
import com.ap.screen.GameScreen;
import com.badlogic.gdx.math.MathUtils;

import java.util.regex.Matcher;

public class CheatCodeController {
    private GameScreen gameScreen;

    private int id = 0;

    private final Sender sender;
    public CheatCodeController(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        sender = gameScreen.getGameClient().getSender();
    }

    public Result ProcessCommand(String command) {
        Matcher matcher;
        System.out.println(command);
       if ((matcher = CheatCodes.TimeSpeed.getMatcher(command)) != null) {
//           return changeTimeSpeed(matcher.group("speed"), matcher.group("unit"));
       } else if ((matcher = CheatCodes.Energy.getMatcher(command)) != null) {
//           return setEnergy(matcher.group("energy"));
       } else if ((matcher = CheatCodes.AddGold.getMatcher(command)) != null) {
//           return setGold(matcher.group("gold"));
       } else if ((matcher = CheatCodes.Thor.getMatcher(command)) != null) {
//           return thor(matcher.group("x"), matcher.group("y"));
       } else if((matcher = CheatCodes.SendMessage.getMatcher(command)) != null) {
           return sendMessage(matcher.group("user"), matcher.group("message"));
       } else if((matcher = CheatCodes.Vote.getMatcher(command)) != null) {
           return vote(matcher.group("user"));
       }
       return new Result(false, "Invalid command");
    }

    private Result vote(String userName) {
        sender.sendVote( userName, id++, 1);
        return new Result(true, "your kick request has been sent.");
    }

    private Result sendMessage(String user, String message) {
        boolean isPrivate = !user.equals("all");
        var result = sender.sendChat(isPrivate ? user : null, message);
        return new Result(result.success, result.message);
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
  //      TimeSystem.setGameSpeed(speed);
        return new Result(true, "game speed successfully changed to " + speed + " s.");
    }

    public Result setEnergy(String energyStr) {
        int energy;
        try {
            energy = Integer.parseInt(energyStr);
        } catch (Exception e) {
            return new Result(false, "value must be integer!");
        }
      //  gameScreen.getEnergyManager().setPercentage(energy);
        return new Result(true, "energy amount successfully changed to " + energy + " .");
    }

    public Result setGold(String goldStr) {
        int gold;
        try {
            gold = Integer.parseInt(goldStr);
        } catch (Exception e) {
            return new Result(false, "value must be integer!");
        }
        GameData.getInstance().setPlayerGold(gold);
        return new Result(true, "gold amount successfully changed to " + gold + " .");
    }

    public Result thor(String xStr, String yStr) {
        int x;
        int y;
        try {
            x = Integer.parseInt(xStr);
            y = Integer.parseInt(yStr);
        } catch (Exception e) {
            return new Result(false, "values must be integer!");
        }
        if (x < 0 || x > Constraints.WORLD_WIDTH || y < 0 || y > Constraints.WORLD_HEIGHT) {
            return new Result(false, "coordinate is out of border!");
        }
        gameScreen.getLightningStorm().toggle(x, y);
        return new Result(true, "BOOM!!!");

    }

}
