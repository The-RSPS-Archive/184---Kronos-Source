package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.shop.ShopManager;

import static io.ruin.cache.ItemID.COINS_995;

public class SawmillOperator {
    public enum Plank {
        WOOD(100, 1511, 960),
        OAK(250, 1521, 8778),
        TEAK(500, 6333, 8780),
        MAHOGANY(1500, 6332, 8782);

        public final int cost, woodId, plankId;

        Plank(int cost, int woodId, int plankId) {
            this.cost = cost;
            this.woodId = woodId;
            this.plankId = plankId;
        }

        public static Plank getFromLog(int logId) {
            for (Plank p : values())
                if (p.woodId == logId)
                    return p;
            return null;
        }


        private static void convertPlank(Player player, Plank plank, int amount) {
            player.closeInterface(InterfaceType.MAIN);
            player.startEvent(event -> {
                int amt = amount;
                boolean isFree = isFree(player);

                while (amt-- > 0) {
                    Item coins = player.getInventory().findItem(COINS_995);

                    if (!isFree && (coins == null || coins.getAmount() < plank.cost)) {
                        player.dialogue(new NPCDialogue(5422, "You'll need to bring me some more coins."));
                        return;
                    }

                    Item wood = player.getInventory().findItem(plank.woodId);
                    if (wood == null) {
                        player.dialogue(new NPCDialogue(5422, "You'll need to bring me some more logs."));
                        return;
                    }

                    if (!isFree)
                        player.getInventory().remove(COINS_995, plank.cost);

                    player.getInventory().remove(plank.woodId, 1);
                    player.getInventory().add(plank.plankId, 1);
                }
            });

        }

        private static boolean isFree(Player player) {
            return player.getPosition().getRegion().id == 15148;
        }

        static {
            NPCAction.register(5422, "talk-to", (player, npc) -> player.dialogue(
                    new NPCDialogue(npc, "Do you want me to make some planks for you? Or would you be interested in some other housing supplies?"),
                    new OptionsDialogue(
                            new Option("Planks please!", () -> player.dialogue(
                                    new PlayerDialogue("Planks please!"),
                                    new NPCDialogue(npc, "What kind of planks do you want?"),
                                    new ActionDialogue(() -> player.openInterface(InterfaceType.MAIN, Interface.CONVERTING_PLANKS))
                            )),
                            new Option("What kind of planks can you make?", () -> player.dialogue(
                                    new PlayerDialogue("What kind of planks can you make?"),
                                    new NPCDialogue(npc, "I can make planks from wood, oak, teak and mahogany. I don't make planks from other woods " +
                                            "as they're no good for making furniture."),
                                    new NPCDialogue(npc, "Wood and oak are all over the place, but teak and mahogany can only be found in a few places " +
                                            "like Karamja and Etceteria.")
                            )),
                            new Option("Can I buy some housing supplies?", () -> ShopManager.openIfExists(player, "")),//TODO Fill this in
                            new Option("Nothing, thanks.", () -> player.dialogue(
                                    new PlayerDialogue("Nothing, thanks."),
                                    new NPCDialogue(npc, "Well come back when you want some. You can't get good quality planks anywhere but here!")))
                    )
            ));
            NPCAction.register(5422, "buy-plank", (player, npc) -> player.openInterface(InterfaceType.MAIN, Interface.CONVERTING_PLANKS));

            InterfaceHandler.register(Interface.CONVERTING_PLANKS, h -> {
                /**
                 * Wood
                 */
                h.actions[106] = (SimpleAction) p -> convertPlank(p, WOOD, 1);
                h.actions[104] = (SimpleAction) p -> convertPlank(p, WOOD, 5);
                h.actions[103] = (SimpleAction) p -> convertPlank(p, WOOD, 10);
                h.actions[102] = (SimpleAction) p -> p.integerInput("Enter amount:", amt -> convertPlank(p, WOOD, amt));
                h.actions[93] = (SimpleAction) p -> convertPlank(p, WOOD, p.getInventory().count(WOOD.woodId));

                /**
                 * Oak
                 */
                h.actions[111] = (SimpleAction) p -> convertPlank(p, OAK, 1);
                h.actions[110] = (SimpleAction) p -> convertPlank(p, OAK, 5);
                h.actions[109] = (SimpleAction) p -> convertPlank(p, OAK, 10);
                h.actions[108] = (SimpleAction) p -> p.integerInput("Enter amount:", amt -> convertPlank(p, OAK, amt));
                h.actions[94] = (SimpleAction) p -> convertPlank(p, OAK, p.getInventory().count(OAK.woodId));

                /**
                 * Teak
                 */
                h.actions[116] = (SimpleAction) p -> convertPlank(p, TEAK, 1);
                h.actions[115] = (SimpleAction) p -> convertPlank(p, TEAK, 5);
                h.actions[114] = (SimpleAction) p -> convertPlank(p, TEAK, 10);
                h.actions[113] = (SimpleAction) p -> p.integerInput("Enter amount:", amt -> convertPlank(p, TEAK, amt));
                h.actions[95] = (SimpleAction) p -> convertPlank(p, TEAK, p.getInventory().count(TEAK.woodId));

                /**
                 * Mahogany
                 */
                h.actions[121] = (SimpleAction) p -> convertPlank(p, MAHOGANY, 1);
                h.actions[120] = (SimpleAction) p -> convertPlank(p, MAHOGANY, 5);
                h.actions[119] = (SimpleAction) p -> convertPlank(p, MAHOGANY, 10);
                h.actions[118] = (SimpleAction) p -> p.integerInput("Enter amount:", amt -> convertPlank(p, MAHOGANY, amt));
                h.actions[96] = (SimpleAction) p -> convertPlank(p, MAHOGANY, p.getInventory().count(MAHOGANY.woodId));
            });
        }
    }
}
