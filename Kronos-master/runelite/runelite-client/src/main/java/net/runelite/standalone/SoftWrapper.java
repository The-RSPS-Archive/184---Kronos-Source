package net.runelite.standalone;

import java.lang.ref.SoftReference;
import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("ed")
public class SoftWrapper extends Wrapper {
   @ObfuscatedName("z")
   SoftReference ref;

   SoftWrapper(Object var1, int var2) {
      super(var2);
      this.ref = new SoftReference(var1);
   }

   @ObfuscatedName("n")
   boolean vmethod3295() {
      return true;
   }

   @ObfuscatedName("z")
   Object vmethod3292() {
      return this.ref.get();
   }
}
