package net.runelite.standalone;

import java.util.ArrayList;
import java.util.List;
import net.runelite.api.DecorativeObject;
import net.runelite.api.GroundObject;
import net.runelite.api.Point;
import net.runelite.api.WallObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.DecorativeObjectChanged;
import net.runelite.api.events.DecorativeObjectDespawned;
import net.runelite.api.events.DecorativeObjectSpawned;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GroundObjectChanged;
import net.runelite.api.events.GroundObjectDespawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.WallObjectChanged;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSActor;
import net.runelite.rs.api.RSCollisionMap;
import net.runelite.rs.api.RSEntity;
import net.runelite.rs.api.RSGameObject;
import net.runelite.rs.api.RSGraphicsObject;
import net.runelite.rs.api.RSNode;
import net.runelite.rs.api.RSNodeDeque;
import net.runelite.rs.api.RSProjectile;
import net.runelite.rs.api.RSTile;
import net.runelite.rs.api.RSTileItem;
import net.runelite.rs.api.RSTileItemPile;
import org.slf4j.Logger;

@ObfuscatedName("dl")
public final class Tile extends Node implements RSTile {
   public static RSNodeDeque[][][] lastGroundItems;
   public static RSGameObject lastGameObject;
   @ObfuscatedName("da")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   static Archive archive13;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 1518104939
   )
   int plane;
   @ObfuscatedName("o")
   int[] gameObjectEdgeMasks;
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Ldd;"
   )
   TileModel model;
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Leo;"
   )
   BoundaryObject boundaryObject;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Lef;"
   )
   TilePaint paint;
   @ObfuscatedName("s")
   @ObfuscatedGetter(
      intValue = 1620350221
   )
   int field1496;
   @ObfuscatedName("t")
   boolean drawSecondary;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 1362708877
   )
   int y;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1003607853
   )
   int x;
   @ObfuscatedName("w")
   boolean drawPrimary;
   @ObfuscatedName("x")
   @ObfuscatedGetter(
      intValue = 824924819
   )
   int drawGameObjectEdges;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "Ldj;"
   )
   FloorDecoration floorDecoration;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 16369307
   )
   int originalPlane;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = 2078500007
   )
   int gameObjectsEdgeMask;
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "[Lej;"
   )
   GameObject[] gameObjects;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = -308551417
   )
   int gameObjectsCount;
   public WallObject previousWallObject;
   public DecorativeObject previousDecorativeObject;
   public GroundObject previousGroundObject;
   public RSGameObject[] previousGameObjects;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = -260677087
   )
   int minPlane;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = 2139191781
   )
   int field1501;
   @ObfuscatedName("g")
   boolean drawGameObjects;
   @ObfuscatedName("h")
   @ObfuscatedGetter(
      intValue = 1066984119
   )
   int field1482;
   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "Ldo;"
   )
   TileItemPile tileItemPile;
   @ObfuscatedName("j")
   @ObfuscatedSignature(
      signature = "Ldl;"
   )
   Tile linkedBelowTile;
   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "Leg;"
   )
   WallDecoration wallDecoration;

   static {
      lastGroundItems = new RSNodeDeque[4][104][104];
   }

   Tile(int var1, int var2, int var3) {
      this.gameObjects = new GameObject[5];
      this.gameObjectsChanged(-1);
      this.gameObjectEdgeMasks = new int[5];
      this.gameObjectsEdgeMask = 0;
      this.originalPlane = this.plane = var1;
      this.x = var2;
      this.y = var3;
   }

   public int getPlane() {
      return this.plane;
   }

   public int getX() {
      return this.x;
   }

   public net.runelite.api.TileItemPile getItemLayer() {
      return this.tileItemPile;
   }

   public WallObject getWallObject() {
      return this.boundaryObject;
   }

   public DecorativeObject getDecorativeObject() {
      return this.wallDecoration;
   }

   public GroundObject getGroundObject() {
      return this.floorDecoration;
   }

   public int getY() {
      return this.y;
   }

   public void gameObjectsChanged(int var1) {
      if(var1 != -1) {
         if(this.previousGameObjects == null) {
            this.previousGameObjects = new GameObject[5];
         }

         RSGameObject var2 = this.previousGameObjects[var1];
         RSGameObject var3 = (RSGameObject)this.getGameObjects()[var1];
         this.previousGameObjects[var1] = var3;
         RSGameObject var4 = lastGameObject;
         lastGameObject = var3;
         if(var3 != var2) {
            if(var3 == null || var3 != var4) {
               boolean var5 = false;
               boolean var6 = false;
               RSEntity var7;
               if(var3 != null) {
                  var7 = var3.getEntity();
                  var5 = var7 instanceof RSActor || var7 instanceof RSProjectile || var7 instanceof RSGraphicsObject;
               }

               if(var2 != null) {
                  var7 = var2.getEntity();
                  var6 = var7 instanceof RSActor || var7 instanceof RSProjectile || var7 instanceof RSGraphicsObject;
               }

               Logger var9 = ViewportMouse.client.getLogger();
               if(var3 == null) {
                  if(var6) {
                     return;
                  }

                  var9.trace("Game object despawn: {}", Integer.valueOf(var2.getId()));
                  GameObjectDespawned var8 = new GameObjectDespawned();
                  var8.setTile(this);
                  var8.setGameObject(var2);
                  ViewportMouse.client.getCallbacks().post(GameObjectDespawned.class, var8);
               } else if(var2 == null) {
                  if(var5) {
                     return;
                  }

                  var9.trace("Game object spawn: {}", Integer.valueOf(var3.getId()));
                  GameObjectSpawned var10 = new GameObjectSpawned();
                  var10.setTile(this);
                  var10.setGameObject(var3);
                  ViewportMouse.client.getCallbacks().post(GameObjectSpawned.class, var10);
               } else {
                  if(var5 && var6) {
                     return;
                  }

                  var9.trace("Game object change: {} -> {}", Integer.valueOf(var2.getId()), Integer.valueOf(var3.getId()));
                  GameObjectChanged var11 = new GameObjectChanged();
                  var11.setTile(this);
                  var11.setPrevious(var2);
                  var11.setGameObject(var3);
                  ViewportMouse.client.getCallbacks().post(GameObjectChanged.class, var11);
               }

            }
         }
      }
   }

   public net.runelite.api.GameObject[] getGameObjects() {
      return this.gameObjects;
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public void wallObjectChanged(int var1) {
      WallObject var2 = this.previousWallObject;
      WallObject var3 = this.getWallObject();
      this.previousWallObject = var3;
      if(var3 == null && var2 != null) {
         WallObjectDespawned var6 = new WallObjectDespawned();
         var6.setTile(this);
         var6.setWallObject(var2);
         ViewportMouse.client.getCallbacks().post(WallObjectDespawned.class, var6);
      } else if(var3 != null && var2 == null) {
         WallObjectSpawned var5 = new WallObjectSpawned();
         var5.setTile(this);
         var5.setWallObject(var3);
         ViewportMouse.client.getCallbacks().post(WallObjectSpawned.class, var5);
      } else if(var3 != null) {
         WallObjectChanged var4 = new WallObjectChanged();
         var4.setTile(this);
         var4.setPrevious(var2);
         var4.setWallObject(var3);
         ViewportMouse.client.getCallbacks().post(WallObjectChanged.class, var4);
      }

   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public void decorativeObjectChanged(int var1) {
      DecorativeObject var2 = this.previousDecorativeObject;
      DecorativeObject var3 = this.getDecorativeObject();
      this.previousDecorativeObject = var3;
      if(var3 == null && var2 != null) {
         DecorativeObjectDespawned var6 = new DecorativeObjectDespawned();
         var6.setTile(this);
         var6.setDecorativeObject(var2);
         ViewportMouse.client.getCallbacks().post(DecorativeObjectDespawned.class, var6);
      } else if(var3 != null && var2 == null) {
         DecorativeObjectSpawned var5 = new DecorativeObjectSpawned();
         var5.setTile(this);
         var5.setDecorativeObject(var3);
         ViewportMouse.client.getCallbacks().post(DecorativeObjectSpawned.class, var5);
      } else if(var3 != null) {
         DecorativeObjectChanged var4 = new DecorativeObjectChanged();
         var4.setTile(this);
         var4.setPrevious(var2);
         var4.setDecorativeObject(var3);
         ViewportMouse.client.getCallbacks().post(DecorativeObjectChanged.class, var4);
      }

   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public void groundObjectChanged(int var1) {
      GroundObject var2 = this.previousGroundObject;
      GroundObject var3 = this.getGroundObject();
      this.previousGroundObject = var3;
      if(var3 == null && var2 != null) {
         GroundObjectDespawned var6 = new GroundObjectDespawned();
         var6.setTile(this);
         var6.setGroundObject(var2);
         ViewportMouse.client.getCallbacks().post(GroundObjectDespawned.class, var6);
      } else if(var3 != null && var2 == null) {
         GroundObjectSpawned var5 = new GroundObjectSpawned();
         var5.setTile(this);
         var5.setGroundObject(var3);
         ViewportMouse.client.getCallbacks().post(GroundObjectSpawned.class, var5);
      } else if(var3 != null) {
         GroundObjectChanged var4 = new GroundObjectChanged();
         var4.setTile(this);
         var4.setPrevious(var2);
         var4.setGroundObject(var3);
         ViewportMouse.client.getCallbacks().post(GroundObjectChanged.class, var4);
      }

   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public void itemLayerChanged(int var1) {
      int var2 = this.getX();
      int var3 = this.getY();
      int var4 = ViewportMouse.client.getPlane();
      RSNodeDeque[][][] var5 = ViewportMouse.client.getGroundItemDeque();
      RSNodeDeque var6 = lastGroundItems[var4][var2][var3];
      RSNodeDeque var7 = var5[var4][var2][var3];
      ItemDespawned var11;
      if(var6 != var7) {
         if(var6 != null) {
            RSNode var8 = var6.getHead();

            for(RSNode var9 = var8.getNext(); var9 != var8; var9 = var9.getNext()) {
               RSTileItem var10 = (RSTileItem)var9;
               var11 = new ItemDespawned(this, var10);
               ViewportMouse.client.getCallbacks().post(ItemDespawned.class, var11);
            }
         }

         lastGroundItems[var4][var2][var3] = var7;
      }

      RSTileItem var18 = ViewportMouse.client.getLastItemDespawn();
      if(var18 != null) {
         ViewportMouse.client.setLastItemDespawn((RSTileItem)null);
      }

      RSTileItemPile var19 = (RSTileItemPile)this.getItemLayer();
      if(var19 == null) {
         if(var18 != null) {
            ItemDespawned var20 = new ItemDespawned(this, var18);
            ViewportMouse.client.getCallbacks().post(ItemDespawned.class, var20);
         }

      } else if(var7 == null) {
         if(var18 != null) {
            var11 = new ItemDespawned(this, var18);
            ViewportMouse.client.getCallbacks().post(ItemDespawned.class, var11);
         }

      } else {
         RSNode var21 = var7.getHead();
         Object var12 = null;
         RSNode var13 = var21.getPrevious();
         boolean var14 = false;
         if(var21 != var13) {
            RSTileItem var15 = (RSTileItem)var13;
            if(var2 != var15.getX() || var3 != var15.getY()) {
               var12 = var15;
            }
         }

         RSNode var22 = var21.getNext();
         RSTileItem var16;
         if(var12 == null && var21 != var22) {
            var16 = (RSTileItem)var22;
            if(var2 != var16.getX() || var3 != var16.getY()) {
               var12 = var16;
               var14 = true;
            }
         }

         if(var18 != null && var18 != var13 && var18 != var22) {
            ItemDespawned var23 = new ItemDespawned(this, var18);
            ViewportMouse.client.getCallbacks().post(ItemDespawned.class, var23);
         }

         if(var12 != null) {
            do {
               var16 = (RSTileItem)var12;
               var16.setX(var2);
               var16.setY(var3);
               ItemSpawned var17 = new ItemSpawned(this, var16);
               ViewportMouse.client.getCallbacks().post(ItemSpawned.class, var17);
               var12 = var14?((RSNode)var12).getNext():((RSNode)var12).getPrevious();
            } while(var12 != var21 && (((RSTileItem)var12).getX() != var2 || ((RSTileItem)var12).getY() != var3));

         }
      }
   }

   public Point getSceneLocation() {
      return new Point(this.getX(), this.getY());
   }

   public WorldPoint getWorldLocation() {
      return WorldPoint.fromScene(ViewportMouse.client, this.getX(), this.getY(), this.getPlane());
   }

   public LocalPoint getLocalLocation() {
      return LocalPoint.fromScene(this.getX(), this.getY());
   }

   public boolean hasLineOfSightTo(net.runelite.api.Tile var1) {
      if(this.getPlane() != var1.getPlane()) {
         return false;
      } else {
         RSCollisionMap[] var2 = ViewportMouse.client.getCollisionMaps();
         if(var2 == null) {
            return false;
         } else {
            int var3 = this.getPlane();
            int[][] var4 = var2[var3].getFlags();
            Point var5 = this.getSceneLocation();
            Point var6 = var1.getSceneLocation();
            if(var5.getX() == var6.getX() && var5.getY() == var6.getY()) {
               return true;
            } else {
               int var7 = var6.getX() - var5.getX();
               int var8 = var6.getY() - var5.getY();
               int var9 = Math.abs(var7);
               int var10 = Math.abs(var8);
               int var11 = 131072;
               int var12 = 131072;
               if(var7 < 0) {
                  var11 |= 4096;
               } else {
                  var11 |= 65536;
               }

               if(var8 < 0) {
                  var12 |= 1024;
               } else {
                  var12 |= 16384;
               }

               int var13;
               int var14;
               int var15;
               int var16;
               int var17;
               int var18;
               if(var9 > var10) {
                  var13 = var5.getX();
                  var14 = var5.getY() << 16;
                  var15 = (var8 << 16) / var9;
                  var14 += 32768;
                  if(var8 < 0) {
                     --var14;
                  }

                  var16 = var7 < 0?-1:1;

                  while(var13 != var6.getX()) {
                     var13 += var16;
                     var17 = var14 >>> 16;
                     if((var4[var13][var17] & var11) != 0) {
                        return false;
                     }

                     var14 += var15;
                     var18 = var14 >>> 16;
                     if(var18 != var17 && (var4[var13][var18] & var12) != 0) {
                        return false;
                     }
                  }
               } else {
                  var13 = var5.getY();
                  var14 = var5.getX() << 16;
                  var15 = (var7 << 16) / var10;
                  var14 += 32768;
                  if(var7 < 0) {
                     --var14;
                  }

                  var16 = var8 < 0?-1:1;

                  while(var13 != var6.getY()) {
                     var13 += var16;
                     var17 = var14 >>> 16;
                     if((var4[var17][var13] & var12) != 0) {
                        return false;
                     }

                     var14 += var15;
                     var18 = var14 >>> 16;
                     if(var18 != var17 && (var4[var18][var13] & var11) != 0) {
                        return false;
                     }
                  }
               }

               return true;
            }
         }
      }
   }

   public List getGroundItems() {
      net.runelite.api.TileItemPile var1 = this.getItemLayer();
      if(var1 == null) {
         return null;
      } else {
         ArrayList var2 = new ArrayList();

         for(Object var3 = var1.getBottom(); var3 instanceof net.runelite.api.TileItem; var3 = ((net.runelite.api.Node)var3).getNext()) {
            var2.add((net.runelite.api.TileItem)var3);
         }

         return var2;
      }
   }

   public int getRenderLevel() {
      return this.originalPlane;
   }

   public net.runelite.api.TilePaint getTilePaint() {
      return this.paint;
   }

   public net.runelite.api.TileModel getTileModel() {
      return this.model;
   }

   public int getFlags() {
      return this.gameObjectsEdgeMask;
   }

   public int getPhysicalLevel() {
      return this.minPlane;
   }

   public void setDraw(boolean var1) {
      this.drawPrimary = var1;
   }

   public boolean isDraw() {
      return this.drawPrimary;
   }

   public void setVisible(boolean var1) {
      this.drawSecondary = var1;
   }

   public boolean isVisible() {
      return this.drawSecondary;
   }

   public void setDrawEntities(boolean var1) {
      this.drawGameObjects = var1;
   }

   public void setWallCullDirection(int var1) {
      this.drawGameObjectEdges = var1;
   }

   public RSTile getBridge() {
      return this.linkedBelowTile;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)Lgn;",
      garbageValue = "283500885"
   )
   public static PacketBufferNode method2444() {
      PacketBufferNode var0;
      if(PacketBufferNode.PacketBufferNode_packetBufferNodeCount == 0) {
         var0 = new PacketBufferNode();
      } else {
         var0 = PacketBufferNode.PacketBufferNode_packetBufferNodes[--PacketBufferNode.PacketBufferNode_packetBufferNodeCount];
      }

      var0.clientPacket = null;
      var0.clientPacketLength = 0;
      var0.packetBuffer = new PacketBuffer(5000);
      return var0;
   }
}
