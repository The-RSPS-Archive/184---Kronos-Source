package io.ruin.model.activities.wilderness;

import io.ruin.Server;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.cache.ItemDef;
import io.ruin.data.impl.Help;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.shop.*;
import io.ruin.utility.Broadcast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BloodyMerchant {

    private static final int BLOODY_MERCHANT = 8507;
    private static NPC bloodyMerchant = null;
    private static BloodyMerchant ACTIVE;
    private static List<ShopItem> bloodyMerchantItems;
    private static Shop bloodyMerchantShop = null;

    private static long spawnTicks = 0;

    private static final BloodyMerchant[] SPAWN_LOCATIONS = {
            new BloodyMerchant(new Position(3029, 3631, 0), "at the center of the Dark Warriors' Fortress"),
            new BloodyMerchant(new Position(3079, 3871, 0), "inside the Lava Maze"),
            new BloodyMerchant(new Position(3357, 3872, 0), "as the Fountain of Rune"),
            new BloodyMerchant(new Position(2994, 3956, 0), "inside the Wilderness Agility Course"),
            new BloodyMerchant(new Position(3294, 3933, 0), "at the Rogues' Castle"),
    };

    /**
     * Separator
     */

    private final Position spawnPosition;

    private final String positionHint;

    public BloodyMerchant(Position spawnPosition, String positionHint) {
        this.spawnPosition = spawnPosition;
        this.positionHint = positionHint;
    }

    /**
     * Event
     */
    static {
//        World.startEvent(e -> {
//            while (true) {
//                spawnTicks = Server.getEnd(4 * 60 * 100); // 4 hours
//                e.delay(4 * 60 * 100); // 4 hours
//                int randomLocation = Random.get(0, SPAWN_LOCATIONS.length - 1);
//
//                BloodyMerchant next = SPAWN_LOCATIONS[randomLocation];
//                while (next == ACTIVE) {
//                    randomLocation = Random.get(0, SPAWN_LOCATIONS.length - 1);
//                    next = SPAWN_LOCATIONS[randomLocation];
//                }
//
//                ACTIVE = next;
//
//                String spawnMessage = "The Bloody Merchant has made an appearance " + ACTIVE.positionHint + "!";
//                broadcastEvent(spawnMessage);
//                spawnMerchant();
//                e.delay(15 * 100); // 15 mins
//                despawnMerchant();
//            }
//        });

        NPCAction.register(BLOODY_MERCHANT, "Trade", (player, npc) -> {
            if (bloodyMerchant == null) {
                player.sendMessage("The bloody merchant isn't interested in trading.");
                return;
            }

            if (player.bloodyMerchantTradeWarning) {
                player.dialogue(
                        new MessageDialogue("<col=ff0000>Warning:</col> Trading the bloody merchant will HIGH RISK skull you."),
                        new OptionsDialogue("Are you sure you wish to trade him?",
                                new Option("Yes, I'm brave.", () -> tradeMerchant(player)),
                                new Option("Eep! No thank you.", () -> player.sendFilteredMessage("You decide to not to trade the merchant.")),
                                new Option("Yes please, don't show this message again.", () -> {
                                    player.bloodyMerchantTradeWarning = false;
                                    tradeMerchant(player);
                                }))
                );
            } else {
                tradeMerchant(player);
            }
        });
    }

    private static void broadcastEvent(String eventMessage) {
        for(Player p : World.players) {
            if(p.broadcastBloodyMechant) {
                p.getPacketSender().sendMessage(eventMessage, "", 14);
            }
        }
        Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", eventMessage);
    }

    private static void tradeMerchant(Player player) {
        player.getCombat().skullHighRisk();
        bloodyMerchantShop.open(player);
    }

    private static void spawnMerchant() {
        World.startEvent(event -> {
            bloodyMerchant = new NPC(BLOODY_MERCHANT).spawn(ACTIVE.spawnPosition);
            bloodyMerchantItems = IntStream
                    .range(0, 10)
                    .mapToObj(i -> BloodMerchantItems.randomItem())
                    .map(bloodMerchantItems -> ShopItem.builder()
                            .id(bloodMerchantItems.itemId)
                            .price(bloodMerchantItems.itemPrice)
                            .amount(Random.get(1, bloodMerchantItems.maxAmt))
                            .build())
                    .collect(Collectors.toList());

            bloodyMerchantShop = Shop.builder()
                    .identifier("f28248e1-969b-4018-b30f-0a93ddb64ba3")//TODO Fill this in
                    .title("Bloody Merchant's Findings")
                    .currency(Currency.BLOOD_MONEY)
                    .defaultStock(bloodyMerchantItems)
                    .restockRules(RestockRules.generateDefault())
                    .generatedByBuilder(true)
                    .build();
            ShopManager.registerShop(bloodyMerchantShop);
        });
    }

    private static void despawnMerchant() {
        if (bloodyMerchant != null) {
            bloodyMerchant.remove();
            bloodyMerchantItems.clear();
            bloodyMerchantShop = null;
            bloodyMerchant = null;
            String despawnMessage = "The Bloody Merchant has left to collect more items.";
            Broadcast.WORLD.sendNews(Icon.WILDERNESS, "Wilderness Event", despawnMessage);
            broadcastEvent(despawnMessage);
        }
    }

    /**
     * Entry
     */
    public static final class Entry extends JournalEntry {

        public static final Entry INSTANCE = new Entry();

        @Override
        public void send(Player player) {
            int minsLeft = (int) ((spawnTicks - Server.currentTick()) / 100);
            if(minsLeft < 0) {
                send(player, "Bloody Merchant", "Active ", Color.GREEN);
                return;
            }
            if (minsLeft == 0)
                send(player, "Bloody Merchant", "Active!", Color.GREEN);
            else if (minsLeft == 1)
                send(player, "Bloody Merchant", "1 minute", Color.YELLOW);
            else if (minsLeft == 60)
                send(player, "Bloody Merchant", "1 hour", Color.RED);
            else if (minsLeft > 60) {
                int hours = minsLeft / 60;
                send(player, "Bloody Merchant", hours + " hour" + (hours > 1 ? "s" : ""), Color.RED);
            } else
                send(player, "Bloody Merchant", minsLeft + " minutes", Color.RED);
        }

        @Override
        public void select(Player player) {
            Help.open(player, "bloody_merchant");
        }
    }

    /**
     * All the possible items our beautiful merchant offers!
     */
    private enum BloodMerchantItems {
        ARMADYL_GODSWORD(11802, 4800, 1),
        ABYSSAL_WHIP(4151, 45, 3),
        ABYSSAL_TENTACLE(12006, 160, 2),
        ABYSSAL_DAGGER(13271, 450, 1),
        SEERS_RING(6731, 300, 3),
        BERSERKER_RING(6737, 500, 3),
        AMULET_OF_FURY(6585, 800, 3),
        HEAVY_BALLISTA(19481, 2000, 1),
        DRAGON_KNIFE(22804, 15, 500),
        DRAGON_THROWNAXE(20849, 6, 500),
        TOXIC_STAFF(12902, 18000, 1),
        UNCHARGED_TOXIC_TRIDENT(1290, 15000, 1),
        IMBUED_HEART(20724, 900, 2),
        MAGES_BOOK(6889, 480, 3),
        AHRIMS_HOOD(4708, 120, 5),
        AHRIMS_ROBETOP(4712, 180, 5),
        AHRIMS_ROBESKIRT(4714, 180, 5),
        KARILS_LEATHERTOP(4736, 180, 5),
        KAIRLS_LEATHERSKIRT(4738, 180, 5),
        DHAROK_HELM(4716, 120, 5),
        DHAROK_PLATEBODY(4720, 180, 5),
        DHAROK_PLATELEGS(4722, 180, 5),
        DHAROK_GREATAXE(4718, 120, 5),
        DRAGONFIRE_SHIELD(11283, 1200, 1),
        BANDOS_GODSWORD(11804, 1500, 1),
        ABYSSAL_BLUDGEON(13263, 600, 1),
        ANGLERFISH(13441, 3, 500),
        DARK_CRAB(11937, 2, 500),
        SUPER_COMBAT_POTION(12696, 2, 200),
        STAMINA_POTION(12626, 1, 200);

        private final int itemId, itemPrice, maxAmt;

        BloodMerchantItems(int itemId, int itemPrice, int maxAmt) {
            this.itemId = itemId;
            this.itemPrice = itemPrice;
            this.maxAmt = maxAmt;
        }

        private static final List<BloodMerchantItems> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

        public static BloodMerchantItems randomItem()  {
            return VALUES.get(Random.get(VALUES.size() - 1));
        }

    }

}
