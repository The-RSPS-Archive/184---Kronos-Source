package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.rs.api.RSTileModel;

@ObfuscatedName("dd")
public final class TileModel implements RSTileModel {
   @ObfuscatedName("s")
   static final int[][] field1330;
   @ObfuscatedName("t")
   static int[] field1344;
   @ObfuscatedName("w")
   static int[] field1328;
   @ObfuscatedName("x")
   static int[] field1346;
   @ObfuscatedName("f")
   static final int[][] field1334;
   @ObfuscatedName("g")
   static int[] field1345;
   @ObfuscatedName("h")
   static int[] field1348;
   @ObfuscatedName("n")
   int[] vertexY;
   @ObfuscatedName("o")
   int rotation;
   @ObfuscatedName("p")
   int[] triangleColorC;
   @ObfuscatedName("q")
   int[] faceX;
   @ObfuscatedName("r")
   int[] triangleColorB;
   @ObfuscatedName("u")
   int[] triangleColorA;
   @ObfuscatedName("v")
   int[] vertexZ;
   @ObfuscatedName("y")
   int[] faceZ;
   @ObfuscatedName("z")
   int[] vertexX;
   @ObfuscatedName("a")
   int underlayRgb;
   @ObfuscatedName("b")
   int shape;
   @ObfuscatedName("c")
   boolean isFlat;
   public int rl$modelBufferOffset;
   public int rl$modelUvBufferOffset;
   public int rl$modelBufferLen;
   @ObfuscatedName("e")
   int overlayRgb;
   @ObfuscatedName("i")
   int[] triangleTextureId;
   @ObfuscatedName("m")
   int[] faceY;

