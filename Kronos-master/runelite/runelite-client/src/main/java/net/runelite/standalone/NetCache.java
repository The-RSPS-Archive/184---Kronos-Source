package net.runelite.standalone;

import java.util.zip.CRC32;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("io")
public class NetCache {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -76407529
   )
   static int NetCache_loadTime;
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Llq;"
   )
   static NodeHashTable NetCache_pendingPriorityResponses;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = 1183889597
   )
   static int NetCache_pendingPriorityResponsesCount;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 2004934077
   )
   static int NetCache_pendingPriorityWritesCount;
   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "[Lie;"
   )
   static Archive[] NetCache_archives;
   @ObfuscatedName("t")
   @ObfuscatedGetter(
      intValue = 361699237
   )
   static int field3271;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Llq;"
   )
   static NodeHashTable NetCache_pendingPriorityWrites;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      longValue = -6661371924416565423L
   )
   static long field3273;
   @ObfuscatedName("x")
   static CRC32 NetCache_crc;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "Llq;"
   )
   static NodeHashTable NetCache_pendingWrites;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lky;"
   )
   static AbstractSocket NetCache_socket;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = 1880332209
   )
   static int NetCache_pendingResponsesCount;
   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "Llq;"
   )
   static NodeHashTable NetCache_pendingResponses;
   @ObfuscatedName("d")
   @ObfuscatedGetter(
      intValue = -703948451
   )
   public static int NetCache_ioExceptions;
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "Lkl;"
   )
   static Buffer NetCache_responseHeaderBuffer;
   @ObfuscatedName("f")
   static byte field3287;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 809384467
   )
   static int NetCache_pendingWritesCount;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = 2096105199
   )
   public static int NetCache_crcMismatches;
   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "Lic;"
   )
   static DualNodeDeque NetCache_pendingWritesQueue;

   static {
      NetCache_loadTime = 0;
      NetCache_pendingPriorityWrites = new NodeHashTable(4096);
      NetCache_pendingPriorityWritesCount = 0;
      NetCache_pendingPriorityResponses = new NodeHashTable(32);
      NetCache_pendingPriorityResponsesCount = 0;
      NetCache_pendingWritesQueue = new DualNodeDeque();
      NetCache_pendingWrites = new NodeHashTable(4096);
      NetCache_pendingWritesCount = 0;
      NetCache_pendingResponses = new NodeHashTable(4096);
      NetCache_pendingResponsesCount = 0;
      NetCache_responseHeaderBuffer = new Buffer(8);
      field3271 = 0;
      NetCache_crc = new CRC32();
      NetCache_archives = new Archive[256];
      field3287 = 0;
      NetCache_crcMismatches = 0;
      NetCache_ioExceptions = 0;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IIS)Z",
      garbageValue = "20573"
   )
   static boolean method4466(int var0, int var1) {
      return var0 != 4 || var1 < 8;
   }

   @ObfuscatedName("jk")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;B)V",
      garbageValue = "-108"
   )
   static final void method4465(String var0) {
      if(Varps.clanChat != null) {
         PacketBufferNode var1 = InterfaceParent.method1140(ClientPacket.field2371, Client.packetWriter.isaacCipher);
         var1.packetBuffer.writeByte(class267.method4877(var0));
         var1.packetBuffer.writeString(var0);
         Client.packetWriter.method1622(var1);
      }
   }

   @ObfuscatedName("kl")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "-101"
   )
   static void method4449(int var0) {
      Client.oculusOrbState = var0;
   }
}
