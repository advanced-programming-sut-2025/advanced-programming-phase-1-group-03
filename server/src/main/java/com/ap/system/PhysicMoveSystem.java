package com.ap.system;

import com.ap.component.Move;
import com.ap.component.Physic;
import com.ap.component.Player;
import com.ap.model.ServerPlayer;
import com.ap.requests.MovePlayerRequest;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.Arrays;

public class PhysicMoveSystem extends IteratingSystem {

    // We define this globally because we don't want to create a vector in every frame
    Vector2 speedVec = new Vector2();

    private int keyDownCounter = 0;
    private Engine engine;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    public PhysicMoveSystem() {
        super(Family.all(Physic.class, Move.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        Move move = Move.mapper.get(entity);
        Physic physic = Physic.mapper.get(entity);

        Body body = physic.getBody();

        if(move.isRooted() || move.getDirection().isZero()) {
            // Direction is zero or rooted -> we should stop movement
            body.setLinearVelocity(Vector2.Zero);
            return;
        }
        float maxSpeed = move.getMaxSpeed();
        speedVec.set(move.getDirection()).nor();
        body.setLinearVelocity(speedVec.x * maxSpeed, speedVec.y * maxSpeed);
    }

    private Vector2 getPlayerDirection(int id) {
        var player = Helper.getPlayer(engine, id);
        assert player != null;
        return Move.mapper.get(player).getDirection();
    }

    public void stopPlayer(ServerPlayer player) {
        keyDownCounter = 0;
        var playerVector2 = getPlayerDirection(player.id);
        playerVector2.set(Vector2.Zero);
    }

    public void movePlayer(MovePlayerRequest request, ServerPlayer senderPlayer) {
        if(request.isKeyDown) {
            keyDownCounter ++;
        } else {
            if(keyDownCounter <= 0) {
                return;
            }
            keyDownCounter--;
        }
        var direction = getPlayerDirection(senderPlayer.id);
        direction.x += request.dx;
        direction.y += request.dy;
    }
}
