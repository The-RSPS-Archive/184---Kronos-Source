package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ko")
public class FontName {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lko;"
   )
   public static final FontName FontName_plain12;
   @ObfuscatedName("o")
   static int[] Tiles_hueMultiplier;
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Lko;"
   )
   public static final FontName FontName_verdana15;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Lko;"
   )
   public static final FontName FontName_verdana13;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lko;"
   )
   public static final FontName FontName_verdana11;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lko;"
   )
   public static final FontName FontName_bold12;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "Llp;"
   )
   static IndexedSprite logoSprite;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lko;"
   )
   public static final FontName FontName_plain11;
   @ObfuscatedName("q")
   String name;

   static {
      FontName_plain11 = new FontName("p11_full");
      FontName_plain12 = new FontName("p12_full");
      FontName_bold12 = new FontName("b12_full");
      FontName_verdana11 = new FontName("verdana_11pt_regular");
      FontName_verdana13 = new FontName("verdana_13pt_regular");
      FontName_verdana15 = new FontName("verdana_15pt_regular");
   }

   FontName(String var1) {
      this.name = var1;
   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "-1537038267"
   )
   static int method5749(int var0, Script var1, boolean var2) {
      Widget var3 = var2?GrandExchangeOfferAgeComparator.field26:KitDefinition.field3452;
      if(var0 == 1500) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.x;
         return 1;
      } else if(var0 == 1501) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.y;
         return 1;
      } else if(var0 == 1502) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.width;
         return 1;
      } else if(var0 == 1503) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.height;
         return 1;
      } else if(var0 == 1504) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.isHidden?1:0;
         return 1;
      } else if(var0 == 1505) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.parentId;
         return 1;
      } else {
         return 2;
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "14"
   )
   static void method5744() {
      ItemContainer.itemContainers = new NodeHashTable(32);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)[Lko;",
      garbageValue = "-88"
   )
   public static FontName[] method5750() {
      return new FontName[]{FontName_verdana13, FontName_bold12, FontName_verdana11, FontName_plain12, FontName_verdana15, FontName_plain11};
   }

   @ObfuscatedName("jr")
   @ObfuscatedSignature(
      signature = "(Lbk;ZI)V",
      garbageValue = "2027801614"
   )
   static final void method5748(InterfaceParent var0, boolean var1) {
      int var2 = var0.group;
      int var3 = (int)var0.key;
      var0.method3497();
      if(var1 && CustomInterfaceEdits.allowUnload(var2)) {
         InterfaceParent.method1137(var2);
      }

      for(IntegerNode var4 = (IntegerNode)Client.widgetClickMasks.method6348(); var4 != null; var4 = (IntegerNode)Client.widgetClickMasks.method6345()) {
         if((long)var2 == (var4.key >> 48 & 65535L)) {
            var4.method3497();
         }
      }

      Widget var5 = Canvas.getWidget(var3);
      if(var5 != null) {
         WorldMapSectionType.method116(var5);
      }

      SecureRandomFuture.method1521();
      if(Client.rootInterface != -1) {
         class28.method588(Client.rootInterface, 1);
      }

   }
}
