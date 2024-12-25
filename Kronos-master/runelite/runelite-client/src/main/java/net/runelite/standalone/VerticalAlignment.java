package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("im")
public enum VerticalAlignment implements Enumerated {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lim;"
   )
   field3261(0, 0),
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lim;"
   )
   VerticalAlignment_centered(2, 1),
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lim;"
   )
   field3259(1, 2);

   @ObfuscatedName("df")
   @ObfuscatedGetter(
      longValue = 8439577585786440307L
   )
   static long field3264;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1741450623
   )
   final int id;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -163994147
   )
   public final int value;

   VerticalAlignment(int var3, int var4) {
      this.value = var3;
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

   @ObfuscatedName("ko")
   @ObfuscatedSignature(
      signature = "(Lho;I)Ljava/lang/String;",
      garbageValue = "-2054955158"
   )
   static String method4441(Widget var0) {
      return class12.method155(class12.method148(var0)) == 0?null:(var0.spellActionName != null && var0.spellActionName.trim().length() != 0?var0.spellActionName:null);
   }
}
