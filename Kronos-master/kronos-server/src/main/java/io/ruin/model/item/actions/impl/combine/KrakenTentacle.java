package io.ruin.model.item.actions.impl.combine;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;

public class KrakenTentacle {

    private static final int ABYSSAL_WHIP = 4151;
    private static final int KRAKEN_TENTACLE = 12004;
    private static final int ABYSSAL_TENTACLE = 12006;

    private static void makeWhip(Player player, Item primary, Item secondary, int result) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>The tentacle will gradually consume your whip and destroy it.<br>You won't be able to get the whip out again.<br>The combined item is not tradeable."),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, your tentacle will be destroyed.", result, 1, () -> {
                    primary.setId(ABYSSAL_TENTACLE);
                    AttributeExtensions.setCharges(primary, 10000);
                    secondary.remove();
                })
        );
    }

    private static void revertWhip(Player player, Item primary) {
        player.dialogue(
                new MessageDialogue("<col=7f0000>Warning!</col><br>Dissolving the abyssal tentacle does NOT return the abyssal whip, only the kraken tentacle. Are you sure?"),
                new YesNoDialogue("Are you sure you want to do this?", "If you select yes, you will only receive a kraken tentacle.", ABYSSAL_TENTACLE, 1, () -> {
                    primary.setId(KRAKEN_TENTACLE);
                    primary.clearAttribute(AttributeTypes.CHARGES);
                })
        );
    }

    private static void check(Player player, Item item) {
        player.sendMessage("Your Abyssal tentacle has " + NumberUtils.formatNumber(AttributeExtensions.getCharges(item)) + " charges left.");
    }

    static {
        ItemAction.registerInventory(ABYSSAL_TENTACLE, "check", KrakenTentacle::check);
        ItemAction.registerEquipment(ABYSSAL_TENTACLE, "check", KrakenTentacle::check);
        ItemAction.registerInventory(ABYSSAL_TENTACLE, "dissolve", KrakenTentacle::revertWhip);
        ItemItemAction.register(ABYSSAL_WHIP, KRAKEN_TENTACLE, (player, primary, secondary) -> makeWhip(player, primary, secondary, 12006));

        ItemDef.get(ABYSSAL_TENTACLE).addPostTargetDefendListener((player, item, hit, target) -> {
            if(Random.rollDie(4, 1))
                target.poison(4);
            int charges = AttributeExtensions.getCharges(item);
            if(charges == 0) //Assume the full abyssal tentacle was bought from a shop or the online store.
                charges = 10000;
            if(--charges <= 0) {
                item.remove();
                player.getInventory().addOrDrop(KRAKEN_TENTACLE, 1);
            }
            AttributeExtensions.setCharges(item, charges);
        });
    }


}
