package net.runelite.standalone;

import java.util.Collection;
import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSIterableNodeDeque;
import net.runelite.rs.api.RSNode;

@ObfuscatedName("js")
public class IterableNodeDeque implements Iterable, Collection, RSIterableNodeDeque {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lfx;"
   )
   Node field3598;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lfx;"
   )
   Node sentinel;

   public IterableNodeDeque() {
      this.sentinel = new Node();
      this.sentinel.previous = this.sentinel;
      this.sentinel.next = this.sentinel;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lfx;)V"
   )
   public void method5019(Node var1) {
      if(var1.next != null) {
         var1.method3497();
      }

      var1.next = this.sentinel.next;
      var1.previous = this.sentinel;
      var1.next.previous = var1;
      var1.previous.next = var1;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Lfx;)Lfx;"
   )
   Node method5047(Node var1) {
      Node var2;
      if(var1 == null) {
         var2 = this.sentinel.previous;
      } else {
         var2 = var1;
      }

      if(var2 == this.sentinel) {
         this.field3598 = null;
         return null;
      } else {
         this.field3598 = var2.previous;
         return var2;
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "()Lfx;"
   )
   public Node method5024() {
      Node var1 = this.field3598;
      if(var1 == this.sentinel) {
         this.field3598 = null;
         return null;
      } else {
         this.field3598 = var1.previous;
         return var1;
      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "()Lfx;"
   )
   public Node method5044() {
      return this.method5047((Node)null);
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lfx;)V"
   )
   public void method5027(Node var1) {
      if(var1.next != null) {
         var1.method3497();
      }

      var1.next = this.sentinel;
      var1.previous = this.sentinel.previous;
      var1.next.previous = var1;
      var1.previous.next = var1;
   }

   @ObfuscatedName("y")
   public boolean method5026() {
      return this.sentinel.previous == this.sentinel;
   }

   @ObfuscatedName("z")
   public void method5032() {
      while(this.sentinel.previous != this.sentinel) {
         this.sentinel.previous.method3497();
      }

   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(Lfx;)Z"
   )
   boolean method5029(Node var1) {
      this.method5019(var1);
      return true;
   }

   public Iterator iterator() {
      return new IterableNodeDequeDescendingIterator(this);
   }

   public int size() {
      return this.method5025();
   }

   public boolean isEmpty() {
      return this.method5026();
   }

   public boolean contains(Object var1) {
      throw new RuntimeException();
   }

   public Object[] toArray(Object[] var1) {
      int var2 = 0;

      for(Node var3 = this.sentinel.previous; var3 != this.sentinel; var3 = var3.previous) {
         var1[var2++] = var3;
      }

      return var1;
   }

   public void clear() {
      this.method5032();
   }

   public boolean add(Object var1) {
      return this.method5029((Node)var1);
   }

   public boolean equals(Object var1) {
      return super.equals(var1);
   }

   public boolean retainAll(Collection var1) {
      throw new RuntimeException();
   }

   public int hashCode() {
      return super.hashCode();
   }

   public boolean containsAll(Collection var1) {
      throw new RuntimeException();
   }

   public boolean addAll(Collection var1) {
      throw new RuntimeException();
   }

   public boolean remove(Object var1) {
      throw new RuntimeException();
   }

   public Object[] toArray() {
      return this.method5070();
   }

   public boolean removeAll(Collection var1) {
      throw new RuntimeException();
   }

   public RSNode getCurrent() {
      return this.sentinel;
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "()[Lfx;"
   )
   Node[] method5070() {
      Node[] var1 = new Node[this.method5025()];
      int var2 = 0;

      for(Node var3 = this.sentinel.previous; var3 != this.sentinel; var3 = var3.previous) {
         var1[var2++] = var3;
      }

      return var1;
   }

   @ObfuscatedName("m")
   int method5025() {
      int var1 = 0;

      for(Node var2 = this.sentinel.previous; var2 != this.sentinel; var2 = var2.previous) {
         ++var1;
      }

      return var1;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lfx;Lfx;)V"
   )
   public static void method5021(Node var0, Node var1) {
      if(var0.next != null) {
         var0.method3497();
      }

      var0.next = var1;
      var0.previous = var1.previous;
      var0.next.previous = var0;
      var0.previous.next = var0;
   }
}
