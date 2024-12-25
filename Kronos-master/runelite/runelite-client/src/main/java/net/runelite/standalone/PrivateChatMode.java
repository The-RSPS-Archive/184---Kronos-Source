package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kc")
public class PrivateChatMode {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lkc;"
   )
   public static final PrivateChatMode field3632;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lkc;"
   )
   static final PrivateChatMode field3633;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lkc;"
   )
   static final PrivateChatMode field3631;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -1358406113
   )
   public final int field3634;

   static {
      field3631 = new PrivateChatMode(0);
      field3632 = new PrivateChatMode(1);
      field3633 = new PrivateChatMode(2);
   }

   PrivateChatMode(int var1) {
      this.field3634 = var1;
   }
}
