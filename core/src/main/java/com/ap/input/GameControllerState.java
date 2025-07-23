package com.ap.input;

import com.ap.component.Controller;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class GameControllerState implements ControllerState{

    private final ImmutableArray<Entity> controllerEntities;

    public GameControllerState(Engine engine) {
        controllerEntities = engine.getEntitiesFor(Family.all(Controller.class).get());
    }

    @Override
    public void keyDown(Command command) {
        for(Entity entity : controllerEntities) {
            Controller.mapper.get(entity).getPressedCommands().add(command);
        }
    }

    @Override
    public void keyUp(Command command) {
        for(Entity entity : controllerEntities) {
            Controller.mapper.get(entity).getReleasedCommands().add(command);
        }
    }

}
