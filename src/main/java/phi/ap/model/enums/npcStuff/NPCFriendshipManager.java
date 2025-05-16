package phi.ap.model.enums.npcStuff;

import phi.ap.model.npcStuff.State;

public enum NPCFriendshipManager {
    Level0(0, 0),
    Level1(1, 200) {
        @Override
        public void setUp(State state) {
            super.setUp(state);
        }
    },
    Level2(2, 400) {
        @Override
        public void setUp(State state) {
            super.setUp(state);
        }
    },
    Level3(3, 600) {
        @Override
        public void setUp(State state) {
            super.setUp(state);
        }
    }
    ;
    private int levelID;
    private int minXP;

    public static final int maxXp = 799;

    public void setUp(State state) {

    }


    NPCFriendshipManager(int levelID, int minXP) {

        this.levelID = levelID;
        this.minXP = minXP;
    }

    public int getLevelID() {
        return levelID;
    }

    public int getMinXP() {
        return minXP;
    }

    public static NPCFriendshipManager getNPCFriendshipManager(int levelID) {
        for (NPCFriendshipManager manager : NPCFriendshipManager.values()) {
            if (manager.levelID == levelID) {
                return manager;
            }
        }
        return null;
    }

    public static NPCFriendshipManager getNPCFriendshipManagerByXp(int xp) {
        NPCFriendshipManager[] values = values();
        for (int i = values.length - 1; i >= 0; i--) {
            NPCFriendshipManager value = values[i];
            if (xp >= value.minXP)
                return value;
        }
        return null;
    }

}
