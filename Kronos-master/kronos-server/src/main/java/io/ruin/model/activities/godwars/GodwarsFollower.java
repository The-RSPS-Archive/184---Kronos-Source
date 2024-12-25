package io.ruin.model.activities.godwars;


import io.ruin.cache.ItemDef;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;

import java.util.Arrays;
import java.util.function.Predicate;

public class GodwarsFollower {

    private static final int[] ARMADYL_FOLLOWER = {3165, 3163, 3164, 3162, 3166, 3167, 3168, 3169, 3170, 3171, 3172, 3173, 3174,
            3175, 3176, 3177, 3178, 3179, 3180, 3181, 3182, 3183};
    private static final int[] SARADOMIN_FOLLOWER = {2206, 2208, 2207, 2205, 2213, 2214, 2210, 2212, 2209, 2211};
    private static final int[] ZAMORAK_FOLLOWER = {3130, 3131, 3132, 3129, 3159, 3133, 3141, 3138, 484, 485, 486, 487, 3134, 5007,
            3135, 3136, 3137, 3140, 3139, 3161, 3160};
    private static final int[] BANDOS_FOLLOWER = {2215, 2216, 2217, 2218, 2244, 2243, 2235, 2236, 2237, 2238, 2239, 2240, 2234, 2246,
            2245, 2249, 2248, 2247, 2241, 2241, 2242};

    private static Bounds GODWARS = new Bounds(2816, 5249, 2943, 5375, -1);

    private static final Predicate<ItemDef> ARMADYL_ITEM = def -> def.name.toLowerCase().contains("armadyl") || def.name.toLowerCase().equals("book of law") || def.name.toLowerCase().equals("honourable blessing");
    private static final Predicate<ItemDef> SARADOMIN_ITEM = def -> def.name.toLowerCase().contains("saradomin") || def.name.toLowerCase().contains("holy") ||def.name.toLowerCase().contains("monk's ") && !def.name.toLowerCase().contains("unholy");
    private static final Predicate<ItemDef> ZAMORAK_ITEM = def -> def.name.toLowerCase().contains("zamorak") || def.name.toLowerCase().contains("unholy") || def.name.toLowerCase().contains("staff of the dead");
    private static final Predicate<ItemDef> BANDOS_ITEM = def -> def.name.toLowerCase().contains("bandos")  || def.name.toLowerCase().equals("book of war") || def.name.toLowerCase().equals("war blessing");

    static {

        ItemDef.cached.values().forEach(def -> {
            if (ARMADYL_ITEM.test(def)) def.godItem[0] = true;
            if (SARADOMIN_ITEM.test(def)) def.godItem[1] = true;
            if (ZAMORAK_ITEM.test(def)) def.godItem[2] = true;
            if (BANDOS_ITEM.test(def)) def.godItem[3] = true;
        });


        /**
         * Armadyl
         */
        SpawnListener.register(ARMADYL_FOLLOWER, npc -> {
            if (npc.getPosition().inBounds(GODWARS)) {
                npc.deathEndListener = (DeathListener.SimplePlayer) killer -> Config.GWD_ARMADYL_KC.set(killer.player, Config.GWD_ARMADYL_KC.get(killer.player) + 1);
                if (npc.aggressionImmunity == null)
                    npc.aggressionImmunity = isProtected(0);
            }
        });

        /**
         * Saradomin
         */
        SpawnListener.register(SARADOMIN_FOLLOWER, npc -> {
            if (npc.getPosition().inBounds(GODWARS)) {
                npc.deathEndListener = (DeathListener.SimplePlayer) killer -> Config.GWD_SARADOMIN_KC.set(killer.player, Config.GWD_SARADOMIN_KC.get(killer.player) + 1);
                if (npc.aggressionImmunity == null)
                    npc.aggressionImmunity = isProtected(1);
            }
        });

        /**
         * Zamorak
         */
        SpawnListener.register(ZAMORAK_FOLLOWER, npc -> {
            if (npc.getPosition().inBounds(GODWARS)) {
                npc.deathEndListener = (DeathListener.SimplePlayer) killer -> Config.GWD_ZAMORAK_KC.set(killer.player, Config.GWD_ZAMORAK_KC.get(killer.player) + 1);
                if (npc.aggressionImmunity == null)
                    npc.aggressionImmunity = isProtected(2);
            }
        });

        /**
         * Bandos
         */
        SpawnListener.register(BANDOS_FOLLOWER, npc -> {
            if (npc.getPosition().inBounds(GODWARS)) {
                npc.deathEndListener = (DeathListener.SimplePlayer) killer -> Config.GWD_BANDOS_KC.set(killer.player, Config.GWD_BANDOS_KC.get(killer.player) + 1);
                if (npc.aggressionImmunity == null)
                    npc.aggressionImmunity = isProtected(3);
            }
        });
    }

    private static Predicate<Entity> isProtected(int godIndex) {
        return entity -> {
            if (entity.player == null)
                return false;
            for (Item item : entity.player.getEquipment().getItems()) {
                if (item != null && item.getDef().godItem[godIndex])
                    return true;
            }
            return false;
        };
    }
}
