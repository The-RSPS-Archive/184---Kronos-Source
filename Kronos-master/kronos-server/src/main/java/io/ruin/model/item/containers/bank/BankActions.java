package io.ruin.model.item.containers.bank;

import io.ruin.Server;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NPCDef;
import io.ruin.cache.ObjectDef;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.handlers.CollectionBox;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.actions.impl.BloodyTokens;
import io.ruin.model.item.actions.impl.PlatinumToken;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.ArrayList;

import static io.ruin.cache.ItemID.*;
import static io.ruin.model.item.actions.impl.BloodyTokens.BLOODY_TOKENS;

public class BankActions {

    /**
     * Npcs
     */

    static {
        Server.afterData.add(() -> {
            ArrayList<Integer> bankerIds = new ArrayList<>();
            NPCDef.forEach(def -> {
                if(NPCAction.register(def.id, "bank", (p, n) -> p.getBank().open())) {
                    NPCAction.register(def.id, "talk-to", BankActions::talk);
                    NPCAction.register(def.id, "collect", (p, n) -> CollectionBox.open(p));
                    bankerIds.add(def.id);
                }
            });
            SpawnListener.forEach(npc -> {
                if(bankerIds.contains(npc.getId())) {
                    addWalkException(npc);
                    markTiles(npc);
                }
            });
        });
    }

