package io.ruin.model.combat;

public enum HitType {

    BLOCKED(true),
    DAMAGE(true),
    POISON(false),
    DISEASE1(false),
    DISEASE2(false),
    VENOM(false),
    HEAL(false);

    public final boolean resetActions;

    HitType(boolean resetActions) {
        this.resetActions = resetActions;
    }

}