package net.runelite.standalone;

import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("p")
final class GrandExchangeOfferWorldComparator implements Comparator {
   @ObfuscatedName("qd")
   @ObfuscatedSignature(
      signature = "Ldv;"
   )
   static PcmPlayer pcmPlayer1;

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lm;Lm;I)I",
      garbageValue = "-1166584542"
   )
   int method84(GrandExchangeEvent var1, GrandExchangeEvent var2) {
      return var1.world < var2.world?-1:(var2.world == var1.world?0:1);
   }

   public int compare(Object var1, Object var2) {
      return this.method84((GrandExchangeEvent)var1, (GrandExchangeEvent)var2);
   }

   public boolean equals(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Lkl;IIIIIII)V",
      garbageValue = "-109432634"
   )
   static final void method91(Buffer var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7;
      if(var2 >= 0 && var2 < 104 && var3 >= 0 && var3 < 104) {
         Tiles.Tiles_renderFlags[var1][var2][var3] = 0;

         while(true) {
            var7 = var0.readUnsignedByte();
            if(var7 == 0) {
               if(var1 == 0) {
                  Tiles.Tiles_heights[0][var2][var3] = -WorldMapDecoration.method5195(var2 + 932731 + var4, var5 + var3 + 556238) * 8;
               } else {
                  Tiles.Tiles_heights[var1][var2][var3] = Tiles.Tiles_heights[var1 - 1][var2][var3] - 240;
               }
               break;
            }

            if(var7 == 1) {
               int var8 = var0.readUnsignedByte();
               if(var8 == 1) {
                  var8 = 0;
               }

               if(var1 == 0) {
                  Tiles.Tiles_heights[0][var2][var3] = -var8 * 8;
               } else {
                  Tiles.Tiles_heights[var1][var2][var3] = Tiles.Tiles_heights[var1 - 1][var2][var3] - var8 * 8;
               }
               break;
            }

            if(var7 <= 49) {
               Tiles.field522[var1][var2][var3] = var0.readByte();
               DevicePcmPlayerProvider.field156[var1][var2][var3] = (byte)((var7 - 2) / 4);
               class298.field3719[var1][var2][var3] = (byte)(var7 - 2 + var6 & 3);
            } else if(var7 <= 81) {
               Tiles.Tiles_renderFlags[var1][var2][var3] = (byte)(var7 - 49);
            } else {
               Tiles.field540[var1][var2][var3] = (byte)(var7 - 81);
            }
         }
      } else {
         while(true) {
            var7 = var0.readUnsignedByte();
            if(var7 == 0) {
               break;
            }

            if(var7 == 1) {
               var0.readUnsignedByte();
               break;
            }

            if(var7 <= 49) {
               var0.readUnsignedByte();
            }
         }
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(III)Lho;",
      garbageValue = "-217608323"
   )
   public static Widget method93(int var0, int var1) {
      Widget var2 = Canvas.getWidget(var0);
      return var1 == -1?var2:(var2 != null && var2.children != null && var1 < var2.children.length?var2.children[var1]:null);
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(IIIIIZI)Llf;",
      garbageValue = "-366639753"
   )
   public static final Sprite createSprite(int var0, int var1, int var2, int var3, int var4, boolean var5) {
      if(var1 == -1) {
         var4 = 0;
      } else if(var4 == 2 && var1 != 1) {
         var4 = 1;
      }

      long var6 = ((long)var3 << 42) + ((long)var4 << 40) + ((long)var2 << 38) + (long)var0 + ((long)var1 << 16);
      Sprite var8;
      if(!var5) {
         var8 = (Sprite)ItemDefinition.ItemDefinition_cachedSprites.method3032(var6);
         if(var8 != null) {
            return var8;
         }
      }

      ItemDefinition var9 = Occluder.getItemDefinition(var0);
      if(var1 > 1 && var9.countobj != null) {
         int var10 = -1;

         for(int var11 = 0; var11 < 10; ++var11) {
            if(var1 >= var9.countco[var11] && var9.countco[var11] != 0) {
               var10 = var9.countobj[var11];
            }
         }

         if(var10 != -1) {
            var9 = Occluder.getItemDefinition(var10);
         }
      }

      Model var19 = var9.method4575(1, 1336448754);
      if(var19 == null) {
         return null;
      } else {
         Sprite var20 = null;
         if(var9.noteTemplate != -1) {
            var20 = createSprite(var9.note, 10, 1, 0, 0, true);
            if(var20 == null) {
               return null;
            }
         } else if(var9.notedId != -1) {
            var20 = createSprite(var9.unnotedId, var1, var2, var3, 0, false);
            if(var20 == null) {
               return null;
            }
         } else if(var9.placeholderTemplate != -1) {
            var20 = createSprite(var9.placeholder, var1, 0, 0, 0, false);
            if(var20 == null) {
               return null;
            }
         }

         int[] var12 = Rasterizer2D.Rasterizer2D_pixels;
         int var13 = Rasterizer2D.Rasterizer2D_width;
         int var14 = Rasterizer2D.Rasterizer2D_height;
         int[] var15 = new int[4];
         Rasterizer2D.method6412(var15);
         var8 = new Sprite(36, 32);
         Rasterizer2D.method6409(var8.pixels, 36, 32);
         Rasterizer2D.reset();
         Rasterizer3D.method2944();
         Rasterizer3D.method2972(16, 16);
         Rasterizer3D.field1748 = false;
         if(var9.placeholderTemplate != -1) {
            var20.method6159(0, 0);
         }

         int var16 = var9.zoom2d;
         if(var5) {
            var16 = (int)(1.5D * (double)var16);
         } else if(var2 == 2) {
            var16 = (int)(1.04D * (double)var16);
         }

         int var17 = var16 * Rasterizer3D.Rasterizer3D_sine[var9.xan2d] >> 16;
         int var18 = var16 * Rasterizer3D.Rasterizer3D_cosine[var9.xan2d] >> 16;
         var19.method2359();
         var19.method2372(0, var9.yan2d, var9.zan2d, var9.xan2d, var9.offsetX2d, var19.height / 2 + var17 + var9.offsetY2d, var18 + var9.offsetY2d);
         if(var9.notedId != -1) {
            var20.method6159(0, 0);
         }

         if(var2 >= 1) {
            var8.method6104(1);
         }

         if(var2 >= 2) {
            var8.method6104(16777215);
         }

         if(var3 != 0) {
            var8.method6105(var3);
         }

         Rasterizer2D.method6409(var8.pixels, 36, 32);
         if(var9.noteTemplate != -1) {
            var20.method6159(0, 0);
         }

         if(var4 == 1 || var4 == 2 && var9.isStackable == 1) {
            FaceNormal.ItemDefinition_fontPlain11.method5329(class22.method456(var1), 0, 9, 16776960, 1);
         }

         if(!var5) {
            ItemDefinition.ItemDefinition_cachedSprites.method3034(var8, var6);
         }

         Rasterizer2D.method6409(var12, var13, var14);
         Rasterizer2D.method6429(var15);
         Rasterizer3D.method2944();
         Rasterizer3D.field1748 = true;
         return var8;
      }
   }
}
