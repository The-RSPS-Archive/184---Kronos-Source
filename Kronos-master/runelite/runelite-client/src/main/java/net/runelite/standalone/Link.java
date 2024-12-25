package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("fs")
public class Link {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lfs;"
   )
   public Link next;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lfs;"
   )
   public Link previous;

   @ObfuscatedName("z")
   public void method3451() {
      if(this.next != null) {
         this.next.previous = this.previous;
         this.previous.next = this.next;
         this.previous = null;
         this.next = null;
      }
   }
}
