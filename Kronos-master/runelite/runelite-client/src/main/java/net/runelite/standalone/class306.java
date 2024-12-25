package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ks")
public class class306 {
   @ObfuscatedName("n")
   static char[] field3758;
   @ObfuscatedName("u")
   static int[] field3755;
   @ObfuscatedName("v")
   static char[] field3757;
   @ObfuscatedName("z")
   static char[] field3756;

   static {
      field3756 = new char[64];

      int var0;
      for(var0 = 0; var0 < 26; ++var0) {
         field3756[var0] = (char)(var0 + 65);
      }

      for(var0 = 26; var0 < 52; ++var0) {
         field3756[var0] = (char)(var0 + 97 - 26);
      }

      for(var0 = 52; var0 < 62; ++var0) {
         field3756[var0] = (char)(var0 + 48 - 52);
      }

      field3756[62] = '+';
      field3756[63] = '/';
      field3758 = new char[64];

      for(var0 = 0; var0 < 26; ++var0) {
         field3758[var0] = (char)(var0 + 65);
      }

      for(var0 = 26; var0 < 52; ++var0) {
         field3758[var0] = (char)(var0 + 97 - 26);
      }

      for(var0 = 52; var0 < 62; ++var0) {
         field3758[var0] = (char)(var0 + 48 - 52);
      }

      field3758[62] = '*';
      field3758[63] = '-';
      field3757 = new char[64];

      for(var0 = 0; var0 < 26; ++var0) {
         field3757[var0] = (char)(var0 + 65);
      }

      for(var0 = 26; var0 < 52; ++var0) {
         field3757[var0] = (char)(var0 + 97 - 26);
      }

      for(var0 = 52; var0 < 62; ++var0) {
         field3757[var0] = (char)(var0 + 48 - 52);
      }

      field3757[62] = '-';
      field3757[63] = '_';
      field3755 = new int[128];

      for(var0 = 0; var0 < field3755.length; ++var0) {
         field3755[var0] = -1;
      }

      for(var0 = 65; var0 <= 90; ++var0) {
         field3755[var0] = var0 - 65;
      }

      for(var0 = 97; var0 <= 122; ++var0) {
         field3755[var0] = var0 - 97 + 26;
      }

      for(var0 = 48; var0 <= 57; ++var0) {
         field3755[var0] = var0 - 48 + 52;
      }

      int[] var2 = field3755;
      field3755[43] = 62;
      var2[42] = 62;
      int[] var1 = field3755;
      field3755[47] = 63;
      var1[45] = 63;
   }

   @ObfuscatedName("gu")
   @ObfuscatedSignature(
      signature = "(IIZI)V",
      garbageValue = "-1781394642"
   )
   static final void method5763(int var0, int var1, boolean var2) {
      if(!var2 || var0 != ServerPacket.field2272 || MusicPatchNode.field2216 != var1) {
         ServerPacket.field2272 = var0;
         MusicPatchNode.field2216 = var1;
         MouseRecorder.method1207(25);
         WorldMapSprite.method784("Loading - please wait.", true);
         int var3 = class215.baseX;
         int var4 = class304.baseY;
         class215.baseX = (var0 - 6) * 8;
         class304.baseY = (var1 - 6) * 8;
         int var5 = class215.baseX - var3;
         int var6 = class304.baseY - var4;
         var3 = class215.baseX;
         var4 = class304.baseY;

         int var7;
         int var9;
         for(var7 = 0; var7 < 32768; ++var7) {
            NPC var8 = Client.npcs[var7];
            if(var8 != null) {
               for(var9 = 0; var9 < 10; ++var9) {
                  var8.pathX[var9] -= var5;
                  var8.pathY[var9] -= var6;
               }

               var8.x -= var5 * 128;
               var8.y -= var6 * -595411840;
            }
         }

         for(var7 = 0; var7 < 2048; ++var7) {
            Player var21 = Client.players[var7];
            if(var21 != null) {
               for(var9 = 0; var9 < 10; ++var9) {
                  var21.pathX[var9] -= var5;
                  var21.pathY[var9] -= var6;
               }

               var21.x -= var5 * 128;
               var21.y -= var6 * -595411840;
            }
         }

         byte var20 = 0;
         byte var18 = 104;
         byte var22 = 1;
         if(var5 < 0) {
            var20 = 103;
            var18 = -1;
            var22 = -1;
         }

         byte var10 = 0;
         byte var11 = 104;
         byte var12 = 1;
         if(var6 < 0) {
            var10 = 103;
            var11 = -1;
            var12 = -1;
         }

         int var14;
         for(int var13 = var20; var13 != var18; var13 += var22) {
            for(var14 = var10; var14 != var11; var14 += var12) {
               int var15 = var5 + var13;
               int var16 = var6 + var14;

               for(int var17 = 0; var17 < 4; ++var17) {
                  if(var15 >= 0 && var16 >= 0 && var15 < 104 && var16 < 104) {
                     Client.groundItems[var17][var13][var14] = Client.groundItems[var17][var15][var16];
                  } else {
                     Client.groundItems[var17][var13][var14] = null;
                  }
               }
            }
         }

         for(PendingSpawn var19 = (PendingSpawn)Client.pendingSpawns.method5103(); var19 != null; var19 = (PendingSpawn)Client.pendingSpawns.method5126()) {
            var19.x -= var5;
            var19.y -= var6;
            if(var19.x < 0 || var19.y < 0 || var19.x >= 104 || var19.y >= 104) {
               var19.method3497();
            }
         }

         if(Client.destinationX != 0) {
            Client.destinationX -= var5;
            Client.destinationY -= var6;
         }

         Client.soundEffectCount = 0;
         Client.queuedSoundEffectCountChanged(-1);
         Client.isCameraLocked = false;
         GrandExchangeOfferOwnWorldComparator.cameraX -= var5 << 7;
         WorldMapIcon_1.cameraZ -= var6 << 7;
         ObjectSound.oculusOrbFocalPointX -= var5 << 7;
         class125.oculusOrbFocalPointY -= var6 << 7;
         Client.field851 = -1;
         Client.graphicsObjects.method5104();
         Client.projectiles.method5104();

         for(var14 = 0; var14 < 4; ++var14) {
            Client.collisionMaps[var14].method3352();
         }

      }
   }
}
