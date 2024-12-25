package io.ruin.model.skills.magic.spells.arceuus;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

public class Reanimate extends Spell {

    public enum Monster {
        GOBLIN(new int[]{13447, 13448}, "Ensouled goblin head", 3, 6.0, 130.0, 7018, Rune.BODY.toItem(2), Rune.NATURE.toItem(1)),
        MONKEY(new int[]{13450, 13451}, "Ensouled monkey head", 7, 14.0, 182.0, 7019, Rune.BODY.toItem(3), Rune.NATURE.toItem(1)),
        IMP(new int[]{13453, 13454}, "Ensouled imp head", 12, 24.0, 286.0, 7020, Rune.BODY.toItem(3), Rune.NATURE.toItem(2)),
        MINOTAUR(new int[]{13453, 13454}, "Ensouled minotaur head", 16, 32.0, 364.0, 7021, Rune.BODY.toItem(4), Rune.NATURE.toItem(2)),
        SCORPION(new int[]{13459, 13460}, "Ensouled scorpion head", 19, 38.0, 454.0, 7022, Rune.SOUL.toItem(1), Rune.NATURE.toItem(1)),
        BEAR(new int[]{13462, 13463}, "Ensouled bear head", 21, 42.0, 480.0, 7023, Rune.SOUL.toItem(1), Rune.NATURE.toItem(1), Rune.BODY.toItem(1)),
        UNICORN(new int[]{13465, 13466}, "Ensouled unicorn head", 22, 44.0, 494.0, 7024, Rune.SOUL.toItem(1), Rune.NATURE.toItem(1), Rune.BODY.toItem(2)),
        DOG(new int[]{13468, 13469}, "Ensouled dog head", 26, 52.0, 520.0, 7025, Rune.SOUL.toItem(1), Rune.NATURE.toItem(2), Rune.BODY.toItem(2)),
        CHAOS_DRUID(new int[]{13471, 13472}, "Ensouled chaos druid head", 30, 60.0, 584.0, 7026, Rune.SOUL.toItem(1), Rune.NATURE.toItem(2), Rune.BODY.toItem(3)),
        GIANT(new int[]{13474, 13475}, "Ensouled giant head", 37, 74.0, 650.0, 7027, Rune.SOUL.toItem(1), Rune.NATURE.toItem(2), Rune.BODY.toItem(4)),
        OGRE(new int[]{13477, 13478}, "Ensouled ogre head", 40, 80.0, 716.0, 7028, Rune.SOUL.toItem(1), Rune.NATURE.toItem(3), Rune.BODY.toItem(4)),
        ELF(new int[]{13480, 13481}, "Ensouled elf head", 43, 86.0, 754.0, 7029, Rune.SOUL.toItem(2), Rune.NATURE.toItem(2), Rune.BODY.toItem(2)),
        TROLL(new int[]{13483, 13484}, "Ensouled troll head", 46, 92.0, 780.0, 7030, Rune.SOUL.toItem(2), Rune.NATURE.toItem(2), Rune.BODY.toItem(3)),
        HORROR(new int[]{13486, 13487}, "Ensouled horror head", 52, 104.0, 832.0, 7031, Rune.SOUL.toItem(2), Rune.NATURE.toItem(2), Rune.BODY.toItem(4)),
        KALPHITE(new int[]{13489, 13490}, "Ensouled kalphite head", 57, 114.0, 884.0, 7032, Rune.SOUL.toItem(2), Rune.NATURE.toItem(3), Rune.BODY.toItem(4)),
        DAGANNOTH(new int[]{13492, 13493}, "Ensouled dagannoth head", 62, 124.0, 936.0, 7033, Rune.SOUL.toItem(3), Rune.NATURE.toItem(3), Rune.BODY.toItem(4)),
        BLOODVELD(new int[]{13495, 13496}, "Ensouled bloodveld head", 65, 130.0, 1040.0, 7034, Rune.SOUL.toItem(2), Rune.NATURE.toItem(2), Rune.BLOOD.toItem(1)),
        TZHAAR(new int[]{13498, 13499}, "Ensouled tzhaar head", 69, 138.0, 1104.0, 7035, Rune.SOUL.toItem(2), Rune.NATURE.toItem(3), Rune.BLOOD.toItem(1)),
        DEMON(new int[]{13501, 13502}, "Ensouled demon head", 72, 144.0, 1170.0, 7036, Rune.SOUL.toItem(2), Rune.NATURE.toItem(4), Rune.BLOOD.toItem(1)),
        AVIANSIE(new int[]{13504, 13505}, "Ensouled aviansie head", 78, 156.0, 1234.0, 7037, Rune.SOUL.toItem(3), Rune.NATURE.toItem(4), Rune.BLOOD.toItem(1)),
        ABYSSAL_CREATURE(new int[]{13507, 13508}, "Ensouled abyssal head", 85, 170.0, 1300.0, 7038, Rune.SOUL.toItem(4), Rune.NATURE.toItem(4), Rune.BLOOD.toItem(1)),
        DRAGON(new int[]{13510, 13511}, "Ensouled dragon head", 93, 186.0, 1560.0, 7039, Rune.SOUL.toItem(4), Rune.NATURE.toItem(4), Rune.BLOOD.toItem(2));

