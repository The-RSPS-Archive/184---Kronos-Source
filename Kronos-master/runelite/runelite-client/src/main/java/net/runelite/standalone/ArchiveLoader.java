package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bs")
public class ArchiveLoader {
   @ObfuscatedName("bz")
   @ObfuscatedSignature(
      signature = "[Llf;"
   )
   static Sprite[] worldSelectBackSprites;
   @ObfuscatedName("c")
   static int[] Tiles_saturation;
   @ObfuscatedName("gi")
   static byte[][] regionMapArchives;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   final Archive archive;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 966789861
   )
   int loadedCount;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1526719037
   )
   final int groupCount;

   @ObfuscatedSignature(
      signature = "(Lie;Ljava/lang/String;)V"
   )
   ArchiveLoader(Archive var1, String var2) {
      this.loadedCount = 0;
      this.archive = var1;
      this.groupCount = var1.method4033();
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "1969120013"
   )
   boolean method1296() {
      this.loadedCount = 0;

      for(int var1 = 0; var1 < this.groupCount; ++var1) {
         if(!this.archive.method4274(var1) || this.archive.method4273(var1)) {
            ++this.loadedCount;
         }
      }

      return this.loadedCount >= this.groupCount;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Lbo;Lbo;IZIZB)I",
      garbageValue = "7"
   )
   static int method1302(World var0, World var1, int var2, boolean var3, int var4, boolean var5) {
      int var6 = UserComparator7.method2893(var0, var1, var2, var3);
      if(var6 != 0) {
         return var3?-var6:var6;
      } else if(var4 == -1) {
         return 0;
      } else {
         int var7 = UserComparator7.method2893(var0, var1, var4, var5);
         return var5?-var7:var7;
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "([BIIB)Ljava/lang/String;",
      garbageValue = "-113"
   )
   public static String method1297(byte[] var0, int var1, int var2) {
      char[] var3 = new char[var2];
      int var4 = 0;

      for(int var5 = 0; var5 < var2; ++var5) {
         int var6 = var0[var5 + var1] & 255;
         if(var6 != 0) {
            if(var6 >= 128 && var6 < 160) {
               char var7 = class298.cp1252AsciiExtension[var6 - 128];
               if(var7 == 0) {
                  var7 = '?';
               }

               var6 = var7;
            }

            var3[var4++] = (char)var6;
         }
      }

      return new String(var3, 0, var4);
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(II)I",
      garbageValue = "-238840199"
   )
   public static int method1301(int var0) {
      return class19.method345(ViewportMouse.ViewportMouse_entityTags[var0]);
   }
}
