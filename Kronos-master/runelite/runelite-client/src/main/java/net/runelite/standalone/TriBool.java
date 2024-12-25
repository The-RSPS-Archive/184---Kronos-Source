package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("jm")
public class TriBool {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Ljm;"
   )
   public static final TriBool TriBool_true;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Ljm;"
   )
   public static final TriBool TriBool_false;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Ljm;"
   )
   public static final TriBool TriBool_unknown;

   static {
      TriBool_unknown = new TriBool();
      TriBool_true = new TriBool();
      TriBool_false = new TriBool();
   }
}
