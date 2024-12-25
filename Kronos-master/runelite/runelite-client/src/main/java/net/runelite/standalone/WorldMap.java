package net.runelite.standalone;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.runelite.api.Point;
import net.runelite.api.WorldMapData;
import net.runelite.api.coords.WorldPoint;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSWorldMap;
import net.runelite.rs.api.RSWorldMapArea;
import net.runelite.rs.api.RSWorldMapManager;

@ObfuscatedName("la")
public class WorldMap implements RSWorldMap {
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "Lko;"
   )
   static final FontName fontNameVerdana15;
   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "Lko;"
   )
   static final FontName fontNameVerdana13;
   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "Lko;"
   )
   static final FontName fontNameVerdana11;
   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "Lkn;"
   )
   Font font;
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   AbstractArchive WorldMap_archive;
   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "Llj;"
   )
   WorldMapArchiveLoader cacheLoader;
   @ObfuscatedName("t")
   @ObfuscatedSignature(
      signature = "Lac;"
   )
   WorldMapArea mainMapArea;
   @ObfuscatedName("w")
   HashMap details;
   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "Lac;"
   )
   WorldMapArea field3833;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   AbstractArchive WorldMap_groundArchive;
   @ObfuscatedName("a")
   HashMap fonts;
   @ObfuscatedName("aa")
   @ObfuscatedGetter(
      intValue = 1837582351
   )
   int worldMapDisplayX;
   @ObfuscatedName("ab")
   @ObfuscatedGetter(
      intValue = -1420038403
   )
   int cyclesPerFlash;
   @ObfuscatedName("ac")
   float zoomTarget;
   @ObfuscatedName("ad")
   @ObfuscatedGetter(
      intValue = 1563853833
   )
   int field3852;
   @ObfuscatedName("ae")
   @ObfuscatedGetter(
      intValue = -1406947901
   )
   int field3858;
   @ObfuscatedName("af")
   HashSet field3866;
   @ObfuscatedName("ai")
   @ObfuscatedGetter(
      intValue = 34575525
   )
   int field3830;
   @ObfuscatedName("aj")
   HashSet enabledElements;
   @ObfuscatedName("ak")
   @ObfuscatedGetter(
      intValue = 361888335
   )
   int field3855;
   @ObfuscatedName("al")
   @ObfuscatedGetter(
      intValue = 1480309149
   )
   int flashCycle;
   @ObfuscatedName("am")
   @ObfuscatedGetter(
      intValue = 329127117
   )
   int field3872;
   @ObfuscatedName("an")
   @ObfuscatedGetter(
      intValue = 50694755
   )
   int field3864;
   @ObfuscatedName("ao")
   @ObfuscatedGetter(
      intValue = -1316059479
   )
   int flashCount;
   @ObfuscatedName("ap")
   @ObfuscatedGetter(
      intValue = 859234001
   )
   int worldMapDisplayY;
   @ObfuscatedName("aq")
   @ObfuscatedGetter(
      longValue = 2458788586712283789L
   )
   long field3856;
   @ObfuscatedName("ar")
   @ObfuscatedGetter(
      intValue = -803250815
   )
   int maxFlashCount;
   @ObfuscatedName("as")
   HashSet flashingElements;
   @ObfuscatedName("at")
   boolean elementsDisabled;
   @ObfuscatedName("au")
   HashSet enabledElementIds;
   @ObfuscatedName("av")
   boolean field3859;
   @ObfuscatedName("aw")
   @ObfuscatedGetter(
      intValue = -673171535
   )
   int worldMapDisplayHeight;
   @ObfuscatedName("ax")
   boolean perpetualFlash;
   @ObfuscatedName("ay")
   HashSet enabledCategories;
   @ObfuscatedName("az")
   @ObfuscatedGetter(
      intValue = 1385726617
   )
   int worldMapDisplayWidth;
   @ObfuscatedName("ba")
   @ObfuscatedGetter(
      intValue = 2138854549
   )
   int minCachedTileX;
   @ObfuscatedName("bc")
   @ObfuscatedSignature(
      signature = "Llf;"
   )
   Sprite sprite;
   @ObfuscatedName("bd")
   final int[] menuOpcodes;
   @ObfuscatedName("be")
   @ObfuscatedGetter(
      intValue = 162914603
   )
   int field3877;
   @ObfuscatedName("bj")
   HashSet field3842;
   @ObfuscatedName("bk")
   @ObfuscatedGetter(
      intValue = 111655549
   )
   int cachedPixelsPerTile;
   @ObfuscatedName("bm")
   List field3868;
   @ObfuscatedName("bn")
   @ObfuscatedGetter(
      intValue = 84437289
   )
   int minCachedTileY;
   @ObfuscatedName("bs")
   @ObfuscatedSignature(
      signature = "Lhb;"
   )
   Coord mouseCoord;
   @ObfuscatedName("bv")
   Iterator iconIterator;
   @ObfuscatedName("bx")
   @ObfuscatedGetter(
      intValue = -1409948611
   )
   int field3863;
   @ObfuscatedName("bz")
   public boolean showCoord;
   @ObfuscatedName("d")
   @ObfuscatedGetter(
      intValue = -1567029615
   )
   int worldMapTargetX;
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "[Llp;"
   )
   IndexedSprite[] mapSceneSprites;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = 1954693049
   )
   int centerTileX;
   @ObfuscatedName("g")
   @ObfuscatedSignature(
      signature = "Lac;"
   )
   WorldMapArea currentMapArea;
   @ObfuscatedName("h")
   @ObfuscatedSignature(
      signature = "Lal;"
   )
   WorldMapManager worldMapManager;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = 1682895493
   )
   int centerTileY;
   @ObfuscatedName("k")
   float zoom;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = 1146650801
   )
   int worldMapTargetY;
   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   AbstractArchive WorldMap_geographyArchive;

   static {
      fontNameVerdana11 = FontName.FontName_verdana11;
      fontNameVerdana13 = FontName.FontName_verdana13;
      fontNameVerdana15 = FontName.FontName_verdana15;
   }

   public WorldMap() {
      this.worldMapTargetX = -1;
      this.worldMapTargetY = -1;
      this.worldMapDisplayWidth = -1;
      this.worldMapDisplayHeight = -1;
      this.worldMapDisplayX = -1;
      this.worldMapDisplayY = -1;
      this.maxFlashCount = 3;
      this.cyclesPerFlash = 50;
      this.perpetualFlash = false;
      this.flashingElements = null;
      this.flashCount = -1;
      this.flashCycle = -1;
      this.field3852 = -1;
      this.field3864 = -1;
      this.field3830 = -1;
      this.field3855 = -1;
      this.field3859 = true;
      this.enabledElements = new HashSet();
      this.enabledCategories = new HashSet();
      this.enabledElementIds = new HashSet();
      this.field3866 = new HashSet();
      this.elementsDisabled = false;
      this.field3863 = 0;
      this.menuOpcodes = new int[]{1008, 1009, 1010, 1011, 1012};
      this.field3842 = new HashSet();
      this.mouseCoord = null;
      this.showCoord = false;
      this.minCachedTileX = -1;
      this.minCachedTileY = -1;
      this.field3877 = -1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-104"
   )
   public void method5923() {
      WorldMapRegion.WorldMapRegion_cachedSprites.method3093(5);
   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "1843160894"
   )
   public void method5882(int var1) {
      WorldMapArea var2 = this.method5899(var1);
      if(var2 != null) {
         this.method5885(var2);
      }

   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1288599041"
   )
   void method5934() {
      if(StudioGame.field2468 != null) {
         this.zoom = this.zoomTarget;
      } else {
         if(this.zoom < this.zoomTarget) {
            this.zoom = Math.min(this.zoomTarget, this.zoom + this.zoom / 30.0F);
         }

         if(this.zoom > this.zoomTarget) {
            this.zoom = Math.max(this.zoomTarget, this.zoom - this.zoom / 30.0F);
         }

      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1882207287"
   )
   void method5876() {
      if(this.method5889()) {
         int var1 = this.worldMapTargetX - this.centerTileX;
         int var2 = this.worldMapTargetY - this.centerTileY;
         if(var1 != 0) {
            var1 /= Math.min(8, Math.abs(var1));
         }

         if(var2 != 0) {
            var2 /= Math.min(8, Math.abs(var2));
         }

         this.method5877(var1 + this.centerTileX, var2 + this.centerTileY, true);
         if(this.worldMapTargetX == this.centerTileX && this.centerTileY == this.worldMapTargetY) {
            this.worldMapTargetX = -1;
            this.worldMapTargetY = -1;
         }

      }
   }

   @ObfuscatedName("r")
   void method5914(int var1, int var2, boolean var3, long var4) {
      if(this.currentMapArea != null) {
         int var6 = (int)((float)this.centerTileX + ((float)(var1 - this.worldMapDisplayX) - (float)this.method6009() * this.zoom / 2.0F) / this.zoom);
         int var7 = (int)((float)this.centerTileY - ((float)(var2 - this.worldMapDisplayY) - (float)this.method5967() * this.zoom / 2.0F) / this.zoom);
         this.mouseCoord = this.currentMapArea.method352(var6 + this.currentMapArea.method360() * 64, var7 + this.currentMapArea.method362() * 64);
         if(this.mouseCoord != null && var3) {
            boolean var8 = Client.staffModLevel >= 2;
            if(var8 && KeyHandler.KeyHandler_pressedKeys[82] && KeyHandler.KeyHandler_pressedKeys[81]) {
               class298.method5476(this.mouseCoord.x, this.mouseCoord.y, this.mouseCoord.plane, false);
            } else {
               boolean var9 = true;
               if(this.field3859) {
                  int var10 = var1 - this.field3872;
                  int var11 = var2 - this.field3858;
                  if(var4 - this.field3856 > 500L || var10 < -25 || var10 > 25 || var11 < -25 || var11 > 25) {
                     var9 = false;
                  }
               }

               if(var9) {
                  PacketBufferNode var12 = InterfaceParent.method1140(ClientPacket.field2409, Client.packetWriter.isaacCipher);
                  var12.packetBuffer.writeInt(this.mouseCoord.method3913());
                  Client.packetWriter.method1622(var12);
                  this.field3856 = 0L;
               }
            }
         }
      } else {
         this.mouseCoord = null;
      }

   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "(Lac;Lhb;Lhb;ZI)V",
      garbageValue = "-1837684354"
   )
   public void method6058(WorldMapArea var1, Coord var2, Coord var3, boolean var4) {
      if(var1 != null) {
         if(this.currentMapArea == null || var1 != this.currentMapArea) {
            this.method5886(var1);
         }

         if(!var4 && this.currentMapArea.method361(var2.plane, var2.x, var2.y)) {
            this.method5968(var2.plane, var2.x, var2.y);
         } else {
            this.method5968(var3.plane, var3.x, var3.y);
         }

      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(IIZZI)V",
      garbageValue = "-1491433552"
   )
   public void method6054(int var1, int var2, boolean var3, boolean var4) {
      long var5 = class33.method680();
      this.method5914(var1, var2, var4, var5);
      if(!this.method5889() && (var4 || var3)) {
         if(var4) {
            this.field3830 = var1;
            this.field3855 = var2;
            this.field3852 = this.centerTileX;
            this.field3864 = this.centerTileY;
         }

         if(this.field3852 != -1) {
            int var7 = var1 - this.field3830;
            int var8 = var2 - this.field3855;
            this.method5877(this.field3852 - (int)((float)var7 / this.zoomTarget), (int)((float)var8 / this.zoomTarget) + this.field3864, false);
         }
      } else {
         this.method5878();
      }

      if(var4) {
         this.field3856 = var5;
         this.field3872 = var1;
         this.field3858 = var2;
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(IIZIIIII)V",
      garbageValue = "1450154554"
   )
   public void method5951(int var1, int var2, boolean var3, int var4, int var5, int var6, int var7) {
      if(this.cacheLoader.method6240()) {
         this.method5934();
         this.method5876();
         if(var3) {
            int var8 = (int)Math.ceil((double)((float)var6 / this.zoom));
            int var9 = (int)Math.ceil((double)((float)var7 / this.zoom));
            List var10 = this.worldMapManager.method611(this.centerTileX - var8 / 2 - 1, this.centerTileY - var9 / 2 - 1, var8 / 2 + this.centerTileX + 1, var9 / 2 + this.centerTileY + 1, var4, var5, var6, var7, var1, var2);
            HashSet var11 = new HashSet();

            Iterator var12;
            AbstractWorldMapIcon var13;
            ScriptEvent var14;
            WorldMapEvent var15;
            for(var12 = var10.iterator(); var12.hasNext(); ParamDefinition.method4313(var14)) {
               var13 = (AbstractWorldMapIcon)var12.next();
               var11.add(var13);
               var14 = new ScriptEvent();
               var15 = new WorldMapEvent(var13.vmethod2277(), var13.coord1, var13.coord2);
               var14.method802(new Object[]{var15, Integer.valueOf(var1), Integer.valueOf(var2)});
               if(this.field3842.contains(var13)) {
                  var14.method803(17);
               } else {
                  var14.method803(15);
               }
            }

            var12 = this.field3842.iterator();

            while(var12.hasNext()) {
               var13 = (AbstractWorldMapIcon)var12.next();
               if(!var11.contains(var13)) {
                  var14 = new ScriptEvent();
                  var15 = new WorldMapEvent(var13.vmethod2277(), var13.coord1, var13.coord2);
                  var14.method802(new Object[]{var15, Integer.valueOf(var1), Integer.valueOf(var2)});
                  var14.method803(16);
                  ParamDefinition.method4313(var14);
               }
            }

            this.field3842 = var11;
         }
      }
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "(Lac;I)V",
      garbageValue = "1889826984"
   )
   void method5885(WorldMapArea var1) {
      if(this.currentMapArea == null || var1 != this.currentMapArea) {
         this.method5886(var1);
         this.method5968(-1, -1, -1);
      }
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1905081652"
   )
   final void method5878() {
      this.field3855 = -1;
      this.field3830 = -1;
      this.field3864 = -1;
      this.field3852 = -1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lhp;Lhp;Lhp;Lkn;Ljava/util/HashMap;[Llp;B)V",
      garbageValue = "1"
   )
   public void method5936(AbstractArchive var1, AbstractArchive var2, AbstractArchive var3, Font var4, HashMap var5, IndexedSprite[] var6) {
      this.mapSceneSprites = var6;
      this.WorldMap_archive = var1;
      this.WorldMap_geographyArchive = var2;
      this.WorldMap_groundArchive = var3;
      this.font = var4;
      this.fonts = new HashMap();
      this.fonts.put(WorldMapLabelSize.WorldMapLabelSize_small, var5.get(fontNameVerdana11));
      this.fonts.put(WorldMapLabelSize.WorldMapLabelSize_medium, var5.get(fontNameVerdana13));
      this.fonts.put(WorldMapLabelSize.WorldMapLabelSize_large, var5.get(fontNameVerdana15));
      this.cacheLoader = new WorldMapArchiveLoader(var1);
      int var7 = this.WorldMap_archive.method4059(WorldMapCacheName.field243.name);
      int[] var8 = this.WorldMap_archive.method4042(var7);
      this.details = new HashMap(var8.length);

      for(int var9 = 0; var9 < var8.length; ++var9) {
         Buffer var10 = new Buffer(this.WorldMap_archive.method4020(var7, var8[var9], (short)2271));
         WorldMapArea var11 = new WorldMapArea();
         var11.decode(var10, var8[var9]);
         this.details.put(var11.method356(), var11);
         if(var11.method405()) {
            this.mainMapArea = var11;
         }
      }

      this.method5885(this.mainMapArea);
      this.field3833 = null;
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1549194975"
   )
   public int method5883() {
      return this.currentMapArea == null?-1:this.currentMapArea.method354();
   }

   @ObfuscatedName("aa")
   @ObfuscatedSignature(
      signature = "(IIIIII)V",
      garbageValue = "-715174082"
   )
   void method5894(int var1, int var2, int var3, int var4, int var5) {
      byte var6 = 20;
      int var7 = var3 / 2 + var1;
      int var8 = var4 / 2 + var2 - 18 - var6;
      Rasterizer2D.method6469(var1, var2, var3, var4, -16777216);
      Rasterizer2D.drawRectangle(var7 - 152, var8, 304, 34, -65536);
      Rasterizer2D.method6469(var7 - 150, var8 + 2, var5 * 3, 30, -65536);
      this.font.method5332("Loading...", var7, var6 + var8, -1, -1);
   }

   @ObfuscatedName("ab")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1380141937"
   )
   public void method5897() {
      this.cacheLoader.method6238();
   }

   @ObfuscatedName("ac")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-182850463"
   )
   public void method5893(int var1) {
      this.zoomTarget = this.method5895(var1);
   }

   @ObfuscatedName("ad")
   @ObfuscatedSignature(
      signature = "(IIII)V",
      garbageValue = "-1606645646"
   )
   public void method5902(int var1, int var2, int var3) {
      if(this.currentMapArea != null) {
         int[] var4 = this.currentMapArea.method369(var1, var2, var3);
         if(var4 != null) {
            this.method6000(var4[0], var4[1]);
         }

      }
   }

   @ObfuscatedName("ae")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1018233161"
   )
   public int method5967() {
      return this.worldMapDisplayHeight;
   }

   @ObfuscatedName("af")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "52"
   )
   public void method5915(int var1) {
      this.flashingElements = new HashSet();
      this.flashCount = 0;
      this.flashCycle = 0;

      for(int var2 = 0; var2 < WorldMapElement.WorldMapElement_count; ++var2) {
         if(Decimator.method2498(var2) != null && Decimator.method2498(var2).category == var1) {
            this.flashingElements.add(Integer.valueOf(Decimator.method2498(var2).objectId));
         }
      }

   }

   @ObfuscatedName("ag")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "1061983788"
   )
   public void method5921(int var1) {
      if(var1 >= 1) {
         this.cyclesPerFlash = var1;
      }

   }

   @ObfuscatedName("ah")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-46547597"
   )
   public void method5910() {
      this.maxFlashCount = 3;
   }

   @ObfuscatedName("ai")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "1574035017"
   )
   public int method5904() {
      return this.currentMapArea == null?-1:this.centerTileX + this.currentMapArea.method360() * 64;
   }

   @ObfuscatedName("aj")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-996390323"
   )
   public void method5912() {
      this.cyclesPerFlash = 50;
   }

   @ObfuscatedName("ak")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "73"
   )
   public int method5905() {
      return this.currentMapArea == null?-1:this.centerTileY + this.currentMapArea.method362() * 64;
   }

   @ObfuscatedName("al")
   @ObfuscatedSignature(
      signature = "(IIB)V",
      garbageValue = "-63"
   )
   public void method5875(int var1, int var2) {
      if(this.currentMapArea != null) {
         this.method5877(var1 - this.currentMapArea.method360() * 64, var2 - this.currentMapArea.method362() * 64, true);
         this.worldMapTargetX = -1;
         this.worldMapTargetY = -1;
      }
   }

   @ObfuscatedName("am")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "9"
   )
   public int method6009() {
      return this.worldMapDisplayWidth;
   }

   @ObfuscatedName("an")
   @ObfuscatedSignature(
      signature = "(IIIB)V",
      garbageValue = "-123"
   )
   public void method5903(int var1, int var2, int var3) {
      if(this.currentMapArea != null) {
         int[] var4 = this.currentMapArea.method369(var1, var2, var3);
         if(var4 != null) {
            this.method5875(var4[0], var4[1]);
         }

      }
   }

   @ObfuscatedName("ao")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "368218253"
   )
   public void method6000(int var1, int var2) {
      if(this.currentMapArea != null && this.currentMapArea.method350(var1, var2)) {
         this.worldMapTargetX = var1 - this.currentMapArea.method360() * 64;
         this.worldMapTargetY = var2 - this.currentMapArea.method362() * 64;
      }
   }

   @ObfuscatedName("ap")
   @ObfuscatedSignature(
      signature = "(II)F",
      garbageValue = "-1053679854"
   )
   float method5895(int var1) {
      return var1 == 25?1.0F:(var1 == 37?1.5F:(var1 == 50?2.0F:(var1 == 75?3.0F:(var1 == 100?4.0F:8.0F))));
   }

   @ObfuscatedName("aq")
   @ObfuscatedSignature(
      signature = "(I)Lhb;",
      garbageValue = "45330133"
   )
   public Coord method5906() {
      return this.currentMapArea == null?null:this.currentMapArea.method352(this.method5904(), this.method5905());
   }

   @ObfuscatedName("ar")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "-76"
   )
   public int method5896() {
      return 1.0D == (double)this.zoomTarget?25:((double)this.zoomTarget == 1.5D?37:(2.0D == (double)this.zoomTarget?50:((double)this.zoomTarget == 3.0D?75:((double)this.zoomTarget == 4.0D?100:200))));
   }

   @ObfuscatedName("as")
   @ObfuscatedSignature(
      signature = "(II)Lac;",
      garbageValue = "1869906825"
   )
   public WorldMapArea method5899(int var1) {
      Iterator var2 = this.details.values().iterator();

      WorldMapArea var3;
      do {
         if(!var2.hasNext()) {
            return null;
         }

         var3 = (WorldMapArea)var2.next();
      } while(var3.method354() != var1);

      return var3;
   }

   @ObfuscatedName("at")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-20"
   )
   public void method5916() {
      this.flashingElements = null;
   }

   @ObfuscatedName("au")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "43"
   )
   public void method5908(int var1) {
      this.flashingElements = new HashSet();
      this.flashingElements.add(Integer.valueOf(var1));
      this.flashCount = 0;
      this.flashCycle = 0;
   }

   @ObfuscatedName("av")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-582916792"
   )
   public void method5909(int var1) {
      if(var1 >= 1) {
         this.maxFlashCount = var1;
      }

   }

   @ObfuscatedName("ax")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-1475229253"
   )
   public boolean method5898() {
      return this.cacheLoader.method6240();
   }

   @ObfuscatedName("ay")
   @ObfuscatedSignature(
      signature = "(ZI)V",
      garbageValue = "87961823"
   )
   public void method6047(boolean var1) {
      this.perpetualFlash = var1;
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(IIIZI)V",
      garbageValue = "-2106348555"
   )
   public void method5881(int var1, int var2, int var3, boolean var4) {
      WorldMapArea var5 = this.method5918(var1, var2, var3);
      if(var5 == null) {
         if(!var4) {
            return;
         }

         var5 = this.mainMapArea;
      }

      boolean var6 = false;
      if(var5 != this.field3833 || var4) {
         this.field3833 = var5;
         this.method5885(var5);
         var6 = true;
      }

      if(var6 || var4) {
         this.method5968(var1, var2, var3);
      }

   }

   @ObfuscatedName("ba")
   @ObfuscatedSignature(
      signature = "(I)Las;",
      garbageValue = "975625926"
   )
   public AbstractWorldMapIcon method5927() {
      if(!this.cacheLoader.method6240()) {
         return null;
      } else if(!this.worldMapManager.method600()) {
         return null;
      } else {
         HashMap var1 = this.worldMapManager.method601();
         this.field3868 = new LinkedList();
         Iterator var2 = var1.values().iterator();

         while(var2.hasNext()) {
            List var3 = (List)var2.next();
            this.field3868.addAll(var3);
         }

         this.iconIterator = this.field3868.iterator();
         return this.method5928();
      }
   }

   @ObfuscatedName("bc")
   @ObfuscatedSignature(
      signature = "(ILhb;I)Lhb;",
      garbageValue = "1301957345"
   )
   public Coord method5925(int var1, Coord var2) {
      if(!this.cacheLoader.method6240()) {
         return null;
      } else if(!this.worldMapManager.method600()) {
         return null;
      } else if(!this.currentMapArea.method350(var2.x, var2.y)) {
         return null;
      } else {
         HashMap var3 = this.worldMapManager.method601();
         List var4 = (List)var3.get(Integer.valueOf(var1));
         if(var4 != null && !var4.isEmpty()) {
            AbstractWorldMapIcon var5 = null;
            int var6 = -1;
            Iterator var7 = var4.iterator();

            while(true) {
               AbstractWorldMapIcon var8;
               int var11;
               do {
                  if(!var7.hasNext()) {
                     return var5.coord2;
                  }

                  var8 = (AbstractWorldMapIcon)var7.next();
                  int var9 = var8.coord2.x - var2.x;
                  int var10 = var8.coord2.y - var2.y;
                  var11 = var10 * var10 + var9 * var9;
                  if(var11 == 0) {
                     return var8.coord2;
                  }
               } while(var11 >= var6 && var5 != null);

               var5 = var8;
               var6 = var11;
            }
         } else {
            return null;
         }
      }
   }

   @ObfuscatedName("bd")
   @ObfuscatedSignature(
      signature = "(IZI)V",
      garbageValue = "1967881085"
   )
   public void method5996(int var1, boolean var2) {
      if(!var2) {
         this.enabledCategories.add(Integer.valueOf(var1));
      } else {
         this.enabledCategories.remove(Integer.valueOf(var1));
      }

      for(int var3 = 0; var3 < WorldMapElement.WorldMapElement_count; ++var3) {
         if(Decimator.method2498(var3) != null && Decimator.method2498(var3).category == var1) {
            int var4 = Decimator.method2498(var3).objectId;
            if(!var2) {
               this.enabledElementIds.add(Integer.valueOf(var4));
            } else {
               this.enabledElementIds.remove(Integer.valueOf(var4));
            }
         }
      }

      this.method5879();
   }

   @ObfuscatedName("bh")
   @ObfuscatedSignature(
      signature = "(IZS)V",
      garbageValue = "128"
   )
   public void method6010(int var1, boolean var2) {
      if(!var2) {
         this.enabledElements.add(Integer.valueOf(var1));
      } else {
         this.enabledElements.remove(Integer.valueOf(var1));
      }

      this.method5879();
   }

   @ObfuscatedName("bj")
   @ObfuscatedSignature(
      signature = "(IB)Z",
      garbageValue = "0"
   )
   public boolean method5922(int var1) {
      return !this.enabledCategories.contains(Integer.valueOf(var1));
   }

   @ObfuscatedName("bk")
   @ObfuscatedSignature(
      signature = "(IILhb;Lhb;I)V",
      garbageValue = "-94159159"
   )
   public void method5926(int var1, int var2, Coord var3, Coord var4) {
      ScriptEvent var5 = new ScriptEvent();
      WorldMapEvent var6 = new WorldMapEvent(var2, var3, var4);
      var5.method802(new Object[]{var6});
      switch(var1) {
      case 1008:
         var5.method803(10);
         break;
      case 1009:
         var5.method803(11);
         break;
      case 1010:
         var5.method803(12);
         break;
      case 1011:
         var5.method803(13);
         break;
      case 1012:
         var5.method803(14);
      }

      ParamDefinition.method4313(var5);
   }

   @ObfuscatedName("bm")
   @ObfuscatedSignature(
      signature = "(B)Z",
      garbageValue = "7"
   )
   public boolean method5950() {
      return !this.elementsDisabled;
   }

   @ObfuscatedName("bn")
   @ObfuscatedSignature(
      signature = "(I)Las;",
      garbageValue = "497923892"
   )
   public AbstractWorldMapIcon method5928() {
      if(this.iconIterator == null) {
         return null;
      } else {
         AbstractWorldMapIcon var1;
         do {
            if(!this.iconIterator.hasNext()) {
               return null;
            }

            var1 = (AbstractWorldMapIcon)this.iconIterator.next();
         } while(var1.vmethod2277() == -1);

         return var1;
      }
   }

   @ObfuscatedName("bs")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1172651735"
   )
   void method5879() {
      this.field3866.clear();
      this.field3866.addAll(this.enabledElements);
      this.field3866.addAll(this.enabledElementIds);
   }

   @ObfuscatedName("bv")
   @ObfuscatedSignature(
      signature = "(IS)Z",
      garbageValue = "15242"
   )
   public boolean method5907(int var1) {
      return !this.enabledElements.contains(Integer.valueOf(var1));
   }

   @ObfuscatedName("bx")
   @ObfuscatedSignature(
      signature = "(ZI)V",
      garbageValue = "1945794085"
   )
   public void method5917(boolean var1) {
      this.elementsDisabled = !var1;
   }

   @ObfuscatedName("bz")
   @ObfuscatedSignature(
      signature = "(IIIIIIB)V",
      garbageValue = "-67"
   )
   public void method6044(int var1, int var2, int var3, int var4, int var5, int var6) {
      if(this.cacheLoader.method6240()) {
         int var7 = (int)Math.ceil((double)((float)var3 / this.zoom));
         int var8 = (int)Math.ceil((double)((float)var4 / this.zoom));
         List var9 = this.worldMapManager.method611(this.centerTileX - var7 / 2 - 1, this.centerTileY - var8 / 2 - 1, var7 / 2 + this.centerTileX + 1, var8 / 2 + this.centerTileY + 1, var1, var2, var3, var4, var5, var6);
         if(!var9.isEmpty()) {
            Iterator var10 = var9.iterator();

            boolean var13;
            do {
               if(!var10.hasNext()) {
                  return;
               }

               AbstractWorldMapIcon var11 = (AbstractWorldMapIcon)var10.next();
               WorldMapElement var12 = Decimator.method2498(var11.vmethod2277());
               var13 = false;

               for(int var14 = this.menuOpcodes.length - 1; var14 >= 0; --var14) {
                  if(var12.menuActions[var14] != null) {
                     WorldMapData_1.method519(var12.menuActions[var14], var12.menuTargetName, this.menuOpcodes[var14], var11.vmethod2277(), var11.coord1.method3913(), var11.coord2.method3913());
                     var13 = true;
                  }
               }
            } while(!var13);

         }
      }
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(IIII)Lac;",
      garbageValue = "-103201422"
   )
   public WorldMapArea method5918(int var1, int var2, int var3) {
      Iterator var4 = this.details.values().iterator();

      WorldMapArea var5;
      do {
         if(!var4.hasNext()) {
            return null;
         }

         var5 = (WorldMapArea)var4.next();
      } while(!var5.method361(var1, var2, var3));

      return var5;
   }

   public RSWorldMapManager getWorldMapManager() {
      return this.worldMapManager;
   }

   public int getWorldMapX() {
      return this.centerTileX;
   }

   public void setWorldMapPositionTarget(int var1, int var2) {
      this.method6000(var1, var2);
   }

   public int getWorldMapY() {
      return this.centerTileY;
   }

   public Point getWorldMapPosition() {
      RSWorldMapManager var1 = this.getWorldMapManager();
      int var2 = this.getWorldMapX() + var1.getSurfaceOffsetX();
      int var3 = this.getWorldMapY() + var1.getSurfaceOffsetY();
      return new Point(var2, var3);
   }

   public void setWorldMapPositionTarget(WorldPoint var1) {
      this.setWorldMapPositionTarget(var1.getX(), var1.getY());
   }

   public int getWorldMapTargetX() {
      return this.worldMapTargetX;
   }

   public int getWorldMapTargetY() {
      return this.worldMapTargetY;
   }

   public float getWorldMapZoom() {
      return this.zoom;
   }

   public int getWorldMapDisplayWidth() {
      return this.worldMapDisplayWidth;
   }

   public int getWorldMapDisplayHeight() {
      return this.worldMapDisplayHeight;
   }

   public int getWorldMapDisplayX() {
      return this.worldMapDisplayX;
   }

   public int getWorldMapDisplayY() {
      return this.worldMapDisplayY;
   }

   public void setWorldMapPosition(int var1, int var2, boolean var3) {
      this.method5877(var1, var2, var3);
   }

   public RSWorldMapArea getWorldMapData() {
      return this.method5884();
   }

   public void initializeWorldMap(WorldMapData var1) {
      this.method5886((WorldMapArea)var1);
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      signature = "(IIIIIII)Z",
      garbageValue = "-219082293"
   )
   boolean method5945(int var1, int var2, int var3, int var4, int var5, int var6) {
      return this.sprite == null?true:(this.sprite.subWidth == var1 && this.sprite.subHeight == var2?(this.worldMapManager.pixelsPerTile != this.cachedPixelsPerTile?true:(this.field3877 != Client.field1113?true:(var3 <= 0 && var4 <= 0?var3 + var1 < var5 || var2 + var4 < var6:true))):true);
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(I)Lac;",
      garbageValue = "-1891004879"
   )
   public WorldMapArea method5884() {
      return this.currentMapArea;
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      signature = "(IIII)V",
      garbageValue = "283727014"
   )
   void method5968(int var1, int var2, int var3) {
      if(this.currentMapArea != null) {
         int[] var4 = this.currentMapArea.method369(var1, var2, var3);
         if(var4 == null) {
            var4 = this.currentMapArea.method369(this.currentMapArea.method353(), this.currentMapArea.method392(), this.currentMapArea.method414());
         }

         this.method5877(var4[0] - this.currentMapArea.method360() * 64, var4[1] - this.currentMapArea.method362() * 64, true);
         this.worldMapTargetX = -1;
         this.worldMapTargetY = -1;
         this.zoom = this.method5895(this.currentMapArea.method359());
         this.zoomTarget = this.zoom;
         this.field3868 = null;
         this.iconIterator = null;
         this.worldMapManager.method594();
      }
   }

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      signature = "(Lac;I)V",
      garbageValue = "1970020075"
   )
   void method5886(WorldMapArea var1) {
      this.currentMapArea = var1;
      this.worldMapManager = new WorldMapManager(this.mapSceneSprites, this.fonts, this.WorldMap_geographyArchive, this.WorldMap_groundArchive);
      this.cacheLoader.method6239(this.currentMapArea.method356());
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "259268213"
   )
   boolean method5889() {
      return this.worldMapTargetX != -1 && this.worldMapTargetY != -1;
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      signature = "(IIIIII)V",
      garbageValue = "-2026264211"
   )
   public void method5913(int var1, int var2, int var3, int var4, int var5) {
      int[] var6 = new int[4];
      Rasterizer2D.method6412(var6);
      Rasterizer2D.method6474(var1, var2, var3 + var1, var2 + var4);
      Rasterizer2D.method6469(var1, var2, var3, var4, -16777216);
      int var7 = this.cacheLoader.method6241();
      if(var7 < 100) {
         this.method5894(var1, var2, var3, var4, var7);
      } else {
         if(!this.worldMapManager.method600()) {
            this.worldMapManager.method593(this.WorldMap_archive, this.currentMapArea.method356(), Client.isMembersWorld);
            if(!this.worldMapManager.method600()) {
               return;
            }
         }

         if(this.flashingElements != null) {
            ++this.flashCycle;
            if(this.flashCycle % this.cyclesPerFlash == 0) {
               this.flashCycle = 0;
               ++this.flashCount;
            }

            if(this.flashCount >= this.maxFlashCount && !this.perpetualFlash) {
               this.flashingElements = null;
            }
         }

         int var8 = (int)Math.ceil((double)((float)var3 / this.zoom));
         int var9 = (int)Math.ceil((double)((float)var4 / this.zoom));
         this.worldMapManager.method595(this.centerTileX - var8 / 2, this.centerTileY - var9 / 2, var8 / 2 + this.centerTileX, var9 / 2 + this.centerTileY, var1, var2, var3 + var1, var2 + var4);
         boolean var10;
         if(!this.elementsDisabled) {
            var10 = false;
            if(var5 - this.field3863 > 100) {
               this.field3863 = var5;
               var10 = true;
            }

            this.worldMapManager.method599(this.centerTileX - var8 / 2, this.centerTileY - var9 / 2, var8 / 2 + this.centerTileX, var9 / 2 + this.centerTileY, var1, var2, var3 + var1, var2 + var4, this.field3866, this.flashingElements, this.flashCycle, this.cyclesPerFlash, var10);
         }

         this.method5891(var1, var2, var3, var4, var8, var9);
         var10 = Client.staffModLevel >= 2;
         if(var10 && this.showCoord && this.mouseCoord != null) {
            this.font.method5329("Coord: " + this.mouseCoord, Rasterizer2D.Rasterizer2D_xClipStart + 10, Rasterizer2D.Rasterizer2D_yClipStart + 20, 16776960, -1);
         }

         this.worldMapDisplayWidth = var8;
         this.worldMapDisplayHeight = var9;
         this.worldMapDisplayX = var1;
         this.worldMapDisplayY = var2;
         Rasterizer2D.method6429(var6);
      }
   }

   @ObfuscatedName("k")
   @ObfuscatedSignature(
      signature = "(IIIII)V",
      garbageValue = "2080480921"
   )
   public void method5892(int var1, int var2, int var3, int var4) {
      if(this.cacheLoader.method6240()) {
         if(!this.worldMapManager.method600()) {
            this.worldMapManager.method593(this.WorldMap_archive, this.currentMapArea.method356(), Client.isMembersWorld);
            if(!this.worldMapManager.method600()) {
               return;
            }
         }

         this.worldMapManager.method597(var1, var2, var3, var4, this.flashingElements, this.flashCycle, this.cyclesPerFlash);
      }
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "(IIIIIIB)V",
      garbageValue = "-51"
   )
   void method5891(int var1, int var2, int var3, int var4, int var5, int var6) {
      if(StudioGame.field2468 != null) {
         int var7 = 512 / (this.worldMapManager.pixelsPerTile * 2);
         int var8 = var3 + 512;
         int var9 = var4 + 512;
         float var10 = 1.0F;
         var8 = (int)((float)var8 / var10);
         var9 = (int)((float)var9 / var10);
         int var11 = this.method5904() - var5 / 2 - var7;
         int var12 = this.method5905() - var6 / 2 - var7;
         int var13 = var1 - (var11 + var7 - this.minCachedTileX) * this.worldMapManager.pixelsPerTile;
         int var14 = var2 - this.worldMapManager.pixelsPerTile * (var7 - (var12 - this.minCachedTileY));
         if(this.method5945(var8, var9, var13, var14, var3, var4)) {
            if(this.sprite != null && this.sprite.subWidth == var8 && this.sprite.subHeight == var9) {
               Arrays.fill(this.sprite.pixels, 0);
            } else {
               this.sprite = new Sprite(var8, var9);
            }

            this.minCachedTileX = this.method5904() - var5 / 2 - var7;
            this.minCachedTileY = this.method5905() - var6 / 2 - var7;
            this.cachedPixelsPerTile = this.worldMapManager.pixelsPerTile;
            StudioGame.field2468.method4234(this.minCachedTileX, this.minCachedTileY, this.sprite, (float)this.cachedPixelsPerTile / var10);
            this.field3877 = Client.field1113;
            var13 = var1 - (var7 + var11 - this.minCachedTileX) * this.worldMapManager.pixelsPerTile;
            var14 = var2 - this.worldMapManager.pixelsPerTile * (var7 - (var12 - this.minCachedTileY));
         }

         Rasterizer2D.fillRectangleAlpha(var1, var2, var3, var4, 0, 128);
         if(1.0F == var10) {
            this.sprite.method6117(var13, var14, 192);
         } else {
            this.sprite.method6120(var13, var14, (int)(var10 * (float)var8), (int)((float)var9 * var10), 192);
         }
      }

   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(IIZI)V",
      garbageValue = "-1851971614"
   )
   final void method5877(int var1, int var2, boolean var3) {
      this.centerTileX = var1;
      this.centerTileY = var2;
      class33.method680();
      if(var3) {
         this.method5878();
      }

   }
}
