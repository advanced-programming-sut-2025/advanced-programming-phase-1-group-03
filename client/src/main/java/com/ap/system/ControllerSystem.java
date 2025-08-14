package com.ap.system;

import com.ap.component.Controller;
import com.ap.component.Move;
import com.ap.component.Network;
import com.ap.component.Player;
import com.ap.input.Command;
import com.ap.network.GameClient;
import com.ap.packet.PlayerInfo;
import com.ap.packet.TradeRoomStarter;
import com.ap.screen.GameScreen;
import com.ap.ui.widget.*;
import com.ap.ui.widget.cheatCode.CheatCodeBox;
import com.ap.ui.widget.tabContents.TabManager;
import com.ap.ui.widget.tabContents.Tabs;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import java.util.ArrayList;

public class ControllerSystem extends IteratingSystem {
    private TabManager tabManager;
    private CraftingMenu craftingMenu;
    private CookingMenu cookingMenu;
    private CheatCodeBox cheatCodeBox;
    private LeaderBoard leaderBoard;
    private EmojiPanel emojiPanel;
    private RefrigeratorMenu refrigeratorMenu;
    private TileSelectionSystem tileSelectionSystem;
    private int totalMovement = 0;

    private GameClient gameClient;
    private GameScreen gameScreen;

    public ControllerSystem(TabManager tabManager,
                            CraftingMenu craftingMenu,
                            CookingMenu cookingMenu,
                            CheatCodeBox cheatCodeBox,
                            LeaderBoard leaderBoard,
                            EmojiPanel emojiPanel,
                            RefrigeratorMenu refrigeratorMenu,
                            Engine engine,
                            GameClient gameClient,
                            GameScreen gameScreen) {
        super(Family.all(Controller.class).get());
        this.gameClient = gameClient;
        this.tileSelectionSystem = engine.getSystem(TileSelectionSystem.class);
        this.craftingMenu = craftingMenu;
        this.cookingMenu = cookingMenu;
        this.tabManager = tabManager;
        this.cheatCodeBox = cheatCodeBox;
        this.gameScreen = gameScreen;
        this.leaderBoard = leaderBoard;
        this.emojiPanel = emojiPanel;
        this.refrigeratorMenu = refrigeratorMenu;
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
                    gameClient.getSender().placeCarrier();
                } case Talk -> {
                    gameScreen.sendVoiceMessage = true;
                } case OpenLeaderBoard -> {
                    leaderBoard.toggle();
                } case OpenTradeStarter -> {
                    gameScreen.getTradeStarterMenu().toggle();
//                    TradeRoomStarter starter = new TradeRoomStarter(new PlayerInfo("user0", Helper.random(0, 7)), new ArrayList<>(), new PlayerInfo("user1", Helper.random(0, 7)), new ArrayList<>());
//                    gameScreen.getTradeMenu().makeInstance(starter);
                } case OpenEmoteMenu -> {
                    emojiPanel.toggle();
                } case OpenRefrigerator -> {
                    refrigeratorMenu.toggle();
                } case Hug -> {
                    hug();
                } case Gift -> {
                    gift();
                } case OpenFishing -> {
                    gameScreen.toggleFish();
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

    private void gift() {
        if(tileSelectionSystem != null) {
            tileSelectionSystem.gift();
        }
    }

    private void clicked() {
        if(tileSelectionSystem != null) {
            tileSelectionSystem.click();
        }
    }

    private void hug() {
        if(tileSelectionSystem != null) {
            tileSelectionSystem.hug();
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
