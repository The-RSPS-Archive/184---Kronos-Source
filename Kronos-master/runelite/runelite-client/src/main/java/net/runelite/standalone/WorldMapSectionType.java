package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("s")
public enum WorldMapSectionType implements Enumerated {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Ls;"
   )
   WORLDMAPSECTIONTYPE0(0, (byte)0),
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Ls;"
   )
   WORLDMAPSECTIONTYPE1(3, (byte)1),
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Ls;"
   )
   WORLDMAPSECTIONTYPE2(2, (byte)2),
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Ls;"
   )
   WORLDMAPSECTIONTYPE3(1, (byte)3);

   @ObfuscatedName("bk")
   static String field59;
   @ObfuscatedName("c")
   public static boolean musicTrackBoolean;
   @ObfuscatedName("p")
   final byte id;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 19347147
   )
   final int type;

   WorldMapSectionType(int var3, byte var4) {
      this.type = var3;
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

   @ObfuscatedName("jf")
   @ObfuscatedSignature(
      signature = "(Lho;I)V",
      garbageValue = "529400812"
   )
   static void method116(Widget var0) {
      if(var0.cycle == Client.field846) {
         Client.field1049[var0.rootIndex] = true;
      }

   }

   @ObfuscatedName("kj")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)Ljava/lang/String;",
      garbageValue = "-1617760184"
   )
   static String method113(String var0) {
      PlayerType[] var1 = class210.getPlayerTypes();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         PlayerType var3 = var1[var2];
         if(var3.modIcon != -1 && var0.startsWith(class256.method4655(var3.modIcon))) {
            var0 = var0.substring(6 + Integer.toString(var3.modIcon).length());
            break;
         }
      }

      return var0;
   }

   @ObfuscatedName("ks")
   @ObfuscatedSignature(
      signature = "(Lho;B)Z",
      garbageValue = "7"
   )
   static boolean method118(Widget var0) {
      return var0.isHidden;
   }
}
