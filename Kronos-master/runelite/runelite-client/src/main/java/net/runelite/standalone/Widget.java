package net.runelite.standalone;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import net.runelite.api.Point;
import net.runelite.api.WidgetNode;
import net.runelite.api.events.WidgetHiddenChanged;
import net.runelite.api.events.WidgetPositioned;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSAbstractFont;
import net.runelite.rs.api.RSNode;
import net.runelite.rs.api.RSNodeHashTable;
import net.runelite.rs.api.RSSprite;
import net.runelite.rs.api.RSWidget;

@ObfuscatedName("ho")
public class Widget extends Node implements RSWidget {

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable Widget_cachedSprites;
   @ObfuscatedName("t")
   public static boolean field2576;
   @ObfuscatedName("w")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable Widget_cachedSpriteMasks;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   static AbstractArchive Widget_archive;
   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable Widget_cachedModels;
   // $FF: synthetic field
   public static boolean $assertionsDisabled;
   public static int rl$widgetLastPosChanged;
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable Widget_cachedFonts;
   @ObfuscatedName("s")
   @ObfuscatedGetter(
      intValue = 1047333541
   )
   public int type;
   @ObfuscatedName("x")
   @ObfuscatedGetter(
      intValue = -938462923
   )
   public int id;
   @ObfuscatedName("aa")
   @ObfuscatedGetter(
      intValue = -1720123363
   )
   public int rawWidth;
   @ObfuscatedName("ab")
   @ObfuscatedGetter(
      intValue = -2047854973
   )
   public int y;
   @ObfuscatedName("ac")
   @ObfuscatedGetter(
      intValue = 998396021
   )
   public int heightAlignment;
   @ObfuscatedName("ad")
   @ObfuscatedGetter(
      intValue = 1499631501
   )
   public int parentId;
   @ObfuscatedName("ae")
   @ObfuscatedGetter(
      intValue = -1588056221
   )
   public int color;
   @ObfuscatedName("af")
   @ObfuscatedGetter(
      intValue = -1279506063
   )
   public int transparencyBot;
   @ObfuscatedName("ag")
   @ObfuscatedGetter(
      intValue = -214954083
   )
   public int mouseOverColor2;
   @ObfuscatedName("ah")
   @ObfuscatedGetter(
      intValue = 1010030427
   )
   public int mouseOverColor;
   @ObfuscatedName("ai")
   @ObfuscatedGetter(
      intValue = 1553708193
   )
   public int scrollX;
   @ObfuscatedName("aj")
   public boolean fill;
   @ObfuscatedName("ak")
   @ObfuscatedGetter(
      intValue = -1983133743
   )
   public int scrollY;
   @ObfuscatedName("al")
   @ObfuscatedGetter(
      intValue = 540658479
   )
   public int field2662;
   @ObfuscatedName("am")
   @ObfuscatedGetter(
      intValue = 319178445
   )
   public int scrollHeight;
   @ObfuscatedName("an")
   public boolean isHidden;
   @ObfuscatedName("ao")
   @ObfuscatedGetter(
      intValue = 2031946853
   )
   public int field2688;
   @ObfuscatedName("ap")
   @ObfuscatedGetter(
      intValue = 450063905
   )
   public int rawHeight;
   @ObfuscatedName("aq")
   @ObfuscatedGetter(
      intValue = 993795721
   )
   public int scrollWidth;
   @ObfuscatedName("ar")
   @ObfuscatedGetter(
      intValue = 654358485
   )
   public int x;
   @ObfuscatedName("as")
   @ObfuscatedGetter(
      intValue = 185246961
   )
   public int height;
   @ObfuscatedName("at")
   @ObfuscatedGetter(
      intValue = 1520637795
   )
   public int lineWid;
   @ObfuscatedName("au")
   @ObfuscatedGetter(
      intValue = -1182281851
   )
   public int transparencyTop;
   @ObfuscatedName("av")
   @ObfuscatedGetter(
      intValue = -1094977017
   )
   public int color2;
   @ObfuscatedName("aw")
   @ObfuscatedGetter(
      intValue = 1824790061
   )
   public int rawY;
   @ObfuscatedName("ax")
   @ObfuscatedGetter(
      intValue = -1115212787
   )
   public int width;
   @ObfuscatedName("ay")
   @ObfuscatedSignature(
      signature = "Llu;"
   )
   public FillMode fillMode;
   @ObfuscatedName("az")
   @ObfuscatedGetter(
      intValue = -1791986137
   )
   public int rawX;
   @ObfuscatedName("ba")
   @ObfuscatedGetter(
      intValue = 352337217
   )
   public int modelId;
   @ObfuscatedName("bb")
   @ObfuscatedGetter(
      intValue = -617034503
   )
   public int modelOffsetX;
   @ObfuscatedName("bc")
   public boolean spriteFlipH;
   @ObfuscatedName("bd")
   @ObfuscatedGetter(
      intValue = -842194727
   )
   public int spriteId;
   @ObfuscatedName("be")
   @ObfuscatedGetter(
      intValue = -1849612505
   )
   int modelId2;
   @ObfuscatedName("bf")
   @ObfuscatedGetter(
      intValue = 820815763
   )
   public int field2634;
   @ObfuscatedName("bg")
   public boolean modelOrthog;
   @ObfuscatedName("bh")
   @ObfuscatedGetter(
      intValue = -660582185
   )
   public int spriteId2;
   @ObfuscatedName("bi")
   @ObfuscatedGetter(
      intValue = -803672091
   )
   public int sequenceId2;
   @ObfuscatedName("bj")
   @ObfuscatedGetter(
      intValue = -908982873
   )
   public int outline;
   @ObfuscatedName("bk")
   @ObfuscatedGetter(
      intValue = -917602037
   )
   public int modelType;
   @ObfuscatedName("bl")
   @ObfuscatedGetter(
      intValue = -516131825
   )
   public int field2700;
   @ObfuscatedName("bm")
   @ObfuscatedGetter(
      intValue = 1539845703
   )
   public int spriteAngle;
   @ObfuscatedName("bn")
   @ObfuscatedGetter(
      intValue = -1558533019
   )
   int modelType2;
   @ObfuscatedName("bo")
   @ObfuscatedGetter(
      intValue = 1532699993
   )
   public int modelAngleZ;
   @ObfuscatedName("bp")
   @ObfuscatedGetter(
      intValue = 1908099759
   )
   public int itemQuantityMode;
   @ObfuscatedName("bq")
   @ObfuscatedGetter(
      intValue = 744470751
   )
   public int modelAngleY;
   @ObfuscatedName("br")
   @ObfuscatedGetter(
      intValue = -1859343581
   )
   public int modelZoom;
   @ObfuscatedName("bs")
   @ObfuscatedGetter(
      intValue = 235051931
   )
   public int spriteShadow;
   @ObfuscatedName("bt")
   @ObfuscatedGetter(
      intValue = 263647227
   )
   public int modelOffsetY;
   @ObfuscatedName("bu")
   @ObfuscatedGetter(
      intValue = -1921357951
   )
   public int sequenceId;
   @ObfuscatedName("bv")
   public boolean spriteTiling;
   @ObfuscatedName("bw")
   public boolean modelTransparency;
   @ObfuscatedName("bx")
   public boolean field2612;
   @ObfuscatedName("by")
   @ObfuscatedGetter(
      intValue = -899088195
   )
   public int modelAngleX;
   @ObfuscatedName("bz")
   public boolean spriteFlipV;
   @ObfuscatedName("ca")
   public int[] field2619;
   @ObfuscatedName("cb")
   public int[] inventoryYOffsets;
   @ObfuscatedName("cc")
   public byte[][] field2654;
   @ObfuscatedName("cd")
   @ObfuscatedGetter(
      intValue = 488082575
   )
   public int paddingX;
   @ObfuscatedName("ce")
   public byte[][] field2585;
   @ObfuscatedName("cf")
   public int[] field2581;
   @ObfuscatedName("cg")
   @ObfuscatedGetter(
      intValue = -2027130901
   )
   public int textYAlignment;
   @ObfuscatedName("ci")
   @ObfuscatedGetter(
      intValue = -349261461
   )
   public int clickMask;
   @ObfuscatedName("cj")
   public int[] inventoryXOffsets;
   @ObfuscatedName("ck")
   public String text;
   public int rl$x;
   public int rl$y;
   public int rl$parentId;
   @ObfuscatedName("cn")
   public boolean textShadowed;
   @ObfuscatedName("co")
   @ObfuscatedGetter(
      intValue = -1471970549
   )
   public int textLineHeight;
   @ObfuscatedName("cr")
   public int[] inventorySprites;
   @ObfuscatedName("cs")
   @ObfuscatedGetter(
      intValue = 275090821
   )
   public int textXAlignment;
   @ObfuscatedName("ct")
   public String[] itemActions;
   @ObfuscatedName("cu")
   public boolean field2652;
   @ObfuscatedName("cv")
   public String text2;
   @ObfuscatedName("cy")
   @ObfuscatedGetter(
      intValue = 1814601209
   )
   public int paddingY;
   @ObfuscatedName("cz")
   @ObfuscatedGetter(
      intValue = 8724653
   )
   public int fontId;
   @ObfuscatedName("d")
   @ObfuscatedGetter(
      intValue = -785941721
   )
   public int xAlignment;
   @ObfuscatedName("da")
   public int[] varTransmitTriggers;
   @ObfuscatedName("db")
   public Object[] onStatTransmit;
   @ObfuscatedName("dc")
   public Object[] onRelease;
   @ObfuscatedName("dd")
   public Object[] onTargetEnter;
   @ObfuscatedName("de")
   public String spellActionName;
   @ObfuscatedName("df")
   public boolean hasListener;
   @ObfuscatedName("dg")
   public Object[] onVarTransmit;
   @ObfuscatedName("dh")
   public int[] invTransmitTriggers;
   @ObfuscatedName("di")
   public Object[] onHold;
   @ObfuscatedName("dj")
   public Object[] onTargetLeave;
   @ObfuscatedName("dk")
   public Object[] onMouseOver;
   @ObfuscatedName("dl")
   public Object[] onInvTransmit;
   @ObfuscatedName("dm")
   @ObfuscatedSignature(
      signature = "Lho;"
   )
   public Widget parent;
   @ObfuscatedName("dn")
   @ObfuscatedGetter(
      intValue = 613508017
   )
   public int dragZoneSize;
   @ObfuscatedName("do")
   public Object[] onDrag;
   @ObfuscatedName("dp")
   public int[] statTransmitTriggers;
   @ObfuscatedName("dq")
   @ObfuscatedGetter(
      intValue = -1263499789
   )
   public int dragThreshold;
   @ObfuscatedName("dr")
   public Object[] onLoad;
   @ObfuscatedName("ds")
   public Object[] onClickRepeat;
   @ObfuscatedName("dt")
   public String dataText;
   @ObfuscatedName("du")
   public Object[] onClick;
   @ObfuscatedName("dv")
   public String[] actions;
   @ObfuscatedName("dw")
   public Object[] onDragComplete;
   @ObfuscatedName("dx")
   public Object[] onMouseRepeat;
   @ObfuscatedName("dy")
   public boolean isScrollBar;
   @ObfuscatedName("dz")
   public Object[] onMouseLeave;
   @ObfuscatedName("ea")
   @ObfuscatedGetter(
      intValue = 758841865
   )
   public int modelFrame;
   @ObfuscatedName("eb")
   @ObfuscatedSignature(
      signature = "[Lho;"
   )
   public Widget[] children;
   @ObfuscatedName("ec")
   public String buttonText;
   @ObfuscatedName("ed")
   public int[] itemQuantities;
   public String[][] itemAttributes;
   @ObfuscatedName("ee")
   public Object[] onMiscTransmit;
   @ObfuscatedName("ef")
   public Object[] onOp;
   @ObfuscatedName("eg")
   public Object[] onStockTransmit;
   @ObfuscatedName("eh")
   public int[] cs1Comparisons;
   @ObfuscatedName("ei")
   public Object[] onSubChange;
   @ObfuscatedName("ej")
   public Object[] field2695;
   @ObfuscatedName("ek")
   public Object[] onTimer;
   @ObfuscatedName("el")
   @ObfuscatedGetter(
      intValue = 878258425
   )
   public int itemQuantity;
   @ObfuscatedName("em")
   public int[] cs1ComparisonValues;
   @ObfuscatedName("en")
   public int[][] cs1Instructions;
   @ObfuscatedName("eo")
   public Object[] onClanTransmit;
   @ObfuscatedName("ep")
   public Object[] onDialogAbort;
   @ObfuscatedName("eq")
   public Object[] onKey;
   @ObfuscatedName("er")
   public Object[] onFriendTransmit;
   @ObfuscatedName("es")
   public Object[] onResize;
   @ObfuscatedName("et")
   @ObfuscatedGetter(
      intValue = 2093634115
   )
   public int modelFrameCycle;
   @ObfuscatedName("eu")
   public int[] itemIds;
   @ObfuscatedName("ev")
   @ObfuscatedGetter(
      intValue = 1603621761
   )
   public int mouseOverRedirect;
   @ObfuscatedName("ew")
   public Object[] onScroll;
   @ObfuscatedName("ex")
   public Object[] onChatTransmit;
   @ObfuscatedName("ey")
   @ObfuscatedGetter(
      intValue = -1532577883
   )
   public int itemId;
   @ObfuscatedName("ez")
   public String spellName;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = -356920675
   )
   public int buttonType;
   @ObfuscatedName("fb")
   public boolean containsMouse;
   @ObfuscatedName("fc")
   public int[] field2717;
   @ObfuscatedName("fd")
   @ObfuscatedGetter(
      intValue = 1204314605
   )
   public int field2591;
   @ObfuscatedName("fe")
   public boolean isClicked;
   @ObfuscatedName("ff")
   @ObfuscatedGetter(
      intValue = 976944717
   )
   public int field2713;
   @ObfuscatedName("fh")
   public boolean field2720;
   @ObfuscatedName("fk")
   @ObfuscatedGetter(
      intValue = -27098335
   )
   public int rootIndex;
   @ObfuscatedName("fm")
   public boolean noScrollThrough;
   @ObfuscatedName("fo")
   @ObfuscatedGetter(
      intValue = 1042446467
   )
   public int field2714;
   @ObfuscatedName("fq")
   @ObfuscatedGetter(
      intValue = -433990399
   )
   public int cycle;
   @ObfuscatedName("fv")
   public boolean noClickThrough;
   @ObfuscatedName("fy")
   @ObfuscatedGetter(
      intValue = -162526605
   )
   public int field2653;
   @ObfuscatedName("g")
   public boolean isIf3;
   @ObfuscatedName("h")
   @ObfuscatedGetter(
      intValue = 970038855
   )
   public int childIndex;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = -645037763
   )
   public int contentType;
   @ObfuscatedName("k")
   @ObfuscatedGetter(
      intValue = -1296359215
   )
   public int widthAlignment;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = 2044954067
   )
   public int yAlignment;

   static {
      Widget_cachedSprites = new EvictingDualNodeHashTable(200);
      Widget_cachedModels = new EvictingDualNodeHashTable(50);
      Widget_cachedFonts = new EvictingDualNodeHashTable(20);
      Widget_cachedSpriteMasks = new EvictingDualNodeHashTable(8);
      field2576 = false;
      rl$$clinit();
   }

   public Widget() {
      this.isIf3 = false;
      this.id = -1;
      this.childIndex = -1;
      this.buttonType = 0;
      this.contentType = 0;
      this.xAlignment = 0;
      this.yAlignment = 0;
      this.widthAlignment = 0;
      this.heightAlignment = 0;
      this.rawX = 0;
      this.rawY = 0;
      this.rawWidth = 0;
      this.rawHeight = 0;
      this.x = 0;
      this.y = 0;
      this.onPositionChanged(-1);
      this.width = 0;
      this.height = 0;
      this.field2688 = 1;
      this.field2662 = 1;
      this.parentId = -1;
      this.isHidden = false;
      this.onHiddenChanged(-1);
      this.scrollX = 0;
      this.scrollY = 0;
      this.scrollWidth = 0;
      this.scrollHeight = 0;
      this.color = 0;
      this.color2 = 0;
      this.mouseOverColor = 0;
      this.mouseOverColor2 = 0;
      this.fill = false;
      this.fillMode = FillMode.SOLID;
      this.transparencyTop = 0;
      this.transparencyBot = 0;
      this.lineWid = 1;
      this.field2612 = false;
      this.spriteId2 = -1;
      this.spriteId = -1;
      this.spriteAngle = 0;
      this.spriteTiling = false;
      this.outline = 0;
      this.spriteShadow = 0;
      this.modelType = 1;
      this.modelId = -1;
      this.modelType2 = 1;
      this.modelId2 = -1;
      this.sequenceId = -1;
      this.sequenceId2 = -1;
      this.modelOffsetX = 0;
      this.modelOffsetY = 0;
      this.modelAngleX = 0;
      this.modelAngleY = 0;
      this.modelAngleZ = 0;
      this.modelZoom = 100;
      this.field2700 = 0;
      this.field2634 = 0;
      this.modelOrthog = false;
      this.modelTransparency = false;
      this.itemQuantityMode = 2;
      this.fontId = -1;
      this.text = "";
      this.text2 = "";
      this.textLineHeight = 0;
      this.textXAlignment = 0;
      this.textYAlignment = 0;
      this.textShadowed = false;
      this.paddingX = 0;
      this.paddingY = 0;
      this.clickMask = 0;
      this.field2652 = false;
      this.dataText = "";
      this.parent = null;
      this.dragZoneSize = 0;
      this.dragThreshold = 0;
      this.isScrollBar = false;
      this.spellActionName = "";
      this.hasListener = false;
      this.mouseOverRedirect = -1;
      this.spellName = "";
      this.buttonText = "Ok";
      this.itemId = -1;
      this.itemQuantity = 0;
      this.modelFrame = 0;
      this.modelFrameCycle = 0;
      this.containsMouse = false;
      this.isClicked = false;
      this.field2591 = -1;
      this.field2653 = 0;
      this.field2713 = 0;
      this.field2714 = 0;
      this.rootIndex = -1;
      this.cycle = -1;
      this.noClickThrough = false;
      this.noScrollThrough = false;
      this.field2720 = false;
      this.rl$$init();
   }

    @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(II)Llf;",
      garbageValue = "378738987"
   )
   public Sprite method3973(int var1) {
      field2576 = false;
      if(var1 >= 0 && var1 < this.inventorySprites.length) {
         int var2 = this.inventorySprites[var1];
         if(var2 == -1) {
            return null;
         } else {
            Sprite var3 = (Sprite)Widget_cachedSprites.method3032((long)var2);
            if(var3 != null) {
               return var3;
            } else {
               var3 = NPCDefinition.method4417(ClientPreferences.Widget_spritesArchive, var2, 0, -1092680498);
               if(var3 != null) {
                  Widget_cachedSprites.method3034(var3, (long)var2);
               } else {
                  field2576 = true;
               }

               return var3;
            }
         }
      } else {
         return null;
      }
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Lkl;S)V",
      garbageValue = "15127"
   )
   void method3966(Buffer var1) {
      this.isIf3 = false;
      this.type = var1.readUnsignedByte();
      this.buttonType = var1.readUnsignedByte();
      this.contentType = var1.readUnsignedShort();
      this.rawX = var1.g2s();
      this.rawY = var1.g2s();
      this.rawWidth = var1.readUnsignedShort();
      this.rawHeight = var1.readUnsignedShort();
      this.transparencyTop = var1.readUnsignedByte();
      this.parentId = var1.readUnsignedShort();
      if(this.parentId == 65535) {
         this.parentId = -1;
      } else {
         this.parentId += this.id & -65536;
      }

      this.mouseOverRedirect = var1.readUnsignedShort();
      if(this.mouseOverRedirect == 65535) {
         this.mouseOverRedirect = -1;
      }

      int var2 = var1.readUnsignedByte();
      int var3;
      if(var2 > 0) {
         this.cs1Comparisons = new int[var2];
         this.cs1ComparisonValues = new int[var2];

         for(var3 = 0; var3 < var2; ++var3) {
            this.cs1Comparisons[var3] = var1.readUnsignedByte();
            this.cs1ComparisonValues[var3] = var1.readUnsignedShort();
         }
      }

      var3 = var1.readUnsignedByte();
      int var4;
      int var5;
      int var6;
      if(var3 > 0) {
         this.cs1Instructions = new int[var3][];

         for(var4 = 0; var4 < var3; ++var4) {
            var5 = var1.readUnsignedShort();
            this.cs1Instructions[var4] = new int[var5];

            for(var6 = 0; var6 < var5; ++var6) {
               this.cs1Instructions[var4][var6] = var1.readUnsignedShort();
               if(this.cs1Instructions[var4][var6] == 65535) {
                  this.cs1Instructions[var4][var6] = -1;
               }
            }
         }
      }

      if(this.type == 0) {
         this.scrollHeight = var1.readUnsignedShort();
         this.isHidden = var1.readUnsignedByte() == 1;
         this.onHiddenChanged(-1);
      }

      if(this.type == 1) {
         var1.readUnsignedShort();
         var1.readUnsignedByte();
      }

      if(this.type == 2) {
         this.itemIds = new int[this.rawWidth * this.rawHeight];
         this.itemQuantities = new int[this.rawHeight * this.rawWidth];
         this.itemAttributes = new String[this.rawHeight * this.rawWidth][3];
         var4 = var1.readUnsignedByte();
         if(var4 == 1) {
            this.clickMask |= 268435456;
         }

         var5 = var1.readUnsignedByte();
         if(var5 == 1) {
            this.clickMask |= 1073741824;
         }

         var6 = var1.readUnsignedByte();
         if(var6 == 1) {
            this.clickMask |= Integer.MIN_VALUE;
         }

         int var7 = var1.readUnsignedByte();
         if(var7 == 1) {
            this.clickMask |= 536870912;
         }

         this.paddingX = var1.readUnsignedByte();
         this.paddingY = var1.readUnsignedByte();
         this.inventoryXOffsets = new int[20];
         this.inventoryYOffsets = new int[20];
         this.inventorySprites = new int[20];

         int var8;
         for(var8 = 0; var8 < 20; ++var8) {
            int var9 = var1.readUnsignedByte();
            if(var9 == 1) {
               this.inventoryXOffsets[var8] = var1.g2s();
               this.inventoryYOffsets[var8] = var1.g2s();
               this.inventorySprites[var8] = var1.readInt();
            } else {
               this.inventorySprites[var8] = -1;
            }
         }

         this.itemActions = new String[5];

         for(var8 = 0; var8 < 5; ++var8) {
            String var11 = var1.readString();
            if(var11.length() > 0) {
               this.itemActions[var8] = var11;
               this.clickMask |= 1 << var8 + 23;
            }
         }
      }

      if(this.type == 3) {
         this.fill = var1.readUnsignedByte() == 1;
      }

      if(this.type == 4 || this.type == 1) {
         this.textXAlignment = var1.readUnsignedByte();
         this.textYAlignment = var1.readUnsignedByte();
         this.textLineHeight = var1.readUnsignedByte();
         this.fontId = var1.readUnsignedShort();
         if(this.fontId == 65535) {
            this.fontId = -1;
         }

         this.textShadowed = var1.readUnsignedByte() == 1;
      }

      if(this.type == 4) {
         this.text = var1.readString();
         this.text2 = var1.readString();
      }

      if(this.type == 1 || this.type == 3 || this.type == 4) {
         this.color = var1.readInt();
      }

      if(this.type == 3 || this.type == 4) {
         this.color2 = var1.readInt();
         this.mouseOverColor = var1.readInt();
         this.mouseOverColor2 = var1.readInt();
      }

      if(this.type == 5) {
         this.spriteId2 = var1.readInt();
         this.spriteId = var1.readInt();
      }

      if(this.type == 6) {
         this.modelType = 1;
         this.modelId = var1.readUnsignedShort();
         if(this.modelId == 65535) {
            this.modelId = -1;
         }

         this.modelType2 = 1;
         this.modelId2 = var1.readUnsignedShort();
         if(this.modelId2 == 65535) {
            this.modelId2 = -1;
         }

         this.sequenceId = var1.readUnsignedShort();
         if(this.sequenceId == 65535) {
            this.sequenceId = -1;
         }

         this.sequenceId2 = var1.readUnsignedShort();
         if(this.sequenceId2 == 65535) {
            this.sequenceId2 = -1;
         }

         this.modelZoom = var1.readUnsignedShort();
         this.modelAngleX = var1.readUnsignedShort();
         this.modelAngleY = var1.readUnsignedShort();
      }

      if(this.type == 7) {
         this.itemIds = new int[this.rawHeight * this.rawWidth];
         this.itemQuantities = new int[this.rawWidth * this.rawHeight];
         this.itemAttributes = new String[this.rawWidth * this.rawHeight][3];
         this.textXAlignment = var1.readUnsignedByte();
         this.fontId = var1.readUnsignedShort();
         if(this.fontId == 65535) {
            this.fontId = -1;
         }

         this.textShadowed = var1.readUnsignedByte() == 1;
         this.color = var1.readInt();
         this.paddingX = var1.g2s();
         this.paddingY = var1.g2s();
         var4 = var1.readUnsignedByte();
         if(var4 == 1) {
            this.clickMask |= 1073741824;
         }

         this.itemActions = new String[5];

         for(var5 = 0; var5 < 5; ++var5) {
            String var10 = var1.readString();
            if(var10.length() > 0) {
               this.itemActions[var5] = var10;
               this.clickMask |= 1 << var5 + 23;
            }
         }
      }

      if(this.type == 8) {
         this.text = var1.readString();
      }

      if(this.buttonType == 2 || this.type == 2) {
         this.spellActionName = var1.readString();
         this.spellName = var1.readString();
         var4 = var1.readUnsignedShort() & 63;
         this.clickMask |= var4 << 11;
      }

      if(this.buttonType == 1 || this.buttonType == 4 || this.buttonType == 5 || this.buttonType == 6) {
         this.buttonText = var1.readString();
         if(this.buttonText.length() == 0) {
            if(this.buttonType == 1) {
               this.buttonText = "Ok";
            }

            if(this.buttonType == 4) {
               this.buttonText = "Select";
            }

            if(this.buttonType == 5) {
               this.buttonText = "Select";
            }

            if(this.buttonType == 6) {
               this.buttonText = "Continue";
            }
         }
      }

      if(this.buttonType == 1 || this.buttonType == 4 || this.buttonType == 5) {
         this.clickMask |= 4194304;
      }

      if(this.buttonType == 6) {
         this.clickMask |= 1;
      }

   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(Lkl;I)V",
      garbageValue = "-508997564"
   )
   void method4001(Buffer var1) {
      var1.readUnsignedByte();
      this.isIf3 = true;
      this.type = var1.readUnsignedByte();
      this.contentType = var1.readUnsignedShort();
      this.rawX = var1.g2s();
      this.rawY = var1.g2s();
      this.rawWidth = var1.readUnsignedShort();
      if(this.type == 9) {
         this.rawHeight = var1.g2s();
      } else {
         this.rawHeight = var1.readUnsignedShort();
      }

      this.widthAlignment = var1.readByte();
      this.heightAlignment = var1.readByte();
      this.xAlignment = var1.readByte();
      this.yAlignment = var1.readByte();
      this.parentId = var1.readUnsignedShort();
      if(this.parentId == 65535) {
         this.parentId = -1;
      } else {
         this.parentId += this.id & -65536;
      }

      this.isHidden = var1.readUnsignedByte() == 1;
      this.onHiddenChanged(-1);
      if(this.type == 0) {
         this.scrollWidth = var1.readUnsignedShort();
         this.scrollHeight = var1.readUnsignedShort();
         this.noClickThrough = var1.readUnsignedByte() == 1;
      }

      if(this.type == 5) {
         this.spriteId2 = var1.readInt();
         this.spriteAngle = var1.readUnsignedShort();
         this.spriteTiling = var1.readUnsignedByte() == 1;
         this.transparencyTop = var1.readUnsignedByte();
         this.outline = var1.readUnsignedByte();
         this.spriteShadow = var1.readInt();
         this.spriteFlipV = var1.readUnsignedByte() == 1;
         this.spriteFlipH = var1.readUnsignedByte() == 1;
      }

      if(this.type == 6) {
         this.modelType = 1;
         this.modelId = var1.readUnsignedShort();
         if(this.modelId == 65535) {
            this.modelId = -1;
         }

         this.modelOffsetX = var1.g2s();
         this.modelOffsetY = var1.g2s();
         this.modelAngleX = var1.readUnsignedShort();
         this.modelAngleY = var1.readUnsignedShort();
         this.modelAngleZ = var1.readUnsignedShort();
         this.modelZoom = var1.readUnsignedShort();
         this.sequenceId = var1.readUnsignedShort();
         if(this.sequenceId == 65535) {
            this.sequenceId = -1;
         }

         this.modelOrthog = var1.readUnsignedByte() == 1;
         var1.readUnsignedShort();
         if(this.widthAlignment != 0) {
            this.field2700 = var1.readUnsignedShort();
         }

         if(this.heightAlignment != 0) {
            var1.readUnsignedShort();
         }
      }

      if(this.type == 4) {
         this.fontId = var1.readUnsignedShort();
         if(this.fontId == 65535) {
            this.fontId = -1;
         }

         this.text = var1.readString();
         this.textLineHeight = var1.readUnsignedByte();
         this.textXAlignment = var1.readUnsignedByte();
         this.textYAlignment = var1.readUnsignedByte();
         this.textShadowed = var1.readUnsignedByte() == 1;
         this.color = var1.readInt();
      }

      if(this.type == 3) {
         this.color = var1.readInt();
         this.fill = var1.readUnsignedByte() == 1;
         this.transparencyTop = var1.readUnsignedByte();
      }

      if(this.type == 9) {
         this.lineWid = var1.readUnsignedByte();
         this.color = var1.readInt();
         this.field2612 = var1.readUnsignedByte() == 1;
      }

      this.clickMask = var1.method5500();
      this.dataText = var1.readString();
      int var2 = var1.readUnsignedByte();
      if(var2 > 0) {
         this.actions = new String[var2];

         for(int var3 = 0; var3 < var2; ++var3) {
            this.actions[var3] = var1.readString();
         }
      }

      this.dragZoneSize = var1.readUnsignedByte();
      this.dragThreshold = var1.readUnsignedByte();
      this.isScrollBar = var1.readUnsignedByte() == 1;
      this.spellActionName = var1.readString();
      this.onLoad = this.method4009(var1);
      this.onMouseOver = this.method4009(var1);
      this.onMouseLeave = this.method4009(var1);
      this.onTargetLeave = this.method4009(var1);
      this.onTargetEnter = this.method4009(var1);
      this.onVarTransmit = this.method4009(var1);
      this.onInvTransmit = this.method4009(var1);
      this.onStatTransmit = this.method4009(var1);
      this.onTimer = this.method4009(var1);
      this.onOp = this.method4009(var1);
      this.onMouseRepeat = this.method4009(var1);
      this.onClick = this.method4009(var1);
      this.onClickRepeat = this.method4009(var1);
      this.onRelease = this.method4009(var1);
      this.onHold = this.method4009(var1);
      this.onDrag = this.method4009(var1);
      this.onDragComplete = this.method4009(var1);
      this.onScroll = this.method4009(var1);
      this.varTransmitTriggers = this.method3969(var1);
      this.invTransmitTriggers = this.method3969(var1);
      this.statTransmitTriggers = this.method3969(var1);
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "(ILjava/lang/String;S)V",
      garbageValue = "-24772"
   )
   public void method3976(int var1, String var2) {
      if(this.actions == null || this.actions.length <= var1) {
         String[] var3 = new String[var1 + 1];
         if(this.actions != null) {
            for(int var4 = 0; var4 < this.actions.length; ++var4) {
               var3[var4] = this.actions[var4];
            }
         }

         this.actions = var3;
      }

      this.actions[var1] = var2;
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(Lkl;I)[I",
      garbageValue = "-132337395"
   )
   int[] method3969(Buffer var1) {
      int var2 = var1.readUnsignedByte();
      if(var2 == 0) {
         return null;
      } else {
         int[] var3 = new int[var2];

         for(int var4 = 0; var4 < var2; ++var4) {
            var3[var4] = var1.readInt();
         }

         return var3;
      }
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(Lix;IZLhr;I)Ldh;"
   )
   public Model method3974(SequenceDefinition var1, int var2, boolean var3, PlayerAppearance var4, int var5) {
      if(var2 != -1 && ViewportMouse.client.isInterpolateWidgetAnimations()) {
         var2 = var2 | this.getModelFrameCycle() << 16 | Integer.MIN_VALUE;
      }

      return (Model)this.copy$getModel(var1, var2, var3, var4, var5);
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(I)Lkn;",
      garbageValue = "2078221283"
   )
   public Font method3972() {
      field2576 = false;
      if(this.fontId == -1) {
         return null;
      } else {
         Font var1 = (Font)Widget_cachedFonts.method3032((long)this.fontId);
         if(var1 != null) {
            return var1;
         } else {
            var1 = ClanMate.method4989(ClientPreferences.Widget_spritesArchive, class12.Widget_fontsArchive, this.fontId, 0);
            if(var1 != null) {
               Widget_cachedFonts.method3034(var1, (long)this.fontId);
            } else {
               field2576 = true;
            }

            return var1;
         }
      }
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(ZB)Llf;"
   )
   public Sprite method3971(boolean var1, byte var2) {
      if(this.getSpriteId() != -1) {
         net.runelite.api.Sprite var3 = (net.runelite.api.Sprite)Client.widgetSpriteOverrides.get(Integer.valueOf(this.getId()));
         if(var3 != null) {
            return (Sprite)((RSSprite)var3);
         }
      }

      return (Sprite)this.copy$getSprite(var1, var2);
   }

   public Rectangle getBounds() {
      Point var1 = this.getCanvasLocation();
      return new Rectangle(var1.getX(), var1.getY(), this.getWidth(), this.getHeight());
   }

   public String getRSText() {
      return this.text;
   }

   public String getRSName() {
      return this.dataText;
   }

   public RSSprite getSprite(boolean var1) {
      return this.method3971(var1, (byte)13);
   }

   public Point getCanvasLocation() {
      return new Point(this.rl$x, this.rl$y);
   }

   public int getParentId() {
      if(!$assertionsDisabled && !ViewportMouse.client.isClientThread()) {
         throw new AssertionError();
      } else {
         int var1 = this.getRSParentId();
         if(var1 != -1) {
            return var1;
         } else {
            int var2 = this.getId();
            if(WidgetInfo.TO_GROUP(var2) == ViewportMouse.client.getWidgetRoot()) {
               return -1;
            } else {
               int var3 = this.rl$parentId;
               if(var3 != -1) {
                  RSNodeHashTable var4 = ViewportMouse.client.getComponentTable();
                  WidgetNode var5 = (WidgetNode)var4.get((long)var3);
                  if(var5 != null && var5.getId() == WidgetInfo.TO_GROUP(var2)) {
                     return var3;
                  }

                  this.rl$parentId = -1;
               }

               int var13 = WidgetInfo.TO_GROUP(this.getId());
               RSNodeHashTable var14 = ViewportMouse.client.getComponentTable();
               RSNode[] var6 = var14.getBuckets();
               RSNode[] var7 = var6;
               int var8 = var6.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  RSNode var10 = var7[var9];

                  for(Object var11 = var10.getNext(); var11 != var10; var11 = ((net.runelite.api.Node)var11).getNext()) {
                     WidgetNode var12 = (WidgetNode)var11;
                     if(var13 == var12.getId()) {
                        return (int)var12.getHash();
                     }
                  }
               }

               return -1;
            }
         }
      }
   }

   public int[] getItemIds() {
      return this.itemIds;
   }

   public int getSpriteId() {
      return this.spriteId2;
   }

   public RSWidget[] getChildren() {
      return this.children;
   }

   public int getRSParentId() {
      return this.parentId;
   }

   public int getId() {
      return this.id;
   }

   public void setRSName(String var1) {
      this.dataText = var1;
   }

   @ObfuscatedSignature(
      signature = "(Lix;IZLhr;I)Ldh;"
   )
   public Model copy$getModel(SequenceDefinition var1, int var2, boolean var3, PlayerAppearance var4, int var5) {
      field2576 = false;
      int var6;
      int var7;
      if(var3) {
         var6 = this.modelType2;
         var7 = this.modelId2;
      } else {
         var6 = this.modelType;
         var7 = this.modelId;
      }

      if(var6 == 0) {
         return null;
      } else if(var6 == 1 && var7 == -1) {
         return null;
      } else {
         Model var8 = (Model)Widget_cachedModels.method3032((long)(var7 + (var6 << 16)));
         if(var8 == null) {
            ModelData var9;
            if(var6 == 1) {
               var9 = ModelData.method2823(TaskHandler.Widget_modelsArchive, var7, 0);
               if(var9 == null) {
                  field2576 = true;
                  return null;
               }

               var8 = var9.method2778(64, 768, -50, -10, -50);
            }

            if(var6 == 2) {
               var9 = PacketBufferNode.getNpcDefinition(var7).method4406();
               if(var9 == null) {
                  field2576 = true;
                  return null;
               }

               var8 = var9.method2778(64, 768, -50, -10, -50);
            }

            if(var6 == 3) {
               if(var4 == null) {
                  return null;
               }

               var9 = var4.method4133();
               if(var9 == null) {
                  field2576 = true;
                  return null;
               }

               var8 = var9.method2778(64, 768, -50, -10, -50);
            }

            if(var6 == 4) {
               ItemDefinition var10 = Occluder.getItemDefinition(var7);
               var9 = var10.method4534(10);
               if(var9 == null) {
                  field2576 = true;
                  return null;
               }

               var8 = var9.method2778(var10.ambient + 64, var10.contrast + 768, -50, -10, -50);
            }

            Widget_cachedModels.method3034(var8, (long)(var7 + (var6 << 16)));
         }

         if(var1 != null) {
            var8 = var1.method4669(var8, var2, (byte)-75);
         }

         return var8;
      }
   }

   public int[] getItemQuantities() {
      return this.itemQuantities;
   }

   public boolean isSelfHidden() {
      return this.isHidden;
   }

   public net.runelite.api.widgets.Widget getParent() {
      int var1 = this.getParentId();
      return var1 == -1?null:ViewportMouse.client.getWidget(WidgetInfo.TO_GROUP(var1), WidgetInfo.TO_CHILD(var1));
   }

   @ObfuscatedSignature(
      signature = "(ZB)Llf;"
   )
   public Sprite copy$getSprite(boolean var1, byte var2) {
      field2576 = false;
      int var3;
      if(var1) {
         var3 = this.spriteId;
      } else {
         var3 = this.spriteId2;
      }

      if(var3 == -1) {
         return null;
      } else {
         long var4 = ((long)this.spriteShadow << 40) + ((this.spriteFlipH?1L:0L) << 39) + (long)var3 + ((long)this.outline << 36) + ((this.spriteFlipV?1L:0L) << 38);
         Sprite var6 = (Sprite)Widget_cachedSprites.method3032(var4);
         if(var6 != null) {
            return var6;
         } else {
            var6 = NPCDefinition.method4417(ClientPreferences.Widget_spritesArchive, var3, 0, -1092680498);
            if(var6 == null) {
               field2576 = true;
               return null;
            } else {
               if(this.spriteFlipV) {
                  var6.method6098();
               }

               if(this.spriteFlipH) {
                  var6.method6108();
               }

               if(this.outline > 0) {
                  var6.method6101(this.outline);
               }

               if(this.outline >= 1) {
                  var6.method6104(1);
               }

               if(this.outline >= 2) {
                  var6.method6104(16777215);
               }

               if(this.spriteShadow != 0) {
                  var6.method6105(this.spriteShadow);
               }

               Widget_cachedSprites.method3034(var6, var4);
               return var6;
            }
         }
      }
   }

   public int getModelFrameCycle() {
      return this.modelFrameCycle;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public WidgetItem getWidgetItem(int var1) {
      int[] var2 = this.getItemIds();
      int[] var3 = this.getItemQuantities();
      String[][] attributes = this.getItemAttributes();
      if(var2 != null && var3 != null) {
         int var4 = this.getWidth();
         int var5 = this.getXPitch();
         int var6 = this.getYPitch();
         int var7 = var2[var1];
         int var8 = var3[var1];
         if(var4 <= 0) {
            return null;
         } else {
            int var9 = var1 / var4;
            int var10 = var1 % var4;
            int var11 = var10 * (var5 + 32) + this.rl$x;
            int var12 = var9 * (var6 + 32) + this.rl$y;
            Rectangle var13 = new Rectangle(var11, var12, 32, 32);
            return new WidgetItem(var7 - 1, var8, var1, var13, this, attributes[var1]);
         }
      } else {
         return null;
      }
   }

   public int getXPitch() {
      return this.paddingX;
   }

   public net.runelite.api.widgets.Widget[] getNestedChildren() {
      if(!$assertionsDisabled && !ViewportMouse.client.isClientThread()) {
         throw new AssertionError();
      } else if(this.getRSParentId() == this.getId()) {
         return new net.runelite.api.widgets.Widget[0];
      } else {
         RSNodeHashTable var1 = ViewportMouse.client.getComponentTable();
         WidgetNode var2 = (WidgetNode)var1.get((long)this.getId());
         if(var2 == null) {
            return new Widget[0];
         } else {
            int var3 = var2.getId();
            ArrayList var4 = new ArrayList();
            RSWidget[] var5 = ViewportMouse.client.getGroup(var3);
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               RSWidget var8 = var5[var7];
               if(var8 != null && var8.getRSParentId() == -1) {
                  var4.add(var8);
               }
            }

            return (net.runelite.api.widgets.Widget[])var4.toArray(new Widget[var4.size()]);
         }
      }
   }

   public int getYPitch() {
      return this.paddingY;
   }

   public void broadcastHidden(boolean var1) {
      WidgetHiddenChanged var2 = new WidgetHiddenChanged();
      var2.setWidget(this);
      var2.setHidden(var1);
      ViewportMouse.client.getCallbacks().post(WidgetHiddenChanged.class, var2);
      RSWidget[] var3 = this.getChildren();
      int var6;
      if(var3 != null) {
         RSWidget[] var4 = var3;
         int var5 = var3.length;

         for(var6 = 0; var6 < var5; ++var6) {
            RSWidget var7 = var4[var6];
            if(var7 != null && !var7.isSelfHidden()) {
               var7.broadcastHidden(var1);
            }
         }
      }

      net.runelite.api.widgets.Widget[] var11 = this.getNestedChildren();
      net.runelite.api.widgets.Widget[] var9 = var11;
      var6 = var11.length;

      for(int var10 = 0; var10 < var6; ++var10) {
         net.runelite.api.widgets.Widget var8 = var9[var10];
         if(var8 != null && !var8.isSelfHidden()) {
            ((RSWidget)var8).broadcastHidden(var1);
         }
      }

   }

   public void setChildren(net.runelite.api.widgets.Widget[] var1) {
      this.children = (Widget[])var1;
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public void onPositionChanged(int var1) {
      int var2 = this.getId();
      if(var2 != -1) {
         int var3 = ViewportMouse.client.getGameCycle();
         if(var3 != rl$widgetLastPosChanged) {
            rl$widgetLastPosChanged = var3;
            ViewportMouse.client.getLogger().trace("Posting widget position changed");
            WidgetPositioned var4 = WidgetPositioned.INSTANCE;
            ViewportMouse.client.getCallbacks().postDeferred(WidgetPositioned.class, var4);
         }
      }
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public void onHiddenChanged(int var1) {
      int var2 = this.getId();
      if(var2 != -1) {
         net.runelite.api.widgets.Widget var3 = this.getParent();
         if(var3 != null) {
            if(var3.isHidden()) {
               return;
            }
         } else if(WidgetInfo.TO_GROUP(var2) != ViewportMouse.client.getWidgetRoot()) {
            return;
         }

         this.broadcastHidden(this.isSelfHidden());
      }
   }

   private void rl$$init() {
      this.rl$parentId = -1;
      this.rl$x = -1;
      this.rl$y = -1;
   }

   public net.runelite.api.Sprite getSprite() {
      return this.getSprite(false);
   }

   public void setRenderParentId(int var1) {
      this.rl$parentId = var1;
   }

   public void setRenderX(int var1) {
      this.rl$x = var1;
   }

   public void setRenderY(int var1) {
      this.rl$y = var1;
   }

   public String getText() {
      return this.getRSText().replace(' ', ' ');
   }

   public String getName() {
      return this.getRSName().replace(' ', ' ');
   }

   public void setName(String var1) {
      this.setRSName(var1.replace(' ', ' '));
   }

   public boolean isHidden() {
      if(!$assertionsDisabled && !ViewportMouse.client.isClientThread()) {
         throw new AssertionError();
      } else if(this.isSelfHidden()) {
         return true;
      } else {
         net.runelite.api.widgets.Widget var1 = this.getParent();
         if(var1 == null) {
            if(WidgetInfo.TO_GROUP(this.getId()) != ViewportMouse.client.getWidgetRoot()) {
               return true;
            }
         } else if(var1.isHidden()) {
            return true;
         }

         return false;
      }
   }

   public Collection getWidgetItems() {
      int[] var1 = this.getItemIds();
      if(var1 == null) {
         return null;
      } else {
         ArrayList var2 = new ArrayList(var1.length);

         for(int var3 = 0; var3 < var1.length; ++var3) {
            if(var1[var3] > 0) {
               WidgetItem var4 = this.getWidgetItem(var3);
               if(var4 != null) {
                  var2.add(var4);
               }
            }
         }

         return var2;
      }
   }

   public net.runelite.api.widgets.Widget getChild(int var1) {
      RSWidget[] var2 = this.getChildren();
      return var2 != null && var2[var1] != null?var2[var1]:null;
   }

   public net.runelite.api.widgets.Widget[] getDynamicChildren() {
      RSWidget[] var1 = this.getChildren();
      if(var1 == null) {
         return new net.runelite.api.widgets.Widget[0];
      } else {
         ArrayList var2 = new ArrayList();
         RSWidget[] var3 = var1;
         int var4 = var1.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            RSWidget var6 = var3[var5];
            if(var6 != null && var6.getRSParentId() == this.getId()) {
               var2.add(var6);
            }
         }

         return (net.runelite.api.widgets.Widget[])var2.toArray(new net.runelite.api.widgets.Widget[var2.size()]);
      }
   }

   public net.runelite.api.widgets.Widget[] getStaticChildren() {
      if(this.getRSParentId() == this.getId()) {
         return new net.runelite.api.widgets.Widget[0];
      } else {
         ArrayList var1 = new ArrayList();
         RSWidget[] var2 = ViewportMouse.client.getGroup(WidgetInfo.TO_GROUP(this.getId()));
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            RSWidget var5 = var2[var4];
            if(var5 != null && var5.getRSParentId() == this.getId()) {
               var1.add(var5);
            }
         }

         return (net.runelite.api.widgets.Widget[])var1.toArray(new Widget[var1.size()]);
      }
   }

   public boolean contains(Point var1) {
      Rectangle var2 = this.getBounds();
      return var2 != null && var2.contains(new java.awt.Point(var1.getX(), var1.getY()));
   }

   public net.runelite.api.widgets.Widget createChild(int var1, int var2) {
      if(!$assertionsDisabled && !ViewportMouse.client.isClientThread()) {
         throw new AssertionError();
      } else {
         RSWidget var3 = ViewportMouse.client.createWidget();
         var3.setType(var2);
         var3.setParentId(this.getId());
         var3.setId(this.getId());
         var3.setIsIf3(true);
         Object var4 = this.getChildren();
         if(var1 < 0) {
            if(var4 == null) {
               var1 = 0;
            } else {
               var1 = 0;

               for(int var6 = ((Object[])var4).length - 1; var6 >= 0; --var6) {
                  if(((Object[])var4)[var6] != null) {
                     var1 = var6 + 1;
                     break;
                  }
               }
            }
         }

         if(var4 == null) {
            var4 = new Widget[var1 + 1];
            this.setChildren((net.runelite.api.widgets.Widget[])var4);
         } else if(((Object[])var4).length <= var1) {
            Widget[] var5 = new Widget[var1 + 1];
            System.arraycopy(var4, 0, var5, 0, ((Object[])var4).length);
            var4 = var5;
            this.setChildren(var5);
         }

         ((Object[])var4)[var1] = var3;
         var3.setIndex(var1);
         return var3;
      }
   }

   public void revalidate() {
      if(!$assertionsDisabled && !ViewportMouse.client.isClientThread()) {
         throw new AssertionError();
      } else {
         ViewportMouse.client.revalidateWidget(this);
      }
   }

   public void revalidateScroll() {
      if(!$assertionsDisabled && !ViewportMouse.client.isClientThread()) {
         throw new AssertionError();
      } else {
         ViewportMouse.client.revalidateWidget(this);
         ViewportMouse.client.revalidateWidgetScroll(ViewportMouse.client.getWidgets()[WidgetInfo.TO_GROUP(this.getId())], this, false);
      }
   }

   public void deleteAllChildren() {
      if(this.getChildren() != null) {
         Arrays.fill(this.getChildren(), (Object)null);
      }

   }

   public void setIsIf3(boolean var1) {
      this.isIf3 = var1;
   }

   public boolean isIf3() {
      return this.isIf3;
   }

   public void setId(int var1) {
      this.id = var1;
   }

   public void setIndex(int var1) {
      this.childIndex = var1;
   }

   public int getIndex() {
      return this.childIndex;
   }

   public void setType(int var1) {
      this.type = var1;
   }

   public int getType() {
      return this.type;
   }

   public void setContentType(int var1) {
      this.contentType = var1;
   }

   public int getContentType() {
      return this.contentType;
   }

   public void setXPositionMode(int var1) {
      this.xAlignment = var1;
   }

   public int getXPositionMode() {
      return this.xAlignment;
   }

   public void setYPositionMode(int var1) {
      this.yAlignment = var1;
   }

   public int getYPositionMode() {
      return this.yAlignment;
   }

   public void setWidthMode(int var1) {
      this.widthAlignment = var1;
   }

   public int getWidthMode() {
      return this.widthAlignment;
   }

   public void setHeightMode(int var1) {
      this.heightAlignment = var1;
   }

   public int getHeightMode() {
      return this.heightAlignment;
   }

   public void setOriginalX(int var1) {
      this.rawX = var1;
   }

   public int getOriginalX() {
      return this.rawX;
   }

   public void setOriginalY(int var1) {
      this.rawY = var1;
   }

   public int getOriginalY() {
      return this.rawY;
   }

   public void setOriginalWidth(int var1) {
      this.rawWidth = var1;
   }

   public int getOriginalWidth() {
      return this.rawWidth;
   }

   public void setOriginalHeight(int var1) {
      this.rawHeight = var1;
   }

   public int getOriginalHeight() {
      return this.rawHeight;
   }

   public void setRelativeX(int var1) {
      this.x = var1;
   }

   public int getRelativeX() {
      return this.x;
   }

   public void setRelativeY(int var1) {
      this.y = var1;
   }

   public int getRelativeY() {
      return this.y;
   }

   public void setWidth(int var1) {
      this.width = var1;
   }

   public void setHeight(int var1) {
      this.height = var1;
   }

   public void setParentId(int var1) {
      this.parentId = var1;
   }

   public void setHidden(boolean var1) {
      this.isHidden = var1;
   }

   public void setScrollX(int var1) {
      this.scrollX = var1;
   }

   public int getScrollX() {
      return this.scrollX;
   }

   public void setScrollY(int var1) {
      this.scrollY = var1;
   }

   public int getScrollY() {
      return this.scrollY;
   }

   public void setScrollWidth(int var1) {
      this.scrollWidth = var1;
   }

   public int getScrollWidth() {
      return this.scrollWidth;
   }

   public void setScrollHeight(int var1) {
      this.scrollHeight = var1;
   }

   public int getScrollHeight() {
      return this.scrollHeight;
   }

   public void setTextColor(int var1) {
      this.color = var1;
   }

   public int getTextColor() {
      return this.color;
   }

   public void setFilled(boolean var1) {
      this.fill = var1;
   }

   public boolean isFilled() {
      return this.fill;
   }

   public void setOpacity(int var1) {
      this.transparencyTop = var1;
   }

   public int getOpacity() {
      return this.transparencyTop;
   }

   public void setSpriteId(int var1) {
      this.spriteId2 = var1;
   }

   public void setBorderType(int var1) {
      this.outline = var1;
   }

   public int getBorderType() {
      return this.outline;
   }

   public int getModelType() {
      return this.modelType;
   }

   public int getModelId() {
      return this.modelId;
   }

   public void setItemQuantityMode(int var1) {
      this.itemQuantityMode = var1;
   }

   public int getItemQuantityMode() {
      return this.itemQuantityMode;
   }

   public void setFontId(int var1) {
      this.fontId = var1;
   }

   public int getFontId() {
      return this.fontId;
   }

   public void setText(String var1) {
      this.text = var1;
   }

   public void setXTextAlignment(int var1) {
      this.textXAlignment = var1;
   }

   public int getXTextAlignment() {
      return this.textXAlignment;
   }

   public void setYTextAlignment(int var1) {
      this.textYAlignment = var1;
   }

   public int getYTextAlignment() {
      return this.textYAlignment;
   }

   public void setTextShadowed(boolean var1) {
      this.textShadowed = var1;
   }

   public boolean getTextShadowed() {
      return this.textShadowed;
   }

   public void setClickMask(int var1) {
      this.clickMask = var1;
   }

   public int getClickMask() {
      return this.clickMask;
   }

   public String[] getActions() {
      return this.actions;
   }

   public void setDragDeadZone(int var1) {
      this.dragZoneSize = var1;
   }

   public int getDragDeadZone() {
      return this.dragZoneSize;
   }

   public void setDragDeadTime(int var1) {
      this.dragThreshold = var1;
   }

   public int getDragDeadTime() {
      return this.dragThreshold;
   }

   public void setTargetVerb(String var1) {
      this.spellActionName = var1;
   }

   public String getTargetVerb() {
      return this.spellActionName;
   }

   public void setHasListener(boolean var1) {
      this.hasListener = var1;
   }

   public boolean hasListener() {
      return this.hasListener;
   }

   public Object[] getOnLoadListener() {
      return this.onLoad;
   }

   public void setOnMouseOverListener(Object[] var1) {
      this.onMouseOver = var1;
   }

   public void setOnMouseRepeatListener(Object[] var1) {
      this.onMouseRepeat = var1;
   }

   public void setOnMouseLeaveListener(Object[] var1) {
      this.onMouseLeave = var1;
   }

   public void setOnTargetEnterListener(Object[] var1) {
      this.onTargetEnter = var1;
   }

   public void setOnTargetLeaveListener(Object[] var1) {
      this.onTargetLeave = var1;
   }

   public Object[] getOnInvTransmit() {
      return this.onInvTransmit;
   }

   public void setOnTimerListener(Object[] var1) {
      this.onTimer = var1;
   }

   public void setOnOpListener(Object[] var1) {
      this.onOp = var1;
   }

   public Object[] getOnOp() {
      return this.onOp;
   }

   public void setOnKeyListener(Object[] var1) {
      this.onKey = var1;
   }

   public Object[] getOnKeyListener() {
      return this.onKey;
   }

   public void setOnDialogAbortListener(Object[] var1) {
      this.onDialogAbort = var1;
   }

   public void setItemId(int var1) {
      this.itemId = var1;
   }

   public int getItemId() {
      return this.itemId;
   }

   public void setItemQuantity(int var1) {
      this.itemQuantity = var1;
   }

   public String[][] getItemAttributes() {
      return this.itemAttributes;
   }

   public int getItemQuantity() {
      return this.itemQuantity;
   }

   public int getModelFrame() {
      return this.modelFrame;
   }

   public boolean containsMouse() {
      return this.containsMouse;
   }

   public void setNoClickThrough(boolean var1) {
      this.noClickThrough = var1;
   }

   public boolean getNoClickThrough() {
      return this.noClickThrough;
   }

   public void setNoScrollThrough(boolean var1) {
      this.noScrollThrough = var1;
   }

   public boolean getNoScrollThrough() {
      return this.noScrollThrough;
   }

   public RSAbstractFont getFont() {
      return this.method3972();
   }

   public void setAction(int var1, String var2) {
      this.method3976(var1, var2);
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(ZB)Lhz;",
      garbageValue = "0"
   )
   public SpriteMask method3975(boolean var1) {
      if(this.spriteId == -1) {
         var1 = false;
      }

      int var2 = var1?this.spriteId:this.spriteId2;
      if(var2 == -1) {
         return null;
      } else {
         long var3 = ((this.spriteFlipV?1L:0L) << 38) + (long)var2 + ((long)this.outline << 36) + ((this.spriteFlipH?1L:0L) << 39) + ((long)this.spriteShadow << 40);
         SpriteMask var5 = (SpriteMask)Widget_cachedSpriteMasks.method3032(var3);
         if(var5 != null) {
            return var5;
         } else {
            Sprite var6 = this.method3971(var1, (byte)8);
            if(var6 == null) {
               return null;
            } else {
               Sprite var7 = var6.method6137();
               int[] var8 = new int[var7.subHeight];
               int[] var9 = new int[var7.subHeight];

               for(int var10 = 0; var10 < var7.subHeight; ++var10) {
                  int var11 = 0;
                  int var12 = var7.subWidth;

                  int var13;
                  for(var13 = 0; var13 < var7.subWidth; ++var13) {
                     if(var7.pixels[var13 + var10 * var7.subWidth] == 0) {
                        var11 = var13;
                        break;
                     }
                  }

                  for(var13 = var7.subWidth - 1; var13 >= var11; --var13) {
                     if(var7.pixels[var13 + var10 * var7.subWidth] == 0) {
                        var12 = var13 + 1;
                        break;
                     }
                  }

                  var8[var10] = var11;
                  var9[var10] = var12 - var11;
               }

               var5 = new SpriteMask(var7.subWidth, var7.subHeight, var9, var8, var2);
               Widget_cachedSpriteMasks.method3034(var5, var3);
               return var5;
            }
         }
      }
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "-1452278640"
   )
   public void method3970(int var1, int var2) {
      int var3 = this.itemIds[var2];
      this.itemIds[var2] = this.itemIds[var1];
      this.itemIds[var1] = var3;
      var3 = this.itemQuantities[var2];
      this.itemQuantities[var2] = this.itemQuantities[var1];
      this.itemQuantities[var1] = var3;
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(Lkl;B)[Ljava/lang/Object;",
      garbageValue = "0"
   )
   Object[] method4009(Buffer var1) {
      int var2 = var1.readUnsignedByte();
      if(var2 == 0) {
         return null;
      } else {
         Object[] var3 = new Object[var2];

         for(int var4 = 0; var4 < var2; ++var4) {
            int var5 = var1.readUnsignedByte();
            if(var5 == 0) {
               var3[var4] = new Integer(var1.readInt());
            } else if(var5 == 1) {
               var3[var4] = var1.readString();
            }
         }

         this.hasListener = true;
         return var3;
      }
   }

   private static void rl$$clinit() {
      $assertionsDisabled = !Client.class.desiredAssertionStatus();
   }

   /**
    * Custom
    */
   //TODO:: Custom Method
   public static Widget addChild(int id, int childType, int childId) {
      if (childType == 0)
         throw new RuntimeException();
      Widget parent = Canvas.getWidget(id);
      if (parent.children == null)
         parent.children = new Widget[childId + 1];
      if (parent.children.length <= childId) {
         Widget[] var35 = new Widget[childId + 1];
         for (int var10 = 0; var10 < parent.children.length; var10++)
            var35[var10] = parent.children[var10];
         parent.children = var35;
      }
      if (childId > 0 && parent.children[childId - 1] == null)
         throw new RuntimeException("" + (childId - 1));
      Widget child = new Widget();
      child.type = childType;
      child.parentId = child.id = parent.id;
      child.childIndex = childId;
      child.isIf3 = true;
      parent.children[childId] = child;
      WorldMapSectionType.method116(parent);
      return child;
   }

   //TODO:: Custom Method
   public static void setItem(Widget inter, int itemId, int itemAmount, int itemAmountSetting) {
      ItemDefinition def = Occluder.getItemDefinition(itemId);
      inter.itemId = itemId;
      inter.itemQuantity = itemAmount;
      inter.modelAngleX = def.xan2d;
      inter.modelAngleZ = def.yan2d;
      inter.modelAngleY = def.zan2d;
      inter.modelOffsetX = def.offsetX2d;
      inter.modelOffsetY = def.offsetY2d;
      inter.modelZoom = def.zoom2d;
      inter.itemQuantityMode = itemAmountSetting;
      try {
         if (inter.field2700 > 0)
            inter.modelZoom = inter.modelZoom * 32 / inter.field2700;
         else if (inter.width > 0)
            inter.modelZoom = inter.modelZoom * 32 / inter.rawWidth;
      } catch (ArithmeticException e) {
         e.printStackTrace();
      }
   }
}
