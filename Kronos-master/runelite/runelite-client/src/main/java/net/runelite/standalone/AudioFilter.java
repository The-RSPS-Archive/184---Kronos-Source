package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ds")
public class AudioFilter {
   @ObfuscatedName("p")
   static float[][] field1548;
   @ObfuscatedName("q")
   static int[][] coefficients;
   @ObfuscatedName("y")
   static int forwardMultiplier;
   @ObfuscatedName("m")
   static float field1550;
   @ObfuscatedName("r")
   int[] field1547;
   @ObfuscatedName("u")
   int[][][] field1546;
   @ObfuscatedName("v")
   int[][][] field1552;
   @ObfuscatedName("z")
   int[] pairs;

   static {
      field1548 = new float[2][8];
      coefficients = new int[2][8];
   }

   AudioFilter() {
      this.pairs = new int[2];
      this.field1552 = new int[2][2][4];
      this.field1546 = new int[2][2][4];
      this.field1547 = new int[2];
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(Lkl;Ldm;)V"
   )
   final void method2504(Buffer var1, SoundEnvelope var2) {
      int var3 = var1.readUnsignedByte();
      this.pairs[0] = var3 >> 4;
      this.pairs[1] = var3 & 15;
      if(var3 != 0) {
         this.field1547[0] = var1.readUnsignedShort();
         this.field1547[1] = var1.readUnsignedShort();
         int var4 = var1.readUnsignedByte();

         int var5;
         int var6;
         for(var5 = 0; var5 < 2; ++var5) {
            for(var6 = 0; var6 < this.pairs[var5]; ++var6) {
               this.field1552[var5][0][var6] = var1.readUnsignedShort();
               this.field1546[var5][0][var6] = var1.readUnsignedShort();
            }
         }

         for(var5 = 0; var5 < 2; ++var5) {
            for(var6 = 0; var6 < this.pairs[var5]; ++var6) {
               if((var4 & 1 << var5 * 4 << var6) != 0) {
                  this.field1552[var5][1][var6] = var1.readUnsignedShort();
                  this.field1546[var5][1][var6] = var1.readUnsignedShort();
               } else {
                  this.field1552[var5][1][var6] = this.field1552[var5][0][var6];
                  this.field1546[var5][1][var6] = this.field1546[var5][0][var6];
               }
            }
         }

         if(var4 != 0 || this.field1547[1] != this.field1547[0]) {
            var2.method2445(var1);
         }
      } else {
         int[] var7 = this.field1547;
         this.field1547[1] = 0;
         var7[0] = 0;
      }

   }

   @ObfuscatedName("u")
   int method2503(int var1, float var2) {
      float var3;
      if(var1 == 0) {
         var3 = (float)this.field1547[0] + (float)(this.field1547[1] - this.field1547[0]) * var2;
         var3 *= 0.0030517578F;
         field1550 = (float)Math.pow(0.1D, (double)(var3 / 20.0F));
         forwardMultiplier = (int)(field1550 * 65536.0F);
      }

      if(this.pairs[var1] == 0) {
         return 0;
      } else {
         var3 = this.method2500(var1, 0, var2);
         field1548[var1][0] = -2.0F * var3 * (float)Math.cos((double)this.method2502(var1, 0, var2));
         field1548[var1][1] = var3 * var3;

         int var4;
         for(var4 = 1; var4 < this.pairs[var1]; ++var4) {
            var3 = this.method2500(var1, var4, var2);
            float var5 = -2.0F * var3 * (float)Math.cos((double)this.method2502(var1, var4, var2));
            float var6 = var3 * var3;
            field1548[var1][var4 * 2 + 1] = field1548[var1][var4 * 2 - 1] * var6;
            field1548[var1][var4 * 2] = field1548[var1][var4 * 2 - 1] * var5 + field1548[var1][var4 * 2 - 2] * var6;

            for(int var7 = var4 * 2 - 1; var7 >= 2; --var7) {
               field1548[var1][var7] += field1548[var1][var7 - 1] * var5 + field1548[var1][var7 - 2] * var6;
            }

            field1548[var1][1] += field1548[var1][0] * var5 + var6;
            field1548[var1][0] += var5;
         }

         if(var1 == 0) {
            for(var4 = 0; var4 < this.pairs[0] * 2; ++var4) {
               field1548[0][var4] *= field1550;
            }
         }

         for(var4 = 0; var4 < this.pairs[var1] * 2; ++var4) {
            coefficients[var1][var4] = (int)(field1548[var1][var4] * 65536.0F);
         }

         return this.pairs[var1] * 2;
      }
   }

   @ObfuscatedName("v")
   float method2502(int var1, int var2, float var3) {
      float var4 = (float)this.field1552[var1][0][var2] + var3 * (float)(this.field1552[var1][1][var2] - this.field1552[var1][0][var2]);
      var4 *= 1.2207031E-4F;
      return method2509(var4);
   }

   @ObfuscatedName("z")
   float method2500(int var1, int var2, float var3) {
      float var4 = (float)this.field1546[var1][0][var2] + var3 * (float)(this.field1546[var1][1][var2] - this.field1546[var1][0][var2]);
      var4 *= 0.0015258789F;
      return 1.0F - (float)Math.pow(10.0D, (double)(-var4 / 20.0F));
   }

   @ObfuscatedName("n")
   static float method2509(float var0) {
      float var1 = 32.703197F * (float)Math.pow(2.0D, (double)var0);
      return var1 * 3.1415927F / 11025.0F;
   }
}
