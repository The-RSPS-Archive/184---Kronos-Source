package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.rs.api.RSIndexedSprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

@ObfuscatedName("lp")
public final class IndexedSprite extends Rasterizer2D implements RSIndexedSprite {
   @ObfuscatedName("n")
   public int[] palette;
   @ObfuscatedName("p")
   public int yOffset;
   @ObfuscatedName("q")
   public int width;
   @ObfuscatedName("r")
   public int xOffset;
   @ObfuscatedName("u")
   public int subHeight;
   @ObfuscatedName("v")
   public int subWidth;
   @ObfuscatedName("z")
   public byte[] pixels;
   @ObfuscatedName("m")
   public int height;

   //TODO: Modified [Added Method]
   public static IndexedSprite fromBytes(byte[] is) {
      ByteArrayInputStream in = null;
      try {
         in = new ByteArrayInputStream(is);
         BufferedImage image = ImageIO.read(in);

         IndexedSprite c = new IndexedSprite();
         c.width = c.subWidth = image.getWidth();
         c.height = c.subHeight = image.getHeight();
         c.yOffset = c.yOffset = 0;

         int[] rgbArray = new int[image.getWidth() * image.getHeight()];
         image.getRGB(0, 0, image.getWidth(), image.getHeight(), rgbArray, 0, image.getWidth());

         byte[] alpha = new byte[image.getWidth() * image.getHeight()];
         int[] palette = new int[255];

         int paletteOffset = 0;
         palette[paletteOffset++] = 1; //no clue what this does

         for(int pixel = 0; pixel < alpha.length; pixel++) {
            int rgb = rgbArray[pixel] & 16777215;
            if(rgb == 16711935) {
               /**
                * Transparency
                */
               alpha[pixel] = 0;
            } else {
               int var7 = Arrays.binarySearch(palette, rgb);
               if(var7 < 0) {
                  var7 = paletteOffset;
                  palette[paletteOffset++] = rgb;
               }
               alpha[pixel] = (byte) var7;
            }
         }

         c.palette = palette;
         c.pixels = alpha;
         return c;
      } catch(Exception e) {
         /* ignored */
         return null;
      } finally {
         if(in != null) {
            try {
               in.close();
            } catch(IOException e) {
               /* nothing we can do */
            }
         }
      }
   }

    @ObfuscatedName("n")
   public void method6319(int var1, int var2, int var3) {
      for(int var4 = 0; var4 < this.palette.length; ++var4) {
         int var5 = this.palette[var4] >> 16 & 255;
         var5 += var1;
         if(var5 < 0) {
            var5 = 0;
         } else if(var5 > 255) {
            var5 = 255;
         }

         int var6 = this.palette[var4] >> 8 & 255;
         var6 += var2;
         if(var6 < 0) {
            var6 = 0;
         } else if(var6 > 255) {
            var6 = 255;
         }

         int var7 = this.palette[var4] & 255;
         var7 += var3;
         if(var7 < 0) {
            var7 = 0;
         } else if(var7 > 255) {
            var7 = 255;
         }

         this.palette[var4] = var7 + (var6 << 8) + (var5 << 16);
      }

   }

   @ObfuscatedName("r")
   public void method6328(int var1, int var2, int var3, int var4) {
      int var5 = this.subWidth;
      int var6 = this.subHeight;
      int var7 = 0;
      int var8 = 0;
      int var9 = this.width;
      int var10 = this.height;
      int var11 = (var9 << 16) / var3;
      int var12 = (var10 << 16) / var4;
      int var13;
      if(this.xOffset > 0) {
         var13 = (var11 + (this.xOffset << 16) - 1) / var11;
         var1 += var13;
         var7 += var13 * var11 - (this.xOffset << 16);
      }

      if(this.yOffset > 0) {
         var13 = (var12 + (this.yOffset << 16) - 1) / var12;
         var2 += var13;
         var8 += var13 * var12 - (this.yOffset << 16);
      }

      if(var5 < var9) {
         var3 = (var11 + ((var5 << 16) - var7) - 1) / var11;
      }

      if(var6 < var10) {
         var4 = (var12 + ((var6 << 16) - var8) - 1) / var12;
      }

      var13 = var1 + var2 * Rasterizer2D.Rasterizer2D_width;
      int var14 = Rasterizer2D.Rasterizer2D_width - var3;
      if(var2 + var4 > Rasterizer2D.Rasterizer2D_yClipEnd) {
         var4 -= var2 + var4 - Rasterizer2D.Rasterizer2D_yClipEnd;
      }

      int var15;
      if(var2 < Rasterizer2D.Rasterizer2D_yClipStart) {
         var15 = Rasterizer2D.Rasterizer2D_yClipStart - var2;
         var4 -= var15;
         var13 += var15 * Rasterizer2D.Rasterizer2D_width;
         var8 += var12 * var15;
      }

      if(var3 + var1 > Rasterizer2D.Rasterizer2D_xClipEnd) {
         var15 = var3 + var1 - Rasterizer2D.Rasterizer2D_xClipEnd;
         var3 -= var15;
         var14 += var15;
      }

      if(var1 < Rasterizer2D.Rasterizer2D_xClipStart) {
         var15 = Rasterizer2D.Rasterizer2D_xClipStart - var1;
         var3 -= var15;
         var13 += var15;
         var7 += var11 * var15;
         var14 += var15;
      }

      method6323(Rasterizer2D.Rasterizer2D_pixels, this.pixels, this.palette, var7, var8, var13, var14, var3, var4, var11, var12, var5);
   }

