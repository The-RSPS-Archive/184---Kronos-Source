package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kz")
public class class313 {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lhp;IIB)[Llp;",
      garbageValue = "0"
   )
   public static IndexedSprite[] method5840(AbstractArchive var0, int var1, int var2) {
      if(!VertexNormal.method2468(var0, var1, var2)) {
         return null;
      } else {
         IndexedSprite[] var4 = new IndexedSprite[class329.SpriteBuffer_spriteCount];

         for(int var5 = 0; var5 < class329.SpriteBuffer_spriteCount; ++var5) {
            IndexedSprite var6 = var4[var5] = new IndexedSprite();
            var6.width = class329.SpriteBuffer_spriteWidth;
            var6.height = Frames.SpriteBuffer_spriteHeight;
            var6.xOffset = class329.SpriteBuffer_xOffsets[var5];
            var6.yOffset = MusicPatchPcmStream.SpriteBuffer_yOffsets[var5];
            var6.subWidth = class329.SpriteBuffer_spriteWidths[var5];
            var6.subHeight = RunException.SpriteBuffer_spriteHeights[var5];
            var6.palette = class329.SpriteBuffer_spritePalette;
            var6.pixels = PacketBufferNode.SpriteBuffer_pixels[var5];
         }

         class329.SpriteBuffer_xOffsets = null;
         MusicPatchPcmStream.SpriteBuffer_yOffsets = null;
         class329.SpriteBuffer_spriteWidths = null;
         RunException.SpriteBuffer_spriteHeights = null;
         class329.SpriteBuffer_spritePalette = null;
         PacketBufferNode.SpriteBuffer_pixels = null;
         return var4;
      }
   }
}
