package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("is")
public class VarcInt extends DualNode {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable VarcInt_cached;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive VarcInt_archive;
   @ObfuscatedName("dp")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   static Archive archive18;
   @ObfuscatedName("v")
   public boolean persist;

   static {
      VarcInt_cached = new EvictingDualNodeHashTable(64);
   }

   public VarcInt() {
      this.persist = false;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "-773678840"
   )
   void method4522(Buffer var1, int var2) {
      if(var2 == 2) {
         this.persist = true;
      }

   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lkl;B)V",
      garbageValue = "32"
   )
   public void method4519(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            return;
         }

         this.method4522(var1, var2);
      }
   }
}
