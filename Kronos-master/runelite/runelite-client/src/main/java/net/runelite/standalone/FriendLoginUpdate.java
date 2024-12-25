package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("jj")
public class FriendLoginUpdate extends Link {
   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "Ldq;"
   )
   public static SoundCache soundCache;
   @ObfuscatedName("r")
   public short world;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Ljq;"
   )
   public Username username;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1649732045
   )
   public int field3559;

   @ObfuscatedSignature(
      signature = "(Ljq;I)V"
   )
   FriendLoginUpdate(Username var1, int var2) {
      this.field3559 = (int)(class33.method680() / 1000L);
      this.username = var1;
      this.world = (short)var2;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(ILcu;ZS)I",
      garbageValue = "-14883"
   )
   static int method4922(int var0, Script var1, boolean var2) {
      Widget var3;
      if(var0 >= 2000) {
         var0 -= 1000;
         var3 = Canvas.getWidget(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
      } else {
         var3 = var2?GrandExchangeOfferAgeComparator.field26:KitDefinition.field3452;
      }

      WorldMapSectionType.method116(var3);
      if(var0 != 1200 && var0 != 1205 && var0 != 1212) {
         if(var0 == 1201) {
            var3.modelType = 2;
            var3.modelId = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            return 1;
         } else if(var0 == 1202) {
            var3.modelType = 3;
            var3.modelId = class215.localPlayer.appearance.method4134();
            return 1;
         } else {
            return 2;
         }
      } else {
         Interpreter.Interpreter_intStackSize -= 2;
         int var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         int var5 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         var3.itemId = var4;
         var3.itemQuantity = var5;
         ItemDefinition var6 = Occluder.getItemDefinition(var4);
         //TODO:: Added Method Call
         CustomShopInterface.updateItem(var3, var6);
         var3.modelAngleX = var6.xan2d;
         var3.modelAngleY = var6.yan2d;
         var3.modelAngleZ = var6.zan2d;
         var3.modelOffsetX = var6.offsetX2d;
         var3.modelOffsetY = var6.offsetY2d;
         var3.modelZoom = var6.zoom2d;
         if(var0 == 1205) {
            var3.itemQuantityMode = 0;
         } else if(var0 == 1212 | var6.isStackable == 1) {
            var3.itemQuantityMode = 1;
         } else {
            var3.itemQuantityMode = 2;
         }

         if(var3.field2700 > 0) {
            var3.modelZoom = var3.modelZoom * 32 / var3.field2700;
         } else if(var3.rawWidth > 0) {
            var3.modelZoom = var3.modelZoom * 32 / var3.rawWidth;
         }

         return 1;
      }
   }
}
