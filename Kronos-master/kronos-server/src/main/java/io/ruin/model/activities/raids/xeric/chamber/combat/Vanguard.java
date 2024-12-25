package io.ruin.model.activities.raids.xeric.chamber.combat;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;

import java.util.LinkedList;
import java.util.List;

public class Vanguard extends NPCCombat {

    private static final Projectile RANGED_PROJECTILE = new Projectile(1332, 5, 0, 20, 40, 0, 16, 64);
    private static final Projectile MAGIC_PROJECTILE = new Projectile(1331, 5, 0, 20, 70, 0, 60, 64);

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(npc.getId() == 7527 ? 1 : 8);
    }

    @Override
    public boolean attack() {
        switch (npc.getId()) {
            case 7527:
                return meleeAttack();
            case 7528:
                return rangedAttack();
            case 7529:
                return magicAttack();
            default:
                return false;
        }
    }

    private boolean magicAttack() {
        if (!withinDistance(8))
            return false;
        npc.animate(getInfo().attack_animation);
        List<Position> targets = new LinkedList<>();
        targets.add(target.getPosition().copy());
        for (int i = 0; i < 2; i++) {
            Direction dir = Random.get(Direction.values());
            targets.add(target.getPosition().copy().translate(dir.deltaX * Random.get(5), dir.deltaY * Random.get(5), 0));
        }
        for (Position pos : targets) {
            int delay = MAGIC_PROJECTILE.send(npc, pos);
            World.sendGraphics(659, 0, delay, pos);
            npc.addEvent(event -> {
                event.delay(2);
                npc.localPlayers().stream().filter(p -> p.getPosition().equals(pos)).forEach(p -> {
                    int dmg = p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC) ? getInfo().max_damage : getInfo().max_damage / 2;
                    p.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(dmg).ignorePrayer());
                });
            });
        }
        return true;
    }

    private boolean rangedAttack() {
        if (!withinDistance(8))
            return false;
        npc.animate(getInfo().attack_animation);
        int dmg = target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? getInfo().max_damage : getInfo().max_damage / 2;
        int delay = RANGED_PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.RANGED).randDamage(dmg).clientDelay(delay).ignorePrayer());
        target.graphics(305, 40, delay);
        for (int i = 0; i < 2; i++) {
            Direction dir = Random.get(Direction.values());
            Position position = target.getPosition().copy().translate(dir.deltaX * Random.get(4), dir.deltaY * Random.get(4), 0);
            int delay2 = RANGED_PROJECTILE.send(npc, position);
            npc.addEvent(event -> {
                World.sendGraphics(305, 40, delay2, position);
                event.delay(1);
                npc.localPlayers().stream().filter(p -> p.getPosition().equals(position)).forEach(p -> {
                    int damage = p.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES) ? getInfo().max_damage : getInfo().max_damage / 2;
                    p.hit(new Hit(npc, AttackStyle.RANGED).randDamage(damage).clientDelay(delay).ignorePrayer());
                });
            });
        }
        return true;
    }

    private boolean meleeAttack() {
        if (!withinDistance(1))
            return false;
        npc.animate(getInfo().attack_animation);
        int dmg = target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE) ? getInfo().max_damage : getInfo().max_damage / 2;
        for (int i = 0; i < 3; i++)
            target.hit(new Hit(npc, AttackStyle.CRUSH).randDamage(dmg).ignorePrayer());
        return true;
    }

    @Override
    public int getRandomDropCount() {
        return 1 + Math.min(2, (ChambersOfXeric.getPartySize(npc) / 5));
    }
}
