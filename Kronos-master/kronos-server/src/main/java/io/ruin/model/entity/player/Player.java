package io.ruin.model.entity.player;

import com.google.gson.annotations.Expose;
import io.netty.channel.Channel;
import io.ruin.Server;
import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.utils.*;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.InterfaceDef;
import io.ruin.cache.Varp;
import io.ruin.event.GameEventProcessor;
import io.ruin.model.World;
import io.ruin.model.activities.duelarena.Duel;
import io.ruin.model.activities.duelarena.DuelArena;
import io.ruin.model.activities.wilderness.BountyHunter;
import io.ruin.model.content.UpgradeMachine;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.UpdateMask;
import io.ruin.model.entity.shared.listeners.*;
import io.ruin.model.entity.shared.masks.*;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.handlers.TeleportInterface;
import io.ruin.model.inter.journal.presets.PresetCustom;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.impl.boxes.mystery.SuperMysteryBox;
import io.ruin.model.item.actions.impl.chargable.SerpentineHelm;
import io.ruin.model.item.actions.impl.storage.DeathStorage;
import io.ruin.model.item.actions.impl.storage.LootingBag;
import io.ruin.model.item.actions.impl.storage.RunePouch;
import io.ruin.model.item.actions.impl.tradepost.TradePost;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.containers.Inventory;
import io.ruin.model.item.containers.Trade;
import io.ruin.model.item.containers.bank.Bank;
import io.ruin.model.item.containers.bank.BankPin;
import io.ruin.model.map.*;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.actions.impl.edgeville.Christmas;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.shop.Shop;
import io.ruin.model.skills.construction.House;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.model.skills.farming.Farming;
import io.ruin.model.skills.hunter.Hunter;
import io.ruin.model.stat.StatList;
import io.ruin.model.stat.StatType;
import io.ruin.network.PacketSender;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.IncomingDecoder;
import io.ruin.services.Hiscores;
import io.ruin.services.Loggers;
import io.ruin.services.XenGroup;
import io.ruin.utility.CS2Script;
import io.ruin.utility.TickDelay;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

import static io.ruin.cache.ItemID.*;

public class Player extends PlayerAttributes {

    public Shop viewingShop;
    /**
     * Session
     */

    private Channel channel;

    private String ipAddress;

    private int ipAddressInt;

    public Channel getChannel() {
        return channel;
    }

    public String getIp() {
        return ipAddress;
    }

    public int getIpInt() {
        return ipAddressInt;
    }

    private String macAddress;

    public String getMACAddress() {
        return macAddress;
    }

    private String uuid;

    public String getUUID() {
        return uuid;
    }

    /**
     * Info
     */

    private int userId;

    private String name;

