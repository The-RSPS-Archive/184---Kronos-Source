package io.ruin.model.entity.player;

import io.ruin.api.utils.StringUtils;
import io.ruin.api.utils.XenPost;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Descending order from highest priority group
 */
public enum PlayerGroup {
    OWNER(100, 100, 100, 100),
    DEVELOPER(3, 6, 16, 100),
    COMMUNITY_MANAGER(8, 7, 17, "Server Manager"),
    ADMINISTRATOR(5, 2, 1, 0),
    MODERATOR(4, 1, 0, 0),
    FORUM_MODERATOR(7, 0, 0, 0),
    SUPPORT(9, 8, 18, 0),
    YOUTUBER(10, 27, 28, 0),
    BETA_TESTER(6, 0, -1, 0),
    ZENYTE(17, 15, 25, 100),
    ONYX(16, 14, 24, 80),
    DRAGONSTONE(15, 13, 23, 60),
    DIAMOND(14, 12, 22, 40),
    RUBY(13, 11, 21, 25),
    EMERALD(12, 10, 20, 15),
    SAPPHIRE(11, 9, 19, 10),
    REGISTERED(2, 0, -1, 0),
    BANNED(18, 0, -1, 0);

    public final int id;

    public final int clientId;

    public final int clientImgId;

    public String title;
    @Getter
    public int doubleDropChance;

    PlayerGroup(int id, int clientId, int clientImgId, String title) {
        this.id = id;
        this.clientId = clientId;
        this.clientImgId = clientImgId;
        this.title = title;
    }
    PlayerGroup(int id, int clientId, int clientImgId, int doubleDropChance) {
        this.id = id;
        this.clientId = clientId;
        this.clientImgId = clientImgId;
        this.doubleDropChance = doubleDropChance;
        this.title = StringUtils.getFormattedEnumName(name());
    }

    PlayerGroup(int id, int clientId, int clientImgId) {
        this(id, clientId, clientImgId, "");
    }

    public void sync(Player player, String type) {
        sync(player, type, null);
    }

    public void sync(Player player, String type, Runnable successAction) {
        CompletableFuture.runAsync(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", player.getUserId());
            map.put("type", type);
            map.put("groupId", id);
            String result = XenPost.post("add_group", map);
            if(successAction != null && "1".equals(result))
                successAction.run();
        });
    }

    public void removePKMode(Player player, String type) {
        removePKMode(player, type, null);
    }

    public void removePKMode(Player player, String type, Runnable successAction) {
        CompletableFuture.runAsync(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", player.getUserId());
            map.put("type", type);
            String result = XenPost.post("remove_group", map);
            if(successAction != null && "1".equals(result))
                successAction.run();
        });
    }

    public String tag() {
        return "<img=" + clientImgId + ">";
    }

    public static final PlayerGroup[] GROUPS_BY_ID;

    static {
        int highestGroupId = 0;
        for(PlayerGroup group : values()) {
            if(group.id > highestGroupId)
                highestGroupId = group.id;
        }
        GROUPS_BY_ID = new PlayerGroup[highestGroupId + 1];
        for(PlayerGroup group : values())
            GROUPS_BY_ID[group.id] = group;
    }

}