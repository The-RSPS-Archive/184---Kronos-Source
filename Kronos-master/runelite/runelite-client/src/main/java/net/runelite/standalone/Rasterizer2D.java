package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("lx")
public class Rasterizer2D extends DualNode {
   @ObfuscatedName("aa")
   public static int Rasterizer2D_height;
   @ObfuscatedName("ab")
   public static int Rasterizer2D_xClipStart;
   @ObfuscatedName("ap")
   public static int Rasterizer2D_yClipStart;
   @ObfuscatedName("ar")
   public static int Rasterizer2D_yClipEnd;
   @ObfuscatedName("aw")
   public static int Rasterizer2D_width;
   @ObfuscatedName("ax")
   public static int Rasterizer2D_xClipEnd;
   @ObfuscatedName("az")
   public static int[] Rasterizer2D_pixels;

   static {
      Rasterizer2D_yClipStart = 0;
      Rasterizer2D_yClipEnd = 0;
      Rasterizer2D_xClipStart = 0;
      Rasterizer2D_xClipEnd = 0;
   }

   @ObfuscatedName("da")
   static void method6413(int var0, int var1, int var2) {
      if(var0 >= Rasterizer2D_xClipStart && var1 >= Rasterizer2D_yClipStart && var0 < Rasterizer2D_xClipEnd && var1 < Rasterizer2D_yClipEnd) {
         Rasterizer2D_pixels[var0 + Rasterizer2D_width * var1] = var2 | -16777216;
      }
   }

   @ObfuscatedName("dc")
   public static void method6419(int var0, int var1, int var2, int var3, int var4, int var5) {
      if(var2 > 0 && var3 > 0) {
         int var6 = 0;
         int var7 = 65536 / var3;
         if(var0 < Rasterizer2D_xClipStart) {
            var2 -= Rasterizer2D_xClipStart - var0;
            var0 = Rasterizer2D_xClipStart;
         }

         if(var1 < Rasterizer2D_yClipStart) {
            var6 += (Rasterizer2D_yClipStart - var1) * var7;
            var3 -= Rasterizer2D_yClipStart - var1;
            var1 = Rasterizer2D_yClipStart;
         }

         if(var0 + var2 > Rasterizer2D_xClipEnd) {
            var2 = Rasterizer2D_xClipEnd - var0;
         }

         if(var3 + var1 > Rasterizer2D_yClipEnd) {
            var3 = Rasterizer2D_yClipEnd - var1;
         }

         int var8 = Rasterizer2D_width - var2;
         int var9 = var0 + Rasterizer2D_width * var1;

         for(int var10 = -var3; var10 < 0; ++var10) {
            int var11 = 65536 - var6 >> 8;
            int var12 = var6 >> 8;
            int var13 = (var12 * (var5 & 16711935) + var11 * (var4 & 16711935) & -16711936) + (var12 * (var5 & 65280) + var11 * (var4 & 65280) & 16711680) >>> 8;

            for(int var14 = -var2; var14 < 0; ++var14) {
               Rasterizer2D_pixels[var9++] = var13 | -16777216;
            }

            var9 += var8;
            var6 += var7;
         }

      }
   }

   @ObfuscatedName("dd")
   public static void method6426(int var0, int var1, int var2, int var3) {
      if(var0 >= Rasterizer2D_xClipStart && var0 < Rasterizer2D_xClipEnd) {
         if(var1 < Rasterizer2D_yClipStart) {
            var2 -= Rasterizer2D_yClipStart - var1;
            var1 = Rasterizer2D_yClipStart;
         }

         if(var2 + var1 > Rasterizer2D_yClipEnd) {
            var2 = Rasterizer2D_yClipEnd - var1;
         }

         int var4 = var0 + Rasterizer2D_width * var1;

         for(int var5 = 0; var5 < var2; ++var5) {
            Rasterizer2D_pixels[var4 + var5 * Rasterizer2D_width] = var3 | -16777216;
         }

      }
   }

   @ObfuscatedName("de")
   public static void reset() {
      int var0 = 0;

      int var1;
      for(var1 = Rasterizer2D_width * Rasterizer2D_height - 7; var0 < var1; Rasterizer2D_pixels[var0++] = 0) {
         Rasterizer2D_pixels[var0++] = 0;
         Rasterizer2D_pixels[var0++] = 0;
         Rasterizer2D_pixels[var0++] = 0;
         Rasterizer2D_pixels[var0++] = 0;
         Rasterizer2D_pixels[var0++] = 0;
         Rasterizer2D_pixels[var0++] = 0;
         Rasterizer2D_pixels[var0++] = 0;
      }

      for(var1 += 7; var0 < var1; Rasterizer2D_pixels[var0++] = 0) {
         ;
      }

   }

