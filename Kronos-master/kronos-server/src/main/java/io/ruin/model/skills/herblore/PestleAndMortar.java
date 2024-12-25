package io.ruin.model.skills.herblore;

import io.ruin.api.utils.Random;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.Tool;

public enum PestleAndMortar {

    UNICORN_HORN(237, 235, "You grind the unicorn horn to dust."),
    CHOCOLATE_BAR(1973, 1975, "You grind the chocolate to dust."),
    KEBBIT_TEETH(10109, 10111, "You grind the kebbit teeth to dust."),
    BIRD_NEST(5075, 6693, "You grind the bird's nest down."),
    DESERT_GOAT_HORN(9735, 9736, "You grind the goat's horn to dust."),
    CHARCOAL(973, 704, "You grind the charcoal to a powder."),
    ASHES(592, 8865, "You grind down the ashes."),
    BLUE_DRAGON_SCALE(243, 241, "You grind the dragon scale to dust."),
    LAVA_SCALE(11992, 11994, null),
    SUPERIOR_DRAGON_BONES(22124, 21975, "You grind the superior bones to dust.");

    public final int before, after;

    public final String message;

    PestleAndMortar(int before, int after, String message) {
        this.before = before;
        this.after = after;
        this.message = message;
    }

    static {
        for(PestleAndMortar item : values()) {
            ItemItemAction.register(item.before, Tool.PESTLE_AND_MORTAR, (player, before, pestleAndMortar) -> player.startEvent(event -> {
                for(Item resource : player.getInventory().getItems()) {
                    if(resource == null || resource.getId() != item.before)
                        continue;
                    if(item != LAVA_SCALE) {
                        resource.setId(item.after);
                        player.sendMessage(item.message);
                    } else {
                        int amountOfShards = Random.get(3, 6);
                        resource.remove();
                        player.getInventory().add(item.after, amountOfShards);
                        player.sendMessage("You grind the lava dragon scale into " + amountOfShards + " shards.");
                    }
                    player.animate(364);
                    event.delay(2);
                }
            }));
        }
    }
}
