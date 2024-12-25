package io.ruin.model.inter.dialogue;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.inter.utils.Unlock;

import java.util.List;

public class OptionsDialogue implements Dialogue {

    private final Object[] params;

    private final Option[] options;

    private boolean resend;

    public OptionsDialogue(List<Option> options) {
        this(options.toArray(new Option[options.size()]));
    }

    public OptionsDialogue(Option... options) {
        this("Select an Option", options);
    }

    public OptionsDialogue(String title, List<Option> options) {
        this(title, options.toArray(new Option[options.size()]));
    }

    public OptionsDialogue(String title, Option... options) {
        params = new Object[]{title, ""};
        for(int i = 0; i < options.length; i++) {
            params[1] += options[i].name;
            if(i != options.length - 1)
                params[1] += "|";
        }
        this.options = options;
    }

    @Override
    public void open(Player player) {
        player.optionsDialogue = this;
        player.openInterface(InterfaceType.CHATBOX, Interface.OPTIONS_DIALOGUE);
        player.getPacketSender().setAlignment(Interface.OPTIONS_DIALOGUE, 1, 0, -10);
        player.getPacketSender().sendClientScript(58, "ss", params);
        new Unlock(Interface.OPTIONS_DIALOGUE, 1).children(1, 5).unlockSingle(player);
    }

    public void resend() {
        resend = true;
    }

    private void handle(Player player, int slot) {
        int index = slot - 1;
        if(index < 0 || index >= options.length) {
            player.closeDialogue();
            return;
        }
        options[index].select(player);
        if(resend) {
            resend = false;
            open(player);
        } else if(player.lastDialogue == this) {
            /* dialogue was not continued */
            player.closeDialogue();
        }
    }

    static {
        InterfaceHandler.register(Interface.OPTIONS_DIALOGUE, h -> {
            h.actions[1] = (SlotAction) (p, slot) -> {
                if (p.optionsDialogue != null) {
                    p.optionsDialogue.handle(p, slot);
                }
            };
        });
    }

}