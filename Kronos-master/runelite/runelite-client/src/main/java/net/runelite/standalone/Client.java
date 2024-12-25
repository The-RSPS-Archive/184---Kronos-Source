package net.runelite.standalone;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.awt.Container;
import java.awt.Dimension;
import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Named;
import net.runelite.api.ChatMessageType;
import net.runelite.api.ClanMember;
import net.runelite.api.Constants;
import net.runelite.api.GameState;
import net.runelite.api.HintArrowType;
import net.runelite.api.Ignore;
import net.runelite.api.IndexDataBase;
import net.runelite.api.InventoryID;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.Prayer;
import net.runelite.api.Skill;
import net.runelite.api.VarClientInt;
import net.runelite.api.VarClientStr;
import net.runelite.api.VarPlayer;
import net.runelite.api.Varbits;
import net.runelite.api.WidgetNode;
import net.runelite.api.WorldType;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AreaSoundEffectPlayed;
import net.runelite.api.events.BoostedLevelChanged;
import net.runelite.api.events.CanvasSizeChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ClanChanged;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.DraggingWidgetChanged;
import net.runelite.api.events.ExperienceChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GrandExchangeOfferChanged;
import net.runelite.api.events.Menu;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.MenuShouldLeftClick;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.events.PlayerDespawned;
import net.runelite.api.events.PlayerMenuOptionsChanged;
import net.runelite.api.events.PlayerSpawned;
import net.runelite.api.events.ResizeableChanged;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.api.events.SoundEffectPlayed;
import net.runelite.api.events.UsernameChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.events.WidgetPressed;
import net.runelite.api.hooks.Callbacks;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.api.vars.AccountType;
import net.runelite.api.widgets.JavaScriptCallback;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.RuneLiteProperties;
import net.runelite.client.callback.Hooks;
import net.runelite.client.util.MiscUtils;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSAbstractArchive;
import net.runelite.rs.api.RSAbstractRasterProvider;
import net.runelite.rs.api.RSChatChannel;
import net.runelite.rs.api.RSClanChat;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSClientPreferences;
import net.runelite.rs.api.RSCollisionMap;
import net.runelite.rs.api.RSDecimator;
import net.runelite.rs.api.RSEnumDefinition;
import net.runelite.rs.api.RSEvictingDualNodeHashTable;
import net.runelite.rs.api.RSFont;
import net.runelite.rs.api.RSFrames;
import net.runelite.rs.api.RSFriendSystem;
import net.runelite.rs.api.RSFriendsList;
import net.runelite.rs.api.RSGrandExchangeOffer;
import net.runelite.rs.api.RSIgnoreList;
import net.runelite.rs.api.RSIndexedSprite;
import net.runelite.rs.api.RSItemContainer;
import net.runelite.rs.api.RSItemDefinition;
import net.runelite.rs.api.RSIterableNodeHashTable;
import net.runelite.rs.api.RSLoginType;
import net.runelite.rs.api.RSMenuAction;
import net.runelite.rs.api.RSMessage;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSMouseRecorder;
import net.runelite.rs.api.RSNPC;
import net.runelite.rs.api.RSNPCDefinition;
import net.runelite.rs.api.RSNode;
import net.runelite.rs.api.RSNodeDeque;
import net.runelite.rs.api.RSNodeHashTable;
import net.runelite.rs.api.RSObjectDefinition;
import net.runelite.rs.api.RSPacketBuffer;
import net.runelite.rs.api.RSPcmStream;
import net.runelite.rs.api.RSPcmStreamMixer;
import net.runelite.rs.api.RSPlayer;
import net.runelite.rs.api.RSRawPcmStream;
import net.runelite.rs.api.RSRawSound;
import net.runelite.rs.api.RSScene;
import net.runelite.rs.api.RSScript;
import net.runelite.rs.api.RSScriptEvent;
import net.runelite.rs.api.RSSoundEffect;
import net.runelite.rs.api.RSSprite;
import net.runelite.rs.api.RSTextureProvider;
import net.runelite.rs.api.RSTileItem;
import net.runelite.rs.api.RSUsername;
import net.runelite.rs.api.RSVarbitDefinition;
import net.runelite.rs.api.RSVarcs;
import net.runelite.rs.api.RSWidget;
import net.runelite.rs.api.RSWorld;
import net.runelite.rs.api.RSWorldMap;
import net.runelite.rs.api.RSWorldMapElement;
import netscape.javascript.JSObject;
import org.slf4j.Logger;

@ObfuscatedName("client")
public final class Client extends GameShell implements Usernamed, RSClient {
   @ObfuscatedName("nz")
   static int[] changedSkills;
   @ObfuscatedName("mx")
   @ObfuscatedGetter(
      intValue = -140222153
   )
   static int runEnergy;
   @ObfuscatedName("mo")
   @ObfuscatedGetter(
      intValue = -1946560921
   )
   static int weight;
   @ObfuscatedName("rc")
   @ObfuscatedGetter(
      intValue = -1154982749
   )
   static int viewportOffsetX;
   @ObfuscatedName("rb")
   @ObfuscatedGetter(
      intValue = -654475437
   )
   static int viewportOffsetY;
   @ObfuscatedName("pg")
   @ObfuscatedSignature(
      signature = "[Llf;"
   )
   static Sprite[] mapIcons;
   @ObfuscatedName("ro")
   @ObfuscatedGetter(
      intValue = -1929841361
   )
   static int viewportHeight;
   @ObfuscatedName("sa")
   @ObfuscatedSignature(
      signature = "[Li;"
   )
   static GrandExchangeOffer[] grandExchangeOffers;
   @ObfuscatedName("rf")
   @ObfuscatedGetter(
      intValue = 720626183
   )
   static int viewportZoom;
   @ObfuscatedName("oz")
   @ObfuscatedGetter(
      intValue = 1348758997
   )
   static int gameDrawingMode;
   @ObfuscatedName("nh")
   @ObfuscatedGetter(
      intValue = 1691099913
   )
   static int chatCycle;
   @ObfuscatedName("nd")
   @ObfuscatedGetter(
      intValue = -15935377
   )
   static int changedSkillsCount;
   @ObfuscatedName("rz")
   @ObfuscatedGetter(
      intValue = 806304417
   )
   static int viewportWidth;
   @ObfuscatedName("qh")
   static int[] soundEffectIds;
   @ObfuscatedName("qt")
   @ObfuscatedSignature(
      signature = "[Lcm;"
   )
   static SoundEffect[] soundEffects;
   @ObfuscatedName("qw")
   static int[] queuedSoundEffectLoops;
   @ObfuscatedName("nc")
   static boolean isDraggingWidget;
   @ObfuscatedName("ql")
   static int[] soundLocations;
   @ObfuscatedName("ox")
   static boolean isResizable;
   @ObfuscatedName("qx")
   static int[] queuedSoundEffectDelays;
   @ObfuscatedName("mw")
   @ObfuscatedSignature(
      signature = "Lho;"
   )
   static Widget draggedOnWidget;
   @ObfuscatedName("qj")
   @ObfuscatedGetter(
      intValue = -1492029485
   )
   static int soundEffectVolume;
   @ObfuscatedName("nt")
   @ObfuscatedGetter(
      intValue = 1655703121
   )
   static int cycleCntr;
   @ObfuscatedName("ov")
   static int[] rootWidgetYs;
   @ObfuscatedName("oq")
   @ObfuscatedSignature(
      signature = "Llq;"
   )
   static NodeHashTable widgetClickMasks;
   @ObfuscatedName("oy")
   static int[] rootWidgetXs;
   @ObfuscatedName("px")
   @ObfuscatedGetter(
      intValue = 242208529
   )
   static int destinationY;
   @ObfuscatedName("pl")
   @ObfuscatedGetter(
      intValue = 858875897
   )
   static int destinationX;
   @ObfuscatedName("md")
   @ObfuscatedSignature(
      signature = "Llq;"
   )
   static NodeHashTable interfaceParents;
   @ObfuscatedName("mk")
   @ObfuscatedSignature(
      signature = "Lho;"
   )
   static Widget clickedWidget;
   @ObfuscatedName("qm")
   @ObfuscatedGetter(
      intValue = -1177667309
   )
   static int soundEffectCount;
   @ObfuscatedName("om")
   @ObfuscatedGetter(
      intValue = -556975935
   )
   static int rootWidgetCount;
   @ObfuscatedName("ol")
   @ObfuscatedGetter(
      intValue = -659707133
   )
   static int field846;
   @ObfuscatedName("sj")
   @ObfuscatedGetter(
      intValue = 11492349
   )
   public static int field1113;
   @ObfuscatedName("og")
   @ObfuscatedGetter(
      longValue = -2357868230662320483L
   )
   static long field962;
   @ObfuscatedName("ot")
   static boolean[] field1049;
   @ObfuscatedName("oi")
   static boolean[] field1055;
   @ObfuscatedName("nj")
   static boolean field1025;
   @ObfuscatedName("op")
   static boolean[] field1050;
   @ObfuscatedName("nm")
   static boolean field1022;
   static String field10222;
   @ObfuscatedName("ng")
   @ObfuscatedGetter(
      intValue = -1435027681
   )
   static int field952;
   @ObfuscatedName("mb")
   @ObfuscatedGetter(
      intValue = -1276072491
   )
   static int widgetClickX;
   @ObfuscatedName("mv")
   @ObfuscatedGetter(
      intValue = 2043143959
   )
   static int widgetClickY;
   @ObfuscatedName("qr")
   static boolean field967;
   @ObfuscatedName("na")
   @ObfuscatedGetter(
      intValue = -868722211
   )
   static int field903;
   @ObfuscatedName("mj")
   @ObfuscatedSignature(
      signature = "Lho;"
   )
   static Widget meslayerContinueWidget;
   @ObfuscatedName("me")
   @ObfuscatedSignature(
      signature = "Lho;"
   )
   static Widget clickedWidgetParent;
   @ObfuscatedName("qb")
   @ObfuscatedGetter(
      intValue = -910754411
   )
   static int field874;
   @ObfuscatedName("nl")
   @ObfuscatedGetter(
      intValue = 194862223
   )
   static int field1024;
   @ObfuscatedName("oe")
   static int[] rootWidgetWidths;
   @ObfuscatedName("pw")
   @ObfuscatedGetter(
      intValue = 253399583
   )
   static int publicChatMode;
   @ObfuscatedName("ru")
   @ObfuscatedSignature(
      signature = "Lhr;"
   )
   static PlayerAppearance playerAppearance;
   @ObfuscatedName("ms")
   @ObfuscatedSignature(
      signature = "Lho;"
   )
   static Widget viewportWidget;
   @ObfuscatedName("ow")
   static int[] rootWidgetHeights;
   @ObfuscatedName("my")
   @ObfuscatedGetter(
      intValue = -2028586371
   )
   public static int staffModLevel;
   @ObfuscatedName("nk")
   @ObfuscatedGetter(
      intValue = -1019005303
   )
   static int field1026;
   @ObfuscatedName("np")
   @ObfuscatedGetter(
      intValue = 1046894235
   )
   static int field1027;
   @ObfuscatedName("oh")
   @ObfuscatedSignature(
      signature = "Ljv;"
   )
   static NodeDeque scriptEvents;
   @ObfuscatedName("tb")
   @ObfuscatedSignature(
      signature = "Lbu;"
   )
   static final class65 field1117;
   @ObfuscatedName("nq")
   @ObfuscatedGetter(
      intValue = -861923243
   )
   static int field1041;
   @ObfuscatedName("tg")
   static int[] field1118;
   @ObfuscatedName("nv")
   @ObfuscatedGetter(
      intValue = 826785605
   )
   static int field1040;
   @ObfuscatedName("td")
   static int[] field1119;
   @ObfuscatedName("qz")
   static boolean isCameraLocked;
   @ObfuscatedName("qa")
   @ObfuscatedGetter(
      intValue = 892519291
   )
   static int field1076;
   @ObfuscatedName("no")
   static int[] field1028;
   @ObfuscatedName("nb")
   @ObfuscatedGetter(
      intValue = -1905651311
   )
   static int field1038;
   @ObfuscatedName("nw")
   @ObfuscatedGetter(
      intValue = -330072185
   )
   static int field1033;
   @ObfuscatedName("on")
   @ObfuscatedGetter(
      intValue = -1310890605
   )
   static int mouseWheelRotation;
   @ObfuscatedName("sl")
   @ObfuscatedGetter(
      intValue = -897184099
   )
   static int field1108;
   @ObfuscatedName("sn")
   @ObfuscatedGetter(
      intValue = 2123814989
   )
   static int field1109;
   @ObfuscatedName("qi")
   @ObfuscatedGetter(
      intValue = -114703177
   )
   static int field969;
   @ObfuscatedName("nn")
   @ObfuscatedGetter(
      intValue = 1294400327
   )
   static int field1039;
   @ObfuscatedName("si")
   static ArrayList archiveLoaders;
   @ObfuscatedName("qf")
   @ObfuscatedGetter(
      intValue = 1977131521
   )
   static int minimapState;
   @ObfuscatedName("se")
   @ObfuscatedGetter(
      intValue = 426040267
   )
   static int archiveLoadersDone;
   @ObfuscatedName("rg")
   static short zoomHeight;
   @ObfuscatedName("ri")
   static short field894;
   @ObfuscatedName("mt")
   static boolean playerMod;
   @ObfuscatedName("pa")
   @ObfuscatedGetter(
      intValue = 168765299
   )
   static int tradeChatMode;
   @ObfuscatedName("rt")
   static short field1088;
   @ObfuscatedName("re")
   static short field1095;
   @ObfuscatedName("rd")
   static short field1100;
   @ObfuscatedName("rp")
   static short zoomWidth;
   @ObfuscatedName("pd")
   static String field1115;
   @ObfuscatedName("ra")
   static short field1099;
   @ObfuscatedName("rr")
   static short field1101;
   @ObfuscatedName("ma")
   @ObfuscatedGetter(
      intValue = 42533643
   )
   static int chatEffects;
   @ObfuscatedName("sx")
   @ObfuscatedSignature(
      signature = "Lbb;"
   )
   static GrandExchangeOfferOwnWorldComparator GrandExchangeEvents_worldComparator;
   @ObfuscatedName("mc")
   @ObfuscatedGetter(
      intValue = -1507863629
   )
   static int field1006;
   @ObfuscatedName("mr")
   @ObfuscatedGetter(
      intValue = -382576035
   )
   static int field1081;
   @ObfuscatedName("rm")
   static boolean[] field1043;
   @ObfuscatedName("pz")
   static long[] field1097;
   @ObfuscatedName("rk")
   static int[] field1090;
   @ObfuscatedName("pi")
   @ObfuscatedGetter(
      longValue = -2403274902957123209L
   )
   static long field1068;
   @ObfuscatedName("rl")
   static int[] field1091;
   @ObfuscatedName("mz")
   @ObfuscatedGetter(
      intValue = -1296819247
   )
   static int field1032;
   @ObfuscatedName("nu")
   static int[] field990;
   @ObfuscatedName("rh")
   static int[] field1092;
   @ObfuscatedName("ne")
   @ObfuscatedGetter(
      intValue = -1128248371
   )
   static int field1031;
   @ObfuscatedName("rj")
   static int[] field1093;
   @ObfuscatedName("pr")
   @ObfuscatedGetter(
      intValue = -1812154483
   )
   static int field1064;
   @ObfuscatedName("su")
   @ObfuscatedSignature(
      signature = "Lly;"
   )
   static PlatformInfoProvider platformInfoProvider;
   @ObfuscatedName("pt")
   @ObfuscatedGetter(
      intValue = -1929025607
   )
   static int field851;
   @ObfuscatedName("mq")
   @ObfuscatedGetter(
      intValue = 494577071
   )
   static int followerIndex;
   @ObfuscatedName("sr")
   @ObfuscatedGetter(
      intValue = -1773628049
   )
   static int field1116;
   @ObfuscatedName("pv")
   @ObfuscatedGetter(
      intValue = -1079323191
   )
   static int mapIconCount;
   @ObfuscatedName("ps")
   static int[] mapIconXs;
   @ObfuscatedName("pu")
   static int[] mapIconYs;
   @ObfuscatedName("pf")
   @ObfuscatedGetter(
      intValue = -1367796571
   )
   static int field1065;
   @ObfuscatedName("pm")
   static int[] field1067;
   @ObfuscatedName("po")
   static int[] field1066;
   @ObfuscatedName("os")
   @ObfuscatedSignature(
      signature = "Ljv;"
   )
   static NodeDeque field1044;
   @ObfuscatedName("ob")
   @ObfuscatedSignature(
      signature = "Ljv;"
   )
   static NodeDeque field971;
   @ObfuscatedName("ph")
   static int[] field1059;
   @ObfuscatedName("mh")
   @ObfuscatedGetter(
      intValue = 1431344687
   )
   static int rootInterface;
   @ObfuscatedName("mm")
   static String selectedSpellName;
   @ObfuscatedName("mp")
   @ObfuscatedGetter(
      intValue = -1549196691
   )
   static int field1001;
   @ObfuscatedName("w")
   @ObfuscatedSignature(
      signature = "[Lfa;"
   )
   static CollisionMap[] collisionMaps;
   @ObfuscatedName("at")
   static boolean field1010;
   @ObfuscatedName("be")
   @ObfuscatedGetter(
      intValue = -1434422805
   )
   static int clientType;
   @ObfuscatedName("bh")
   @ObfuscatedGetter(
      intValue = -1282298749
   )
   static int worldProperties;
   @ObfuscatedName("bi")
   @ObfuscatedGetter(
      intValue = -1513198459
   )
   static int gameState;
   @ObfuscatedName("bm")
   @ObfuscatedGetter(
      intValue = -744372313
   )
   static int gameBuild;
   @ObfuscatedName("bs")
   public static boolean isMembersWorld;
   @ObfuscatedName("bu")
   static boolean onMobile;
   @ObfuscatedName("bx")
   @ObfuscatedGetter(
      intValue = 1846758827
   )
   public static int worldId;
   @ObfuscatedName("bz")
   static boolean isLowDetail;
   @ObfuscatedName("ca")
   @ObfuscatedGetter(
      intValue = -204101851
   )
   static int hintArrowY;
   @ObfuscatedName("cb")
   @ObfuscatedGetter(
      intValue = -445977517
   )
   static int field854;
   @ObfuscatedName("cc")
   @ObfuscatedGetter(
      intValue = 2049608033
   )
   static int hintArrowType;
   @ObfuscatedName("cd")
   @ObfuscatedGetter(
      intValue = 1080568465
   )
   static int cycle;
   @ObfuscatedName("ce")
   @ObfuscatedGetter(
      intValue = -1130052375
   )
   static int rebootTimer;
   @ObfuscatedName("cf")
   @ObfuscatedGetter(
      intValue = -1373196151
   )
   static int hintArrowHeight;
   @ObfuscatedName("ch")
   @ObfuscatedSignature(
      signature = "Lcn;"
   )
   static AttackOption playerAttackOption;
   @ObfuscatedName("ci")
   static boolean hadFocus;
   @ObfuscatedName("cl")
   @ObfuscatedGetter(
      intValue = 648504057
   )
   static int hintArrowNpcIndex;
   public static int tickCount;
   public static boolean interpolatePlayerAnimations;
   public static boolean interpolateNpcAnimations;
   public static boolean interpolateObjectAnimations;
   public static boolean interpolateWidgetAnimations;
   public static RSTileItem lastItemDespawn;
   public static boolean stretchedFast;
   public static boolean stretchedKeepAspectRatio;
   public static boolean stretchedEnabled;
   public static boolean stretchedIntegerScaling;
   public static boolean isHidingEntities;
   public static boolean hidePlayers;
   public static boolean hideFriends;
   public static boolean hideLocalPlayer;
   public static boolean hideLocalPlayer2D;
   public static boolean hideNPCs;
   public static boolean hideProjectiles;
   public static boolean hideFriendAttackOptions;
   public static boolean hideFriendCastOptions;
   public static boolean hidePlayers2D;
   public static boolean hideClanMates;
   public static boolean hideDeadNPCs;
   public static int inventoryDragDelay;
   public static int skyboxColor;
   public static boolean hideDisconnect;
   public static boolean hideClanmateAttackOptions;
   public static boolean hideClanmateCastOptions;
   public static Set unhiddenCasts;
   public static Map widgetSpriteOverrides;
   public static Dimension cachedStretchedDimensions;
   public static int[] rl$modelViewportXs;
   public static boolean printMenuActions;
   public static boolean hideNPCs2D;
   public static boolean hideAttackers;
   public static int oldMenuEntryCount;
   public static Map spriteOverrides;
   public static List hideSpecificPlayers;
   public static Dimension cachedRealDimensions;
   public static boolean $assertionsDisabled;
   public static boolean pitchRelaxEnabled;
   public static HashMap hiddenNpcsName;
   public static HashMap hiddenNpcsDeath;
   public static double scalingFactor;
   public static boolean oldIsResized;
   public static int[] rl$modelViewportYs;
   public static int lastSoundEffectCount;
   public static RSScript currentScript;
   public static int lastPitch;
   public static int itemPressedDurationBuffer;
   public static RSPlayer[] oldPlayers;
   public static int lastPitchTarget;
   public static int currentScriptPC;
   @ObfuscatedName("cm")
   static int hintArrowSubY;
   @ObfuscatedName("cn")
   static boolean isLoading;
   @ObfuscatedName("cq")
   @ObfuscatedGetter(
      intValue = 1932039353
   )
   static int hintArrowX;
   @ObfuscatedName("cr")
   @ObfuscatedGetter(
      intValue = -624148243
   )
   static int field855;
   @ObfuscatedName("ct")
   @ObfuscatedGetter(
      longValue = -8037513059658187963L
   )
   static long field856;
   @ObfuscatedName("cu")
   static boolean displayFps;
   @ObfuscatedName("cw")
   @ObfuscatedGetter(
      intValue = 275182897
   )
   static int hintArrowPlayerIndex;
   @ObfuscatedName("cx")
   static int hintArrowSubX;
   @ObfuscatedName("cy")
   @ObfuscatedGetter(
      longValue = 5290188514648997903L
   )
   static long mouseLastLastPressedTimeMillis;
   @ObfuscatedName("dd")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   static Archive archive10;
   @ObfuscatedName("de")
   @ObfuscatedGetter(
      intValue = -900928897
   )
   static int field872;
   @ObfuscatedName("dm")
   @ObfuscatedGetter(
      intValue = 963568873
   )
   static int titleLoadingStage;
   @ObfuscatedName("dt")
   @ObfuscatedSignature(
      signature = "Lcn;"
   )
   static AttackOption npcAttackOption;
   @ObfuscatedName("dy")
   @ObfuscatedGetter(
      intValue = 584801883
   )
   static int js5ConnectState;
   @ObfuscatedName("eb")
   @ObfuscatedGetter(
      intValue = -347893507
   )
   static int npcCount;
   @ObfuscatedName("ed")
   static byte[] randomDatData;
   @ObfuscatedName("ee")
   @ObfuscatedGetter(
      intValue = 460753713
   )
   static int field879;
   @ObfuscatedName("ef")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   static Archive archive17;
   @ObfuscatedName("eo")
   @ObfuscatedGetter(
      intValue = 1787272407
   )
   static int field878;
   @ObfuscatedName("ep")
   @ObfuscatedSignature(
      signature = "Lff;"
   )
   static class158 loginType;
   @ObfuscatedName("eq")
   @ObfuscatedGetter(
      intValue = -14383853
   )
   static int loginState;
   @ObfuscatedName("er")
   @ObfuscatedGetter(
      intValue = -1286153331
   )
   static int field877;
   @ObfuscatedName("et")
   @ObfuscatedSignature(
      signature = "[Lck;"
   )
   static NPC[] npcs;
   @ObfuscatedName("ev")
   static boolean Login_isUsernameRemembered;
   @ObfuscatedName("ew")
   @ObfuscatedGetter(
      intValue = 982456973
   )
   static int js5Errors;
   @ObfuscatedName("ez")
   @ObfuscatedSignature(
      signature = "Lcb;"
   )
   static SecureRandomFuture secureRandomFuture;
   @ObfuscatedName("fa")
   static HashMap fontsMap;
   @ObfuscatedName("fb")
   static int[] npcIndices;
   @ObfuscatedName("fc")
   static boolean field938;
   @ObfuscatedName("fd")
   static int[] field889;
   @ObfuscatedName("fe")
   @ObfuscatedGetter(
      intValue = -1928648709
   )
   static int field841;
   @ObfuscatedName("fh")
   @ObfuscatedSignature(
      signature = "Len;"
   )
   static UrlRequester urlRequester;
   @ObfuscatedName("fj")
   @ObfuscatedGetter(
      intValue = 543460729
   )
   static int field897;
   @ObfuscatedName("fm")
   @ObfuscatedSignature(
      signature = "Ljd;"
   )
   static Timer timer;
   @ObfuscatedName("fo")
   @ObfuscatedSignature(
      signature = "Lcl;"
   )
   public static final PacketWriter packetWriter;
   @ObfuscatedName("fp")
   @ObfuscatedGetter(
      intValue = -355199335
   )
   static int field899;
   @ObfuscatedName("fq")
   @ObfuscatedGetter(
      intValue = -1810258911
   )
   static int logoutTimer;
   @ObfuscatedName("fv")
   static boolean useBufferedSocket;
   @ObfuscatedName("fx")
   @ObfuscatedGetter(
      intValue = -1697289513
   )
   static int field898;
   @ObfuscatedName("gf")
   @ObfuscatedGetter(
      intValue = -2045474947
   )
   static int field906;
   @ObfuscatedName("gl")
   static boolean isInInstance;
   @ObfuscatedName("gm")
   static final int[] field905;
   @ObfuscatedName("gs")
   static int[][][] instanceChunkTemplates;
   @ObfuscatedName("gv")
   @ObfuscatedGetter(
      intValue = 1416543095
   )
   static int field900;
   @ObfuscatedName("gy")
   @ObfuscatedGetter(
      intValue = 1539626603
   )
   static int field901;
   @ObfuscatedName("hb")
   @ObfuscatedGetter(
      intValue = -1280891227
   )
   static int alternativeScrollbarWidth;
   @ObfuscatedName("hc")
   @ObfuscatedGetter(
      intValue = -1192657817
   )
   static int field974;
   @ObfuscatedName("hd")
   @ObfuscatedGetter(
      intValue = -148858111
   )
   static int mouseCamClickedY;
   @ObfuscatedName("hf")
   @ObfuscatedGetter(
      intValue = 1391902969
   )
   static int mouseCamClickedX;
   @ObfuscatedName("hh")
   @ObfuscatedGetter(
      intValue = -1078673109
   )
   static int field907;
   @ObfuscatedName("hi")
   @ObfuscatedGetter(
      intValue = 56611529
   )
   static int camAngleDY;
   @ObfuscatedName("hj")
   static int camFollowHeight;
   @ObfuscatedName("hk")
   @ObfuscatedGetter(
      intValue = -1693078415
   )
   static int field909;
   @ObfuscatedName("hl")
   static boolean field911;
   @ObfuscatedName("hm")
   @ObfuscatedGetter(
      intValue = -851788043
   )
   static int camAngleX;
   @ObfuscatedName("hp")
   @ObfuscatedGetter(
      intValue = -1637520021
   )
   static int field922;
   @ObfuscatedName("ht")
   @ObfuscatedGetter(
      intValue = 1330821821
   )
   static int field908;
   @ObfuscatedName("hu")
   @ObfuscatedGetter(
      intValue = 1157777555
   )
   static int camAngleY;
   @ObfuscatedName("hw")
   @ObfuscatedGetter(
      intValue = 1963305499
   )
   static int oculusOrbState;
   @ObfuscatedName("hx")
   @ObfuscatedGetter(
      intValue = 594555395
   )
   static int camAngleDX;
   @ObfuscatedName("hz")
   @ObfuscatedGetter(
      intValue = -572241931
   )
   static int field910;
   @ObfuscatedName("ia")
   static int[] overheadTextCyclesRemaining;
   @ObfuscatedName("ib")
   @ObfuscatedGetter(
      intValue = -1611804213
   )
   static int oculusOrbSlowedSpeed;
   @ObfuscatedName("ic")
   @ObfuscatedGetter(
      intValue = -527920877
   )
   static int viewportTempY;
   @ObfuscatedName("id")
   static int[] overheadTextXOffsets;
   @ObfuscatedName("if")
   static int[] overheadTextColors;
   @ObfuscatedName("ig")
   static boolean field1087;
   @ObfuscatedName("ih")
   @ObfuscatedGetter(
      intValue = -1473797019
   )
   static int field923;
   @ObfuscatedName("ii")
   @ObfuscatedGetter(
      intValue = -1627292871
   )
   static int viewportTempX;
   @ObfuscatedName("ij")
   static boolean field929;
   @ObfuscatedName("ik")
   @ObfuscatedGetter(
      intValue = -1967946355
   )
   static int overheadTextCount;
   @ObfuscatedName("il")
   @ObfuscatedGetter(
      intValue = -1976595977
   )
   static int viewportDrawCount;
   @ObfuscatedName("im")
   @ObfuscatedGetter(
      intValue = -1513473005
   )
   static int field926;
   @ObfuscatedName("in")
   static int[][] tileLastDrawnActor;
   @ObfuscatedName("io")
   @ObfuscatedGetter(
      intValue = -925368297
   )
   static int oculusOrbNormalSpeed;
   @ObfuscatedName("ip")
   static int[] overheadTextYs;
   @ObfuscatedName("iq")
   @ObfuscatedGetter(
      intValue = 773031175
   )
   static int field928;
   @ObfuscatedName("ir")
   static int[] overheadTextEffects;
   @ObfuscatedName("is")
   @ObfuscatedGetter(
      intValue = 130801757
   )
   static int field930;
   @ObfuscatedName("it")
   static String selectedItemName;
   @ObfuscatedName("iu")
   @ObfuscatedGetter(
      intValue = 2101736993
   )
   static int overheadTextLimit;
   @ObfuscatedName("iv")
   static String[] overheadText;
   @ObfuscatedName("iw")
   static int[] overheadTextAscents;
   @ObfuscatedName("iy")
   static int[] overheadTextXs;
   @ObfuscatedName("jb")
   @ObfuscatedGetter(
      intValue = 1577400495
   )
   static int field842;
   @ObfuscatedName("jc")
   @ObfuscatedGetter(
      intValue = 2007699929
   )
   static int field951;
   @ObfuscatedName("jd")
   @ObfuscatedGetter(
      intValue = -438601773
   )
   static int field953;
   @ObfuscatedName("je")
   @ObfuscatedGetter(
      intValue = 1152437527
   )
   static int mouseCrossState;
   @ObfuscatedName("jf")
   @ObfuscatedGetter(
      intValue = 2052271037
   )
   static int field958;
   @ObfuscatedName("jh")
   @ObfuscatedGetter(
      intValue = 1036462411
   )
   static int dragItemSlotDestination;
   @ObfuscatedName("jj")
   static boolean renderSelf;
   @ObfuscatedName("jl")
   static boolean showLoadingMessages;
   @ObfuscatedName("jm")
   @ObfuscatedSignature(
      signature = "[Lbi;"
   )
   static Player[] players;
   @ObfuscatedName("jo")
   @ObfuscatedGetter(
      intValue = -1643414129
   )
   static int field954;
   @ObfuscatedName("jp")
   @ObfuscatedGetter(
      intValue = -1979890787
   )
   static int dragItemSlotSource;
   @ObfuscatedName("jq")
   @ObfuscatedGetter(
      intValue = 87159269
   )
   static int localPlayerIndex;
   @ObfuscatedName("jr")
   static boolean field956;
   @ObfuscatedName("js")
   @ObfuscatedGetter(
      intValue = 978670267
   )
   static int mouseCrossX;
   @ObfuscatedName("jt")
   @ObfuscatedGetter(
      intValue = -588599615
   )
   static int mouseCrossColor;
   @ObfuscatedName("jv")
   @ObfuscatedGetter(
      intValue = -1556961545
   )
   static int mouseCrossY;
   @ObfuscatedName("jw")
   @ObfuscatedGetter(
      intValue = -1965230699
   )
   static int itemDragDuration;
   @ObfuscatedName("jx")
   static boolean showMouseCross;
   @ObfuscatedName("jy")
   @ObfuscatedGetter(
      intValue = 1781220751
   )
   static int field844;
   @ObfuscatedName("ka")
   @ObfuscatedSignature(
      signature = "Ljv;"
   )
   static NodeDeque pendingSpawns;
   @ObfuscatedName("kb")
   static boolean[] playerOptionsPriorities;
   @ObfuscatedName("kd")
   static int[] experience;
   @ObfuscatedName("kf")
   @ObfuscatedGetter(
      intValue = 722101389
   )
   static int combatTargetPlayerIndex;
   @ObfuscatedName("kg")
   static boolean isMenuOpen;
   @ObfuscatedName("kh")
   static String[] playerMenuActions;
   @ObfuscatedName("ki")
   @ObfuscatedGetter(
      intValue = -2014818077
   )
   static int drawPlayerNames;
   @ObfuscatedName("kj")
   @ObfuscatedSignature(
      signature = "Ljv;"
   )
   static NodeDeque graphicsObjects;
   @ObfuscatedName("kl")
   @ObfuscatedSignature(
      signature = "[[[Ljv;"
   )
   static NodeDeque[][][] groundItems;
   @ObfuscatedName("km")
   static int[] currentLevels;
   @ObfuscatedName("kn")
   static final int[] playerMenuOpcodes;
   @ObfuscatedName("ko")
   @ObfuscatedGetter(
      intValue = 729085131
   )
   static int field848;
   @ObfuscatedName("kr")
   static int[] field966;
   @ObfuscatedName("kt")
   @ObfuscatedSignature(
      signature = "Ljv;"
   )
   static NodeDeque projectiles;
   @ObfuscatedName("kv")
   @ObfuscatedGetter(
      intValue = 421096745
   )
   static int leftClickOpensMenu;
   @ObfuscatedName("ky")
   static int[] levels;
   @ObfuscatedName("kz")
   static int[] defaultRotations;
   @ObfuscatedName("la")
   @ObfuscatedGetter(
      intValue = 1721267483
   )
   static int field996;
   @ObfuscatedName("lb")
   static int[] menuArguments2;
   @ObfuscatedName("ld")
   static int[] menuArguments1;
   @ObfuscatedName("lf")
   static boolean tapToDrop;
   @ObfuscatedName("lg")
   @ObfuscatedGetter(
      intValue = -546279315
   )
   static int viewportY;
   @ObfuscatedName("lh")
   @ObfuscatedGetter(
      intValue = -1819095265
   )
   static int viewportX;
   @ObfuscatedName("li")
   @ObfuscatedGetter(
      intValue = -133249451
   )
   static int selectedSpellChildIndex;
   @ObfuscatedName("lj")
   @ObfuscatedGetter(
      intValue = 509497683
   )
   static int field995;
   @ObfuscatedName("ll")
   static String[] menuActions;
   @ObfuscatedName("lm")
   static boolean followerOpsLowPriority;
   @ObfuscatedName("ln")
   static String[] menuTargets;
   @ObfuscatedName("lo")
   static boolean showMouseOverText;
   @ObfuscatedName("lp")
   static boolean shiftClickDrop;
   @ObfuscatedName("lq")
   static int[] menuIdentifiers;
   @ObfuscatedName("lr")
   @ObfuscatedGetter(
      intValue = 241697491
   )
   static int isItemSelected;
   @ObfuscatedName("ls")
   static int[] menuOpcodes;
   @ObfuscatedName("lw")
   @ObfuscatedGetter(
      intValue = 813297127
   )
   static int menuOptionsCount;
   @ObfuscatedName("lx")
   static boolean[] menuShiftClick;
   @ObfuscatedName("lz")
   static boolean isSpellSelected;
   @ObfuscatedName("mf")
   static String selectedSpellActionName;
   public DrawCallbacks drawCallbacks;
   @Inject
   @Named("Core Logger")
   public Logger logger;
   public boolean gpu;
   public BigInteger modulus;
   @Inject
   public Callbacks callbacks;
   public Cache varbitCache;
   public Cache enumCache;

   static {
      field1010 = true;
      worldId = 1;
      worldProperties = 0;
      gameBuild = 0;
      isMembersWorld = false;
      isLowDetail = false;
      clientType = -1;
      onMobile = false;
      gameState = 0;
      isLoading = true;
      cycle = 0;
      mouseLastLastPressedTimeMillis = 1L;
      field854 = -1;
      field855 = -1;
      field856 = -1L;
      hadFocus = true;
      displayFps = false;
      rebootTimer = 0;
      hintArrowType = 0;
      hintArrowNpcIndex = 0;
      hintArrowPlayerIndex = 0;
      hintArrowX = 0;
      hintArrowY = 0;
      hintArrowHeight = 0;
      hintArrowSubX = 0;
      hintArrowSubY = 0;
      playerAttackOption = AttackOption.AttackOption_hidden;
      npcAttackOption = AttackOption.AttackOption_hidden;
      titleLoadingStage = 0;
      js5ConnectState = 0;
      field872 = 0;
      js5Errors = 0;
      loginState = 0;
      field877 = 0;
      field878 = 0;
      field879 = 0;
      loginType = class158.field1959;
      Login_isUsernameRemembered = false;
      secureRandomFuture = new SecureRandomFuture();
      randomDatData = null;
      npcs = new NPC['耀'];
      npcCount = 0;
      npcIndices = new int['耀'];
      field841 = 0;
      field889 = new int[250];
      packetWriter = new PacketWriter();
      logoutTimer = 0;
      field938 = false;
      useBufferedSocket = true;
      timer = new Timer();
      fontsMap = new HashMap();
      field897 = 0;
      field898 = 1;
      field899 = 0;
      field900 = 1;
      field901 = 0;
      collisionMaps = new CollisionMap[4];
      isInInstance = false;
      instanceChunkTemplates = new int[4][13][13];
      field905 = new int[]{0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3};
      field906 = 0;
      field907 = 2301979;
      field908 = 5063219;
      field909 = 3353893;
      field910 = 7759444;
      field911 = false;
      alternativeScrollbarWidth = 0;
      camAngleX = 128;
      camAngleY = 0;
      camAngleDY = 0;
      camAngleDX = 0;
      mouseCamClickedX = 0;
      mouseCamClickedY = 0;
      oculusOrbState = 0;
      camFollowHeight = 289544614;
      field974 = 0;
      field922 = 0;
      field923 = 0;
      oculusOrbNormalSpeed = 12;
      oculusOrbSlowedSpeed = 6;
      field926 = 0;
      field1087 = false;
      field928 = 0;
      field929 = false;
      field930 = 0;
      overheadTextCount = 0;
      overheadTextLimit = 50;
      overheadTextXs = new int[overheadTextLimit];
      overheadTextYs = new int[overheadTextLimit];
      overheadTextAscents = new int[overheadTextLimit];
      overheadTextXOffsets = new int[overheadTextLimit];
      overheadTextColors = new int[overheadTextLimit];
      overheadTextEffects = new int[overheadTextLimit];
      overheadTextCyclesRemaining = new int[overheadTextLimit];
      overheadText = new String[overheadTextLimit];
      tileLastDrawnActor = new int[104][104];
      viewportDrawCount = 0;
      viewportTempX = -1;
      viewportTempY = -1;
      mouseCrossX = 0;
      mouseCrossY = 0;
      mouseCrossState = 0;
      mouseCrossColor = 0;
      showMouseCross = true;
      field958 = 0;
      field951 = 0;
      dragItemSlotSource = 0;
      field953 = 0;
      field954 = 0;
      dragItemSlotDestination = 0;
      field956 = false;
      itemDragDuration = 0;
      field842 = 0;
      showLoadingMessages = true;
      players = new Player[2048];
      localPlayerIndex = -1;
      field844 = 0;
      renderSelf = true;
      drawPlayerNames = 0;
      field848 = 0;
      field966 = new int[1000];
      playerMenuOpcodes = new int[]{44, 45, 46, 47, 48, 49, 50, 51};
      playerMenuActions = new String[8];
      playerOptionsPriorities = new boolean[8];
      defaultRotations = new int[]{768, 1024, 1280, 512, 1536, 256, 0, 1792};
      combatTargetPlayerIndex = -1;
      groundItems = new NodeDeque[4][104][104];
      pendingSpawns = new NodeDeque();
      projectiles = new NodeDeque();
      graphicsObjects = new NodeDeque();
      currentLevels = new int[25];
      levels = new int[25];
      experience = new int[25];
      leftClickOpensMenu = 0;
      isMenuOpen = false;
      menuOptionsCount = 0;
      menuArguments1 = new int[500];
      menuArguments2 = new int[500];
      menuOpcodes = new int[500];
      menuIdentifiers = new int[500];
      menuActions = new String[500];
      menuTargets = new String[500];
      menuShiftClick = new boolean[500];
      followerOpsLowPriority = false;
      shiftClickDrop = false;
      tapToDrop = false;
      showMouseOverText = true;
      viewportX = -1;
      viewportY = -1;
      field995 = 0;
      field996 = 50;
      isItemSelected = 0;
      selectedItemName = null;
      isSpellSelected = false;
      selectedSpellChildIndex = -1;
      field1001 = -1;
      selectedSpellActionName = null;
      selectedSpellName = null;
      rootInterface = -1;
      interfaceParents = new NodeHashTable(8);
      field1006 = 0;
      field1032 = -1;
      chatEffects = 0;
      field1081 = 0;
      meslayerContinueWidget = null;
      runEnergy = 0;
      weight = 0;
      staffModLevel = 0;
      followerIndex = -1;
      playerMod = false;
      viewportWidget = null;
      clickedWidget = null;
      clickedWidgetParent = null;
      widgetClickX = 0;
      widgetClickY = 0;
      draggedOnWidget = null;
      field1022 = false;
      field903 = -1;
      field1024 = -1;
      field1025 = false;
      field1026 = -1;
      field1027 = -1;
      isDraggingWidget = false;
      cycleCntr = 1;
      field990 = new int[32];
      field1031 = 0;
      field1028 = new int[32];
      field1033 = 0;
      changedSkills = new int[32];
      changedSkillsCount = 0;
      chatCycle = 0;
      field952 = 0;
      field1038 = 0;
      field1039 = 0;
      field1040 = 0;
      field1041 = 0;
      mouseWheelRotation = 0;
      scriptEvents = new NodeDeque();
      field1044 = new NodeDeque();
      field971 = new NodeDeque();
      widgetClickMasks = new NodeHashTable(512);
      rootWidgetCount = 0;
      field846 = -2;
      field1049 = new boolean[100];
      field1050 = new boolean[100];
      field1055 = new boolean[100];
      rootWidgetXs = new int[100];
      rootWidgetYs = new int[100];
      rootWidgetWidths = new int[100];
      rootWidgetHeights = new int[100];
      gameDrawingMode = 0;
      field962 = 0L;
      isResizable = true;
      field1059 = new int[]{16776960, 16711680, 65280, 65535, 16711935, 16777215};
      publicChatMode = 0;
      tradeChatMode = 0;
      field1115 = "";
      field1097 = new long[100];
      field1064 = 0;
      field1065 = 0;
      field1066 = new int[128];
      field1067 = new int[128];
      field1068 = -1L;
      field851 = -1;
      mapIconCount = 0;
      mapIconXs = new int[1000];
      mapIconYs = new int[1000];
      mapIcons = new Sprite[1000];
      destinationX = 0;
      destinationY = 0;
      minimapState = 0;
      field969 = 255;
      field874 = -1;
      field967 = false;
      soundEffectVolume = 127;
      field1076 = 127;
      soundEffectCount = 0;
      soundEffectIds = new int[50];
      queuedSoundEffectLoops = new int[50];
      queuedSoundEffectDelays = new int[50];
      soundLocations = new int[50];
      soundEffects = new SoundEffect[50];
      isCameraLocked = false;
      field1043 = new boolean[5];
      field1090 = new int[5];
      field1091 = new int[5];
      field1092 = new int[5];
      field1093 = new int[5];
      field1088 = 256;
      field1095 = 205;
      zoomHeight = 256;
      zoomWidth = 320;
      field894 = 1;
      field1099 = 32767;
      field1100 = 1;
      field1101 = 32767;
      viewportOffsetX = 0;
      viewportOffsetY = 0;
      viewportWidth = 0;
      viewportHeight = 0;
      viewportZoom = 0;
      playerAppearance = new PlayerAppearance();
      field1108 = -1;
      field1109 = -1;
      platformInfoProvider = new DesktopPlatformInfoProvider();
      grandExchangeOffers = new GrandExchangeOffer[8];
      GrandExchangeEvents_worldComparator = new GrandExchangeOfferOwnWorldComparator();
      field1113 = -1;
      archiveLoaders = new ArrayList(10);
      archiveLoadersDone = 0;
      field1116 = 0;
      field1117 = new class65();
      field1118 = new int[50];
      field1119 = new int[50];
      rl$$clinit();
      rl$$clinit1();
      rl$$clinit2();
      rl$$clinit3();
      rl$$clinit4();
      rl$$clinit5();
      rl$$clinit6();
   }

   public Client() {
      this.rl$$init();
      this.rl$$init1();
   }

   @ObfuscatedName("ab")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-60"
   )
   protected final void vmethod1937() {
      ++cycle;
      this.method1650();

      while(true) {
         NodeDeque var2 = ArchiveDiskActionHandler.ArchiveDiskActionHandler_requestQueue;
         ArchiveDiskAction var1;
         synchronized(ArchiveDiskActionHandler.ArchiveDiskActionHandler_requestQueue) {
            var1 = (ArchiveDiskAction)ArchiveDiskActionHandler.ArchiveDiskActionHandler_responseQueue.method5108();
         }

         if(var1 == null) {
            int var5;
            try {
               if(class197.field2173 == 1) {
                  var5 = class38.midiPcmStream.method3607();
                  if(var5 > 0 && class38.midiPcmStream.method3551()) {
                     var5 -= MusicPatchNode2.field2119;
                     if(var5 < 0) {
                        var5 = 0;
                     }

                     class38.midiPcmStream.method3622(var5);
                  } else {
                     class38.midiPcmStream.method3628();
                     class38.midiPcmStream.method3537();
                     if(class197.musicTrackArchive != null) {
                        class197.field2173 = 2;
                     } else {
                        class197.field2173 = 0;
                     }

                     class197.musicTrack = null;
                     FriendLoginUpdate.soundCache = null;
                  }
               }
            } catch (Exception var9) {
               var9.printStackTrace();
               class38.midiPcmStream.method3628();
               class197.field2173 = 0;
               class197.musicTrack = null;
               FriendLoginUpdate.soundCache = null;
               class197.musicTrackArchive = null;
            }

            WorldMapID.method687();
            KeyHandler var10 = KeyHandler.KeyHandler_instance;
            synchronized(KeyHandler.KeyHandler_instance) {
               ++KeyHandler.KeyHandler_idleCycles;
               KeyHandler.field179 = KeyHandler.field181;
               KeyHandler.field162 = 0;
               int var6;
               if(KeyHandler.field174 >= 0) {
                  while(KeyHandler.field174 != KeyHandler.field173) {
                     var6 = KeyHandler.field166[KeyHandler.field173];
                     KeyHandler.field173 = KeyHandler.field173 + 1 & 127;
                     if(var6 < 0) {
                        KeyHandler.KeyHandler_pressedKeys[~var6] = false;
                     } else {
                        if(!KeyHandler.KeyHandler_pressedKeys[var6] && KeyHandler.field162 < KeyHandler.field177.length - 1) {
                           KeyHandler.field177[++KeyHandler.field162 - 1] = var6;
                        }

                        KeyHandler.KeyHandler_pressedKeys[var6] = true;
                     }
                  }
               } else {
                  for(var6 = 0; var6 < 112; ++var6) {
                     KeyHandler.KeyHandler_pressedKeys[var6] = false;
                  }

                  KeyHandler.field174 = KeyHandler.field173;
               }

               if(KeyHandler.field162 > 0) {
                  KeyHandler.KeyHandler_idleCycles = 0;
               }

               KeyHandler.field181 = KeyHandler.field172;
            }

            VertexNormal.method2465();
            if(KeyHandler.mouseWheel != null) {
               var5 = KeyHandler.mouseWheel.vmethod3454();
               mouseWheelRotation = var5;
            }

            if(gameState == 0) {
               class188.method3738();
               WorldMapDecoration.method5191();
            } else if(gameState == 5) {
               LoginPacket.method3724(this);
               class188.method3738();
               WorldMapDecoration.method5191();
            } else if(gameState != 10 && gameState != 11) {
               if(gameState == 20) {
                  LoginPacket.method3724(this);
                  this.method1653();
               } else if(gameState == 25) {
                  KeyHandler.method482();
               }
            } else {
               LoginPacket.method3724(this);
            }

            if(gameState == 30) {
               this.method1654();
            } else if(gameState == 40 || gameState == 45) {
               this.method1653();
            }

            return;
         }

         var1.archive.method4287(var1.archiveDisk, (int)var1.key, var1.data, false);
      }
   }

   @ObfuscatedName("am")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-2052837160"
   )
   protected final void vmethod1643() {
   }

   @ObfuscatedName("ar")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "16"
   )
   protected final void vmethod1646() {
      int[] var1 = new int[]{20, 260, 10000};
      int[] var2 = new int[]{1000, 100, 500};
      if(var1 != null && var2 != null) {
         ByteArrayPool.ByteArrayPool_alternativeSizes = var1;
         ByteArrayPool.ByteArrayPool_altSizeArrayCounts = new int[var1.length];
         class86.ByteArrayPool_arrays = new byte[var1.length][][];

         for(int var5 = 0; var5 < ByteArrayPool.ByteArrayPool_alternativeSizes.length; ++var5) {
            class86.ByteArrayPool_arrays[var5] = new byte[var2[var5]][];
         }
      } else {
         ByteArrayPool.ByteArrayPool_alternativeSizes = null;
         ByteArrayPool.ByteArrayPool_altSizeArrayCounts = null;
         class86.ByteArrayPool_arrays = null;
      }

      //TODO: Modified OLD:[43594]
      HitSplatDefinition.port1 = gameBuild == 0?7306:worldId + 40000;
      //TODO: Modified OLD:[443]
      BZip2State.port2 = gameBuild == 0?7306:worldId + 50000;
      NPC.port3 = HitSplatDefinition.port1;
      HorizontalAlignment.field3270 = class211.field2494;
      PlayerAppearance.field2742 = class211.field2493;
      PlayerAppearance.field2748 = class211.field2495;
      DevicePcmPlayerProvider.field153 = class211.field2492;
      urlRequester = new UrlRequester();
      this.method959();
      this.method960();
      KeyHandler.mouseWheel = this.method957();
      WorldMapData_0.masterDisk = new ArchiveDisk(255, JagexCache.JagexCache_dat2File, JagexCache.JagexCache_idx255File, 500000);
      AbstractArchive.clientPreferences = ItemDefinition.method4600();
      this.method1039();
      String var4 = WorldMapManager.null_string;
      class37.applet = this;
      if(var4 != null) {
         class37.field279 = var4;
      }

      if(gameBuild != 0) {
         displayFps = true;
      }

      UserComparator8.method2878(AbstractArchive.clientPreferences.windowMode);
      Tiles.friendSystem = new FriendSystem(WorldMapSection1.loginType);
   }

   @ObfuscatedName("as")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "87"
   )
   protected final void vmethod1699() {
      if(class197.varcs.method2173()) {
         class197.varcs.method2168();
      }

      if(WorldMapLabel.mouseRecorder != null) {
         WorldMapLabel.mouseRecorder.isRunning = false;
      }

      WorldMapLabel.mouseRecorder = null;
      packetWriter.method1623();
      class191.method3763();
      method1901();
      KeyHandler.mouseWheel = null;
      if(class213.pcmPlayer0 != null) {
         class213.pcmPlayer0.method2707();
      }

      if(GrandExchangeOfferWorldComparator.pcmPlayer1 != null) {
         GrandExchangeOfferWorldComparator.pcmPlayer1.method2707();
      }

      class93.method2215();
      UrlRequest.method2915();
      if(urlRequester != null) {
         urlRequester.method3051();
         urlRequester = null;
      }

      Occluder.method3081();
   }

   @ObfuscatedName("ax")
   @ObfuscatedSignature(
      signature = "(ZI)V",
      garbageValue = "-1679134607"
   )
   protected final void vmethod1732(boolean var1) {
      this.draw(var1);

      boolean var2;
      label169: {
         try {
            if(class197.field2173 == 2) {
               if(class197.musicTrack == null) {
                  class197.musicTrack = MusicTrack.method3731(class197.musicTrackArchive, class183.musicTrackGroupId, class38.musicTrackFileId);
                  if(class197.musicTrack == null) {
                     var2 = false;
                     break label169;
                  }
               }

               if(FriendLoginUpdate.soundCache == null) {
                  FriendLoginUpdate.soundCache = new SoundCache(class197.soundEffectsArchive, SecureRandomCallable.musicSamplesArchive);
               }

               if(class38.midiPcmStream.method3533(class197.musicTrack, class197.musicPatchesArchive, FriendLoginUpdate.soundCache, 22050)) {
                  class38.midiPcmStream.method3643();
                  class38.midiPcmStream.method3622(TileItem.field816);
                  class38.midiPcmStream.method3615(class197.musicTrack, WorldMapSectionType.musicTrackBoolean);
                  class197.field2173 = 0;
                  class197.musicTrack = null;
                  FriendLoginUpdate.soundCache = null;
                  class197.musicTrackArchive = null;
                  var2 = true;
                  break label169;
               }
            }
         } catch (Exception var6) {
            var6.printStackTrace();
            class38.midiPcmStream.method3628();
            class197.field2173 = 0;
            class197.musicTrack = null;
            FriendLoginUpdate.soundCache = null;
            class197.musicTrackArchive = null;
         }

         var2 = false;
      }

      if(var2 && field967 && class213.pcmPlayer0 != null) {
         class213.pcmPlayer0.method2708();
      }

      if((gameState == 10 || gameState == 20 || gameState == 30) && 0L != field962 && class33.method680() > field962) {
         UserComparator8.method2878(class256.method4656());
      }

      int var4;
      if(var1) {
         for(var4 = 0; var4 < 100; ++var4) {
            field1049[var4] = true;
         }
      }

      if(gameState == 0) {
         this.method1082(Login.Login_loadingPercent, Login.Login_loadingText, var1);
      } else if(gameState == 5) {
         VarbitDefinition.method4260(class170.fontBold12, GrandExchangeOfferOwnWorldComparator.fontPlain11, GraphicsDefaults.fontPlain12);
      } else if(gameState != 10 && gameState != 11) {
         if(gameState == 20) {
            VarbitDefinition.method4260(class170.fontBold12, GrandExchangeOfferOwnWorldComparator.fontPlain11, GraphicsDefaults.fontPlain12);
         } else if(gameState == 25) {
            if(field901 == 1) {
               if(field897 > field898) {
                  field898 = field897;
               }

               var4 = (field898 * 50 - field897 * 50) / field898;
               WorldMapSprite.method784("Loading - please wait." + "<br>" + " (" + var4 + "%" + ")", false);
            } else if(field901 == 2) {
               if(field899 > field900) {
                  field900 = field899;
               }

               var4 = (field900 * 50 - field899 * 50) / field900 + 50;
               WorldMapSprite.method784("Loading - please wait." + "<br>" + " (" + var4 + "%" + ")", false);
            } else {
               WorldMapSprite.method784("Loading - please wait.", false);
            }
         } else if(gameState == 30) {
            this.method1657();
         } else if(gameState == 40) {
            WorldMapSprite.method784("Connection lost" + "<br>" + "Please wait - attempting to reestablish", false);
         } else if(gameState == 45) {
            WorldMapSprite.method784("Please wait...", false);
         }
      } else {
         VarbitDefinition.method4260(class170.fontBold12, GrandExchangeOfferOwnWorldComparator.fontPlain11, GraphicsDefaults.fontPlain12);
      }

      if(gameState == 30 && gameDrawingMode == 0 && !var1 && !isResizable) {
         for(var4 = 0; var4 < rootWidgetCount; ++var4) {
            if(field1050[var4]) {
               class30.rasterProvider.vmethod6276(rootWidgetXs[var4], rootWidgetYs[var4], rootWidgetWidths[var4], rootWidgetHeights[var4]);
               field1050[var4] = false;
            }
         }
      } else if(gameState > 0) {
         class30.rasterProvider.vmethod6275(0, 0);

         for(var4 = 0; var4 < rootWidgetCount; ++var4) {
            field1050[var4] = false;
         }
      }

   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "2027797509"
   )
   protected final void vmethod1819() {
      field962 = class33.method680() + 500L;
      this.method1656();
      if(rootInterface != -1) {
         this.method1874(true);
      }

   }

   public RSItemContainer getItemContainer(InventoryID var1) {
      RSNodeHashTable var2 = this.getItemContainers();
      return (RSItemContainer)var2.get((long)var1.getId());
   }

   public RSSprite[] getSprites(IndexDataBase var1, int var2, int var3) {
      RSAbstractArchive var4 = (RSAbstractArchive)var1;
      byte[] var5 = var4.getConfigData(var2, var3);
      if(var5 == null) {
         return null;
      } else {
         this.decodeSprite(var5);
         int var6 = this.getIndexedSpriteCount();
         int var7 = this.getIndexedSpriteWidth();
         int var8 = this.getIndexedSpriteHeight();
         int[] var9 = this.getIndexedSpriteOffsetXs();
         int[] var10 = this.getIndexedSpriteOffsetYs();
         int[] var11 = this.getIndexedSpriteWidths();
         int[] var12 = this.getIndexedSpriteHeights();
         byte[][] var13 = this.getSpritePixels();
         int[] var14 = this.getIndexedSpritePalette();
         Sprite[] var15 = new Sprite[var6];

         for(int var16 = 0; var16 < var6; ++var16) {
            int var17 = var11[var16];
            int var18 = var12[var16];
            byte[] var19 = var13[var16];
            int[] var20 = new int[var17 * var18];
            RSSprite var21 = this.createSprite(var20, var17, var18);
            var21.setMaxHeight(var8);
            var21.setMaxWidth(var7);
            var21.setOffsetX(var9[var16]);
            var21.setOffsetY(var10[var16]);

            for(int var22 = 0; var22 < var17 * var18; ++var22) {
               var20[var22] = var14[var19[var22] & 255];
            }

            var15[var16] = (Sprite)var21;
         }

         this.setIndexedSpriteOffsetXs((int[])null);
         this.setIndexedSpriteOffsetYs((int[])null);
         this.setIndexedSpriteWidths((int[])null);
         this.setIndexedSpriteHeights((int[])null);
         this.setIndexedSpritePalette((int[])null);
         this.setSpritePixels((byte[][])null);
         return var15;
      }
   }

   public RSSprite drawInstanceMap(int var1) {
      RSSprite var2 = this.createSprite(new int[262144], 512, 512);
      RSSprite var3 = this.getMinimapSprite();
      RSScene var4 = this.getScene();
      int[] var5 = var2.getPixels();
      byte[][][] var6 = this.getTileSettings();

      try {
         this.setMinimapSprite(var2);

         int var8;
         int var9;
         for(var8 = 1; var8 < 103; ++var8) {
            int var7 = (103 - var8) * 2048 + 24628;

            for(var9 = 1; var9 < 103; ++var9) {
               if((var6[var1][var9][var8] & 24) == 0) {
                  var4.drawTile(var5, var7, 512, var1, var9, var8);
               }

               if(var1 < 3 && (var6[var1 + 1][var9][var8] & 8) != 0) {
                  var4.drawTile(var5, var7, 512, var1 + 1, var9, var8);
               }

               var7 += 4;
            }
         }

         var8 = (238 + (int)(Math.random() * 20.0D) - 10 << 16) + (238 + (int)(Math.random() * 20.0D) - 10 << 8) + (238 + (int)(Math.random() * 20.0D) - 10);
         var9 = 238 + (int)(Math.random() * 20.0D) - 10 << 16;
         var2.setRaster();

         for(int var10 = 1; var10 < 103; ++var10) {
            for(int var11 = 1; var11 < 103; ++var11) {
               if((var6[var1][var11][var10] & 24) == 0) {
                  this.drawObject(var1, var11, var10, var8, var9);
               }

               if(var1 < 3 && (var6[var1 + 1][var11][var10] & 8) != 0) {
                  this.drawObject(var1 + 1, var11, var10, var8, var9);
               }
            }
         }
      } finally {
         this.getBufferProvider().setRaster();
         this.setMinimapSprite(var3);
      }

      return var2;
   }

   public void setRSModIcons(RSIndexedSprite[] var1) {
      AbstractFont.AbstractFont_modIconSprites = (IndexedSprite[])var1;
   }

   public int getCycleCntr() {
      return cycleCntr;
   }

   public int getDestinationX() {
      return destinationX;
   }

   public int getFlags() {
      return worldProperties;
   }

   public void setLowMemory(boolean var1) {
      isLowDetail = var1;
   }

   public int getRSGameState() {
      return gameState;
   }

   public int getNpcIndexesCount() {
      return npcCount;
   }

   public int[] getBoostedSkillLevels() {
      return currentLevels;
   }

   public int[] getRealSkillLevels() {
      return levels;
   }

   public int getWidgetRoot() {
      return rootInterface;
   }

   public RSAbstractArchive getIndexCache4() {
      return GrandExchangeOfferAgeComparator.archive4;
   }

   public RSEvictingDualNodeHashTable getHealthBarCache() {
      return HealthBarDefinition.HealthBarDefinition_cached;
   }

   public int getPlayerIndexesCount() {
      return Players.Players_count;
   }

   public int getSelectedSceneTileX() {
      return Scene.Scene_selectedX;
   }

   public int getMenuX() {
      return UrlRequester.menuX;
   }

   public boolean isStretchedEnabled() {
      return stretchedEnabled;
   }

   public void playSoundEffect(int var1, int var2, int var3, int var4, int var5) {
      int var6 = ((var2 & 255) << 16) + ((var3 & 255) << 8) + (var4 & 255);
      int[] var7 = this.getQueuedSoundEffectIDs();
      int[] var8 = this.getQueuedSoundEffectLoops();
      int[] var9 = this.getQueuedSoundEffectDelays();
      RSSoundEffect[] var10 = this.getAudioEffects();
      int[] var11 = this.getSoundLocations();
      int var12 = this.getQueuedSoundEffectCount();
      var7[var12] = var1;
      var8[var12] = 1;
      var9[var12] = var5;
      var10[var12] = null;
      var11[var12] = var6;
      this.setQueuedSoundEffectCount(var12 + 1);
   }

   public RSNodeHashTable getItemContainers() {
      return ItemContainer.itemContainers;
   }

   public int getMouseX() {
      return MouseHandler.MouseHandler_xVolatile;
   }

   public RSLoginType getLoginType() {
      return WorldMapSection1.loginType;
   }

   public HintArrowType getHintArrowType() {
      int var1 = ViewportMouse.client.getHintArrowTargetType();
      return var1 == HintArrowType.NPC.getValue()?HintArrowType.NPC:(var1 == HintArrowType.PLAYER.getValue()?HintArrowType.PLAYER:(var1 == HintArrowType.WORLD_POSITION.getValue()?HintArrowType.WORLD_POSITION:HintArrowType.NONE));
   }

   public void draw(boolean var1) {
      this.callbacks.clientMainLoop();
   }

   public int getVar(Varbits var1) {
      int var2 = var1.getId();
      return this.getVarbitValue(this.getVarps(), var2);
   }

   public int[] getSkillExperiences() {
      return experience;
   }

   public void menuOpened(int var1, int var2) {
      MenuOpened var3 = new MenuOpened();
      var3.setMenuEntries(this.getMenuEntries());
      this.callbacks.post(MenuOpened.class, var3);
      if(var3.isModified()) {
         this.setMenuEntries(var3.getMenuEntries());
      }

   }

   public Map getVarcMap() {
      return this.getVarcs().getVarcMap();
   }

   public boolean isResized() {
      return isResizable;
   }

   public RSWidget[][] getWidgets() {
      return UserComparator5.Widget_interfaceComponents;
   }

   public final boolean copy$shouldLeftClickOpenMenu(byte var1) {
      int var2 = BuddyRankComparator.method3386();
      return (leftClickOpensMenu == 1 && menuOptionsCount > 2 || ParamDefinition.method4325(var2)) && !menuShiftClick[var2];
   }

   public RSVarcs getVarcs() {
      return class197.varcs;
   }

   public RSClanChat getClanMemberManager() {
      return Varps.clanChat;
   }

   public RSSprite createSprite(int[] var1, int var2, int var3) {
      return new Sprite(var1, var2, var3);
   }

   public int[] getVarps() {
      return Varps.Varps_main;
   }

   public String[] getMenuOptions() {
      return menuActions;
   }

   public RSFriendSystem getFriendManager() {
      return Tiles.friendSystem;
   }

   public int getMenuOptionCount() {
      return menuOptionsCount;
   }

   public RSNodeDeque getProjectilesDeque() {
      return projectiles;
   }

   public RSNodeDeque getGraphicsObjectDeque() {
      return graphicsObjects;
   }

   public void addChatMessage(int var1, String var2, String var3, String var4) {
      WorldMapData_1.method514(var1, var2, var3, var4);
   }

   public Callbacks getCallbacks() {
      return this.callbacks;
   }

   public MenuEntry[] getMenuEntries() {
      int var1 = this.getMenuOptionCount();
      String[] var2 = this.getMenuOptions();
      String[] var3 = this.getMenuTargets();
      int[] var4 = this.getMenuIdentifiers();
      int[] var5 = this.getMenuOpcodes();
      int[] var6 = this.getMenuArguments1();
      int[] var7 = this.getMenuArguments2();
      boolean[] var8 = this.getMenuForceLeftClick();
      MenuEntry[] var9 = new MenuEntry[var1];

      for(int var10 = 0; var10 < var1; ++var10) {
         MenuEntry var11 = var9[var10] = new MenuEntry();
         var11.setOption(var2[var10]);
         var11.setTarget(var3[var10]);
         var11.setIdentifier(var4[var10]);
         var11.setOpcode(var5[var10]);
         var11.setParam0(var6[var10]);
         var11.setParam1(var7[var10]);
         var11.setForceLeftClick(var8[var10]);
      }

      return var9;
   }

   private void rl$$init() {
      this.enumCache = CacheBuilder.newBuilder().maximumSize(64L).build();
      this.modulus = new BigInteger("94210824259843347324509385276594109263523823612210415282840685497179394322370180677069205378760490069724955139827325518162089726630921395369270393801925644637806226306156731189625154078707248525519618118185550146216513714101970726787284175941436804270501308516733103597242337227056455402809871503542425244523");
   }

   public int getMenuY() {
      return class37.menuY;
   }

   public int getDestinationY() {
      return destinationY;
   }

   public void setChatCycle(int var1) {
      chatCycle = var1;
   }

   public int[] getNpcIndices() {
      return npcIndices;
   }

   public int[] getPlayerIndices() {
      return Players.Players_indices;
   }

   public void setSceneLowMemory(boolean var1) {
      Scene.Scene_isLowDetail = var1;
   }

   public int getSelectedSceneTileY() {
      return Scene.Scene_selectedY;
   }

   public RSSoundEffect getTrack(RSAbstractArchive var1, int var2, int var3) {
      return SoundEffect.method2092((AbstractArchive)var1, var2, var3);
   }

   public void invokeMenuAction(int var1, int var2, int var3, int var4, String var5, String var6, int var7, int var8) {
      ViewportMouse.client.sendMenuAction(var1, var2, var3, var4, var5, "!AUTHENTIC" + var6, var7, var8);
   }

   public RSUsername createName(String var1, RSLoginType var2) {
      return new Username(var1, (LoginType)var2);
   }

   public int getMouseY() {
      return MouseHandler.MouseHandler_yVolatile;
   }

   public RSSprite getMinimapSprite() {
      return ObjectSound.sceneMinimapSprite;
   }

   public int get3dZoom() {
      return Rasterizer3D.Rasterizer3D_zoom;
   }

   public void decodeSprite(byte[] var1) {
      Tiles.method1200(var1);
   }

   public int[] getQueuedSoundEffectIDs() {
      return soundEffectIds;
   }

   public net.runelite.api.widgets.Widget getWidget(int var1, int var2) {
      RSWidget[][] var3 = this.getWidgets();
      if(var3 != null && var3.length > var1) {
         RSWidget[] var4 = var3[var1];
         return var4 != null && var4.length > var2?var4[var2]:null;
      } else {
         return null;
      }
   }

   public String[] getMenuTargets() {
      return menuTargets;
   }

   public void setVarbitValue(int[] var1, int var2, int var3) {
      RSVarbitDefinition var4 = (RSVarbitDefinition)this.varbitCache.getIfPresent(Integer.valueOf(var2));
      if(var4 == null) {
         ViewportMouse.client.getVarbit(var2);
         RSEvictingDualNodeHashTable var5 = ViewportMouse.client.getVarbitCache();
         var4 = (RSVarbitDefinition)var5.get((long)var2);
         this.varbitCache.put(Integer.valueOf(var2), var4);
      }

      int var8 = var4.getLeastSignificantBit();
      int var6 = var4.getMostSignificantBit();
      int var7 = (1 << var6 - var8 + 1) - 1;
      var1[var4.getIndex()] = var1[var4.getIndex()] & ~(var7 << var8) | (var3 & var7) << var8;
   }

   public RSEvictingDualNodeHashTable getHealthBarSpriteCache() {
      return HealthBarDefinition.HealthBarDefinition_cachedSprites;
   }

   public net.runelite.api.widgets.Widget getWidget(WidgetInfo var1) {
      int var2 = var1.getGroupId();
      int var3 = var1.getChildId();
      return this.getWidget(var2, var3);
   }

   public int getVarbitValue(int[] var1, int var2) {
      if(!$assertionsDisabled && !ViewportMouse.client.isClientThread()) {
         throw new AssertionError();
      } else {
         RSVarbitDefinition var3 = (RSVarbitDefinition)this.varbitCache.getIfPresent(Integer.valueOf(var2));
         if(var3 == null) {
            ViewportMouse.client.getVarbit(var2);
            RSEvictingDualNodeHashTable var4 = ViewportMouse.client.getVarbitCache();
            var3 = (RSVarbitDefinition)var4.get((long)var2);
            this.varbitCache.put(Integer.valueOf(var2), var3);
         }

         if(var3.getIndex() == 0 && var3.getLeastSignificantBit() == 0 && var3.getMostSignificantBit() == 0) {
            throw new IndexOutOfBoundsException("Varbit " + var2 + " does not exist");
         } else {
            int var8 = var1[var3.getIndex()];
            int var5 = var3.getLeastSignificantBit();
            int var6 = var3.getMostSignificantBit();
            int var7 = (1 << var6 - var5 + 1) - 1;
            return var8 >> var5 & var7;
         }
      }
   }

   private void rl$$init1() {
      this.varbitCache = CacheBuilder.newBuilder().maximumSize(128L).build();
   }

   public RSNPC[] getCachedNPCs() {
      return npcs;
   }

   public RSPlayer[] getCachedPlayers() {
      return players;
   }

   public int getMenuWidth() {
      return FriendSystem.menuWidth;
   }

   public void setAudioHighMemory(boolean var1) {
      PcmPlayer.PcmPlayer_stereo = var1;
   }

   public int getIndexedSpriteCount() {
      return class329.SpriteBuffer_spriteCount;
   }

   public int[] getQueuedSoundEffectLoops() {
      return queuedSoundEffectLoops;
   }

   public RSScene getScene() {
      return PacketWriter.scene;
   }

   public int[] getMenuIdentifiers() {
      return menuIdentifiers;
   }

   public void set3dZoom(int var1) {
      Rasterizer3D.Rasterizer3D_zoom = var1;
   }

   public long getOverallExperience() {
      int[] var1 = this.getSkillExperiences();
      long var2 = 0L;
      int[] var4 = var1;
      int var5 = var1.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         int var7 = var4[var6];
         var2 += (long)var7;
      }

      return var2;
   }

   public int getSoundEffectVolume() {
      return soundEffectVolume;
   }

   public void setObjectDefinitionLowDetail(boolean var1) {
      ObjectDefinition.ObjectDefinition_isLowDetail = var1;
   }

   public RSEnumDefinition getRsEnum(int var1) {
      return UserComparator10.getEnum(var1);
   }

   public int getMenuHeight() {
      return WorldMapDecoration.menuHeight;
   }

   public final void copy$menu(short var1) {
      boolean var2 = false;

      int var3;
      int var6;
      while(!var2) {
         var2 = true;

         for(var3 = 0; var3 < menuOptionsCount - 1; ++var3) {
            if(menuOpcodes[var3] < 1000 && menuOpcodes[var3 + 1] > 1000) {
               String var4 = menuTargets[var3];
               menuTargets[var3] = menuTargets[var3 + 1];
               menuTargets[var3 + 1] = var4;
               String var5 = menuActions[var3];
               menuActions[var3] = menuActions[var3 + 1];
               menuActions[var3 + 1] = var5;
               var6 = menuOpcodes[var3];
               menuOpcodes[var3] = menuOpcodes[var3 + 1];
               menuOpcodes[var3 + 1] = var6;
               var6 = menuArguments1[var3];
               menuArguments1[var3] = menuArguments1[var3 + 1];
               menuArguments1[var3 + 1] = var6;
               var6 = menuArguments2[var3];
               menuArguments2[var3] = menuArguments2[var3 + 1];
               menuArguments2[var3 + 1] = var6;
               var6 = menuIdentifiers[var3];
               menuIdentifiers[var3] = menuIdentifiers[var3 + 1];
               menuIdentifiers[var3 + 1] = var6;
               boolean var7 = menuShiftClick[var3];
               menuShiftClick[var3] = menuShiftClick[var3 + 1];
               menuShiftClick[var3 + 1] = var7;
               var2 = false;
            }
         }
      }

      if(Frames.dragInventoryWidget == null) {
         if(clickedWidget == null) {
            int var17 = MouseHandler.MouseHandler_lastButton;
            int var14;
            int var15;
            if(isMenuOpen) {
               int var8;
               int var9;
               int var18;
               if(var17 != 1 && (WorldMapIcon_1.mouseCam || var17 != 4)) {
                  var3 = MouseHandler.MouseHandler_x;
                  var14 = MouseHandler.MouseHandler_y;
                  if(var3 < UrlRequester.menuX - 10 || var3 > FriendSystem.menuWidth + UrlRequester.menuX + 10 || var14 < class37.menuY - 10 || var14 > class37.menuY + WorldMapDecoration.menuHeight + 10) {
                     isMenuOpen = false;
                     var15 = UrlRequester.menuX;
                     var6 = class37.menuY;
                     var18 = FriendSystem.menuWidth;
                     var8 = WorldMapDecoration.menuHeight;

                     for(var9 = 0; var9 < rootWidgetCount; ++var9) {
                        if(rootWidgetWidths[var9] + rootWidgetXs[var9] > var15 && rootWidgetXs[var9] < var18 + var15) {
                           if(var1 != 255) {
                              return;
                           }

                           if(rootWidgetHeights[var9] + rootWidgetYs[var9] > var6 && rootWidgetYs[var9] < var6 + var8) {
                              field1049[var9] = true;
                           }
                        }
                     }
                  }
               }

               if(var17 == 1 || !WorldMapIcon_1.mouseCam && var17 == 4) {
                  var3 = UrlRequester.menuX;
                  var14 = class37.menuY;
                  var15 = FriendSystem.menuWidth;
                  var6 = MouseHandler.MouseHandler_lastPressedX;
                  var18 = MouseHandler.MouseHandler_lastPressedY;
                  var8 = -1;

                  int var10;
                  for(var9 = 0; var9 < menuOptionsCount; ++var9) {
                     var10 = (menuOptionsCount - 1 - var9) * 15 + var14 + 31;
                     if(var6 > var3 && var6 < var15 + var3 && var18 > var10 - 13 && var18 < var10 + 3) {
                        var8 = var9;
                     }
                  }

                  if(var8 != -1) {
                     ModelData0.method3068(var8);
                  }

                  isMenuOpen = false;
                  var9 = UrlRequester.menuX;
                  var10 = class37.menuY;
                  int var11 = FriendSystem.menuWidth;
                  int var12 = WorldMapDecoration.menuHeight;

                  for(int var13 = 0; var13 < rootWidgetCount; ++var13) {
                     if(rootWidgetXs[var13] + rootWidgetWidths[var13] > var9 && rootWidgetXs[var13] < var9 + var11 && rootWidgetHeights[var13] + rootWidgetYs[var13] > var10 && rootWidgetYs[var13] < var12 + var10) {
                        field1049[var13] = true;
                     }
                  }
               }
            } else {
               label296: {
                  var3 = BuddyRankComparator.method3386();
                  if(var17 != 1) {
                     if(WorldMapIcon_1.mouseCam || var17 != 4) {
                        break label296;
                     }

                     if(var1 != 255) {
                        return;
                     }
                  }

                  if(var3 >= 0) {
                     label322: {
                        var14 = menuOpcodes[var3];
                        if(var14 != 39 && var14 != 40 && var14 != 41 && var14 != 42 && var14 != 43) {
                           if(var1 != 255) {
                              return;
                           }

                           if(var14 != 33 && var14 != 34) {
                              if(var1 != 255) {
                                 return;
                              }

                              if(var14 != 35 && var14 != 36 && var14 != 37 && var14 != 38 && var14 != 1005) {
                                 break label322;
                              }
                           }
                        }

                        var15 = menuArguments1[var3];
                        var6 = menuArguments2[var3];
                        Widget var16 = Canvas.getWidget(var6);
                        if(ModelData0.method3069(class12.method148(var16)) || WorldMapSection0.method3907(class12.method148(var16))) {
                           if(Frames.dragInventoryWidget != null && !field956) {
                              if(var1 != 255) {
                                 return;
                              }

                              if(menuOptionsCount > 0 && !this.method1660((byte)-74)) {
                                 class11.method137(field953, field954);
                              }
                           }

                           field956 = false;
                           itemDragDuration = 0;
                           itemPressedDurationChanged(-1);
                           if(Frames.dragInventoryWidget != null) {
                              if(var1 != 255) {
                                 return;
                              }

                              WorldMapSectionType.method116(Frames.dragInventoryWidget);
                           }

                           Frames.dragInventoryWidget = Canvas.getWidget(var6);
                           dragItemSlotSource = var15;
                           field953 = MouseHandler.MouseHandler_lastPressedX;
                           field954 = MouseHandler.MouseHandler_lastPressedY;
                           if(var3 >= 0) {
                              if(var1 != 255) {
                                 return;
                              }

                              ChatChannel.method1524(var3);
                           }

                           WorldMapSectionType.method116(Frames.dragInventoryWidget);
                           return;
                        }
                     }
                  }
               }

               if((var17 == 1 || !WorldMapIcon_1.mouseCam && var17 == 4) && this.method1660((byte)-50)) {
                  var17 = 2;
               }

               if((var17 == 1 || !WorldMapIcon_1.mouseCam && var17 == 4) && menuOptionsCount > 0) {
                  if(var1 != 255) {
                     return;
                  }

                  ModelData0.method3068(var3);
               }

               if(var17 == 2 && menuOptionsCount > 0) {
                  this.method1661(MouseHandler.MouseHandler_lastPressedX, MouseHandler.MouseHandler_lastPressedY);
               }
            }

         }
      }
   }

   public int getIndexedSpriteWidth() {
      return class329.SpriteBuffer_spriteWidth;
   }

   public RSSprite createItemSprite(int var1, int var2, int var3, int var4, int var5, boolean var6) {
      return GrandExchangeOfferWorldComparator.createSprite(var1, var2, var3, var4, var5, var6);
   }

   public RSScriptEvent createScriptEvent() {
      return new ScriptEvent();
   }

   public int[] getQueuedSoundEffectDelays() {
      return queuedSoundEffectDelays;
   }

   public int[] getMenuOpcodes() {
      return menuOpcodes;
   }

   public Dimension getRealDimensions() {
      if(!this.isStretchedEnabled()) {
         return this.getCanvas().getSize();
      } else {
         if(cachedRealDimensions == null) {
            if(this.isResized()) {
               Container var1 = this.getCanvas().getParent();
               int var2 = var1.getWidth();
               int var3 = var1.getHeight();
               int var4 = (int)((double)var2 / scalingFactor);
               int var5 = (int)((double)var3 / scalingFactor);
               if(var4 < 765 || var5 < 503) {
                  double var6 = (double)var2 / 765.0D;
                  double var8 = (double)var3 / 503.0D;
                  double var10 = Math.min(var6, var8);
                  var4 = (int)((double)var2 / var10);
                  var5 = (int)((double)var3 / var10);
               }

               cachedRealDimensions = new Dimension(var4, var5);
            } else {
               cachedRealDimensions = Constants.GAME_FIXED_SIZE;
            }
         }

         return cachedRealDimensions;
      }
   }

   public boolean boundingboxCheck(net.runelite.api.Model var1, int var2, int var3, int var4) {
      int var5 = ViewportMouse.client.getCameraPitch();
      int var6 = ViewportMouse.client.getCameraYaw();
      int var7 = Perspective.SINE[var5];
      int var8 = Perspective.COSINE[var5];
      int var9 = Perspective.SINE[var6];
      int var10 = Perspective.COSINE[var6];
      int var11 = ViewportMouse.client.getCenterX();
      int var12 = ViewportMouse.client.getCenterY();
      int var13 = ViewportMouse.client.getViewportMouseX();
      int var14 = ViewportMouse.client.getViewportMouseY();
      int var15 = ViewportMouse.client.get3dZoom();
      int var16 = (var13 - var11) * 50 / var15;
      int var17 = (var14 - var12) * 50 / var15;
      int var18 = (var13 - var11) * 10000 / var15;
      int var19 = (var14 - var12) * 10000 / var15;
      int var20 = rl$rot1(var17, 50, var8, var7);
      int var21 = rl$rot2(var17, 50, var8, var7);
      var17 = var20;
      var20 = rl$rot1(var19, 10000, var8, var7);
      int var22 = rl$rot2(var19, 10000, var8, var7);
      var19 = var20;
      var20 = rl$rot3(var16, var21, var10, var9);
      var21 = rl$rot4(var16, var21, var10, var9);
      var16 = var20;
      var20 = rl$rot3(var18, var22, var10, var9);
      var22 = rl$rot4(var18, var22, var10, var9);
      int var23 = (var20 - var16) / 2;
      int var24 = (var19 - var17) / 2;
      int var25 = (var22 - var21) / 2;
      int var26 = Math.abs(var23);
      int var27 = Math.abs(var24);
      int var28 = Math.abs(var25);
      int var29 = var2 + var1.getCenterX();
      int var30 = var3 + var1.getCenterY();
      int var31 = var4 + var1.getCenterZ();
      int var32 = var1.getExtremeX();
      int var33 = var1.getExtremeY();
      int var34 = var1.getExtremeZ();
      int var35 = (var16 + var20) / 2;
      int var36 = (var17 + var19) / 2;
      int var37 = (var22 + var21) / 2;
      int var38 = var35 - var29;
      int var39 = var36 - var30;
      int var40 = var37 - var31;
      boolean var41;
      if(Math.abs(var38) > var32 + var26) {
         var41 = false;
      } else if(Math.abs(var39) > var33 + var27) {
         var41 = false;
      } else if(Math.abs(var40) > var34 + var28) {
         var41 = false;
      } else if(Math.abs(var40 * var24 - var39 * var25) > var33 * var28 + var34 * var27) {
         var41 = false;
      } else if(Math.abs(var38 * var25 - var40 * var23) > var34 * var26 + var32 * var28) {
         var41 = false;
      } else if(Math.abs(var39 * var23 - var38 * var24) > var33 * var26 + var32 * var27) {
         var41 = false;
      } else {
         var41 = true;
      }

      return var41;
   }

   public byte[][][] getTileSettings() {
      return Tiles.Tiles_renderFlags;
   }

   public int getIndexedSpriteHeight() {
      return Frames.SpriteBuffer_spriteHeight;
   }

   public RSSoundEffect[] getAudioEffects() {
      return soundEffects;
   }

   public int[] getMenuArguments1() {
      return menuArguments1;
   }

   public int getPlane() {
      return WorldMapRectangle.plane;
   }

   public void setMenuEntries(MenuEntry[] var1) {
      int var2 = 0;
      String[] var3 = this.getMenuOptions();
      String[] var4 = this.getMenuTargets();
      int[] var5 = this.getMenuIdentifiers();
      int[] var6 = this.getMenuOpcodes();
      int[] var7 = this.getMenuArguments1();
      int[] var8 = this.getMenuArguments2();
      boolean[] var9 = this.getMenuForceLeftClick();
      MenuEntry[] var10 = var1;
      int var11 = var1.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         MenuEntry var13 = var10[var12];
         if(var13 != null) {
            var3[var2] = var13.getOption();
            var4[var2] = var13.getTarget();
            var5[var2] = var13.getIdentifier();
            var6[var2] = var13.getOpcode();
            var7[var2] = var13.getParam0();
            var8[var2] = var13.getParam1();
            var9[var2] = var13.isForceLeftClick();
            ++var2;
         }
      }

      this.setMenuOptionCount(var2);
      oldMenuEntryCount = var2;
   }

   public RSDecimator getSoundEffectResampler() {
      return Interpreter.decimator;
   }

   public void runScript(RSScriptEvent var1, int var2) {
      KeyHandler.method506((ScriptEvent)var1, var2, (byte)0);
   }

   public RSAbstractRasterProvider getBufferProvider() {
      return class30.rasterProvider;
   }

   public void setMinimapSprite(net.runelite.api.Sprite var1) {
      ObjectSound.sceneMinimapSprite = (Sprite)var1;
   }

   public int[] getIndexedSpriteOffsetXs() {
      return class329.SpriteBuffer_xOffsets;
   }

   public int[] getSoundLocations() {
      return soundLocations;
   }

   public void rasterizerDrawHorizontalLine(int var1, int var2, int var3, int var4) {
      Rasterizer2D.method6424(var1, var2, var3, var4);
   }

   public int[] getMenuArguments2() {
      return menuArguments2;
   }

   public int[] getIndexedSpriteOffsetYs() {
      return MusicPatchPcmStream.SpriteBuffer_yOffsets;
   }

   public RSRawPcmStream createRawPcmStream(RSRawSound var1, int var2, int var3) {
      return RawPcmStream.method2601((RawSound)var1, var2, var3);
   }

   public int getQueuedSoundEffectCount() {
      return soundEffectCount;
   }

   public boolean[] getMenuForceLeftClick() {
      return menuShiftClick;
   }

   public int[] getIndexedSpriteWidths() {
      return class329.SpriteBuffer_spriteWidths;
   }

   public void setMenuOptionCount(int var1) {
      menuOptionsCount = var1;
   }

   public RSPcmStreamMixer getSoundEffectAudioQueue() {
      return WorldMapLabelSize.pcmStreamMixer;
   }

   public void addHashAtMouse(long var1) {
      long[] var3 = ViewportMouse.client.getEntitiesAtMouse();
      int var4 = ViewportMouse.client.getEntitiesAtMouseCount();
      if(var4 < 1000) {
         var3[var4] = var1;
         ViewportMouse.client.setEntitiesAtMouseCount(var4 + 1);
      }

   }

   public int[] getIndexedSpriteHeights() {
      return RunException.SpriteBuffer_spriteHeights;
   }

   public byte[][] getSpritePixels() {
      return PacketBufferNode.SpriteBuffer_pixels;
   }

   public int[] getIndexedSpritePalette() {
      return class329.SpriteBuffer_spritePalette;
   }

   public void setQueuedSoundEffectCount(int var1) {
      soundEffectCount = var1;
   }

   public void rasterizerDrawVerticalLine(int var1, int var2, int var3, int var4) {
      Rasterizer2D.method6426(var1, var2, var3, var4);
   }

   public void setIndexedSpriteOffsetXs(int[] var1) {
      class329.SpriteBuffer_xOffsets = var1;
   }

   public void setIndexedSpriteOffsetYs(int[] var1) {
      MusicPatchPcmStream.SpriteBuffer_yOffsets = var1;
   }

   public void setIndexedSpriteWidths(int[] var1) {
      class329.SpriteBuffer_spriteWidths = var1;
   }

   public void setIndexedSpriteHeights(int[] var1) {
      RunException.SpriteBuffer_spriteHeights = var1;
   }

   public void setIndexedSpritePalette(int[] var1) {
      class329.SpriteBuffer_spritePalette = var1;
   }

   public void rasterizerDrawRectangle(int var1, int var2, int var3, int var4, int var5) {
      Rasterizer2D.drawRectangle(var1, var2, var3, var4, var5);
   }

   public void setSpritePixels(byte[][] var1) {
      PacketBufferNode.SpriteBuffer_pixels = var1;
   }

   public void drawObject(int var1, int var2, int var3, int var4, int var5) {
      class30.method649(var1, var2, var3, var4, var5);
   }

   public void rasterizerDrawCircle(int var1, int var2, int var3, int var4) {
      Rasterizer2D.method6415(var1, var2, var3, var4);
   }

   public void rasterizerDrawGradient(int var1, int var2, int var3, int var4, int var5, int var6) {
      Rasterizer2D.method6419(var1, var2, var3, var4, var5, var6);
   }

   public void rasterizerFillRectangle(int var1, int var2, int var3, int var4, int var5) {
      Rasterizer2D.method6469(var1, var2, var3, var4, var5);
   }

   public RSFont getFontBold12() {
      return class170.fontBold12;
   }

   public void rasterizerFillRectangleAlpha(int var1, int var2, int var3, int var4, int var5, int var6) {
      Rasterizer2D.fillRectangleAlpha(var1, var2, var3, var4, var5, var6);
   }

   public final void init() {
      try {
         if(this.method967()) {
            for(int var1 = 0; var1 <= 19; ++var1) {
               String var2 = this.getParameter(Integer.toString(var1));
               if(var2 != null) {
                  switch(var1) {
                  case 1:
                     useBufferedSocket = Integer.parseInt(var2) != 0;
                  case 2:
                  case 11:
                  case 13:
                  case 16:
                  default:
                     break;
                  case 3:
                     if(var2.equalsIgnoreCase("true")) {
                        isMembersWorld = true;
                     } else {
                        isMembersWorld = false;
                     }
                     break;
                  case 4:
                     if(clientType == -1) {
                        clientType = Integer.parseInt(var2);
                     }
                     break;
                  case 5:
                     worldProperties = Integer.parseInt(var2);
                     break;
                  case 6:
                     WorldMapLabelSize.clientLanguage = Language.method3820(Integer.parseInt(var2));
                     break;
                  case 7:
                     VertexNormal.field1530 = FloorDecoration.method2433(Integer.parseInt(var2));
                     break;
                  case 8:
                     if(var2.equalsIgnoreCase("true")) {
                        ;
                     }
                     break;
                  case 9:
                     class197.field2177 = var2;
                     break;
                  case 10:
                     StudioGame[] var3 = new StudioGame[]{StudioGame.game3, StudioGame.runescape, StudioGame.stellardawn, StudioGame.oldscape, StudioGame.game4, StudioGame.game5};
                     class10.field66 = (StudioGame)NetSocket.getEnumeratedTypeIndex(var3, Integer.parseInt(var2));
                     if(class10.field66 == StudioGame.oldscape) {
                        WorldMapSection1.loginType = LoginType.oldscape;
                     } else {
                        WorldMapSection1.loginType = LoginType.field3936;
                     }
                     break;
                  case 12:
                     worldId = Integer.parseInt(var2);
                     break;
                  case 14:
                     WorldMapArea.field140 = Integer.parseInt(var2);
                     break;
                  case 15:
                     gameBuild = Integer.parseInt(var2);
                     break;
                  case 17:
                     WorldMapSectionType.field59 = var2;
                  }
               }
            }

            GrandExchangeOfferUnitPriceComparator.method1471();
            class158.worldHost = CustomMain.worldType.getGameServerAddress();
            String var11 = VertexNormal.field1530.name;
            byte var12 = 0;

            try {
               JagexCache.idxCount = 21;
               BufferedNetSocket.cacheGamebuild = var12;

               try {
                  HealthBarUpdate.field589 = System.getProperty("os.name");
               } catch (Exception var19) {
                  HealthBarUpdate.field589 = "Unknown";
               }

               class163.field1987 = HealthBarUpdate.field589.toLowerCase();

               try {
                  JagexCache.userHomeDirectory = System.getProperty("user.home");
                  if(JagexCache.userHomeDirectory != null) {
                     JagexCache.userHomeDirectory = JagexCache.userHomeDirectory + "/";
                  }
               } catch (Exception var18) {
                  ;
               }

               try {
                  if(class163.field1987.startsWith("win")) {
                     if(JagexCache.userHomeDirectory == null) {
                        JagexCache.userHomeDirectory = System.getenv("USERPROFILE");
                     }
                  } else if(JagexCache.userHomeDirectory == null) {
                     JagexCache.userHomeDirectory = System.getenv("HOME");
                  }

                  if(JagexCache.userHomeDirectory != null) {
                     JagexCache.userHomeDirectory = JagexCache.userHomeDirectory + "/";
                  }
               } catch (Exception var17) {
                  ;
               }

               if(JagexCache.userHomeDirectory == null) {
                  JagexCache.userHomeDirectory = "~/";
               }

               UserComparator4.field1892 = new String[]{"c:/rscache/", "/rscache/", "c:/windows/", "c:/winnt/", "c:/", JagexCache.userHomeDirectory, "/tmp/", ""};
               class266.field3545 = new String[]{".jagex_cache_" + BufferedNetSocket.cacheGamebuild, ".file_store_" + BufferedNetSocket.cacheGamebuild};

               int var6;
               File var7;
               label176:
               for(int var13 = 0; var13 < 4; ++var13) {
                  GrandExchangeOfferOwnWorldComparator.cacheDir = WorldMapRegion.method337("oldschool", var11, var13);
                  if(!GrandExchangeOfferOwnWorldComparator.cacheDir.exists()) {
                     GrandExchangeOfferOwnWorldComparator.cacheDir.mkdirs();
                  }

                  File[] var4 = GrandExchangeOfferOwnWorldComparator.cacheDir.listFiles();
                  if(var4 == null) {
                     break;
                  }

                  File[] var5 = var4;
                  var6 = 0;

                  while(true) {
                     if(var6 >= var5.length) {
                        break label176;
                     }

                     var7 = var5[var6];

                     boolean var8;
                     try {
                        RandomAccessFile var9 = new RandomAccessFile(var7, "rw");
                        int var10 = var9.read();
                        var9.seek(0L);
                        var9.write(var10);
                        var9.seek(0L);
                        var9.close();
                        var8 = true;
                     } catch (Exception var16) {
                        var8 = false;
                     }

                     if(!var8) {
                        break;
                     }

                     ++var6;
                  }
               }

               File var23 = GrandExchangeOfferOwnWorldComparator.cacheDir;
               FileSystem.FileSystem_cacheDir = var23;
               if(!FileSystem.FileSystem_cacheDir.exists()) {
                  throw new RuntimeException("");
               }

               FileSystem.FileSystem_hasPermissions = true;

               try {
                  File var24 = new File(JagexCache.userHomeDirectory, "random.dat");
                  if(var24.exists()) {
                     JagexCache.JagexCache_randomDat = new BufferedFile(new AccessFile(var24, "rw", 25L), 24, 0);
                  } else {
                     label153:
                     for(int var14 = 0; var14 < class266.field3545.length; ++var14) {
                        for(var6 = 0; var6 < UserComparator4.field1892.length; ++var6) {
                           var7 = new File(UserComparator4.field1892[var6] + class266.field3545[var14] + File.separatorChar + "random.dat");
                           if(var7.exists()) {
                              JagexCache.JagexCache_randomDat = new BufferedFile(new AccessFile(var7, "rw", 25L), 24, 0);
                              break label153;
                           }
                        }
                     }
                  }

                  if(JagexCache.JagexCache_randomDat == null) {
                     RandomAccessFile var25 = new RandomAccessFile(var24, "rw");
                     var6 = var25.read();
                     var25.seek(0L);
                     var25.write(var6);
                     var25.seek(0L);
                     var25.close();
                     JagexCache.JagexCache_randomDat = new BufferedFile(new AccessFile(var24, "rw", 25L), 24, 0);
                  }
               } catch (IOException var20) {
                  ;
               }

               JagexCache.JagexCache_dat2File = new BufferedFile(new AccessFile(UserComparator8.method2885("main_file_cache.dat2"), "rw", 1048576000L), 5200, 0);
               JagexCache.JagexCache_idx255File = new BufferedFile(new AccessFile(UserComparator8.method2885("main_file_cache.idx255"), "rw", 1048576L), 6000, 0);
               class188.JagexCache_idxFiles = new BufferedFile[JagexCache.idxCount];

               for(int var15 = 0; var15 < JagexCache.idxCount; ++var15) {
                  class188.JagexCache_idxFiles[var15] = new BufferedFile(new AccessFile(UserComparator8.method2885("main_file_cache.idx" + var15), "rw", 1048576L), 6000, 0);
               }
            } catch (Exception var21) {
               class19.method342((String)null, var21);
            }

            ViewportMouse.client = this;
            ClientType.clientType = clientType;
            this.method965(765, 503, 184);
         }
      } catch (RuntimeException var22) {
         throw class125.method2877(var22, "client.init(" + ')');
      }
   }

   public void setPrintMenuActions(boolean var1) {
      printMenuActions = var1;
   }

   public void setHideDisconnect(boolean var1) {
      hideDisconnect = var1;
   }

   public void setHideFriendAttackOptions(boolean var1) {
      hideFriendAttackOptions = var1;
   }

   public void setHideFriendCastOptions(boolean var1) {
      hideFriendCastOptions = var1;
   }

   public void setHideClanmateAttackOptions(boolean var1) {
      hideClanmateAttackOptions = var1;
   }

   public void setHideClanmateCastOptions(boolean var1) {
      hideClanmateCastOptions = var1;
   }

   public void setUnhiddenCasts(Set var1) {
      unhiddenCasts = var1;
   }

   public DrawCallbacks getDrawCallbacks() {
      return this.drawCallbacks;
   }

   public void setDrawCallbacks(DrawCallbacks var1) {
      this.drawCallbacks = var1;
   }

   public Logger getLogger() {
      return this.logger;
   }

   public boolean isInterpolatePlayerAnimations() {
      return interpolatePlayerAnimations;
   }

   public void setInterpolatePlayerAnimations(boolean var1) {
      interpolatePlayerAnimations = var1;
   }

   public boolean isInterpolateNpcAnimations() {
      return interpolateNpcAnimations;
   }

   public void setInterpolateNpcAnimations(boolean var1) {
      interpolateNpcAnimations = var1;
   }

   public boolean isInterpolateObjectAnimations() {
      return interpolateObjectAnimations;
   }

   public void setInterpolateObjectAnimations(boolean var1) {
      interpolateObjectAnimations = var1;
   }

   public boolean isInterpolateWidgetAnimations() {
      return interpolateWidgetAnimations;
   }

   public void setInterpolateWidgetAnimations(boolean var1) {
      interpolateWidgetAnimations = var1;
   }

   public void setInventoryDragDelay(int var1) {
      inventoryDragDelay = var1;
   }

   public AccountType getAccountType() {
      int var1 = this.getVar(Varbits.ACCOUNT_TYPE);
      switch(var1) {
      case 1:
         return AccountType.IRONMAN;
      case 2:
         return AccountType.ULTIMATE_IRONMAN;
      case 3:
         return AccountType.HARDCORE_IRONMAN;
      default:
         return AccountType.NORMAL;
      }
   }

   public net.runelite.api.Tile getSelectedSceneTile() {
      int var1 = this.getSelectedSceneTileX();
      int var2 = this.getSelectedSceneTileY();
      return var1 != -1 && var2 != -1?this.getScene().getTiles()[this.getPlane()][var1][var2]:null;
   }

   public List getPlayers() {
      int var1 = this.getPlayerIndexesCount();
      int[] var2 = this.getPlayerIndices();
      RSPlayer[] var3 = this.getCachedPlayers();
      ArrayList var4 = new ArrayList(var1);

      for(int var5 = 0; var5 < var1; ++var5) {
         var4.add(var3[var2[var5]]);
      }

      return var4;
   }

   public List getNpcs() {
      int var1 = this.getNpcIndexesCount();
      int[] var2 = this.getNpcIndices();
      RSNPC[] var3 = this.getCachedNPCs();
      ArrayList var4 = new ArrayList(var1);

      for(int var5 = 0; var5 < var1; ++var5) {
         var4.add(var3[var2[var5]]);
      }

      return var4;
   }

   public int getBoostedSkillLevel(Skill var1) {
      int[] var2 = this.getBoostedSkillLevels();
      return var2[var1.ordinal()];
   }

   public int getRealSkillLevel(Skill var1) {
      int[] var2 = this.getRealSkillLevels();
      return var2[var1.ordinal()];
   }

   public int getTotalLevel() {
      int var1 = 0;
      int[] var2 = ViewportMouse.client.getRealSkillLevels();
      int var3 = Skill.CONSTRUCTION.ordinal();

      for(int var4 = 0; var4 < var2.length; ++var4) {
         if(var4 <= var3) {
            var1 += var2[var4];
         }
      }

      return var1;
   }

   public void addChatMessage(ChatMessageType var1, String var2, String var3, String var4) {
      this.addChatMessage(var1.getType(), var2, var3, var4);
   }

   public GameState getGameState() {
      return GameState.of(this.getRSGameState());
   }

   public void setGameState(GameState var1) {
      ViewportMouse.client.setGameState(var1.getState());
   }

   public Point getMouseCanvasPosition() {
      return new Point(this.getMouseX(), this.getMouseY());
   }

   public net.runelite.api.widgets.Widget[] getWidgetRoots() {
      int var1 = this.getWidgetRoot();
      ArrayList var2 = new ArrayList();
      RSWidget[] var3 = this.getWidgets()[var1];
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         RSWidget var6 = var3[var5];
         if(var6 != null && var6.getRSParentId() == -1) {
            var2.add(var6);
         }
      }

      return (net.runelite.api.widgets.Widget[])var2.toArray(new net.runelite.api.widgets.Widget[var2.size()]);
   }

   public RSWidget[] getGroup(int var1) {
      RSWidget[][] var2 = this.getWidgets();
      return var2 != null && var1 >= 0 && var1 < var2.length && var2[var1] != null?var2[var1]:null;
   }

   public int getVar(VarPlayer var1) {
      int[] var2 = this.getVarps();
      return var2[var1.getId()];
   }

   public int getVarpValue(int[] var1, int var2) {
      return var1[var2];
   }

   public void setVarpValue(int[] var1, int var2, int var3) {
      var1[var2] = var3;
   }

   public boolean isPrayerActive(Prayer var1) {
      return this.getVar(var1.getVarbit()) == 1;
   }

   public int getSkillExperience(Skill var1) {
      int[] var2 = this.getSkillExperiences();
      if(var1 == Skill.OVERALL) {
         this.logger.debug("getSkillExperience called for {}!", var1);
         return (int)this.getOverallExperience();
      } else {
         int var3 = var1.ordinal();
         return var3 >= var2.length?-1:var2[var3];
      }
   }

   public void refreshChat() {
      this.setChatCycle(this.getCycleCntr());
   }

   public net.runelite.api.widgets.Widget getViewportWidget() {
      return this.isResized()?(this.getVar(Varbits.SIDE_PANELS) == 1?this.getWidget(WidgetInfo.RESIZABLE_VIEWPORT_BOTTOM_LINE):this.getWidget(WidgetInfo.RESIZABLE_VIEWPORT_OLD_SCHOOL_BOX)):this.getWidget(WidgetInfo.FIXED_VIEWPORT);
   }

   public List getProjectiles() {
      ArrayList var1 = new ArrayList();
      RSNodeDeque var2 = this.getProjectilesDeque();
      RSNode var3 = var2.getHead();

      for(net.runelite.api.Node var4 = var3.getNext(); var4 != var3; var4 = var4.getNext()) {
         var1.add((net.runelite.api.Projectile)var4);
      }

      return var1;
   }

   public List getGraphicsObjects() {
      ArrayList var1 = new ArrayList();
      RSNodeDeque var2 = this.getGraphicsObjectDeque();
      RSNode var3 = var2.getHead();

      for(net.runelite.api.Node var4 = var3.getNext(); var4 != var3; var4 = var4.getNext()) {
         var1.add((net.runelite.api.GraphicsObject)var4);
      }

      return var1;
   }

   public void setModIcons(net.runelite.api.IndexedSprite[] var1) {
      this.setRSModIcons((RSIndexedSprite[])((RSIndexedSprite[])var1));
   }

   public LocalPoint getLocalDestinationLocation() {
      int var1 = this.getDestinationX();
      int var2 = this.getDestinationY();
      return var1 != 0 && var2 != 0?LocalPoint.fromScene(var1, var2):null;
   }

   public void changeMemoryMode(boolean var1) {
      this.setLowMemory(var1);
      this.setSceneLowMemory(var1);
      this.setAudioHighMemory(true);
      this.setObjectDefinitionLowDetail(var1);
   }

   public boolean isFriended(String var1, boolean var2) {
      RSUsername var3 = this.createName(var1, this.getLoginType());
      return this.getFriendManager().isFriended(var3, var2);
   }

   public int getClanChatCount() {
      RSClanChat var1 = this.getClanMemberManager();
      return var1 != null?var1.getCount():0;
   }

   public ClanMember[] getClanMembers() {
      RSClanChat var1 = this.getClanMemberManager();
      if(var1 == null) {
         return null;
      } else {
         int var2 = var1.getCount();
         return (ClanMember[])Arrays.copyOf(var1.getNameables(), var2);
      }
   }

   public String getClanOwner() {
      return this.getClanMemberManager().getClanOwner();
   }

   public String getClanChatName() {
      return this.getClanMemberManager().getClanChatName();
   }

   public net.runelite.api.Friend[] getFriends() {
      RSFriendSystem var1 = this.getFriendManager();
      if(var1 == null) {
         return null;
      } else {
         RSFriendsList var2 = var1.getFriendContainer();
         if(var2 == null) {
            return null;
         } else {
            int var3 = var2.getCount();
            return (net.runelite.api.Friend[])Arrays.copyOf(var2.getNameables(), var3);
         }
      }
   }

   public int getFriendsCount() {
      RSFriendSystem var1 = this.getFriendManager();
      if(var1 == null) {
         return -1;
      } else {
         RSFriendsList var2 = var1.getFriendContainer();
         return var2 == null?-1:var2.getCount();
      }
   }

   public Ignore[] getIgnores() {
      RSFriendSystem var1 = this.getFriendManager();
      if(var1 == null) {
         return null;
      } else {
         RSIgnoreList var2 = var1.getIgnoreContainer();
         if(var2 == null) {
            return null;
         } else {
            int var3 = var2.getCount();
            return (Ignore[])Arrays.copyOf(var2.getNameables(), var3);
         }
      }
   }

   public int getIgnoreCount() {
      RSFriendSystem var1 = this.getFriendManager();
      if(var1 == null) {
         return -1;
      } else {
         RSIgnoreList var2 = var1.getIgnoreContainer();
         return var2 == null?-1:var2.getCount();
      }
   }

   public boolean isClanMember(String var1) {
      RSClanChat var2 = this.getClanMemberManager();
      return var2 != null && var2.isMember(this.createName(var1, this.getLoginType()));
   }

   public net.runelite.api.Sprite createItemSprite(int var1, int var2, int var3, int var4, int var5, boolean var6, int var7) {
      if(!$assertionsDisabled && !this.isClientThread()) {
         throw new AssertionError();
      } else {
         int var8 = this.get3dZoom();
         this.set3dZoom(var7);

         RSSprite var9;
         try {
            var9 = this.createItemSprite(var1, var2, var3, var4, var5, var6);
         } finally {
            this.set3dZoom(var8);
         }

         return var9;
      }
   }

   public boolean hasHintArrow() {
      return ViewportMouse.client.getHintArrowTargetType() != HintArrowType.NONE.getValue();
   }

   public void clearHintArrow() {
      ViewportMouse.client.setHintArrowTargetType(HintArrowType.NONE.getValue());
   }

   public void setHintArrow(net.runelite.api.NPC var1) {
      ViewportMouse.client.setHintArrowTargetType(HintArrowType.NPC.getValue());
      ViewportMouse.client.setHintArrowNpcTargetIdx(var1.getIndex());
   }

   public void setHintArrow(net.runelite.api.Player var1) {
      ViewportMouse.client.setHintArrowTargetType(HintArrowType.PLAYER.getValue());
      ViewportMouse.client.setHintArrowPlayerTargetIdx(((RSPlayer)var1).getPlayerId());
   }

   public void setHintArrow(WorldPoint var1) {
      ViewportMouse.client.setHintArrowTargetType(HintArrowType.WORLD_POSITION.getValue());
      ViewportMouse.client.setHintArrowX(var1.getX());
      ViewportMouse.client.setHintArrowY(var1.getY());
      ViewportMouse.client.setHintArrowOffsetX(64);
      ViewportMouse.client.setHintArrowOffsetY(64);
   }

   public WorldPoint getHintArrowPoint() {
      if(this.getHintArrowType() == HintArrowType.WORLD_POSITION) {
         int var1 = ViewportMouse.client.getHintArrowX();
         int var2 = ViewportMouse.client.getHintArrowY();
         return new WorldPoint(var1, var2, ViewportMouse.client.getPlane());
      } else {
         return null;
      }
   }

   public net.runelite.api.Player getHintArrowPlayer() {
      if(this.getHintArrowType() == HintArrowType.PLAYER) {
         int var1 = ViewportMouse.client.getHintArrowPlayerTargetIdx();
         RSPlayer[] var2 = ViewportMouse.client.getCachedPlayers();
         return var1 >= 0 && var1 < var2.length?var2[var1]:null;
      } else {
         return null;
      }
   }

   public net.runelite.api.NPC getHintArrowNpc() {
      if(this.getHintArrowType() == HintArrowType.NPC) {
         int var1 = ViewportMouse.client.getHintArrowNpcTargetIdx();
         RSNPC[] var2 = ViewportMouse.client.getCachedNPCs();
         return var1 >= 0 && var1 < var2.length?var2[var1]:null;
      } else {
         return null;
      }
   }

   public int getTickCount() {
      return tickCount;
   }

   public void setTickCount(int var1) {
      tickCount = var1;
   }

   public EnumSet getWorldType() {
      int var1 = this.getFlags();
      return WorldType.fromMask(var1);
   }

   public RSTileItem getLastItemDespawn() {
      return lastItemDespawn;
   }

   public void setLastItemDespawn(RSTileItem var1) {
      lastItemDespawn = var1;
   }

   public boolean isGpu() {
      return this.gpu;
   }

   public void setGpu(boolean var1) {
      this.gpu = var1;
   }

   public void queueChangedSkill(Skill var1) {
      int[] var2 = ViewportMouse.client.getChangedSkills();
      int var3 = ViewportMouse.client.getChangedSkillsCount();
      ++var3;
      var2[var3 - 1 & 31] = var1.ordinal();
      ViewportMouse.client.setChangedSkillsCount(var3);
   }

   public void setSkyboxColor(int var1) {
      skyboxColor = var1;
   }

   public int getSkyboxColor() {
      return skyboxColor;
   }

   public net.runelite.api.EnumDefinition getEnum(int var1) {
      if(!$assertionsDisabled && !this.isClientThread()) {
         throw new AssertionError("getEnum must be called on client thread");
      } else {
         RSEnumDefinition var2 = (RSEnumDefinition)this.enumCache.getIfPresent(Integer.valueOf(var1));
         if(var2 != null) {
            return var2;
         } else {
            var2 = this.getRsEnum(var1);
            this.enumCache.put(Integer.valueOf(var1), var2);
            return var2;
         }
      }
   }

   public void resetHealthBarCaches() {
      this.getHealthBarCache().reset();
      this.getHealthBarSpriteCache().reset();
   }

   public void addFriend(String var1) {
      RSFriendSystem var2 = this.getFriendManager();
      var2.addFriend(var1);
   }

   public void removeFriend(String var1) {
      RSFriendSystem var2 = this.getFriendManager();
      var2.removeFriend(var1);
   }

   public BigInteger getModulus() {
      return this.modulus;
   }

   public void setModulus(BigInteger var1) {
      this.modulus = var1;
   }

   public Map getSpriteOverrides() {
      return spriteOverrides;
   }

   public Map getWidgetSpriteOverrides() {
      return widgetSpriteOverrides;
   }

   public void setStretchedEnabled(boolean var1) {
      stretchedEnabled = var1;
   }

   public boolean isStretchedFast() {
      return stretchedFast;
   }

   public void setStretchedFast(boolean var1) {
      stretchedFast = var1;
   }

   public void setStretchedIntegerScaling(boolean var1) {
      stretchedIntegerScaling = var1;
   }

   public void setStretchedKeepAspectRatio(boolean var1) {
      stretchedKeepAspectRatio = var1;
   }

   public void setScalingFactor(int var1) {
      scalingFactor = 1.0D + (double)var1 / 100.0D;
   }

   public Dimension getStretchedDimensions() {
      if(cachedStretchedDimensions == null) {
         Container var1 = this.getCanvas().getParent();
         int var2 = var1.getWidth();
         int var3 = var1.getHeight();
         Dimension var4 = this.getRealDimensions();
         if(stretchedKeepAspectRatio) {
            double var5 = var4.getWidth() / var4.getHeight();
            int var7 = (int)((double)var3 * var5);
            if(var7 > var2) {
               var3 = (int)((double)var2 / var5);
            } else {
               var2 = var7;
            }
         }

         if(stretchedIntegerScaling) {
            if(var2 > var4.width) {
               var2 -= var2 % var4.width;
            }

            if(var3 > var4.height) {
               var3 -= var3 % var4.height;
            }
         }

         cachedStretchedDimensions = new Dimension(var2, var3);
      }

      return cachedStretchedDimensions;
   }

   public void invalidateStretching(boolean var1) {
      cachedRealDimensions = null;
      cachedStretchedDimensions = null;
      if(var1 && this.isResized()) {
         this.setResizeCanvasNextFrame(true);
      }

   }

   public void setCameraPitchRelaxerEnabled(boolean var1) {
      if(pitchRelaxEnabled != var1) {
         pitchRelaxEnabled = var1;
         if(!var1) {
            int var2 = ViewportMouse.client.getCameraPitchTarget();
            if(var2 > 383) {
               ViewportMouse.client.setCameraPitchTarget(383);
            }
         }

      }
   }

   public void runScript(Object... var1) {
      if(!$assertionsDisabled && !this.isClientThread()) {
         throw new AssertionError();
      } else if(!$assertionsDisabled && currentScript != null) {
         throw new AssertionError();
      } else if(!$assertionsDisabled && !(var1[0] instanceof Integer) && !(var1[0] instanceof JavaScriptCallback)) {
         throw new AssertionError("The first argument should always be a ScriptID!");
      } else {
         RSScriptEvent var2 = this.createScriptEvent();
         var2.setArguments(var1);
         this.runScript(var2, 5000000);
      }
   }

   public void playSoundEffect(int var1) {
      this.playSoundEffect(var1, 0, 0, 0, 0);
   }

   public void playSoundEffect(int var1, int var2, int var3, int var4) {
      this.playSoundEffect(var1, var2, var3, var4, 0);
   }

   public void playSoundEffect(int var1, int var2) {
      RSSoundEffect var3 = this.getTrack(this.getIndexCache4(), var1, 0);
      if(var3 != null) {
         int var4 = this.getSoundEffectVolume();
         if(var4 != 0) {
            var2 = var4;
         }

         RSRawSound var5 = var3.toRawAudioNode().applyResampler(this.getSoundEffectResampler());
         RSRawPcmStream var6 = this.createRawPcmStream(var5, 100, var2);
         var6.setNumLoops(1);
         this.getSoundEffectAudioQueue().addSubStream((RSPcmStream)var6);
      }
   }

   public void checkClickbox(net.runelite.api.Model var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10) {
      RSModel var12 = (RSModel)var1;
      boolean var13 = var10 != 0L && (int)(var10 >>> 16 & 1L) != 1;
      boolean var14 = ViewportMouse.client.getViewportContainsMouse();
      if(var13 && var14) {
         if(this.boundingboxCheck(var12, var7, var8, var9)) {
            if(Math.sqrt((double)(var7 * var7 + var9 * var9)) <= 12800.0D) {
               if(var12.isClickable()) {
                  this.addHashAtMouse(var10);
               } else {
                  int var15 = var12.getVerticesCount();
                  int var16 = var12.getTrianglesCount();
                  int[] var17 = var12.getVerticesX();
                  int[] var18 = var12.getVerticesY();
                  int[] var19 = var12.getVerticesZ();
                  int[] var20 = var12.getTrianglesX();
                  int[] var21 = var12.getTrianglesY();
                  int[] var22 = var12.getTrianglesZ();
                  int[] var23 = var12.getFaceColors3();
                  int var24 = ViewportMouse.client.get3dZoom();
                  int var25 = ViewportMouse.client.getCenterX();
                  int var26 = ViewportMouse.client.getCenterY();
                  int var27 = 0;
                  int var28 = 0;
                  if(var2 != 0) {
                     var27 = Perspective.SINE[var2];
                     var28 = Perspective.COSINE[var2];
                  }

                  int var29;
                  int var30;
                  int var31;
                  int var32;
                  int var33;
                  for(var29 = 0; var29 < var15; ++var29) {
                     var30 = var17[var29];
                     var31 = var18[var29];
                     var32 = var19[var29];
                     if(var2 != 0) {
                        var33 = var32 * var27 + var30 * var28 >> 16;
                        var32 = var32 * var28 - var30 * var27 >> 16;
                        var30 = var33;
                     }

                     var30 += var7;
                     var31 += var8;
                     var32 += var9;
                     var33 = var30 * var6 + var5 * var32 >> 16;
                     var32 = var32 * var6 - var5 * var30 >> 16;
                     var30 = var33;
                     var33 = var31 * var4 - var3 * var32 >> 16;
                     var32 = var32 * var4 + var3 * var31 >> 16;
                     if(var32 >= 50) {
                        rl$modelViewportYs[var29] = var30 * var24 / var32 + var25;
                        rl$modelViewportXs[var29] = var33 * var24 / var32 + var26;
                     } else {
                        rl$modelViewportYs[var29] = -5000;
                     }
                  }

                  var29 = ViewportMouse.client.getViewportMouseX();
                  var30 = ViewportMouse.client.getViewportMouseY();

                  for(var31 = 0; var31 < var16; ++var31) {
                     if(var23[var31] != -2) {
                        var32 = var20[var31];
                        var33 = var21[var31];
                        int var34 = var22[var31];
                        int var35 = rl$modelViewportYs[var32];
                        int var36 = rl$modelViewportYs[var33];
                        int var37 = rl$modelViewportYs[var34];
                        int var38 = rl$modelViewportXs[var32];
                        int var39 = rl$modelViewportXs[var33];
                        int var40 = rl$modelViewportXs[var34];
                        if(var35 != -5000 && var36 != -5000 && var37 != -5000) {
                           int var41 = var12.isClickable()?20:5;
                           int var42 = var41 + var30;
                           boolean var43;
                           if(var42 < var38 && var42 < var39 && var42 < var40) {
                              var43 = false;
                           } else {
                              var42 = var30 - var41;
                              if(var42 > var38 && var42 > var39 && var42 > var40) {
                                 var43 = false;
                              } else {
                                 var42 = var41 + var29;
                                 if(var42 < var35 && var42 < var36 && var42 < var37) {
                                    var43 = false;
                                 } else {
                                    var42 = var29 - var41;
                                    if(var42 > var35 && var42 > var36 && var42 > var37) {
                                       var43 = false;
                                    } else {
                                       var43 = true;
                                    }
                                 }
                              }
                           }

                           if(var43) {
                              this.addHashAtMouse(var10);
                              break;
                           }
                        }
                     }
                  }

               }
            }
         }
      }
   }

   public void draw2010Menu() {
      int var1 = this.getMenuX();
      int var2 = this.getMenuY();
      int var3 = this.getMenuWidth();
      int var4 = this.getMenuHeight();
      this.rasterizerDrawHorizontalLine(var1 + 2, var2, var3 - 4, 7170651);
      this.rasterizerDrawHorizontalLine(var1 + 2, var2 + var4 - 1, var3 - 4, 7170651);
      this.rasterizerDrawVerticalLine(var1, var2 + 2, var4 - 4, 7170651);
      this.rasterizerDrawVerticalLine(var1 + var3 - 1, var2 + 2, var4 - 4, 7170651);
      this.rasterizerDrawRectangle(var1 + 1, var2 + 5, var3 - 2, var4 - 6, 2827810);
      this.rasterizerDrawHorizontalLine(var1 + 1, var2 + 17, var3 - 2, 2827810);
      this.rasterizerDrawCircle(var1 + 2, var2 + var4 - 3, 0, 2827810);
      this.rasterizerDrawCircle(var1 + var3 - 3, var2 + var4 - 3, 0, 2827810);
      this.rasterizerDrawGradient(var1 + 2, var2 + 1, var3 - 4, 16, 3288610, 592388);
      this.rasterizerFillRectangle(var1 + 1, var2 + 1, 2, 4, 2827810);
      this.rasterizerFillRectangle(var1 + var3 - 3, var2 + 1, 2, 4, 2827810);
      this.rasterizerDrawHorizontalLine(var1 + 2, var2 + 18, var3 - 4, 5392957);
      this.rasterizerDrawHorizontalLine(var1 + 3, var2 + var4 - 3, var3 - 6, 5392957);
      this.rasterizerDrawVerticalLine(var1 + 2, var2 + 18, var4 - 21, 5392957);
      this.rasterizerDrawVerticalLine(var1 + var3 - 3, var2 + 18, var4 - 21, 5392957);
      this.rasterizerFillRectangle(var1 + 3, var2 + 19, var3 - 6, var4 - 22, 2828060);
      this.rasterizerDrawCircle(var1 + 1, var2 + 1, 0, 7170651);
      this.rasterizerDrawCircle(var1 + var3 - 2, var2 + 1, 0, 7170651);
      this.rasterizerDrawCircle(var1 + 1, var2 + var4 - 2, 0, 7170651);
      this.rasterizerDrawCircle(var1 + var3 - 2, var2 + var4 - 2, 0, 7170651);
      RSFont var5 = this.getFontBold12();
      var5.drawTextLeftAligned("Choose Option", var1 + 3, var2 + 14, 13023381, -1);
      int var6 = this.getMouseX();
      int var7 = this.getMouseY();
      int var8 = this.getMenuOptionCount();
      String[] var9 = this.getMenuTargets();
      String[] var10 = this.getMenuOptions();

      for(int var11 = 0; var11 < var8; ++var11) {
         int var12 = (var8 - 1 - var11) * 15 + var2 + 31;
         String var13 = var10[var11];
         if(!var9[var11].isEmpty()) {
            var13 = var13 + " " + var9[var11];
         }

         var5.drawTextLeftAligned(var13, var1 + 3, var12, 13023381, -1);
         if(var6 > var1 && var6 < var3 + var1 && var7 > var12 - 13 && var7 < var12 + 3) {
            this.rasterizerFillRectangleAlpha(var1 + 3, var12 - 12, var3 - 6, 15, 16777215, 80);
         }
      }

   }

   public MenuEntry getLeftClickMenuEntry() {
      int var1 = this.getMenuOptionCount() - 1;
      return new MenuEntry(this.getMenuOptions()[var1], this.getMenuTargets()[var1], this.getMenuIdentifiers()[var1], this.getMenuOpcodes()[var1], this.getMenuArguments1()[var1], this.getMenuArguments2()[var1], this.getMenuForceLeftClick()[var1]);
   }

   public void setLeftClickMenuEntry(MenuEntry var1) {
      int var2 = this.getMenuOptionCount() - 1;
      this.getMenuOptions()[var2] = var1.getOption();
      this.getMenuTargets()[var2] = var1.getTarget();
      this.getMenuIdentifiers()[var2] = var1.getIdentifier();
      this.getMenuOpcodes()[var2] = var1.getOpcode();
      this.getMenuArguments1()[var2] = var1.getParam0();
      this.getMenuArguments2()[var2] = var1.getParam1();
      this.getMenuForceLeftClick()[var2] = var1.isForceLeftClick();
   }

   public void setVarbit(Varbits var1, int var2) {
      int var3 = var1.getId();
      this.setVarbitValue(this.getVarps(), var3, var2);
   }

   public int getVar(VarClientInt var1) {
      Map var2 = this.getVarcMap();
      Object var3 = var2.get(Integer.valueOf(var1.getIndex()));
      return var3 instanceof Integer?((Integer)var3).intValue():0;
   }

   public String getVar(VarClientStr var1) {
      Map var2 = this.getVarcMap();
      Object var3 = var2.get(Integer.valueOf(var1.getIndex()));
      return var3 instanceof String?(String)var3:"";
   }

   public void setVar(VarClientStr var1, String var2) {
      Map var3 = this.getVarcMap();
      var3.put(Integer.valueOf(var1.getIndex()), var2);
   }

   public void setVar(VarClientInt var1, int var2) {
      Map var3 = this.getVarcMap();
      var3.put(Integer.valueOf(var1.getIndex()), Integer.valueOf(var2));
   }

   public void openWorldHopper() {
      this.invokeMenuAction(-1, WidgetInfo.WORLD_SWITCHER_BUTTON.getId(), MenuOpcode.WIDGET_DEFAULT.getId(), 1, "World Switcher", "", 658, 384);
   }

   public void hopToWorld(net.runelite.api.World var1) {
      int var2 = var1.getId();
      this.invokeMenuAction(var2, WidgetInfo.WORLD_SWITCHER_LIST.getId(), MenuOpcode.WIDGET_DEFAULT.getId(), 1, "Switch", "<col=ff9040>" + (var2 - 300) + "</col>", 683, 244);
   }

   public void setIsHidingEntities(boolean var1) {
      isHidingEntities = var1;
   }

   public void setPlayersHidden(boolean var1) {
      hidePlayers = var1;
   }

   public void setPlayersHidden2D(boolean var1) {
      hidePlayers2D = var1;
   }

   public void setFriendsHidden(boolean var1) {
      hideFriends = var1;
   }

   public void setClanMatesHidden(boolean var1) {
      hideClanMates = var1;
   }

   public void setLocalPlayerHidden(boolean var1) {
      hideLocalPlayer = var1;
   }

   public void setLocalPlayerHidden2D(boolean var1) {
      hideLocalPlayer2D = var1;
   }

   public void setNPCsHidden(boolean var1) {
      hideNPCs = var1;
   }

   public void setNPCsHidden2D(boolean var1) {
      hideNPCs2D = var1;
   }

   public void addHiddenNpcName(String var1) {
      var1 = var1.toLowerCase();
      int var2 = ((Integer)hiddenNpcsName.getOrDefault(var1, Integer.valueOf(0))).intValue();
      if(var2 == Integer.MAX_VALUE) {
         throw new RuntimeException("NPC name " + var1 + " has been hidden Integer.MAX_VALUE times, is something wrong?");
      } else {
         ++var2;
         hiddenNpcsName.put(var1, Integer.valueOf(var2));
      }
   }

   public void removeHiddenNpcName(String var1) {
      var1 = var1.toLowerCase();
      int var2 = ((Integer)hiddenNpcsName.getOrDefault(var1, Integer.valueOf(0))).intValue();
      if(var2 != 0) {
         --var2;
         hiddenNpcsName.put(var1, Integer.valueOf(var2));
      }
   }

   public void forciblyUnhideNpcName(String var1) {
      var1 = var1.toLowerCase();
      hiddenNpcsName.put(var1, Integer.valueOf(0));
   }

   public void addHiddenNpcDeath(String var1) {
      var1 = var1.toLowerCase();
      int var2 = ((Integer)hiddenNpcsDeath.getOrDefault(var1, Integer.valueOf(0))).intValue();
      if(var2 == Integer.MAX_VALUE) {
         throw new RuntimeException("NPC death " + var1 + " has been hidden Integer.MAX_VALUE times, is something wrong?");
      } else {
         ++var2;
         hiddenNpcsDeath.put(var1, Integer.valueOf(var2));
      }
   }

   public void removeHiddenNpcDeath(String var1) {
      var1 = var1.toLowerCase();
      int var2 = ((Integer)hiddenNpcsDeath.getOrDefault(var1, Integer.valueOf(0))).intValue();
      if(var2 != 0) {
         --var2;
         hiddenNpcsDeath.put(var1, Integer.valueOf(var2));
      }
   }

   public void forciblyUnhideNpcDeath(String var1) {
      var1 = var1.toLowerCase();
      hiddenNpcsDeath.put(var1, Integer.valueOf(0));
   }

   public void setHideSpecificPlayers(List var1) {
      hideSpecificPlayers = var1;
   }

   public void setAttackersHidden(boolean var1) {
      hideAttackers = var1;
   }

   public void setProjectilesHidden(boolean var1) {
      hideProjectiles = var1;
   }

   public void setDeadNPCsHidden(boolean var1) {
      hideDeadNPCs = var1;
   }

   public RSWorld createWorld() {
      return new World();
   }

   public RSWidget createWidget() {
      return new Widget();
   }

   public RSNodeDeque newNodeDeque() {
      return new NodeDeque();
   }

   public RSTileItem newTileItem() {
      return new TileItem();
   }

   public RSIndexedSprite createIndexedSprite() {
      return new IndexedSprite();
   }

   public RSClientPreferences getPreferences() {
      return AbstractArchive.clientPreferences;
   }

   public RSIndexedSprite[] getModIcons() {
      return AbstractFont.AbstractFont_modIconSprites;
   }

   public void setSelectedItemID(int var1) {
      AbstractWorldMapData.selectedItemId = var1;
   }

   public RSTextureProvider getTextureProvider() {
      return Archive.textureProvider;
   }

   public void setCompass(net.runelite.api.Sprite var1) {
      AttackOption.compass = (Sprite)var1;
   }

   public void setSelectedSpellWidget(int var1) {
      AttackOption.selectedSpellWidget = var1;
   }

   public int getSelectedSpellWidget() {
      return AttackOption.selectedSpellWidget;
   }

   public void insertMenuItem(String var1, String var2, int var3, int var4, int var5, int var6, boolean var7) {
      AttackOption.method2104(var1, var2, var3, var4, var5, var6, var7);
   }

   public RSSprite[] getMapDots() {
      return class17.mapDotSprites;
   }

   public RSIndexedSprite[] getMapScene() {
      return class125.mapSceneSprites;
   }

   public int getOculusOrbFocalPointY() {
      return class125.oculusOrbFocalPointY;
   }

   public void setSelectedItemWidget(int var1) {
      class12.selectedItemWidget = var1;
   }

   public int getBaseX() {
      return class215.baseX;
   }

   public RSPlayer getLocalPlayer() {
      return class215.localPlayer;
   }

   public RSFrames getFrames(int var1) {
      return class210.method3930(var1);
   }

   public int getBaseY() {
      return class304.baseY;
   }

   public int[][] getXteaKeys() {
      return class289.xteaKeys;
   }

   public void changeWorld(net.runelite.api.World var1) {
      class8.method101((World)var1);
   }

   public RSWidget getDraggedWidget() {
      return clickedWidget;
   }

   public RSNodeHashTable getComponentTable() {
      return interfaceParents;
   }

   public int[] getWidgetPositionsX() {
      return rootWidgetXs;
   }

   public RSNodeHashTable getWidgetFlags() {
      return widgetClickMasks;
   }

   public void setGameDrawingMode(int var1) {
      gameDrawingMode = var1;
   }

   public int getGameDrawingMode() {
      return gameDrawingMode;
   }

   public int[] getWidgetPositionsY() {
      return rootWidgetYs;
   }

   public boolean isDraggingWidget() {
      return isDraggingWidget;
   }

   public int getEnergy() {
      return runEnergy;
   }

   public int getWeight() {
      return weight;
   }

   public void setDraggedOnWidget(net.runelite.api.widgets.Widget var1) {
      draggedOnWidget = (Widget)var1;
   }

   public RSWidget getDraggedOnWidget() {
      return draggedOnWidget;
   }

   public RSGrandExchangeOffer[] getGrandExchangeOffers() {
      return grandExchangeOffers;
   }

   public RSSprite[] getMapIcons() {
      return mapIcons;
   }

   public int getScale() {
      return viewportZoom;
   }

   public int getViewportWidth() {
      return viewportWidth;
   }

   public int getViewportHeight() {
      return viewportHeight;
   }

   public int getViewportXOffset() {
      return viewportOffsetX;
   }

   public int[] getChangedSkills() {
      return changedSkills;
   }

   public int getViewportYOffset() {
      return viewportOffsetY;
   }

   public void setChangedSkillsCount(int var1) {
      changedSkillsCount = var1;
   }

   public int getChangedSkillsCount() {
      return changedSkillsCount;
   }

   public RSCollisionMap[] getCollisionMaps() {
      return collisionMaps;
   }

   public int getWorld() {
      return worldId;
   }

   public int getGameCycle() {
      return cycle;
   }

   public void setHintArrowTargetType(int var1) {
      hintArrowType = var1;
   }

   public int getHintArrowTargetType() {
      return hintArrowType;
   }

   public void setHintArrowNpcTargetIdx(int var1) {
      hintArrowNpcIndex = var1;
   }

   public int getHintArrowNpcTargetIdx() {
      return hintArrowNpcIndex;
   }

   public void setHintArrowPlayerTargetIdx(int var1) {
      hintArrowPlayerIndex = var1;
   }

   public int getHintArrowPlayerTargetIdx() {
      return hintArrowPlayerIndex;
   }

   public void setHintArrowX(int var1) {
      hintArrowX = var1;
   }

   public int getHintArrowX() {
      return hintArrowX;
   }

   public void setHintArrowY(int var1) {
      hintArrowY = var1;
   }

   public int getHintArrowY() {
      return hintArrowY;
   }

   public void setHintArrowOffsetX(int var1) {
      hintArrowSubX = var1 * -759871936;
   }

   public void setHintArrowOffsetY(int var1) {
      hintArrowSubY = var1 * 99349824;
   }

   public boolean isInInstancedRegion() {
      return isInInstance;
   }

   public int[][][] getInstanceTemplateChunks() {
      return instanceChunkTemplates;
   }

   public void setCameraPitchTarget(int var1) {
      camAngleX = var1;
   }

   public int getCameraPitchTarget() {
      return camAngleX;
   }

   public int getMapAngle() {
      return camAngleY;
   }

   public void setOculusOrbState(int var1) {
      oculusOrbState = var1;
   }

   public int getOculusOrbState() {
      return oculusOrbState;
   }

   public void setOculusOrbNormalSpeed(int var1) {
      oculusOrbNormalSpeed = var1;
   }

   public int[][] getOccupiedTilesTick() {
      return tileLastDrawnActor;
   }

   public int getIf1DraggedItemIndex() {
      return dragItemSlotSource;
   }

   public void setItemPressedDuration(int var1) {
      itemDragDuration = var1;
   }

   public int getItemPressedDuration() {
      return itemDragDuration;
   }

   public int getLocalPlayerIndex() {
      return localPlayerIndex;
   }

   public void setRenderSelf(boolean var1) {
      renderSelf = var1;
   }

   public boolean getRenderSelf() {
      return renderSelf;
   }

   public int[] getPlayerMenuTypes() {
      return playerMenuOpcodes;
   }

   public String[] getPlayerOptions() {
      return playerMenuActions;
   }

   public boolean[] getPlayerOptionsPriorities() {
      return playerOptionsPriorities;
   }

   public int getLocalInteractingIndex() {
      return combatTargetPlayerIndex;
   }

   public RSNodeDeque[][][] getGroundItemDeque() {
      return groundItems;
   }

   public boolean isMenuOpen() {
      return isMenuOpen;
   }

   public void setSpellSelected(boolean var1) {
      isSpellSelected = var1;
   }

   public boolean isSpellSelected() {
      return isSpellSelected;
   }

   public void setSelectedSpellChildIndex(int var1) {
      selectedSpellChildIndex = var1;
   }

   public int getSelectedSpellChildIndex() {
      return selectedSpellChildIndex;
   }

   public void setSelectedSpellName(String var1) {
      selectedSpellName = var1;
   }

   public String getSelectedSpellName() {
      return selectedSpellName;
   }

   public void revalidateWidget(net.runelite.api.widgets.Widget var1) {
      this.method1774((Widget)var1);
   }

   public RSWorldMap getRenderOverview() {
      return Decimator.method2485();
   }

   public void setOtp(String var1) {
      DesktopPlatformInfoProvider.otp = var1;
   }

   public void setSelectedItemSlot(int var1) {
      DevicePcmPlayerProvider.selectedItemSlot = var1;
   }

   public int getCanvasWidth() {
      return FloorDecoration.canvasWidth;
   }

   public RSWidget getIf1DraggedWidget() {
      return Frames.dragInventoryWidget;
   }

   public int getFPS() {
      return GameShell.fps;
   }

   public void revalidateWidgetScroll(net.runelite.api.widgets.Widget[] var1, net.runelite.api.widgets.Widget var2, boolean var3) {
      GameShell.method1005((Widget[])var1, (Widget)var2, var3);
   }

   public RSAbstractArchive getIndexSprites() {
      return GrandExchangeOfferAgeComparator.archive8;
   }

   public RSAbstractArchive getIndexScripts() {
      return GrandExchangeOfferOwnWorldComparator.archive12;
   }

   public int getCameraX() {
      return GrandExchangeOfferOwnWorldComparator.cameraX;
   }

   public RSObjectDefinition getObjectDefinition(int var1) {
      return GrandExchangeOfferOwnWorldComparator.getObjectDefinition(var1);
   }

   public void setCameraPitch(int var1) {
      IgnoreList.cameraPitch = var1;
   }

   public int getCameraPitch() {
      return IgnoreList.cameraPitch;
   }

   public int[] getIntStack() {
      return Interpreter.Interpreter_intStack;
   }

   public void setIntStackSize(int var1) {
      Interpreter.Interpreter_intStackSize = var1;
   }

   public int getIntStackSize() {
      return Interpreter.Interpreter_intStackSize;
   }

   public String[] getStringStack() {
      return Interpreter.Interpreter_stringStack;
   }

   public void setStringStackSize(int var1) {
      Interpreter.Interpreter_stringStackSize = var1;
   }

   public int getStringStackSize() {
      return Interpreter.Interpreter_stringStackSize;
   }

   public void sendMenuAction(int var1, int var2, int var3, int var4, String var5, String var6, int var7, int var8) {
      InvDefinition.method4339(var1, var2, var3, var4, var5, var6, var7, var8, 1826014571);
   }

   public RSEvictingDualNodeHashTable getItemDefinitionCache() {
      return ItemDefinition.ItemDefinition_cached;
   }

   public int getItemCount() {
      return JagexCache.ItemDefinition_fileCount;
   }

   public boolean[] getPressedKeys() {
      return KeyHandler.KeyHandler_pressedKeys;
   }

   public int getKeyboardIdleTicks() {
      return KeyHandler.KeyHandler_idleCycles;
   }

   public int getLoginIndex() {
      return Login.loginIndex;
   }

   public void setUsername(String var1) {
      Login.Login_username = var1;
   }

   public String getUsername() {
      return Login.Login_username;
   }

   public void setPassword(String var1) {
      Login.Login_password = var1;
   }

   public int getCurrentLoginField() {
      return Login.currentLoginField;
   }

   public void queueSoundEffect(int var1, int var2, int var3) {
      Message.method888(var1, var2, var3);
   }

   public Map getChatLineMap() {
      return Messages.Messages_channels;
   }

   public RSIterableNodeHashTable getMessages() {
      return Messages.Messages_hashTable;
   }

   public void setAnimOffsetX(int var1) {
      Model.Model_transformTempX = var1;
   }

   public void setAnimOffsetY(int var1) {
      Model.Model_transformTempY = var1;
   }

   public void setAnimOffsetZ(int var1) {
      Model.Model_transformTempZ = var1;
   }

   public int getMouseIdleTicks() {
      return MouseHandler.MouseHandler_idleCycles;
   }

   public int getMouseCurrentButton() {
      return MouseHandler.MouseHandler_currentButton;
   }

   public long getMouseLastPressedMillis() {
      return MouseHandler.MouseHandler_lastPressedTimeMillis;
   }

   public int[] getMapRegions() {
      return MouseHandler.regions;
   }

   public void setGameState(int var1) {
      MouseRecorder.method1207(var1);
   }

   public RSEvictingDualNodeHashTable getCachedModels2() {
      return ObjectDefinition.ObjectDefinition_cachedModels;
   }

   public int getOculusOrbFocalPointX() {
      return ObjectSound.oculusOrbFocalPointX;
   }

   public RSItemDefinition getItemDefinition(int var1) {
      return Occluder.getItemDefinition(var1);
   }

   public RSNPCDefinition getNpcDefinition(int var1) {
      return PacketBufferNode.getNpcDefinition(var1);
   }

   public RSMenuAction getTempMenuAction() {
      return ParamDefinition.tempMenuAction;
   }

   public int[] getGraphicsPixels() {
      return Rasterizer2D.Rasterizer2D_pixels;
   }

   public int getGraphicsPixelsWidth() {
      return Rasterizer2D.Rasterizer2D_width;
   }

   public int getGraphicsPixelsHeight() {
      return Rasterizer2D.Rasterizer2D_height;
   }

   public int getStartY() {
      return Rasterizer2D.Rasterizer2D_yClipStart;
   }

   public int getEndY() {
      return Rasterizer2D.Rasterizer2D_yClipEnd;
   }

   public int getStartX() {
      return Rasterizer2D.Rasterizer2D_xClipStart;
   }

   public int getEndX() {
      return Rasterizer2D.Rasterizer2D_xClipEnd;
   }

   public int getCenterX() {
      return Rasterizer3D.Rasterizer3D_clipMidX;
   }

   public int getCenterY() {
      return Rasterizer3D.Rasterizer3D_clipMidY;
   }

   public int getRasterizer3D_clipNegativeMidX() {
      return Rasterizer3D.Rasterizer3D_clipNegativeMidX;
   }

   public int getRasterizer3D_clipMidX2() {
      return Rasterizer3D.Rasterizer3D_clipMidX2;
   }

   public int getRasterizer3D_clipNegativeMidY() {
      return Rasterizer3D.Rasterizer3D_clipNegativeMidY;
   }

   public int getRasterizer3D_clipMidY2() {
      return Rasterizer3D.Rasterizer3D_clipMidY2;
   }

   public int getRevision() {
      return RunException.RunException_revision;
   }

   public void setTileUpdateCount(int var1) {
      Scene.tileUpdateCount = var1;
   }

   public int getTileUpdateCount() {
      return Scene.tileUpdateCount;
   }

   public void setScenePlane(int var1) {
      Scene.Scene_plane = var1;
   }

   public void setCycle(int var1) {
      Scene.Scene_drawnCount = var1;
   }

   public int getCycle() {
      return Scene.Scene_drawnCount;
   }

   public void setMinTileX(int var1) {
      Scene.Scene_cameraXTileMin = var1;
   }

   public void setMaxTileX(int var1) {
      Scene.Scene_cameraXTileMax = var1;
   }

   public void setMinTileZ(int var1) {
      Scene.Scene_cameraYTileMin = var1;
   }

   public void setMaxTileZ(int var1) {
      Scene.Scene_cameraYTileMax = var1;
   }

   public void setScreenCenterX(int var1) {
      Scene.Scene_cameraXTile = var1;
   }

   public void setScreenCenterZ(int var1) {
      Scene.Scene_cameraYTile = var1;
   }

   public void setCameraX2(int var1) {
      Scene.Scene_cameraX = var1;
   }

   public int getCameraX2() {
      return Scene.Scene_cameraX;
   }

   public void setCameraY2(int var1) {
      Scene.Scene_cameraY = var1;
   }

   public int getCameraY2() {
      return Scene.Scene_cameraY;
   }

   public void setCameraZ2(int var1) {
      Scene.Scene_cameraZ = var1;
   }

   public int getCameraZ2() {
      return Scene.Scene_cameraZ;
   }

   public void setPitchSin(int var1) {
      Scene.Scene_cameraPitchSine = var1;
   }

   public void setPitchCos(int var1) {
      Scene.Scene_cameraPitchCosine = var1;
   }

   public void setYawSin(int var1) {
      Scene.Scene_cameraYawSine = var1;
   }

   public void setYawCos(int var1) {
      Scene.Scene_cameraYawCosine = var1;
   }

   public void setCheckClick(boolean var1) {
      Scene.checkClick = var1;
   }

   public boolean isCheckClick() {
      return Scene.checkClick;
   }

   public void setMouseCanvasHoverPositionX(int var1) {
      Scene.Scene_selectedScreenX = var1;
   }

   public int getMouseX2() {
      return Scene.Scene_selectedScreenX;
   }

   public void setMouseCanvasHoverPositionY(int var1) {
      Scene.Scene_selectedScreenY = var1;
   }

   public int getMouseY2() {
      return Scene.Scene_selectedScreenY;
   }

   public void setSelectedSceneTileX(int var1) {
      Scene.Scene_selectedX = var1;
   }

   public void setSelectedSceneTileY(int var1) {
      Scene.Scene_selectedY = var1;
   }

   public void setViewportWalking(boolean var1) {
      Scene.viewportWalking = var1;
   }

   public boolean[][][][] getVisibilityMaps() {
      return Scene.visibilityMap;
   }

   public void setRenderArea(boolean[][] var1) {
      Scene.visibleTiles = var1;
   }

   public boolean containsBounds(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      return Scene.method3252(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public void scaleSprite(int[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12) {
      Sprite.method6111(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }

   public void promptCredentials(boolean var1) {
      TilePaint.method2911(var1);
   }

   public int[][][] getTileHeights() {
      return Tiles.Tiles_heights;
   }

   public RSSprite[] getCrossSprites() {
      return UrlRequest.crossSprites;
   }

   public RSEvictingDualNodeHashTable getVarbitCache() {
      return VarbitDefinition.VarbitDefinition_cached;
   }

   public int getCameraZ() {
      return Varcs.cameraY;
   }

   public boolean getViewportContainsMouse() {
      return ViewportMouse.ViewportMouse_isInViewport;
   }

   public int getViewportMouseX() {
      return ViewportMouse.ViewportMouse_x;
   }

   public int getViewportMouseY() {
      return ViewportMouse.ViewportMouse_y;
   }

   public void setEntitiesAtMouseCount(int var1) {
      ViewportMouse.ViewportMouse_entityCount = var1;
   }

   public int getEntitiesAtMouseCount() {
      return ViewportMouse.ViewportMouse_entityCount;
   }

   public long[] getEntitiesAtMouse() {
      return ViewportMouse.ViewportMouse_entityTags;
   }

   public int getCanvasHeight() {
      return WallDecoration.canvasHeight;
   }

   public RSEvictingDualNodeHashTable getWidgetSpriteCache() {
      return Widget.Widget_cachedSprites;
   }

   public RSWorld[] getWorldList() {
      return World.World_worlds;
   }

   public void updateItemPile(int var1, int var2) {
      WorldMapCacheName.method673(var1, var2);
   }

   public RSWorldMapElement[] getMapElementConfigs() {
      return WorldMapElement.WorldMapElement_cached;
   }

   public int getCameraY() {
      return WorldMapIcon_1.cameraZ;
   }

   public RSMouseRecorder getMouseRecorder() {
      return WorldMapLabel.mouseRecorder;
   }

   public int getCameraYaw() {
      return WorldMapSection2.cameraYaw;
   }

   public int getVarbit(int var1) {
      return WorldMapSprite.method782(var1);
   }

   @ObfuscatedName("eh")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1951808439"
   )
   void method1890() {
      if(false && NetCache.NetCache_crcMismatches >= 4) {
         this.method984("js5crc");
         gameState = 1000;
         gameStateChanged(-1);
      } else {
         if(NetCache.NetCache_ioExceptions >= 4) {
            if(gameState <= 5) {
               this.method984("js5io");
               gameState = 1000;
               gameStateChanged(-1);
               return;
            }

            field872 = 3000;
            NetCache.NetCache_ioExceptions = 3;
         }

         if(--field872 + 1 <= 0) {
            try {
               if(js5ConnectState == 0) {
                  //TODO: Modified OLD:[NPC.myWorldPort]
                  Login.js5SocketTask = GameShell.taskHandler.method3418(CustomMain.worldType.getFileServerAddress(), 7304);
                  ++js5ConnectState;
               }

               if(js5ConnectState == 1) {
                  if(Login.js5SocketTask.status == 2) {
                     this.method1652(-1);
                     return;
                  }

                  if(Login.js5SocketTask.status == 1) {
                     ++js5ConnectState;
                  }
               }

               if(js5ConnectState == 2) {
                  if(useBufferedSocket) {
                     Socket var2 = (Socket)Login.js5SocketTask.result;
                     BufferedNetSocket var1 = new BufferedNetSocket(var2, 40000, 5000);
                     class22.js5Socket = var1;
                  } else {
                     class22.js5Socket = new NetSocket((Socket)Login.js5SocketTask.result, GameShell.taskHandler, 5000);
                  }

                  Buffer var5 = new Buffer(5);
                  var5.writeByte(15);
                  var5.writeInt(184);
                  class22.js5Socket.vmethod5817(var5.array, 0, 5);
                  ++js5ConnectState;
                  VerticalAlignment.field3264 = class33.method680();
               }

               if(js5ConnectState == 3) {
                  if(class22.js5Socket.vmethod5838() <= 0 && (useBufferedSocket || gameState > 5)) {
                     if(class33.method680() - VerticalAlignment.field3264 > 30000L) {
                        this.method1652(-2);
                        return;
                     }
                  } else {
                     int var3 = class22.js5Socket.vmethod5815();
                     if(var3 != 0) {
                        this.method1652(var3);
                        return;
                     }

                     ++js5ConnectState;
                  }
               }

               if(js5ConnectState == 4) {
                  MenuAction.method2160(class22.js5Socket, gameState > 20);
                  Login.js5SocketTask = null;
                  class22.js5Socket = null;
                  js5ConnectState = 0;
                  js5Errors = 0;
               }

            } catch (IOException var4) {
               this.method1652(-3);
            }
         }
      }
   }

   @ObfuscatedName("em")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "0"
   )
   void method1652(int var1) {
      Login.js5SocketTask = null;
      class22.js5Socket = null;
      js5ConnectState = 0;
      if(NPC.port3 == HitSplatDefinition.port1) {
         NPC.port3 = BZip2State.port2;
      } else {
         NPC.port3 = HitSplatDefinition.port1;
      }

      ++js5Errors;
      if(js5Errors >= 2 && (var1 == 7 || var1 == 9)) {
         if(gameState <= 5) {
            this.method984("js5connect_full");
            gameState = 1000;
            gameStateChanged(-1);
         } else {
            field872 = 3000;
         }
      } else if(js5Errors >= 2 && var1 == 6) {
         this.method984("js5connect_outofdate");
         gameState = 1000;
         gameStateChanged(-1);
      } else if(js5Errors >= 4) {
         if(gameState <= 5) {
            this.method984("js5connect");
            gameState = 1000;
            gameStateChanged(-1);
         } else {
            field872 = 3000;
         }
      }

   }

   @ObfuscatedName("en")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "942594737"
   )
   void method1650() {
      if(gameState != 1000) {
         boolean var1 = PlayerAppearance.method4159();
         if(!var1) {
            this.method1890();
         }

      }
   }

   @ObfuscatedName("ey")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "684778966"
   )
   final void method1653() {
      Object var1 = packetWriter.method1624();
      PacketBuffer var2 = packetWriter.packetBuffer;

      try {
         if(loginState == 0) {
            if(GraphicsObject.secureRandom == null && (secureRandomFuture.method1516() || field877 > 250)) {
               GraphicsObject.secureRandom = secureRandomFuture.method1518();
               secureRandomFuture.method1515();
               secureRandomFuture = null;
            }

            if(GraphicsObject.secureRandom != null) {
               if(var1 != null) {
                  ((AbstractSocket)var1).vmethod5821();
                  var1 = null;
               }

               LoginPacket.socketTask = null;
               field938 = false;
               field877 = 0;
               loginState = 1;
            }
         }

         if(loginState == 1) {
            if(LoginPacket.socketTask == null) {
               //TODO: Modified [OLD: NPC.myWorldPort]
               LoginPacket.socketTask = GameShell.taskHandler.method3418(class158.worldHost, 13302);
            }

            if(LoginPacket.socketTask.status == 2) {
               throw new IOException();
            }

            if(LoginPacket.socketTask.status == 1) {
               if(useBufferedSocket) {
                  Socket var4 = (Socket)LoginPacket.socketTask.result;
                  BufferedNetSocket var3 = new BufferedNetSocket(var4, 40000, 5000);
                  var1 = var3;
               } else {
                  var1 = new NetSocket((Socket)LoginPacket.socketTask.result, GameShell.taskHandler, 5000);
               }

               packetWriter.method1621((AbstractSocket)var1);
               LoginPacket.socketTask = null;
               loginState = 2;
            }
         }

         if(loginState == 2) {
            packetWriter.method1618();
            PacketBufferNode var22 = Tile.method2444();
            var22.packetBuffer.writeByte(LoginPacket.handshake.id);
            packetWriter.method1622(var22);
            packetWriter.method1619();
            var2.offset = 0;
            loginState = 3;
         }

         boolean var13;
         int var14;
         if(loginState == 3) {
            if(class213.pcmPlayer0 != null) {
               class213.pcmPlayer0.method2705();
            }

            if(GrandExchangeOfferWorldComparator.pcmPlayer1 != null) {
               GrandExchangeOfferWorldComparator.pcmPlayer1.method2705();
            }

            var13 = true;
            if(useBufferedSocket && !((AbstractSocket)var1).vmethod5816(1)) {
               var13 = false;
            }

            if(var13) {
               var14 = ((AbstractSocket)var1).vmethod5815();
               if(class213.pcmPlayer0 != null) {
                  class213.pcmPlayer0.method2705();
               }

               if(GrandExchangeOfferWorldComparator.pcmPlayer1 != null) {
                  GrandExchangeOfferWorldComparator.pcmPlayer1.method2705();
               }

               if(var14 != 0) {
                  ArchiveDisk.method5318(var14);
                  return;
               }

               var2.offset = 0;
               loginState = 4;
            }
         }

         int var33;
         if(loginState == 4) {
            if(var2.offset < 8) {
               var33 = ((AbstractSocket)var1).vmethod5838();
               if(var33 > 8 - var2.offset) {
                  var33 = 8 - var2.offset;
               }

               if(var33 > 0) {
                  ((AbstractSocket)var1).vmethod5832(var2.array, var2.offset, var33);
                  var2.offset += var33;
               }
            }

            if(var2.offset == 8) {
               var2.offset = 0;
               Tiles.field534 = var2.method5502();
               loginState = 5;
            }
         }

         int var6;
         if(loginState == 5) {
            packetWriter.packetBuffer.offset = 0;
            packetWriter.method1618();
            PacketBuffer rsaBuffer = new PacketBuffer(500);

            int[] var25 = new int[]{(int) (Math.random() * 9.9999999E7D),
                    (int) (Math.random() * 9.9999999E7D), (int) (Tiles.field534 >> 32),
                    (int) (Tiles.field534 & 0xFFFFFFFFFFFFFFFFL)};
            //TODO: MODIFIED
            //int[] var25 = new int[]{GraphicsObject.secureRandom.nextInt(), GraphicsObject.secureRandom.nextInt(), GraphicsObject.secureRandom.nextInt(), GraphicsObject.secureRandom.nextInt()};

            rsaBuffer.offset = 0;
            rsaBuffer.writeByte(1); //encryption succeed.
            rsaBuffer.writeInt(var25[0]); //rsa key
            rsaBuffer.writeInt(var25[1]); //rsa key
            rsaBuffer.writeInt(var25[2]); //rsa key
            rsaBuffer.writeInt(var25[3]); //rsa key
            rsaBuffer.method5485(Tiles.field534); //seed
            if(gameState == 40) {
               rsaBuffer.writeInt(DirectByteArrayCopier.field2133[0]);
               rsaBuffer.writeInt(DirectByteArrayCopier.field2133[1]);
               rsaBuffer.writeInt(DirectByteArrayCopier.field2133[2]);
               rsaBuffer.writeInt(DirectByteArrayCopier.field2133[3]);
            } else { //authenticator
               rsaBuffer.writeByte(loginType.getId());
               switch(loginType.type) {
               case 0:
               case 1: //2fa code
                  rsaBuffer.write24BitInteger(class202.field2335);
                  ++rsaBuffer.offset;
                  break;
               case 2: // no code trust the key
                  rsaBuffer.offset += 4;
                  break;
               case 3: //login with a trust key
                  rsaBuffer.writeInt(((Integer)AbstractArchive.clientPreferences.parameters.get(Integer.valueOf(class292.method5270(Login.Login_username)))).intValue());
               }

               rsaBuffer.writeByte(class319.field3904.getId());
               rsaBuffer.writeString(Login.Login_password);
            }

            rsaBuffer.createSecureBuffer(class78.field803, class78.field804, -2071004135);
            DirectByteArrayCopier.field2133 = var25;
            PacketBufferNode var5 = Tile.method2444();
            var5.packetBuffer.offset = 0;
            if(gameState == 40) {
               var5.packetBuffer.writeByte(LoginPacket.requestWorldReconnect.id);
            } else {
               var5.packetBuffer.writeByte(LoginPacket.requestWorldLogin.id);
            }

            var5.packetBuffer.method5481(0);
            var6 = var5.packetBuffer.offset;
            var5.packetBuffer.writeInt(RuneLiteProperties.getMainRevision());
            var5.packetBuffer.writeInt(RuneLiteProperties.getSubRevision());//TODO:: Modified 1->2
            var5.packetBuffer.writeByte(clientType); //buffer length
            var5.packetBuffer.writeBytes(rsaBuffer.array, 0, rsaBuffer.offset); //write rsa keys
            int rsaOffset = var5.packetBuffer.offset;
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] macAddress = network.getHardwareAddress();
            var5.packetBuffer.writeByte(macAddress.length);
            var5.packetBuffer.writeBytes(macAddress, 0, macAddress.length);
            var5.packetBuffer.writeString(field10222);
            var5.packetBuffer.writeString(Login.Login_username);
            var5.packetBuffer.writeByte((isResizable ? 1 : 0) << 1 | (isLowDetail ? 1 : 0));
            var5.packetBuffer.method5481(FloorDecoration.canvasWidth);
            var5.packetBuffer.method5481(WallDecoration.canvasHeight);

            PacketBuffer var8 = var5.packetBuffer;
            int var11;
            if(randomDatData != null) {
               var8.writeBytes(randomDatData, 0, randomDatData.length);
            } else {
               byte[] var10 = new byte[24];

               try {
                  JagexCache.JagexCache_randomDat.method22(0L);
                  JagexCache.JagexCache_randomDat.method31(var10);

                  for(var11 = 0; var11 < 24 && var10[var11] == 0; ++var11) {
                     ;
                  }

                  if(var11 >= 24) {
                     throw new IOException();
                  }
               } catch (Exception var20) {
                  for(int var12 = 0; var12 < 24; ++var12) {
                     var10[var12] = -1;
                  }
               }

               var8.writeBytes(var10, 0, var10.length);
            }
            var5.packetBuffer.writeString(class197.field2177);
            var5.packetBuffer.writeInt(WorldMapArea.field140);

            Buffer clientInformation = new Buffer(class261.platformInfo.method6373());
            class261.platformInfo.method6372(clientInformation);
            var5.packetBuffer.writeBytes(clientInformation.array, 0, clientInformation.array.length);
            var5.packetBuffer.writeByte(clientType);
            var5.packetBuffer.writeInt(0);
            //start checksum
            var5.packetBuffer.writeInt(class4.archive0.hash);
            var5.packetBuffer.writeInt(WorldMapLabelSize.archive1.hash);
            var5.packetBuffer.writeInt(FaceNormal.archive2.hash);
            var5.packetBuffer.writeInt(BoundaryObject.archive3.hash);
            var5.packetBuffer.writeInt(GrandExchangeOfferAgeComparator.archive4.hash);
            var5.packetBuffer.writeInt(class11.archive5.hash);
            var5.packetBuffer.writeInt(class212.archive6.hash);
            var5.packetBuffer.writeInt(Language.archive7.hash);
            var5.packetBuffer.writeInt(GrandExchangeOfferAgeComparator.archive8.hash);
            var5.packetBuffer.writeInt(AttackOption.archive9.hash);
            var5.packetBuffer.writeInt(archive10.hash);
            var5.packetBuffer.writeInt(ClanMate.archive11.hash);
            var5.packetBuffer.writeInt(GrandExchangeOfferOwnWorldComparator.archive12.hash);
            var5.packetBuffer.writeInt(Tile.archive13.hash);
            var5.packetBuffer.writeInt(WorldMapData_1.archive14.hash);
            var5.packetBuffer.writeInt(Script.archive15.hash);
            var5.packetBuffer.writeInt(0);
            var5.packetBuffer.writeInt(archive17.hash);
            var5.packetBuffer.writeInt(VarcInt.archive18.hash);
            var5.packetBuffer.writeInt(WorldMapLabel.archive19.hash);
            var5.packetBuffer.writeInt(WorldMapEvent.archive20.hash);
            //end checksum
            var5.packetBuffer.method5635(var25, rsaOffset, var5.packetBuffer.offset);
            var5.packetBuffer.method5492(var5.packetBuffer.offset - var6);
            packetWriter.method1622(var5);
            packetWriter.method1619();
            packetWriter.isaacCipher = new IsaacCipher(var25);
            int[] var32 = new int[4];
            var11 = 0;

            while(true) {
               if(var11 >= 4) {
                  var2.method5273(var32);
                  loginState = 6;
                  break;
               }

               var32[var11] = var25[var11] + 50;
               ++var11;
            }
         }

         if(loginState == 6 && ((AbstractSocket)var1).vmethod5838() > 0) {
            var33 = ((AbstractSocket)var1).vmethod5815();
            if(var33 == 21 && gameState == 20) {
               loginState = 9;
            } else if(var33 == 2) {
               loginState = 11;
            } else if(var33 == 15 && gameState == 40) {
               packetWriter.serverPacketLength = -1;
               loginState = 16;
            } else if(var33 == 64) {
               loginState = 7;
            } else if(var33 == 23 && field878 < 1) {
               ++field878;
               loginState = 0;
            } else {
               if(var33 != 29) {
                  ArchiveDisk.method5318(var33);
                  return;
               }

               loginState = 14;
            }
         }

         if(loginState == 7 && ((AbstractSocket)var1).vmethod5838() > 0) {
            Message.field398 = ((AbstractSocket)var1).vmethod5815();
            loginState = 8;
         }

         if(loginState == 8 && ((AbstractSocket)var1).vmethod5838() >= Message.field398) {
            ((AbstractSocket)var1).vmethod5832(var2.array, 0, Message.field398);
            var2.offset = 0;
            loginState = 6;
         }

         if(loginState == 9 && ((AbstractSocket)var1).vmethod5838() > 0) {
            field879 = (((AbstractSocket)var1).vmethod5815() + 3) * 60;
            loginState = 10;
         }

         if(loginState == 10) {
            field877 = 0;
            PlayerType.method3939("You have only just left another world.", "Your profile will be transferred in:", field879 / 60 + " seconds.");
            if(--field879 <= 0) {
               loginState = 0;
            }

            return;
         }

         if(loginState == 11 && ((AbstractSocket)var1).vmethod5838() >= 1) {
            World.field570 = ((AbstractSocket)var1).vmethod5815();
            loginState = 12;
         }

         int var16;
         if(loginState == 12 && ((AbstractSocket)var1).vmethod5838() >= World.field570) {
            var13 = ((AbstractSocket)var1).vmethod5815() == 1;
            ((AbstractSocket)var1).vmethod5832(var2.array, 0, 4);
            var2.offset = 0;
            boolean var34 = false;
            if(var13) {
               var14 = var2.method5276() << 24;
               var14 |= var2.method5276() << 16;
               var14 |= var2.method5276() << 8;
               var14 |= var2.method5276();
               var16 = class292.method5270(Login.Login_username);
               if(AbstractArchive.clientPreferences.parameters.size() >= 10 && !AbstractArchive.clientPreferences.parameters.containsKey(Integer.valueOf(var16))) {
                  Iterator var17 = AbstractArchive.clientPreferences.parameters.entrySet().iterator();
                  var17.next();
                  var17.remove();
               }

               AbstractArchive.clientPreferences.parameters.put(Integer.valueOf(var16), Integer.valueOf(var14));
            }

            if(Login_isUsernameRemembered) {
               AbstractArchive.clientPreferences.rememberedUsername = Login.Login_username;
            } else {
               AbstractArchive.clientPreferences.rememberedUsername = null;
            }

            Language.method3830();
            staffModLevel = ((AbstractSocket)var1).vmethod5815();
            playerMod = ((AbstractSocket)var1).vmethod5815() == 1;
            localPlayerIndex = ((AbstractSocket)var1).vmethod5815();
            localPlayerIndex <<= 8;
            localPlayerIndex += ((AbstractSocket)var1).vmethod5815();
            field844 = ((AbstractSocket)var1).vmethod5815();
            ((AbstractSocket)var1).vmethod5832(var2.array, 0, 1);
            var2.offset = 0;
            ServerPacket[] var29 = class94.method2222();
            var6 = var2.method5302();
            if(var6 < 0 || var6 >= var29.length) {
               throw new IOException(var6 + " " + var2.offset);
            }

            packetWriter.serverPacket = var29[var6];
            packetWriter.serverPacketLength = packetWriter.serverPacket.length;
            ((AbstractSocket)var1).vmethod5832(var2.array, 0, 2);
            var2.offset = 0;
            packetWriter.serverPacketLength = var2.readUnsignedShort();

            try {
               class27.method539(ViewportMouse.client, "zap");
            } catch (Throwable var19) {
               ;
            }

            loginState = 13;
         }

         if(loginState == 13) {
            if(((AbstractSocket)var1).vmethod5838() >= packetWriter.serverPacketLength) {
               var2.offset = 0;
               ((AbstractSocket)var1).vmethod5832(var2.array, 0, packetWriter.serverPacketLength);
               timer.method4851();
               mouseLastLastPressedTimeMillis = 1L;
               WorldMapLabel.mouseRecorder.index = 0;
               TaskHandler.hasFocus = true;
               hadFocus = true;
               field1068 = -1L;
               class94.reflectionChecks = new IterableNodeDeque();
               packetWriter.method1618();
               packetWriter.packetBuffer.offset = 0;
               packetWriter.serverPacket = null;
               packetWriter.field837 = null;
               packetWriter.field828 = null;
               packetWriter.field838 = null;
               packetWriter.serverPacketLength = 0;
               packetWriter.field834 = 0;
               rebootTimer = 0;
               logoutTimer = 0;
               hintArrowType = 0;
               ScriptEvent.method808();
               MouseHandler.MouseHandler_idleCycles = 0;
               WorldMapDecoration.method5197();
               isItemSelected = 0;
               isSpellSelected = false;
               soundEffectCount = 0;
               queuedSoundEffectCountChanged(-1);
               camAngleY = 0;
               oculusOrbState = 0;
               StudioGame.field2468 = null;
               minimapState = 0;
               field851 = -1;
               destinationX = 0;
               destinationY = 0;
               playerAttackOption = AttackOption.AttackOption_hidden;
               npcAttackOption = AttackOption.AttackOption_hidden;
               npcCount = 0;
               ClientPreferences.method1154();

               for(var33 = 0; var33 < 2048; ++var33) {
                  players[var33] = null;
                  cachedPlayersChanged(var33);
               }

               for(var33 = 0; var33 < 32768; ++var33) {
                  npcs[var33] = null;
                  cachedNPCsChanged(var33);
               }

               combatTargetPlayerIndex = -1;
               projectiles.method5104();
               graphicsObjects.method5104();

               for(var33 = 0; var33 < 4; ++var33) {
                  for(var14 = 0; var14 < 104; ++var14) {
                     for(var16 = 0; var16 < 104; ++var16) {
                        groundItems[var33][var14][var16] = null;
                     }
                  }
               }

               pendingSpawns = new NodeDeque();
               Tiles.friendSystem.method895();

               for(var33 = 0; var33 < VarpDefinition.VarpDefinition_fileCount; ++var33) {
                  VarpDefinition var28 = AbstractWorldMapData.method3328(var33);
                  if(var28 != null) {
                     Varps.Varps_temp[var33] = 0;
                     Varps.Varps_main[var33] = 0;
                     settingsChanged(var33);
                  }
               }

               class197.varcs.method2169();
               followerIndex = -1;
               if(rootInterface != -1) {
                  InterfaceParent.method1137(rootInterface);
               }

               for(InterfaceParent var26 = (InterfaceParent)interfaceParents.method6348(); var26 != null; var26 = (InterfaceParent)interfaceParents.method6345()) {
                  FontName.method5748(var26, true);
               }

               rootInterface = -1;
               interfaceParents = new NodeHashTable(8);
               meslayerContinueWidget = null;
               ScriptEvent.method808();
               playerAppearance.method4152((int[])null, new int[]{0, 0, 0, 0, 0}, false, -1);

               for(var33 = 0; var33 < 8; ++var33) {
                  playerMenuActions[var33] = null;
                  playerOptionsChanged(var33);
                  playerOptionsPriorities[var33] = false;
               }

               FontName.method5744();
               isLoading = true;

               for(var33 = 0; var33 < 100; ++var33) {
                  field1049[var33] = true;
               }

               class217.method3954();
               Varps.clanChat = null;
               clanMemberManagerChanged(-1);

               for(var33 = 0; var33 < 8; ++var33) {
                  grandExchangeOffers[var33] = new GrandExchangeOffer();
                  onGrandExchangeOffersChanged(var33);
               }

               TileItem.grandExchangeEvents = null;
               AttackOption.method2105(var2);
               ServerPacket.field2272 = -1;
               PlayerType.method3945(false, var2);
               packetWriter.serverPacket = null;
            }

            return;
         }

         if(loginState == 14 && ((AbstractSocket)var1).vmethod5838() >= 2) {
            var2.offset = 0;
            ((AbstractSocket)var1).vmethod5832(var2.array, 0, 2);
            var2.offset = 0;
            class28.field202 = var2.readUnsignedShort();
            loginState = 15;
         }

         if(loginState == 15 && ((AbstractSocket)var1).vmethod5838() >= class28.field202) {
            var2.offset = 0;
            ((AbstractSocket)var1).vmethod5832(var2.array, 0, class28.field202);
            var2.offset = 0;
            String var24 = var2.readString();
            String var27 = var2.readString();
            String var30 = var2.readString();
            PlayerType.method3939(var24, var27, var30);
            MouseRecorder.method1207(10);
         }

         if(loginState != 16) {
            ++field877;
            if(field877 > 2000) {
               if(field878 < 1) {
                  if(HitSplatDefinition.port1 == NPC.port3) {
                     NPC.port3 = BZip2State.port2;
                  } else {
                     NPC.port3 = HitSplatDefinition.port1;
                  }

                  ++field878;
                  loginState = 0;
                  return;
               }

               ArchiveDisk.method5318(-3);
               return;
            }

            return;
         }

         if(packetWriter.serverPacketLength == -1) {
            if(((AbstractSocket)var1).vmethod5838() < 2) {
               return;
            }

            ((AbstractSocket)var1).vmethod5832(var2.array, 0, 2);
            var2.offset = 0;
            packetWriter.serverPacketLength = var2.readUnsignedShort();
         }

         if(((AbstractSocket)var1).vmethod5838() >= packetWriter.serverPacketLength) {
            ((AbstractSocket)var1).vmethod5832(var2.array, 0, packetWriter.serverPacketLength);
            var2.offset = 0;
            var33 = packetWriter.serverPacketLength;
            timer.method4853();
            packetWriter.method1618();
            packetWriter.packetBuffer.offset = 0;
            packetWriter.serverPacket = null;
            packetWriter.field837 = null;
            packetWriter.field828 = null;
            packetWriter.field838 = null;
            packetWriter.serverPacketLength = 0;
            packetWriter.field834 = 0;
            rebootTimer = 0;
            ScriptEvent.method808();
            minimapState = 0;
            destinationX = 0;

            for(var14 = 0; var14 < 2048; ++var14) {
               players[var14] = null;
               cachedPlayersChanged(var14);
            }

            class215.localPlayer = null;

            for(var14 = 0; var14 < npcs.length; ++var14) {
               NPC var31 = npcs[var14];
               if(var31 != null) {
                  var31.targetIndex = -1;
                  var31.interactingChanged(-1);
                  var31.false0 = false;
               }
            }

            FontName.method5744();
            MouseRecorder.method1207(30);

            for(var14 = 0; var14 < 100; ++var14) {
               field1049[var14] = true;
            }

            class217.method3954();
            AttackOption.method2105(var2);
            if(var33 == var2.offset) {
               return;
            }

            throw new RuntimeException();
         }
      } catch (IOException var21) {
         if(field878 < 1) {
            if(NPC.port3 == HitSplatDefinition.port1) {
               NPC.port3 = BZip2State.port2;
            } else {
               NPC.port3 = HitSplatDefinition.port1;
            }

            ++field878;
            loginState = 0;
            return;
         }

         ArchiveDisk.method5318(-2);
         return;
      }

   }

   @ObfuscatedName("fd")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1173156947"
   )
   final void method1654() {
      if(rebootTimer > 1) {
         --rebootTimer;
      }

      if(logoutTimer > 0) {
         --logoutTimer;
      }

      if(field938) {
         field938 = false;
         MouseRecorder.method1208();
      } else {
         if(!isMenuOpen) {
            KeyHandler.method505();
         }

         for(int var1 = 0; var1 < 100 && this.parsePacket(packetWriter); ++var1) {
            ;
         }

         if(gameState == 30) {
            while(true) {
               ReflectionCheck var2 = (ReflectionCheck)class94.reflectionChecks.method5044();
               boolean var29;
               if(var2 == null) {
                  var29 = false;
               } else {
                  var29 = true;
               }

               int var3;
               PacketBufferNode var30;
               if(!var29) {
                  PacketBufferNode var14;
                  int var15;
                  if(timer.field3532) {
                     var14 = InterfaceParent.method1140(ClientPacket.field2343, packetWriter.isaacCipher);
                     var14.packetBuffer.writeByte(0);
                     var15 = var14.packetBuffer.offset;
                     timer.method4854(var14.packetBuffer);
                     var14.packetBuffer.method5493(var14.packetBuffer.offset - var15);
                     packetWriter.method1622(var14);
                     timer.method4852();
                  }

                  Object var36 = WorldMapLabel.mouseRecorder.lock;
                  int var4;
                  int var5;
                  int var6;
                  int var7;
                  int var8;
                  int var9;
                  int var10;
                  int var11;
                  int var12;
                  synchronized(WorldMapLabel.mouseRecorder.lock) {
                     if(!field1010) {
                        WorldMapLabel.mouseRecorder.index = 0;
                     } else if(MouseHandler.MouseHandler_lastButton != 0 || WorldMapLabel.mouseRecorder.index >= 40) {
                        var30 = null;
                        var3 = 0;
                        var4 = 0;
                        var5 = 0;
                        var6 = 0;

                        for(var7 = 0; var7 < WorldMapLabel.mouseRecorder.index && (var30 == null || var30.packetBuffer.offset - var3 < 246); ++var7) {
                           var4 = var7;
                           var8 = WorldMapLabel.mouseRecorder.ys[var7];
                           if(var8 < -1) {
                              var8 = -1;
                           } else if(var8 > 65534) {
                              var8 = 65534;
                           }

                           var9 = WorldMapLabel.mouseRecorder.xs[var7];
                           if(var9 < -1) {
                              var9 = -1;
                           } else if(var9 > 65534) {
                              var9 = 65534;
                           }

                           if(var9 != field854 || var8 != field855) {
                              if(var30 == null) {
                                 var30 = InterfaceParent.method1140(ClientPacket.field2344, packetWriter.isaacCipher);
                                 var30.packetBuffer.writeByte(0);
                                 var3 = var30.packetBuffer.offset;
                                 var30.packetBuffer.offset += 2;
                                 var5 = 0;
                                 var6 = 0;
                              }

                              if(field856 != -1L) {
                                 var10 = var9 - field854;
                                 var11 = var8 - field855;
                                 var12 = (int)((WorldMapLabel.mouseRecorder.millis[var7] - field856) / 20L);
                                 var5 = (int)((long)var5 + (WorldMapLabel.mouseRecorder.millis[var7] - field856) % 20L);
                              } else {
                                 var10 = var9;
                                 var11 = var8;
                                 var12 = Integer.MAX_VALUE;
                              }

                              field854 = var9;
                              field855 = var8;
                              if(var12 < 8 && var10 >= -32 && var10 <= 31 && var11 >= -32 && var11 <= 31) {
                                 var10 += 32;
                                 var11 += 32;
                                 var30.packetBuffer.method5481((var12 << 12) + var11 + (var10 << 6));
                              } else if(var12 < 32 && var10 >= -128 && var10 <= 127 && var11 >= -128 && var11 <= 127) {
                                 var10 += 128;
                                 var11 += 128;
                                 var30.packetBuffer.writeByte(var12 + 128);
                                 var30.packetBuffer.method5481(var11 + (var10 << 8));
                              } else if(var12 < 32) {
                                 var30.packetBuffer.writeByte(var12 + 192);
                                 if(var9 != -1 && var8 != -1) {
                                    var30.packetBuffer.writeInt(var9 | var8 << 16);
                                 } else {
                                    var30.packetBuffer.writeInt(Integer.MIN_VALUE);
                                 }
                              } else {
                                 var30.packetBuffer.method5481((var12 & 8191) + 57344);
                                 if(var9 != -1 && var8 != -1) {
                                    var30.packetBuffer.writeInt(var9 | var8 << 16);
                                 } else {
                                    var30.packetBuffer.writeInt(Integer.MIN_VALUE);
                                 }
                              }

                              ++var6;
                              field856 = WorldMapLabel.mouseRecorder.millis[var7];
                           }
                        }

                        if(var30 != null) {
                           var30.packetBuffer.method5493(var30.packetBuffer.offset - var3);
                           var7 = var30.packetBuffer.offset;
                           var30.packetBuffer.offset = var3;
                           var30.packetBuffer.writeByte(var5 / var6);
                           var30.packetBuffer.writeByte(var5 % var6);
                           var30.packetBuffer.offset = var7;
                           packetWriter.method1622(var30);
                        }

                        if(var4 >= WorldMapLabel.mouseRecorder.index) {
                           WorldMapLabel.mouseRecorder.index = 0;
                        } else {
                           WorldMapLabel.mouseRecorder.index -= var4;
                           System.arraycopy(WorldMapLabel.mouseRecorder.xs, var4, WorldMapLabel.mouseRecorder.xs, 0, WorldMapLabel.mouseRecorder.index);
                           System.arraycopy(WorldMapLabel.mouseRecorder.ys, var4, WorldMapLabel.mouseRecorder.ys, 0, WorldMapLabel.mouseRecorder.index);
                           System.arraycopy(WorldMapLabel.mouseRecorder.millis, var4, WorldMapLabel.mouseRecorder.millis, 0, WorldMapLabel.mouseRecorder.index);
                        }
                     }
                  }

                  PacketBufferNode var18;
                  if(MouseHandler.MouseHandler_lastButton == 1 || !WorldMapIcon_1.mouseCam && MouseHandler.MouseHandler_lastButton == 4 || MouseHandler.MouseHandler_lastButton == 2) {
                     long var16 = (MouseHandler.MouseHandler_lastPressedTimeMillis - mouseLastLastPressedTimeMillis * -1L) / 50L;
                     if(var16 > 4095L) {
                        var16 = 4095L;
                     }

                     mouseLastLastPressedTimeMillis = MouseHandler.MouseHandler_lastPressedTimeMillis * -1L;
                     var3 = MouseHandler.MouseHandler_lastPressedY;
                     if(var3 < 0) {
                        var3 = 0;
                     } else if(var3 > WallDecoration.canvasHeight) {
                        var3 = WallDecoration.canvasHeight;
                     }

                     var4 = MouseHandler.MouseHandler_lastPressedX;
                     if(var4 < 0) {
                        var4 = 0;
                     } else if(var4 > FloorDecoration.canvasWidth) {
                        var4 = FloorDecoration.canvasWidth;
                     }

                     var5 = (int)var16;
                     var18 = InterfaceParent.method1140(ClientPacket.field2366, packetWriter.isaacCipher);
                     var18.packetBuffer.method5481((MouseHandler.MouseHandler_lastButton == 2?1:0) + (var5 << 1));
                     var18.packetBuffer.method5481(var4);
                     var18.packetBuffer.method5481(var3);
                     packetWriter.method1622(var18);
                  }

                  if(KeyHandler.field162 > 0) {
                     var14 = InterfaceParent.method1140(ClientPacket.field2380, packetWriter.isaacCipher);
                     var14.packetBuffer.method5481(0);
                     var15 = var14.packetBuffer.offset;
                     long var19 = class33.method680();

                     for(var5 = 0; var5 < KeyHandler.field162; ++var5) {
                        long var21 = var19 - field1068;
                        if(var21 > 16777215L) {
                           var21 = 16777215L;
                        }

                        field1068 = var19;
                        var14.packetBuffer.method5539((int)var21);
                        var14.packetBuffer.method5515(KeyHandler.field177[var5]);
                     }

                     var14.packetBuffer.method5492(var14.packetBuffer.offset - var15);
                     packetWriter.method1622(var14);
                  }

                  if(field928 > 0) {
                     --field928;
                  }

                  if(KeyHandler.KeyHandler_pressedKeys[96] || KeyHandler.KeyHandler_pressedKeys[97] || KeyHandler.KeyHandler_pressedKeys[98] || KeyHandler.KeyHandler_pressedKeys[99]) {
                     field929 = true;
                  }

                  if(field929 && field928 <= 0) {
                     field928 = 20;
                     field929 = false;
                     var14 = InterfaceParent.method1140(ClientPacket.field2365, packetWriter.isaacCipher);
                     var14.packetBuffer.method5532(camAngleX);
                     var14.packetBuffer.method5530(camAngleY);
                     packetWriter.method1622(var14);
                  }

                  if(TaskHandler.hasFocus && !hadFocus) {
                     hadFocus = true;
                     var14 = InterfaceParent.method1140(ClientPacket.field2342, packetWriter.isaacCipher);
                     var14.packetBuffer.writeByte(1);
                     packetWriter.method1622(var14);
                  }

                  if(!TaskHandler.hasFocus && hadFocus) {
                     hadFocus = false;
                     var14 = InterfaceParent.method1140(ClientPacket.field2342, packetWriter.isaacCipher);
                     var14.packetBuffer.writeByte(0);
                     packetWriter.method1622(var14);
                  }

                  if(Tiles.worldMap != null) {
                     Tiles.worldMap.method5923();
                  }

                  if(GrandExchangeOfferAgeComparator.ClanChat_inClanChat) {
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4766();
                     }

                     Archive.method4308();
                     GrandExchangeOfferAgeComparator.ClanChat_inClanChat = false;
                  }

                  class47.method824();
                  if(gameState != 30) {
                     return;
                  }

                  class158.method3399();
                  HealthBarUpdate.method1258();
                  ++packetWriter.field834;
                  if(packetWriter.field834 > 750) {
                     MouseRecorder.method1208();
                     return;
                  }

                  class19.method338();
                  WallDecoration.method2914();
                  int[] var37 = Players.Players_indices;

                  for(var15 = 0; var15 < Players.Players_count; ++var15) {
                     Player var23 = players[var37[var15]];
                     if(var23 != null && var23.overheadTextCyclesRemaining > 0) {
                        --var23.overheadTextCyclesRemaining;
                        if(var23.overheadTextCyclesRemaining == 0) {
                           var23.overheadText = null;
                           var23.overheadTextChanged(-1);
                        }
                     }
                  }

                  for(var15 = 0; var15 < npcCount; ++var15) {
                     var3 = npcIndices[var15];
                     NPC var24 = npcs[var3];
                     if(var24 != null && var24.overheadTextCyclesRemaining > 0) {
                        --var24.overheadTextCyclesRemaining;
                        if(var24.overheadTextCyclesRemaining == 0) {
                           var24.overheadText = null;
                           var24.overheadTextChanged(-1);
                        }
                     }
                  }

                  ++field906;
                  if(mouseCrossColor != 0) {
                     mouseCrossState += 20;
                     if(mouseCrossState >= 400) {
                        mouseCrossColor = 0;
                     }
                  }

                  if(GrandExchangeOfferOwnWorldComparator.field345 != null) {
                     ++field958;
                     if(field958 >= 15) {
                        WorldMapSectionType.method116(GrandExchangeOfferOwnWorldComparator.field345);
                        GrandExchangeOfferOwnWorldComparator.field345 = null;
                     }
                  }

                  Widget var38 = NetSocket.mousedOverWidgetIf1;
                  Widget var31 = Strings.field2812;
                  NetSocket.mousedOverWidgetIf1 = null;
                  Strings.field2812 = null;
                  draggedOnWidget = null;
                  field1025 = false;
                  field1022 = false;
                  field1065 = 0;

                  while(DynamicObject.method1568() && field1065 < 128) {
                     if(staffModLevel >= 2 && KeyHandler.KeyHandler_pressedKeys[82] && SecureRandomFuture.field748 == 66) {
                        String var40 = Entity.method3079();
                        ViewportMouse.client.method963(var40);
                     } else if(oculusOrbState != 1 || KeyHandler.field182 <= 0) {
                        field1067[field1065] = SecureRandomFuture.field748;
                        field1066[field1065] = KeyHandler.field182;
                        ++field1065;
                     }
                  }

                  boolean var32 = staffModLevel >= 2;
                  if(var32 && KeyHandler.KeyHandler_pressedKeys[82] && KeyHandler.KeyHandler_pressedKeys[81] && mouseWheelRotation != 0) {
                     var4 = class215.localPlayer.plane - mouseWheelRotation;
                     if(var4 < 0) {
                        var4 = 0;
                     } else if(var4 > 3) {
                        var4 = 3;
                     }

                     if(var4 != class215.localPlayer.plane) {
                        class298.method5476(class215.localPlayer.pathX[0] + class215.baseX, class215.localPlayer.pathY[0] + class304.baseY, var4, false);
                     }

                     mouseWheelRotation = 0;
                  }

                  if(rootInterface != -1) {
                     AbstractWorldMapData.method3325(rootInterface, 0, 0, FloorDecoration.canvasWidth, WallDecoration.canvasHeight, 0, 0);
                  }

                  ++cycleCntr;
                  onCycleCntrChanged(-1);

                  while(true) {
                     Widget var25;
                     Widget var39;
                     ScriptEvent var41;
                     do {
                        var41 = (ScriptEvent)field1044.method5108();
                        if(var41 == null) {
                           while(true) {
                              do {
                                 var41 = (ScriptEvent)field971.method5108();
                                 if(var41 == null) {
                                    while(true) {
                                       do {
                                          var41 = (ScriptEvent)scriptEvents.method5108();
                                          if(var41 == null) {
                                             this.method1659((short)255);
                                             if(Tiles.worldMap != null) {
                                                Tiles.worldMap.method5881(WorldMapRectangle.plane, (class215.localPlayer.x >> 7) + class215.baseX, (class215.localPlayer.y * 682054857 >> 7) + class304.baseY, false);
                                                Tiles.worldMap.method5897();
                                             }

                                             if(clickedWidget != null) {
                                                this.method1663();
                                             }

                                             if(Frames.dragInventoryWidget != null) {
                                                WorldMapSectionType.method116(Frames.dragInventoryWidget);
                                                ++itemDragDuration;
                                                itemPressedDurationChanged(-1);
                                                if(MouseHandler.MouseHandler_currentButton == 0) {
                                                   if(field956) {
                                                      if(Frames.dragInventoryWidget == DevicePcmPlayerProvider.field154 && dragItemSlotDestination != dragItemSlotSource) {
                                                         Widget var42 = Frames.dragInventoryWidget;
                                                         byte var33 = 0;
                                                         if(field1081 == 1 && var42.contentType == 206) {
                                                            var33 = 1;
                                                         }

                                                         if(var42.itemIds[dragItemSlotDestination] <= 0) {
                                                            var33 = 0;
                                                         }

                                                         if(WorldMapSection0.method3907(class12.method148(var42))) {
                                                            var6 = dragItemSlotSource;
                                                            var7 = dragItemSlotDestination;
                                                            var42.itemIds[var7] = var42.itemIds[var6];
                                                            var42.itemQuantities[var7] = var42.itemQuantities[var6];
                                                            var42.itemIds[var6] = -1;
                                                            var42.itemQuantities[var6] = 0;
                                                         } else if(var33 == 1) {
                                                            var6 = dragItemSlotSource;
                                                            var7 = dragItemSlotDestination;

                                                            while(var7 != var6) {
                                                               if(var6 > var7) {
                                                                  var42.method3970(var6 - 1, var6);
                                                                  --var6;
                                                               } else if(var6 < var7) {
                                                                  var42.method3970(var6 + 1, var6);
                                                                  ++var6;
                                                               }
                                                            }
                                                         } else {
                                                            var42.method3970(dragItemSlotDestination, dragItemSlotSource);
                                                         }

                                                         var18 = InterfaceParent.method1140(ClientPacket.field2378, packetWriter.isaacCipher);
                                                         var18.packetBuffer.method5684(Frames.dragInventoryWidget.id);
                                                         var18.packetBuffer.method5481(dragItemSlotDestination);
                                                         var18.packetBuffer.writeByte(var33);
                                                         var18.packetBuffer.method5530(dragItemSlotSource);
                                                         packetWriter.method1622(var18);
                                                      }
                                                   } else if(this.method1660((byte)-5)) {
                                                      this.method1661(field953, field954);
                                                   } else if(menuOptionsCount > 0) {
                                                      class11.method137(field953, field954);
                                                   }

                                                   field958 = 10;
                                                   MouseHandler.MouseHandler_lastButton = 0;
                                                   Frames.dragInventoryWidget = null;
                                                   //TODO:: Custom Drag
                                                } else if (itemDragDuration >= CustomMisc.DRAG && (MouseHandler.MouseHandler_x > field953 + CustomMisc.DRAG || MouseHandler.MouseHandler_x < field953 - CustomMisc.DRAG || MouseHandler.MouseHandler_y > field954 + CustomMisc.DRAG || MouseHandler.MouseHandler_y < field954 - CustomMisc.DRAG)) {
                                                field956 = true;
                                                }
                                             }

                                             if(Scene.method3153()) {
                                                var4 = Scene.Scene_selectedX;
                                                var5 = Scene.Scene_selectedY;
                                                var18 = InterfaceParent.method1140(ClientPacket.field2437, packetWriter.isaacCipher);
                                                var18.packetBuffer.writeByte(5);
                                                var18.packetBuffer.method5532(var4 + class215.baseX);
                                                var18.packetBuffer.method5530(var5 + class304.baseY);
                                                var18.packetBuffer.method5522(KeyHandler.KeyHandler_pressedKeys[82]?(KeyHandler.KeyHandler_pressedKeys[81]?2:1):0);
                                                packetWriter.method1622(var18);
                                                Scene.method3154();
                                                mouseCrossX = MouseHandler.MouseHandler_lastPressedX;
                                                mouseCrossY = MouseHandler.MouseHandler_lastPressedY;
                                                mouseCrossColor = 1;
                                                mouseCrossState = 0;
                                                destinationX = var4;
                                                destinationY = var5;
                                             }

                                             if(var38 != NetSocket.mousedOverWidgetIf1) {
                                                if(var38 != null) {
                                                   WorldMapSectionType.method116(var38);
                                                }

                                                if(NetSocket.mousedOverWidgetIf1 != null) {
                                                   WorldMapSectionType.method116(NetSocket.mousedOverWidgetIf1);
                                                }
                                             }

                                             if(var31 != Strings.field2812 && field996 == field995) {
                                                if(var31 != null) {
                                                   WorldMapSectionType.method116(var31);
                                                }

                                                if(Strings.field2812 != null) {
                                                   WorldMapSectionType.method116(Strings.field2812);
                                                }
                                             }

                                             if(Strings.field2812 != null) {
                                                if(field995 < field996) {
                                                   ++field995;
                                                   if(field995 == field996) {
                                                      WorldMapSectionType.method116(Strings.field2812);
                                                   }
                                                }
                                             } else if(field995 > 0) {
                                                --field995;
                                             }

                                             if(oculusOrbState == 0) {
                                                var4 = class215.localPlayer.x;
                                                var5 = class215.localPlayer.y * 682054857;
                                                if(ObjectSound.oculusOrbFocalPointX - var4 < -500 || ObjectSound.oculusOrbFocalPointX - var4 > 500 || class125.oculusOrbFocalPointY - var5 < -500 || class125.oculusOrbFocalPointY - var5 > 500) {
                                                   ObjectSound.oculusOrbFocalPointX = var4;
                                                   class125.oculusOrbFocalPointY = var5;
                                                }

                                                if(var4 != ObjectSound.oculusOrbFocalPointX) {
                                                   ObjectSound.oculusOrbFocalPointX += (var4 - ObjectSound.oculusOrbFocalPointX) / 16;
                                                }

                                                if(var5 != class125.oculusOrbFocalPointY) {
                                                   class125.oculusOrbFocalPointY += (var5 - class125.oculusOrbFocalPointY) / 16;
                                                }

                                                var6 = ObjectSound.oculusOrbFocalPointX >> 7;
                                                var7 = class125.oculusOrbFocalPointY >> 7;
                                                var8 = MusicPatchPcmStream.method3798(ObjectSound.oculusOrbFocalPointX, class125.oculusOrbFocalPointY, WorldMapRectangle.plane);
                                                var9 = 0;
                                                if(var6 > 3 && var7 > 3 && var6 < 100 && var7 < 100) {
                                                   for(var10 = var6 - 4; var10 <= var6 + 4; ++var10) {
                                                      for(var11 = var7 - 4; var11 <= var7 + 4; ++var11) {
                                                         var12 = WorldMapRectangle.plane;
                                                         if(var12 < 3 && (Tiles.Tiles_renderFlags[1][var10][var11] & 2) == 2) {
                                                            ++var12;
                                                         }

                                                         int var26 = var8 - Tiles.Tiles_heights[var12][var10][var11];
                                                         if(var26 > var9) {
                                                            var9 = var26;
                                                         }
                                                      }
                                                   }
                                                }

                                                var10 = var9 * 192;
                                                if(var10 > 98048) {
                                                   var10 = 98048;
                                                }

                                                if(var10 < 32768) {
                                                   var10 = 32768;
                                                }

                                                if(var10 > field930) {
                                                   field930 += (var10 - field930) / 24;
                                                } else if(var10 < field930) {
                                                   field930 += (var10 - field930) / 80;
                                                }

                                                ModelData0.field1785 = (MusicPatchPcmStream.method3798(class215.localPlayer.x, 682054857 * class215.localPlayer.y, WorldMapRectangle.plane) - -844153885 * camFollowHeight) * -506989123;
                                             } else if(oculusOrbState == 1) {
                                                class4.method66();
                                                short var34 = -1;
                                                if(KeyHandler.KeyHandler_pressedKeys[33]) {
                                                   var34 = 0;
                                                } else if(KeyHandler.KeyHandler_pressedKeys[49]) {
                                                   var34 = 1024;
                                                }

                                                if(KeyHandler.KeyHandler_pressedKeys[48]) {
                                                   if(var34 == 0) {
                                                      var34 = 1792;
                                                   } else if(var34 == 1024) {
                                                      var34 = 1280;
                                                   } else {
                                                      var34 = 1536;
                                                   }
                                                } else if(KeyHandler.KeyHandler_pressedKeys[50]) {
                                                   if(var34 == 0) {
                                                      var34 = 256;
                                                   } else if(var34 == 1024) {
                                                      var34 = 768;
                                                   } else {
                                                      var34 = 512;
                                                   }
                                                }

                                                byte var35 = 0;
                                                if(KeyHandler.KeyHandler_pressedKeys[35]) {
                                                   var35 = -1;
                                                } else if(KeyHandler.KeyHandler_pressedKeys[51]) {
                                                   var35 = 1;
                                                }

                                                var6 = 0;
                                                if(var34 >= 0 || var35 != 0) {
                                                   var6 = KeyHandler.KeyHandler_pressedKeys[81]?oculusOrbSlowedSpeed:oculusOrbNormalSpeed;
                                                   var6 *= 16;
                                                   field922 = var34;
                                                   field923 = var35;
                                                }

                                                if(field974 < var6) {
                                                   field974 += var6 / 8;
                                                   if(field974 > var6) {
                                                      field974 = var6;
                                                   }
                                                } else if(field974 > var6) {
                                                   field974 = field974 * 9 / 10;
                                                }

                                                if(field974 > 0) {
                                                   var7 = field974 / 16;
                                                   if(field922 >= 0) {
                                                      var4 = field922 - WorldMapSection2.cameraYaw & 2047;
                                                      var8 = Rasterizer3D.Rasterizer3D_sine[var4];
                                                      var9 = Rasterizer3D.Rasterizer3D_cosine[var4];
                                                      ObjectSound.oculusOrbFocalPointX += var8 * var7 / 65536;
                                                      class125.oculusOrbFocalPointY += var7 * var9 / 65536;
                                                   }

                                                   if(field923 != 0) {
                                                      ModelData0.field1785 += var7 * field923 * -506989123;
                                                      if(ModelData0.field1785 * -1351160427 > 0) {
                                                         ModelData0.field1785 = 0;
                                                      }
                                                   }
                                                } else {
                                                   field922 = -1;
                                                   field923 = -1;
                                                }

                                                if(KeyHandler.KeyHandler_pressedKeys[13]) {
                                                   packetWriter.method1622(InterfaceParent.method1140(ClientPacket.field2439, packetWriter.isaacCipher));
                                                   oculusOrbState = 0;
                                                }
                                             }

                                             if(MouseHandler.MouseHandler_currentButton == 4 && WorldMapIcon_1.mouseCam) {
                                                var4 = MouseHandler.MouseHandler_y - mouseCamClickedY;
                                                camAngleDX = var4 * 2;
                                                mouseCamClickedY = var4 != -1 && var4 != 1?(mouseCamClickedY + MouseHandler.MouseHandler_y) / 2:MouseHandler.MouseHandler_y;
                                                var5 = mouseCamClickedX - MouseHandler.MouseHandler_x;
                                                camAngleDY = var5 * 2;
                                                mouseCamClickedX = var5 != -1 && var5 != 1?(MouseHandler.MouseHandler_x + mouseCamClickedX) / 2:MouseHandler.MouseHandler_x;
                                             } else {
                                                if(KeyHandler.KeyHandler_pressedKeys[96]) {
                                                   camAngleDY += (-24 - camAngleDY) / 2;
                                                } else if(KeyHandler.KeyHandler_pressedKeys[97]) {
                                                   camAngleDY += (24 - camAngleDY) / 2;
                                                } else {
                                                   camAngleDY /= 2;
                                                }

                                                if(KeyHandler.KeyHandler_pressedKeys[98]) {
                                                   camAngleDX += (12 - camAngleDX) / 2;
                                                } else if(KeyHandler.KeyHandler_pressedKeys[99]) {
                                                   camAngleDX += (-12 - camAngleDX) / 2;
                                                } else {
                                                   camAngleDX /= 2;
                                                }

                                                mouseCamClickedY = MouseHandler.MouseHandler_y;
                                                mouseCamClickedX = MouseHandler.MouseHandler_x;
                                             }

                                             camAngleY = camAngleDY / 2 + camAngleY & 2047;
                                             camAngleX += camAngleDX / 2;
                                             onCameraPitchTargetChanged(-1);
                                             if(camAngleX < 128) {
                                                camAngleX = 128;
                                                onCameraPitchTargetChanged(-1);
                                             }

                                             if(camAngleX > 383) {
                                                camAngleX = 383;
                                                onCameraPitchTargetChanged(-1);
                                             }

                                             if(isCameraLocked) {
                                                ChatChannel.method1532();
                                             }

                                             //TODO::Widget timer tick
                                             CustomWidgetTimer.tick();

                                             for(var4 = 0; var4 < 5; ++var4) {
                                                ++field1093[var4];
                                             }

                                             class197.varcs.method2172();
                                             var4 = WorldMapLabelSize.method3522();
                                             var5 = SecureRandomCallable.method1136();
                                             if(var4 > 15000 && var5 > 15000) {
                                                logoutTimer = 250;
                                                MouseHandler.MouseHandler_idleCycles = 14500;
                                                var18 = InterfaceParent.method1140(ClientPacket.field2381, packetWriter.isaacCipher);
                                                packetWriter.method1622(var18);
                                             }

                                             Tiles.friendSystem.method894();
                                             ++packetWriter.pendingWrites;
                                             if(packetWriter.pendingWrites > 50) {
                                                var18 = InterfaceParent.method1140(ClientPacket.field2363, packetWriter.isaacCipher);
                                                packetWriter.method1622(var18);
                                             }

                                             try {
                                                packetWriter.method1619();
                                             } catch (IOException var27) {
                                                MouseRecorder.method1208();
                                             }

                                             return;
                                          }

                                          var25 = var41.widget;
                                          if(var25.childIndex < 0) {
                                             break;
                                          }

                                          var39 = Canvas.getWidget(var25.parentId);
                                       } while(var39 == null || var39.children == null || var25.childIndex >= var39.children.length || var25 != var39.children[var25.childIndex]);

                                       ParamDefinition.method4313(var41);
                                    }
                                 }

                                 var25 = var41.widget;
                                 if(var25.childIndex < 0) {
                                    break;
                                 }

                                 var39 = Canvas.getWidget(var25.parentId);
                              } while(var39 == null || var39.children == null || var25.childIndex >= var39.children.length || var25 != var39.children[var25.childIndex]);

                              ParamDefinition.method4313(var41);
                           }
                        }

                        var25 = var41.widget;
                        if(var25.childIndex < 0) {
                           break;
                        }

                        var39 = Canvas.getWidget(var25.parentId);
                     } while(var39 == null || var39.children == null || var25.childIndex >= var39.children.length || var25 != var39.children[var25.childIndex]);

                     ParamDefinition.method4313(var41);
                  }
               }

               var30 = InterfaceParent.method1140(ClientPacket.field2352, packetWriter.isaacCipher);
               var30.packetBuffer.writeByte(0);
               var3 = var30.packetBuffer.offset;
               class188.method3740(var30.packetBuffer);
               var30.packetBuffer.method5493(var30.packetBuffer.offset - var3);
               packetWriter.method1622(var30);
            }
         }
      }
   }

   @ObfuscatedName("fj")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1430506238"
   )
   final void method1657() {
      int var1;
      if(rootInterface != -1) {
         var1 = rootInterface;
         if(WorldMapData_0.method171(var1)) {
            ChatChannel.method1533(UserComparator5.Widget_interfaceComponents[var1], -1);
         }
      }

      for(var1 = 0; var1 < rootWidgetCount; ++var1) {
         if(field1049[var1]) {
            field1050[var1] = true;
         }

         field1055[var1] = field1049[var1];
         field1049[var1] = false;
      }

      field846 = cycle;
      viewportX = -1;
      viewportY = -1;
      DevicePcmPlayerProvider.field154 = null;
      if(rootInterface != -1) {
         rootWidgetCount = 0;
         ItemContainer.method1459(rootInterface, 0, 0, FloorDecoration.canvasWidth, WallDecoration.canvasHeight, 0, 0, -1);
      }

      Hooks.drawAfterWidgets();
      Rasterizer2D.method6410();
      if(showMouseCross) {
         if(mouseCrossColor == 1) {
            UrlRequest.crossSprites[mouseCrossState / 100].method6159(mouseCrossX - 8, mouseCrossY - 8);
         }

         if(mouseCrossColor == 2) {
            UrlRequest.crossSprites[mouseCrossState / 100 + 4].method6159(mouseCrossX - 8, mouseCrossY - 8);
         }
      }

      int var2;
      int var3;
      if(!isMenuOpen) {
         if(viewportX != -1) {
            var1 = viewportX;
            var2 = viewportY;
            if((menuOptionsCount >= 2 || isItemSelected != 0 || isSpellSelected) && showMouseOverText) {
               var3 = BuddyRankComparator.method3386();
               String var4;
               if(isItemSelected == 1 && menuOptionsCount < 2) {
                  var4 = "Use" + " " + selectedItemName + " " + "->";
               } else if(isSpellSelected && menuOptionsCount < 2) {
                  var4 = selectedSpellActionName + " " + selectedSpellName + " " + "->";
               } else {
                  var4 = class28.method591(var3);
               }

               if(menuOptionsCount > 2) {
                  var4 = var4 + World.method1251(16777215) + " " + '/' + " " + (menuOptionsCount - 2) + " more options";
               }

               class170.fontBold12.method5393(var4, var1 + 4, var2 + 15, 16777215, 0, cycle / 1000);
            }
         }
      } else {
         var1 = UrlRequester.menuX;
         var2 = class37.menuY;
         var3 = FriendSystem.menuWidth;
         int var13 = WorldMapDecoration.menuHeight;
         int var5 = 6116423;
         Rasterizer2D.method6469(var1, var2, var3, var13, var5);
         Rasterizer2D.method6469(var1 + 1, var2 + 1, var3 - 2, 16, 0);
         Rasterizer2D.drawRectangle(var1 + 1, var2 + 18, var3 - 2, var13 - 19, 0);
         class170.fontBold12.method5329("Choose Option", var1 + 3, var2 + 14, var5, -1);
         int var6 = MouseHandler.MouseHandler_x;
         int var7 = MouseHandler.MouseHandler_y;

         int var8;
         int var9;
         int var10;
         for(var8 = 0; var8 < menuOptionsCount; ++var8) {
            var9 = var2 + (menuOptionsCount - 1 - var8) * 15 + 31;
            var10 = 16777215;
            if(var6 > var1 && var6 < var3 + var1 && var7 > var9 - 13 && var7 < var9 + 3) {
               var10 = 16776960;
            }

            class170.fontBold12.method5329(class28.method591(var8), var1 + 3, var9, var10, 0);
         }

         var8 = UrlRequester.menuX;
         var9 = class37.menuY;
         var10 = FriendSystem.menuWidth;
         int var11 = WorldMapDecoration.menuHeight;

         for(int var12 = 0; var12 < rootWidgetCount; ++var12) {
            if(rootWidgetXs[var12] + rootWidgetWidths[var12] > var8 && rootWidgetXs[var12] < var10 + var8 && rootWidgetHeights[var12] + rootWidgetYs[var12] > var9 && rootWidgetYs[var12] < var9 + var11) {
               field1050[var12] = true;
            }
         }
      }

      if(gameDrawingMode == 3) {
         for(var1 = 0; var1 < rootWidgetCount; ++var1) {
            if(field1055[var1]) {
               Rasterizer2D.fillRectangleAlpha(rootWidgetXs[var1], rootWidgetYs[var1], rootWidgetWidths[var1], rootWidgetHeights[var1], 16711935, 128);
            } else if(field1050[var1]) {
               Rasterizer2D.fillRectangleAlpha(rootWidgetXs[var1], rootWidgetYs[var1], rootWidgetWidths[var1], rootWidgetHeights[var1], 16711680, 128);
            }
         }
      }

      class11.method141(WorldMapRectangle.plane, class215.localPlayer.x, class215.localPlayer.y * 682054857, field906);
      field906 = 0;
   }

   @ObfuscatedName("fs")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-6"
   )
   void method1656() {
      int var1 = FloorDecoration.canvasWidth;
      int var2 = WallDecoration.canvasHeight;
      if(super.contentWidth < var1) {
         var1 = super.contentWidth;
      }

      if(super.contentHeight < var2) {
         var2 = super.contentHeight;
      }

      if(AbstractArchive.clientPreferences != null) {
         try {
            Client var3 = ViewportMouse.client;
            Object[] var4 = new Object[]{Integer.valueOf(class256.method4656())};
            JSObject.getWindow(var3).call("resize", var4);
         } catch (Throwable var5) {
            ;
         }
      }

   }

   @ObfuscatedName("gh")
   @ObfuscatedSignature(
      signature = "(Lcl;I)Z",
      garbageValue = "-1809167955"
   )
   final boolean parsePacket(PacketWriter var1) {
      AbstractSocket var2 = var1.method1624();
      PacketBuffer var3 = var1.packetBuffer;
      if(var2 == null) {
         return false;
      } else {
         int var6;
         boolean var10000;
         String var31;
         try {
            int var5;
            if(var1.serverPacket == null) {
               if(var1.field827) {
                  if(!var2.vmethod5816(1)) {
                     var10000 = false;
                     return var10000;
                  }

                  var2.vmethod5832(var1.packetBuffer.array, 0, 1);
                  var1.field834 = 0;
                  var1.field827 = false;
               }

               var3.offset = 0;
               if(var3.method5277()) {
                  if(!var2.vmethod5816(1)) {
                     var10000 = false;
                     return var10000;
                  }

                  var2.vmethod5832(var1.packetBuffer.array, 1, 1);
                  var1.field834 = 0;
               }

               var1.field827 = true;
               ServerPacket[] var4 = class94.method2222();
               var5 = var3.method5302();
               if(var5 < 0 || var5 >= var4.length) {
                  throw new IOException(var5 + " " + var3.offset);
               }

               var1.serverPacket = var4[var5];
               var1.serverPacketLength = var1.serverPacket.length;
            }

            if(var1.serverPacketLength == -1) {
               if(!var2.vmethod5816(1)) {
                  var10000 = false;
                  return var10000;
               }

               var1.method1624().vmethod5832(var3.array, 0, 1);
               var1.serverPacketLength = var3.array[0] & 255;
            }

            if(var1.serverPacketLength == -2) {
               if(!var2.vmethod5816(2)) {
                  var10000 = false;
                  return var10000;
               }

               var1.method1624().vmethod5832(var3.array, 0, 2);
               var3.offset = 0;
               var1.serverPacketLength = var3.readUnsignedShort();
            }

            if(!var2.vmethod5816(var1.serverPacketLength)) {
               var10000 = false;
               return var10000;
            }

            var3.offset = 0;
            var2.vmethod5832(var3.array, 0, var1.serverPacketLength);
            var1.field834 = 0;
            timer.method4848();
            var1.field838 = var1.field828;
            var1.field828 = var1.field837;
            var1.field837 = var1.serverPacket;
            if(ServerPacket.field2262 == var1.serverPacket) {
               GrandExchangeOfferTotalQuantityComparator.method208(class202.field2325);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            int slot;
            int var8;
            long var9;
            int var4;
            if(ServerPacket.field2263 == var1.serverPacket) {
               var4 = var3.readLEShort();
               if(var4 == 65535) {
                  var4 = -1;
               }

               var5 = var3.readUnsignedShort();
               if(var5 == 65535) {
                  var5 = -1;
               }

               var6 = var3.readLEInt();
               slot = var3.readLEInt();

               for(var8 = var4; var8 <= var5; ++var8) {
                  var9 = ((long)slot << 32) + (long)var8;
                  Node var42 = widgetClickMasks.method6346(var9);
                  if(var42 != null) {
                     var42.method3497();
                  }

                  widgetClickMasks.put(new IntegerNode(var6), var9);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2308 == var1.serverPacket) {
               var4 = var3.readUnsignedByte();
               NetCache.method4449(var4);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            Widget var46;
            if(ServerPacket.field2236 == var1.serverPacket) {
               var4 = var3.method5538();
               var5 = var3.method5538();
               var6 = var3.readLEInt();
               var46 = Canvas.getWidget(var6);
               if(var5 != var46.rawX || var4 != var46.rawY || var46.xAlignment != 0 || var46.yAlignment != 0) {
                  var46.rawX = var5;
                  var46.rawY = var4;
                  var46.xAlignment = 0;
                  var46.yAlignment = 0;
                  WorldMapSectionType.method116(var46);
                  this.method1774(var46);
                  if(var46.type == 0) {
                     GameShell.method1005(UserComparator5.Widget_interfaceComponents[var6 >> 16], var46, false);
                  }
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2296 == var1.serverPacket) {
               GrandExchangeOfferTotalQuantityComparator.method208(class202.field2326);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2284 == var1.serverPacket) {
               rebootTimer = var3.readLEShort() * 30;
               field1041 = cycleCntr;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            boolean var45;
            if(ServerPacket.field2250 == var1.serverPacket) {
               var45 = var3.readUnsignedByte() == 1;
               if(var45) {
                  class86.field1141 = class33.method680() - var3.method5502();
                  TileItem.grandExchangeEvents = new GrandExchangeEvents(var3, true);
               } else {
                  TileItem.grandExchangeEvents = null;
               }

               field1040 = cycleCntr;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2259 == var1.serverPacket) {
               Occluder.method3080(var3.readString());
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            int var10;
            Widget var18;
            int var19;
            if(ServerPacket.SEND_CONTAINER_ITEMS == var1.serverPacket) { //opcode 49

               int interfaceHash = var3.readInt(); //interface hash
               int containerId = var3.readUnsignedShort(); //container id
               int length = var3.readUnsignedShort(); //items length

               if(interfaceHash < -70000) {
                  interfaceHash += 32768;
               }

               Widget widget;
               if(interfaceHash >= 0) {
                  widget = Canvas.getWidget(interfaceHash);
               } else {
                  widget = null;
               }

               if (widget != null) { //if interface exists, populate slots as default empty
                  if (widget.itemIds == null) {
                     widget.itemIds = new int[28];
                     widget.itemQuantities = new int[28];
                     widget.itemAttributes = new String[28][3];
                  }
                  for (int i = 0; i < widget.itemIds.length; ++i) {
                     widget.itemIds[i] = 0;
                     widget.itemQuantities[i] = 0;
                     widget.itemAttributes[i] = new String[3];
                  }
               }

               class163.resetContainerContents(containerId);

               for (int i = 0; i < length; ++i) {

                  int itemId = var3.readInt(); //item id
                  int itemAmt = var3.readInt(); //item amount
                  int upgradeLength = var3.readUnsignedByte(); //upgrade length

                  String[] attributes = new String[upgradeLength];
                  for (int attrib = 0; attrib < upgradeLength; attrib++) {
                     attributes[attrib] = var3.readString();
                  }

                  if (widget != null && i < widget.itemIds.length) {
                     widget.itemIds[i] = itemId;
                     widget.itemQuantities[i] = itemAmt;
                     if (upgradeLength >= 0)
                        System.arraycopy(attributes, 0, widget.itemAttributes[i], 0, upgradeLength);
                  }

                  class8.setContainerItem(containerId, i, itemId - 1, itemAmt, widget == null ? null : i < widget.itemAttributes.length ? widget.itemAttributes[i] : null, upgradeLength);
               }

               if(widget != null) {
                  WorldMapSectionType.method116(widget);
               }

               class329.method6295();
               field1028[++field1033 - 1 & 31] = containerId & 32767;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2267 == var1.serverPacket) {
               isCameraLocked = true;
               class212.field2499 = var3.readUnsignedByte();
               Clock.field2041 = var3.readUnsignedByte();
               GrandExchangeOfferOwnWorldComparator.field344 = var3.readUnsignedShort();
               MouseRecorder.field543 = var3.readUnsignedByte();
               Messages.field813 = var3.readUnsignedByte();
               if(Messages.field813 >= 100) {
                  GrandExchangeOfferOwnWorldComparator.cameraX = class212.field2499 * 128 + 64;
                  WorldMapIcon_1.cameraZ = Clock.field2041 * 128 + 64;
                  Varcs.cameraY = MusicPatchPcmStream.method3798(GrandExchangeOfferOwnWorldComparator.cameraX, WorldMapIcon_1.cameraZ, WorldMapRectangle.plane) - GrandExchangeOfferOwnWorldComparator.field344;
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2290 == var1.serverPacket) {
               GrandExchangeOfferTotalQuantityComparator.method208(class202.field2332);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2283 == var1.serverPacket) {
               var4 = var3.readInt();
               var5 = var3.readInt();
               var6 = class8.method111();
               PacketBufferNode var48 = InterfaceParent.method1140(ClientPacket.field2355, packetWriter.isaacCipher);
               var48.packetBuffer.method5522(var6);
               var48.packetBuffer.writeInt(var4);
               var48.packetBuffer.method5543(var5);
               var48.packetBuffer.method5515(GameShell.fps);
               packetWriter.method1622(var48);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2305 == var1.serverPacket) {
               class329.method6295();
               weight = var3.g2s();
               field1041 = cycleCntr;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2298 == var1.serverPacket) {
               var4 = var3.readInt();
               if(var4 != field926) {
                  field926 = var4;
                  UserComparator4.method3290();
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2256 == var1.serverPacket) {
               if(Varps.clanChat != null) {
                  Varps.clanChat.method4929(var3);
               }

               class202.method3854();
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            InterfaceParent var17;
            Widget var20;
            if(ServerPacket.field2307 == var1.serverPacket) {
               var4 = var3.readLEInt();
               var5 = var3.readInt();
               InterfaceParent var52 = (InterfaceParent)interfaceParents.method6346((long)var5);
               var17 = (InterfaceParent)interfaceParents.method6346((long)var4);
               if(var17 != null) {
                  FontName.method5748(var17, var52 == null || var17.group != var52.group);
               }

               if(var52 != null) {
                  var52.method3497();
                  interfaceParents.put(var52, (long)var4);
               }

               var20 = Canvas.getWidget(var5);
               if(var20 != null) {
                  WorldMapSectionType.method116(var20);
               }

               var20 = Canvas.getWidget(var4);
               if(var20 != null) {
                  WorldMapSectionType.method116(var20);
                  GameShell.method1005(UserComparator5.Widget_interfaceComponents[var20.id >>> 16], var20, true);
               }

               if(rootInterface != -1) {
                  class28.method588(rootInterface, 1);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2297 == var1.serverPacket) {
               Tiles.friendSystem.method918(var3, var1.serverPacketLength);
               field952 = cycleCntr;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            long var12;
            long var21;
            long var23;
            String var36;
            if(ServerPacket.field2280 == var1.serverPacket) {
               var36 = var3.readString();
               String clanName = var3.readString();
               //var21 = var3.method5502();
               var23 = (long)var3.readUnsignedShort();
               var9 = (long)var3.method5500();

               int perm = var3.readUnsignedByte();

               System.out.println(perm);

               PlayerType var11 = (PlayerType)NetSocket.getEnumeratedTypeIndex(class210.getPlayerTypes(), perm);
               var12 = var9 + (var23 << 32);
               boolean var44 = false;

               for(int var15 = 0; var15 < 100; ++var15) {
                  if(field1097[var15] == var12) {
                     var44 = true;
                     break;
                  }
               }

               if(var11.isUser && Tiles.friendSystem.method897(new Username(var36, WorldMapSection1.loginType))) {
                  var44 = true;
               }

               if(!var44 && field842 == 0) {
                  field1097[field1064] = var12;
                  field1064 = (field1064 + 1) % 100;
                  String var25 = AbstractFont.method5328(NetSocket.method3456(class65.method1308(var3)));
                  if(var11.modIcon != -1) {
                     WorldMapData_1.method514(9, class256.method4655(var11.modIcon) + var36, var25, clanName);
                  } else {
                     WorldMapData_1.method514(9, var36, var25, clanName);
                  }
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2306 == var1.serverPacket) {
               var3.offset += 28;
               if(var3.method5520()) {
                  ArchiveDisk.method5309(var3, var3.offset - 28);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2286 == var1.serverPacket) {
               var4 = var3.readUnsignedByte();
               if(var3.readUnsignedByte() == 0) {
                  grandExchangeOffers[var4] = new GrandExchangeOffer();
                  onGrandExchangeOffersChanged(var4);
                  var3.offset += 18;
               } else {
                  --var3.offset;
                  grandExchangeOffers[var4] = new GrandExchangeOffer(var3, false);
                  onGrandExchangeOffersChanged(var4);
               }

               field1039 = cycleCntr;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2247 == var1.serverPacket) {
               var4 = var3.method5558();
               var5 = var3.method5546();
               var18 = Canvas.getWidget(var5);
               if(var4 != var18.sequenceId || var4 == -1) {
                  var18.sequenceId = var4;
                  var18.modelFrame = 0;
                  var18.modelFrameCycle = 0;
                  WorldMapSectionType.method116(var18);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2311 == var1.serverPacket) {
               var4 = var3.readInt();
               var5 = var3.readLEInt();
               var6 = var3.readInt();
               var46 = Canvas.getWidget(var4);
               ItemDefinition var50;
               if(!var46.isIf3) {
                  if(var6 == -1) {
                     var46.modelType = 0;
                     var1.serverPacket = null;
                     var10000 = true;
                     return var10000;
                  }

                  var50 = Occluder.getItemDefinition(var6);
                  var46.modelType = 4;
                  var46.modelId = var6;
                  var46.modelAngleX = var50.xan2d;
                  var46.modelAngleY = var50.yan2d;
                  var46.modelZoom = var50.zoom2d * 100 / var5;
                  WorldMapSectionType.method116(var46);
               } else {
                  var46.itemId = var6;
                  var46.itemQuantity = var5;
                  var50 = Occluder.getItemDefinition(var6);
                  var46.modelAngleX = var50.xan2d;
                  var46.modelAngleY = var50.yan2d;
                  var46.modelAngleZ = var50.zan2d;
                  var46.modelOffsetX = var50.offsetX2d;
                  var46.modelOffsetY = var50.offsetY2d;
                  var46.modelZoom = var50.zoom2d;
                  if(var50.isStackable == 1) {
                     var46.itemQuantityMode = 1;
                  } else {
                     var46.itemQuantityMode = 2;
                  }

                  if(var46.field2700 > 0) {
                     var46.modelZoom = var46.modelZoom * 32 / var46.field2700;
                  } else if(var46.rawWidth > 0) {
                     var46.modelZoom = var46.modelZoom * 32 / var46.rawWidth;
                  }

                  WorldMapSectionType.method116(var46);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2282 == var1.serverPacket) {
               var4 = var3.method5546();
               var5 = var3.readShortA();
               var18 = Canvas.getWidget(var4);
               if(var18.modelType != 2 || var5 != var18.modelId) {
                  var18.modelType = 2;
                  var18.modelId = var5;
                  WorldMapSectionType.method116(var18);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2255 == var1.serverPacket) {
               GrandExchangeOfferTotalQuantityComparator.method208(class202.field2331);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2270 == var1.serverPacket) {
               var36 = var3.readString();
               var21 = (long)var3.readUnsignedShort();
               var23 = (long)var3.method5500();
               PlayerType var26 = (PlayerType)NetSocket.getEnumeratedTypeIndex(class210.getPlayerTypes(), var3.readUnsignedByte());
               long var27 = (var21 << 32) + var23;
               boolean var29 = false;

               for(int var13 = 0; var13 < 100; ++var13) {
                  if(field1097[var13] == var27) {
                     var29 = true;
                     break;
                  }
               }

               if(Tiles.friendSystem.method897(new Username(var36, WorldMapSection1.loginType))) {
                  var29 = true;
               }

               if(!var29 && field842 == 0) {
                  field1097[field1064] = var27;
                  field1064 = (field1064 + 1) % 100;
                  String var30 = AbstractFont.method5328(NetSocket.method3456(class65.method1308(var3)));
                  byte var14;
                  if(var26.isPrivileged) {
                     var14 = 7;
                  } else {
                     var14 = 3;
                  }

                  if(var26.modIcon != -1) {
                     class217.sendGameMessage(var14, class256.method4655(var26.modIcon) + var36, var30);
                  } else {
                     class217.sendGameMessage(var14, var36, var30);
                  }
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            Widget var55;
            if(ServerPacket.field2244 == var1.serverPacket) {
               var4 = var3.readLEInt();
               var55 = Canvas.getWidget(var4);

               for(var6 = 0; var6 < var55.itemIds.length; ++var6) {
                  var55.itemIds[var6] = -1;
                  var55.itemIds[var6] = 0;
               }

               WorldMapSectionType.method116(var55);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2300 == var1.serverPacket) {
               InterfaceParent.method1141(true, var3);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2278 == var1.serverPacket) {
               var4 = var3.readInt1();
               var5 = var3.readLEShort();

               if (var5 >= 20000) {
                  CustomMisc.handleCustomVarp(var5, var4);
                  var1.serverPacket = null;
                  return true;
               }

               Varps.Varps_temp[var5] = var4;
               if(Varps.Varps_main[var5] != var4) {
                  Varps.Varps_main[var5] = var4;
                  settingsChanged(var5);
               }

               NetSocket.method3457(var5);
               field990[++field1031 - 1 & 31] = var5;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2265 == var1.serverPacket) {
               class28.field199 = var3.method5525();
               WorldMapSection2.field3815 = var3.readUnsignedByte();
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2287 == var1.serverPacket) {
               var4 = var3.readUnsignedByte();
               var5 = var3.readUnsignedByte();
               var6 = var3.readUnsignedByte();
               slot = var3.readUnsignedByte();
               field1043[var4] = true;
               field1090[var4] = var5;
               field1091[var4] = var6;
               field1092[var4] = slot;
               field1093[var4] = 0;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2316 == var1.serverPacket) {
               var4 = var3.method5565();
               var31 = var3.readString();
               var6 = var3.readByteA();
               if(var6 >= 1 && var6 <= 8) {
                  if(var31.equalsIgnoreCase("null")) {
                     var31 = null;
                  }

                  playerMenuActions[var6 - 1] = var31;
                  playerOptionsChanged(var6 - 1);
                  playerOptionsPriorities[var6 - 1] = var4 == 0;
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2318 == var1.serverPacket) {
               World var39 = new World();
               var39.host = var3.readString();
               var39.id = var3.readUnsignedShort();
               var5 = var3.readInt();
               var39.properties = var5;
               MouseRecorder.method1207(45);
               var2.vmethod5821();
               var2 = null;
               class8.method101(var39);
               var1.serverPacket = null;
               var10000 = false;
               return var10000;
            }

            if(ServerPacket.field2303 == var1.serverPacket) {
               Tiles.friendSystem.method892();
               field952 = cycleCntr;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2271 == var1.serverPacket) {
               var4 = var3.readUnsignedByte();
               SequenceDefinition.method4691(var4, -645881783);
               var1.serverPacket = null;
               var10000 = false;
               return var10000;
            }

            if(ServerPacket.field2312 == var1.serverPacket) {
               isCameraLocked = false;

               for(var4 = 0; var4 < 5; ++var4) {
                  field1043[var4] = false;
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2314 == var1.serverPacket) {
               GrandExchangeOfferTotalQuantityComparator.method208(class202.field2327);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2309 == var1.serverPacket) {
               if(var1.serverPacketLength == 0) {
                  Varps.clanChat = null;
                  clanMemberManagerChanged(-1);
               } else {
                  if(Varps.clanChat == null) {
                     Varps.clanChat = new ClanChat(WorldMapSection1.loginType, ViewportMouse.client);
                     clanMemberManagerChanged(-1);
                  }

                  Varps.clanChat.method4928(var3);
               }

               class202.method3854();
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2315 == var1.serverPacket) {
               hintArrowType = var3.readUnsignedByte();
               if(hintArrowType == 1) {
                  hintArrowNpcIndex = var3.readUnsignedShort();
               }

               if(hintArrowType >= 2 && hintArrowType <= 6) {
                  if(hintArrowType == 2) {
                     hintArrowSubX = -1041380800;
                     hintArrowSubY = 1854168896;
                  }

                  if(hintArrowType == 3) {
                     hintArrowSubX = 0;
                     hintArrowSubY = 1854168896;
                  }

                  if(hintArrowType == 4) {
                     hintArrowSubX = -2082761600;
                     hintArrowSubY = 1854168896;
                  }

                  if(hintArrowType == 5) {
                     hintArrowSubX = -1041380800;
                     hintArrowSubY = 0;
                  }

                  if(hintArrowType == 6) {
                     hintArrowSubX = -1041380800;
                     hintArrowSubY = -586629504;
                  }

                  hintArrowType = 2;
                  hintArrowX = var3.readUnsignedShort();
                  hintArrowY = var3.readUnsignedShort();
                  hintArrowHeight = var3.readUnsignedByte();
               }

               if(hintArrowType == 10) {
                  hintArrowPlayerIndex = var3.readUnsignedShort();
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2293 == var1.serverPacket) {
               var4 = var3.readShortA();
               WorldMapAreaData.method670(var4);
               field1028[++field1033 - 1 & 31] = var4 & 32767;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2324 == var1.serverPacket) {
               var4 = var3.readShortA();
               rootInterface = var4;
               this.method1874(false);
               WorldMapManager.method631(var4);
               class186.method3728(rootInterface, 38319207);

               for(var5 = 0; var5 < 100; ++var5) {
                  field1049[var5] = true;
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            String var51;
            if(ServerPacket.field2238 == var1.serverPacket) {
               var4 = var3.readUnsignedSmart();
               boolean var43 = var3.readUnsignedByte() == 1;
               var51 = "";
               boolean var41 = false;
               if(var43) {
                  var51 = var3.readString();
                  if(Tiles.friendSystem.method897(new Username(var51, WorldMapSection1.loginType))) {
                     var41 = true;
                  }
               }

               String var49 = var3.readString();
               if(!var41) {
                  class217.sendGameMessage(var4, var51, var49);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2302 == var1.serverPacket) {
               PlayerType.method3945(false, var1.packetBuffer);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2239 == var1.serverPacket) {
               byte[] var38 = new byte[var1.serverPacketLength];
               var3.method5282(var38, 0, var38.length);
               Buffer var59 = new Buffer(var38);
               var51 = var59.readString();
               ArchiveDiskActionHandler.method4341(var51, true, false);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2273 == var1.serverPacket) {
               var45 = var3.readUnsignedByte() == 1;
               var5 = var3.readLEInt();
               var18 = Canvas.getWidget(var5);
               if(var45 != var18.isHidden) {
                  var18.isHidden = var45;
                  var18.onHiddenChanged(-1);
                  WorldMapSectionType.method116(var18);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.UPDATE_CONATINER_ITEMS == var1.serverPacket) {

               int interfaceHash = var3.readInt(); //interface hash
               int container = var3.readUnsignedShort(); //container
               if(interfaceHash < -70000) {
                  container += 32768;
               }

               Widget widget;
               if(interfaceHash >= 0) {
                  widget = Canvas.getWidget(interfaceHash);
               } else {
                  widget = null;
               }

               int itemId;
               int itemAmt;
               int upgradeSize;
               String[] attributes;

               for(; var3.offset < var1.serverPacketLength; class8.setContainerItem(container, slot, itemId - 1, itemAmt, attributes, upgradeSize)) {

                  slot = var3.readUnsignedSmart(); //slot
                  itemId = var3.readInt(); //item id
                  itemAmt = var3.readInt(); //fat amount
                  upgradeSize = var3.readUnsignedByte();

                  attributes = new String[upgradeSize];
                  for (int i = 0; i < upgradeSize; i++) {
                     String attribute = var3.readString();
                     attributes[i] = attribute;
                  }

                  if(widget != null && widget.itemIds != null && widget.itemQuantities != null && slot >= 0 && slot < widget.itemIds.length) {
                     widget.itemIds[slot] = itemId;
                     widget.itemQuantities[slot] = itemAmt;
                     if (upgradeSize > 0) {
                        System.arraycopy(attributes, 0, widget.itemAttributes[slot], 0, upgradeSize);
                     }
                  }
               }

               if(widget != null) {
                  WorldMapSectionType.method116(widget);
               }

               class329.method6295();
               field1028[++field1033 - 1 & 31] = container & 32767;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2299 == var1.serverPacket) {
               var4 = var3.readInt1();
               var5 = var3.readInt1();
               var18 = Canvas.getWidget(var4);
               if(var18.modelType != 1 || var5 != var18.modelId) {
                  var18.modelType = 1;
                  var18.modelId = var5;
                  WorldMapSectionType.method116(var18);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2276 == var1.serverPacket) {
               var4 = var3.readUnsignedShort();
               var5 = var3.readLEInt();
               var6 = var4 >> 10 & 31;
               slot = var4 >> 5 & 31;
               var8 = var4 & 31;
               var19 = (slot << 11) + (var6 << 19) + (var8 << 3);
               Widget var57 = Canvas.getWidget(var5);
               if(var19 != var57.color) {
                  var57.color = var19;
                  WorldMapSectionType.method116(var57);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2243 == var1.serverPacket) {
               var4 = var3.readUnsignedShort();
               var5 = var3.readShortA();
               var6 = var3.readLEShortA();
               slot = var3.readInt();
               var20 = Canvas.getWidget(slot);
               if(var5 != var20.modelAngleX || var6 != var20.modelAngleY || var4 != var20.modelZoom) {
                  var20.modelAngleX = var5;
                  var20.modelAngleY = var6;
                  var20.modelZoom = var4;
                  WorldMapSectionType.method116(var20);
               }
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2242 == var1.serverPacket) {
               tradeChatMode = var3.method5565();
               publicChatMode = var3.readByteA();
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2281 == var1.serverPacket) {
               class329.method6295();
               var4 = var3.readInt1();
               var5 = var3.readByteA();
               var6 = var3.method5565();
               experience[var5] = var4;
               experiencedChanged(var5);
               currentLevels[var5] = var6;
               boostedSkillLevelsChanged(var5);
               levels[var5] = 1;

               for(slot = 0; slot < 98; ++slot) {
                  if(var4 >= Skills.Skills_experienceTable[slot]) {
                     levels[var5] = slot + 2;
                  }
               }

               changedSkills[++changedSkillsCount - 1 & 31] = var5;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2291 == var1.serverPacket) {
               minimapState = var3.readUnsignedByte();
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2248 == var1.serverPacket) {
               var4 = var3.readShortA();
               var5 = var3.method5546();
               var6 = var3.readLEShortA();
               var46 = Canvas.getWidget(var5);
               var46.field2634 = var4 + (var6 << 16);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2292 == var1.serverPacket) {
               var36 = var3.readString();
               Object[] var58 = new Object[var36.length() + 1];

               for(var6 = var36.length() - 1; var6 >= 0; --var6) {
                  if(var36.charAt(var6) == 's') {
                     var58[var6 + 1] = var3.readString();
                  } else {
                     var58[var6 + 1] = new Integer(var3.readInt());
                  }
               }

               var58[0] = new Integer(var3.readInt());
               ScriptEvent var47 = new ScriptEvent();
               var47.args = var58;
               ParamDefinition.method4313(var47);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2304 == var1.serverPacket) {
               isCameraLocked = true;
               GameShell.field464 = var3.readUnsignedByte();
               SecureRandomFuture.field746 = var3.readUnsignedByte();
               class125.field1658 = var3.readUnsignedShort();
               class93.field1241 = var3.readUnsignedByte();
               ScriptEvent.field339 = var3.readUnsignedByte();
               if(ScriptEvent.field339 >= 100) {
                  var4 = GameShell.field464 * 128 + 64;
                  var5 = SecureRandomFuture.field746 * 128 + 64;
                  var6 = MusicPatchPcmStream.method3798(var4, var5, WorldMapRectangle.plane) - class125.field1658;
                  slot = var4 - GrandExchangeOfferOwnWorldComparator.cameraX;
                  var8 = var6 - Varcs.cameraY;
                  var19 = var5 - WorldMapIcon_1.cameraZ;
                  var10 = (int)Math.sqrt((double)(var19 * var19 + slot * slot));
                  IgnoreList.cameraPitch = (int)(Math.atan2((double)var8, (double)var10) * 325.949D) & 2047;
                  onCameraPitchChanged(-1);
                  WorldMapSection2.cameraYaw = (int)(Math.atan2((double)slot, (double)var19) * -325.949D) & 2047;
                  if(IgnoreList.cameraPitch < 128) {
                     IgnoreList.cameraPitch = 128;
                     onCameraPitchChanged(-1);
                  }

                  if(IgnoreList.cameraPitch > 383) {
                     IgnoreList.cameraPitch = 383;
                     onCameraPitchChanged(-1);
                  }
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2277 == var1.serverPacket) {
               GrandExchangeOfferTotalQuantityComparator.method208(class202.field2330);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2266 == var1.serverPacket) {
               var4 = var3.readInt();
               var5 = var3.readUnsignedByte();
               var6 = var3.readUnsignedShort();
               var17 = (InterfaceParent)interfaceParents.method6346((long)var4);
               if(var17 != null) {
                  FontName.method5748(var17, var6 != var17.group);
               }

               WorldMapManager.method626(var4, var6, var5);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2313 == var1.serverPacket) {
               GrandExchangeOfferTotalQuantityComparator.method208(class202.field2329);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2321 == var1.serverPacket) {
               var4 = var3.readInt();
               InterfaceParent var56 = (InterfaceParent)interfaceParents.method6346((long)var4);
               if(var56 != null) {
                  FontName.method5748(var56, true);
               }

               if(meslayerContinueWidget != null) {
                  WorldMapSectionType.method116(meslayerContinueWidget);
                  meslayerContinueWidget = null;
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2258 == var1.serverPacket) {
               var4 = var3.readShortA();
               byte var40 = var3.readByte();

               if (var4 >= 20000) {
                  CustomMisc.handleCustomVarp(var4, var40);
                  var1.serverPacket = null;
                  return true;
               }

               Varps.Varps_temp[var4] = var40;
               if(Varps.Varps_main[var4] != var40) {
                  Varps.Varps_main[var4] = var40;
                  settingsChanged(var4);
               }

               NetSocket.method3457(var4);
               field990[++field1031 - 1 & 31] = var4;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2323 == var1.serverPacket) {
               class329.method6295();
               runEnergy = var3.readUnsignedByte();
               field1041 = cycleCntr;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2317 == var1.serverPacket) {
               var4 = var3.readLEInt();
               var55 = Canvas.getWidget(var4);
               var55.modelType = 3;
               var55.modelId = class215.localPlayer.appearance.method4134();
               WorldMapSectionType.method116(var55);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2301 == var1.serverPacket) {
               WorldMapSection2.field3815 = var3.method5565();
               class28.field199 = var3.method5525();

               if(class28.field199 == 255 && class28.field199 == 255) {
                  for(int localX = 0; localX < 104; localX++) {
                     for(int localY = 0; localY < 104; localY++) {
                        if (groundItems[WorldMapRectangle.plane][localX][localY] != null) {
                           //groundItemDeque[WorldMapRectangle.plane][localX][localY] = null;
                           //MapCacheArchiveNames.groundItemSpawned(localX, localY);
                        }
                     }
                  }
                  for (PendingSpawn pendingspawn_0 = (PendingSpawn) pendingSpawns.getTail(); pendingspawn_0 != null; pendingspawn_0 = (PendingSpawn) pendingSpawns.getPrevious()) {
                     if (pendingspawn_0.plane == WorldMapRectangle.plane) {
                        pendingspawn_0.hitpoints = 0;
                     }
                  }
               } else {
                  for (var4 = class28.field199; var4 < class28.field199 + 8; ++var4) {
                     for (var5 = WorldMapSection2.field3815; var5 < WorldMapSection2.field3815 + 8; ++var5) {
                        if (groundItems[WorldMapRectangle.plane][var4][var5] != null) {
                           groundItems[WorldMapRectangle.plane][var4][var5] = null;
                           WorldMapCacheName.method673(var4, var5);
                        }
                     }
                  }

                  for (PendingSpawn var37 = (PendingSpawn) pendingSpawns.method5103(); var37 != null; var37 = (PendingSpawn) pendingSpawns.method5126()) {
                     if (var37.x >= class28.field199 && var37.x < class28.field199 + 8 && var37.y >= WorldMapSection2.field3815 && var37.y < WorldMapSection2.field3815 + 8 && var37.plane == WorldMapRectangle.plane) {
                        var37.hitpoints = 0;
                     }
                  }
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2264 == var1.serverPacket) {
               class202.method3855(var3, var1.serverPacketLength);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2310 == var1.serverPacket) {
               for(var4 = 0; var4 < Varps.Varps_main.length; ++var4) {
                  if(Varps.Varps_temp[var4] != Varps.Varps_main[var4]) {
                     Varps.Varps_main[var4] = Varps.Varps_temp[var4];
                     settingsChanged(var4);
                     NetSocket.method3457(var4);
                     field990[++field1031 - 1 & 31] = var4;
                  }
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2295 == var1.serverPacket) {
               Tiles.friendSystem.ignoreList.method5142(var3, var1.serverPacketLength);
               class194.method3773();
               field952 = cycleCntr;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2289 == var1.serverPacket) {
               InterfaceParent.method1141(false, var3);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2257 == var1.serverPacket) {
               PlayerType.method3945(true, var1.packetBuffer);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2268 == var1.serverPacket) {
               var4 = var3.readLEInt();
               var31 = var3.readString();
               var18 = Canvas.getWidget(var4);
               if(!var31.equals(var18.text)) {
                  var18.text = var31;
                  WorldMapSectionType.method116(var18);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2241 == var1.serverPacket) {
               var4 = var3.readUnsignedShort();
               if(var4 == 65535) {
                  var4 = -1;
               }

               MusicPatchNode2.method3725(var4);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2319 == var1.serverPacket) {
               var4 = var3.readLEShortA();
               if(var4 == 65535) {
                  var4 = -1;
               }

               var5 = var3.method5542();
               ClientPacket.method3878(var4, var5);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2237 == var1.serverPacket) {
               GrandExchangeOfferTotalQuantityComparator.method208(class202.field2328);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2240 == var1.serverPacket) {
               for(var4 = 0; var4 < VarpDefinition.VarpDefinition_fileCount; ++var4) {
                  VarpDefinition var54 = AbstractWorldMapData.method3328(var4);
                  if(var54 != null) {
                     Varps.Varps_temp[var4] = 0;
                     Varps.Varps_main[var4] = 0;
                     settingsChanged(var4);
                  }
               }

               class329.method6295();
               field1031 += 32;
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2245 == var1.serverPacket) {
               GrandExchangeOfferTotalQuantityComparator.method208(class202.field2334);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2274 == var1.serverPacket) {
               var4 = var3.offset + var1.serverPacketLength;
               var5 = var3.readUnsignedShort();
               var6 = var3.readUnsignedShort();
               if(var5 != rootInterface) {
                  rootInterface = var5;
                  this.method1874(false);
                  WorldMapManager.method631(rootInterface);
                  class186.method3728(rootInterface, 173929760);

                  for(slot = 0; slot < 100; ++slot) {
                     field1049[slot] = true;
                  }
               }

               InterfaceParent var32;
               for(; var6-- > 0; var32.field499 = true) {
                  slot = var3.readInt();
                  var8 = var3.readUnsignedShort();
                  var19 = var3.readUnsignedByte();
                  var32 = (InterfaceParent)interfaceParents.method6346((long)slot);
                  if(var32 != null && var8 != var32.group) {
                     FontName.method5748(var32, true);
                     var32 = null;
                  }

                  if(var32 == null) {
                     var32 = WorldMapManager.method626(slot, var8, var19);
                  }
               }

               for(var17 = (InterfaceParent)interfaceParents.method6348(); var17 != null; var17 = (InterfaceParent)interfaceParents.method6345()) {
                  if(var17.field499) {
                     var17.field499 = false;
                  } else {
                     FontName.method5748(var17, true);
                  }
               }

               widgetClickMasks = new NodeHashTable(512);

               label1180:
               while(true) {
                  if(var3.offset < var4) {
                     slot = var3.readInt();
                     var8 = var3.readUnsignedShort();
                     var19 = var3.readUnsignedShort();
                     var10 = var3.readInt();
                     int var33 = var8;

                     while(true) {
                        if(var33 > var19) {
                           continue label1180;
                        }

                        var12 = (long)var33 + ((long)slot << 32);
                        widgetClickMasks.put(new IntegerNode(var10), var12);
                        ++var33;
                     }
                  }

                  var1.serverPacket = null;
                  var10000 = true;
                  return var10000;
               }
            }

            if(ServerPacket.field2320 == var1.serverPacket) {
               var36 = var3.readString();
               var31 = AbstractFont.method5328(NetSocket.method3456(class65.method1308(var3)));
               class217.sendGameMessage(6, var36, var31);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2252 == var1.serverPacket) {
               DynamicObject.method1570();
               var1.serverPacket = null;
               var10000 = false;
               return var10000;
            }

            if(ServerPacket.field2269 == var1.serverPacket) {
               var4 = var3.readUnsignedShort();
               var5 = var3.readUnsignedByte();
               var6 = var3.readUnsignedShort();
               Message.method888(var4, var5, var6);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2254 == var1.serverPacket) {
               WorldMapManager.method630(var3, var1.serverPacketLength);
               Archive.method4280();
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2246 == var1.serverPacket) {
               GrandExchangeOfferTotalQuantityComparator.method208(class202.field2333);
               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2294 == var1.serverPacket) {
               if(rootInterface != -1) {
                  class28.method588(rootInterface, 0);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2251 == var1.serverPacket) {
               class28.field199 = var3.method5525();
               WorldMapSection2.field3815 = var3.readUnsignedByte();

               while(var3.offset < var1.serverPacketLength) {
                  var4 = var3.readUnsignedByte();
                  class202 var53 = WorldMapDecoration.method5196()[var4];
                  GrandExchangeOfferTotalQuantityComparator.method208(var53);
               }

               var1.serverPacket = null;
               var10000 = true;
               return var10000;
            }

            if(ServerPacket.field2288 != var1.serverPacket) {
               if(ServerPacket.field2261 == var1.serverPacket) {
                  destinationX = var3.readUnsignedByte();
                  if(destinationX == 255) {
                     destinationX = 0;
                  }

                  destinationY = var3.readUnsignedByte();
                  if(destinationY == 255) {
                     destinationY = 0;
                  }

                  var1.serverPacket = null;
                  var10000 = true;
                  return var10000;
               }

               if(ServerPacket.field2253 == var1.serverPacket) {
                  class213.privateChatMode = Fonts.method5757(var3.readUnsignedByte());
                  var1.serverPacket = null;
                  var10000 = true;
                  return var10000;
               }

               if(ServerPacket.field2279 == var1.serverPacket) {
                  var45 = var3.method5503();
                  if(var45) {
                     if(StudioGame.field2468 == null) {
                        StudioGame.field2468 = new class235();
                     }
                  } else {
                     StudioGame.field2468 = null;
                  }

                  var1.serverPacket = null;
                  var10000 = true;
                  return var10000;
               }

               if(ServerPacket.field2260 == var1.serverPacket) {
                  var4 = var3.readLEInt();
                  var5 = var3.readLEShortA();
                  var18 = Canvas.getWidget(var4);
                  if(var18 != null && var18.type == 0) {
                     if(var5 > var18.scrollHeight - var18.height) {
                        var5 = var18.scrollHeight - var18.height;
                     }

                     if(var5 < 0) {
                        var5 = 0;
                     }

                     if(var5 != var18.scrollY) {
                        var18.scrollY = var5;
                        WorldMapSectionType.method116(var18);
                     }
                  }

                  var1.serverPacket = null;
                  var10000 = true;
                  return var10000;
               }

               /*
                * TODO: Custom Packets
                */
               if (ServerPacket.CLAN_CHAT_SETTINGS == var1.serverPacket) {
                  CustomClanSettingsInterface.update(var3.readString(), var3.readByte(), var3.readByte(), var3.readByte());
                  var1.serverPacket = null;
                  return true;
               }

               if (ServerPacket.OPEN_URL == var1.serverPacket) {
                  String url = var3.readString();
                  boolean copy = var3.readByte() == 1;
                  ArchiveDiskActionHandler.method4341(url, true, false);
                  if(copy)
                     ViewportMouse.client.method963(url);
                  var1.serverPacket = null;
                  return true;
               }

               if (ServerPacket.SHOP_INTERFACE == var1.serverPacket) {
                  String name = var3.readString();
                  int size = var3.g2s();
                  int[][] items = new int[size][];
                  int[] itemRemaining = new int[size];
                  TileItem[] stock = new TileItem[size];
                  for (int i = 0; i < size; i++) {
                     int itemId = var3.g2s();
                     int itemCost = var3.readInt();
                     int remaining = var3.readInt();
                     itemRemaining[i] = remaining;
                     TileItem it = new TileItem(itemId, itemCost);
                     items[i] = new int[] {itemId, itemCost};
                     stock[i] = it;
                  }
                  CustomShopInterface.init(name, items, itemRemaining);
                  CustomShopInterface.updateMappedContainer(1124, stock);
                  var1.serverPacket = null;
                  return true;
               }

               if(ServerPacket.TELEPORT_INTERFACE == var1.serverPacket) {
                  String title = var3.readString();

                  int catIndex = var3.readUnsignedByte();
                  String[] cats = new String[var3.readUnsignedByte()];
                  for(int i = 0; i < cats.length; i++)
                     cats[i] = var3.readString();

                  int subIndex = var3.readUnsignedByte();
                  String[] subs = new String[var3.readUnsignedByte()];
                  for(int i = 0; i < subs.length; i++)
                     subs[i] = var3.readString();

                  List<String> options = new ArrayList<String>();
                  while(var3.offset < var1.serverPacketLength)
                     options.add(var3.readString());

                  CustomTeleportInterface.load(title, catIndex, cats, subIndex, subs, (String[])((List)options).toArray(new String[0]));
                  var1.serverPacket = null;
                  return true;
               }

               if(ServerPacket.DROP_TABLE_INTERFACE == var1.serverPacket) {
                  String name = var3.readString();
                  int petId = var3.readUnsignedShort();
                  int petAverage = var3.readUnsignedShort();
                  List<Integer[]> drops = new ArrayList<Integer[]>();
                  while(var3.offset < var1.serverPacketLength) {
                     Integer[] drop = new Integer[5];
                     drop[0] = var3.readUnsignedShort();    //id
                     drop[1] = var3.readUnsignedByte();     //broadcast
                     drop[2] = var3.readInt();              //min amount
                     drop[3] = var3.readInt();              //max amount
                     drop[4] = var3.readUnsignedShort();    //average
                     drops.add(drop);
                  }
                  CustomDropViewerInterface.init(name, petId, petAverage, drops);
                  var1.serverPacket = null;
                  return true;
               }

               if (ServerPacket.WIDGET_TIMER == var1.serverPacket) {
                  try {
                     int spriteId = var3.readUnsignedByte();
                     int time = var3.readUnsignedShort();
                     String name = var3.readString();
                     String description = var3.readString();
                     CustomWidgetTimer.add(new CustomWidgetTimer(spriteId, time, name, description));
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
                  var1.serverPacket = null;
                  return true;
               }

               class19.method342("" + (var1.serverPacket != null?var1.serverPacket.id:-1) + "," + (var1.field828 != null?var1.field828.id:-1) + "," + (var1.field838 != null?var1.field838.id:-1) + "," + var1.serverPacketLength, (Throwable)null);
               DynamicObject.method1570();
               return true;
            }

            for(var4 = 0; var4 < players.length; ++var4) {
               if(players[var4] != null) {
                  players[var4].sequence = -1;
                  players[var4].animationChanged(-1);
               }
            }

            for(var4 = 0; var4 < npcs.length; ++var4) {
               if(npcs[var4] != null) {
                  npcs[var4].sequence = -1;
                  npcs[var4].animationChanged(-1);
               }
            }

            var1.serverPacket = null;
            var10000 = true;
         } catch (IOException var34) {
            MouseRecorder.method1208();
            return true;
         } catch (Exception var35) {
            var31 = "" + (var1.serverPacket != null?var1.serverPacket.id:-1) + "," + (var1.field828 != null?var1.field828.id:-1) + "," + (var1.field838 != null?var1.field838.id:-1) + "," + var1.serverPacketLength + "," + (class215.localPlayer.pathX[0] + class215.baseX) + "," + (class215.localPlayer.pathY[0] + class304.baseY) + ",";

            for(var6 = 0; var6 < var1.serverPacketLength && var6 < 50; ++var6) {
               var31 = var31 + var3.array[var6] + ",";
            }

            class19.method342(var31, var35);
            DynamicObject.method1570();
            return true;
         }

         return var10000;
      }
   }

   @ObfuscatedName("he")
   final void method1659(short var1) {
      Menu var2 = Menu.MENU;
      var2.reset();
      this.getCallbacks().post(Menu.class, var2);
      if(var2.shouldRun()) {
         this.copy$menu(var1);
      }

   }

   @ObfuscatedName("ho")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "1079121521"
   )
   final void method1661(int var1, int var2) {
      this.menuOpened(var1, var2);
      UserComparator6.method3507(var1, var2);
      PacketWriter.scene.method3151(WorldMapRectangle.plane, var1, var2, false);
      isMenuOpen = true;
   }

   @ObfuscatedName("hq")
   final boolean method1660(byte var1) {
      if(this.copy$shouldLeftClickOpenMenu(var1)) {
         return true;
      } else {
         MenuShouldLeftClick var2 = new MenuShouldLeftClick();
         ViewportMouse.client.getCallbacks().post(MenuShouldLeftClick.class, var2);
         if(var2.isForceRightClick()) {
            return true;
         } else {
            int var3 = this.getMenuOptionCount();
            if(var3 > 0) {
               int var4 = this.getMenuOpcodes()[var3 - 1];
               return var4 == MenuOpcode.RUNELITE_OVERLAY.getId();
            } else {
               return false;
            }
         }
      }
   }

   @ObfuscatedName("ij")
   @ObfuscatedSignature(
      signature = "(ZI)V",
      garbageValue = "313560965"
   )
   final void method1874(boolean var1) {
      class256.method4654(rootInterface, FloorDecoration.canvasWidth, WallDecoration.canvasHeight, var1);
   }

   @ObfuscatedName("is")
   @ObfuscatedSignature(
      signature = "(Lho;B)V",
      garbageValue = "1"
   )
   void method1774(Widget var1) {
      Widget var2 = var1.parentId == -1?null:Canvas.getWidget(var1.parentId);
      int var3;
      int var4;
      if(var2 == null) {
         var3 = FloorDecoration.canvasWidth;
         var4 = WallDecoration.canvasHeight;
      } else {
         var3 = var2.width;
         var4 = var2.height;
      }

      TileItem.method1607(var1, var3, var4, false);
      class33.method678(var1, var3, var4);
   }

   @ObfuscatedName("jt")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-808375331"
   )
   final void method1663() {
      WorldMapSectionType.method116(clickedWidget);
      ++ViewportMouse.widgetDragDuration;
      if(field1025 && field1022) {
         int var1 = MouseHandler.MouseHandler_x;
         int var2 = MouseHandler.MouseHandler_y;
         var1 -= widgetClickX;
         var2 -= widgetClickY;
         if(var1 < field903) {
            var1 = field903;
         }

         if(var1 + clickedWidget.width > field903 + clickedWidgetParent.width) {
            var1 = field903 + clickedWidgetParent.width - clickedWidget.width;
         }

         if(var2 < field1024) {
            var2 = field1024;
         }

         if(var2 + clickedWidget.height > field1024 + clickedWidgetParent.height) {
            var2 = field1024 + clickedWidgetParent.height - clickedWidget.height;
         }

         int var3 = var1 - field1026;
         int var4 = var2 - field1027;
         int var5 = clickedWidget.dragZoneSize;
         if(ViewportMouse.widgetDragDuration > clickedWidget.dragThreshold && (var3 > var5 || var3 < -var5 || var4 > var5 || var4 < -var5)) {
            isDraggingWidget = true;
            draggingWidgetChanged(-1);
         }

         int var6 = var1 - field903 + clickedWidgetParent.scrollX;
         int var7 = var2 - field1024 + clickedWidgetParent.scrollY;
         ScriptEvent var8;
         if(clickedWidget.onDrag != null && isDraggingWidget) {
            var8 = new ScriptEvent();
            var8.widget = clickedWidget;
            var8.mouseX = var6;
            var8.mouseY = var7;
            var8.args = clickedWidget.onDrag;
            ParamDefinition.method4313(var8);
         }

         if(MouseHandler.MouseHandler_currentButton == 0) {
            if(isDraggingWidget) {
               if(clickedWidget.onDragComplete != null) {
                  var8 = new ScriptEvent();
                  var8.widget = clickedWidget;
                  var8.mouseX = var6;
                  var8.mouseY = var7;
                  var8.dragTarget = draggedOnWidget;
                  var8.args = clickedWidget.onDragComplete;
                  ParamDefinition.method4313(var8);
               }

               if(draggedOnWidget != null && GrandExchangeEvents.method99(clickedWidget) != null) {
                  PacketBufferNode var9 = InterfaceParent.method1140(ClientPacket.field2434, packetWriter.isaacCipher);
                  var9.packetBuffer.method5532(draggedOnWidget.childIndex);
                  var9.packetBuffer.method5532(draggedOnWidget.itemId);
                  var9.packetBuffer.method5530(clickedWidget.childIndex);
                  var9.packetBuffer.method5684(draggedOnWidget.id);
                  var9.packetBuffer.method5659(clickedWidget.itemId);
                  var9.packetBuffer.writeInt(clickedWidget.id);
                  packetWriter.method1622(var9);
               }
            } else if(this.method1660((byte)-77)) {
               this.method1661(field1026 + widgetClickX, widgetClickY + field1027);
            } else if(menuOptionsCount > 0) {
               class11.method137(field1026 + widgetClickX, field1027 + widgetClickY);
            }

            clickedWidget = null;
         }

      } else {
         if(ViewportMouse.widgetDragDuration > 1) {
            clickedWidget = null;
         }

      }
   }

   @ObfuscatedName("kz")
   @ObfuscatedSignature(
      signature = "(S)Ljq;",
      garbageValue = "9955"
   )
   public Username vmethod5099() {
      return class215.localPlayer != null?class215.localPlayer.username:null;
   }

   @ObfuscatedName("q")
   static int method2043(long var0) {
      return (int)(var0 >>> 14 & 3L);
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "431297908"
   )
   public static void method1901() {
      if(MouseHandler.MouseHandler_instance != null) {
         MouseHandler var0 = MouseHandler.MouseHandler_instance;
         synchronized(MouseHandler.MouseHandler_instance) {
            MouseHandler.MouseHandler_instance = null;
         }
      }

   }

   @ObfuscatedSignature(
      signature = "(Lba;IB)V"
   )
   public static void copy$runScript(ScriptEvent var0, int var1, byte var2) {
      try {
         Object[] var3 = var0.args;
         Object var4;
         int var18;
         if(Strings.method4189(var0.type)) {
            EnumDefinition.worldMapEvent = (WorldMapEvent)var3[0];
            WorldMapElement var5 = Decimator.method2498(EnumDefinition.worldMapEvent.mapElement);
            var4 = currentScript = class30.method637(var0.type, var5.objectId, var5.category);
         } else {
            var18 = currentScriptPC = ((Integer)var3[0]).intValue();
            var4 = currentScript = LoginPacket.method3723(var18);
         }

         if(var4 != null) {
            Interpreter.Interpreter_intStackSize = 0;
            Interpreter.Interpreter_stringStackSize = 0;
            currentScriptPC = -1;
            var18 = -1;
            int[] var6 = ((Script)var4).opcodes;
            int[] var7 = ((Script)var4).intOperands;
            byte var8 = -1;
            Interpreter.Interpreter_frameDepth = 0;
            Interpreter.field645 = false;
            boolean var29 = false;

            label890: {
               label891: {
                  try {
                     int var11;
                     try {
                        label942: {
                           var29 = true;
                           ScriptFrame.field649 = new int[((Script)var4).localIntCount];
                           int var9 = 0;
                           ScriptFrame.field1761 = new String[((Script)var4).localStringCount];
                           int var10 = 0;

                           int var12;
                           int var10000;
                           String var19;
                           for(var11 = 1; var11 < var3.length; ++var11) {
                              if(var3[var11] instanceof Integer) {
                                 var12 = ((Integer)var3[var11]).intValue();
                                 if(var12 == -2147483647) {
                                    if(var2 != 0) {
                                       return;
                                    }

                                    var12 = var0.mouseX;
                                 }

                                 if(var12 == -2147483646) {
                                    var12 = var0.mouseY;
                                 }

                                 if(var12 == -2147483645) {
                                    if(var2 != 0) {
                                       return;
                                    }

                                    var12 = var0.widget != null?var0.widget.id:-1;
                                 }

                                 if(var12 == -2147483644) {
                                    var12 = var0.opIndex;
                                 }

                                 if(var12 == -2147483643) {
                                    if(var2 != 0) {
                                       return;
                                    }

                                    var12 = var0.widget != null?var0.widget.childIndex:-1;
                                 }

                                 if(var12 == -2147483642) {
                                    if(var0.dragTarget != null) {
                                       if(var2 != 0) {
                                          return;
                                       }

                                       var10000 = var0.dragTarget.id;
                                    } else {
                                       var10000 = -1;
                                    }

                                    var12 = var10000;
                                 }

                                 if(var12 == -2147483641) {
                                    var12 = var0.dragTarget != null?var0.dragTarget.childIndex:-1;
                                 }

                                 if(var12 == -2147483640) {
                                    var12 = var0.keyTyped;
                                 }

                                 if(var12 == -2147483639) {
                                    var12 = var0.keyPressed;
                                 }

                                 ScriptFrame.field649[var9++] = var12;
                              } else if(var3[var11] instanceof String) {
                                 var19 = (String)var3[var11];
                                 if(var19.equals("event_opbase")) {
                                    var19 = var0.targetName;
                                 }

                                 ScriptFrame.field1761[var10++] = var19;
                              }
                           }

                           var11 = 0;
                           Interpreter.field660 = var0.field337;

                           while(true) {
                              ++var11;
                              if(var11 > var1) {
                                 throw new RuntimeException();
                              }

                              int var33;
                              do {
                                 ++var18;
                                 currentScriptPC = var18;
                                 var33 = var6[var18];
                              } while(vmExecuteOpcode(var33));

                              int var21;
                              if(var33 >= 100) {
                                 boolean var36;
                                 if(((Script)var4).intOperands[var18] == 1) {
                                    var36 = true;
                                 } else {
                                    var36 = false;
                                 }

                                 var21 = TileItem.method1606(var33, (Script)var4, var36);
                                 switch(var21) {
                                 case 0:
                                    var29 = false;
                                    break label942;
                                 case 1:
                                 default:
                                    break;
                                 case 2:
                                    throw new IllegalStateException();
                                 }
                              } else {
                                 if(var2 != 0) {
                                    return;
                                 }

                                 if(var33 == 0) {
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var7[var18];
                                 } else if(var33 == 1) {
                                    var12 = var7[var18];
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Varps.Varps_main[var12];
                                 } else if(var33 == 2) {
                                    var12 = var7[var18];
                                    Varps.Varps_main[var12] = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                                    settingsChanged(var12);
                                    NetSocket.method3457(var12);
                                 } else if(var33 == 3) {
                                    Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = ((Script)var4).stringOperands[var18];
                                 } else {
                                    int var10001;
                                    if(var33 == 6) {
                                       var10001 = var7[var18];
                                       var10000 = var18 + var7[var18];
                                       currentScriptPC = var18 + var10001;
                                       var18 = var10000;
                                    } else if(var33 == 7) {
                                       if(var2 != 0) {
                                          return;
                                       }

                                       Interpreter.Interpreter_intStackSize -= 2;
                                       if(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize] != Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]) {
                                          var10001 = var7[var18];
                                          var10000 = var18 + var7[var18];
                                          currentScriptPC = var18 + var10001;
                                          var18 = var10000;
                                       }
                                    } else if(var33 == 8) {
                                       Interpreter.Interpreter_intStackSize -= 2;
                                       if(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize] == Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]) {
                                          var10001 = var7[var18];
                                          var10000 = var18 + var7[var18];
                                          currentScriptPC = var18 + var10001;
                                          var18 = var10000;
                                       }
                                    } else if(var33 == 9) {
                                       Interpreter.Interpreter_intStackSize -= 2;
                                       if(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize] < Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]) {
                                          if(var2 != 0) {
                                             return;
                                          }

                                          var10001 = var7[var18];
                                          var10000 = var18 + var7[var18];
                                          currentScriptPC = var18 + var10001;
                                          var18 = var10000;
                                       }
                                    } else if(var33 == 10) {
                                       Interpreter.Interpreter_intStackSize -= 2;
                                       if(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize] > Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]) {
                                          var10001 = var7[var18];
                                          var10000 = var18 + var7[var18];
                                          currentScriptPC = var18 + var10001;
                                          var18 = var10000;
                                       }
                                    } else if(var33 == 21) {
                                       if(Interpreter.Interpreter_frameDepth == 0) {
                                          var29 = false;
                                          break label891;
                                       }

                                       ScriptFrame var38 = Interpreter.Interpreter_frames[--Interpreter.Interpreter_frameDepth];
                                       var4 = currentScript = var38.field638;
                                       var6 = ((Script)var4).opcodes;
                                       var7 = ((Script)var4).intOperands;
                                       var18 = currentScriptPC = var38.pc;
                                       ScriptFrame.field649 = var38.field640;
                                       ScriptFrame.field1761 = var38.field641;
                                    } else if(var33 == 25) {
                                       var12 = var7[var18];
                                       Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = WorldMapSprite.method782(var12);
                                    } else if(var33 == 27) {
                                       var12 = var7[var18];
                                       WorldMapElement.method4378(var12, Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
                                    } else if(var33 == 31) {
                                       Interpreter.Interpreter_intStackSize -= 2;
                                       if(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize] <= Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]) {
                                          var10001 = var7[var18];
                                          var10000 = var18 + var7[var18];
                                          currentScriptPC = var18 + var10001;
                                          var18 = var10000;
                                       }
                                    } else if(var33 == 32) {
                                       Interpreter.Interpreter_intStackSize -= 2;
                                       if(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize] >= Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]) {
                                          var10001 = var7[var18];
                                          var10000 = var18 + var7[var18];
                                          currentScriptPC = var18 + var10001;
                                          var18 = var10000;
                                       }
                                    } else if(var33 == 33) {
                                       Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = ScriptFrame.field649[var7[var18]];
                                    } else if(var33 == 34) {
                                       ScriptFrame.field649[var7[var18]] = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                                    } else if(var33 == 35) {
                                       Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = ScriptFrame.field1761[var7[var18]];
                                    } else if(var33 == 36) {
                                       ScriptFrame.field1761[var7[var18]] = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                                    } else if(var33 == 37) {
                                       var12 = var7[var18];
                                       Interpreter.Interpreter_stringStackSize -= var12;
                                       String var35 = FloorOverlayDefinition.method4360(Interpreter.Interpreter_stringStack, Interpreter.Interpreter_stringStackSize, var12);
                                       Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var35;
                                    } else if(var33 == 38) {
                                       if(var2 != 0) {
                                          return;
                                       }

                                       --Interpreter.Interpreter_intStackSize;
                                    } else if(var33 == 39) {
                                       --Interpreter.Interpreter_stringStackSize;
                                    } else {
                                       int var16;
                                       if(var33 != 40) {
                                          if(var33 == 42) {
                                             Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = class197.varcs.method2170(var7[var18]);
                                          } else if(var33 == 43) {
                                             class197.varcs.method2190(var7[var18], Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
                                          } else if(var33 == 44) {
                                             var12 = var7[var18] >> 16;
                                             var21 = var7[var18] & 65535;
                                             int var22 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                                             if(var22 < 0) {
                                                break;
                                             }

                                             if(var22 > 5000) {
                                                if(var2 != 0) {
                                                   return;
                                                }
                                                break;
                                             }

                                             Interpreter.Interpreter_arrayLengths[var12] = var22;
                                             byte var23 = -1;
                                             if(var21 == 105) {
                                                var23 = 0;
                                             }

                                             for(var16 = 0; var16 < var22; ++var16) {
                                                Interpreter.Interpreter_arrays[var12][var16] = var23;
                                             }
                                          } else if(var33 == 45) {
                                             var12 = var7[var18];
                                             var21 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                                             if(var21 < 0 || var21 >= Interpreter.Interpreter_arrayLengths[var12]) {
                                                throw new RuntimeException();
                                             }

                                             Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Interpreter.Interpreter_arrays[var12][var21];
                                          } else if(var33 == 46) {
                                             var12 = var7[var18];
                                             Interpreter.Interpreter_intStackSize -= 2;
                                             var21 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                                             if(var21 < 0 || var21 >= Interpreter.Interpreter_arrayLengths[var12]) {
                                                throw new RuntimeException();
                                             }

                                             Interpreter.Interpreter_arrays[var12][var21] = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
                                          } else if(var33 == 47) {
                                             var19 = class197.varcs.method2161(var7[var18]);
                                             if(var19 == null) {
                                                var19 = Strings.field2799;
                                             }

                                             Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var19;
                                          } else if(var33 == 48) {
                                             class197.varcs.method2166(var7[var18], Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize]);
                                          } else if(var33 == 49) {
                                             var19 = class197.varcs.method2165(var7[var18]);
                                             Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var19;
                                          } else if(var33 == 50) {
                                             if(var2 != 0) {
                                                return;
                                             }

                                             class197.varcs.method2164(var7[var18], Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize]);
                                          } else {
                                             if(var33 != 60) {
                                                throw new IllegalStateException();
                                             }

                                             IterableNodeHashTable var37 = ((Script)var4).switches[var7[var18]];
                                             IntegerNode var34 = (IntegerNode)var37.method6061((long)Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
                                             if(var34 != null) {
                                                var18 = currentScriptPC = var18 + var34.integer;
                                             }
                                          }
                                       } else {
                                          var12 = var7[var18];
                                          Script var13 = LoginPacket.method3723(var12);
                                          int[] var14 = new int[var13.localIntCount];
                                          String[] var15 = new String[var13.localStringCount];

                                          for(var16 = 0; var16 < var13.intArgumentCount; ++var16) {
                                             var14[var16] = Interpreter.Interpreter_intStack[var16 + (Interpreter.Interpreter_intStackSize - var13.intArgumentCount)];
                                          }

                                          for(var16 = 0; var16 < var13.stringArgumentCount; ++var16) {
                                             var15[var16] = Interpreter.Interpreter_stringStack[var16 + (Interpreter.Interpreter_stringStackSize - var13.stringArgumentCount)];
                                          }

                                          Interpreter.Interpreter_intStackSize -= var13.intArgumentCount;
                                          Interpreter.Interpreter_stringStackSize -= var13.stringArgumentCount;
                                          ScriptFrame var20 = new ScriptFrame();
                                          var20.field638 = (Script)var4;
                                          var20.pc = var18;
                                          var20.field640 = ScriptFrame.field649;
                                          var20.field641 = ScriptFrame.field1761;
                                          Interpreter.Interpreter_frames[++Interpreter.Interpreter_frameDepth - 1] = var20;
                                          currentScript = var13;
                                          var4 = var13;
                                          var6 = var13.opcodes;
                                          var7 = var13.intOperands;
                                          currentScriptPC = -1;
                                          var18 = -1;
                                          ScriptFrame.field649 = var14;
                                          ScriptFrame.field1761 = var15;
                                       }
                                    }
                                 }
                              }
                           }

                           throw new RuntimeException();
                        }
                     } catch (Exception var30) {
                        StringBuilder var25 = new StringBuilder(30);
                        var25.append("").append(((Node)var4).key).append(" ");

                        for(var11 = Interpreter.Interpreter_frameDepth - 1; var11 >= 0; --var11) {
                           var25.append("").append(Interpreter.Interpreter_frames[var11].field638.key).append(" ");
                        }

                        var25.append("").append(var8);
                        class19.method342(var25.toString(), var30);
                        var29 = false;
                        break label890;
                     }
                  } finally {
                     if(var29) {
                        if(Interpreter.field645) {
                           Interpreter.field646 = true;
                           class37.method728();
                           Interpreter.field646 = false;
                           Interpreter.field645 = false;
                        }

                     }
                  }

                  if(Interpreter.field645) {
                     if(var2 != 0) {
                        return;
                     }

                     Interpreter.field646 = true;
                     class37.method728();
                     Interpreter.field646 = false;
                     Interpreter.field645 = false;
                  }

                  return;
               }

               if(Interpreter.field645) {
                  Interpreter.field646 = true;
                  class37.method728();
                  Interpreter.field646 = false;
                  Interpreter.field645 = false;
               }

               return;
            }

            if(Interpreter.field645) {
               Interpreter.field646 = true;
               class37.method728();
               Interpreter.field646 = false;
               Interpreter.field645 = false;
            }

         }
      } catch (RuntimeException var32) {
         throw class125.method2877(var32, "ah.n(" + ')');
      }
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void canvasWidthChanged(int var0) {
      ViewportMouse.client.getCallbacks().post(CanvasSizeChanged.class, CanvasSizeChanged.INSTANCE);
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void gameStateChanged(int var0) {
      GameStateChanged var1 = new GameStateChanged();
      var1.setGameState(ViewportMouse.client.getGameState());
      ViewportMouse.client.getCallbacks().post(GameStateChanged.class, var1);
   }

   @ObfuscatedSignature(
      signature = "(Lhp;III)Llf;"
   )
   public static Sprite copy$SpriteBuffer_getSprite(AbstractArchive var0, int var1, int var2, int var3) {
      return !VertexNormal.method2468(var0, var1, var2)?null:FillMode.method6384();
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void canvasHeightChanged(int var0) {
      ViewportMouse.client.getCallbacks().post(CanvasSizeChanged.class, CanvasSizeChanged.INSTANCE);
   }

   public static void onAddChatMessage(int var0, String var1, String var2, String var3) {
      Logger var4 = ViewportMouse.client.getLogger();
      if(var4.isDebugEnabled()) {
         var4.debug("Chat message type {}: {}", ChatMessageType.of(var0), var2);
      }

      Map var5 = ViewportMouse.client.getChatLineMap();
      RSChatChannel var6 = (RSChatChannel)var5.get(Integer.valueOf(var0));
      RSMessage var7 = var6.getLines()[0];
      ChatMessageType var8 = ChatMessageType.of(var0);
      ChatMessage var9 = new ChatMessage(var7, var8, var1, var2, var3, var7.getTimestamp());
      ViewportMouse.client.getCallbacks().post(ChatMessage.class, var9);
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void onMenuOptionsChanged(int var0) {
      int var1 = oldMenuEntryCount;
      int var2 = ViewportMouse.client.getMenuOptionCount();
      oldMenuEntryCount = var2;
      String[] var3 = ViewportMouse.client.getMenuOptions();
      String[] var4 = ViewportMouse.client.getMenuTargets();
      int[] var5 = ViewportMouse.client.getMenuIdentifiers();
      int[] var6 = ViewportMouse.client.getMenuOpcodes();
      int[] var7 = ViewportMouse.client.getMenuArguments1();
      int[] var8 = ViewportMouse.client.getMenuArguments2();
      boolean[] var9 = ViewportMouse.client.getMenuForceLeftClick();
      if(var2 == var1 + 1) {
         MenuEntryAdded var10 = new MenuEntryAdded(var3[var1], var4[var1], var5[var1], var6[var1], var7[var1], var8[var1], var9[var1]);
         ViewportMouse.client.getCallbacks().post(MenuEntryAdded.class, var10);
         if(var10.isModified() && ViewportMouse.client.getMenuOptionCount() == var2) {
            var3[var1] = var10.getOption();
            var4[var1] = var10.getTarget();
            var5[var1] = var10.getIdentifier();
            var6[var1] = var10.getOpcode();
            var7[var1] = var10.getParam0();
            var8[var1] = var10.getParam1();
            var9[var1] = var10.isForceLeftClick();
         }
      }

   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void onUsernameChanged(int var0) {
      ViewportMouse.client.getCallbacks().post(UsernameChanged.class, UsernameChanged.INSTANCE);
   }

   public static void copy$Rasterizer2D_drawGradientPixels(int var0, int var1, int var2, int var3, int var4, int var5, byte[] var6, int var7, boolean var8) {
      if(var0 + var2 >= 0 && var3 + var1 >= 0) {
         if(var0 < Rasterizer2D.Rasterizer2D_width && var1 < Rasterizer2D.Rasterizer2D_height) {
            int var9 = 0;
            int var10 = 0;
            if(var0 < 0) {
               var9 -= var0;
               var2 += var0;
            }

            if(var1 < 0) {
               var10 -= var1;
               var3 += var1;
            }

            if(var0 + var2 > Rasterizer2D.Rasterizer2D_width) {
               var2 = Rasterizer2D.Rasterizer2D_width - var0;
            }

            if(var3 + var1 > Rasterizer2D.Rasterizer2D_height) {
               var3 = Rasterizer2D.Rasterizer2D_height - var1;
            }

            int var11 = var6.length / var7;
            int var12 = Rasterizer2D.Rasterizer2D_width - var2;
            int var13 = var4 >>> 24;
            int var14 = var5 >>> 24;
            int var15;
            int var16;
            int var17;
            int var18;
            int var19;
            if(var8 && (var13 != 255 || var14 != 255)) {
               var15 = var0 + var9 + (var10 + var1) * Rasterizer2D.Rasterizer2D_width;

               for(var16 = var10 + var1; var16 < var3 + var10 + var1; ++var16) {
                  for(var17 = var0 + var9; var17 < var0 + var9 + var2; ++var17) {
                     var18 = (var16 - var1) % var11;
                     var19 = (var17 - var0) % var7;
                     int var20 = var4;
                     if(var6[var19 + var18 * var7] != 0) {
                        var20 = var5;
                     }

                     int var21 = var20 >>> 24;
                     int var22 = 255 - var21;
                     int var23 = Rasterizer2D.Rasterizer2D_pixels[var15];
                     int var24 = (var21 * (var20 & 65280) + var22 * (var23 & 65280) & 16711680) + ((var20 & 16711935) * var21 + (var23 & 16711935) * var22 & -16711936) >> 8;
                     Rasterizer2D.Rasterizer2D_pixels[var15++] = var24 | -16777216;
                  }

                  var15 += var12;
               }
            } else {
               var15 = var0 + var9 + (var10 + var1) * Rasterizer2D.Rasterizer2D_width;

               for(var16 = var10 + var1; var16 < var3 + var10 + var1; ++var16) {
                  for(var17 = var0 + var9; var17 < var0 + var9 + var2; ++var17) {
                     var18 = (var16 - var1) % var11;
                     var19 = (var17 - var0) % var7;
                     if(var6[var19 + var18 * var7] != 0) {
                        Rasterizer2D.Rasterizer2D_pixels[var15++] = var5 | -16777216;
                     } else {
                        Rasterizer2D.Rasterizer2D_pixels[var15++] = var4 | -16777216;
                     }
                  }

                  var15 += var12;
               }
            }

         }
      }
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void queuedSoundEffectCountChanged(int var0) {
      int var1 = ViewportMouse.client.getQueuedSoundEffectCount();
      if(var1 == lastSoundEffectCount + 1) {
         int var2 = var1 - 1;
         int var3 = ViewportMouse.client.getSoundLocations()[var2];
         if(var3 == 0) {
            SoundEffectPlayed var4 = new SoundEffectPlayed();
            var4.setSoundId(ViewportMouse.client.getQueuedSoundEffectIDs()[var2]);
            var4.setDelay(ViewportMouse.client.getQueuedSoundEffectDelays()[var2]);
            ViewportMouse.client.getCallbacks().post(SoundEffectPlayed.class, var4);
         } else {
            int var8 = var3 >> 16 & 255;
            int var5 = var3 >> 8 & 255;
            int var6 = var3 & 255;
            AreaSoundEffectPlayed var7 = new AreaSoundEffectPlayed();
            var7.setSoundId(ViewportMouse.client.getQueuedSoundEffectIDs()[var2]);
            var7.setSceneX(var8);
            var7.setSceneY(var5);
            var7.setRange(var6);
            var7.setDelay(ViewportMouse.client.getQueuedSoundEffectDelays()[var2]);
            ViewportMouse.client.getCallbacks().post(AreaSoundEffectPlayed.class, var7);
         }
      }

      lastSoundEffectCount = var1;
   }

   public static int rl$rot1(int var0, int var1, int var2, int var3) {
      return var0 * var2 + var3 * var1 >> 16;
   }

   public static int rl$rot2(int var0, int var1, int var2, int var3) {
      return var2 * var1 - var3 * var0 >> 16;
   }

   public static void drawAlpha(int[] var0, int var1, int var2, int var3) {
      if(ViewportMouse.client.isGpu() && var0 == ViewportMouse.client.getBufferProvider().getPixels()) {
         int var4 = var3 + ((var0[var1] >>> 24) * (255 - var3) * 32897 >>> 23);
         var0[var1] = var2 & 16777215 | var4 << 24;
      } else {
         var0[var1] = var2;
      }
   }

   public static void renderWidgetLayer(net.runelite.api.widgets.Widget[] var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      Callbacks var9 = ViewportMouse.client.getCallbacks();
      RSNodeHashTable var10 = ViewportMouse.client.getComponentTable();
      net.runelite.api.widgets.Widget[] var11 = var0;
      int var12 = var0.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         net.runelite.api.widgets.Widget var14 = var11[var13];
         RSWidget var15 = (RSWidget)var14;
         if(var15 != null && var15.getRSParentId() == var1 && !var15.isSelfHidden()) {
            if(var1 != -1) {
               var15.setRenderParentId(var1);
            }

            int var16 = var6 + var15.getRelativeX();
            int var17 = var7 + var15.getRelativeY();
            var15.setRenderX(var16);
            var15.setRenderY(var17);
            int var18 = var15.getType();
            if(var18 == 5 && var15.getItemId() != -1) {
               if(var16 >= var2 && var16 <= var4 && var17 >= var3 && var17 <= var5) {
                  String[] attributes = new String[3];
                  WidgetItem var29 = new WidgetItem(var15.getItemId(), var15.getItemQuantity(), -1, var15.getBounds(), var15, attributes);
                  var9.drawItem(var15.getItemId(), var29);
               }
            } else if(var18 == 2) {
               Collection var19 = var15.getWidgetItems();
               Iterator var27 = var19.iterator();

               while(var27.hasNext()) {
                  WidgetItem var28 = (WidgetItem)var27.next();
                  var9.drawItem(var28.getId(), var28);
               }
            }

            WidgetNode var30 = (WidgetNode)var10.get((long)var15.getId());
            if(var30 != null) {
               int var20 = var15.getId();
               int var21 = var30.getId();
               RSWidget[] var22 = ViewportMouse.client.getWidgets()[var21];
               RSWidget[] var23 = var22;
               int var24 = var22.length;

               for(int var25 = 0; var25 < var24; ++var25) {
                  RSWidget var26 = var23[var25];
                  if (var26 == null)
                     var26 = new Widget();
                  if(var26.getRSParentId() == -1) {
                     var26.setRenderParentId(var20);
                  }
               }
            }
         }
      }

   }

   public static int rl$rot3(int var0, int var1, int var2, int var3) {
      return var0 * var2 - var3 * var1 >> 16;
   }

   public static int rl$rot4(int var0, int var1, int var2, int var3) {
      return var2 * var1 + var3 * var0 >> 16;
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void itemPressedDurationChanged(int var0) {
      if(ViewportMouse.client.getItemPressedDuration() > 0) {
         ++itemPressedDurationBuffer;
         if(itemPressedDurationBuffer >= inventoryDragDelay) {
            ViewportMouse.client.setItemPressedDuration(itemPressedDurationBuffer);
         } else {
            ViewportMouse.client.setItemPressedDuration(0);
         }
      } else {
         itemPressedDurationBuffer = 0;
      }

   }

   public static final void copy$menuAction(int var0, int var1, int var2, int var3, String var4, String var5, int var6, int var7, int var8) {
      if(var2 >= 2000) {
         var2 -= 2000;
      }

      PacketBufferNode var9;
      PacketBuffer var10000;
      byte var10001;
      if(var2 == 1) {
         mouseCrossX = var6;
         mouseCrossY = var7;
         mouseCrossColor = 2;
         mouseCrossState = 0;
         destinationX = var0;
         destinationY = var1;
         var9 = InterfaceParent.method1140(ClientPacket.field2386, packetWriter.isaacCipher);
         var9.packetBuffer.method5530(var0 + class215.baseX);
         var9.packetBuffer.method5481(class304.baseY + var1);
         var9.packetBuffer.method5659(AbstractWorldMapData.selectedItemId);
         var9.packetBuffer.method5684(class12.selectedItemWidget);
         var9.packetBuffer.method5481(DevicePcmPlayerProvider.selectedItemSlot);
         var9.packetBuffer.method5595(var3);
         var10000 = var9.packetBuffer;
         if(KeyHandler.KeyHandler_pressedKeys[82]) {
            if(var8 <= 1715425487) {
               return;
            }

            var10001 = 1;
         } else {
            var10001 = 0;
         }

         var10000.method5522(var10001);
         packetWriter.method1622(var9);
      } else if(var2 == 2) {
         mouseCrossX = var6;
         mouseCrossY = var7;
         mouseCrossColor = 2;
         mouseCrossState = 0;
         destinationX = var0;
         destinationY = var1;
         var9 = InterfaceParent.method1140(ClientPacket.field2408, packetWriter.isaacCipher);
         var9.packetBuffer.writeInt(AttackOption.selectedSpellWidget);
         var9.packetBuffer.writeByte(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
         var9.packetBuffer.method5481(class304.baseY + var1);
         var9.packetBuffer.method5595(var3);
         var9.packetBuffer.method5481(var0 + class215.baseX);
         var9.packetBuffer.method5659(selectedSpellChildIndex);
         packetWriter.method1622(var9);
      } else if(var2 == 3) {
         mouseCrossX = var6;
         mouseCrossY = var7;
         mouseCrossColor = 2;
         mouseCrossState = 0;
         destinationX = var0;
         destinationY = var1;
         var9 = InterfaceParent.method1140(ClientPacket.field2391, packetWriter.isaacCipher);
         var9.packetBuffer.method5522(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
         var9.packetBuffer.method5481(var3);
         var9.packetBuffer.method5659(class304.baseY + var1);
         var9.packetBuffer.method5532(var0 + class215.baseX);
         packetWriter.method1622(var9);
      } else if(var2 == 4) {
         if(var8 <= 1715425487) {
            return;
         }

         mouseCrossX = var6;
         mouseCrossY = var7;
         mouseCrossColor = 2;
         mouseCrossState = 0;
         destinationX = var0;
         destinationY = var1;
         var9 = InterfaceParent.method1140(ClientPacket.field2346, packetWriter.isaacCipher);
         var9.packetBuffer.method5532(var3);
         var9.packetBuffer.method5481(var0 + class215.baseX);
         var10000 = var9.packetBuffer;
         if(KeyHandler.KeyHandler_pressedKeys[82]) {
            if(var8 <= 1715425487) {
               return;
            }

            var10001 = 1;
         } else {
            var10001 = 0;
         }

         var10000.method5515(var10001);
         var9.packetBuffer.method5659(class304.baseY + var1);
         packetWriter.method1622(var9);
      } else if(var2 == 5) {
         mouseCrossX = var6;
         mouseCrossY = var7;
         mouseCrossColor = 2;
         mouseCrossState = 0;
         destinationX = var0;
         destinationY = var1;
         var9 = InterfaceParent.method1140(ClientPacket.field2382, packetWriter.isaacCipher);
         var9.packetBuffer.method5481(var3);
         var9.packetBuffer.method5659(var0 + class215.baseX);
         var9.packetBuffer.method5532(class304.baseY + var1);
         var9.packetBuffer.method5521(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
         packetWriter.method1622(var9);
      } else if(var2 == 6) {
         mouseCrossX = var6;
         mouseCrossY = var7;
         mouseCrossColor = 2;
         mouseCrossState = 0;
         destinationX = var0;
         destinationY = var1;
         var9 = InterfaceParent.method1140(ClientPacket.field2422, packetWriter.isaacCipher);
         var9.packetBuffer.method5481(var0 + class215.baseX);
         var9.packetBuffer.method5522(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
         var9.packetBuffer.method5659(var3);
         var9.packetBuffer.method5659(class304.baseY + var1);
         packetWriter.method1622(var9);
      } else {
         PacketBufferNode var10;
         NPC var14;
         if(var2 == 7) {
            var14 = npcs[var3];
            if(var14 != null) {
               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var10 = InterfaceParent.method1140(ClientPacket.field2351, packetWriter.isaacCipher);
               var10.packetBuffer.method5595(class12.selectedItemWidget);
               var10.packetBuffer.method5532(AbstractWorldMapData.selectedItemId);
               var10.packetBuffer.method5659(var3);
               var10.packetBuffer.method5532(DevicePcmPlayerProvider.selectedItemSlot);
               var10.packetBuffer.method5515(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
               packetWriter.method1622(var10);
            }
         } else if(var2 == 8) {
            if(var8 <= 1715425487) {
               return;
            }

            var14 = npcs[var3];
            if(var14 != null) {
               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var10 = InterfaceParent.method1140(ClientPacket.field2416, packetWriter.isaacCipher);
               var10.packetBuffer.method5530(selectedSpellChildIndex);
               var10.packetBuffer.method5530(var3);
               var10.packetBuffer.method5595(AttackOption.selectedSpellWidget);
               var10.packetBuffer.writeByte(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
               packetWriter.method1622(var10);
            }
         } else if(var2 == 9) {
            var14 = npcs[var3];
            if(var14 != null) {
               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var10 = InterfaceParent.method1140(ClientPacket.field2429, packetWriter.isaacCipher);
               var10.packetBuffer.method5515(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
               var10.packetBuffer.method5530(var3);
               packetWriter.method1622(var10);
            }
         } else if(var2 == 10) {
            if(var8 <= 1715425487) {
               return;
            }

            var14 = npcs[var3];
            if(var14 != null) {
               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var10 = InterfaceParent.method1140(ClientPacket.field2436, packetWriter.isaacCipher);
               var10.packetBuffer.method5515(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
               var10.packetBuffer.method5659(var3);
               packetWriter.method1622(var10);
            }
         } else if(var2 == 11) {
            var14 = npcs[var3];
            if(var14 != null) {
               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var10 = InterfaceParent.method1140(ClientPacket.field2375, packetWriter.isaacCipher);
               var10.packetBuffer.writeByte(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
               var10.packetBuffer.method5530(var3);
               packetWriter.method1622(var10);
            }
         } else if(var2 == 12) {
            var14 = npcs[var3];
            if(var14 != null) {
               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var10 = InterfaceParent.method1140(ClientPacket.field2419, packetWriter.isaacCipher);
               var10.packetBuffer.writeByte(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
               var10.packetBuffer.method5530(var3);
               packetWriter.method1622(var10);
            }
         } else if(var2 == 13) {
            if(var8 <= 1715425487) {
               return;
            }

            var14 = npcs[var3];
            if(var14 != null) {
               if(var8 <= 1715425487) {
                  return;
               }

               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var10 = InterfaceParent.method1140(ClientPacket.field2377, packetWriter.isaacCipher);
               var10.packetBuffer.method5515(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
               var10.packetBuffer.method5530(var3);
               packetWriter.method1622(var10);
            }
         } else {
            Player var16;
            if(var2 == 14) {
               var16 = players[var3];
               if(var16 != null) {
                  mouseCrossX = var6;
                  mouseCrossY = var7;
                  mouseCrossColor = 2;
                  mouseCrossState = 0;
                  destinationX = var0;
                  destinationY = var1;
                  var10 = InterfaceParent.method1140(ClientPacket.field2399, packetWriter.isaacCipher);
                  var10.packetBuffer.method5532(var3);
                  var10.packetBuffer.method5684(class12.selectedItemWidget);
                  var10.packetBuffer.method5481(AbstractWorldMapData.selectedItemId);
                  var10.packetBuffer.method5530(DevicePcmPlayerProvider.selectedItemSlot);
                  var10.packetBuffer.writeByte(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
                  packetWriter.method1622(var10);
               }
            } else if(var2 == 15) {
               var16 = players[var3];
               if(var16 != null) {
                  mouseCrossX = var6;
                  mouseCrossY = var7;
                  mouseCrossColor = 2;
                  mouseCrossState = 0;
                  destinationX = var0;
                  destinationY = var1;
                  var10 = InterfaceParent.method1140(ClientPacket.field2395, packetWriter.isaacCipher);
                  var10.packetBuffer.method5530(var3);
                  var10.packetBuffer.method5659(selectedSpellChildIndex);
                  var10000 = var10.packetBuffer;
                  if(KeyHandler.KeyHandler_pressedKeys[82]) {
                     if(var8 <= 1715425487) {
                        return;
                     }

                     var10001 = 1;
                  } else {
                     var10001 = 0;
                  }

                  var10000.writeByte(var10001);
                  var10.packetBuffer.method5543(AttackOption.selectedSpellWidget);
                  packetWriter.method1622(var10);
               }
            } else if(var2 == 16) {
               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var9 = InterfaceParent.method1140(ClientPacket.field2353, packetWriter.isaacCipher);
               var9.packetBuffer.method5532(var3);
               var9.packetBuffer.writeInt(class12.selectedItemWidget);
               var9.packetBuffer.method5659(DevicePcmPlayerProvider.selectedItemSlot);
               var9.packetBuffer.method5532(class304.baseY + var1);
               var9.packetBuffer.method5515(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
               var9.packetBuffer.method5659(var0 + class215.baseX);
               var9.packetBuffer.method5659(AbstractWorldMapData.selectedItemId);
               packetWriter.method1622(var9);
            } else if(var2 == 17) {
               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var9 = InterfaceParent.method1140(ClientPacket.field2374, packetWriter.isaacCipher);
               var9.packetBuffer.method5659(class304.baseY + var1);
               var9.packetBuffer.method5595(AttackOption.selectedSpellWidget);
               var9.packetBuffer.method5532(var3);
               var9.packetBuffer.method5659(selectedSpellChildIndex);
               var9.packetBuffer.method5530(var0 + class215.baseX);
               var10000 = var9.packetBuffer;
               if(KeyHandler.KeyHandler_pressedKeys[82]) {
                  if(var8 <= 1715425487) {
                     return;
                  }

                  var10001 = 1;
               } else {
                  var10001 = 0;
               }

               var10000.method5521(var10001);
               packetWriter.method1622(var9);
            } else if(var2 == 18) {
               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var9 = InterfaceParent.method1140(ClientPacket.field2433, packetWriter.isaacCipher);
               var9.packetBuffer.method5515(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
               var9.packetBuffer.method5530(class304.baseY + var1);
               var9.packetBuffer.method5659(var0 + class215.baseX);
               var9.packetBuffer.method5532(var3);
               packetWriter.method1622(var9);
            } else if(var2 == 19) {
               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var9 = InterfaceParent.method1140(ClientPacket.field2361, packetWriter.isaacCipher);
               var10000 = var9.packetBuffer;
               if(KeyHandler.KeyHandler_pressedKeys[82]) {
                  if(var8 <= 1715425487) {
                     return;
                  }

                  var10001 = 1;
               } else {
                  var10001 = 0;
               }

               var10000.method5521(var10001);
               var9.packetBuffer.method5659(var0 + class215.baseX);
               var9.packetBuffer.method5659(var3);
               var9.packetBuffer.method5659(class304.baseY + var1);
               packetWriter.method1622(var9);
            } else if(var2 == 20) {
               if(var8 <= 1715425487) {
                  return;
               }

               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var9 = InterfaceParent.method1140(ClientPacket.field2396, packetWriter.isaacCipher);
               var9.packetBuffer.method5522(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
               var9.packetBuffer.method5532(var0 + class215.baseX);
               var9.packetBuffer.method5659(class304.baseY + var1);
               var9.packetBuffer.method5530(var3);
               packetWriter.method1622(var9);
            } else if(var2 == 21) {
               if(var8 <= 1715425487) {
                  return;
               }

               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var9 = InterfaceParent.method1140(ClientPacket.field2421, packetWriter.isaacCipher);
               var9.packetBuffer.method5532(var0 + class215.baseX);
               var9.packetBuffer.method5659(class304.baseY + var1);
               var10000 = var9.packetBuffer;
               if(KeyHandler.KeyHandler_pressedKeys[82]) {
                  if(var8 <= 1715425487) {
                     return;
                  }

                  var10001 = 1;
               } else {
                  var10001 = 0;
               }

               var10000.method5515(var10001);
               var9.packetBuffer.method5481(var3);
               packetWriter.method1622(var9);
            } else if(var2 == 22) {
               if(var8 <= 1715425487) {
                  return;
               }

               mouseCrossX = var6;
               mouseCrossY = var7;
               mouseCrossColor = 2;
               mouseCrossState = 0;
               destinationX = var0;
               destinationY = var1;
               var9 = InterfaceParent.method1140(ClientPacket.field2348, packetWriter.isaacCipher);
               var9.packetBuffer.method5530(var0 + class215.baseX);
               var9.packetBuffer.method5530(class304.baseY + var1);
               var9.packetBuffer.method5532(var3);
               var9.packetBuffer.method5515(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
               packetWriter.method1622(var9);
            } else if(var2 == 23) {
               if(isMenuOpen) {
                  if(var8 <= 1715425487) {
                     return;
                  }

                  PacketWriter.scene.method3152();
               } else {
                  PacketWriter.scene.method3151(WorldMapRectangle.plane, var0, var1, true);
               }
            } else {
               PacketBufferNode var11;
               Widget var17;
               if(var2 == 24) {
                  var17 = Canvas.getWidget(var1);
                  boolean var12 = true;
                  if(var17.contentType > 0) {
                     if(var8 <= 1715425487) {
                        return;
                     }

                     var12 = class22.method457(var17);
                  }

                  if(var12) {
                     var11 = InterfaceParent.method1140(ClientPacket.field2417, packetWriter.isaacCipher);
                     var11.packetBuffer.writeInt(var1);
                     packetWriter.method1622(var11);
                  }
               } else {
                  if(var2 == 25) {
                     var17 = GrandExchangeOfferWorldComparator.method93(var1, var0);
                     if(var17 != null) {
                        class214.method3938();
                        class19.method340(var1, var0, class12.method155(class12.method148(var17)), var17.itemId);
                        isItemSelected = 0;
                        selectedSpellActionName = VerticalAlignment.method4441(var17);
                        if(selectedSpellActionName == null) {
                           selectedSpellActionName = "null";
                        }

                        if(var17.isIf3) {
                           selectedSpellName = var17.dataText + World.method1251(16777215);
                        } else {
                           selectedSpellName = World.method1251(65280) + var17.spellName + World.method1251(16777215);
                        }
                     }

                     return;
                  }

                  if(var2 == 26) {
                     class37.method728();
                  } else {
                     int var13;
                     Widget var15;
                     if(var2 == 28) {
                        var9 = InterfaceParent.method1140(ClientPacket.field2417, packetWriter.isaacCipher);
                        var9.packetBuffer.writeInt(var1);
                        packetWriter.method1622(var9);
                        var15 = Canvas.getWidget(var1);
                        if(var15.cs1Instructions != null && var15.cs1Instructions[0][0] == 5) {
                           var13 = var15.cs1Instructions[0][1];
                           Varps.Varps_main[var13] = 1 - Varps.Varps_main[var13];
                           settingsChanged(var13);
                           NetSocket.method3457(var13);
                        }
                     } else if(var2 == 29) {
                        var9 = InterfaceParent.method1140(ClientPacket.field2417, packetWriter.isaacCipher);
                        var9.packetBuffer.writeInt(var1);
                        packetWriter.method1622(var9);
                        var15 = Canvas.getWidget(var1);
                        if(var15.cs1Instructions != null && var15.cs1Instructions[0][0] == 5) {
                           var13 = var15.cs1Instructions[0][1];
                           if(Varps.Varps_main[var13] != var15.cs1ComparisonValues[0]) {
                              Varps.Varps_main[var13] = var15.cs1ComparisonValues[0];
                              settingsChanged(var13);
                              NetSocket.method3457(var13);
                           }
                        }
                     } else if(var2 == 30) {
                        if(meslayerContinueWidget == null) {
                           Clock.method3518(var1, var0);
                           meslayerContinueWidget = GrandExchangeOfferWorldComparator.method93(var1, var0);
                           WorldMapSectionType.method116(meslayerContinueWidget);
                        }
                     } else if(var2 == 31) {
                        var9 = InterfaceParent.method1140(ClientPacket.field2425, packetWriter.isaacCipher);
                        var9.packetBuffer.method5684(var1);
                        var9.packetBuffer.method5532(DevicePcmPlayerProvider.selectedItemSlot);
                        var9.packetBuffer.writeInt(class12.selectedItemWidget);
                        var9.packetBuffer.method5481(AbstractWorldMapData.selectedItemId);
                        var9.packetBuffer.method5481(var0);
                        var9.packetBuffer.method5481(var3);
                        packetWriter.method1622(var9);
                        field958 = 0;
                        GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                        field951 = var0;
                     } else if(var2 == 32) {
                        var9 = InterfaceParent.method1140(ClientPacket.field2402, packetWriter.isaacCipher);
                        var9.packetBuffer.method5532(selectedSpellChildIndex);
                        var9.packetBuffer.method5530(var0);
                        var9.packetBuffer.method5530(var3);
                        var9.packetBuffer.method5684(var1);
                        var9.packetBuffer.method5543(AttackOption.selectedSpellWidget);
                        packetWriter.method1622(var9);
                        field958 = 0;
                        GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                        field951 = var0;
                     } else if(var2 == 33) {
                        if(var8 <= 1715425487) {
                           return;
                        }

                        var9 = InterfaceParent.method1140(ClientPacket.field2367, packetWriter.isaacCipher);
                        var9.packetBuffer.writeInt(var1);
                        var9.packetBuffer.method5530(var0);
                        var9.packetBuffer.method5530(var3);
                        packetWriter.method1622(var9);
                        field958 = 0;
                        GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                        field951 = var0;
                     } else if(var2 == 34) {
                        var9 = InterfaceParent.method1140(ClientPacket.field2411, packetWriter.isaacCipher);
                        var9.packetBuffer.writeInt(var1);
                        var9.packetBuffer.method5530(var0);
                        var9.packetBuffer.method5659(var3);
                        packetWriter.method1622(var9);
                        field958 = 0;
                        GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                        field951 = var0;
                     } else if(var2 == 35) {
                        var9 = InterfaceParent.method1140(ClientPacket.field2345, packetWriter.isaacCipher);
                        var9.packetBuffer.method5530(var3);
                        var9.packetBuffer.method5595(var1);
                        var9.packetBuffer.method5481(var0);
                        packetWriter.method1622(var9);
                        field958 = 0;
                        GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                        field951 = var0;
                     } else if(var2 == 36) {
                        var9 = InterfaceParent.method1140(ClientPacket.field2354, packetWriter.isaacCipher);
                        var9.packetBuffer.method5532(var3);
                        var9.packetBuffer.method5530(var0);
                        var9.packetBuffer.method5684(var1);
                        packetWriter.method1622(var9);
                        field958 = 0;
                        GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                        field951 = var0;
                     } else if(var2 == 37) {
                        var9 = InterfaceParent.method1140(ClientPacket.field2403, packetWriter.isaacCipher);
                        var9.packetBuffer.method5659(var0);
                        var9.packetBuffer.method5684(var1);
                        var9.packetBuffer.method5659(var3);
                        packetWriter.method1622(var9);
                        field958 = 0;
                        GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                        field951 = var0;
                     } else {
                        if(var2 == 38) {
                           class214.method3938();
                           var17 = Canvas.getWidget(var1);
                           isItemSelected = 1;
                           DevicePcmPlayerProvider.selectedItemSlot = var0;
                           class12.selectedItemWidget = var1;
                           AbstractWorldMapData.selectedItemId = var3;
                           WorldMapSectionType.method116(var17);
                           selectedItemName = World.method1251(16748608) + Occluder.getItemDefinition(var3).name + World.method1251(16777215);
                           if(selectedItemName == null) {
                              if(var8 <= 1715425487) {
                                 return;
                              }

                              selectedItemName = "null";
                           }

                           return;
                        }

                        if(var2 == 39) {
                           var9 = InterfaceParent.method1140(ClientPacket.field2373, packetWriter.isaacCipher);
                           var9.packetBuffer.method5659(var0);
                           var9.packetBuffer.method5481(var3);
                           var9.packetBuffer.method5543(var1);
                           packetWriter.method1622(var9);
                           field958 = 0;
                           GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                           field951 = var0;
                        } else if(var2 == 40) {
                           var9 = InterfaceParent.method1140(ClientPacket.field2428, packetWriter.isaacCipher);
                           var9.packetBuffer.method5684(var1);
                           var9.packetBuffer.method5659(var0);
                           var9.packetBuffer.method5532(var3);
                           packetWriter.method1622(var9);
                           field958 = 0;
                           GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                           field951 = var0;
                        } else if(var2 == 41) {
                           var9 = InterfaceParent.method1140(ClientPacket.field2406, packetWriter.isaacCipher);
                           var9.packetBuffer.method5530(var0);
                           var9.packetBuffer.method5543(var1);
                           var9.packetBuffer.method5530(var3);
                           packetWriter.method1622(var9);
                           field958 = 0;
                           GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                           field951 = var0;
                        } else if(var2 == 42) {
                           var9 = InterfaceParent.method1140(ClientPacket.field2415, packetWriter.isaacCipher);
                           var9.packetBuffer.method5481(var3);
                           var9.packetBuffer.method5481(var0);
                           var9.packetBuffer.method5684(var1);
                           packetWriter.method1622(var9);
                           field958 = 0;
                           GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                           field951 = var0;
                        } else if(var2 == 43) {
                           var9 = InterfaceParent.method1140(ClientPacket.field2438, packetWriter.isaacCipher);
                           var9.packetBuffer.method5481(var3);
                           var9.packetBuffer.method5543(var1);
                           var9.packetBuffer.method5481(var0);
                           packetWriter.method1622(var9);
                           field958 = 0;
                           GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                           field951 = var0;
                        } else if(var2 == 44) {
                           var16 = players[var3];
                           if(var16 != null) {
                              mouseCrossX = var6;
                              mouseCrossY = var7;
                              mouseCrossColor = 2;
                              mouseCrossState = 0;
                              destinationX = var0;
                              destinationY = var1;
                              var10 = InterfaceParent.method1140(ClientPacket.field2387, packetWriter.isaacCipher);
                              var10.packetBuffer.method5659(var3);
                              var10.packetBuffer.method5521(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
                              packetWriter.method1622(var10);
                           }
                        } else if(var2 == 45) {
                           var16 = players[var3];
                           if(var16 != null) {
                              mouseCrossX = var6;
                              mouseCrossY = var7;
                              mouseCrossColor = 2;
                              mouseCrossState = 0;
                              destinationX = var0;
                              destinationY = var1;
                              var10 = InterfaceParent.method1140(ClientPacket.field2383, packetWriter.isaacCipher);
                              var10.packetBuffer.method5532(var3);
                              var10000 = var10.packetBuffer;
                              if(KeyHandler.KeyHandler_pressedKeys[82]) {
                                 if(var8 <= 1715425487) {
                                    return;
                                 }

                                 var10001 = 1;
                              } else {
                                 var10001 = 0;
                              }

                              var10000.writeByte(var10001);
                              packetWriter.method1622(var10);
                           }
                        } else if(var2 == 46) {
                           if(var8 <= 1715425487) {
                              return;
                           }

                           var16 = players[var3];
                           if(var16 != null) {
                              mouseCrossX = var6;
                              mouseCrossY = var7;
                              mouseCrossColor = 2;
                              mouseCrossState = 0;
                              destinationX = var0;
                              destinationY = var1;
                              var10 = InterfaceParent.method1140(ClientPacket.field2362, packetWriter.isaacCipher);
                              var10.packetBuffer.method5515(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
                              var10.packetBuffer.method5530(var3);
                              packetWriter.method1622(var10);
                           }
                        } else if(var2 == 47) {
                           var16 = players[var3];
                           if(var16 != null) {
                              mouseCrossX = var6;
                              mouseCrossY = var7;
                              mouseCrossColor = 2;
                              mouseCrossState = 0;
                              destinationX = var0;
                              destinationY = var1;
                              var10 = InterfaceParent.method1140(ClientPacket.field2427, packetWriter.isaacCipher);
                              var10.packetBuffer.method5532(var3);
                              var10.packetBuffer.method5522(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
                              packetWriter.method1622(var10);
                           }
                        } else if(var2 == 48) {
                           var16 = players[var3];
                           if(var16 != null) {
                              mouseCrossX = var6;
                              mouseCrossY = var7;
                              mouseCrossColor = 2;
                              mouseCrossState = 0;
                              destinationX = var0;
                              destinationY = var1;
                              var10 = InterfaceParent.method1140(ClientPacket.field2398, packetWriter.isaacCipher);
                              var10.packetBuffer.method5522(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
                              var10.packetBuffer.method5530(var3);
                              packetWriter.method1622(var10);
                           }
                        } else if(var2 == 49) {
                           var16 = players[var3];
                           if(var16 != null) {
                              mouseCrossX = var6;
                              mouseCrossY = var7;
                              mouseCrossColor = 2;
                              mouseCrossState = 0;
                              destinationX = var0;
                              destinationY = var1;
                              var10 = InterfaceParent.method1140(ClientPacket.field2370, packetWriter.isaacCipher);
                              var10.packetBuffer.method5481(var3);
                              var10000 = var10.packetBuffer;
                              if(KeyHandler.KeyHandler_pressedKeys[82]) {
                                 if(var8 <= 1715425487) {
                                    return;
                                 }

                                 var10001 = 1;
                              } else {
                                 var10001 = 0;
                              }

                              var10000.method5521(var10001);
                              packetWriter.method1622(var10);
                           }
                        } else if(var2 == 50) {
                           var16 = players[var3];
                           if(var16 != null) {
                              mouseCrossX = var6;
                              mouseCrossY = var7;
                              mouseCrossColor = 2;
                              mouseCrossState = 0;
                              destinationX = var0;
                              destinationY = var1;
                              var10 = InterfaceParent.method1140(ClientPacket.field2430, packetWriter.isaacCipher);
                              var10.packetBuffer.method5530(var3);
                              var10000 = var10.packetBuffer;
                              if(KeyHandler.KeyHandler_pressedKeys[82]) {
                                 if(var8 <= 1715425487) {
                                    return;
                                 }

                                 var10001 = 1;
                              } else {
                                 var10001 = 0;
                              }

                              var10000.method5521(var10001);
                              packetWriter.method1622(var10);
                           }
                        } else if(var2 == 51) {
                           var16 = players[var3];
                           if(var16 != null) {
                              if(var8 <= 1715425487) {
                                 return;
                              }

                              mouseCrossX = var6;
                              mouseCrossY = var7;
                              mouseCrossColor = 2;
                              mouseCrossState = 0;
                              destinationX = var0;
                              destinationY = var1;
                              var10 = InterfaceParent.method1140(ClientPacket.field2418, packetWriter.isaacCipher);
                              var10.packetBuffer.method5521(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
                              var10.packetBuffer.method5659(var3);
                              packetWriter.method1622(var10);
                           }
                        } else {
                           label1035: {
                              if(var2 != 57) {
                                 if(var2 == 58) {
                                    var17 = GrandExchangeOfferWorldComparator.method93(var1, var0);
                                    if(var17 != null) {
                                       var10 = InterfaceParent.method1140(ClientPacket.field2392, packetWriter.isaacCipher);
                                       var10.packetBuffer.method5684(var1);
                                       var10.packetBuffer.method5481(var17.itemId);
                                       var10.packetBuffer.method5595(AttackOption.selectedSpellWidget);
                                       var10.packetBuffer.method5532(var0);
                                       var10.packetBuffer.method5659(selectedSpellChildIndex);
                                       var10.packetBuffer.method5659(field1001);
                                       packetWriter.method1622(var10);
                                    }
                                    break label1035;
                                 }

                                 if(var2 == 1001) {
                                    mouseCrossX = var6;
                                    mouseCrossY = var7;
                                    mouseCrossColor = 2;
                                    mouseCrossState = 0;
                                    destinationX = var0;
                                    destinationY = var1;
                                    var9 = InterfaceParent.method1140(ClientPacket.field2390, packetWriter.isaacCipher);
                                    var9.packetBuffer.method5481(class304.baseY + var1);
                                    var9.packetBuffer.method5481(var0 + class215.baseX);
                                    var9.packetBuffer.method5481(var3);
                                    var9.packetBuffer.method5521(KeyHandler.KeyHandler_pressedKeys[82]?1:0);
                                    packetWriter.method1622(var9);
                                    break label1035;
                                 }

                                 if(var2 == 1002) {
                                    mouseCrossX = var6;
                                    mouseCrossY = var7;
                                    mouseCrossColor = 2;
                                    mouseCrossState = 0;
                                    var9 = InterfaceParent.method1140(ClientPacket.field2376, packetWriter.isaacCipher);
                                    var9.packetBuffer.method5530(var3);
                                    packetWriter.method1622(var9);
                                    break label1035;
                                 }

                                 if(var2 == 1003) {
                                    mouseCrossX = var6;
                                    mouseCrossY = var7;
                                    mouseCrossColor = 2;
                                    mouseCrossState = 0;
                                    var14 = npcs[var3];
                                    if(var14 != null) {
                                       NPCDefinition var18 = var14.definition;
                                       if(var18.transforms != null) {
                                          var18 = var18.method4407();
                                       }

                                       if(var18 != null) {
                                          if(var8 <= 1715425487) {
                                             return;
                                          }

                                          var11 = InterfaceParent.method1140(ClientPacket.field2404, packetWriter.isaacCipher);
                                          var11.packetBuffer.method5659(var18.id);
                                          packetWriter.method1622(var11);
                                       }
                                    }
                                    break label1035;
                                 }

                                 if(var2 == 1004) {
                                    mouseCrossX = var6;
                                    mouseCrossY = var7;
                                    mouseCrossColor = 2;
                                    mouseCrossState = 0;

                                    var9 = InterfaceParent.method1140(ClientPacket.field2368, packetWriter.isaacCipher);
                                    var9.packetBuffer.method5530(var3);
                                    var9.packetBuffer.method5595(var1);
                                    var9.packetBuffer.method5481(var0);
                                    packetWriter.method1622(var9);
                                    break label1035;
                                 }

                                 if(var2 == 1005) {
                                    var17 = Canvas.getWidget(var1);
                                    if(var17 != null && var17.itemQuantities[var0] >= 100000) {
                                       class217.sendGameMessage(27, "", var17.itemQuantities[var0] + " x " + Occluder.getItemDefinition(var3).name);
                                    } else {
                                       var10 = InterfaceParent.method1140(ClientPacket.field2368, packetWriter.isaacCipher);
                                       var10.packetBuffer.method5530(var3);
                                       var10.packetBuffer.method5595(var1);
                                       var10.packetBuffer.method5481(var0);
                                       packetWriter.method1622(var10);
                                    }

                                    field958 = 0;
                                    GrandExchangeOfferOwnWorldComparator.field345 = Canvas.getWidget(var1);
                                    field951 = var0;
                                    break label1035;
                                 }

                                 if(var2 != 1007) {
                                    if(var2 != 1011 && var2 != 1009 && var2 != 1008 && var2 != 1010) {
                                       if(var2 != 1012) {
                                          break label1035;
                                       }

                                       if(var8 <= 1715425487) {
                                          return;
                                       }
                                    }

                                    Tiles.worldMap.method5926(var2, var3, new Coord(var0), new Coord(var1));
                                    break label1035;
                                 }
                              }

                              var17 = GrandExchangeOfferWorldComparator.method93(var1, var0);
                              if(var17 != null) {
                                 WorldMapSection1.method770(var3, var1, var0, var17.itemId, var5);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if(isItemSelected != 0) {
         isItemSelected = 0;
         WorldMapSectionType.method116(Canvas.getWidget(class12.selectedItemWidget));
      }

      if(isSpellSelected) {
         class214.method3938();
      }

      if(GrandExchangeOfferOwnWorldComparator.field345 != null && field958 == 0) {
         WorldMapSectionType.method116(GrandExchangeOfferOwnWorldComparator.field345);
      }

   }

   public static boolean vmExecuteOpcode(int var0) {
      if(var0 == 6599) {
         if(!$assertionsDisabled && currentScript.getInstructions()[currentScriptPC] != 6599) {
            throw new AssertionError();
         } else {
            int var1 = ViewportMouse.client.getStringStackSize();
            String[] var10000 = ViewportMouse.client.getStringStack();
            --var1;
            String var2 = var10000[var1];
            ViewportMouse.client.setStringStackSize(var1);
            if("debug".equals(var2)) {
               int var3 = ViewportMouse.client.getIntStackSize();
               var10000 = ViewportMouse.client.getStringStack();
               --var1;
               String var4 = var10000[var1];
               StringBuffer var5 = new StringBuffer();
               Matcher var6 = Pattern.compile("%(.)").matcher(var4);

               while(var6.find()) {
                  var6.appendReplacement(var5, "");
                  switch(var6.group(1).charAt(0)) {
                  case 'd':
                  case 'i':
                     int[] var8 = ViewportMouse.client.getIntStack();
                     --var3;
                     var5.append(var8[var3]);
                     break;
                  case 's':
                     String[] var10001 = ViewportMouse.client.getStringStack();
                     --var1;
                     var5.append(var10001[var1]);
                     break;
                  default:
                     var5.append(var6.group(0)).append("=unknown");
                  }
               }

               var6.appendTail(var5);
               ViewportMouse.client.getLogger().debug(var5.toString());
               ViewportMouse.client.setStringStackSize(var1);
               ViewportMouse.client.setIntStackSize(var3);
               return true;
            } else {
               ScriptCallbackEvent var7 = new ScriptCallbackEvent();
               var7.setScript(currentScript);
               var7.setEventName(var2);
               ViewportMouse.client.getCallbacks().post(ScriptCallbackEvent.class, var7);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void resizeChanged(int var0) {
      boolean var1 = ViewportMouse.client.isResized();
      if(var1 != oldIsResized) {
         ResizeableChanged var2 = new ResizeableChanged();
         var2.setResized(var1);
         ViewportMouse.client.getCallbacks().post(ResizeableChanged.class, var2);
         oldIsResized = var1;
      }

   }

   public static void settingsChanged(int var0) {
      VarbitChanged var1 = new VarbitChanged();
      var1.setIndex(var0);
      ViewportMouse.client.getCallbacks().post(VarbitChanged.class, var1);
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void draggingWidgetChanged(int var0) {
      DraggingWidgetChanged var1 = new DraggingWidgetChanged();
      var1.setDraggingWidget(ViewportMouse.client.isDraggingWidget());
      ViewportMouse.client.getCallbacks().post(DraggingWidgetChanged.class, var1);
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void onTempMenuActionChanged(int var0) {
      if(ViewportMouse.client.getTempMenuAction() != null) {
         ViewportMouse.client.getCallbacks().post(WidgetPressed.class, WidgetPressed.INSTANCE);
      }

   }

   public static void onGrandExchangeOffersChanged(int var0) {
      if(var0 != -1) {
         RSGrandExchangeOffer var1 = ViewportMouse.client.getGrandExchangeOffers()[var0];
         if(var1 != null) {
            GrandExchangeOfferChanged var2 = new GrandExchangeOfferChanged();
            var2.setOffer(var1);
            var2.setSlot(var0);
            ViewportMouse.client.getCallbacks().post(GrandExchangeOfferChanged.class, var2);
         }
      }
   }

   public static void updateNpcs(boolean var0, RSPacketBuffer var1) {
      ViewportMouse.client.getCallbacks().updateNpcs();
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void onCameraPitchTargetChanged(int var0) {
      int var1 = ViewportMouse.client.getCameraPitchTarget();
      int var2 = var1;
      if(pitchRelaxEnabled && lastPitchTarget > 383 && var1 == 383) {
         var2 = lastPitchTarget;
         if(var2 > 512) {
            var2 = 512;
         }

         ViewportMouse.client.setCameraPitchTarget(var2);
      }

      lastPitchTarget = var2;
   }

   public static final void copy$forceDisconnect(int var0, int var1) {
      DynamicObject.method1570();
      switch(var0) {
      case 1:
         Login.loginIndex = 24;
         PlayerType.method3939("", "You were disconnected from the server.", "");
         break;
      case 2:
         class11.method138();
      }

   }

   public static void playerOptionsChanged(int var0) {
      MenuOpcode[] var1 = new MenuOpcode[]{MenuOpcode.PLAYER_FIRST_OPTION, MenuOpcode.PLAYER_SECOND_OPTION, MenuOpcode.PLAYER_THIRD_OPTION, MenuOpcode.PLAYER_FOURTH_OPTION, MenuOpcode.PLAYER_FIFTH_OPTION, MenuOpcode.PLAYER_SIXTH_OPTION, MenuOpcode.PLAYER_SEVENTH_OPTION, MenuOpcode.PLAYER_EIGTH_OPTION};
      if(var0 >= 0 && var0 < var1.length) {
         MenuOpcode var2 = var1[var0];
         ViewportMouse.client.getPlayerMenuTypes()[var0] = var2.getId();
      }

      PlayerMenuOptionsChanged var3 = new PlayerMenuOptionsChanged();
      var3.setIndex(var0);
      ViewportMouse.client.getCallbacks().post(PlayerMenuOptionsChanged.class, var3);
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void clanMemberManagerChanged(int var0) {
      ViewportMouse.client.getCallbacks().post(ClanChanged.class, new ClanChanged(ViewportMouse.client.getClanMemberManager() != null));
   }

   public static void cachedNPCsChanged(int var0) {
      RSNPC[] var1 = ViewportMouse.client.getCachedNPCs();
      if(var0 >= 0 && var0 < var1.length) {
         RSNPC var2 = var1[var0];
         if(var2 != null) {
            var2.setIndex(var0);
            ViewportMouse.client.getCallbacks().postDeferred(NpcSpawned.class, new NpcSpawned(var2));
         }

      }
   }

   public static void copy$runWidgetOnLoadListener(int var0, int var1) {
      if(var0 != -1) {
         if(!WorldMapData_0.method171(var0)) {
            if(var1 < 1341374281) {
               ;
            }
         } else {
            Widget[] var2 = UserComparator5.Widget_interfaceComponents[var0];

            for(int var3 = 0; var3 < var2.length; ++var3) {
               Widget var4 = var2[var3];
               if (var4 == null) {
                  var4 = new Widget();
               }
               if(var4.onLoad != null) {
                  ScriptEvent var5 = new ScriptEvent();
                  var5.widget = var4;
                  var5.args = var4.onLoad;
                  KeyHandler.method506(var5, 5000000, (byte)0);
               }
            }

         }
      }
   }

   public static void copy$Rasterizer2D_fillRectangleGradientAlpha(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if(var2 > 0 && var3 > 0) {
         int var8 = 0;
         int var9 = 65536 / var3;
         if(var0 < Rasterizer2D.Rasterizer2D_xClipStart) {
            var2 -= Rasterizer2D.Rasterizer2D_xClipStart - var0;
            var0 = Rasterizer2D.Rasterizer2D_xClipStart;
         }

         if(var1 < Rasterizer2D.Rasterizer2D_yClipStart) {
            var8 += (Rasterizer2D.Rasterizer2D_yClipStart - var1) * var9;
            var3 -= Rasterizer2D.Rasterizer2D_yClipStart - var1;
            var1 = Rasterizer2D.Rasterizer2D_yClipStart;
         }

         if(var0 + var2 > Rasterizer2D.Rasterizer2D_xClipEnd) {
            var2 = Rasterizer2D.Rasterizer2D_xClipEnd - var0;
         }

         if(var3 + var1 > Rasterizer2D.Rasterizer2D_yClipEnd) {
            var3 = Rasterizer2D.Rasterizer2D_yClipEnd - var1;
         }

         int var10 = Rasterizer2D.Rasterizer2D_width - var2;
         int var11 = Rasterizer2D.Rasterizer2D_width * var1 + var0;

         for(int var12 = -var3; var12 < 0; ++var12) {
            int var13 = 65536 - var8 >> 8;
            int var14 = var8 >> 8;
            int var15 = (var13 * var6 + var14 * var7 & 65280) >>> 8;
            if(var15 == 0) {
               var11 += Rasterizer2D.Rasterizer2D_width;
               var8 += var9;
            } else {
               int var16 = (var13 * (var4 & 65280) + var14 * (var5 & 65280) & 16711680) + (var13 * (var4 & 16711935) + var14 * (var5 & 16711935) & -16711936) >>> 8;
               int var17 = 255 - var15;
               int var18 = ((var16 & 16711935) * var15 >> 8 & 16711935) + (var15 * (var16 & 65280) >> 8 & 65280);

               for(int var19 = -var2; var19 < 0; ++var19) {
                  int var20 = Rasterizer2D.Rasterizer2D_pixels[var11];
                  if(var20 == 0) {
                     Rasterizer2D.Rasterizer2D_pixels[var11++] = var18 | -16777216;
                  } else {
                     var20 = ((var20 & 16711935) * var17 >> 8 & 16711935) + (var17 * (var20 & 65280) >> 8 & 65280);
                     Rasterizer2D.Rasterizer2D_pixels[var11++] = var18 + var20 | -16777216;
                  }
               }

               var11 += var10;
               var8 += var9;
            }
         }

      }
   }

   public static void experiencedChanged(int var0) {
      ExperienceChanged var1 = new ExperienceChanged();
      Skill[] var2 = Skill.values();
      if(var0 < var2.length - 1) {
         Skill var3 = var2[var0];
         var1.setSkill(var3);
         ViewportMouse.client.getCallbacks().post(ExperienceChanged.class, var1);
      }

   }

   public static void boostedSkillLevelsChanged(int var0) {
      Skill[] var1 = Skill.values();
      if(var0 >= 0 && var0 < var1.length - 1) {
         Skill var2 = var1[var0];
         BoostedLevelChanged var3 = new BoostedLevelChanged();
         var3.setSkill(var2);
         ViewportMouse.client.getCallbacks().post(BoostedLevelChanged.class, var3);
      }

   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void onCameraPitchChanged(int var0) {
      int var1 = ViewportMouse.client.getCameraPitch();
      int var2 = var1;
      if(pitchRelaxEnabled && lastPitch > 383 && var1 == 383) {
         var2 = lastPitch;
         if(var2 > 512) {
            var2 = 512;
         }

         ViewportMouse.client.setCameraPitch(var2);
      }

      lastPitch = var2;
   }

   public static void cachedPlayersChanged(int var0) {
      RSPlayer[] var1 = ViewportMouse.client.getCachedPlayers();
      if(var0 >= 0 && var0 < var1.length) {
         RSPlayer var2 = var1[var0];
         RSPlayer var3 = oldPlayers[var0];
         oldPlayers[var0] = var2;
         if(var3 != null) {
            ViewportMouse.client.getCallbacks().post(PlayerDespawned.class, new PlayerDespawned(var3));
         }

         if(var2 != null) {
            ViewportMouse.client.getCallbacks().postDeferred(PlayerSpawned.class, new PlayerSpawned(var2));
         }

      }
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public static void onCycleCntrChanged(int var0) {
      ViewportMouse.client.getCallbacks().post(ClientTick.class, ClientTick.INSTANCE);
   }

   public static boolean shouldHideAttackOptionFor(RSPlayer var0) {
      return ViewportMouse.client.isSpellSelected()?(hideFriendCastOptions && var0.isFriended() || hideClanmateCastOptions && var0.isClanMember()) && !unhiddenCasts.contains(ViewportMouse.client.getSelectedSpellName().replaceAll("<[^>]*>", "").toLowerCase()):hideFriendAttackOptions && var0.isFriended() || hideClanmateAttackOptions && var0.isClanMember();
   }

   private static void rl$$clinit() {
      $assertionsDisabled = !Client.class.desiredAssertionStatus();
      oldPlayers = new Player[2048];
      hideDisconnect = false;
      hideFriendAttackOptions = false;
      hideClanmateAttackOptions = false;
      hideFriendCastOptions = false;
      hideClanmateCastOptions = false;
      unhiddenCasts = new HashSet();
      File folder = new File(MiscUtils.getPreferencesDir());
      File preferences = new File(folder, RuneLiteProperties.getKronosPreferences());
      try (FileReader fr = new FileReader(preferences)) {
         try (BufferedReader br = new BufferedReader(fr)) {
            field10222 = br.readLine();
         } catch (IOException exception) {
            exception.printStackTrace();
         }
      } catch (IOException exception) {
         exception.printStackTrace();
      }
   }

   private static void rl$$clinit1() {
      spriteOverrides = new HashMap();
      widgetSpriteOverrides = new HashMap();
   }

   private static void rl$$clinit2() {
      pitchRelaxEnabled = false;
      lastPitch = 128;
      lastPitchTarget = 128;
   }

   private static void rl$$clinit3() {
      $assertionsDisabled = !Client.class.desiredAssertionStatus();
   }

   private static void rl$$clinit4() {
      rl$modelViewportXs = new int[4700];
      rl$modelViewportYs = new int[4700];
   }

   private static void rl$$clinit5() {
      $assertionsDisabled = !Client.class.desiredAssertionStatus();
   }

   private static void rl$$clinit6() {
      hiddenNpcsName = new HashMap();
      hiddenNpcsDeath = new HashMap();
      hideSpecificPlayers = new ArrayList();
   }

   static void aaf() {
      if(field1087 && class215.localPlayer != null) {
         int var0 = class215.localPlayer.pathX[0];
         int var1 = class215.localPlayer.pathY[0];
         if(var0 < 0 || var1 < 0 || var0 >= 104 || var1 >= 104) {
            return;
         }

         ObjectSound.oculusOrbFocalPointX = class215.localPlayer.x;
         int var2 = MusicPatchPcmStream.method3798(class215.localPlayer.x * 1389855768, class215.localPlayer.y * -1163478977, WorldMapRectangle.plane) - camFollowHeight * 341280813;
         if(var2 < ModelData0.field1785 * -1351160427) {
            ModelData0.field1785 = var2 * 376443893;
         }

         class125.oculusOrbFocalPointY = class215.localPlayer.y * 682054857;
         field1087 = false;
      }

   }

   static void aaw() {
      if(field1087 && class215.localPlayer != null) {
         int var0 = class215.localPlayer.pathX[0];
         int var1 = class215.localPlayer.pathY[0];
         if(var0 < 0 || var1 < 0 || var0 >= 104 || var1 >= 104) {
            return;
         }

         ObjectSound.oculusOrbFocalPointX = class215.localPlayer.x;
         int var2 = MusicPatchPcmStream.method3798(class215.localPlayer.x, class215.localPlayer.y * 682054857, WorldMapRectangle.plane) - camFollowHeight * -844153885;
         if(var2 < ModelData0.field1785 * -1351160427) {
            ModelData0.field1785 = var2 * -506989123;
         }

         class125.oculusOrbFocalPointY = class215.localPlayer.y * 682054857;
         field1087 = false;
      }

   }

   static void aac() {
      if(field1087 && class215.localPlayer != null) {
         int var0 = class215.localPlayer.pathX[0];
         int var1 = class215.localPlayer.pathY[0];
         if(var0 < 0 || var1 < 0 || var0 >= 638955859 || var1 >= 104) {
            return;
         }

         ObjectSound.oculusOrbFocalPointX = class215.localPlayer.x;
         int var2 = MusicPatchPcmStream.method3798(class215.localPlayer.x * -646834471, class215.localPlayer.y * 1282427502, WorldMapRectangle.plane * -75268835) - camFollowHeight * -844153885;
         if(var2 < ModelData0.field1785 * -1351160427) {
            ModelData0.field1785 = var2 * -506989123;
         }

         class125.oculusOrbFocalPointY = class215.localPlayer.y * 471723272;
         field1087 = false;
      }

   }

   static void aad() {
      if(StudioGame.field2468 != null) {
         field1113 = cycle;
         StudioGame.field2468.method4231();

         for(int var0 = 0; var0 < players.length; ++var0) {
            if(players[var0] != null) {
               StudioGame.field2468.method4232((players[var0].x * 152504076 >> 7) + class215.baseX, (players[var0].y * 682054857 >> 7) + class304.baseY);
            }
         }
      }

   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "-1109826367"
   )
   static int method2082(int var0, Script var1, boolean var2) {
      Widget var3 = Canvas.getWidget(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
      if(var0 == 2600) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.scrollX;
         return 1;
      } else if(var0 == 2601) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.scrollY;
         return 1;
      } else if(var0 == 2602) {
         Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3.text;
         return 1;
      } else if(var0 == 2603) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.scrollWidth;
         return 1;
      } else if(var0 == 2604) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.scrollHeight;
         return 1;
      } else if(var0 == 2605) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.modelZoom;
         return 1;
      } else if(var0 == 2606) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.modelAngleX;
         return 1;
      } else if(var0 == 2607) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.modelAngleZ;
         return 1;
      } else if(var0 == 2608) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.modelAngleY;
         return 1;
      } else if(var0 == 2609) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.transparencyTop;
         return 1;
      } else if(var0 == 2610) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.transparencyBot;
         return 1;
      } else if(var0 == 2611) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.color;
         return 1;
      } else if(var0 == 2612) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.color2;
         return 1;
      } else if(var0 == 2613) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.fillMode.getId();
         return 1;
      } else if(var0 == 2614) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.modelTransparency?1:0;
         return 1;
      } else {
         return 2;
      }
   }
}
