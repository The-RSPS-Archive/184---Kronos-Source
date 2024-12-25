package io.ruin.model.skills.runecrafting;

import com.google.common.collect.Lists;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Utils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author ReverendDread on 3/12/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Slf4j
public class OuraniaAltar {

    //Need to take another look at a way to do this better.
    static {
        ObjectAction.register(29631,"Craft-rune", (player, obj) -> {
            player.startEvent(event -> {

                List<Altars> altars = Lists.newArrayList(Altars.values());
                int essenceCount = 0, fromPouches = 0;
                ArrayList<Item> essence = player.getInventory().collectItems(Essence.PURE.id);
                essenceCount += fromPouches = Altars.essenceFromPouches(player);

                if (essence != null) {
                    essenceCount += essence.size();
                }

                if (fromPouches > 0) {
                    Altars.clearPouches(player);
                }

                if (essenceCount > 0) {

                    player.lock(LockType.FULL_DELAY_DAMAGE);
                    player.animate(791);
                    player.graphics(186, 100, 0);
                    event.delay(4);

                    IntStream.range(0, essenceCount).forEach(i -> {
                        Altars altar = Utils.randomTypeOfList(altars);
                        player.getInventory().add(altar.runeID, 1);
                        player.getInventory().remove(Essence.PURE.id, 1);
                        player.getStats().addXp(StatType.Runecrafting, altar.experience, true);
                        altar.counter.increment(player, 1);
                    });

                    player.unlock();

                } else {
                    player.sendMessage("You don't have any essence to craft.");
                }

            });
        });
        ObjectAction.register(29636, "Climb", ((player, obj) -> {
            player.startEvent((e) -> {
                e.path(player,new Position(3015, 5629, 0));
                player.getMovement().teleport(2453, 3231, 0);
            });
        }));
        ObjectAction.register(29635, "Climb", ((player, obj) -> {
            player.startEvent((e) -> {
                e.path(player, new Position(2453, 3231, 0));
                player.getMovement().teleport(3015, 5629, 0);
            });
        }));
    }

}
