package phi.ap.view.menus.MainMenus.sub_menus;

import phi.ap.Controller.MenuControllers.MainMenuControllers.subMenus.DialogueAnswerController;
import phi.ap.model.enums.commands.DialogueAnswerCommands;
import phi.ap.model.enums.commands.GameMenuCommands;
import phi.ap.view.AppMenu;

import java.util.regex.Matcher;

public class DialogueAnswerMenu extends AppMenu {

    DialogueAnswerController controller = DialogueAnswerController.getInstance();
    @Override
    public void check(String input) {
        Matcher matcher;
        if ((matcher = DialogueAnswerCommands.AnswerYesNo.getMatcher(input)) != null) {
            System.out.println(controller.getAnswer(matcher.group("answer")));
        } else if ((matcher = DialogueAnswerCommands.Enter.getMatcher(input)) != null) {
            System.out.println(controller.noAnswer());
        } else {
            System.out.println("invalid input");
        }
    }

    @Override
    public void start() {

    }
}