   @ObfuscatedName("v")
   public void method6320(int var1, int var2) {
      var1 += this.xOffset;
      var2 += this.yOffset;
      int var3 = var1 + var2 * Rasterizer2D.Rasterizer2D_width;
      int var4 = 0;
      int var5 = this.subHeight;
      int var6 = this.subWidth;
      int var7 = Rasterizer2D.Rasterizer2D_width - var6;
      int var8 = 0;
      int var9;
      if(var2 < Rasterizer2D.Rasterizer2D_yClipStart) {
         var9 = Rasterizer2D.Rasterizer2D_yClipStart - var2;
         var5 -= var9;
         var2 = Rasterizer2D.Rasterizer2D_yClipStart;
         var4 += var9 * var6;
         var3 += var9 * Rasterizer2D.Rasterizer2D_width;
      }

      if(var5 + var2 > Rasterizer2D.Rasterizer2D_yClipEnd) {
         var5 -= var5 + var2 - Rasterizer2D.Rasterizer2D_yClipEnd;
      }

      if(var1 < Rasterizer2D.Rasterizer2D_xClipStart) {
         var9 = Rasterizer2D.Rasterizer2D_xClipStart - var1;
         var6 -= var9;
         var1 = Rasterizer2D.Rasterizer2D_xClipStart;
         var4 += var9;
         var3 += var9;
         var8 += var9;
         var7 += var9;
      }

      if(var6 + var1 > Rasterizer2D.Rasterizer2D_xClipEnd) {
         var9 = var6 + var1 - Rasterizer2D.Rasterizer2D_xClipEnd;
         var6 -= var9;
         var8 += var9;
         var7 += var9;
      }

      if(var6 > 0 && var5 > 0) {
         method6326(Rasterizer2D.Rasterizer2D_pixels, this.pixels, this.palette, var4, var3, var6, var5, var7, var8);
      }
   }

   @ObfuscatedName("z")
   public void normalize() {
      if(this.subWidth != this.width || this.subHeight != this.height) {
         byte[] var1 = new byte[this.width * this.height];
         int var2 = 0;

         for(int var3 = 0; var3 < this.subHeight; ++var3) {
            for(int var4 = 0; var4 < this.subWidth; ++var4) {
               var1[var4 + (var3 + this.yOffset) * this.width + this.xOffset] = this.pixels[var2++];
            }
         }

         this.pixels = var1;
         this.subWidth = this.width;
         this.subHeight = this.height;
         this.xOffset = 0;
         this.yOffset = 0;
      }
   }

   public void setPixels(byte[] var1) {
      this.pixels = var1;
   }

   public byte[] getPixels() {
      return this.pixels;
   }

   public void setPalette(int[] var1) {
      this.palette = var1;
   }

   public int[] getPalette() {
      return this.palette;
   }

   public void setWidth(int var1) {
      this.subWidth = var1;
   }

   public int getWidth() {
      return this.subWidth;
   }

   public void setHeight(int var1) {
      this.subHeight = var1;
   }

   public int getHeight() {
      return this.subHeight;
   }

   public void setOffsetX(int var1) {
      this.xOffset = var1;
   }

   public int getOffsetX() {
      return this.xOffset;
   }

   public void setOffsetY(int var1) {
      this.yOffset = var1;
   }

   public int getOffsetY() {
      return this.yOffset;
   }

   public void setOriginalWidth(int var1) {
      this.width = var1;
   }

   public int getOriginalWidth() {
      return this.width;
   }

   public void setOriginalHeight(int var1) {
      this.height = var1;
   }

   public int getOriginalHeight() {
      return this.height;
   }

   @ObfuscatedName("p")
   static void method6323(int[] var0, byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
      int var12 = var3;

      for(int var13 = -var8; var13 < 0; ++var13) {
         int var14 = var11 * (var4 >> 16);

         for(int var15 = -var7; var15 < 0; ++var15) {
            byte var16 = var1[(var3 >> 16) + var14];
            if(var16 != 0) {
               var0[var5++] = var2[var16 & 255] | -16777216;
            } else {
               ++var5;
            }

            var3 += var9;
         }

         var4 += var10;
         var3 = var12;
         var5 += var6;
      }

   }

   @ObfuscatedName("u")
   static void method6326(int[] var0, byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      int var9 = -(var5 >> 2);
      var5 = -(var5 & 3);

      for(int var10 = -var6; var10 < 0; ++var10) {
         int var11;
         byte var12;
         for(var11 = var9; var11 < 0; ++var11) {
            var12 = var1[var3++];
            if(var12 != 0) {
               var0[var4++] = var2[var12 & 255] | -16777216;
            } else {
               ++var4;
            }

            var12 = var1[var3++];
            if(var12 != 0) {
               var0[var4++] = var2[var12 & 255] | -16777216;
            } else {
               ++var4;
            }

            var12 = var1[var3++];
            if(var12 != 0) {
               var0[var4++] = var2[var12 & 255] | -16777216;
            } else {
               ++var4;
            }

            var12 = var1[var3++];
            if(var12 != 0) {
               var0[var4++] = var2[var12 & 255] | -16777216;
            } else {
               ++var4;
            }
         }

         for(var11 = var5; var11 < 0; ++var11) {
            var12 = var1[var3++];
            if(var12 != 0) {
               var0[var4++] = var2[var12 & 255] | -16777216;
            } else {
               ++var4;
            }
         }

         var4 += var7;
         var3 += var8;
      }

   }
}
