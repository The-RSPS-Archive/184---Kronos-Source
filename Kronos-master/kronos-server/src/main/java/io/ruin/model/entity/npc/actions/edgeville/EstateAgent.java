package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.skills.construction.House;
import io.ruin.model.skills.construction.HouseLocation;
import io.ruin.model.skills.construction.HouseStyle;
import io.ruin.model.stat.StatType;

import java.util.Arrays;

import static io.ruin.cache.ItemID.COINS_995;

public class EstateAgent {

    private static final int HOUSE_COST = 100_000;
    private static final int ESTATE_AGENT = 5419;

    static {
        NPCAction.register(ESTATE_AGENT, "talk-to", EstateAgent::agentDialogue);
    }

    private static void agentDialogue(Player player, NPC npc) {
        if (player.house == null) { // purchase house dialogue
            player.dialogue(new NPCDialogue(npc, "Hello there, " + player.getName() + ". Welcome to " + World.type.getWorldName() + "'s Housing Agency! What can I do for you?"),
                    new OptionsDialogue(
                            new Option("How can I get a house?", () -> {
                                player.dialogue(new PlayerDialogue("How can I get a house?"),
                                        new NPCDialogue(npc, "I can sell you a starting house in Edgeville for " + Color.COOL_BLUE.wrap(NumberUtils.formatNumber(HOUSE_COST)) + " coins. As you increase " +
                                                "your construction skill you will be able to have your house moved to other areas and redecorated in other styles."),
                                        new NPCDialogue(npc, "Would you like to purchase a starter house?"),
                                        new OptionsDialogue("Purchase a house for " + Color.COOL_BLUE.wrap(NumberUtils.formatNumber(HOUSE_COST)) + " coins?",
                                                new Option("Yes please!", () -> {
                                                    player.dialogue(new PlayerDialogue("Yes please!"),
                                                            new ActionDialogue(() -> {
                                                                if (player.getInventory().contains(COINS_995, HOUSE_COST)) {
                                                                    player.getInventory().remove(COINS_995, HOUSE_COST);
                                                                    player.house = new House();
                                                                    player.dialogue(new NPCDialogue(npc, "Congratulations, adventurer! You now have your very own house! Simply step through the portal south of me, or teleport to your house to visit it."),
                                                                            new PlayerDialogue("Will do, thank you."),
                                                                            new NPCDialogue(npc, "Come see me again if you'd like to move your house somewhere else, or have it redecorated."));
                                                                } else {
                                                                    player.dialogue(new NPCDialogue(npc, "Oh, you don't seem to have the money with you right now. Come back when you do!"));
                                                                }
                                                            }));
                                                }),
                                                new Option("No thanks.", () -> {
                                                    player.dialogue(new PlayerDialogue("No thanks."),
                                                            new NPCDialogue(npc, "Okay, if you change your mind you know where to find me."));
                                                })
                                        )
                                );
                            }),
                            new Option("Tell me about houses.", () -> {
                                player.dialogue(new PlayerDialogue("Tell me about houses."),
                                        new NPCDialogue(npc, "It all came out of the wizards' experiments. They found a way to fold space, so that they could pack many acres of land " +
                                                "into an area only a foot across."),
                                        new NPCDialogue(npc, "They created several folded-space regions across " + World.type.getWorldName() + ". Each one contains hundreds of small plots here people can buy houses."),
                                        new PlayerDialogue("Ah, so that's how everyone can have a house without them cluttering up the wold!"),
                                        new NPCDialogue(npc, "Quite. The wizards didn't want to get bogged down in the business side of things so they hired me to sell the houses."),
                                        new NPCDialogue(npc, "There are various other people across Runite who can help you furnish your house. You should start buying planks from the sawmill operator " +
                                                "just west of me."),
                                        new PlayerDialogue("Thanks for the information!"));
                            })
                    ));
        } else {
            player.dialogue(new NPCDialogue(npc, "Hello there, " + player.getName() + ". Welcome to " + World.type.getWorldName() + "'s Housing Agency! What can I do for you?"),
                    new OptionsDialogue(
                            new Option("Move my house", () ->
                                    player.dialogue(new PlayerDialogue("I'd like to move my house, please!"),
                                            new ActionDialogue(() -> selectLocation(player)))),
                            new Option("Redecorate my house", () ->
                                    player.dialogue(new PlayerDialogue("I'm interested in redecorating my house."),
                                            new ActionDialogue(() -> selectStyle(player)))),
                            new Option("Demolish my house", () ->
                                    player.dialogue(new PlayerDialogue("I want to demolish my house."),
                                            new ActionDialogue(() -> confirmDemolish(player))))
                    ));
        }
    }

    private static void selectLocation(Player player) {
        OptionScroll.open(player, "Select a location", Arrays.stream(HouseLocation.values())
                .map(loc -> new Option(loc.getName() + " (Level " + loc.getLevelReq() + ") - " + NumberUtils.formatNumber(loc.getCost()) + " gp", () -> moveHouse(player, loc)))
                .toArray(Option[]::new));
    }

