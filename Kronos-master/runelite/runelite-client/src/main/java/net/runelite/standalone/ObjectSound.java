package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bg")
public final class ObjectSound extends Node {
   @ObfuscatedName("pk")
   @ObfuscatedSignature(
      signature = "Llf;"
   )
   static Sprite sceneMinimapSprite;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Ljv;"
   )
   static NodeDeque objectSounds;
   @ObfuscatedName("hs")
   @ObfuscatedGetter(
      intValue = -1030939799
   )
   static int oculusOrbFocalPointX;
   @ObfuscatedName("o")
   int[] soundEffectIds;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 514830237
   )
   int y;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -394210361
   )
   int field414;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Liz;"
   )
   ObjectDefinition obj;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -331149697
   )
   int x;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 1770378821
   )
   int plane;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = 287004655
   )
   int soundEffectId;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 1622269101
   )
   int field413;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = 1113854033
   )
   int field421;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = -91764577
   )
   int field424;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 985540703
   )
   int field418;
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "Ldu;"
   )
   RawPcmStream stream2;
   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "Ldu;"
   )
   RawPcmStream stream1;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 1094960613
   )
   int field415;

   static {
      objectSounds = new NodeDeque();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-75"
   )
   void method949() {
      int var1 = this.soundEffectId;
      ObjectDefinition var2 = this.obj.method4733();
      if(var2 != null) {
         this.soundEffectId = var2.ambientSoundId;
         this.field415 = var2.int4 * 128;
         this.field418 = var2.int5;
         this.field424 = var2.int6;
         this.soundEffectIds = var2.soundEffectIds;
      } else {
         this.soundEffectId = -1;
         this.field415 = 0;
         this.field418 = 0;
         this.field424 = 0;
         this.soundEffectIds = null;
      }

      if(var1 != this.soundEffectId && this.stream1 != null) {
         WorldMapLabelSize.pcmStreamMixer.method1476(this.stream1);
         this.stream1 = null;
      }

   }
}
