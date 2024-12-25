package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kp")
public class DefaultsGroup {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lkp;"
   )
   static final DefaultsGroup field3746;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 779550305
   )
   final int group;

   static {
      field3746 = new DefaultsGroup(3);
   }

   DefaultsGroup(int var1) {
      this.group = var1;
   }
}
