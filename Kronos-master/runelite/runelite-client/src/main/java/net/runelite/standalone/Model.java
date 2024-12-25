package net.runelite.standalone;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.model.Jarvis;
import net.runelite.api.model.Triangle;
import net.runelite.api.model.Vertex;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSAnimation;
import net.runelite.rs.api.RSFrames;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSSkeleton;

@ObfuscatedName("dh")
public class Model extends Entity implements RSModel {
   @ObfuscatedName("n")
   static byte[] Model_sharedSequenceModelFaceAlphas;
   @ObfuscatedName("u")
   static byte[] Model_sharedSpotAnimationModelFaceAlphas;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Ldh;"
   )
   static Model Model_sharedSpotAnimationModel;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Ldh;"
   )
   static Model Model_sharedSequenceModel;
   @ObfuscatedName("ae")
   static int[] field1396;
   @ObfuscatedName("af")
   static int[] field1423;
   @ObfuscatedName("ag")
   static int[] field1419;
   @ObfuscatedName("ah")
   static int[] field1392;
   @ObfuscatedName("ai")
   static boolean[] field1412;
   @ObfuscatedName("ak")
   static boolean[] field1413;
   @ObfuscatedName("am")
   static int[] modelViewportYs;
   @ObfuscatedName("aq")
   static int[] modelViewportXs;
   @ObfuscatedName("at")
   static int[][] field1385;
   @ObfuscatedName("au")
   static int[][] field1422;
   @ObfuscatedName("av")
   static int[] field1417;
   @ObfuscatedName("ay")
   static int[] field1421;
   @ObfuscatedName("bb")
   static int[] field1440;
   @ObfuscatedName("bc")
   static int Model_transformTempZ;
   @ObfuscatedName("bd")
   static int[] field1427;
   @ObfuscatedName("be")
   static int[] Model_sine;
   @ObfuscatedName("bh")
   static int[] field1398;
   @ObfuscatedName("bi")
   static int[] field1439;
   @ObfuscatedName("bj")
   static int[] field1430;
   @ObfuscatedName("bk")
   static boolean field1434;
   @ObfuscatedName("bm")
   static int[] field1428;
   @ObfuscatedName("bs")
   static int Model_transformTempX;
   @ObfuscatedName("bu")
   static int[] Model_cosine;
   @ObfuscatedName("bv")
   static int[] field1416;
   @ObfuscatedName("bx")
   static int[] field1437;
   @ObfuscatedName("bz")
   static int Model_transformTempY;
   public static boolean $assertionsDisabled;
   @ObfuscatedName("o")
   int[] faceColors1;
   @ObfuscatedName("p")
   int[] verticesX;
   @ObfuscatedName("q")
   int[] verticesY;
   @ObfuscatedName("r")
   int verticesCount;
   @ObfuscatedName("s")
   int field1425;
   @ObfuscatedName("t")
   byte[] faceAlphas;
   @ObfuscatedName("w")
   byte[] faceRenderPriorities;
   @ObfuscatedName("x")
   short[] faceTextures;
   @ObfuscatedName("y")
   int indicesCount;
   @ObfuscatedName("a")
   int[] faceColors2;
   @ObfuscatedName("aa")
   int xzRadius;
   @ObfuscatedName("ab")
   int xMid;
   @ObfuscatedName("ac")
   public boolean isSingleTile;
   @ObfuscatedName("ad")
   int zMidOffset;
   @ObfuscatedName("al")
   int yMidOffset;
   @ObfuscatedName("ao")
   int xMidOffset;
   @ObfuscatedName("ap")
   int diameter;
   @ObfuscatedName("ar")
   int radius;
   @ObfuscatedName("as")
   int zMid;
   @ObfuscatedName("aw")
   int bottomY;
   @ObfuscatedName("ax")
   int yMid;
   @ObfuscatedName("az")
   int boundsType;
   @ObfuscatedName("b")
   int[] indices3;
   @ObfuscatedName("c")
   int[] indices2;
   public int rl$sceneId;
   public int rl$bufferOffset;
   public int rl$uvBufferOffset;
   public float[][] rl$faceTextureUCoordinates;
   public float[][] rl$faceTextureVCoordinates;
   @ObfuscatedName("d")
   int[] field1393;
   @ObfuscatedName("e")
   int[] faceColors3;
   @ObfuscatedName("f")
   int[] field1394;
   @ObfuscatedName("g")
   byte[] field1379;
   @ObfuscatedName("h")
   byte field1374;
   @ObfuscatedName("i")
   int[] indices1;
   @ObfuscatedName("j")
   int[] field1395;
   @ObfuscatedName("k")
   int[][] faceLabelsAlpha;
   @ObfuscatedName("l")
   int[][] vertexLabels;
   @ObfuscatedName("m")
   int[] verticesZ;

   static {
      Model_sharedSequenceModel = new Model();
      Model_sharedSequenceModelFaceAlphas = new byte[1];
      Model_sharedSpotAnimationModel = new Model();
      Model_sharedSpotAnimationModelFaceAlphas = new byte[1];
      field1412 = new boolean[4700];
      field1413 = new boolean[4700];
      modelViewportXs = new int[4700];
      modelViewportYs = new int[4700];
      field1396 = new int[4700];
      field1417 = new int[4700];
      field1392 = new int[4700];
      field1419 = new int[4700];
      field1421 = new int[1600];
      field1422 = new int[1600][512];
      field1423 = new int[12];
      field1385 = new int[12][2000];
      field1437 = new int[2000];
      field1398 = new int[2000];
      field1427 = new int[12];
      field1428 = new int[10];
      field1416 = new int[10];
      field1430 = new int[10];
      field1434 = true;
      Model_sine = Rasterizer3D.Rasterizer3D_sine;
      Model_cosine = Rasterizer3D.Rasterizer3D_cosine;
      field1439 = Rasterizer3D.Rasterizer3D_colorPalette;
      field1440 = Rasterizer3D.field1747;
      rl$$clinit();
   }

   Model() {
      this.verticesCount = 0;
      this.indicesCount = 0;
      this.field1374 = 0;
      this.field1425 = 0;
      this.isSingleTile = false;
      this.xMidOffset = -1;
      this.yMidOffset = -1;
      this.zMidOffset = -1;
   }

   @ObfuscatedSignature(
      signature = "([Ldh;I)V"
   )
   public Model(Model[] var1, int var2) {
      this.verticesCount = 0;
      this.indicesCount = 0;
      this.field1374 = 0;
      this.field1425 = 0;
      this.isSingleTile = false;
      this.xMidOffset = -1;
      this.yMidOffset = -1;
      this.zMidOffset = -1;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;
      this.verticesCount = 0;
      this.indicesCount = 0;
      this.field1425 = 0;
      this.field1374 = -1;

      int var7;
      Model var8;
      for(var7 = 0; var7 < var2; ++var7) {
         var8 = var1[var7];
         if(var8 != null) {
            this.verticesCount += var8.verticesCount;
            this.indicesCount += var8.indicesCount;
            this.field1425 += var8.field1425;
            if(var8.faceRenderPriorities != null) {
               var3 = true;
            } else {
               if(this.field1374 == -1) {
                  this.field1374 = var8.field1374;
               }

               if(this.field1374 != var8.field1374) {
                  var3 = true;
               }
            }

            var4 |= var8.faceAlphas != null;
            var5 |= var8.faceTextures != null;
            var6 |= var8.field1379 != null;
         }
      }

      this.verticesX = new int[this.verticesCount];
      this.verticesY = new int[this.verticesCount];
      this.verticesZ = new int[this.verticesCount];
      this.indices1 = new int[this.indicesCount];
      this.indices2 = new int[this.indicesCount];
      this.indices3 = new int[this.indicesCount];
      this.faceColors1 = new int[this.indicesCount];
      this.faceColors2 = new int[this.indicesCount];
      this.faceColors3 = new int[this.indicesCount];
      if(var3) {
         this.faceRenderPriorities = new byte[this.indicesCount];
      }

      if(var4) {
         this.faceAlphas = new byte[this.indicesCount];
      }

      if(var5) {
         this.faceTextures = new short[this.indicesCount];
      }

      if(var6) {
         this.field1379 = new byte[this.indicesCount];
      }

      if(this.field1425 > 0) {
         this.field1394 = new int[this.field1425];
         this.field1395 = new int[this.field1425];
         this.field1393 = new int[this.field1425];
      }

      this.verticesCount = 0;
      this.indicesCount = 0;
      this.field1425 = 0;

      for(var7 = 0; var7 < var2; ++var7) {
         var8 = var1[var7];
         if(var8 != null) {
            int var9;
            for(var9 = 0; var9 < var8.indicesCount; ++var9) {
               this.indices1[this.indicesCount] = this.verticesCount + var8.indices1[var9];
               this.indices2[this.indicesCount] = this.verticesCount + var8.indices2[var9];
               this.indices3[this.indicesCount] = this.verticesCount + var8.indices3[var9];
               this.faceColors1[this.indicesCount] = var8.faceColors1[var9];
               this.faceColors2[this.indicesCount] = var8.faceColors2[var9];
               this.faceColors3[this.indicesCount] = var8.faceColors3[var9];
               if(var3) {
                  if(var8.faceRenderPriorities != null) {
                     this.faceRenderPriorities[this.indicesCount] = var8.faceRenderPriorities[var9];
                  } else {
                     this.faceRenderPriorities[this.indicesCount] = var8.field1374;
                  }
               }

               if(var4 && var8.faceAlphas != null) {
                  this.faceAlphas[this.indicesCount] = var8.faceAlphas[var9];
               }

               if(var5) {
                  if(var8.faceTextures != null) {
                     this.faceTextures[this.indicesCount] = var8.faceTextures[var9];
                  } else {
                     this.faceTextures[this.indicesCount] = -1;
                  }
               }

               if(var6) {
                  if(var8.field1379 != null && var8.field1379[var9] != -1) {
                     this.field1379[this.indicesCount] = (byte)(this.field1425 + var8.field1379[var9]);
                  } else {
                     this.field1379[this.indicesCount] = -1;
                  }
               }

               ++this.indicesCount;
            }

            for(var9 = 0; var9 < var8.field1425; ++var9) {
               this.field1394[this.field1425] = this.verticesCount + var8.field1394[var9];
               this.field1395[this.field1425] = this.verticesCount + var8.field1395[var9];
               this.field1393[this.field1425] = this.verticesCount + var8.field1393[var9];
               ++this.field1425;
            }

            for(var9 = 0; var9 < var8.verticesCount; ++var9) {
               this.verticesX[this.verticesCount] = var8.verticesX[var9];
               this.verticesY[this.verticesCount] = var8.verticesY[var9];
               this.verticesZ[this.verticesCount] = var8.verticesZ[var9];
               ++this.verticesCount;
            }
         }
      }

      this.rl$init(var1, var2);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Z)Ldh;"
   )
   public Model method2355(boolean var1) {
      if(!var1 && Model_sharedSequenceModelFaceAlphas.length < this.indicesCount) {
         Model_sharedSequenceModelFaceAlphas = new byte[this.indicesCount + 100];
      }

      return this.method2357(var1, Model_sharedSequenceModel, Model_sharedSequenceModelFaceAlphas);
   }

   @ObfuscatedName("o")
   void method2365(int var1, int[] var2, int var3, int var4, int var5) {
      int var6 = var2.length;
      int var7;
      int var8;
      int var11;
      int var12;
      if(var1 == 0) {
         var7 = 0;
         Model_transformTempX = 0;
         Model_transformTempY = 0;
         Model_transformTempZ = 0;

         for(var8 = 0; var8 < var6; ++var8) {
            int var9 = var2[var8];
            if(var9 < this.vertexLabels.length) {
               int[] var10 = this.vertexLabels[var9];

               for(var11 = 0; var11 < var10.length; ++var11) {
                  var12 = var10[var11];
                  Model_transformTempX += this.verticesX[var12];
                  Model_transformTempY += this.verticesY[var12];
                  Model_transformTempZ += this.verticesZ[var12];
                  ++var7;
               }
            }
         }

         if(var7 > 0) {
            Model_transformTempX = var3 + Model_transformTempX / var7;
            Model_transformTempY = var4 + Model_transformTempY / var7;
            Model_transformTempZ = var5 + Model_transformTempZ / var7;
         } else {
            Model_transformTempX = var3;
            Model_transformTempY = var4;
            Model_transformTempZ = var5;
         }

      } else {
         int[] var18;
         int var19;
         if(var1 == 1) {
            for(var7 = 0; var7 < var6; ++var7) {
               var8 = var2[var7];
               if(var8 < this.vertexLabels.length) {
                  var18 = this.vertexLabels[var8];

                  for(var19 = 0; var19 < var18.length; ++var19) {
                     var11 = var18[var19];
                     this.verticesX[var11] += var3;
                     this.verticesY[var11] += var4;
                     this.verticesZ[var11] += var5;
                  }
               }
            }

         } else if(var1 == 2) {
            for(var7 = 0; var7 < var6; ++var7) {
               var8 = var2[var7];
               if(var8 < this.vertexLabels.length) {
                  var18 = this.vertexLabels[var8];

                  for(var19 = 0; var19 < var18.length; ++var19) {
                     var11 = var18[var19];
                     this.verticesX[var11] -= Model_transformTempX;
                     this.verticesY[var11] -= Model_transformTempY;
                     this.verticesZ[var11] -= Model_transformTempZ;
                     var12 = (var3 & 255) * 8;
                     int var13 = (var4 & 255) * 8;
                     int var14 = (var5 & 255) * 8;
                     int var15;
                     int var16;
                     int var17;
                     if(var14 != 0) {
                        var15 = Model_sine[var14];
                        var16 = Model_cosine[var14];
                        var17 = var15 * this.verticesY[var11] + var16 * this.verticesX[var11] >> 16;
                        this.verticesY[var11] = var16 * this.verticesY[var11] - var15 * this.verticesX[var11] >> 16;
                        this.verticesX[var11] = var17;
                     }

                     if(var12 != 0) {
                        var15 = Model_sine[var12];
                        var16 = Model_cosine[var12];
                        var17 = var16 * this.verticesY[var11] - var15 * this.verticesZ[var11] >> 16;
                        this.verticesZ[var11] = var15 * this.verticesY[var11] + var16 * this.verticesZ[var11] >> 16;
                        this.verticesY[var11] = var17;
                     }

                     if(var13 != 0) {
                        var15 = Model_sine[var13];
                        var16 = Model_cosine[var13];
                        var17 = var15 * this.verticesZ[var11] + var16 * this.verticesX[var11] >> 16;
                        this.verticesZ[var11] = var16 * this.verticesZ[var11] - var15 * this.verticesX[var11] >> 16;
                        this.verticesX[var11] = var17;
                     }

                     this.verticesX[var11] += Model_transformTempX;
                     this.verticesY[var11] += Model_transformTempY;
                     this.verticesZ[var11] += Model_transformTempZ;
                  }
               }
            }

         } else if(var1 == 3) {
            for(var7 = 0; var7 < var6; ++var7) {
               var8 = var2[var7];
               if(var8 < this.vertexLabels.length) {
                  var18 = this.vertexLabels[var8];

                  for(var19 = 0; var19 < var18.length; ++var19) {
                     var11 = var18[var19];
                     this.verticesX[var11] -= Model_transformTempX;
                     this.verticesY[var11] -= Model_transformTempY;
                     this.verticesZ[var11] -= Model_transformTempZ;
                     this.verticesX[var11] = var3 * this.verticesX[var11] / 128;
                     this.verticesY[var11] = var4 * this.verticesY[var11] / 128;
                     this.verticesZ[var11] = var5 * this.verticesZ[var11] / 128;
                     this.verticesX[var11] += Model_transformTempX;
                     this.verticesY[var11] += Model_transformTempY;
                     this.verticesZ[var11] += Model_transformTempZ;
                  }
               }
            }

         } else if(var1 == 5) {
            if(this.faceLabelsAlpha != null && this.faceAlphas != null) {
               for(var7 = 0; var7 < var6; ++var7) {
                  var8 = var2[var7];
                  if(var8 < this.faceLabelsAlpha.length) {
                     var18 = this.faceLabelsAlpha[var8];

                     for(var19 = 0; var19 < var18.length; ++var19) {
                        var11 = var18[var19];
                        var12 = (this.faceAlphas[var11] & 255) + var3 * 8;
                        if(var12 < 0) {
                           var12 = 0;
                        } else if(var12 > 255) {
                           var12 = 255;
                        }

                        this.faceAlphas[var11] = (byte)var12;
                     }
                  }
               }
            }

         }
      }
   }

   @ObfuscatedName("p")
   public void method2359() {
      if(this.boundsType != 1) {
         this.boundsType = 1;
         super.height = 0;
         this.bottomY = 0;
         this.xzRadius = 0;

         for(int var1 = 0; var1 < this.verticesCount; ++var1) {
            int var2 = this.verticesX[var1];
            int var3 = this.verticesY[var1];
            int var4 = this.verticesZ[var1];
            if(-var3 > super.height) {
               super.height = -var3;
            }

            if(var3 > this.bottomY) {
               this.bottomY = var3;
            }

            int var5 = var2 * var2 + var4 * var4;
            if(var5 > this.xzRadius) {
               this.xzRadius = var5;
            }
         }

         this.xzRadius = (int)(Math.sqrt((double)this.xzRadius) + 0.99D);
         this.radius = (int)(Math.sqrt((double)(this.xzRadius * this.xzRadius + super.height * super.height)) + 0.99D);
         this.diameter = this.radius + (int)(Math.sqrt((double)(this.xzRadius * this.xzRadius + this.bottomY * this.bottomY)) + 0.99D);
      }
   }

   @ObfuscatedName("q")
   void method2360() {
      if(this.boundsType != 2) {
         this.boundsType = 2;
         this.xzRadius = 0;

         for(int var1 = 0; var1 < this.verticesCount; ++var1) {
            int var2 = this.verticesX[var1];
            int var3 = this.verticesY[var1];
            int var4 = this.verticesZ[var1];
            int var5 = var2 * var2 + var4 * var4 + var3 * var3;
            if(var5 > this.xzRadius) {
               this.xzRadius = var5;
            }
         }

         this.xzRadius = (int)(Math.sqrt((double)this.xzRadius) + 0.99D);
         this.radius = this.xzRadius;
         this.diameter = this.xzRadius + this.xzRadius;
      }
   }

   @ObfuscatedName("r")
   void method2356(int var1) {
      if(this.xMidOffset == -1) {
         int var2 = 0;
         int var3 = 0;
         int var4 = 0;
         int var5 = 0;
         int var6 = 0;
         int var7 = 0;
         int var8 = Model_cosine[var1];
         int var9 = Model_sine[var1];

         for(int var10 = 0; var10 < this.verticesCount; ++var10) {
            int var11 = Rasterizer3D.method2962(this.verticesX[var10], this.verticesZ[var10], var8, var9);
            int var12 = this.verticesY[var10];
            int var13 = Rasterizer3D.method2963(this.verticesX[var10], this.verticesZ[var10], var8, var9);
            if(var11 < var2) {
               var2 = var11;
            }

            if(var11 > var5) {
               var5 = var11;
            }

            if(var12 < var3) {
               var3 = var12;
            }

            if(var12 > var6) {
               var6 = var12;
            }

            if(var13 < var4) {
               var4 = var13;
            }

            if(var13 > var7) {
               var7 = var13;
            }
         }

         this.xMid = (var5 + var2) / 2;
         this.yMid = (var6 + var3) / 2;
         this.zMid = (var7 + var4) / 2;
         this.xMidOffset = (var5 - var2 + 1) / 2;
         this.yMidOffset = (var6 - var3 + 1) / 2;
         this.zMidOffset = (var7 - var4 + 1) / 2;
         if(this.xMidOffset < 32) {
            this.xMidOffset = 32;
         }

         if(this.zMidOffset < 32) {
            this.zMidOffset = 32;
         }

         if(this.isSingleTile) {
            this.xMidOffset += 8;
            this.zMidOffset += 8;
         }

      }
   }

   @ObfuscatedName("s")
   public void method2423(int var1, int var2, int var3) {
      for(int var4 = 0; var4 < this.verticesCount; ++var4) {
         this.verticesX[var4] += var1;
         this.verticesY[var4] += var2;
         this.verticesZ[var4] += var3;
      }

      this.method2397();
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(ZLdh;[B)Ldh;"
   )
   Model method2357(boolean var1, Model var2, byte[] var3) {
      this.rl$buildSharedModel(var1, var2, var3);
      var2.verticesCount = this.verticesCount;
      var2.indicesCount = this.indicesCount;
      var2.field1425 = this.field1425;
      if(var2.verticesX == null || var2.verticesX.length < this.verticesCount) {
         var2.verticesX = new int[this.verticesCount + 100];
         var2.verticesY = new int[this.verticesCount + 100];
         var2.verticesZ = new int[this.verticesCount + 100];
      }

      int var4;
      for(var4 = 0; var4 < this.verticesCount; ++var4) {
         var2.verticesX[var4] = this.verticesX[var4];
         var2.verticesY[var4] = this.verticesY[var4];
         var2.verticesZ[var4] = this.verticesZ[var4];
      }

      if(var1) {
         var2.faceAlphas = this.faceAlphas;
      } else {
         var2.faceAlphas = var3;
         if(this.faceAlphas == null) {
            for(var4 = 0; var4 < this.indicesCount; ++var4) {
               var2.faceAlphas[var4] = 0;
            }
         } else {
            for(var4 = 0; var4 < this.indicesCount; ++var4) {
               var2.faceAlphas[var4] = this.faceAlphas[var4];
            }
         }
      }

      var2.indices1 = this.indices1;
      var2.indices2 = this.indices2;
      var2.indices3 = this.indices3;
      var2.faceColors1 = this.faceColors1;
      var2.faceColors2 = this.faceColors2;
      var2.faceColors3 = this.faceColors3;
      var2.faceRenderPriorities = this.faceRenderPriorities;
      var2.field1379 = this.field1379;
      var2.faceTextures = this.faceTextures;
      var2.field1374 = this.field1374;
      var2.field1394 = this.field1394;
      var2.field1395 = this.field1395;
      var2.field1393 = this.field1393;
      var2.vertexLabels = this.vertexLabels;
      var2.faceLabelsAlpha = this.faceLabelsAlpha;
      var2.isSingleTile = this.isSingleTile;
      var2.method2397();
      return var2;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Z)Ldh;"
   )
   public Model method2362(boolean var1) {
      if(!var1 && Model_sharedSpotAnimationModelFaceAlphas.length < this.indicesCount) {
         Model_sharedSpotAnimationModelFaceAlphas = new byte[this.indicesCount + 100];
      }

      return this.method2357(var1, Model_sharedSpotAnimationModel, Model_sharedSpotAnimationModelFaceAlphas);
   }

   @ObfuscatedName("x")
   public void method2368() {
      for(int var1 = 0; var1 < this.verticesCount; ++var1) {
         int var2 = this.verticesZ[var1];
         this.verticesZ[var1] = this.verticesX[var1];
         this.verticesX[var1] = -var2;
      }

      this.method2397();
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "([[IIIIZI)Ldh;"
   )
   public Model method2354(int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      Model var7 = this.copy$contourGround(var1, var2, var3, var4, var5, var6);
      if(var7 != null && var7 != this) {
         RSModel var8 = (RSModel)var7;
         var8.setFaceTextureUCoordinates(this.rl$faceTextureUCoordinates);
         var8.setFaceTextureVCoordinates(this.rl$faceTextureVCoordinates);
      }

      return var7;
   }

   @ObfuscatedName("a")
   public void method2366() {
      for(int var1 = 0; var1 < this.verticesCount; ++var1) {
         int var2 = this.verticesX[var1];
         this.verticesX[var1] = this.verticesZ[var1];
         this.verticesZ[var1] = -var2;
      }

      this.method2397();
   }

   @ObfuscatedName("ac")
   final void method2377(int var1) {
      int var2 = Rasterizer3D.Rasterizer3D_clipMidX;
      int var3 = Rasterizer3D.Rasterizer3D_clipMidY;
      int var4 = 0;
      int var5 = this.indices1[var1];
      int var6 = this.indices2[var1];
      int var7 = this.indices3[var1];
      int var8 = field1419[var5];
      int var9 = field1419[var6];
      int var10 = field1419[var7];
      if(this.faceAlphas == null) {
         Rasterizer3D.Rasterizer3D_alpha = 0;
      } else {
         Rasterizer3D.Rasterizer3D_alpha = this.faceAlphas[var1] & 255;
      }

      int var11;
      int var12;
      int var13;
      int var14;
      if(var8 >= 50) {
         field1428[var4] = modelViewportXs[var5];
         field1416[var4] = modelViewportYs[var5];
         field1430[var4++] = this.faceColors1[var1];
      } else {
         var11 = field1417[var5];
         var12 = field1392[var5];
         var13 = this.faceColors1[var1];
         if(var10 >= 50) {
            var14 = field1440[var10 - var8] * (50 - var8);
            field1428[var4] = var2 + Rasterizer3D.Rasterizer3D_zoom * (var11 + ((field1417[var7] - var11) * var14 >> 16)) / 50;
            field1416[var4] = var3 + Rasterizer3D.Rasterizer3D_zoom * (var12 + ((field1392[var7] - var12) * var14 >> 16)) / 50;
            field1430[var4++] = var13 + ((this.faceColors3[var1] - var13) * var14 >> 16);
         }

         if(var9 >= 50) {
            var14 = field1440[var9 - var8] * (50 - var8);
            field1428[var4] = var2 + Rasterizer3D.Rasterizer3D_zoom * (var11 + ((field1417[var6] - var11) * var14 >> 16)) / 50;
            field1416[var4] = var3 + Rasterizer3D.Rasterizer3D_zoom * (var12 + ((field1392[var6] - var12) * var14 >> 16)) / 50;
            field1430[var4++] = var13 + ((this.faceColors2[var1] - var13) * var14 >> 16);
         }
      }

      if(var9 >= 50) {
         field1428[var4] = modelViewportXs[var6];
         field1416[var4] = modelViewportYs[var6];
         field1430[var4++] = this.faceColors2[var1];
      } else {
         var11 = field1417[var6];
         var12 = field1392[var6];
         var13 = this.faceColors2[var1];
         if(var8 >= 50) {
            var14 = field1440[var8 - var9] * (50 - var9);
            field1428[var4] = var2 + Rasterizer3D.Rasterizer3D_zoom * (var11 + ((field1417[var5] - var11) * var14 >> 16)) / 50;
            field1416[var4] = var3 + Rasterizer3D.Rasterizer3D_zoom * (var12 + ((field1392[var5] - var12) * var14 >> 16)) / 50;
            field1430[var4++] = var13 + ((this.faceColors1[var1] - var13) * var14 >> 16);
         }

         if(var10 >= 50) {
            var14 = field1440[var10 - var9] * (50 - var9);
            field1428[var4] = var2 + Rasterizer3D.Rasterizer3D_zoom * (var11 + ((field1417[var7] - var11) * var14 >> 16)) / 50;
            field1416[var4] = var3 + Rasterizer3D.Rasterizer3D_zoom * (var12 + ((field1392[var7] - var12) * var14 >> 16)) / 50;
            field1430[var4++] = var13 + ((this.faceColors3[var1] - var13) * var14 >> 16);
         }
      }

      if(var10 >= 50) {
         field1428[var4] = modelViewportXs[var7];
         field1416[var4] = modelViewportYs[var7];
         field1430[var4++] = this.faceColors3[var1];
      } else {
         var11 = field1417[var7];
         var12 = field1392[var7];
         var13 = this.faceColors3[var1];
         if(var9 >= 50) {
            var14 = field1440[var9 - var10] * (50 - var10);
            field1428[var4] = var2 + Rasterizer3D.Rasterizer3D_zoom * (var11 + ((field1417[var6] - var11) * var14 >> 16)) / 50;
            field1416[var4] = var3 + Rasterizer3D.Rasterizer3D_zoom * (var12 + ((field1392[var6] - var12) * var14 >> 16)) / 50;
            field1430[var4++] = var13 + ((this.faceColors2[var1] - var13) * var14 >> 16);
         }

         if(var8 >= 50) {
            var14 = field1440[var8 - var10] * (50 - var10);
            field1428[var4] = var2 + Rasterizer3D.Rasterizer3D_zoom * (var11 + ((field1417[var5] - var11) * var14 >> 16)) / 50;
            field1416[var4] = var3 + Rasterizer3D.Rasterizer3D_zoom * (var12 + ((field1392[var5] - var12) * var14 >> 16)) / 50;
            field1430[var4++] = var13 + ((this.faceColors1[var1] - var13) * var14 >> 16);
         }
      }

      var11 = field1428[0];
      var12 = field1428[1];
      var13 = field1428[2];
      var14 = field1416[0];
      int var15 = field1416[1];
      int var16 = field1416[2];
      Rasterizer3D.field1727 = false;
      int var17;
      int var18;
      int var19;
      int var20;
      if(var4 == 3) {
         if(var11 < 0 || var12 < 0 || var13 < 0 || var11 > Rasterizer3D.Rasterizer3D_clipWidth || var12 > Rasterizer3D.Rasterizer3D_clipWidth || var13 > Rasterizer3D.Rasterizer3D_clipWidth) {
            Rasterizer3D.field1727 = true;
         }

         if(this.faceTextures != null && this.faceTextures[var1] != -1) {
            if(this.field1379 != null && this.field1379[var1] != -1) {
               var20 = this.field1379[var1] & 255;
               var17 = this.field1394[var20];
               var18 = this.field1395[var20];
               var19 = this.field1393[var20];
            } else {
               var17 = var5;
               var18 = var6;
               var19 = var7;
            }

            if(this.faceColors3[var1] == -1) {
               Rasterizer3D.method2957(var14, var15, var16, var11, var12, var13, this.faceColors1[var1], this.faceColors1[var1], this.faceColors1[var1], field1417[var17], field1417[var18], field1417[var19], field1392[var17], field1392[var18], field1392[var19], field1419[var17], field1419[var18], field1419[var19], this.faceTextures[var1]);
            } else {
               Rasterizer3D.method2957(var14, var15, var16, var11, var12, var13, field1430[0], field1430[1], field1430[2], field1417[var17], field1417[var18], field1417[var19], field1392[var17], field1392[var18], field1392[var19], field1419[var17], field1419[var18], field1419[var19], this.faceTextures[var1]);
            }
         } else if(this.faceColors3[var1] == -1) {
            Rasterizer3D.method2955(var14, var15, var16, var11, var12, var13, field1439[this.faceColors1[var1]]);
         } else {
            Rasterizer3D.method2953(var14, var15, var16, var11, var12, var13, field1430[0], field1430[1], field1430[2]);
         }
      }

      if(var4 == 4) {
         if(var11 < 0 || var12 < 0 || var13 < 0 || var11 > Rasterizer3D.Rasterizer3D_clipWidth || var12 > Rasterizer3D.Rasterizer3D_clipWidth || var13 > Rasterizer3D.Rasterizer3D_clipWidth || field1428[3] < 0 || field1428[3] > Rasterizer3D.Rasterizer3D_clipWidth) {
            Rasterizer3D.field1727 = true;
         }

         if(this.faceTextures != null && this.faceTextures[var1] != -1) {
            if(this.field1379 != null && this.field1379[var1] != -1) {
               var20 = this.field1379[var1] & 255;
               var17 = this.field1394[var20];
               var18 = this.field1395[var20];
               var19 = this.field1393[var20];
            } else {
               var17 = var5;
               var18 = var6;
               var19 = var7;
            }

            short var21 = this.faceTextures[var1];
            if(this.faceColors3[var1] == -1) {
               Rasterizer3D.method2957(var14, var15, var16, var11, var12, var13, this.faceColors1[var1], this.faceColors1[var1], this.faceColors1[var1], field1417[var17], field1417[var18], field1417[var19], field1392[var17], field1392[var18], field1392[var19], field1419[var17], field1419[var18], field1419[var19], var21);
               Rasterizer3D.method2957(var14, var16, field1416[3], var11, var13, field1428[3], this.faceColors1[var1], this.faceColors1[var1], this.faceColors1[var1], field1417[var17], field1417[var18], field1417[var19], field1392[var17], field1392[var18], field1392[var19], field1419[var17], field1419[var18], field1419[var19], var21);
            } else {
               Rasterizer3D.method2957(var14, var15, var16, var11, var12, var13, field1430[0], field1430[1], field1430[2], field1417[var17], field1417[var18], field1417[var19], field1392[var17], field1392[var18], field1392[var19], field1419[var17], field1419[var18], field1419[var19], var21);
               Rasterizer3D.method2957(var14, var16, field1416[3], var11, var13, field1428[3], field1430[0], field1430[2], field1430[3], field1417[var17], field1417[var18], field1417[var19], field1392[var17], field1392[var18], field1392[var19], field1419[var17], field1419[var18], field1419[var19], var21);
            }
         } else if(this.faceColors3[var1] == -1) {
            var17 = field1439[this.faceColors1[var1]];
            Rasterizer3D.method2955(var14, var15, var16, var11, var12, var13, var17);
            Rasterizer3D.method2955(var14, var16, field1416[3], var11, var13, field1428[3], var17);
         } else {
            Rasterizer3D.method2953(var14, var15, var16, var11, var12, var13, field1430[0], field1430[1], field1430[2]);
            Rasterizer3D.method2953(var14, var16, field1416[3], var11, var13, field1428[3], field1430[0], field1430[2], field1430[3]);
         }
      }

   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(Lep;ILep;I[I)V"
   )
   public void method2419(Frames var1, int var2, Frames var3, int var4, int[] var5) {
      if(var2 != -1) {
         if(var5 != null && var4 != -1) {
            Animation var6 = var1.frames[var2];
            Animation var7 = var3.frames[var4];
            Skeleton var8 = var6.skeleton;
            Model_transformTempX = 0;
            Model_transformTempY = 0;
            Model_transformTempZ = 0;
            byte var9 = 0;
            int var13 = var9 + 1;
            int var10 = var5[var9];

            int var11;
            int var12;
            for(var11 = 0; var11 < var6.transformCount; ++var11) {
               for(var12 = var6.transformSkeletonLabels[var11]; var12 > var10; var10 = var5[var13++]) {
                  ;
               }

               if(var12 != var10 || var8.transformTypes[var12] == 0) {
                  this.method2365(var8.transformTypes[var12], var8.labels[var12], var6.transformXs[var11], var6.transformYs[var11], var6.transformZs[var11]);
               }
            }

            Model_transformTempX = 0;
            Model_transformTempY = 0;
            Model_transformTempZ = 0;
            var9 = 0;
            var13 = var9 + 1;
            var10 = var5[var9];

            for(var11 = 0; var11 < var7.transformCount; ++var11) {
               for(var12 = var7.transformSkeletonLabels[var11]; var12 > var10; var10 = var5[var13++]) {
                  ;
               }

               if(var12 == var10 || var8.transformTypes[var12] == 0) {
                  this.method2365(var8.transformTypes[var12], var8.labels[var12], var7.transformXs[var11], var7.transformYs[var11], var7.transformZs[var11]);
               }
            }

            this.method2397();
         } else {
            this.method2363(var1, var2);
         }
      }
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(Lep;I)V"
   )
   public void method2363(Frames var1, int var2) {
      if(this.vertexLabels != null) {
         if(var2 != -1) {
            Animation var3 = var1.frames[var2];
            Skeleton var4 = var3.skeleton;
            Model_transformTempX = 0;
            Model_transformTempY = 0;
            Model_transformTempZ = 0;

            for(int var5 = 0; var5 < var3.transformCount; ++var5) {
               int var6 = var3.transformSkeletonLabels[var5];
               this.method2365(var4.transformTypes[var6], var4.labels[var6], var3.transformXs[var5], var3.transformYs[var5], var3.transformZs[var5]);
            }

            this.method2397();
         }
      }
   }

   @ObfuscatedName("cb")
   void vmethod3071(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9) {
      field1421[0] = -1;
      if(this.boundsType != 1) {
         this.method2359();
      }

      this.method2356(var1);
      int var11 = var5 * var8 - var4 * var6 >> 16;
      int var12 = var2 * var7 + var3 * var11 >> 16;
      int var13 = var3 * this.xzRadius >> 16;
      int var14 = var12 + var13;
      if(var14 > 50 && var12 < 3500) {
         int var15 = var8 * var4 + var5 * var6 >> 16;
         int var16 = (var15 - this.xzRadius) * Rasterizer3D.Rasterizer3D_zoom;
         if(var16 / var14 < Rasterizer3D.Rasterizer3D_clipMidX2) {
            int var17 = (var15 + this.xzRadius) * Rasterizer3D.Rasterizer3D_zoom;
            if(var17 / var14 > Rasterizer3D.Rasterizer3D_clipNegativeMidX) {
               int var18 = var3 * var7 - var11 * var2 >> 16;
               int var19 = var2 * this.xzRadius >> 16;
               int var20 = (var18 + var19) * Rasterizer3D.Rasterizer3D_zoom;
               if(var20 / var14 > Rasterizer3D.Rasterizer3D_clipNegativeMidY) {
                  int var21 = (var3 * super.height >> 16) + var19;
                  int var22 = (var18 - var21) * Rasterizer3D.Rasterizer3D_zoom;
                  if(var22 / var14 < Rasterizer3D.Rasterizer3D_clipMidY2) {
                     int var23 = var13 + (var2 * super.height >> 16);
                     boolean var24 = false;
                     boolean var25 = false;
                     if(var12 - var23 <= 50) {
                        var25 = true;
                     }

                     boolean var26 = var25 || this.field1425 > 0;
                     int var27 = Clock.method3513();
                     int var28 = ViewportMouse.ViewportMouse_y;
                     boolean var30 = ViewportMouse.ViewportMouse_isInViewport;
                     boolean var32 = Language.method3835(var9);
                     boolean var33 = false;
                     int var35;
                     int var36;
                     int var37;
                     if(var32 && var30) {
                        boolean var34 = false;
                        if(field1434) {
                           var34 = SecureRandomCallable.method1133(this, var6, var7, var8);
                        } else {
                           var35 = var12 - var13;
                           if(var35 <= 50) {
                              var35 = 50;
                           }

                           if(var15 > 0) {
                              var16 /= var14;
                              var17 /= var35;
                           } else {
                              var17 /= var14;
                              var16 /= var35;
                           }

                           if(var18 > 0) {
                              var22 /= var14;
                              var20 /= var35;
                           } else {
                              var20 /= var14;
                              var22 /= var35;
                           }

                           var36 = var27 - Rasterizer3D.Rasterizer3D_clipMidX;
                           var37 = var28 - Rasterizer3D.Rasterizer3D_clipMidY;
                           if(var36 > var16 && var36 < var17 && var37 > var22 && var37 < var20) {
                              var34 = true;
                           }
                        }

                        if(var34) {
                           if(this.isSingleTile) {
                              ViewportMouse.ViewportMouse_entityTags[++ViewportMouse.ViewportMouse_entityCount - 1] = var9;
                           } else {
                              var33 = true;
                           }
                        }
                     }

                     int var47 = Rasterizer3D.Rasterizer3D_clipMidX;
                     var35 = Rasterizer3D.Rasterizer3D_clipMidY;
                     var36 = 0;
                     var37 = 0;
                     if(var1 != 0) {
                        var36 = Model_sine[var1];
                        var37 = Model_cosine[var1];
                     }

                     for(int var38 = 0; var38 < this.verticesCount; ++var38) {
                        int var39 = this.verticesX[var38];
                        int var40 = this.verticesY[var38];
                        int var41 = this.verticesZ[var38];
                        int var42;
                        if(var1 != 0) {
                           var42 = var41 * var36 + var39 * var37 >> 16;
                           var41 = var41 * var37 - var39 * var36 >> 16;
                           var39 = var42;
                        }

                        var39 += var6;
                        var40 += var7;
                        var41 += var8;
                        var42 = var41 * var4 + var5 * var39 >> 16;
                        var41 = var5 * var41 - var39 * var4 >> 16;
                        var39 = var42;
                        var42 = var3 * var40 - var41 * var2 >> 16;
                        var41 = var40 * var2 + var3 * var41 >> 16;
                        field1396[var38] = var41 - var12;
                        if(var41 >= 50) {
                           modelViewportXs[var38] = var39 * Rasterizer3D.Rasterizer3D_zoom / var41 + var47;
                           modelViewportYs[var38] = var42 * Rasterizer3D.Rasterizer3D_zoom / var41 + var35;
                        } else {
                           modelViewportXs[var38] = -5000;
                           var24 = true;
                        }

                        if(var26) {
                           field1417[var38] = var39;
                           field1392[var38] = var42;
                           field1419[var38] = var41;
                        }
                     }

                     try {
                        this.method2375(var24, var33, this.isSingleTile, var9);
                     } catch (Exception var46) {
                        ;
                     }

                  }
               }
            }
         }
      }
   }

   public int[] getTrianglesX() {
      return this.indices1;
   }

   public int[][] getVertexGroups() {
      return this.vertexLabels;
   }

   public void rl$buildSharedModel(boolean var1, net.runelite.api.Model var2, byte[] var3) {
      RSModel var4 = (RSModel)var2;
      var4.setFaceTextureUCoordinates(this.rl$faceTextureUCoordinates);
      var4.setFaceTextureVCoordinates(this.rl$faceTextureVCoordinates);
   }

   @ObfuscatedSignature(
      signature = "([[IIIIZI)Ldh;"
   )
   public Model copy$contourGround(int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      this.method2359();
      int var7 = var2 - this.xzRadius;
      int var8 = var2 + this.xzRadius;
      int var9 = var4 - this.xzRadius;
      int var10 = var4 + this.xzRadius;
      if(var7 >= 0 && var8 + 128 >> 7 < var1.length && var9 >= 0 && var10 + 128 >> 7 < var1[0].length) {
         var7 >>= 7;
         var8 = var8 + 127 >> 7;
         var9 >>= 7;
         var10 = var10 + 127 >> 7;
         if(var3 == var1[var7][var9] && var3 == var1[var8][var9] && var3 == var1[var7][var10] && var3 == var1[var8][var10]) {
            return this;
         } else {
            Model var11;
            if(var5) {
               var11 = new Model();
               var11.verticesCount = this.verticesCount;
               var11.indicesCount = this.indicesCount;
               var11.field1425 = this.field1425;
               var11.verticesX = this.verticesX;
               var11.verticesZ = this.verticesZ;
               var11.indices1 = this.indices1;
               var11.indices2 = this.indices2;
               var11.indices3 = this.indices3;
               var11.faceColors1 = this.faceColors1;
               var11.faceColors2 = this.faceColors2;
               var11.faceColors3 = this.faceColors3;
               var11.faceRenderPriorities = this.faceRenderPriorities;
               var11.faceAlphas = this.faceAlphas;
               var11.field1379 = this.field1379;
               var11.faceTextures = this.faceTextures;
               var11.field1374 = this.field1374;
               var11.field1394 = this.field1394;
               var11.field1395 = this.field1395;
               var11.field1393 = this.field1393;
               var11.vertexLabels = this.vertexLabels;
               var11.faceLabelsAlpha = this.faceLabelsAlpha;
               var11.isSingleTile = this.isSingleTile;
               var11.verticesY = new int[var11.verticesCount];
            } else {
               var11 = this;
            }

            int var12;
            int var13;
            int var14;
            int var15;
            int var16;
            int var17;
            int var18;
            int var19;
            int var20;
            int var21;
            if(var6 == 0) {
               for(var12 = 0; var12 < var11.verticesCount; ++var12) {
                  var13 = var2 + this.verticesX[var12];
                  var14 = var4 + this.verticesZ[var12];
                  var15 = var13 & 127;
                  var16 = var14 & 127;
                  var17 = var13 >> 7;
                  var18 = var14 >> 7;
                  var19 = var1[var17][var18] * (128 - var15) + var1[var17 + 1][var18] * var15 >> 7;
                  var20 = var1[var17][var18 + 1] * (128 - var15) + var15 * var1[var17 + 1][var18 + 1] >> 7;
                  var21 = var19 * (128 - var16) + var20 * var16 >> 7;
                  var11.verticesY[var12] = var21 + this.verticesY[var12] - var3;
               }
            } else {
               for(var12 = 0; var12 < var11.verticesCount; ++var12) {
                  var13 = (-this.verticesY[var12] << 16) / super.height;
                  if(var13 < var6) {
                     var14 = var2 + this.verticesX[var12];
                     var15 = var4 + this.verticesZ[var12];
                     var16 = var14 & 127;
                     var17 = var15 & 127;
                     var18 = var14 >> 7;
                     var19 = var15 >> 7;
                     var20 = var1[var18][var19] * (128 - var16) + var1[var18 + 1][var19] * var16 >> 7;
                     var21 = var1[var18][var19 + 1] * (128 - var16) + var16 * var1[var18 + 1][var19 + 1] >> 7;
                     int var22 = var20 * (128 - var17) + var21 * var17 >> 7;
                     var11.verticesY[var12] = (var6 - var13) * (var22 - var3) / var6 + this.verticesY[var12];
                  }
               }
            }

            var11.method2397();
            return var11;
         }
      } else {
         return this;
      }
   }

   public int[] getVerticesX() {
      return this.verticesX;
   }

   public int[] getTrianglesY() {
      return this.indices2;
   }

   public void setFaceTextureUCoordinates(float[][] var1) {
      this.rl$faceTextureUCoordinates = var1;
   }

   public List getVertices() {
      int[] var1 = this.getVerticesX();
      int[] var2 = this.getVerticesY();
      int[] var3 = this.getVerticesZ();
      ArrayList var4 = new ArrayList(this.getVerticesCount());

      for(int var5 = 0; var5 < this.getVerticesCount(); ++var5) {
         Vertex var6 = new Vertex(var1[var5], var2[var5], var3[var5]);
         var4.add(var6);
      }

      return var4;
   }

   public int[] getVerticesY() {
      return this.verticesY;
   }

   public int[] getTrianglesZ() {
      return this.indices3;
   }

   public void setFaceTextureVCoordinates(float[][] var1) {
      this.rl$faceTextureVCoordinates = var1;
   }

   public int[] getVerticesZ() {
      return this.verticesZ;
   }

   public int getVerticesCount() {
      return this.verticesCount;
   }

   public int getTrianglesCount() {
      return this.indicesCount;
   }

   public void interpolateFrames(RSSkeleton var1, RSAnimation var2, RSAnimation var3, int var4, int var5) {
      int var6;
      int var7;
      if(var3 != null && var4 != 0) {
         var6 = 0;
         var7 = 0;

         for(int var8 = 0; var8 < var1.getCount(); ++var8) {
            boolean var9 = false;
            if(var6 < var2.getTransformCount() && var2.getTransformTypes()[var6] == var8) {
               var9 = true;
            }

            boolean var10 = false;
            if(var7 < var3.getTransformCount() && var3.getTransformTypes()[var7] == var8) {
               var10 = true;
            }

            if(var9 || var10) {
               short var11 = 0;
               int var12 = var1.getTypes()[var8];
               if(var12 == 3 || var12 == 10) {
                  var11 = 128;
               }

               int var13 = var11;
               int var14 = var11;
               int var15 = var11;
               if(var9) {
                  var13 = var2.getTranslatorX()[var6];
                  var14 = var2.getTranslatorY()[var6];
                  var15 = var2.getTranslatorZ()[var6];
                  ++var6;
               }

               int var16 = var11;
               int var17 = var11;
               int var18 = var11;
               if(var10) {
                  var16 = var3.getTranslatorX()[var7];
                  var17 = var3.getTranslatorY()[var7];
                  var18 = var3.getTranslatorZ()[var7];
                  ++var7;
               }

               int var19;
               int var20;
               int var21;
               if(var12 == 2) {
                  int var22 = var16 - var13 & 255;
                  int var23 = var17 - var14 & 255;
                  int var24 = var18 - var15 & 255;
                  if(var22 >= 128) {
                     var22 -= 256;
                  }

                  if(var23 >= 128) {
                     var23 -= 256;
                  }

                  if(var24 >= 128) {
                     var24 -= 256;
                  }

                  var19 = var13 + var22 * var4 / var5 & 255;
                  var20 = var14 + var23 * var4 / var5 & 255;
                  var21 = var15 + var24 * var4 / var5 & 255;
               } else if(var12 == 5) {
                  var19 = var13;
                  var20 = 0;
                  var21 = 0;
               } else {
                  var19 = var13 + (var16 - var13) * var4 / var5;
                  var20 = var14 + (var17 - var14) * var4 / var5;
                  var21 = var15 + (var18 - var15) * var4 / var5;
               }

               this.animate(var12, var1.getList()[var8], var19, var20, var21);
            }
         }
      } else {
         for(var6 = 0; var6 < var2.getTransformCount(); ++var6) {
            var7 = var2.getTransformTypes()[var6];
            this.animate(var1.getTypes()[var7], var1.getList()[var7], var2.getTranslatorX()[var6], var2.getTranslatorY()[var6], var2.getTranslatorZ()[var6]);
         }
      }

   }

   public void resetBounds() {
      this.method2397();
   }

   public void animate(int var1, int[] var2, int var3, int var4, int var5) {
      this.method2365(var1, var2, var3, var4, var5);
   }

   public void rl$init(RSModel[] var1, int var2) {
      int var3 = 0;

      for(int var4 = 0; var4 < var2; ++var4) {
         RSModel var5 = var1[var4];
         if(var5 != null) {
            var3 += var5.getTrianglesCount();
         }
      }

      float[][] var12 = new float[var3][];
      float[][] var13 = new float[var3][];
      int var6 = 0;

      for(int var7 = 0; var7 < var2; ++var7) {
         RSModel var8 = var1[var7];
         if(var8 != null) {
            float[][] var9 = var8.getFaceTextureUCoordinates();
            float[][] var10 = var8.getFaceTextureVCoordinates();

            for(int var11 = 0; var11 < var8.getTrianglesCount(); ++var11) {
               if(var9 != null && var10 != null) {
                  var12[var6] = var9[var11];
                  var13[var6] = var10[var11];
               }

               ++var6;
            }
         }
      }

      this.setFaceTextureUCoordinates(var12);
      this.setFaceTextureVCoordinates(var13);
   }

   public List getTriangles() {
      int[] var1 = this.getTrianglesX();
      int[] var2 = this.getTrianglesY();
      int[] var3 = this.getTrianglesZ();
      List var4 = this.getVertices();
      ArrayList var5 = new ArrayList(this.getTrianglesCount());

      for(int var6 = 0; var6 < this.getTrianglesCount(); ++var6) {
         int var7 = var1[var6];
         int var8 = var2[var6];
         int var9 = var3[var6];
         Triangle var10 = new Triangle((Vertex)var4.get(var7), (Vertex)var4.get(var8), (Vertex)var4.get(var9));
         var5.add(var10);
      }

      return var5;
   }

   public void interpolateFrames(RSFrames var1, int var2, RSFrames var3, int var4, int var5, int var6) {
      if(this.getVertexGroups() != null && var2 != -1) {
         RSAnimation var7 = var1.getFrames()[var2];
         RSSkeleton var8 = var7.getSkin();
         RSAnimation var9 = null;
         if(var3 != null) {
            var9 = var3.getFrames()[var4];
            if(var9.getSkin() != var8) {
               var9 = null;
            }
         }

         ViewportMouse.client.setAnimOffsetX(0);
         ViewportMouse.client.setAnimOffsetY(0);
         ViewportMouse.client.setAnimOffsetZ(0);
         this.interpolateFrames(var8, var7, var9, var5, var6);
         this.resetBounds();
      }

   }

   public Polygon getConvexHull(int var1, int var2, int var3, int var4) {
      if(!$assertionsDisabled && !ViewportMouse.client.isClientThread()) {
         throw new AssertionError();
      } else {
         List var5 = this.getVertices();

         for(int var6 = 0; var6 < var5.size(); ++var6) {
            Vertex var7 = (Vertex)var5.get(var6);
            var5.set(var6, var7.rotate(var3));
         }

         ArrayList var10 = new ArrayList();
         Iterator var12 = var5.iterator();

         Point var9;
         while(var12.hasNext()) {
            Vertex var8 = (Vertex)var12.next();
            var9 = Perspective.localToCanvas(ViewportMouse.client, var1 - var8.getX(), var2 - var8.getZ(), var4 + var8.getY());
            if(var9 != null) {
               var10.add(var9);
            }
         }

         List var11 = Jarvis.convexHull(var10);
         if(var11 == null) {
            return null;
         } else {
            Polygon var13 = new Polygon();
            Iterator var14 = var11.iterator();

            while(var14.hasNext()) {
               var9 = (Point)var14.next();
               var13.addPoint(var9.getX(), var9.getY());
            }

            return var13;
         }
      }
   }

   public int getSceneId() {
      return this.rl$sceneId;
   }

   public void setSceneId(int var1) {
      this.rl$sceneId = var1;
   }

   public int getBufferOffset() {
      return this.rl$bufferOffset;
   }

   public void setBufferOffset(int var1) {
      this.rl$bufferOffset = var1;
   }

   public int getUvBufferOffset() {
      return this.rl$uvBufferOffset;
   }

   public void setUvBufferOffset(int var1) {
      this.rl$uvBufferOffset = var1;
   }

   public float[][] getFaceTextureUCoordinates() {
      return this.rl$faceTextureUCoordinates;
   }

   public float[][] getFaceTextureVCoordinates() {
      return this.rl$faceTextureVCoordinates;
   }

   public int[] getFaceColors1() {
      return this.faceColors1;
   }

   public int[] getFaceColors2() {
      return this.faceColors2;
   }

   public int[] getFaceColors3() {
      return this.faceColors3;
   }

   public byte[] getFaceRenderPriorities() {
      return this.faceRenderPriorities;
   }

   public byte[] getTriangleTransparencies() {
      return this.faceAlphas;
   }

   public short[] getFaceTextures() {
      return this.faceTextures;
   }

   public boolean isClickable() {
      return this.isSingleTile;
   }

   public int getXYZMag() {
      return this.xzRadius;
   }

   public int getRadius() {
      return this.radius;
   }

   public int getCenterX() {
      return this.xMid;
   }

   public int getCenterY() {
      return this.yMid;
   }

   public int getCenterZ() {
      return this.zMid;
   }

   public int getExtremeX() {
      return this.xMidOffset;
   }

   public int getExtremeY() {
      return this.yMidOffset;
   }

   public int getExtremeZ() {
      return this.zMidOffset;
   }

   public RSModel toSharedModel(boolean var1) {
      return this.method2355(var1);
   }

   public RSModel toSharedSpotAnimModel(boolean var1) {
      return this.method2362(var1);
   }

   public void calculateExtreme(int var1) {
      this.method2356(var1);
   }

   public void calculateBoundsCylinder() {
      this.method2359();
   }

   public void rotateY90Ccw() {
      this.method2366();
   }

   public void rotateY180Ccw() {
      this.method2367();
   }

   public void rotateY270Ccw() {
      this.method2368();
   }

   @ObfuscatedName("d")
   public final void method2373(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      field1421[0] = -1;
      if(this.boundsType != 2 && this.boundsType != 1) {
         this.method2360();
      }

      int var9 = Rasterizer3D.Rasterizer3D_clipMidX;
      int var10 = Rasterizer3D.Rasterizer3D_clipMidY;
      int var11 = Model_sine[var1];
      int var12 = Model_cosine[var1];
      int var13 = Model_sine[var2];
      int var14 = Model_cosine[var2];
      int var15 = Model_sine[var3];
      int var16 = Model_cosine[var3];
      int var17 = Model_sine[var4];
      int var18 = Model_cosine[var4];
      int var19 = var17 * var6 + var18 * var7 >> 16;

      for(int var20 = 0; var20 < this.verticesCount; ++var20) {
         int var21 = this.verticesX[var20];
         int var22 = this.verticesY[var20];
         int var23 = this.verticesZ[var20];
         int var24;
         if(var3 != 0) {
            var24 = var22 * var15 + var21 * var16 >> 16;
            var22 = var22 * var16 - var21 * var15 >> 16;
            var21 = var24;
         }

         if(var1 != 0) {
            var24 = var22 * var12 - var23 * var11 >> 16;
            var23 = var22 * var11 + var23 * var12 >> 16;
            var22 = var24;
         }

         if(var2 != 0) {
            var24 = var23 * var13 + var21 * var14 >> 16;
            var23 = var23 * var14 - var21 * var13 >> 16;
            var21 = var24;
         }

         var21 += var5;
         var22 += var6;
         var23 += var7;
         var24 = var22 * var18 - var23 * var17 >> 16;
         var23 = var22 * var17 + var23 * var18 >> 16;
         field1396[var20] = var23 - var19;
         modelViewportXs[var20] = var9 + var21 * Rasterizer3D.Rasterizer3D_zoom / var8;
         modelViewportYs[var20] = var10 + var24 * Rasterizer3D.Rasterizer3D_zoom / var8;
         if(this.field1425 > 0) {
            field1417[var20] = var21;
            field1392[var20] = var24;
            field1419[var20] = var23;
         }
      }

      try {
         this.method2375(false, false, false, 0L);
      } catch (Exception var26) {
         ;
      }

   }

   @ObfuscatedName("e")
   public void method2367() {
      for(int var1 = 0; var1 < this.verticesCount; ++var1) {
         this.verticesX[var1] = -this.verticesX[var1];
         this.verticesZ[var1] = -this.verticesZ[var1];
      }

      this.method2397();
   }

   @ObfuscatedName("f")
   public void method2402(int var1, int var2, int var3) {
      for(int var4 = 0; var4 < this.verticesCount; ++var4) {
         this.verticesX[var4] = this.verticesX[var4] * var1 / 128;
         this.verticesY[var4] = var2 * this.verticesY[var4] / 128;
         this.verticesZ[var4] = var3 * this.verticesZ[var4] / 128;
      }

      this.method2397();
   }

   @ObfuscatedName("h")
   public void method2369(int var1) {
      int var2 = Model_sine[var1];
      int var3 = Model_cosine[var1];

      for(int var4 = 0; var4 < this.verticesCount; ++var4) {
         int var5 = var3 * this.verticesY[var4] - var2 * this.verticesZ[var4] >> 16;
         this.verticesZ[var4] = var2 * this.verticesY[var4] + var3 * this.verticesZ[var4] >> 16;
         this.verticesY[var4] = var5;
      }

      this.method2397();
   }

   @ObfuscatedName("i")
   void method2397() {
      this.boundsType = 0;
      this.xMidOffset = -1;
   }

   @ObfuscatedName("j")
   public final void method2372(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      field1421[0] = -1;
      if(this.boundsType != 2 && this.boundsType != 1) {
         this.method2360();
      }

      int var8 = Rasterizer3D.Rasterizer3D_clipMidX;
      int var9 = Rasterizer3D.Rasterizer3D_clipMidY;
      int var10 = Model_sine[var1];
      int var11 = Model_cosine[var1];
      int var12 = Model_sine[var2];
      int var13 = Model_cosine[var2];
      int var14 = Model_sine[var3];
      int var15 = Model_cosine[var3];
      int var16 = Model_sine[var4];
      int var17 = Model_cosine[var4];
      int var18 = var16 * var6 + var17 * var7 >> 16;

      for(int var19 = 0; var19 < this.verticesCount; ++var19) {
         int var20 = this.verticesX[var19];
         int var21 = this.verticesY[var19];
         int var22 = this.verticesZ[var19];
         int var23;
         if(var3 != 0) {
            var23 = var21 * var14 + var20 * var15 >> 16;
            var21 = var21 * var15 - var20 * var14 >> 16;
            var20 = var23;
         }

         if(var1 != 0) {
            var23 = var21 * var11 - var22 * var10 >> 16;
            var22 = var21 * var10 + var22 * var11 >> 16;
            var21 = var23;
         }

         if(var2 != 0) {
            var23 = var22 * var12 + var20 * var13 >> 16;
            var22 = var22 * var13 - var20 * var12 >> 16;
            var20 = var23;
         }

         var20 += var5;
         var21 += var6;
         var22 += var7;
         var23 = var21 * var17 - var22 * var16 >> 16;
         var22 = var21 * var16 + var22 * var17 >> 16;
         field1396[var19] = var22 - var18;
         modelViewportXs[var19] = var20 * Rasterizer3D.Rasterizer3D_zoom / var22 + var8;
         modelViewportYs[var19] = var23 * Rasterizer3D.Rasterizer3D_zoom / var22 + var9;
         if(this.field1425 > 0) {
            field1417[var19] = var20;
            field1392[var19] = var23;
            field1419[var19] = var22;
         }
      }

      try {
         this.method2375(false, false, false, 0L);
      } catch (Exception var25) {
         ;
      }

   }

   @ObfuscatedName("k")
   final void method2376(int var1) {
      if(field1413[var1]) {
         this.method2377(var1);
      } else {
         int var2 = this.indices1[var1];
         int var3 = this.indices2[var1];
         int var4 = this.indices3[var1];
         Rasterizer3D.field1727 = field1412[var1];
         if(this.faceAlphas == null) {
            Rasterizer3D.Rasterizer3D_alpha = 0;
         } else {
            Rasterizer3D.Rasterizer3D_alpha = this.faceAlphas[var1] & 255;
         }

         if(this.faceTextures != null && this.faceTextures[var1] != -1) {
            int var5;
            int var6;
            int var7;
            if(this.field1379 != null && this.field1379[var1] != -1) {
               int var8 = this.field1379[var1] & 255;
               var5 = this.field1394[var8];
               var6 = this.field1395[var8];
               var7 = this.field1393[var8];
            } else {
               var5 = var2;
               var6 = var3;
               var7 = var4;
            }

            if(this.faceColors3[var1] == -1) {
               Rasterizer3D.method2957(modelViewportYs[var2], modelViewportYs[var3], modelViewportYs[var4], modelViewportXs[var2], modelViewportXs[var3], modelViewportXs[var4], this.faceColors1[var1], this.faceColors1[var1], this.faceColors1[var1], field1417[var5], field1417[var6], field1417[var7], field1392[var5], field1392[var6], field1392[var7], field1419[var5], field1419[var6], field1419[var7], this.faceTextures[var1]);
            } else {
               Rasterizer3D.method2957(modelViewportYs[var2], modelViewportYs[var3], modelViewportYs[var4], modelViewportXs[var2], modelViewportXs[var3], modelViewportXs[var4], this.faceColors1[var1], this.faceColors2[var1], this.faceColors3[var1], field1417[var5], field1417[var6], field1417[var7], field1392[var5], field1392[var6], field1392[var7], field1419[var5], field1419[var6], field1419[var7], this.faceTextures[var1]);
            }
         } else if(this.faceColors3[var1] == -1) {
            Rasterizer3D.method2955(modelViewportYs[var2], modelViewportYs[var3], modelViewportYs[var4], modelViewportXs[var2], modelViewportXs[var3], modelViewportXs[var4], field1439[this.faceColors1[var1]]);
         } else {
            Rasterizer3D.method2953(modelViewportYs[var2], modelViewportYs[var3], modelViewportYs[var4], modelViewportXs[var2], modelViewportXs[var3], modelViewportXs[var4], this.faceColors1[var1], this.faceColors2[var1], this.faceColors3[var1]);
         }

      }
   }

   @ObfuscatedName("l")
   final void method2375(boolean var1, boolean var2, boolean var3, long var4) {
      if(this.diameter < 1600) {
         int var6;
         for(var6 = 0; var6 < this.diameter; ++var6) {
            field1421[var6] = 0;
         }

         var6 = var3?20:5;

         int var7;
         int var8;
         int var10;
         int var11;
         int var12;
         int var13;
         int var15;
         int var16;
         int var18;
         int var27;
         for(var7 = 0; var7 < this.indicesCount; ++var7) {
            if(this.faceColors3[var7] != -2) {
               var8 = this.indices1[var7];
               var27 = this.indices2[var7];
               var10 = this.indices3[var7];
               var11 = modelViewportXs[var8];
               var12 = modelViewportXs[var27];
               var13 = modelViewportXs[var10];
               int var17;
               int var34;
               if(var1 && (var11 == -5000 || var12 == -5000 || var13 == -5000)) {
                  var34 = field1417[var8];
                  var15 = field1417[var27];
                  var16 = field1417[var10];
                  var17 = field1392[var8];
                  var18 = field1392[var27];
                  int var19 = field1392[var10];
                  int var20 = field1419[var8];
                  int var21 = field1419[var27];
                  int var22 = field1419[var10];
                  var34 -= var15;
                  var16 -= var15;
                  var17 -= var18;
                  var19 -= var18;
                  var20 -= var21;
                  var22 -= var21;
                  int var23 = var17 * var22 - var20 * var19;
                  int var24 = var20 * var16 - var34 * var22;
                  int var25 = var34 * var19 - var17 * var16;
                  if(var15 * var23 + var18 * var24 + var21 * var25 > 0) {
                     field1413[var7] = true;
                     int var26 = (field1396[var8] + field1396[var27] + field1396[var10]) / 3 + this.radius;
                     field1422[var26][field1421[var26]++] = var7;
                  }
               } else {
                  if(var2) {
                     var15 = modelViewportYs[var8];
                     var16 = modelViewportYs[var27];
                     var17 = modelViewportYs[var10];
                     var18 = var6 + ViewportMouse.ViewportMouse_y;
                     boolean var14;
                     if(var18 < var15 && var18 < var16 && var18 < var17) {
                        var14 = false;
                     } else {
                        var18 = ViewportMouse.ViewportMouse_y - var6;
                        if(var18 > var15 && var18 > var16 && var18 > var17) {
                           var14 = false;
                        } else {
                           var18 = var6 + ViewportMouse.ViewportMouse_x;
                           if(var18 < var11 && var18 < var12 && var18 < var13) {
                              var14 = false;
                           } else {
                              var18 = ViewportMouse.ViewportMouse_x - var6;
                              if(var18 > var11 && var18 > var12 && var18 > var13) {
                                 var14 = false;
                              } else {
                                 var14 = true;
                              }
                           }
                        }
                     }

                     if(var14) {
                        ViewportMouse.ViewportMouse_entityTags[++ViewportMouse.ViewportMouse_entityCount - 1] = var4;
                        var2 = false;
                     }
                  }

                  if((var11 - var12) * (modelViewportYs[var10] - modelViewportYs[var27]) - (var13 - var12) * (modelViewportYs[var8] - modelViewportYs[var27]) > 0) {
                     field1413[var7] = false;
                     if(var11 >= 0 && var12 >= 0 && var13 >= 0 && var11 <= Rasterizer3D.Rasterizer3D_clipWidth && var12 <= Rasterizer3D.Rasterizer3D_clipWidth && var13 <= Rasterizer3D.Rasterizer3D_clipWidth) {
                        field1412[var7] = false;
                     } else {
                        field1412[var7] = true;
                     }

                     var34 = (field1396[var8] + field1396[var27] + field1396[var10]) / 3 + this.radius;
                     field1422[var34][field1421[var34]++] = var7;
                  }
               }
            }
         }

         int[] var9;
         if(this.faceRenderPriorities == null) {
            for(var7 = this.diameter - 1; var7 >= 0; --var7) {
               var8 = field1421[var7];
               if(var8 > 0) {
                  var9 = field1422[var7];

                  for(var10 = 0; var10 < var8; ++var10) {
                     this.method2376(var9[var10]);
                  }
               }
            }

         } else {
            for(var7 = 0; var7 < 12; ++var7) {
               field1423[var7] = 0;
               field1427[var7] = 0;
            }

            for(var7 = this.diameter - 1; var7 >= 0; --var7) {
               var8 = field1421[var7];
               if(var8 > 0) {
                  var9 = field1422[var7];

                  for(var10 = 0; var10 < var8; ++var10) {
                     var11 = var9[var10];
                     byte var33 = this.faceRenderPriorities[var11];
                     var13 = field1423[var33]++;
                     field1385[var33][var13] = var11;
                     if(var33 < 10) {
                        field1427[var33] += var7;
                     } else if(var33 == 10) {
                        field1437[var13] = var7;
                     } else {
                        field1398[var13] = var7;
                     }
                  }
               }
            }

            var7 = 0;
            if(field1423[1] > 0 || field1423[2] > 0) {
               var7 = (field1427[1] + field1427[2]) / (field1423[1] + field1423[2]);
            }

            var8 = 0;
            if(field1423[3] > 0 || field1423[4] > 0) {
               var8 = (field1427[3] + field1427[4]) / (field1423[3] + field1423[4]);
            }

            var27 = 0;
            if(field1423[6] > 0 || field1423[8] > 0) {
               var27 = (field1427[8] + field1427[6]) / (field1423[8] + field1423[6]);
            }

            var11 = 0;
            var12 = field1423[10];
            int[] var30 = field1385[10];
            int[] var31 = field1437;
            if(var11 == var12) {
               var11 = 0;
               var12 = field1423[11];
               var30 = field1385[11];
               var31 = field1398;
            }

            if(var11 < var12) {
               var10 = var31[var11];
            } else {
               var10 = -1000;
            }

            for(var15 = 0; var15 < 10; ++var15) {
               while(var15 == 0 && var10 > var7) {
                  this.method2376(var30[var11++]);
                  if(var11 == var12 && var30 != field1385[11]) {
                     var11 = 0;
                     var12 = field1423[11];
                     var30 = field1385[11];
                     var31 = field1398;
                  }

                  if(var11 < var12) {
                     var10 = var31[var11];
                  } else {
                     var10 = -1000;
                  }
               }

               while(var15 == 3 && var10 > var8) {
                  this.method2376(var30[var11++]);
                  if(var11 == var12 && var30 != field1385[11]) {
                     var11 = 0;
                     var12 = field1423[11];
                     var30 = field1385[11];
                     var31 = field1398;
                  }

                  if(var11 < var12) {
                     var10 = var31[var11];
                  } else {
                     var10 = -1000;
                  }
               }

               while(var15 == 5 && var10 > var27) {
                  this.method2376(var30[var11++]);
                  if(var11 == var12 && var30 != field1385[11]) {
                     var11 = 0;
                     var12 = field1423[11];
                     var30 = field1385[11];
                     var31 = field1398;
                  }

                  if(var11 < var12) {
                     var10 = var31[var11];
                  } else {
                     var10 = -1000;
                  }
               }

               var16 = field1423[var15];
               int[] var32 = field1385[var15];

               for(var18 = 0; var18 < var16; ++var18) {
                  this.method2376(var32[var18]);
               }
            }

            while(var10 != -1000) {
               this.method2376(var30[var11++]);
               if(var11 == var12 && var30 != field1385[11]) {
                  var11 = 0;
                  var30 = field1385[11];
                  var12 = field1423[11];
                  var31 = field1398;
               }

               if(var11 < var12) {
                  var10 = var31[var11];
               } else {
                  var10 = -1000;
               }
            }

         }
      }
   }

   @ObfuscatedName("m")
   public int method2361() {
      this.method2359();
      return this.xzRadius;
   }

   private static void rl$$clinit() {
      $assertionsDisabled = !Client.class.desiredAssertionStatus();
   }
}
