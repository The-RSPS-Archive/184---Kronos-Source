package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("v")
final class class12 implements class16 {
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   static AbstractArchive Widget_fontsArchive;
   @ObfuscatedName("ix")
   @ObfuscatedGetter(
      intValue = 1489483619
   )
   static int selectedItemWidget;

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;B)Ljava/lang/Object;",
      garbageValue = "-93"
   )
   public Object vmethod210(Buffer var1) {
      return Long.valueOf(var1.method5502());
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/Long;Lkl;B)V",
      garbageValue = "33"
   )
   void method143(Long var1, Buffer var2) {
      var2.method5485(var1.longValue());
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/Object;Lkl;B)V",
      garbageValue = "0"
   )
   public void vmethod213(Object var1, Buffer var2) {
      this.method143((Long)var1, var2);
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/CharSequence;II)I",
      garbageValue = "-2101135652"
   )
   public static int method154(CharSequence var0, int var1) {
      return SoundCache.method2476(var0, var1, true);
   }

   @ObfuscatedName("v")
   public static int method153(long var0) {
      return (int)(var0 >>> 0 & 127L);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(II)I",
      garbageValue = "-620659228"
   )
   public static int method155(int var0) {
      return var0 >> 11 & 63;
   }

   @ObfuscatedName("kk")
   @ObfuscatedSignature(
      signature = "(Lho;I)I",
      garbageValue = "-2052318981"
   )
   static int method148(Widget var0) {
      IntegerNode var1 = (IntegerNode)Client.widgetClickMasks.method6346(((long)var0.id << 32) + (long)var0.childIndex);
      return var1 != null?var1.integer:var0.clickMask;
   }
}
