package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("dm")
public class SoundEnvelope {
   @ObfuscatedName("n")
   int[] durations;
   @ObfuscatedName("p")
   int form;
   @ObfuscatedName("q")
   int ticks;
   @ObfuscatedName("r")
   int end;
   @ObfuscatedName("u")
   int start;
   @ObfuscatedName("v")
   int[] phases;
   @ObfuscatedName("y")
   int max;
   @ObfuscatedName("z")
   int segments;
   @ObfuscatedName("c")
   int amplitude;
   @ObfuscatedName("i")
   int step;
   @ObfuscatedName("m")
   int phaseIndex;

   SoundEnvelope() {
      this.segments = 2;
      this.durations = new int[2];
      this.phases = new int[2];
      this.durations[0] = 0;
      this.durations[1] = 65535;
      this.phases[0] = 0;
      this.phases[1] = 65535;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;)V"
   )
   final void method2445(Buffer var1) {
      this.segments = var1.readUnsignedByte();
      this.durations = new int[this.segments];
      this.phases = new int[this.segments];

      for(int var2 = 0; var2 < this.segments; ++var2) {
         this.durations[var2] = var1.readUnsignedShort();
         this.phases[var2] = var1.readUnsignedShort();
      }

   }

   @ObfuscatedName("u")
   final int method2454(int var1) {
      if(this.max >= this.ticks) {
         this.amplitude = this.phases[this.phaseIndex++] << 15;
         if(this.phaseIndex >= this.segments) {
            this.phaseIndex = this.segments - 1;
         }

         this.ticks = (int)((double)this.durations[this.phaseIndex] / 65536.0D * (double)var1);
         if(this.ticks > this.max) {
            this.step = ((this.phases[this.phaseIndex] << 15) - this.amplitude) / (this.ticks - this.max);
         }
      }

      this.amplitude += this.step;
      ++this.max;
      return this.amplitude - this.step >> 15;
   }

   @ObfuscatedName("v")
   final void method2456() {
      this.ticks = 0;
      this.phaseIndex = 0;
      this.step = 0;
      this.amplitude = 0;
      this.max = 0;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lkl;)V"
   )
   final void method2448(Buffer var1) {
      this.form = var1.readUnsignedByte();
      this.start = var1.readInt();
      this.end = var1.readInt();
      this.method2445(var1);
   }
}
