package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("k")
public class WorldMapDecoration {
   @ObfuscatedName("kw")
   @ObfuscatedGetter(
      intValue = -1785453027
   )
   static int menuHeight;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 1850610867
   )
   final int decoration;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1741054453
   )
   final int rotation;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -865323619
   )
   final int objectDefinitionId;

   WorldMapDecoration(int var1, int var2, int var3) {
      this.objectDefinitionId = var1;
      this.decoration = var2;
      this.rotation = var3;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-917346374"
   )
   static void method5197() {
      Messages.Messages_channels.clear();
      Messages.Messages_hashTable.method6063();
      Messages.Messages_queue.method4897();
      Messages.Messages_count = 0;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lie;IIIBZI)V",
      garbageValue = "248169639"
   )
   static void method5198(Archive var0, int var1, int var2, int var3, byte var4, boolean var5) {
      long var6 = (long)((var1 << 16) + var2);
      NetFileRequest var8 = (NetFileRequest)NetCache.NetCache_pendingPriorityWrites.method6346(var6);
      if(var8 == null) {
         var8 = (NetFileRequest)NetCache.NetCache_pendingPriorityResponses.method6346(var6);
         if(var8 == null) {
            var8 = (NetFileRequest)NetCache.NetCache_pendingWrites.method6346(var6);
            if(var8 != null) {
               if(var5) {
                  var8.method3491();
                  NetCache.NetCache_pendingPriorityWrites.put(var8, var6);
                  --NetCache.NetCache_pendingWritesCount;
                  ++NetCache.NetCache_pendingPriorityWritesCount;
               }

            } else {
               if(!var5) {
                  var8 = (NetFileRequest)NetCache.NetCache_pendingResponses.method6346(var6);
                  if(var8 != null) {
                     return;
                  }
               }

               var8 = new NetFileRequest();
               var8.archive = var0;
               var8.crc = var3;
               var8.padding = var4;
               if(var5) {
                  NetCache.NetCache_pendingPriorityWrites.put(var8, var6);
                  ++NetCache.NetCache_pendingPriorityWritesCount;
               } else {
                  NetCache.NetCache_pendingWritesQueue.method4249(var8);
                  NetCache.NetCache_pendingWrites.put(var8, var6);
                  ++NetCache.NetCache_pendingWritesCount;
               }

            }
         }
      }
   }

   @ObfuscatedName("z")
   static boolean method5194(long var0) {
      return Client.method2043(var0) == 2;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(III)I",
      garbageValue = "-9791286"
   )
   static int method5192(int var0, int var1) {
      ItemContainer var2 = (ItemContainer)ItemContainer.itemContainers.method6346((long)var0);
      return var2 == null?-1:(var1 >= 0 && var1 < var2.ids.length?var2.ids[var1]:-1);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)[Lgw;",
      garbageValue = "1443955115"
   )
   public static class202[] method5196() {
      return new class202[]{class202.field2329, class202.field2330, class202.field2328, class202.field2327, class202.field2325, class202.field2326, class202.field2331, class202.field2332, class202.field2333, class202.field2334};
   }

   @ObfuscatedName("ap")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "71"
   )
   protected static final void method5191() {
      GameShell.clock.vmethod3510();

      int var0;
      for(var0 = 0; var0 < 32; ++var0) {
         GameShell.graphicsTickTimes[var0] = 0L;
      }

      for(var0 = 0; var0 < 32; ++var0) {
         GameShell.clientTickTimes[var0] = 0L;
      }

      GameShell.gameCyclesToDo = 0;
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(III)I",
      garbageValue = "2081647418"
   )
   static final int method5195(int var0, int var1) {
      int var2 = WorldMapLabelSize.method3527(45365 + var0, var1 + 91923, 4) - 128 + (WorldMapLabelSize.method3527(10294 + var0, 37821 + var1, 2) - 128 >> 1) + (WorldMapLabelSize.method3527(var0, var1, 1) - 128 >> 2);
      var2 = (int)((double)var2 * 0.3D) + 35;
      if(var2 < 10) {
         var2 = 10;
      } else if(var2 > 60) {
         var2 = 60;
      }

      return var2;
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "(Lcf;B)V",
      garbageValue = "0"
   )
   public static final void method5193(PcmPlayerProvider var0) {
      PcmPlayer.pcmPlayerProvider = var0;
   }
}
