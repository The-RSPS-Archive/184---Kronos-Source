package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("fv")
public class Task {
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 1191522307
   )
   int type;
   @ObfuscatedName("q")
   public int intArgument;
   @ObfuscatedName("r")
   public volatile int status;
   @ObfuscatedName("y")
   public volatile Object result;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lfv;"
   )
   Task next;
   @ObfuscatedName("m")
   Object objectArgument;

   Task() {
      this.status = 0;
   }
}