   @ObfuscatedName("df")
   static void method6415(int var0, int var1, int var2, int var3) {
      if(var2 == 0) {
         method6413(var0, var1, var3);
      } else {
         if(var2 < 0) {
            var2 = -var2;
         }

         int var4 = var1 - var2;
         if(var4 < Rasterizer2D_yClipStart) {
            var4 = Rasterizer2D_yClipStart;
         }

         int var5 = var2 + var1 + 1;
         if(var5 > Rasterizer2D_yClipEnd) {
            var5 = Rasterizer2D_yClipEnd;
         }

         int var6 = var4;
         int var7 = var2 * var2;
         int var8 = 0;
         int var9 = var1 - var4;
         int var10 = var9 * var9;
         int var11 = var10 - var9;
         if(var1 > var5) {
            var1 = var5;
         }

         int var12;
         int var13;
         int var14;
         int var15;
         while(var6 < var1) {
            while(var11 <= var7 || var10 <= var7) {
               var10 = var10 + var8 + var8;
               var11 += var8++ + var8;
            }

            var12 = var0 - var8 + 1;
            if(var12 < Rasterizer2D_xClipStart) {
               var12 = Rasterizer2D_xClipStart;
            }

            var13 = var0 + var8;
            if(var13 > Rasterizer2D_xClipEnd) {
               var13 = Rasterizer2D_xClipEnd;
            }

            var14 = var12 + var6 * Rasterizer2D_width;

            for(var15 = var12; var15 < var13; ++var15) {
               Rasterizer2D_pixels[var14++] = var3 | -16777216;
            }

            ++var6;
            var10 -= var9-- + var9;
            var11 -= var9 + var9;
         }

         var8 = var2;
         var9 = var6 - var1;
         var11 = var7 + var9 * var9;
         var10 = var11 - var2;

         for(var11 -= var9; var6 < var5; var10 += var9++ + var9) {
            while(var11 > var7 && var10 > var7) {
               var11 -= var8-- + var8;
               var10 -= var8 + var8;
            }

            var12 = var0 - var8;
            if(var12 < Rasterizer2D_xClipStart) {
               var12 = Rasterizer2D_xClipStart;
            }

            var13 = var0 + var8;
            if(var13 > Rasterizer2D_xClipEnd - 1) {
               var13 = Rasterizer2D_xClipEnd - 1;
            }

            var14 = var12 + var6 * Rasterizer2D_width;

            for(var15 = var12; var15 <= var13; ++var15) {
               Rasterizer2D_pixels[var14++] = var3 | -16777216;
            }

            ++var6;
            var11 = var11 + var9 + var9;
         }

      }
   }

   @ObfuscatedName("dg")
   public static void method6428(int var0, int var1, int var2, int var3, int var4) {
      var2 -= var0;
      var3 -= var1;
      if(var3 == 0) {
         if(var2 >= 0) {
            method6424(var0, var1, var2 + 1, var4);
         } else {
            method6424(var0 + var2, var1, -var2 + 1, var4);
         }

      } else if(var2 == 0) {
         if(var3 >= 0) {
            method6426(var0, var1, var3 + 1, var4);
         } else {
            method6426(var0, var3 + var1, -var3 + 1, var4);
         }

      } else {
         if(var3 + var2 < 0) {
            var0 += var2;
            var2 = -var2;
            var1 += var3;
            var3 = -var3;
         }

         int var5;
         int var6;
         if(var2 > var3) {
            var1 <<= 16;
            var1 += 32768;
            var3 <<= 16;
            var5 = (int)Math.floor((double)var3 / (double)var2 + 0.5D);
            var2 += var0;
            if(var0 < Rasterizer2D_xClipStart) {
               var1 += var5 * (Rasterizer2D_xClipStart - var0);
               var0 = Rasterizer2D_xClipStart;
            }

            if(var2 >= Rasterizer2D_xClipEnd) {
               var2 = Rasterizer2D_xClipEnd - 1;
            }

            while(var0 <= var2) {
               var6 = var1 >> 16;
               if(var6 >= Rasterizer2D_yClipStart && var6 < Rasterizer2D_yClipEnd) {
                  Rasterizer2D_pixels[var0 + var6 * Rasterizer2D_width] = var4 | -16777216;
               }

               var1 += var5;
               ++var0;
            }
         } else {
            var0 <<= 16;
            var0 += 32768;
            var2 <<= 16;
            var5 = (int)Math.floor((double)var2 / (double)var3 + 0.5D);
            var3 += var1;
            if(var1 < Rasterizer2D_yClipStart) {
               var0 += (Rasterizer2D_yClipStart - var1) * var5;
               var1 = Rasterizer2D_yClipStart;
            }

            if(var3 >= Rasterizer2D_yClipEnd) {
               var3 = Rasterizer2D_yClipEnd - 1;
            }

            while(var1 <= var3) {
               var6 = var0 >> 16;
               if(var6 >= Rasterizer2D_xClipStart && var6 < Rasterizer2D_xClipEnd) {
                  Rasterizer2D_pixels[var6 + Rasterizer2D_width * var1] = var4 | -16777216;
               }

               var0 += var5;
               ++var1;
            }
         }

      }
   }

