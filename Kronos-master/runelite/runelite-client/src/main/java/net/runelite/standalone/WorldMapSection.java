package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ad")
public interface WorldMapSection {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IIII)Z",
      garbageValue = "-327883108"
   )
   boolean vmethod5843(int var1, int var2, int var3);

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Lkl;B)V",
      garbageValue = "-99"
   )
   void decode(Buffer var1);

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(III)Lhb;",
      garbageValue = "1439979703"
   )
   Coord vmethod5846(int var1, int var2);

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(IIII)[I",
      garbageValue = "-1322751923"
   )
   int[] vmethod5845(int var1, int var2, int var3);

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(III)Z",
      garbageValue = "828411560"
   )
   boolean vmethod5844(int var1, int var2);

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lac;I)V",
      garbageValue = "2098672163"
   )
   void vmethod5850(WorldMapArea var1);
}
