package io.ruin.model.entity.npc.actions.guild.crafting;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;

import java.util.ArrayList;

import static io.ruin.cache.ItemID.COINS_995;

public enum Tanner {

    SOFT_LEATHER("Soft Leather", 1739, 1741, 10),
    HARD_LEATHER("Hard Leather", 1739, 1743, 30),
    SNAKESKIN("Snakeskin", 7801, 6289, 20),
    SECOND_SNAKESKIN("Snakeskin", 6287, 6289, 15),
    GREEN_DRAGONHIDE("Green d'hide", 1753, 1745, 20),
    BLUE_DRAGONHIDE("Blue d'hide", 1751, 2505, 30),
    RED_DRAGONHIDE("Red d'hide", 1749, 2507, 35),
    BLACK_DRAGONHIDE("Black d'hide", 1747, 2509, 40);

    public int raw, product, cost;
    public String displayName, rawName, productName;

    Tanner(String displayName, int raw, int product, int cost) {
        this.raw = raw;
        this.product = product;
        this.cost = cost;
        this.displayName = displayName;
        this.rawName = ItemDef.get(raw).name.toLowerCase();
        this.productName = ItemDef.get(product).name.toLowerCase();
    }

    private static final int[] TANNERS = {
            5809, // canifis
            3231, // al-kharid
    };
    private static final int[] MATERIALS = {1739, 7801, 6287, 1753, 1751, 1749, 1747};

    private static void tan(Player player, Tanner tanner, int amount) {
        Item rawItem = player.getInventory().findItem(tanner.raw);
        Item noted = null;
        if (rawItem == null && (ItemDef.get(tanner.raw).notedId < 1 || (noted = player.getInventory().findItem(ItemDef.get(tanner.raw).notedId)) == null)) {
            player.sendMessage("You don't have any " + tanner.rawName + "s to tan.");
            return;
        }

        if (!player.getInventory().contains(COINS_995, tanner.cost)) {
            player.sendMessage("You haven't got enough coins to pay for " + tanner.productName + "s.");
            return;
        }

        player.closeInterfaces();
        Item finalNoted = noted;
        player.startEvent(event -> {
            int made = 0;
            if (ItemDef.get(tanner.raw).notedId > 0) {
                if (finalNoted != null) {
                    int tan = Math.min(Math.min(finalNoted.getAmount(), amount), player.getInventory().getAmount(COINS_995) / tanner.cost);
                    if (tan == 0)
                        return;
                    player.getInventory().remove(COINS_995, tanner.cost * tan);
                    player.getInventory().remove(finalNoted.getId(), tan);
                    player.getInventory().add(ItemDef.get(tanner.product).notedId, tan);
                    made += tan;
                }
            }
            while (made++ < amount) {
                Item raw = player.getInventory().findItem(tanner.raw);
                if (raw == null)
                    break;
                if (!player.getInventory().contains(COINS_995, tanner.cost))
                    break;

                raw.setId(tanner.product);
                player.getInventory().remove(COINS_995, tanner.cost);
            }

            if ((made - 1) > 1)
                player.sendMessage("The tanner tans " + (made - 1) + " " + tanner.rawName + "s for you.");
            else
                player.sendMessage("The tanner tans your " + tanner.rawName + ".");
        });

    }

