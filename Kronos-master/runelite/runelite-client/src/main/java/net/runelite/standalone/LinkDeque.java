package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("je")
public class LinkDeque {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lfs;"
   )
   Link current;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lfs;"
   )
   Link sentinel;

   public LinkDeque() {
      this.sentinel = new Link();
      this.sentinel.previous = this.sentinel;
      this.sentinel.next = this.sentinel;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "()Lfs;"
   )
   public Link method4872() {
      Link var1 = this.sentinel.previous;
      if(var1 == this.sentinel) {
         this.current = null;
         return null;
      } else {
         this.current = var1.previous;
         return var1;
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "()Lfs;"
   )
   public Link method4874() {
      Link var1 = this.current;
      if(var1 == this.sentinel) {
         this.current = null;
         return null;
      } else {
         this.current = var1.previous;
         return var1;
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lfs;)V"
   )
   public void method4871(Link var1) {
      if(var1.next != null) {
         var1.method3451();
      }

      var1.next = this.sentinel.next;
      var1.previous = this.sentinel;
      var1.next.previous = var1;
      var1.previous.next = var1;
   }
}
