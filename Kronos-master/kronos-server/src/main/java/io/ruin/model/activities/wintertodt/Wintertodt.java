package io.ruin.model.activities.wintertodt;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.map.Direction;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Region;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;
import io.ruin.utility.TickDelay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Wintertodt { // TODO make hits not reset events


    public static final int REGION_ID = 6462;
    public static final Region REGION = Region.get(REGION_ID);

    static final int EMPTY_BRAZIER = 29312;
    static final int BROKEN_BRAZIER = 29313;
    static final int BURNING_BRAZIER = 29314;

    private static final int MAX_HP = 3500;
    private static int health = 0;

    private static final TickDelay startDelay = new TickDelay();

    private static final TickDelay pyromancerTextDelay = new TickDelay();
    private static final String[] PYROMANCER_DEAD_TEXT = {"My flame burns low.", "Mummy!", "I think I'm dying.", "We are doomed.", "Ugh, help me!"};
    private static boolean started = false;

    public static final int BRUMA_ROOT = 20695;
    public static final int BRUMA_KINDLING = 20696;
    public static final int REJUV_POT = 20697;
    public static final int BRUMA_HERB = 20698;

    static final int PYROMANCER = 7371;
    public static final int INCAPACITATED_PYROMANCER = 7372;
    private static final int FLAME = 7373;

    private static final int SNOW_EFFECT = 26690;

    private static final int ACTIVE_STORM = 29308;
    private static final int INACTIVE_STORM = 29309;
    private static final GameObject WINTERTODT = Tile.getObject(ACTIVE_STORM, 1627, 4004, 0, 10, 0);

    private static final List<Integer> GAME_ITEMS = Arrays.asList(BRUMA_ROOT, Wintertodt.BRUMA_KINDLING, 20697, Wintertodt.BRUMA_HERB, 20699, 20700, 20701, 20702);

    public static final Brazier[] BRAZIERS = {
            new Brazier(Tile.getObject(29312, 1620, 3997, 0, 10, 0), new NPC(7371).spawn(1619, 3996, 0, Direction.NORTH_EAST, 0), 0, 0), // sw
            new Brazier(Tile.getObject(29312, 1620, 4015, 0, 10, 0), new NPC(7371).spawn(1619, 4018, 0, Direction.SOUTH_EAST, 0), 0, 2), // nw
            new Brazier(Tile.getObject(29312, 1638, 4015, 0, 10, 0), new NPC(7371).spawn(1641, 4018, 0, Direction.SOUTH_WEST, 0), 2, 2), // ne
            new Brazier(Tile.getObject(29312, 1638, 3997, 0, 10, 0), new NPC(7371).spawn(1641, 3996, 0, Direction.NORTH_WEST, 0), 2, 0) // se
    };

    public static boolean isActive() {
        return !startDelay.isDelayed();
    }

    static {
        startDelay.delay(100);
        WINTERTODT.setId(INACTIVE_STORM);
        started = false;

        Arrays.stream(BRAZIERS).forEach(b -> ObjectAction.register(b.getObject(), 1, ((player, obj) -> b.getObject().setId(BURNING_BRAZIER))));
        Arrays.stream(BRAZIERS).forEach(b -> b.getObject().skipReachCheck = pos -> Misc.getDistance(b.getObject().x + 1, b.getObject().y + 1, pos.getX(), pos.getY()) <= 2);
        World.startEvent(event -> { // main game loop
            while (true) {
                if (startDelay.isDelayed()) {
                    event.delay(startDelay.remaining());
                }
                if (!started) {
                    start();
                }
                if (!REGION.players.isEmpty()) {
                    applyColdDamage();
                    extinguishBraziers();
                    doMagicAttack();
                    attackPyromancers();
                    pyromancerText();
                    dealDamage();
                }
                sendAll();
                event.delay(2);

            }
        });
    }

    private static void applyColdDamage() {
        REGION.players.forEach(p -> {
            if (p.getAbsY() > 3987 && Random.rollDie(25, 1)) {
                p.hit(new Hit().randDamage(getColdDamage(p)).setResetActions(false));
                p.sendMessage("The cold of the Wintertodt seeps into your bones.");
            }
        });
    }

    private static void dealDamage() {
        int damage = 0;
        for (Brazier b : BRAZIERS) {
            if (b.isPyromancerAlive() && b.getObject().id == BURNING_BRAZIER) {
                shootFlame(b);
                damage += 5;
            }
        }
        if (damage > 0) {
            health -= damage;
            if (health <= 0) {
                death();
            }
        } else {
            health = Math.min(MAX_HP, health + 5);
        }
    }

    private static void death() {
        started = false;
        startDelay.delay(100);
        WINTERTODT.setId(INACTIVE_STORM);
        for (Brazier b : BRAZIERS) {
            b.getPyromancer().forceText("We can rest for a time.");
            b.getPyromancer().transform(PYROMANCER);
            b.getPyromancer().setHp(b.getPyromancer().getMaxHp());
            b.getPyromancer().resetAnimation();
            b.getObject().setId(EMPTY_BRAZIER);
        }
        REGION.players.forEach(Wintertodt::award);
    }

    private static void award(Player player) {
        removeGameItems(player);
        player.getStats().addXp(StatType.Firemaking, player.getStats().get(StatType.Firemaking).fixedLevel * 100, true);
        if (player.wintertodtPoints > player.wintertodtHighscore) {
            player.wintertodtHighscore = player.wintertodtPoints;
            player.sendMessage("You have a new high score! " + player.wintertodtHighscore);
        }
        player.wintertodtKills.increment(player);
        player.sendMessage("Your subdued Wintertodt count is: <col=ff0000>" + PlayerCounter.WINTERTODT_SUBDUED.get(player) + "</col>.");
        if (player.wintertodtPoints >= 500) {
            int crates = player.wintertodtPoints / 500; // guaranteed crates
            if (Random.rollDie(500, player.wintertodtPoints % 500)) {
                crates++;
            }
            if (crates > 1) {
                player.sendMessage("You have gained " + crates + " supply crates!");
            } else {
                player.sendMessage("You have gained a supply crate!");
            }
            player.getInventory().addOrDrop(20703, crates);
        } else {
            player.sendMessage("You did not earn enough points to be worthy of a gift from the citizens of Kourend this time.");
        }

        /* reset the players points now that the game has ended. */
        player.wintertodtPoints = 0;
    }

    private static void pyromancerText() {
        if (!pyromancerTextDelay.isDelayed()) {
            pyromancerTextDelay.delay(8);
            for (Brazier b: BRAZIERS) {
                if (!b.isPyromancerAlive()) {
                    b.getPyromancer().forceText(Random.get(PYROMANCER_DEAD_TEXT));
                } else if (b.getObject().id == EMPTY_BRAZIER) {
                    b.getPyromancer().forceText("Light this brazier!");
                } else if (b.getObject().id == BROKEN_BRAZIER) {
                    b.getPyromancer().forceText("Fix this brazier!");
                } else if (Random.rollDie(4, 1)) {
                    b.getPyromancer().forceText("Yemalo shi cardito!");
                }

                if (b.isPyromancerAlive() && b.getObject().id == BURNING_BRAZIER && Random.rollDie(3, 1)) {
                    b.getPyromancer().animate(4432);
                }
            }
        }
    }

    private static void attackPyromancers() {
        List<NPC> pyros = Arrays.stream(BRAZIERS).filter(Brazier::isPyromancerAlive).map(Brazier::getPyromancer).collect(Collectors.toList());
        if (!pyros.isEmpty()) {
            if (Random.rollDie(pyros.size() * 30, pyros.size())) {
                NPC pyro = Random.get(pyros);
                GameObject obj = GameObject.spawn(SNOW_EFFECT, pyro.getAbsX(), pyro.getAbsY(), pyro.getHeight(), 10, 0);
                World.startEvent(event -> {
                    event.delay(4);
                    obj.graphics(502, 90, 0);
                    pyro.hit(new Hit().randDamage(6, 10));
                    obj.remove();
                });
            }
        }
    }

    private static void extinguishBraziers() {
        for (Brazier b : BRAZIERS) {
            if (b.getObject().id == BURNING_BRAZIER && Random.rollDie((health + 1500) / 10, 10)) {
                if (Tile.getObject(SNOW_EFFECT, b.getObject().x, b.getObject().y, b.getObject().z, 10, 0) != null) // already breaking
                    continue;
                if (Random.rollDie(health < (MAX_HP / 2) ? 2 : 3, 1)) {
                    breakBrazier(b);
                } else {
                    b.getObject().graphics(502, 115, 0);
                    b.getObject().setId(EMPTY_BRAZIER);
                }
            }
        }
    }

    private static void breakBrazier(Brazier b) {
        World.startEvent(event -> {
            GameObject obj1 = GameObject.spawn(SNOW_EFFECT, b.getObject().x + 1, b.getObject().y, 0, 10, 0),
                    obj2 = GameObject.spawn(SNOW_EFFECT, b.getObject().x, b.getObject().y + 1, 0, 10, 0),
                    obj3 = GameObject.spawn(SNOW_EFFECT, b.getObject().x + 1, b.getObject().y + 1, 0, 10, 0),
                    obj4 = GameObject.spawn(SNOW_EFFECT, b.getObject().x + 2, b.getObject().y + 1, 0, 10, 0),
                    obj5 = GameObject.spawn(SNOW_EFFECT, b.getObject().x + 1, b.getObject().y + 2, 0, 10, 0);
            event.delay(4);
            obj1.remove();
            obj2.remove();
            obj3.remove();
            obj4.remove();
            obj5.remove();
            if (isActive()) {
                b.getObject().setId(BROKEN_BRAZIER);
                b.getObject().graphics(502, 90, 0);
                REGION.players.forEach(p -> {
                    if (Misc.getDistance(b.getObject().x + 1, b.getObject().y + 1, p.getAbsX(), p.getAbsY()) <= 2) {
                        p.sendMessage("The brazier is broken and shrapnel damages you.");
                        p.hit(new Hit().randDamage(getBrazierAttackDamage(p)));
                    }
                });
            }
        });
    }

    private static void doMagicAttack() {
        if (!Random.rollDie(25, 1))
            return;
        Player player = Random.get(REGION.players);
        if (player.getAbsY() <= 3987)
            return;
        int baseX = player.getAbsX();
        int baseY = player.getAbsY();
        World.startEvent(event -> {
            List<GameObject> suckMyDickHitler = new ArrayList<>();
            suckMyDickHitler.add(GameObject.spawn(SNOW_EFFECT, baseX, baseY, 0, 10, 0));
            if (Tile.allowObjectPlacement(baseX + 1, baseY + 1, 0))
                suckMyDickHitler.add(GameObject.spawn(SNOW_EFFECT, baseX + 1, baseY+ 1, 0, 10, 0));
            if (Tile.allowObjectPlacement(baseX + 1, baseY - 1, 0))
                suckMyDickHitler.add(GameObject.spawn(SNOW_EFFECT, baseX + 1, baseY -1, 0, 10, 0));
            if (Tile.allowObjectPlacement(baseX - 1, baseY + 1, 0))
                suckMyDickHitler.add(GameObject.spawn(SNOW_EFFECT, baseX - 1, baseY + 1, 0, 10, 0));
            if (Tile.allowObjectPlacement(baseX - 1, baseY - 1, 0))
                suckMyDickHitler.add(GameObject.spawn(SNOW_EFFECT, baseX - 1, baseY - 1, 0, 10, 0));

            event.delay(4);

            suckMyDickHitler.forEach(o -> o.setId(o.x == baseX && o.y == baseY ? 29325 : 29324));
            REGION.players.forEach(p -> {
                if (Misc.getDistance(p.getPosition(), baseX, baseY) <= 1) {
                    p.sendMessage("The freezing cold attack of the Wintertodt's magic hits you.");
                    p.hit(new Hit().randDamage(getAreaAttackDamage(p)));
                }
            });
            event.delay(30);
            suckMyDickHitler.forEach(GameObject::remove);

        });
    }

    private static void start() {
        WINTERTODT.setId(ACTIVE_STORM);
        for (Brazier b : BRAZIERS) {
            b.getObject().setId(EMPTY_BRAZIER);
            if (!b.isPyromancerAlive())
                b.getPyromancer().transform(PYROMANCER);
        }
        health = MAX_HP;
        started = true;
        sendAll();
    }


    public static void send(Player player) {
        if (!isActive()) {
            player.getPacketSender().sendClientScript(1426, "i", startDelay.remaining());
        }
        player.getPacketSender().sendClientScript(1421, "iiiiiiiiii", player.wintertodtPoints, health, BRAZIERS[0].getPyromancerState(),
                BRAZIERS[1].getPyromancerState(), BRAZIERS[2].getPyromancerState(),
                BRAZIERS[3].getPyromancerState(),
                BRAZIERS[0].getBrazierState(), BRAZIERS[1].getBrazierState(),
                BRAZIERS[2].getBrazierState(), BRAZIERS[3].getBrazierState());
    }

    public static void sendAll() {
        REGION.players.forEach(Wintertodt::send);
    }

    public static void shootFlame(Brazier source) {
        NPC flame = new NPC(FLAME).spawn(source.getObject().x + source.getFlameOffsetX(), source.getObject().y + source.getFlameOffsetY(), source.getObject().z);
        flame.startEvent(event -> {
            flame.lock();
            while (!flame.getPosition().equals(1630, 4007)) {
                int stepX = (1630 - flame.getAbsX()) / Math.abs(1630 - flame.getAbsX());
                int stepY = (4007 - flame.getAbsY()) / Math.abs(4007 - flame.getAbsY());
                flame.step(stepX, stepY, StepType.FORCE_WALK);
                event.delay(1);
            }
            flame.unlock();
            flame.remove();
        });
    }

    public static void removeGameItems(Player player) {
        for (Item item : player.getInventory().getItems()) {
            if (item != null && GAME_ITEMS.contains(item.getId())) {
                item.remove();
            }
        }
    }


    public static void addPoints(Player player, int amount) {
        int old = player.wintertodtPoints;
        player.wintertodtPoints += amount;
        if (old < 500 && player.wintertodtPoints >= 500) {
            player.sendMessage("You have helped enough to earn a supply crate. Further work will go towards better rewards.");
        }
        PlayerCounter.LIFETIME_WINTERTODT_POINTS.increment(player, amount);
        send(player);
    }

    public static int getWarmItemsWorn(Player player) {
        return 4; //todo
    }

    public static int getBraziersLit() {
        int count = 0;
        for (Brazier b : BRAZIERS)
            if (b.getObject().id == BURNING_BRAZIER)
                count++;
        return count;
    }

    public static int getColdDamage(Player player) {
        return (int) ((16.0 - getWarmItemsWorn(player) - (2 * Math.min(3, getBraziersLit()))) * (player.getStats().get(StatType.Hitpoints).fixedLevel + 1) / player.getStats().get(StatType.Firemaking).fixedLevel);
    }

    public static int getBrazierAttackDamage(Player player) {
        return (int) ((10.0 - getWarmItemsWorn(player)) * (player.getStats().get(StatType.Hitpoints).fixedLevel + 1) / player.getStats().get(StatType.Firemaking).fixedLevel) * 2;
    }

    public static int getAreaAttackDamage(Player player) {
        return (int) ((10.0 - getWarmItemsWorn(player)) * (player.getStats().get(StatType.Hitpoints).fixedLevel + 1) / player.getStats().get(StatType.Firemaking).fixedLevel) * 3;
    }

    public static void entered(Player player) {
        player.openInterface(InterfaceType.PRIMARY_OVERLAY, 396);
        Wintertodt.send(player);
    }

    public static void exited(Player player, boolean logout) {
        if (logout) {
            player.getMovement().teleport(1630, 3963);
        } else {
            player.closeInterface(InterfaceType.PRIMARY_OVERLAY);
        }
        Wintertodt.removeGameItems(player);
    }

    static {
        MapListener.registerRegion(REGION_ID)
                .onEnter(Wintertodt::entered)
                .onExit(Wintertodt::exited);
    }

}
