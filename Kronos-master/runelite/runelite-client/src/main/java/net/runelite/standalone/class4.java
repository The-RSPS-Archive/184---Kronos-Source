package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("n")
final class class4 implements class16 {
   @ObfuscatedName("dr")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   static Archive archive0;

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;B)Ljava/lang/Object;",
      garbageValue = "-93"
   )
   public Object vmethod210(Buffer var1) {
      return Integer.valueOf(var1.readInt());
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/Integer;Lkl;I)V",
      garbageValue = "-1770008879"
   )
   void method64(Integer var1, Buffer var2) {
      var2.writeInt(var1.intValue());
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/Object;Lkl;B)V",
      garbageValue = "0"
   )
   public void vmethod213(Object var1, Buffer var2) {
      this.method64((Integer)var1, var2);
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(ZZI)I",
      garbageValue = "1419553069"
   )
   public static int method56(boolean var0, boolean var1) {
      byte var2 = 0;
      int var3 = var2 + NetCache.NetCache_pendingPriorityWritesCount + NetCache.NetCache_pendingPriorityResponsesCount;
      return var3;
   }

   @ObfuscatedName("hh")
   @ObfuscatedSignature(
      signature = "(IIIIIIII)V",
      garbageValue = "557524614"
   )
   static final void method68(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      if(var2 >= 1 && var3 >= 1 && var2 <= 102 && var3 <= 102) {
         if(Client.isLowDetail && var0 != WorldMapRectangle.plane) {
            return;
         }

         long var7 = 0L;
         boolean var9 = true;
         boolean var10 = false;
         boolean var11 = false;
         if(var1 == 0) {
            var7 = PacketWriter.scene.method3140(var0, var2, var3);
         }

         if(var1 == 1) {
            var7 = PacketWriter.scene.method3265(var0, var2, var3);
         }

         if(var1 == 2) {
            var7 = PacketWriter.scene.method3142(var0, var2, var3);
         }

         if(var1 == 3) {
            var7 = PacketWriter.scene.method3143(var0, var2, var3);
         }

         int var12;
         if(0L != var7) {
            var12 = PacketWriter.scene.method3144(var0, var2, var3, var7);
            int var14 = class86.method2114(var7);
            int var15 = var12 & 31;
            int var16 = var12 >> 6 & 3;
            ObjectDefinition var13;
            if(var1 == 0) {
               PacketWriter.scene.method3114(var0, var2, var3);
               var13 = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(var14);
               if(var13.interactType != 0) {
                  Client.collisionMaps[var0].method3338(var2, var3, var15, var16, var13.boolean1);
               }
            }

            if(var1 == 1) {
               PacketWriter.scene.method3230(var0, var2, var3);
            }

            if(var1 == 2) {
               PacketWriter.scene.method3191(var0, var2, var3);
               var13 = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(var14);
               if(var2 + var13.sizeX > 103 || var3 + var13.sizeX > 103 || var2 + var13.sizeY > 103 || var3 + var13.sizeY > 103) {
                  return;
               }

               if(var13.interactType != 0) {
                  Client.collisionMaps[var0].method3339(var2, var3, var13.sizeX, var13.sizeY, var16, var13.boolean1);
               }
            }

            if(var1 == 3) {
               PacketWriter.scene.method3134(var0, var2, var3);
               var13 = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(var14);
               if(var13.interactType == 1) {
                  Client.collisionMaps[var0].method3341(var2, var3);
               }
            }
         }

         if(var4 >= 0) {
            var12 = var0;
            if(var0 < 3 && (Tiles.Tiles_renderFlags[1][var2][var3] & 2) == 2) {
               var12 = var0 + 1;
            }

            class11.method140(var0, var12, var2, var3, var4, var5, var6, PacketWriter.scene, Client.collisionMaps[var0]);
         }
      }

   }

   @ObfuscatedName("hl")
   @ObfuscatedSignature(
      signature = "(Lbi;IIBI)V",
      garbageValue = "916902326"
   )
   static final void method65(Player var0, int var1, int var2, byte var3) {
      int var4 = var0.pathX[0];
      int var5 = var0.pathY[0];
      int var6 = var0.method1089();
      if(var4 >= var6 && var4 < 104 - var6 && var5 >= var6 && var5 < 104 - var6) {
         if(var1 >= var6 && var1 < 104 - var6 && var2 >= var6 && var2 < 104 - var6) {
            int var9 = var0.method1089();
            Client.field1117.approxDestinationX = var1;
            Client.field1117.approxDestinationY = var2;
            Client.field1117.approxDestinationSizeX = 1;
            Client.field1117.approxDestinationSizeY = 1;
            class65 var10 = Client.field1117;
            int var11 = class198.method3815(var4, var5, var9, var10, Client.collisionMaps[var0.plane], true, Client.field1118, Client.field1119);
            if(var11 >= 1) {
               for(int var12 = 0; var12 < var11 - 1; ++var12) {
                  var0.method1100(Client.field1118[var12], Client.field1119[var12], var3);
               }

            }
         }
      }
   }

   @ObfuscatedName("kt")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "2"
   )
   static void method66() {
      if(Client.field1087 && class215.localPlayer != null) {
         int var0 = class215.localPlayer.pathX[0];
         int var1 = class215.localPlayer.pathY[0];
         if(var0 < 0 || var1 < 0 || var0 >= 104 || var1 >= 104) {
            return;
         }

         ObjectSound.oculusOrbFocalPointX = class215.localPlayer.x;
         int var2 = MusicPatchPcmStream.method3798(class215.localPlayer.x, class215.localPlayer.y * 682054857, WorldMapRectangle.plane) - Client.camFollowHeight * -844153885;
         if(var2 < ModelData0.field1785 * -1351160427) {
            ModelData0.field1785 = var2 * -506989123;
         }

         class125.oculusOrbFocalPointY = class215.localPlayer.y * 682054857;
         Client.field1087 = false;
      }

   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "(ZB)V",
      garbageValue = "16"
   )
   public static void method67(boolean var0) {
      if(var0 != class163.ItemDefinition_inMembersWorld) {
         InterfaceParent.method1138();
         class163.ItemDefinition_inMembersWorld = var0;
      }

   }
}
