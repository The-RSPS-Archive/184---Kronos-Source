package net.runelite.standalone;

import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ld")
public class UserComparator2 implements Comparator {
   @ObfuscatedName("z")
   final boolean reversed;

   public UserComparator2(boolean var1) {
      this.reversed = var1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljh;Ljh;I)I",
      garbageValue = "1965280999"
   )
   int method6080(User var1, User var2) {
      return this.reversed?var1.method4879().method4992(var2.method4879()):var2.method4879().method4992(var1.method4879());
   }

   public boolean equals(Object var1) {
      return super.equals(var1);
   }

   public int compare(Object var1, Object var2) {
      return this.method6080((User)var1, (User)var2);
   }
}
