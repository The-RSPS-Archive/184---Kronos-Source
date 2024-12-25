package io.ruin.model.inter.handlers;

import io.ruin.api.utils.Random;
import io.ruin.cache.AnimDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.containers.Equipment;
import org.apache.commons.lang3.RandomUtils;

public enum TabEmote {

    YES(0, 855),
    NO(1, 856),
    BOW(2, 858),
    ANGRY(3, 859),
    THINK(4, 857),
    WAVE(5, 863),
    SHRUG(6, 2113),
    CHEER(7, 862),
    BECKON(8, 864),
    LAUGH(9, 861),
    JUMP_FOR_JOY(10, 2109),
    YAWN(11, 2111),
    DANCE(12, 866),
    JIG(13, 2106),
    SPIN(14, 2107),
    HEAD_BANG(15, 2108),
    CRY(16, 860),
    BLOW_KISS(17, 1374),
    PANIC(18, 2105),
    RASPBERRY(19, 2110),
    CLAP(20, 865),
    SALUTE(21, 2112),
    GOBLIN_BOW(22, 2127),
    GOBLIN_SALUTE(23, 2128),
    GLASS_BOX(24, 1131),
    CLIMB_ROPE(25, 1130),
    LEAN(26, 1129),
    GLASS_WALL(27, 1128),
    IDEA(28, 4276),
    STOMP(29, 4278),
    FLAP(30, 4280),
    SLAP_HEAD(31, 4275),
    ZOMBIE_WALK(32, 3544),
    ZOMBIE_DANCE(33, 3543),
    SCARED(34, 2836),
    RABBIT_HOP(35, 6111),
    SIT_UP(36, 2763),
    PUSH_UP(37, 2762),
    STAR_JUMP(38, 2761),
    JOG(39, 2764),
    ZOMBIE_HAND(40, 1708, 320),
    HYPER_MOBILE_DRINKER(41, 7131),
    SKILL_CAPE(42, -1, -1),
    AIR_GUITAR(43, 4751, 1239),
    URI_TRANSFORM(44, -1, -1),
    SMOOTH_DANCE(45, 7533, -1),
    CRAZY_DANCE(46, -1, -1),
    PREMIER_SHIELD(47, 7751, 1412);

    public int slot, animationID, gfxId;

    TabEmote(int slot, int animationID) {
        this(slot, animationID, -1);
    }

    TabEmote(int slot, int animationId, int gfxId) {
        this.slot = slot;
        this.animationID = animationId;
        this.gfxId = gfxId;
    }

    static {
        InterfaceHandler.register(Interface.EMOTE, h -> h.actions[1] = (SlotAction) (p, slot) -> {
            TabEmote[] emotes = values();
            if(slot < 0 || slot >= emotes.length)
                return;
            TabEmote emote = emotes[slot];
            if(emote == null)
                return;
            if(p.emoteDelay.isDelayed())
                return;

            int emoteId = emote.animationID;
            if(slot == TabEmote.SKILL_CAPE.slot) {
                emote.animationID = skillCapeAnim(p);
                emote.gfxId = skillCapeGraphic(p);
                if(emote.animationID == -1) {
                    p.sendMessage("You need to be wearing a Skill Cape to perform this emote.");
                    return;
                }
                p.startEvent(event -> {
                    p.lock();
                    AnimDef def = AnimDef.get(emote.animationID);
                    if(def != null)
                        event.delay(def.getTickDelay());
                    p.unlock();
                });
            } else if (slot == TabEmote.DANCE.slot && p.getEquipment().getId(Equipment.SLOT_LEGS) == 10394) {
                emoteId = 5316;
            } else if (slot == TabEmote.CRAZY_DANCE.slot) {
                if (Random.rollDie(2))
                    emoteId = 7536;
                else
                    emoteId = 7537;
            }

            p.animate(emoteId);
            if(emote.gfxId != -1)
                p.graphics(emote.gfxId);

            if(p.yesDelay.isDelayed())
                return;
            if(emote == YES && !p.yesDelay.isDelayed()) {
                p.yesDelay.delay(3);
            }
            if(emote == NO && !p.noDelay.isDelayed()) {
                p.noDelay.delay(2);
            }

            AnimDef def = AnimDef.get(emote.animationID);
            if(def != null)
                p.emoteDelay.delay(def.getTickDelay());
            p.resetSteps();
        });

        LoginListener.register(player -> {
            //Unlock all the emotes
            Config.EMOTES.set(player, 16777215);
            Config.UNLOCK_GOBLIN_BOW_AND_SALUTE_EMOTE.set(player, 7);
            Config.UNLOCK_FLAP_EMOTE.set(player, 1);
            Config.UNLOCK_SLAP_HEAD_EMOTE.set(player, 1);
            Config.UNLOCK_IDEA_EMOTE.set(player, 1);
            Config.UNLOCK_STAMP_EMOTE.set(player, 1);
        });
    }

