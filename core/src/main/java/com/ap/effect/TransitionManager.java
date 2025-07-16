package com.ap.effect;

import com.ap.Constraints;
import com.ap.GdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TransitionManager {
    private float transitionDuration = 0.5f;

    private Class<? extends Screen> newScreen;
    private final GdxGame game;
    private final FitViewport viewport;
    private boolean isTransitioning = false;
    private boolean expanding;
    private final ShapeRenderer shapeRenderer;
    private float timer = 0f;

    public TransitionManager(GdxGame game) {
        this.game = game;
        this.viewport = game.getViewport();
        shapeRenderer = new ShapeRenderer();
    }

    public void initTransition(Class<? extends Screen> newScreen) {
        this.newScreen = newScreen;
        this.isTransitioning = true;
        expanding = true;
        timer = 0f;
    }
    public void render(float deltaTime) {
        if(!isTransitioning)
            return;

        timer += deltaTime;
        float progress = MathUtils.clamp(timer / transitionDuration, 0, 1);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        float screenWidth = Constraints.WORLD_WIDTH_RESOLUTION;
        float screenHeight = Constraints.WORLD_HEIGHT_RESOLUTION;
        float maxRadius = (float) Math.sqrt(screenWidth * screenWidth
                + screenHeight * screenHeight);

        if(expanding) {
            shapeRenderer.circle(screenWidth / 2f, screenHeight / 2f,
                    maxRadius * progress);

            if(progress >= 1) {
                expanding = false;
                timer = 0f;
                game.changeScreen(newScreen);
            }
        } else {
            shapeRenderer.circle(screenWidth / 2f, screenHeight / 2f,
                    maxRadius * (1 - progress));

            if(progress >= 1) {
                isTransitioning = false;
            }
        }

        shapeRenderer.end();
    }

    public boolean isTransitioning() {
        return isTransitioning;
    }
}
