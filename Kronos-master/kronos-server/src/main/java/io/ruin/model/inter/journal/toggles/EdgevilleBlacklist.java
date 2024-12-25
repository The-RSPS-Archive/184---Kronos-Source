package io.ruin.model.inter.journal.toggles;

import com.google.gson.annotations.Expose;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.journal.JournalEntry;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;

public class EdgevilleBlacklist extends JournalEntry {

    static {
        LoginListener.register(EdgevilleBlacklist::check);
    }

    @Expose
    private String name;

    private static final Bounds EDGEVILLE_BOUNDS = new Bounds(2993, 3523, 3124, 3597, -1);
    public static final EdgevilleBlacklist[] ENTRIES = {
            new EdgevilleBlacklist(1),
            new EdgevilleBlacklist(2),
            new EdgevilleBlacklist(3)
    };

    private int id;

    public EdgevilleBlacklist(int id) {
        this.id = id;
    }

    public static void check(Player player) {
        if (player.edgevilleBlacklistedUsers.length < ENTRIES.length) {
            EdgevilleBlacklist[] custom = player.edgevilleBlacklistedUsers;
            player.edgevilleBlacklistedUsers = new EdgevilleBlacklist[ENTRIES.length];
            System.arraycopy(custom, 0, player.edgevilleBlacklistedUsers, 0, custom.length);
        }
        EdgevilleBlacklist[] edgevilleBlacklistedUsers = player.edgevilleBlacklistedUsers;
        for (int i = 0; i < edgevilleBlacklistedUsers.length; i++) {
            EdgevilleBlacklist b = edgevilleBlacklistedUsers[i];
            if (b != null && b.name == null)
                player.edgevilleBlacklistedUsers[i] = null;
        }
    }

    @Override
    public void send(Player player) {
        EdgevilleBlacklist blacklistedUser = player.edgevilleBlacklistedUsers[id - 1];
        if(blacklistedUser == null)
            sendEmpty(player);
        else
            sendCustom(player, blacklistedUser);
    }

    @Override
    public void select(Player player) {
        EdgevilleBlacklist blacklist = player.edgevilleBlacklistedUsers[id - 1];
        if(player.wildernessLevel > 0) {
            player.dialogue(new MessageDialogue("You can't change your blacklist settings inside the wilderness."));
            return;
        }
        if(blacklist == null) {
            player.dialogue(
                    new OptionsDialogue("Would you like to prevent a user from attacking you?",
                            new Option("Yes", () -> player.nameInput("Enter the player's display name:", name -> {
                                if (name.equalsIgnoreCase(player.getName())) {
                                    player.retryNameInput("...how are you going to attack yourself?");
                                    return;
                                }
                                Player target = World.getPlayer(name);
                                if (target == null) {
                                    player.retryNameInput("This player can not be found. Please try again.");
                                    return;
                                }
                                for(EdgevilleBlacklist blacklistedUsers : player.edgevilleBlacklistedUsers) {
                                    if(blacklistedUsers != null) {
                                        if(blacklistedUsers.name.equalsIgnoreCase(name)) {
                                            player.retryNameInput("You've already blacklisted this user. Please try again.");
                                            return;
                                        }
                                    }
                                }
                                EdgevilleBlacklist newBlacklistedUser = new EdgevilleBlacklist(id);
                                newBlacklistedUser.name = name;
                                player.edgevilleBlacklistedUsers[id - 1] = newBlacklistedUser;
                                player.dialogue(new MessageDialogue(name + " can no longer attack you inside Edgeville Wilderness."));
                                sendCustom(player, newBlacklistedUser);
                            })),
                            new Option("No")
                    )
            );
        } else {
            player.dialogue(
                    new OptionsDialogue(
                            "Blacklisted User: " + blacklist.name,
                            new Option("Change User", () -> player.nameInput("Enter the player's display name:", name -> {
                                if (name.equalsIgnoreCase(player.getName())) {
                                    player.retryNameInput("...how are you going to attack yourself?");
                                    return;
                                }
                                Player target = World.getPlayer(name);
                                if (target == null) {
                                    player.retryNameInput("This player can not be found. Please try again.");
                                    return;
                                }
                                for(EdgevilleBlacklist blacklistedUsers : player.edgevilleBlacklistedUsers) {
                                    if(blacklistedUsers != null) {
                                        if(blacklistedUsers.name.equalsIgnoreCase(name)) {
                                            player.retryNameInput("You've already blacklisted this user. Please try again.");
                                            return;
                                        }
                                    }
                                }
                                blacklist.name = name;
                                player.dialogue(new MessageDialogue("Player successfully changed."));
                                sendCustom(player, blacklist);
                            })),
                            new Option("Delete User", () -> player.dialogue(
                                    new OptionsDialogue("Remove this user?",
                                            new Option("Yes", () -> {
                                                player.edgevilleBlacklistedUsers[id - 1] = null;
                                                player.dialogue(new MessageDialogue("Player successfully removed from your Edgeville blacklist."));
                                                sendEmpty(player);
                                            }),
                                            new Option("No")
                                    )
                            ))
                    )
            );
        }
    }

    private void sendEmpty(Player player) {
        send(player, "Empty - Add User", Color.BRONZE);
    }

    private void sendCustom(Player player, EdgevilleBlacklist blacklist) {
        send(player, blacklist.name, Color.BRONZE);
    }

    public static boolean canAttack(Player player, Player pTarget) {
        if(player.getPosition().inBounds(EDGEVILLE_BOUNDS) || pTarget.getPosition().inBounds(EDGEVILLE_BOUNDS)) {
            for(EdgevilleBlacklist blacklistedUsers : player.edgevilleBlacklistedUsers) {
                if(blacklistedUsers != null) {
                    if(blacklistedUsers.name.equalsIgnoreCase(pTarget.getName())) {
                        player.sendMessage("Please remove this player from your blacklist before attempting to attack him.");
                        return false;
                    }
                }
            }
            for(EdgevilleBlacklist blacklistedUsers : pTarget.edgevilleBlacklistedUsers) {
                if(blacklistedUsers != null) {
                    if(blacklistedUsers.name.equalsIgnoreCase(player.getName())) {
                        player.sendMessage("You can't attack this player because they have you on their blacklist.");
                        return false;
                    }
                }
            }
        }

        return true;
    }
}