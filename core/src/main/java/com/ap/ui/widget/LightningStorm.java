package com.ap.ui.widget;

import com.ap.asset.AssetService;
import com.ap.asset.MusicAsset;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LightningStorm extends Actor {

    private enum State {
        FADING_IN_CLOUDS,
        WAITING_FOR_LIGHTNING,
        FLASHING_LIGHTNING,
        FADING_OUT_CLOUDS,
        DONE
    }

    private final TextureRegion clouds;
    private final TextureRegion light;
    private final Texture whiteFlash; // بافت سفید کوچک

    private final Stage stage;
    private final AssetService assetService;
    private final Skin skin;
    private final AudioService audioService;

    private float cloudAlpha = 0f;
    private float lightningAlpha = 0f;
    private float flashAlpha = 0f;
    private float timeAccumulator = 0f;
    private State currentState = State.FADING_IN_CLOUDS;

    private final float posX;
    private final float posY;
    private final float widthScale = 0.5f;
    private final float heightScale = 0.44f;
    private boolean isShowing = false;

    LightningStorm instance;

    private boolean thunderPlayed = false;

    public LightningStorm(AssetService assetService, Skin skin, Stage stage, AudioService audioService, float x, float y) {
        this.assetService = assetService;
        this.skin = skin;
        this.stage = stage;
        this.audioService = audioService;
        this.posX = x;
        this.posY = y;

        clouds = new TextureRegion(new Texture(Gdx.files.internal("graphics/LightningCloud.png")));
        light = new TextureRegion(new Texture(Gdx.files.internal("graphics/LightningStorm3.png")));
        whiteFlash = new Texture(Gdx.files.internal("graphics/whiteflash.png")); // فایل white.png باید سفید یک‌دست باشه
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        timeAccumulator += delta;

        switch (currentState) {
            case FADING_IN_CLOUDS:
                cloudAlpha += delta * 0.5f;
                if (cloudAlpha >= 1f) {
                    cloudAlpha = 1f;
                    timeAccumulator = 0f;
                    currentState = State.WAITING_FOR_LIGHTNING;
                }
                break;

            case WAITING_FOR_LIGHTNING:
                if (timeAccumulator > 1.5f) {
                    timeAccumulator = 0f;
                    lightningAlpha = 1f;
                    flashAlpha = 0.7f; // شروع فلاش
                    if (!thunderPlayed) {
                        audioService.playSound(SoundAsset.Thunder, 1);
                        thunderPlayed = true;
                    }
                    currentState = State.FLASHING_LIGHTNING;
                }
                break;

            case FLASHING_LIGHTNING:
                lightningAlpha -= delta * 2f;
                flashAlpha -= delta * 2.5f; // محو تدریجی فلاش

                if (lightningAlpha <= 0f) {
                    lightningAlpha = 0f;
                    flashAlpha = 0f;
                    timeAccumulator = 0f;
                    currentState = State.FADING_OUT_CLOUDS;
                }
                break;

            case FADING_OUT_CLOUDS:
                cloudAlpha -= delta * 0.3f;
                if (cloudAlpha <= 0f) {
                    cloudAlpha = 0f;
                    currentState = State.DONE;
                }
                break;

            case DONE:
                isShowing = false;
                remove();
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // رسم ابرها
        batch.setColor(1f, 1f, 1f, cloudAlpha);
        batch.draw(clouds, 0, 0);

        // رسم صاعقه
        if (lightningAlpha > 0f) {
            batch.setColor(1f, 1f, 1f, lightningAlpha);
            batch.draw(
                    light, posX, posY,
                    0, 0,
                    light.getRegionWidth(), light.getRegionHeight(),
                    widthScale, heightScale,
                    0f
            );
        }

        // رسم فلاش سفید روی کل صفحه
        if (flashAlpha > 0f) {
            batch.setColor(1f, 1f, 1f, flashAlpha);
            batch.draw(whiteFlash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        // ریست رنگ
        batch.setColor(1f, 1f, 1f, 1f);
    }

    public void toggle(float x, float y) {
        System.out.println("!! " + stage.getActors().contains(instance, true));
        if (!isShowing) {
            instance = new LightningStorm(assetService, skin, stage, audioService, x, y);
            stage.addActor(instance);
        } else {
            stage.getActors().removeValue(instance, true);
        }
        isShowing = !isShowing;
        System.out.println("!! " + stage.getActors().contains(instance, true));
    }
}
