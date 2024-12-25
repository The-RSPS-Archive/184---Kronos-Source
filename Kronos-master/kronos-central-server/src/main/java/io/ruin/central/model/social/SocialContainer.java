package io.ruin.central.model.social;

import com.google.gson.annotations.Expose;

public class SocialContainer {

    @Expose protected int friendsCount;
    @Expose public SocialMember[] friends;
    @Expose protected int ignoresCount;
    @Expose public SocialMember[] ignores;

    public boolean addFriend(SocialMember friend) {
        if (this.friends == null) {
            this.friends = new SocialMember[400];
        }
        if (this.friendsCount >= this.friends.length || this.isFriend(friend.userId)) {
            return false;
        }
        this.friends[this.friendsCount++] = friend;
        return true;
    }

    public int deleteFriend(String name) {
        for (int index = 0; index < this.friendsCount; ++index) {
            SocialMember friend = this.friends[index];
            if (!name.equalsIgnoreCase(friend.name)) continue;
            --this.friendsCount;
            for (int i = index; i < this.friendsCount; ++i) {
                this.friends[i] = this.friends[i + 1];
            }
            this.friends[this.friendsCount] = null;
            return friend.userId;
        }
        return -1;
    }

    public SocialMember getFriend(String name) {
        if (this.friends == null) {
            return null;
        }
        for (SocialMember friend : this.friends) {
            if (friend == null || !name.equalsIgnoreCase(friend.name)) continue;
            return friend;
        }
        return null;
    }

    public SocialMember getFriend(int userId) {
        if (this.friends == null) {
            return null;
        }
        for (SocialMember friend : this.friends) {
            if (friend == null || friend.userId != userId) continue;
            return friend;
        }
        return null;
    }

    public boolean isFriend(int userId) {
        return this.getFriend(userId) != null;
    }

    public void addIgnore(SocialMember ignore) {
        if (this.ignores == null) {
            this.ignores = new SocialMember[400];
        }
        if (this.ignoresCount >= this.ignores.length || this.isIgnored(ignore.userId)) {
            return;
        }
        this.ignores[this.ignoresCount++] = ignore;
    }

    public int deleteIgnore(String name) {
        for (int index = 0; index < this.ignoresCount; ++index) {
            SocialMember ignore = this.ignores[index];
            if (!name.equalsIgnoreCase(ignore.name)) continue;
            --this.ignoresCount;
            for (int i = index; i < this.ignoresCount; ++i) {
                this.ignores[i] = this.ignores[i + 1];
            }
            this.ignores[this.ignoresCount] = null;
            return ignore.userId;
        }
        return -1;
    }

    public SocialMember getIgnore(int userId) {
        if (this.ignores == null) {
            return null;
        }
        for (SocialMember ignore : this.ignores) {
            if (ignore == null || ignore.userId != userId) continue;
            return ignore;
        }
        return null;
    }

    public boolean isIgnored(int userId) {
        return this.getIgnore(userId) != null;
    }
}

