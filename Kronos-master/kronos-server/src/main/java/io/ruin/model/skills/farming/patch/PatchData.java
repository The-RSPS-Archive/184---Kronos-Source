package io.ruin.model.skills.farming.patch;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;

public enum PatchData {

    FALADOR_HERB(8150, Config.FARMING_PATCH_4, 6),
    CATHERBY_HERB(8151, Config.FARMING_PATCH_4, 6),
    ARDOUGNE_HERB(8152, Config.FARMING_PATCH_4, 6),
    CANIFIS_HERB(8153, Config.FARMING_PATCH_4, 6),
    TROLLHEIM_HERB(18816, Config.FARMING_PATCH_1, 6),
    ZEAH_HERB(27115, Config.FARMING_PATCH_4, 6),

    FALADOR_FLOWER(7847, Config.FARMING_PATCH_3, 5),
    CATHERBY_FLOWER(7848, Config.FARMING_PATCH_3, 5),
    ARDOUGNE_FLOWER(7849, Config.FARMING_PATCH_3, 5),
    CANIFIS_FLOWER(7850, Config.FARMING_PATCH_3, 5),
    ZEAH_FLOWER(27111, Config.FARMING_PATCH_3, 5),

    FALADOR_NORTH(8550, Config.FARMING_PATCH_1, 0),
    FALADOR_SOUTH(8551, Config.FARMING_PATCH_2, 0),
    CATHERBY_NORTH(8552, Config.FARMING_PATCH_1, 0),
    CATHERBY_SOUTH(8553, Config.FARMING_PATCH_2, 0),
    ARDOUGNE_NORTH(8554, Config.FARMING_PATCH_1, 0),
    ARDOUGNE_SOUTH(8555, Config.FARMING_PATCH_2, 0),
    CANIFIS_NORTH(8556, Config.FARMING_PATCH_1, 0),
    CANIFIS_SOUTH(8557, Config.FARMING_PATCH_2, 0),
    ZEAH_NORTH(27113, Config.FARMING_PATCH_1, 0),
    ZEAH_SOUTH(27114, Config.FARMING_PATCH_2, 0),

    TAVERLEY_TREE(8388, "saplings", Config.FARMING_PATCH_1, 2),
    FALADOR_TREE(8389, "saplings", Config.FARMING_PATCH_1, 2),
    VARROCK_TREE(8390, "saplings", Config.FARMING_PATCH_1, 2),
    LUMBRIDGE_TREE(8391, "saplings", Config.FARMING_PATCH_1, 2),
    GNOME_TREE(19147, "saplings", Config.FARMING_PATCH_1, 2),

    GNOME_FRUIT(7962, "saplings", Config.FARMING_PATCH_2, 3),
    VILLAGE_FRUIT(7963, "saplings", Config.FARMING_PATCH_1, 3),
    BRIMHAVEN_FRUIT(7964, "saplings", Config.FARMING_PATCH_1, 3),
    CATHERBY_FRUIT(7965, "saplings", Config.FARMING_PATCH_1, 3),
    LLETYA_FRUIT(26579, "saplings", Config.FARMING_PATCH_1, 3),

    FALADOR_COMPOST_BIN(7836, Config.FARMING_COMPOST_BIN, -1),
    CATHERBY_COMPOST_BIN(7837, Config.FARMING_COMPOST_BIN, -1),
    CANIFIS_COMPOST_BIN(7838, Config.FARMING_COMPOST_BIN, -1),
    ARDOUGNE_COMPOST_BIN(7839, Config.FARMING_COMPOST_BIN, -1),
    ZEAH_COMPOST_BIN(27112, Config.FARMING_COMPOST_BIN, -1),

    VARROCK_BUSH(7577, Config.FARMING_PATCH_1, 4),
    RIMMINGTON_BUSH(7578, Config.FARMING_PATCH_1, 4),
    ETCETERIA_BUSH(7579, Config.FARMING_PATCH_1, 4),
    ARDOUGNE_BUSH(7580, Config.FARMING_PATCH_1, 4),

    YANILLE_HOPS(8173, Config.FARMING_PATCH_1, 1),
    ENTRANA_HOPS(8174, Config.FARMING_PATCH_1, 1),
    LUMBRIDGE_HOPS(8175, Config.FARMING_PATCH_1, 1),
    SEERS_HOPS(8176, Config.FARMING_PATCH_1, 1),

    CALQUAT(7807, "saplings", Config.FARMING_PATCH_1, 7),
    CACTUS(7771, Config.FARMING_PATCH_1, 7),
    CANIFIS_MUSHROOM(8337, Config.FARMING_PATCH_1, 7),

    BRIMHAVEN_SPIRIT_TREE(8383, "saplings", Config.FARMING_PATCH_2, 7),
    PORT_SARIM_SPIRIT_TREE(8338, "saplings", Config.FARMING_PATCH_1, 7),
    ETCETERIA_SPIRIT_TREE(8382, "saplings", Config.FARMING_PATCH_2, 7),
    ZEAH_SPIRIT_TREE(27116, "saplings", Config.FARMING_PATCH_1, 7);

    PatchData(int objectId, Config config, int guideChildId) {
        this.objectId = objectId;
        this.config = config;
        this.type = "seeds";
        this.guideChildId = guideChildId;
    }


    PatchData(int objectId, String type, Config config, int guideChildId) {
        this.objectId = objectId;
        this.config = config;
        this.type = type;
        this.guideChildId = guideChildId;
    }

    public int getObjectId() {
        return objectId;
    }

    public Config getConfig() {
        return config;
    }

    public int getGuideChildId() {
        return guideChildId;
    }

    public String getType() {
        return type;
    }

    private final int objectId;
    private final Config config;
    private final String type;
    private final int guideChildId;

    static {
        for (PatchData pd : values()) {
            for (int i = 1; i < 6; i++) {
                final int opt = i;
                ObjectAction.register(pd.getObjectId(), opt, ((player, obj) -> player.getFarming().handleObject(obj, opt)));
            }
            ItemObjectAction.register(pd.getObjectId(), ((player, item, obj) -> player.getFarming().itemOnPatch(item, obj)));
        }

    }

}