        public final int[] headId;
        public final int levelReq, reanimatedNPCId;
        public final String headName;
        public final double mageExp, prayerExp;
        public Item[] runes;

        Monster(int[] headId, String headName, int levelReq, double mageExp, double prayerExp, int reanimatedNPCId, Item... runes) {
            this.headId = headId;
            this.headName = headName;
            this.levelReq = levelReq;
            this.mageExp = mageExp;
            this.prayerExp = prayerExp;
            this.reanimatedNPCId = reanimatedNPCId;
            this.runes = runes;
        }
    }

    private static Bounds SPAWN_AREA = new Bounds(1703, 3874, 1745, 3904, 0);
    private static final Projectile projectile = new Projectile(1289, 40, 10, 5, 75, 15, 10, 64);

    private static void reanimate(Player player, Monster monster, Item item) {
        Position oldPosition = new Position(player.getAbsX(), player.getAbsY(), 0);
        player.startEvent(event -> {
            player.lock();
            player.step(-1, 0, StepType.FORCE_WALK);
            event.delay(1);
            player.face(oldPosition.getX(), oldPosition.getY());
            event.delay(1);
            player.animate(7198);
            player.graphics(1288);
            event.delay(1);
            player.getInventory().remove(item.getId(), 1);
            GroundItem groundItem = new GroundItem(item.getId(), 1).owner(player).position(oldPosition).spawn();
            projectile.send(player.getAbsX(), player.getAbsY(), oldPosition.getX(), oldPosition.getY());
            event.delay(2);
            World.sendGraphics(1290, 0, 0, oldPosition);
            event.delay(1);
            groundItem.remove();
            event.delay(4);
            NPC animated = new NPC(monster.reanimatedNPCId).spawn(oldPosition.getX(), oldPosition.getY(), player.getHeight()).targetPlayer(player, true);
            animated.deathEndListener = (DeathListener.Simple) () -> {
                animated.remove();
                player.getStats().addXp(StatType.Prayer, monster.prayerExp, true);
            };
            animated.attackTargetPlayer(() -> !player.getPosition().isWithinDistance(animated.getPosition()));
            player.unlock();
        });
    }

    public Reanimate(Monster monster) {
        registerItem(monster.levelReq, monster.mageExp, true, monster.runes, (player, item) -> {
                if (item.getId() != monster.headId[0] && item.getId() != monster.headId[1]) {
                    player.dialogue(new ItemDialogue().one(monster.headId[0], "This spell cannot reanimate that item.<br> Its intended target is: <col=862013>" + monster.headName + "</col>."));
                    return false;
                }

                if (!player.getPosition().inBounds(SPAWN_AREA)) {
                    player.dialogue(new ItemDialogue().one(monster.headId[0], "The creature cannot be reanimated here. The power<br>of the crystals by the Dark Altar will increase the<br>potency of the spell."));
                    return false;
                }

                if (player.npcTarget) {
                    player.sendFilteredMessage("You need to kill the monster you already have before spawning another!");
                    return false;
                }

                reanimate(player, monster, item);
            return true;
        });
    }

}
