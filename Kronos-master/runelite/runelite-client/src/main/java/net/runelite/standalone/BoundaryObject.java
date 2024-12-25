package net.runelite.standalone;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSBoundaryObject;
import net.runelite.rs.api.RSEntity;
import net.runelite.rs.api.RSModel;

@ObfuscatedName("eo")
public final class BoundaryObject implements RSBoundaryObject {
   @ObfuscatedName("dc")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   static Archive archive3;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 614934347
   )
   int tileHeight;
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Ler;"
   )
   public Entity entity1;
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Ler;"
   )
   public Entity entity2;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = -1916194803
   )
   int orientationB;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -1866626591
   )
   int y;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -602196191
   )
   int x;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = -461421073
   )
   int flags;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -21907105
   )
   int orientationA;
   public int wallPlane;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      longValue = 7018407287880452951L
   )
   public long tag;

   BoundaryObject() {
      this.tag = 0L;
      this.flags = 0;
   }

   public Point getCanvasLocation(int var1) {
      return Perspective.localToCanvas(ViewportMouse.client, this.getLocalLocation(), this.getPlane(), var1);
   }

   public Polygon getConvexHull() {
      RSModel var1 = this.getModelA();
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

   public RSModel getModelB() {
      RSEntity var1 = this.getEntity2();
      return var1 == null?null:(var1 instanceof net.runelite.api.Model?(RSModel)var1:var1.getModel());
   }

   public RSEntity getEntity2() {
      return this.entity2;
   }

   public RSModel getModelA() {
      RSEntity var1 = this.getEntity1();
      return var1 == null?null:(var1 instanceof net.runelite.api.Model?(RSModel)var1:var1.getModel());
   }

   public RSEntity getEntity1() {
      return this.entity1;
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
      return this.wallPlane;
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
      this.wallPlane = var1;
   }

   public Shape getClickbox() {
      Shape var1 = Perspective.getClickbox(ViewportMouse.client, this.getModelA(), 0, this.getLocalLocation());
      Shape var2 = Perspective.getClickbox(ViewportMouse.client, this.getModelB(), 0, this.getLocalLocation());
      return var1 == null && var2 == null?null:(var1 != null?var1:var2);
   }

   public int getOrientationA() {
      return this.orientationA;
   }

   public int getOrientationB() {
      return this.orientationB;
   }

   public int getConfig() {
      return this.flags;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(CB)Z",
      garbageValue = "31"
   )
   static final boolean method3062(char var0) {
      return var0 == 160 || var0 == ' ' || var0 == '_' || var0 == '-';
   }
}
