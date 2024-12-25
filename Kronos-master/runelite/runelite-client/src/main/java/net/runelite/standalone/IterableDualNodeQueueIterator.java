package net.runelite.standalone;

import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("jt")
public class IterableDualNodeQueueIterator implements Iterator {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lfw;"
   )
   DualNode head;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lfw;"
   )
   DualNode last;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lji;"
   )
   IterableDualNodeQueue queue;

   @ObfuscatedSignature(
      signature = "(Lji;)V"
   )
   IterableDualNodeQueueIterator(IterableDualNodeQueue var1) {
      this.last = null;
      this.queue = var1;
      this.head = this.queue.sentinel.previousDual;
      this.last = null;
   }

   public void remove() {
      if(this.last == null) {
         throw new IllegalStateException();
      } else {
         this.last.method3491();
         this.last = null;
      }
   }

   public boolean hasNext() {
      return this.queue.sentinel != this.head;
   }

   public Object next() {
      DualNode var1 = this.head;
      if(var1 == this.queue.sentinel) {
         var1 = null;
         this.head = null;
      } else {
         this.head = var1.previousDual;
      }

      this.last = var1;
      return var1;
   }
}
