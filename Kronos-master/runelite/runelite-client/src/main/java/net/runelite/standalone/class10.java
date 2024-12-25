package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("t")
public class class10 {
   @ObfuscatedName("bv")
   @ObfuscatedSignature(
      signature = "Lha;"
   )
   static StudioGame field66;

   @ObfuscatedName("gt")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-194801296"
   )
   static boolean method120() {
      return (Client.drawPlayerNames & 8) != 0;
   }
}
