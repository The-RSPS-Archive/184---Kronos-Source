package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("eb")
public class UserComparator7 extends AbstractUserComparator {
   @ObfuscatedName("ft")
   @ObfuscatedSignature(
      signature = "Lkr;"
   )
   static Fonts WorldMapElement_fonts;
   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "[Llp;"
   )
   static IndexedSprite[] title_muteSprite;
   @ObfuscatedName("z")
   final boolean reversed;

   public UserComparator7(boolean var1) {
      this.reversed = var1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljl;Ljl;I)I",
      garbageValue = "1477313663"
   )
   int method2887(Buddy var1, Buddy var2) {
      return var1.world != 0 && var2.world != 0?(this.reversed?var1.int2 - var2.int2:var2.int2 - var1.int2):this.method5015(var1, var2);
   }

   public int compare(Object var1, Object var2) {
      return this.method2887((Buddy)var1, (Buddy)var2);
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(Lbo;Lbo;IZI)I",
      garbageValue = "-147225017"
   )
   static int method2893(World var0, World var1, int var2, boolean var3) {
      if(var2 == 1) {
         int var4 = var0.population;
         int var5 = var1.population;
         if(!var3) {
            if(var4 == -1) {
               var4 = 2001;
            }

            if(var5 == -1) {
               var5 = 2001;
            }
         }

         return var4 - var5;
      } else {
         return var2 == 2?var0.location - var1.location:(var2 == 3?(var0.activity.equals("-")?(var1.activity.equals("-")?0:(var3?-1:1)):(var1.activity.equals("-")?(var3?1:-1):var0.activity.compareTo(var1.activity))):(var2 == 4?(var0.method1213()?(var1.method1213()?0:1):(var1.method1213()?-1:0)):(var2 == 5?(var0.method1241()?(var1.method1241()?0:1):(var1.method1241()?-1:0)):(var2 == 6?(var0.method1247()?(var1.method1247()?0:1):(var1.method1247()?-1:0)):(var2 == 7?(var0.method1212()?(var1.method1212()?0:1):(var1.method1212()?-1:0)):var0.id - var1.id)))));
      }
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(CB)Z",
      garbageValue = "1"
   )
   public static boolean method2886(char var0) {
      return var0 >= 'A' && var0 <= 'Z' || var0 >= 'a' && var0 <= 'z';
   }
}
