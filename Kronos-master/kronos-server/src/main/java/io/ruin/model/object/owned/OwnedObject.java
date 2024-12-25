package io.ruin.model.object.owned;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import lombok.Getter;

import java.util.Optional;

/**
 * @author ReverendDread on 7/9/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Getter
public abstract class OwnedObject extends GameObject {

    //Unique identifer for this object.
    private final String identifier;
    //This objects owner uuid.
    private int ownerUID;

    public OwnedObject(Player owner, String identifier, int id, Position pos, int type, int direction) {
        super(id, pos, type, direction);
        this.ownerUID = owner.getUserId();
        this.identifier = identifier;
    }

    public OwnedObject(int ownerUID, String identifier, int id, Position pos, int type, int direction) {
        super(id, pos, type, direction);
        this.ownerUID = ownerUID;
        this.identifier = identifier;
    }

    public void destroy() {
        World.deregisterOwnedObject(this);
        this.remove();
    }

    public abstract void tick();

    public boolean isOwner(Player player) {
        return player.getUserId() == ownerUID;
    }

    public Player getOwner() {
        return World.getPlayer(ownerUID, true);
    }

    public Optional<Player> getOwnerOpt() {
        return World.getPlayerByUid(ownerUID);
    }

}


