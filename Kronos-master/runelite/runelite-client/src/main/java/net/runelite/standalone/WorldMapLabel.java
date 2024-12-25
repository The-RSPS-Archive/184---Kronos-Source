package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("aw")
public class WorldMapLabel {
   @ObfuscatedName("cj")
   @ObfuscatedSignature(
      signature = "Lbn;"
   )
   static MouseRecorder mouseRecorder;
   @ObfuscatedName("db")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   static Archive archive19;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -38349507
   )
   int width;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lg;"
   )
   WorldMapLabelSize size;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -212014043
   )
   int height;
   @ObfuscatedName("z")
   String text;

   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;IILg;)V"
   )
   WorldMapLabel(String var1, int var2, int var3, WorldMapLabelSize var4) {
      this.text = var1;
      this.width = var2;
      this.height = var3;
      this.size = var4;
   }

   @ObfuscatedName("ah")
   @ObfuscatedSignature(
      signature = "(ILhb;ZI)V",
      garbageValue = "203573450"
   )
   static void method743(int var0, Coord var1, boolean var2) {
      WorldMapArea var3 = Decimator.method2485().method5899(var0);
      int var4 = class215.localPlayer.plane;
      int var5 = (class215.localPlayer.x >> 7) + class215.baseX;
      int var6 = (class215.localPlayer.y * 682054857 >> 7) + class304.baseY;
      Coord var7 = new Coord(var4, var5, var6);
      Decimator.method2485().method6058(var3, var7, var1, var2);
   }

   @ObfuscatedName("kq")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "125"
   )
   static final void method742() {
      PacketBufferNode var0 = InterfaceParent.method1140(ClientPacket.field2393, Client.packetWriter.isaacCipher);
      var0.packetBuffer.writeByte(0);
      Client.packetWriter.method1622(var0);
   }
}
