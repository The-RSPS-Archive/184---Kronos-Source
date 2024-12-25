package net.runelite.standalone;

import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("jx")
public class IterableNodeDequeDescendingIterator implements Iterator {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lfx;"
   )
   Node field3612;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lfx;"
   )
   Node last;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Ljs;"
   )
   IterableNodeDeque deque;

   @ObfuscatedSignature(
      signature = "(Ljs;)V"
   )
   IterableNodeDequeDescendingIterator(IterableNodeDeque var1) {
      this.last = null;
      this.method5159(var1);
   }

   @ObfuscatedName("c")
   void method5150() {
      this.field3612 = this.deque != null?this.deque.sentinel.previous:null;
      this.last = null;
   }

   public void remove() {
      if(this.last == null) {
         throw new IllegalStateException();
      } else {
         this.last.method3497();
         this.last = null;
      }
   }

   public Object next() {
      Node var1 = this.field3612;
      if(var1 == this.deque.sentinel) {
         var1 = null;
         this.field3612 = null;
      } else {
         this.field3612 = var1.previous;
      }

      this.last = var1;
      return var1;
   }

   public boolean hasNext() {
      return this.deque.sentinel != this.field3612;
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(Ljs;)V"
   )
   void method5159(IterableNodeDeque var1) {
      this.deque = var1;
      this.method5150();
   }
}
