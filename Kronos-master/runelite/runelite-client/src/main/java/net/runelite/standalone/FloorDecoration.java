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
import net.runelite.rs.api.RSEntity;
import net.runelite.rs.api.RSFloorDecoration;
import net.runelite.rs.api.RSModel;

@ObfuscatedName("dj")
public final class FloorDecoration implements RSFloorDecoration {
   @ObfuscatedName("d")
   @ObfuscatedGetter(
      intValue = 1829552079
   )
   public static int canvasWidth;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 1834213503
   )
   int tileHeight;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -1953587145
   )
   int flags;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      longValue = 3642112439868332829L
   )
   public long tag;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -848464295
   )
   int y;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 461857131
   )
   int x;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Ler;"
   )
   public Entity entity;
   public int groundObjectPlane;

   public Point getCanvasLocation(int var1) {
      return Perspective.localToCanvas(ViewportMouse.client, this.getLocalLocation(), this.getPlane(), var1);
   }

   public Polygon getConvexHull() {
      RSModel var1 = this.getModel();
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

   public RSModel getModel() {
      RSEntity var1 = this.getEntity();
      return var1 == null?null:(var1 instanceof net.runelite.api.Model?(RSModel)var1:var1.getModel());
   }

   public RSEntity getEntity() {
      return this.entity;
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
      return this.groundObjectPlane;
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
      this.groundObjectPlane = var1;
   }

   public Shape getClickbox() {
      return Perspective.getClickbox(ViewportMouse.client, this.getModel(), 0, this.getLocalLocation());
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(II)Lhs;",
      garbageValue = "-1156266256"
   )
   public static ServerBuild method2433(int var0) {
      ServerBuild[] var1 = new ServerBuild[]{ServerBuild.BUILDLIVE, ServerBuild.LIVE, ServerBuild.RC, ServerBuild.WIP};
      ServerBuild[] var2 = var1;

      for(int var3 = 0; var3 < var2.length; ++var3) {
         ServerBuild var4 = var2[var3];
         if(var0 == var4.field2757) {
            return var4;
         }
      }

      return null;
   }

   @ObfuscatedName("jl")
   @ObfuscatedSignature(
      signature = "(IIIILlf;Lhz;S)V",
      garbageValue = "1700"
   )
   static final void method2432(int var0, int var1, int var2, int var3, Sprite var4, SpriteMask var5) {
      int var6 = var3 * var3 + var2 * var2;
      if(var6 > 4225 && var6 < 90000) {
         int var7 = Client.camAngleY & 2047;
         int var8 = Rasterizer3D.Rasterizer3D_sine[var7];
         int var9 = Rasterizer3D.Rasterizer3D_cosine[var7];
         int var10 = var9 * var2 + var3 * var8 >> 16;
         int var11 = var3 * var9 - var8 * var2 >> 16;
         double var12 = Math.atan2((double)var10, (double)var11);
         int var14 = var5.width / 2 - 25;
         int var15 = (int)(Math.sin(var12) * (double)var14);
         int var16 = (int)(Math.cos(var12) * (double)var14);
         byte var17 = 20;
         PendingSpawn.redHintArrowSprite.method6125(var15 + (var0 + var5.width / 2 - var17 / 2), var5.height / 2 + var1 - var17 / 2 - var16 - 10, var17, var17, 15, 15, var12, 256);
      } else {
         class214.method3936(var0, var1, var2, var3, var4, var5);
      }

   }
}
