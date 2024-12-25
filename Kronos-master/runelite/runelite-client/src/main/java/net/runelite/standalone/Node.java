package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSNode;

@ObfuscatedName("fx")
public class Node implements RSNode {
   @ObfuscatedName("cl")
   public long key;
   @ObfuscatedName("cq")
   @ObfuscatedSignature(
      signature = "Lfx;"
   )
   public Node next;
   @ObfuscatedName("cw")
   @ObfuscatedSignature(
      signature = "Lfx;"
   )
   public Node previous;

   public void onUnlink() {
   }

   public void rl$unlink() {
      this.onUnlink();
   }

   public long getHash() {
      return this.key;
   }

   public RSNode getNext() {
      return this.previous;
   }

   public RSNode getPrevious() {
      return this.next;
   }

   public void unlink() {
      this.method3497();
   }

   @ObfuscatedName("fa")
   public boolean method3494() {
      return this.next != null;
   }

   @ObfuscatedName("fu")
   public void method3497() {
      this.rl$unlink();
      if(this.next != null) {
         this.next.previous = this.previous;
         this.previous.next = this.next;
         this.previous = null;
         this.next = null;
      }
   }
}