    private static void leatherTanning(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.LEATHER_TANNING);
            /* Interface settings */
        for (int i = 0; i < values().length; i++) {
            Tanner type = values()[i];
            String color = color(player, type.raw, type.cost);
            player.getPacketSender().sendString(Interface.LEATHER_TANNING, 108 + i, color + type.displayName);
            player.getPacketSender().sendString(Interface.LEATHER_TANNING, 116 + i, color + NumberUtils.formatNumber(type.cost) + " coins");
            player.getPacketSender().sendItem(Interface.LEATHER_TANNING, 100 + i, type.raw, 250);
        }
    }

    private static String color(Player player, int rawMaterial, int cost) {
        if ((player.getInventory().findItem(rawMaterial) == null && (ItemDef.get(rawMaterial).notedId < 1 || player.getInventory().findItem(ItemDef.get(rawMaterial).notedId) == null)) || !player.getInventory().contains(COINS_995, cost))
            return "<col=f80000>";
        return "<col=00c8f8>";
    }

    static {
        /**
         * Talk-to
         */
        for (int tanner : TANNERS) {
            NPCAction.register(tanner, "talk-to", (player, npc) -> {
                ArrayList<Item> bows = player.getInventory().collectItems(MATERIALS);
                if (bows != null) {
                    player.dialogue(
                            new NPCDialogue(npc, "Greetings friend. I am a manufacturer of leather.").animate(590),
                            new NPCDialogue(npc, "I see you have brought me some hide. Would you like me to tan them for you?").animate(569),
                            new OptionsDialogue(
                                    new Option("Yes please.", () -> leatherTanning(player)),
                                    new Option("No thanks.", () -> player.dialogue(
                                            new PlayerDialogue("No thanks."),
                                            new NPCDialogue(npc, "Very well, sir, as you wish.").animate(562)))
                            )
                    );
                } else {
                    player.dialogue(
                            new NPCDialogue(npc, "Greetings friend. I am a manufacturer of leather.").animate(590),
                            new OptionsDialogue(
                                    new Option("Can I buy some leather then?", () -> player.dialogue(
                                            new PlayerDialogue("Can I buy some leather then?"),
                                            new NPCDialogue(npc, "I make leather from animal hides. Bring me some cowhides and one gold coin per hide, and" +
                                                    " I'll tan them into soft leather for you.").animate(590),
                                            new PlayerDialogue("Thanks!").animate(562)
                                    )),
                                    new Option("Leather is rather weak stuff.", () -> player.dialogue(
                                            new PlayerDialogue("Leather is rather weak stuff.").animate(588),
                                            new NPCDialogue(npc, "Normal leather may be quite weak, but it's very cheap - I make it from cowhides for only " +
                                                    "1 gp per hide - and it's so easy to craft that anyone can work with it.").animate(590),
                                            new NPCDialogue(npc, "Alternatively you could try hard leather. It's not so easy to craft, but I only charge 3 gp " +
                                                    "per cowhide to prepare it, and it makes much sturdier armour.").animate(569),
                                            new NPCDialogue(npc, "I can also tan snake hides and dragonhides, suitable for crafting into the highest quality armour for rangers.").animate(568),
                                            new PlayerDialogue("Thanks, I'll bear it in mind.").animate(562)
                                    ))
                            )
                    );
                }
            });
            /**
             * Trade
             */
            NPCAction.register(tanner, "trade", (player, obj) -> leatherTanning(player));
        }
        InterfaceHandler.register(Interface.LEATHER_TANNING, h -> {
            /**
             * Soft leather
             */
            h.actions[148] = (SimpleAction) p -> tan(p, SOFT_LEATHER, 1);
            h.actions[140] = (SimpleAction) p -> tan(p, SOFT_LEATHER, 5);
            h.actions[132] = (SimpleAction) p -> p.integerInput("Enter amount:", amt -> tan(p, SOFT_LEATHER, amt));
            h.actions[124] = (SimpleAction) p -> tan(p, SOFT_LEATHER, Integer.MAX_VALUE);

            /**
             * Hard leather
             */
            h.actions[149] = (SimpleAction) p -> tan(p, HARD_LEATHER, 1);
            h.actions[141] = (SimpleAction) p -> tan(p, HARD_LEATHER, 5);
            h.actions[133] = (SimpleAction) p -> p.integerInput("Enter amount:", amt -> tan(p, HARD_LEATHER, amt));
            h.actions[125] = (SimpleAction) p -> tan(p, HARD_LEATHER, Integer.MAX_VALUE);

            /**
             * Snakeskin
             */
            h.actions[150] = (SimpleAction) p -> tan(p, SNAKESKIN, 1);
            h.actions[142] = (SimpleAction) p -> tan(p, SNAKESKIN, 5);
            h.actions[134] = (SimpleAction) p -> p.integerInput("Enter amount:", amt -> tan(p, SNAKESKIN, amt));
            h.actions[126] = (SimpleAction) p -> tan(p, SNAKESKIN, Integer.MAX_VALUE);

            /**
             * Snakeskin
             */
            h.actions[151] = (SimpleAction) p -> tan(p, SECOND_SNAKESKIN, 1);
            h.actions[143] = (SimpleAction) p -> tan(p, SECOND_SNAKESKIN, 5);
            h.actions[135] = (SimpleAction) p -> p.integerInput("Enter amount:", amt -> tan(p, SECOND_SNAKESKIN, amt));
            h.actions[127] = (SimpleAction) p -> tan(p, SECOND_SNAKESKIN, Integer.MAX_VALUE);

            /**
             * Green d'hide
             */
            h.actions[152] = (SimpleAction) p -> tan(p, GREEN_DRAGONHIDE, 1);
            h.actions[144] = (SimpleAction) p -> tan(p, GREEN_DRAGONHIDE, 5);
            h.actions[136] = (SimpleAction) p -> p.integerInput("Enter amount:", amt -> tan(p, GREEN_DRAGONHIDE, amt));
            h.actions[128] = (SimpleAction) p -> tan(p, GREEN_DRAGONHIDE, Integer.MAX_VALUE);

            /**
             * Blue d'hide
             */
            h.actions[153] = (SimpleAction) p -> tan(p, BLUE_DRAGONHIDE, 1);
            h.actions[145] = (SimpleAction) p -> tan(p, BLUE_DRAGONHIDE, 5);
            h.actions[137] = (SimpleAction) p -> p.integerInput("Enter amount:", amt -> tan(p, BLUE_DRAGONHIDE, amt));
            h.actions[129] = (SimpleAction) p -> tan(p, BLUE_DRAGONHIDE, Integer.MAX_VALUE);

            /**
             * Red d'hide
             */
            h.actions[154] = (SimpleAction) p -> tan(p, RED_DRAGONHIDE, 1);
            h.actions[146] = (SimpleAction) p -> tan(p, RED_DRAGONHIDE, 5);
            h.actions[138] = (SimpleAction) p -> p.integerInput("Enter amount:", amt -> tan(p, RED_DRAGONHIDE, amt));
            h.actions[130] = (SimpleAction) p -> tan(p, RED_DRAGONHIDE, Integer.MAX_VALUE);

            /**
             * Black d'hide
             */
            h.actions[155] = (SimpleAction) p -> tan(p, BLACK_DRAGONHIDE, 1);
            h.actions[147] = (SimpleAction) p -> tan(p, BLACK_DRAGONHIDE, 5);
            h.actions[139] = (SimpleAction) p -> p.integerInput("Enter amount:", amt -> tan(p, BLACK_DRAGONHIDE, amt));
            h.actions[131] = (SimpleAction) p -> tan(p, BLACK_DRAGONHIDE, Integer.MAX_VALUE);
        });

    }
}
