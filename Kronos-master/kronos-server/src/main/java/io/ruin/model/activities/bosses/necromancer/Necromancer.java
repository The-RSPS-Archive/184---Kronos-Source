package io.ruin.model.activities.bosses.necromancer;

import com.google.common.collect.Lists;
import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.*;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.stat.StatType;
import io.ruin.utility.TickDelay;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author ReverendDread on 6/15/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class Necromancer extends NPCCombat {

    private static final Projectile CORRUPTED_SURGE_PROJ = new Projectile(5018, 43, 31, 51, 56, 10, 16, 64);
    private static final Graphic CORRUPTED_SURGE_GFX = Graphic.builder().id(5017).height(127).build();
    private static final int CORRUPTED_SURGE_ANIM = 7855;
    private static final Graphic CORRUTPED_SURGE_HIT_GFX = Graphic.builder().id(5014).height(124).soundId(163).build();
    private static final Projectile CORRUPTION_PROJ = new Projectile(5010, 36, 31, 61, 56, 10, 16, 64);
    private static final Graphic CORRUPTION_HIT_GFX = Graphic.builder().id(5012).soundId(121).soundType(0).build();
    private static final int CORRUPTION_ANIM = 1163;
    private static final Graphic SPLASH_GFX = Graphic.builder().id(85).height(124).soundId(227).build();
    private static final Projectile METEOR = new Projectile(5016, 500, 0, 61, 56, 25, 16, 64);
    private static final Graphic METEOR_HIT_GFX = Graphic.builder().id(5007).build();
    private static final TickDelay MINION_DELAY = new TickDelay();
    private static final Projectile MINION_PROJ = new Projectile(5013, 43, 0, 51, 56, 10, 16, 64);
    private static final Projectile ICE_BLITZ = new Projectile(56, 10);
    private static final Graphic ICE_BLITZ_HIT_GFX = Graphic.builder().id(367).soundId(169).build();
    private static final int ICE_BLITZ_ANIM = 1978;
    private static final int MINION_ID = 15004;
    private static final Projectile DARK_FLAMES_PROJ = new Projectile(5015, 43, 31, 51, 56, 10, 16, 64);
    private static final Graphic DARK_FLAMES_HIT_GFX = Graphic.builder().id(5011).build();
    private static final Position SPAWN_POSITION = Position.of(2270, 5534, 0);

    private int phase = 0;

    @Override
    public void init() {
        npc.setIgnoreMulti(true);
        npc.hitListener = new HitListener().postDamage((hit -> {
            double ratio = (double) npc.getHp() / npc.getMaxHp();
            if (ratio == 0) {
                return;
            }
            if ((phase == 0 && ratio < 0.75) || (phase == 1 && ratio < 0.50) || (phase == 2 && ratio < 0.25)) {
                List<Position> positions = getRandomPositions(npc.getPosition(),4, 32, null);
                World.startEvent(e -> {
                    npc.animate(8316);
                    npc.publicSound(2907, 1, 0);
                    for (Position position : positions) {
                        int clientDelay = METEOR.send(position.relative(Random.get(-3, 3), Random.get(-3, 3)), position);
                        World.sendGraphics(METEOR_HIT_GFX.getId(), METEOR_HIT_GFX.getHeight(), clientDelay, position);
                        if (target != null && target.getPosition().isWithinDistance(position, 1)) {
                            target.hit(new Hit().fixedDamage((int) (target.getMaxHp() * 0.99)));
                        }
                        e.delay(2);
                    }
                });
                phase++;
            }
        }));
        MINION_DELAY.delay(Random.get(30, 60));
    }

    @Override
    public void follow() {
        follow(12);
    }

    @Override
    public boolean attack() {
        int random = Random.get(1, 8);
        switch (random) {
            case 1:
            case 2:
            case 3:
            case 4: {
                    boolean protecting_magic = target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC);
                    int max_hit = info.max_damage;
                    if (protecting_magic) {
                        max_hit = (max_hit / 4);
                    }
                    npc.animate(CORRUPTED_SURGE_ANIM);
                    npc.graphics(CORRUPTED_SURGE_GFX);
                    npc.publicSound(162, 1, 0);
                    int clientDelay = CORRUPTED_SURGE_PROJ.send(npc, target);
                    Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(max_hit).clientDelay(clientDelay).ignorePrayer();
                    hit.postDamage(e -> {
                        if (!hit.isBlocked()) {
                            target.graphics(CORRUTPED_SURGE_HIT_GFX.getId(), CORRUTPED_SURGE_HIT_GFX.getHeight(), CORRUTPED_SURGE_HIT_GFX.getDelay());
                            target.publicSound(CORRUTPED_SURGE_HIT_GFX.getSoundId());
                        } else {
                            target.graphics(SPLASH_GFX.getId(), SPLASH_GFX.getHeight(), SPLASH_GFX.getDelay());
                            target.publicSound(SPLASH_GFX.getSoundId());
                        }
                    });
                    target.hit(hit);
                    if (!MINION_DELAY.isDelayed()) {
                        List<Position> positions = getRandomPositions(target.getPosition(),8, 1, (position ->
                                !position.isWithinDistance(target.getPosition(), 6) && position.getTile().clipping == 0));
                        npc.animate(ICE_BLITZ_ANIM);
                        npc.graphics(366, 124, 0);
                        npc.publicSound(171, 1, 0);
                        Hit iceBlitz = projectileAttack(ICE_BLITZ, ICE_BLITZ_ANIM, ICE_BLITZ_HIT_GFX, SPLASH_GFX, AttackStyle.MAGIC, info.max_damage, false);
                        iceBlitz.postDamage(e -> {
                            e.freeze(30, npc);
                            e.graphics(369);
                        });
                        clientDelay = MINION_PROJ.send(npc, positions.get(0));
                        int tickDelay = ((clientDelay * 25) / 600);
                        World.sendGraphics(METEOR_HIT_GFX.getId(), METEOR_HIT_GFX.getHeight(), clientDelay, positions.get(0));
                        NPC minion = new NPC(MINION_ID);
                        World.startEvent(e -> {
                            try {
                                final Entity entity = target;
                                e.delay(tickDelay);
                                minion.spawn(positions.get(0));
                                minion.getCombat().setAllowRespawn(false);
                                minion.setIgnoreMulti(true);
                                minion.getCombat().setTarget(target);
                                minion.face(entity);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                        MINION_DELAY.delay(Random.get(30, 60));
                    }
                }
                break;
            case 5:
            case 6: {
                    npc.publicSound(119, 1, 0);
                    Hit hit = projectileAttack(CORRUPTION_PROJ, CORRUPTION_ANIM, CORRUPTION_HIT_GFX, SPLASH_GFX, AttackStyle.MAGIC, info.max_damage, false);
                    hit.postDamage(e -> {
                        if (!hit.isBlocked()) {
                            int points = target.player.getStats().get(StatType.Prayer).currentLevel;
                            target.player.getPrayer().drain((int) (points * 0.05));
                            target.player.sendMessage("Your prayer has been drained slightly.");
                        }
                    });
                }
                break;
            case 7:
            case 8: {
                    Hit hit = projectileAttack(DARK_FLAMES_PROJ, CORRUPTED_SURGE_ANIM, DARK_FLAMES_HIT_GFX, SPLASH_GFX, AttackStyle.MAGIC, info.max_damage, false);
                    hit.postDamage(e -> {
                        if (!hit.isBlocked()) {
                            target.player.getStats().get(StatType.Defence).drain(0.75);
                            target.player.sendMessage("Your defence has been drained slightly.");
                        }
                    });
                }
                break;
        }
        return true;
    }

    private List<Position> getRandomPositions(Position source, int size, int count, Predicate<Position> predicate) {
        List<Position> positions = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            int minX = (source.getX() - size), maxX = (source.getX() + size);
            int minY = (source.getY() - size), maxY = (source.getY() + size);
            int x = (int) (minX + Math.random() * (maxX - minX));
            int y = (int) (minY + Math.random() * (maxY - minY));
            Position position = Position.of(x, y, source.getZ());
            if (predicate != null && !predicate.test(position))
                return getRandomPositions(source, size, count, predicate);
            positions.add(position);
        }
        return positions;
    }

    private static void createAndEnter(Player player) {
        DynamicMap map = new DynamicMap().build(9046, 1);
        NPC necromancer = new NPC(15003).spawn(map.convertX(SPAWN_POSITION.getX()), map.convertY(SPAWN_POSITION.getY()), 0, Direction.SOUTH, 5);
        map.addNpc(necromancer);
        player.startEvent(event -> {
            player.lock(LockType.FULL_NULLIFY_DAMAGE);
            player.getMovement().teleport(map.convertPosition(player.getPosition()));
            event.delay(1);
            player.animate(819);
            player.getMovement().force(0, 2, 0, 0, 0, 60, Direction.NORTH);
            event.delay(2);
            map.assignListener(player).onExit((p, logout) -> {
                if (logout)
                    p.getMovement().teleport(2271, 5519, 0);
                necromancer.remove();
                p.deathEndListener = null;
                map.destroy();
            });
            player.unlock();
        });
        ObjectAction exitAction = (p, obj) -> {
            p.startEvent(event -> {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(819);
                p.getMovement().force(0, -1, 0, 0, 0, 60, Direction.SOUTH);
                event.delay(2);
                p.getMovement().teleport(map.revertPosition(p.getPosition()));
                p.unlock();
            });
        };
        ObjectAction.register(20839, map.convertX(2270), map.convertY(5520), 0, "pass", exitAction);
    }
    
    static {
        ObjectAction.register(20839, 2270, 5520, 0, 1, (player, obj) -> createAndEnter(player));
    }

}
