package io.ruin.model.item.containers;

import io.ruin.Server;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.ItemContainerG;
import io.ruin.services.Loggers;
import io.ruin.utility.TickDelay;

import java.util.Arrays;

import static io.ruin.cache.ItemID.COINS_995;

public class Trade extends ItemContainer {

    /**
     * Initiation
     */

    private int stage = 0;

    private boolean accepted;

    private Trade targetTrade;

    public Trade(Player player) {
        init(player, 28, -1, 64212, 90, false);
        mirror(-2, 60981, 90);
    }

    private void init(Trade targetTrade) {
        close();
        this.targetTrade = targetTrade;
        this.requestUserId = -1;
        update(null);
    }

    private void sendMessage(String message) {
        player.getPacketSender().sendMessage(message, null, 102);
    }

    /**
     * Sigmund
     */

    private boolean sigmund;

    public void tradeSigmund() {
        Trade sigmundTrade = new Trade(null);
        sigmundTrade.sigmund = true;
        init(sigmundTrade);
    }

    private void updateSigmund() {
        long price = 0L;
        for(Item item : getItems()) {
            if(item != null)
                price += 0;//(long) SigmundTheMerchant.getPrice(player, item) * item.getAmount();
        }
        Item coins = targetTrade.get(0);
        if(price == 0) {
            if(coins == null)
                return;
            coins.remove();
        } else {
            if(price > Integer.MAX_VALUE)
                price = Integer.MAX_VALUE;
            if(coins == null)
                targetTrade.set(0, new Item(COINS_995, (int) price));
            else
                coins.setAmount((int) price);
        }
        targetTrade.sendUpdates(this);
    }


    /**
     * Requesting
     */

    private TickDelay requestEnd;

    private int requestUserId = -1;

    public void request(Player target) {
        if (player.getGameMode().isIronMan() && !target.isAdmin()) {
            player.sendMessage("Ironmen stand alone.");
            return;
        }
        if(player.jailerName != null) {
            player.sendMessage("You can't trade while jailed.");
            return;
        }
        if(target.jailerName != null) {
            player.sendMessage("You can't trade with jailed players.");
            return;
        }
        if(player.getDuel().stage >= 4) {
            player.sendMessage("You can't trade while in a duel.");
            return;
        }
        if(player.joinedTournament) {
            player.sendMessage("You can't trade while you're signed up for a tournament.");
            return;
        }
        if(player.supplyChestRestricted) {
            player.sendMessage("The power of the supply chest prevents you from trading!");
            return;
        }
        if(target.joinedTournament) {
            player.sendMessage("This player can't trade right now.");
            return;
        }
        if(target.getGameMode().isIronMan() && !player.isAdmin()) {
            player.sendMessage(target.getName() + " is an ironman and so cannot trade.");
            return;
        }
        if(target.isLocked()) {
            player.sendMessage("This player is currently busy.");
            return;
        }
        Trade targetTrade = target.getTrade();
        if(targetTrade.requestUserId == player.getUserId() && targetTrade.requestEnd.isDelayed()) {
            init(targetTrade);
            targetTrade.init(this);
            return;
        }
        if(requestEnd == null)
            requestEnd = new TickDelay();
        requestEnd.delay(25);
        requestUserId = target.getUserId();

        target.getPacketSender().sendMessage(player.getName() + " wishes to trade with you.", player.getName(), 101);
        sendMessage("Sending trade offer...");
        if(player.wildernessLevel > 0)
            sendMessage("Trading in the Wilderness is dangerous - you might get killed!");
    }

    /**
     * Updating
     */

    private void update(ItemContainerG mirrorContainer) {
        if(mirrorContainer == null) {
            mirrorContainer = this;
            open();
        } else {
            accepted = false;
            if(targetTrade.sigmund) {
                updateState();
                updateSigmund();
                sendUpdates(null);
                return;
            }

            targetTrade.accepted = false;
            targetTrade.updateState();
            targetTrade.updateSlots();
            updateState();
        }
        sendUpdates(mirrorContainer);
    }

    /**
     * Interfaces
     */

