package com.ap.client.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Graphic implements Component {
    public static final ComponentMapper<Graphic> mapper = ComponentMapper.getFor(Graphic.class);

    private TextureRegion texture;
    private Color color;
    private boolean isVisible = true;

    public Graphic(TextureRegion texture, Color color) {
        this.texture = texture;
        this.color = color;
    }
    public Graphic(TextureRegion texture) {
        this.texture = texture;
        this.color = Color.WHITE.cpy();
    }
    public Color getColor() {
        return color;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public void setColor(Color color) {
        this.color = color;
    }

//    public boolean isVisible() {
//        return isVisible;
//    }
//
//    public void setVisible(boolean visible) {
//        isVisible = visible;
//    }
}
