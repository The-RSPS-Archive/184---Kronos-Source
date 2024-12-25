package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("fl")
public class ByteArrayNode extends Node {
   @ObfuscatedName("z")
   public byte[] byteArray;

   public ByteArrayNode(byte[] var1) {
      this.byteArray = var1;
   }
}
