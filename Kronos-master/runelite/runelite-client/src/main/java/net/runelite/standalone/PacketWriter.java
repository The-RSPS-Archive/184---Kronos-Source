package net.runelite.standalone;

import java.io.IOException;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("cl")
public class PacketWriter {
   @ObfuscatedName("gt")
   @ObfuscatedSignature(
      signature = "Lex;"
   )
   static Scene scene;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Ljs;"
   )
   IterableNodeDeque packetBufferNodes;
   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "Lgv;"
   )
   ServerPacket field828;
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Lkf;"
   )
   PacketBuffer packetBuffer;
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Lgv;"
   )
   ServerPacket serverPacket;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Llg;"
   )
   public IsaacCipher isaacCipher;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lkl;"
   )
   Buffer buffer;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1980831337
   )
   int bufferSize;
   @ObfuscatedName("y")
   boolean field827;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lky;"
   )
   AbstractSocket socket;
   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "Lgv;"
   )
   ServerPacket field838;
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "Lgv;"
   )
   ServerPacket field837;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 1159394087
   )
   int pendingWrites;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = -1246763735
   )
   int field834;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 1968732153
   )
   int serverPacketLength;

   PacketWriter() {
      this.packetBufferNodes = new IterableNodeDeque();
      this.bufferSize = 0;
      this.buffer = new Buffer(5000);
      this.packetBuffer = new PacketBuffer(40000);
      this.serverPacket = null;
      this.serverPacketLength = 0;
      this.field827 = true;
      this.field834 = 0;
      this.pendingWrites = 0;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1302279031"
   )
   final void method1619() throws IOException {
      if(this.socket != null && this.bufferSize > 0) {
         this.buffer.offset = 0;

         while(true) {
            PacketBufferNode var1 = (PacketBufferNode)this.packetBufferNodes.method5044();
            if(var1 == null || var1.index > this.buffer.array.length - this.buffer.offset) {
               this.socket.vmethod5817(this.buffer.array, 0, this.buffer.offset);
               this.pendingWrites = 0;
               break;
            }

            this.buffer.writeBytes(var1.packetBuffer.array, 0, var1.index);
            this.bufferSize -= var1.index;
            var1.method3497();
            var1.packetBuffer.method5479();
            var1.method3764();
         }
      }

   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-54"
   )
   void method1637() {
      this.socket = null;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(I)Lky;",
      garbageValue = "1051298543"
   )
   AbstractSocket method1624() {
      return this.socket;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1724388420"
   )
   void method1623() {
      if(this.socket != null) {
         this.socket.vmethod5821();
         this.socket = null;
      }

   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lky;I)V",
      garbageValue = "750746684"
   )
   void method1621(AbstractSocket var1) {
      this.socket = var1;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lgn;I)V",
      garbageValue = "1433741323"
   )
   public final void method1622(PacketBufferNode var1) {
      this.packetBufferNodes.method5019(var1);
      var1.index = var1.packetBuffer.offset;
      var1.packetBuffer.offset = 0;
      this.bufferSize += var1.index;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1613977862"
   )
   final void method1618() {
      this.packetBufferNodes.method5032();
      this.bufferSize = 0;
   }
}
