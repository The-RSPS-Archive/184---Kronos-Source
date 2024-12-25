package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ke")
public final class class292 {
   @ObfuscatedName("n")
   static long[] field3643;
   @ObfuscatedName("z")
   public static final char[] base37Table;

   static {
      base37Table = new char[]{'_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
      field3643 = new long[12];

      for(int var0 = 0; var0 < field3643.length; ++var0) {
         field3643[var0] = (long)Math.pow(37.0D, (double)var0);
      }

   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/CharSequence;I)I",
      garbageValue = "757556043"
   )
   public static int method5270(CharSequence var0) {
      int var1 = var0.length();
      int var2 = 0;

      for(int var3 = 0; var3 < var1; ++var3) {
         var2 = (var2 << 5) - var2 + var0.charAt(var3);
      }

      return var2;
   }
}
