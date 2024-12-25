package io.ruin.central.model.social.clan;

import com.google.gson.annotations.Expose;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.filestore.utility.Huffman;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import io.ruin.central.Server;
import io.ruin.central.model.Player;
import io.ruin.central.model.social.SocialList;
import io.ruin.central.model.social.SocialMember;
import io.ruin.central.model.social.SocialRank;
import io.ruin.central.utility.XenUser;

import java.util.HashMap;

public class ClanChat extends ClanContainer {

    public SocialList parent;
    private String owner;
    @Expose private int lastId = -1;
    @Expose public String name;
    @Expose public SocialRank enterRank = null;
    @Expose public SocialRank talkRank = null;
    @Expose public SocialRank kickRank = SocialRank.CORPORAL;
    public ClanChat active;
    private boolean joining;
    private HashMap<Integer, Long> kicked;

    public void init(Player player) {
        this.sendSettings(player);
        if (this.lastId != -1) {
            this.join(player, this.lastId);
        }
    }

    private void sendSettings(Player player) {
        String name = this.name == null ? "Chat disabled" : this.name;
        int enterRank = this.enterRank == null ? -1 : this.enterRank.id;
        int talkRank = this.talkRank == null ? -1 : this.talkRank.id;
        int kickRank = this.kickRank.id;
        OutBuffer out = new OutBuffer(2 + (name.length() + 1) + 3).sendVarBytePacket(86).addString(name).addByte(enterRank).addByte(talkRank).addByte(kickRank);
        player.write(out);
    }

    public void update(boolean settingsOnly) {
        if (this.ccMembersCount == 0) {
            return;
        }
        OutBuffer out = settingsOnly ? this.getSettingsBuffer() : this.getChannelBuffer();
        for (int i = 0; i < this.ccMembersCount; ++i) {
            SocialMember member = this.ccMembers[i];
            Player pMember = Server.getPlayer(member.userId);
            if (pMember == null) continue;
            pMember.write(out);
        }
    }

    public void disable() {
        for (int i = 0; i < this.ccMembersCount; ++i) {
            SocialMember member = this.ccMembers[i];
            Player pMember = Server.getPlayer(member.userId);
            if (pMember == null) continue;
            pMember.getClanChat().setActive(pMember, null);
            pMember.sendMessage("The clan chat channel you were in has been disabled.", 11);
        }
        if (this.kicked != null) {
            this.kicked.clear();
            this.kicked = null;
        }
    }

    public boolean isDisabled() {
        return this.name == null;
    }

    private void setActive(Player player, ClanChat newActive) {
        if (this.active == newActive) {
            return;
        }
        if (newActive == null) {
            this.active.removeClanMember(player.name);
            player.write(this.active.getLeaveBuffer());
            if (!this.active.isDisabled()) {
                this.active.update(false);
            }
        } else {
            this.lastId = newActive.parent.userId;
            newActive.update(false);
        }
        this.active = newActive;
    }

    public void join(Player player, Object search) {
        if (this.joining) {
            player.sendMessage("You are already joining a channel, please wait...", 11);
            return;
        }
        player.sendMessage("Attempting to join channel...", 11);
        XenUser.forObj(search, user -> {
            this.join(player, user);
            this.joining = false;
        });
    }

    private void join(Player player, XenUser user) {
        if (user == null) {
            player.sendMessage("Unable to join channel - social server offline.", 11);
            return;
        }
        if (user.name == null) {
            player.sendMessage("The channel you tried to join does not exist.", 11);
            return;
        }
        if (this.active != null) {
            player.sendMessage("You are already in a channel!", 11);
            return;
        }
        SocialList ownerList = SocialList.get(user.id);
        if (ownerList.isIgnored(player.userId)) {
            player.sendMessage("You are blocked from joining this channel.");
            return;
        }
        ClanChat joinCc = ownerList.cc;
        joinCc.owner = user.name;
        if (joinCc.addMember(player)) {
            this.setActive(player, joinCc);
            player.sendMessage("Now talking in clan chat channel " + joinCc.name + ".", 11);
            player.sendMessage("To talk, start each line of chat with the / symbol.", 11);
        }
    }

    public void leave(Player player, boolean logout) {
        if (this.active == null) {
            return;
        }
        this.lastId = logout ? this.active.parent.userId : -1;
        this.setActive(player, null);
    }

