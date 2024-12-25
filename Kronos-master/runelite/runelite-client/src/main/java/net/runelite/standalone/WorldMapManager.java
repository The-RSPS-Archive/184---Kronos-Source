package net.runelite.standalone;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSWorldMapManager;

@ObfuscatedName("al")
public final class WorldMapManager implements RSWorldMapManager {
   @ObfuscatedName("ai")
   protected static String null_string;
   @ObfuscatedName("n")
   boolean loadStarted;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = 16562041
   )
   int tileY;
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "[[Laa;"
   )
   WorldMapRegion[][] regions;
   @ObfuscatedName("q")
   HashMap field214;
   @ObfuscatedName("r")
   HashMap icons;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Llf;"
   )
   Sprite compositeTextureSprite;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lan;"
   )
   WorldMapAreaData mapAreaData;
   @ObfuscatedName("w")
   @ObfuscatedGetter(
      intValue = 1390818345
   )
   public int pixelsPerTile;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   final AbstractArchive geographyArchive;
   @ObfuscatedName("z")
   boolean loaded;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = -2004318801
   )
   int tileWidth;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = -753174201
   )
   int tileX;
   @ObfuscatedName("c")
   final HashMap fonts;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = 52515237
   )
   int tileHeight;
   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   final AbstractArchive groundArchive;
   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "[Llp;"
   )
   IndexedSprite[] mapSceneSprites;

   @ObfuscatedSignature(
      signature = "([Llp;Ljava/util/HashMap;Lhp;Lhp;)V"
   )
   public WorldMapManager(IndexedSprite[] var1, HashMap var2, AbstractArchive var3, AbstractArchive var4) {
      this.loaded = false;
      this.loadStarted = false;
      this.field214 = new HashMap();
      this.pixelsPerTile = 0;
      this.mapSceneSprites = var1;
      this.fonts = var2;
      this.geographyArchive = var3;
      this.groundArchive = var4;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1715469054"
   )
   public final void method594() {
      this.icons = null;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(IIIIIIIIIII)Ljava/util/List;",
      garbageValue = "642627718"
   )
   public List method611(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      LinkedList var11 = new LinkedList();
      if(!this.loaded) {
         return var11;
      } else {
         WorldMapRectangle var12 = this.method605(var1, var2, var3, var4);
         float var13 = this.method603(var7, var3 - var1);
         int var14 = (int)(var13 * 64.0F);
         int var15 = this.tileX + var1;
         int var16 = var2 + this.tileY;

         for(int var17 = var12.x; var17 < var12.x + var12.width; ++var17) {
            for(int var18 = var12.y; var18 < var12.y + var12.height; ++var18) {
               List var19 = this.regions[var17][var18].method249(var5 + var14 * (this.regions[var17][var18].regionX * 64 - var15) / 64, var8 + var6 - var14 * (this.regions[var17][var18].regionY * 64 - var16 + 64) / 64, var14, var9, var10);
               if(!var19.isEmpty()) {
                  var11.addAll(var19);
               }
            }
         }

         return var11;
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(IIIII)Lj;",
      garbageValue = "1969728750"
   )
   WorldMapRectangle method605(int var1, int var2, int var3, int var4) {
      WorldMapRectangle var5 = new WorldMapRectangle(this);
      int var6 = this.tileX + var1;
      int var7 = var2 + this.tileY;
      int var8 = var3 + this.tileX;
      int var9 = var4 + this.tileY;
      int var10 = var6 / 64;
      int var11 = var7 / 64;
      int var12 = var8 / 64;
      int var13 = var9 / 64;
      var5.width = var12 - var10 + 1;
      var5.height = var13 - var11 + 1;
      var5.x = var10 - this.mapAreaData.method360();
      var5.y = var11 - this.mapAreaData.method362();
      if(var5.x < 0) {
         var5.width += var5.x;
         var5.x = 0;
      }

      if(var5.x > this.regions.length - var5.width) {
         var5.width = this.regions.length - var5.x;
      }

      if(var5.y < 0) {
         var5.height += var5.y;
         var5.y = 0;
      }

      if(var5.y > this.regions[0].length - var5.height) {
         var5.height = this.regions[0].length - var5.y;
      }

      var5.width = Math.min(var5.width, this.regions.length);
      var5.height = Math.min(var5.height, this.regions[0].length);
      return var5;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(IIIILjava/util/HashSet;IIB)V",
      garbageValue = "-113"
   )
   public void method597(int var1, int var2, int var3, int var4, HashSet var5, int var6, int var7) {
      if(this.compositeTextureSprite != null) {
         this.compositeTextureSprite.method6110(var1, var2, var3, var4);
         if(var6 > 0 && var6 % var7 < var7 / 2) {
            if(this.icons == null) {
               this.method622();
            }

            Iterator var8 = var5.iterator();

            while(true) {
               List var10;
               do {
                  if(!var8.hasNext()) {
                     return;
                  }

                  int var9 = ((Integer)var8.next()).intValue();
                  var10 = (List)this.icons.get(Integer.valueOf(var9));
               } while(var10 == null);

               Iterator var11 = var10.iterator();

               while(var11.hasNext()) {
                  AbstractWorldMapIcon var12 = (AbstractWorldMapIcon)var11.next();
                  int var13 = var3 * (var12.coord2.x - this.tileX) / this.tileWidth;
                  int var14 = var4 - (var12.coord2.y - this.tileY) * var4 / this.tileHeight;
                  Rasterizer2D.method6416(var13 + var1, var14 + var2, 2, 16776960, 256);
               }
            }
         }
      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(IIIIIIIILjava/util/HashSet;Ljava/util/HashSet;IIZI)V",
      garbageValue = "-1410717760"
   )
   public final void method599(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, HashSet var9, HashSet var10, int var11, int var12, boolean var13) {
      WorldMapRectangle var14 = this.method605(var1, var2, var3, var4);
      float var15 = this.method603(var7 - var5, var3 - var1);
      int var16 = (int)(64.0F * var15);
      int var17 = this.tileX + var1;
      int var18 = var2 + this.tileY;

      int var19;
      int var20;
      for(var19 = var14.x; var19 < var14.x + var14.width; ++var19) {
         for(var20 = var14.y; var20 < var14.y + var14.height; ++var20) {
            if(var13) {
               this.regions[var19][var20].method302();
            }

            this.regions[var19][var20].method228(var5 + var16 * (this.regions[var19][var20].regionX * 64 - var17) / 64, var8 - var16 * (this.regions[var19][var20].regionY * 64 - var18 + 64) / 64, var16, var9);
         }
      }

      if(var10 != null && var11 > 0) {
         for(var19 = var14.x; var19 < var14.x + var14.width; ++var19) {
            for(var20 = var14.y; var20 < var14.height + var14.y; ++var20) {
               this.regions[var19][var20].method255(var10, var11, var12);
            }
         }
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(IIIIIIIII)V",
      garbageValue = "-99829048"
   )
   public final void method595(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      int[] var9 = Rasterizer2D.Rasterizer2D_pixels;
      int var10 = Rasterizer2D.Rasterizer2D_width;
      int var11 = Rasterizer2D.Rasterizer2D_height;
      int[] var12 = new int[4];
      Rasterizer2D.method6412(var12);
      WorldMapRectangle var13 = this.method605(var1, var2, var3, var4);
      float var14 = this.method603(var7 - var5, var3 - var1);
      int var15 = (int)Math.ceil((double)var14);
      this.pixelsPerTile = var15;
      if(!this.field214.containsKey(Integer.valueOf(var15))) {
         class28 var16 = new class28(var15);
         var16.method556();
         this.field214.put(Integer.valueOf(var15), var16);
      }

      int var23 = var13.width + var13.x - 1;
      int var17 = var13.y + var13.height - 1;

      int var18;
      int var19;
      for(var18 = var13.x; var18 <= var23; ++var18) {
         for(var19 = var13.y; var19 <= var17; ++var19) {
            this.regions[var18][var19].method227(var15, (class28)this.field214.get(Integer.valueOf(var15)), this.mapSceneSprites, this.geographyArchive, this.groundArchive);
         }
      }

      Rasterizer2D.method6409(var9, var10, var11);
      Rasterizer2D.method6429(var12);
      var18 = (int)(var14 * 64.0F);
      var19 = this.tileX + var1;
      int var20 = var2 + this.tileY;

      for(int var21 = var13.x; var21 < var13.width + var13.x; ++var21) {
         for(int var22 = var13.y; var22 < var13.y + var13.height; ++var22) {
            this.regions[var21][var22].method218(var5 + var18 * (this.regions[var21][var22].regionX * 64 - var19) / 64, var8 - var18 * (this.regions[var21][var22].regionY * 64 - var20 + 64) / 64, var18);
         }
      }

   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)Ljava/util/HashMap;",
      garbageValue = "1690655537"
   )
   public HashMap method601() {
      this.method622();
      return this.icons;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lhp;Ljava/lang/String;ZS)V",
      garbageValue = "8475"
   )
   public void method593(AbstractArchive var1, String var2, boolean var3) {
      if(!this.loadStarted) {
         this.loaded = false;
         this.loadStarted = true;
         System.nanoTime();
         int var4 = var1.method4059(WorldMapCacheName.field243.name);
         int var5 = var1.method4039(var4, var2);
         Buffer var6 = new Buffer(var1.getFileData(WorldMapCacheName.field243.name, var2));
         Buffer var7 = new Buffer(var1.getFileData(WorldMapCacheName.field248.name, var2));
         System.nanoTime();
         System.nanoTime();
         this.mapAreaData = new WorldMapAreaData();

         try {
            this.mapAreaData.method666(var6, var7, var5, var3);
         } catch (IllegalStateException var19) {
            return;
         }

         this.mapAreaData.method392();
         this.mapAreaData.method353();
         this.mapAreaData.method414();

         this.tileX = this.mapAreaData.method360() * 64;
         this.tileY = this.mapAreaData.method362() * 64;
         this.tileWidth = (this.mapAreaData.method410() - this.mapAreaData.method360() + 1) * 64;
         this.tileHeight = (this.mapAreaData.method363() - this.mapAreaData.method362() + 1) * 64;
         int var16 = this.mapAreaData.method410() - this.mapAreaData.method360() + 1;
         int var9 = this.mapAreaData.method363() - this.mapAreaData.method362() + 1;
         System.nanoTime();
         System.nanoTime();
         class11.method128();
         this.regions = new WorldMapRegion[var16][var9];
         Iterator var10 = this.mapAreaData.worldMapData0Set.iterator();

         while(var10.hasNext()) {
            WorldMapData_0 var11 = (WorldMapData_0)var10.next();
            int var12 = var11.regionX;
            int var13 = var11.regionY;
            int var14 = var12 - this.mapAreaData.method360();
            int var15 = var13 - this.mapAreaData.method362();
            this.regions[var14][var15] = new WorldMapRegion(var12, var13, this.mapAreaData.method358(), this.fonts);
            this.regions[var14][var15].method219(var11, this.mapAreaData.iconList);
         }

         for(int var17 = 0; var17 < var16; ++var17) {
            for(int var18 = 0; var18 < var9; ++var18) {
               if(this.regions[var17][var18] == null) {
                  this.regions[var17][var18] = new WorldMapRegion(this.mapAreaData.method360() + var17, this.mapAreaData.method362() + var18, this.mapAreaData.method358(), this.fonts);
                  this.regions[var17][var18].method317(this.mapAreaData.worldMapData1Set, this.mapAreaData.iconList);
               }
            }
         }

         System.nanoTime();
         System.nanoTime();
         if(var1.method4108(WorldMapCacheName.field242.name, var2)) {
            byte[] var20 = var1.getFileData(WorldMapCacheName.field242.name, var2);
            this.compositeTextureSprite = class28.method577(var20);
         }

         System.nanoTime();
         var1.method4034();
         var1.method4036();
         this.loaded = true;
      }
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(III)F",
      garbageValue = "-1120188290"
   )
   float method603(int var1, int var2) {
      return ViewportMouse.client.getRenderOverview().getWorldMapZoom();
   }

   public int getSurfaceOffsetX() {
      return this.tileX;
   }

   public int getSurfaceOffsetY() {
      return this.tileY;
   }

   public boolean isLoaded() {
      return this.method600();
   }

   public float getPixelsPerTile(int var1, int var2) {
      return this.method603(var1, var2);
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "442760180"
   )
   void method622() {
      if(this.icons == null) {
         this.icons = new HashMap();
      }

      this.icons.clear();

      for(int var1 = 0; var1 < this.regions.length; ++var1) {
         for(int var2 = 0; var2 < this.regions[var1].length; ++var2) {
            List var3 = this.regions[var1][var2].method336();
            Iterator var4 = var3.iterator();

            while(var4.hasNext()) {
               AbstractWorldMapIcon var5 = (AbstractWorldMapIcon)var4.next();
               if(var5.method694()) {
                  int var6 = var5.vmethod2277();
                  if(!this.icons.containsKey(Integer.valueOf(var6))) {
                     LinkedList var7 = new LinkedList();
                     var7.add(var5);
                     this.icons.put(Integer.valueOf(var6), var7);
                  } else {
                     List var8 = (List)this.icons.get(Integer.valueOf(var6));
                     var8.add(var5);
                  }
               }
            }
         }
      }

   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-1274702917"
   )
   public boolean method600() {
      return this.loaded;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkf;IB)V",
      garbageValue = "74"
   )
   static final void method630(PacketBuffer var0, int var1) {
      int var2 = var0.offset;
      Players.Players_pendingUpdateCount = 0;
      int var3 = 0;
      var0.method5293();

      int var4;
      int var5;
      int var6;
      int var8;
      int var9;
      for(var4 = 0; var4 < Players.Players_count; ++var4) {
         var5 = Players.Players_indices[var4];
         if((Players.field1200[var5] & 1) == 0) {
            if(var3 > 0) {
               --var3;
               Players.field1200[var5] = (byte)(Players.field1200[var5] | 2);
            } else {
               var6 = var0.method5281(1);
               if(var6 == 0) {
                  var8 = var0.method5281(2);
                  if(var8 == 0) {
                     var9 = 0;
                  } else if(var8 == 1) {
                     var9 = var0.method5281(5);
                  } else if(var8 == 2) {
                     var9 = var0.method5281(8);
                  } else {
                     var9 = var0.method5281(11);
                  }

                  var3 = var9;
                  Players.field1200[var5] = (byte)(Players.field1200[var5] | 2);
               } else {
                  UrlRequest.method2924(var0, var5);
               }
            }
         }
      }

      var0.method5279();
      if(var3 != 0) {
         throw new RuntimeException();
      } else {
         var0.method5293();

         for(var4 = 0; var4 < Players.Players_count; ++var4) {
            var5 = Players.Players_indices[var4];
            if((Players.field1200[var5] & 1) != 0) {
               if(var3 > 0) {
                  --var3;
                  Players.field1200[var5] = (byte)(Players.field1200[var5] | 2);
               } else {
                  var6 = var0.method5281(1);
                  if(var6 == 0) {
                     var8 = var0.method5281(2);
                     if(var8 == 0) {
                        var9 = 0;
                     } else if(var8 == 1) {
                        var9 = var0.method5281(5);
                     } else if(var8 == 2) {
                        var9 = var0.method5281(8);
                     } else {
                        var9 = var0.method5281(11);
                     }

                     var3 = var9;
                     Players.field1200[var5] = (byte)(Players.field1200[var5] | 2);
                  } else {
                     UrlRequest.method2924(var0, var5);
                  }
               }
            }
         }

         var0.method5279();
         if(var3 != 0) {
            throw new RuntimeException();
         } else {
            var0.method5293();

            for(var4 = 0; var4 < Players.Players_emptyIdxCount; ++var4) {
               var5 = Players.Players_emptyIndices[var4];
               if((Players.field1200[var5] & 1) != 0) {
                  if(var3 > 0) {
                     --var3;
                     Players.field1200[var5] = (byte)(Players.field1200[var5] | 2);
                  } else {
                     var6 = var0.method5281(1);
                     if(var6 == 0) {
                        var8 = var0.method5281(2);
                        if(var8 == 0) {
                           var9 = 0;
                        } else if(var8 == 1) {
                           var9 = var0.method5281(5);
                        } else if(var8 == 2) {
                           var9 = var0.method5281(8);
                        } else {
                           var9 = var0.method5281(11);
                        }

                        var3 = var9;
                        Players.field1200[var5] = (byte)(Players.field1200[var5] | 2);
                     } else if(class296.method5453(var0, var5)) {
                        Players.field1200[var5] = (byte)(Players.field1200[var5] | 2);
                     }
                  }
               }
            }

            var0.method5279();
            if(var3 != 0) {
               throw new RuntimeException();
            } else {
               var0.method5293();

               for(var4 = 0; var4 < Players.Players_emptyIdxCount; ++var4) {
                  var5 = Players.Players_emptyIndices[var4];
                  if((Players.field1200[var5] & 1) == 0) {
                     if(var3 > 0) {
                        --var3;
                        Players.field1200[var5] = (byte)(Players.field1200[var5] | 2);
                     } else {
                        var6 = var0.method5281(1);
                        if(var6 == 0) {
                           var8 = var0.method5281(2);
                           if(var8 == 0) {
                              var9 = 0;
                           } else if(var8 == 1) {
                              var9 = var0.method5281(5);
                           } else if(var8 == 2) {
                              var9 = var0.method5281(8);
                           } else {
                              var9 = var0.method5281(11);
                           }

                           var3 = var9;
                           Players.field1200[var5] = (byte)(Players.field1200[var5] | 2);
                        } else if(class296.method5453(var0, var5)) {
                           Players.field1200[var5] = (byte)(Players.field1200[var5] | 2);
                        }
                     }
                  }
               }

               var0.method5279();
               if(var3 != 0) {
                  throw new RuntimeException();
               } else {
                  Players.Players_count = 0;
                  Players.Players_emptyIdxCount = 0;

                  Player var16;
                  for(var4 = 1; var4 < 2048; ++var4) {
                     Players.field1200[var4] = (byte)(Players.field1200[var4] >> 1);
                     var16 = Client.players[var4];
                     if(var16 != null) {
                        Players.Players_indices[++Players.Players_count - 1] = var4;
                     } else {
                        Players.Players_emptyIndices[++Players.Players_emptyIdxCount - 1] = var4;
                     }
                  }

                  for(var3 = 0; var3 < Players.Players_pendingUpdateCount; ++var3) {
                     var4 = Players.Players_pendingUpdateIndices[var3];
                     var16 = Client.players[var4];
                     var6 = var0.readUnsignedByte();
                     if((var6 & 1) != 0) {
                        var6 += var0.readUnsignedByte() << 8;
                     }

                     byte var7 = -1;
                     if((var6 & 256) != 0) {
                        for(var8 = 0; var8 < 3; ++var8) {
                           var16.actions[var8] = var0.readString();
                        }
                     }

                     if((var6 & 2048) != 0) {
                        var16.spotAnimation = var0.readUnsignedShort();
                        var16.spotAnimationChanged(-1);
                        var8 = var0.readInt();
                        var16.heightOffset = var8 >> 16;
                        var16.field707 = (var8 & 65535) + Client.cycle;
                        var16.spotAnimationFrame = 0;
                        var16.spotAnimationFrameCycle = 0;
                        if(var16.field707 > Client.cycle) {
                           var16.spotAnimationFrame = -1;
                        }

                        if(var16.spotAnimation == 65535) {
                           var16.spotAnimation = -1;
                           var16.spotAnimationChanged(-1);
                        }
                     }

                     if((var6 & 2) != 0) {
                        var16.overheadText = var0.readString();
                        var16.overheadTextChanged(-1);
                        if(var16.overheadText.charAt(0) == '~') {
                           var16.overheadText = var16.overheadText.substring(1);
                           var16.overheadTextChanged(-1);
                           class217.sendGameMessage(2, var16.username.method5001(), var16.overheadText);
                        } else if(var16 == class215.localPlayer) {
                           class217.sendGameMessage(2, var16.username.method5001(), var16.overheadText);
                        }

                        var16.isAutoChatting = false;
                        var16.overheadTextColor = 0;
                        var16.overheadTextEffect = 0;
                        var16.overheadTextCyclesRemaining = 150;
                     }

                     if((var6 & 8) != 0) {
                        var8 = var0.method5525();
                        byte[] var17 = new byte[var8];
                        Buffer var10 = new Buffer(var17);
                        var0.method5548(var17, 0, var8);
                        Players.field1192[var4] = var10;
                        var16.method1088(var10);
                     }

                     int var11;
                     int var12;
                     int var15;
                     if((var6 & 128) != 0) {
                        var8 = var0.readUnsignedShort();
                        int rank = var0.readUnsignedByte();
                        PlayerType playerType = (PlayerType) NetSocket.getEnumeratedTypeIndex(class210.getPlayerTypes(), rank);
                        boolean var18 = var0.method5525() == 1;
                        var11 = var0.method5525();
                        var12 = var0.offset;
                        if(var16.username != null && var16.appearance != null) {
                           boolean var13 = false;
                           if(playerType.isUser && Tiles.friendSystem.method897(var16.username)) {
                              var13 = true;
                           }

                           if(!var13 && Client.field842 == 0 && !var16.isHidden) {
                              Players.field1202.offset = 0;
                              var0.method5547(Players.field1202.array, 0, var11);
                              Players.field1202.offset = 0;
                              String var14 = AbstractFont.method5328(NetSocket.method3456(class65.method1308(Players.field1202)));
                              var16.overheadText = var14.trim();
                              var16.overheadTextChanged(-1);
                              var16.overheadTextColor = var8 >> 8;
                              var16.overheadTextEffect = var8 & 255;
                              var16.overheadTextCyclesRemaining = 150;
                              var16.isAutoChatting = var18;
                              var16.field682 = var16 != class215.localPlayer && playerType.isUser && "" != Client.field1115 && var14.toLowerCase().indexOf(Client.field1115) == -1;
                              if(playerType.isPrivileged) {
                                 var15 = var18?91:1;
                              } else {
                                 var15 = var18?90:2;
                              }

                              if(playerType.modIcon != -1) {
                                 class217.sendGameMessage(var15, class256.method4655(playerType.modIcon) + var16.username.method5001(), var14);
                              } else {
                                 class217.sendGameMessage(var15, var16.username.method5001(), var14);
                              }
                           }
                        }

                        var0.offset = var11 + var12;
                     }

                     if((var6 & 4096) != 0) {
                        Players.field1191[var4] = var0.readByte();
                     }

                     if((var6 & 16) != 0) {
                        var8 = var0.readLEShortA();
                        if(var8 == 65535) {
                           var8 = -1;
                        }

                        var9 = var0.readUnsignedByte();
                        LoginPacket.method3722(var16, var8, var9);
                     }

                     if((var6 & 512) != 0) {
                        var7 = var0.readByte();
                     }

                     if((var6 & 1024) != 0) {
                        var16.field709 = var0.method5527();
                        var16.field711 = var0.method5529();
                        var16.field710 = var0.method5527();
                        var16.field712 = var0.method5527();
                        var16.field686 = var0.readUnsignedShort() + Client.cycle;
                        var16.field714 = var0.readLEShortA() + Client.cycle;
                        var16.field715 = var0.readShortA();
                        if(var16.field491) {
                           var16.field709 += var16.tileX;
                           var16.field711 += var16.tileY;
                           var16.field710 += var16.tileX;
                           var16.field712 += var16.tileY;
                           var16.pathLength = 0;
                        } else {
                           var16.field709 += var16.pathX[0];
                           var16.field711 += var16.pathY[0];
                           var16.field710 += var16.pathX[0];
                           var16.field712 += var16.pathY[0];
                           var16.pathLength = 1;
                        }

                        var16.field726 = 0;
                     }

                     if((var6 & 4) != 0) {
                        var8 = var0.readByteA();
                        int var19;
                        int var21;
                        int var23;
                        if(var8 > 0) {
                           for(var9 = 0; var9 < var8; ++var9) {
                              var11 = -1;
                              var12 = -1;
                              var21 = -1;
                              var23 = var0.readUnsignedSmart();
                              if(var23 == 32767) {
                                 var23 = var0.readUnsignedSmart();
                                 var12 = var0.readUnsignedSmart();
                                 var11 = var0.readUnsignedSmart();
                                 var21 = var0.readUnsignedSmart();
                              } else if(var23 != 32766) {
                                 var12 = var0.readUnsignedSmart();
                              } else {
                                 var23 = -1;
                              }

                              var19 = var0.readUnsignedSmart();
                              var16.method1434(var23, var12, var11, var21, Client.cycle, var19);
                           }
                        }

                        var9 = var0.method5565();
                        if(var9 > 0) {
                           for(var23 = 0; var23 < var9; ++var23) {
                              var11 = var0.readUnsignedSmart();
                              var12 = var0.readUnsignedSmart();
                              if(var12 != 32767) {
                                 var21 = var0.readUnsignedSmart();
                                 var19 = var0.readByteA();
                                 var15 = var12 > 0?var0.readByteA():var19;
                                 var16.method1429(var11, Client.cycle, var12, var21, var19, var15);
                              } else {
                                 var16.method1430(var11);
                              }
                           }
                        }
                     }

                     if((var6 & 64) != 0) {
                        var16.targetIndex = var0.readLEShortA();
                        var16.interactingChanged(-1);
                        if(var16.targetIndex == 65535) {
                           var16.targetIndex = -1;
                           var16.interactingChanged(-1);
                        }
                     }

                     if((var6 & 32) != 0) {
                        var16.field695 = var0.readUnsignedShort();
                        if(var16.pathLength == 0) {
                           var16.orientation = var16.field695;
                           var16.field695 = -1;
                        }
                     }

                     if(var16.field491) {
                        if(var7 == 127) {
                           var16.method1099(var16.tileX, var16.tileY);
                        } else {
                           byte var20;
                           if(var7 != -1) {
                              var20 = var7;
                           } else {
                              var20 = Players.field1191[var4];
                           }

                           var16.method1111(var16.tileX, var16.tileY, var20);
                        }
                     }
                  }

                  if(var0.offset - var2 != var1) {
                     throw new RuntimeException(var0.offset - var2 + " " + var1);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedName("jg")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "938839327"
   )
   static final void method631(int var0) {
      if(WorldMapData_0.method171(var0)) {
         Widget[] var1 = UserComparator5.Widget_interfaceComponents[var0];

         for(int var2 = 0; var2 < var1.length; ++var2) {
            Widget var3 = var1[var2];
            if(var3 != null) {
               var3.modelFrame = 0;
               var3.modelFrameCycle = 0;
            }
         }

      }
   }

   @ObfuscatedName("jh")
   @ObfuscatedSignature(
      signature = "(IIII)Lbk;",
      garbageValue = "1688623868"
   )
   static final InterfaceParent method626(int var0, int var1, int var2) {
      InterfaceParent var3 = new InterfaceParent();
      var3.group = var1;
      var3.type = var2;
      Client.interfaceParents.put(var3, (long)var0);
      method631(var1);
      Widget var4 = Canvas.getWidget(var0);
      WorldMapSectionType.method116(var4);
      if(Client.meslayerContinueWidget != null) {
         WorldMapSectionType.method116(Client.meslayerContinueWidget);
         Client.meslayerContinueWidget = null;
      }

      SecureRandomFuture.method1521();
      GameShell.method1005(UserComparator5.Widget_interfaceComponents[var0 >> 16], var4, false);
      class186.method3728(var1, 544838520);
      if(Client.rootInterface != -1) {
         class28.method588(Client.rootInterface, 1);
      }

      return var3;
   }
}
