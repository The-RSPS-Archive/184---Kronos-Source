package io.ruin.model.activities.bosses;

import io.ruin.api.utils.Random;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.utility.TickDelay;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Npc id = 15021
 *
 * Gfx -
 * Bomb - 5025
 * Tornado - 5026
 * Secondary gfx for bombs - 5027
 * Vial - 5028
 * slash - 5029
 *
 * Weapon special attack -
 * gfx = 5032
 *
 * Animations -
 * Defence - 2605
 * Death - 2607
 * Enraged attack - 2609
 * Normal melee - 2610
 * Slash attack - 2611
 * right slash attack - 2612
 * Vial throw - 2613
 *
 * @author ReverendDread on 7/21/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class KaalKetJor extends NPCCombat {

    private static final int VIAL_THROW = 2613;
    private static final int BASIC_MELEE = 2610;
    private static final int RIGHT_SLASH = 2612, LEFT_SLASH = 2611;
    private static final Projectile VIAL_PROJ = new Projectile(5028, 350, 0, 30, 56, 10, 32, -32).tileOffset(1);
    private static final Projectile SECONDARY_VIAL_PROJ = new Projectile(5033, 350, 0, 30, 56, 10, 32, 32).tileOffset(1);
    private static final TickDelay TORNADO_DELAY = new TickDelay();

    @Override
    public void init() {
        TORNADO_DELAY.delay(50);
        npc.setIgnoreMulti(true);
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        int random = Random.get(1, 10);
        switch (random) {
            case 1:
            case 2:
                return vialAttack();
            case 3:
            case 4:
            case 5:
            case 6:
                return bigCleave();
            case 7:
            case 8:
            case 9:
            case 10:
                return smallCleave();
        }
        return true;
    }

    /**
     * Vial throwing attack
     * @return
     */
    private boolean vialAttack() {
        npc.animate(VIAL_THROW);
        List<Player> targets = npc.localPlayers().stream().filter(t -> ProjectileRoute.allow(npc, t)).collect(Collectors.toList());
        for (Player player : targets) {
            final Position explosionPos = player.getPosition().copy();
            int clientDelay = VIAL_PROJ.send(npc, explosionPos);
            SECONDARY_VIAL_PROJ.send(npc, explosionPos);
            int tickDelay = ((clientDelay * 25) / 600) - 1;
            World.sendGraphics(5025, 0, clientDelay, explosionPos);
            World.startEvent(e -> {
                try {
                    e.delay(tickDelay);
                    if (player.getPosition().equals(explosionPos)) {
                        List<Position> tiles = explosionPos.area(1);
                        for (int i = 0; i < 3; i++) {
                            World.sendGraphics(5027, 0, 0, Random.get(tiles));
                        }
                        player.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(15));
                        player.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(15));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        return !targets.isEmpty();
    }

    /**
     * Cleave attack
     * @return
     */
    private boolean bigCleave() {
        npc.animate(LEFT_SLASH);
        npc.graphics(5029, 350, 0);
        List<Player> targets = npc.localPlayers().stream().filter(t ->
                ProjectileRoute.allow(npc, t) && t.getPosition().isWithinDistance(target.getPosition(), 3)
        ).collect(Collectors.toList());
        for (Player player : targets) {
            player.hit(new Hit(npc, AttackStyle.SLASH).randDamage(info.max_damage));
        }
        return !targets.isEmpty();
    }

    /**
     * Tornado attack
     * @return
     */
    private boolean tornados() {
        List<Player> targets = npc.localPlayers().stream().filter(t -> ProjectileRoute.allow(npc, t)).collect(Collectors.toList());
        for (Player player : targets) {
            NPC tornado = new NPC(15024).spawn(npc.getPosition().copy().center(npc.getSize()));
            tornado.setIgnoreMulti(true);
            tornado.getCombat().setAllowRespawn(false);
            tornado.getCombat().setTarget(player);
            World.startEvent(e -> {
                int hits = 0;
                int ticks = 0;
                while (ticks++ < 25 && hits < 4) {
                    if (tornado.getPosition().isWithinDistance(player.getPosition(), 1)) {
                        player.hit(new Hit(tornado, AttackStyle.MAGIC).randDamage(20));
                        hits++;
                    }
                    e.delay(1);
                }
                tornado.remove();
            });
        }
        TORNADO_DELAY.delay(100);
        return !targets.isEmpty();
    }

    /**
     * Basic melee attack
     * @return
     */
    private boolean smallCleave() {
        basicAttack(BASIC_MELEE, AttackStyle.CRUSH, info.max_damage);
        if (!TORNADO_DELAY.isDelayed()) {
            tornados();
        }
        return true;
    }

    static {
        NPCDef.get(15021).ignoreOccupiedTiles = true;
        NPCDef.get(15024).ignoreOccupiedTiles = true;
    }

}
