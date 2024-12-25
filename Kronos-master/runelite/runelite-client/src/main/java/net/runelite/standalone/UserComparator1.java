package net.runelite.standalone;

import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("lw")
public class UserComparator1 implements Comparator {
   @ObfuscatedName("z")
   final boolean reversed;

   public UserComparator1(boolean var1) {
      this.reversed = var1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljh;Ljh;I)I",
      garbageValue = "-67354255"
   )
   int method6407(User var1, User var2) {
      return this.reversed?var1.vmethod5168(var2):var2.vmethod5168(var1);
   }

   public int compare(Object var1, Object var2) {
      return this.method6407((User)var1, (User)var2);
   }

   public boolean equals(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "456557125"
   )
   public static void method6398() {
      PlayerAppearance.PlayerAppearance_cachedModels.method3035();
   }
}
