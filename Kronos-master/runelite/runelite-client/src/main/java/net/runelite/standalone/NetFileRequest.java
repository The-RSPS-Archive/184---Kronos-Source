package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("hc")
public class NetFileRequest extends DualNode {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -461319929
   )
   int crc;
   @ObfuscatedName("v")
   byte padding;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   Archive archive;
}
