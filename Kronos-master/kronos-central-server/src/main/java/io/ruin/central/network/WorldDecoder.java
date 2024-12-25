package io.ruin.central.network;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.netty.MessageDecoder;
import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.utils.IPBans;
import io.ruin.api.utils.MACBan;
import io.ruin.api.utils.UUIDBan;
import io.ruin.central.Server;
import io.ruin.central.model.Player;
import io.ruin.central.model.social.SocialList;
import io.ruin.central.model.social.SocialMember;
import io.ruin.central.model.social.SocialRank;
import io.ruin.central.model.social.clan.ClanChat;
import io.ruin.central.model.world.World;
import io.ruin.central.model.world.WorldLogin;

public class WorldDecoder extends MessageDecoder<World> {
    public long lastMessageAt = System.currentTimeMillis();

    public WorldDecoder() {
        super(null, true);
    }

    @Override
    public void handle(World world, InBuffer in, int opcode) {
        this.lastMessageAt = System.currentTimeMillis();
        if (opcode == 0) {
            return;
        }
        if (opcode == 1) {
            String ip = in.readString();
            String name = in.readString();
            String password = in.readString();
            String email = in.readString();
            String macAddress = in.readString();
            String uuid = in.readString();
            int tfaCode = in.readMedium();
            boolean tfaTrust = in.readByte() == 1;
            int tfaTrustKey = in.readUnsignedByte();
            new WorldLogin(world, new LoginInfo(ip, name, password, email, macAddress, uuid, tfaCode, tfaTrust, tfaTrustKey));
            return;
        }
        if (opcode == 2) {
            int userId = in.readInt();
            world.removePlayer(userId);
            return;
        }
        if (opcode == 3) {
            int userId = in.readInt();
            int attempt = in.readUnsignedByte();
            String json = in.readString();
            if (!Player.save(userId, world, json)) {
                return;
            }
            if (attempt != -1) {
                world.sendSaveResponse(userId, attempt);
            }
            return;
        }
        if (opcode == 4) {
            int userId = in.readInt();
            String message = in.readString();
            if (userId == -1) {
                for (World w : Server.worlds) {
                    for (Player p : w.players) {
                        p.sendMessage(message);
                    }
                }
            } else {
                Player player = Server.getPlayer(userId);
                if (player == null) {
                    return;
                }
                player.sendMessage(message);
                for (World w : Server.worlds) {
                    for (Player p : w.players) {
                        if (!p.socialList.isFriend(userId)) continue;
                        p.sendMessage(message);
                    }
                }
            }
            return;
        }
        if (opcode == 5) {
            int userId = in.readInt();
            byte privacy = in.readByte();
            Player player = Server.getPlayer(userId);
            if (player == null) {
                return;
            }
            player.socialList.privacy = privacy;
            return;
        }
        if (opcode == 6) {
            int userId = in.readInt();
            String username = in.readString();
            byte requestType = in.readByte();
            Player player = Server.getPlayer(userId);
            if (player == null) {
                return;
            }
            SocialList.handle(player, username, requestType);
            return;
        }
        if (opcode == 7) {
            int senderId = in.readInt();
            byte rankId = in.readByte();
            String username = in.readString();
            String message = in.readString();
            Player sender = Server.getPlayer(senderId);
            if (sender == null) {
                return;
            }
            SocialList.sendPrivateMessage(sender, rankId, username, message);
            return;
        }
        if (opcode == 8) {
            int userId = in.readInt();
            String name = in.readString();
            Player player = Server.getPlayer(userId);
            if (player == null) {
                return;
            }
            ClanChat cc = player.getClanChat();
            if (name.isEmpty()) {
                cc.name = null;
                cc.disable();
            } else {
                cc.name = name;
                cc.update(true);
            }
            return;
        }
        if (opcode == 9) {
            int userId = in.readInt();
            byte settingId = in.readByte();
            byte value = in.readByte();
            Player player = Server.getPlayer(userId);
            if (player == null) {
                return;
            }
            ClanChat cc = player.getClanChat();
            if (settingId == 0) {
                cc.enterRank = SocialRank.get(value, null);
            } else if (settingId == 1) {
                cc.talkRank = SocialRank.get(value, null);
            } else {
                cc.kickRank = SocialRank.get(value, SocialRank.CORPORAL);
                cc.update(true);
            }
            return;
        }
        if (opcode == 10) {
            int userId = in.readInt();
            String friendName = in.readString();
            SocialRank rank = SocialRank.get(in.readByte(), null);
            if (rank == null) {
                return;
            }
            Player player = Server.getPlayer(userId);
            if (player == null) {
                return;
            }
            SocialMember friend = player.socialList.getFriend(friendName);
            if (friend == null || friend.rank == rank) {
                return;
            }
            friend.rank = rank;
            friend.resend();
            ClanChat cc = player.getClanChat();
            if (cc.inClan(friend.userId)) {
                cc.update(false);
            }
            return;
        }
        if (opcode == 11) {
            int userId = in.readInt();
            String ownerName = in.readString();
            Player player = Server.getPlayer(userId);
            if (player == null) {
                return;
            }
            if (ownerName.isEmpty()) {
                player.getClanChat().leave(player, false);
            } else {
                player.getClanChat().join(player, ownerName);
            }
            return;
        }
        if (opcode == 12) {
            int userId = in.readInt();
            String kickName = in.readString();
            Player player = Server.getPlayer(userId);
            if (player == null) {
                return;
            }
            ClanChat active = player.getActiveClanChat();
            if (active == null) {
                return;
            }
            active.kick(player, kickName);
            return;
        }
        if (opcode == 13) {
            int userId = in.readInt();
            byte rankId = in.readByte();
            String message = in.readString();
            Player player = Server.getPlayer(userId);
            if (player == null) {
                return;
            }
            ClanChat active = player.getActiveClanChat();
            if (active == null) {
                return;
            }
            active.message(player, rankId, message);
            return;
        }
        if (opcode == 14) {
            IPBans.refreshBans();
            MACBan.refreshBans();
            UUIDBan.refreshBans();
            return;
        }
        if (opcode == 15) {
            String uuid = in.readString();
            UUIDBan.requestBan(uuid);
            return;
        }
        if (opcode == 16) {
            String ip = in.readString();
            int userId = in.readInt();
            Player player = Server.getPlayer(userId);
            if (player == null) {
                return;
            }
            IPBans.requestBan(player.name, ip);
            return;
        }
        if (opcode == 17) {
            String mac = in.readString();
            int userId = in.readInt();
            Player player = Server.getPlayer(userId);
            if (player == null) {
                return;
            }
            MACBan.requestBan(player.name, mac);
            return;
        }
    }

    @Override
    public int getSize(int opcode) {
        switch (opcode) {
            case 0:
            case 14: {
                return 0;
            }
            case 1: {
                return -1;
            }
            case 2: {
                return 4;
            }
            case 3: {
                return -4;
            }
            case 4: {
                return -1;
            }
            case 5: {
                return 5;
            }
            case 6: {
                return -1;
            }
            case 7: {
                return -1;
            }
            case 8: {
                return -1;
            }
            case 9: {
                return 6;
            }
            case 10: {
                return -1;
            }
            case 11: {
                return -1;
            }
            case 12: {
                return -1;
            }
            case 13: {
                return -1;
            }
            case 15: {
                return -1;
            }
            case 16: {
                return -1;
            }
            case 17: {
                return -1;
            }
        }
        return -128;
    }
}

