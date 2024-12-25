package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("hz")
public class SpriteMask extends DualNode {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -1322653373
   )
   public final int height;
   @ObfuscatedName("u")
   public final int[] xStarts;
   @ObfuscatedName("v")
   public final int[] xWidths;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 470195623
   )
   public final int width;

   SpriteMask(int var1, int var2, int[] var3, int[] var4, int var5) {
      this.width = var1;
      this.height = var2;
      this.xWidths = var3;
      this.xStarts = var4;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(III)Z",
      garbageValue = "-886102829"
   )
   public boolean method4191(int var1, int var2) {
      if(var2 >= 0 && var2 < this.xStarts.length) {
         int var3 = this.xStarts[var2];
         if(var1 >= var3 && var1 <= var3 + this.xWidths[var2]) {
            return true;
         }
      }

      return false;
   }
}
