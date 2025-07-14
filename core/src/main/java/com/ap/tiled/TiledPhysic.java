package com.ap.tiled;

import com.ap.Constraints;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class TiledPhysic {
    public static FixtureDef fixtureDefOf(MapObject mapObject, Vector2 relativeTo, Vector2 scaling) {
        if(mapObject instanceof RectangleMapObject rectangleObject) {
            return rectangleFixtureDef(rectangleObject, relativeTo, scaling);
        } else if(mapObject instanceof EllipseMapObject ellipseObject) {
            return ellipseFixtureDef(ellipseObject, relativeTo, scaling);
        } else if(mapObject instanceof PolygonMapObject polygonObject) {
        } else if(mapObject instanceof PolylineMapObject polylineObject) {

        } else {
            throw new GdxRuntimeException("Unsupported MapObject " + mapObject);
        }
        return null;
    }

    private static FixtureDef ellipseFixtureDef(EllipseMapObject mapObject, Vector2 relativeTo, Vector2 scaling) {
        return null;
    }

    /**
     * Create rectangle fixture for the given mapObject
     * relativeTo must be in the world unit
     * @param mapObject Given map object
     * @param relativeTo Position of the bottom left corner of sprite, by default it's zero
     * @param scaling Scaling factors
     */
    private static FixtureDef rectangleFixtureDef(RectangleMapObject mapObject,
                                                  Vector2 relativeTo,
                                                  Vector2 scaling) {
        Rectangle rectangle = mapObject.getRectangle();
        float rectX = rectangle.x;
        float rectY = rectangle.y;
        float rectW = rectangle.width;
        float rectH = rectangle.height;

        float boxX = rectX * Constraints.UNIT_SCALE * scaling.x - relativeTo.x;
        float boxY = rectY * Constraints.UNIT_SCALE * scaling.y - relativeTo.y;

        // Half of the world width
        float boxW = rectW * Constraints.UNIT_SCALE * scaling.x * 0.5f;
        float boxH = rectH * Constraints.UNIT_SCALE * scaling.y * 0.5f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxW, boxH, new Vector2(boxX + boxW, boxY + boxH), 0);
        return fixtureOfObjectAndShape(mapObject, shape);
    }

    private static FixtureDef fixtureOfObjectAndShape(RectangleMapObject mapObject, Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = mapObject.getProperties().get("friction", 0f, Float.class);
        fixtureDef.restitution = mapObject.getProperties().get("restitution", 0f, Float.class);
        fixtureDef.density = mapObject.getProperties().get("density", 0f, Float.class);
        fixtureDef.isSensor = mapObject.getProperties().get("sensor", false, Boolean.class);
        fixtureDef.shape = shape;
        return fixtureDef;
    }
}
