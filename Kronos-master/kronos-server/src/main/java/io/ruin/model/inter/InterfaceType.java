package io.ruin.model.inter;

import io.ruin.model.entity.player.Player;

public enum InterfaceType {
    /**
     * Basic types
     */
    MAIN(
            new Component(548, 21),
            new Component(161, 13),
            new Component(164, 13),
            false
    ),
    INVENTORY(
            new Component(548, 64),
            new Component(161, 66),
            new Component(164, 66),
            false
    ),
    INVENTORY_OVERLAY(
            new Component(548, 64),
            new Component(161, 66),
            new Component(164, 66),
            true
    ),
    SOCIAL_TAB(
            new Component(548, 75),
            new Component(161, 77),
            new Component(164, 77),
            true
    ),
    CHATBOX(
            new Component(162, 561),
            false
    ),
    /**
     * Not sure how the following values effect the overlays, but they're the only difference in client..
     *   xAlignment = 0
     *   yAlignment = 0
     */
    WILDERNESS_OVERLAY( //unique type required for bounty hunter
            new Component(548, 15),
            new Component(161, 4),
            new Component(164, 4),
            true
    ),
    UNUSED_OVERLAY1(
            new Component(548, 17),
            new Component(161, 7),
            new Component(164, 7),
            true
    ),
    UNUSED_OVERLAY2(
            new Component(548, 18),
            new Component(161, 8),
            new Component(164, 8),
            true
    ),
    /**
     * Not sure how the following values effect the overlays, but they're the only difference in client..
     *   xAlignment = 1
     *   yAlignment = 1
     */
    SECONDARY_OVERLAY( //fading, castle wars game, snow falling
            new Component(548, 14),
            new Component(161, 3),
            new Component(164, 3),
            true
    ),
    PRIMARY_OVERLAY( //castle wars wait lobby, duel challenge area, gnomeball, puro puro imp view, mage training arena, lms lobby, tut
            new Component(548, 15),
            new Component(161, 4),
            new Component(164, 4),
            true
    ),
    TARGET_OVERLAY( //kinda guessing it uses this, doesn't really matter, as long as it has it's own type..
            new Component(548, 16),
            new Component(161, 5),
            new Component(164, 5),
            true
    ),

    POH_LOADING(
            new Component(548, 15),
            new Component(161, 3),
            new Component(164, 3),
            false
    ),

    WORLD_MAP(
            new Component(548, 22),
            new Component(161, 14),
            new Component(164, 14),
            true
    ),
    ;

    public final Component fixedComponent;

    public final Component resizedComponent;

    public final Component resizedStackedComponent;

    public final int overlaySetting;

    InterfaceType(Component sharedComponent, boolean overlay) {
        this(sharedComponent, sharedComponent, sharedComponent, overlay);
    }

    InterfaceType(Component fixedComponent, Component resizedComponent, Component resizedStackedComponent, boolean overlay) {
        this.fixedComponent = fixedComponent;
        this.resizedComponent = resizedComponent;
        this.resizedStackedComponent = resizedStackedComponent;
        this.overlaySetting = overlay ? 1 : 0;
    }

    public void open(Player player, int id) {
        Component component = getComponent(player);
        player.getPacketSender().sendInterface(id, component.parentId, component.childId, overlaySetting);
    }

    public void close(Player player) {
        Component component = getComponent(player);
        player.getPacketSender().removeInterface(component.parentId, component.childId);
    }

    private Component getComponent(Player player) {
        if(player.getGameFrameId() == 161)
            return resizedComponent;
        if(player.getGameFrameId() == 164)
            return resizedStackedComponent;
        return fixedComponent;
    }

    private static final class Component {

        final int parentId, childId;

        Component(int parentId, int childId) {
            this.parentId = parentId;
            this.childId = childId;
        }

    }

}