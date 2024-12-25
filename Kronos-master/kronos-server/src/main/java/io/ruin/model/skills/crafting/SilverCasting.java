package io.ruin.model.skills.crafting;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.stat.StatType;

public enum SilverCasting {

    /**
     * Right side
     */
    HOLY(16, -1, 50.0, 1714, 1),
    UNHOLY(17, -1, 50.0, 1720, 1),
    SILVER_SICKLE(18, -1, 50.0, 2961, 1),
    SILVER_CROSSBOW_BOLT(21, -1, 50.0, 9382, 10),
    TIARA(23, -1, 52.5, 5525, 1),

    /**
     * Left side
     */
    OPAL_RING(1, 1609, 10.0, 21081, 1),
    JADE_RING(13, 1611, 32.0, 21084, 1),
    TOPAZ_RING(16, 1613, 35.0, 21087, 1),

    OPAL_NECKLACE(16, 1609, 35.0, 21090, 1),
    JADE_NECKLACE(25, 1611, 54.0, 21093, 1),
    TOPAZ_NECKLACE(32, 1613, 75.0, 21096, 1),

    OPAL_AMULET(27, 1609, 55.0, 21099, 1),
    JADE_AMULET(34, 1611, 70.0, 21102, 1),
    TOPAZ_AMULET(45, 1613, 80.0, 21105, 1),

    OPAL_BRACELET(22, 1609, 45.0, 21117, 1),
    JADE_BRACELET(29, 1611, 60.0, 21120, 1),
    TOPAZ_BRACELET(38, 1613, 70.0, 21123, 1);

    public final int levelReq, gem, result, amount;
    public final double exp;

    SilverCasting(int levelReq, int gem, double exp, int result, int amount) {
        this.levelReq = levelReq;
        this.gem = gem;
        this.exp = exp;
        this.result = result;
        this.amount = amount;
    }

    private static final int SILVER_BAR = 2355;

    private static void craft(Player player, SilverCasting silverCasting, int amount, boolean rightSide) {
        player.closeInterfaces();
        if (!player.getStats().check(StatType.Crafting, silverCasting.levelReq, "make this"))
            return;
        if(rightSide) {
            player.startEvent(event -> {
                int amt = amount;
                while (amt-- > 0) {
                    Item silverBar = player.getInventory().findItem(SILVER_BAR);
                    if (silverBar == null)
                        return;
                    player.animate(899);
                    event.delay(3);
                    silverBar.remove();
                    player.getInventory().add(silverCasting.result, silverCasting.amount);
                    player.getStats().addXp(StatType.Crafting, silverCasting.exp, true);
                    event.delay(1);
                }
            });
        } else {
            player.startEvent(event -> {
                int amt = amount;
                while (amt-- > 0) {
                    Item gem = player.getInventory().findItem(silverCasting.gem);
                    if (gem == null)
                        return;
                    Item silverBar = player.getInventory().findItem(SILVER_BAR);
                    if (silverBar == null)
                        return;
                    player.animate(899);
                    event.delay(3);
                    gem.remove();
                    silverBar.remove();
                    player.getInventory().add(silverCasting.result, 1);
                    player.getStats().addXp(StatType.Crafting, silverCasting.exp, true);
                    event.delay(1);
                }
            });
        }
    }


    private static void option(Player player, SilverCasting silverCasting, int option, boolean rightSide) {
        if (option == 1)
            craft(player, silverCasting, 1, rightSide);
        if (option == 2)
            craft(player, silverCasting, 5, rightSide);
        if (option == 3)
            craft(player, silverCasting, 10, rightSide);
        if (option == 4)
            player.integerInput("Enter amount:", amt -> craft(player, silverCasting, amt, rightSide));
        if (option == 5)
            craft(player, silverCasting, Integer.MAX_VALUE, rightSide);
    }


    static {
        ItemObjectAction.register(SILVER_BAR, "furnace", (player, item, object) -> {
            player.openInterface(InterfaceType.MAIN, Interface.SILVER_CASTING);
            player.getPacketSender().sendAccessMask(Interface.SILVER_CASTING, 4, 0, 11, 62);
            player.getPacketSender().sendAccessMask(Interface.SILVER_CASTING, 6, 0, 7, 62);
        });

        InterfaceHandler.register(Interface.SILVER_CASTING, h -> {
            /**
             * Left side
             */
            h.actions[4] = (DefaultAction) (player, option, slot, itemId) -> {
                /**
                 * Rings
                 */
                if (slot == 0)
                    option(player, OPAL_RING, option, false);
                if (slot == 1)
                    option(player, JADE_RING, option, false);
                if (slot == 2)
                    option(player, TOPAZ_RING, option, false);

                /**
                 * Necklaces
                 */
                if (slot == 3)
                    option(player, OPAL_NECKLACE, option, false);
                if (slot == 4)
                    option(player, JADE_NECKLACE, option, false);
                if (slot == 5)
                    option(player, TOPAZ_NECKLACE, option, false);

                /**
                 * Amulet (u)
                 */
                if (slot == 6)
                    option(player, OPAL_AMULET, option, false);
                if (slot == 7)
                    option(player, JADE_AMULET, option, false);
                if (slot == 8)
                    option(player, TOPAZ_AMULET, option, false);

                /**
                 * Bracelets
                 */
                if (slot == 9)
                    option(player, OPAL_BRACELET, option, false);
                if (slot == 10)
                    option(player, JADE_BRACELET, option, false);
                if (slot == 11)
                    option(player, TOPAZ_BRACELET, option, false);
            };

            /**
             * Right side
             */
            h.actions[6] = (DefaultAction) (player, option, slot, itemId) -> {
                if (slot == 0)
                    option(player, HOLY, option, true);
                if (slot == 1)
                    option(player, UNHOLY, option, true);
                if (slot == 2)
                    option(player, SILVER_SICKLE, option, true);
                if (slot == 4)
                    option(player, SILVER_CROSSBOW_BOLT, option, true);
                if (slot == 5)
                    option(player, TIARA, option, true);
            };
        });
    }
}
