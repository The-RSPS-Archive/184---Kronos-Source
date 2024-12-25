package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("dq")
public class SoundCache {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   AbstractArchive musicSampleIndex;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Llq;"
   )
   NodeHashTable rawSounds;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Llq;"
   )
   NodeHashTable musicSamples;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   AbstractArchive soundEffectIndex;

   @ObfuscatedSignature(
      signature = "(Lhp;Lhp;)V"
   )
   public SoundCache(AbstractArchive var1, AbstractArchive var2) {
      this.musicSamples = new NodeHashTable(256);
      this.rawSounds = new NodeHashTable(256);
      this.soundEffectIndex = var1;
      this.musicSampleIndex = var2;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(II[II)Lch;",
      garbageValue = "2026666010"
   )
   RawSound method2472(int var1, int var2, int[] var3) {
      int var4 = var2 ^ (var1 << 4 & 65535 | var1 >>> 12);
      var4 |= var1 << 16;
      long var5 = (long)var4 ^ 4294967296L;
      RawSound var7 = (RawSound)this.rawSounds.method6346(var5);
      if(var7 != null) {
         return var7;
      } else if(var3 != null && var3[0] <= 0) {
         return null;
      } else {
         VorbisSample var8 = (VorbisSample)this.musicSamples.method6346(var5);
         if(var8 == null) {
            var8 = VorbisSample.method2143(this.musicSampleIndex, var1, var2);
            if(var8 == null) {
               return null;
            }

            this.musicSamples.put(var8, var5);
         }

         var7 = var8.method2141(var3);
         if(var7 == null) {
            return null;
         } else {
            var8.method3497();
            this.rawSounds.put(var7, var5);
            return var7;
         }
      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I[II)Lch;",
      garbageValue = "-1432840715"
   )
   public RawSound method2469(int var1, int[] var2) {
      if(this.musicSampleIndex.method4033() == 1) {
         return this.method2472(0, var1, var2);
      } else if(this.musicSampleIndex.fileCount(var1) == 1) {
         return this.method2472(var1, 0, var2);
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I[II)Lch;",
      garbageValue = "223393704"
   )
   public RawSound method2470(int var1, int[] var2) {
      if(this.soundEffectIndex.method4033() == 1) {
         return this.method2484(0, var1, var2);
      } else if(this.soundEffectIndex.fileCount(var1) == 1) {
         return this.method2484(var1, 0, var2);
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(II[II)Lch;",
      garbageValue = "1393796182"
   )
   RawSound method2484(int var1, int var2, int[] var3) {
      int var4 = var2 ^ (var1 << 4 & 65535 | var1 >>> 12);
      var4 |= var1 << 16;
      long var5 = (long)var4;
      RawSound var7 = (RawSound)this.rawSounds.method6346(var5);
      if(var7 != null) {
         return var7;
      } else if(var3 != null && var3[0] <= 0) {
         return null;
      } else {
         SoundEffect var8 = SoundEffect.method2092(this.soundEffectIndex, var1, var2);
         if(var8 == null) {
            return null;
         } else {
            var7 = var8.method2086();
            this.rawSounds.put(var7, var5);
            if(var3 != null) {
               var3[0] -= var7.samples.length;
            }

            return var7;
         }
      }
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/CharSequence;IZI)I",
      garbageValue = "2108549296"
   )
   static int method2476(CharSequence var0, int var1, boolean var2) {
      if(var1 >= 2 && var1 <= 36) {
         boolean var3 = false;
         boolean var4 = false;
         int var5 = 0;
         int var6 = var0.length();

         for(int var7 = 0; var7 < var6; ++var7) {
            char var8 = var0.charAt(var7);
            if(var7 == 0) {
               if(var8 == '-') {
                  var3 = true;
                  continue;
               }

               if(var8 == '+') {
                  continue;
               }
            }

            int var10;
            if(var8 >= '0' && var8 <= '9') {
               var10 = var8 - '0';
            } else if(var8 >= 'A' && var8 <= 'Z') {
               var10 = var8 - '7';
            } else {
               if(var8 < 'a' || var8 > 'z') {
                  throw new NumberFormatException();
               }

               var10 = var8 - 'W';
            }

            if(var10 >= var1) {
               throw new NumberFormatException();
            }

            if(var3) {
               var10 = -var10;
            }

            int var9 = var5 * var1 + var10;
            if(var9 / var1 != var5) {
               throw new NumberFormatException();
            }

            var5 = var9;
            var4 = true;
         }

         if(!var4) {
            throw new NumberFormatException();
         } else {
            return var5;
         }
      } else {
         throw new IllegalArgumentException("" + var1);
      }
   }
}
