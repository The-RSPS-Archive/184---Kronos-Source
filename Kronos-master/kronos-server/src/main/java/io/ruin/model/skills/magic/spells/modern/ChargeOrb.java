package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.stat.StatType;

public class ChargeOrb extends Spell {

    private static final int UNPOWERED_ORB = 567;

    public enum ChargeSpell {

        CHARGE_WATER(2151, 56, 66.0, 571, 149, Rune.COSMIC.toItem(3), Rune.WATER.toItem(30)),
        CHARGE_EARTH(2150, 60, 70.0, 575, 151, Rune.COSMIC.toItem(3), Rune.EARTH.toItem(30)),
        CHARGE_FIRE(2153, 63, 73.0, 569, 152, Rune.COSMIC.toItem(3), Rune.FIRE.toItem(30)),
        CHARGE_AIR(2152, 66, 76.0, 573, 150, Rune.COSMIC.toItem(3), Rune.AIR.toItem(30));

        public final int objectId, levelReq, orbId, gfx;
        public final double exp;
        public Item[] runes;

        ChargeSpell(int objectId, int levelReq, double exp, int orbId, int gfx, Item... runes) {
            this.objectId = objectId;
            this.levelReq = levelReq;
            this.exp = exp;
            this.orbId = orbId;
            this.gfx = gfx;
            this.runes = runes;
        }
    }

    private static void make(Player player, Item orb, ChargeSpell chargeSpell) {
        player.getStats().addXp(StatType.Magic, chargeSpell.exp, true);
        player.animate(726);
        player.graphics(chargeSpell.gfx, 100, 0);
        orb.setId(chargeSpell.orbId);
    }

    public ChargeOrb(ChargeSpell chargeSpell) {
        SkillItem item = new SkillItem(chargeSpell.orbId).addAction((player, amount, event) -> {
            while (amount-- > 0) {
                RuneRemoval r = null;
                if (chargeSpell.runes != null && (r = RuneRemoval.get(player, chargeSpell.runes)) == null) {
                    player.sendMessage("You don't have enough runes to cast this spell.");
                    return;
                }
                Item orb = player.getInventory().findItem(UNPOWERED_ORB);
                if (orb == null)
                    return;
                if (player.getInventory().hasMultiple(orb.getId())) {
                    if (r != null)
                        r.remove();
                    make(player, orb, chargeSpell);
                    event.delay(5);
                    continue;
                }
                make(player, orb, chargeSpell);
                break;
            }
        });
        registerObject(chargeSpell.levelReq, chargeSpell.runes, (player, object) -> {
            if (object.id != chargeSpell.objectId) {
                player.sendMessage("Nothing interesting happens.");
                return false;
            }
            Item orb = player.getInventory().findItem(UNPOWERED_ORB);
            if (orb == null) {
                player.sendMessage("You need an unpowered orb to do this.");
                return false;
            }
            if (player.getInventory().hasMultiple(UNPOWERED_ORB)) {
                SkillDialogue.make(player, item);
                return true;
            }
            make(player, orb, chargeSpell);
            return true;
        });

    }

}