package net.runelite.standalone;

import javax.imageio.ImageIO;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("au")
public class class38 {
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lgb;"
   )
   public static MidiPcmStream midiPcmStream;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = -649185671
   )
   public static int musicTrackFileId;

   static {
      ImageIO.setUseCache(false);
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(IZIZB)V",
      garbageValue = "-64"
   )
   static void method730(int var0, boolean var1, int var2, boolean var3) {
      if(World.World_worlds != null) {
         AbstractWorldMapData.method3327(0, World.World_worlds.length - 1, var0, var1, var2, var3);
      }

   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "([BI)Lkn;",
      garbageValue = "-1770627772"
   )
   static Font method731(byte[] var0) {
      if(var0 == null) {
         return null;
      } else {
         Font var1 = new Font(var0, class329.SpriteBuffer_xOffsets, MusicPatchPcmStream.SpriteBuffer_yOffsets, class329.SpriteBuffer_spriteWidths, RunException.SpriteBuffer_spriteHeights, class329.SpriteBuffer_spritePalette, PacketBufferNode.SpriteBuffer_pixels);
         class329.SpriteBuffer_xOffsets = null;
         MusicPatchPcmStream.SpriteBuffer_yOffsets = null;
         class329.SpriteBuffer_spriteWidths = null;
         RunException.SpriteBuffer_spriteHeights = null;
         class329.SpriteBuffer_spritePalette = null;
         PacketBufferNode.SpriteBuffer_pixels = null;
         return var1;
      }
   }
}