    private void open() {
        stage = 1;
        sendAll = true;
        String targetName = targetTrade.sigmund ? "Sigmund The Merchant" : targetTrade.player.getName();
        player.getPacketSender().sendClientScript(209, "s", targetName);
        Config.MY_TRADE_MODIFIED.set(player, 0);
        Config.OTHER_TRADE_MODIFIED.set(player, 0);
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, 335);
        player.openInterface(InterfaceType.INVENTORY, 336);
        player.getPacketSender().sendString(335, 24, player.getName() + " offers:");
        player.getPacketSender().sendString(335, 27, targetName + " offers:");
        player.getPacketSender().sendString(335, 31, "Trading With: " + targetName);
        updateState();
        player.getPacketSender().sendAccessMask(335, 25, 0, 27, 1086);
        player.getPacketSender().sendAccessMask(335, 28, 0, 27, 1024);
        player.getPacketSender().sendClientScript(149, "IviiiIsssss", 22020096, 93, 4, 7, 0, -1, "Offer<col=ff9040>", "Offer-5<col=ff9040>", "Offer-10<col=ff9040>", "Offer-All<col=ff9040>", "Offer-X<col=ff9040>");
        player.getPacketSender().sendAccessMask(336, 0, 0, 27, 1086);
        updateSlots();
    }

    private void updateState() {
        String s = null;
        if(accepted)
            s = "Waiting for other player...";
        else if(targetTrade.accepted)
            s = "Other player has accepted.";
        if(stage == 1)
            player.getPacketSender().sendString(335, 30, s == null ? "" : s);
        else
            player.getPacketSender().sendString(334, 4, s == null ? "Are you sure you want to make this trade?" : s);
    }

    private void updateSlots() {
        if(targetTrade.sigmund)
            player.getPacketSender().sendString(335, 9, "Offer items you would like to sell to Sigmund.");
        else
            player.getPacketSender().sendString(335, 9, targetTrade.player.getName() + " has " + targetTrade.player.getInventory().getFreeSlots() + " free inventory slots.");
    }

    public void close() {
        if(targetTrade == null) {
            return;
        }
        if(!targetTrade.sigmund) {
            targetTrade.sendMessage("The other player declined the trade.");
            targetTrade.destroy(true);
            targetTrade.closeInterfaces();
        }
        sendMessage("You declined the trade.");
        destroy(true);
    }

    private void closeInterfaces() {
        player.closeInterface(InterfaceType.MAIN);
        player.closeInterface(InterfaceType.INVENTORY);
    }

    /**
     * Accept
     */

    private void accept(boolean firstScreen) {
        if(accepted)
            return;
        if(firstScreen) {
            if(stage != 1)
                return;
            int slotsRequired = 0;
            for(Item item : targetTrade.getItems()) {
                if(item == null || (item.getDef().stackable && player.getInventory().hasId(item.getId())))
                    continue;
                slotsRequired++;
            }
            if(player.getInventory().getFreeSlots() < slotsRequired) {
                sendMessage("You don't have enough inventory space to accept this trade.");
                return;
            }
            if(targetTrade.sigmund) {
                second();
                return;
            }
            if(targetTrade.accepted) {
                second();
                targetTrade.second();
                return;
            }
        } else {
            if(stage != 2)
                return;
            if(targetTrade.sigmund) {
                destroy(false);
                closeInterfaces();
                return;
            }
            if(targetTrade.accepted) {
                targetTrade.destroy(false);
                targetTrade.closeInterfaces();
                destroy(false);
                closeInterfaces();
                return;
            }
        }
        accepted = true;
        targetTrade.updateState();
        updateState();
    }

    /**
     * Offer
     */

    public void offer(Item item, int amount) {
        if(stage != 1) {
            /* not on the first screen! */
            return;
        }
        if(targetTrade.sigmund) {
           // if (SigmundTheMerchant.getPrice(player, item) == 0 || !item.getDef().tradeable || item.getDef().free) {
                sendMessage("Sigmund has no interest in buying that item.");
                return;
            //}
        } else if (!item.getDef().tradeable && !player.isAdmin() && !targetTrade.player.isAdmin()) {
            sendMessage("You can't trade this item.");
            return;
        } else if (!canTrade(item)) {
            sendMessage("You can't trade this item.");
            return;
        }
        int moved = item.move(item.getId(), amount, this);
        if(moved <= 0) {
            sendMessage("Not enough space in your trade.");
            return;
        }
        update(targetTrade);
    }

    private boolean canTrade(Item item) {
        return !item.hasAttributes();
    }

    /**
     * Remove
     */

    public void remove(Item item, int amount) {
        if(stage != 1) {
            /* not on the first screen! */
            return;
        }
        int moved = item.move(item.getId(), amount, player.getInventory());
        if(moved <= 0) {
            Server.logWarning(player.getName() + " failed to remove item (" + item.getId() + ", " + item.getAmount() + ") from trade, this should NEVER happen!");
            return;
        }
        update(targetTrade);

        if(targetTrade.sigmund) {
            /* no need to warn */
            return;
        }

        player.getPacketSender().sendClientScript(765, "Ii", 0, item.getSlot());
        Config.MY_TRADE_MODIFIED.set(player, 1);

        targetTrade.player.getPacketSender().sendClientScript(765, "Ii", 1, item.getSlot());
        Config.OTHER_TRADE_MODIFIED.set(targetTrade.player, 1);
    }

    /**
     * Second
     */

    private void second() {
        stage = 2;
        accepted = false;
        player.getPacketSender().sendString(334, 4, "Are you sure you want to make this trade?");
        player.getPacketSender().sendString(334, 23, "You are about to give:");
        player.getPacketSender().sendString(334, 24, "In return you will receive:");
        player.getPacketSender().sendString(334, 30, "Trading with:<br>" + (targetTrade.sigmund ? "Sigmund The Merchant" : targetTrade.player.getName()));
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, 334);
    }

    /**
     * Destroy
     */

    private void destroy(boolean returnItems) {
        if(returnItems) {
            /**
             * Return my items
             */
            for(Item item : getItems()) {
                if(item != null)
                    player.getInventory().add(item);
            }
        } else if(targetTrade.sigmund) {
            /*
             * Trade coins
             */
            Item coins = targetTrade.get(0);
            if(coins != null)
                player.getInventory().add(coins);
            sendMessage("Your trade with Sigmund the Merchant was successful.");
            if (coins != null && coins.getAmount() > 1_000_000) {
                String format = String.format("Sigmund: Player:[%s] Coins:[%d] Sold:[%s] ", player.getName(), coins.getAmount(), Arrays.toString(getItems()));
                ServerWrapper.log(format);
            }
            for(Item item : getItems()) {
                if(item != null)
                    Loggers.logSigmund(player.getUserId(), item.getId(), item.getAmount());
            }
            //if(targetTrade.targetTrade != null && (itemCount > 0 || targetTrade.itemCount > 0))
               // Loggers.logTrade(player.getUserId(), player.getName(), player.getIp(), -1, "Sigmund the Merchant", "", items, targetTrade.items);
        } else {
            /*
             * Trade my items
             */
            for(Item item : getItems()) {
                if(item != null)
                    targetTrade.player.getInventory().add(item);
            }
            sendMessage("Your trade with " + targetTrade.player.getName() + " was successful.");
            if(targetTrade.targetTrade != null && (itemCount > 0 || targetTrade.itemCount > 0))
                //Loggers.logTrade(player.getUserId(), player.getName(), player.getIp(), targetTrade.player.getUserId(), targetTrade.player.getName(), targetTrade.player.getIp(), items, targetTrade.items);
                Loggers.logTrade(player, targetTrade.player, items, targetTrade.items);
        }
        stage = 0;
        accepted = false;
        targetTrade = null;
        player.getPacketSender().sendClientScript(209, "s", "");
        clear();
    }

    /**
     * Actions
     */

    static {
        /**
         * First screen
         */
        InterfaceHandler.register(Interface.TRADE_SCREEN, h -> {
            h.actions[10] = (SimpleTradeAction) p -> p.getTrade().accept(true);
            h.actions[25] = (TradeAction) (p, option, slot, itemId) -> {
                Item item = p.getTrade().get(slot, itemId);
                if(item == null)
                    return;
                if(option == 1)
                    p.getTrade().remove(item, 1);
                else if(option == 2)
                    p.getTrade().remove(item, 5);
                else if(option == 3)
                    p.getTrade().remove(item, 10);
                else if(option == 4)
                    p.getTrade().remove(item, Integer.MAX_VALUE);
                else if(option == 5)
                    p.integerInput("Enter amount:", amt -> p.getTrade().remove(item, amt));
                else
                    item.examine(p);
            };
            h.actions[28] = (TradeAction) (p, option, slot, itemId) -> {
                Item item = p.getTrade().targetTrade.get(slot, itemId);
                if(item == null)
                    return;
                item.examine(p);
            };
        });
        /**
         * First screen (inventory)
         */
        InterfaceHandler.register(Interface.TRADE_INVENTORY, h -> {
            h.actions[0] = (TradeAction) (p, option, slot, itemId) -> {
                Item item = p.getInventory().get(slot, itemId);
                if(item == null)
                    return;
                if(option == 1)
                    p.getTrade().offer(item, 1);
                else if(option == 2)
                    p.getTrade().offer(item, 5);
                else if(option == 3)
                    p.getTrade().offer(item, 10);
                else if(option == 4)
                    p.getTrade().offer(item, Integer.MAX_VALUE);
                else if(option == 5)
                    p.integerInput("Enter amount:", amt -> p.getTrade().offer(item, amt));
                else
                    item.examine(p);
            };
        });
        /**
         * Confirmation screen
         */
        InterfaceHandler.register(Interface.TRADE_CONFIRMATION, h -> {
            h.actions[13] = (SimpleTradeAction) p -> p.getTrade().accept(false);
            h.actions[14] = (SimpleTradeAction) p -> {
                p.getTrade().close();
                p.getTrade().closeInterfaces();
            };
        });
    }

    private interface TradeAction extends InterfaceAction {

        void handle(Player player, int option, int slot, int itemId);

        default void handleClick(Player player, int option, int slot, int itemId) {
            if(player.getTrade().targetTrade != null) //active check!!
                handle(player, option, slot, itemId);
        }

    }

    private interface SimpleTradeAction extends TradeAction {

        void handle(Player player);

        default void handle(Player player, int option, int slot, int itemId) {
            handle(player);
        }

    }

}
