package net.runelite.standalone;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Hashtable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSRasterProvider;

@ObfuscatedName("bx")
public final class RasterProvider extends AbstractRasterProvider implements RSRasterProvider {
   @ObfuscatedName("n")
   Image image;
   @ObfuscatedName("z")
   Component component;

   RasterProvider(int var1, int var2, Component var3) {
      super.width = var1;
      super.height = var2;
      super.pixels = new int[var2 * var1 + 1];
      DataBufferInt var4 = new DataBufferInt(super.pixels, super.pixels.length);
      DirectColorModel var5 = new DirectColorModel(32, 16711680, 65280, 255);
      WritableRaster var6 = Raster.createWritableRaster(var5.createCompatibleSampleModel(super.width, super.height), var4, (Point)null);
      this.image = new BufferedImage(var5, var6, false, new Hashtable());
      this.method1421(var3);
      this.method6274();
      this.init(var1, var2, var3);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "-1952064900"
   )
   public final void vmethod6275(int var1, int var2) {
      this.method1414(this.component.getGraphics(), var1, var2);
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(Ljava/awt/Graphics;IIIII)V",
      garbageValue = "1517141753"
   )
   final void method1413(Graphics var1, int var2, int var3, int var4, int var5) {
      try {
         Shape var6 = var1.getClip();
         var1.clipRect(var2, var3, var4, var5);
         var1.drawImage(this.image, 0, 0, this.component);
         var1.setClip(var6);
      } catch (Exception var7) {
         this.component.repaint();
      }

   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Ljava/awt/Graphics;IIB)V",
      garbageValue = "111"
   )
   final void method1414(Graphics var1, int var2, int var3) {
      ViewportMouse.client.getCallbacks().draw(this, var1, var2, var3);
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(IIIII)V",
      garbageValue = "-67564732"
   )
   public final void vmethod6276(int var1, int var2, int var3, int var4) {
      this.method1413(this.component.getGraphics(), var1, var2, var3, var4);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljava/awt/Component;I)V",
      garbageValue = "1269592003"
   )
   final void method1421(Component var1) {
      this.component = var1;
   }

   public void setImage(Image var1) {
      this.image = var1;
   }

   public void init(int var1, int var2, Component var3) {
      if(ViewportMouse.client.isGpu()) {
         int[] var4 = this.getPixels();
         DataBufferInt var5 = new DataBufferInt(var4, var4.length);
         DirectColorModel var6 = new DirectColorModel(ColorSpace.getInstance(1000), 32, 16711680, 65280, 255, -16777216, true, 3);
         WritableRaster var7 = Raster.createWritableRaster(var6.createCompatibleSampleModel(var1, var2), var5, (Point)null);
         BufferedImage var8 = new BufferedImage(var6, var7, true, new Hashtable());
         this.setImage(var8);
      }
   }

   public Component getCanvas() {
      return this.component;
   }

   public Image getImage() {
      return this.image;
   }
}
