package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("lc")
public class class317 {
   @ObfuscatedName("c")
   static final int[] field3892;
   @ObfuscatedName("i")
   static final int[] field3888;

   static {
      field3888 = new int[2048];
      field3892 = new int[2048];
      double var0 = 0.0030679615757712823D;

      for(int var2 = 0; var2 < 2048; ++var2) {
         field3888[var2] = (int)(65536.0D * Math.sin(var0 * (double)var2));
         field3892[var2] = (int)(65536.0D * Math.cos((double)var2 * var0));
      }

   }
}
