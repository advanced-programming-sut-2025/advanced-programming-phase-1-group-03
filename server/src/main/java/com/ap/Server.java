package com.ap;

import com.ap.asset.AssetService;
import com.ap.asset.MapAsset;
import com.ap.model.Room;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;

import java.util.ArrayList;

public class Server implements ApplicationListener {
    long prevTime;

    @Override
    public void create() {
        prevTime = System.currentTimeMillis();
        GameServer server = new GameServer();
        try {
            server.start();
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        long now = System.currentTimeMillis();

        if(now - prevTime >= 1000 * Configuration.STEP_UPDATING) {

            float delta = (now - prevTime) / 1000f;
            prevTime = now;

            for(Room room : new ArrayList<>(ServerData.instance.activeRooms)) {
                if(room.game == null) {
                    continue;
                }

                var game = room.game;
                game.update(delta);
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
