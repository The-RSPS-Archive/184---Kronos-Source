package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kk")
public class class298 {
   @ObfuscatedName("q")
   static byte[][][] field3719;
   @ObfuscatedName("z")
   static final char[] cp1252AsciiExtension;

   static {
      cp1252AsciiExtension = new char[]{'€', '\u0000', '‚', 'ƒ', '„', '…', '†', '‡', 'ˆ', '‰', 'Š', '‹', 'Œ', '\u0000', 'Ž', '\u0000', '\u0000', '‘', '’', '“', '”', '•', '–', '—', '˜', '™', 'š', '›', 'œ', '\u0000', 'ž', 'Ÿ'};
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(DDII)[D",
      garbageValue = "-1793531075"
   )
   public static double[] method5477(double var0, double var2, int var4) {
      int var5 = var4 * 2 + 1;
      double[] var6 = new double[var5];
      int var7 = -var4;

      for(int var8 = 0; var7 <= var4; ++var8) {
         var6[var8] = TaskHandler.method3415((double)var7, var0, var2);
         ++var7;
      }

      return var6;
   }

   @ObfuscatedName("kf")
   @ObfuscatedSignature(
      signature = "(IIIZI)V",
      garbageValue = "326242369"
   )
   public static void method5476(int var0, int var1, int var2, boolean var3) {
      PacketBufferNode var4 = InterfaceParent.method1140(ClientPacket.field2347, Client.packetWriter.isaacCipher);
      var4.packetBuffer.method5515(var2);
      var4.packetBuffer.method5595(var3?Client.field926:0);
      var4.packetBuffer.method5481(var1);
      var4.packetBuffer.method5532(var0);
      Client.packetWriter.method1622(var4);
   }
}