   @ObfuscatedName("di")
   public static void method6420(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = ViewportMouse.client.getGraphicsPixelsWidth();
      int var9 = ViewportMouse.client.getStartX();
      int var10 = ViewportMouse.client.getStartY();
      int var11 = ViewportMouse.client.getEndX();
      int var12 = ViewportMouse.client.getEndY();
      int[] var13 = ViewportMouse.client.getGraphicsPixels();
      if(!ViewportMouse.client.isGpu()) {
         Client.copy$Rasterizer2D_fillRectangleGradientAlpha(var0, var1, var2, var3, var4, var5, var6, var7);
      } else {
         if(var2 > 0 && var3 > 0) {
            int var14 = 0;
            int var15 = 65536 / var3;
            if(var0 < var9) {
               var2 -= var9 - var0;
               var0 = var9;
            }

            if(var1 < var10) {
               var14 += (var10 - var1) * var15;
               var3 -= var10 - var1;
               var1 = var10;
            }

            if(var0 + var2 > var11) {
               var2 = var11 - var0;
            }

            if(var3 + var1 > var12) {
               var3 = var12 - var1;
            }

            int var16 = var8 - var2;
            int var17 = var0 + var8 * var1;

            for(int var18 = -var3; var18 < 0; ++var18) {
               int var19 = 65536 - var14 >> 8;
               int var20 = var14 >> 8;
               int var21 = (var19 * var6 + var20 * var7 & 65280) >>> 8;
               if(var21 == 0) {
                  var17 += var8;
                  var14 += var15;
               } else {
                  int var22 = (var19 * (var4 & 65280) + var20 * (var5 & 65280) & 16711680) + (var19 * (var4 & 16711935) + var20 * (var5 & 16711935) & -16711936) >>> 8;
                  int var23 = 255 - var21;
                  int var24 = ((var22 & 16711935) * var21 >> 8 & 16711935) + (var21 * (var22 & 65280) >> 8 & 65280);

                  for(int var25 = -var2; var25 < 0; ++var25) {
                     int var26 = var13[var17];
                     var26 = ((var26 & 16711935) * var23 >> 8 & 16711935) + (var23 * (var26 & 65280) >> 8 & 65280);
                     Client.drawAlpha(var13, var17++, var24 + var26, var21);
                  }

                  var17 += var16;
                  var14 += var15;
               }
            }
         }

      }
   }

   @ObfuscatedName("dj")
   static void method6427(int var0, int var1, int var2, int var3, int var4) {
      if(var0 >= Rasterizer2D_xClipStart && var0 < Rasterizer2D_xClipEnd) {
         if(var1 < Rasterizer2D_yClipStart) {
            var2 -= Rasterizer2D_yClipStart - var1;
            var1 = Rasterizer2D_yClipStart;
         }

         if(var2 + var1 > Rasterizer2D_yClipEnd) {
            var2 = Rasterizer2D_yClipEnd - var1;
         }

         int var5 = 256 - var4;
         int var6 = (var3 >> 16 & 255) * var4;
         int var7 = (var3 >> 8 & 255) * var4;
         int var8 = var4 * (var3 & 255);
         int var12 = var0 + Rasterizer2D_width * var1;

         for(int var13 = 0; var13 < var2; ++var13) {
            int var9 = var5 * (Rasterizer2D_pixels[var12] >> 16 & 255);
            int var10 = (Rasterizer2D_pixels[var12] >> 8 & 255) * var5;
            int var11 = var5 * (Rasterizer2D_pixels[var12] & 255);
            int var14 = (var8 + var11 >> 8) + (var6 + var9 >> 8 << 16) + (var7 + var10 >> 8 << 8);
            Client.drawAlpha(Rasterizer2D_pixels, var12, var14, var4);
            var12 += Rasterizer2D_width;
         }

      }
   }

