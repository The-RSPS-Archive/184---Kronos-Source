package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("j")
public final class WorldMapRectangle {
   @ObfuscatedName("jz")
   @ObfuscatedGetter(
      intValue = 1984702959
   )
   static int plane;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 2120102399
   )
   int height;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 1496265891
   )
   int y;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1194762567
   )
   int x;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 1437732891
   )
   int width;
   // $FF: synthetic field
   @ObfuscatedSignature(
      signature = "Lal;"
   )
   final WorldMapManager this$0;

   @ObfuscatedSignature(
      signature = "(Lal;)V"
   )
   WorldMapRectangle(WorldMapManager var1) {
      this.this$0 = var1;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Lhp;Ljava/lang/String;Ljava/lang/String;I)Llp;",
      garbageValue = "418770572"
   )
   public static IndexedSprite method4765(AbstractArchive var0, String var1, String var2) {
      int var3 = var0.method4059(var1);
      int var4 = var0.method4039(var3, var2);
      IndexedSprite var5;
      if(!VertexNormal.method2468(var0, var3, var4)) {
         var5 = null;
      } else {
         IndexedSprite var7 = new IndexedSprite();
         var7.width = class329.SpriteBuffer_spriteWidth;
         var7.height = Frames.SpriteBuffer_spriteHeight;
         var7.xOffset = class329.SpriteBuffer_xOffsets[0];
         var7.yOffset = MusicPatchPcmStream.SpriteBuffer_yOffsets[0];
         var7.subWidth = class329.SpriteBuffer_spriteWidths[0];
         var7.subHeight = RunException.SpriteBuffer_spriteHeights[0];
         var7.palette = class329.SpriteBuffer_spritePalette;
         var7.pixels = PacketBufferNode.SpriteBuffer_pixels[0];
         class329.SpriteBuffer_xOffsets = null;
         MusicPatchPcmStream.SpriteBuffer_yOffsets = null;
         class329.SpriteBuffer_spriteWidths = null;
         RunException.SpriteBuffer_spriteHeights = null;
         class329.SpriteBuffer_spritePalette = null;
         PacketBufferNode.SpriteBuffer_pixels = null;
         var5 = var7;
      }

      return var5;
   }
}
