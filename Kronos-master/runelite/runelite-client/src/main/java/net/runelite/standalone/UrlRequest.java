package net.runelite.standalone;

import java.net.URL;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("eh")
public class UrlRequest {
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = -535227307
   )
   public static int PcmPlayer_sampleRate;
   @ObfuscatedName("gq")
   @ObfuscatedSignature(
      signature = "[Llf;"
   )
   static Sprite[] crossSprites;
   @ObfuscatedName("n")
   volatile boolean isDone0;
   @ObfuscatedName("v")
   volatile byte[] response0;
   @ObfuscatedName("z")
   final URL url;

   UrlRequest(URL var1) {
      this.url = var1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)[B",
      garbageValue = "-213988486"
   )
   public byte[] response() {
      return this.response0;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "737522726"
   )
   public boolean isDone() {
      return this.isDone0;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "142413926"
   )
   public static void method2915() {
      Object var0 = ArchiveDiskActionHandler.ArchiveDiskActionHandler_lock;
      synchronized(ArchiveDiskActionHandler.ArchiveDiskActionHandler_lock) {
         if(ArchiveDiskActionHandler.field3167 != 0) {
            ArchiveDiskActionHandler.field3167 = 1;

            try {
               ArchiveDiskActionHandler.ArchiveDiskActionHandler_lock.wait();
            } catch (InterruptedException var3) {
               ;
            }
         }
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkf;II)V",
      garbageValue = "-1347303356"
   )
   static void method2924(PacketBuffer var0, int var1) {
      boolean var2 = var0.method5281(1) == 1;
      if(var2) {
         Players.Players_pendingUpdateIndices[++Players.Players_pendingUpdateCount - 1] = var1;
      }

      int var3 = var0.method5281(2);
      Player var4 = Client.players[var1];
      if(var3 == 0) {
         if(var2) {
            var4.field491 = false;
         } else if(Client.localPlayerIndex == var1) {
            throw new RuntimeException();
         } else {
            Players.Players_regions[var1] = (var4.plane << 28) + (class304.baseY + var4.pathY[0] >> 13) + (class215.baseX + var4.pathX[0] >> 13 << 14);
            if(var4.field695 != -1) {
               Players.Players_orientations[var1] = var4.field695;
            } else {
               Players.Players_orientations[var1] = var4.orientation;
            }

            Players.Players_targetIndices[var1] = var4.targetIndex;
            Client.players[var1] = null;
            Client.cachedPlayersChanged(var1);
            if(var0.method5281(1) != 0) {
               class296.method5453(var0, var1);
            }

         }
      } else {
         int var5;
         int var6;
         int var7;
         if(var3 == 1) {
            var5 = var0.method5281(3);
            var6 = var4.pathX[0];
            var7 = var4.pathY[0];
            if(var5 == 0) {
               --var6;
               --var7;
            } else if(var5 == 1) {
               --var7;
            } else if(var5 == 2) {
               ++var6;
               --var7;
            } else if(var5 == 3) {
               --var6;
            } else if(var5 == 4) {
               ++var6;
            } else if(var5 == 5) {
               --var6;
               ++var7;
            } else if(var5 == 6) {
               ++var7;
            } else if(var5 == 7) {
               ++var6;
               ++var7;
            }

            if(Client.localPlayerIndex != var1 || var4.x >= 1536 && var4.y * 682054857 >= 1536 && var4.x < 11776 && var4.y * 682054857 < 11776) {
               if(var2) {
                  var4.field491 = true;
                  var4.tileX = var6;
                  var4.tileY = var7;
               } else {
                  var4.field491 = false;
                  var4.method1111(var6, var7, Players.field1191[var1]);
               }
            } else {
               var4.method1099(var6, var7);
               var4.field491 = false;
            }

         } else if(var3 == 2) {
            var5 = var0.method5281(4);
            var6 = var4.pathX[0];
            var7 = var4.pathY[0];
            if(var5 == 0) {
               var6 -= 2;
               var7 -= 2;
            } else if(var5 == 1) {
               --var6;
               var7 -= 2;
            } else if(var5 == 2) {
               var7 -= 2;
            } else if(var5 == 3) {
               ++var6;
               var7 -= 2;
            } else if(var5 == 4) {
               var6 += 2;
               var7 -= 2;
            } else if(var5 == 5) {
               var6 -= 2;
               --var7;
            } else if(var5 == 6) {
               var6 += 2;
               --var7;
            } else if(var5 == 7) {
               var6 -= 2;
            } else if(var5 == 8) {
               var6 += 2;
            } else if(var5 == 9) {
               var6 -= 2;
               ++var7;
            } else if(var5 == 10) {
               var6 += 2;
               ++var7;
            } else if(var5 == 11) {
               var6 -= 2;
               var7 += 2;
            } else if(var5 == 12) {
               --var6;
               var7 += 2;
            } else if(var5 == 13) {
               var7 += 2;
            } else if(var5 == 14) {
               ++var6;
               var7 += 2;
            } else if(var5 == 15) {
               var6 += 2;
               var7 += 2;
            }

            if(Client.localPlayerIndex == var1 && (var4.x < 1536 || var4.y * 682054857 < 1536 || var4.x >= 11776 || var4.y * 682054857 >= 11776)) {
               var4.method1099(var6, var7);
               var4.field491 = false;
            } else if(var2) {
               var4.field491 = true;
               var4.tileX = var6;
               var4.tileY = var7;
            } else {
               var4.field491 = false;
               var4.method1111(var6, var7, Players.field1191[var1]);
            }

         } else {
            var5 = var0.method5281(1);
            int var8;
            int var9;
            int var10;
            int var11;
            if(var5 == 0) {
               var6 = var0.method5281(12);
               var7 = var6 >> 10;
               var8 = var6 >> 5 & 31;
               if(var8 > 15) {
                  var8 -= 32;
               }

               var9 = var6 & 31;
               if(var9 > 15) {
                  var9 -= 32;
               }

               var10 = var8 + var4.pathX[0];
               var11 = var9 + var4.pathY[0];
               if(Client.localPlayerIndex == var1 && (var4.x < 1536 || var4.y * 682054857 < 1536 || var4.x >= 11776 || var4.y * 682054857 >= 11776)) {
                  var4.method1099(var10, var11);
                  var4.field491 = false;
               } else if(var2) {
                  var4.field491 = true;
                  var4.tileX = var10;
                  var4.tileY = var11;
               } else {
                  var4.field491 = false;
                  var4.method1111(var10, var11, Players.field1191[var1]);
               }

               var4.plane = (byte)(var7 + var4.plane & 3);
               if(Client.localPlayerIndex == var1) {
                  WorldMapRectangle.plane = var4.plane;
               }

            } else {
               var6 = var0.method5281(30);
               var7 = var6 >> 28;
               var8 = var6 >> 14 & 16383;
               var9 = var6 & 16383;
               var10 = (var8 + class215.baseX + var4.pathX[0] & 16383) - class215.baseX;
               var11 = (var9 + class304.baseY + var4.pathY[0] & 16383) - class304.baseY;
               if(Client.localPlayerIndex != var1 || var4.x >= 1536 && var4.y * 682054857 >= 1536 && var4.x < 11776 && var4.y * 682054857 < 11776) {
                  if(var2) {
                     var4.field491 = true;
                     var4.tileX = var10;
                     var4.tileY = var11;
                  } else {
                     var4.field491 = false;
                     var4.method1111(var10, var11, Players.field1191[var1]);
                  }
               } else {
                  var4.method1099(var10, var11);
                  var4.field491 = false;
               }

               var4.plane = (byte)(var7 + var4.plane & 3);
               if(Client.localPlayerIndex == var1) {
                  WorldMapRectangle.plane = var4.plane;
               }

            }
         }
      }
   }

   @ObfuscatedName("gy")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "1"
   )
   static void method2925() {
      if(Client.combatTargetPlayerIndex >= 0 && Client.players[Client.combatTargetPlayerIndex] != null) {
         Players.method2146(Client.players[Client.combatTargetPlayerIndex], false);
      }

   }

   @ObfuscatedName("iv")
   @ObfuscatedSignature(
      signature = "(Lho;Lit;IIZI)V",
      garbageValue = "-1977494158"
   )
   static final void method2923(Widget var0, ItemDefinition var1, int var2, int var3, boolean var4) {
      String[] var5 = var1.inventoryActions;
      byte var6 = -1;
      String var7 = null;
      if(var5 != null && var5[var3] != null) {
         if(var3 == 0) {
            var6 = 33;
         } else if(var3 == 1) {
            var6 = 34;
         } else if(var3 == 2) {
            var6 = 35;
         } else if(var3 == 3) {
            var6 = 36;
         } else {
            var6 = 37;
         }

         var7 = var5[var3];
      } else if(var3 == 4) {
         var6 = 37;
         var7 = "Drop";
      }

      if(var6 != -1 && var7 != null) {
         AttackOption.method2104(var7, World.method1251(16748608) + var1.name, var6, var1.id, var2, var0.id, var4);
      }

   }
}
