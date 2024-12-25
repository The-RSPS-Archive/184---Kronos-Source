package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("ez")
public class DirectWrapper extends Wrapper {
   @ObfuscatedName("z")
   Object obj;

   DirectWrapper(Object var1, int var2) {
      super(var2);
      this.obj = var1;
   }

   @ObfuscatedName("n")
   boolean vmethod3295() {
      return false;
   }

   @ObfuscatedName("z")
   Object vmethod3292() {
      return this.obj;
   }
}
