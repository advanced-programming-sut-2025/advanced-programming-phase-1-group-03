package com.ap.system;


import com.ap.items.ItemStack;
import com.ap.screen.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ClickSystem implements InputProcessor {
    private final Camera camera;
    private final GameScreen gameScreen;
    public ClickSystem(Camera camera, GameScreen gameScreen) {
        this.camera = camera;
        this.gameScreen = gameScreen;
    }




    @Override
    public boolean keyDown(int i) {
            return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }



        @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldPos = camera.unproject(new Vector3(screenX, screenY, 0));

        gameScreen.getGameClient().getSender().sendWorldClickRequest(worldPos.x, worldPos.y, button, gameScreen.getItemContainer().getSelectedItem());

        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
