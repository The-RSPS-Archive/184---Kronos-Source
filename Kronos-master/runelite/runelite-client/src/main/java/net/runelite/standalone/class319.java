package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("le")
public enum class319 implements Enumerated {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lle;"
   )
   field3904(4, 0),
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lle;"
   )
   field3895(1, 1),
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lle;"
   )
   field3902(8, 2),
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lle;"
   )
   field3897(2, 3),
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Lle;"
   )
   field3894(6, 4),
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Lle;"
   )
   field3899(5, 5),
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Lle;"
   )
   field3901(0, 6),
   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "Lle;"
   )
   field3898(7, 7),
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "Lle;"
   )
   field3900(3, 8);

   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = -2131314821
   )
   final int id;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = -504561675
   )
   final int field3903;

   class319(int var3, int var4) {
      this.field3903 = var3;
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

   @ObfuscatedName("gs")
   @ObfuscatedSignature(
      signature = "(Lby;IIIIII)V"
   )
   static final void method6089(Actor var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      if(Scene.shouldDraw(var0, true)) {
         Scene.copy$drawActor2d(var0, var1, var2, var3, var4, var5, var6);
      }

   }
}
