package com.ap.ui.widget;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class FishingMiniGame extends Actor {

    private final AssetService assetService;
    private final Skin skin;
    private final World world;

    private final TextureAtlas textureAtlas;
    private final TextureRegion bodyFish;
    private final TextureRegion background;
    private final TextureRegion greenBar;

    private final ShapeRenderer shapeRenderer;

    private final float backgroundScale = 2.5f;
    private final float fishScale = 1.5f;

    private float backgroundPosX;
    private float backgroundPosY;
    private float waterBottomY, waterTopY;

    private float fishOffsetX = 45;
    private float fishOffsetY;
    private float fishMinY, fishMaxY;

    private Body barBody;
    private final float barWidth = 9;
    private final float barHeight = 26;
    private float barOffsetX;
    private float barStartY;

    private Body anchor;
    private Joint prismaticJoint;

    private float catchProgress = 30f;
    private final float PROGRESS_SPEED = 10f;

    private final float TIME_STEP = 1/60f;
    private float accumulator = 0;

    // Fish movement
    private enum FishMovementType {
        MIXED, SMOOTH, SINKER, FLOATER, DART
    }


    private boolean isPerfect = true;

    private FishMovementType movementType = FishMovementType.MIXED;
    private float moveTimer = 0f;
    private int lastMove = 0;

    public FishingMiniGame(AssetService assetService, Skin skin, float x, float y, Stage stage) {
        this.assetService = assetService;
        this.skin = skin;
        this.backgroundPosX = x;
        this.backgroundPosY = y;

        this.world = new World(new Vector2(0, -80f), true);

        this.textureAtlas = assetService.get(AtlasAsset.Fish);
        this.bodyFish = textureAtlas.findRegion("Fishing-8");
        this.background = new TextureRegion(new Texture(Gdx.files.internal("graphics/FishingMiniGame.png")));
        this.greenBar = new TextureRegion(new Texture(Gdx.files.internal("graphics/greenBar.png")));

        this.shapeRenderer = new ShapeRenderer();

        float bgHeight = background.getRegionHeight() * backgroundScale;
        waterBottomY = backgroundPosY - 95f;
        waterTopY = backgroundPosY + bgHeight - 125f;

        fishMinY = waterBottomY + fishScale * bodyFish.getRegionHeight() / 2f;
        fishMaxY = waterTopY - fishScale * bodyFish.getRegionHeight() / 2f;
        fishOffsetY = (fishMinY + fishMaxY) / 2f - backgroundPosY;

        barOffsetX = background.getRegionWidth() / 2f - barWidth / 2f;
        barStartY = (waterBottomY + waterTopY) / 2f;

        createBounds();
        createBarBody();
        createPrismaticConstraint();

        barBody.applyLinearImpulse(new Vector2(0, 5f), barBody.getWorldCenter(), true);

        stage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    barBody.applyLinearImpulse(new Vector2(0, 50000f), barBody.getWorldCenter(), true);
                    return true;
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        selectFish();
    }

    private void selectFish() {
        double rand = Math.random() * 5;
        if(rand <= 1)
            movementType = FishMovementType.MIXED;
        else if(rand <= 2)
            movementType = FishMovementType.DART;
        else if(rand <= 3)
            movementType = FishMovementType.FLOATER;
        else if(rand <= 4)
            movementType = FishMovementType.SINKER;
        else if(rand <= 5)
            movementType = FishMovementType.SMOOTH;
    }

    private void createBounds() {
        BodyDef bd = new BodyDef(); bd.type = BodyDef.BodyType.StaticBody;
        Body bottom = world.createBody(bd);
        EdgeShape edge = new EdgeShape();
        edge.set(new Vector2(backgroundPosX, waterBottomY),
                new Vector2(backgroundPosX + background.getRegionWidth() * backgroundScale, waterBottomY));
        bottom.createFixture(edge, 0f); edge.dispose();

        Body top = world.createBody(bd);
        edge = new EdgeShape();
        edge.set(new Vector2(backgroundPosX, waterTopY),
                new Vector2(backgroundPosX + background.getRegionWidth() * backgroundScale, waterTopY));
        top.createFixture(edge, 0f); edge.dispose();
    }

    private void createBarBody() {
        BodyDef bd = new BodyDef(); bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(backgroundPosX + barOffsetX * backgroundScale, barStartY);
        barBody = world.createBody(bd);

        PolygonShape box = new PolygonShape();
        box.setAsBox(barWidth/2f * backgroundScale, barHeight/2f * backgroundScale);
        FixtureDef fd = new FixtureDef();
        fd.shape = box; fd.density = 0.5f; fd.friction = 0.2f; fd.restitution = 0.1f;
        barBody.createFixture(fd); box.dispose();
    }

    private void createPrismaticConstraint() {
        BodyDef abd = new BodyDef(); abd.type = BodyDef.BodyType.StaticBody;
        abd.position.set(backgroundPosX + barOffsetX * backgroundScale, barStartY);
        anchor = world.createBody(abd);

        PrismaticJointDef pjd = new PrismaticJointDef();
        pjd.initialize(anchor, barBody, anchor.getWorldCenter(), new Vector2(0, 1));
        pjd.enableLimit = true;
        float halfRange = (waterTopY - waterBottomY) / 2f;
        pjd.lowerTranslation = -halfRange;
        pjd.upperTranslation = halfRange;
        world.createJoint(pjd);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        moveTimer += delta;
        if (moveTimer >= 0.5f) {
            moveTimer = 0f;

            int move = 0;
            switch (movementType) {
                case MIXED:
                    move = MathUtils.random(-1, 1);
                    break;

                case SMOOTH:
                    if (MathUtils.random() < 0.7f) {
                        move = lastMove;
                    } else {
                        move = MathUtils.random(-1, 1);
                    }
                    break;

                case SINKER:
                    if (lastMove == 0 && MathUtils.random() < 0.3f) {
                        move = -1;
                    } else {
                        move = MathUtils.random(-1, 1);
                    }
                    break;

                case FLOATER:
                    if (lastMove == 0 && MathUtils.random() < 0.3f) {
                        move = 1;
                    } else {
                        move = MathUtils.random(-1, 1);
                    }
                    break;

                case DART:
                    move = MathUtils.random(-1, 1);
                    break;
            }

            lastMove = move;

            float deltaY = movementType == FishMovementType.DART ? 9f : 5f;
            deltaY *= 4.5f;
            fishOffsetY += move * deltaY;
            fishOffsetY = MathUtils.clamp(fishOffsetY, fishMinY - backgroundPosY, fishMaxY - backgroundPosY);
        }

        accumulator += delta;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        float fishWorldY = backgroundPosY + fishOffsetY;
        float barY = barBody.getPosition().y - barHeight/2f * backgroundScale;
        float barTop = barY + barHeight * backgroundScale;
        if (fishWorldY >= barY && fishWorldY <= barTop) {
            catchProgress += PROGRESS_SPEED * delta;
        } else {
            isPerfect = false;
            catchProgress -= PROGRESS_SPEED * delta;
        }
        catchProgress = MathUtils.clamp(catchProgress, 0f, 100f);
        // here we should give the fish fo inventory
        if (catchProgress >= 100f || catchProgress <= 0f) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, backgroundPosX, backgroundPosY,
                background.getRegionWidth()/2f, background.getRegionHeight()/2f,
                background.getRegionWidth(), background.getRegionHeight(),
                backgroundScale, backgroundScale, 0);

        Vector2 pos = barBody.getPosition();
        batch.draw(greenBar,
                pos.x - barWidth/2f * backgroundScale - 28,
                pos.y - barHeight/2f * backgroundScale,
                barWidth * backgroundScale, barHeight * backgroundScale);

        batch.draw(bodyFish,
                backgroundPosX + fishOffsetX * backgroundScale - bodyFish.getRegionWidth()*fishScale/2 - 92,
                backgroundPosY + fishOffsetY - bodyFish.getRegionHeight()*fishScale/2,
                bodyFish.getRegionWidth()*fishScale, bodyFish.getRegionHeight()*fishScale);

        float fullBarHeight = background.getRegionHeight() * backgroundScale - 26.6f;
        float progHeight = fullBarHeight * (catchProgress / 100f);
        float progX = backgroundPosX + background.getRegionWidth() * backgroundScale - 72;
        float progY = backgroundPosY - 100;

        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(progX, progY, 7f, progHeight);
        shapeRenderer.end();
        batch.begin();
    }
}