    private static int skillCapeAnim(Player player) {
        int capeId = player.getEquipment().getId(Equipment.SLOT_CAPE);
        if(capeId == 9747 || capeId == 9748) //Attack
            return 4959;
        if(capeId == 9753 || capeId == 9754) //Defence
            return 4961;
        if(capeId == 9750 || capeId == 9751) //Strength
            return 4981;
        if(capeId == 9768 || capeId == 9769) //Hitpoints
            return 4971;
        if(capeId == 9756 || capeId == 9757) //Ranged
            return 4973;
        if(capeId == 9762 || capeId == 9763) //Magic
            return 4939;
        if(capeId == 9759 || capeId == 9760) //Prayer
            return 4979;
        if(capeId == 9801 || capeId == 9802) //Cooking
            return 4955;
        if(capeId == 9807 || capeId == 9808) //Woodcutting
            return 4957;
        if(capeId == 9783 || capeId == 9784) //Fletching
            return 4937;
        if(capeId == 9798 || capeId == 9799) //Fishing
            return 4951;
        if(capeId == 9804 || capeId == 9805) //Firemaking
            return 4975;
        if(capeId == 9780 || capeId == 9781) //Crafting
            return 4949;
        if(capeId == 9795 || capeId == 9796) //Smithing
            return 4943;
        if(capeId == 9792 || capeId == 9793) //Mining
            return 4941;
        if(capeId == 9774 || capeId == 9775) //Herblore
            return 4969;
        if(capeId == 9771 || capeId == 9772) //Agility
            return 4977;
        if(capeId == 9777 || capeId == 9778) //Thieving
            return 4965;
        if(capeId == 9786 || capeId == 9787) //Slayer
            return 4967;
        if(capeId == 9810 || capeId == 9811) //Farming
            return 4963;
        if(capeId == 9765 || capeId == 9766) //Runecrafting
            return 4947;
        if(capeId == 9789 || capeId == 9790) //Construction
            return 4953;
        if(capeId == 9948 || capeId == 9949) //Hunter
            return 5158;
        if(capeId == 9813) //Quest
            return 4945;
        if(capeId == 13069) //Achievement
            return 7121;
        if(capeId == 13280 || capeId == 13329 || capeId == 13331 || capeId == 13333 || capeId == 13335
                || capeId == 13337 || capeId == 13342 || capeId == 20760 || capeId == 21776 || capeId == 21780
                || capeId == 21784 || capeId == 21898 || capeId == 21285 ) //Max cape
            return 7121;
        return -1;
    }

    private static int skillCapeGraphic(Player player) {
        int capeId = player.getEquipment().getId(Equipment.SLOT_CAPE);
        if(capeId == 9747 || capeId == 9748) //Attack
            return 823;
        if(capeId == 9753 || capeId == 9754) //Defence
            return 824;
        if(capeId == 9750 || capeId == 9751) //Strength
            return 828;
        if(capeId == 9768 || capeId == 9769) //Hitpoints
            return 833;
        if(capeId == 9756 || capeId == 9757) //Ranged
            return 832;
        if(capeId == 9762 || capeId == 9763) //Magic
            return 813;
        if(capeId == 9759 || capeId == 9760) //Prayer
            return 829;
        if(capeId == 9801 || capeId == 9802) //Cooking
            return 821;
        if(capeId == 9807 || capeId == 9808) //Woodcutting
            return 822;
        if(capeId == 9783 || capeId == 9784) //Fletching
            return 812;
        if(capeId == 9798 || capeId == 9799) //Fishing
            return 819;
        if(capeId == 9804 || capeId == 9805) //Firemaking
            return 831;
        if(capeId == 9780 || capeId == 9781) //Crafting
            return 818;
        if(capeId == 9795 || capeId == 9796) //Smithing
            return 815;
        if(capeId == 9792 || capeId == 9793) //Mining
            return 814;
        if(capeId == 9774 || capeId == 9775) //Herblore
            return 835;
        if(capeId == 9771 || capeId == 9772) //Agility
            return 830;
        if(capeId == 9777 || capeId == 9778) //Thieving
            return 826;
        if(capeId == 9786 || capeId == 9787) //Slayer
            return 827;
        if(capeId == 9810 || capeId == 9811) //Farming
            return 825;
        if(capeId == 9765 || capeId == 9766) //Runecrafting
            return 817;
        if(capeId == 9789 || capeId == 9790) //Construction
            return 820;
        if(capeId == 9948 || capeId == 9949) //Hunter
            return 907 ;
        if(capeId == 9813) //Quest
            return 816;
        if(capeId == 13069) //Achievement
            return 1286;
        if(capeId == 13280 || capeId == 13329 || capeId == 13331 || capeId == 13333 || capeId == 13335 ||
                capeId == 13337 || capeId == 13342 || capeId == 20760 || capeId == 21776 || capeId == 21780 ||
                capeId == 21784 || capeId == 21898 || capeId == 21285 ) //Max cape
            return 1286;
        return -1;
    }

}
