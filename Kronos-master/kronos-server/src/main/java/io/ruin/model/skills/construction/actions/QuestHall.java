package io.ruin.model.skills.construction.actions;

import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;

public class QuestHall {

    static {
        int glory = Buildable.MOUNTED_AMULET_OF_GLORY.getBuiltObjects()[0];
        ObjectAction.register(glory, "edgeville", (player, obj) -> AchievementGallery.JewelleryTeleport.EDGEVILLE.teleport(player));
        ObjectAction.register(glory, "karamja", (player, obj) -> AchievementGallery.JewelleryTeleport.KARAMJA.teleport(player));
        ObjectAction.register(glory, "draynor village", (player, obj) -> AchievementGallery.JewelleryTeleport.DRAYNOR_VILLAGE.teleport(player));
        ObjectAction.register(glory, "al kharid", (player, obj) -> AchievementGallery.JewelleryTeleport.AL_KHARID.teleport(player));
    }

}