   static {
      field1328 = new int[6];
      field1344 = new int[6];
      field1345 = new int[6];
      field1346 = new int[6];
      field1348 = new int[6];
      field1330 = new int[][]{{1, 3, 5, 7}, {1, 3, 5, 7}, {1, 3, 5, 7}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 2, 6}, {1, 3, 5, 7, 2, 8}, {1, 3, 5, 7, 2, 8}, {1, 3, 5, 7, 11, 12}, {1, 3, 5, 7, 11, 12}, {1, 3, 5, 7, 13, 14}};
      field1334 = new int[][]{{0, 1, 2, 3, 0, 0, 1, 3}, {1, 1, 2, 3, 1, 0, 1, 3}, {0, 1, 2, 3, 1, 0, 1, 3}, {0, 0, 1, 2, 0, 0, 2, 4, 1, 0, 4, 3}, {0, 0, 1, 4, 0, 0, 4, 3, 1, 1, 2, 4}, {0, 0, 4, 3, 1, 0, 1, 2, 1, 0, 2, 4}, {0, 1, 2, 4, 1, 0, 1, 4, 1, 0, 4, 3}, {0, 4, 1, 2, 0, 4, 2, 5, 1, 0, 4, 5, 1, 0, 5, 3}, {0, 4, 1, 2, 0, 4, 2, 3, 0, 4, 3, 5, 1, 0, 4, 5}, {0, 0, 4, 5, 1, 4, 1, 2, 1, 4, 2, 3, 1, 4, 3, 5}, {0, 0, 1, 5, 0, 1, 4, 5, 0, 1, 2, 4, 1, 0, 5, 3, 1, 5, 4, 3, 1, 4, 2, 3}, {1, 0, 1, 5, 1, 1, 4, 5, 1, 1, 2, 4, 0, 0, 5, 3, 0, 5, 4, 3, 0, 4, 2, 3}, {1, 0, 5, 4, 1, 0, 1, 5, 0, 0, 4, 3, 0, 4, 5, 3, 0, 5, 2, 3, 0, 1, 2, 5}};
   }

   TileModel(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19) {
      this.isFlat = true;
      if(var7 != var6 || var8 != var6 || var9 != var6) {
         this.isFlat = false;
      }

      this.shape = var1;
      this.rotation = var2;
      this.underlayRgb = var18;
      this.overlayRgb = var19;
      short var20 = 128;
      int var21 = var20 / 2;
      int var22 = var20 / 4;
      int var23 = var20 * 3 / 4;
      int[] var24 = field1330[var1];
      int var25 = var24.length;
      this.vertexX = new int[var25];
      this.vertexY = new int[var25];
      this.vertexZ = new int[var25];
      int[] var26 = new int[var25];
      int[] var27 = new int[var25];
      int var28 = var20 * var4;
      int var29 = var5 * var20;

      int var31;
      int var32;
      int var33;
      int var34;
      int var35;
      int var36;
      for(int var30 = 0; var30 < var25; ++var30) {
         var31 = var24[var30];
         if((var31 & 1) == 0 && var31 <= 8) {
            var31 = (var31 - var2 - var2 - 1 & 7) + 1;
         }

         if(var31 > 8 && var31 <= 12) {
            var31 = (var31 - 9 - var2 & 3) + 9;
         }

         if(var31 > 12 && var31 <= 16) {
            var31 = (var31 - 13 - var2 & 3) + 13;
         }

         if(var31 == 1) {
            var32 = var28;
            var33 = var29;
            var34 = var6;
            var35 = var10;
            var36 = var14;
         } else if(var31 == 2) {
            var32 = var28 + var21;
            var33 = var29;
            var34 = var7 + var6 >> 1;
            var35 = var11 + var10 >> 1;
            var36 = var15 + var14 >> 1;
         } else if(var31 == 3) {
            var32 = var28 + var20;
            var33 = var29;
            var34 = var7;
            var35 = var11;
            var36 = var15;
         } else if(var31 == 4) {
            var32 = var28 + var20;
            var33 = var29 + var21;
            var34 = var8 + var7 >> 1;
            var35 = var11 + var12 >> 1;
            var36 = var15 + var16 >> 1;
         } else if(var31 == 5) {
            var32 = var28 + var20;
            var33 = var29 + var20;
            var34 = var8;
            var35 = var12;
            var36 = var16;
         } else if(var31 == 6) {
            var32 = var28 + var21;
            var33 = var29 + var20;
            var34 = var8 + var9 >> 1;
            var35 = var13 + var12 >> 1;
            var36 = var17 + var16 >> 1;
         } else if(var31 == 7) {
            var32 = var28;
            var33 = var29 + var20;
            var34 = var9;
            var35 = var13;
            var36 = var17;
         } else if(var31 == 8) {
            var32 = var28;
            var33 = var29 + var21;
            var34 = var9 + var6 >> 1;
            var35 = var13 + var10 >> 1;
            var36 = var17 + var14 >> 1;
         } else if(var31 == 9) {
            var32 = var28 + var21;
            var33 = var29 + var22;
            var34 = var7 + var6 >> 1;
            var35 = var11 + var10 >> 1;
            var36 = var15 + var14 >> 1;
         } else if(var31 == 10) {
            var32 = var28 + var23;
            var33 = var29 + var21;
            var34 = var8 + var7 >> 1;
            var35 = var11 + var12 >> 1;
            var36 = var15 + var16 >> 1;
         } else if(var31 == 11) {
            var32 = var28 + var21;
            var33 = var29 + var23;
            var34 = var8 + var9 >> 1;
            var35 = var13 + var12 >> 1;
            var36 = var17 + var16 >> 1;
         } else if(var31 == 12) {
            var32 = var28 + var22;
            var33 = var29 + var21;
            var34 = var9 + var6 >> 1;
            var35 = var13 + var10 >> 1;
            var36 = var17 + var14 >> 1;
         } else if(var31 == 13) {
            var32 = var28 + var22;
            var33 = var29 + var22;
            var34 = var6;
            var35 = var10;
            var36 = var14;
         } else if(var31 == 14) {
            var32 = var28 + var23;
            var33 = var29 + var22;
            var34 = var7;
            var35 = var11;
            var36 = var15;
         } else if(var31 == 15) {
            var32 = var28 + var23;
            var33 = var29 + var23;
            var34 = var8;
            var35 = var12;
            var36 = var16;
         } else {
            var32 = var28 + var22;
            var33 = var29 + var23;
            var34 = var9;
            var35 = var13;
            var36 = var17;
         }

         this.vertexX[var30] = var32;
         this.vertexY[var30] = var34;
         this.vertexZ[var30] = var33;
         var26[var30] = var35;
         var27[var30] = var36;
      }

      int[] var38 = field1334[var1];
      var31 = var38.length / 4;
      this.faceX = new int[var31];
      this.faceY = new int[var31];
      this.faceZ = new int[var31];
      this.triangleColorA = new int[var31];
      this.triangleColorB = new int[var31];
      this.triangleColorC = new int[var31];
      if(var3 != -1) {
         this.triangleTextureId = new int[var31];
      }

      var32 = 0;

      for(var33 = 0; var33 < var31; ++var33) {
         var34 = var38[var32];
         var35 = var38[var32 + 1];
         var36 = var38[var32 + 2];
         int var37 = var38[var32 + 3];
         var32 += 4;
         if(var35 < 4) {
            var35 = var35 - var2 & 3;
         }

         if(var36 < 4) {
            var36 = var36 - var2 & 3;
         }

         if(var37 < 4) {
            var37 = var37 - var2 & 3;
         }

         this.faceX[var33] = var35;
         this.faceY[var33] = var36;
         this.faceZ[var33] = var37;
         if(var34 == 0) {
            this.triangleColorA[var33] = var26[var35];
            this.triangleColorB[var33] = var26[var36];
            this.triangleColorC[var33] = var26[var37];
            if(this.triangleTextureId != null) {
               this.triangleTextureId[var33] = -1;
            }
         } else {
            this.triangleColorA[var33] = var27[var35];
            this.triangleColorB[var33] = var27[var36];
            this.triangleColorC[var33] = var27[var37];
            if(this.triangleTextureId != null) {
               this.triangleTextureId[var33] = var3;
            }
         }
      }

      var33 = var6;
      var34 = var7;
      if(var7 < var6) {
         var33 = var7;
      }

      if(var7 > var7) {
         var34 = var7;
      }

      if(var8 < var33) {
         var33 = var8;
      }

      if(var8 > var34) {
         var34 = var8;
      }

      if(var9 < var33) {
         var33 = var9;
      }

      if(var9 > var34) {
         var34 = var9;
      }

      var33 /= 14;
      var34 /= 14;
   }

   public int getBufferOffset() {
      return this.rl$modelBufferOffset;
   }

   public void setBufferOffset(int var1) {
      this.rl$modelBufferOffset = var1;
   }

   public int getUvBufferOffset() {
      return this.rl$modelUvBufferOffset;
   }

   public void setUvBufferOffset(int var1) {
      this.rl$modelUvBufferOffset = var1;
   }

   public int getBufferLen() {
      return this.rl$modelBufferLen;
   }

   public void setBufferLen(int var1) {
      this.rl$modelBufferLen = var1;
   }

   public int[] getVertexX() {
      return this.vertexX;
   }

   public int[] getVertexY() {
      return this.vertexY;
   }

   public int[] getVertexZ() {
      return this.vertexZ;
   }

   public int[] getTriangleColorA() {
      return this.triangleColorA;
   }

   public int[] getTriangleColorB() {
      return this.triangleColorB;
   }

   public int[] getTriangleColorC() {
      return this.triangleColorC;
   }

   public int[] getFaceX() {
      return this.faceX;
   }

   public int[] getFaceY() {
      return this.faceY;
   }

   public int[] getFaceZ() {
      return this.faceZ;
   }

   public int[] getTriangleTextureId() {
      return this.triangleTextureId;
   }

   public int getShape() {
      return this.shape;
   }

   public int getRotation() {
      return this.rotation;
   }

   public int getModelUnderlay() {
      return this.underlayRgb;
   }

   public int getModelOverlay() {
      return this.overlayRgb;
   }
}