    private String password;

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        getAppearance().update();
    }

    public String getPassword() {
        return password;
    }

    /**
     * Groups & Rank (Used to display client icons)
     */
    private boolean[] groups;

    private PlayerGroup primaryGroup;

    private PlayerGroup clientGroup; //the group displayed on clients

    public PlayerGroup getPrimaryGroup() {
        return primaryGroup;
    }

    private void setGroups(List<Integer> groupIds) {
        groups = new boolean[PlayerGroup.GROUPS_BY_ID.length];
        for(Integer id : groupIds) {
            PlayerGroup group = PlayerGroup.GROUPS_BY_ID[id];
            if(group != null)
                groups[group.id] = true;
        }
        primaryGroup = PlayerGroup.GROUPS_BY_ID[groupIds.get(0)];
        updateClientGroup();
    }


    public void updateClientGroup() {
        if(primaryGroup != null && primaryGroup.clientImgId != -1) {
            clientGroup = primaryGroup;
            return;
        }
        for(PlayerGroup group : PlayerGroup.values()) {
            if(groups[group.id] && group.clientImgId != -1) {
                clientGroup = group;
                return;
            }
        }
        clientGroup = PlayerGroup.REGISTERED;
    }

    public void join(PlayerGroup g) {
        groups[g.id] = true;
        updateClientGroup();
    }

    public void leave(PlayerGroup g) {
        groups[g.id] = false;
        updateClientGroup();
    }

    public boolean isGroup(PlayerGroup g) {
        return groups[g.id];
    }

    public boolean isModerator() {
        return isGroup(PlayerGroup.MODERATOR);
    }

    public boolean isSupport() {
        return isGroup(PlayerGroup.SUPPORT);
    }

    public boolean isAdmin() {
        return isGroup(PlayerGroup.OWNER) || isGroup(PlayerGroup.DEVELOPER) || isGroup(PlayerGroup.COMMUNITY_MANAGER)
                || isGroup(PlayerGroup.ADMINISTRATOR);
    }

    public boolean isStaff() {
        return  isGroup(PlayerGroup.MODERATOR) ||  isGroup(PlayerGroup.SUPPORT)
                || isGroup(PlayerGroup.ADMINISTRATOR) || isGroup(PlayerGroup.OWNER) || isGroup(PlayerGroup.DEVELOPER)
                 || isGroup(PlayerGroup.COMMUNITY_MANAGER) || isGroup(PlayerGroup.ADMINISTRATOR);
    }

    public boolean isSapphire() {
        return isGroup(PlayerGroup.SAPPHIRE) || isRuby();
    }

    public boolean isRuby() {
        return isGroup(PlayerGroup.SAPPHIRE) || isDiamond();
    }

    public boolean isDiamond() {
        return isGroup(PlayerGroup.DIAMOND) || isDragonStone();
    }

    public boolean isDragonStone() {
        return isGroup(PlayerGroup.DRAGONSTONE) || isOnyx();
    }

    public boolean isOnyx() {
        return isGroup(PlayerGroup.ONYX) || isZenyte();
    }

    public boolean isZenyte() {
        return isGroup(PlayerGroup.ZENYTE);
    }

    public boolean[] getGroups() {
        return groups;
    }

    public PlayerGroup getClientGroup() {
        return hidePlayerIcon ? PlayerGroup.REGISTERED : clientGroup;
    }

    public int getClientGroupId() {
        if (hidePlayerIcon)
            return 0;
        if (clientGroup.ordinal() > PlayerGroup.SUPPORT.ordinal() && getGameMode() != GameMode.STANDARD) { // tough call here - what do we prioritize, ironman or custom ranks like donator/youtuber?
            return getGameMode().ordinal() + 2;
        }
        return clientGroup.clientId;
    }

    /**
     * Private message information
     */
    private int unreadPMs;

    public int getUnreadPMs() {
        return unreadPMs;
    }

    /**
     * Incoming packets
     */
    private IncomingDecoder decoder;

    public IncomingDecoder getDecoder() {
        return decoder;
    }

    /**
     * Outgoing packets
     */
    private PacketSender packetSender;

    public void sendMessage(String message) {
        packetSender.sendMessage(message, null, 0);
    }

    public void sendURL(String message) {
        packetSender.sendMessage(message, World.type.getWebsiteUrl(), 4);
    }

    public void sendNotification(String message) {
        packetSender.sendMessage(message, "", 14);
    }

    public void sendFilteredMessage(String message) {
        packetSender.sendMessage(message, null, 105);
    }

    public void openUrl(String title, String url) {
        dialogue(new MessageDialogue("Opening " + title + "...<br>If this page fails to open please navigate your browser to:<br><col=880088>" + url));
        packetSender.sendUrl(url, false);
    }

    public void openUrl(String url) {
        packetSender.sendUrl(url, false);
    }

    public void sendScroll(String title, String... lines) {
        if(isVisibleInterface(119))
            closeInterface(InterfaceType.MAIN);
        packetSender.sendString(119, 2, title);
        int childId = 4;
        packetSender.sendString(119, childId++, "");
        for(String s : lines)
            packetSender.sendString(119, childId++, s);
        packetSender.sendClientScript(917, "ii", -1, -1);
        openInterface(InterfaceType.MAIN, 119);
        packetSender.sendClientScript(2523, "1i", 1, lines.length);
    }

    public void sendHintArrow(Entity target) {
        packetSender.sendHintIcon(target);
    }

    public void sendHintArray(Position tile) {
        packetSender.sendHintIcon(tile);
    }

    public void clearHintArrow() {
        packetSender.resetHintIcon(false);
    }

    public PacketSender getPacketSender() {
        return packetSender;
    }

    /**
     * Online
     */

    private boolean online;

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

    /**
     * Display
     */
    private int displayMode = -1;

    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode;
    }

    public boolean hasDisplay() {
        return displayMode != -1;
    }

    public boolean isFixedScreen() {
        return displayMode < 2;
    }

    /**
     * Game frame
     */
    private int gameFrameId;

    public void setGameFrameId(int gameFrameId) {
        this.gameFrameId = gameFrameId;
    }

    public int getGameFrameId() {
        return gameFrameId;
    }

    /**
     * Interface visibility
     */
    private boolean[] visibleInterfaces = new boolean[InterfaceDef.COUNTS.length];

    private Integer[][] visibleInterfaceIds = new Integer[InterfaceDef.COUNTS.length][];

    public void setVisibleInterface(int interfaceId, int parentId, int childId) {
        if(visibleInterfaceIds[parentId] == null)
            visibleInterfaceIds[parentId] = new Integer[InterfaceDef.COUNTS[parentId]];
        else if(visibleInterfaceIds[parentId] != null) {
            Integer id = visibleInterfaceIds[parentId][childId];
            if(id != null)
                visibleInterfaces[id] = false;
        }
        visibleInterfaces[interfaceId] = true;
        visibleInterfaceIds[parentId][childId] = interfaceId;
    }

    public void removeVisibleInterface(int parentId, int childId) {
        if (visibleInterfaceIds[parentId][childId] != null)
            visibleInterfaces[visibleInterfaceIds[parentId][childId]] = false;
        visibleInterfaceIds[parentId][childId] = null;
    }

    public void moveVisibleInterface(int fromParentId, int fromChildId, int toParentId, int toChildId) {
        Integer interfaceId = visibleInterfaceIds[fromParentId][fromChildId];
        if(interfaceId == null)
            return;
        setVisibleInterface(interfaceId, toParentId, toChildId);
        visibleInterfaceIds[fromParentId][fromChildId] = null;
    }

    public boolean isVisibleInterface(int interfaceId) {
        return interfaceId == gameFrameId || visibleInterfaces[interfaceId];
    }

    /**
     * Interfaces - Opening, Closing & Handling
     */

    private InterfaceHandler[] activeInterfaceHandlers = new InterfaceHandler[InterfaceType.values().length];

    public void openInterface(InterfaceType type, int interfaceId, InterfaceHandler handler) {
        closeChatbox(type == InterfaceType.CHATBOX); //dupe prevention
        InterfaceHandler activeHandler = activeInterfaceHandlers[type.ordinal()];
        if(activeHandler != null && activeHandler.closedAction != null)
            activeHandler.closedAction.accept(this, interfaceId);
        type.open(this, interfaceId);
        activeInterfaceHandlers[type.ordinal()] = handler == null ? InterfaceHandler.EMPTY_HANDLER : handler;
    }

    public void openInterface(InterfaceType type, int interfaceId) {
        openInterface(type, interfaceId, InterfaceHandler.HANDLERS[interfaceId]);
    }

    public void setInterfaceUnderlay(int color, int transparency) {
        CS2Script.TOPLEVEL_MAINMODAL_OPEN.sendScript(this, color, transparency);
    }

    public void closeInterface(InterfaceType type) {
        InterfaceHandler activeHandler = activeInterfaceHandlers[type.ordinal()];
        if(activeHandler == null)
            return;
        if(activeHandler.closedAction != null)
            activeHandler.closedAction.accept(this, -1);
        type.close(this);
        activeInterfaceHandlers[type.ordinal()] = null;
    }

    public void closeInterfaces() {
        closeInterfaces(false);
    }

    public void closeInterfaces(boolean skipDialogues) {
        for(InterfaceType type : InterfaceType.values()) {
            if(type.overlaySetting != 1)
                closeInterface(type);
        }
        if(trade != null)
            trade.close();
        if(duel != null)
            duel.close();
        closeChatbox(skipDialogues);
    }

    public void closeChatbox(boolean skipDialogues) {
        if(!skipDialogues && dialogues != null)
            closeDialogue();
        if(consumerInt != null || consumerString != null) {
            consumerInt = null;
            consumerString = null;
            packetSender.sendClientScript(299, "ii", 1, 1);
        }
    }

    /**
     * Dialogue
     */

    private int dialogueStage;

    private Dialogue[] dialogues;

    public Dialogue lastDialogue;

    public OptionsDialogue optionsDialogue;

    public SkillDialogue skillDialogue;

    public YesNoDialogue yesNoDialogue;

    private void openDialogue(boolean closeInterfaces, Dialogue... dialogues) {
        if(closeInterfaces) //important to be true in almost every case to prevent dupes!
            closeInterfaces(true);
        this.dialogueStage = 1;
        this.dialogues = dialogues;
        this.optionsDialogue = null;
        this.skillDialogue = null;
        this.yesNoDialogue = null;
        (lastDialogue = dialogues[0]).open(this);
    }

    public void dialogue(Dialogue... dialogues) {
        openDialogue(true, dialogues);
    }

    public void unsafeDialogue(Dialogue... dialogues) {
        openDialogue(false, dialogues);
    }

    public void continueDialogue() {
        onDialogueContinued();
        if (dialogues == null || dialogueStage >= dialogues.length) {
            closeDialogue();
        } else {
            (lastDialogue = dialogues[dialogueStage++]).open(this);
        }
    }

    public void closeDialogue() {
        dialogues = null;
        if(lastDialogue != null) {
            lastDialogue.closed(this);
            lastDialogue = null;
        }
        optionsDialogue = null;
        skillDialogue = null;
        yesNoDialogue = null;
        closeInterface(InterfaceType.CHATBOX);
    }

    public boolean hasDialogue() {
        return dialogues != null || optionsDialogue != null || skillDialogue != null || yesNoDialogue != null;
    }

    /**
     * Input
     */

    public Consumer<Integer> consumerInt;

    public boolean retryIntConsumer;

    public Consumer<String> consumerString;

    public boolean retryStringConsumer;

    /**
     * Integer input (Whole numbers!!)
     */

    public void integerInput(String message, Consumer<Integer> consumer) {
        consumerInt = consumer;
        retryIntConsumer = false;
        packetSender.sendClientScript(108, "s", message);
    }

    public void retryIntegerInput(String message) {
        integerInput(message, consumerInt);
        retryIntConsumer = true;
    }

    /**
     * Item input
     */

    public void itemSearch(String message, boolean allItems, Consumer<Integer> consumer) {
        consumerInt = consumer;
        retryIntConsumer = false;
        packetSender.sendClientScript(750, "s1g", message, (allItems ? 2 : 1), -1);
    }

    /**
     * String input (Basically any group of characters!)
     */

    public void stringInput(String message, Consumer<String> consumer) {
        consumerString = consumer;
        retryStringConsumer = false;
        packetSender.sendClientScript(110, "s", message);
    }

    public void retryStringInput(String message) {
        stringInput(message, consumerString);
        retryStringConsumer = true;
    }

    /**
     * Name input (Basically only allows characters used in player names!)
     */

    public void nameInput(String message, Consumer<String> consumer) {
        consumerString = consumer;
        retryStringConsumer = false;
        packetSender.sendClientScript(109, "s", message);
    }

    public void retryNameInput(String message) {
        nameInput(message, consumerString);
        retryStringConsumer = true;
    }

    /**
     * Options
     */

    private PlayerAction[] actions = new PlayerAction[8];

    public void setAction(int option, PlayerAction action) {
        if(action == null) {
            PlayerAction previousAction = actions[option - 1];
            if(previousAction == null)
                return;
            actions[option - 1] = null;
            packetSender.sendPlayerAction("null", false, option);
        } else {
            actions[option - 1] = action;
            packetSender.sendPlayerAction(action.name, action.top, option);
        }
    }

    private void setInvisibleAction(int option, PlayerAction action) {
        actions[option - 1] = action;
    }

    public PlayerAction getAction(int option) {
        return actions[option - 1];
    }

    /**
     * Configs
     */

    @Expose public HashMap<Integer, Integer> savedConfigs = new HashMap<>();

    public int[] varps = new int[Varp.LOADED.length];

    private boolean[] updatedVarps = new boolean[varps.length];

    private int[] updatedVarpIds = new int[varps.length];

    private int updatedVarpCount;

    public void updateVarp(int id) {
        if(updatedVarps[id])
            return;
        updatedVarps[id] = true;
        updatedVarpIds[updatedVarpCount++] = id;
    }

    public void sendVarps() {
        if(updatedVarpCount == 0)
            return;
        for(int i = 0; i < updatedVarpCount; i++) {
            int id = updatedVarpIds[i];
            int value = varps[id];
            updatedVarps[id] = false;
            packetSender.sendVarp(id, value);
        }
        updatedVarpCount = 0;
    }

    /**
     * Region
     */

    public Region lastRegion;

    private ArrayList<Region> regions = new ArrayList<>();

    public void addRegion(Region region) {
        region.players.add(this);
        regions.add(region);
    }

    public void removeFromRegions() {
        regions.removeIf(region -> {
            region.players.remove(this);
            return true;
        });
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    /**
     * Player updating
     */

    private PlayerUpdater updater;

    public Iterable<Player> localPlayers() {
        return updater.localIterator();
    }

    public boolean isLocal(Player p) {
        return updater.local[p.getIndex()];
    }

    public PlayerUpdater getUpdater() {
        return updater;
    }

    /**
     * Npc updating
     */

    private PlayerNPCUpdater npcUpdater;

    public Collection<NPC> localNpcs() {
        return npcUpdater.localNpcs;
    }

    public PlayerNPCUpdater getNpcUpdater() {
        return npcUpdater;
    }

    /**
     * Movement
     */

    @Expose private PlayerMovement movement;

    public PlayerMovement getMovement() {
        return movement;
    }

    /**
     * Masks
     */

    private UpdateMask[] masks;

    @Expose private Appearance appearance;

    private ChatUpdate chatUpdate;

    protected ForceMovementUpdate forceMovementUpdate;

    protected MovementModeUpdate movementModeUpdate;

    protected TeleportModeUpdate teleportModeUpdate;

    public UpdateMask[] getMasks() {
        return masks;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public ChatUpdate getChatUpdate() {
        return chatUpdate;
    }

    /**
     * Items
     */

    @Expose private Inventory inventory;

    @Expose private Equipment equipment;

    private Trade trade;

    private Duel duel;

    @Expose private Bank bank;

    @Expose private BankPin bankPin;

    @Expose private LootingBag lootingBag;

    @Expose private RunePouch runePouch;

    public RunePouch getTournamentRunePouch() {
        return tournamentRunePouch;
    }

    @Expose private RunePouch tournamentRunePouch;

    private SuperMysteryBox box;

    @Expose private DeathStorage deathStorage;

    @Expose private ItemContainer privateRaidStorage;

    @Expose private ItemContainer raidRewards = new ItemContainer();

    public Inventory getInventory() {
        return inventory;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Trade getTrade() {
        return trade;
    }

    public Duel getDuel() {
        return duel;
    }

    public Bank getBank() {
        return bank;
    }

    public BankPin getBankPin() {
        return bankPin;
    }

    public LootingBag getLootingBag() {
        return lootingBag;
    }

    public RunePouch getRunePouch() {
        return runePouch;
    }

    public SuperMysteryBox getBox() { return box; }

    public DeathStorage getDeathStorage() {
        return deathStorage;
    }

    /**
     * Farming
     */
    @Expose public Farming farming;

    public Farming getFarming() {
        return farming;
    }

    /**
     * Stats
     */

    @Expose private StatList stats;

    public StatList getStats() {
        return stats;
    }

    /**
     * Prayer
     */

    @Expose private PlayerPrayer prayer;

    public PlayerPrayer getPrayer() {
        return prayer;
    }

    /**
     * Combat
     */

    @Expose private PlayerCombat combat;

    public PlayerCombat getCombat() {
        return combat;
    }

    /**
     * Bounty Hunter
     */

    @Expose private BountyHunter bountyHunter;

    public BountyHunter getBountyHunter() {
        return bountyHunter;
    }

    /**
     * Trade post
     */
    @Expose private TradePost tradePost;

    public TradePost getTradePost() {
        return tradePost;
    }

    /**
     * Hitpoints
     */

    @Override
    public int setHp(int newHp) {
        return stats.get(StatType.Hitpoints).alter(newHp);
    }

    @Override
    public int setMaxHp(int newHp) {
        return combat == null ? 0 : stats.get(StatType.Hitpoints.ordinal()).set(newHp);
    }

    @Override
    public int getHp() {
        return stats.get(StatType.Hitpoints).currentLevel;
    }

    @Override
    public int getMaxHp() {
        return stats.get(StatType.Hitpoints).fixedLevel;
    }

    /**
     * Map listeners
     */

    private List<MapListener> activeMapListeners = new ArrayList<>();

    private boolean[] activeStaticMapListeners = new boolean[MapListener.LISTENERS.length];

    public void addActiveMapListener(MapListener listener) {
        //Warning: If the listener isn't already active this won't function properly!
        //Another important note: listener.onEnter(p) will never be called when using this method.
        activeMapListeners.add(listener);
    }

    private void validateMapListeners() {
        if(!activeMapListeners.isEmpty()) {
            activeMapListeners.removeIf(listener -> {
                if(!listener.isActive(this)) {
                    if(listener.exitAction != null)
                        listener.exitAction.exited(this, false);
                    if(listener.staticId != -1)
                        activeStaticMapListeners[listener.staticId] = false;
                    return true;
                }
                return false;
            });
        }
        for(int i = 0; i < MapListener.LISTENERS.length; i++) {
            if(activeStaticMapListeners[i])
                continue;
            MapListener listener = MapListener.LISTENERS[i];
            if(listener.isActive(this)) {
                addActiveMapListener(listener);
                if(listener.enterAction != null)
                    listener.enterAction.entered(this);
                activeStaticMapListeners[i] = true;
            }
        }
    }

    public void collectResource(int harvestId, int amount) {
        collectResource(new Item(harvestId, amount));
    }

    public void collectResource(Item resource) {
        for(Item item : player.getEquipment().getItems()) {
            if(item != null && item.getDef() != null) {
                List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                boolean hasEffect = upgrades != null;
                if (hasEffect) {
                    for (String s : upgrades) {
                        try {
                            if (s.equalsIgnoreCase("empty"))
                                continue;
                            ItemEffect effect = ItemEffect.valueOf(s);
                            effect.getUpgrade().collectResource(player, resource);
                        } catch (Exception ex) {
                            System.err.println("Unknown upgrade { " + s + " } found!");
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void removeMapListeners() {
        if(!activeMapListeners.isEmpty()) {
            activeMapListeners.removeIf(listener -> {
                if(listener.exitAction != null)
                    listener.exitAction.exited(this, true);
                return true;
            });
        }
        activeMapListeners = null;
    }

    /**
     * Misc listeners
     */

    public LogoutListener logoutListener;

    public AllowPrayerListener allowPrayerListener;
    public ActivatePrayerListener activatePrayerListener;

    public TeleportListener teleportListener;

    /**
     * Init
     */

    public void init(LoginInfo info) {
        this.channel = info.channel;
        this.ipAddress = info.ipAddress;
        this.macAddress = info.macAddress;
        this.ipAddressInt = info.ipAddressInt;
        this.userId = info.userId;
        this.name = info.name;
        this.password = info.password;
        this.tfa = info.tfaCode != 0;
        this.unreadPMs = info.unreadPMs;
        this.uuid = info.uuid;

        setGroups(info.groupIds);

        if(position == null)
            position = new Position(3086, 3495, 0);
        else
            position.updateFirstChunk();
        lastPosition = position.copy();

        decoder = new IncomingDecoder(new ISAACCipher(info.keys));
        for(int i = 0; i < 4; i++)
            info.keys[i] += 50;
        packetSender = new PacketSender(this, new ISAACCipher(info.keys));

        updater = new PlayerUpdater(this);
        npcUpdater = new PlayerNPCUpdater(this);
        movement = new PlayerMovement(this);

        if(appearance == null)
            appearance = new Appearance();
        appearance.setPlayer(this);

        masks = new UpdateMask[]{ //order same way as client reads
                graphicsUpdate = new GraphicsUpdate(),
                forceTextUpdate = new ForceTextUpdate(),
                appearance,
                chatUpdate = new ChatUpdate(),
                movementModeUpdate = new MovementModeUpdate(),
                animationUpdate = new AnimationUpdate(),
                teleportModeUpdate = new TeleportModeUpdate(),
                forceMovementUpdate = new ForceMovementUpdate(),
                hitsUpdate = new HitsUpdate(),
                entityDirectionUpdate = new EntityDirectionUpdate(),
                mapDirectionUpdate = new MapDirectionUpdate(),
        };

        if(inventory == null)
            inventory = new Inventory();
        inventory.init(this, 28, Interface.INVENTORY, 0, 93, false);
        inventory.sendAll = true;

        if(equipment == null)
            equipment = new Equipment();
        equipment.init(this, 14, -1, 64208, 94, false);
        equipment.update(Equipment.SLOT_WEAPON);
        equipment.sendAll = true;

        trade = new Trade(this);

        duel = new Duel(this);

        if(bank == null)
            bank = new Bank();
        bank.init(this, 800, -1, 64207, 95, true);

        if(lootingBag == null)
            lootingBag = new LootingBag();
        lootingBag.init(this, 28, -1, 63786, 516, false);

        if(runePouch == null)
            runePouch = new RunePouch();
        runePouch.init(this, 3, -1, -1, -1, false);

        if(tournamentRunePouch == null)
            tournamentRunePouch = new RunePouch();
        tournamentRunePouch.init(this, 3, -1, -1, -1, false);

        if(box == null)
            box = new SuperMysteryBox();
        box.init(this, 24, -1, -1, 510, false);

        if(deathStorage == null)
            deathStorage = new DeathStorage();
        deathStorage.init(this, 100, -1, -1, 525, false);

        if(privateRaidStorage == null)
            privateRaidStorage = new ItemContainer();
        privateRaidStorage.init(player, 90, -1, -1, 583, false);
        privateRaidStorage.sendAll = true;

        if(raidRewards == null)
            raidRewards = new ItemContainer();
        raidRewards.init(player, 3, -1, -1, 581, false);

        if(bankPin == null)
            bankPin = new BankPin();
        bankPin.init(this);

        if(stats == null)
            stats = new StatList();
        stats.init(this);

        if(prayer == null)
            prayer = new PlayerPrayer();
        prayer.init(this);

        if(farming == null)
            farming = new Farming();
        farming.init(this);

        if(combat == null)
            combat = new PlayerCombat();
        combat.init(this);

        if(bountyHunter == null)
            bountyHunter = new BountyHunter();
        bountyHunter.init(this);

        if (tradePost == null) {
            tradePost = new TradePost();
        }
        tradePost.init(player);

        if (teleports == null) {
            teleports = new TeleportInterface();
        }
        teleports.setPlayer(this);

        if (upgradeMachine == null) {
            upgradeMachine = new UpgradeMachine();
        }
        upgradeMachine.setPlayer(this);

        checkMulti();
        Tile.occupy(this);
    }

    /**
     * Start & Finish
     */

    private Bounds EDGEVILLE = new Bounds(3063, 3457, 3121, 3519, -1);

    public void start() {
        combat.start();
        movement.sendEnergy(-1);
        getCombat().resetTb();
        /*
         * Misc
         */
        PresetCustom.check(this);
        bankPin.loggedIn();
        /*
         * Actions
         */
        setAction(2, PlayerAction.FOLLOW);
        setAction(3, PlayerAction.TRADE);
        setInvisibleAction(4, PlayerAction.TRADE);
        /*
         * Messages
         */
        sendMessage("Welcome to " + World.type.getWorldName() + "." + (" The Final Challenge!"));

        packetSender.sendDiscordPresence("Idle");
        if(World.weekendExpBoost)
            player.sendMessage(Color.COOL_BLUE.wrap("The 25% experience weekend boost is currently active!"));

        World.sendLoginMessages(this);

        /*
         * Listeners
         */
        LoginListener.executeAll(this);

        /*
         * Misc 2
         */

        if(pet != null)
            pet.spawn(this);

        Loggers.addOnlinePlayer(userId, name, World.id, ipAddress, player.isSupport(), player.isModerator(), player.isAdmin());

        Title.load(this);

        /*
         * Clean those who have a crazy amount of money on update
         */
        if (lastLoginDate != null && LocalDate.parse(lastLoginDate).isBefore(LocalDate.of(2019, 11, 10))) {
            if (player.inventory.getAmount(COINS_995) > 10000000) {
                player.inventory.findItem(COINS_995).setAmount(2000000);
            }
            if (player.bank.getAmount(COINS_995) > 10000000) {
                player.bank.findItem(COINS_995).setAmount(2000000);
            }

            if (player.inventory.getAmount(PLATINUM_TOKEN) > 10000) {
                player.inventory.findItem(PLATINUM_TOKEN).setAmount(2000);
            }
            if (player.bank.getAmount(PLATINUM_TOKEN) > 10000) {
                player.bank.findItem(PLATINUM_TOKEN).setAmount(2000);
            }
        }

        /*
         * Daily
         */
        if (lastLoginDate == null || LocalDate.parse(lastLoginDate).isBefore(LocalDate.now())) {
            dailyReset();
        }

        /*
         * Donator Benefit: [1 Daily Lottery Ticket]
         */
        if (lastLoginDate != null && LocalDate.parse(lastLoginDate).isBefore(LocalDate.now()) && isSapphire()) {
            sendMessage("You have received your daily lottery ticket. Thanks for supporting the server!");
            bank.add(1464, 1);
        }



        packetSender.sendVarp(1737, -2147483648);
        packetSender.sendClientScript(1105, "i", 1);
        packetSender.sendClientScript(423, "s", name);
        packetSender.sendClientScript(2498, "i11", 1, 0, 0);
        Config.HAS_DISPLAY_NAME.set(player, 1);
        packetSender.sendClientScript(828, "i", 1);
        packetSender.sendClientScript(233, "ImiiiiiiA", 24772664, 38864, 15, 200, 81, 1885, 0, 2000, 8498);
        packetSender.sendClientScript(233, "ImiiiiiiA", 24772665, 38864, 10, 180, 78, 158, 0, 2000, 8500);
        if (World.isDev())
            player.join(PlayerGroup.OWNER);
    }

    public void finish() {
        /*
         * Scrolls Timer Saving
         */
        if (player.rareDropBonus.remaining() > 0) {
            player.rareDropBonusTimeLeft = player.rareDropBonus.remaining();
        }
        if (player.expBonus.remaining() > 0) {
            player.expBonusTimeLeft = player.expBonus.remaining();
        }
        if (player.first3.remaining() > 0) {
            player.first3TimeLeft = player.first3.remaining();
        }
        if (player.petDropBonus.remaining() > 0) {
            player.petDropBonusTimeLeft = player.petDropBonus.remaining();
        }
        if (player.blackChinchompaBoost.remaining() > 0) {
            player.blackChinchompaBoostTimeLeft = player.blackChinchompaBoost.remaining();
        }
        if (player.darkCrabBoost.remaining() > 0) {
            player.darkCrabBoostTimeLeft = player.darkCrabBoost.remaining();
        }
		GameEventProcessor.killFor(this);
        Hunter.collapseAll(this);
        resetActions(true, true, true);
        bankPin.loggedOut();
        bountyHunter.loggedOut();
        if(logoutListener != null && logoutListener.logoutAction != null) {
            logoutListener.logoutAction.logout(this);
            logoutListener = null;
        }
        removeMapListeners();
        removeFromRegions();
        movement.finishTeleport(getPosition()); //if a teleport is queued, finish it!
        setHidden(true);
    }

    /**
     * Reset Actions
     */

    public void resetActions(boolean closeInterfaces, boolean resetMovement, boolean resetCombat) {
        GameEventProcessor.killFor(this);
        if(!isLocked())
            stopEvent(resetCombat);
        if(closeInterfaces)
            closeInterfaces();
        if(resetMovement) {
            movement.reset();
            movement.following = null;
            faceNone(false);
            TargetRoute.reset(this);
            if (seat != null)
                seat.stand(player);
        }
        if(resetCombat && combat.getTarget() != null)
            combat.reset();
    }

    /**
     * Logout & Saving
     */

    public int logoutStage; //0=no logout, 1=logout required, -1=logout accepted

    private final Object saveLock = new Object();

    private int saveAttempt;

    private long saveRetry = -1;

    public long lastBoxHeal = 0;

    private TickDelay xLogDelay;

    public void attemptLogout() {
        if(combat.isDead() || combat.isDefending(17)) {
            sendMessage("You can't log out until 10 seconds after the end of combat.");
            return;
        }
        if(supplyChestRestricted) {
            sendMessage("The power of the supply chest prevents you from logging out!");
            return;
        }
        if(isLockedExclude(LockType.FULL_ALLOW_LOGOUT) && player.getAppearance().getNpcId() == -1)
            return;
        if(logoutListener != null && logoutListener.attemptAction != null && !logoutListener.attemptAction.allow(this))
            return;
        logoutStage = 1;
        packetSender.sendDiscordPresence("In Lobby");
        packetSender.sendLogout();
    }

    public void forceLogout() {
        logoutStage = -1;
        packetSender.sendDiscordPresence("In Lobby");
        packetSender.sendLogout();
    }

    public void checkLogout() {
        /**
         * Validate channel & decode packets.
         */
        if(logoutStage == 0) {
            if(!channel.isActive() || decoder.timeout()) {
                channel.close();
                logoutStage = 1;
            } else {
                if(!decoder.process(this, 250))
                    Server.logWarning(name + " has suspicious packet count!");
                if(logoutStage == 0) //This player hasn't tried to log out, we good.
                    return;
            }
        }
        /**
         * Attempt to logout if a logout is required.
         */
        if(logoutStage == 1) {
            if(xLogDelay == null) {
                if(combat.isDead() || combat.isDefending(17)) {
                    xLogDelay = new TickDelay();
                    xLogDelay.delay(100); //after 60 seconds of xlogging on rs, no matter if you're in combat you dc.
                    return;
                }
            } else if(xLogDelay.isDelayed()) {
                if(combat.isDead() || combat.isDefending(17)) {
                    //This player is in combat, wait...
                    return;
                }
            }
            if(isLockedExclude(LockType.FULL_ALLOW_LOGOUT)) {
                //This player is locked, we must wait!
                return;
            }
            logoutStage = -1;
        }
        /**
         * Logout accepted, finish this player.
         */
        if(online) {
            online = false;
            finish();
        }
        /**
         * Attempt to save this player. (Player won't be fully logged out until save is complete!)
         */
        if(saveRetry != -1) {
            if(!Server.isPast(saveRetry))
                return;
            Server.logWarning(name + " is taking too long to save, retrying...");
        }
        int attempt;
        synchronized(saveLock) {
            if(++saveAttempt > Byte.MAX_VALUE)
                saveAttempt = 0;
            attempt = saveAttempt;
        }
        saveRetry = Server.getEnd(15);
        PlayerFile.save(this, attempt);
    }

    public void finishLogout(int attempt) {
        synchronized(saveLock) {
            if(attempt != saveAttempt)
                return;
        }
        World.players.remove(getIndex());
        CentralClient.sendLogout(userId);
        /**
         * Misc things like SQL updates
         */
        XenGroup.update(this);
        Hiscores.save(this);
        Loggers.logPlayer(this);
        Loggers.updateItems(this);
        Loggers.removeOnlinePlayer(userId, World.id);
    }

    /**
     * Processing
     */

    public void process() {

        processHits();
        processEvent();

        inventory.sendUpdates();
        equipment.sendUpdates();
        bank.sendUpdates();
        lootingBag.sendUpdates();
        runePouch.sendUpdates();
        tournamentRunePouch.sendUpdates();
        box.sendUpdates();

        combat.preAttack();
        TargetRoute.beforeMovement(this);
        movement.process();
        TargetRoute.afterMovement(this);

        Region region;
        if(movement.hasMoved() && lastRegion != (region = getPosition().getRegion()))
            lastRegion = region;
        validateMapListeners();

        combat.attack();

        prayer.process();
        stats.process();

        tick();
    }

    /**
     * Tick
     */

    private void tick() {
        playTime++;

        if(++appreciationTicks >= 100) {
            appreciationPoints += Random.get(5, 15);
            appreciationTicks = 0;
        }
        if(++specialRestoreTicks >= 50) {
            specialRestoreTicks = 0;
            combat.restoreSpecial(10);
        }
        if(antifireTicks > 0) {
            antifireTicks--;
            if(antifireTicks == 30) {
                sendMessage("<col=7f007f>Your antifire potion is about to expire.");
                privateSound(3120, 3, 0);
            } else if(antifireTicks == 0) {
                sendMessage("<col=7f007f>Your antifire potion has expired.");
                privateSound(2607, 1, 0);
            }
        }
        if(superAntifireTicks > 0) {
            superAntifireTicks--;
            if(superAntifireTicks == 30) {
                sendMessage("<col=7f007f>Your super antifire potion is about to expire.");
                privateSound(3120, 3, 0);
            } else if(superAntifireTicks == 0) {
                sendMessage("<col=7f007f>Your super antifire potion has expired.");
                privateSound(2607, 1, 0);
            }
        }
        if(staminaTicks > 0) {
            staminaTicks--;
            if(staminaTicks == 17) {
                sendMessage("<col=8f4808>Your stamina potion is about to expire.");
                privateSound(3120, 3, 0);
            } else if(staminaTicks == 0) {
                sendMessage("<col=8f4808>Your stamina potion has expired.");
                privateSound(2672, 3, 0);
                Config.STAMINA_POTION.set(this, 0);
            }
        }
        if (dragonAxeSpecial > 0) { // really need a better system to handle things like this :)
            --dragonAxeSpecial;
            if (dragonAxeSpecial == 0) {
                player.sendMessage("<col=ff0000>Your woodcutting buff has expired.</col>");
            }
        }
        if (infernalAxeSpecial > 0) {
            --infernalAxeSpecial;
            if (infernalAxeSpecial == 0) {
                player.sendMessage("<col=ff0000>Your infernal woodcutting buff has expired.</col>");
            }
        }

        if (dragonPickaxeSpecial > 0) { // really need a better system to handle things like this :)
            --dragonPickaxeSpecial;
            if (dragonPickaxeSpecial == 0) {
                player.sendMessage("<col=ff0000>Your mining buff has expired.</col>");
            }
        }
        if (infernalPickaxeSpecial > 0) {
            --infernalPickaxeSpecial;
            if (infernalPickaxeSpecial == 0) {
                player.sendMessage("<col=ff0000>Your infernal mining buff has expired.</col>");
            }
        }

        if (lastTimeKilledDonatorBoss > 0) {
            if (System.currentTimeMillis() >= lastTimeKilledDonatorBoss + 86400000) {
                player.sendMessage("<col=ff0000>You can now kill the donator boss again.</col>");
                lastTimeKilledDonatorBoss = 0;
                timesKilledDonatorBoss = 0;
            }
        }

        if(movement.hasMoved()) {
            idleTicks = 0;
            isIdle = false;
        } else if(++idleTicks >= 1000) {
            isIdle = true;
        }
        for (Item item : equipment.getItems()) {
            if (item != null && item.getDef() != null)
                item.getDef().onTick(this, item);
        }

        if(player.wildernessLevel <= 0 && !player.pvpAttackZone && player.snowballPeltOption &&
                !player.getEquipment().hasId(Christmas.SNOWBALL) && !player.getPosition().inBounds(DuelArena.BOUNDS)
                && !player.getPosition().inBounds(DuelArena.CUSTOM_EDGE)) {
            player.setAction(1, null);
            player.snowballPeltOption = false;
        }

    }

    /**
     * Bm
     */
    public void rewardBm(Player target, int amount) {
        String format = String.format("BloodMoneyKill:[Player:[%s] Position:%s IPAddress:[%s] Target:[%s] IPAddress:[%s] Amount:[%d]]", player.getName(), player.getPosition(), player.getIp(), target.getName(), target.getIp(), amount);
        ServerWrapper.log(format);

        if(inventory.add(BLOOD_MONEY, amount) > 0)
            sendFilteredMessage("You received <col=6f0000>" + NumberUtils.formatNumber(amount) + "</col> blood money for killing: " + target.getName() + ".");
        else
            new GroundItem(BLOOD_MONEY, amount).owner(this).position(target.getPosition()).spawn();
    }

    public void rewardBm(NPC target, int amount) {
        String format = String.format("BloodMoneyKillNPC:[Player:[%s] Position:%s IPAddress:[%s] Target:[%s] Amount:[%d]]", player.getName(), player.getPosition(), player.getIp(), target.getDef().name, amount);
        ServerWrapper.log(format);
        if(inventory.add(BLOOD_MONEY, amount) > 0)
            sendFilteredMessage("You received <col=6f0000>" + NumberUtils.formatNumber(amount) + "</col> blood money for killing npc: " + target.getDef().name);
        else
            new GroundItem(BLOOD_MONEY, amount).owner(this).position(target.getPosition()).spawn();
    }

    @Override
    public boolean isPoisonImmune() {
        return super.isPoisonImmune() ||
                player.getEquipment().hasId(SerpentineHelm.SERPENTINE.getChargedId()) ||
                player.getEquipment().hasId(SerpentineHelm.MAGMA.getChargedId()) ||
                player.getEquipment().hasId(SerpentineHelm.TANZANITE.getChargedId());
    }

    @Override
    public boolean isVenomImmune() {
        return super.isVenomImmune() ||
                player.getEquipment().hasId(SerpentineHelm.SERPENTINE.getChargedId()) ||
                player.getEquipment().hasId(SerpentineHelm.MAGMA.getChargedId()) ||
                player.getEquipment().hasId(SerpentineHelm.TANZANITE.getChargedId());
    }

    public GameMode getGameMode() {
        return GameMode.values()[Config.IRONMAN_MODE.get(this)];
    }

    public Title getTitle() {
            return title;
    }

    public House getCurrentHouse() {
        return getPosition().getRegion().getHouse();
    }

    public Room getCurrentRoom() {
        if (getCurrentHouse() == null)
            return null;
        return getCurrentHouse().getCurrentRoom(this);
    }

    public boolean isInOwnHouse() {
        return house != null && getCurrentHouse() == house;
    }


    public void dailyReset() {
        lastLoginDate = LocalDate.now().toString();
        DailyResetListener.executeAll(this);
    }

    public boolean showHitAsExperience() {
        return showHitAsExperience;
    }

    public ItemContainer getPrivateRaidStorage() {
        return privateRaidStorage;
    }

    public ItemContainer getRaidRewards() {
        return raidRewards;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    public void onDialogueContinued() {
        if (onDialogueContinued != null) {
            onDialogueContinued.run();
            System.out.println("Running onDialogueContinued runnable.");
        }
        onDialogueContinued = null;
    }
}