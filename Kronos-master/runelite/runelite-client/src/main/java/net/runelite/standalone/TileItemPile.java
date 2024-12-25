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
import net.runelite.rs.api.RSTileItemPile;

@ObfuscatedName("do")
public final class TileItemPile implements RSTileItemPile {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -149113077
   )
   int tileHeight;
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Ler;"
   )
   Entity third;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      longValue = 3581588196263566823L
   )
   long tag;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Ler;"
   )
   Entity second;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -1956463223
   )
   int y;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 905063527
   )
   int x;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Ler;"
   )
   Entity first;
   public int itemLayerPlane;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 814116925
   )
   int height;

   public Point getCanvasLocation(int var1) {
      return Perspective.localToCanvas(ViewportMouse.client, this.getLocalLocation(), this.getPlane(), var1);
   }

   public Area getClickbox() {
      throw new UnsupportedOperationException();
   }

   public RSEntity getBottom() {
      return this.first;
   }

   public RSEntity getMiddle() {
      return this.second;
   }

   public RSEntity getTop() {
      return this.third;
   }

   public long getHash() {
      return this.tag;
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
      return this.itemLayerPlane;
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
      this.itemLayerPlane = var1;
   }

   public net.runelite.api.Model getModelBottom() {
      RSEntity var1 = this.getBottom();
      return var1 == null?null:(var1 instanceof net.runelite.api.Model?(net.runelite.api.Model)var1:var1.getModel());
   }

   public net.runelite.api.Model getModelMiddle() {
      RSEntity var1 = this.getMiddle();
      return var1 == null?null:(var1 instanceof net.runelite.api.Model?(net.runelite.api.Model)var1:var1.getModel());
   }

   public net.runelite.api.Model getModelTop() {
      RSEntity var1 = this.getTop();
      return var1 == null?null:(var1 instanceof net.runelite.api.Model?(net.runelite.api.Model)var1:var1.getModel());
   }

   public int getHeight() {
      return this.height;
   }
}
