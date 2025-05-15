package phi.ap.model.enums.npcStuff;

import phi.ap.model.npcStuff.State;

public enum NPCFriendshipManager {
    Level0(0),
    Level1(1) {
        @Override
        public void setUp(State state) {
            super.setUp(state);
        }
    },
    Level2(2) {
        @Override
        public void setUp(State state) {
            super.setUp(state);
        }
    },
    Level3(3) {
        @Override
        public void setUp(State state) {
            super.setUp(state);
        }
    }
    ;
    private int levelID;

    public void setUp(State state) {

    }


    NPCFriendshipManager(int levelID) {
        this.levelID = levelID;
    }

    public int getLevelID() {
        return levelID;
    }

    public static NPCFriendshipManager getNPCFriendshipManager(int levelID) {
        for (NPCFriendshipManager manager : NPCFriendshipManager.values()) {
            if (manager.levelID == levelID) {
                return manager;
            }
        }
        return null;
    }

}
