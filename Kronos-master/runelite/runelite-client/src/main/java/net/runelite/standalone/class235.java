package net.runelite.standalone;

import java.util.HashMap;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ib")
public class class235 {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lll;"
   )
   Bounds bounds;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 459040833
   )
   int field3125;
   @ObfuscatedName("u")
   int[] field3121;
   @ObfuscatedName("v")
   int[] field3123;
   @ObfuscatedName("z")
   final HashMap spriteMap;

   public class235() {
      this.spriteMap = new HashMap();
      this.bounds = new Bounds(0, 0);
      this.field3123 = new int[2048];
      this.field3121 = new int[2048];
      this.field3125 = 0;
      class263.field3528 = new int[2000];
      int var1 = 0;
      int var2 = 240;

      int var4;
      for(byte var3 = 12; var1 < 16; var2 -= var3) {
         var4 = ScriptFrame.method1310((double)((float)var2 / 360.0F), 0.9998999834060669D, (double)(0.075F + 0.425F * (float)var1 / 16.0F));
         class263.field3528[var1] = var4;
         ++var1;
      }

      var2 = 48;

      for(int var6 = var2 / 6; var1 < class263.field3528.length; var2 -= var6) {
         var4 = var1 * 2;

         for(int var5 = ScriptFrame.method1310((double)((float)var2 / 360.0F), 0.9998999834060669D, 0.5D); var1 < var4 && var1 < class263.field3528.length; ++var1) {
            class263.field3528[var1] = var5;
         }
      }

   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IB)Llf;",
      garbageValue = "-125"
   )
   Sprite method4229(int var1) {
      if(!this.spriteMap.containsKey(Integer.valueOf(var1))) {
         this.method4230(var1);
      }

      return (Sprite)this.spriteMap.get(Integer.valueOf(var1));
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Llf;Llf;Lll;I)V",
      garbageValue = "-1255792875"
   )
   void method4235(Sprite var1, Sprite var2, Bounds var3) {
      if(var3.highX != 0 && var3.highY != 0) {
         int var4 = 0;
         int var5 = 0;
         if(var3.lowX == 0) {
            var4 = var1.subWidth - var3.highX;
         }

         if(var3.lowY == 0) {
            var5 = var1.subHeight - var3.highY;
         }

         int var6 = var4 + var5 * var1.subWidth;
         int var7 = var3.lowX + var2.subWidth * var3.lowY;

         for(int var8 = 0; var8 < var3.highY; ++var8) {
            for(int var9 = 0; var9 < var3.highX; ++var9) {
               int var10001 = var7++;
               var2.pixels[var10001] += var1.pixels[var6++];
            }

            var6 += var1.subWidth - var3.highX;
            var7 += var2.subWidth - var3.highX;
         }

      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(IILlf;FI)V",
      garbageValue = "-177737442"
   )
   public final void method4234(int var1, int var2, Sprite var3, float var4) {
      int var5 = (int)(var4 * 18.0F);
      Sprite var6 = this.method4229(var5);
      int var7 = var5 * 2 + 1;
      Bounds var8 = new Bounds(0, 0, var3.subWidth, var3.subHeight);
      Bounds var9 = new Bounds(0, 0);
      this.bounds.method6252(var7, var7);
      System.nanoTime();

      int var10;
      int var11;
      int var12;
      for(var10 = 0; var10 < this.field3125; ++var10) {
         var11 = this.field3123[var10];
         var12 = this.field3121[var10];
         int var13 = (int)((float)(var11 - var1) * var4) - var5;
         int var14 = (int)((float)var3.subHeight - var4 * (float)(var12 - var2)) - var5;
         this.bounds.method6251(var13, var14);
         this.bounds.method6254(var8, var9);
         this.method4235(var6, var3, var9);
      }

      System.nanoTime();
      System.nanoTime();

      for(var10 = 0; var10 < var3.pixels.length; ++var10) {
         if(var3.pixels[var10] == 0) {
            var3.pixels[var10] = -16777216;
         } else {
            var11 = (var3.pixels[var10] + 64 - 1) / 256;
            if(var11 <= 0) {
               var3.pixels[var10] = -16777216;
            } else {
               if(var11 > class263.field3528.length) {
                  var11 = class263.field3528.length;
               }

               var12 = class263.field3528[var11 - 1];
               var3.pixels[var10] = -16777216 | var12;
            }
         }
      }

      System.nanoTime();
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1070284652"
   )
   public final void method4231() {
      this.field3125 = 0;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(IIB)V",
      garbageValue = "5"
   )
   public final void method4232(int var1, int var2) {
      if(this.field3125 < this.field3123.length) {
         this.field3123[this.field3125] = var1;
         this.field3121[this.field3125] = var2;
         ++this.field3125;
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "1811962046"
   )
   void method4230(int var1) {
      int var2 = var1 * 2 + 1;
      double[] var3 = class298.method5477(0.0D, (double)((float)var1 / 3.0F), var1);
      double var4 = var3[var1] * var3[var1];
      int[] var6 = new int[var2 * var2];
      boolean var7 = false;

      for(int var8 = 0; var8 < var2; ++var8) {
         for(int var9 = 0; var9 < var2; ++var9) {
            int var10 = var6[var9 + var8 * var2] = (int)(256.0D * (var3[var8] * var3[var9] / var4));
            if(!var7 && var10 > 0) {
               var7 = true;
            }
         }
      }

      Sprite var11 = new Sprite(var6, var2, var2);
      this.spriteMap.put(Integer.valueOf(var1), var11);
   }
}
