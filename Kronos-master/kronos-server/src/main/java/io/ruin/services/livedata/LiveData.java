package io.ruin.services.livedata;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import io.ruin.api.utils.PackageLoader;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.api.utils.TimeUtils;
import io.ruin.content.activities.tournament.TournamentManager;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.containers.bank.Bank;
import io.ruin.model.item.containers.bank.BankItem;
import io.ruin.model.map.Position;
import io.ruin.services.livedata.handler.LiveDataUpdateHandler;
import io.ruin.utility.OfflineMode;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathTemplateHandler;
import io.undertow.util.HttpString;
import io.undertow.util.StatusCodes;

import java.util.HashMap;
import java.util.Map;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;


public class LiveData {

//    private static String auth = "297c97af304660eee9f889a94d238fb436be0b4cd362982affd4a344a49f18cb";
//
//    private static PathTemplateHandler pathHandler = Handlers.pathTemplate();
//
//    public static void start() {
//        if (OfflineMode.enabled)
//            return;
//        System.out.print(TimeUtils.addTimestamp("Loading live data update handlers... "));
//        try {
//            int loaded = loadUpdateHandlers();
//            System.out.println("Loaded " + loaded + " update handlers.");
//        } catch (Exception e) {
//            ServerWrapper.logError("Failed to load update handlers", e);
//            return;
//        }
//        System.out.println(TimeUtils.addTimestamp("Starting live data server..."));
//        Undertow server = Undertow.builder().addHttpListener(8181, "0.0.0.0")
//                .setHandler(pathHandler)
//                .build();
//        server.start();
//        playerSummary();
//        containerSummary();
//
//        pathHandler.add("/tournament.json", authHandler(exchange -> {
//            Map<String, Object> info = new HashMap<>();
//            boolean inProgress = TournamentManager.activeTournament != null && TournamentManager.activeTournament.getInProgress();
//            info.put("active", inProgress);
//            info.put("seconds_left", TournamentManager.activityTimer * 60);
//
//            exchange.getResponseHeaders().put(HttpString.tryFromString(HttpHeaders.CONTENT_TYPE), "application/json");
//            exchange.getResponseSender().send(new Gson().toJson(info));
//        }));
//    }
//
//    public static HttpHandler authHandler(HttpHandler protectedHandler) {
//        return exchange -> {
//            if (auth.equals(exchange.getRequestHeaders().get("Auth", 0))) {
//                protectedHandler.handleRequest(exchange);
//            } else {
//                exchange.setStatusCode(StatusCodes.UNAUTHORIZED);
//                exchange.getResponseSender().send("Auth denied.");
//            }
//        };
//    }
//
//    private static void playerSummary() {
//        // Register player summary handler
//        pathHandler.remove("/players/summary.json");
//        pathHandler.add("/players/summary.json", authHandler(exchange -> {
//            Map<Integer, PlayerInfo> infos = new HashMap<>();
//            for (Player player : World.players) {
//                PlayerInfo info = new PlayerInfo();
//                info.name = player.getName();
//                info.rights = player.getClientGroupId();
//                info.position = player.getPosition();
//                info.value = totalValueOf(player);
//                info.ip = player.getIp();
//                info.id = player.getUserId();
//                infos.put(player.getUserId(), info);
//            }
//            exchange.getResponseHeaders().put(HttpString.tryFromString(HttpHeaders.CONTENT_TYPE), "application/json");
//            exchange.getResponseSender().send(new Gson().toJson(infos));
//        }));
//    }
//
//    private static void containerSummary() {
//        // Register item resolver
//        pathHandler.remove("/players/{player}/items.json");
//        pathHandler.add("/players/{player}/items.json", authHandler(exchange -> {
//            String pparam = exchange.getQueryParameters().get("player").getFirst();
//            int userId = Integer.parseInt(pparam);
//
//            exchange.setStatusCode(404); // In case we don't find it
//
//            Player player = World.getPlayer(userId, true);
//            if (player == null)
//                return;
//            ItemInfo info = new ItemInfo();
//            info.name = player.getName();
//            info.rights = player.getClientGroupId();
//            info.tile = player.getPosition();
//            info.value = totalValueOf(player);
//            info.ip = player.getIp();
//            info.character = player.getUserId();
//
//            info.inventory = new ItemContainerInfo(player.getInventory());
//            info.equipment = new ItemContainerInfo(player.getEquipment());
//            info.bank = new ItemContainerInfo(player.getBank());
//
//            exchange.setStatusCode(200);
//            exchange.getResponseHeaders().put(HttpString.tryFromString(HttpHeaders.CONTENT_TYPE), "application/json");
//            exchange.getResponseSender().send(new Gson().toJson(info));
//        }));
//    }
//
//    public static int totalValueOf(Player player) {
//        int totalWealth = 0;
//
//        //Bank
//        for (BankItem item : player.getBank().getItems()) {
//            if (item != null && item.getId() != Bank.BLANK_ID)
//                totalWealth += getWealth(item);
//        }
//
//        //Inventory
//        for (Item item : player.getInventory().getItems()) {
//            if (item != null)
//                totalWealth += getWealth(item);
//        }
//
//        //Equipment
//        for (Item item : player.getEquipment().getItems()) {
//            if (item != null)
//                totalWealth += getWealth(item);
//        }
//
//        return totalWealth;
//    }
//
//    public static long totalValueOf(ItemContainerG<? extends Item> container) {
//        long l = 0;
//        for (Item i : container.getItems()) {
//            if (i != null)
//                l += getWealth(i);
//        }
//        return l;
//    }
//
//    public static long getWealth(Item item) {
//        //This method is pretty crappy...
//        //Probably want to rethink it later on!
//        if (item.getId() == COINS_995)
//            return item.getAmount();
//        if (item.getId() == BLOOD_MONEY)
//            return item.getAmount();
//        long price = item.getDef().highAlchValue;
//        return item.getAmount() * price;
//    }
//
//    static class PlayerInfo {
//        public String name;
//        public long value;
//        public Position position;
//        public int rights;
//        public int id;
//        public String ip;
//    }
//
//    static class ItemInfo {
//        public String name;
//        public long value;
//        public Position tile;
//        public int rights;
//        public int character;
//        public String ip;
//
//
//        public ItemContainerInfo inventory;
//        public ItemContainerInfo equipment;
//        public ItemContainerInfo bank;
//    }
//
//
//    private static int loadUpdateHandlers() throws Exception {
//        int loaded = 0;
//        for (Class<? extends LiveDataUpdateHandler> c : PackageLoader.load("io.ruin.services.livedata.handler.impl", LiveDataUpdateHandler.class, false)) {
//            try {
//                LiveDataUpdateHandler lduh = c.newInstance();
//                pathHandler.add(lduh.path(), lduh.getHttpHandler());
//                loaded++;
//            } catch (Exception e) {
//                ServerWrapper.logError("Failed to load live data update handler: " + c.getName(), e);
//            }
//        }
//        return loaded;
//    }
//
//    static class ItemContainerInfo {
//
//        public long value;
//        public int[] ids;
//        public int[] amounts;
//
//        public ItemContainerInfo(ItemContainerG<? extends Item> container) {
//            value = totalValueOf(container);
//
//            ids = new int[container.getItems().length];
//            amounts = new int[container.getItems().length];
//
//            for (int i = 0; i < ids.length; i++) {
//                Item item = container.get(i);
//
//                if (item != null) {
//                    ids[i] = item.getId();
//                    amounts[i] = item.getAmount();
//                }
//            }
//        }
//    }

}
