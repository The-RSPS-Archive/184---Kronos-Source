package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("hn")
public enum WorldMapDecorationType implements Enumerated {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2555(0, 0),
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2545(1, 0),
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2546(2, 0),
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2547(3, 0),
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2553(9, 2),
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2557(4, 1),
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2550(5, 1),
   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2558(6, 1),
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2548(7, 1),
   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2551(8, 1),
   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2554(12, 2),
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2564(13, 2),
   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2556(14, 2),
   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2552(15, 2),
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2544(16, 2),
   @ObfuscatedName("w")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2559(17, 2),
   @ObfuscatedName("t")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2560(18, 2),
   @ObfuscatedName("g")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2561(19, 2),
   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2562(20, 2),
   @ObfuscatedName("h")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2563(21, 2),
   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2549(10, 2),
   @ObfuscatedName("f")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2565(11, 2),
   @ObfuscatedName("j")
   @ObfuscatedSignature(
      signature = "Lhn;"
   )
   field2566(22, 3);

   @ObfuscatedName("d")
   @ObfuscatedGetter(
      intValue = -240297385
   )
   public final int id;

   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "0"
   )
   WorldMapDecorationType(int var3, int var4) {
      this.id = var3;
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
