package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("ec")
public abstract class Wrapper extends DualNode {
   @ObfuscatedName("n")
   final int size;

   Wrapper(int var1) {
      this.size = var1;
   }

   @ObfuscatedName("n")
   abstract boolean vmethod3295();

   @ObfuscatedName("z")
   abstract Object vmethod3292();
}
