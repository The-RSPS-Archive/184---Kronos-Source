package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gx")
public abstract class AbstractByteArrayCopier {
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "([BI)V",
      garbageValue = "-793496359"
   )
   abstract void vmethod3858(byte[] var1);

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(B)[B",
      garbageValue = "-37"
   )
   abstract byte[] vmethod3857();

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)Lbo;",
      garbageValue = "1470103686"
   )
   static World method3875() {
      return World.World_listCount < World.World_count?World.World_worlds[++World.World_listCount - 1]:null;
   }

   @ObfuscatedName("gv")
   @ObfuscatedSignature(
      signature = "(IIIIZI)V",
      garbageValue = "496236852"
   )
   static final void method3874(int var0, int var1, int var2, int var3, boolean var4) {
      if(var2 < 1) {
         var2 = 1;
      }

      if(var3 < 1) {
         var3 = 1;
      }

      int var5 = var3 - 334;
      int var6;
      if(var5 < 0) {
         var6 = Client.field1088;
      } else if(var5 >= 100) {
         var6 = Client.field1095;
      } else {
         var6 = (Client.field1095 - Client.field1088) * var5 / 100 + Client.field1088;
      }

      int var7 = var3 * var6 * 512 / (var2 * 334);
      int var8;
      int var9;
      short var10;
      if(var7 < Client.field1100) {
         var10 = Client.field1100;
         var6 = var10 * var2 * 334 / (var3 * 512);
         if(var6 > Client.field1099) {
            var6 = Client.field1099;
            var8 = var3 * var6 * 512 / (var10 * 334);
            var9 = (var2 - var8) / 2;
            if(var4) {
               Rasterizer2D.method6410();
               Rasterizer2D.method6469(var0, var1, var9, var3, -16777216);
               Rasterizer2D.method6469(var0 + var2 - var9, var1, var9, var3, -16777216);
            }

            var0 += var9;
            var2 -= var9 * 2;
         }
      } else if(var7 > Client.field1101) {
         var10 = Client.field1101;
         var6 = var10 * var2 * 334 / (var3 * 512);
         if(var6 < Client.field894) {
            var6 = Client.field894;
            var8 = var10 * var2 * 334 / (var6 * 512);
            var9 = (var3 - var8) / 2;
            if(var4) {
               Rasterizer2D.method6410();
               Rasterizer2D.method6469(var0, var1, var2, var9, -16777216);
               Rasterizer2D.method6469(var0, var3 + var1 - var9, var2, var9, -16777216);
            }

            var1 += var9;
            var3 -= var9 * 2;
         }
      }

      Client.viewportZoom = var3 * var6 / 334;
      if(var2 != Client.viewportWidth || var3 != Client.viewportHeight) {
         GrandExchangeOfferTotalQuantityComparator.method194(var2, var3);
      }

      Client.viewportOffsetX = var0;
      Client.viewportOffsetY = var1;
      Client.viewportWidth = var2;
      Client.viewportHeight = var3;
   }
}
