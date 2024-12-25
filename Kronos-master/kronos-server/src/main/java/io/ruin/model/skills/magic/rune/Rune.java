package io.ruin.model.skills.magic.rune;

import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;

/**
 * SPECIFIC ORDER IS USED FOR RUNE POUCH
 */
public enum Rune {
    AIR,
    WATER,
    EARTH,
    FIRE,
    MIND,
    CHAOS,
    DEATH,
    BLOOD,
    COSMIC,
    NATURE,
    LAW,
    BODY,
    SOUL,
    ASTRAL,
    MIST(WATER, AIR),
    MUD(WATER, EARTH),
    DUST(EARTH, AIR),
    LAVA(EARTH, FIRE),
    STEAM(FIRE, WATER),
    SMOKE(FIRE, AIR),
    WRATH;

    private int id = -1;

    private final Rune[] combinations;

    public static final Rune[] VALUES = values();

    Rune(Rune... comboRunes) {
        this.combinations = comboRunes.length == 0 ? null : comboRunes;
    }

    public boolean accept(Rune rune) {
        if(rune == null)
            return false;
        if(rune == this)
            return true;
        if(rune.combinations != null) {
            for(Rune comboRune : rune.combinations) {
                if(comboRune == this)
                    return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public Item toItem(int amount) {
        return new Item(id, amount);
    }

    static {
        ItemDef.forEach(def -> {
            String name = def.name.toLowerCase();
            for(Rune rune : values()) {
                if(name.replace(" (nz)", "").equalsIgnoreCase(rune.name() + " rune")) {
                    if(rune.id == -1)
                        rune.id = def.id;
                    def.rune = rune;
                    break;
                }
                if(name.contains("staff") && name.contains(rune.name().toLowerCase())) {
                    def.staffRune = rune;
                    break;
                }
            }
        });
    }

}
