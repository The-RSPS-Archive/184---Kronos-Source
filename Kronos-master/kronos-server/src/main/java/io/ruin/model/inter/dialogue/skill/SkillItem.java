package io.ruin.model.inter.dialogue.skill;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.process.event.Event;
import kilim.Pausable;

import java.util.function.Predicate;

public class SkillItem {

    protected int id;

    protected String name;
    private Predicate<Player> req;

    private SkillItemConsumer<Player, Integer, Event> action;

    public SkillItem(int id) {
        this.id = id;
        ItemDef def = ItemDef.get(id);
        if (def != null)
            this.name = def.name;
        else
            this.name = "null";
    }

    public SkillItem addReq(Predicate<Player> req) {
        this.req = req;
        return this;
    }

    public SkillItem addAction(SkillItemConsumer<Player, Integer, Event> action) {
        this.action = action;
        return this;
    }

    public SkillItem name(String name) {
        this.name = name;
        return this;
    }

    protected void select(Player player, int amount) {
        player.closeDialogue();
        if(req != null && !req.test(player))
            return;
        player.startEvent(event -> action.accept(player, amount, event));
    }

    @FunctionalInterface
    public interface SkillItemConsumer<A, B, C> {
        void accept(A a, B b, C c) throws Pausable;
    }

}