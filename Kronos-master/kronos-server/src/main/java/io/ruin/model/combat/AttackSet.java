package io.ruin.model.combat;

import com.google.gson.annotations.Expose;

public class AttackSet {

    @Expose public int child;

    @Expose public AttackType type;

    @Expose public AttackStyle style;

    @Expose public Integer attackAnimation;

    @Expose public Integer attackSound;

}
