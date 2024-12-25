package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.data.impl.Help;
import io.ruin.data.impl.teleports;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.XpMode;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.XpCounter;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Direction;
import io.ruin.model.stat.StatType;
import io.ruin.network.central.CentralClient;
import io.ruin.utility.Broadcast;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static io.ruin.cache.ItemID.*;

@Slf4j
public class StarterGuide {

	private static final NPC GUIDE = SpawnListener.first(306);

	static {
		NPCDef.get(307).ignoreOccupiedTiles = true;
		NPCAction.register(GUIDE, "view-help", (player, npc) -> Help.open(player));
		NPCAction.register(GUIDE, "view-guide", (player, npc) -> player.dialogue(
                new OptionsDialogue("Watch the guide?",
                        new Option("Yes", () -> tutorial(player)),
                        new Option("No", player::closeDialogue))
        ));
		NPCAction.register(GUIDE, "talk-to", StarterGuide::optionsDialogue);

		LoginListener.register(player -> {
            if (player.newPlayer) {
                XpCounter.select(player, 1);
                CentralClient.sendClanRequest(player.getUserId(), "Help");
                tutorial(player);
            } else {
                //player.getPacketSender().sendMessage("Latest Update: " + LatestUpdate.LATEST_UPDATE_TITLE + "|" + LatestUpdate.LATEST_UPDATE_URL, "", 14);
            }
		});
	}

    private static void optionsDialogue(Player player, NPC npc) {
        player.dialogue(new NPCDialogue(npc, "Hello " + player.getName() + ", is there something I could assist you with?"),
                new OptionsDialogue(
                        new Option("View help pages", () -> Help.open(player)),
                        new Option("Replay tutorial", () -> ecoTutorial(player)),
                        new Option("Change home point", () -> {
                            npc.startEvent(event -> {
                                if (!player.edgeHome) {
                                    player.dialogue(new NPCDialogue(npc, "I can move your spawn point and <br>" +
                                            "home teleport location to Edgeville.<br>" +
                                            "it will cost 5,000,000 GP.<br>" +
                                            "Would you like to do this?"),
                                    new OptionsDialogue(
                                        new Option("No.. I like this home.", player::closeDialogue),
                                        new Option("Yes! I want to respawn in Edgeville!", () -> {
                                            if (player.getInventory().hasItem(995, 5000000)) {
                                                player.getInventory().remove(995, 5000000);
                                                player.edgeHome = true;
                                                player.dialogue(new NPCDialogue(npc, "Your spawn point has been changed<br>" +
                                                        "to Edgeville! If you'd like to change<br>" +
                                                        "it back, just speak to me again."));
                                            } else {
                                                player.dialogue(new NPCDialogue(npc, "I'm sorry but it doesn't look like<br>" +
                                                        "you can afford this.."));
                                            }
                                    })));
                                } else {
                                    player.dialogue(
                                    new NPCDialogue(npc, "Are you wanting to move your<br>" +
                                            "spawn point back to home? It will cost<br>" +
                                            "another 5,000,000 GP."),
                                    new OptionsDialogue(
                                            new Option("No thanks.", player::closeDialogue),
                                            new Option("Yes please!", () -> {
                                                if (player.getInventory().hasItem(995, 5000000)) {
                                                    player.getInventory().remove(995, 5000000);
                                                    player.edgeHome = false;
                                                    player.dialogue(new NPCDialogue(npc, "Your spawn point has been changed<br>" +
                                                            "back to home. If you'd like it changed,<br>" +
                                                            "just speak to me again!"));
                                                } else {
                                                    player.dialogue(new NPCDialogue(npc,"I'm sorry but it doesn't look like<br>" +
                                                            "you can afford this.."));
                                                }
                                            })
                                    ));
                                }
                            });
                        })));
    }

