package io.ruin.central.model.social;

import com.google.gson.annotations.Expose;
import io.ruin.central.model.Player;
import io.ruin.central.utility.XenUser;

import java.util.Objects;

public class SocialMember {

    @Expose public final int userId;
    @Expose public String name;
    @Expose public String lastName;
    protected boolean newName;
    @Expose public SocialRank rank;
    public int worldId = -1;

    public SocialMember(XenUser user, SocialRank rank) {
        this.userId = user.id;
        this.name = user.name;
        this.lastName = (user.lastName == null || user.lastName.equalsIgnoreCase("null")) ? "" : user.lastName;
        this.rank = rank;
    }

    public SocialMember(int userId, String name, int worldId) {
        this.userId = userId;
        this.name = name;
        this.lastName = "";
        this.worldId = worldId;
    }

    public void resend() {
        this.worldId = -1;
    }

    protected void checkName(Player player) {
        if (!Objects.equals(this.name, player.name)) {
            System.err.print(this.name);
            this.lastName = this.name;
            this.name = player.name;
            this.newName = true;
        }
    }

    public boolean sendNewName() {
        if (this.newName) {
            this.newName = false;
            this.resend();
            return true;
        }
        return false;
    }
}

