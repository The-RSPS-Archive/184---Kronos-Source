package io.ruin.services;

import com.google.common.collect.Lists;
import io.ruin.Server;
import io.ruin.api.database.DatabaseStatement;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.cache.ItemID;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
@Slf4j
public class Store {

    /**
     * Credits item
     */

    public static void claimCredits(Player player, Item item) {
        if(!World.isLive() && !player.isAdmin()) {
            player.dialogue(new MessageDialogue("Sorry, you can't claim credits on this world."));
            return;
        }
        PlayerAction action = player.getAction(1);
        if(action == PlayerAction.FIGHT || action == PlayerAction.ATTACK) {
            player.dialogue(new MessageDialogue("Sorry, you can't claim credits from where you're standing."));
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Your claimed credits will be made available to your online account.", item, () -> claimCredits0(player, item)));
    }

    private static void claimCredits0(Player player, Item item) {
        player.lock();
        player.dialogue(new ItemDialogue().one(item.getId(), "Attempting to claim credits, please wait...").hideContinue());
        Server.forumDb.execute(new DatabaseStatement() {
            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = connection.prepareStatement("SELECT * FROM xf_user WHERE user_id = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    statement.setInt(1, player.getUserId());
                    resultSet = statement.executeQuery();
                    if(!resultSet.next()) {
                        resultSet.moveToInsertRow();
                        updateCredits(player, item.getAmount(), resultSet);
                        resultSet.insertRow();
                    } else {
                        long credits = resultSet.getInt("donation_shards");
                        updateCredits(player, (int) Math.min(credits + item.getAmount(), Integer.MAX_VALUE), resultSet);
                        resultSet.updateRow();
                    }
                    finish(true);
                } finally {
                    DatabaseUtils.close(statement, resultSet);
                }
            }
            @Override
            public void failed(Throwable t) {
                finish(false);
                Server.logError("", t); //todo exclude timeouts
            }
            private void finish(boolean success) {
                Server.worker.execute(() -> {
                    if(success) {
                        item.remove();
                        player.dialogue(new ItemDialogue().one(item.getId(), item.getAmount() + " " + World.type.getWorldName() + " Credits have been added to your web account."));
                    } else {
                        player.dialogue(new MessageDialogue("Unable to claim credits at this time, please try again."));
                    }
                    player.unlock();
                });
            }
        });
    }

    private static void updateCredits(Player player, int credits, ResultSet resultSet) throws SQLException {
        resultSet.updateInt("user_id", player.getUserId());
        resultSet.updateString("username", player.getName()); //eh idk why this is stored lol
        resultSet.updateInt("donation_shards", credits);
    }

    static {
        ItemAction.registerInventory(13190, "claim", Store::claimCredits);
    }

    /**
     * Purchases
     */

