package io.ruin.model.skills.construction.room.impl;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.ObjectDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Hotspot;
import io.ruin.model.skills.construction.RoomDefinition;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.ruin.model.skills.magic.rune.Rune.*;

public class PortalChamberRoom extends Room {

    public enum PortalDestination {
        VARROCK(25, new Bounds(3211, 3422, 3214, 3424, 0), new int[]{13615, 13622, 13629}, LAW.toItem(100), AIR.toItem(300), FIRE.toItem(100)),
        LUMBRIDGE(31, new Bounds(3221, 3218, 3224, 3219, 0), new int[]{13616, 13623, 13630}, LAW.toItem(100), EARTH.toItem(100), AIR.toItem(300)),
        FALADOR(37, new Bounds(2964, 3378, 2966, 3379, 0), new int[]{13617, 13624, 13631}, LAW.toItem(100), WATER.toItem(100), AIR.toItem(300)),
        CAMELOT(45, new Bounds(2756, 3746, 2759, 3480, 0), new int[]{13618, 13625, 13632}, LAW.toItem(100), AIR.toItem(500)),
        ARDOUGNE(51, new Bounds(2659, 3304, 2664, 3308, 0), new int[]{13619, 13626, 13633}, LAW.toItem(200), WATER.toItem(200)),
        WATCHTOWER(58, new Bounds(2546, 3112, 2547, 3113, 2), new int[]{13620, 13627, 13634}, LAW.toItem(200), EARTH.toItem(200)),
        SENNTISTEN(60, new Bounds(3349, 3345, 3346, 3348, 0), new int[]{29340, 29348, 29356}, SOUL.toItem(100), LAW.toItem(200)),
        MARIM(64, new Bounds(2784, 2785, 2785, 2786, 0), new int[]{29344, 29352, 29360}, LAW.toItem(200), FIRE.toItem(200), WATER.toItem(200), new Item(1963, 100)),
        KHARYRLL(66, new Bounds(3490, 3471, 3493, 3472, 0), new int[]{29338, 29346, 29354}, BLOOD.toItem(100), LAW.toItem(200)),
        LUNAR_ISLE(69, new Bounds(2112, 3915, 0, 1), new int[]{29339, 29347, 29355}, LAW.toItem(100), ASTRAL.toItem(200), EARTH.toItem(200)),
        KOUREND(69, new Bounds(1644, 3672, 1642, 3674, 0), new int[]{29345, 29353, 29361}, SOUL.toItem(200), LAW.toItem(200), FIRE.toItem(500), WATER.toItem(400)),
        WATERBIRTH_ISLAND(72, new Bounds(2546, 3758, 0, 1), new int[]{29342, 29350, 29358}, LAW.toItem(100), ASTRAL.toItem(200), WATER.toItem(100)),
        FISHING_GUILD(85, new Bounds(2611, 3392, 0, 1), new int[]{29343, 29351, 29359}, LAW.toItem(300), ASTRAL.toItem(300), WATER.toItem(1000)),
        ANNAKARL(90, new Bounds(3287, 3886, 3288, 3887, 0), new int[]{29341, 29349, 29357}, BLOOD.toItem(200), LAW.toItem(200)),

        GRAND_EXCHANGE(VARROCK, new Bounds(3163, 3463, 3166, 3466, 0)),
        SEERS_VILLAGE(CAMELOT, new Bounds(2725, 3485, 2726, 3486, 0)),
        YANILLE(WATCHTOWER, new Bounds(2605, 3093, 0, 1));

        int levelReq;
        Item[] runes;
        int[] portalIds; //teak, mahog, marble
        Predicate<Player> focusReq;
        String name;
        boolean hidden;
        Predicate<Player> alternateReq;
        PortalDestination alternate;
        public Bounds bounds;

        PortalDestination(int levelReq, Bounds bounds, int[] portalIds, Item... runes) {
            this.levelReq = levelReq;
            this.bounds = bounds;
            this.runes = runes;
            this.portalIds = portalIds;
            name = StringUtils.fixCaps(name().replace('_', ' '));
            this.focusReq = p -> true;
        }

        PortalDestination(PortalDestination other, Bounds bounds) {
            this(other.levelReq, bounds, other.portalIds, other.runes);
            other.alternate = this;
            other.alternateReq = p -> true;
            alternate = other;
            alternateReq = p -> true;
            hidden = true;
        }
    }

