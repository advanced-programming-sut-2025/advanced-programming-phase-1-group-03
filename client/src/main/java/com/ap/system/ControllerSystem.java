package com.ap.system;

import com.ap.component.Controller;
import com.ap.component.Move;
import com.ap.component.Network;
import com.ap.component.Player;
import com.ap.input.Command;
import com.ap.network.GameClient;
import com.ap.screen.GameScreen;
import com.ap.ui.widget.LeaderBoard;
import com.ap.ui.widget.cheatCode.CheatCodeBox;
import com.ap.ui.widget.CookingMenu;
import com.ap.ui.widget.CraftingMenu;
import com.ap.ui.widget.tabContents.TabManager;
import com.ap.ui.widget.tabContents.Tabs;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class ControllerSystem extends IteratingSystem {
    private TabManager tabManager;
    private CraftingMenu craftingMenu;
    private CookingMenu cookingMenu;
    private CheatCodeBox cheatCodeBox;
    private LeaderBoard leaderBoard;
    private TileSelectionSystem tileSelectionSystem;
    private CarrierSystem carrierSystem;
    private int totalMovement = 0;

    private GameClient gameClient;
    private GameScreen gameScreen;

    public ControllerSystem(TabManager tabManager,
                            CraftingMenu craftingMenu,
                            CookingMenu cookingMenu,
                            CheatCodeBox cheatCodeBox,
                            LeaderBoard leaderBoard,
                            Engine engine,
                            GameClient gameClient,
                            GameScreen gameScreen) {
        super(Family.all(Controller.class).get());
        this.gameClient = gameClient;
        this.tileSelectionSystem = engine.getSystem(TileSelectionSystem.class);
        this.carrierSystem = engine.getSystem(CarrierSystem.class);
        this.craftingMenu = craftingMenu;
        this.cookingMenu = cookingMenu;
        this.tabManager = tabManager;
        this.cheatCodeBox = cheatCodeBox;
        this.gameScreen = gameScreen;
        this.leaderBoard = leaderBoard;
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
                    moveEntity(entity, 1f, 0f, true);
                    totalMovement ++;
                }
                case Left -> {
                    moveEntity(entity, -1f, 0f, true);
                    totalMovement ++;
                }
                case Down -> {
                    moveEntity(entity, 0f, -1f, true);
                    totalMovement ++;
                }
                case Up -> {
                    moveEntity(entity, 0f, 1f, true);
                    totalMovement ++;
                } case OpenInventory -> {
                    tabManager.toggle();
                } case Click -> {
                    clicked();
                } case OpenCrafting -> {
                    if (!tabManager.isShowing()) {
                        tabManager.toggle();
                        tabManager.setCurrentContent(Tabs.Crafting);
                    } else {
                        tabManager.toggle();
                    }
                } case OpenCooking -> {
                        cookingMenu.toggle();
                } case OpenCheatCode -> {
                    cheatCodeBox.toggle();
                } case Place -> {
                    carrierSystem.place();
                } case Talk -> {
                    gameScreen.sendVoiceMessage = true;
                } case OpenLeaderBoard -> {
                    leaderBoard.toggle();
                } case OpenTradeStarter -> {
                    gameScreen.getTradeStarterMenu().toggle();
                }

            }
        }

        // We processed all the pressed commands
        controller.getPressedCommands().clear();
        for (Command command : controller.getReleasedCommands()) {
            if(command == Command.Talk) {
                gameScreen.sendVoiceMessage = false;
            }

            if(totalMovement <= 0) {
                continue;
            }
            switch (command) {
                case Right -> {
                    moveEntity(entity, -1f, 0f, false);
                    totalMovement --;
                }
                case Left -> {
                    moveEntity(entity, 1f, 0f, false);
                    totalMovement --;
                }
                case Down -> {
                    moveEntity(entity, 0f, 1f, false);
                    totalMovement --;;
                }
                case Up -> {
                    moveEntity(entity, 0f, -1f, false);
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

    private void moveEntity(Entity entity, float dx, float dy, boolean isKeyDown) {
        if(Network.mapper.has(entity)) {
            gameClient.getSender().sendMove(dx, dy, isKeyDown);
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
