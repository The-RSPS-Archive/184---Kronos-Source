package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("hx")
public class Strings {
   @ObfuscatedName("bm")
   public static String field2838;
   @ObfuscatedName("cc")
   public static String field2828;
   @ObfuscatedName("jd")
   public static String field3054;
   @ObfuscatedName("jo")
   public static String field3055;
   @ObfuscatedName("jp")
   public static String field3053;
   public static String field2799;
   @ObfuscatedName("lt")
   @ObfuscatedSignature(
      signature = "Lho;"
   )
   static Widget field2812;

   static {
      field2838 = "Please visit the support page for assistance.";
      field2828 = "Please visit the support page for assistance.";
      field3053 = "";
      field3054 = "Page has opened in a new window.";
      field3055 = "(Please check your popup blocker.)";
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IB)Z",
      garbageValue = "-5"
   )
   public static boolean method4189(int var0) {
      return var0 == 10 || var0 == 11 || var0 == 12 || var0 == 13 || var0 == 14 || var0 == 15 || var0 == 16 || var0 == 17;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(II)Z",
      garbageValue = "-552615239"
   )
   public static boolean method4188(int var0) {
      return var0 >= WorldMapDecorationType.field2549.id && var0 <= WorldMapDecorationType.field2565.id;
   }
}
