package com.ap.system;

import com.ap.component.Controller;
import com.ap.component.Move;
import com.ap.input.Command;
import com.ap.ui.widget.InventoryMenu;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class ControllerSystem extends IteratingSystem {
    private InventoryMenu inventoryMenu;

    public ControllerSystem(InventoryMenu inventoryMenu) {
        super(Family.all(Controller.class).get());
        this.inventoryMenu = inventoryMenu;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Controller controller = Controller.mapper.get(entity);
        if (controller.getReleasedCommands().isEmpty() && controller.getPressedCommands().isEmpty()) {
            return;
        }
        for (Command command : controller.getPressedCommands()) {
            switch (command) {
                case Right -> {
                    moveEntity(entity, 1f, 0f);
                }
                case Left -> {
                    moveEntity(entity, -1f, 0f);
                }
                case Down -> {
                    moveEntity(entity, 0f, -1f);
                }
                case Up -> {
                    moveEntity(entity, 0f, 1f);
                } case OpenInventory -> {
                    inventoryMenu.toggle();
                }
            }
        }

        // We processed all the pressed commands
        controller.getPressedCommands().clear();

        for (Command command : controller.getReleasedCommands()) {
            switch (command) {
                case Right -> {
                    moveEntity(entity, -1f, 0f);
                }
                case Left -> {
                    moveEntity(entity, 1f, 0f);
                }
                case Down -> {
                    moveEntity(entity, 0f, 1f);
                }
                case Up -> {
                    moveEntity(entity, 0f, -1f);
                }
            }
        }

        controller.getReleasedCommands().clear();

    }
    private void moveEntity(Entity entity, float dx, float dy) {
        Move move = Move.mapper.get(entity);
        if(move != null) {
            move.getDirection().x += dx;
            move.getDirection().y += dy;
        }
    }
}
