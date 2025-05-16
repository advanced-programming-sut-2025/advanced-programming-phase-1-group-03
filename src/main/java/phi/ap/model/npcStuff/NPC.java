package phi.ap.model.npcStuff;

import phi.ap.model.App;
import phi.ap.model.Game;
import phi.ap.model.Player;
import phi.ap.model.enums.npcStuff.NPCTypes;
import phi.ap.model.items.Item;
import phi.ap.model.npcStuff.dialogueStuff.DialogueNode;

import java.util.ArrayList;

public class NPC extends Item {
    private NPCTypes type;
    private ArrayList<State> states = new ArrayList<>();

    public NPC(NPCTypes type) {
        this.type = type;
        setName(type.toString());
        for (Player player : Game.getInstance().getPlayers()) {
            states.add(new State(player, type));
        }
        fillTile(type.getTile());
    }

    public NPCTypes getType() {
        return type;
    }

    public State getState(Player player) {
        return states.get(App.getInstance().getPlayerService().getPlayerIndex(player));
    }

    @Override
    public void doTask() {

    }
}
