package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ic")
public final class DualNodeDeque {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lfw;"
   )
   DualNode sentinel;

   public DualNodeDeque() {
      this.sentinel = new DualNode();
      this.sentinel.previousDual = this.sentinel;
      this.sentinel.nextDual = this.sentinel;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lfw;)V"
   )
   public void method4253(DualNode var1) {
      if(var1.nextDual != null) {
         var1.method3491();
      }

      var1.nextDual = this.sentinel;
      var1.previousDual = this.sentinel.previousDual;
      var1.nextDual.previousDual = var1;
      var1.previousDual.nextDual = var1;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "()Lfw;"
   )
   public DualNode method4251() {
      DualNode var1 = this.sentinel.previousDual;
      return var1 == this.sentinel?null:var1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lfw;)V"
   )
   public void method4249(DualNode var1) {
      if(var1.nextDual != null) {
         var1.method3491();
      }

      var1.nextDual = this.sentinel.nextDual;
      var1.previousDual = this.sentinel;
      var1.nextDual.previousDual = var1;
      var1.previousDual.nextDual = var1;
   }
}
