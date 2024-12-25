package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("fd")
public class BuddyRankComparator extends AbstractUserComparator {
   @ObfuscatedName("z")
   final boolean reversed;

   public BuddyRankComparator(boolean var1) {
      this.reversed = var1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljl;Ljl;I)I",
      garbageValue = "-1443378495"
   )
   int method3384(Buddy var1, Buddy var2) {
      return var2.rank != var1.rank?(this.reversed?var1.rank - var2.rank:var2.rank - var1.rank):this.method5015(var1, var2);
   }

   public int compare(Object var1, Object var2) {
      return this.method3384((Buddy)var1, (Buddy)var2);
   }

   @ObfuscatedName("hs")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "120"
   )
   static final int method3386() {
      return Client.menuOptionsCount - 1;
   }
}
