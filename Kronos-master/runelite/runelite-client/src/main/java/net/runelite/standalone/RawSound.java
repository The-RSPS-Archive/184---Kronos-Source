package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSDecimator;
import net.runelite.rs.api.RSRawSound;

@ObfuscatedName("ch")
public class RawSound extends AbstractSound implements RSRawSound {
   @ObfuscatedName("n")
   public byte[] samples;
   @ObfuscatedName("r")
   public boolean field809;
   @ObfuscatedName("u")
   int end;
   @ObfuscatedName("v")
   public int start;
   @ObfuscatedName("z")
   public int sampleRate;

   RawSound(int var1, byte[] var2, int var3, int var4) {
      this.sampleRate = var1;
      this.samples = var2;
      this.start = var3;
      this.end = var4;
   }

   RawSound(int var1, byte[] var2, int var3, int var4, boolean var5) {
      this.sampleRate = var1;
      this.samples = var2;
      this.start = var3;
      this.end = var4;
      this.field809 = var5;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ldr;)Lch;"
   )
   public RawSound method1579(Decimator var1) {
      this.samples = var1.method2497(this.samples);
      this.sampleRate = var1.method2487(this.sampleRate);
      if(this.start == this.end) {
         this.start = this.end = var1.method2496(this.start);
      } else {
         this.start = var1.method2496(this.start);
         this.end = var1.method2496(this.end);
         if(this.start == this.end) {
            --this.start;
         }
      }

      return this;
   }

   public RSRawSound applyResampler(RSDecimator var1) {
      return this.method1579((Decimator)var1);
   }
}
