package io.ruin.model.skills.farming.farmer;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.patch.Patch;
import io.ruin.model.skills.farming.patch.PatchData;

import static io.ruin.cache.ItemID.COINS_995;

public enum Farmer {
    ELSTAN(2663, PatchData.FALADOR_NORTH, PatchData.FALADOR_SOUTH),
    LYRA(2666, PatchData.CANIFIS_NORTH, PatchData.CANIFIS_SOUTH),
    DANTAERA(2664, PatchData.CATHERBY_NORTH, PatchData.CATHERBY_SOUTH),
    KRAGEN(2665, PatchData.ARDOUGNE_NORTH, PatchData.ARDOUGNE_SOUTH),
    MARISI(6921, PatchData.ZEAH_NORTH, PatchData.ZEAH_SOUTH),

    FAYETH(2681, PatchData.LUMBRIDGE_TREE),
    TREZNOR(2680, PatchData.VARROCK_TREE),
    HESKEL(2679, PatchData.FALADOR_TREE),
    ALAIN(2678, PatchData.TAVERLEY_TREE),
    PRISSY_SCILLA(2687, PatchData.GNOME_TREE),

    BOLONGO(2682, PatchData.GNOME_FRUIT),
    ELLENA(2670, PatchData.CATHERBY_FRUIT),
    GILETH(2683, PatchData.VILLAGE_FRUIT),
    GARTH(2669, PatchData.BRIMHAVEN_FRUIT),
    LILIWEN(2689, PatchData.LLETYA_FRUIT),

    DREVEN(2674, PatchData.VARROCK_BUSH),
    TARIA(2675, PatchData.RIMMINGTON_BUSH),
    RHAZIEN(2676, PatchData.ETCETERIA_BUSH),
    TORRELL(2677, PatchData.ARDOUGNE_BUSH),

    VASQUEN(2672, PatchData.LUMBRIDGE_HOPS),
    RHONEN(2673, PatchData.SEERS_HOPS),
    SELENA(2671, PatchData.YANILLE_HOPS),
    FRANCIS(2667, PatchData.ENTRANA_HOPS),

    IMIAGO(2688, PatchData.CALQUAT),
    AYESHA(310, PatchData.CACTUS),

    PRAISTAN_EBOLA(2686, PatchData.BRIMHAVEN_SPIRIT_TREE),
    FRIZZY_SKERNIP(2684, PatchData.PORT_SARIM_SPIRIT_TREE),
    YULF_SQUECKS(2685, PatchData.ETCETERIA_SPIRIT_TREE),
    LAMMY_LANGLE(6814, PatchData.ZEAH_SPIRIT_TREE);

    Farmer(int npcId, PatchData patch1, PatchData patch2) {
        this.npcId = npcId;
        this.patch1 = patch1;
        this.patch2 = patch2;
    }

    Farmer(int npcId, PatchData patch1) {
        this(npcId, patch1, null);
    }

    private int npcId;

    private PatchData patch1;
    private PatchData patch2;

    private static void attemptPayment(Player player, NPC npc, PatchData pd) {
        Patch patch = player.getFarming().getPatch(pd.getObjectId());
        if (patch == null) {
            throw new IllegalArgumentException();
        }
        if (patch.getPlantedCrop() == null) {
            player.dialogue(new NPCDialogue(npc, "You don't have any " + pd.getType() + " planted in that patch. Plant " +
                    "some and I might agree to look after it for you."));
            return;
        }
        if (patch.isFarmerProtected()) {
            player.dialogue(new NPCDialogue(npc, "What do you mean? I'm already watching that for you."));
            return;
        }
        if (patch.getDiseaseStage() == 1) {
            player.dialogue(new NPCDialogue(npc, "Your patch is diseased, cure it first if you want me to watch it for you."));
            return;
        }
        if (patch.getDiseaseStage() == 2) {
            player.dialogue(new NPCDialogue(npc, "Your patch is already dead, there's nothing I can do about that."));
            return;
        }
        if (patch.getStage() >= patch.getPlantedCrop().getTotalStages()) {
            player.dialogue(new NPCDialogue(npc, "Your patch is already fully grown."));
            return;
        }
        Item payment = patch.getPlantedCrop().getPayment();
        if (payment == null) {
            player.dialogue(new NPCDialogue(npc, "Sorry, I'm not familiar with what you have planted on that patch."));
            return;
        }
        int id = payment.getId();
        if (!player.getInventory().contains(id, payment.getAmount())) {
            if (payment.getDef().notedId == -1 || !player.getInventory().contains(payment.getDef().notedId, payment.getAmount())) {
                player.dialogue(new NPCDialogue(npc, "I can watch over your patch for you if you pay me " + getItemName(payment) + "."));
                return;
            } else {
                id = payment.getDef().notedId;
            }
        }
        player.getInventory().remove(id, payment.getAmount());
        patch.setFarmerProtected(true);
        player.dialogue(new NPCDialogue(npc, "That'll do nicely, sir. Leave it with me - I'll make sure that patch grows for you."));
    }

    private static String getItemName(Item item) {
        String str = "";
        ItemDef def = item.getDef();
        if (item.getId() == COINS_995) {
            return NumberUtils.formatNumber(item.getAmount()) + " coins";
        } else {
            if (item.getAmount() > 1) {
                str += String.valueOf(item.getAmount());
            } else {
                str += StringUtils.vowelStart(def.name) && !def.name.contains("(") ? "an" : "a";
            }
            str += " ";
            if (def.name.endsWith("(5)")) {
                str += "basket";
                if (item.getAmount() > 1)
                    str += "s";
                str += " filled with 5 " + def.name.toLowerCase().substring(0, def.name.indexOf("("));
                if (item.getAmount() > 1)
                    str += " each";
            } else if (def.name.endsWith("(10)")) {
                str += "sack";
                if (item.getAmount() > 1)
                    str += "s";
                str += " filled with 10 " + def.name.toLowerCase().substring(0, def.name.indexOf("("));
                if (item.getAmount() > 1)
                    str += " each";
            } else {
                str += def.name;
            }
            return str;
        }
    }

    static {
        for (Farmer farmer : values()) {
            NPCAction.register(farmer.npcId, 1, (player, npc) -> {
                //TODO shop, navigate to payment
            });
            if (farmer.patch1 != null) {
                NPCAction.register(farmer.npcId, 3, (player, npc) -> {
                    attemptPayment(player, npc, farmer.patch1);
                });
            }
            if (farmer.patch2 != null) {
                NPCAction.register(farmer.npcId, 4, (player, npc) -> {
                    attemptPayment(player, npc, farmer.patch2);
                });
            }
        }
    }


}
