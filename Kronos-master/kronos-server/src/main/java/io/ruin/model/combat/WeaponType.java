package io.ruin.model.combat;

import com.google.gson.annotations.Expose;

public class WeaponType {

    public static WeaponType UNARMED;

    @Expose public int config;

    @Expose public int maxDistance;

    @Expose public int attackTicks;

    @Expose public int attackAnimation;

    @Expose public int defendAnimation;

    @Expose public int equipSound;

    @Expose public int attackSound;

    @Expose public AttackSet[] attackSets;

    @Expose public int[] renderAnimations;

}