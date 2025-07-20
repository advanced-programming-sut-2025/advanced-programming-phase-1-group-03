package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.model.Season;
import com.ap.model.Weather;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;

public class Clock extends Actor {
    private final TextureAtlas atlas;
    private final AssetService assetService;
    private final Skin skin;

    private float scale = 2.5f;

    private TextureRegion clockImage;
    private TextureRegion arrowImage;

    private String time;
    private String date;
    private int arrowAngle;
    private TextureRegion seasonImage;
    private TextureRegion weatherImage;

    public Clock(AssetService assetService, Skin skin) {
        this.assetService = assetService;
        atlas = assetService.get(AtlasAsset.UI);
        this.skin = skin;
        setupUI();
        setTime("00:00");
        setDate("Mon. 1");
        setArrowAngle(120);
    }

    private void setupUI() {
        clockImage = atlas.findRegion("Clock");
        arrowImage = atlas.findRegion("ClockArrow");
        setX(Constraints.WORLD_WIDTH_RESOLUTION - clockImage.getRegionWidth() * scale - 20);
        setY(Constraints.WORLD_HEIGHT_RESOLUTION- clockImage.getRegionHeight() * scale - 20);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float weatherImageXOffset = 72.7f;
        float weatherImageYOffset = 87.7f;
        batch.draw(weatherImage,
                getX() + weatherImageXOffset, getY() + weatherImageYOffset,
                0, 0,
                weatherImage.getRegionWidth(), weatherImage.getRegionHeight(),
                scale, scale,
                0);

        float seasonImageXOffset = 133f;
        float seasonImageYOffset = 87.5f;
        batch.draw(seasonImage,
                getX() + seasonImageXOffset, getY() + seasonImageYOffset,
                0, 0,
                seasonImage.getRegionWidth(), seasonImage.getRegionHeight(),
                scale, scale,
                0);

        batch.draw(clockImage,
                getX(), getY(), 0, 0
                , clockImage.getRegionWidth(), clockImage.getRegionHeight(),
                scale, scale,
                0);

        BitmapFont font = skin.getFont("font24");
        float clockYOffset = 72;
        float clockXOffset = 100 - time.length();
        font.draw(batch, time, getX() + clockXOffset, getY() + clockYOffset);

        float dateYOffset = 130;
        float dateXOffset = 100 - date.length();
        font.draw(batch, date, getX() + dateXOffset, getY() + dateYOffset);

        float arrowYOffset = 95f;
        float arrowXOffset = 50f;
        batch.draw(arrowImage,
                getX() + arrowXOffset, getY() + arrowYOffset,
                arrowImage.getRegionWidth() /  2f, 1,
                arrowImage.getRegionWidth(), arrowImage.getRegionHeight(),
                scale, scale,
                arrowAngle);

    }


    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setArrowAngle(int arrowAngle) {
        this.arrowAngle = arrowAngle;
    }

    public void setWeather(Weather weather) {
        this.weatherImage = atlas.findRegion(weather.name());
    }

    public void setSeason(Season season) {
        this.seasonImage = atlas.findRegion(season.name());
    }
}
