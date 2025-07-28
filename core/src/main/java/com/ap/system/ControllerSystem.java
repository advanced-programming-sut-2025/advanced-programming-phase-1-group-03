package com.ap.system;

import com.ap.component.Controller;
import com.ap.component.Move;
import com.ap.component.Player;
import com.ap.input.Command;
import com.ap.ui.widget.CraftingMenu;
import com.ap.ui.widget.InventoryMenu;
import com.ap.ui.widget.tabContents.TabManager;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class ControllerSystem extends IteratingSystem {
    private InventoryMenu inventoryMenu;
    private TabManager tabManager;
    private CraftingMenu craftingMenu;
    private TileSelectionSystem tileSelectionSystem;
    private int totalMovement = 0;

    public ControllerSystem(TabManager tabManager, CraftingMenu craftingMenu,  Engine engine) {
        super(Family.all(Controller.class).get());
        this.tileSelectionSystem = engine.getSystem(TileSelectionSystem.class);
        this.craftingMenu = craftingMenu;
        this.tabManager = tabManager;
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
                    totalMovement ++;
                }
                case Left -> {
                    moveEntity(entity, -1f, 0f);
                    totalMovement ++;
                }
                case Down -> {
                    moveEntity(entity, 0f, -1f);
                    totalMovement ++;
                }
                case Up -> {
                    moveEntity(entity, 0f, 1f);
                    totalMovement ++;
                } case OpenInventory -> {
                    tabManager.toggle();
                } case Click -> {
                    clicked();
                } case OpenCrafting -> {
                    craftingMenu.toggle();
                }
            }
        }

        // We processed all the pressed commands
        controller.getPressedCommands().clear();
        for (Command command : controller.getReleasedCommands()) {
            if(totalMovement <= 0) {
                continue;
            }
            switch (command) {
                case Right -> {
                    moveEntity(entity, -1f, 0f);
                    totalMovement --;
                }
                case Left -> {
                    moveEntity(entity, 1f, 0f);
                    totalMovement --;
                }
                case Down -> {
                    moveEntity(entity, 0f, 1f);
                    totalMovement --;;
                }
                case Up -> {
                    moveEntity(entity, 0f, -1f);
                    totalMovement --;
                }
            }
        }
        controller.getReleasedCommands().clear();

    }

    private void clicked() {
        if(tileSelectionSystem != null) {
            tileSelectionSystem.click();
        }
    }

    private void moveEntity(Entity entity, float dx, float dy) {
        Move move = Move.mapper.get(entity);
        if(move != null) {
            move.getDirection().x += dx;
            move.getDirection().y += dy;
        }
    }

    public void reset() {
        for(Entity entity :getEntities()) {
            if(Player.mapper.has(entity)) {
                Move move = Move.mapper.get(entity);
                move.getDirection().x = 0;
                move.getDirection().y = 0;
            }
        }
        totalMovement = 0;
    }
}
