package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("cx")
public class VorbisCodebook {
   @ObfuscatedName("n")
   int entries;
   @ObfuscatedName("p")
   int[] keys;
   @ObfuscatedName("r")
   float[][] field1249;
   @ObfuscatedName("u")
   int[] field1246;
   @ObfuscatedName("v")
   int[] lengthMap;
   @ObfuscatedName("z")
   int dimensions;

   VorbisCodebook() {
      VorbisSample.method2135(24);
      this.dimensions = VorbisSample.method2135(16);
      this.entries = VorbisSample.method2135(24);
      this.lengthMap = new int[this.entries];
      boolean var1 = VorbisSample.method2126() != 0;
      int var2;
      int var3;
      int var5;
      if(var1) {
         var2 = 0;

         for(var3 = VorbisSample.method2135(5) + 1; var2 < this.entries; ++var3) {
            int var4 = VorbisSample.method2135(TileItem.method1605(this.entries - var2));

            for(var5 = 0; var5 < var4; ++var5) {
               this.lengthMap[var2++] = var3;
            }
         }
      } else {
         boolean var14 = VorbisSample.method2126() != 0;

         for(var3 = 0; var3 < this.entries; ++var3) {
            if(var14 && VorbisSample.method2126() == 0) {
               this.lengthMap[var3] = 0;
            } else {
               this.lengthMap[var3] = VorbisSample.method2135(5) + 1;
            }
         }
      }

      this.method2226();
      var2 = VorbisSample.method2135(4);
      if(var2 > 0) {
         float var15 = VorbisSample.method2139(VorbisSample.method2135(32));
         float var16 = VorbisSample.method2139(VorbisSample.method2135(32));
         var5 = VorbisSample.method2135(4) + 1;
         boolean var6 = VorbisSample.method2126() != 0;
         int var7;
         if(var2 == 1) {
            var7 = method2225(this.entries, this.dimensions);
         } else {
            var7 = this.entries * this.dimensions;
         }

         this.field1246 = new int[var7];

         int var8;
         for(var8 = 0; var8 < var7; ++var8) {
            this.field1246[var8] = VorbisSample.method2135(var5);
         }

         this.field1249 = new float[this.entries][this.dimensions];
         float var9;
         int var10;
         int var11;
         if(var2 == 1) {
            for(var8 = 0; var8 < this.entries; ++var8) {
               var9 = 0.0F;
               var10 = 1;

               for(var11 = 0; var11 < this.dimensions; ++var11) {
                  int var12 = var8 / var10 % var7;
                  float var13 = (float)this.field1246[var12] * var16 + var15 + var9;
                  this.field1249[var8][var11] = var13;
                  if(var6) {
                     var9 = var13;
                  }

                  var10 *= var7;
               }
            }
         } else {
            for(var8 = 0; var8 < this.entries; ++var8) {
               var9 = 0.0F;
               var10 = var8 * this.dimensions;

               for(var11 = 0; var11 < this.dimensions; ++var11) {
                  float var17 = (float)this.field1246[var10] * var16 + var15 + var9;
                  this.field1249[var8][var11] = var17;
                  if(var6) {
                     var9 = var17;
                  }

                  ++var10;
               }
            }
         }
      }

   }

   @ObfuscatedName("n")
   void method2226() {
      int[] var1 = new int[this.entries];
      int[] var2 = new int[33];

      int var3;
      int var4;
      int var5;
      int var6;
      int var7;
      int var8;
      int var10;
      for(var3 = 0; var3 < this.entries; ++var3) {
         var4 = this.lengthMap[var3];
         if(var4 != 0) {
            var5 = 1 << 32 - var4;
            var6 = var2[var4];
            var1[var3] = var6;
            int var12;
            if((var6 & var5) != 0) {
               var7 = var2[var4 - 1];
            } else {
               var7 = var6 | var5;

               for(var8 = var4 - 1; var8 >= 1; --var8) {
                  var12 = var2[var8];
                  if(var12 != var6) {
                     break;
                  }

                  var10 = 1 << 32 - var8;
                  if((var12 & var10) != 0) {
                     var2[var8] = var2[var8 - 1];
                     break;
                  }

                  var2[var8] = var12 | var10;
               }
            }

            var2[var4] = var7;

            for(var8 = var4 + 1; var8 <= 32; ++var8) {
               var12 = var2[var8];
               if(var12 == var6) {
                  var2[var8] = var7;
               }
            }
         }
      }

      this.keys = new int[8];
      int var11 = 0;

      for(var3 = 0; var3 < this.entries; ++var3) {
         var4 = this.lengthMap[var3];
         if(var4 != 0) {
            var5 = var1[var3];
            var6 = 0;

            for(var7 = 0; var7 < var4; ++var7) {
               var8 = Integer.MIN_VALUE >>> var7;
               if((var5 & var8) != 0) {
                  if(this.keys[var6] == 0) {
                     this.keys[var6] = var11;
                  }

                  var6 = this.keys[var6];
               } else {
                  ++var6;
               }

               if(var6 >= this.keys.length) {
                  int[] var9 = new int[this.keys.length * 2];

                  for(var10 = 0; var10 < this.keys.length; ++var10) {
                     var9[var10] = this.keys[var10];
                  }

                  this.keys = var9;
               }

               var8 >>>= 1;
            }

            this.keys[var6] = ~var3;
            if(var6 >= var11) {
               var11 = var6 + 1;
            }
         }
      }

   }

   @ObfuscatedName("u")
   float[] method2228() {
      return this.field1249[this.method2227()];
   }

   @ObfuscatedName("v")
   int method2227() {
      int var1;
      for(var1 = 0; this.keys[var1] >= 0; var1 = VorbisSample.method2126() != 0?this.keys[var1]:var1 + 1) {
         ;
      }

      return ~this.keys[var1];
   }

   @ObfuscatedName("z")
   static int method2225(int var0, int var1) {
      int var2;
      for(var2 = (int)Math.pow((double)var0, 1.0D / (double)var1) + 1; class33.method677(var2, var1) > var0; --var2) {
         ;
      }

      return var2;
   }
}
