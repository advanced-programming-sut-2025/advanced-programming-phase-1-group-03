package phi.ap.Controller;

import phi.ap.Controller.MenuControllers.MainMenuControllers.GameMenuController;
import phi.ap.model.*;
import phi.ap.model.items.Item;
import phi.ap.model.items.relations.Friendship;
import phi.ap.model.items.relations.TradeRequest;

import java.util.ResourceBundle;

public class TradeMenuController {
    public Result<String> startMenu() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Game players: \n");
        for(Player player : Game.getInstance().getPlayers())
            stringBuilder.append(player.getUser().getUsername() + "\n");
        stringBuilder.append(TradeRequest.getUnSeenTradeList(Game.getInstance().getCurrentPlayer()));
        return new Result<>(true, stringBuilder.toString());
    }
    public Result<String> offerTrade(String username, String typeOfTrade, String ItemName,
                                     String amountString, String priceString, String targetItemString, String targetAmountString) {
        Player target = Game.getInstance().getPlayerByUserName(username);
        if(target == null)
            return new Result<>(false, "There is no player with this userName.");
        GameMenuController gameMenuController = new GameMenuController();
        Item item = App.getInstance().getGameService().getItem(ItemName);
        if(item == null)
            return new Result<>(false, "There is no item with this name.");
        Integer amount;
        try {
            amount = Integer.parseInt(amountString);
        } catch (Exception e) {
            return new Result<>(false, "amount is invalid.");
        }
        if(priceString != null && targetItemString != null)
            return new Result<>(false, "You should specify only one method to trade.");
        ItemStack givingItemStack = new ItemStack(item, amount);
        Integer price = null;
        Item targetItem = null;
        Integer targetAmount = null;
        if(priceString != null) {
            try {
                price = Integer.parseInt(priceString);
            } catch (Exception e) {
                return new Result<>(false, "price is invalid.");
            }
        }
        else {
            targetItem = App.getInstance().getGameService().getItem(targetItemString);
            if(targetItem == null)
                return new Result<>(false, "targetItem invalid.");
            try {
                targetAmount = Integer.parseInt(targetAmountString);
            } catch (Exception e) {
                return new Result<>(false, "targetAmount invalid.");
            }
        }
        if(!typeOfTrade.equals("offer") && !typeOfTrade.equals("request"))
            return new Result<>(false, "type of trade invalid.");
        if(typeOfTrade.equals("offer")) {
            ItemStack tempItemStack = Game.getInstance().getCurrentPlayer().getInventoryManager().getItem(item);
            if(tempItemStack.getAmount() < amount)
                return new Result<>(false, "You don't have enough amount of " + item.getName());
        }
        else if(typeOfTrade.equals("request")) {
            if(price != null) {
                if(Game.getInstance().getCurrentPlayer().getGold() < price)
                    return new Result<>(false, "You don't have enough money.");
            }
            else {
                ItemStack tempItemStack = Game.getInstance().getCurrentPlayer().getInventoryManager().getItem(targetItem);
                if(tempItemStack.getAmount() < targetAmount)
                    return new Result<>(false, "You don't have enough amount of " + targetItem.getName());
            }
        }
        TradeRequest.getTrades().add(new TradeRequest(typeOfTrade, new ItemStack(item, amount),
                new ItemStack(targetItem, targetAmount), price, Game.getInstance().getCurrentPlayer(),
                Game.getInstance().getPlayerByUserName(username)));
        return new Result<>(true, "trade request added.");
    }
    public Result<String> showListOfTrades() {
        return new Result<>(true, TradeRequest.getTradeList(Game.getInstance().getCurrentPlayer()));
    }
    public Result<String> tradeResponse(String answer, String idString) {
        if(!answer.equals("accept") && !answer.equals("reject"))
            return new Result<>(false, "answer invalid");
        Integer id;
        try {
            id = Integer.parseInt(idString);
        } catch (Exception e) {
            return new Result<>(false, "id invalid");
        }
        TradeRequest tradeRequest = TradeRequest.getTradeById(id);
        if(tradeRequest == null)
            return new Result<>(false, "id invalid");
        if(!Game.getInstance().getCurrentPlayer().equals(tradeRequest.getGetter()))
            return new Result<>(false, "You are not part of this deal!");
        tradeRequest.setAnswer(answer);
        Player respondent = Game.getInstance().getCurrentPlayer();
        Player applicant = tradeRequest.getSender();
        if(answer.equals("reject")) {
            Friendship.AddXp(tradeRequest.getGetter(), tradeRequest.getSender(), -30);
            return new Result<>(false, "trade rejected.");
        }
        else {
            if(tradeRequest.getType().equals("offer")) {
                // remove targetItem from respondent
                if(applicant.getInventoryManager().getItem(tradeRequest.getItem().getItem()).getAmount() < (tradeRequest.getItem().getAmount()))
                    return new Result<>(false, "applicant doesn't have " + tradeRequest.getItem().getItem().getName());
                if(tradeRequest.getPrice() != null) {
                    if(respondent.getGold() < tradeRequest.getPrice())
                        return new Result<>(false, "You don't have enough money.");
                    respondent.setGold(respondent.getGold() -
                            tradeRequest.getPrice());
                    applicant.setGold(applicant.getGold() + tradeRequest.getPrice());
                }
                else {
                    if(Game.getInstance().getCurrentPlayer().getInventoryManager().getItem(tradeRequest.getTargetItem().
                            getItem()).getAmount() < tradeRequest.getTargetItem().getAmount())
                        return new Result<>(false, "You don't have enough amount of " +
                                tradeRequest.getTargetItem().getItem().getName());
                    respondent.getInventoryManager().removeItem(
                            tradeRequest.getTargetItem().getItem(), tradeRequest.getTargetItem().getAmount());
                    applicant.getInventoryManager().addItem(tradeRequest.getTargetItem());
                }
                respondent.getInventoryManager().addItem(tradeRequest.getItem());
                applicant.getInventoryManager().removeItem(tradeRequest.getItem().getItem(), tradeRequest.getItem().getAmount());
            }
            else {
                if(tradeRequest.getPrice() != null) {
                    if(applicant.getGold() < tradeRequest.getPrice())
                        return new Result<>(false, "applicant doesn't have enough money");
                    respondent.setGold(respondent.getGold() + tradeRequest.getPrice());
                    applicant.setGold(tradeRequest.getPrice() - applicant.getGold());
                }
                else {
                    if(applicant.getInventoryManager().getItem(tradeRequest.getTargetItem().getItem()).getAmount()
                            < tradeRequest.getTargetItem().getAmount())
                        return new Result<>(false, "applicant doesn't have enough amount of " +
                                tradeRequest.getTargetItem().getItem().getName());
                    respondent.getInventoryManager().addItem(tradeRequest.getTargetItem());
                    applicant.getInventoryManager().removeItem(tradeRequest.getTargetItem().getItem(), tradeRequest.getTargetItem().getAmount());
                }
                if(respondent.getInventoryManager().getItem(tradeRequest.getItem().
                        getItem()).getAmount() < tradeRequest.getItem().getAmount())
                    return new Result<>(false, "You don't have enough amount of " +
                            tradeRequest.getItem().getItem().getName());
                respondent.getInventoryManager().removeItem(
                        tradeRequest.getItem().getItem(), tradeRequest.getItem().getAmount());
                applicant.getInventoryManager().addItem(tradeRequest.getItem());
            }
            Friendship.AddXp(tradeRequest.getGetter(), tradeRequest.getSender(), +50);
            return new Result<>(true, "trade accepted.");
        }
    }
    public Result<String> showTradeHistory() {
        return new Result<>(true, TradeRequest.printTradeHistory(Game.getInstance().getCurrentPlayer()));
    }
}