	@SneakyThrows
	private static void ecoTutorial(Player player) {
		boolean actuallyNew = player.newPlayer;
		player.inTutorial = true;
		player.startEvent(event -> {
            player.lock(LockType.FULL_ALLOW_LOGOUT);
			player.getMovement().teleport(2038, 3577, 0);
			if (actuallyNew) {
				player.openInterface(InterfaceType.MAIN, Interface.APPEARANCE_CUSTOMIZATION);
				while (player.isVisibleInterface(Interface.APPEARANCE_CUSTOMIZATION)) {
					event.delay(1);
				}
			}
			NPC guide = new NPC(307).spawn(2037, 3577, 0, Direction.SOUTH, 0); // 307 is a copy of 306 without options so it doesnt get in other people's way
			player.logoutListener = new LogoutListener().onLogout(p -> guide.remove());
			player.getPacketSender().sendHintIcon(guide);
			guide.face(player);
			player.face(guide);
			boolean startTutorial = false;
			if (actuallyNew) {
			    player.dialogue(
			            new NPCDialogue(guide, "Please select an experience mode.<br>" +
                                "You can change this later, but only to an easier<br>" +
                                "experience mode, not harder."),
                        new OptionsDialogue(
                                new Option("Easy - 100x Combat, 30x Skilling", () -> XpMode.setXpMode(player, XpMode.EASY)),
                                new Option("Medium - 60x Combat, 30x Skilling", () -> XpMode.setXpMode(player, XpMode.MEDIUM)),
                                new Option("Hard - 5x Combat, 5x Skilling - " + Color.RED.wrap("+10% Drop Chance!"), () -> XpMode.setXpMode(player, XpMode.HARD))
                        )
                );
			    event.waitForDialogue(player);
				player.dialogue(
				        new NPCDialogue(guide, "Before I let you go, let's talk about your game mode."),
						new NPCDialogue(guide, "Do you want to see the options for Iron Man modes?"),
						new OptionsDialogue("View Iron Man options?",
                                new Option("Yes", () -> {
							        GameMode.openSelection(player);
							        player.unsafeDialogue(new MessageDialogue("Close the interface once you're happy with your selection." +
                                        "<br><br><col=ff0000>WARNING:</col> This is the ONLY chance to choose your Iron Man mode.").hideContinue());
						}), new Option("No", player::closeDialogue)));
				event.waitForDialogue(player);
				String text = "You want to be a part of the economy, then? Great!";
				if (player.getGameMode() == GameMode.IRONMAN) {
					text = "Iron Man, huh? Self-sufficiency is quite a challenge, good luck!";
				} else if (player.getGameMode() == GameMode.HARDCORE_IRONMAN) {
					text = "Hardcore?! You only live once... make it count!";
				} else if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
					text = "Ultimate Iron Man... Up for quite the challenge, aren't you?";
				}
				if (player.getGameMode().isIronMan()) {
                    player.dialogue(new NPCDialogue(guide, text),
                    new NPCDialogue(guide, "I'll give you a few items to help get you started..."),
                    new NPCDialogue(guide, "There you go, some basic stuff. If you need anything else, remember to check the shops.") {
                        @Override
                        public void open(Player player) {
                            giveEcoStarter(player);
                            player.newPlayer = false;
                            super.open(player);
                        }
                    });
                } else {
                    player.dialogue(new NPCDialogue(guide, "Excellent.. I'll give you a few items to help get you started and fill your bank with various starter items"),
                    new NPCDialogue(guide, "There you go, some basic stuff. If you need anything else, remember to check Sigmund's shop.") {
                        @Override
                            public void open(Player player) {
                            giveEcoStarter(player);
                            player.newPlayer = false;
                            super.open(player);
                        }
                    });
                }
				event.waitForDialogue(player);
                Broadcast.WORLD.sendNews(player.getName() + " has just joined " + World.type.getWorldName() + "!");
                startTutorial = true;
            }
			if (startTutorial) {

                player.dialogue(new NPCDialogue(guide,
                "Greetings, " + player.getName() + "! Welcome to " + World.type.getWorldName() + ".<br>" +
                "Would you like to a run down on how the server works?"),
                new OptionsDialogue("Play the tutorial?", new Option("Yes!", () -> introCutscene(guide, player)), new Option("No, I know what I'm doing!", () -> {
                    player.closeDialogue();
                    player.inTutorial = false;
                    player.logoutListener = null;
                    player.setTutorialStage(0);
                    player.unlock();
                    guide.addEvent(evt -> {
                        evt.delay(2);
                        World.sendGraphics(86, 50, 0, guide.getPosition());
                        guide.remove();
                    });
                })));
            }
		});
	}

    private static void introCutscene(NPC guide, Player player) {

        guide.startEvent((e) -> {
            player.getPacketSender().sendClientScript(39, "i", 100);
            Config.LOCK_CAMERA.set(player, 1);
            player.getPacketSender().moveCameraToLocation(2050, 3572, 600, 0, 12);
            player.getPacketSender().turnCameraToLocation(2045, 3577, 400, 0, 25);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "This is our teleporter. Using it will give you access<br>" +
                            "to locations all around the world, and even in the<br>" +
                            "wilderness. You can even access your most recent<br>" +
                            "teleport by right-clicking on the teleporter!"));
            e.waitForDialogue(player);

            player.getMovement().teleport(2019, 3577, 0);
            e.delay(1);
            player.getPacketSender().moveCameraToLocation(2015, 3577, 750, 0, 12);
            player.getPacketSender().turnCameraToLocation(2010, 3577, 500, 0, 30);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "This is Kronos bank. It houses the normal bank<br>" +
                            "amenities but also inside are the vote point and<br>" +
                            "donation managers."));
            e.waitForDialogue(player);
            player.getPacketSender().moveCameraToLocation(2013, 3577, 750, 0, 12);
            player.getPacketSender().turnCameraToLocation(2019, 3577, 500, 0, 18);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "Here we have the trading post.<br>" +
                            "You can buy items from other players, much like<br>" +
                            "the grand exchange."));
            e.waitForDialogue(player);

            player.getMovement().teleport(2031, 3577, 0);
            player.getPacketSender().moveCameraToLocation(2026, 3573, 500, 0, 12);
            player.getPacketSender().turnCameraToLocation(2032, 3571, 500, 0, 30);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "Here is the construction portal. Kronos offers full<br>" +
                            "construction! There are also NPC's here to sell you<br>" +
                            "supplies to build your house and remodel it as well."));
            e.waitForDialogue(player);

            player.getMovement().teleport(2031, 3577, 0);
            player.getPacketSender().turnCameraToLocation(2020, 3558, 400, 0, 30);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "All of our basic shops are located in this building<br>" +
                            "The ironman shop is in the small building to<br>" +
                            "the east."));
            e.waitForDialogue(player);

            player.getPacketSender().moveCameraToLocation(2064, 3583, 1000, 0, 12);
            player.getPacketSender().turnCameraToLocation(2062, 3570, 0, 0, 30);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "There is a skilling area to the east of home<br>" +
                            "just over the bridge. Fishing is also available<br>" +
                            "to the north."));
            e.delay(10);
            player.getPacketSender().moveCameraToLocation(2064, 3572, 1200, 0, 12);
            player.getPacketSender().turnCameraToLocation(2064, 3590, 0, 0, 32);
            e.waitForDialogue(player);
            Config.LOCK_CAMERA.set(player, 0);
            player.getPacketSender().resetCamera();
            player.setTutorialStage(1);

            guide.getMovement().teleport(2031, 3577, 0);
            player.getMovement().teleport(2032, 3577, 0);
            guide.face(player);
            player.face(guide);
            player.getPacketSender().moveCameraToLocation(2032, 3582, 450, 0, 12);
            player.getPacketSender().turnCameraToLocation(2032, 3577, 400, 0, 30);
            player.dialogue(new NPCDialogue(guide,
                    "If you have any other questions, there are always<br>" +
                            "helpful users in the help clan chat"));
            e.waitForDialogue(player);
            guide.animate(863);
            player.inTutorial = false;
            player.unlock();
            player.setTutorialStage(0);
            guide.addEvent(evt -> {
                evt.delay(2);
                World.sendGraphics(86, 50, 0, guide.getPosition());
                player.logoutListener = null;
                guide.remove();
            });
            player.getPacketSender().resetCamera();
        });
    }

    private static void giveEcoStarter(Player player) {
        player.getInventory().add(COINS_995, 10000); // gp
        player.getInventory().add(558, 500); // Mind Rune
        player.getInventory().add(556, 1500); // Air Rune
        player.getInventory().add(554, 1000); // Fire Rune
        player.getInventory().add(555, 1000); // Water Rune
        player.getInventory().add(557, 1000); // Earth Rune
        player.getInventory().add(562, 1000); // Chaos Rune
        player.getInventory().add(560, 500); // Death Rune
        player.getInventory().add(1381, 1); // Air Staff
        player.getInventory().add(362, 50); // Tuna
        player.getInventory().add(863, 300); // Iron Knives
        player.getInventory().add(867, 150); // Adamant Knives
        player.getInventory().add(1169, 1); // Coif
        player.getInventory().add(1129, 1); // Leather body
        player.getInventory().add(1095, 1); // Leather Chaps
        player.getInventory().add(13385, 1); // Xeric Hat
        player.getInventory().add(12867, 1); // Blue d hide set
        player.getInventory().add(13024, 1); // Rune set
        player.getInventory().add(11978, 1); // Glory 6
        player.getInventory().add(13387, 1); // Xerican Top
        player.getInventory().add(1323, 1); // Iron scim
        player.getInventory().add(1333, 1); // Rune scim
        player.getInventory().add(4587, 1); // Dragon Scim
        switch (player.getGameMode()) {
            case IRONMAN:
                player.getInventory().add(12810, 1);
                player.getInventory().add(12811, 1);
                player.getInventory().add(12812, 1);
                break;
            case ULTIMATE_IRONMAN:
                player.getInventory().add(12813, 1);
                player.getInventory().add(12814, 1);
                player.getInventory().add(12815, 1);
                break;
            case HARDCORE_IRONMAN:
                player.getInventory().add(20792, 1);
                player.getInventory().add(20794, 1);
                player.getInventory().add(20796, 1);
                break;
            case STANDARD:
                player.getInventory().add(COINS_995, 115000);
                break;
        }
    }

	private static NPC find(Player player, int id) {
		for (NPC n : player.localNpcs()) {
			if (n.getId() == id)
				return n;
		}
		throw new IllegalArgumentException();
	}

	private static void setDrag(Player player) {
		player.dialogue(
				new OptionsDialogue("What drag setting would you like to use?",
						new Option("5 (OSRS) (2007) Drag", () -> setDrag(player, 5)),
						new Option("10 (Pre-EoC) (2011) Drag", () -> setDrag(player, 10))
				)
		);
	}

	private static void setDrag(Player player, int drag) {
		player.dragSetting = drag;
	}

	private static void tutorial(Player player) {
        ecoTutorial(player);
	}

	private static void addPKModeItemToBank(Player player) {
        player.getBank().add(19625, 5); // Home teleport
        player.getBank().add(2550, 3); // Recoils
        player.getBank().add(385, 125); // Sharks
        player.getBank().add(3144, 50); // Karambwans
        player.getBank().add(2436, 5); // attk
        player.getBank().add(2440, 5); // str
        player.getBank().add(2444, 5); // range
        player.getBank().add(3024, 5); // restore
//Next Line
        player.getBank().add(6685, 10); // brew
        player.getBank().add(560, 2250); // Death runes
        player.getBank().add(565, 1000); // Blood runes
        player.getBank().add(561, 300); // Nature runes
        player.getBank().add(145, 1); // atk
        player.getBank().add(157, 1); // str
        player.getBank().add(169, 1); // range
        player.getBank().add(3026, 1); // restore
//Next Line
        player.getBank().add(6687, 1); // brew
        player.getBank().add(9075, 400); // Astral runes
        player.getBank().add(555, 6000); // Water runes
        player.getBank().add(557, 1000); // Earth runes
        player.getBank().add(147, 1); // atk
        player.getBank().add(159, 1); // str
        player.getBank().add(171, 1); // range
        player.getBank().add(3028, 1); // restore
//Next Line
        player.getBank().add(6689, 1); // brew
        player.getBank().add(7458, 100); // mithril gloves for pures
        player.getBank().add(7462, 100); // gloves
        player.getBank().add(3842, 100); // god book
        player.getBank().add(149, 1); // atk
        player.getBank().add(161, 1); // str
        player.getBank().add(173, 1); // range
        player.getBank().add(3030, 1); // restore
//Next Line
        player.getBank().add(6691, 1); // brew
        player.getBank().add(9144, 500); // bolts
        player.getBank().add(2503, 5); // hides
        player.getBank().add(4099, 5); // Mystic
        player.getBank().add(2414, 100); // zamy god cape
        player.getBank().add(10828, 5); // neit helm
        player.getBank().add(4587, 5); // Scim
        player.getBank().add(1163, 3); // rune full helm
//Next Line
        player.getBank().add(562, 50); // Chaos rune
        player.getBank().add(892, 400); // rune arrows
        player.getBank().add(2497, 5); // hides
        player.getBank().add(4101, 5); // Mystic
        player.getBank().add(4675, 5); // ancient staff
        player.getBank().add(1201, 5); // rune
        player.getBank().add(5698, 5); // dagger
        player.getBank().add(1127, 3); // rune pl8
//Next Line
        player.getBank().add(563, 50); // law rune
        player.getBank().add(9185, 5); // crossbow
        player.getBank().add(10499, 100); // avas
        player.getBank().add(4103, 5); // Mystic
        player.getBank().add(4107, 5); // Mystic
        player.getBank().add(3105, 5); // climbers
        player.getBank().add(11978, 3); // glory(6)
        player.getBank().add(1079, 3); // rune legs
//Next Line
        player.getBank().add(1215, 2); // dagger unpoisoned
        player.getBank().add(3751, 2); // zerker helm
        player.getBank().add(1093, 2); // rune


        // Give the players PK stats
        player.getStats().get(StatType.Attack).set(99);
        player.getStats().get(StatType.Strength).set(99);
        player.getStats().get(StatType.Defence).set(99);
        player.getStats().get(StatType.Hitpoints).set(99);
        player.getStats().get(StatType.Magic).set(99);
        player.getStats().get(StatType.Ranged).set(99);
        player.getStats().get(StatType.Prayer).set(99);
        player.getCombat().updateLevel();
    }

}
