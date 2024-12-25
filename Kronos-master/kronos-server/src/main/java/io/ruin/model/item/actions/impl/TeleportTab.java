package io.ruin.model.item.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.spells.HomeTeleport;

public enum TeleportTab {

    //Normal
    VARROCK(8007, 3212, 3423, 0),
    LUMBRIDGE(8008, 3222, 3218, 0),
    FALADOR(8009, 2965, 3380, 0),
    CAMELOT(8010, 2757, 3479, 0),
    ARDOUGNE(8011, 2662, 3307, 0),
    WATCHTOWER(8012, 2549, 3114, 2),

    //Ancient Magicks
    ANNAKARL(12775, 3288, 3886, 0),
    CARRALLANGAR(12776, 3161, 3667, 0),
    DAREEYAK(12777, 2966, 3696, 0),
    GHORROCK(12778, 2972, 3872, 0),
    KHARYRLL(12779, 3497, 3487, 0),
    LASSAR(12780, 3014, 3500, 0),
    PADDEWWA(12781, 3097, 9882, 0),
    SENNTISTEN(12782, 3349, 3346, 0),

    //Arceuus
    LUMBRIDGE_GRAVEYARD(19613, 3240, 3195, 0),
    DRAYNOR_MANOR(19615, 3109, 3350, 0),
    MIND_ALTAR(19617, 2981, 3510, 0),
    SALVE_GRAVEYARD(19619, 3431, 3460, 0),
    FENKENSTRAIN(19621, 3549, 3529, 0),
    WEST_ARDOUGNE(19623, 3502, 3292, 0),
    HARMONY_ISLAND(19625, World.HOME.getX(), World.HOME.getY(), World.HOME.getZ()),
    CEMENTERY(19627, 2979, 3763, 0),
    BARROWS(19629, 3564, 3312, 0),
    APE_ATOLL(19631, 3212, 3424, 0),

    //Redirected
    RIMMINGTON(11741, 2954, 3224, 0),
    TAVERLEY(11742, 2894, 3465, 0),
    POLLNIVNEACH(11743, 3340, 3004, 0),
    RELLEKA(11744, 2670, 3632, 0),
    BRIMHAVEN(11745, 2758, 3178, 0),
    YANILLE(11746, 2544, 3095, 0),
    TROLLHEIM(11747, 2910, 3612, 0);

    public final int id, x, y, z;

    TeleportTab(int id, int x, int y, int z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private void teleport(Player player, Item tab) {
        player.getMovement().startTeleport(event -> {
            player.animate(4069, 16);
            player.privateSound(965, 1, 15);
            event.delay(2);
            tab.remove(1);
            player.animate(4071);
            player.graphics(678);
            event.delay(2);
            if (this == HARMONY_ISLAND) {
                Position override = HomeTeleport.getHomeTeleportOverride(player);
                if (override != null) {
                    player.getMovement().teleport(override);
                } else {
                    if (!player.edgeHome) {
                        player.getMovement().teleport(x, y, z);
                    } else {
                        player.getMovement().teleport(World.EDGEHOME);
                    }
                }
            } else
                player.getMovement().teleport(x, y, z);
        });
    }

    public static void houseTab(Player player, Item tab) {
        if (player.house == null) {
            player.sendMessage("You don't have a house to teleport to.");
            return;
        }
        player.getMovement().startTeleport(event -> {
            player.lock(LockType.FULL_NULLIFY_DAMAGE);
            player.animate(4069, 16);
            player.privateSound(965, 1, 15);
            event.delay(2);
            if(tab.getId() == 8013)
                tab.remove(1);
            player.animate(4071);
            player.graphics(678);
            event.delay(1);
            if (Config.TELEPORT_INSIDE.get(player) == 0) {
                player.house.buildAndEnter(player, false);
                while (player.isLocked())
                    event.delay(1);
            } else {
                event.delay(1);
                player.getMovement().teleport(player.house.getLocation().getPosition());
            }
            player.unlock();
        });
    }

    static {
        for(TeleportTab tab : values())
            ItemAction.registerInventory(tab.id, "break", tab::teleport);
        ItemAction.registerInventory(8013, "break", TeleportTab::houseTab);
        ItemAction.registerInventory(4251, "empty", (player, item) -> {
            if (player.wildernessLevel <= 20) {
                player.startEvent(e -> {
                    player.lock(LockType.FULL_NULLIFY_DAMAGE);
                    player.animate(878);
                    player.graphics(1273);
                    e.delay(3);
                    player.getMovement().teleport(3659, 3522, 0);
                    player.animate(-1);
                    player.unlock();
                });
            } else {
                player.sendMessage("You may not use this past level 20 wilderness!");
            }
        });
    }

}