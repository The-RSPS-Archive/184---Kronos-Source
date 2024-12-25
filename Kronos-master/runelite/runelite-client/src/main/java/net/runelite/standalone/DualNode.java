package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSDualNode;

@ObfuscatedName("fw")
public class DualNode extends Node implements RSDualNode {
   @ObfuscatedName("cc")
   @ObfuscatedSignature(
      signature = "Lfw;"
   )
   public DualNode nextDual;
   @ObfuscatedName("ce")
   @ObfuscatedSignature(
      signature = "Lfw;"
   )
   public DualNode previousDual;
   @ObfuscatedName("cu")
   public long keyDual;

   @ObfuscatedName("ce")
   public void method3491() {
      if(this.nextDual != null) {
         this.nextDual.previousDual = this.previousDual;
         this.previousDual.nextDual = this.nextDual;
         this.previousDual = null;
         this.nextDual = null;
      }
   }

   public void unlinkDual() {
      this.method3491();
   }
}
