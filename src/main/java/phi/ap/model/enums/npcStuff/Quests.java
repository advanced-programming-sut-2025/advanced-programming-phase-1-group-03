package phi.ap.model.enums.npcStuff;

import phi.ap.model.App;
import phi.ap.model.ItemStack;
import phi.ap.model.enums.*;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Recipe;
import phi.ap.model.npcStuff.State;
import phi.ap.model.npcStuff.dialogueStuff.Condition;
import phi.ap.model.npcStuff.dialogueStuff.ConditionTypes;

import java.util.ArrayList;
import java.util.List;

public enum Quests {
    Sebastian3("Sebastian",
            ConditionTypes.currentDayMore(40),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(ForagingMineralTypes.IronBar.toString()), 50)
            )),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(ForagingMineralTypes.Diamond.toString()), 2)
            ))
    ),
    Sebastian1("Sebastian",
            ConditionTypes.always(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(FoodTypes.PumpkinPie.toString()), 1)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            int c = 1;
            if (state.getFriendshipLevel() >= 2) c = 2;
            state.getPlayer().setGold(state.getPlayer().getGold() + 5000 * c);
        }
        public String details() {
            StringBuilder res = new StringBuilder();
            res.append(super.details());
            res.append("Rewards: 5000$\n");
            return res.toString();
        }
    },
    Sebastian2("Sebastian",
            ConditionTypes.friendshipMore(1),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(StoneTypes.RegularStone.toString()), 150)
            )),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(ForagingMineralTypes.Quartz.toString()), 50)
            ))
    ),
    Abigail1("Abigail",
            ConditionTypes.always(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(ForagingMineralTypes.GoldBar.toString()), 1)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            int c = 1;
            if (state.getFriendshipLevel() >= 2) c = 2;
            state.advanceFriendshipXp(200 * c);
        }
        public String details() {
            StringBuilder res = new StringBuilder();
            res.append(super.details());
            res.append("Rewards: 200 friendship xp\n");
            return res.toString();
        }
    },
    Abigail2("Abigail",
            ConditionTypes.friendshipMore(2),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(CropsTypes.Pumpkin.toString()), 1)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            int c = 1;
            if (state.getFriendshipLevel() >= 2) c = 2;
            state.getPlayer().setGold(state.getPlayer().getGold() + 500 * c);
        }
        public String details() {
            StringBuilder res = new StringBuilder();
            res.append(super.details());
            res.append("Rewards: 500$\n");
            return res.toString();
        }
    },
    Abigail3("Abigail",
            ConditionTypes.currentDayMore(30),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(CropsTypes.Wheat.toString()), 50)
            )),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(CraftingTypes.Sprinkler.toString()), 1)
            ))
    ),
    Harvey2("Harvey",
            ConditionTypes.friendshipMore(2),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(CropsTypes.Tulip.toString()), 1)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            int c = 1;
            if (state.getFriendshipLevel() >= 2) c = 2;
            state.getPlayer().setGold(state.getPlayer().getGold() + 750 * c);
        }
        public String details() {
            StringBuilder res = new StringBuilder();
            res.append(super.details());
            res.append("Rewards: 750$\n");
            return res.toString();
        }
    },
    Harvey1("Harvey",
            ConditionTypes.always(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(FishTypes.Salmon.toString()), 1)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            int c = 1;
            if (state.getFriendshipLevel() >= 2) c = 2;
            state.advanceFriendshipXp(200 * c);
        }
        public String details() {
            StringBuilder res = new StringBuilder();
            res.append(super.details());
            res.append("Rewards: 200 friendship xp\n");
            return res.toString();
        }
    },
    Harvey3("Harvey",
            ConditionTypes.currentDayMore(25),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(FoodTypes.Wine.toString()), 1)
            )),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(FoodTypes.Salad.toString()), 5)
            ))
    ),
    Lia1("Lia",
            ConditionTypes.always(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem("Wood"), 10)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            int c = 1;
            if (state.getFriendshipLevel() >= 2) c = 2;
            state.getPlayer().setGold(state.getPlayer().getGold() + 500 * c);
        }
        public String details() {
            StringBuilder res = new StringBuilder();
            res.append(super.details());
            res.append("Rewards: 500$\n");
            return res.toString();
        }
    },
    Lia2("Lia",
            ConditionTypes.friendshipMore(2),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(FishTypes.Salmon.toString()), 1)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            state.getPlayer().getCookingRecipes().add(FoodTypes.SalmonDinner.getRecipe());
        }
        public String details() {
            StringBuilder res = new StringBuilder();
            res.append(super.details());
            res.append("Rewards: SalmonDinner cooking recipe\n");
            return res.toString();
        }
    },
    Lia3("Lia",
            ConditionTypes.currentDayMore(35),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem("Wood"), 200)
            )),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(CraftingTypes.DeluxeScarecrow.toString()), 3)
            ))
    ),
    RobinJr1("RobinJr",
            ConditionTypes.always(),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem("Wood"), 80)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            int c = 1;
            if (state.getFriendshipLevel() >= 2) c = 2;
            state.getPlayer().setGold(state.getPlayer().getGold() + 1000 * c);
        }
        public String details() {
            StringBuilder res = new StringBuilder();
            res.append(super.details());
            res.append("Rewards: 1000$\n");
            return res.toString();
        }
    },
    RobinJr2("RobinJr",
            ConditionTypes.friendshipMore(2),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(ForagingMineralTypes.IronBar.toString()), 10)
            )),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem(CraftingTypes.BeeHouse.toString()), 3)
            ))
    ),
    RobinJr3("RobinJr",
            ConditionTypes.currentDayMore(45),
            new ArrayList<>(List.of(
                    new ItemStack(App.getInstance().getGameService().getItem("Wood"), 1000)
            )),
            new ArrayList<>(List.of(
            ))
    ) {
        @Override
        public void addRewards(State state) {
            super.addRewards(state);
            int c = 1;
            if (state.getFriendshipLevel() >= 2) c = 2;
            state.getPlayer().setGold(state.getPlayer().getGold() + 25000 * c);
        }
        public String details() {
            StringBuilder res = new StringBuilder();
            res.append(super.details());
            res.append("Rewards: 25000$\n");
            return res.toString();
        }
    }
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
        state.getPlayer().getDoneQuests().add(this);
        state.getPlayer().getActiveQuests().remove(this);
    }

    public void addRewards(State state) {
        for (ItemStack stack : rewards) {
            int c = 1;
            if (state.getFriendshipLevel() >= 2) c = 2;
            state.getPlayer().getInventoryManager().addItem(stack.getItem(), stack.getAmount() * 2);
        }
    }

    public String getOwner() {
        return owner;
    }

    public String details() {
        StringBuilder res = new StringBuilder();
        res.append("NPC: " + owner + "\n");
        res.append("Requiements: " + requirements + "\n");
        res.append("Rewards: " + rewards + "\n");
        return res.toString();
    }
}
