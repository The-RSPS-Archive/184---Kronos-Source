package io.ruin.model.activities.wilderness;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.KillingSpree;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.utility.Misc;
import io.ruin.utility.TimedList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class BountyHunter {

    private static final Bounds EDGEVILLE_BOUNDS = new Bounds(2993, 3523, 3124, 3597, -1);

    public enum Targeting {

        ALL("All Wilderness", p -> true),
        DISABLED("Disabled", p -> false),
        EDGEVILLE("Edgeville Only", p -> p.getPosition().inBounds(EDGEVILLE_BOUNDS));

        public final String name;

        private final Predicate<Player> active;

        Targeting(String name, Predicate<Player> active) {
            this.name = name;
            this.active = active;
        }

        public boolean isActive(Player player) {
            return active.test(player);
        }

        private static boolean match(Player player, Player searching) {
            Targeting t1 = player.getBountyHunter().targeting;
            if (!t1.isActive(player))
                return false;
            Targeting t2 = searching.getBountyHunter().targeting;
            if (t1 == t2)
                return true;
            if (t1 == ALL)
                return t2.isActive(player);
            if (t2 == ALL)
                return t1.isActive(searching);
            return false;
        }

    }

    /**
     * Separator
     */

    private Player player;

    @Expose public Targeting targeting = Targeting.ALL;

    public boolean interfaceHidden;

    public Player target;

    @Expose private TimedList recentTargets = new TimedList();

    protected int returnTicks;

    private int returnTimeoutTicks;

    @Expose private int skips;

    @Expose private long skipsResetAt;

    @Expose private long penaltyEnd;

    public void init(Player player) {
        this.player = player;
    }

    protected void checkActive() {
        if (targeting == Targeting.DISABLED && target != null) {
            //If player disables their targeting and they have a target, auto skip.
            skip(false);
        }
        if (player.wildernessLevel > 0) {
            if (returnTicks > 0 && ++returnTimeoutTicks >= 60)
                returnTicks = 0;
            if (interfaceHidden) {
                if (targeting.isActive(player)) {
                    interfaceHidden = false;
                    player.getPacketSender().sendVarp(20002, 0); //custom
                }
            } else {
                if (!targeting.isActive(player)) {
                    interfaceHidden = true;
                    player.getPacketSender().sendVarp(20002, 1); //custom
                }
            }
        } else {
            if (returnTicks == 0) {
                if (target != null) {
                    returnTicks = 200;
                    Config.WILDERNESS_TIMER.set(player, returnTicks);
                    player.getPacketSender().setHidden(Interface.WILDERNESS_OVERLAY, 57, false);
                    player.getPacketSender().setHidden(Interface.WILDERNESS_OVERLAY, 58, true);
                    player.sendMessage("<col=ff0000>You have 2 minutes to return to the Wilderness before you lose your target.");
                }
            } else if (returnTicks > 0) {
                if (--returnTicks == 0) {
                    player.closeInterface(InterfaceType.WILDERNESS_OVERLAY);
                    if (target != null) {
                        target.sendMessage("Your target has fled, you will be assigned a new one shortly.");
                        skip(false);
                    }
                } else {
                    if (returnTicks == 100)
                        player.sendMessage("<col=ff0000>You have one minute to return to the Wilderness before you lose your target.");
                    if (returnTimeoutTicks > 0) {
                        Config.WILDERNESS_TIMER.set(player, returnTicks + 1); //+ 1 for slightly better accuracy...
                        player.getPacketSender().setHidden(Interface.WILDERNESS_OVERLAY, 57, false);
                        player.getPacketSender().setHidden(Interface.WILDERNESS_OVERLAY, 58, true);
                    }
                }
            }
            returnTimeoutTicks = 0;
        }
    }

    /**
     * Targeting
     */

    private boolean searchForTarget(long ms) {
        if (player.getCombat().isDead())
            return false;
        if (target != null) {
            updateTargetInfo();
            return false;
        }
        if (!targeting.isActive(player))
            return false;
        if (hasPenalty(ms)) {
            updatePenaltyInfo(Math.max(1L, TimeUnit.MILLISECONDS.toMinutes(penaltyEnd - ms)));
            return false;
        }
        return true;
    }

    private boolean allowAsTarget(Player searching, long ms) {
        if (player.getCombat().isDead())
            return false;
        if (player.getUserId() == searching.getUserId())
            return false;
        if (player.getIpInt() == searching.getIpInt())
            return false;
        if (Math.abs(player.getCombat().getLevel() - searching.getCombat().getLevel()) > 5)
            return false;
        if (target != null)
            return false;
        if (!Targeting.match(player, searching))
            return false;
        if (hasPenalty(ms))
            return false;
        return !recentTargets.contains(searching.getUserId(), ms, 10L);
    }

    private void target(Player pTarget, long ms) {
        target = pTarget;
        recentTargets.add(pTarget.getUserId(), ms);
        player.getPacketSender().sendHintIcon(target);
        player.sendMessage("<col=ff0000>You've been assigned a target: " + pTarget.getName());
        updateTargetInfo();
    }

    private void removeTarget() {
        target = null;
        player.getPacketSender().resetHintIcon(false);
        if (player.wildernessLevel == 0)
            player.closeInterface(InterfaceType.WILDERNESS_OVERLAY);
        returnTicks = returnTimeoutTicks = 0;
        clearTargetInfo();
    }

    private boolean hasPenalty(long ms) {
        if (skips >= 10) {
            skipsResetAt = skips = 0;
            penaltyEnd = ms + 1800000; //30 minutes
            return true;
        }
        if (skips > 0) {
            if (skipsResetAt == 0)
                skipsResetAt = ms + 1800000; //30 minutes
            else if (ms >= skipsResetAt)
                skipsResetAt = skips = 0;
        }
        return ms < penaltyEnd;
    }

    /**
     * Info (Interface)
     */

    private void updateTargetInfo() {
        /**
         * Target info
         */
        String skullImg = target.getCombat().isSkulled() ? "<img=" + KillingSpree.imgId(target) + "> " : "";
        int distance = Misc.getDistance(player.getPosition(), target.getPosition());
        String distanceColor;
        if (distance <= 20)
            distanceColor = "<col=009900>";
        else if (distance <= 40)
            distanceColor = "<col=996600>";
        else if (distance <= 60)
            distanceColor = "<col=990000>";
        else if (distance <= 80)
            distanceColor = "<col=df0101>";
        else
            distanceColor = "<col=0080ff>";
        String wild;
        if (target.wildernessLevel > 0) {
            int base = (int) Math.floor(target.wildernessLevel / 5) * 5;
            wild = "Lvl " + Math.max(1, base) + "-" + (base + 4);
        } else {
            wild = "Safe";
        }
        player.getPacketSender().sendString(Interface.WILDERNESS_OVERLAY, 47, skullImg + target.getName());
        player.getPacketSender().sendString(Interface.WILDERNESS_OVERLAY, 48, distanceColor + wild + ", Cmb " + target.getCombat().getLevel());

        /**
         * Target wealth
         */
        long targetWealth = countWealth(target.getInventory()) + countWealth(target.getEquipment());

        if (target.getInventory().hasId(11941))
            targetWealth += countWealth(target.getLootingBag());
        if (target.getInventory().hasId(12791))
            targetWealth += countWealth(target.getRunePouch());

        if (targetWealth >= 2500000)
            Config.BOUNTY_HUNTER_RISK.set(player, 5); //red
        else if (targetWealth >= 1100000)
            Config.BOUNTY_HUNTER_RISK.set(player, 4); //blue
        else if (targetWealth >= 500000)
            Config.BOUNTY_HUNTER_RISK.set(player, 3); //green
        else if (targetWealth >= 100000)
            Config.BOUNTY_HUNTER_RISK.set(player, 2); //silver
        else
            Config.BOUNTY_HUNTER_RISK.set(player, 1); //brown

        /**
         * Target emblems
         */
        Item highestEmblem = findHighestEmblem(target);
        Config.BOUNTY_HUNTER_EMBLEM.set(player, highestEmblem == null ? 0 : Math.max(1, highestEmblem.getId() - 12746));
    }

    private void clearTargetInfo() {
        player.getPacketSender().sendString(90, 47, "None");
        player.getPacketSender().sendString(90, 48, "Level: -----");
        Config.BOUNTY_HUNTER_RISK.set(player, 0);
        Config.BOUNTY_HUNTER_EMBLEM.set(player, 0);
    }

    private void updatePenaltyInfo(long minutes) {
        player.getPacketSender().sendString(90, 47, "<col=ff0000>---</col>");
        player.getPacketSender().sendString(90, 48, minutes + " " + (minutes > 1 ? "mins" : "min"));
        Config.BOUNTY_HUNTER_RISK.set(player, 0);
        Config.BOUNTY_HUNTER_EMBLEM.set(player, 0);
    }

    /**
     * Skipping
     */

    public void skip(boolean warning) {
        if(player.getCombat().isDefending(17)) {
            player.sendMessage("You need to be out of combat for 10 seconds before skipping a bounty target.");
            return;
        }
        if (warning) {
            String message = "<col=ff0000>Warning:</col> Skipping too many targets in a short period of time can<br>cause you to incur a target restriction penalty. You should not use<br>this too frequently.";
            int lineHeight;
            if (skips == 0) {
                lineHeight = 24;
            } else if (skips == 1) {
                message += " You have abandoned your target once recently.";
                lineHeight = 24;
            } else {
                message += " You have abandoned your target " + skips + " times<br>recently.";
                lineHeight = 17;
            }
            player.dialogue(
                    new MessageDialogue(message).lineHeight(lineHeight),
                    new OptionsDialogue(
                            new Option("Yes.", () -> {
                                if (target != null)
                                    skip(false);
                                player.closeDialogue();
                            }),
                            new Option("No.", player::closeDialogue)
                    )
            );
        } else {
            skips++;

            target.getBountyHunter().removeTarget();
            removeTarget();

            if (skips == 1)
                player.sendMessage("<col=ff0000>You have abandoned your target once in the last 30 minutes.");
            else if (skips < 10)
                player.sendMessage("<col=ff0000>You have abandoned your target " + skips + " times in the last 30 minutes.");
            else
                player.sendMessage("<col=ff0000>You have abandoned your target 10 times in 30 minutes. You can't have another one for 30 minutes.");
        }
    }

    /**
     * Logging out
     */

    public void loggedOut() {
        if (target != null) {
            target.sendMessage("Your target has logged out, you will be assigned a new one shortly.");
            skip(false);
        }
    }

    /**
     * Death
     */

    public boolean deathByTarget(Player pKiller) {
        if (target != null) {
            Player target = this.target;
            target.getBountyHunter().removeTarget();
            removeTarget();
            if (pKiller != null && pKiller.getUserId() == target.getUserId()) {
                long ms = System.currentTimeMillis();
                player.getBountyHunter().penaltyEnd = ms + 360000; //6 minutes (same as rs)
                pKiller.getBountyHunter().penaltyEnd = ms + 60000; //1 minute
                pKiller.sendFilteredMessage("<col=ff0000>You've killed your target: " + player.getName());
                pKiller.sendFilteredMessage("Next target in: <col=ff0000> 1 minute");
                Item emblem = findHighestEmblem(pKiller);
                if (emblem != null && emblem.getId() != 12756)
                    emblem.setId(emblem.getId() == 12746 ? 12748 : emblem.getId() + 1);
                else
                    new GroundItem(12746, 1).owner(pKiller).position(player.getPosition()).spawn();
                int kills = Config.BOUNTY_HUNTER_TARGET_KILLS.get(pKiller) + 1;
                int record = Config.BOUNTY_HUNTER_TARGET_RECORD.get(pKiller);
                if (kills > record)
                    Config.BOUNTY_HUNTER_TARGET_RECORD.set(pKiller, kills);
                Config.BOUNTY_HUNTER_TARGET_KILLS.set(pKiller, kills);
                return true;
            }
            target.sendMessage("Your target has died, you will be assigned a new one shortly.");
        }
        if (pKiller != null && player.wildernessLevel > 0) { //this wilderness check isn't very good...
            int kills = Config.BOUNTY_HUNTER_ROGUE_KILLS.get(pKiller) + 1;
            int record = Config.BOUNTY_HUNTER_ROGUE_RECORD.get(pKiller);
            if (kills > record)
                Config.BOUNTY_HUNTER_ROGUE_RECORD.set(pKiller, kills);
            Config.BOUNTY_HUNTER_ROGUE_KILLS.set(pKiller, kills);
            //Auto upgrade emblem if non-target kill
            Item emblem = findHighestEmblem(pKiller);
            if (emblem != null && emblem.getId() != 12756)
                emblem.setId(emblem.getId() == 12746 ? 12748 : emblem.getId() + 1);

        }
        return false;
    }

    /**
     * Misc
     */

    private static long countWealth(ItemContainer container) {
        long wealth = 0;
        for (Item item : container.getItems()) {
            if (item != null)
                wealth += (item.getAmount() * item.getDef().protectValue);
        }
        return wealth;
    }

    private static Item findHighestEmblem(Player player) {
        Item highest = null;
        for (Item item : player.getInventory().getItems()) {
            if (item != null && (item.getId() == 12746 || (item.getId() >= 12748 && item.getId() <= 12756))) {
                if (highest == null || item.getId() > highest.getId())
                    highest = item;
            }
        }
        return highest;
    }

    /**
     * Register Event & Interface.
     */

    static {
        List<Player> potentialTargets = new ArrayList<>(500);
        World.startEvent(e -> {
            while (true) {
                long ms = System.currentTimeMillis();
                for (Player p1 : Wilderness.players) {
                    if (!p1.getBountyHunter().searchForTarget(ms))
                        continue;
                    for (Player p2 : Wilderness.players) {
                        if (!p2.getBountyHunter().allowAsTarget(p1, ms))
                            continue;
                        potentialTargets.add(p2);
                    }
                    if (!potentialTargets.isEmpty()) {
                        Player p2 = Random.get(potentialTargets);
                        p2.getBountyHunter().target(p1, ms);
                        p1.getBountyHunter().target(p2, ms);
                        potentialTargets.clear();
                        continue;
                    }
                    p1.getBountyHunter().clearTargetInfo();
                }
                e.delay(20);
            }
        });
        InterfaceHandler.register(Interface.WILDERNESS_OVERLAY, h -> h.actions[56] = (SimpleAction) p -> p.getBountyHunter().skip(true));
    }

}