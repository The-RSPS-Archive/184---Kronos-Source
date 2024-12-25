package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ao")
public class WorldMapCacheName {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lao;"
   )
   public static final WorldMapCacheName field248;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Lao;"
   )
   public static final WorldMapCacheName field246;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lao;"
   )
   static final WorldMapCacheName field244;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lao;"
   )
   public static final WorldMapCacheName field242;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lao;"
   )
   public static final WorldMapCacheName field243;
   @ObfuscatedName("ay")
   @ObfuscatedGetter(
      intValue = 1366497929
   )
   static int field245;
   @ObfuscatedName("p")
   public final String name;

   static {
      field243 = new WorldMapCacheName("details");
      field248 = new WorldMapCacheName("compositemap");
      field242 = new WorldMapCacheName("compositetexture");
      field244 = new WorldMapCacheName("area");
      field246 = new WorldMapCacheName("labels");
   }

   WorldMapCacheName(String var1) {
      this.name = var1;
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1512894139"
   )
   static final void method674() {
      EnumDefinition.method4225("Your ignore list is full. Max of 100 for free users, and 400 for members");
   }

   @ObfuscatedName("ht")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "2059241635"
   )
   static final void method673(int var0, int var1) {
      NodeDeque var2 = Client.groundItems[WorldMapRectangle.plane][var0][var1];
      if(var2 == null) {
         PacketWriter.scene.method3178(WorldMapRectangle.plane, var0, var1);
      } else {
         long var3 = -99999999L;
         TileItem var5 = null;

         TileItem var6;
         for(var6 = (TileItem)var2.method5103(); var6 != null; var6 = (TileItem)var2.method5126()) {
            ItemDefinition var7 = Occluder.getItemDefinition(var6.id);
            long var8 = (long)var7.price;
            if(var7.isStackable == 1) {
               var8 *= (long)(var6.quantity + 1);
            }

            if(var8 > var3) {
               var3 = var8;
               var5 = var6;
            }
         }

         if(var5 == null) {
            PacketWriter.scene.method3178(WorldMapRectangle.plane, var0, var1);
         } else {
            var2.method5106(var5);
            TileItem var12 = null;
            TileItem var11 = null;

            for(var6 = (TileItem)var2.method5103(); var6 != null; var6 = (TileItem)var2.method5126()) {
               if(var5.id != var6.id) {
                  if(var12 == null) {
                     var12 = var6;
                  }

                  if(var6.id != var12.id && var11 == null) {
                     var11 = var6;
                  }
               }
            }

            long var9 = class263.method4846(var0, var1, 3, false, 0);
            PacketWriter.scene.method3233(WorldMapRectangle.plane, var0, var1, MusicPatchPcmStream.method3798(var0 * 128 + 64, var1 * 128 + 64, WorldMapRectangle.plane), var5, var9, var12, var11);
         }
      }
   }
}
