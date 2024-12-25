package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSDualNode;
import net.runelite.rs.api.RSEvictingDualNodeHashTable;

@ObfuscatedName("em")
public final class EvictingDualNodeHashTable implements RSEvictingDualNodeHashTable {
   @ObfuscatedName("n")
   int capacity;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Lji;"
   )
   IterableDualNodeQueue deque;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Llb;"
   )
   IterableNodeHashTable hashTable;
   @ObfuscatedName("v")
   int remainingCapacity;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lfw;"
   )
   DualNode field1755;

   public EvictingDualNodeHashTable(int var1) {
      this.field1755 = new DualNode();
      this.deque = new IterableDualNodeQueue();
      this.capacity = var1;
      this.remainingCapacity = var1;

      int var2;
      for(var2 = 1; var2 + var2 < var1; var2 += var2) {
         ;
      }

      this.hashTable = new IterableNodeHashTable(var2);
   }

   @ObfuscatedName("n")
   public void method3033(long var1) {
      DualNode var3 = (DualNode)this.hashTable.method6061(var1);
      if(var3 != null) {
         var3.method3497();
         var3.method3491();
         ++this.remainingCapacity;
      }

   }

   @ObfuscatedName("u")
   public void method3035() {
      this.deque.method4897();
      this.hashTable.method6063();
      this.field1755 = new DualNode();
      this.remainingCapacity = this.capacity;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lfw;J)V"
   )
   public void method3034(DualNode var1, long var2) {
      if(this.remainingCapacity == 0) {
         DualNode var4 = this.deque.method4920();
         var4.method3497();
         var4.method3491();
         if(var4 == this.field1755) {
            var4 = this.deque.method4920();
            var4.method3497();
            var4.method3491();
         }
      } else {
         --this.remainingCapacity;
      }

      this.hashTable.put(var1, var2);
      this.deque.add(var1);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(J)Lfw;"
   )
   public DualNode method3032(long var1) {
      DualNode var3 = (DualNode)this.hashTable.method6061(var1);
      if(var3 != null) {
         this.deque.add(var3);
      }

      return var3;
   }

   public void setCapacity(int var1) {
      this.capacity = var1;
   }

   public void setRemainingCapacity(int var1) {
      this.remainingCapacity = var1;
   }

   public RSDualNode get(long var1) {
      return this.method3032(var1);
   }

   public void reset() {
      this.method3035();
   }
}