   @ObfuscatedName("dk")
   public static void method6421(int var0, int var1, int var2, int var3, int var4, int var5, byte[] var6, int var7, boolean var8) {
      int var9 = ViewportMouse.client.getGraphicsPixelsWidth();
      int var10 = ViewportMouse.client.getGraphicsPixelsHeight();
      int[] var11 = ViewportMouse.client.getGraphicsPixels();
      if(!ViewportMouse.client.isGpu()) {
         Client.copy$Rasterizer2D_drawGradientPixels(var0, var1, var2, var3, var4, var5, var6, var7, var8);
      } else {
         if(var0 + var2 >= 0 && var3 + var1 >= 0 && var0 < var9 && var1 < var10) {
            int var12 = 0;
            int var13 = 0;
            if(var0 < 0) {
               var12 -= var0;
               var2 += var0;
            }

            if(var1 < 0) {
               var13 -= var1;
               var3 += var1;
            }

            if(var0 + var2 > var9) {
               var2 = var9 - var0;
            }

            if(var3 + var1 > var10) {
               var3 = var10 - var1;
            }

            int var14 = var6.length / var7;
            int var15 = var9 - var2;
            int var16 = var4 >>> 24;
            int var17 = var5 >>> 24;
            int var18;
            int var19;
            int var20;
            int var21;
            int var22;
            if(var16 == 255 && var17 == 255) {
               var18 = var0 + var12 + var9 * (var13 + var1);

               for(var19 = var13 + var1; var19 < var3 + var13 + var1; ++var19) {
                  for(var20 = var0 + var12; var20 < var0 + var12 + var2; ++var20) {
                     var21 = (var19 - var1) % var14;
                     var22 = (var20 - var0) % var7;
                     if(var6[var22 + var21 * var7] != 0) {
                        var11[var18++] = var5;
                     } else {
                        var11[var18++] = var4;
                     }
                  }

                  var18 += var15;
               }
            } else {
               var18 = var0 + var12 + var9 * (var13 + var1);

               for(var19 = var13 + var1; var19 < var3 + var13 + var1; ++var19) {
                  for(var20 = var0 + var12; var20 < var0 + var12 + var2; ++var20) {
                     var21 = (var19 - var1) % var14;
                     var22 = (var20 - var0) % var7;
                     int var23 = var4;
                     if(var6[var22 + var21 * var7] != 0) {
                        var23 = var5;
                     }

                     int var24 = var23 >>> 24;
                     int var25 = 255 - var24;
                     int var26 = var11[var18];
                     int var27 = (var24 * (var23 & 65280) + var25 * (var26 & 65280) & 16711680) + ((var23 & 16711935) * var24 + (var26 & 16711935) * var25 & -16711936) >> 8;
                     Client.drawAlpha(var11, var18++, var27, var24);
                  }

                  var18 += var15;
               }
            }
         }

      }
   }

   @ObfuscatedName("dl")
   public static void method6430(int var0, int var1, int var2, int[] var3, int[] var4) {
      int var5 = var0 + Rasterizer2D_width * var1;

      for(var1 = 0; var1 < var3.length; ++var1) {
         int var6 = var5 + var3[var1];

         for(var0 = -var4[var1]; var0 < 0; ++var0) {
            Rasterizer2D_pixels[var6++] = var2 | -16777216;
         }

         var5 += Rasterizer2D_width;
      }

   }