    public void kick(Player kickedBy, String kickName) {
        if (this.isDisabled()) {
            return;
        }
        SocialRank kickerRank = this.getRank(kickedBy);
        if (!ClanChat.meetsRank(this.kickRank, kickerRank)) {
            kickedBy.sendMessage("You are not a high enough rank to kick from this channel.", 11);
            return;
        }
        Player toKick = Server.getPlayer(kickName);
        if (toKick == null || toKick.getActiveClanChat() != this) {
            kickedBy.sendMessage(kickName + " is not active in this channel.", 11);
            return;
        }
        SocialRank toKickRank = this.getRank(toKick);
        if (toKickRank == SocialRank.OWNER) {
            kickedBy.sendMessage("You can't kick this channel's owner!", 11);
            return;
        }
        if (!ClanChat.meetsRank(toKickRank, kickerRank)) {
            kickedBy.sendMessage("You are not a high enough rank to kick this member.", 11);
            return;
        }
        if (this.kicked == null) {
            this.kicked = new HashMap();
        }
        this.kicked.put(toKick.userId, System.currentTimeMillis() + 3600000L);
        toKick.getClanChat().leave(toKick, false);
        toKick.sendMessage("You have been kicked from the channel.", 11);
    }

    private boolean addMember(Player pMember) {
        Long kickedUntil;
        if (this.isDisabled()) {
            pMember.sendMessage("The channel you tried to join is currently disabled.", 11);
            return false;
        }
        if (this.kicked != null && (kickedUntil = this.kicked.get(pMember.userId)) != null) {
            if (kickedUntil > System.currentTimeMillis()) {
                pMember.sendMessage("You cannot join this channel because you are currently banned from it.", 11);
                return false;
            }
            this.kicked.remove(pMember.userId);
        }
        if (!ClanChat.meetsRank(this.enterRank, this.getRank(pMember))) {
            pMember.sendMessage("You are not a high enough rank to enter this channel.", 11);
            return false;
        }
        if (!this.addClanMember(new SocialMember(pMember.userId, pMember.name, pMember.world.id))) {
            pMember.sendMessage("The channel you tried to join is full.", 11);
            return false;
        }
        return true;
    }

    public void message(Player sender, int rankId, String message) {
        if (this.isDisabled()) {
            return;
        }
        SocialRank senderRank = this.getRank(sender);
        if (!ClanChat.meetsRank(this.talkRank, senderRank)) {
            sender.sendMessage("You are not a high enough rank to talk in this channel.", 11);
            return;
        }
        message = StringUtils.fixCaps(message);
        for (int i = 0; i < this.ccMembersCount; ++i) {
            SocialMember member = this.ccMembers[i];
            Player pMember = Server.getPlayer(member.userId);
            if (pMember == null) continue;
            pMember.write(this.getMessageBuffer(sender.name, rankId, message));
        }
    }

    private OutBuffer getMessageBuffer(String senderName, int rankId, String message) {
        OutBuffer out = new OutBuffer(255).sendVarBytePacket(22);
        out.addString(senderName);
        out.addString(this.name);
        for (int i = 0; i < 5; ++i) {
            out.addByte(Random.get(255));
        }
        out.addByte(rankId);
        Huffman.encrypt(out, message);
        return out;
    }

    private OutBuffer getBuffer(int type) {
        if (type == 0) {
            return new OutBuffer(3).sendVarShortPacket(48);
        }
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(48).
                addString(this.owner).
                addString(this.name).
                addByte(ClanChat.getRankId(this.kickRank));
        if (type == 2) {
            out.addByte(255);
            return out;
        }
        out.addByte(this.ccMembersCount);
        for (int i = 0; i < this.ccMembersCount; ++i) {
            SocialMember member = this.ccMembers[i];
            out.addString(member.name);
            out.addShort(member.worldId);
            out.addByte(this.getRankId(member.userId));
            out.addByte(0);
        }
        return out;
    }

    private OutBuffer getLeaveBuffer() {
        return this.getBuffer(0);
    }

    private OutBuffer getChannelBuffer() {
        return this.getBuffer(1);
    }

    private OutBuffer getSettingsBuffer() {
        return this.getBuffer(2);
    }

    private SocialRank getRank(Player player) {
        if (player.userId == this.parent.userId) {
            return SocialRank.OWNER;
        }
        if (player.admin) {
            return SocialRank.ADMIN;
        }
        SocialMember friend = this.parent.getFriend(player.userId);
        return friend == null ? null : friend.rank;
    }

    private int getRankId(int userId) {
        if (userId == this.parent.userId) {
            return SocialRank.OWNER.id;
        }
        Player player = Server.getPlayer(userId);
        if (player != null && player.admin) {
            return SocialRank.ADMIN.id;
        }
        SocialMember friend = this.parent.getFriend(userId);
        return friend == null ? -1 : friend.rank.id;
    }

    private static int getRankId(SocialRank rank) {
        return rank == null ? -1 : rank.id;
    }

    private static boolean meetsRank(SocialRank reqRank, SocialRank checkRank) {
        return reqRank == null || checkRank != null && checkRank.id >= reqRank.id;
    }
}

