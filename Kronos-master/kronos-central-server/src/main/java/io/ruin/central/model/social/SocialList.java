package io.ruin.central.model.social;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.JsonUtils;
import io.ruin.api.utils.StringUtils;
import io.ruin.central.Server;
import io.ruin.central.model.Player;
import io.ruin.central.model.social.clan.ClanChat;
import io.ruin.central.utility.XenUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

public class SocialList extends SocialContainer {

    public int userId;
    private boolean sent;
    @Expose public int privacy;
    @Expose public ClanChat cc;
    private static final HashMap<Integer, SocialList> LOADED = new HashMap();
    private static final File social_folder = new File("_saved/social/");

    public void offline(Player player) {
        this.sent = false;
        this.cc.leave(player, true);
        SocialList.save(this);
    }

    public void process(Player player) {
        if (!this.sent) {
            this.sent = true;
            this.update(null);
            player.sendSocial(true, this.friends);
            player.sendSocial(false, this.ignores);
            player.sendPrivacy(this.privacy);
            this.cc.init(player);
            return;
        }
        this.update(player);
    }

    private void update(Player sendFor) {
        if (this.friends != null) {
            for (SocialMember friend : this.friends) {
                if (friend == null) continue;
                int updatedWorldId = 0;
                Player pFriend = Server.getPlayer(friend.userId);
                if (pFriend != null) {
                    boolean hidden;
                    friend.checkName(pFriend);
                    SocialList fList = SocialList.get(friend.userId);
                    boolean bl = hidden = fList.privacy == 2 || fList.privacy == 1 && !fList.isFriend(this.userId) || fList.isIgnored(this.userId);
                    if (!hidden) {
                        updatedWorldId = pFriend.world.id;
                    }
                }
                if (friend.worldId == updatedWorldId && !friend.newName) continue;
                friend.worldId = updatedWorldId;
                if (sendFor == null) continue;
                sendFor.sendSocial(true, friend);
            }
        }
        if (this.ignores != null) {
            for (SocialMember ignore : this.ignores) {
                if (ignore == null) continue;
                Player pIgnore = Server.getPlayer(ignore.userId);
                if (pIgnore != null) {
                    ignore.checkName(pIgnore);
                }
                if (ignore.worldId == 0 && !ignore.newName) continue;
                ignore.worldId = 0;
                if (sendFor == null) continue;
                sendFor.sendSocial(false, ignore);
            }
        }
    }

    private void add(Player player, String name, boolean ignore) {
        String type = ignore ? "ignore" : "friend";
        XenUser.forObj(name, user -> {
                    if (user == null) {
                        player.sendMessage("Unable to add " + type + " - social server offline.");
                        return;
                    }
                    if (user.name == null) {
                        player.sendMessage("Unable to add " + type + " - unknown player.");
                        return;
                    }
                    SocialMember member = new SocialMember(user, ignore ? null : SocialRank.FRIEND);
                    if (ignore) {
                        this.addIgnore(member);
                    } else if (this.addFriend(member) && this.cc.inClan(member.userId)) {
                        this.cc.update(false);
                    }
                }
        );
    }

    private void delete(String name) {
        int userId = this.deleteFriend(name);
        if (userId != -1 && this.cc.inClan(userId)) {
            this.cc.update(false);
        }
    }

    public static void handle(Player player, String name, int requestType) {
        if (requestType == 1) {
            player.socialList.add(player, name, false);
        } else if (requestType == 2) {
            player.socialList.delete(name);
        } else if (requestType == 3) {
            player.socialList.add(player, name, true);
        } else if (requestType == 4) {
            player.socialList.deleteIgnore(name);
        }
    }

    public static void sendPrivateMessage(Player player, int rankId, String name, String message) {
        Player receiver = Server.getPlayer(name);
        if (receiver == null) {
            return;
        }
        message = StringUtils.fixCaps(message);
        player.sendPM(name, message);
        receiver.sendReceivePM(player.name, rankId, message);
    }

    public static SocialList get(int userId) {
        SocialList loaded = LOADED.get(userId);
        if (loaded == null) {
            File file = new File(social_folder, userId + ".json");
            if (file.exists()) {
                try {
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    String json = new String(bytes);
                    loaded = (SocialList) JsonUtils.GSON_EXPOSE.fromJson(json, SocialList.class);
                } catch (Exception e) {
                    Server.logError(e.getMessage());
                }
            }
            if (loaded == null) {
                loaded = new SocialList();
            }
        }
        if (loaded.cc == null) {
            loaded.cc = new ClanChat();
        }
        loaded.cc.parent = loaded;
        loaded.userId = userId;
        LOADED.put(loaded.userId, loaded);
        return loaded;
    }

    private static void save(SocialList list) {
        try {
            String json = JsonUtils.GSON_EXPOSE.toJson(list);
            if(!social_folder.exists() && !social_folder.mkdirs())
                throw new IOException("social directory could not be created!");
            Files.write(new File(social_folder, list.userId + ".json").toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (Exception e) {
            Server.logError(e.getMessage());
        }
    }
}

