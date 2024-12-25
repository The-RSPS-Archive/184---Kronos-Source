package net.runelite.standalone;

import net.runelite.api.events.ItemQuantityChanged;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSTile;
import net.runelite.rs.api.RSTileItem;

@ObfuscatedName("cj")
public final class TileItem extends Entity implements RSTileItem {
   @ObfuscatedName("st")
   @ObfuscatedSignature(
      signature = "Lq;"
   )
   static GrandExchangeEvents grandExchangeEvents;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = 1727946021
   )
   public static int field816;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -1667884647
   )
   int quantity;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -1488876063
   )
   int id;
   public int rl$sceneY;
   public int rl$sceneX;

   TileItem() {
      this.rl$$init();
   }

   //TODO: Custom Constructor
   public TileItem(int id, int quantity) {
      this.id = id;
      this.quantity = quantity;
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)Ldh;"
   )
   protected final Model vmethod3072(int var1) {
      return Occluder.getItemDefinition(this.id).method4575(this.quantity, 1336448754);
   }

   public void onUnlink() {
      if(this.rl$sceneX != -1 && ViewportMouse.client.getLastItemDespawn() == null) {
         ViewportMouse.client.setLastItemDespawn(this);
      }

   }

   private void rl$$init() {
      this.rl$sceneX = -1;
      this.rl$sceneY = -1;
   }

   public int getId() {
      return this.id;
   }

   public int getQuantity() {
      return this.quantity;
   }

   public net.runelite.api.Tile getTile() {
      int var1 = this.rl$sceneX;
      int var2 = this.rl$sceneY;
      if(var1 != -1 && var2 != -1) {
         RSTile[][][] var3 = ViewportMouse.client.getScene().getTiles();
         return var3[ViewportMouse.client.getPlane()][var1][var2];
      } else {
         return null;
      }
   }

   public void quantityChanged(int var1) {
      if(this.rl$sceneX != -1) {
         ViewportMouse.client.getLogger().debug("Item quantity changed: {} ({} -> {})", new Object[]{Integer.valueOf(this.getId()), Integer.valueOf(this.getQuantity()), Integer.valueOf(var1)});
         ItemQuantityChanged var2 = new ItemQuantityChanged(this, this.getTile(), this.getQuantity(), var1);
         ViewportMouse.client.getCallbacks().post(ItemQuantityChanged.class, var2);
      }

   }

   public int getX() {
      return this.rl$sceneX;
   }

   public void setX(int var1) {
      this.rl$sceneX = var1;
   }

   public int getY() {
      return this.rl$sceneY;
   }

   public void setY(int var1) {
      this.rl$sceneY = var1;
   }

   public void setId(int var1) {
      this.id = var1;
   }

   public void setQuantity(int var1) {
      this.quantity = var1;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(II)I",
      garbageValue = "160391262"
   )
   public static int method1605(int var0) {
      int var1 = 0;
      if(var0 < 0 || var0 >= 65536) {
         var0 >>>= 16;
         var1 += 16;
      }

      if(var0 >= 256) {
         var0 >>>= 8;
         var1 += 8;
      }

      if(var0 >= 16) {
         var0 >>>= 4;
         var1 += 4;
      }

      if(var0 >= 4) {
         var0 >>>= 2;
         var1 += 2;
      }

      if(var0 >= 1) {
         var0 >>>= 1;
         ++var1;
      }

      return var0 + var1;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "1159842254"
   )
   static int method1606(int var0, Script var1, boolean var2) {
      return var0 < 1000?WorldMapSection2.method5868(var0, var1, var2):(var0 < 1100?Login.method1567(var0, var1, var2):(var0 < 1200?class86.method2112(var0, var1, var2):(var0 < 1300?FriendLoginUpdate.method4922(var0, var1, var2):(var0 < 1400?class28.method589(var0, var1, var2):(var0 < 1500?Tiles.method1155(var0, var1, var2):(var0 < 1600?FontName.method5749(var0, var1, var2):(var0 < 1700?UserComparator6.method3505(var0, var1, var2):(var0 < 1800?VarpDefinition.method4497(var0, var1, var2):(var0 < 1900?World.method1250(var0, var1, var2):(var0 < 2000?ClientPacket.method3877(var0, var1, var2):(var0 < 2100?Login.method1567(var0, var1, var2):(var0 < 2200?class86.method2112(var0, var1, var2):(var0 < 2300?FriendLoginUpdate.method4922(var0, var1, var2):(var0 < 2400?class28.method589(var0, var1, var2):(var0 < 2500?Tiles.method1155(var0, var1, var2):(var0 < 2600?class37.method727(var0, var1, var2):(var0 < 2700?Client.method2082(var0, var1, var2):(var0 < 2800?UserComparator6.method3509(var0, var1, var2):(var0 < 2900?WorldMapRegion.method283(var0, var1, var2):(var0 < 3000?ClientPacket.method3877(var0, var1, var2):(var0 < 3200?Messages.method1601(var0, var1, var2):(var0 < 3300?ReflectionCheck.method2145(var0, var1, var2):(var0 < 3400?ServerPacket.method3848(var0, var1, var2):(var0 < 3500?PacketBuffer.method5305(var0, var1, var2):(var0 < 3700?Buddy.method4961(var0, var1, var2):(var0 < 4000?AbstractWorldMapData.method3329(var0, var1, var2):(var0 < 4100?WorldMapAreaData.method669(var0, var1, var2):(var0 < 4200?WorldMapData_0.method156(var0, var1, var2):(var0 < 4300?class227.method4176(var0, var1, var2):(var0 < 5100?Tiles.method1201(var0, var1, var2):(var0 < 5400?Canvas.method780(var0, var1, var2):(var0 < 5600?WorldMapData_1.method536(var0, var1, var2):(var0 < 5700?class227.method4175(var0, var1, var2):(var0 < 6300?Entity.method3070(var0, var1, var2):(var0 < 6400 ? CustomMisc.handleCustomInstructions(var0, var1, var2):(var0 < 6600?class213.method3934(var0, var1, var2):(var0 < 6700?UserComparator6.method3500(var0, var1, var2):2)))))))))))))))))))))))))))))))))))));
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(CB)Z",
      garbageValue = "-1"
   )
   public static boolean method1602(char var0) {
      return var0 >= ' ' && var0 <= '~'?true:(var0 >= 160 && var0 <= 255?true:var0 == 8364 || var0 == 338 || var0 == 8212 || var0 == 339 || var0 == 376);
   }

   @ObfuscatedName("ip")
   @ObfuscatedSignature(
      signature = "(Lho;IIZI)V",
      garbageValue = "97221829"
   )
   static void method1607(Widget var0, int var1, int var2, boolean var3) {
      int var4 = var0.width;
      int var5 = var0.height;
      if(var0.widthAlignment == 0) {
         var0.width = var0.rawWidth;
      } else if(var0.widthAlignment == 1) {
         var0.width = var1 - var0.rawWidth;
      } else if(var0.widthAlignment == 2) {
         var0.width = var0.rawWidth * var1 >> 14;
      }

      if(var0.heightAlignment == 0) {
         var0.height = var0.rawHeight;
      } else if(var0.heightAlignment == 1) {
         var0.height = var2 - var0.rawHeight;
      } else if(var0.heightAlignment == 2) {
         var0.height = var2 * var0.rawHeight >> 14;
      }

      if(var0.widthAlignment == 4) {
         var0.width = var0.field2688 * var0.height / var0.field2662;
      }

      if(var0.heightAlignment == 4) {
         var0.height = var0.field2662 * var0.width / var0.field2688;
      }

      if(var0.contentType == 1337) {
         Client.viewportWidget = var0;
      }

      if(var3 && var0.onResize != null && (var4 != var0.width || var5 != var0.height)) {
         ScriptEvent var6 = new ScriptEvent();
         var6.widget = var0;
         var6.args = var0.onResize;
         Client.scriptEvents.method5105(var6);
      }

   }
}
