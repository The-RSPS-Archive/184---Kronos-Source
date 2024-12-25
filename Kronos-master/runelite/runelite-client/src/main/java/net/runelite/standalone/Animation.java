package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSAnimation;
import net.runelite.rs.api.RSSkeleton;

@ObfuscatedName("da")
public class Animation implements RSAnimation {
   @ObfuscatedName("n")
   static int[] field1295;
   @ObfuscatedName("u")
   static int[] field1297;
   @ObfuscatedName("v")
   static int[] field1294;
   @ObfuscatedName("z")
   static int[] field1304;
   @ObfuscatedName("p")
   int transformCount;
   @ObfuscatedName("q")
   int[] transformSkeletonLabels;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Lew;"
   )
   Skeleton skeleton;
   @ObfuscatedName("y")
   int[] transformYs;
   @ObfuscatedName("c")
   boolean hasAlphaTransform;
   @ObfuscatedName("i")
   int[] transformZs;
   @ObfuscatedName("m")
   int[] transformXs;

   static {
      field1304 = new int[500];
      field1295 = new int[500];
      field1294 = new int[500];
      field1297 = new int[500];
   }

   @ObfuscatedSignature(
      signature = "([BLew;)V"
   )
   Animation(byte[] var1, Skeleton var2) {
      this.skeleton = null;
      this.transformCount = -1;
      this.hasAlphaTransform = false;
      this.skeleton = var2;
      Buffer var3 = new Buffer(var1);
      Buffer var4 = new Buffer(var1);
      var3.offset = 2;
      int var5 = var3.readUnsignedByte();
      int var6 = -1;
      int var7 = 0;
      var4.offset = var5 + var3.offset;

      int var8;
      for(var8 = 0; var8 < var5; ++var8) {
         int var9 = var3.readUnsignedByte();
         if(var9 > 0) {
            if(this.skeleton.transformTypes[var8] != 0) {
               for(int var10 = var8 - 1; var10 > var6; --var10) {
                  if(this.skeleton.transformTypes[var10] == 0) {
                     field1304[var7] = var10;
                     field1295[var7] = 0;
                     field1294[var7] = 0;
                     field1297[var7] = 0;
                     ++var7;
                     break;
                  }
               }
            }

            field1304[var7] = var8;
            short var11 = 0;
            if(this.skeleton.transformTypes[var8] == 3) {
               var11 = 128;
            }

            if((var9 & 1) != 0) {
               field1295[var7] = var4.method5509();
            } else {
               field1295[var7] = var11;
            }

            if((var9 & 2) != 0) {
               field1294[var7] = var4.method5509();
            } else {
               field1294[var7] = var11;
            }

            if((var9 & 4) != 0) {
               field1297[var7] = var4.method5509();
            } else {
               field1297[var7] = var11;
            }

            var6 = var8;
            ++var7;
            if(this.skeleton.transformTypes[var8] == 5) {
               this.hasAlphaTransform = true;
            }
         }
      }

      if(var1.length != var4.offset) {
         throw new RuntimeException();
      } else {
         this.transformCount = var7;
         this.transformSkeletonLabels = new int[var7];
         this.transformXs = new int[var7];
         this.transformYs = new int[var7];
         this.transformZs = new int[var7];

         for(var8 = 0; var8 < var7; ++var8) {
            this.transformSkeletonLabels[var8] = field1304[var8];
            this.transformXs[var8] = field1295[var8];
            this.transformYs[var8] = field1294[var8];
            this.transformZs[var8] = field1297[var8];
         }

      }
   }

   public RSSkeleton getSkin() {
      return this.skeleton;
   }

   public int getTransformCount() {
      return this.transformCount;
   }

   public int[] getTransformTypes() {
      return this.transformSkeletonLabels;
   }

   public int[] getTranslatorX() {
      return this.transformXs;
   }

   public int[] getTranslatorY() {
      return this.transformYs;
   }

   public int[] getTranslatorZ() {
      return this.transformZs;
   }

   public boolean isShowing() {
      return this.hasAlphaTransform;
   }
}
