package net.runelite.standalone;

import java.awt.FontMetrics;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ii")
public class FloorOverlayDefinition extends DualNode {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable FloorOverlayDefinition_cached;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive FloorOverlayDefinition_archive;
   @ObfuscatedName("ap")
   static FontMetrics loginScreenFontMetrics;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 420813751
   )
   public int secondaryRgb;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = 1507689835
   )
   public int hue;
   @ObfuscatedName("r")
   public boolean hideUnderlay;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -1279665091
   )
   public int texture;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 1255212273
   )
   public int primaryRgb;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = -298509321
   )
   public int lightness;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = 1614583675
   )
   public int secondaryLightness;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 1584668069
   )
   public int secondarySaturation;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 171308105
   )
   public int secondaryHue;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = -898918847
   )
   public int saturation;

   static {
      FloorOverlayDefinition_cached = new EvictingDualNodeHashTable(64);
   }

   public FloorOverlayDefinition() {
      this.primaryRgb = 0;
      this.texture = -1;
      this.hideUnderlay = true;
      this.secondaryRgb = -1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;IB)V",
      garbageValue = "64"
   )
   public void method4357(Buffer var1, int var2) {
      while(true) {
         int var3 = var1.readUnsignedByte();
         if(var3 == 0) {
            return;
         }

         this.method4358(var1, var3, var2);
      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-1969849655"
   )
   void method4356(int var1) {
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
      double var16 = (var8 + var10) / 2.0D;
      if(var8 != var10) {
         if(var16 < 0.5D) {
            var14 = (var10 - var8) / (var8 + var10);
         }

         if(var16 >= 0.5D) {
            var14 = (var10 - var8) / (2.0D - var10 - var8);
         }

         if(var10 == var2) {
            var12 = (var4 - var6) / (var10 - var8);
         } else if(var10 == var4) {
            var12 = (var6 - var2) / (var10 - var8) + 2.0D;
         } else if(var6 == var10) {
            var12 = (var2 - var4) / (var10 - var8) + 4.0D;
         }
      }

      var12 /= 6.0D;
      this.hue = (int)(256.0D * var12);
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

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;III)V",
      garbageValue = "-973510442"
   )
   void method4358(Buffer var1, int var2, int var3) {
      if(var2 == 1) {
         this.primaryRgb = var1.method5500();
      } else if(var2 == 2) {
         this.texture = var1.readUnsignedByte();
      } else if(var2 == 5) {
         this.hideUnderlay = false;
      } else if(var2 == 7) {
         this.secondaryRgb = var1.method5500();
      } else if(var2 == 8) {
         ;
      }

   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "1"
   )
   public void method4365() {
      if(this.secondaryRgb != -1) {
         this.method4356(this.secondaryRgb);
         this.secondaryHue = this.hue;
         this.secondarySaturation = this.saturation;
         this.secondaryLightness = this.lightness;
      }

      this.method4356(this.primaryRgb);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "([Ljava/lang/CharSequence;III)Ljava/lang/String;",
      garbageValue = "137024567"
   )
   public static String method4360(CharSequence[] var0, int var1, int var2) {
      if(var2 == 0) {
         return "";
      } else if(var2 == 1) {
         CharSequence var3 = var0[var1];
         return var3 == null?"null":var3.toString();
      } else {
         int var8 = var2 + var1;
         int var4 = 0;

         for(int var5 = var1; var5 < var8; ++var5) {
            CharSequence var6 = var0[var5];
            if(var6 == null) {
               var4 += 4;
            } else {
               var4 += var6.length();
            }
         }

         StringBuilder var9 = new StringBuilder(var4);

         for(int var10 = var1; var10 < var8; ++var10) {
            CharSequence var7 = var0[var10];
            if(var7 == null) {
               var9.append("null");
            } else {
               var9.append(var7);
            }
         }

         return var9.toString();
      }
   }
}
