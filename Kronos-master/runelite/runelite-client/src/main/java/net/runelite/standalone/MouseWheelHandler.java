package net.runelite.standalone;

import java.awt.Component;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSMouseWheelHandler;

@ObfuscatedName("av")
public final class MouseWheelHandler implements MouseWheel, MouseWheelListener, RSMouseWheelHandler {
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -2136851861
   )
   int rotation;

   MouseWheelHandler() {
      this.rotation = 0;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Ljava/awt/Component;I)V",
      garbageValue = "-2135806748"
   )
   void method733(Component var1) {
      var1.removeMouseWheelListener(this);
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "-125"
   )
   public synchronized int vmethod3454() {
      int var1 = this.rotation;
      this.rotation = 0;
      return var1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljava/awt/Component;B)V",
      garbageValue = "39"
   )
   void method734(Component var1) {
      var1.addMouseWheelListener(this);
   }

   public synchronized void copy$mouseWheelMoved(MouseWheelEvent var1) {
      this.rotation += var1.getWheelRotation();
   }

   public synchronized void mouseWheelMoved(MouseWheelEvent var1) {
      var1 = ViewportMouse.client.getCallbacks().mouseWheelMoved(var1);
      if(!var1.isConsumed()) {
         this.copy$mouseWheelMoved(var1);
      }

   }
}
