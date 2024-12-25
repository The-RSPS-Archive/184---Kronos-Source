package net.runelite.standalone;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.Angle;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSEntity;
import net.runelite.rs.api.RSGameObject;
import net.runelite.rs.api.RSModel;

@ObfuscatedName("ej")
public final class GameObject implements RSGameObject {
   @ObfuscatedName("gh")
   @ObfuscatedSignature(
      signature = "[Llf;"
   )
   static Sprite[] mapMarkerSprites;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 23072497
   )
   int plane;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      longValue = 2276908467403178719L
   )
   public long tag;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 1901316909
   )
   int orientation;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -714921437
   )
   int startX;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = -921514335
   )
   int centerY;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 1096373851
   )
   int centerX;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 1467218039
   )
   int height;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = -1244242871
   )
   int startY;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Ler;"
   )
   public Entity entity;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = 8294449
   )
   int flags;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = 505349389
   )
   int lastDrawn;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = -1933245697
   )
   int field1718;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 1759509999
   )
   int endY;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = -1336647537
   )
   int endX;

   GameObject() {
      this.tag = 0L;
      this.flags = 0;
   }

   public Point getCanvasLocation(int var1) {
      return Perspective.localToCanvas(ViewportMouse.client, this.getLocalLocation(), this.getPlane(), var1);
   }

   public Polygon getConvexHull() {
      RSModel var1 = this.getModel();
      if(var1 == null) {
         return null;
      } else {
         int var2 = Perspective.getTileHeight(ViewportMouse.client, new LocalPoint(this.getX(), this.getY()), ViewportMouse.client.getPlane());
         return var1.getConvexHull(this.getX(), this.getY(), this.getRsOrientation(), var2);
      }
   }

   public int getRelativeX() {
      return this.startX;
   }

   public int getOffsetX() {
      return this.endX;
   }

   public long getHash() {
      return this.tag;
   }

   public RSModel getModel() {
      RSEntity var1 = this.getEntity();
      return var1 == null?null:(var1 instanceof RSModel?(RSModel)var1:var1.getModel());
   }

   public RSEntity getEntity() {
      return this.entity;
   }

   public int getRsOrientation() {
      return this.orientation;
   }

   public int getX() {
      return this.centerX;
   }

   public LocalPoint getLocalLocation() {
      return new LocalPoint(this.getX(), this.getY());
   }

   public int getRelativeY() {
      return this.startY;
   }

   public int getOffsetY() {
      return this.endY;
   }

   public int getFlags() {
      return this.flags;
   }

   public int getY() {
      return this.centerY;
   }

   public int getPlane() {
      return this.plane;
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

   public Point getSceneMinLocation() {
      return new Point(this.getRelativeX(), this.getRelativeY());
   }

   public Point getSceneMaxLocation() {
      return new Point(this.getOffsetX(), this.getOffsetY());
   }

   public Shape getClickbox() {
      return Perspective.getClickbox(ViewportMouse.client, this.getModel(), this.getRsOrientation(), this.getLocalLocation());
   }

   public Angle getOrientation() {
      int var1 = this.getRsOrientation();
      int var2 = this.getFlags() >> 6 & 3;
      return new Angle(var1 + var2 * 512);
   }

   public int getHeight() {
      return this.height;
   }
}
