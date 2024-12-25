package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

/**
 * @author ReverendDread on 7/21/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class KaruulmLongsword implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("karuulm longsword");
    }

    @Override
    public boolean handle(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(6147);
        victim.graphics(5032);
        victim.hit(new Hit(player, AttackStyle.MAGICAL_MELEE, attackType).randDamage((int) (maxDamage * .75)).boostAttack(.10).boostDamage(.15));
        victim.hit(new Hit(player, AttackStyle.MAGICAL_MELEE, attackType).randDamage((int) (maxDamage * .5)).boostAttack(.10).boostDamage(.15));
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 75;
    }
}
