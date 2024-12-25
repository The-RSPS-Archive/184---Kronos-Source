package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSHealthBar;
import net.runelite.rs.api.RSHealthBarDefinition;
import net.runelite.rs.api.RSIterableNodeDeque;

@ObfuscatedName("cz")
public class HealthBar extends Node implements RSHealthBar {
   @ObfuscatedName("bu")
   @ObfuscatedSignature(
      signature = "Llp;"
   )
   static IndexedSprite worldSelectRightSprite;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Ljs;"
   )
   IterableNodeDeque updates;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lip;"
   )
   HealthBarDefinition definition;

   @ObfuscatedSignature(
      signature = "(Lip;)V"
   )
   HealthBar(HealthBarDefinition var1) {
      this.updates = new IterableNodeDeque();
      this.definition = var1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IB)Lbq;",
      garbageValue = "109"
   )
   HealthBarUpdate method2253(int var1) {
      HealthBarUpdate var2 = (HealthBarUpdate)this.updates.method5044();
      if(var2 != null && var2.cycle <= var1) {
         for(HealthBarUpdate var3 = (HealthBarUpdate)this.updates.method5024(); var3 != null && var3.cycle <= var1; var3 = (HealthBarUpdate)this.updates.method5024()) {
            var2.method3497();
            var2 = var3;
         }

         if(this.definition.int5 + var2.cycleOffset + var2.cycle > var1) {
            return var2;
         } else {
            var2.method3497();
            return null;
         }
      } else {
         return null;
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-783831173"
   )
   boolean method2248() {
      return this.updates.method5026();
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IIIIB)V",
      garbageValue = "1"
   )
   void method2246(int var1, int var2, int var3, int var4) {
      HealthBarUpdate var5 = null;
      int var6 = 0;

      for(HealthBarUpdate var7 = (HealthBarUpdate)this.updates.method5044(); var7 != null; var7 = (HealthBarUpdate)this.updates.method5024()) {
         ++var6;
         if(var7.cycle == var1) {
            var7.method1259(var1, var2, var3, var4);
            return;
         }

         if(var7.cycle <= var1) {
            var5 = var7;
         }
      }

      if(var5 == null) {
         if(var6 < 4) {
            this.updates.method5027(new HealthBarUpdate(var1, var2, var3, var4));
         }

      } else {
         IterableNodeDeque.method5021(new HealthBarUpdate(var1, var2, var3, var4), var5);
         if(var6 >= 4) {
            this.updates.method5044().method3497();
         }

      }
   }

   public RSHealthBarDefinition getDefinition() {
      return this.definition;
   }

   public RSIterableNodeDeque getUpdates() {
      return this.updates;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Llb;III)I",
      garbageValue = "-1932083268"
   )
   static int getParam(IterableNodeHashTable var0, int var1, int var2) {
      if(var0 == null) {
         return var2;
      } else {
         IntegerNode var3 = (IntegerNode)var0.method6061((long)var1);
         return var3 == null?var2:var3.integer;
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(IZI)Ljava/lang/String;",
      garbageValue = "1634227663"
   )
   public static String method2255(int var0, boolean var1) {
      if(var1 && var0 >= 0) {
         int var3 = var0;
         String var2;
         if(var1 && var0 >= 0) {
            int var4 = 2;

            for(int var5 = var0 / 10; var5 != 0; ++var4) {
               var5 /= 10;
            }

            char[] var6 = new char[var4];
            var6[0] = '+';

            for(int var7 = var4 - 1; var7 > 0; --var7) {
               int var8 = var3;
               var3 /= 10;
               int var9 = var8 - var3 * 10;
               if(var9 >= 10) {
                  var6[var7] = (char)(var9 + 87);
               } else {
                  var6[var7] = (char)(var9 + 48);
               }
            }

            var2 = new String(var6);
         } else {
            var2 = Integer.toString(var0, 10);
         }

         return var2;
      } else {
         return Integer.toString(var0);
      }
   }

   @ObfuscatedName("fr")
   @ObfuscatedSignature(
      signature = "(Lby;I)V",
      garbageValue = "1394806934"
   )
   static final void method2247(Actor var0) {
      if(var0.field714 == Client.cycle || var0.sequence == -1 || var0.sequenceDelay != 0 || var0.sequenceFrameCycle + 1 > GrandExchangeOfferUnitPriceComparator.method1468(var0.sequence).frameLengths[var0.sequenceFrame]) {
         int var1 = var0.field714 - var0.field686;
         int var2 = Client.cycle - var0.field686;
         int var3 = var0.field709 * 128 + var0.size * 64;
         int var4 = var0.field711 * 128 + var0.size * 64;
         int var5 = var0.field710 * 128 + var0.size * 64;
         int var6 = var0.field712 * 128 + var0.size * 64;
         var0.x = (var2 * var5 + var3 * (var1 - var2)) / var1;
         var0.y = (var6 * var2 + var4 * (var1 - var2)) / var1 * -944175751;
      }

      var0.field687 = 0;
      var0.orientation = var0.field715;
      var0.rotation = var0.orientation;
   }
}
