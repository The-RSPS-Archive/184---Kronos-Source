package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("cr")
public class Players {
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = 301065307
   )
   static int Players_pendingUpdateCount;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 842790617
   )
   static int Players_count;
   @ObfuscatedName("q")
   static int[] Players_indices;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "[Lkl;"
   )
   static Buffer[] field1192;
   @ObfuscatedName("u")
   static byte[] field1191;
   @ObfuscatedName("v")
   static byte[] field1200;
   @ObfuscatedName("y")
   static int[] Players_emptyIndices;
   @ObfuscatedName("a")
   static int[] Players_pendingUpdateIndices;
   @ObfuscatedName("b")
   static int[] Players_targetIndices;
   @ObfuscatedName("c")
   static int[] Players_orientations;
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "Lkl;"
   )
   static Buffer field1202;
   @ObfuscatedName("i")
   static int[] Players_regions;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = -662435465
   )
   static int Players_emptyIdxCount;

   static {
      field1200 = new byte[2048];
      field1191 = new byte[2048];
      field1192 = new Buffer[2048];
      Players_count = 0;
      Players_indices = new int[2048];
      Players_emptyIdxCount = 0;
      Players_emptyIndices = new int[2048];
      Players_regions = new int[2048];
      Players_orientations = new int[2048];
      Players_targetIndices = new int[2048];
      Players_pendingUpdateCount = 0;
      Players_pendingUpdateIndices = new int[2048];
      field1202 = new Buffer(new byte[5000]);
   }

   @ObfuscatedName("gw")
   @ObfuscatedSignature(
      signature = "(Lbi;ZI)V",
      garbageValue = "-1924716638"
   )
   static void method2146(Player var0, boolean var1) {
      if(var0 != null && var0.vmethod1611() && !var0.isHidden) {
         var0.isUnanimated = false;
         if((Client.isLowDetail && Players_count > 50 || Players_count > 200) && var1 && var0.readySequence == var0.movementSequence) {
            var0.isUnanimated = true;
         }

         int var2 = var0.x >> 7;
         int var3 = var0.y * 682054857 >> 7;
         if(var2 >= 0 && var2 < 104 && var3 >= 0 && var3 < 104) {
            long var4 = class263.method4846(0, 0, 0, false, var0.index);
            if(var0.model0 != null && Client.cycle >= var0.animationCycleStart && Client.cycle < var0.animationCycleEnd) {
               var0.isUnanimated = false;
               var0.tileHeight = MusicPatchPcmStream.method3798(var0.x, var0.y * 682054857, WorldMapRectangle.plane);
               var0.playerCycle = Client.cycle;
               PacketWriter.scene.method3126(WorldMapRectangle.plane, var0.x, var0.y * 682054857, var0.tileHeight, 60, var0, var0.rotation, var4, var0.field492, var0.field481, var0.field482, var0.field483);
            } else {
               if((var0.x & 127) == 64 && (var0.y * 682054857 & 127) == 64) {
                  if(Client.tileLastDrawnActor[var2][var3] == Client.viewportDrawCount) {
                     return;
                  }

                  Client.tileLastDrawnActor[var2][var3] = Client.viewportDrawCount;
               }

               var0.tileHeight = MusicPatchPcmStream.method3798(var0.x, var0.y * 682054857, WorldMapRectangle.plane);
               var0.playerCycle = Client.cycle;
               PacketWriter.scene.method3125(WorldMapRectangle.plane, var0.x, var0.y * 682054857, var0.tileHeight, 60, var0, var0.rotation, var4, var0.isWalking);
            }
         }
      }

   }
}
