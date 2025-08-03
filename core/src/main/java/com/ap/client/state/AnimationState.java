package com.ap.client.state;

import com.ap.client.component.Animation2D;
import com.ap.client.component.Fsm;
import com.ap.client.component.Move;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum AnimationState implements State<Entity> {
    Idle() {
        @Override
        public void enter(Entity entity) {
            Animation2D.mapper.get(entity).setAnimationType(Animation2D.AnimationType.Idle);
        }

        @Override
        public void update(Entity entity) {
            Move move = Move.mapper.get(entity);
            if (move != null && !move.isRooted() && !move.getDirection().isZero()) {
                Fsm.mapper.get(entity).getAnimationFsm().changeState(AnimationState.Walk);
                return;
            }
        }

        @Override
        public void exit(Entity entity) {

        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    },
    Walk() {
        @Override
        public void enter(Entity entity) {
            Animation2D.mapper.get(entity).setAnimationType(Animation2D.AnimationType.Walk);
        }

        @Override
        public void update(Entity entity) {
            Move move = Move.mapper.get(entity);
            if (move.isRooted() || move.getDirection().isZero()) {
                Fsm.mapper.get(entity).getAnimationFsm().changeState(AnimationState.Idle);
                return;
            }
        }

        @Override
        public void exit(Entity entity) {

        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    }
}
