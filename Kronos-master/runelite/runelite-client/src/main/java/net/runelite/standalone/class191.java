package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gl")
public class class191 {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lgl;"
   )
   static final class191 field2144;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lgl;"
   )
   static final class191 field2145;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lgl;"
   )
   static final class191 field2143;

   static {
      field2143 = new class191();
      field2144 = new class191();
      field2145 = new class191();
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1561368496"
   )
   public static void method3763() {
      if(KeyHandler.KeyHandler_instance != null) {
         KeyHandler var0 = KeyHandler.KeyHandler_instance;
         synchronized(KeyHandler.KeyHandler_instance) {
            KeyHandler.KeyHandler_instance = null;
         }
      }

   }

   @ObfuscatedName("fa")
   @ObfuscatedSignature(
      signature = "(Lby;I)V",
      garbageValue = "616240991"
   )
   static final void method3762(Actor var0) {
      int var1 = var0.field686 - Client.cycle;
      int var2 = var0.field709 * 128 + var0.size * 64;
      int var3 = var0.field711 * 128 + var0.size * 64;
      var0.x += (var2 - var0.x) / var1;
      var0.y += (var3 - var0.y * 682054857) / var1 * -944175751;
      var0.field687 = 0;
      var0.orientation = var0.field715;
   }
}
