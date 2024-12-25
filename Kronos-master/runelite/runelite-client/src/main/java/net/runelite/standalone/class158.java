package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ff")
public enum class158 implements Enumerated {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lff;"
   )
   field1953(3, 0),
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lff;"
   )
   field1950(1, 1),
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lff;"
   )
   field1959(2, 2),
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lff;"
   )
   field1951(0, 3);

   @ObfuscatedName("ei")
   static String worldHost;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 1779758939
   )
   final int id;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = -1676935957
   )
   public final int type;

   class158(int var3, int var4) {
      this.type = var3;
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

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(IIB)I",
      garbageValue = "18"
   )
   static final int method3398(int var0, int var1) {
      int var2 = var0 + var1 * 57;
      var2 ^= var2 << 13;
      int var3 = var2 * (var2 * var2 * 15731 + 789221) + 1376312589 & Integer.MAX_VALUE;
      return var3 >> 19 & 255;
   }

   @ObfuscatedName("gz")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-44"
   )
   static final void method3399() {
      for(PendingSpawn var0 = (PendingSpawn)Client.pendingSpawns.method5103(); var0 != null; var0 = (PendingSpawn)Client.pendingSpawns.method5126()) {
         if(var0.hitpoints > 0) {
            --var0.hitpoints;
         }

         boolean var1;
         int var2;
         int var3;
         ObjectDefinition var4;
         if(var0.hitpoints == 0) {
            if(var0.objectId >= 0) {
               var2 = var0.objectId;
               var3 = var0.field631;
               var4 = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(var2);
               if(var3 == 11) {
                  var3 = 10;
               }

               if(var3 >= 5 && var3 <= 8) {
                  var3 = 4;
               }

               var1 = var4.method4738(var3);
               if(!var1) {
                  continue;
               }
            }

            class4.method68(var0.plane, var0.type, var0.x, var0.y, var0.objectId, var0.field632, var0.field631);
            var0.method3497();
         } else {
            if(var0.delay > 0) {
               --var0.delay;
            }

            if(var0.delay == 0 && var0.x >= 1 && var0.y >= 1 && var0.x <= 102 && var0.y <= 102) {
               if(var0.id >= 0) {
                  var2 = var0.id;
                  var3 = var0.field626;
                  var4 = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(var2);
                  if(var3 == 11) {
                     var3 = 10;
                  }

                  if(var3 >= 5 && var3 <= 8) {
                     var3 = 4;
                  }

                  var1 = var4.method4738(var3);
                  if(!var1) {
                     continue;
                  }
               }

               class4.method68(var0.plane, var0.type, var0.x, var0.y, var0.id, var0.orientation, var0.field626);
               var0.delay = -1;
               if(var0.objectId == var0.id && var0.objectId == -1) {
                  var0.method3497();
               } else if(var0.id == var0.objectId && var0.field632 == var0.orientation && var0.field631 == var0.field626) {
                  var0.method3497();
               }
            }
         }
      }

   }
}
