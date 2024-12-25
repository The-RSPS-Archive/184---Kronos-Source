package net.runelite.standalone;

import java.util.HashSet;
import java.util.Set;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gs")
public enum class198 implements Enumerated {
   field2191("", 0, new class191[]{class191.field2145}),
   field2180("", 1, new class191[]{class191.field2144, class191.field2145}),
   field2181("", 2, new class191[]{class191.field2144, class191.field2143, class191.field2145}),
   field2182("", 3, new class191[]{class191.field2144}),
   field2183("", 4),
   field2190("", 5, new class191[]{class191.field2144, class191.field2145}),
   field2185("", 6, new class191[]{class191.field2145}),
   field2186("", 8, new class191[]{class191.field2144, class191.field2145}),
   field2179("", 9, new class191[]{class191.field2144, class191.field2143}),
   field2188("", 10, new class191[]{class191.field2144}),
   field2189("", 11, new class191[]{class191.field2144}),
   field2184("", 12, new class191[]{class191.field2144, class191.field2145}),
   field2187("", 13, new class191[]{class191.field2144});

   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = 1983326673
   )
   final int id;
   @ObfuscatedName("e")
   final Set field2193;

   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I[Lgl;)V"
   )
   class198(String var3, int var4, class191[] var5) {
      this.field2193 = new HashSet();
      this.id = var4;
      class191[] var6 = var5;

      for(int var7 = 0; var7 < var6.length; ++var7) {
         class191 var8 = var6[var7];
         this.field2193.add(var8);
      }

   }

   class198(String var3, int var4) {
      this.field2193 = new HashSet();
      this.id = var4;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(S)I",
      garbageValue = "221"
   )
   public int getId() {
      return this.id;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IILfi;Lfa;I)Z",
      garbageValue = "-1742878989"
   )
   static final boolean method3810(int var0, int var1, RouteStrategy var2, CollisionMap var3) {
      int var4 = var0;
      int var5 = var1;
      byte var6 = 64;
      byte var7 = 64;
      int var8 = var0 - var6;
      int var9 = var1 - var7;
      class170.directions[var6][var7] = 99;
      class170.distances[var6][var7] = 0;
      byte var10 = 0;
      int var11 = 0;
      class170.bufferX[var10] = var0;
      byte var10001 = var10;
      int var18 = var10 + 1;
      class170.bufferY[var10001] = var1;
      int[][] var12 = var3.flags;

      while(var11 != var18) {
         var4 = class170.bufferX[var11];
         var5 = class170.bufferY[var11];
         var11 = var11 + 1 & 4095;
         int var16 = var4 - var8;
         int var17 = var5 - var9;
         int var13 = var4 - var3.xInset;
         int var14 = var5 - var3.yInset;
         if(var2.vmethod3410(1, var4, var5, var3)) {
            UserComparator10.field1808 = var4;
            class170.field2003 = var5;
            return true;
         }

         int var15 = class170.distances[var16][var17] + 1;
         if(var16 > 0 && class170.directions[var16 - 1][var17] == 0 && (var12[var13 - 1][var14] & 19136776) == 0) {
            class170.bufferX[var18] = var4 - 1;
            class170.bufferY[var18] = var5;
            var18 = var18 + 1 & 4095;
            class170.directions[var16 - 1][var17] = 2;
            class170.distances[var16 - 1][var17] = var15;
         }

         if(var16 < 127 && class170.directions[var16 + 1][var17] == 0 && (var12[var13 + 1][var14] & 19136896) == 0) {
            class170.bufferX[var18] = var4 + 1;
            class170.bufferY[var18] = var5;
            var18 = var18 + 1 & 4095;
            class170.directions[var16 + 1][var17] = 8;
            class170.distances[var16 + 1][var17] = var15;
         }

         if(var17 > 0 && class170.directions[var16][var17 - 1] == 0 && (var12[var13][var14 - 1] & 19136770) == 0) {
            class170.bufferX[var18] = var4;
            class170.bufferY[var18] = var5 - 1;
            var18 = var18 + 1 & 4095;
            class170.directions[var16][var17 - 1] = 1;
            class170.distances[var16][var17 - 1] = var15;
         }

         if(var17 < 127 && class170.directions[var16][var17 + 1] == 0 && (var12[var13][var14 + 1] & 19136800) == 0) {
            class170.bufferX[var18] = var4;
            class170.bufferY[var18] = var5 + 1;
            var18 = var18 + 1 & 4095;
            class170.directions[var16][var17 + 1] = 4;
            class170.distances[var16][var17 + 1] = var15;
         }

         if(var16 > 0 && var17 > 0 && class170.directions[var16 - 1][var17 - 1] == 0 && (var12[var13 - 1][var14 - 1] & 19136782) == 0 && (var12[var13 - 1][var14] & 19136776) == 0 && (var12[var13][var14 - 1] & 19136770) == 0) {
            class170.bufferX[var18] = var4 - 1;
            class170.bufferY[var18] = var5 - 1;
            var18 = var18 + 1 & 4095;
            class170.directions[var16 - 1][var17 - 1] = 3;
            class170.distances[var16 - 1][var17 - 1] = var15;
         }

         if(var16 < 127 && var17 > 0 && class170.directions[var16 + 1][var17 - 1] == 0 && (var12[var13 + 1][var14 - 1] & 19136899) == 0 && (var12[var13 + 1][var14] & 19136896) == 0 && (var12[var13][var14 - 1] & 19136770) == 0) {
            class170.bufferX[var18] = var4 + 1;
            class170.bufferY[var18] = var5 - 1;
            var18 = var18 + 1 & 4095;
            class170.directions[var16 + 1][var17 - 1] = 9;
            class170.distances[var16 + 1][var17 - 1] = var15;
         }

         if(var16 > 0 && var17 < 127 && class170.directions[var16 - 1][var17 + 1] == 0 && (var12[var13 - 1][var14 + 1] & 19136824) == 0 && (var12[var13 - 1][var14] & 19136776) == 0 && (var12[var13][var14 + 1] & 19136800) == 0) {
            class170.bufferX[var18] = var4 - 1;
            class170.bufferY[var18] = var5 + 1;
            var18 = var18 + 1 & 4095;
            class170.directions[var16 - 1][var17 + 1] = 6;
            class170.distances[var16 - 1][var17 + 1] = var15;
         }

         if(var16 < 127 && var17 < 127 && class170.directions[var16 + 1][var17 + 1] == 0 && (var12[var13 + 1][var14 + 1] & 19136992) == 0 && (var12[var13 + 1][var14] & 19136896) == 0 && (var12[var13][var14 + 1] & 19136800) == 0) {
            class170.bufferX[var18] = var4 + 1;
            class170.bufferY[var18] = var5 + 1;
            var18 = var18 + 1 & 4095;
            class170.directions[var16 + 1][var17 + 1] = 12;
            class170.distances[var16 + 1][var17 + 1] = var15;
         }
      }

      UserComparator10.field1808 = var4;
      class170.field2003 = var5;
      return false;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "1437512124"
   )
   static void method3814(int var0, int var1) {
      int[] var2 = new int[4];
      int[] var3 = new int[4];
      var2[0] = var0;
      var3[0] = var1;
      int var4 = 1;

      for(int var5 = 0; var5 < 4; ++var5) {
         if(World.World_sortOption1[var5] != var0) {
            var2[var4] = World.World_sortOption1[var5];
            var3[var4] = World.World_sortOption2[var5];
            ++var4;
         }
      }

      World.World_sortOption1 = var2;
      World.World_sortOption2 = var3;
      WorldMapData_0.method170(World.World_worlds, 0, World.World_worlds.length - 1, World.World_sortOption1, World.World_sortOption2);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)[Lge;",
      garbageValue = "-311102379"
   )
   static LoginPacket[] method3818() {
      return new LoginPacket[]{LoginPacket.requestWorldLogin, LoginPacket.field2103, LoginPacket.field2106, LoginPacket.requestWorldReconnect, LoginPacket.handshake};
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IIILfi;Lfa;Z[I[IB)I",
      garbageValue = "-91"
   )
   public static int method3815(int var0, int var1, int var2, RouteStrategy var3, CollisionMap var4, boolean var5, int[] var6, int[] var7) {
      int var9;
      for(int var8 = 0; var8 < 128; ++var8) {
         for(var9 = 0; var9 < 128; ++var9) {
            class170.directions[var8][var9] = 0;
            class170.distances[var8][var9] = 99999999;
         }
      }

      boolean var27;
      if(var2 == 1) {
         var27 = method3810(var0, var1, var3, var4);
      } else if(var2 == 2) {
         var27 = RouteStrategy.method3413(var0, var1, var3, var4);
      } else {
         var27 = class125.method2873(var0, var1, var2, var3, var4);
      }

      var9 = var0 - 64;
      int var10 = var1 - 64;
      int var11 = UserComparator10.field1808;
      int var12 = class170.field2003;
      int var13;
      int var14;
      int var16;
      if(!var27) {
         var13 = Integer.MAX_VALUE;
         var14 = Integer.MAX_VALUE;
         byte var15 = 10;
         var16 = var3.approxDestinationX;
         int var17 = var3.approxDestinationY;
         int var18 = var3.approxDestinationSizeX;
         int var19 = var3.approxDestinationSizeY;

         for(int var20 = var16 - var15; var20 <= var16 + var15; ++var20) {
            for(int var21 = var17 - var15; var21 <= var15 + var17; ++var21) {
               int var22 = var20 - var9;
               int var23 = var21 - var10;
               if(var22 >= 0 && var23 >= 0 && var22 < 128 && var23 < 128 && class170.distances[var22][var23] < 100) {
                  int var24 = 0;
                  if(var20 < var16) {
                     var24 = var16 - var20;
                  } else if(var20 > var16 + var18 - 1) {
                     var24 = var20 - (var18 + var16 - 1);
                  }

                  int var25 = 0;
                  if(var21 < var17) {
                     var25 = var17 - var21;
                  } else if(var21 > var19 + var17 - 1) {
                     var25 = var21 - (var19 + var17 - 1);
                  }

                  int var26 = var25 * var25 + var24 * var24;
                  if(var26 < var13 || var26 == var13 && class170.distances[var22][var23] < var14) {
                     var13 = var26;
                     var14 = class170.distances[var22][var23];
                     var11 = var20;
                     var12 = var21;
                  }
               }
            }
         }

         if(var13 == Integer.MAX_VALUE) {
            return -1;
         }
      }

      if(var0 == var11 && var12 == var1) {
         return 0;
      } else {
         byte var28 = 0;
         class170.bufferX[var28] = var11;
         var13 = var28 + 1;
         class170.bufferY[var28] = var12;

         int var29;
         for(var14 = var29 = class170.directions[var11 - var9][var12 - var10]; var0 != var11 || var12 != var1; var14 = class170.directions[var11 - var9][var12 - var10]) {
            if(var29 != var14) {
               var29 = var14;
               class170.bufferX[var13] = var11;
               class170.bufferY[var13++] = var12;
            }

            if((var14 & 2) != 0) {
               ++var11;
            } else if((var14 & 8) != 0) {
               --var11;
            }

            if((var14 & 1) != 0) {
               ++var12;
            } else if((var14 & 4) != 0) {
               --var12;
            }
         }

         var16 = 0;

         while(var13-- > 0) {
            var6[var16] = class170.bufferX[var13];
            var7[var16++] = class170.bufferY[var13];
            if(var16 >= var6.length) {
               break;
            }
         }

         return var16;
      }
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(IIS)I",
      garbageValue = "5004"
   )
   static final int method3817(int var0, int var1) {
      if(var0 == -1) {
         return 12345678;
      } else {
         var1 = (var0 & 127) * var1 / 128;
         if(var1 < 2) {
            var1 = 2;
         } else if(var1 > 126) {
            var1 = 126;
         }

         return (var0 & 65408) + var1;
      }
   }
}