    private static void talk(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "Good day, how may I help you?"),
                new OptionsDialogue(
                        new Option("I'd like to access my bank account, please.", () -> player.getBank().open()),
                        new Option("I'd like to check my PIN settings.", () -> player.getBankPin().openSettings()),
                        new Option("I'd like to collect items.", () -> CollectionBox.open(player)),
                        new Option("What is this place?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("What is this place?"),
                                    new NPCDialogue(npc, "This is a branch of the Bank of " + World.type.getWorldName() + ". We have branches in many towns."),
                                    new OptionsDialogue(
                                            new Option("And what do you do?", () -> {
                                                player.dialogue(
                                                        new PlayerDialogue("And what do you do?"),
                                                        new NPCDialogue(npc, "We will look after your items and money for you. Leave your valuables with us if you want to keep them safe.")
                                                );
                                            }),
                                            new Option("Didn't you used to be called the Bank of Varrock?", () -> {
                                                player.dialogue(
                                                        new PlayerDialogue("Didn't you used to be called the Bank of Varrock?"),
                                                        new NPCDialogue(npc, "Yes we did, but people kept on coming into our branches outside of Varrock and telling us that our signs were wrong. They acted as if we didn't know what town we were in or something.")
                                                );
                                            })
                                    )
                            );
                        })
                )
        );
    }

    private static void addWalkException(NPC npc) {
        if(npc.walkBounds != null)
            return;
        int deltaX = npc.spawnDirection.deltaX;
        int deltaY = npc.spawnDirection.deltaY;
        int x = npc.getAbsX() + deltaX;
        int y = npc.getAbsY() + deltaY;
        int z = npc.getHeight();
        GameObject obj = Tile.getObject(-1, x, y, z, 10, -1);
        if(obj == null)
            return;
        ObjectDef def = obj.getDef();
        if(def.xLength > 1 || def.yLength > 1)
            return;
        npc.walkTo = new Position(x + deltaX, y + deltaY, z);
    }

    /**
     * Objects
     */

    static {
        ObjectDef.forEach(objDef -> {
            if(objDef.hasOption("bank") || objDef.name.toLowerCase().contains("bank")) {
                if(ObjectAction.register(objDef.id, "use", (p, obj) -> p.getBank().open()) || ObjectAction.register(objDef.id, "bank", (p, obj) -> p.getBank().open())) {
                    objDef.bank = true;
                    ObjectAction.register(objDef.id, "collect", (p, obj) -> CollectionBox.open(p));
                    ItemObjectAction.register(objDef.id, (p, item, obj) -> itemOnBank(p, item));
                }
            }
            if(objDef.name.toLowerCase().contains("bank deposit box")
                    || objDef.id == 10661 // deposit chest in zul-andra
                    ) {
                if(ObjectAction.register(objDef.id, "deposit", (p, obj) -> p.getBank().openDepositBox()))
                    ItemObjectAction.register(objDef.id, (p, item, obj) -> itemOnDeposit(p, item));
            }
        });
    }

    private static void itemOnBank(Player player, Item item) {
        if (item.getId() == COINS_995 || item.getId() == PLATINUM_TOKEN) {
            PlatinumToken.exchange(player, item);
        } else if (item.getId() == BLOODY_TOKENS || item.getId() == BLOOD_MONEY) {
            BloodyTokens.exchange(player, item);
        } else {
            ItemDef def = item.getDef();
//            if (!def.isNote()) {
//                player.dialogue(new MessageDialogue("Use a banknote on the bank to convert it to an item."));
//                return;
//            }
            if (def.isNote()) {
                player.dialogue(new OptionsDialogue(
                        "Un-note the " + (item.getAmount() == 1 ? "banknote" : "banknotes") + "?",
                        new Option("Yes", () -> {
                            int freeSlots = player.getInventory().getFreeSlots();
                            if (item.getAmount() == 1)
                                freeSlots++;
                            int exchanged;
                            if (freeSlots >= item.getAmount()) {
                                item.remove();
                                player.getInventory().add(def.notedId, exchanged = item.getAmount());
                            } else {
                                item.remove(freeSlots);
                                player.getInventory().add(def.notedId, exchanged = freeSlots);
                            }
                            player.dialogue(new MessageDialogue("The bank exchanges your banknote for an item" + (exchanged == 1 ? "" : "s") + "."));
                        }),
                        new Option("No", player::closeDialogue)
                ));
            } else {
                if (def.notedId != -1) {
                    player.dialogue(new OptionsDialogue(
                            "Note the " + (item.getAmount() == 1 ? "item" : "items") + "?",
                            new Option("Yes", () -> {
                                int removed = item.remove(player.getInventory().getAmount(item.getId()));
                                player.getInventory().add(def.notedId, removed);
                                player.dialogue(new MessageDialogue("The bank exchanges your banknote for an item" + (removed == 1 ? "" : "s") + "."));
                            }),
                            new Option("No", player::closeDialogue)
                    ));
                } else {
                    player.sendMessage("This item cannot be noted.");
                }
            }
        }
    }

    private static void itemOnDeposit(Player player, Item item) {
        int count = item.count();
        if(count == 1) {
            quickDeposit(player, item, 1);
        } else if(count == 2) {
            player.dialogue(new OptionsDialogue(
                    "How many would you like to deposit?",
                    new Option("One", () -> quickDeposit(player, item, 1)),
                    new Option("Both", () -> quickDeposit(player, item, count))
            ));
        } else if(count <= 5) {
            player.dialogue(new OptionsDialogue(
                    "How many would you like to deposit?",
                    new Option("One", () -> quickDeposit(player, item, 1)),
                    new Option("X", () -> player.integerInput("Enter amount:", amt -> quickDeposit(player, item, amt))),
                    new Option("All", () -> quickDeposit(player, item, count))
            ));
        } else {
            player.dialogue(new OptionsDialogue(
                    "How many would you like to deposit?",
                    new Option("One", () -> quickDeposit(player, item, 1)),
                    new Option("Five", () -> quickDeposit(player, item, 5)),
                    new Option("X", () -> player.integerInput("Enter amount:", amt -> quickDeposit(player, item, amt))),
                    new Option("All", () -> quickDeposit(player, item, count))
            ));
        }
    }

    private static void quickDeposit(Player player, Item item, int amount) {
        if(player.getBank().deposit(item, amount, true) != 0)
            player.animate(834);
        player.closeDialogue();
    }

    /**
     * Marking
     */
    private static void markTiles(NPC npc) {
        markTiles(npc.getAbsX(), npc.getAbsY(), npc.getHeight());
    }

    public static void markTiles(GameObject obj) {
        //This will only mark tiles for objects in cache.
        //Custom objects will require custom exceptions!
        if(obj.getDef().bank)
            markTiles(obj.x, obj.y, obj.z);
    }

    public static void markTiles(int srcX, int srcY, int srcZ) {
        Tile srcTile = Tile.get(srcX, srcY, srcZ, true);
        int westMostX = srcX, eastMostX = srcX;
        int southMostY = srcY, northMostY = srcY;
        int distance, maxDistance = srcTile.roofExists ? 30 : 8;
        /*
         * West
         */
        distance = maxDistance;
        while(distance-- > 0) {
            Tile tile = Tile.get(westMostX - 1, srcY, srcZ, true);
            if(tile.roofExists != srcTile.roofExists)
                break;
            westMostX--;
        }
        /*
         * East
         */
        distance = maxDistance;
        while(distance-- > 0) {
            Tile tile = Tile.get(eastMostX + 1, srcY, srcZ, true);
            if(tile.roofExists != srcTile.roofExists)
                break;
            eastMostX++;
        }
        /*
         * South
         */
        distance = maxDistance;
        while(distance-- > 0) {
            Tile tile = Tile.get(srcX, southMostY - 1, srcZ, true);
            if(tile.roofExists != srcTile.roofExists)
                break;
            southMostY--;
        }
        /*
         * North
         */
        distance = maxDistance;
        while(distance-- > 0) {
            Tile tile = Tile.get(srcX, northMostY + 1, srcZ, true);
            if(tile.roofExists != srcTile.roofExists)
                break;
            northMostY++;
        }
        /*
         * Mark
         */
        for(int x = westMostX; x <= eastMostX; x++) {
            for(int y = southMostY; y <= northMostY; y++)
                Tile.get(x, y, srcZ, true).nearBank = true;
        }
    }

}
