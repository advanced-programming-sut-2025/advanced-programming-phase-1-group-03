package com.ap.utils;

import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.ap.component.Container;
import com.ap.component.Physic;
import com.ap.component.Transform;
import com.ap.model.Season;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class Helper {
    public static void removeEntity(Entity entity, Engine engine, World world) {
        if(Container.mapper.has(entity)) {
            for(Entity child : Container.mapper.get(entity).getChildren()) {
                removeEntity(child, engine, world);
            }
        }
        if(Physic.mapper.has(entity)) {
            Physic physic = Physic.mapper.get(entity);
            world.destroyBody(physic.getBody());
        }
        engine.removeEntity(entity);
    }
    public static int random(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
    public static void addEntity(Entity entity, Engine engine) {
        engine.addEntity(entity);
        if(Container.mapper.has(entity)) {
            for(Entity child : Container.mapper.get(entity).getChildren()) {
                addEntity(child, engine);
            }
        }
    }

    public static Body getTopBodyAtPoint(Vector2 tilePos, World world) {
        tilePos.add(new Vector2(0.5f, 0.5f));
        final Body[] topBody = {null};
        final int[] highestZIndex = {-1};
        final float[] highestYIndex = {-1};

        world.QueryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                if(fixture.getBody().getUserData() instanceof Entity entity) {
                    if (Transform.mapper.has(entity)) {
                        Transform transform = Transform.mapper.get(entity);
                        if (transform.getZ() > highestZIndex[0]) {
                            highestZIndex[0] = transform.getZ();
                            highestYIndex[0] = -1;
                            topBody[0] = fixture.getBody();
                        } else if(transform.getZ() == highestZIndex[0] &&
                            transform.getSortOffsetY()  > highestYIndex[0]) {
                            highestYIndex[0] = transform.getSortOffsetY();
                            topBody[0] = fixture.getBody();
                        }
                    }
                } else if(fixture.getBody().getUserData() instanceof TiledMapTile tile) {
                    if(highestZIndex[0] != -1) {
                        return true;
                    }
                    highestZIndex[0] = 0;
                    topBody[0] = fixture.getBody();
                }
                return true;
            }
        }, tilePos.x - 0.01f, tilePos.y - 0.01f, tilePos.x + 0.01f, tilePos.y + 0.01f);

        return topBody[0];
    }

    public static void playMusicOfSeason(AudioService audioService, Season season) {
        switch (season) {
            case Spring -> {
                audioService.playMusic(MusicAsset.Spring);
            }
            case Summer -> {
                audioService.playMusic(MusicAsset.Summer);
            }
            case Fall -> {
                audioService.playMusic(MusicAsset.Fall);
            }
            case Winter -> {
                audioService.playMusic(MusicAsset.Winter);
            }
        }
    }

    public static float calculateDistance(Vector2 a, Vector2 b) {
        return (float) Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }
}