    @Expose
    private PortalDestination[] portalDestinations = new PortalDestination[3];

    @Override
    protected void onBuild() {
        for (int i = 0; i < portalDestinations.length; i++) { // render our selected portals
            renderPortal(i);
        }
        getHotspotObjects(Hotspot.TELEPORT_FOCUS).forEach(o -> ObjectAction.register(o, 1, (player, gameObject) -> openFocusSelection(player)));
        getHotspotObjects(Hotspot.TELEPORT_FOCUS).forEach(o -> ObjectAction.register(o, 2, (player, gameObject) -> openScrySelection(player)));

        getHotspotObjects(Hotspot.PORTAL_1).forEach(obj -> ObjectAction.register(obj, 1, (p, o) -> teleport(p, 0)));
        getHotspotObjects(Hotspot.PORTAL_2).forEach(obj -> ObjectAction.register(obj, 1, (p, o) -> teleport(p, 1)));
        getHotspotObjects(Hotspot.PORTAL_3).forEach(obj -> ObjectAction.register(obj, 1, (p, o) -> teleport(p, 2)));

        getHotspotObjects(Hotspot.PORTAL_1).forEach(obj -> ObjectAction.register(obj, 2, (p, o) -> toggle(p, 0)));
        getHotspotObjects(Hotspot.PORTAL_2).forEach(obj -> ObjectAction.register(obj, 2, (p, o) -> toggle(p, 1)));
        getHotspotObjects(Hotspot.PORTAL_3).forEach(obj -> ObjectAction.register(obj, 2, (p, o) -> toggle(p, 2)));
    }

    private void openScrySelection(Player player) {
        if (getBuilt(Hotspot.TELEPORT_FOCUS) != Buildable.SCRYING_POOL)
            return;
        player.dialogue(new OptionsDialogue("Choose an option",
                new Option("Portal 1: " + (portalDestinations[0] == null ? "Not focused" : portalDestinations[0].name), () -> scry(player, 0)),
                new Option("Portal 2: " + (portalDestinations[1] == null ? "Not focused" : portalDestinations[1].name), () -> scry(player, 1)),
                new Option("Portal 3: " + (portalDestinations[2] == null ? "Not focused" : portalDestinations[2].name), () -> scry(player, 2))
        ));
    }

    private void scry(Player player, int portalIndex) {
        PortalDestination dest = portalDestinations[portalIndex];
        if (dest == null) {
            player.dialogue(new MessageDialogue("That portal is not focused."));
            return;
        }
        //this makes me very nervous
        player.addEvent(event -> {
            Position returnPosition = player.getPosition().localPosition();
            player.getMovement().teleport(dest.bounds.randomX(), dest.bounds.randomY(), dest.bounds.z);
            player.setHidden(true);
            player.getAppearance().setNpcId(21); // invis
            player.openInterface(InterfaceType.SECONDARY_OVERLAY, Interface.SCRYING_POOL);
            player.lock();
            event.delay(1);
            player.dialogue(new MessageDialogue("You view " + dest.name + "...") {
                @Override
                public void closed(Player player) {
                    if (getHouse().getOwner() == player) {
                        player.house.buildAndEnter(player, returnPosition, false);
                    } else {
                        getHouse().enterOrPortal(player, returnPosition);
                    }
                }
            });
            event.waitForDialogue(player);
            player.setHidden(false);
            player.getAppearance().setNpcId(-1);
            player.unlock();
        });
    }

    private void teleport(Player p, int portalIndex) {
        PortalDestination dest = portalDestinations[portalIndex];
        if (dest == null) {
            p.sendMessage("Invalid portal?");
            return;
        }
        p.getMovement().teleport(dest.bounds.randomX(), dest.bounds.randomY(), dest.bounds.z);
    }

    private void toggle(Player p, int portalIndex) {
        PortalDestination dest = portalDestinations[portalIndex];
        if (dest == null) {
            p.sendMessage("Invalid portal?");
            return;
        }
        if (!p.isInOwnHouse()) {
            p.dialogue(new MessageDialogue("Only the house owner can do that."));
            return;
        }
        if (dest.alternate == null) {
            p.dialogue(new MessageDialogue("This portal doesn't have an alternate destination."));
            return;
        }
        if (!dest.alternateReq.test(p)) {
            return;
        }
        portalDestinations[portalIndex] = dest.alternate;
        p.dialogue(new MessageDialogue("Portal destination changed to " + portalDestinations[portalIndex].name + "."));
    }

