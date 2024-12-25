package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("fk")
public class class163 implements class167 {
   @ObfuscatedName("s")
   public static String field1987;
   @ObfuscatedName("ao")
   @ObfuscatedSignature(
      signature = "Lll;"
   )
   static Bounds field1985;
   @ObfuscatedName("m")
   static boolean ItemDefinition_inMembersWorld;

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "1376590669"
   )
   static void resetContainerContents(int containerId) {
      ItemContainer container = (ItemContainer)ItemContainer.itemContainers.method6346(containerId);
      if(container != null) {
         for(int slot = 0; slot < container.ids.length; ++slot) {
            container.ids[slot] = -1;
            container.quantities[slot] = 0;
            container.itemAttributes[slot] = new String[] { "null", "null", "null" };
         }
      }
   }
}
