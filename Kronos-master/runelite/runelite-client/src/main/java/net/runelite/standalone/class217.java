package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("hk")
public class class217 {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lht;"
   )
   public static Huffman huffman;

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(ILjava/lang/String;Ljava/lang/String;B)V",
      garbageValue = "-67"
   )
   static void sendGameMessage(int var0, String var1, String var2) {
      WorldMapData_1.method514(var0, var1, var2, (String)null);
   }

   @ObfuscatedName("fl")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "2143242016"
   )
   static void method3954() {
      PacketBufferNode var0 = InterfaceParent.method1140(ClientPacket.field2412, Client.packetWriter.isaacCipher);
      var0.packetBuffer.writeByte(class256.method4656());
      var0.packetBuffer.method5481(FloorDecoration.canvasWidth);
      var0.packetBuffer.method5481(WallDecoration.canvasHeight);
      Client.packetWriter.method1622(var0);
   }
}
