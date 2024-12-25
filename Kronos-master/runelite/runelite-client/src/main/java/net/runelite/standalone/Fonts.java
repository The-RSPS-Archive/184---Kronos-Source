package net.runelite.standalone;

import java.util.HashMap;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kr")
public class Fonts {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   AbstractArchive fontsArchive;
   @ObfuscatedName("v")
   HashMap map;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   AbstractArchive spritesArchive;

   @ObfuscatedSignature(
      signature = "(Lhp;Lhp;)V"
   )
   public Fonts(AbstractArchive var1, AbstractArchive var2) {
      this.spritesArchive = var1;
      this.fontsArchive = var2;
      this.map = new HashMap();
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "([Lko;I)Ljava/util/HashMap;",
      garbageValue = "-1165155916"
   )
   public HashMap method5758(FontName[] var1) {
      HashMap var2 = new HashMap();
      FontName[] var3 = var1;

      for(int var4 = 0; var4 < var3.length; ++var4) {
         FontName var5 = var3[var4];
         if(this.map.containsKey(var5)) {
            var2.put(var5, this.map.get(var5));
         } else {
            AbstractArchive var7 = this.spritesArchive;
            AbstractArchive var8 = this.fontsArchive;
            String var9 = var5.name;
            int var10 = var7.method4059(var9);
            int var11 = var7.method4039(var10, "");
            Font var6 = ClanMate.method4989(var7, var8, var10, var11);
            if(var6 != null) {
               this.map.put(var5, var6);
               var2.put(var5, var6);
            }
         }
      }

      return var2;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(II)Lkc;",
      garbageValue = "274947810"
   )
   public static PrivateChatMode method5757(int var0) {
      PrivateChatMode[] var1 = BZip2State.method5454();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         PrivateChatMode var3 = var1[var2];
         if(var0 == var3.field3634) {
            return var3;
         }
      }

      return null;
   }
}
