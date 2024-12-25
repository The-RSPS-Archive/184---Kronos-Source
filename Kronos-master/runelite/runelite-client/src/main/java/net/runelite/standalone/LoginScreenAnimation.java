package net.runelite.standalone;

import java.net.MalformedURLException;
import java.net.URL;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("br")
public class LoginScreenAnimation {
   @ObfuscatedName("as")
   @ObfuscatedSignature(
      signature = "Lll;"
   )
   static Bounds field610;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = 1054808651
   )
   int field601;
   @ObfuscatedName("q")
   int[] field595;
   @ObfuscatedName("s")
   @ObfuscatedGetter(
      intValue = -389038271
   )
   int field603;
   @ObfuscatedName("t")
   int[] field609;
   @ObfuscatedName("w")
   int[] field605;
   @ObfuscatedName("x")
   @ObfuscatedGetter(
      intValue = 153935283
   )
   int field607;
   @ObfuscatedName("y")
   int[] field597;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "[Llp;"
   )
   IndexedSprite[] sprites;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = -1416071611
   )
   int field592;
   @ObfuscatedName("b")
   int[] field600;
   @ObfuscatedName("c")
   int[] field599;
   @ObfuscatedName("e")
   int[] field598;
   @ObfuscatedName("g")
   int[] field593;
   @ObfuscatedName("h")
   @ObfuscatedGetter(
      intValue = 1803502465
   )
   int field602;
   @ObfuscatedName("i")
   int[] field608;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 433825167
   )
   int field596;

   @ObfuscatedSignature(
      signature = "([Llp;)V"
   )
   LoginScreenAnimation(IndexedSprite[] var1) {
      this.field595 = new int[256];
      this.field596 = 0;
      this.field601 = 0;
      this.field592 = 0;
      this.field607 = 0;
      this.field602 = 0;
      this.field603 = 0;
      this.sprites = var1;
      this.method1265();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1384251878"
   )
   void method1266() {
      this.field608 = null;
      this.field599 = null;
      this.field600 = null;
      this.field597 = null;
      this.field609 = null;
      this.field593 = null;
      this.field598 = null;
      this.field605 = null;
      this.field607 = 0;
      this.field602 = 0;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "97"
   )
   final void method1285(int var1) {
      int var2 = this.field597.length;
      if(this.field601 > 0) {
         this.method1271(this.field601, this.field599);
      } else if(this.field592 > 0) {
         this.method1271(this.field592, this.field600);
      } else {
         for(int var3 = 0; var3 < var2; ++var3) {
            this.field597[var3] = this.field608[var3];
         }
      }

      this.method1272(var1);
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(I[II)V",
      garbageValue = "-1982126918"
   )
   final void method1271(int var1, int[] var2) {
      int var3 = this.field597.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         if(var1 > 768) {
            this.field597[var4] = this.method1269(this.field608[var4], var2[var4], 1024 - var1);
         } else if(var1 > 256) {
            this.field597[var4] = var2[var4];
         } else {
            this.field597[var4] = this.method1269(var2[var4], this.field608[var4], 256 - var1);
         }
      }

   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(IIII)I",
      garbageValue = "1638746019"
   )
   final int method1269(int var1, int var2, int var3) {
      int var4 = 256 - var3;
      return (var3 * (var2 & 65280) + var4 * (var1 & 65280) & 16711680) + (var4 * (var1 & 16711935) + var3 * (var2 & 16711935) & -16711936) >> 8;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-1108655518"
   )
   final void method1268(int var1) {
      this.field607 += var1 * 128;
      int var2;
      if(this.field607 > this.field609.length) {
         this.field607 -= this.field609.length;
         var2 = (int)(Math.random() * 12.0D);
         this.method1273(this.sprites[var2]);
      }

      var2 = 0;
      int var3 = var1 * 128;
      int var4 = (256 - var1) * 128;

      int var6;
      for(int var5 = 0; var5 < var4; ++var5) {
         var6 = this.field598[var2 + var3] - this.field609[var2 + this.field607 & this.field609.length - 1] * var1 / 6;
         if(var6 < 0) {
            var6 = 0;
         }

         this.field598[var2++] = var6;
      }

      byte var15 = 10;
      var6 = 128 - var15;

      int var7;
      int var10;
      for(var7 = 256 - var1; var7 < 256; ++var7) {
         int var8 = var7 * 128;

         for(int var9 = 0; var9 < 128; ++var9) {
            var10 = (int)(Math.random() * 100.0D);
            if(var10 < 50 && var9 > var15 && var9 < var6) {
               this.field598[var9 + var8] = 255;
            } else {
               this.field598[var8 + var9] = 0;
            }
         }
      }

      if(this.field601 > 0) {
         this.field601 -= var1 * 4;
      }

      if(this.field592 > 0) {
         this.field592 -= var1 * 4;
      }

      if(this.field601 == 0 && this.field592 == 0) {
         var7 = (int)(Math.random() * (double)(2000 / var1));
         if(var7 == 0) {
            this.field601 = 1024;
         }

         if(var7 == 1) {
            this.field592 = 1024;
         }
      }

      for(var7 = 0; var7 < 256 - var1; ++var7) {
         this.field595[var7] = this.field595[var7 + var1];
      }

      for(var7 = 256 - var1; var7 < 256; ++var7) {
         this.field595[var7] = (int)(Math.sin((double)this.field596 / 14.0D) * 16.0D + Math.sin((double)this.field596 / 15.0D) * 14.0D + Math.sin((double)this.field596 / 16.0D) * 12.0D);
         ++this.field596;
      }

      this.field602 += var1;
      var7 = ((Client.cycle & 1) + var1) / 2;
      if(var7 > 0) {
         short var16 = 128;
         byte var17 = 2;
         var10 = 128 - var17 - var17;

         int var11;
         int var12;
         int var13;
         for(var11 = 0; var11 < this.field602 * 100; ++var11) {
            var12 = (int)(Math.random() * (double)var10) + var17;
            var13 = (int)(Math.random() * (double)var16) + var16;
            this.field598[var12 + (var13 << 7)] = 192;
         }

         this.field602 = 0;

         int var14;
         for(var11 = 0; var11 < 256; ++var11) {
            var12 = 0;
            var13 = var11 * 128;

            for(var14 = -var7; var14 < 128; ++var14) {
               if(var14 + var7 < 128) {
                  var12 += this.field598[var7 + var14 + var13];
               }

               if(var14 - (var7 + 1) >= 0) {
                  var12 -= this.field598[var13 + var14 - (var7 + 1)];
               }

               if(var14 >= 0) {
                  this.field605[var14 + var13] = var12 / (var7 * 2 + 1);
               }
            }
         }

         for(var11 = 0; var11 < 128; ++var11) {
            var12 = 0;

            for(var13 = -var7; var13 < 256; ++var13) {
               var14 = var13 * 128;
               if(var7 + var13 < 256) {
                  var12 += this.field605[var7 * 128 + var14 + var11];
               }

               if(var13 - (var7 + 1) >= 0) {
                  var12 -= this.field605[var14 + var11 - (var7 + 1) * 128];
               }

               if(var13 >= 0) {
                  this.field598[var11 + var14] = var12 / (var7 * 2 + 1);
               }
            }
         }
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "-938798150"
   )
   void method1267(int var1, int var2) {
      if(this.field598 == null) {
         this.method1265();
      }

      if(this.field603 == 0) {
         this.field603 = var2;
      }

      int var3 = var2 - this.field603;
      if(var3 >= 256) {
         var3 = 0;
      }

      this.field603 = var2;
      if(var3 > 0) {
         this.method1268(var3);
      }

      this.method1285(var1);
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(Llp;I)V",
      garbageValue = "1363271921"
   )
   final void method1273(IndexedSprite var1) {
      int var2;
      for(var2 = 0; var2 < this.field609.length; ++var2) {
         this.field609[var2] = 0;
      }

      int var3;
      for(var2 = 0; var2 < 5000; ++var2) {
         var3 = (int)(Math.random() * 128.0D * 256.0D);
         this.field609[var3] = (int)(Math.random() * 256.0D);
      }

      int var4;
      int var5;
      for(var2 = 0; var2 < 20; ++var2) {
         for(var3 = 1; var3 < 255; ++var3) {
            for(var4 = 1; var4 < 127; ++var4) {
               var5 = var4 + (var3 << 7);
               this.field593[var5] = (this.field609[var5 + 1] + this.field609[var5 + 128] + this.field609[var5 - 128] + this.field609[var5 - 1]) / 4;
            }
         }

         int[] var8 = this.field609;
         this.field609 = this.field593;
         this.field593 = var8;
      }

      if(var1 != null) {
         var2 = 0;

         for(var3 = 0; var3 < var1.subHeight; ++var3) {
            for(var4 = 0; var4 < var1.subWidth; ++var4) {
               if(var1.pixels[var2++] != 0) {
                  var5 = var4 + var1.xOffset + 16;
                  int var6 = var3 + var1.yOffset + 16;
                  int var7 = var5 + (var6 << 7);
                  this.field609[var7] = 0;
               }
            }
         }
      }

   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-27245818"
   )
   void method1265() {
      this.field608 = new int[256];

      int var1;
      for(var1 = 0; var1 < 64; ++var1) {
         this.field608[var1] = var1 * 262144;
      }

      for(var1 = 0; var1 < 64; ++var1) {
         this.field608[var1 + 64] = var1 * 1024 + 16711680;
      }

      for(var1 = 0; var1 < 64; ++var1) {
         this.field608[var1 + 128] = var1 * 4 + 16776960;
      }

      for(var1 = 0; var1 < 64; ++var1) {
         this.field608[var1 + 192] = 16777215;
      }

      this.field599 = new int[256];

      for(var1 = 0; var1 < 64; ++var1) {
         this.field599[var1] = var1 * 1024;
      }

      for(var1 = 0; var1 < 64; ++var1) {
         this.field599[var1 + 64] = var1 * 4 + 65280;
      }

      for(var1 = 0; var1 < 64; ++var1) {
         this.field599[var1 + 128] = var1 * 262144 + 65535;
      }

      for(var1 = 0; var1 < 64; ++var1) {
         this.field599[var1 + 192] = 16777215;
      }

      this.field600 = new int[256];

      for(var1 = 0; var1 < 64; ++var1) {
         this.field600[var1] = var1 * 4;
      }

      for(var1 = 0; var1 < 64; ++var1) {
         this.field600[var1 + 64] = var1 * 262144 + 255;
      }

      for(var1 = 0; var1 < 64; ++var1) {
         this.field600[var1 + 128] = var1 * 1024 + 16711935;
      }

      for(var1 = 0; var1 < 64; ++var1) {
         this.field600[var1 + 192] = 16777215;
      }

      this.field597 = new int[256];
      this.field607 = 0;
      this.field609 = new int['耀'];
      this.field593 = new int['耀'];
      this.method1273((IndexedSprite)null);
      this.field598 = new int['耀'];
      this.field605 = new int['耀'];
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-224486060"
   )
   final void method1272(int var1) {
      int var2 = 0;

      for(int var3 = 1; var3 < 255; ++var3) {
         int var4 = (256 - var3) * this.field595[var3] / 256;
         int var5 = var4 + var1;
         int var6 = 0;
         int var7 = 128;
         if(var5 < 0) {
            var6 = -var5;
            var5 = 0;
         }

         if(var5 + 128 >= class30.rasterProvider.width) {
            var7 = class30.rasterProvider.width - var5;
         }

         int var8 = var5 + (var3 + 8) * class30.rasterProvider.width;
         var2 += var6;

         for(int var9 = var6; var9 < var7; ++var9) {
            int var10 = this.field598[var2++];
            int var11 = var8 % Rasterizer2D.Rasterizer2D_width;
            if(var10 != 0 && var11 >= Rasterizer2D.Rasterizer2D_xClipStart && var11 < Rasterizer2D.Rasterizer2D_xClipEnd) {
               int var12 = var10;
               int var13 = 256 - var10;
               var10 = this.field597[var10];
               int var14 = class30.rasterProvider.pixels[var8];
               class30.rasterProvider.pixels[var8++] = -16777216 | ((var14 & 16711935) * var13 + (var10 & 16711935) * var12 & -16711936) + (var12 * (var10 & 65280) + var13 * (var14 & 65280) & 16711680) >> 8;
            } else {
               ++var8;
            }
         }

         var2 += 128 - var7;
      }

   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(CI)Z",
      garbageValue = "1979853088"
   )
   public static boolean method1288(char var0) {
      if((var0 <= 0 || var0 >= 128) && (var0 < 160 || var0 > 255)) {
         if(var0 != 0) {
            char[] var1 = class298.cp1252AsciiExtension;

            for(int var2 = 0; var2 < var1.length; ++var2) {
               char var3 = var1[var2];
               if(var0 == var3) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)Z",
      garbageValue = "666011978"
   )
   static boolean method1290(String var0) {
      if(var0 == null) {
         return false;
      } else {
         try {
            new URL(var0);
         } catch (MalformedURLException var2) {
            return false;
         }

         return true;
      }
   }

   @ObfuscatedName("an")
   @ObfuscatedSignature(
      signature = "([BIII)I",
      garbageValue = "-1562433827"
   )
   static int method1293(byte[] var0, int var1, int var2) {
      int var3 = -1;

      for(int var4 = var1; var4 < var2; ++var4) {
         var3 = var3 >>> 8 ^ Buffer.crc32Table[(var3 ^ var0[var4]) & 255];
      }

      var3 = ~var3;
      return var3;
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(Lho;IIII)V",
      garbageValue = "1378912334"
   )
   static final void method1274(Widget var0, int var1, int var2, int var3) {
      if(var0.field2619 == null) {
         throw new RuntimeException();
      } else {
         var0.field2619[var1] = var2;
         var0.field2581[var1] = var3;
      }
   }

   @ObfuscatedName("kh")
   @ObfuscatedSignature(
      signature = "([BII)V",
      garbageValue = "-2002563170"
   )
   static void method1277(byte[] var0, int var1) {
      if(Client.randomDatData == null) {
         Client.randomDatData = new byte[24];
      }

      class288.method5220(var0, var1, Client.randomDatData, 0, 24);
   }
}
