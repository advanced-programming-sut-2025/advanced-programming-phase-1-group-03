package com.ap.ui.widget.tabContents;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.audio.AudioService;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class AbstractContent extends Actor {

    protected final Stage stage;
    protected final AssetService assetService;
    protected final Skin skin;
    protected final TextureAtlas atlas;
    protected final AudioService audioService;

    private boolean isShowing;
    protected Table background;
    protected Table content;

    private Group group;

    private int width; //just inner cells
    private int height; //just inner cells
    private final int tileWidth;
    private final int tileHeight;

    protected final Tabs icon;

    private float startX;
    private float startY;

    public final TextureRegion innerCell;
    public final TextureRegion UL_Corner;
    public final TextureRegion UR_Corner;
    public final TextureRegion DL_Corner;
    public final TextureRegion DR_Corner;
    public final TextureRegion L_Line;
    public final TextureRegion R_Line;
    public final TextureRegion D_Line;
    public final TextureRegion U_Line;
    public final TextureRegion L_Joint;
    public final TextureRegion R_Joint;
    public final TextureRegion U_Joint;
    public final TextureRegion D_Joint;
    public final TextureRegion H_Line;
    public final TextureRegion V_Line;
    public final TextureRegion H_thin_line;
    public final TextureRegion V_thin_line;

    public AbstractContent(Stage stage, AssetService assetService, Skin skin, AudioService audioService, int width, int height, Tabs icon) {
        tileHeight = 32;
        tileWidth = 32;
        this.stage = stage;
        this.assetService = assetService;
        this.skin = skin;
        this.atlas = assetService.get(AtlasAsset.UIElements);
        this.height = height;
        this.width = width;
        this.audioService = audioService;
        this.icon = icon;
        innerCell = atlas.findRegion("cell/blank");
        UL_Corner = atlas.findRegion("border/filled/ul");
        UR_Corner = atlas.findRegion("border/filled/ur");
        DL_Corner = atlas.findRegion("border/filled/dl");
        DR_Corner = atlas.findRegion("border/filled/dr");
        L_Line = atlas.findRegion("border/filled/l_line");
        R_Line = atlas.findRegion("border/filled/r_line");
        D_Line = atlas.findRegion("border/filled/d_line");
        U_Line = atlas.findRegion("border/filled/u_line");
        L_Joint = atlas.findRegion("border/filled/l_joint");
        R_Joint = atlas.findRegion("border/filled/r_joint");
        U_Joint = atlas.findRegion("border/filled/u_joint");
        D_Joint = atlas.findRegion("border/filled/d_joint");
        H_Line = atlas.findRegion("border/filled/h_line");
        V_Line = atlas.findRegion("border/filled/v_line");
        H_thin_line = atlas.findRegion("border/filled/h_thin_line");
        V_thin_line = atlas.findRegion("border/filled/v_thin_line");

        background = new Table();
        content = new Table();
        group = new Group();

        assembleBackground();


//        group.setPosition(startX, startY);
        group.addActor(background);
        group.addActor(content);

    }

    private void assembleBackground() {
        int n = MathUtils.ceil((float) height / tileWidth);
        int m = MathUtils.ceil((float) width / tileHeight);
        for (int i = 0; i <= n + 1; i++) {
            for (int j = 0; j <= m + 1; j++) {
                TextureRegion region;

                if (i == 0 && j == 0) {
                    region = UL_Corner;
                } else if (i == 0 && j == m + 1) {
                    region = UR_Corner;
                } else if (i == n + 1 && j == 0) {
                    region = DL_Corner;
                } else if (i == n + 1 && j == m + 1) {
                    region = DR_Corner;
                } else if (j == 0) {
                    region = L_Line;
                } else if (j == m + 1) {
                    region = R_Line;
                } else if (i == 0) {
                    region = U_Line;
                } else if (i == n + 1) {
                    region = D_Line;
                } else {
                    region = innerCell;
                }

                Image tile = new Image(new TextureRegionDrawable(region));
                background.add(tile).size(tileWidth, tileHeight);
            }
            background.row();
        }
        background.pack();
        startX = (Constraints.WORLD_WIDTH_RESOLUTION - background.getWidth()) / 2f;
        startY = (Constraints.WORLD_HEIGHT_RESOLUTION - background.getHeight()) / 2f;

        background.setPosition(startX, startY);

        content.setSize(background.getWidth(), background.getHeight());
        content.setPosition(background.getX(), background.getY());
        System.out.println(background.getWidth() + "," + background.getHeight() + ",\n" + startX + "," + startY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        group.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
//        super.act(delta);
        group.act(delta);
    }

    @Override
    public void moveBy(float x, float y) {
        background.moveBy(x, y);
        content.moveBy(x, y);
        startX = background.getX();
        startY = background.getY();
    }
    @Override
    public void setPosition(float x, float y) {
        background.setPosition(x, y);
        content.setPosition(x, y);
        startX = background.getX();
        startY = background.getY();
    }

    @Override
    public float getHeight() {
        return background.getHeight();
    }
    @Override
    public float getWidth() {
        return background.getWidth();
    }


    public boolean isShowing() {
        return isShowing;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }


    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }
}
