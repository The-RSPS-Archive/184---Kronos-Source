package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSNode;
import net.runelite.rs.api.RSNodeDeque;

@ObfuscatedName("jv")
public class NodeDeque implements RSNodeDeque {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lfx;"
   )
   Node current;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lfx;"
   )
   public Node sentinel;

   public NodeDeque() {
      this.sentinel = new Node();
      this.sentinel.previous = this.sentinel;
      this.sentinel.next = this.sentinel;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lfx;)V"
   )
   public void method5105(Node var1) {
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
      signature = "()Lfx;"
   )
   public Node method5109() {
      Node var1 = this.sentinel.next;
      if(var1 == this.sentinel) {
         return null;
      } else {
         var1.method3497();
         return var1;
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "()Lfx;"
   )
   public Node method5103() {
      Node var1 = this.sentinel.previous;
      if(var1 == this.sentinel) {
         this.current = null;
         return null;
      } else {
         this.current = var1.previous;
         return var1;
      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "()Lfx;"
   )
   public Node method5108() {
      Node var1 = this.sentinel.previous;
      if(var1 == this.sentinel) {
         return null;
      } else {
         var1.method3497();
         return var1;
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lfx;)V"
   )
   public void method5106(Node var1) {
      if(var1.next != null) {
         var1.method3497();
      }

      var1.next = this.sentinel;
      var1.previous = this.sentinel.previous;
      var1.next.previous = var1;
      var1.previous.next = var1;
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "()Lfx;"
   )
   public Node method5126() {
      Node var1 = this.current;
      if(var1 == this.sentinel) {
         this.current = null;
         return null;
      } else {
         this.current = var1.previous;
         return var1;
      }
   }

   @ObfuscatedName("z")
   public void method5104() {
      while(true) {
         Node var1 = this.sentinel.previous;
         if(var1 == this.sentinel) {
            this.current = null;
            return;
         }

         var1.method3497();
      }
   }

   public RSNode getHead() {
      return this.sentinel;
   }

   public RSNode getCurrent() {
      return this.current;
   }

   public void addFirst(RSNode var1) {
      this.method5105((Node)var1);
   }

   public RSNode last() {
      return this.method5103();
   }

   public RSNode previous() {
      return this.method5126();
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "()Lfx;"
   )
   public Node getPrevious() {
      Node var1 = this.current;
      if(var1 == this.sentinel) {
         this.current = null;
         return null;
      } else {
         this.current = var1.next;
         return var1;
      }
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "()Lfx;"
   )
   public Node getTail() {
      Node var1 = this.sentinel.next;
      if(var1 == this.sentinel) {
         this.current = null;
         return null;
      } else {
         this.current = var1.next;
         return var1;
      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lfx;Lfx;)V"
   )
   public static void method5130(Node var0, Node var1) {
      if(var0.next != null) {
         var0.method3497();
      }

      var0.next = var1.next;
      var0.previous = var1;
      var0.next.previous = var0;
      var0.previous.next = var0;
   }
}
