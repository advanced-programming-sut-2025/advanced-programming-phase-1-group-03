package com.ap.system;

import com.ap.asset.AssetService;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.component.Animation2D;
import com.ap.component.Player;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.audio.Sound;

public class PlayerAudioSystem extends IteratingSystem {
    private final AssetService assetService;
    private Sound walkSound;
    long musicId = -1;

    public PlayerAudioSystem(AssetService assetService) {
        super(Family.all(Animation2D.class, Player.class).get());
        this.assetService = assetService;
        setup();
    }

    private void setup() {
        walkSound = assetService.get(SoundAsset.Walk);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Animation2D animation2D = Animation2D.mapper.get(entity);
        if(animation2D.getAnimationType() == Animation2D.AnimationType.Walk) {
            if(musicId == -1) {
                musicId = walkSound.play(0.1f);
                walkSound.setLooping(musicId, true);
            }
        } else {
            musicId = -1;
            walkSound.stop();
        }
    }
}
