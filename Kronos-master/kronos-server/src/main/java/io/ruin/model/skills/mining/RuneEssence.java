package io.ruin.model.skills.mining;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public class RuneEssence {

    private static void mine(Player player, boolean darkEssence) {
        Pickaxe pickaxe = Pickaxe.find(player);
        if (pickaxe == null) {
            player.dialogue(new MessageDialogue("You need a pickaxe to mine this rock. You do not have a pickaxe which" +
                    " you have the Mining level to use."));
            return;
        }

        if (player.getInventory().isFull()) {
            player.privateSound(2277);
            player.sendMessage("Your inventory is too full to hold any more rune stones.");
            return;
        }

        if(!player.getInventory().hasId(Tool.CHISEL) && darkEssence) {
            player.sendMessage("You need a chisel to mine the rune stones.");
            return;
        }

        player.startEvent(event -> {
           event.delay(1);
           player.sendMessage("You swing your pick at the rock.");

           while(true) {
               if (player.getInventory().isFull()) {
                   player.privateSound(2277);
                   player.sendMessage("Your inventory is too full to hold any more rune stones.");
                   player.resetAnimation();
                   return;
               }

               if(darkEssence && player.getStats().get(StatType.Crafting).fixedLevel < 38) {
                   player.sendMessage("You need a Crafting level of least 38 to mine from this rock.");
                   return;
               }

               Stat stat = player.getStats().get(StatType.Mining);
               player.animate(pickaxe.regularAnimationID);
               event.delay(darkEssence ? ticks(stat.currentLevel, pickaxe) : 2);

               if(darkEssence) {
                   player.animate(7201);
                   event.delay(6);
                   player.getInventory().add(13445, 1);
                   player.getStats().addXp(StatType.Crafting, 8.0, true);
                   player.getStats().addXp(StatType.Mining, 12.0, true);
                   PlayerCounter.MINED_DARK_ESSENCE.increment(player, 1);
               } else {
                   boolean pure = stat.currentLevel >= 30;
                   PlayerCounter counter = pure ? PlayerCounter.MINED_PURE_ESSENCE : PlayerCounter.MINED_RUNE_ESSENCE;
                   int itemId = pure ? 7936 : 1436;
                   double average = getEssenceAverage(player, pickaxe);
                   if (average < 1 && Random.get() > average)
                       continue;
                   int essenceCount = 1;
                   if (average > 1) {
                       double fixed = Math.floor(average);
                       essenceCount = (int) fixed;
                       double chance = average - fixed;
                       if (Random.get() < chance)
                           essenceCount += 1;
                   }
                   player.getInventory().add(itemId, essenceCount);
                   counter.increment(player, essenceCount);
                   player.getStats().addXp(StatType.Mining, 1.5 * essenceCount, true);
               }
           }
        });
    }

    private static double power(int level, Pickaxe pickaxe) {
        double points = ((level -1) + 1 + pickaxe.points);
        return (Math.min(80, points));
    }

    private static int ticks(int level, Pickaxe pickaxe) {
        double power = power(level, pickaxe);
        if(power > 50)
            return 2;
        if(power > 30)
            return 3;
        if(power > 15)
            return 4;
        return 5;
    }

    private static double getEssenceAverage(Player player, Pickaxe pickaxe) {
        double chance = 0.6;
        chance *= 1 + (getEffectiveMiningLevel(player) * 0.03);
        chance *= 1 + (pickaxe.points * pickaxe.points / 2000.0);
        return chance;
    }

    private static int getEffectiveMiningLevel(Player player) {
        return player.getStats().get(StatType.Mining).currentLevel;
    }

    private static void exitPortal(Player player) {
        player.startEvent(event -> {
           player.lock(LockType.FULL_NULLIFY_DAMAGE);
           player.graphics(110, 124, 30);
           player.sendMessage("You step through the portal.");
           event.delay(2);
           player.getMovement().teleport(3253, 3401); //lol why these coords
           player.unlock();
        });
    }

    static {
        ObjectAction.register(34773, "mine", (p, obj) -> mine(p, false));
        ObjectAction.register(34825, "use", (p, obj) -> exitPortal(p));
        ObjectAction.register(8981, 1, (p, obj) -> mine(p, true));
        ObjectAction.register(10796, 1, (p, obj) -> mine(p, true));
    }
}
