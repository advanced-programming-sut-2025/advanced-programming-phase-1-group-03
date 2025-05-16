package phi.ap.model.npcStuff.dialogueStuff;

import phi.ap.model.npcStuff.State;

public interface Condition {
    public boolean matches(State state);
}
