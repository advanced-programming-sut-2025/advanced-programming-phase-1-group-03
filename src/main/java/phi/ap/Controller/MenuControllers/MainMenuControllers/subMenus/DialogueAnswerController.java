package phi.ap.Controller.MenuControllers.MainMenuControllers.subMenus;

import phi.ap.model.App;
import phi.ap.model.Result;
import phi.ap.model.enums.Menus.Menu;
import phi.ap.model.npcStuff.State;
import phi.ap.model.npcStuff.dialogueStuff.DialogueNode;

public class DialogueAnswerController {
    private static DialogueAnswerController instance = null;
    private DialogueAnswerController() {}
    public static DialogueAnswerController getInstance() {
        if (instance == null) {
            instance = new DialogueAnswerController();
        }
        return instance;
    }

    private DialogueNode dialogue;
    private State state;

    public Result<String> runDialogue(DialogueNode dialogue, State state) {
        this.dialogue = dialogue;
        this.state = state;
        App.getInstance().changeMenu(Menu.DialogueAnwerMenu);
        state.setLastConversation(dialogue);
        return new Result<>(true, state.getNpc().getNpcName() + ": " + dialogue.getText());
    }

    public Result<String> goNext() {
        for (DialogueNode child : dialogue.getChildren()) {
            Result<String> result = child.runDialogue(state);
            if (result.success) {
                return result;
            }
        }
        App.getInstance().changeMenu(Menu.GameMenu);
        return new Result<>(true, "Meeting done!");
    }

    public Result<String> noAnswer() {
        if (dialogue.isAnswerNeed()) return new Result<>(false, "Yes or No?");
        return goNext();
    }

    public Result<String> getAnswer(String answer) {
        if (!dialogue.isAnswerNeed()) return goNext();
        state.setLastAnswer(answer);
        return goNext();
    }
}
