package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("as")
public abstract class AbstractWorldMapIcon {
   @ObfuscatedName("h")
   @ObfuscatedSignature(
      signature = "Lkl;"
   )
   static Buffer NetCache_reference;
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Lhb;"
   )
   public final Coord coord2;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = -744946711
   )
   int screenX;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 1215104535
   )
   int screenY;
   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "Lhb;"
   )
   public final Coord coord1;

   @ObfuscatedSignature(
      signature = "(Lhb;Lhb;)V"
   )
   AbstractWorldMapIcon(Coord var1, Coord var2) {
      this.coord1 = var1;
      this.coord2 = var2;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)Law;",
      garbageValue = "13348956"
   )
   abstract WorldMapLabel vmethod2273();

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "806304417"
   )
   abstract int vmethod2257();

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-175699296"
   )
   abstract int vmethod2269();

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "-79"
   )
   public abstract int vmethod2277();

   @ObfuscatedName("aa")
   @ObfuscatedSignature(
      signature = "(III)Z",
      garbageValue = "-2112119378"
   )
   boolean method695(int var1, int var2) {
      if(!this.method694()) {
         return false;
      } else {
         WorldMapElement var3 = Decimator.method2498(this.vmethod2277());
         int var4 = this.vmethod2269();
         int var5 = this.vmethod2257();
         switch(var3.horizontalAlignment.value) {
         case 0:
            if(var1 >= this.screenX && var1 < var4 + this.screenX) {
               break;
            }

            return false;
         case 1:
            if(var1 > this.screenX - var4 && var1 <= this.screenX) {
               break;
            }

            return false;
         case 2:
            if(var1 < this.screenX - var4 / 2 || var1 > var4 / 2 + this.screenX) {
               return false;
            }
         }

         switch(var3.verticalAlignment.value) {
         case 0:
            if(var2 <= this.screenY - var5 || var2 > this.screenY) {
               return false;
            }
            break;
         case 1:
            if(var2 < this.screenY || var2 >= var5 + this.screenY) {
               return false;
            }
            break;
         case 2:
            if(var2 < this.screenY - var5 / 2 || var2 > var5 / 2 + this.screenY) {
               return false;
            }
         }

         return true;
      }
   }

   @ObfuscatedName("ac")
   @ObfuscatedSignature(
      signature = "(B)Z",
      garbageValue = "69"
   )
   boolean method694() {
      return this.vmethod2277() >= 0;
   }

   @ObfuscatedName("ap")
   @ObfuscatedSignature(
      signature = "(IIB)Z",
      garbageValue = "122"
   )
   boolean method696(int var1, int var2) {
      WorldMapLabel var3 = this.vmethod2273();
      return var3 == null?false:(var1 >= this.screenX - var3.width / 2 && var1 <= var3.width / 2 + this.screenX?var2 >= this.screenY && var2 <= this.screenY + var3.height:false);
   }

   @ObfuscatedName("k")
   @ObfuscatedSignature(
      signature = "(III)Z",
      garbageValue = "1521252994"
   )
   boolean method689(int var1, int var2) {
      return this.method695(var1, var2)?true:this.method696(var1, var2);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "([BILjava/lang/CharSequence;I)I",
      garbageValue = "1746697865"
   )
   public static int method718(byte[] var0, int var1, CharSequence var2) {
      int var3 = var2.length();
      int var4 = var1;

      for(int var5 = 0; var5 < var3; ++var5) {
         char var6 = var2.charAt(var5);
         if(var6 <= 127) {
            var0[var4++] = (byte)var6;
         } else if(var6 <= 2047) {
            var0[var4++] = (byte)(192 | var6 >> 6);
            var0[var4++] = (byte)(128 | var6 & '?');
         } else {
            var0[var4++] = (byte)(224 | var6 >> '\f');
            var0[var4++] = (byte)(128 | var6 >> 6 & 63);
            var0[var4++] = (byte)(128 | var6 & '?');
         }
      }

      return var4 - var1;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(I)[Llf;",
      garbageValue = "-1750401163"
   )
   static Sprite[] method720() {
      Sprite[] var0 = new Sprite[class329.SpriteBuffer_spriteCount];

      for(int var1 = 0; var1 < class329.SpriteBuffer_spriteCount; ++var1) {
         Sprite var2 = var0[var1] = new Sprite();
         var2.width = class329.SpriteBuffer_spriteWidth;
         var2.height = Frames.SpriteBuffer_spriteHeight;
         var2.xOffset = class329.SpriteBuffer_xOffsets[var1];
         var2.yOffset = MusicPatchPcmStream.SpriteBuffer_yOffsets[var1];
         var2.subWidth = class329.SpriteBuffer_spriteWidths[var1];
         var2.subHeight = RunException.SpriteBuffer_spriteHeights[var1];
         int var3 = var2.subHeight * var2.subWidth;
         byte[] var4 = PacketBufferNode.SpriteBuffer_pixels[var1];
         var2.pixels = new int[var3];

         for(int var5 = 0; var5 < var3; ++var5) {
            var2.pixels[var5] = class329.SpriteBuffer_spritePalette[var4[var5] & 255];
         }
      }

      class329.SpriteBuffer_xOffsets = null;
      MusicPatchPcmStream.SpriteBuffer_yOffsets = null;
      class329.SpriteBuffer_spriteWidths = null;
      RunException.SpriteBuffer_spriteHeights = null;
      class329.SpriteBuffer_spritePalette = null;
      PacketBufferNode.SpriteBuffer_pixels = null;
      return var0;
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(CI)Z",
      garbageValue = "431385014"
   )
   public static boolean method710(char var0) {
      return var0 >= '0' && var0 <= '9';
   }
}
