package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSAbstractRasterProvider;

@ObfuscatedName("lm")
public abstract class AbstractRasterProvider implements RSAbstractRasterProvider {
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Llp;"
   )
   static IndexedSprite titlebuttonSprite;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 809652275
   )
   public int height;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 1221954905
   )
   public int width;
   @ObfuscatedName("v")
   public int[] pixels;

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "-1952064900"
   )
   public abstract void vmethod6275(int var1, int var2);

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(IIIII)V",
      garbageValue = "-67564732"
   )
   public abstract void vmethod6276(int var1, int var2, int var3, int var4);

   public int[] getPixels() {
      return this.pixels;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setRaster() {
      this.method6274();
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-997992638"
   )
   public final void method6274() {
      Rasterizer2D.method6409(this.pixels, this.width, this.height);
   }
}
