package net.runelite.standalone;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSEntity;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSWallDecoration;

@ObfuscatedName("eg")
public final class WallDecoration implements RSWallDecoration {
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = -1573146665
   )
   public static int canvasHeight;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 1324796191
   )
   int tileHeight;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 43570649
   )
   int xOffset;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -396532533
   )
   int yOffset;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1549979331
   )
   int orientation2;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -337099317
   )
   int y;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 1787906731
   )
   int x;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "Ler;"
   )
   public Entity entity2;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 500861795
   )
   int orientation;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 14602599
   )
   int flags;
   public int decorativeObjectPlane;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      longValue = 463073903231293703L
   )
   public long tag;
   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "Ler;"
   )
   public Entity entity1;

   WallDecoration() {
      this.tag = 0L;
      this.flags = 0;
   }

   public Point getCanvasLocation(int var1) {
      return Perspective.localToCanvas(ViewportMouse.client, this.getLocalLocation(), this.getPlane(), var1);
   }

   public Polygon getConvexHull() {
      RSModel var1 = this.getModel1();
      if(var1 == null) {
         return null;
      } else {
         int var2 = Perspective.getTileHeight(ViewportMouse.client, new LocalPoint(this.getX(), this.getY()), ViewportMouse.client.getPlane());
         return var1.getConvexHull(this.getX() + this.getXOffset(), this.getY() + this.getYOffset(), 0, var2);
      }
   }

   public Polygon getConvexHull2() {
      RSModel var1 = this.getModel2();
      if(var1 == null) {
         return null;
      } else {
         int var2 = Perspective.getTileHeight(ViewportMouse.client, new LocalPoint(this.getX(), this.getY()), ViewportMouse.client.getPlane());
         return var1.getConvexHull(this.getX(), this.getY(), 0, var2);
      }
   }

   public long getHash() {
      return this.tag;
   }

   public RSModel getModel1() {
      RSEntity var1 = this.getEntity1();
      if(var1 == null) {
         return null;
      } else {
         RSModel var2;
         if(var1 instanceof net.runelite.api.Model) {
            var2 = (RSModel)var1;
         } else {
            var2 = var1.getModel();
         }

         return var2;
      }
   }

   public RSModel getModel2() {
      RSEntity var1 = this.getEntity2();
      if(var1 == null) {
         return null;
      } else {
         RSModel var2;
         if(var1 instanceof net.runelite.api.Model) {
            var2 = (RSModel)var1;
         } else {
            var2 = var1.getModel();
         }

         return var2;
      }
   }

   public RSEntity getEntity1() {
      return this.entity1;
   }

   public RSEntity getEntity2() {
      return this.entity2;
   }

   public int getX() {
      return this.x;
   }

   public LocalPoint getLocalLocation() {
      return new LocalPoint(this.getX(), this.getY());
   }

   public int getY() {
      return this.y;
   }

   public int getPlane() {
      return this.decorativeObjectPlane;
   }

   public int getXOffset() {
      return this.xOffset;
   }

   public int getYOffset() {
      return this.yOffset;
   }

   public int getId() {
      long var1 = this.getHash();
      return (int)(var1 >>> 17 & 4294967295L);
   }

   public WorldPoint getWorldLocation() {
      return WorldPoint.fromLocal(ViewportMouse.client, this.getX(), this.getY(), this.getPlane());
   }

   public Point getCanvasLocation() {
      return this.getCanvasLocation(0);
   }

   public Polygon getCanvasTilePoly() {
      return Perspective.getCanvasTilePoly(ViewportMouse.client, this.getLocalLocation());
   }

   public Point getCanvasTextLocation(Graphics2D var1, String var2, int var3) {
      return Perspective.getCanvasTextLocation(ViewportMouse.client, var1, this.getLocalLocation(), var2, var3);
   }

   public Point getMinimapLocation() {
      return Perspective.localToMinimap(ViewportMouse.client, this.getLocalLocation());
   }

   public void setPlane(int var1) {
      this.decorativeObjectPlane = var1;
   }

   public Shape getClickbox() {
      new Area();
      LocalPoint var2 = this.getLocalLocation();
      Shape var3 = Perspective.getClickbox(ViewportMouse.client, this.getModel1(), 0, new LocalPoint(var2.getX() + this.getXOffset(), var2.getY() + this.getYOffset()));
      Shape var4 = Perspective.getClickbox(ViewportMouse.client, this.getModel2(), 0, var2);
      return var3 == null && var4 == null?null:(var3 != null?var3:var4);
   }

   public int getOrientation() {
      return this.orientation2;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(II)Lif;",
      garbageValue = "-746198187"
   )
   public static ParamDefinition method2913(int var0) {
      ParamDefinition var1 = (ParamDefinition)ParamDefinition.ParamDefinition_cached.method3032((long)var0);
      if(var1 != null) {
         return var1;
      } else {
         byte[] var2 = ParamDefinition.ParamDefinition_archive.method4020(11, var0, (short)-887);
         var1 = new ParamDefinition();
         if(var2 != null) {
            var1.method4311(new Buffer(var2));
         }

         var1.method4310();
         ParamDefinition.ParamDefinition_cached.method3034(var1, (long)var0);
         return var1;
      }
   }

   @ObfuscatedName("fz")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1910753346"
   )
   static final void method2914() {
      for(int var0 = 0; var0 < Client.npcCount; ++var0) {
         int var1 = Client.npcIndices[var0];
         NPC var2 = Client.npcs[var1];
         if(var2 != null) {
            class329.method6315(var2, var2.definition.size);
         }
      }

   }
}
