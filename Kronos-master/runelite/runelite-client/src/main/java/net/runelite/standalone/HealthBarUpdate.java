package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSHealthBarUpdate;

@ObfuscatedName("bq")
public class HealthBarUpdate extends Node implements RSHealthBarUpdate {
   @ObfuscatedName("f")
   public static String field589;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -670451385
   )
   int health;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -809927427
   )
   int cycleOffset;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -326291957
   )
   int health2;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 2051178983
   )
   int cycle;

   HealthBarUpdate(int var1, int var2, int var3, int var4) {
      this.cycle = var1;
      this.health = var2;
      this.health2 = var3;
      this.cycleOffset = var4;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IIIII)V",
      garbageValue = "1094969728"
   )
   void method1259(int var1, int var2, int var3, int var4) {
      this.cycle = var1;
      this.health = var2;
      this.health2 = var3;
      this.cycleOffset = var4;
   }

   public int getHealthRatio() {
      return this.health;
   }

   public int getHealth() {
      return this.health2;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(III)Lbe;",
      garbageValue = "776889337"
   )
   static Message method1263(int var0, int var1) {
      ChatChannel var2 = (ChatChannel)Messages.Messages_channels.get(Integer.valueOf(var0));
      return var2.method1531(var1);
   }

   @ObfuscatedName("ff")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "146894465"
   )
   static final void method1258() {
      for(int var0 = 0; var0 < Client.soundEffectCount; ++var0) {
         --Client.queuedSoundEffectDelays[var0];
         if(Client.queuedSoundEffectDelays[var0] >= -10) {
            SoundEffect var9 = Client.soundEffects[var0];
            if(var9 == null) {
               Object var10000 = null;
               var9 = SoundEffect.method2092(GrandExchangeOfferAgeComparator.archive4, Client.soundEffectIds[var0], 0);
               if(var9 == null) {
                  continue;
               }

               Client.queuedSoundEffectDelays[var0] += var9.method2095();
               Client.soundEffects[var0] = var9;
            }

            if(Client.queuedSoundEffectDelays[var0] < 0) {
               int var2;
               if(Client.soundLocations[var0] != 0) {
                  int var3 = (Client.soundLocations[var0] & 255) * 128;
                  int var4 = Client.soundLocations[var0] >> 16 & 255;
                  int var5 = var4 * 128 + 64 - class215.localPlayer.x;
                  if(var5 < 0) {
                     var5 = -var5;
                  }

                  int var6 = Client.soundLocations[var0] >> 8 & 255;
                  int var7 = var6 * 128 + 64 - class215.localPlayer.y * 682054857;
                  if(var7 < 0) {
                     var7 = -var7;
                  }

                  int var8 = var5 + var7 - 128;
                  if(var8 > var3) {
                     Client.queuedSoundEffectDelays[var0] = -100;
                     continue;
                  }

                  if(var8 < 0) {
                     var8 = 0;
                  }

                  var2 = (var3 - var8) * Client.field1076 / var3;
               } else {
                  var2 = Client.soundEffectVolume;
               }

               if(var2 > 0) {
                  RawSound var10 = var9.method2086().method1579(Interpreter.decimator);
                  RawPcmStream var11 = RawPcmStream.method2601(var10, 100, var2);
                  var11.method2547(Client.queuedSoundEffectLoops[var0] - 1);
                  WorldMapLabelSize.pcmStreamMixer.method1475(var11);
               }

               Client.queuedSoundEffectDelays[var0] = -100;
            }
         } else {
            --Client.soundEffectCount;
            Client.queuedSoundEffectCountChanged(-1);

            for(int var1 = var0; var1 < Client.soundEffectCount; ++var1) {
               Client.soundEffectIds[var1] = Client.soundEffectIds[var1 + 1];
               Client.soundEffects[var1] = Client.soundEffects[var1 + 1];
               Client.queuedSoundEffectLoops[var1] = Client.queuedSoundEffectLoops[var1 + 1];
               Client.queuedSoundEffectDelays[var1] = Client.queuedSoundEffectDelays[var1 + 1];
               Client.soundLocations[var1] = Client.soundLocations[var1 + 1];
            }

            --var0;
         }
      }

      if(Client.field967) {
         boolean var12;
         if(class197.field2173 != 0) {
            var12 = true;
         } else {
            var12 = class38.midiPcmStream.method3551();
         }

         if(!var12) {
            if(Client.field969 != 0 && Client.field874 != -1) {
               class78.method1576(class212.archive6, Client.field874, 0, Client.field969, false);
            }

            Client.field967 = false;
         }
      }

   }
}
