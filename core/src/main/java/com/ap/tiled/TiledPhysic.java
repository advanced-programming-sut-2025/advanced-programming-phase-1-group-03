package com.ap.tiled;

import com.ap.Constraints;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class TiledPhysic {
    public static FixtureDef fixtureDefOf(MapObject mapObject, Vector2 relativeTo, Vector2 scaling) {
        if(mapObject instanceof RectangleMapObject rectangleObject) {
            return rectangleFixtureDef(rectangleObject, relativeTo, scaling);
        } else if(mapObject instanceof EllipseMapObject ellipseObject) {
            return ellipseFixtureDef(ellipseObject, relativeTo, scaling);
        } else if(mapObject instanceof PolygonMapObject polygonObject) {
            Polygon polygon = polygonObject.getPolygon();
            float offsetX = polygon.getX() * Constraints.UNIT_SCALE;
            float offsetY = polygon.getY() * Constraints.UNIT_SCALE;
            return polygonFixtureDef(polygonObject, polygon.getVertices(), offsetX, offsetY, scaling, relativeTo);
        } else if(mapObject instanceof PolylineMapObject polylineObject) {

        } else {
            throw new GdxRuntimeException("Unsupported MapObject " + mapObject);
        }
        return null;
    }

    private static FixtureDef ellipseFixtureDef(EllipseMapObject mapObject, Vector2 relativeTo, Vector2 scaling) {
        return null;
    }

    private static FixtureDef polygonFixtureDef(
            MapObject mapObject, // Could be PolygonMapObject or PolylineMapObject
            float[] polyVertices,
            float offsetX,
            float offsetY,
            Vector2 scaling,
            Vector2 relativeTo
    ) {
        offsetX = (offsetX * scaling.x) - relativeTo.x;
        offsetY = (offsetY * scaling.y) - relativeTo.y;
        float[] vertices = new float[polyVertices.length];
        for (int vertexIdx = 0; vertexIdx < polyVertices.length; vertexIdx += 2) {
            // x-coordinate
            vertices[vertexIdx] = offsetX + polyVertices[vertexIdx] * Constraints.UNIT_SCALE * scaling.x;
            // y-coordinate
            vertices[vertexIdx + 1] = offsetY + polyVertices[vertexIdx + 1] * Constraints.UNIT_SCALE * scaling.y;
        }

        ChainShape shape = new ChainShape();
        if (mapObject instanceof PolygonMapObject) {
            shape.createLoop(vertices);
        } else { // PolylineMapObject
            shape.createChain(vertices);
        }
        return fixtureOfObjectAndShape(mapObject, shape);
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

    public static FixtureDef fixtureOfObjectAndShape(MapObject mapObject, Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = mapObject.getProperties().get("friction", 0f, Float.class);
        fixtureDef.restitution = mapObject.getProperties().get("restitution", 0f, Float.class);
        fixtureDef.density = mapObject.getProperties().get("density", 0f, Float.class);
        fixtureDef.isSensor = mapObject.getProperties().get("sensor", false, Boolean.class);
        fixtureDef.shape = shape;
        return fixtureDef;
    }

    public static Body createBodyForTile(int x, int y, Object userData, World world, boolean sensor) {
        return createRectagleBody(x, y, 1, 1, userData, world, sensor);
    }

    public static Body createRectagleBody(int x, int y, int w, int h, Object userData, World world, boolean sensor) {
        if (w <= 0 || h <= 0) {
            throw new IllegalArgumentException("Invalid width or height: " + w + ", " + h);
        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);
        body.setUserData(userData);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / 2f, h / 2f, new Vector2(w / 2f, h / 2f), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = sensor;
        body.createFixture(fixtureDef);

        shape.dispose();

        return body;
    }
}
