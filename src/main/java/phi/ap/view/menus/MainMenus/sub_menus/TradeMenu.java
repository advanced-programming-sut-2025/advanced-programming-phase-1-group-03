package phi.ap.view.menus.MainMenus.sub_menus;

import phi.ap.Controller.TradeMenuController;
import phi.ap.model.enums.commands.DialogueAnswerCommands;
import phi.ap.model.enums.commands.TradeMenuCommands;
import phi.ap.view.AppMenu;

import java.util.regex.Matcher;

public class TradeMenu extends AppMenu {
    TradeMenuController controller = new TradeMenuController();
    public void check(String input) {
        Matcher matcher;
        if ((matcher = TradeMenuCommands.StartTrade.getMatcher(input)) != null) {
            System.out.println(controller.startMenu());
        } else if ((matcher = TradeMenuCommands.Trade.getMatcher(input)) != null) {
            System.out.println(controller.offerTrade(matcher.group("userName"), matcher.group("type"),
                    matcher.group("item"), matcher.group("amount"), matcher.group("price"),
                    matcher.group("targetItem"), matcher.group("targetAmount")));
        } else if ((matcher = TradeMenuCommands.TradeList.getMatcher(input)) != null) {
            System.out.println(controller.showListOfTrades());
        } else if ((matcher = TradeMenuCommands.TradeResponse.getMatcher(input)) != null) {
            System.out.println(controller.tradeResponse(matcher.group("condition"), matcher.group("id")));
        }  else if ((matcher = TradeMenuCommands.TradeHistory.getMatcher(input)) != null) {
            System.out.println(controller.showTradeHistory());
        }
        else {
            System.out.println("invalid input");
        }
    }
    @Override
    public void start() {
        System.out.println("Redirecting to trade menu....");
    }
}
