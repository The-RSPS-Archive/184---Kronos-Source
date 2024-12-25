package io.ruin.model.activities.wilderness;

import com.google.common.collect.Lists;
import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.NpcID;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.journal.toggles.EdgevilleBlacklist;
import io.ruin.model.inter.journal.toggles.RiskProtection;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Broadcast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Wilderness {

    private static final int BASE_BM_PER_BOSS_DAMAGE = 2;
    private static final int BASE_POINTS_PER_BOSS_DAMAGE = 1;

    public static final Bounds REVENANT_CAVE = new Bounds(3136, 10048, 3263, 10239, -1);

    public static final int[][] mageArenaBounds = new int[][] {
            { 3083, 3942 },
            { 3094, 3953 },
            { 3115, 3953 },
            { 3127, 3942 },
            { 3127, 3922 },
            { 3117, 3912 },
            { 3093, 3912 },
            { 3082, 3922 }
    };

    public static ArrayList<Player> players = new ArrayList<>(500);

    private static boolean checkActive(Player player) {
        if(player.wildernessLevel == -1 || player.getMovement().hasMoved())
            player.wildernessLevel = getLevel(player.getPosition());
        player.getBountyHunter().checkActive();
        return player.wildernessLevel > 0;
    }

    private static void entered(Player player) {
/*        int ipCount = 1;
        ArrayList<Player> ipSharedPlayers = new ArrayList();
        for(Player p : players) {
            if(player.getIp().equalsIgnoreCase(p.getIp()))
                ipCount++;
            if(ipCount > 2) {
                teleportOutOfWilderness(ipSharedPlayers);
                break;
            }
        }*/

        players.add(player);
        player.getPacketSender().sendDiscordPresence("Wilderness");
        player.attackPlayerListener = Wilderness::allowAttack;
        player.attackNpcListener = Wilderness::allowNPCAttack;
        RiskProtection.monitorRiskProtection(player);
        player.getPacketSender().sendVarp(20003, 0); //custom to make sure client doesn't think pvp world
        player.openInterface(InterfaceType.WILDERNESS_OVERLAY, Interface.WILDERNESS_OVERLAY);
        if (!player.bountyHunterOverlay) {
            player.getPacketSender().sendVarp(20002, 1);
        } else {
            player.getPacketSender().sendVarp(20002, 0);
        }
        player.getPacketSender().setHidden(Interface.WILDERNESS_OVERLAY, 63, true); //hide safe area sprite
        player.getPacketSender().setHidden(Interface.WILDERNESS_OVERLAY, 66, false); //show wilderness level
        Config.IN_PVP_AREA.set(player, 1);
        player.setAction(1, PlayerAction.ATTACK);
        player.getEquipment().update(0); //force a slot update
        player.getEquipment().sendUpdates();
    }

    private static boolean allowNPCAttack(Player player, NPC npc, boolean b) {
        int[] mageArenaNpcs = {NpcID.BATTLE_MAGE, NpcID.BATTLE_MAGE_1611, NpcID.BATTLE_MAGE_1612 };
        if (player.getBounds().inBounds(mageArenaBounds, player.getPosition())) {
            boolean mageArenaNPC = false;
            for (int id : mageArenaNpcs) {
                if (npc.getId() == id)
                    mageArenaNPC = true;
            }
            if (mageArenaNPC && !player.getCombat().getAttackStyle().isMagic() && !player.getCombat().useSpell()) {
                player.sendMessage("You can only attack with magical attacks in the Mage Arena.");
                return false;
            }
        }
        return true;
    }

    private static void exited(Player player, boolean logout) {
        players.remove(player);
        player.getBountyHunter().interfaceHidden = false;
        if(!logout) {
            player.attackPlayerListener = null;
            player.attackNpcListener = null;
            player.mageArena = player.resourceArea = false; //just to be safe
            player.getCombat().resetTb();
            player.supplyChestRestricted = false;
            player.insideWildernessAgilityCourse = false;
            player.getCombat().resetKillers(); //important
            //TODO: - clear hits???????????????
            player.getPacketSender().sendDiscordPresence("Idle");
            Config.IN_PVP_AREA.set(player, 0);
            if(player.getBountyHunter().returnTicks == 0)
                player.closeInterface(InterfaceType.WILDERNESS_OVERLAY);
            player.setAction(1, null);
            player.getEquipment().update(0); //force a slot update
            player.getEquipment().sendUpdates();
        }
    }

    private static boolean allowAttack(Player player, Player pTarget, boolean message) {

        if (player.getBounds().inBounds(mageArenaBounds, player.getPosition())) {
            if (!player.getCombat().getAttackStyle().isMagic() && !player.getCombat().useSpell()) {
                player.sendMessage("You can only attack with magical attacks in the Mage Arena.");
                return false;
            }
        }

        if(pTarget.wildernessLevel == 0) {
            if(message)
                player.sendMessage("You can't attack players who aren't in the Wilderness.");
            return false;
        }

        if (isAtWildernessLimitForIP(player)) {
            if(message)
                player.sendMessage("You can't attack yourself.");
            return false;
        }

        if(pTarget == player.getBountyHunter().target) {
            if (Math.abs(player.getCombat().getLevel() - pTarget.getCombat().getLevel()) > 5) {
                if (message)
                    player.sendMessage("You must be within 5 combat levels of your target to attack them.");
                return false;
            }
            return true;
        }

        if(!player.inMulti() && !pTarget.inMulti()) {
            RiskProtection.monitorRiskProtection(player);
            RiskProtection.monitorRiskProtection(pTarget);
            long playerProtection = RiskProtection.protectionValue(player);
            long pTargetProtection = RiskProtection.protectionValue(pTarget);

            if (player.riskedBloodMoney < pTargetProtection) {
                player.sendMessage(Color.DARK_RED.wrap("This player is currently under risk protection. You must risk an additional " +
                        NumberUtils.formatNumber(pTargetProtection - player.riskedBloodMoney) + " coins in order to attack them."));
                return false;
            }

            if (playerProtection > pTarget.riskedBloodMoney) {
                player.riskProtectionTier = 0;
                player.riskProtectionExpirationDelay.delay(10 * 100); // 10 minutes
                player.sendMessage("By attacking this player you forfeit your risk protection for 10 minutes.");
//                if (player.journal == Journal.TOGGLES)
//                    RiskProtection.INSTANCE.send(player);
            }
        }

        int wildernessLevel = player.wildernessLevel;
        int combatLevel = player.getCombat().getLevel();
        int targetWildernessLevel = pTarget.wildernessLevel;
        int targetCombatLevel = pTarget.getCombat().getLevel();
        if(!((combatLevel + wildernessLevel >= targetCombatLevel && combatLevel - wildernessLevel <= targetCombatLevel) && (targetCombatLevel + targetWildernessLevel) >= combatLevel && targetCombatLevel - targetWildernessLevel <= combatLevel)) {
            if(message)
                player.sendMessage("Your combat level difference is to high to attack from here. Please move deeper into the wilderness");
            return false;
        }

        return EdgevilleBlacklist.canAttack(player, pTarget);
    }

    /**
     * Checks if the {@code player}'s remote ip address has 2 or more players already in the wilderness.
     */
    private static boolean isAtWildernessLimitForIP(Player player) {
        int count = 0;

        for (Player others : Wilderness.players) {
            if (player.getIpInt() == others.getIpInt() && ++count >= 2) {
                return true;
            }
        }

        return false;
    }

    private static void teleportOutOfWilderness(ArrayList<Player> players) {
        for(Player p : players) {
            if(p.wildernessLevel > 30)
                continue;
            if(p.getCombat().checkTb())
                continue;
            p.startEvent(e -> {
                p.lock();
                p.animate(714);
                p.graphics(111, 92, 0);
                p.sendMessage(Color.DARK_RED.wrap("You can only have 2 accounts inside the wilderness at a time."));
                p.publicSound(200);
                e.delay(3);
                p.getMovement().teleport(3087, 3503, 0);
                p.animate(-1);
                p.unlock();
            });
            break;
        }
    }

    private static void setLevels(Bounds bounds, Function<Integer, Integer> f) {
        bounds.forEachPos(pos -> setLevel(pos.getX(), pos.getY(), pos.getZ(), f.apply(pos.getY())));
    }

    private static void setLevels(Bounds bounds, int wildernessLevel) {
        bounds.forEachPos(pos -> setLevel(pos.getX(), pos.getY(), pos.getZ(), wildernessLevel));
    }

    private static void setLevel(int absX, int absY, int absZ, int level) {
        Tile.get(absX, absY, absZ, true).wildernessLevel = level;
    }

    public static int getLevel(Position position) {
        return getLevel(position.getX(), position.getY(), position.getZ());
    }

    public static int getLevel(int x, int y, int z) {
        Tile tile = Tile.get(x, y, z, false);
        return tile == null ? 0 : tile.wildernessLevel;
    }

    public static void setSafePVPInstance(Bounds bounds, boolean safe) {
        bounds.forEachPos(pos -> setSafePVPInstance(pos.getX(), pos.getY(), pos.getZ(), safe));
    }

    private static void setSafePVPInstance(int absX, int absY, int absZ, boolean safe) {
        Tile.get(absX, absY, absZ, true).safePVPInstance = safe;
    }

    public static boolean getSafePVPInstance(int x, int y, int z) {
            Tile tile = Tile.get(x, y, z, false);
            return tile != null && tile.safePVPInstance;
    }

    private static final List<Item> RESOURCE_PACK_LOOT = Lists.newArrayList(
            new Item(6686, 15), //saradomin brew
            new Item(12695, 6), //super combat
            new Item(3025, 10), //super restore
            new Item(392, 35), //manta ray
            new Item(12626, 6), //stamina potion
            new Item(3145, 35)
    );

    static {
        //Set default areas
        setLevels(new Bounds(2944, 3525, 3391, 4351, -1), y -> ((y - 3520) / 8) + 1); //main
        setLevels(new Bounds(3008, 10112, 3071, 10175, -1), y -> ((y - 9920) / 8) - 1); //gwd
        setLevels(new Bounds(2944, 9920, 3391, 10879, -1), y -> ((y - 9920) / 8) + 1); //idk

        //Unset certain areas
        setLevels(new Bounds(2941, 3676, 2947, 3681, -1), 0); //trollheim shortcut
        setLevels(new Bounds(2998, 3525, 3026, 3536, -1), 0); //black knight fortress part 1
        setLevels(new Bounds(3005, 3537, 3023, 3545, -1), 0); //black knight fortress part 2
        setLevels(new Bounds(3024, 3537, 3026, 3542, -1), 0); //black knight fortress part 3
        setLevels(new Bounds(3027, 3525, 3032, 3530, -1), 0); //black knight fortress part 4
        setLevels(new Bounds(3003, 3537, 3004, 3538, -1), 0); //black knight fortress part 5
        setLevel(2997, 3525, 0, 0);

        ItemAction.registerInventory(30104, "open", (player, item) -> {
            Item reward = Random.get(RESOURCE_PACK_LOOT);
            player.getInventory().remove(item.getId(), 1);
            player.getInventory().add(reward);
        });
        ItemAction.registerInventory(30141, "read", (player, item) -> {
            player.blackChinchompaBoost.addDelaySeconds((int) TimeUnit.MINUTES.toSeconds(30));
            player.getInventory().remove(item);
            player.sendMessage("You now have " + player.blackChinchompaBoost.remainingToMins() + " minutes of Black chinchompa skilling boost.");
        });
        ItemAction.registerInventory(30143, "read", (player, item) -> {
            player.darkCrabBoost.addDelaySeconds((int) TimeUnit.MINUTES.toSeconds(30));
            player.getInventory().remove(item);
            player.sendMessage("You now have " + player.darkCrabBoost.remainingToMins() + " minutes of Dark crab skilling boost.");
        });

        LoginListener.register(player -> {
            if (player.blackChinchompaBoostTimeLeft > 0) {
                player.blackChinchompaBoost.delay(player.blackChinchompaBoostTimeLeft);
                player.blackChinchompaBoostTimeLeft = 0;
            }
            if (player.darkCrabBoostTimeLeft > 0) {
                player.darkCrabBoost.delay(player.darkCrabBoostTimeLeft);
                player.darkCrabBoostTimeLeft = 0;
            }
        });

        //Register map listener
        MapListener.register(Wilderness::checkActive)
                .onEnter(Wilderness::entered)
                .onExit(Wilderness::exited);
    }

    /**
     * Eco stuff
     */

    private static final List<StatType> BONUS_SKILLS = Arrays.asList(
            StatType.Attack,
            StatType.Strength,
            StatType.Defence,
            StatType.Hitpoints,
            StatType.Ranged,
            StatType.Magic,
            StatType.Prayer,
            StatType.Slayer,
            StatType.Fishing,
            StatType.Mining,
            StatType.Woodcutting,
            StatType.Thieving
    );

    public static double getXPModifier(Player player, StatType stat) {
        if(player.wildernessLevel < 1)
            return 0;
        if(player.resourceArea)
            return 0.8;
        if(BONUS_SKILLS.contains(stat))
            return (Math.min(30, player.wildernessLevel) * 0.005) + (Math.max(0, player.wildernessLevel - 30) * 0.01);
        return 0;
    }

    private static final Item[] ARMOUR_TABLE = {
            new Item(22616), // Vesta's chainbody
            new Item(22619), // Vesta's longsword
            new Item(22625), // Statius's full helm
            new Item(22628), // Statius's platebody
            new Item(22631), // Statius's platelegs
            new Item(22650), // Zuriel's hood
            new Item(22653), // Zuriel's robe top
            new Item(22656), // Zuriel's robe bottom
            new Item(22638), // Morrigan's coif
            new Item(22641), // Morrigan's leather body
            new Item(22644), // Morrigan's leather chaps
    };

    private static final Item[] CLUE_KEY_TABLE = {
            new Item(3455),
            new Item(3457),
            new Item(3458),
    };

    private static final Item[] WEAPON_TABLE = {
            new Item(22610), // Vesta's spear
            new Item(22613), // Vesta's longsword
            new Item(22622), // Statius's warhammer
            new Item(22647), // Zuriel's staff
            new Item(22647), // Morrigan's staff
    };

    private static final Item[] PVP_ITEM_DROP_TABLE = {
              new Item(22610), // vesta
              new Item(22613),
              new Item(22616),
              new Item(22619),

              new Item(22622), // statius
              new Item(22625),
              new Item(22628),
              new Item(22631),

              new Item(22634, 20), // Morrigan
              new Item(22636, 20),
              new Item(22638),
              new Item(22641),
              new Item(22644),

              new Item(22647), // Zuriel
              new Item(22650),
              new Item(22653),
              new Item(22656),
    };

    private static int getPvPItemChance(NPC npc) {
        return Math.max(500, (int) (2000 - (npc.getDef().combatLevel * 2) - (Math.pow(npc.getDef().combatLevel, 2) / 300)));
    }

    public static void rollPvPItemDrop(Player player, NPC npc, Position dropPosition) {
        int chance;
        Item[] table;

        switch (npc.getDef().name.toLowerCase()) {
            case "callisto":
            case "venenatis":
            case "vet'ion":
            case "vet'ion reborn":
                chance = 500;
                table = ARMOUR_TABLE;
                break;
            case "chaos elemental":
            case "crazy archaeologist":
            case "chaos fanatic":
            case "scorpia":
                chance = 1500;
                table = ARMOUR_TABLE;
                break;
            case "revenant cyclops":
            case "revenant hellhound":
            case "revenant demon":
                chance = 3000;
                table = WEAPON_TABLE;
                break;
            case "revenant ork":
                chance = 2500;
                table = WEAPON_TABLE;
                break;
            case "revenant dark beast":
                chance = 2000;
                table = WEAPON_TABLE;
                break;
            case "revenant knight":
            case "revenant dragon":
                chance = 1850;
                table = WEAPON_TABLE;
                break;
            default:
                return;
        }

        if (Random.rollDie(chance)) {
            Item drop = Random.get(table);
            new GroundItem(drop).position(dropPosition).owner(player).spawn();
            String message = player.getName() + " just received ";
            if(drop.getAmount() > 1)
                message += NumberUtils.formatNumber(drop.getAmount()) + " x " + drop.getDef().name;
            else
                message += drop.getDef().name;
            Broadcast.WORLD.sendNews(player, message + " from " + npc.getDef().descriptiveName + "!");
            player.sendMessage("You have been red skulled and tele-blocked because of your loot!");
            player.getCombat().skullHighRisk();
            player.getCombat().teleblock();
        }
    }

    public static void rollClueKeyDrop(Player player, NPC npc, Position dropPosition) {
        int chance;
        Item[] table;
        if (npc.wildernessSpawnLevel <= 0) {
            return;
        }
        switch (npc.getDef().name.toLowerCase()) {
            case "callisto":
            case "venenatis":
            case "vet'ion":
            case "vet'ion reborn":
            case "chaos elemental":
            case "crazy archaeologist":
            case "chaos fanatic":
            case "scorpia":
                chance = 25;
                table = CLUE_KEY_TABLE;
                break;
            case "ankou":
            case "hobgoblin":
            case "magic axe":
            case "ice warrior":
            case "ice giant":
                chance = 200;
                table = CLUE_KEY_TABLE;
                break;
            case "hellhound":
            case "king black dragon":
            case "green dragon":
                chance = 150;
                table = CLUE_KEY_TABLE;
                break;
            case "zombie":
                chance = 400;
                table = CLUE_KEY_TABLE;
                break;
            default:
                return;
        }

        if (Random.rollDie(chance)) {
            Item drop = Random.get(table);
            new GroundItem(drop).position(dropPosition).owner(player).spawn();
        }
    }

    public static void bloodMoneyDrop(Player player, NPC npc) {
        int bloodMoney;
        if (npc.wildernessSpawnLevel <= 0) {
            return;
        }
        switch (npc.getDef().name.toLowerCase()) {
            case "callisto":
            case "venenatis":
            case "vet'ion":
            case "vet'ion reborn":
            case "chaos elemental":
            case "crazy archaeologist":
            case "chaos fanatic":
            case "scorpia":
                bloodMoney = Random.get(2, 30);
                break;
            case "revenant cyclops":
            case "revenant hellhound":
            case "revenant demon":
                bloodMoney = Random.get(1, 3);
                break;
            case "revenant ork":
                bloodMoney = Random.get(2, 5);
                break;
            case "revenant dark beast":
                bloodMoney = Random.get(3, 6);
                break;
            case "revenant knight":
            case "revenant dragon":
                bloodMoney = Random.get(5, 10);
                break;
            default:
                return;
        }

        player.rewardBm(npc, bloodMoney);
    }

    /**
     * Misc
     */

    public static boolean stopFollowCheck(Player player) {
        if(player.wildernessLevel == 0 && player.pvpInstancePosition == null)
            return true;
        return !player.pvpAttackZone && player.pvpInstancePosition != null;
    }

    public static boolean stopFollowing(Player player, int stepX, int stepY) {
        if(stopFollowCheck(player)) {
            TargetRoute route = player.getRouteFinder().targetRoute;
            boolean check = (player.getMovement().following != null) || (route != null && route.target != null && route.target.player != null && route.target != player.getCombat().getTarget());
            if(check && ((player.pvpInstancePosition != null && !getSafePVPInstance(stepX, stepY, player.getHeight())) || (player.wildernessLevel == 0 && getLevel(stepX, stepY, player.getHeight()) > 0))) {
                player.faceNone(false);
                player.getMovement().following = null;
                player.getMovement().reset();
                if(route != null)
                    route.reset();
                player.sendMessage("You were stopped from following someone into the wilderness.");
                return true;
            }
        }
        return false;
    }

    public static void resourcePackWithBoss(Player player, NPC npc) {
        int chance;

        switch (npc.getDef().name.toLowerCase()) {
            case "callisto":
            case "venenatis":
            case "vet'ion":
            case "vet'ion reborn":
            case "chaos elemental":
            case "crazy archaeologist":
            case "chaos fanatic":
            case "scorpia":
                chance = 25;
                break;
            default:
                return;
        }
        if (Random.rollDie(chance)) {
            new GroundItem(new Item(30104, 1)).position(npc.getPosition()).owner(player).spawn();
        }
    }

}