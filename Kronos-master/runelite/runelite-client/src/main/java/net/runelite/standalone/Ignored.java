package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSIgnored;
import net.runelite.rs.api.RSUsername;

@ObfuscatedName("jn")
public class Ignored extends User implements RSIgnored {
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -2093810797
   )
   int id;

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljn;I)I",
      garbageValue = "1947810954"
   )
   int method4962(Ignored var1) {
      return this.id - var1.id;
   }

   public int compareTo(Object var1) {
      return this.method4962((Ignored)var1);
   }

   public String getName() {
      return this.getRsName().getName();
   }

   public String getPrevName() {
      RSUsername var1 = this.getRsPrevName();
      return var1 == null?null:var1.getName();
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "(Ljh;I)I",
      garbageValue = "-531306911"
   )
   public int vmethod5168(User var1) {
      return this.method4962((Ignored)var1);
   }
}
