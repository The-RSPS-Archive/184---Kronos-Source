package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gd")
public class class183 {
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = 823451467
   )
   public static int musicTrackGroupId;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   static AbstractArchive SequenceDefinition_skeletonsArchive;
   @ObfuscatedName("z")
   static int[] field2097;

   static {
      new Object();
      field2097 = new int[33];
      field2097[0] = 0;
      int var0 = 2;

      for(int var1 = 1; var1 < 33; ++var1) {
         field2097[var1] = var0 - 1;
         var0 += var0;
      }

   }
}