   @ObfuscatedName("dm")
   public static void method6474(int var0, int var1, int var2, int var3) {
      if(var0 < 0) {
         var0 = 0;
      }

      if(var1 < 0) {
         var1 = 0;
      }

      if(var2 > Rasterizer2D_width) {
         var2 = Rasterizer2D_width;
      }

      if(var3 > Rasterizer2D_height) {
         var3 = Rasterizer2D_height;
      }

      Rasterizer2D_xClipStart = var0;
      Rasterizer2D_yClipStart = var1;
      Rasterizer2D_xClipEnd = var2;
      Rasterizer2D_yClipEnd = var3;
   }

   @ObfuscatedName("dn")
   public static void method6478(int var0, int var1, int var2, int var3) {
      if(Rasterizer2D_xClipStart < var0) {
         Rasterizer2D_xClipStart = var0;
      }

      if(Rasterizer2D_yClipStart < var1) {
         Rasterizer2D_yClipStart = var1;
      }

      if(Rasterizer2D_xClipEnd > var2) {
         Rasterizer2D_xClipEnd = var2;
      }

      if(Rasterizer2D_yClipEnd > var3) {
         Rasterizer2D_yClipEnd = var3;
      }

   }

   @ObfuscatedName("do")
   public static void method6424(int var0, int var1, int var2, int var3) {
      if(var1 >= Rasterizer2D_yClipStart && var1 < Rasterizer2D_yClipEnd) {
         if(var0 < Rasterizer2D_xClipStart) {
            var2 -= Rasterizer2D_xClipStart - var0;
            var0 = Rasterizer2D_xClipStart;
         }

         if(var0 + var2 > Rasterizer2D_xClipEnd) {
            var2 = Rasterizer2D_xClipEnd - var0;
         }

         int var4 = var0 + Rasterizer2D_width * var1;

         for(int var5 = 0; var5 < var2; ++var5) {
            Rasterizer2D_pixels[var4 + var5] = var3 | -16777216;
         }

      }
   }

   @ObfuscatedName("dq")
   public static void method6412(int[] var0) {
      var0[0] = Rasterizer2D_xClipStart;
      var0[1] = Rasterizer2D_yClipStart;
      var0[2] = Rasterizer2D_xClipEnd;
      var0[3] = Rasterizer2D_yClipEnd;
   }

   @ObfuscatedName("dr")
   public static void method6416(int var0, int var1, int var2, int var3, int var4) {
      if(var4 != 0) {
         if(var4 == 256) {
            method6415(var0, var1, var2, var3);
         } else {
            if(var2 < 0) {
               var2 = -var2;
            }

            int var5 = 256 - var4;
            int var6 = (var3 >> 16 & 255) * var4;
            int var7 = (var3 >> 8 & 255) * var4;
            int var8 = var4 * (var3 & 255);
            int var12 = var1 - var2;
            if(var12 < Rasterizer2D_yClipStart) {
               var12 = Rasterizer2D_yClipStart;
            }

            int var13 = var2 + var1 + 1;
            if(var13 > Rasterizer2D_yClipEnd) {
               var13 = Rasterizer2D_yClipEnd;
            }

            int var14 = var12;
            int var15 = var2 * var2;
            int var16 = 0;
            int var17 = var1 - var12;
            int var18 = var17 * var17;
            int var19 = var18 - var17;
            if(var1 > var13) {
               var1 = var13;
            }

            int var9;
            int var10;
            int var11;
            int var20;
            int var21;
            int var22;
            int var23;
            int var24;
            while(var14 < var1) {
               while(var19 <= var15 || var18 <= var15) {
                  var18 = var18 + var16 + var16;
                  var19 += var16++ + var16;
               }

               var20 = var0 - var16 + 1;
               if(var20 < Rasterizer2D_xClipStart) {
                  var20 = Rasterizer2D_xClipStart;
               }

               var21 = var0 + var16;
               if(var21 > Rasterizer2D_xClipEnd) {
                  var21 = Rasterizer2D_xClipEnd;
               }

               var22 = var20 + var14 * Rasterizer2D_width;

               for(var23 = var20; var23 < var21; ++var23) {
                  var9 = var5 * (Rasterizer2D_pixels[var22] >> 16 & 255);
                  var10 = (Rasterizer2D_pixels[var22] >> 8 & 255) * var5;
                  var11 = var5 * (Rasterizer2D_pixels[var22] & 255);
                  var24 = (var8 + var11 >> 8) + (var6 + var9 >> 8 << 16) + (var7 + var10 >> 8 << 8);
                  Client.drawAlpha(Rasterizer2D_pixels, var22++, var24, var4);
               }

               ++var14;
               var18 -= var17-- + var17;
               var19 -= var17 + var17;
            }

            var16 = var2;
            var17 = -var17;
            var19 = var15 + var17 * var17;
            var18 = var19 - var2;

            for(var19 -= var17; var14 < var13; var18 += var17++ + var17) {
               while(var19 > var15 && var18 > var15) {
                  var19 -= var16-- + var16;
                  var18 -= var16 + var16;
               }

               var20 = var0 - var16;
               if(var20 < Rasterizer2D_xClipStart) {
                  var20 = Rasterizer2D_xClipStart;
               }

               var21 = var0 + var16;
               if(var21 > Rasterizer2D_xClipEnd - 1) {
                  var21 = Rasterizer2D_xClipEnd - 1;
               }

               var22 = var20 + var14 * Rasterizer2D_width;

               for(var23 = var20; var23 <= var21; ++var23) {
                  var9 = var5 * (Rasterizer2D_pixels[var22] >> 16 & 255);
                  var10 = (Rasterizer2D_pixels[var22] >> 8 & 255) * var5;
                  var11 = var5 * (Rasterizer2D_pixels[var22] & 255);
                  var24 = (var8 + var11 >> 8) + (var6 + var9 >> 8 << 16) + (var7 + var10 >> 8 << 8);
                  Rasterizer2D_pixels[var22++] = var24 | -16777216;
               }

               ++var14;
               var19 = var19 + var17 + var17;
            }

         }
      }
   }

