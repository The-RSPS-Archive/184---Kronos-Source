package io.ruin.model.skills.magic.spells.modern;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.journal.presets.Preset;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.jewellery.BraceletOfEthereum;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

import static io.ruin.cache.ItemID.COINS_995;

public class Alchemy extends Spell {

    private enum Level {
        LOW(712, 763, 21, 31, 3, Rune.NATURE.toItem(1), Rune.FIRE.toItem(3)),
        HIGH(713, 764, 55, 65, 5, Rune.NATURE.toItem(1), Rune.FIRE.toItem(5))
        ;
        private int anim, gfx, levelReq;
        private double xp;
        private Item[] runes;
        private int delay;

        Level(int anim, int gfx, int levelReq, double xp, int delay, Item... runes) {
            this.anim = anim;
            this.gfx = gfx;
            this.levelReq = levelReq;
            this.xp = xp;
            this.runes = runes;
            this.delay = delay;
        }
    }

    public Alchemy(boolean low) {
        Level level = low ? Level.LOW : Level.HIGH;
        registerItem(level.levelReq, level.xp, true, level.runes, (player, item) -> {
            if (player.alchDelay.isDelayed()) {
                // queue next alch
                player.startEvent(event -> {
                    event.delay(player.alchDelay.remaining());
                    itemAction.accept(player, item);
                });
                return false;
            }
            return alch(player, item, level);
        });
    }

    private static boolean alch(Player player, Item item, Level level) {
        if (item.getId() == 7462 || item.getId() == Preset.DRAGON_2H_SWORD || item.getId() == BraceletOfEthereum.REVENANT_ETHER) {
            player.sendMessage("You can't alchemise this item.");
            return false;
        }

        int value = level == Level.LOW ? item.getDef().lowAlchValue : item.getDef().highAlchValue;
        if(item.getDef().isNote())
            value = item.getDef().fromNote().highAlchValue;
        if (value == 0 || item.getId() == COINS_995) {
            player.sendMessage("You can't alchemise this item.");
            return false;
        }
        if (item.getAmount() == 1 && item.getDef().stackable && !player.getInventory().hasRoomFor(COINS_995)) {
            player.sendMessage("Not enough space in your inventory.");
            return false;
        }
        if ((item.getId() == Rune.NATURE.getId() && item.getAmount() < level.runes[0].getAmount() + 1)
                || (item.getId() == Rune.FIRE.getId() && item.getAmount() < level.runes[1].getAmount() + 1)) {
            player.sendMessage("You don't have the required runes to cast this spell.");
            return false;
        }
        if(item.getDef().free) {
            player.sendMessage("You can't alchemise this item.");
            return false;
        }
        if(!item.getDef().tradeable) {
            player.sendMessage("You can't alchemise this item.");
            return false;
        }

        if (item.getDef().stackable)
            item.remove(1);
        else
            item.remove();
        player.animate(level.anim);
        player.graphics(level.gfx, 46, 0);
        player.getInventory().add(COINS_995, value);
        player.getPacketSender().sendClientScript(915, "i", 6);
        player.alchDelay.delay(level.delay);
        return true;
    }

}
