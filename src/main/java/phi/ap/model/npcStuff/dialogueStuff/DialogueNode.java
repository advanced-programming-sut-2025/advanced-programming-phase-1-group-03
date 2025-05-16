package phi.ap.model.npcStuff.dialogueStuff;

import phi.ap.Controller.MenuControllers.MainMenuControllers.subMenus.DialogueAnswerController;
import phi.ap.model.Result;
import phi.ap.model.npcStuff.State;

import java.util.ArrayList;

public class DialogueNode {
    private String text;
    private Condition condition;
    private DialogueNode parent = null;
    private ArrayList<DialogueNode> children = new ArrayList<>();
    private String topic = "";
    private boolean answerNeed = false;

    public DialogueNode(String text, Condition condition) {
        this.text = text;
        this.condition = condition;
        answerNeed = false;
    }

    public DialogueNode(String text, Condition condition, boolean answerNeed) {
        this.text = text;
        this.condition = condition;
        this.answerNeed = answerNeed;
    }

    public DialogueNode(String text, Condition condition, boolean answerNeed, String topic) {
        this.text = text;
        this.condition = condition;
        this.answerNeed = answerNeed;
        this.topic = topic;
    }

    public boolean isAnswerNeed() {
        return answerNeed;
    }

    public void setAnswerNeed(boolean answerNeed) {
        this.answerNeed = answerNeed;
    }

    public String getText() {
        return text;
    }

    public Condition getCondition() {
        return condition;
    }

    public DialogueNode getParent() {
        return parent;
    }

    public void addChild(DialogueNode child) {
        children.add(child);
        child.parent = this;
    }
    public boolean matches(State state) {
        return condition == null || condition.matches(state);
    }

    public Result<String> runDialogue(State state) {
        if (!matches(state)) return new Result<>(false, "");
        return DialogueAnswerController.getInstance().runDialogue(this, state);
    }

    public ArrayList<DialogueNode> getChildren() {
        return children;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
