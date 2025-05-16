package phi.ap.model.enums.npcStuff;

import phi.ap.model.App;
import phi.ap.model.ItemStack;
import phi.ap.model.enums.*;
import phi.ap.model.items.Item;
import phi.ap.model.npcStuff.State;
import phi.ap.model.npcStuff.dialogueStuff.Condition;
import phi.ap.model.npcStuff.dialogueStuff.ConditionTypes;

import java.util.ArrayList;
import java.util.List;

public enum Quests {
    Sebastian1("Sebastian",
            ConditionTypes.never(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(ForagingMineralTypes.IronBar.toString()), 50)
            )),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(ForagingMineralTypes.Diamond.toString()), 2)
            ))
    ),
    Sebastian2("Sebastian",
            ConditionTypes.never(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(FoodTypes.PumpkinPie.toString()), 1)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            state.getPlayer().setGold(state.getPlayer().getGold() + 5000);
        }
    },
    Sebastian3("Sebastian",
            ConditionTypes.never(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(StoneTypes.RegularStone.toString()), 150)
            )),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(ForagingMineralTypes.Quartz.toString()), 50)
            ))
    ),
    Abigail1("Abigail",
            ConditionTypes.never(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(ForagingMineralTypes.GoldBar.toString()), 1)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            state.advanceFriendshipXp(200);
        }
    },
    Abigail2("Abigail",
            ConditionTypes.never(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(CropsTypes.Pumpkin.toString()), 1)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            state.getPlayer().setGold(state.getPlayer().getGold() + 500);
        }
    },
    Abigail3("Abigail",
            ConditionTypes.never(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(CropsTypes.Wheat.toString()), 50)
            )),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(CraftingTypes.Sprinkler.toString()), 1)
            ))
    ),
    Harvey1("Harvey",
            ConditionTypes.never(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(CropsTypes.Tulip.toString()), 1)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            state.getPlayer().setGold(state.getPlayer().getGold() + 750);
        }
    },
    Harvey2("Harvey",
            ConditionTypes.never(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(FishTypes.Salmon.toString()), 1)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            state.getPlayer().setGold(state.getPlayer().getGold() + 500);
        }
    },
    Harvey3("Harvey",
            ConditionTypes.never(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(CropsTypes.Wheat.toString()), 50)
            )),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(CraftingTypes.Sprinkler.toString()), 1)
            ))
    )
    ;
    private Condition activeChecker;
    private ArrayList<ItemStack> requirements;
    private ArrayList<ItemStack> rewards;
    private String owner;

    Quests(String owner, Condition activeChecker, ArrayList<ItemStack> requirements, ArrayList<ItemStack> rewards) {
        this.owner = owner;
        this.activeChecker = activeChecker;
        this.requirements = requirements;
        this.rewards = rewards;
    }

    public boolean activeCheck(State state) {
        return activeChecker.matches(state);
    }

    public boolean reqCheck(State state) {
        for (ItemStack stack : requirements) {
            if (state.getPlayer().getInventoryManager().getItem(stack.getItem()).getAmount() < stack.getAmount()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<ItemStack> getRewards() {
        return rewards;
    }

    public void doQuest(State state) {
        for (ItemStack stack : requirements) {
            state.getPlayer().getInventoryManager().removeItem(stack.getItem(), stack.getAmount());
        }
    }

    public void addRewards(State state) {
        for (ItemStack stack : rewards) {
            state.getPlayer().getInventoryManager().addItem(stack.getItem(), stack.getAmount());
        }
    }

    public String getOwner() {
        return owner;
    }
}
