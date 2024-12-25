package net.runelite.standalone;

import java.util.Comparator;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("c")
final class GrandExchangeOfferUnitPriceComparator implements Comparator {
   @ObfuscatedName("s")
   @ObfuscatedGetter(
      intValue = -1987201491
   )
   static int loginBoxCenter;

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lm;Lm;I)I",
      garbageValue = "1379036847"
   )
   int method1463(GrandExchangeEvent var1, GrandExchangeEvent var2) {
      return var1.grandExchangeOffer.unitPrice < var2.grandExchangeOffer.unitPrice?-1:(var2.grandExchangeOffer.unitPrice == var1.grandExchangeOffer.unitPrice?0:1);
   }

   public int compare(Object var1, Object var2) {
      return this.method1463((GrandExchangeEvent)var1, (GrandExchangeEvent)var2);
   }

   public boolean equals(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(II)Lix;",
      garbageValue = "-991827860"
   )
   public static SequenceDefinition method1468(int var0) {
      SequenceDefinition var1 = (SequenceDefinition)SequenceDefinition.SequenceDefinition_cached.method3032((long)var0);
      if(var1 != null) {
         return var1;
      } else {
         byte[] var2 = SequenceDefinition.SequenceDefinition_archive.method4020(12, var0, (short)12545);
         var1 = new SequenceDefinition();
         if(var2 != null) {
            var1.method4658(new Buffer(var2));
         }

         var1.method4687();
         SequenceDefinition.SequenceDefinition_cached.method3034(var1, (long)var0);
         return var1;
      }
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/Object;ZI)[B",
      garbageValue = "264970062"
   )
   public static byte[] method1473(Object var0, boolean var1) {
      if(var0 == null) {
         return null;
      } else if(var0 instanceof byte[]) {
         byte[] var6 = (byte[])((byte[])var0);
         if(var1) {
            int var4 = var6.length;
            byte[] var5 = new byte[var4];
            System.arraycopy(var6, 0, var5, 0, var4);
            return var5;
         } else {
            return var6;
         }
      } else if(var0 instanceof AbstractByteArrayCopier) {
         AbstractByteArrayCopier var2 = (AbstractByteArrayCopier)var0;
         return var2.vmethod3857();
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedName("eg")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "35"
   )
   static final void method1471() {
      Scene.Scene_isLowDetail = false;
      Client.isLowDetail = false;
   }

   @ObfuscatedName("gl")
   @ObfuscatedSignature(
      signature = "(Lbi;B)Z",
      garbageValue = "64"
   )
   static boolean method1466(Player var0) {
      if(Client.drawPlayerNames == 0) {
         return false;
      } else if(class215.localPlayer != var0) {
         boolean var1 = (Client.drawPlayerNames & 4) != 0;
         boolean var2 = var1;
         boolean var3;
         if(!var1) {
            var3 = (Client.drawPlayerNames & 1) != 0;
            var2 = var3 && var0.method1090();
         }

         var3 = var2;
         if(!var2) {
            boolean var4 = (Client.drawPlayerNames & 2) != 0;
            var3 = var4 && var0.method1093();
         }

         return var3;
      } else {
         return class10.method120();
      }
   }
}
