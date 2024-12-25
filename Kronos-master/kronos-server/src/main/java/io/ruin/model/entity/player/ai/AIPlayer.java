package io.ruin.model.entity.player.ai;

import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.utils.ListUtils;
import io.ruin.model.World;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.entity.player.ai.scripts.AIScript;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.stat.StatType;
import io.ruin.network.incoming.handlers.ActionButtonHandler;
import io.ruin.network.incoming.handlers.ObjectActionHandler;

public class AIPlayer extends Player {

    private String username;

    private AIScript current;

    public AIPlayer(String username, Position position) {
        this.username = username;
        this.position = position.copy();
    }

    public void init() {
        int index = World.players.add(this, 1);
        this.setIndex(index);
        LoginInfo info = new LoginInfo(ArtificialChannel.getSingleton(), username, "password", "", "", null, 0, false, 0, World.id, new int[4]);
        info.update(-1, info.name, "", ListUtils.toList(PlayerGroup.REGISTERED.id), 0);
        init(info);
        this.setName(username);
        this.lastRegion = position.getRegion();
        this.newPlayer = false;
        this.setOnline(true);
        this.start();

    }

    @Override
    public void checkLogout() {
        if(logoutStage == -1) {
            setOnline(false);
            finish();
            World.players.remove(getIndex());
        }
    }

    public void runScript(AIScript script) {
        System.out.println("Starting script.");
        current = script;
        startEvent(event -> {
            script.start();
            while(true) {
                boolean check = script.run();
                System.out.println("Running script..." + check);
                if (check) {
                    event.delay(script.getDelay());
                    script.finish();
                    break;
                }
                event.delay(script.getDelay());
            }

        });

    }

    public void setStat(StatType stat, int level) {
        getStats().set(stat, level);
    }

    public void max() {
        this.getStats().set(StatType.Attack, 99);
        this.getStats().set(StatType.Strength, 99);
        this.getStats().set(StatType.Defence, 99);
        this.getStats().set(StatType.Hitpoints, 99);
        this.getStats().set(StatType.Prayer, 99);
    }

    public void equipItem(int id, boolean equip) {
        if (equip) {
            Item item = player.getInventory().findItem(id);
            if (item != null)
                this.getEquipment().equip(item);
        } else {
            Item item = this.getEquipment().findItem(id);
            if (item != null)
                this.getEquipment().unequip(item);
        }
    }

    public void dropItem(int id) {
        Item item = player.getInventory().findItem(id);
        player.getInventory().remove(id, item.getAmount());
        GroundItem gi = new GroundItem(item);
        gi.owner(this).spawn(1);
    }

    public void move(Position to, boolean run) {
        player.getMovement().setCtrlRun(run);
        player.getRouteFinder().routeAbsolute(to.getX(), to.getY());
    }

    public void attack(Entity entity) {
        face(entity);
        getCombat().setTarget(entity);
    }

    public void handleDialogue(int option) {
        int hash = 219 << 16 | 1;
        ActionButtonHandler.handleAction(player, 1, hash, option + 1, -1, true);
    }

    public void handleInterfaceAction(int interId, int childId, int option, int slot, int itemId) {
        ActionButtonHandler.handleAction(this, option, (interId << 16 | childId), slot, itemId, false);
    }

    public void handleObjectInteraction(int option, int objectId, int objectX, int objectY, boolean run) {
        ObjectActionHandler.handleAction(this, option, objectId, objectX, objectY, run ? 1 : 0);
    }
}
