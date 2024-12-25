package net.runelite.standalone;

import java.io.IOException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ky")
public abstract class AbstractSocket {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "1784126558"
   )
   public abstract int vmethod5815() throws IOException;

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "([BIIB)V",
      garbageValue = "6"
   )
   public abstract void vmethod5817(byte[] var1, int var2, int var3) throws IOException;

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "([BIII)I",
      garbageValue = "-2035668362"
   )
   public abstract int vmethod5832(byte[] var1, int var2, int var3) throws IOException;

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(II)Z",
      garbageValue = "-336706705"
   )
   public abstract boolean vmethod5816(int var1) throws IOException;

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "3"
   )
   public abstract int vmethod5838() throws IOException;

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-119"
   )
   public abstract void vmethod5821();
}
