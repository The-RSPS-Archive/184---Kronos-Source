package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSTexture;

@ObfuscatedName("dg")
public class Texture extends Node implements RSTexture {
   @ObfuscatedName("e")
   static int[] Texture_animatedPixels;
   @ObfuscatedName("o")
   int[] pixels;
   @ObfuscatedName("p")
   boolean field1363;
   @ObfuscatedName("q")
   int[] fileIds;
   @ObfuscatedName("r")
   int averageRGB;
   @ObfuscatedName("y")
   int[] field1361;
   @ObfuscatedName("a")
   boolean isLoaded;
   @ObfuscatedName("b")
   int animationSpeed;
   @ObfuscatedName("c")
   int animationDirection;
   public float rl$u;
   public float rl$v;
   @ObfuscatedName("i")
   int[] field1367;
   @ObfuscatedName("m")
   int[] field1365;

   @ObfuscatedSignature(
      signature = "(Lkl;)V"
   )
   Texture(Buffer var1) {
      this.isLoaded = false;
      this.averageRGB = var1.readUnsignedShort();
      this.field1363 = var1.readUnsignedByte() == 1;
      int var2 = var1.readUnsignedByte();
      if(var2 >= 1 && var2 <= 4) {
         this.fileIds = new int[var2];

         int var3;
         for(var3 = 0; var3 < var2; ++var3) {
            this.fileIds[var3] = var1.readUnsignedShort();
         }

         if(var2 > 1) {
            this.field1365 = new int[var2 - 1];

            for(var3 = 0; var3 < var2 - 1; ++var3) {
               this.field1365[var3] = var1.readUnsignedByte();
            }
         }

         if(var2 > 1) {
            this.field1361 = new int[var2 - 1];

            for(var3 = 0; var3 < var2 - 1; ++var3) {
               this.field1361[var3] = var1.readUnsignedByte();
            }
         }

         this.field1367 = new int[var2];

         for(var3 = 0; var3 < var2; ++var3) {
            this.field1367[var3] = var1.readInt();
         }

         this.animationDirection = var1.readUnsignedByte();
         this.animationSpeed = var1.readUnsignedByte();
         this.pixels = null;
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedName("n")
   void method2347() {
      this.pixels = null;
   }

   @ObfuscatedName("v")
   void method2345(int var1) {
      if(!ViewportMouse.client.isGpu()) {
         this.copy$animate(var1);
      } else {
         ViewportMouse.client.getDrawCallbacks().animate(this, var1);
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(DILhp;)Z"
   )
   boolean method2346(double brightness, int var3, AbstractArchive var4) {
      int var5;
      for(var5 = 0; var5 < this.fileIds.length; ++var5) {
         if(var4.method4029(this.fileIds[var5]) == null) {
            return false;
         }
      }

      var5 = var3 * var3;
      this.pixels = new int[var5];

      for(int var6 = 0; var6 < this.fileIds.length; ++var6) {
         int var8 = this.fileIds[var6];
         byte[] var10 = var4.method4027(var8);
         boolean var9;
         if(var10 == null) {
            var9 = false;
         } else {
            Tiles.method1200(var10);
            var9 = true;
         }

         IndexedSprite sprite;
         if(!var9) {
            sprite = null;
         } else {
            IndexedSprite var11 = new IndexedSprite();
            var11.width = class329.SpriteBuffer_spriteWidth;
            var11.height = Frames.SpriteBuffer_spriteHeight;
            var11.xOffset = class329.SpriteBuffer_xOffsets[0];
            var11.yOffset = MusicPatchPcmStream.SpriteBuffer_yOffsets[0];
            var11.subWidth = class329.SpriteBuffer_spriteWidths[0];
            var11.subHeight = RunException.SpriteBuffer_spriteHeights[0];
            var11.palette = class329.SpriteBuffer_spritePalette;
            var11.pixels = PacketBufferNode.SpriteBuffer_pixels[0];
            class329.SpriteBuffer_xOffsets = null;
            MusicPatchPcmStream.SpriteBuffer_yOffsets = null;
            class329.SpriteBuffer_spriteWidths = null;
            RunException.SpriteBuffer_spriteHeights = null;
            class329.SpriteBuffer_spritePalette = null;
            PacketBufferNode.SpriteBuffer_pixels = null;
            sprite = var11;
         }

         sprite.normalize();
         var10 = sprite.pixels;
         int[] pixels = sprite.palette;
         int var12 = this.field1367[var6];
         int var13;
         int var14;
         int index;
         int var16;
         if((var12 & -16777216) == 50331648) {
            var13 = var12 & 0xff00ff;
            var14 = var12 >> 8 & 255;

            for(index = 0; index < pixels.length; ++index) {
               var16 = pixels[index];
               if(var16 >> 8 == (var16 & 0xffff)) {
                  var16 &= 255;
                  pixels[index] = var13 * var16 >> 8 & 0xff00ff | var14 * var16 & 65280;
               }
            }
         }

         for (int pixel = 0; pixel < pixels.length; ++pixel) {
            pixels[pixel] = Rasterizer3D.adjustBrightness(pixels[pixel], brightness);
         }

         if(var6 == 0) {
            var13 = 0;
         } else {
            var13 = this.field1365[var6 - 1];
         }

         if(var13 == 0) {
            if(var3 == sprite.subWidth) {
               for(var14 = 0; var14 < var5; ++var14) {
                  this.pixels[var14] = pixels[var10[var14] & 255];
               }
            } else if(sprite.subWidth == 64 && var3 == 128) {
               var14 = 0;

               for(index = 0; index < var3; ++index) {
                  for(var16 = 0; var16 < var3; ++var16) {
                     this.pixels[var14++] = pixels[var10[(index >> 1 << 6) + (var16 >> 1)] & 255];
                  }
               }
            } else {
               if(sprite.subWidth != 128 || var3 != 64) {
                  throw new RuntimeException();
               }

               var14 = 0;

               for(index = 0; index < var3; ++index) {
                  for(var16 = 0; var16 < var3; ++var16) {
                     this.pixels[var14++] = pixels[var10[(var16 << 1) + (index << 1 << 7)] & 255];
                  }
               }
            }
         }

         if(var13 == 1) {
            ;
         }

         if(var13 == 2) {
            ;
         }

         if(var13 == 3) {
            ;
         }
      }

      return true;
   }

   public void copy$animate(int var1) {
      if(this.pixels != null) {
         short var2;
         int var3;
         int var4;
         int var5;
         int var6;
         int var7;
         int[] var10;
         if(this.animationDirection == 1 || this.animationDirection == 3) {
            if(Texture_animatedPixels == null || Texture_animatedPixels.length < this.pixels.length) {
               Texture_animatedPixels = new int[this.pixels.length];
            }

            if(this.pixels.length == 4096) {
               var2 = 64;
            } else {
               var2 = 128;
            }

            var3 = this.pixels.length;
            var4 = var2 * this.animationSpeed * var1;
            var5 = var3 - 1;
            if(this.animationDirection == 1) {
               var4 = -var4;
            }

            for(var6 = 0; var6 < var3; ++var6) {
               var7 = var6 + var4 & var5;
               Texture_animatedPixels[var6] = this.pixels[var7];
            }

            var10 = this.pixels;
            this.pixels = Texture_animatedPixels;
            Texture_animatedPixels = var10;
         }

         if(this.animationDirection == 2 || this.animationDirection == 4) {
            if(Texture_animatedPixels == null || Texture_animatedPixels.length < this.pixels.length) {
               Texture_animatedPixels = new int[this.pixels.length];
            }

            if(this.pixels.length == 4096) {
               var2 = 64;
            } else {
               var2 = 128;
            }

            var3 = this.pixels.length;
            var4 = this.animationSpeed * var1;
            var5 = var2 - 1;
            if(this.animationDirection == 2) {
               var4 = -var4;
            }

            for(var6 = 0; var6 < var3; var6 += var2) {
               for(var7 = 0; var7 < var2; ++var7) {
                  int var8 = var6 + var7;
                  int var9 = var6 + (var7 + var4 & var5);
                  Texture_animatedPixels[var8] = this.pixels[var9];
               }
            }

            var10 = this.pixels;
            this.pixels = Texture_animatedPixels;
            Texture_animatedPixels = var10;
         }

      }
   }

   public float getU() {
      return this.rl$u;
   }

   public void setU(float var1) {
      this.rl$u = var1;
   }

   public float getV() {
      return this.rl$v;
   }

   public void setV(float var1) {
      this.rl$v = var1;
   }

   public int getAnimationDirection() {
      return this.animationDirection;
   }

   public int getAnimationSpeed() {
      return this.animationSpeed;
   }

   public int[] getPixels() {
      return this.pixels;
   }

   public boolean isLoaded() {
      return this.isLoaded;
   }
}
