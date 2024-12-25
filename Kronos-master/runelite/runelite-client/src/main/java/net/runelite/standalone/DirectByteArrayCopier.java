package net.runelite.standalone;

import java.io.IOException;
import java.nio.ByteBuffer;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gj")
public class DirectByteArrayCopier extends AbstractByteArrayCopier {
   @ObfuscatedName("eu")
   static int[] field2133;
   @ObfuscatedName("l")
   static int[] BZip2Decompressor_block;
   @ObfuscatedName("z")
   ByteBuffer directBuffer;

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "([BI)V",
      garbageValue = "-793496359"
   )
   void vmethod3858(byte[] var1) {
      this.directBuffer = ByteBuffer.allocateDirect(var1.length);
      this.directBuffer.position(0);
      this.directBuffer.put(var1);
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(B)[B",
      garbageValue = "-37"
   )
   byte[] vmethod3857() {
      byte[] var1 = new byte[this.directBuffer.capacity()];
      this.directBuffer.position(0);
      this.directBuffer.get(var1);
      return var1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(ZI)V",
      garbageValue = "-2052817052"
   )
   public static void method3744(boolean var0) {
      if(NetCache.NetCache_socket != null) {
         try {
            Buffer var1 = new Buffer(4);
            var1.writeByte(var0?2:3);
            var1.write24BitInteger(0);
            NetCache.NetCache_socket.vmethod5817(var1.array, 0, 4);
         } catch (IOException var4) {
            try {
               NetCache.NetCache_socket.vmethod5821();
            } catch (Exception var3) {
               ;
            }

            ++NetCache.NetCache_ioExceptions;
            NetCache.NetCache_socket = null;
         }

      }
   }
}