    public static void claimPurchases(Player player, NPC npc, StoreConsumer consumer) {
        if(!World.isLive() && !player.isAdmin()) {
            player.dialogue(new NPCDialogue(npc, "Sorry, you can't claim store purchases on this world."));
            return;
        }
        player.lock();
        player.dialogue(new NPCDialogue(npc, "Attempting to claim store purchases, please wait...").hideContinue());
        Server.siteDb.execute(new DatabaseStatement() {

            List<Item> items = Lists.newArrayList();
            List<Integer> orders = Lists.newArrayList();
            int spent = 0;

            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = connection.prepareStatement("SELECT ol.price * ol.quantity price, ol.quantity quantity, ol.order_id id, ol.product_item_id FROM orders JOIN order_lines ol ON orders.id = ol.order_id  WHERE player_name = ? AND status = 'Approved';", ResultSet.TYPE_SCROLL_SENSITIVE);
                    statement.setString(1, player.getName());
                    resultSet = statement.executeQuery();
                    while (resultSet.next()) {

                        int itemId = resultSet.getInt("product_item_id");
                        int itemAmount = resultSet.getInt("quantity");
                        int orderId = resultSet.getInt("id");
                        int price = resultSet.getInt("price");
                        spent += price;

                        orders.add(orderId);
                        if (itemId == 1233) { // Starter package
                            items.add(new Item(290, 1));
                            items.add(new Item(6199, 1));
                            items.add(new Item(4151, 1));
                            items.add(new Item(537, 120));
                        } else if (itemId == 1234) { // Green Skin
                            player.unlockedGreenSkin = true;
                            player.sendMessage("Right click the Make-over mage to use your new green skin!");
                        } else if (itemId == 1235) { // Blue Skin
                            player.unlockedBlueSkin = true;
                            player.sendMessage("Right click the Make-over mage to use your new blue skin!");
                        } else if (itemId == 1236) { // Purple Skin
                            player.unlockedPurpleSkin = true;
                            player.sendMessage("Right click the Make-over mage to use your new purple skin!");
                        } else if (itemId == 1237) { // black Skin
                            player.unlockedBlackSkin = true;
                            player.sendMessage("Right click the Make-over mage to use your new black skin!");
                        } else if (itemId == 1238) { // white Skin
                            player.unlockedWhiteSkin = true;
                            player.sendMessage("Right click the Make-over mage to use your new white skin!");
                        } else if (itemId == 1239) { // Custom title
                            player.hasCustomTitle = true;
                            player.sendMessage("You can now use custom titles! Speak to the Make-over Mage to use it");
                        } else if (itemId == 75000) { //void melee
                            items.add(new Item(11665, itemAmount));
                            items.add(new Item(8840, itemAmount));
                            items.add(new Item(8839, itemAmount));
                            items.add(new Item(8842, itemAmount));
                        } else if (itemId == 75001) { //void Range
                            items.add(new Item(11664, itemAmount));
                            items.add(new Item(8840, itemAmount));
                            items.add(new Item(8839, itemAmount));
                            items.add(new Item(8842, itemAmount));
                        } else if (itemId == 75002) { //void Mage
                            items.add(new Item(11663, itemAmount));
                            items.add(new Item(8840, itemAmount));
                            items.add(new Item(8839, itemAmount));
                            items.add(new Item(8842, itemAmount));
                        } else if (itemId == 75003) { //Full Void
                            items.add(new Item(11663, itemAmount));
                            items.add(new Item(11664, itemAmount));
                            items.add(new Item(11665, itemAmount));
                            items.add(new Item(8840, itemAmount));
                            items.add(new Item(8839, itemAmount));
                            items.add(new Item(8842, itemAmount));
                        } else if (itemId == 75004) {
                            items.add(new Item(20095, itemAmount));
                            items.add(new Item(20098, itemAmount));
                            items.add(new Item(20101, itemAmount));
                            items.add(new Item(20104, itemAmount));
                            items.add(new Item(20107, itemAmount));
                        } else if (itemId == 75005) {
                            items.add(new Item(11826, itemAmount));
                            items.add(new Item(11828, itemAmount));
                            items.add(new Item(20130, itemAmount));
                        } else if (itemId == 75006) {
                            items.add(new Item(ItemID.ARMADYL_CHAINSKIRT, itemAmount));
                            items.add(new Item(ItemID.ARMADYL_CHESTPLATE, itemAmount));
                            items.add(new Item(ItemID.ARMADYL_HELMET, itemAmount));
                        } else if (itemId == 13072) {
                            items.add(new Item(13073, itemAmount));
                            items.add(new Item(13072, itemAmount));
                            items.add(new Item(8842, itemAmount));
                            items.add(new Item(11663, itemAmount));
                            items.add(new Item(11664, itemAmount));
                            items.add(new Item(11665, itemAmount));
                        } else if (itemId == 75007) { //angler fish x250
                            items.add(new Item(13442, 250 * itemAmount));
                        } else if (itemId == 75008) { //karambwan x250
                            items.add(new Item(3145, 250 * itemAmount));
                        } else if (itemId == 75009) { //zulrah scales x5000
                            items.add(new Item(12934, 5000 * itemAmount));
                        } else if (itemId == 75010) { //dragon darts x150
                            items.add(new Item(11230, 150 * itemAmount));
                        } else if (itemId == 75011) { //dragon arrows x1000
                            items.add(new Item(11212, 1000 * itemAmount));
                        } else if (itemId == 75012) { //dragon javelin x150
                            items.add(new Item(19484, 150 * itemAmount));
                        } else {
                            if (itemId == 10834) {
                                player.diceHost = true;
                            }
                            items.add(new Item(itemId, itemAmount));
                        }
                    }
                    finish(false);
                } finally {
                    DatabaseUtils.close(statement, resultSet);
                    markClaimed(orders, player.getIp());
                }
            }

            @Override
            public void failed(Throwable t) {
                finish(true);
                Server.logError("", t);
            }

            private void finish(boolean error) {
                Server.worker.execute(() -> {
                    consumer.accept(items, spent, error);
                    player.unlock();
                });
            }
        });
    }

    private static void markClaimed(List<Integer> orders, String ip) {
        Server.siteDb.execute(connection -> {
            PreparedStatement statement = null;
            for(int o : orders) {
                try {
                    statement = connection.prepareStatement("UPDATE orders SET claimed_ip=?, status=?, claimed_at=? WHERE id=?", ResultSet.CONCUR_UPDATABLE);
                    statement.setString(1, ip);
                    statement.setString(2, "Claimed");
                    statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    statement.setInt(4, o);
                    statement.executeUpdate();
                } finally {
                    DatabaseUtils.close(statement);
                }
            }
        });
    }

    @FunctionalInterface
    public interface StoreConsumer {
        void accept(List<Item> items, long spent, boolean error);
    }

}
