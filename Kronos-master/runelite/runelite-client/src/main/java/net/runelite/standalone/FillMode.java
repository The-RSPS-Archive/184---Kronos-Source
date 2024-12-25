package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("lu")
public enum FillMode implements Enumerated {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Llu;"
   )
   SOLID(0, 0),
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Llu;"
   )
   field4020(1, 1),
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Llu;"
   )
   field4021(2, 2);

   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = -532498775
   )
   final int id;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -640256921
   )
   public final int value;

   FillMode(int var3, int var4) {
      this.value = var3;
      this.id = var4;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(S)I",
      garbageValue = "221"
   )
   public int getId() {
      return this.id;
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(I)Llf;",
      garbageValue = "1508787563"
   )
   static Sprite method6384() {
      Sprite var0 = new Sprite();
      var0.width = class329.SpriteBuffer_spriteWidth;
      var0.height = Frames.SpriteBuffer_spriteHeight;
      var0.xOffset = class329.SpriteBuffer_xOffsets[0];
      var0.yOffset = MusicPatchPcmStream.SpriteBuffer_yOffsets[0];
      var0.subWidth = class329.SpriteBuffer_spriteWidths[0];
      var0.subHeight = RunException.SpriteBuffer_spriteHeights[0];
      int var1 = var0.subHeight * var0.subWidth;
      byte[] var2 = PacketBufferNode.SpriteBuffer_pixels[0];
      var0.pixels = new int[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var0.pixels[var3] = class329.SpriteBuffer_spritePalette[var2[var3] & 255];
      }

      class329.SpriteBuffer_xOffsets = null;
      MusicPatchPcmStream.SpriteBuffer_yOffsets = null;
      class329.SpriteBuffer_spriteWidths = null;
      RunException.SpriteBuffer_spriteHeights = null;
      class329.SpriteBuffer_spritePalette = null;
      PacketBufferNode.SpriteBuffer_pixels = null;
      return var0;
   }
}
