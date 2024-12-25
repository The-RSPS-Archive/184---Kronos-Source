package net.runelite.standalone;

import java.util.Iterator;
import net.runelite.api.Perspective;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.api.util.Text;
import net.runelite.client.callback.Hooks;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSBoundaryObject;
import net.runelite.rs.api.RSEntity;
import net.runelite.rs.api.RSFloorDecoration;
import net.runelite.rs.api.RSGameObject;
import net.runelite.rs.api.RSNPC;
import net.runelite.rs.api.RSNodeDeque;
import net.runelite.rs.api.RSPlayer;
import net.runelite.rs.api.RSProjectile;
import net.runelite.rs.api.RSScene;
import net.runelite.rs.api.RSTile;
import net.runelite.rs.api.RSTileItem;
import net.runelite.rs.api.RSTileItemPile;
import net.runelite.rs.api.RSTileModel;
import net.runelite.rs.api.RSWallDecoration;

@ObfuscatedName("ex")
public class Scene implements RSScene {
   @ObfuscatedName("s")
   static int Scene_cameraYTileMax;
   @ObfuscatedName("t")
   static int Scene_drawnCount;
   @ObfuscatedName("w")
   static int Scene_plane;
   @ObfuscatedName("x")
   static int Scene_cameraXTileMax;
   @ObfuscatedName("z")
   public static boolean Scene_isLowDetail;
   @ObfuscatedName("aa")
   static int Scene_cameraYawCosine;
   @ObfuscatedName("ab")
   static int Scene_selectedPlane;
   @ObfuscatedName("ac")
   static int Scene_cameraPitchSine;
   @ObfuscatedName("ad")
   static boolean viewportWalking;
   @ObfuscatedName("ae")
   static int Scene_currentOccludersCount;
   @ObfuscatedName("af")
   static final int[] field1869;
   @ObfuscatedName("ag")
   static final int[] field1887;
   @ObfuscatedName("ah")
   @ObfuscatedSignature(
      signature = "Ljv;"
   )
   static NodeDeque Scene_tilesDeque;
   @ObfuscatedName("aj")
   static final int[] field1870;
   @ObfuscatedName("ak")
   static int Scene_planesCount;
   @ObfuscatedName("al")
   public static int Scene_selectedY;
   @ObfuscatedName("am")
   @ObfuscatedSignature(
      signature = "[[Les;"
   )
   static Occluder[][] Scene_planeOccluders;
   @ObfuscatedName("ao")
   public static int Scene_selectedX;
   @ObfuscatedName("ap")
   @ObfuscatedSignature(
      signature = "[Lej;"
   )
   static GameObject[] gameObjects;
   @ObfuscatedName("aq")
   static int[] Scene_planeOccluderCounts;
   @ObfuscatedName("ar")
   static boolean checkClick;
   @ObfuscatedName("as")
   static int Scene_selectedScreenY;
   @ObfuscatedName("at")
   static final int[] field1868;
   @ObfuscatedName("au")
   static final int[] field1872;
   @ObfuscatedName("av")
   @ObfuscatedSignature(
      signature = "[Les;"
   )
   static Occluder[] Scene_currentOccluders;
   @ObfuscatedName("aw")
   static int Scene_cameraYawSine;
   @ObfuscatedName("ax")
   static int Scene_selectedScreenX;
   @ObfuscatedName("ay")
   static final int[] field1823;
   @ObfuscatedName("az")
   static int Scene_cameraPitchCosine;
   @ObfuscatedName("ba")
   static int Scene_viewportYCenter;
   @ObfuscatedName("bc")
   static boolean[][] visibleTiles;
   @ObfuscatedName("be")
   static int Scene_viewportYMin;
   @ObfuscatedName("bi")
   static int Scene_viewportYMax;
   @ObfuscatedName("bk")
   static int Scene_viewportXCenter;
   @ObfuscatedName("bn")
   static int Scene_viewportXMin;
   @ObfuscatedName("bu")
   static int Scene_viewportXMax;
   @ObfuscatedName("bx")
   static final int[] field1875;
   @ObfuscatedName("bz")
   static boolean[][][][] visibilityMap;
   public static int rl$drawDistance;
   public static int[] tmpX;
   public static int[] tmpY;
   @ObfuscatedName("d")
   static int Scene_cameraX;
   @ObfuscatedName("e")
   static int tileUpdateCount;
   @ObfuscatedName("f")
   static int Scene_cameraXTile;
   @ObfuscatedName("g")
   static int Scene_cameraXTileMin;
   @ObfuscatedName("h")
   static int Scene_cameraYTileMin;
   @ObfuscatedName("j")
   static int Scene_cameraYTile;
   @ObfuscatedName("k")
   static int Scene_cameraZ;
   @ObfuscatedName("l")
   static int Scene_cameraY;
   @ObfuscatedName("n")
   int planes;
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "[[[Ldl;"
   )
   Tile[][][] tiles;
   @ObfuscatedName("q")
   int minPlane;
   @ObfuscatedName("r")
   int[][][] tileHeights;
   @ObfuscatedName("u")
   int ySize;
   @ObfuscatedName("v")
   int xSize;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "[Lej;"
   )
   GameObject[] tempGameObjects;
   @ObfuscatedName("bj")
   int[][] tileShape2D;
   @ObfuscatedName("bs")
   int[][] tileRotation2D;
   @ObfuscatedName("i")
   int[][][] field1832;
   @ObfuscatedName("m")
   int tempGameObjectsCount;

   static {
      Scene_isLowDetail = true;
      tileUpdateCount = 0;
      Scene_plane = 0;
      gameObjects = new GameObject[100];
      checkClick = false;
      Scene_selectedPlane = 0;
      Scene_selectedScreenX = 0;
      Scene_selectedScreenY = 0;
      Scene_selectedX = -1;
      Scene_selectedY = -1;
      viewportWalking = false;
      Scene_planesCount = 4;
      Scene_planeOccluderCounts = new int[Scene_planesCount];
      Scene_planeOccluders = new Occluder[Scene_planesCount][500];
      Scene_currentOccludersCount = 0;
      Scene_currentOccluders = new Occluder[500];
      Scene_tilesDeque = new NodeDeque();
      field1887 = new int[]{19, 55, 38, 155, 255, 110, 137, 205, 76};
      field1870 = new int[]{160, 192, 80, 96, 0, 144, 80, 48, 160};
      field1823 = new int[]{76, 8, 137, 4, 0, 1, 38, 2, 19};
      field1872 = new int[]{0, 0, 2, 0, 0, 2, 1, 1, 0};
      field1869 = new int[]{2, 0, 0, 2, 0, 0, 0, 4, 4};
      field1868 = new int[]{0, 4, 4, 8, 0, 0, 8, 0, 0};
      field1875 = new int[]{1, 1, 0, 0, 0, 8, 0, 0, 8};
      visibilityMap = new boolean[8][32][51][51];
      rl$$clinit();
   }

   public Scene(int var1, int var2, int var3, int[][][] var4) {
      this.minPlane = 0;
      this.tempGameObjectsCount = 0;
      this.tempGameObjects = new GameObject[5000];
      this.tileShape2D = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1}, {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1}};
      this.tileRotation2D = new int[][]{{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, {12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3}, {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, {3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12}};
      this.planes = var1;
      this.xSize = var2;
      this.ySize = var3;
      this.tiles = new Tile[var1][var2][var3];
      this.field1832 = new int[var1][var2 + 1][var3 + 1];
      this.tileHeights = var4;
      this.method3141();
   }

   @ObfuscatedName("n")
   public void method3115(int var1) {
      this.minPlane = var1;

      for(int var2 = 0; var2 < this.xSize; ++var2) {
         for(int var3 = 0; var3 < this.ySize; ++var3) {
            if(this.tiles[var1][var2][var3] == null) {
               this.tiles[var1][var2][var3] = new Tile(var1, var2, var3);
            }
         }
      }

   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(IIIIILer;IJIIII)Z"
   )
   public boolean method3126(int var1, int var2, int var3, int var4, int var5, Entity var6, int var7, long var8, int var10, int var11, int var12, int var13) {
      return var6 == null?true:this.method3127(var1, var10, var11, var12 - var10 + 1, var13 - var11 + 1, var2, var3, var4, var6, var7, true, var8, 0);
   }

   @ObfuscatedName("p")
   public void method3156(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19, int var20) {
      TilePaint var21;
      int var22;
      if(var4 == 0) {
         var21 = new TilePaint(var11, var12, var13, var14, -1, var19, false);

         for(var22 = var1; var22 >= 0; --var22) {
            if(this.tiles[var22][var2][var3] == null) {
               this.tiles[var22][var2][var3] = new Tile(var22, var2, var3);
            }
         }

         this.tiles[var1][var2][var3].paint = var21;
      } else if(var4 != 1) {
         TileModel var23 = new TileModel(var4, var5, var6, var2, var3, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20);

         for(var22 = var1; var22 >= 0; --var22) {
            if(this.tiles[var22][var2][var3] == null) {
               this.tiles[var22][var2][var3] = new Tile(var22, var2, var3);
            }
         }

         this.tiles[var1][var2][var3].model = var23;
      } else {
         var21 = new TilePaint(var15, var16, var17, var18, var6, var20, var8 == var7 && var7 == var9 && var10 == var7);

         for(var22 = var1; var22 >= 0; --var22) {
            if(this.tiles[var22][var2][var3] == null) {
               this.tiles[var22][var2][var3] = new Tile(var22, var2, var3);
            }
         }

         this.tiles[var1][var2][var3].paint = var21;
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(IIIILer;JI)V"
   )
   public void method3235(int var1, int var2, int var3, int var4, Entity var5, long var6, int var8) {
      this.copy$newFloorDecoration(var1, var2, var3, var4, var5, var6, var8);
      RSTile var9 = this.getTiles()[var1][var2][var3];
      if(var9 != null) {
         RSFloorDecoration var10 = (RSFloorDecoration)var9.getGroundObject();
         if(var10 != null) {
            var10.setPlane(var1);
         }
      }

   }

   @ObfuscatedName("r")
   public void method3198(int var1, int var2, int var3, int var4) {
      Tile var5 = this.tiles[var1][var2][var3];
      if(var5 != null) {
         this.tiles[var1][var2][var3].minPlane = var4;
      }
   }

   @ObfuscatedName("s")
   public void method3114(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      if(var4 != null) {
         var4.boundaryObject = null;
         var4.wallObjectChanged(-1);
      }
   }

   @ObfuscatedName("v")
   public void method3220(int var1, int var2) {
      Tile var3 = this.tiles[0][var1][var2];

      for(int var4 = 0; var4 < 3; ++var4) {
         Tile var5 = this.tiles[var4][var1][var2] = this.tiles[var4 + 1][var1][var2];
         if(var5 != null) {
            --var5.plane;

            for(int var6 = 0; var6 < var5.gameObjectsCount; ++var6) {
               GameObject var7 = var5.gameObjects[var6];
               if(WorldMapDecoration.method5194(var7.tag) && var7.startX == var1 && var2 == var7.startY) {
                  --var7.plane;
               }
            }
         }
      }

      if(this.tiles[0][var1][var2] == null) {
         this.tiles[0][var1][var2] = new Tile(0, var1, var2);
      }

      this.tiles[0][var1][var2].linkedBelowTile = var3;
      this.tiles[3][var1][var2] = null;
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "(Lej;)V"
   )
   void method3225(GameObject var1) {
      for(int var2 = var1.startX; var2 <= var1.endX; ++var2) {
         for(int var3 = var1.startY; var3 <= var1.endY; ++var3) {
            Tile var4 = this.tiles[var1.plane][var2][var3];
            if(var4 != null) {
               int var5;
               for(var5 = 0; var5 < var4.gameObjectsCount; ++var5) {
                  if(var4.gameObjects[var5] == var1) {
                     --var4.gameObjectsCount;

                     for(int var6 = var5; var6 < var4.gameObjectsCount; ++var6) {
                        var4.gameObjects[var6] = var4.gameObjects[var6 + 1];
                        var4.gameObjectsChanged(var6);
                        var4.gameObjectEdgeMasks[var6] = var4.gameObjectEdgeMasks[var6 + 1];
                     }

                     var4.gameObjects[var4.gameObjectsCount] = null;
                     var4.gameObjectsChanged(var4.gameObjectsCount);
                     break;
                  }
               }

               var4.gameObjectsEdgeMask = 0;

               for(var5 = 0; var5 < var4.gameObjectsCount; ++var5) {
                  var4.gameObjectsEdgeMask |= var4.gameObjectEdgeMasks[var5];
               }
            }
         }
      }

   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(IIIILer;Ler;IIJI)V"
   )
   public void method3116(int var1, int var2, int var3, int var4, Entity var5, Entity var6, int var7, int var8, long var9, int var11) {
      this.copy$newBoundaryObject(var1, var2, var3, var4, var5, var6, var7, var8, var9, var11);
      RSTile var12 = this.getTiles()[var1][var2][var3];
      if(var12 != null) {
         RSBoundaryObject var13 = (RSBoundaryObject)var12.getWallObject();
         if(var13 != null) {
            var13.setPlane(var1);
         }
      }

   }

   @ObfuscatedName("z")
   public void method3141() {
      int var1;
      int var2;
      for(var1 = 0; var1 < this.planes; ++var1) {
         for(var2 = 0; var2 < this.xSize; ++var2) {
            for(int var3 = 0; var3 < this.ySize; ++var3) {
               this.tiles[var1][var2][var3] = null;
            }
         }
      }

      for(var1 = 0; var1 < Scene_planesCount; ++var1) {
         for(var2 = 0; var2 < Scene_planeOccluderCounts[var1]; ++var2) {
            Scene_planeOccluders[var1][var2] = null;
         }

         Scene_planeOccluderCounts[var1] = 0;
      }

      for(var1 = 0; var1 < this.tempGameObjectsCount; ++var1) {
         this.tempGameObjects[var1] = null;
      }

      this.tempGameObjectsCount = 0;

      for(var1 = 0; var1 < gameObjects.length; ++var1) {
         gameObjects[var1] = null;
      }

   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(IIIIIIIILer;IZJI)Z"
   )
   boolean method3127(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, Entity var9, int var10, boolean var11, long var12, int var14) {
      boolean var15 = shouldDraw(var9, false);
      if(!var15) {
         int var16 = var6 >> 7;
         int var17 = var7 >> 7;
         ViewportMouse.client.getOccupiedTilesTick()[var16][var17] = -1;
      }

      return var15 && this.copy$newGameObject(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var14);
   }

   @ObfuscatedName("aa")
   @ObfuscatedSignature(
      signature = "(III)Lej;"
   )
   public GameObject method3138(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      if(var4 == null) {
         return null;
      } else {
         for(int var5 = 0; var5 < var4.gameObjectsCount; ++var5) {
            GameObject var6 = var4.gameObjects[var5];
            if(WorldMapDecoration.method5194(var6.tag) && var2 == var6.startX && var3 == var6.startY) {
               return var6;
            }
         }

         return null;
      }
   }

   @ObfuscatedName("ab")
   public long method3265(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      return var4 != null && var4.wallDecoration != null?var4.wallDecoration.tag:0L;
   }

   @ObfuscatedName("ac")
   @ObfuscatedSignature(
      signature = "(III)Leg;"
   )
   public WallDecoration method3137(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      return var4 == null?null:var4.wallDecoration;
   }

   @ObfuscatedName("ad")
   @ObfuscatedSignature(
      signature = "(Ldw;III)V"
   )
   void method3146(ModelData var1, int var2, int var3, int var4) {
      Tile var5;
      ModelData var6;
      if(var3 < this.xSize) {
         var5 = this.tiles[var2][var3 + 1][var4];
         if(var5 != null && var5.floorDecoration != null && var5.floorDecoration.entity instanceof ModelData) {
            var6 = (ModelData)var5.floorDecoration.entity;
            ModelData.method2777(var1, var6, 128, 0, 0, true);
         }
      }

      if(var4 < this.xSize) {
         var5 = this.tiles[var2][var3][var4 + 1];
         if(var5 != null && var5.floorDecoration != null && var5.floorDecoration.entity instanceof ModelData) {
            var6 = (ModelData)var5.floorDecoration.entity;
            ModelData.method2777(var1, var6, 0, 0, 128, true);
         }
      }

      if(var3 < this.xSize && var4 < this.ySize) {
         var5 = this.tiles[var2][var3 + 1][var4 + 1];
         if(var5 != null && var5.floorDecoration != null && var5.floorDecoration.entity instanceof ModelData) {
            var6 = (ModelData)var5.floorDecoration.entity;
            ModelData.method2777(var1, var6, 128, 0, 128, true);
         }
      }

      if(var3 < this.xSize && var4 > 0) {
         var5 = this.tiles[var2][var3 + 1][var4 - 1];
         if(var5 != null && var5.floorDecoration != null && var5.floorDecoration.entity instanceof ModelData) {
            var6 = (ModelData)var5.floorDecoration.entity;
            ModelData.method2777(var1, var6, 128, 0, -128, true);
         }
      }

   }

   @ObfuscatedName("ae")
   public void method3152() {
      viewportWalking = true;
   }

   @ObfuscatedName("ag")
   public void method3155(int var1, int var2, int var3, int var4, int var5, int var6) {
      DrawCallbacks var7 = ViewportMouse.client.getDrawCallbacks();
      if(var7 != null) {
         var7.drawScene(var1, var2, var3, var4, var5, var6);
      }

      boolean var8 = ViewportMouse.client.isGpu();
      boolean var9 = ViewportMouse.client.isCheckClick();
      int var10;
      int var11;
      if(!ViewportMouse.client.isMenuOpen()) {
         ViewportMouse.client.setCheckClick(true);
         var10 = ViewportMouse.client.getMouseX();
         var11 = ViewportMouse.client.getMouseY();
         ViewportMouse.client.setMouseCanvasHoverPositionX(var10 - ViewportMouse.client.getViewportXOffset());
         ViewportMouse.client.setMouseCanvasHoverPositionY(var11 - ViewportMouse.client.getViewportYOffset());
      }

      if(!var8 && Client.skyboxColor != 0) {
         ViewportMouse.client.rasterizerFillRectangle(ViewportMouse.client.getViewportXOffset(), ViewportMouse.client.getViewportYOffset(), ViewportMouse.client.getViewportWidth(), ViewportMouse.client.getViewportHeight(), Client.skyboxColor);
      }

      var10 = this.getMaxX();
      var11 = this.getMaxY();
      int var12 = this.getMaxZ();
      int var13 = this.getMinLevel();
      RSTile[][][] var14 = this.getTiles();
      int var15 = var8?rl$drawDistance:25;
      if(var1 < 0) {
         var1 = 0;
      } else if(var1 >= var10 * 128) {
         var1 = var10 * 128 - 1;
      }

      if(var3 < 0) {
         var3 = 0;
      } else if(var3 >= var12 * 128) {
         var3 = var12 * 128 - 1;
      }

      int var16 = var4;
      if(var4 < 128) {
         var4 = 128;
      } else if(var4 > 383) {
         var4 = 383;
      }

      if(!Client.pitchRelaxEnabled) {
         var16 = var4;
      }

      ViewportMouse.client.setCycle(ViewportMouse.client.getCycle() + 1);
      ViewportMouse.client.setPitchSin(Perspective.SINE[var16]);
      ViewportMouse.client.setPitchCos(Perspective.COSINE[var16]);
      ViewportMouse.client.setYawSin(Perspective.SINE[var5]);
      ViewportMouse.client.setYawCos(Perspective.COSINE[var5]);
      int[][][] var17 = ViewportMouse.client.getTileHeights();
      boolean[][] var18 = ViewportMouse.client.getVisibilityMaps()[(var4 - 128) / 32][var5 / 64];
      ViewportMouse.client.setRenderArea(var18);
      ViewportMouse.client.setCameraX2(var1);
      ViewportMouse.client.setCameraY2(var2);
      ViewportMouse.client.setCameraZ2(var3);
      int var19 = var1 / 128;
      int var20 = var3 / 128;
      ViewportMouse.client.setScreenCenterX(var19);
      ViewportMouse.client.setScreenCenterZ(var20);
      ViewportMouse.client.setScenePlane(var6);
      int var21 = var19 - var15;
      if(var21 < 0) {
         var21 = 0;
      }

      int var22 = var20 - var15;
      if(var22 < 0) {
         var22 = 0;
      }

      int var23 = var19 + var15;
      if(var23 > var10) {
         var23 = var10;
      }

      int var24 = var20 + var15;
      if(var24 > var12) {
         var24 = var12;
      }

      ViewportMouse.client.setMinTileX(var21);
      ViewportMouse.client.setMinTileZ(var22);
      ViewportMouse.client.setMaxTileX(var23);
      ViewportMouse.client.setMaxTileZ(var24);
      this.updateOccluders();
      ViewportMouse.client.setTileUpdateCount(0);

      int var25;
      RSTile[][] var26;
      int var27;
      int var28;
      for(var25 = var13; var25 < var11; ++var25) {
         var26 = var14[var25];

         for(var27 = var21; var27 < var23; ++var27) {
            for(var28 = var22; var28 < var24; ++var28) {
               RSTile var29 = var26[var27][var28];
               if(var29 != null) {
                  if(var29.getPhysicalLevel() <= var6 && (var8 || var18[var27 - var19 + 25][var28 - var20 + 25] || var17[var25][var27][var28] - var2 >= 2000)) {
                     var29.setDraw(true);
                     var29.setVisible(true);
                     var29.setDrawEntities(true);
                     ViewportMouse.client.setTileUpdateCount(ViewportMouse.client.getTileUpdateCount() + 1);
                  } else {
                     var29.setDraw(false);
                     var29.setVisible(false);
                     var29.setWallCullDirection(0);
                  }
               }
            }
         }
      }

      int var30;
      int var31;
      int var32;
      RSTile var33;
      int var34;
      for(var25 = var13; var25 < var11; ++var25) {
         var26 = var14[var25];

         for(var27 = -var15; var27 <= 0; ++var27) {
            var28 = var27 + var19;
            var34 = var19 - var27;
            if(var28 >= var21 || var34 < var23) {
               for(var30 = -var15; var30 <= 0; ++var30) {
                  var31 = var30 + var20;
                  var32 = var20 - var30;
                  if(var28 >= var21) {
                     if(var31 >= var22) {
                        var33 = var26[var28][var31];
                        if(var33 != null && var33.isDraw()) {
                           this.draw(var33, true);
                        }
                     }

                     if(var32 < var24) {
                        var33 = var26[var28][var32];
                        if(var33 != null && var33.isDraw()) {
                           this.draw(var33, true);
                        }
                     }
                  }

                  if(var34 < var23) {
                     if(var31 >= var22) {
                        var33 = var26[var34][var31];
                        if(var33 != null && var33.isDraw()) {
                           this.draw(var33, true);
                        }
                     }

                     if(var32 < var24) {
                        var33 = var26[var34][var32];
                        if(var33 != null && var33.isDraw()) {
                           this.draw(var33, true);
                        }
                     }
                  }

                  if(ViewportMouse.client.getTileUpdateCount() == 0) {
                     ViewportMouse.client.setCheckClick(false);
                     if(!var9) {
                        ViewportMouse.client.setViewportWalking(false);
                     }

                     ViewportMouse.client.getCallbacks().drawScene();
                     return;
                  }
               }
            }
         }
      }

      for(var25 = var13; var25 < var11; ++var25) {
         var26 = var14[var25];

         for(var27 = -var15; var27 <= 0; ++var27) {
            var28 = var27 + var19;
            var34 = var19 - var27;
            if(var28 >= var21 || var34 < var23) {
               for(var30 = -var15; var30 <= 0; ++var30) {
                  var31 = var30 + var20;
                  var32 = var20 - var30;
                  if(var28 >= var21) {
                     if(var31 >= var22) {
                        var33 = var26[var28][var31];
                        if(var33 != null && var33.isDraw()) {
                           this.draw(var33, false);
                        }
                     }

                     if(var32 < var24) {
                        var33 = var26[var28][var32];
                        if(var33 != null && var33.isDraw()) {
                           this.draw(var33, false);
                        }
                     }
                  }

                  if(var34 < var23) {
                     if(var31 >= var22) {
                        var33 = var26[var34][var31];
                        if(var33 != null && var33.isDraw()) {
                           this.draw(var33, false);
                        }
                     }

                     if(var32 < var24) {
                        var33 = var26[var34][var32];
                        if(var33 != null && var33.isDraw()) {
                           this.draw(var33, false);
                        }
                     }
                  }

                  if(ViewportMouse.client.getTileUpdateCount() == 0) {
                     ViewportMouse.client.setCheckClick(false);
                     if(!var9) {
                        ViewportMouse.client.setViewportWalking(false);
                     }

                     ViewportMouse.client.getCallbacks().drawScene();
                     return;
                  }
               }
            }
         }
      }

      ViewportMouse.client.setCheckClick(false);
      if(!var9) {
         ViewportMouse.client.setViewportWalking(false);
      }

      ViewportMouse.client.getCallbacks().drawScene();
   }

   @ObfuscatedName("ai")
   public void method3148(int[] var1, int var2, int var3, int var4, int var5, int var6) {
      Tile var7 = this.tiles[var4][var5][var6];
      if(var7 != null) {
         TilePaint var8 = var7.paint;
         int var10;
         if(var8 != null) {
            int var9 = var8.rgb;
            if(var9 != 0) {
               for(var10 = 0; var10 < 4; ++var10) {
                  var1[var2] = var9;
                  var1[var2 + 1] = var9;
                  var1[var2 + 2] = var9;
                  var1[var2 + 3] = var9;
                  var2 += var3;
               }

            }
         } else {
            TileModel var18 = var7.model;
            if(var18 != null) {
               var10 = var18.shape;
               int var11 = var18.rotation;
               int var12 = var18.underlayRgb;
               int var13 = var18.overlayRgb;
               int[] var14 = this.tileShape2D[var10];
               int[] var15 = this.tileRotation2D[var11];
               int var16 = 0;
               int var17;
               if(var12 != 0) {
                  for(var17 = 0; var17 < 4; ++var17) {
                     var1[var2] = var14[var15[var16++]] == 0?var12:var13;
                     var1[var2 + 1] = var14[var15[var16++]] == 0?var12:var13;
                     var1[var2 + 2] = var14[var15[var16++]] == 0?var12:var13;
                     var1[var2 + 3] = var14[var15[var16++]] == 0?var12:var13;
                     var2 += var3;
                  }
               } else {
                  for(var17 = 0; var17 < 4; ++var17) {
                     if(var14[var15[var16++]] != 0) {
                        var1[var2] = var13;
                     }

                     if(var14[var15[var16++]] != 0) {
                        var1[var2 + 1] = var13;
                     }

                     if(var14[var15[var16++]] != 0) {
                        var1[var2 + 2] = var13;
                     }

                     if(var14[var15[var16++]] != 0) {
                        var1[var2 + 3] = var13;
                     }

                     var2 += var3;
                  }
               }

            }
         }
      }
   }

   @ObfuscatedName("aj")
   @ObfuscatedSignature(
      signature = "(Ldl;Z)V"
   )
   void method3221(Tile var1, boolean var2) {
      Scene_tilesDeque.method5105(var1);

      while(true) {
         Tile var3;
         int var4;
         int var5;
         int var6;
         int var7;
         Tile[][] var8;
         Tile var9;
         int var11;
         int var14;
         int var15;
         int var16;
         int var24;
         int var25;
         do {
            do {
               do {
                  do {
                     do {
                        do {
                           while(true) {
                              BoundaryObject var10;
                              GameObject var12;
                              int var17;
                              int var18;
                              boolean var20;
                              int var21;
                              Tile var36;
                              while(true) {
                                 do {
                                    var3 = (Tile)Scene_tilesDeque.method5108();
                                    if(var3 == null) {
                                       return;
                                    }
                                 } while(!var3.drawSecondary);

                                 var4 = var3.x;
                                 var5 = var3.y;
                                 var6 = var3.plane;
                                 var7 = var3.originalPlane;
                                 var8 = this.tiles[var6];
                                 if(!var3.drawPrimary) {
                                    break;
                                 }

                                 if(var2) {
                                    if(var6 > 0) {
                                       var9 = this.tiles[var6 - 1][var4][var5];
                                       if(var9 != null && var9.drawSecondary) {
                                          continue;
                                       }
                                    }

                                    if(var4 <= Scene_cameraXTile && var4 > Scene_cameraXTileMin) {
                                       var9 = var8[var4 - 1][var5];
                                       if(var9 != null && var9.drawSecondary && (var9.drawPrimary || (var3.gameObjectsEdgeMask & 1) == 0)) {
                                          continue;
                                       }
                                    }

                                    if(var4 >= Scene_cameraXTile && var4 < Scene_cameraXTileMax - 1) {
                                       var9 = var8[var4 + 1][var5];
                                       if(var9 != null && var9.drawSecondary && (var9.drawPrimary || (var3.gameObjectsEdgeMask & 4) == 0)) {
                                          continue;
                                       }
                                    }

                                    if(var5 <= Scene_cameraYTile && var5 > Scene_cameraYTileMin) {
                                       var9 = var8[var4][var5 - 1];
                                       if(var9 != null && var9.drawSecondary && (var9.drawPrimary || (var3.gameObjectsEdgeMask & 8) == 0)) {
                                          continue;
                                       }
                                    }

                                    if(var5 >= Scene_cameraYTile && var5 < Scene_cameraYTileMax - 1) {
                                       var9 = var8[var4][var5 + 1];
                                       if(var9 != null && var9.drawSecondary && (var9.drawPrimary || (var3.gameObjectsEdgeMask & 2) == 0)) {
                                          continue;
                                       }
                                    }
                                 } else {
                                    var2 = true;
                                 }

                                 var3.drawPrimary = false;
                                 if(var3.linkedBelowTile != null) {
                                    var9 = var3.linkedBelowTile;
                                    if(var9.paint != null) {
                                       if(!this.method3119(0, var4, var5)) {
                                          this.method3113(var9.paint, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var4, var5);
                                       }
                                    } else if(var9.model != null && !this.method3119(0, var4, var5)) {
                                       this.method3157(var9.model, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var4, var5);
                                    }

                                    var10 = var9.boundaryObject;
                                    if(var10 != null) {
                                       Hooks.renderDraw(var10.entity1, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var10.x - Scene_cameraX, var10.tileHeight - Scene_cameraY, var10.y - Scene_cameraZ, var10.tag);
                                    }

                                    for(var11 = 0; var11 < var9.gameObjectsCount; ++var11) {
                                       var12 = var9.gameObjects[var11];
                                       if(var12 != null) {
                                          Hooks.renderDraw(var12.entity, var12.orientation, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var12.centerX - Scene_cameraX, var12.height - Scene_cameraY, var12.centerY - Scene_cameraZ, var12.tag);
                                       }
                                    }
                                 }

                                 var20 = false;
                                 if(var3.paint != null) {
                                    if(!this.method3119(var7, var4, var5)) {
                                       var20 = true;
                                       if(var3.paint.neColor != 12345678 || checkClick && var6 <= Scene_selectedPlane) {
                                          this.method3113(var3.paint, var7, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var4, var5);
                                       }
                                    }
                                 } else if(var3.model != null && !this.method3119(var7, var4, var5)) {
                                    var20 = true;
                                    this.method3157(var3.model, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var4, var5);
                                 }

                                 var21 = 0;
                                 var11 = 0;
                                 BoundaryObject var31 = var3.boundaryObject;
                                 WallDecoration var13 = var3.wallDecoration;
                                 if(var31 != null || var13 != null) {
                                    if(var4 == Scene_cameraXTile) {
                                       ++var21;
                                    } else if(Scene_cameraXTile < var4) {
                                       var21 += 2;
                                    }

                                    if(var5 == Scene_cameraYTile) {
                                       var21 += 3;
                                    } else if(Scene_cameraYTile > var5) {
                                       var21 += 6;
                                    }

                                    var11 = field1887[var21];
                                    var3.field1501 = field1823[var21];
                                 }

                                 if(var31 != null) {
                                    if((var31.orientationA & field1870[var21]) != 0) {
                                       if(var31.orientationA == 16) {
                                          var3.drawGameObjectEdges = 3;
                                          var3.field1482 = field1872[var21];
                                          var3.field1496 = 3 - var3.field1482;
                                       } else if(var31.orientationA == 32) {
                                          var3.drawGameObjectEdges = 6;
                                          var3.field1482 = field1869[var21];
                                          var3.field1496 = 6 - var3.field1482;
                                       } else if(var31.orientationA == 64) {
                                          var3.drawGameObjectEdges = 12;
                                          var3.field1482 = field1868[var21];
                                          var3.field1496 = 12 - var3.field1482;
                                       } else {
                                          var3.drawGameObjectEdges = 9;
                                          var3.field1482 = field1875[var21];
                                          var3.field1496 = 9 - var3.field1482;
                                       }
                                    } else {
                                       var3.drawGameObjectEdges = 0;
                                    }

                                    if((var31.orientationA & var11) != 0 && !this.method3162(var7, var4, var5, var31.orientationA)) {
                                       Hooks.renderDraw(var31.entity1, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var31.x - Scene_cameraX, var31.tileHeight - Scene_cameraY, var31.y - Scene_cameraZ, var31.tag);
                                    }

                                    if((var31.orientationB & var11) != 0 && !this.method3162(var7, var4, var5, var31.orientationB)) {
                                       Hooks.renderDraw(var31.entity2, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var31.x - Scene_cameraX, var31.tileHeight - Scene_cameraY, var31.y - Scene_cameraZ, var31.tag);
                                    }
                                 }

                                 if(var13 != null && !this.method3200(var7, var4, var5, var13.entity1.height)) {
                                    if((var13.orientation & var11) != 0) {
                                       Hooks.renderDraw(var13.entity1, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var13.x - Scene_cameraX + var13.xOffset, var13.tileHeight - Scene_cameraY, var13.y - Scene_cameraZ + var13.yOffset, var13.tag);
                                    } else if(var13.orientation == 256) {
                                       var14 = var13.x - Scene_cameraX;
                                       var15 = var13.tileHeight - Scene_cameraY;
                                       var16 = var13.y - Scene_cameraZ;
                                       var17 = var13.orientation2;
                                       if(var17 != 1 && var17 != 2) {
                                          var18 = var14;
                                       } else {
                                          var18 = -var14;
                                       }

                                       int var19;
                                       if(var17 != 2 && var17 != 3) {
                                          var19 = var16;
                                       } else {
                                          var19 = -var16;
                                       }

                                       if(var19 < var18) {
                                          Hooks.renderDraw(var13.entity1, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var14 + var13.xOffset, var15, var16 + var13.yOffset, var13.tag);
                                       } else if(var13.entity2 != null) {
                                          Hooks.renderDraw(var13.entity2, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var14, var15, var16, var13.tag);
                                       }
                                    }
                                 }

                                 if(var20) {
                                    FloorDecoration var22 = var3.floorDecoration;
                                    if(var22 != null) {
                                       Hooks.renderDraw(var22.entity, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var22.x - Scene_cameraX, var22.tileHeight - Scene_cameraY, var22.y - Scene_cameraZ, var22.tag);
                                    }

                                    TileItemPile var23 = var3.tileItemPile;
                                    if(var23 != null && var23.height == 0) {
                                       if(var23.second != null) {
                                          Hooks.renderDraw(var23.second, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var23.x - Scene_cameraX, var23.tileHeight - Scene_cameraY, var23.y - Scene_cameraZ, var23.tag);
                                       }

                                       if(var23.third != null) {
                                          Hooks.renderDraw(var23.third, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var23.x - Scene_cameraX, var23.tileHeight - Scene_cameraY, var23.y - Scene_cameraZ, var23.tag);
                                       }

                                       if(var23.first != null) {
                                          Hooks.renderDraw(var23.first, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var23.x - Scene_cameraX, var23.tileHeight - Scene_cameraY, var23.y - Scene_cameraZ, var23.tag);
                                       }
                                    }
                                 }

                                 var14 = var3.gameObjectsEdgeMask;
                                 if(var14 != 0) {
                                    if(var4 < Scene_cameraXTile && (var14 & 4) != 0) {
                                       var36 = var8[var4 + 1][var5];
                                       if(var36 != null && var36.drawSecondary) {
                                          Scene_tilesDeque.method5105(var36);
                                       }
                                    }

                                    if(var5 < Scene_cameraYTile && (var14 & 2) != 0) {
                                       var36 = var8[var4][var5 + 1];
                                       if(var36 != null && var36.drawSecondary) {
                                          Scene_tilesDeque.method5105(var36);
                                       }
                                    }

                                    if(var4 > Scene_cameraXTile && (var14 & 1) != 0) {
                                       var36 = var8[var4 - 1][var5];
                                       if(var36 != null && var36.drawSecondary) {
                                          Scene_tilesDeque.method5105(var36);
                                       }
                                    }

                                    if(var5 > Scene_cameraYTile && (var14 & 8) != 0) {
                                       var36 = var8[var4][var5 - 1];
                                       if(var36 != null && var36.drawSecondary) {
                                          Scene_tilesDeque.method5105(var36);
                                       }
                                    }
                                 }
                                 break;
                              }

                              if(var3.drawGameObjectEdges != 0) {
                                 var20 = true;

                                 for(var21 = 0; var21 < var3.gameObjectsCount; ++var21) {
                                    if(var3.gameObjects[var21].lastDrawn != Scene_drawnCount && (var3.gameObjectEdgeMasks[var21] & var3.drawGameObjectEdges) == var3.field1482) {
                                       var20 = false;
                                       break;
                                    }
                                 }

                                 if(var20) {
                                    var10 = var3.boundaryObject;
                                    if(!this.method3162(var7, var4, var5, var10.orientationA)) {
                                       Hooks.renderDraw(var10.entity1, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var10.x - Scene_cameraX, var10.tileHeight - Scene_cameraY, var10.y - Scene_cameraZ, var10.tag);
                                    }

                                    var3.drawGameObjectEdges = 0;
                                 }
                              }

                              if(!var3.drawGameObjects) {
                                 break;
                              }

                              try {
                                 int var34 = var3.gameObjectsCount;
                                 var3.drawGameObjects = false;
                                 var21 = 0;

                                 label563:
                                 for(var11 = 0; var11 < var34; ++var11) {
                                    var12 = var3.gameObjects[var11];
                                    if(var12.lastDrawn != Scene_drawnCount) {
                                       for(var24 = var12.startX; var24 <= var12.endX; ++var24) {
                                          for(var14 = var12.startY; var14 <= var12.endY; ++var14) {
                                             var36 = var8[var24][var14];
                                             if(var36.drawPrimary) {
                                                var3.drawGameObjects = true;
                                                continue label563;
                                             }

                                             if(var36.drawGameObjectEdges != 0) {
                                                var16 = 0;
                                                if(var24 > var12.startX) {
                                                   ++var16;
                                                }

                                                if(var24 < var12.endX) {
                                                   var16 += 4;
                                                }

                                                if(var14 > var12.startY) {
                                                   var16 += 8;
                                                }

                                                if(var14 < var12.endY) {
                                                   var16 += 2;
                                                }

                                                if((var16 & var36.drawGameObjectEdges) == var3.field1496) {
                                                   var3.drawGameObjects = true;
                                                   continue label563;
                                                }
                                             }
                                          }
                                       }

                                       gameObjects[var21++] = var12;
                                       var24 = Scene_cameraXTile - var12.startX;
                                       var14 = var12.endX - Scene_cameraXTile;
                                       if(var14 > var24) {
                                          var24 = var14;
                                       }

                                       var15 = Scene_cameraYTile - var12.startY;
                                       var16 = var12.endY - Scene_cameraYTile;
                                       if(var16 > var15) {
                                          var12.field1718 = var24 + var16;
                                       } else {
                                          var12.field1718 = var24 + var15;
                                       }
                                    }
                                 }

                                 while(var21 > 0) {
                                    var11 = -50;
                                    var25 = -1;

                                    for(var24 = 0; var24 < var21; ++var24) {
                                       GameObject var35 = gameObjects[var24];
                                       if(var35.lastDrawn != Scene_drawnCount) {
                                          if(var35.field1718 > var11) {
                                             var11 = var35.field1718;
                                             var25 = var24;
                                          } else if(var11 == var35.field1718) {
                                             var15 = var35.centerX - Scene_cameraX;
                                             var16 = var35.centerY - Scene_cameraZ;
                                             var17 = gameObjects[var25].centerX - Scene_cameraX;
                                             var18 = gameObjects[var25].centerY - Scene_cameraZ;
                                             if(var15 * var15 + var16 * var16 > var17 * var17 + var18 * var18) {
                                                var25 = var24;
                                             }
                                          }
                                       }
                                    }

                                    if(var25 == -1) {
                                       break;
                                    }

                                    GameObject var33 = gameObjects[var25];
                                    var33.lastDrawn = Scene_drawnCount;
                                    if(!this.method3164(var7, var33.startX, var33.endX, var33.startY, var33.endY, var33.entity.height)) {
                                       Hooks.renderDraw(var33.entity, var33.orientation, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var33.centerX - Scene_cameraX, var33.height - Scene_cameraY, var33.centerY - Scene_cameraZ, var33.tag);
                                    }

                                    for(var14 = var33.startX; var14 <= var33.endX; ++var14) {
                                       for(var15 = var33.startY; var15 <= var33.endY; ++var15) {
                                          Tile var26 = var8[var14][var15];
                                          if(var26.drawGameObjectEdges != 0) {
                                             Scene_tilesDeque.method5105(var26);
                                          } else if((var14 != var4 || var15 != var5) && var26.drawSecondary) {
                                             Scene_tilesDeque.method5105(var26);
                                          }
                                       }
                                    }
                                 }

                                 if(!var3.drawGameObjects) {
                                    break;
                                 }
                              } catch (Exception var28) {
                                 var3.drawGameObjects = false;
                                 break;
                              }
                           }
                        } while(!var3.drawSecondary);
                     } while(var3.drawGameObjectEdges != 0);

                     if(var4 > Scene_cameraXTile || var4 <= Scene_cameraXTileMin) {
                        break;
                     }

                     var9 = var8[var4 - 1][var5];
                  } while(var9 != null && var9.drawSecondary);

                  if(var4 < Scene_cameraXTile || var4 >= Scene_cameraXTileMax - 1) {
                     break;
                  }

                  var9 = var8[var4 + 1][var5];
               } while(var9 != null && var9.drawSecondary);

               if(var5 > Scene_cameraYTile || var5 <= Scene_cameraYTileMin) {
                  break;
               }

               var9 = var8[var4][var5 - 1];
            } while(var9 != null && var9.drawSecondary);

            if(var5 < Scene_cameraYTile || var5 >= Scene_cameraYTileMax - 1) {
               break;
            }

            var9 = var8[var4][var5 + 1];
         } while(var9 != null && var9.drawSecondary);

         var3.drawSecondary = false;
         --tileUpdateCount;
         TileItemPile var32 = var3.tileItemPile;
         if(var32 != null && var32.height != 0) {
            if(var32.second != null) {
               Hooks.renderDraw(var32.second, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var32.x - Scene_cameraX, var32.tileHeight - Scene_cameraY - var32.height, var32.y - Scene_cameraZ, var32.tag);
            }

            if(var32.third != null) {
               Hooks.renderDraw(var32.third, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var32.x - Scene_cameraX, var32.tileHeight - Scene_cameraY - var32.height, var32.y - Scene_cameraZ, var32.tag);
            }

            if(var32.first != null) {
               Hooks.renderDraw(var32.first, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var32.x - Scene_cameraX, var32.tileHeight - Scene_cameraY - var32.height, var32.y - Scene_cameraZ, var32.tag);
            }
         }

         if(var3.field1501 != 0) {
            WallDecoration var29 = var3.wallDecoration;
            if(var29 != null && !this.method3200(var7, var4, var5, var29.entity1.height)) {
               if((var29.orientation & var3.field1501) != 0) {
                  Hooks.renderDraw(var29.entity1, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var29.x - Scene_cameraX + var29.xOffset, var29.tileHeight - Scene_cameraY, var29.y - Scene_cameraZ + var29.yOffset, var29.tag);
               } else if(var29.orientation == 256) {
                  var11 = var29.x - Scene_cameraX;
                  var25 = var29.tileHeight - Scene_cameraY;
                  var24 = var29.y - Scene_cameraZ;
                  var14 = var29.orientation2;
                  if(var14 != 1 && var14 != 2) {
                     var15 = var11;
                  } else {
                     var15 = -var11;
                  }

                  if(var14 != 2 && var14 != 3) {
                     var16 = var24;
                  } else {
                     var16 = -var24;
                  }

                  if(var16 >= var15) {
                     Hooks.renderDraw(var29.entity1, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var11 + var29.xOffset, var25, var24 + var29.yOffset, var29.tag);
                  } else if(var29.entity2 != null) {
                     Hooks.renderDraw(var29.entity2, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var11, var25, var24, var29.tag);
                  }
               }
            }

            BoundaryObject var27 = var3.boundaryObject;
            if(var27 != null) {
               if((var27.orientationB & var3.field1501) != 0 && !this.method3162(var7, var4, var5, var27.orientationB)) {
                  Hooks.renderDraw(var27.entity2, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var27.x - Scene_cameraX, var27.tileHeight - Scene_cameraY, var27.y - Scene_cameraZ, var27.tag);
               }

               if((var27.orientationA & var3.field1501) != 0 && !this.method3162(var7, var4, var5, var27.orientationA)) {
                  Hooks.renderDraw(var27.entity1, 0, Scene_cameraPitchSine, Scene_cameraPitchCosine, Scene_cameraYawSine, Scene_cameraYawCosine, var27.x - Scene_cameraX, var27.tileHeight - Scene_cameraY, var27.y - Scene_cameraZ, var27.tag);
               }
            }
         }

         Tile var30;
         if(var6 < this.planes - 1) {
            var30 = this.tiles[var6 + 1][var4][var5];
            if(var30 != null && var30.drawSecondary) {
               Scene_tilesDeque.method5105(var30);
            }
         }

         if(var4 < Scene_cameraXTile) {
            var30 = var8[var4 + 1][var5];
            if(var30 != null && var30.drawSecondary) {
               Scene_tilesDeque.method5105(var30);
            }
         }

         if(var5 < Scene_cameraYTile) {
            var30 = var8[var4][var5 + 1];
            if(var30 != null && var30.drawSecondary) {
               Scene_tilesDeque.method5105(var30);
            }
         }

         if(var4 > Scene_cameraXTile) {
            var30 = var8[var4 - 1][var5];
            if(var30 != null && var30.drawSecondary) {
               Scene_tilesDeque.method5105(var30);
            }
         }

         if(var5 > Scene_cameraYTile) {
            var30 = var8[var4][var5 - 1];
            if(var30 != null && var30.drawSecondary) {
               Scene_tilesDeque.method5105(var30);
            }
         }
      }
   }

   @ObfuscatedName("al")
   public void method3139(int var1, int var2, int var3) {
      for(int var4 = 0; var4 < this.planes; ++var4) {
         for(int var5 = 0; var5 < this.xSize; ++var5) {
            for(int var6 = 0; var6 < this.ySize; ++var6) {
               Tile var7 = this.tiles[var4][var5][var6];
               if(var7 != null) {
                  BoundaryObject var8 = var7.boundaryObject;
                  ModelData var10;
                  if(var8 != null && var8.entity1 instanceof ModelData) {
                     ModelData var9 = (ModelData)var8.entity1;
                     this.method3180(var9, var4, var5, var6, 1, 1);
                     if(var8.entity2 instanceof ModelData) {
                        var10 = (ModelData)var8.entity2;
                        this.method3180(var10, var4, var5, var6, 1, 1);
                        ModelData.method2777(var9, var10, 0, 0, 0, false);
                        var8.entity2 = var10.method2778(var10.ambient, var10.contrast, var1, var2, var3);
                     }

                     var8.entity1 = var9.method2778(var9.ambient, var9.contrast, var1, var2, var3);
                  }

                  for(int var12 = 0; var12 < var7.gameObjectsCount; ++var12) {
                     GameObject var14 = var7.gameObjects[var12];
                     if(var14 != null && var14.entity instanceof ModelData) {
                        ModelData var11 = (ModelData)var14.entity;
                        this.method3180(var11, var4, var5, var6, var14.endX - var14.startX + 1, var14.endY - var14.startY + 1);
                        var14.entity = var11.method2778(var11.ambient, var11.contrast, var1, var2, var3);
                     }
                  }

                  FloorDecoration var13 = var7.floorDecoration;
                  if(var13 != null && var13.entity instanceof ModelData) {
                     var10 = (ModelData)var13.entity;
                     this.method3146(var10, var4, var5, var6);
                     var13.entity = var10.method2778(var10.ambient, var10.contrast, var1, var2, var3);
                  }
               }
            }
         }
      }

   }

   @ObfuscatedName("am")
   public void method3151(int var1, int var2, int var3, boolean var4) {
      if(!method3153() || var4) {
         checkClick = true;
         viewportWalking = var4;
         Scene_selectedPlane = var1;
         Scene_selectedScreenX = var2;
         Scene_selectedScreenY = var3;
         Scene_selectedX = -1;
         Scene_selectedY = -1;
      }
   }

   @ObfuscatedName("an")
   @ObfuscatedSignature(
      signature = "(Ldw;IIIII)V"
   )
   void method3180(ModelData var1, int var2, int var3, int var4, int var5, int var6) {
      boolean var7 = true;
      int var8 = var3;
      int var9 = var3 + var5;
      int var10 = var4 - 1;
      int var11 = var4 + var6;

      for(int var12 = var2; var12 <= var2 + 1; ++var12) {
         if(var12 != this.planes) {
            for(int var13 = var8; var13 <= var9; ++var13) {
               if(var13 >= 0 && var13 < this.xSize) {
                  for(int var14 = var10; var14 <= var11; ++var14) {
                     if(var14 >= 0 && var14 < this.ySize && (!var7 || var13 >= var9 || var14 >= var11 || var14 < var4 && var3 != var13)) {
                        Tile var15 = this.tiles[var12][var13][var14];
                        if(var15 != null) {
                           int var16 = (this.tileHeights[var12][var13 + 1][var14] + this.tileHeights[var12][var13 + 1][var14 + 1] + this.tileHeights[var12][var13][var14] + this.tileHeights[var12][var13][var14 + 1]) / 4 - (this.tileHeights[var2][var3 + 1][var4] + this.tileHeights[var2][var3][var4] + this.tileHeights[var2][var3 + 1][var4 + 1] + this.tileHeights[var2][var3][var4 + 1]) / 4;
                           BoundaryObject var17 = var15.boundaryObject;
                           if(var17 != null) {
                              ModelData var18;
                              if(var17.entity1 instanceof ModelData) {
                                 var18 = (ModelData)var17.entity1;
                                 ModelData.method2777(var1, var18, (1 - var5) * 64 + (var13 - var3) * 128, var16, (var14 - var4) * 128 + (1 - var6) * 64, var7);
                              }

                              if(var17.entity2 instanceof ModelData) {
                                 var18 = (ModelData)var17.entity2;
                                 ModelData.method2777(var1, var18, (1 - var5) * 64 + (var13 - var3) * 128, var16, (var14 - var4) * 128 + (1 - var6) * 64, var7);
                              }
                           }

                           for(int var23 = 0; var23 < var15.gameObjectsCount; ++var23) {
                              GameObject var19 = var15.gameObjects[var23];
                              if(var19 != null && var19.entity instanceof ModelData) {
                                 ModelData var20 = (ModelData)var19.entity;
                                 int var21 = var19.endX - var19.startX + 1;
                                 int var22 = var19.endY - var19.startY + 1;
                                 ModelData.method2777(var1, var20, (var21 - var5) * 64 + (var19.startX - var3) * 128, var16, (var19.startY - var4) * 128 + (var22 - var6) * 64, var7);
                              }
                           }
                        }
                     }
                  }
               }
            }

            --var8;
            var7 = false;
         }
      }

   }

   @ObfuscatedName("ao")
   public int method3144(int var1, int var2, int var3, long var4) {
      Tile var6 = this.tiles[var1][var2][var3];
      if(var6 == null) {
         return -1;
      } else if(var6.boundaryObject != null && var6.boundaryObject.tag == var4) {
         return var6.boundaryObject.flags & 255;
      } else if(var6.wallDecoration != null && var6.wallDecoration.tag == var4) {
         return var6.wallDecoration.flags & 255;
      } else if(var6.floorDecoration != null && var6.floorDecoration.tag == var4) {
         return var6.floorDecoration.flags & 255;
      } else {
         for(int var7 = 0; var7 < var6.gameObjectsCount; ++var7) {
            if(var6.gameObjects[var7].tag == var4) {
               return var6.gameObjects[var7].flags & 255;
            }
         }

         return -1;
      }
   }

   @ObfuscatedName("ap")
   @ObfuscatedSignature(
      signature = "(III)Ldj;"
   )
   public FloorDecoration method3256(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      return var4 != null && var4.floorDecoration != null?var4.floorDecoration:null;
   }

   @ObfuscatedName("ar")
   public long method3140(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      return var4 != null && var4.boundaryObject != null?var4.boundaryObject.tag:0L;
   }

   @ObfuscatedName("as")
   public long method3143(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      return var4 != null && var4.floorDecoration != null?var4.floorDecoration.tag:0L;
   }

   @ObfuscatedName("au")
   @ObfuscatedSignature(
      signature = "(Ldd;IIIIII)V"
   )
   void method3157(TileModel var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if(!ViewportMouse.client.isGpu()) {
         this.copy$drawTileOverlay(var1, var2, var3, var4, var5, var6, var7);
      } else {
         DrawCallbacks var8 = ViewportMouse.client.getDrawCallbacks();
         if(var8 != null) {
            try {
               int var9 = ViewportMouse.client.getCameraX2();
               int var10 = ViewportMouse.client.getCameraY2();
               int var11 = ViewportMouse.client.getCameraZ2();
               int var12 = ViewportMouse.client.get3dZoom();
               int var13 = ViewportMouse.client.getCenterX();
               int var14 = ViewportMouse.client.getCenterY();
               var8.drawSceneModel(0, var2, var3, var4, var5, -var9, -var10, -var11, var1, ViewportMouse.client.getPlane(), var6, var7, var12, var13, var14);
               boolean var15 = ViewportMouse.client.isCheckClick();
               if(var15) {
                  RSTileModel var16 = (RSTileModel)var1;
                  int[] var17 = var16.getFaceX();
                  int[] var18 = var16.getFaceY();
                  int[] var19 = var16.getFaceZ();
                  int[] var20 = var16.getVertexX();
                  int[] var21 = var16.getVertexY();
                  int[] var22 = var16.getVertexZ();
                  int var23 = var20.length;
                  int var24 = var17.length;
                  int var25 = ViewportMouse.client.getMouseX2();
                  int var26 = ViewportMouse.client.getMouseY2();

                  int var27;
                  int var28;
                  int var29;
                  int var30;
                  int var31;
                  int var32;
                  int var33;
                  int var34;
                  int var35;
                  int var36;
                  for(var27 = 0; var27 < var23; ++var27) {
                     var28 = var20[var27] - var9;
                     var29 = var21[var27] - var10;
                     var30 = var22[var27] - var11;
                     var31 = var30 * var4 + var5 * var28 >> 16;
                     var32 = var5 * var30 - var28 * var4 >> 16;
                     var33 = var3 * var29 - var32 * var2 >> 16;
                     var34 = var29 * var2 + var3 * var32 >> 16;
                     if(var34 < 50) {
                        return;
                     }

                     var35 = var31 * var12 / var34 + var13;
                     var36 = var33 * var12 / var34 + var14;
                     tmpX[var27] = var35;
                     tmpY[var27] = var36;
                  }

                  for(var27 = 0; var27 < var24; ++var27) {
                     var28 = var17[var27];
                     var29 = var18[var27];
                     var30 = var19[var27];
                     var31 = tmpX[var28];
                     var32 = tmpX[var29];
                     var33 = tmpX[var30];
                     var34 = tmpY[var28];
                     var35 = tmpY[var29];
                     var36 = tmpY[var30];
                     if((var31 - var32) * (var36 - var35) - (var34 - var35) * (var33 - var32) > 0 && ViewportMouse.client.containsBounds(var25, var26, var34, var35, var36, var31, var32, var33)) {
                        setTargetTile(var6, var7);
                        return;
                     }
                  }

               }
            } catch (Exception var38) {
               ViewportMouse.client.getLogger().warn("error during overlay rendering", var38);
            }
         }
      }
   }

   @ObfuscatedName("ax")
   public long method3142(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      if(var4 == null) {
         return 0L;
      } else {
         for(int var5 = 0; var5 < var4.gameObjectsCount; ++var5) {
            GameObject var6 = var4.gameObjects[var5];
            if(WorldMapDecoration.method5194(var6.tag) && var2 == var6.startX && var3 == var6.startY) {
               return var6.tag;
            }
         }

         return 0L;
      }
   }

   @ObfuscatedName("ay")
   @ObfuscatedSignature(
      signature = "(Lef;IIIIIII)V"
   )
   void method3113(TilePaint var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if(!ViewportMouse.client.isGpu()) {
         try {
            this.copy$drawTileUnderlay(var1, var2, var3, var4, var5, var6, var7, var8);
         } catch (Exception var41) {
            ViewportMouse.client.getLogger().warn("error during tile underlay rendering", var41);
         }

      } else {
         DrawCallbacks var9 = ViewportMouse.client.getDrawCallbacks();
         if(var9 != null) {
            try {
               int[][][] var10 = this.getTileHeights();
               int var11 = ViewportMouse.client.getCameraX2();
               int var12 = ViewportMouse.client.getCameraY2();
               int var13 = ViewportMouse.client.getCameraZ2();
               int var14 = ViewportMouse.client.get3dZoom();
               int var15 = ViewportMouse.client.getCenterX();
               int var16 = ViewportMouse.client.getCenterY();
               int var17 = ViewportMouse.client.getMouseX2();
               int var18 = ViewportMouse.client.getMouseY2();
               boolean var19 = ViewportMouse.client.isCheckClick();
               int var20;
               int var21 = var20 = (var7 << 7) - var11;
               int var22;
               int var23 = var22 = (var8 << 7) - var13;
               int var24;
               int var25 = var24 = var21 + 128;
               int var26;
               int var27 = var26 = var23 + 128;
               int var28 = var10[var2][var7][var8] - var12;
               int var29 = var10[var2][var7 + 1][var8] - var12;
               int var30 = var10[var2][var7 + 1][var8 + 1] - var12;
               int var31 = var10[var2][var7][var8 + 1] - var12;
               int var32 = var21 * var6 + var5 * var23 >> 16;
               var23 = var23 * var6 - var5 * var21 >> 16;
               var21 = var32;
               var32 = var28 * var4 - var3 * var23 >> 16;
               var23 = var23 * var4 + var3 * var28 >> 16;
               var28 = var32;
               if(var23 >= 50) {
                  var32 = var25 * var6 + var5 * var22 >> 16;
                  var22 = var22 * var6 - var5 * var25 >> 16;
                  var25 = var32;
                  var32 = var29 * var4 - var3 * var22 >> 16;
                  var22 = var22 * var4 + var3 * var29 >> 16;
                  var29 = var32;
                  if(var22 >= 50) {
                     var32 = var24 * var6 + var5 * var27 >> 16;
                     var27 = var27 * var6 - var5 * var24 >> 16;
                     var24 = var32;
                     var32 = var30 * var4 - var3 * var27 >> 16;
                     var27 = var27 * var4 + var3 * var30 >> 16;
                     var30 = var32;
                     if(var27 >= 50) {
                        var32 = var20 * var6 + var5 * var26 >> 16;
                        var26 = var26 * var6 - var5 * var20 >> 16;
                        var20 = var32;
                        var32 = var31 * var4 - var3 * var26 >> 16;
                        var26 = var26 * var4 + var3 * var31 >> 16;
                        if(var26 >= 50) {
                           int var33 = var21 * var14 / var23 + var15;
                           int var34 = var28 * var14 / var23 + var16;
                           int var35 = var25 * var14 / var22 + var15;
                           int var36 = var29 * var14 / var22 + var16;
                           int var37 = var24 * var14 / var27 + var15;
                           int var38 = var30 * var14 / var27 + var16;
                           int var39 = var20 * var14 / var26 + var15;
                           int var40 = var32 * var14 / var26 + var16;
                           var9.drawScenePaint(0, var3, var4, var5, var6, -var11, -var12, -var13, var1, var2, var7, var8, var14, var15, var16);
                           if((var37 - var39) * (var36 - var40) - (var38 - var40) * (var35 - var39) > 0 && var19 && ViewportMouse.client.containsBounds(var17, var18, var38, var40, var36, var37, var39, var35)) {
                              setTargetTile(var7, var8);
                           }

                           if((var33 - var35) * (var40 - var36) - (var34 - var36) * (var39 - var35) > 0 && var19 && ViewportMouse.client.containsBounds(var17, var18, var34, var36, var40, var33, var35, var39)) {
                              setTargetTile(var7, var8);
                           }
                        }
                     }
                  }
               }
            } catch (Exception var42) {
               ViewportMouse.client.getLogger().warn("error during underlay rendering", var42);
            }

         }
      }
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(IIIIILer;IJZ)Z"
   )
   public boolean method3125(int var1, int var2, int var3, int var4, int var5, Entity var6, int var7, long var8, boolean var10) {
      if(var6 == null) {
         return true;
      } else {
         int var11 = var2 - var5;
         int var12 = var3 - var5;
         int var13 = var5 + var2;
         int var14 = var3 + var5;
         if(var10) {
            if(var7 > 640 && var7 < 1408) {
               var14 += 128;
            }

            if(var7 > 1152 && var7 < 1920) {
               var13 += 128;
            }

            if(var7 > 1664 || var7 < 384) {
               var12 -= 128;
            }

            if(var7 > 128 && var7 < 896) {
               var11 -= 128;
            }
         }

         var11 /= 128;
         var12 /= 128;
         var13 /= 128;
         var14 /= 128;
         return this.method3127(var1, var11, var12, var13 - var11 + 1, var14 - var12 + 1, var2, var3, var4, var6, var7, true, var8, 0);
      }
   }

   @ObfuscatedName("bd")
   boolean method3162(int var1, int var2, int var3, int var4) {
      if(!this.method3119(var1, var2, var3)) {
         return false;
      } else {
         int var5 = var2 << 7;
         int var6 = var3 << 7;
         int var7 = this.tileHeights[var1][var2][var3] - 1;
         int var8 = var7 - 120;
         int var9 = var7 - 230;
         int var10 = var7 - 238;
         if(var4 < 16) {
            if(var4 == 1) {
               if(var5 > Scene_cameraX) {
                  if(!this.method3165(var5, var7, var6)) {
                     return false;
                  }

                  if(!this.method3165(var5, var7, var6 + 128)) {
                     return false;
                  }
               }

               if(var1 > 0) {
                  if(!this.method3165(var5, var8, var6)) {
                     return false;
                  }

                  if(!this.method3165(var5, var8, var6 + 128)) {
                     return false;
                  }
               }

               if(!this.method3165(var5, var9, var6)) {
                  return false;
               }

               if(!this.method3165(var5, var9, var6 + 128)) {
                  return false;
               }

               return true;
            }

            if(var4 == 2) {
               if(var6 < Scene_cameraZ) {
                  if(!this.method3165(var5, var7, var6 + 128)) {
                     return false;
                  }

                  if(!this.method3165(var5 + 128, var7, var6 + 128)) {
                     return false;
                  }
               }

               if(var1 > 0) {
                  if(!this.method3165(var5, var8, var6 + 128)) {
                     return false;
                  }

                  if(!this.method3165(var5 + 128, var8, var6 + 128)) {
                     return false;
                  }
               }

               if(!this.method3165(var5, var9, var6 + 128)) {
                  return false;
               }

               if(!this.method3165(var5 + 128, var9, var6 + 128)) {
                  return false;
               }

               return true;
            }

            if(var4 == 4) {
               if(var5 < Scene_cameraX) {
                  if(!this.method3165(var5 + 128, var7, var6)) {
                     return false;
                  }

                  if(!this.method3165(var5 + 128, var7, var6 + 128)) {
                     return false;
                  }
               }

               if(var1 > 0) {
                  if(!this.method3165(var5 + 128, var8, var6)) {
                     return false;
                  }

                  if(!this.method3165(var5 + 128, var8, var6 + 128)) {
                     return false;
                  }
               }

               if(!this.method3165(var5 + 128, var9, var6)) {
                  return false;
               }

               if(!this.method3165(var5 + 128, var9, var6 + 128)) {
                  return false;
               }

               return true;
            }

            if(var4 == 8) {
               if(var6 > Scene_cameraZ) {
                  if(!this.method3165(var5, var7, var6)) {
                     return false;
                  }

                  if(!this.method3165(var5 + 128, var7, var6)) {
                     return false;
                  }
               }

               if(var1 > 0) {
                  if(!this.method3165(var5, var8, var6)) {
                     return false;
                  }

                  if(!this.method3165(var5 + 128, var8, var6)) {
                     return false;
                  }
               }

               if(!this.method3165(var5, var9, var6)) {
                  return false;
               }

               if(!this.method3165(var5 + 128, var9, var6)) {
                  return false;
               }

               return true;
            }
         }

         return !this.method3165(var5 + 64, var10, var6 + 64)?false:(var4 == 16?this.method3165(var5, var9, var6 + 128):(var4 == 32?this.method3165(var5 + 128, var9, var6 + 128):(var4 == 64?this.method3165(var5 + 128, var9, var6):(var4 == 128?this.method3165(var5, var9, var6):true))));
      }
   }

   @ObfuscatedName("bh")
   boolean method3119(int var1, int var2, int var3) {
      int var4 = this.field1832[var1][var2][var3];
      if(var4 == -Scene_drawnCount) {
         return false;
      } else if(var4 == Scene_drawnCount) {
         return true;
      } else {
         int var5 = var2 << 7;
         int var6 = var3 << 7;
         if(this.method3165(var5 + 1, this.tileHeights[var1][var2][var3], var6 + 1) && this.method3165(var5 + 128 - 1, this.tileHeights[var1][var2 + 1][var3], var6 + 1) && this.method3165(var5 + 128 - 1, this.tileHeights[var1][var2 + 1][var3 + 1], var6 + 128 - 1) && this.method3165(var5 + 1, this.tileHeights[var1][var2][var3 + 1], var6 + 128 - 1)) {
            this.field1832[var1][var2][var3] = Scene_drawnCount;
            return true;
         } else {
            this.field1832[var1][var2][var3] = -Scene_drawnCount;
            return false;
         }
      }
   }

   @ObfuscatedName("bj")
   boolean method3165(int var1, int var2, int var3) {
      for(int var4 = 0; var4 < Scene_currentOccludersCount; ++var4) {
         Occluder var5 = Scene_currentOccluders[var4];
         int var6;
         int var7;
         int var8;
         int var9;
         int var10;
         if(var5.field1799 == 1) {
            var6 = var5.minX - var1;
            if(var6 > 0) {
               var7 = (var6 * var5.field1802 >> 8) + var5.minZ;
               var8 = (var6 * var5.field1803 >> 8) + var5.maxZ;
               var9 = (var6 * var5.field1796 >> 8) + var5.minY;
               var10 = (var6 * var5.field1804 >> 8) + var5.maxY;
               if(var3 >= var7 && var3 <= var8 && var2 >= var9 && var2 <= var10) {
                  return true;
               }
            }
         } else if(var5.field1799 == 2) {
            var6 = var1 - var5.minX;
            if(var6 > 0) {
               var7 = (var6 * var5.field1802 >> 8) + var5.minZ;
               var8 = (var6 * var5.field1803 >> 8) + var5.maxZ;
               var9 = (var6 * var5.field1796 >> 8) + var5.minY;
               var10 = (var6 * var5.field1804 >> 8) + var5.maxY;
               if(var3 >= var7 && var3 <= var8 && var2 >= var9 && var2 <= var10) {
                  return true;
               }
            }
         } else if(var5.field1799 == 3) {
            var6 = var5.minZ - var3;
            if(var6 > 0) {
               var7 = (var6 * var5.field1801 >> 8) + var5.minX;
               var8 = (var6 * var5.field1788 >> 8) + var5.maxX;
               var9 = (var6 * var5.field1796 >> 8) + var5.minY;
               var10 = (var6 * var5.field1804 >> 8) + var5.maxY;
               if(var1 >= var7 && var1 <= var8 && var2 >= var9 && var2 <= var10) {
                  return true;
               }
            }
         } else if(var5.field1799 == 4) {
            var6 = var3 - var5.minZ;
            if(var6 > 0) {
               var7 = (var6 * var5.field1801 >> 8) + var5.minX;
               var8 = (var6 * var5.field1788 >> 8) + var5.maxX;
               var9 = (var6 * var5.field1796 >> 8) + var5.minY;
               var10 = (var6 * var5.field1804 >> 8) + var5.maxY;
               if(var1 >= var7 && var1 <= var8 && var2 >= var9 && var2 <= var10) {
                  return true;
               }
            }
         } else if(var5.field1799 == 5) {
            var6 = var2 - var5.minY;
            if(var6 > 0) {
               var7 = (var6 * var5.field1801 >> 8) + var5.minX;
               var8 = (var6 * var5.field1788 >> 8) + var5.maxX;
               var9 = (var6 * var5.field1802 >> 8) + var5.minZ;
               var10 = (var6 * var5.field1803 >> 8) + var5.maxZ;
               if(var1 >= var7 && var1 <= var8 && var3 >= var9 && var3 <= var10) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @ObfuscatedName("bm")
   boolean method3200(int var1, int var2, int var3, int var4) {
      if(!this.method3119(var1, var2, var3)) {
         return false;
      } else {
         int var5 = var2 << 7;
         int var6 = var3 << 7;
         return this.method3165(var5 + 1, this.tileHeights[var1][var2][var3] - var4, var6 + 1) && this.method3165(var5 + 128 - 1, this.tileHeights[var1][var2 + 1][var3] - var4, var6 + 1) && this.method3165(var5 + 128 - 1, this.tileHeights[var1][var2 + 1][var3 + 1] - var4, var6 + 128 - 1) && this.method3165(var5 + 1, this.tileHeights[var1][var2][var3 + 1] - var4, var6 + 128 - 1);
      }
   }

   @ObfuscatedName("bv")
   boolean method3164(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7;
      int var8;
      if(var3 == var2 && var5 == var4) {
         if(!this.method3119(var1, var2, var4)) {
            return false;
         } else {
            var7 = var2 << 7;
            var8 = var4 << 7;
            return this.method3165(var7 + 1, this.tileHeights[var1][var2][var4] - var6, var8 + 1) && this.method3165(var7 + 128 - 1, this.tileHeights[var1][var2 + 1][var4] - var6, var8 + 1) && this.method3165(var7 + 128 - 1, this.tileHeights[var1][var2 + 1][var4 + 1] - var6, var8 + 128 - 1) && this.method3165(var7 + 1, this.tileHeights[var1][var2][var4 + 1] - var6, var8 + 128 - 1);
         }
      } else {
         for(var7 = var2; var7 <= var3; ++var7) {
            for(var8 = var4; var8 <= var5; ++var8) {
               if(this.field1832[var1][var7][var8] == -Scene_drawnCount) {
                  return false;
               }
            }
         }

         var7 = (var2 << 7) + 1;
         var8 = (var4 << 7) + 2;
         int var9 = this.tileHeights[var1][var2][var4] - var6;
         if(!this.method3165(var7, var9, var8)) {
            return false;
         } else {
            int var10 = (var3 << 7) - 1;
            if(!this.method3165(var10, var9, var8)) {
               return false;
            } else {
               int var11 = (var5 << 7) - 1;
               if(!this.method3165(var7, var9, var11)) {
                  return false;
               } else if(!this.method3165(var10, var9, var11)) {
                  return false;
               } else {
                  return true;
               }
            }
         }
      }
   }

   @ObfuscatedName("bx")
   void method3160() {
      int var1 = Scene_planeOccluderCounts[Scene_plane];
      Occluder[] var2 = Scene_planeOccluders[Scene_plane];
      Scene_currentOccludersCount = 0;

      for(int var3 = 0; var3 < var1; ++var3) {
         Occluder var4 = var2[var3];
         int var5;
         int var6;
         int var7;
         int var9;
         boolean var13;
         if(var4.type == 1) {
            var5 = var4.minTileX - Scene_cameraXTile + 90;
            if(var5 >= 0 && var5 <= 50) {
               var6 = var4.minTileY - Scene_cameraYTile + 90;
               if(var6 < 0) {
                  var6 = 0;
               }

               var7 = var4.maxTileY - Scene_cameraYTile + 90;
               if(var7 > 50) {
                  var7 = 50;
               }

               var13 = false;

               while(var6 <= var7) {
                  if(visibleTiles[var5][var6++]) {
                     var13 = true;
                     break;
                  }
               }

               if(var13) {
                  var9 = Scene_cameraX - var4.minX;
                  if(var9 > 32) {
                     var4.field1799 = 1;
                  } else {
                     if(var9 >= -32) {
                        continue;
                     }

                     var4.field1799 = 2;
                     var9 = -var9;
                  }

                  var4.field1802 = (var4.minZ - Scene_cameraZ << 8) / var9;
                  var4.field1803 = (var4.maxZ - Scene_cameraZ << 8) / var9;
                  var4.field1796 = (var4.minY - Scene_cameraY << 8) / var9;
                  var4.field1804 = (var4.maxY - Scene_cameraY << 8) / var9;
                  Scene_currentOccluders[Scene_currentOccludersCount++] = var4;
               }
            }
         } else if(var4.type == 2) {
            var5 = var4.minTileY - Scene_cameraYTile + 90;
            if(var5 >= 0 && var5 <= 50) {
               var6 = var4.minTileX - Scene_cameraXTile + 90;
               if(var6 < 0) {
                  var6 = 0;
               }

               var7 = var4.maxTileX - Scene_cameraXTile + 90;
               if(var7 > 50) {
                  var7 = 50;
               }

               var13 = false;

               while(var6 <= var7) {
                  if(visibleTiles[var6++][var5]) {
                     var13 = true;
                     break;
                  }
               }

               if(var13) {
                  var9 = Scene_cameraZ - var4.minZ;
                  if(var9 > 32) {
                     var4.field1799 = 3;
                  } else {
                     if(var9 >= -32) {
                        continue;
                     }

                     var4.field1799 = 4;
                     var9 = -var9;
                  }

                  var4.field1801 = (var4.minX - Scene_cameraX << 8) / var9;
                  var4.field1788 = (var4.maxX - Scene_cameraX << 8) / var9;
                  var4.field1796 = (var4.minY - Scene_cameraY << 8) / var9;
                  var4.field1804 = (var4.maxY - Scene_cameraY << 8) / var9;
                  Scene_currentOccluders[Scene_currentOccludersCount++] = var4;
               }
            }
         } else if(var4.type == 4) {
            var5 = var4.minY - Scene_cameraY;
            if(var5 > 128) {
               var6 = var4.minTileY - Scene_cameraYTile + 90;
               if(var6 < 0) {
                  var6 = 0;
               }

               var7 = var4.maxTileY - Scene_cameraYTile + 90;
               if(var7 > 50) {
                  var7 = 50;
               }

               if(var6 <= var7) {
                  int var8 = var4.minTileX - Scene_cameraXTile + 90;
                  if(var8 < 0) {
                     var8 = 0;
                  }

                  var9 = var4.maxTileX - Scene_cameraXTile + 90;
                  if(var9 > 50) {
                     var9 = 50;
                  }

                  boolean var10 = false;

                  label144:
                  for(int var11 = var8; var11 <= var9; ++var11) {
                     for(int var12 = var6; var12 <= var7; ++var12) {
                        if(visibleTiles[var11][var12]) {
                           var10 = true;
                           break label144;
                        }
                     }
                  }

                  if(var10) {
                     var4.field1799 = 5;
                     var4.field1801 = (var4.minX - Scene_cameraX << 8) / var5;
                     var4.field1788 = (var4.maxX - Scene_cameraX << 8) / var5;
                     var4.field1802 = (var4.minZ - Scene_cameraZ << 8) / var5;
                     var4.field1803 = (var4.maxZ - Scene_cameraZ << 8) / var5;
                     Scene_currentOccluders[Scene_currentOccludersCount++] = var4;
                  }
               }
            }
         }
      }

   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(IIIIIILer;IJI)Z"
   )
   public boolean method3124(int var1, int var2, int var3, int var4, int var5, int var6, Entity var7, int var8, long var9, int var11) {
      if(var7 == null) {
         return true;
      } else {
         int var12 = var5 * 64 + var2 * 128;
         int var13 = var6 * 64 + var3 * 128;
         return this.method3127(var1, var2, var3, var5, var6, var12, var13, var4, var7, var8, false, var9, var11);
      }
   }

   @ObfuscatedSignature(
      signature = "(IIIILer;JLer;Ler;)V"
   )
   public void copy$newGroundItemPile(int var1, int var2, int var3, int var4, Entity var5, long var6, Entity var8, Entity var9) {
      TileItemPile var10 = new TileItemPile();
      var10.first = var5;
      var10.x = var2 * 128 + 64;
      var10.y = var3 * 128 + 64;
      var10.tileHeight = var4;
      var10.tag = var6;
      var10.second = var8;
      var10.third = var9;
      int var11 = 0;
      Tile var12 = this.tiles[var1][var2][var3];
      if(var12 != null) {
         for(int var13 = 0; var13 < var12.gameObjectsCount; ++var13) {
            if((var12.gameObjects[var13].flags & 256) == 256 && var12.gameObjects[var13].entity instanceof Model) {
               Model var14 = (Model)var12.gameObjects[var13].entity;
               var14.method2359();
               if(var14.height > var11) {
                  var11 = var14.height;
               }
            }
         }
      }

      var10.height = var11;
      if(this.tiles[var1][var2][var3] == null) {
         this.tiles[var1][var2][var3] = new Tile(var1, var2, var3);
      }

      this.tiles[var1][var2][var3].tileItemPile = var10;
      this.tiles[var1][var2][var3].itemLayerChanged(-1);
   }

   @ObfuscatedSignature(
      signature = "(IIIILer;JI)V"
   )
   public void copy$newFloorDecoration(int var1, int var2, int var3, int var4, Entity var5, long var6, int var8) {
      if(var5 != null) {
         FloorDecoration var9 = new FloorDecoration();
         var9.entity = var5;
         var9.x = var2 * 128 + 64;
         var9.y = var3 * 128 + 64;
         var9.tileHeight = var4;
         var9.tag = var6;
         var9.flags = var8;
         if(this.tiles[var1][var2][var3] == null) {
            this.tiles[var1][var2][var3] = new Tile(var1, var2, var3);
         }

         this.tiles[var1][var2][var3].floorDecoration = var9;
         this.tiles[var1][var2][var3].groundObjectChanged(-1);
      }
   }

   @ObfuscatedSignature(
      signature = "(IIIILer;Ler;IIJI)V"
   )
   public void copy$newBoundaryObject(int var1, int var2, int var3, int var4, Entity var5, Entity var6, int var7, int var8, long var9, int var11) {
      if(var5 != null || var6 != null) {
         BoundaryObject var12 = new BoundaryObject();
         var12.tag = var9;
         var12.flags = var11;
         var12.x = var2 * 128 + 64;
         var12.y = var3 * 128 + 64;
         var12.tileHeight = var4;
         var12.entity1 = var5;
         var12.entity2 = var6;
         var12.orientationA = var7;
         var12.orientationB = var8;

         for(int var13 = var1; var13 >= 0; --var13) {
            if(this.tiles[var13][var2][var3] == null) {
               this.tiles[var13][var2][var3] = new Tile(var13, var2, var3);
            }
         }

         this.tiles[var1][var2][var3].boundaryObject = var12;
         this.tiles[var1][var2][var3].wallObjectChanged(-1);
      }
   }

   @ObfuscatedSignature(
      signature = "(IIIILer;Ler;IIIIJI)V"
   )
   public void copy$newWallDecoration(int var1, int var2, int var3, int var4, Entity var5, Entity var6, int var7, int var8, int var9, int var10, long var11, int var13) {
      if(var5 != null) {
         WallDecoration var14 = new WallDecoration();
         var14.tag = var11;
         var14.flags = var13;
         var14.x = var2 * 128 + 64;
         var14.y = var3 * 128 + 64;
         var14.tileHeight = var4;
         var14.entity1 = var5;
         var14.entity2 = var6;
         var14.orientation = var7;
         var14.orientation2 = var8;
         var14.xOffset = var9;
         var14.yOffset = var10;

         for(int var15 = var1; var15 >= 0; --var15) {
            if(this.tiles[var15][var2][var3] == null) {
               this.tiles[var15][var2][var3] = new Tile(var15, var2, var3);
            }
         }

         this.tiles[var1][var2][var3].wallDecoration = var14;
         this.tiles[var1][var2][var3].decorativeObjectChanged(-1);
      }
   }

   public RSTile[][][] getTiles() {
      return this.tiles;
   }

   @ObfuscatedSignature(
      signature = "(Ldd;IIIIII)V"
   )
   public void copy$drawTileOverlay(TileModel var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = var1.vertexX.length;

      int var9;
      int var10;
      int var11;
      int var12;
      int var13;
      for(var9 = 0; var9 < var8; ++var9) {
         var10 = var1.vertexX[var9] - Scene_cameraX;
         var11 = var1.vertexY[var9] - Scene_cameraY;
         var12 = var1.vertexZ[var9] - Scene_cameraZ;
         var13 = var12 * var4 + var5 * var10 >> 16;
         var12 = var5 * var12 - var10 * var4 >> 16;
         var10 = var13;
         var13 = var3 * var11 - var12 * var2 >> 16;
         var12 = var11 * var2 + var3 * var12 >> 16;
         if(var12 < 50) {
            return;
         }

         if(var1.triangleTextureId != null) {
            TileModel.field1345[var9] = var10;
            TileModel.field1346[var9] = var13;
            TileModel.field1348[var9] = var12;
         }

         TileModel.field1328[var9] = var10 * Rasterizer3D.Rasterizer3D_zoom / var12 + Rasterizer3D.Rasterizer3D_clipMidX;
         TileModel.field1344[var9] = var13 * Rasterizer3D.Rasterizer3D_zoom / var12 + Rasterizer3D.Rasterizer3D_clipMidY;
      }

      Rasterizer3D.Rasterizer3D_alpha = 0;
      var8 = var1.faceX.length;

      for(var9 = 0; var9 < var8; ++var9) {
         var10 = var1.faceX[var9];
         var11 = var1.faceY[var9];
         var12 = var1.faceZ[var9];
         var13 = TileModel.field1328[var10];
         int var14 = TileModel.field1328[var11];
         int var15 = TileModel.field1328[var12];
         int var16 = TileModel.field1344[var10];
         int var17 = TileModel.field1344[var11];
         int var18 = TileModel.field1344[var12];
         if((var13 - var14) * (var18 - var17) - (var16 - var17) * (var15 - var14) > 0) {
            Rasterizer3D.field1727 = false;
            if(var13 < 0 || var14 < 0 || var15 < 0 || var13 > Rasterizer3D.Rasterizer3D_clipWidth || var14 > Rasterizer3D.Rasterizer3D_clipWidth || var15 > Rasterizer3D.Rasterizer3D_clipWidth) {
               Rasterizer3D.field1727 = true;
            }

            if(checkClick && method3252(Scene_selectedScreenX, Scene_selectedScreenY, var16, var17, var18, var13, var14, var15)) {
               Scene_selectedX = var6;
               Scene_selectedY = var7;
            }

            if(var1.triangleTextureId != null && var1.triangleTextureId[var9] != -1) {
               if(!Scene_isLowDetail) {
                  if(var1.isFlat) {
                     Rasterizer3D.method2947(var16, var17, var18, var13, var14, var15, var1.triangleColorA[var9], var1.triangleColorB[var9], var1.triangleColorC[var9], TileModel.field1345[0], TileModel.field1345[1], TileModel.field1345[3], TileModel.field1346[0], TileModel.field1346[1], TileModel.field1346[3], TileModel.field1348[0], TileModel.field1348[1], TileModel.field1348[3], var1.triangleTextureId[var9]);
                  } else {
                     Rasterizer3D.method2947(var16, var17, var18, var13, var14, var15, var1.triangleColorA[var9], var1.triangleColorB[var9], var1.triangleColorC[var9], TileModel.field1345[var10], TileModel.field1345[var11], TileModel.field1345[var12], TileModel.field1346[var10], TileModel.field1346[var11], TileModel.field1346[var12], TileModel.field1348[var10], TileModel.field1348[var11], TileModel.field1348[var12], var1.triangleTextureId[var9]);
                  }
               } else {
                  int var19 = Rasterizer3D.Rasterizer3D_textureLoader.vmethod2926(var1.triangleTextureId[var9]);
                  Rasterizer3D.method2953(var16, var17, var18, var13, var14, var15, method3158(var19, var1.triangleColorA[var9]), method3158(var19, var1.triangleColorB[var9]), method3158(var19, var1.triangleColorC[var9]));
               }
            } else if(var1.triangleColorA[var9] != 12345678) {
               Rasterizer3D.method2953(var16, var17, var18, var13, var14, var15, var1.triangleColorA[var9], var1.triangleColorB[var9], var1.triangleColorC[var9]);
            }
         }
      }

   }

   @ObfuscatedSignature(
      signature = "(Lef;IIIIIII)V"
   )
   public void copy$drawTileUnderlay(TilePaint var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      int var9;
      int var10 = var9 = (var7 << 7) - Scene_cameraX;
      int var11;
      int var12 = var11 = (var8 << 7) - Scene_cameraZ;
      int var13;
      int var14 = var13 = var10 + 128;
      int var15;
      int var16 = var15 = var12 + 128;
      int var17 = this.tileHeights[var2][var7][var8] - Scene_cameraY;
      int var18 = this.tileHeights[var2][var7 + 1][var8] - Scene_cameraY;
      int var19 = this.tileHeights[var2][var7 + 1][var8 + 1] - Scene_cameraY;
      int var20 = this.tileHeights[var2][var7][var8 + 1] - Scene_cameraY;
      int var21 = var10 * var6 + var5 * var12 >> 16;
      var12 = var12 * var6 - var5 * var10 >> 16;
      var10 = var21;
      var21 = var17 * var4 - var3 * var12 >> 16;
      var12 = var12 * var4 + var3 * var17 >> 16;
      var17 = var21;
      if(var12 >= 50) {
         var21 = var14 * var6 + var5 * var11 >> 16;
         var11 = var11 * var6 - var5 * var14 >> 16;
         var14 = var21;
         var21 = var18 * var4 - var3 * var11 >> 16;
         var11 = var11 * var4 + var3 * var18 >> 16;
         var18 = var21;
         if(var11 >= 50) {
            var21 = var13 * var6 + var5 * var16 >> 16;
            var16 = var16 * var6 - var5 * var13 >> 16;
            var13 = var21;
            var21 = var19 * var4 - var3 * var16 >> 16;
            var16 = var16 * var4 + var3 * var19 >> 16;
            var19 = var21;
            if(var16 >= 50) {
               var21 = var9 * var6 + var5 * var15 >> 16;
               var15 = var15 * var6 - var5 * var9 >> 16;
               var9 = var21;
               var21 = var20 * var4 - var3 * var15 >> 16;
               var15 = var15 * var4 + var3 * var20 >> 16;
               if(var15 >= 50) {
                  int var22 = var10 * Rasterizer3D.Rasterizer3D_zoom / var12 + Rasterizer3D.Rasterizer3D_clipMidX;
                  int var23 = var17 * Rasterizer3D.Rasterizer3D_zoom / var12 + Rasterizer3D.Rasterizer3D_clipMidY;
                  int var24 = var14 * Rasterizer3D.Rasterizer3D_zoom / var11 + Rasterizer3D.Rasterizer3D_clipMidX;
                  int var25 = var18 * Rasterizer3D.Rasterizer3D_zoom / var11 + Rasterizer3D.Rasterizer3D_clipMidY;
                  int var26 = var13 * Rasterizer3D.Rasterizer3D_zoom / var16 + Rasterizer3D.Rasterizer3D_clipMidX;
                  int var27 = var19 * Rasterizer3D.Rasterizer3D_zoom / var16 + Rasterizer3D.Rasterizer3D_clipMidY;
                  int var28 = var9 * Rasterizer3D.Rasterizer3D_zoom / var15 + Rasterizer3D.Rasterizer3D_clipMidX;
                  int var29 = var21 * Rasterizer3D.Rasterizer3D_zoom / var15 + Rasterizer3D.Rasterizer3D_clipMidY;
                  Rasterizer3D.Rasterizer3D_alpha = 0;
                  int var30;
                  if((var26 - var28) * (var25 - var29) - (var27 - var29) * (var24 - var28) > 0) {
                     Rasterizer3D.field1727 = false;
                     if(var26 < 0 || var28 < 0 || var24 < 0 || var26 > Rasterizer3D.Rasterizer3D_clipWidth || var28 > Rasterizer3D.Rasterizer3D_clipWidth || var24 > Rasterizer3D.Rasterizer3D_clipWidth) {
                        Rasterizer3D.field1727 = true;
                     }

                     if(checkClick && method3252(Scene_selectedScreenX, Scene_selectedScreenY, var27, var29, var25, var26, var28, var24)) {
                        Scene_selectedX = var7;
                        Scene_selectedY = var8;
                     }

                     if(var1.texture == -1) {
                        if(var1.neColor != 12345678) {
                           Rasterizer3D.method2953(var27, var29, var25, var26, var28, var24, var1.neColor, var1.nwColor, var1.seColor);
                        }
                     } else if(!Scene_isLowDetail) {
                        if(var1.isFlat) {
                           Rasterizer3D.method2947(var27, var29, var25, var26, var28, var24, var1.neColor, var1.nwColor, var1.seColor, var10, var14, var9, var17, var18, var21, var12, var11, var15, var1.texture);
                        } else {
                           Rasterizer3D.method2947(var27, var29, var25, var26, var28, var24, var1.neColor, var1.nwColor, var1.seColor, var13, var9, var14, var19, var21, var18, var16, var15, var11, var1.texture);
                        }
                     } else {
                        var30 = Rasterizer3D.Rasterizer3D_textureLoader.vmethod2926(var1.texture);
                        Rasterizer3D.method2953(var27, var29, var25, var26, var28, var24, method3158(var30, var1.neColor), method3158(var30, var1.nwColor), method3158(var30, var1.seColor));
                     }
                  }

                  if((var22 - var24) * (var29 - var25) - (var23 - var25) * (var28 - var24) > 0) {
                     Rasterizer3D.field1727 = false;
                     if(var22 < 0 || var24 < 0 || var28 < 0 || var22 > Rasterizer3D.Rasterizer3D_clipWidth || var24 > Rasterizer3D.Rasterizer3D_clipWidth || var28 > Rasterizer3D.Rasterizer3D_clipWidth) {
                        Rasterizer3D.field1727 = true;
                     }

                     if(checkClick && method3252(Scene_selectedScreenX, Scene_selectedScreenY, var23, var25, var29, var22, var24, var28)) {
                        Scene_selectedX = var7;
                        Scene_selectedY = var8;
                     }

                     if(var1.texture == -1) {
                        if(var1.swColor != 12345678) {
                           Rasterizer3D.method2953(var23, var25, var29, var22, var24, var28, var1.swColor, var1.seColor, var1.nwColor);
                        }
                     } else if(!Scene_isLowDetail) {
                        Rasterizer3D.method2947(var23, var25, var29, var22, var24, var28, var1.swColor, var1.seColor, var1.nwColor, var10, var14, var9, var17, var18, var21, var12, var11, var15, var1.texture);
                     } else {
                        var30 = Rasterizer3D.Rasterizer3D_textureLoader.vmethod2926(var1.texture);
                        Rasterizer3D.method2953(var23, var25, var29, var22, var24, var28, method3158(var30, var1.swColor), method3158(var30, var1.seColor), method3158(var30, var1.nwColor));
                     }
                  }

               }
            }
         }
      }
   }

   @ObfuscatedSignature(
      signature = "(IIIIIIIILer;IZJI)Z"
   )
   public boolean copy$newGameObject(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, Entity var9, int var10, boolean var11, long var12, int var14) {
      int var16;
      for(int var15 = var2; var15 < var2 + var4; ++var15) {
         for(var16 = var3; var16 < var3 + var5; ++var16) {
            if(var15 < 0 || var16 < 0 || var15 >= this.xSize || var16 >= this.ySize) {
               return false;
            }

            Tile var22 = this.tiles[var1][var15][var16];
            if(var22 != null && var22.gameObjectsCount >= 5) {
               return false;
            }
         }
      }

      GameObject var21 = new GameObject();
      var21.tag = var12;
      var21.flags = var14;
      var21.plane = var1;
      var21.centerX = var6;
      var21.centerY = var7;
      var21.height = var8;
      var21.entity = var9;
      var21.orientation = var10;
      var21.startX = var2;
      var21.startY = var3;
      var21.endX = var2 + var4 - 1;
      var21.endY = var3 + var5 - 1;

      for(var16 = var2; var16 < var2 + var4; ++var16) {
         for(int var17 = var3; var17 < var3 + var5; ++var17) {
            int var18 = 0;
            if(var16 > var2) {
               ++var18;
            }

            if(var16 < var2 + var4 - 1) {
               var18 += 4;
            }

            if(var17 > var3) {
               var18 += 8;
            }

            if(var17 < var3 + var5 - 1) {
               var18 += 2;
            }

            for(int var19 = var1; var19 >= 0; --var19) {
               if(this.tiles[var19][var16][var17] == null) {
                  this.tiles[var19][var16][var17] = new Tile(var19, var16, var17);
               }
            }

            Tile var23 = this.tiles[var1][var16][var17];
            var23.gameObjects[var23.gameObjectsCount] = var21;
            var23.gameObjectsChanged(var23.gameObjectsCount);
            var23.gameObjectEdgeMasks[var23.gameObjectsCount] = var18;
            var23.gameObjectsEdgeMask |= var18;
            ++var23.gameObjectsCount;
         }
      }

      if(var11) {
         this.tempGameObjects[this.tempGameObjectsCount++] = var21;
      }

      return true;
   }

   public int[][][] getTileHeights() {
      return this.tileHeights;
   }

   public int getMaxX() {
      return this.xSize;
   }

   public int getMaxY() {
      return this.planes;
   }

   public int getMaxZ() {
      return this.ySize;
   }

   public int getMinLevel() {
      return this.minPlane;
   }

   public void updateOccluders() {
      this.method3160();
   }

   public void draw(net.runelite.api.Tile var1, boolean var2) {
      this.method3221((Tile)var1, var2);
   }

   public int getDrawDistance() {
      return rl$drawDistance;
   }

   public void setDrawDistance(int var1) {
      rl$drawDistance = var1;
   }

   public void addItem(int var1, int var2, WorldPoint var3) {
      int var4 = var3.getX() - ViewportMouse.client.getBaseX();
      int var5 = var3.getY() - ViewportMouse.client.getBaseY();
      int var6 = var3.getPlane();
      if(var4 >= 0 && var5 >= 0 && var4 < 104 && var5 < 104) {
         RSTileItem var7 = ViewportMouse.client.newTileItem();
         var7.setId(var1);
         var7.setQuantity(var2);
         RSNodeDeque[][][] var8 = ViewportMouse.client.getGroundItemDeque();
         if(var8[var6][var4][var5] == null) {
            var8[var6][var4][var5] = ViewportMouse.client.newNodeDeque();
         }

         var8[var6][var4][var5].addFirst(var7);
         if(var6 == ViewportMouse.client.getPlane()) {
            ViewportMouse.client.updateItemPile(var4, var5);
         }

      }
   }

   public void removeItem(int var1, int var2, WorldPoint var3) {
      int var4 = var3.getX() - ViewportMouse.client.getBaseX();
      int var5 = var3.getY() - ViewportMouse.client.getBaseY();
      int var6 = var3.getPlane();
      if(var4 >= 0 && var5 >= 0 && var4 < 104 && var5 < 104) {
         RSNodeDeque var7 = ViewportMouse.client.getGroundItemDeque()[var6][var4][var5];
         if(var7 != null) {
            for(RSTileItem var8 = (RSTileItem)var7.last(); var8 != null; var8 = (RSTileItem)var7.previous()) {
               if(var8.getId() == var1 && var2 == 1) {
                  var8.unlink();
                  break;
               }
            }

            if(var7.last() == null) {
               ViewportMouse.client.getGroundItemDeque()[var6][var4][var5] = null;
            }

            ViewportMouse.client.updateItemPile(var4, var5);
         }
      }
   }

   public RSGameObject[] getObjects() {
      return this.tempGameObjects;
   }

   public void newGroundItemPile(int var1, int var2, int var3, int var4, RSEntity var5, long var6, RSEntity var8, RSEntity var9) {
      this.method3233(var1, var2, var3, var4, (Entity)var5, var6, (Entity)var8, (Entity)var9);
   }

   public void drawTile(int[] var1, int var2, int var3, int var4, int var5, int var6) {
      this.method3148(var1, var2, var3, var4, var5, var6);
   }

   @ObfuscatedName("d")
   public void method3134(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      if(var4 != null) {
         var4.floorDecoration = null;
         var4.groundObjectChanged(-1);
      }
   }

   @ObfuscatedName("e")
   public void method3128() {
      for(int var1 = 0; var1 < this.tempGameObjectsCount; ++var1) {
         GameObject var2 = this.tempGameObjects[var1];
         this.method3225(var2);
         this.tempGameObjects[var1] = null;
      }

      this.tempGameObjectsCount = 0;
   }

   @ObfuscatedName("f")
   public void method3230(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      if(var4 != null) {
         var4.wallDecoration = null;
         var4.decorativeObjectChanged(-1);
      }
   }

   @ObfuscatedName("h")
   public void method3195(int var1, int var2, int var3, int var4) {
      Tile var5 = this.tiles[var1][var2][var3];
      if(var5 != null) {
         WallDecoration var6 = var5.wallDecoration;
         if(var6 != null) {
            var6.xOffset = var4 * var6.xOffset / 16;
            var6.yOffset = var4 * var6.yOffset / 16;
         }
      }
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(IIIILer;Ler;IIIIJI)V"
   )
   public void method3131(int var1, int var2, int var3, int var4, Entity var5, Entity var6, int var7, int var8, int var9, int var10, long var11, int var13) {
      this.copy$newWallDecoration(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var13);
      RSTile var14 = this.getTiles()[var1][var2][var3];
      if(var14 != null) {
         RSWallDecoration var15 = (RSWallDecoration)var14.getDecorativeObject();
         if(var15 != null) {
            var15.setPlane(var1);
         }
      }

   }

   @ObfuscatedName("j")
   public void method3191(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      if(var4 != null) {
         for(int var5 = 0; var5 < var4.gameObjectsCount; ++var5) {
            GameObject var6 = var4.gameObjects[var5];
            if(WorldMapDecoration.method5194(var6.tag) && var2 == var6.startX && var3 == var6.startY) {
               this.method3225(var6);
               return;
            }
         }

      }
   }

   @ObfuscatedName("k")
   @ObfuscatedSignature(
      signature = "(III)Leo;"
   )
   public BoundaryObject method3136(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      return var4 == null?null:var4.boundaryObject;
   }

   @ObfuscatedName("l")
   public void method3178(int var1, int var2, int var3) {
      Tile var4 = this.tiles[var1][var2][var3];
      if(var4 != null) {
         var4.tileItemPile = null;
         var4.itemLayerChanged(-1);
      }
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(IIIILer;JLer;Ler;)V"
   )
   public void method3233(int var1, int var2, int var3, int var4, Entity var5, long var6, Entity var8, Entity var9) {
      this.copy$newGroundItemPile(var1, var2, var3, var4, var5, var6, var8, var9);
      RSTile var10 = this.getTiles()[var1][var2][var3];
      if(var10 != null) {
         RSTileItemPile var11 = (RSTileItemPile)var10.getItemLayer();
         if(var11 != null) {
            var11.setPlane(var1);
         }
      }

   }

   @ObfuscatedName("u")
   public static void method3117(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      Occluder var8 = new Occluder();
      var8.minTileX = var2 / 128;
      var8.maxTileX = var3 / 128;
      var8.minTileY = var4 / 128;
      var8.maxTileY = var5 / 128;
      var8.type = var1;
      var8.minX = var2;
      var8.maxX = var3;
      var8.minZ = var4;
      var8.maxZ = var5;
      var8.minY = var6;
      var8.maxY = var7;
      Scene_planeOccluders[var0][Scene_planeOccluderCounts[var0]++] = var8;
   }

   @ObfuscatedName("af")
   static final int method3158(int var0, int var1) {
      var1 = (var0 & 127) * var1 >> 7;
      if(var1 < 2) {
         var1 = 2;
      } else if(var1 > 126) {
         var1 = 126;
      }

      return (var0 & 65408) + var1;
   }

   @ObfuscatedName("ah")
   public static void method3154() {
      Scene_selectedX = -1;
      viewportWalking = false;
   }

   @ObfuscatedName("ak")
   public static void method3149(int[] var0, int var1, int var2, int var3, int var4) {
      Scene_viewportXMin = 0;
      Scene_viewportYMin = 0;
      Scene_viewportXMax = var3;
      Scene_viewportYMax = var4;
      Scene_viewportXCenter = var3 / 2;
      Scene_viewportYCenter = var4 / 2;
      boolean[][][][] var5 = new boolean[var0.length][32][53][53];

      int var6;
      int var7;
      int var8;
      int var9;
      int var11;
      int var12;
      for(var6 = 128; var6 <= 383; var6 += 32) {
         for(var7 = 0; var7 < 2048; var7 += 64) {
            Scene_cameraPitchSine = Rasterizer3D.Rasterizer3D_sine[var6];
            Scene_cameraPitchCosine = Rasterizer3D.Rasterizer3D_cosine[var6];
            Scene_cameraYawSine = Rasterizer3D.Rasterizer3D_sine[var7];
            Scene_cameraYawCosine = Rasterizer3D.Rasterizer3D_cosine[var7];
            var8 = (var6 - 128) / 32;
            var9 = var7 / 64;

            for(int var10 = -26; var10 < 26; ++var10) {
               for(var11 = -26; var11 < 26; ++var11) {
                  var12 = var10 * 128;
                  int var13 = var11 * 128;
                  boolean var14 = false;

                  for(int var15 = -var1; var15 <= var2; var15 += 128) {
                     if(method3121(var12, var0[var8] + var15, var13)) {
                        var14 = true;
                        break;
                     }
                  }

                  var5[var8][var9][var10 + 1 + 25][var11 + 1 + 25] = var14;
               }
            }
         }
      }

      for(var6 = 0; var6 < 8; ++var6) {
         for(var7 = 0; var7 < 32; ++var7) {
            for(var8 = -25; var8 < 25; ++var8) {
               for(var9 = -25; var9 < 25; ++var9) {
                  boolean var16 = false;

                  label76:
                  for(var11 = -1; var11 <= 1; ++var11) {
                     for(var12 = -1; var12 <= 1; ++var12) {
                        if(var5[var6][var7][var8 + var11 + 1 + 25][var9 + var12 + 1 + 25]) {
                           var16 = true;
                           break label76;
                        }

                        if(var5[var6][(var7 + 1) % 31][var8 + var11 + 1 + 25][var9 + var12 + 1 + 25]) {
                           var16 = true;
                           break label76;
                        }

                        if(var5[var6 + 1][var7][var8 + var11 + 1 + 25][var9 + var12 + 1 + 25]) {
                           var16 = true;
                           break label76;
                        }

                        if(var5[var6 + 1][(var7 + 1) % 31][var8 + var11 + 1 + 25][var9 + var12 + 1 + 25]) {
                           var16 = true;
                           break label76;
                        }
                     }
                  }

                  visibilityMap[var6][var7][var8 + 25][var9 + 25] = var16;
               }
            }
         }
      }

   }

   @ObfuscatedName("aq")
   static boolean method3121(int var0, int var1, int var2) {
      int var3 = var0 * Scene_cameraYawCosine + var2 * Scene_cameraYawSine >> 16;
      int var4 = var2 * Scene_cameraYawCosine - var0 * Scene_cameraYawSine >> 16;
      int var5 = var4 * Scene_cameraPitchCosine + Scene_cameraPitchSine * var1 >> 16;
      int var6 = Scene_cameraPitchCosine * var1 - var4 * Scene_cameraPitchSine >> 16;
      if(var5 >= 50 && var5 <= 3500) {
         int var7 = var3 * 128 / var5 + Scene_viewportXCenter;
         int var8 = var6 * 128 / var5 + Scene_viewportYCenter;
         return var7 >= Scene_viewportXMin && var7 <= Scene_viewportXMax && var8 >= Scene_viewportYMin && var8 <= Scene_viewportYMax;
      } else {
         return false;
      }
   }

   @ObfuscatedName("at")
   static boolean method3252(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if(var1 < var2 && var1 < var3 && var1 < var4) {
         return false;
      } else if(var1 > var2 && var1 > var3 && var1 > var4) {
         return false;
      } else if(var0 < var5 && var0 < var6 && var0 < var7) {
         return false;
      } else if(var0 > var5 && var0 > var6 && var0 > var7) {
         return false;
      } else {
         int var8 = (var1 - var2) * (var6 - var5) - (var0 - var5) * (var3 - var2);
         int var9 = (var7 - var6) * (var1 - var3) - (var0 - var6) * (var4 - var3);
         int var10 = (var5 - var7) * (var1 - var4) - (var2 - var4) * (var0 - var7);
         return var8 == 0?(var9 != 0?(var9 < 0?var10 <= 0:var10 >= 0):true):(var8 < 0?var9 <= 0 && var10 <= 0:var9 >= 0 && var10 >= 0);
      }
   }

   @ObfuscatedName("av")
   public static boolean method3153() {
      return viewportWalking && Scene_selectedX != -1;
   }

   public static boolean shouldDraw(Object var0, boolean var1) {
      if(!Client.isHidingEntities) {
         return true;
      } else if(!(var0 instanceof RSPlayer)) {
         if(var0 instanceof RSNPC) {
            RSNPC var8 = (RSNPC)var0;
            if(!Client.hideAttackers && var8.getInteracting() == ViewportMouse.client.getLocalPlayer()) {
               return true;
            } else if(Client.hideDeadNPCs && var8.getHealthRatio() == 0) {
               return false;
            } else if(var8.getName() != null && ((Integer)Client.hiddenNpcsName.getOrDefault(Text.standardize(var8.getName().toLowerCase()), Integer.valueOf(0))).intValue() > 0) {
               return false;
            } else if(var8.getName() != null && var8.getHealthRatio() == 0 && ((Integer)Client.hiddenNpcsDeath.getOrDefault(Text.standardize(var8.getName().toLowerCase()), Integer.valueOf(0))).intValue() > 0) {
               return false;
            } else {
               return var1?!Client.hideNPCs2D:!Client.hideNPCs;
            }
         } else if(var0 instanceof RSProjectile) {
            return !Client.hideProjectiles;
         } else {
            return true;
         }
      } else {
         boolean var2 = var1?Client.hideLocalPlayer2D:Client.hideLocalPlayer;
         boolean var3 = var1?Client.hidePlayers2D:Client.hidePlayers;
         boolean var4 = var0 == ViewportMouse.client.getLocalPlayer();
         RSPlayer var5 = (RSPlayer)var0;
         Iterator var6 = Client.hideSpecificPlayers.iterator();

         while(var6.hasNext()) {
            String var7 = (String)var6.next();
            if(var7 != null && !var7.equals("") && var5.getName() != null && var5.getName().equalsIgnoreCase(var7)) {
               return false;
            }
         }

         if(var4) {
            if(!var2) {
               return true;
            }
         } else if(!var3) {
            return true;
         }

         if(!Client.hideAttackers && var5.getInteracting() == ViewportMouse.client.getLocalPlayer()) {
            return true;
         } else if(var5.getName() == null) {
            return false;
         } else {
            return !Client.hideFriends && var5.isFriend() || !var4 && !Client.hideClanMates && var5.isClanMember();
         }
      }
   }

   public static void setTargetTile(int var0, int var1) {
      ViewportMouse.client.setSelectedSceneTileX(var0);
      ViewportMouse.client.setSelectedSceneTileY(var1);
   }

   private static void rl$$clinit() {
      tmpX = new int[6];
      tmpY = new int[6];
   }

   @ObfuscatedSignature(
      signature = "(Lby;IIIIII)V"
   )
   public static final void copy$drawActor2d(Actor var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      if(var0 != null && var0.vmethod1611()) {
         if(var0 instanceof NPC) {
            if(var6 <= 962629039) {
               return;
            }

            NPCDefinition var7 = ((NPC)var0).definition;
            if(var7.transforms != null) {
               var7 = var7.method4407();
            }

            if(var7 == null) {
               return;
            }
         }

         int var76 = Players.Players_count;
         int[] var8 = Players.Players_indices;
         byte var9 = 0;
         if(var1 < var76 && var0.playerCycle == Client.cycle && GrandExchangeOfferUnitPriceComparator.method1466((Player)var0)) {
            Player var10 = (Player)var0;
            if(var1 < var76) {
               World.method1253(var0, var0.defaultHeight + 15);
               AbstractFont var11 = (AbstractFont)Client.fontsMap.get(FontName.FontName_plain12);
               byte var12 = 9;
               var11.method5332(var10.username.method5001(), var2 + Client.viewportTempX, var3 + Client.viewportTempY - var12, 16777215, 0);
               var9 = 18;
            }
         }

         int var77 = -2;
         int var16;
         int var17;
         int var23;
         int var24;
         if(!var0.healthBars.method5026()) {
            if(var6 <= 962629039) {
               return;
            }

            World.method1253(var0, var0.defaultHeight + 15);

            for(HealthBar var90 = (HealthBar)var0.healthBars.method5044(); var90 != null; var90 = (HealthBar)var0.healthBars.method5024()) {
               HealthBarUpdate var78 = var90.method2253(Client.cycle);
               if(var78 == null) {
                  if(var90.method2248()) {
                     var90.method3497();
                  }
               } else {
                  HealthBarDefinition var13 = var90.definition;
                  Sprite var14 = var13.method4485();
                  Sprite var15 = var13.method4471();
                  var17 = 0;
                  if(var14 != null && var15 != null) {
                     if(var6 <= 962629039) {
                        return;
                     }

                     if(var13.widthPadding * 2 < var15.subWidth) {
                        var17 = var13.widthPadding;
                     }

                     var16 = var15.subWidth - var17 * 2;
                  } else {
                     var16 = var13.width;
                  }

                  int var18 = 255;
                  boolean var19 = true;
                  int var20 = Client.cycle - var78.cycle;
                  int var21 = var16 * var78.health2 / var13.width;
                  int var22;
                  int var95;
                  if(var78.cycleOffset > var20) {
                     var22 = var13.int4 == 0?0:var13.int4 * (var20 / var13.int4);
                     var23 = var16 * var78.health / var13.width;
                     var95 = var22 * (var21 - var23) / var78.cycleOffset + var23;
                  } else {
                     var95 = var21;
                     var22 = var13.int5 + var78.cycleOffset - var20;
                     if(var13.int3 >= 0) {
                        var18 = (var22 << 8) / (var13.int5 - var13.int3);
                     }
                  }

                  if(var78.health2 > 0 && var95 < 1) {
                     if(var6 <= 962629039) {
                        return;
                     }

                     var95 = 1;
                  }

                  if(var14 != null && var15 != null) {
                     if(var6 <= 962629039) {
                        return;
                     }

                     if(var16 == var95) {
                        var95 += var17 * 2;
                     } else {
                        var95 += var17;
                     }

                     var22 = var14.subHeight;
                     var77 += var22;
                     var23 = var2 + Client.viewportTempX - (var16 >> 1);
                     var24 = var3 + Client.viewportTempY - var77;
                     var23 -= var17;
                     if(var18 >= 0 && var18 < 255) {
                        var14.method6114(var23, var24, var18);
                        Rasterizer2D.method6478(var23, var24, var23 + var95, var24 + var22);
                        var15.method6114(var23, var24, var18);
                     } else {
                        var14.method6159(var23, var24);
                        Rasterizer2D.method6478(var23, var24, var95 + var23, var24 + var22);
                        var15.method6159(var23, var24);
                     }

                     Rasterizer2D.method6474(var2, var3, var2 + var4, var3 + var5);
                     var77 += 2;
                  } else {
                     var77 += 5;
                     if(Client.viewportTempX > -1) {
                        var22 = var2 + Client.viewportTempX - (var16 >> 1);
                        var23 = var3 + Client.viewportTempY - var77;
                        Rasterizer2D.method6469(var22, var23, var95, 5, 65280);
                        Rasterizer2D.method6469(var22 + var95, var23, var16 - var95, 5, 16711680);
                     }

                     var77 += 2;
                  }
               }
            }
         }

         if(var77 == -2) {
            var77 += 7;
         }

         var77 += var9;
         if(var1 < var76) {
            Player var91 = (Player)var0;
            if(var91.isHidden) {
               return;
            }

            if(var91.headIconPk != -1 || var91.headIconPrayer != -1) {
               World.method1253(var0, var0.defaultHeight + 15);
               if(Client.viewportTempX > -1) {
                  if(var6 <= 962629039) {
                     return;
                  }

                  if(var91.headIconPk != -1) {
                     var77 += 25;
                     StructDefinition.headIconPkSprites[var91.headIconPk].method6159(var2 + Client.viewportTempX - 12, var3 + Client.viewportTempY - var77);
                  }

                  if(var91.headIconPrayer != -1) {
                     var77 += 25;
                     WorldMapRegion.headIconPrayerSprites[var91.headIconPrayer].method6159(var2 + Client.viewportTempX - 12, var3 + Client.viewportTempY - var77);
                  }
               }
            }

            if(var1 >= 0 && Client.hintArrowType == 10) {
               if(var6 <= 962629039) {
                  return;
               }

               if(var8[var1] == Client.hintArrowPlayerIndex) {
                  World.method1253(var0, var0.defaultHeight + 15);
                  if(Client.viewportTempX > -1) {
                     var77 += ReflectionCheck.headIconHintSprites[1].subHeight;
                     ReflectionCheck.headIconHintSprites[1].method6159(var2 + Client.viewportTempX - 12, var3 + Client.viewportTempY - var77);
                  }
               }
            }
         } else {
            NPCDefinition var92 = ((NPC)var0).definition;
            if(var92.transforms != null) {
               var92 = var92.method4407();
            }

            if(var92.headIconPrayer >= 0 && var92.headIconPrayer < WorldMapRegion.headIconPrayerSprites.length) {
               World.method1253(var0, var0.defaultHeight + 15);
               if(Client.viewportTempX > -1) {
                  WorldMapRegion.headIconPrayerSprites[var92.headIconPrayer].method6159(var2 + Client.viewportTempX - 12, var3 + Client.viewportTempY - 30);
               }
            }

            if(Client.hintArrowType == 1 && Client.npcIndices[var1 - var76] == Client.hintArrowNpcIndex && Client.cycle % 20 < 10) {
               if(var6 <= 962629039) {
                  return;
               }

               World.method1253(var0, var0.defaultHeight + 15);
               if(Client.viewportTempX > -1) {
                  if(var6 <= 962629039) {
                     return;
                  }

                  ReflectionCheck.headIconHintSprites[0].method6159(var2 + Client.viewportTempX - 12, var3 + Client.viewportTempY - 28);
               }
            }
         }

         if(var0.overheadText != null) {
            label832: {
               if(var6 <= 962629039) {
                  return;
               }

               if(var1 < var76) {
                  if(var6 <= 962629039) {
                     return;
                  }

                  if(var0.field682) {
                     break label832;
                  }

                  if(Client.publicChatMode != 4) {
                     if(var6 <= 962629039) {
                        return;
                     }

                     if(var0.isAutoChatting || Client.publicChatMode != 0 && Client.publicChatMode != 3 && (Client.publicChatMode != 1 || !((Player)var0).method1090())) {
                        break label832;
                     }
                  }
               }

               World.method1253(var0, var0.defaultHeight);
               if(Client.viewportTempX > -1 && Client.overheadTextCount < Client.overheadTextLimit) {
                  Client.overheadTextXOffsets[Client.overheadTextCount] = class170.fontBold12.method5324(var0.overheadText) / 2;
                  Client.overheadTextAscents[Client.overheadTextCount] = class170.fontBold12.ascent;
                  Client.overheadTextXs[Client.overheadTextCount] = Client.viewportTempX;
                  Client.overheadTextYs[Client.overheadTextCount] = Client.viewportTempY;
                  Client.overheadTextColors[Client.overheadTextCount] = var0.overheadTextColor;
                  Client.overheadTextEffects[Client.overheadTextCount] = var0.overheadTextEffect;
                  Client.overheadTextCyclesRemaining[Client.overheadTextCount] = var0.overheadTextCyclesRemaining;
                  Client.overheadText[Client.overheadTextCount] = var0.overheadText;
                  ++Client.overheadTextCount;
               }
            }
         }

         for(int var79 = 0; var79 < 4; ++var79) {
            int var93 = var0.hitSplatCycles[var79];
            int var80 = var0.hitSplatTypes[var79];
            HitSplatDefinition var94 = null;
            int var81 = 0;
            HitSplatDefinition var82;
            if(var80 >= 0) {
               if(var93 <= Client.cycle) {
                  continue;
               }

               var17 = var0.hitSplatTypes[var79];
               var82 = (HitSplatDefinition)HitSplatDefinition.HitSplatDefinition_cached.method3032((long)var17);
               HitSplatDefinition var83;
               if(var82 != null) {
                  var83 = var82;
               } else {
                  byte[] var84 = HitSplatDefinition.HitSplatDefinition_archive.method4020(32, var17, (short)-8188);
                  var82 = new HitSplatDefinition();
                  if(var84 != null) {
                     var82.method4619(new Buffer(var84));
                  }

                  HitSplatDefinition.HitSplatDefinition_cached.method3034(var82, (long)var17);
                  var83 = var82;
               }

               var94 = var83;
               var81 = var83.field3400;
               if(var83 != null && var83.transforms != null) {
                  var94 = var83.method4645();
                  if(var94 == null) {
                     var0.hitSplatCycles[var79] = -1;
                     continue;
                  }
               }
            } else if(var93 < 0) {
               continue;
            }

            var16 = var0.hitSplatTypes2[var79];
            HitSplatDefinition var85 = null;
            HitSplatDefinition var97;
            if(var16 >= 0) {
               var97 = (HitSplatDefinition)HitSplatDefinition.HitSplatDefinition_cached.method3032((long)var16);
               if(var97 != null) {
                  if(var6 <= 962629039) {
                     return;
                  }

                  var82 = var97;
               } else {
                  byte[] var86 = HitSplatDefinition.HitSplatDefinition_archive.method4020(32, var16, (short)6914);
                  var97 = new HitSplatDefinition();
                  if(var86 != null) {
                     var97.method4619(new Buffer(var86));
                  }

                  HitSplatDefinition.HitSplatDefinition_cached.method3034(var97, (long)var16);
                  var82 = var97;
               }

               var85 = var82;
               if(var82 != null && var82.transforms != null) {
                  var85 = var82.method4645();
               }
            }

            if(var93 - var81 <= Client.cycle) {
               if(var94 == null) {
                  var0.hitSplatCycles[var79] = -1;
               } else {
                  World.method1253(var0, var0.defaultHeight / 2);
                  if(Client.viewportTempX > -1) {
                     if(var79 == 1) {
                        Client.viewportTempY -= 20;
                     }

                     if(var79 == 2) {
                        Client.viewportTempX -= 15;
                        Client.viewportTempY -= 10;
                     }

                     if(var79 == 3) {
                        Client.viewportTempX += 15;
                        Client.viewportTempY -= 10;
                     }

                     var97 = null;
                     Sprite var99 = null;
                     Sprite var87 = null;
                     Sprite var88 = null;
                     var23 = 0;
                     var24 = 0;
                     int var25 = 0;
                     int var26 = 0;
                     int var27 = 0;
                     int var28 = 0;
                     int var29 = 0;
                     int var30 = 0;
                     Sprite var31 = null;
                     Sprite var32 = null;
                     Sprite var33 = null;
                     Sprite var34 = null;
                     int var35 = 0;
                     int var36 = 0;
                     int var37 = 0;
                     int var38 = 0;
                     int var39 = 0;
                     int var40 = 0;
                     int var41 = 0;
                     int var42 = 0;
                     int var43 = 0;
                     Sprite var98 = var94.method4626();
                     int var44;
                     if(var98 != null) {
                        var23 = var98.subWidth;
                        var44 = var98.subHeight;
                        if(var44 > var43) {
                           var43 = var44;
                        }

                        var27 = var98.xOffset;
                     }

                     var99 = var94.method4623();
                     if(var99 != null) {
                        var24 = var99.subWidth;
                        var44 = var99.subHeight;
                        if(var44 > var43) {
                           var43 = var44;
                        }

                        var28 = var99.xOffset;
                     }

                     var87 = var94.method4624();
                     if(var87 != null) {
                        var25 = var87.subWidth;
                        var44 = var87.subHeight;
                        if(var44 > var43) {
                           var43 = var44;
                        }

                        var29 = var87.xOffset;
                     }

                     var88 = var94.method4648();
                     if(var88 != null) {
                        var26 = var88.subWidth;
                        var44 = var88.subHeight;
                        if(var44 > var43) {
                           var43 = var44;
                        }

                        var30 = var88.xOffset;
                     }

                     if(var85 != null) {
                        var31 = var85.method4626();
                        if(var31 != null) {
                           var35 = var31.subWidth;
                           var44 = var31.subHeight;
                           if(var44 > var43) {
                              if(var6 <= 962629039) {
                                 return;
                              }

                              var43 = var44;
                           }

                           var39 = var31.xOffset;
                        }

                        var32 = var85.method4623();
                        if(var32 != null) {
                           var36 = var32.subWidth;
                           var44 = var32.subHeight;
                           if(var44 > var43) {
                              var43 = var44;
                           }

                           var40 = var32.xOffset;
                        }

                        var33 = var85.method4624();
                        if(var33 != null) {
                           var37 = var33.subWidth;
                           var44 = var33.subHeight;
                           if(var44 > var43) {
                              if(var6 <= 962629039) {
                                 return;
                              }

                              var43 = var44;
                           }

                           var41 = var33.xOffset;
                        }

                        var34 = var85.method4648();
                        if(var34 != null) {
                           var38 = var34.subWidth;
                           var44 = var34.subHeight;
                           if(var44 > var43) {
                              var43 = var44;
                           }

                           var42 = var34.xOffset;
                        }
                     }

                     Font var89 = var94.method4622();
                     if(var89 == null) {
                        var89 = GrandExchangeOfferOwnWorldComparator.fontPlain11;
                     }

                     Font var45;
                     if(var85 != null) {
                        var45 = var85.method4622();
                        if(var45 == null) {
                           if(var6 <= 962629039) {
                              return;
                           }

                           var45 = GrandExchangeOfferOwnWorldComparator.fontPlain11;
                        }
                     } else {
                        var45 = GrandExchangeOfferOwnWorldComparator.fontPlain11;
                     }

                     String var46 = null;
                     String var47 = null;
                     boolean var48 = false;
                     int var49 = 0;
                     var46 = var94.method4621(var0.hitSplatValues[var79]);
                     int var96 = var89.method5324(var46);
                     if(var85 != null) {
                        var47 = var85.method4621(var0.hitSplatValues2[var79]);
                        var49 = var45.method5324(var47);
                     }

                     int var50 = 0;
                     int var51 = 0;
                     if(var24 > 0) {
                        if(var87 == null && var88 == null) {
                           var50 = 1;
                        } else {
                           var50 = var96 / var24 + 1;
                        }
                     }

                     if(var85 != null) {
                        if(var6 <= 962629039) {
                           return;
                        }

                        if(var36 > 0) {
                           label724: {
                              if(var33 == null) {
                                 if(var6 <= 962629039) {
                                    return;
                                 }

                                 if(var34 == null) {
                                    var51 = 1;
                                    break label724;
                                 }
                              }

                              var51 = var49 / var36 + 1;
                           }
                        }
                     }

                     int var52 = 0;
                     int var53 = var52;
                     if(var23 > 0) {
                        var52 += var23;
                     }

                     var52 += 2;
                     int var54 = var52;
                     if(var25 > 0) {
                        if(var6 <= 962629039) {
                           return;
                        }

                        var52 += var25;
                     }

                     int var55 = var52;
                     int var56 = var52;
                     int var57;
                     if(var24 > 0) {
                        var57 = var24 * var50;
                        var52 += var57;
                        var56 += (var57 - var96) / 2;
                     } else {
                        var52 += var96;
                     }

                     var57 = var52;
                     if(var26 > 0) {
                        var52 += var26;
                     }

                     int var58 = 0;
                     int var59 = 0;
                     int var60 = 0;
                     int var61 = 0;
                     int var62 = 0;
                     int var63;
                     if(var85 != null) {
                        var52 += 2;
                        var58 = var52;
                        if(var35 > 0) {
                           var52 += var35;
                        }

                        var52 += 2;
                        var59 = var52;
                        if(var37 > 0) {
                           var52 += var37;
                        }

                        var60 = var52;
                        var62 = var52;
                        if(var36 > 0) {
                           var63 = var36 * var51;
                           var52 += var63;
                           var62 += (var63 - var49) / 2;
                        } else {
                           var52 += var49;
                        }

                        var61 = var52;
                        if(var38 > 0) {
                           var52 += var38;
                        }
                     }

                     var63 = var0.hitSplatCycles[var79] - Client.cycle;
                     int var64 = var94.field3405 - var63 * var94.field3405 / var94.field3400;
                     int var65 = var63 * var94.field3393 / var94.field3400 + -var94.field3393;
                     int var66 = var64 + (var2 + Client.viewportTempX - (var52 >> 1));
                     int var67 = var65 + (var3 + Client.viewportTempY - 12);
                     int var68 = var67;
                     int var69 = var67 + var43;
                     int var70 = var67 + var94.field3416 + 15;
                     int var71 = var70 - var89.maxAscent;
                     int var72 = var70 + var89.maxDescent;
                     if(var71 < var67) {
                        var68 = var71;
                     }

                     if(var72 > var69) {
                        var69 = var72;
                     }

                     int var73 = 0;
                     int var74;
                     int var75;
                     if(var85 != null) {
                        var73 = var67 + var85.field3416 + 15;
                        var74 = var73 - var45.maxAscent;
                        var75 = var73 + var45.maxDescent;
                        if(var74 < var68) {
                           ;
                        }

                        if(var75 > var69) {
                           ;
                        }
                     }

                     var74 = 255;
                     if(var94.field3407 >= 0) {
                        var74 = (var63 << 8) / (var94.field3400 - var94.field3407);
                     }

                     if(var74 >= 0 && var74 < 255) {
                        if(var98 != null) {
                           var98.method6114(var66 + var53 - var27, var67, var74);
                        }

                        if(var87 != null) {
                           var87.method6114(var54 + var66 - var29, var67, var74);
                        }

                        if(var99 != null) {
                           if(var6 <= 962629039) {
                              return;
                           }

                           for(var75 = 0; var75 < var50; ++var75) {
                              var99.method6114(var24 * var75 + (var55 + var66 - var28), var67, var74);
                           }
                        }

                        if(var88 != null) {
                           var88.method6114(var57 + var66 - var30, var67, var74);
                        }

                        var89.method5380(var46, var56 + var66, var70, var94.textColor, 0, var74);
                        if(var85 != null) {
                           if(var6 <= 962629039) {
                              return;
                           }

                           if(var31 != null) {
                              if(var6 <= 962629039) {
                                 return;
                              }

                              var31.method6114(var58 + var66 - var39, var67, var74);
                           }

                           if(var33 != null) {
                              var33.method6114(var59 + var66 - var41, var67, var74);
                           }

                           if(var32 != null) {
                              for(var75 = 0; var75 < var51; ++var75) {
                                 var32.method6114(var36 * var75 + (var66 + var60 - var40), var67, var74);
                              }
                           }

                           if(var34 != null) {
                              if(var6 <= 962629039) {
                                 return;
                              }

                              var34.method6114(var61 + var66 - var42, var67, var74);
                           }

                           var45.method5380(var47, var66 + var62, var73, var85.textColor, 0, var74);
                        }
                     } else {
                        if(var98 != null) {
                           var98.method6159(var66 + var53 - var27, var67);
                        }

                        if(var87 != null) {
                           var87.method6159(var66 + var54 - var29, var67);
                        }

                        if(var99 != null) {
                           if(var6 <= 962629039) {
                              return;
                           }

                           for(var75 = 0; var75 < var50; ++var75) {
                              var99.method6159(var75 * var24 + (var55 + var66 - var28), var67);
                           }
                        }

                        if(var88 != null) {
                           var88.method6159(var57 + var66 - var30, var67);
                        }

                        var89.method5329(var46, var56 + var66, var70, var94.textColor | -16777216, 0);
                        if(var85 != null) {
                           if(var6 <= 962629039) {
                              return;
                           }

                           if(var31 != null) {
                              if(var6 <= 962629039) {
                                 return;
                              }

                              var31.method6159(var66 + var58 - var39, var67);
                           }

                           if(var33 != null) {
                              var33.method6159(var66 + var59 - var41, var67);
                           }

                           if(var32 != null) {
                              for(var75 = 0; var75 < var51; ++var75) {
                                 if(var6 <= 962629039) {
                                    return;
                                 }

                                 var32.method6159(var75 * var36 + (var60 + var66 - var40), var67);
                              }
                           }

                           if(var34 != null) {
                              var34.method6159(var61 + var66 - var42, var67);
                           }

                           var45.method5329(var47, var62 + var66, var73, var85.textColor | -16777216, 0);
                        }
                     }
                  }
               }
            }
         }

      }
   }
}