    private void openFocusSelection(Player player) {
        Consumer<Integer> scroll = slot -> OptionScroll.open(player, "Choose a destination", true, Arrays.stream(PortalDestination.values())
                .filter(pd -> !pd.hidden)
                .map(pd -> new Option(pd.name, () -> focus(player, slot, pd)))
                .collect(Collectors.toList()));
        player.dialogue(new OptionsDialogue("Choose an option",
                new Option("Portal 1: " + (portalDestinations[0] == null ? "Not focused" : portalDestinations[0].name), () -> scroll.accept(0)),
                new Option("Portal 2: " + (portalDestinations[1] == null ? "Not focused" : portalDestinations[1].name), () -> scroll.accept(1)),
                new Option("Portal 3: " + (portalDestinations[2] == null ? "Not focused" : portalDestinations[2].name), () -> scroll.accept(2))
        ));
    }

    private void renderPortal(int i) {
        Buildable portalFrame = getBuilt(3 + i);
        if (portalDestinations[i] == null || portalFrame == null)
            return;
        int portalId = -1;
        if (portalFrame == Buildable.TEAK_PORTAL_FRAME)
            portalId = portalDestinations[i].portalIds[0];
        else if (portalFrame == Buildable.MAHOGANY_PORTAL_FRAME)
            portalId = portalDestinations[i].portalIds[1];
        else if (portalFrame == Buildable.MARBLE_PORTAL_FRAME)
            portalId = portalDestinations[i].portalIds[2];
        int finalPortalId = portalId;
        getHotspotObjects(RoomDefinition.PORTAL_CHAMBER.getHotspots()[3 + i]).forEach(obj -> obj.setId(finalPortalId));
    }

    @Override
    protected void onBuildableChanged(Player player, Hotspot hotspot, Buildable newBuildable) {
        if (newBuildable == null) {
            if (hotspot == Hotspot.PORTAL_1)
                portalDestinations[0] = null;
            else if (hotspot == Hotspot.PORTAL_2)
                portalDestinations[1] = null;
            else if (hotspot == Hotspot.PORTAL_3)
                portalDestinations[2] = null;
        }
    }

    private void focus(Player player, int index, PortalDestination dest) {
        if (getBuilt(3 + index) == null) {
            player.dialogue(new MessageDialogue("You need to build a portal frame there first."));
            return;
        }
        if (!player.getStats().check(StatType.Magic, dest.levelReq)) {
            player.dialogue(new MessageDialogue("You'll need a Magic level of at least " + dest.levelReq + " to direct a portal to " + dest.name + "."));
            return;
        }
        if (!player.getInventory().containsAll(true, dest.runes)) {
            StringBuilder items = new StringBuilder();
            for (int i = 0; i < dest.runes.length; i++) {
                items.append(dest.runes[i].getAmount()).append(" x ").append(dest.runes[i].getDef().name);
                if (i != dest.runes.length - 1)
                    items.append(", ");
            }
            player.dialogue(new MessageDialogue("You will need the following items to direct the portal to " + dest.name + ":<br>" + items));
            return;
        }
        if (!dest.focusReq.test(player)) {
            return;
        }
        portalDestinations[index] = dest;
        renderPortal(index);
        player.animate(722);
        player.dialogue(new MessageDialogue("The portal now leads to " + dest.name + "."));
        player.getInventory().removeAll(true, dest.runes);
        player.getStats().addXp(StatType.Magic, 100, true);
    }

    public static void find() {
        for (PortalDestination pd : PortalDestination.values()) {
            String nameSearch = pd.name().replace('_', ' ') + " Portal";
            int[] portals = new int[3];
            for (ObjectDef def : ObjectDef.LOADED.values()) {
                if (def == null || def.name == null || def.modelIds == null)
                    continue;
                if (def.name.equalsIgnoreCase(nameSearch)) {
                    if (def.modelIds[1] == 11240)
                        portals[0] = def.id;
                    else if (def.modelIds[1] == 11235)
                        portals[1] = def.id;
                    else if (def.modelIds[1] == 11237)
                        portals[2] = def.id;
                }
            }
            System.out.println(pd.name() + "(new int[]" + Arrays.toString(portals).replace('[', '{').replace(']', '}') + "),");
        }
    }

}