    private static void moveHouse(Player player, HouseLocation newLocation) {
        if (player.house == null) {
            return;
        }
        if (player.house.getLocation() == newLocation) {
            player.dialogue(new NPCDialogue(ESTATE_AGENT, "Your house is already at that location. Would you like to move it somewhere else?"),
                    new ActionDialogue(() -> selectLocation(player)));
            return;
        }
        if (player.getStats().get(StatType.Construction).fixedLevel < newLocation.getLevelReq()) {
            player.dialogue(new NPCDialogue(ESTATE_AGENT, "My apologies, but I'm only authorized to move your house to " + newLocation.getName() + " if you have a Construction level of at least " + newLocation.getLevelReq()),
                    new NPCDialogue(ESTATE_AGENT, "Perhaps you'd like to move your house to a different location?"),
                    new ActionDialogue(() -> selectLocation(player))
            );
            return;
        }
        if (!player.getInventory().contains(COINS_995, newLocation.getCost())) {
            player.dialogue(new NPCDialogue(ESTATE_AGENT, "I must charge a fee of " + NumberUtils.formatNumber(newLocation.getCost()) + " coins to move your house to " + newLocation.getName() + ", but you don't seem to have that much with you right now."),
                    new NPCDialogue(ESTATE_AGENT, "Perhaps you'd like to move your house to a different location?"),
                    new ActionDialogue(() -> selectLocation(player))
            );
            return;
        }
        player.getInventory().remove(COINS_995, newLocation.getCost());
        player.house.setLocation(newLocation);
        player.dialogue(new NPCDialogue(ESTATE_AGENT, "Very well, I shall make the necessary arrangements to move your house to " + newLocation.getName() + "."),
                new PlayerDialogue("Okay."),
                new NPCDialogue(ESTATE_AGENT, "..."),
                new PlayerDialogue("Hello?"),
                new NPCDialogue(ESTATE_AGENT, "..."),
                new PlayerDialogue("...?"),
                new NPCDialogue(ESTATE_AGENT, "Congratulations! Your house has been moved."),
                new PlayerDialogue("Uh... thanks...")
        );
    }

    private static void selectStyle(Player player) {
        OptionScroll.open(player, "Select a location", Arrays.stream(HouseStyle.values())
                .map(style -> new Option(StringUtils.fixCaps(style.name().replace('_', ' ')) + " - " + NumberUtils.formatNumber(style.cost) + " gp",
                        () -> redecorate(player, style)))
                .toArray(Option[]::new));
    }

    private static void redecorate(Player player, HouseStyle newStyle) {
        if (player.house == null) {
            return;
        }
        if (player.house.getStyle() == newStyle) {
            player.dialogue(new NPCDialogue(ESTATE_AGENT, "Your house is already decorated in that style. Would you like to choose a different one?"),
                    new ActionDialogue(() -> selectStyle(player)));
            return;
        }
        if (player.getStats().get(StatType.Construction).fixedLevel < newStyle.level) {
            player.dialogue(new NPCDialogue(ESTATE_AGENT, "My apologies, but I'm only authorized to redecorate your house with the " + StringUtils.getFormattedEnumName(newStyle) + " style if you have a Construction level of at least " + newStyle.level + "."),
                    new NPCDialogue(ESTATE_AGENT, "Perhaps you'd like to choose a different style?"),
                    new ActionDialogue(() -> selectStyle(player))
            );
            return;
        }
        if (!player.getInventory().contains(COINS_995, newStyle.cost)) {
            player.dialogue(new NPCDialogue(ESTATE_AGENT, "I must charge a fee of " + NumberUtils.formatNumber(newStyle.cost) + " coins to redecorate your house with the " + StringUtils.getFormattedEnumName(newStyle) + " style, but you don't seem to have that much with you right now."),
                    new NPCDialogue(ESTATE_AGENT, "Perhaps you'd like to choose a different style?"),
                    new ActionDialogue(() -> selectStyle(player))
            );
            return;
        }
        player.getInventory().remove(COINS_995, newStyle.cost);
        player.house.setStyle(newStyle);
        player.dialogue(new NPCDialogue(ESTATE_AGENT, "Very well, I shall make the necessary arrangements to redecorate your house with the " + StringUtils.getFormattedEnumName(newStyle) + " style..."),
                new NPCDialogue(ESTATE_AGENT, "Congratulations! Your house has been redecorated."),
                new PlayerDialogue("Thanks.")
        );
    }

    private static void confirmDemolish(Player player) {
        player.dialogue(new NPCDialogue(ESTATE_AGENT, "Are you sure you want to do that? Demolishing your house cannot be undone, and you will lose everything you've built in it."),
                new OptionsDialogue(
                        new Option("Nevermind"),
                        new Option("Yes, I'm sure", () -> {
                            player.stringInput("Your house will be demolished. THIS CANNOT BE UNDONE. Type \"I understand\" to confirm:", input -> {
                                if (input.equalsIgnoreCase("i understand")) {
                                    if (player.getBankPin().requiresVerification(p -> doDemolish(player))) {
                                        return;
                                    }
                                    doDemolish(player);
                                }
                            });
                        })
                ));
    }

    private static void doDemolish(Player player) {
        player.house.expelGuests();
        player.house = new House();
        player.dialogue(new NPCDialogue(ESTATE_AGENT, "As you wish. Your house has been demolished and returned to the default state."),
                new PlayerDialogue("Thank you."));
    }

}
