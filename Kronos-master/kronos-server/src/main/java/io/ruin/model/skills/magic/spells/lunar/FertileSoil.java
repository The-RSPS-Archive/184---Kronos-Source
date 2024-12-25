package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.World;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.impl.CompostBin;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

public class FertileSoil extends Spell {

    public FertileSoil() {
        Item[] runes = {
                Rune.NATURE.toItem(2),
                Rune.ASTRAL.toItem(3),
                Rune.EARTH.toItem(15)
        };
        registerObject(83, runes, (p, obj) -> {
            Patch patch = p.getFarming().getPatch(obj);
            if (patch == null) {
                p.sendMessage("This spell can only be cast on farming patches.");
                return false;
            }
            if (patch instanceof CompostBin) {
                p.sendMessage("No, that would be silly.");
                return false;
            }
            if (patch.getCompost() != 0) {
                p.sendMessage("That patch has already been treated.");
                return false;
            }
            if (patch.getPlantedCrop() != null && patch.getStage() >= patch.getPlantedCrop().getTotalStages()) {
                p.sendMessage("Composting it isn't going to make it any bigger.");
                return false;
            }
            p.animate(4413);
            World.sendGraphics(724, 0, 0, obj.x, obj.y, obj.z);
            patch.setCompost(2); // super
            if (patch.getPlantedCrop() != null && patch.getStage() < patch.getPlantedCrop().getTotalStages())
                patch.setProduceCount(patch.calculateProduceAmount());
            return true;
        });
    }
}
