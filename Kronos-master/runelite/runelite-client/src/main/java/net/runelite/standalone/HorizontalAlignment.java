package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("in")
public enum HorizontalAlignment implements Enumerated {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lin;"
   )
   field3267(1, 0),
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lin;"
   )
   HorizontalAlignment_centered(2, 1),
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lin;"
   )
   field3265(0, 2);

   @ObfuscatedName("q")
   public static short[] field3270;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1916297519
   )
   final int id;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 87636045
   )
   public final int value;

   HorizontalAlignment(int var3, int var4) {
      this.value = var3;
      this.id = var4;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(S)I",
      garbageValue = "221"
   )
   public int getId() {
      return this.id;
   }
}
