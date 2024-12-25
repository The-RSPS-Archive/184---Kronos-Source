package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bw")
public class Interpreter {
   @ObfuscatedName("qg")
   @ObfuscatedSignature(
      signature = "Ldr;"
   )
   static Decimator decimator;
   @ObfuscatedName("p")
   static int[][] Interpreter_arrays;
   @ObfuscatedName("q")
   static int[] Interpreter_intStack;
   @ObfuscatedName("r")
   static int[] Interpreter_arrayLengths;
   @ObfuscatedName("w")
   static final String[] Interpreter_MONTHS;
   @ObfuscatedName("x")
   static boolean field646;
   @ObfuscatedName("y")
   static String[] Interpreter_stringStack;
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "[Lbv;"
   )
   static ScriptFrame[] Interpreter_frames;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 1889538287
   )
   static int Interpreter_frameDepth;
   @ObfuscatedName("d")
   static final double field648;
   @ObfuscatedName("e")
   static java.util.Calendar Interpreter_calendar;
   @ObfuscatedName("g")
   static boolean field645;
   @ObfuscatedName("h")
   @ObfuscatedGetter(
      intValue = -1964719699
   )
   static int field660;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = -133806221
   )
   static int Interpreter_stringStackSize;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 313560965
   )
   static int Interpreter_intStackSize;

   static {
      Interpreter_arrayLengths = new int[5];
      Interpreter_arrays = new int[5][5000];
      Interpreter_intStack = new int[1000];
      Interpreter_stringStack = new String[1000];
      Interpreter_frameDepth = 0;
      Interpreter_frames = new ScriptFrame[50];
      Interpreter_calendar = java.util.Calendar.getInstance();
      Interpreter_MONTHS = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
      field645 = false;
      field646 = false;
      field660 = 0;
      field648 = Math.log(2.0D);
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;IB)Z",
      garbageValue = "106"
   )
   static boolean method1410(String var0, int var1) {
      return WorldMapID.method684(var0, var1, "openjs");
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Lgt;I)I",
      garbageValue = "-1997764542"
   )
   public static int method1409(CharSequence var0, CharSequence var1, Language var2) {
      int var3 = var0.length();
      int var4 = var1.length();
      int var5 = 0;
      int var6 = 0;
      char var7 = 0;
      char var8 = 0;

      while(var5 - var7 < var3 || var6 - var8 < var4) {
         if(var5 - var7 >= var3) {
            return -1;
         }

         if(var6 - var8 >= var4) {
            return 1;
         }

         char var9;
         if(var7 != 0) {
            var9 = var7;
            boolean var14 = false;
         } else {
            var9 = var0.charAt(var5++);
         }

         char var10;
         if(var8 != 0) {
            var10 = var8;
            boolean var15 = false;
         } else {
            var10 = var1.charAt(var6++);
         }

         var7 = class125.method2876(var9);
         var8 = class125.method2876(var10);
         var9 = class17.method214(var9, var2);
         var10 = class17.method214(var10, var2);
         if(var10 != var9 && Character.toUpperCase(var9) != Character.toUpperCase(var10)) {
            var9 = Character.toLowerCase(var9);
            var10 = Character.toLowerCase(var10);
            if(var10 != var9) {
               return MusicPatchNode.method3843(var9, var2) - MusicPatchNode.method3843(var10, var2);
            }
         }
      }

      int var16 = Math.min(var3, var4);

      char var12;
      int var17;
      for(var17 = 0; var17 < var16; ++var17) {
         if(var2 == Language.Language_FR) {
            var5 = var3 - 1 - var17;
            var6 = var4 - 1 - var17;
         } else {
            var6 = var17;
            var5 = var17;
         }

         char var11 = var0.charAt(var5);
         var12 = var1.charAt(var6);
         if(var11 != var12 && Character.toUpperCase(var11) != Character.toUpperCase(var12)) {
            var11 = Character.toLowerCase(var11);
            var12 = Character.toLowerCase(var12);
            if(var12 != var11) {
               return MusicPatchNode.method3843(var11, var2) - MusicPatchNode.method3843(var12, var2);
            }
         }
      }

      var17 = var3 - var4;
      if(var17 != 0) {
         return var17;
      } else {
         for(int var18 = 0; var18 < var16; ++var18) {
            var12 = var0.charAt(var18);
            char var13 = var1.charAt(var18);
            if(var13 != var12) {
               return MusicPatchNode.method3843(var12, var2) - MusicPatchNode.method3843(var13, var2);
            }
         }

         return 0;
      }
   }
}
