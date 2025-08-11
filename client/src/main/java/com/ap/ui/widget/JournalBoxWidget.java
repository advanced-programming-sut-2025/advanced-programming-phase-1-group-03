package com.ap.ui.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class JournalBoxWidget {
    private final TextureRegion boxTexture;
    private final String text;
    private final float x, y;
    private final float boxScale;
    private final BitmapFont font;

    private final float padding = 5f;

    public JournalBoxWidget(TextureRegion boxTexture, String text,
                            float x, float y, float boxScale, BitmapFont font) {
        this.boxTexture = boxTexture;
        this.text = text;
        this.x = x;
        this.y = y;
        this.boxScale = boxScale;
        this.font = font;
    }

    public float getHeight() {
        return boxTexture.getRegionHeight() * boxScale + padding;
    }

    public void draw(Batch batch) {
        // Draw background box
        batch.draw(boxTexture,
                x, y,
                0, 0,
                boxTexture.getRegionWidth(), boxTexture.getRegionHeight(),
                boxScale, boxScale,
                0);

        // Draw text inside the box
        float textX = x + 40f;
        float textY = y + boxTexture.getRegionHeight() * boxScale - 20f;
        font.setColor(Color.BROWN);
        font.draw(batch, text, textX, textY);
    }
}
