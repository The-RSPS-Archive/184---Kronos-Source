package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSAnimation;
import net.runelite.rs.api.RSFrames;

@ObfuscatedName("ep")
public class Frames extends DualNode implements RSFrames {
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -779358423
   )
   public static int SpriteBuffer_spriteHeight;
   @ObfuscatedName("ba")
   @ObfuscatedSignature(
      signature = "[Llp;"
   )
   static IndexedSprite[] worldSelectStars;
   @ObfuscatedName("jg")
   @ObfuscatedSignature(
      signature = "Lho;"
   )
   static Widget dragInventoryWidget;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "[Lda;"
   )
   Animation[] frames;

   @ObfuscatedSignature(
      signature = "(Lhp;Lhp;IZ)V",
      garbageValue = "0"
   )
   public Frames(AbstractArchive var1, AbstractArchive var2, int var3, boolean var4) {
      NodeDeque var5 = new NodeDeque();
      int var6 = var1.fileCount(var3);
      this.frames = new Animation[var6];
      int[] var7 = var1.method4042(var3);

      for(int var8 = 0; var8 < var7.length; ++var8) {
         byte[] var9 = var1.method4020(var3, var7[var8], (short)10411);
         Skeleton var10 = null;
         int var11 = (var9[0] & 255) << 8 | var9[1] & 255;

         for(Skeleton var12 = (Skeleton)var5.method5103(); var12 != null; var12 = (Skeleton)var5.method5126()) {
            if(var11 == var12.id) {
               var10 = var12;
               break;
            }
         }

         if(var10 == null) {
            byte[] var13 = var2.method4028(var11, 0);
            var10 = new Skeleton(var11, var13);
            var5.method5105(var10);
         }

         this.frames[var7[var8]] = new Animation(var9, var10);
      }

   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(II)Z",
      garbageValue = "-546780661"
   )
   public boolean method3064(int var1) {
      return this.frames[var1].hasAlphaTransform;
   }

   public RSAnimation[] getFrames() {
      return this.frames;
   }
}
