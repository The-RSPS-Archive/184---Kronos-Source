package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gn")
public class PacketBufferNode extends Node {
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 6984373
   )
   static int PacketBufferNode_packetBufferNodeCount;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "[Lgn;"
   )
   static PacketBufferNode[] PacketBufferNode_packetBufferNodes;
   @ObfuscatedName("y")
   public static byte[][] SpriteBuffer_pixels;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -1214677313
   )
   int clientPacketLength;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 232074557
   )
   public int index;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lkf;"
   )
   public PacketBuffer packetBuffer;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lgy;"
   )
   ClientPacket clientPacket;

   static {
      PacketBufferNode_packetBufferNodes = new PacketBufferNode[300];
      PacketBufferNode_packetBufferNodeCount = 0;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-873176834"
   )
   public void method3764() {
      if(PacketBufferNode_packetBufferNodeCount < PacketBufferNode_packetBufferNodes.length) {
         PacketBufferNode_packetBufferNodes[++PacketBufferNode_packetBufferNodeCount - 1] = this;
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(II)Lil;",
      garbageValue = "1677952312"
   )
   public static NPCDefinition getNpcDefinition(int var0) {
      NPCDefinition var1 = (NPCDefinition)NPCDefinition.NpcDefinition_cached.method3032((long)var0);
      if(var1 != null) {
         return var1;
      } else {
         byte[] var2 = NPCDefinition.NpcDefinition_archive.method4020(9, var0, (short)-14113);
         var1 = new NPCDefinition();
         var1.id = var0;
         if(var2 != null) {
            var1.method4429(new Buffer(var2));
            var1.postDecode(); //TODO: Modified
         }

         var1.method4402();
         NPCDefinition.NpcDefinition_cached.method3034(var1, (long)var0);
         return var1;
      }
   }
}
