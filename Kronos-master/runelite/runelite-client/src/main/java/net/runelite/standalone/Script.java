package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSScript;

@ObfuscatedName("cu")
public class Script extends DualNode implements RSScript {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   static EvictingDualNodeHashTable Script_cached;
   @ObfuscatedName("dh")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   static Archive archive15;
   @ObfuscatedName("n")
   int[] opcodes;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -473600855
   )
   int localStringCount;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -171399011
   )
   int intArgumentCount;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1534316869
   )
   int localIntCount;
   @ObfuscatedName("u")
   String[] stringOperands;
   @ObfuscatedName("v")
   int[] intOperands;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "[Llb;"
   )
   IterableNodeHashTable[] switches;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = -1063868631
   )
   int stringArgumentCount;

   static {
      Script_cached = new EvictingDualNodeHashTable(128);
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(II)[Llb;",
      garbageValue = "1885034841"
   )
   IterableNodeHashTable[] method2200(int var1) {
      return new IterableNodeHashTable[var1];
   }

   public int[] getInstructions() {
      return this.opcodes;
   }

   public int[] getIntOperands() {
      return this.intOperands;
   }
}
