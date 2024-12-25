package net.runelite.standalone;

import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ji")
public class IterableDualNodeQueue implements Iterable {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lfw;"
   )
   DualNode head;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lfw;"
   )
   public DualNode sentinel;

   public IterableDualNodeQueue() {
      this.sentinel = new DualNode();
      this.sentinel.previousDual = this.sentinel;
      this.sentinel.nextDual = this.sentinel;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lfw;)V"
   )
   public void add(DualNode var1) {
      if(var1.nextDual != null) {
         var1.method3491();
      }

      var1.nextDual = this.sentinel.nextDual;
      var1.previousDual = this.sentinel;
      var1.nextDual.previousDual = var1;
      var1.previousDual.nextDual = var1;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Lfw;)Lfw;"
   )
   DualNode method4901(DualNode var1) {
      DualNode var2;
      if(var1 == null) {
         var2 = this.sentinel.previousDual;
      } else {
         var2 = var1;
      }

      if(var2 == this.sentinel) {
         this.head = null;
         return null;
      } else {
         this.head = var2.previousDual;
         return var2;
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "()Lfw;"
   )
   public DualNode method4902() {
      DualNode var1 = this.head;
      if(var1 == this.sentinel) {
         this.head = null;
         return null;
      } else {
         this.head = var1.previousDual;
         return var1;
      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "()Lfw;"
   )
   public DualNode method4898() {
      return this.method4901((DualNode)null);
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "()Lfw;"
   )
   public DualNode method4920() {
      DualNode var1 = this.sentinel.previousDual;
      if(var1 == this.sentinel) {
         return null;
      } else {
         var1.method3491();
         return var1;
      }
   }

   @ObfuscatedName("z")
   public void method4897() {
      while(this.sentinel.previousDual != this.sentinel) {
         this.sentinel.previousDual.method3491();
      }

   }

   public Iterator iterator() {
      return new IterableDualNodeQueueIterator(this);
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lfw;Lfw;)V"
   )
   public static void method4899(DualNode var0, DualNode var1) {
      if(var0.nextDual != null) {
         var0.method3491();
      }

      var0.nextDual = var1;
      var0.previousDual = var1.previousDual;
      var0.nextDual.previousDual = var0;
      var0.previousDual.nextDual = var0;
   }
}