   @ObfuscatedName("ds")
   public static void method6469(int var0, int var1, int var2, int var3, int var4) {
      if(var0 < Rasterizer2D_xClipStart) {
         var2 -= Rasterizer2D_xClipStart - var0;
         var0 = Rasterizer2D_xClipStart;
      }

      if(var1 < Rasterizer2D_yClipStart) {
         var3 -= Rasterizer2D_yClipStart - var1;
         var1 = Rasterizer2D_yClipStart;
      }

      if(var0 + var2 > Rasterizer2D_xClipEnd) {
         var2 = Rasterizer2D_xClipEnd - var0;
      }

      if(var3 + var1 > Rasterizer2D_yClipEnd) {
         var3 = Rasterizer2D_yClipEnd - var1;
      }

      int var5 = Rasterizer2D_width - var2;
      int var6 = var0 + Rasterizer2D_width * var1;

      for(int var7 = -var3; var7 < 0; ++var7) {
         for(int var8 = -var2; var8 < 0; ++var8) {
            Rasterizer2D_pixels[var6++] = var4 | -16777216;
         }

         var6 += var5;
      }

   }

   @ObfuscatedName("dt")
   public static void method6409(int[] var0, int var1, int var2) {
      Rasterizer2D_pixels = var0;
      Rasterizer2D_width = var1;
      Rasterizer2D_height = var2;
      method6474(0, 0, var1, var2);
   }

   @ObfuscatedName("du")
   public static void fillRectangleAlpha(int var0, int var1, int var2, int var3, int var4, int var5) {
      if(var0 < Rasterizer2D_xClipStart) {
         var2 -= Rasterizer2D_xClipStart - var0;
         var0 = Rasterizer2D_xClipStart;
      }

      if(var1 < Rasterizer2D_yClipStart) {
         var3 -= Rasterizer2D_yClipStart - var1;
         var1 = Rasterizer2D_yClipStart;
      }

      if(var0 + var2 > Rasterizer2D_xClipEnd) {
         var2 = Rasterizer2D_xClipEnd - var0;
      }

      if(var3 + var1 > Rasterizer2D_yClipEnd) {
         var3 = Rasterizer2D_yClipEnd - var1;
      }

      var4 = (var5 * (var4 & 16711935) >> 8 & 16711935) + (var5 * (var4 & 65280) >> 8 & 65280);
      int var6 = 256 - var5;
      int var7 = Rasterizer2D_width - var2;
      int var8 = var0 + Rasterizer2D_width * var1;

      for(int var9 = 0; var9 < var3; ++var9) {
         for(int var10 = -var2; var10 < 0; ++var10) {
            int var11 = Rasterizer2D_pixels[var8];
            var11 = ((var11 & 16711935) * var6 >> 8 & 16711935) + (var6 * (var11 & 65280) >> 8 & 65280);
            Client.drawAlpha(Rasterizer2D_pixels, var8++, var11 + var4, var5);
         }

         var8 += var7;
      }

   }

