package io.ruin.model.inter.dialogue.skill;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.dialogue.Dialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;

import java.util.function.BiConsumer;

public class SkillDialogue implements Dialogue {

    private static final SkillItem EMPTY = new SkillItem(-1).name("");

    /**
     * Handling
     */

    static {
        InterfaceHandler.register(Interface.MAKE_DIALOGUE, h -> {
            for (int i = 0; i < 10; i++) {
                int index = i;
                h.actions[14 + i] = (DefaultAction) (player, option, slot, itemId) -> {
                      select(player, index, slot);
                };
            }
            h.closedAction = (player, integer) -> {
                Config.CHATBOX_INTERFACE_USE_FULL_FRAME.set(player, 0);
                player.skillDialogue = null;
            };
        });
    }

    private static void select(Player player, int itemIndex, int itemAmount) {
        SkillDialogue d = player.skillDialogue;
        if(d == null)
            return;
        if(itemIndex < 0 || itemIndex >= d.items.length)
            return;
        player.lastMakeXAmount = itemAmount;
        if (d.getAction() != null) {
            d.getAction().accept(player, new Item(d.items[itemIndex].id, itemAmount));
        } else {
            d.items[itemIndex].select(player, itemAmount);
        }
    }

    /**
     * Opening
     */

    public static void cook(Player player, SkillItem... item) {
        player.dialogue(new SkillDialogue("How many would you like to cook?", item));
    }

    public static void make(Player player, BiConsumer<Player, Item> action, SkillItem... items) {
        player.dialogue(new SkillDialogue(items).setAction(action));
    }

    public static void make(Player player, SkillItem... items) {
        player.dialogue(new SkillDialogue(items));
    }

    public static void makeNoMakeXAllowed(Player player, SkillItem... items) {
        player.dialogue(new SkillDialogue(items).noMakeX());
    }

    private SkillItem getItem(int index) {
        if (index < 0)
            return null;
        if (index >= items.length)
            return EMPTY;
        return items[index];
    }

    /**
     * Separator
     */

    private SkillItem[] items;
    private String title;
    private boolean allowMakeX;
    private int maxAmount = 28;
    private BiConsumer<Player, Item> action;

    private SkillDialogue(String title, SkillItem... items) {
        this.title = title;
        this.items = items;
        allowMakeX = true;
    }

    private SkillDialogue(SkillItem... items) {
        this(items.length > 1 ? "What would you like to make?" : "How many would you like to make?", items);
    }

    private SkillDialogue noMakeX() {
        allowMakeX = false;
        return this;
    }

    private SkillDialogue maxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
        return this;
    }

    @Override
    public void open(Player player) {
        player.skillDialogue = this;
        player.openInterface(InterfaceType.CHATBOX, Interface.MAKE_DIALOGUE);
        StringBuilder sb = new StringBuilder(title);
        sb.append("|");
        for (int i = 0; i < 10; i++) {
            sb.append(getItem(i).name)
                    .append("|");
        }
        player.getPacketSender().sendClientScript(2046, "isiooooooooooi", allowMakeX ? 0 : 3,
                sb.toString(),
                maxAmount,
                getItem(0).id, getItem(1).id, getItem(2).id, getItem(3).id, getItem(4).id,
                getItem(5).id, getItem(6).id, getItem(7).id, getItem(8).id, getItem(9).id,
                player.lastMakeXAmount);
        for (int i = 0; i < items.length; i++) {
            player.getPacketSender().sendAccessMask(Interface.MAKE_DIALOGUE, 14 + i, 1, 8, 1);
        }
        Config.CHATBOX_INTERFACE_USE_FULL_FRAME.set(player, 1);
    }

    public SkillDialogue setAction(BiConsumer<Player, Item> selectedAction) {
        this.action = selectedAction;
        return this;
    }

    public BiConsumer<Player, Item> getAction() {
        return action;
    }
}