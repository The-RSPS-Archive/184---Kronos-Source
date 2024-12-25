package io.ruin.model.map.object.actions.impl;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 3/2/2020
 */

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.magic.SpellBook;

public class OccultAltar {
    static {
        ObjectAction.register(29150, actions -> {
            actions[1] = (player, obj) -> switchBook(player, SpellBook.MODERN, true);
            actions[2] = (player, obj) -> switchBook(player, SpellBook.ANCIENT, true);
            actions[3] = (player, obj) -> switchBook(player, SpellBook.LUNAR, true);
            actions[4] = (player, obj) -> switchBook(player, SpellBook.ARCEUUS, true);
        });
    }

    public static void switchBook(Player player, SpellBook book, boolean altar) {
        if(book.isActive(player) && altar) {
            player.dialogue(new MessageDialogue("You're already using this spellbook."));
            return;
        }
        book.setActive(player);
        if(altar) {
            player.animate(645);
            player.sendMessage("You are now using the " + book.name + " spellbook.");
        }
    }
}
