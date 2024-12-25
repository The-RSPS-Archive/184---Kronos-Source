package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("fz")
public abstract class Clock {
   @ObfuscatedName("qq")
   @ObfuscatedGetter(
      intValue = -240800435
   )
   static int field2041;

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(III)I",
      garbageValue = "569029249"
   )
   public abstract int vmethod3511(int var1, int var2);

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-54"
   )
   public abstract void vmethod3510();

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "1644538233"
   )
   static final int method3513() {
      return ViewportMouse.ViewportMouse_x;
   }

   @ObfuscatedName("hf")
   @ObfuscatedSignature(
      signature = "(IIB)V",
      garbageValue = "-31"
   )
   static void method3518(int var0, int var1) {
      PacketBufferNode var2 = InterfaceParent.method1140(ClientPacket.field2341, Client.packetWriter.isaacCipher);
      var2.packetBuffer.method5595(var0);
      var2.packetBuffer.method5481(var1);
      Client.packetWriter.method1622(var2);
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(II)I",
      garbageValue = "1663360326"
   )
   static int method3519(int var0) {
      Message var1 = (Message)Messages.Messages_hashTable.method6061((long)var0);
      return var1 == null?-1:(var1.nextDual == Messages.Messages_queue.sentinel?-1:((Message)var1.nextDual).count);
   }
}
