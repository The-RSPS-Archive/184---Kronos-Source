package io.ruin.model.map.object.actions.impl.edgeville;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.activities.duelarena.DuelArena;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.shop.*;

import java.util.ArrayList;

public class Christmas {

    private static final int CHRISTMAS_TREE = 19038;

    public static final int SNOWBALL = 10501;
    private static final int[] SNOW_PILES = {19031, 19033, 19034, 19035};

    public static int SNOWBALL_EXCHANGE = 29709;

    private static final Position[] SNOW_POSITIONS = {
            new Position(3083, 3502, 0),
            new Position(3077, 3505, 0),
            new Position(3086, 3493, 0),
            new Position(3086, 3486, 0),
            new Position(3092, 3504, 0),
            new Position(3102, 3498, 0),
            new Position(3102, 3506, 0),
            new Position(3105, 3512, 0)
    };

    private static void grabPresent(Player player, GameObject christmasTree) {
        if (!player.isSapphire()) {
            player.sendMessage("You need to be a donator to grab a present from the christmas tree!");
            return;
        }
        if (player.gotChristmasPresent) {
            player.sendMessage("You've already grabbed your Christmas present!");
            return;
        }
        if (player.getInventory().isFull()) {
            player.sendMessage("You need at least one inventory slot to grab a Christmas present!");
            return;
        }

        player.startEvent(event -> {
            player.lock();
            player.gotChristmasPresent = true;
            player.animate(832);
            player.getInventory().add(6199, 1);
            player.dialogue(new ItemDialogue().one(6199, "You grab a Mystery Box from the Christmas tree. Merry Christmas and thank you for supporting " + World.type.getWorldName() + "!"));
            event.delay(1);
            player.unlock();
        });
    }

    private static void takeSnow(Player player, GameObject snow) {
        if (!player.getInventory().hasRoomFor(SNOWBALL)) {
            player.sendMessage("You don't have enough room to carry snow!");
            return;
        }

        player.startEvent(event -> {
            event.delay(1);
            player.sendMessage("You attempt to make some snowballs...");
            while (true) {
                if (!player.getInventory().hasRoomFor(SNOWBALL))
                    return;
                player.animate(5067);
                player.getInventory().add(SNOWBALL, 3);
                event.delay(5);
            }
        });
    }

    public static final Projectile SNOWBALL_PROJ = new Projectile(861, 30, 12, 62, 50, 15, 11, 64);

    public static void throwSnow(Player player, Player target) {
        player.startEvent(event -> {
            while(player.getEquipment().hasId(SNOWBALL)) {
                if(target.wildernessLevel > 0) {
                    player.dialogue(new PlayerDialogue("He might kill me if I throw a snowball at him while he's in the wilderness.."));
                    break;
                }
                if(player.snowballCooldown.isDelayed())
                    break;
                if(player.isIdle)
                    break;
                TargetRoute.set(player, target, 5);
                player.lock();
                player.animate(5063);
                player.graphics(860);
                player.getEquipment().remove(10501, 1);
                int delay = SNOWBALL_PROJ.send(player, target);
                target.graphics(862, 30, delay - 50);
                player.snowballPoints += Random.get(1, 3);
                player.snowballCooldown.delay(4);
//                if(player.journal == Journal.MAIN)
//                    SnowballPoints.INSTANCE.send(player);
                player.unlock();
                event.delay(5);
            }
        });
    }

    static {
        /**
         * Todo: create a system to handle this better
         */
        LoginListener.register(player -> {
            if (player.wildernessLevel <= 0 && player.getEquipment().hasId(SNOWBALL))
                player.setAction(1, PlayerAction.PELT);
        });

        ItemDef.get(SNOWBALL).addOnTickEvent((player, item) -> {
            if (player.wildernessLevel <= 0 && !player.pvpAttackZone && !player.getPosition().inBounds(DuelArena.CUSTOM_EDGE)) {
                player.setAction(1, PlayerAction.PELT);
                player.snowballPeltOption = true;
            }
        });

        if (World.christmas) {
            ObjectAction.register(CHRISTMAS_TREE, "grab-present", Christmas::grabPresent);

            for (int snow : SNOW_PILES)
                ObjectAction.register(snow, "take", Christmas::takeSnow);

            for (Position snowPosition : SNOW_POSITIONS)
                MapListener.registerPosition(snowPosition)
                        .onEnter(player -> player.openInterface(InterfaceType.SECONDARY_OVERLAY, 204))
                        .onExit((player, logout) -> player.closeInterface(InterfaceType.SECONDARY_OVERLAY));

            ArrayList<ShopItem> rewards = new ArrayList<>(20);
            rewards.add(new ShopItem(20836, 100_000, 200));
            rewards.add(new ShopItem(20834, 100_000, 150));
            rewards.add(new ShopItem(10507, 100_000, 100));

            rewards.add(new ShopItem(6856, 100_000, 50));
            rewards.add(new ShopItem(6857, 100_000, 50));
            rewards.add(new ShopItem(6858, 100_000, 50));
            rewards.add(new ShopItem(6859, 100_000, 50));
            rewards.add(new ShopItem(6860, 100_000, 50));
            rewards.add(new ShopItem(6861, 100_000, 50));
            rewards.add(new ShopItem(6862, 100_000, 50));
            rewards.add(new ShopItem(6863, 100_000, 50));

            rewards.add(new ShopItem(10836, 100_000, 75));
            rewards.add(new ShopItem(10837, 100_000, 75));
            rewards.add(new ShopItem(10838, 100_000, 75));
            rewards.add(new ShopItem(10839, 100_000, 75));
            rewards.add(new ShopItem(10840, 100_000, 75));


            Shop snowballExchange = Shop.builder()
                    .identifier("0afe49e7-a9f4-4297-9aa0-9f7c92090159")
                    .defaultStock(rewards)
                    .currency(Currency.SNOWBALL_POINTS)
                    .canSellToStore(false)
                    .generatedByBuilder(true)
                    .build();

            ShopManager.registerShop(snowballExchange);
                    //new Shop("Snowball Point Exchange", ShopCurrency.SNOWBALL_POINTS, true, rewards);

            ObjectAction.register(SNOWBALL_EXCHANGE, "open", (player, obj) -> snowballExchange.open(player));
            ObjectAction.register(SNOWBALL_EXCHANGE, "information", (player, obj) -> {
                player.sendScroll("<col=800000>Snowball Point Exchange",
                        "You can <col=800000>earn points by throwing snowballs at other players</col>. Each",
                        "time your throw one at somebody, you'll earn <col=800000>1-3 points</col>. You can",
                        "spend those points on <col=800000>Christmas apparel</col>! Snowball points have",
                        "been added to your player journal.",
                        "",
                        "You currently have <col=800000>" + player.snowballPoints + " </col>snowball points.",
                        "",
                        "Merry Christmas and thank you for playing " + World.type.getWorldName() + "!");
            });

        }
    }
}
