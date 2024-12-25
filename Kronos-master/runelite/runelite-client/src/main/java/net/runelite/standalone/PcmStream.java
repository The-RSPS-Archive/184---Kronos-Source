package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSPcmStream;

@ObfuscatedName("dc")
public abstract class PcmStream extends Node implements RSPcmStream {
   @ObfuscatedName("t")
   @ObfuscatedSignature(
      signature = "Ldc;"
   )
   PcmStream after;
   @ObfuscatedName("w")
   volatile boolean active;
   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "Ldx;"
   )
   AbstractSound sound;
   @ObfuscatedName("g")
   int field1325;

   protected PcmStream() {
      this.active = true;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "()Ldc;"
   )
   protected abstract PcmStream vmethod3775();

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "()Ldc;"
   )
   protected abstract PcmStream vmethod3794();

   @ObfuscatedName("y")
   protected abstract void vmethod3777(int[] var1, int var2, int var3);

   @ObfuscatedName("ah")
   int vmethod2542() {
      return 255;
   }

   @ObfuscatedName("c")
   protected abstract void vmethod3778(int var1);

   @ObfuscatedName("fh")
   final void method2327(int[] var1, int var2, int var3) {
      if(this.active) {
         this.vmethod3777(var1, var2, var3);
      } else {
         this.vmethod3778(var3);
      }

   }

   @ObfuscatedName("m")
   protected abstract int vmethod3787();
}
