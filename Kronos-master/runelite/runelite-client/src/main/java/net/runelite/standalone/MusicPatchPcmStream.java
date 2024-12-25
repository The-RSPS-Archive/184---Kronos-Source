package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gp")
public class MusicPatchPcmStream extends PcmStream {
   @ObfuscatedName("r")
   public static int[] SpriteBuffer_yOffsets;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Ljv;"
   )
   NodeDeque queue;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lca;"
   )
   PcmStreamMixer mixer;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lgb;"
   )
   MidiPcmStream superStream;

   @ObfuscatedSignature(
      signature = "(Lgb;)V"
   )
   MusicPatchPcmStream(MidiPcmStream var1) {
      this.queue = new NodeDeque();
      this.mixer = new PcmStreamMixer();
      this.superStream = var1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lgu;IB)V",
      garbageValue = "66"
   )
   void method3780(MusicPatchNode var1, int var2) {
      if((this.superStream.field2067[var1.field2214] & 4) != 0 && var1.field2227 < 0) {
         int var3 = this.superStream.field2064[var1.field2214] / UrlRequest.PcmPlayer_sampleRate;
         int var4 = (var3 + 1048575 - var1.field2213) / var3;
         var1.field2213 = var3 * var2 + var1.field2213 & 1048575;
         if(var4 <= var2) {
            if(this.superStream.field2071[var1.field2214] == 0) {
               var1.stream = RawPcmStream.method2577(var1.rawSound, var1.stream.method2560(), var1.stream.method2551(), var1.stream.method2552());
            } else {
               var1.stream = RawPcmStream.method2577(var1.rawSound, var1.stream.method2560(), 0, var1.stream.method2552());
               this.superStream.method3544(var1, var1.patch.field2135[var1.field2218] < 0);
            }

            if(var1.patch.field2135[var1.field2218] < 0) {
               var1.stream.method2547(-1);
            }

            var2 = var1.field2213 / var3;
         }
      }

      var1.stream.vmethod3778(var2);
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "()Ldc;"
   )
   protected PcmStream vmethod3775() {
      MusicPatchNode var1 = (MusicPatchNode)this.queue.method5103();
      return (PcmStream)(var1 == null?null:(var1.stream != null?var1.stream:this.vmethod3794()));
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "()Ldc;"
   )
   protected PcmStream vmethod3794() {
      MusicPatchNode var1;
      do {
         var1 = (MusicPatchNode)this.queue.method5126();
         if(var1 == null) {
            return null;
         }
      } while(var1.stream == null);

      return var1.stream;
   }

   @ObfuscatedName("y")
   protected void vmethod3777(int[] var1, int var2, int var3) {
      this.mixer.vmethod3777(var1, var2, var3);

      for(MusicPatchNode var6 = (MusicPatchNode)this.queue.method5103(); var6 != null; var6 = (MusicPatchNode)this.queue.method5126()) {
         if(!this.superStream.method3566(var6)) {
            int var4 = var2;
            int var5 = var3;

            do {
               if(var5 <= var6.field2232) {
                  this.method3784(var6, var1, var4, var5, var4 + var5);
                  var6.field2232 -= var5;
                  break;
               }

               this.method3784(var6, var1, var4, var6.field2232, var4 + var5);
               var4 += var6.field2232;
               var5 -= var6.field2232;
            } while(!this.superStream.method3567(var6, var1, var4, var5));
         }
      }

   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lgu;[IIIII)V",
      garbageValue = "1901627378"
   )
   void method3784(MusicPatchNode var1, int[] var2, int var3, int var4, int var5) {
      if((this.superStream.field2067[var1.field2214] & 4) != 0 && var1.field2227 < 0) {
         int var6 = this.superStream.field2064[var1.field2214] / UrlRequest.PcmPlayer_sampleRate;

         while(true) {
            int var7 = (var6 + 1048575 - var1.field2213) / var6;
            if(var7 > var4) {
               var1.field2213 += var6 * var4;
               break;
            }

            var1.stream.vmethod3777(var2, var3, var7);
            var3 += var7;
            var4 -= var7;
            var1.field2213 += var7 * var6 - 1048576;
            int var8 = UrlRequest.PcmPlayer_sampleRate / 100;
            int var9 = 262144 / var6;
            if(var9 < var8) {
               var8 = var9;
            }

            RawPcmStream var10 = var1.stream;
            if(this.superStream.field2071[var1.field2214] == 0) {
               var1.stream = RawPcmStream.method2577(var1.rawSound, var10.method2560(), var10.method2551(), var10.method2552());
            } else {
               var1.stream = RawPcmStream.method2577(var1.rawSound, var10.method2560(), 0, var10.method2552());
               this.superStream.method3544(var1, var1.patch.field2135[var1.field2218] < 0);
               var1.stream.method2556(var8, var10.method2551());
            }

            if(var1.patch.field2135[var1.field2218] < 0) {
               var1.stream.method2547(-1);
            }

            var10.method2558(var8);
            var10.vmethod3777(var2, var3, var5 - var3);
            if(var10.method2562()) {
               this.mixer.method1475(var10);
            }
         }
      }

      var1.stream.vmethod3777(var2, var3, var4);
   }

   @ObfuscatedName("c")
   protected void vmethod3778(int var1) {
      this.mixer.vmethod3778(var1);

      for(MusicPatchNode var3 = (MusicPatchNode)this.queue.method5103(); var3 != null; var3 = (MusicPatchNode)this.queue.method5126()) {
         if(!this.superStream.method3566(var3)) {
            int var2 = var1;

            do {
               if(var2 <= var3.field2232) {
                  this.method3780(var3, var2);
                  var3.field2232 -= var2;
                  break;
               }

               this.method3780(var3, var3.field2232);
               var2 -= var3.field2232;
            } while(!this.superStream.method3567(var3, (int[])null, 0, var2));
         }
      }

   }

   @ObfuscatedName("m")
   protected int vmethod3787() {
      return 0;
   }

   @ObfuscatedName("gf")
   @ObfuscatedSignature(
      signature = "(IIII)I",
      garbageValue = "-781967444"
   )
   static final int method3798(int var0, int var1, int var2) {
      int var3 = var0 >> 7;
      int var4 = var1 >> 7;
      if(var3 >= 0 && var4 >= 0 && var3 <= 103 && var4 <= 103) {
         int var5 = var2;
         if(var2 < 3 && (Tiles.Tiles_renderFlags[1][var3][var4] & 2) == 2) {
            var5 = var2 + 1;
         }

         int var6 = var0 & 127;
         int var7 = var1 & 127;
         int var8 = (128 - var6) * Tiles.Tiles_heights[var5][var3][var4] + var6 * Tiles.Tiles_heights[var5][var3 + 1][var4] >> 7;
         int var9 = var6 * Tiles.Tiles_heights[var5][var3 + 1][var4 + 1] + Tiles.Tiles_heights[var5][var3][var4 + 1] * (128 - var6) >> 7;
         return var9 * var7 + var8 * (128 - var7) >> 7;
      } else {
         return 0;
      }
   }
}