   @ObfuscatedName("dv")
   public static void method6410() {
      Rasterizer2D_xClipStart = 0;
      Rasterizer2D_yClipStart = 0;
      Rasterizer2D_xClipEnd = Rasterizer2D_width;
      Rasterizer2D_yClipEnd = Rasterizer2D_height;
   }

   @ObfuscatedName("dw")
   static void method6425(int var0, int var1, int var2, int var3, int var4) {
      if(var1 >= Rasterizer2D_yClipStart && var1 < Rasterizer2D_yClipEnd) {
         if(var0 < Rasterizer2D_xClipStart) {
            var2 -= Rasterizer2D_xClipStart - var0;
            var0 = Rasterizer2D_xClipStart;
         }

         if(var0 + var2 > Rasterizer2D_xClipEnd) {
            var2 = Rasterizer2D_xClipEnd - var0;
         }

         int var5 = 256 - var4;
         int var6 = (var3 >> 16 & 255) * var4;
         int var7 = (var3 >> 8 & 255) * var4;
         int var8 = var4 * (var3 & 255);
         int var12 = var0 + Rasterizer2D_width * var1;

         for(int var13 = 0; var13 < var2; ++var13) {
            int var9 = var5 * (Rasterizer2D_pixels[var12] >> 16 & 255);
            int var10 = (Rasterizer2D_pixels[var12] >> 8 & 255) * var5;
            int var11 = var5 * (Rasterizer2D_pixels[var12] & 255);
            int var14 = (var8 + var11 >> 8) + (var6 + var9 >> 8 << 16) + (var7 + var10 >> 8 << 8);
            Client.drawAlpha(Rasterizer2D_pixels, var12++, var14, var4);
         }

      }
   }

   @ObfuscatedName("dx")
   public static void drawRectangle(int var0, int var1, int var2, int var3, int var4) {
      method6424(var0, var1, var2, var4);
      method6424(var0, var3 + var1 - 1, var2, var4);
      method6426(var0, var1, var3, var4);
      method6426(var0 + var2 - 1, var1, var3, var4);
   }

   @ObfuscatedName("dy")
   public static void method6429(int[] var0) {
      Rasterizer2D_xClipStart = var0[0];
      Rasterizer2D_yClipStart = var0[1];
      Rasterizer2D_xClipEnd = var0[2];
      Rasterizer2D_yClipEnd = var0[3];
   }

   @ObfuscatedName("dz")
   public static void method6465(int var0, int var1, int var2, int var3, int var4, int var5) {
      method6425(var0, var1, var2, var4, var5);
      method6425(var0, var3 + var1 - 1, var2, var4, var5);
      if(var3 >= 3) {
         method6427(var0, var1 + 1, var3 - 2, var4, var5);
         method6427(var0 + var2 - 1, var1 + 1, var3 - 2, var4, var5);
      }

   }

   //TODO: Custom Method
   public static void transparentBox(int i, int j, int k, int l, int i1, int j1, int opac) {
      int j3 = 256 - opac;
      if (k < Rasterizer2D_xClipStart) {
         i1 -= Rasterizer2D_xClipStart - k;
         k = Rasterizer2D_xClipStart;
      }
      if (j < Rasterizer2D_yClipStart) {
         i -= Rasterizer2D_yClipStart - j;
         j = Rasterizer2D_yClipStart;
      }
      if (k + i1 > Rasterizer2D_xClipEnd)
         i1 = Rasterizer2D_xClipEnd - k;
      if (j + i > Rasterizer2D_yClipEnd)
         i = Rasterizer2D_yClipEnd - j;
      int k1 = Rasterizer2D_width - i1;
      int l1 = k + j * Rasterizer2D_width;
      if (j1 != 0)
         Rasterizer3D.Rasterizer3D_clipMidY2 = -374;
      for (int i2 = -i; i2 < 0; i2++) {
         for (int j2 = -i1; j2 < 0; j2++) {
            int i3 = Rasterizer2D_pixels[l1];
            Rasterizer2D_pixels[l1++] = ((l & 0xff00ff) * opac + (i3 & 0xff00ff) * j3 & 0xff00ff00) + ((l & 0xff00) * opac + (i3 & 0xff00) * j3 & 0xff0000) >> 8;
         }
         l1 += k1;
      }
   }
}
