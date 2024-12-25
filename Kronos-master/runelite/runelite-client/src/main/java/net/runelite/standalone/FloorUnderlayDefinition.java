package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("iu")
public class FloorUnderlayDefinition extends DualNode {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable FloorUnderlayDefinition_cached;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive FloorUnderlayDefinition_archive;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 1084903515
   )
   public int lightness;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = 568958993
   )
   public int hueMultiplier;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 922832793
   )
   public int saturation;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -2031878355
   )
   public int hue;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1923870903
   )
   int rgb;

   static {
      FloorUnderlayDefinition_cached = new EvictingDualNodeHashTable(64);
   }

   FloorUnderlayDefinition() {
      this.rgb = 0;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-2090929650"
   )
   void method4602() {
      this.method4607(this.rgb);
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "307020215"
   )
   void method4607(int var1) {
      double var2 = (double)(var1 >> 16 & 255) / 256.0D;
      double var4 = (double)(var1 >> 8 & 255) / 256.0D;
      double var6 = (double)(var1 & 255) / 256.0D;
      double var8 = var2;
      if(var4 < var2) {
         var8 = var4;
      }

      if(var6 < var8) {
         var8 = var6;
      }

      double var10 = var2;
      if(var4 > var2) {
         var10 = var4;
      }

      if(var6 > var10) {
         var10 = var6;
      }

      double var12 = 0.0D;
      double var14 = 0.0D;
      double var16 = (var10 + var8) / 2.0D;
      if(var10 != var8) {
         if(var16 < 0.5D) {
            var14 = (var10 - var8) / (var8 + var10);
         }

         if(var16 >= 0.5D) {
            var14 = (var10 - var8) / (2.0D - var10 - var8);
         }

         if(var10 == var2) {
            var12 = (var4 - var6) / (var10 - var8);
         } else if(var10 == var4) {
            var12 = 2.0D + (var6 - var2) / (var10 - var8);
         } else if(var6 == var10) {
            var12 = 4.0D + (var2 - var4) / (var10 - var8);
         }
      }

      var12 /= 6.0D;
      this.saturation = (int)(256.0D * var14);
      this.lightness = (int)(var16 * 256.0D);
      if(this.saturation < 0) {
         this.saturation = 0;
      } else if(this.saturation > 255) {
         this.saturation = 255;
      }

      if(this.lightness < 0) {
         this.lightness = 0;
      } else if(this.lightness > 255) {
         this.lightness = 255;
      }

      if(var16 > 0.5D) {
         this.hueMultiplier = (int)(512.0D * var14 * (1.0D - var16));
      } else {
         this.hueMultiplier = (int)(512.0D * var16 * var14);
      }

      if(this.hueMultiplier < 1) {
         this.hueMultiplier = 1;
      }

      this.hue = (int)((double)this.hueMultiplier * var12);
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lkl;III)V",
      garbageValue = "-1673078715"
   )
   void method4612(Buffer var1, int var2, int var3) {
      if(var2 == 1) {
         this.rgb = var1.method5500();
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "-840924474"
   )
   void method4603(Buffer var1, int var2) {
      while(true) {
         int var3 = var1.readUnsignedByte();
         if(var3 == 0) {
            return;
         }

         this.method4612(var1, var3, var2);
      }
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IIB)I",
      garbageValue = "1"
   )
   static int method4617(int var0, int var1) {
      ItemContainer var2 = (ItemContainer)ItemContainer.itemContainers.method6346((long)var0);
      return var2 == null?0:(var1 >= 0 && var1 < var2.quantities.length?var2.quantities[var1]:0);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)[Lcn;",
      garbageValue = "126"
   )
   static AttackOption[] method4604() {
      return new AttackOption[]{AttackOption.AttackOption_hidden, AttackOption.AttackOption_leftClickWhereAvailable, AttackOption.AttackOption_alwaysRightClick, AttackOption.AttackOption_dependsOnCombatLevels};
   }
}
