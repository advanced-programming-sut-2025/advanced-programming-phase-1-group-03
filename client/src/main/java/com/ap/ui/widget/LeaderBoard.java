package com.ap.ui.widget;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.packet.LeaderBoardInfo;
import com.ap.responses.LeaderBoardResponse;
import com.ap.screen.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class LeaderBoard extends Actor {

    private final AssetService assetService;
    private final Stage stage;
    private final BitmapFont font;
    private TextureAtlas atlas;
    private final GameScreen game;

    private LeaderBoard instance;

    private TextureRegion background;
    private TextureRegion exitButton;

    private final float backgroundScale = 0.7f;
    private final float exitScale = 0.6f;

    private float backgroundPosX = 310.2f;
    private float backgroundPosY = 136.6f;

    private float exitPosX;
    private float exitPosY;

    private boolean isShowing = false;

    private Array<Player> players = new Array<>();

    public LeaderBoard(AssetService assetService, Stage stage, GameScreen game) {
        this.assetService = assetService;
        this.stage = stage;
        this.atlas = assetService.get(AtlasAsset.Journal);
        this.font = new BitmapFont();
        this.font.getData().setScale(1.3f);
        this.game = game;

        setupUI();

        exitPosX = backgroundPosX + (background.getRegionWidth() * backgroundScale) - (exitButton.getRegionWidth() * exitScale) + 15;
        exitPosY = backgroundPosY + (background.getRegionHeight() * backgroundScale) - (exitButton.getRegionHeight() * exitScale);

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (isOnTarget(exitButton, exitScale, exitPosX, exitPosY, x, y)) {
                    isShowing = false;
                    stage.getActors().removeValue(instance, true);
                    return true;
                }
                return false;
            }
        });
    }

    private void setupUI() {
        background = atlas.findRegion("JournalBackground");
        exitButton = atlas.findRegion("Exit");
    }

    private boolean isOnTarget(TextureRegion textureRegion, float scale, float posX, float posY, float x, float y) {
        float width = textureRegion.getRegionWidth() * scale;
        float height = textureRegion.getRegionHeight() * scale;
        return x >= posX && x <= posX + width && y >= posY && y <= posY + height;
    }

    public void toggle() {
        if (!isShowing) {
            instance = new LeaderBoard(assetService, stage, game);
            LeaderBoardResponse leaderBoardResponse = game.getGameClient().getSender().sendLeaderBoardRequest();
            instance.loadPlayers(leaderBoardResponse);
            instance.setupUI();
            stage.addActor(instance);
        } else {
            stage.getActors().removeValue(instance, true);
        }
        isShowing = !isShowing;
    }

    public static class Player {
        public String username;
        public int gold;
        public int questsCompleted;
        public double averageSkill;

        public Player(String username, int gold, int questsCompleted, double averageSkill) {
            this.username = username;
            this.gold = gold;
            this.questsCompleted = questsCompleted;
            this.averageSkill = averageSkill;
        }
    }

    public void loadPlayers(LeaderBoardResponse leaderBoardResponse) {
        for(LeaderBoardInfo leaderBoardInfo : leaderBoardResponse.leaderBoardInfos) {
            System.out.println(leaderBoardInfo.playerName);
            players.add(new Player(leaderBoardInfo.playerName, leaderBoardInfo.gold,
                    leaderBoardInfo.completeQuest, leaderBoardInfo.skillNum));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background,
                backgroundPosX, backgroundPosY,
                0, 0,
                background.getRegionWidth(), background.getRegionHeight(),
                backgroundScale, backgroundScale,
                0);

        batch.draw(exitButton,
                exitPosX, exitPosY,
                0, 0,
                exitButton.getRegionWidth(), exitButton.getRegionHeight(),
                exitScale, exitScale,
                0);

        float startX = backgroundPosX + 30;
        float startY = backgroundPosY + background.getRegionHeight() * backgroundScale - 30;

        font.setColor(Color.WHITE);
        font.draw(batch, String.format("%-6s %-12s %-8s %-8s %-8s", "Rank", "Name", "Gold", "Quests", "AvgSkill"),
                startX, startY);

        startY -= 10;
        font.setColor(Color.DARK_GRAY);

        startY -= 20;

        for (int i = 0; i < players.size; i++) {
            Player p = players.get(i);

            if (i == 0) {
                font.setColor(Color.GOLD);
            } else if (i == 1) {
                font.setColor(Color.LIGHT_GRAY);
            } else if (i == 2) {
                font.setColor(Color.BROWN);
            } else {
                font.setColor(Color.BLACK);
            }

            String row = String.format("%-6d    %-12s   %-8d    %-8d   %-8.2f",
                    (i + 1), p.username, p.gold, p.questsCompleted, p.averageSkill);

            font.draw(batch, row, startX, startY);
            startY -= 10;
            font.setColor(Color.DARK_GRAY);
            font.draw(batch, "", startX, startY);
            startY -= 20;
            font.draw(batch, "------------------------------------------------------------", startX, startY);
            startY -= 20;
            font.draw(batch, "", startX, startY);
            startY -= 20;
        }
    }
}
