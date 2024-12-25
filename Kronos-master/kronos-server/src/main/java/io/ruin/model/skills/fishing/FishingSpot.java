package io.ruin.model.skills.fishing;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class FishingSpot {

    private FishingTool tool;

    private FishingCatch[] regularCatches, barehandCatches;

    private FishingSpot(FishingTool tool) {
        this.tool = tool;
    }

    private FishingSpot regularCatches(FishingCatch... regularCatches) {
        this.regularCatches = regularCatches;
        return this;
    }

    private FishingSpot barehandCatches(FishingCatch... barehandCatches) {
        this.barehandCatches = barehandCatches;
        return this;
    }

    private FishingCatch randomCatch(int level, boolean barehand, FishingTool tool) {
        FishingCatch[] catches = barehand ? barehandCatches : regularCatches;
        double roll = Random.get();
        for(int i = catches.length - 1; i >= 0; i--) {
            FishingCatch c = catches[i];
            int levelDifference = level - c.levelReq;
            if(levelDifference < 0) {
                /* not high enough level */
                continue;
            }
            double chance = c.baseChance;
            if(chance >= 1.0) {
                /* always catch this bad boy */
                return c;
            }
            if(tool == FishingTool.DRAGON_HARPOON)
                chance += 1.20;
                chance += (double) levelDifference * 0.01;
                if (roll > Math.min(chance, 0.90)) {
                    /* failed to catch */
                    continue;
                }
            return c;
        }
        return null;
    }

    private void fish(Player player, NPC npc) {
        boolean barehand;
        Stat fishing = player.getStats().get(StatType.Fishing);
        if (tool == FishingTool.HARPOON && hasDragonHarpoon(player)) {
            if(fishing.currentLevel < 61) {
                player.sendMessage("You need a Fishing level of at least 61 to fish with a dragon harpoon.");
                return;
            }

            tool = FishingTool.DRAGON_HARPOON;
        }

        if(player.getInventory().contains(new Item(tool.id)) || (tool == FishingTool.HARPOON && hasDragonHarpoon(player))) {
            FishingCatch lowestCatch = regularCatches[0];

            if(fishing.currentLevel < lowestCatch.levelReq) {
                player.sendMessage("You need a Fishing level of at least " + lowestCatch.levelReq + " to fish at this spot.");
                return;
            }

            barehand = false;
        } else {
            if(barehandCatches == null) {
                player.sendMessage("You need a " + tool.primaryName + " to fish at this spot.");
                return;
            }

            FishingCatch lowestCatch = barehandCatches[0];

            if(fishing.currentLevel < lowestCatch.levelReq) {
                player.sendMessage("You need a Fishing level of at least " + lowestCatch.levelReq + " to barehand fish at this spot.");
                player.sendMessage("To fish at this spot normally, you'll need a " + tool.primaryName + ".");
                return;
            }

            if(player.getStats().get(StatType.Agility).currentLevel < lowestCatch.agilityReq) {
                player.sendMessage("You need an Agility level of at least " + lowestCatch.agilityReq + " to barehand fish at this spot.");
                player.sendMessage("To fish at this spot normally, you'll need a " + tool.primaryName + ".");
                return;
            }

            if(player.getStats().get(StatType.Strength).currentLevel < lowestCatch.strengthReq) {
                player.sendMessage("You need a Strength level of at least " + lowestCatch.strengthReq + " to barehand fish at this spot.");
                player.sendMessage("To fish at this spot normally, you'll need a " + tool.primaryName + ".");
                return;
            }

            barehand = true;
        }

        Item secondary;
        if(barehand || tool.secondaryId == -1) {
            secondary = null;
        } else if((secondary = player.getInventory().findItem(tool.secondaryId)) == null) {
            player.sendMessage("You need at least one " + tool.secondaryName + " to fish at this spot.");
            return;
        }

        if(player.getInventory().isFull()) {
            player.sendMessage("Your inventory is too full to hold any fish.");
            return;
        }

        if(npc.getId() == INFERNO_EEL) {
            if(!player.getEquipment().hasId(1580)) {
                player.sendMessage("You need to be wearing ice gloves to catch infernal eel.");
                return;
            }
        }

        /**
         * Start event
         */
        player.animate(barehand ? 6703 : tool.startAnimationId);
        player.startEvent(event -> {
            int animTicks = 2;
            boolean firstBarehandAnim = true;
            while(true) {
                if(animTicks > 0) { //we do this so we can check if the npc has moved every tick
                    int diffX = Math.abs(player.getAbsX() - npc.getAbsX());
                    int diffY = Math.abs(player.getAbsY() - npc.getAbsY());
                    if(diffX + diffY > 1) {
                        player.resetAnimation();
                        return;
                    }

                    event.delay(1);
                    animTicks--;
                    continue;
                }

                FishingCatch c = randomCatch(fishing.currentLevel, barehand, tool);
                if(c != null) {
                    if(npc.getId() == MINNOWS && (npc.minnowsFish || (npc.minnowsFish = Random.rollDie(100)))) {
                        npc.graphics(1387);
                        player.getInventory().remove(c.id, 26);
                        player.sendFilteredMessage("A flying fish jumps up and eats some of your minnows!");
                    } else {
                        if(secondary != null)
                            secondary.incrementAmount(-1);

                        if(npc.getId() == MINNOWS)
                            player.sendFilteredMessage("You catch some minnows!");

                        if(npc.getId() == INFERNO_EEL)
                            player.sendFilteredMessage("You catch an infernal eel. It hardens as you handle it with your ice gloves.");

                        int amount = npc.getId() == MINNOWS ? Random.get(10, 26) : 1;
                        player.collectResource(new Item(c.id, amount));

                        if (player.darkCrabBoost.isDelayed()) {
                            if (Random.rollPercent(20))
                                amount++;
                        }

                        player.getInventory().add(c.id, amount);

                        if(npc.getId() != MINNOWS)
                            PlayerCounter.TOTAL_FISH.increment(player, 1);

                        player.getStats().addXp(StatType.Fishing, c.xp * anglerBonus(player), true);

                        FishingClueBottle.roll(player, c, barehand);
                        if (Random.rollDie(c.petOdds))
                            Pet.HERON.unlock(player);

                        if(npc.fishingArea != null && npc.fishingArea != FishingArea.RESOURCE_AREA && Random.rollDie(75))
                            npc.fishingArea.move(npc);

                        if(c.barbarianXp > 0) {
                            player.getStats().addXp(StatType.Agility, c.barbarianXp, true);
                            player.getStats().addXp(StatType.Strength, c.barbarianXp, true);

                            if(barehand) {
                                if(c == FishingCatch.BARBARIAN_TUNA)
                                    player.animate(firstBarehandAnim ? 6710 : 6711);
                                else if(c == FishingCatch.BARBARIAN_SWORDFISH)
                                    player.animate(firstBarehandAnim ? 6707 : 6708);
                                else if(c == FishingCatch.BARBARIAN_SHARK)
                                    player.animate(firstBarehandAnim ? 6705 : 6706);

                                firstBarehandAnim = !firstBarehandAnim;
                                animTicks = 8;
                            }
                        }

                        if(player.getInventory().isFull()) {
                            player.sendMessage("Your inventory is too full to hold any more fish.");
                            player.resetAnimation();
                            return;
                        }

                        if(!barehand && tool.secondaryId != -1) {
                            Item requiredSecondary = player.getInventory().findItem(tool.secondaryId);

                            if(requiredSecondary == null) {
                                player.sendMessage("You need at least one " + tool.secondaryName + " to fish at this spot.");
                                return;
                            }
                        }
                    }
                }

                if(animTicks == 0) {
                    player.animate(barehand ? 6704 : tool.loopAnimationId);
                    animTicks = 3;
                }
            }
        });
    }

    private static boolean hasDragonHarpoon(Player player) {
        if(player.getInventory().hasId(FishingTool.DRAGON_HARPOON.id))
            return true;

        ItemDef playerWeapon = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        if(playerWeapon != null && playerWeapon.id == FishingTool.DRAGON_HARPOON.id)
            return true;

        return false;
    }

    private void register(int npcId, String option) {
        NPCAction.register(npcId, option, this::fish);
    }

    /**
     * :)
     */

    public static final int NET_BAIT = 1518;            //shrimps,anchovies / sardine,herring

    public static final int LURE_BAIT = 1506;           //trout,salmon / pike

    public static final int CAGE_HARPOON = 1519;        //lobster / tuna,swordfish

    public static final int BIG_NET_HARPOON = 1520;     //shark / tuna,swordfish

    public static final int SMALL_NET_HARPOON = 4316;   //monkfish / swordfish

    public static final int USE_ROD = 1542;             //leaping

    public static final int CAGE = 1535;                //dark crab

    public static final int BAIT = 6825;                //angler

    private static final int MINNOWS = 7731;            //minnows

    public static final int INFERNO_EEL = 7676;        //infernal eel

    public static final int KARAMBWAN_SPOT = 4712;

    public static final int MOLTEN_EEL = 15018;

    static {

        new FishingSpot(FishingTool.KARAMBWAN_VESSEL)
                .regularCatches(FishingCatch.KARAMBWAN)
                .register(KARAMBWAN_SPOT, "fish");
        /**
         * Net / Bait
         */
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.SHRIMPS, FishingCatch.ANCHOVIES)
                .register(NET_BAIT, "small net");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.SARDINE, FishingCatch.HERRING)
                .register(NET_BAIT, "bait");
        /**
         * Lure / Bait
         */
        new FishingSpot(FishingTool.FLY_FISHING_ROD)
                .regularCatches(FishingCatch.TROUT, FishingCatch.SALMON)
                .register(LURE_BAIT, "lure");
        new FishingSpot(FishingTool.FISHING_ROD)
                .regularCatches(FishingCatch.PIKE)
                .register(LURE_BAIT, "bait");
        /**
         * Cage / Harpoon
         */
        new FishingSpot(FishingTool.LOBSTER_POT)
                .regularCatches(FishingCatch.LOBSTER)
                .register(CAGE_HARPOON, "cage");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.TUNA, FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_TUNA, FishingCatch.BARBARIAN_SWORDFISH)
                .register(CAGE_HARPOON, "harpoon");
        /**
         * Net (big) / Harpoon
         */
        new FishingSpot(FishingTool.BIG_FISHING_NET)
                .regularCatches(FishingCatch.MACKEREL, FishingCatch.COD, FishingCatch.BASS)
                .register(BIG_NET_HARPOON, "big net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SHARK)
                .barehandCatches(FishingCatch.BARBARIAN_SHARK)
                .register(BIG_NET_HARPOON, "harpoon");
        /**
         * Net (small) / Harpoon
         */
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.MONKFISH)
                .register(SMALL_NET_HARPOON, "net");
        new FishingSpot(FishingTool.HARPOON)
                .regularCatches(FishingCatch.SWORDFISH)
                .barehandCatches(FishingCatch.BARBARIAN_SWORDFISH)
                .register(SMALL_NET_HARPOON, "harpoon");
        /**
         * Use-rod (Leaping)
         */
        new FishingSpot(FishingTool.BARBARIAN_ROD)
                .regularCatches(FishingCatch.LEAPING_TROUT, FishingCatch.LEAPING_SALMON, FishingCatch.LEAPING_STURGEON)
                .register(USE_ROD, "use-rod");
        /**
         * Cage (Dark crab)
         */
        new FishingSpot(FishingTool.DARK_CRAB_POT)
                .regularCatches(FishingCatch.DARK_CRAB)
                .register(CAGE, "cage");

        /**
         * Bait (Angler)
         */
        new FishingSpot(FishingTool.ANGLER_ROD)
                .regularCatches(FishingCatch.ANGLERFISH)
                .register(BAIT, "bait");
        /**
         * Minnows
         */
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.MINNOWS)
                .register(MINNOWS, "small net");
        NPC minnow1 = new NPC(MINNOWS).spawn(2611, 3443, 0);
        NPC minnow2 = new NPC(MINNOWS).spawn(2610, 3444, 0);
        NPC minnow3 = new NPC(MINNOWS).spawn(2618, 3443, 0);
        NPC minnow4 = new NPC(MINNOWS).spawn(2619, 3444, 0);
        World.startEvent(e -> {
            while(true) {
                e.delay(20);
                moveMinnow(minnow1, minnow3);
                e.delay(4);
                moveMinnow(minnow2, minnow4);
            }
        });
        /**
         * Infernal eel
         */
        new FishingSpot(FishingTool.OILY_FISHING_ROD)
                .regularCatches(FishingCatch.INFERNAL_EEL)
                .register(INFERNO_EEL, "bait");
        /**
         * Molten eel
         */
        new FishingSpot(FishingTool.SMALL_FISHING_NET)
                .regularCatches(FishingCatch.MOLTEN_EEL)
                .register(MOLTEN_EEL, "bait");
    }

    private static void moveMinnow(NPC... minnows) {
        for(NPC minnow : minnows) {
            int x = minnow.getAbsX();
            int y = minnow.getAbsY();

            if(y == 3443) {
                if(x == 2609 || x == 2617)
                    minnow.step(0, 1, StepType.FORCE_WALK);
                else
                    minnow.step(-1, 0, StepType.FORCE_WALK);
            } else {
                if(x == 2612 || x == 2620)
                    minnow.step(0, -1, StepType.FORCE_WALK);
                else
                    minnow.step(1, 0, StepType.FORCE_WALK);
            }

            minnow.minnowsFish = false;
        }
    }

    private static double anglerBonus(Player player) {
        double bonus = 1.0;
        Item hat = player.getEquipment().get(Equipment.SLOT_HAT);
        Item top = player.getEquipment().get(Equipment.SLOT_CHEST);
        Item waders = player.getEquipment().get(Equipment.SLOT_LEGS);
        Item boots = player.getEquipment().get(Equipment.SLOT_FEET);

        if (hat != null && hat.getId() == 13258)
            bonus += 0.4;
        if (top != null && top.getId() == 13259)
            bonus += 0.8;
        if (waders != null && waders.getId() == 13260)
            bonus += 0.6;
        if (boots != null && boots.getId() == 13261)
            bonus += 0.2;

        /* Whole set gives an additional 0.5% exp bonus */
        if (bonus >= 3.0)
            bonus += 0.5;

        return bonus;
    }

}