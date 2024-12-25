package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("jc")
public class class263 {
   @ObfuscatedName("y")
   static int[] field3528;

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(IIIZII)J",
      garbageValue = "1747811159"
   )
   public static long method4846(int var0, int var1, int var2, boolean var3, int var4) {
      long var5 = (long)((var0 & 127) << 0 | (var1 & 127) << 7 | (var2 & 3) << 14) | ((long)var4 & 4294967295L) << 17;
      if(var3) {
         var5 |= 65536L;
      }

      return var5;
   }
}
