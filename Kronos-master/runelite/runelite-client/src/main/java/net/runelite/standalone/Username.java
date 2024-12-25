package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSUsername;

@ObfuscatedName("jq")
public class Username implements Comparable, RSUsername {
   @ObfuscatedName("n")
   String cleanName;
   @ObfuscatedName("z")
   String name;

   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;Lli;)V"
   )
   public Username(String var1, LoginType var2) {
      this.name = var1;
      this.cleanName = ScriptEvent.method801(var1, var2);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "1914408235"
   )
   public boolean method4991() {
      return this.cleanName != null;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Ljq;I)I",
      garbageValue = "432116697"
   )
   public int method4992(Username var1) {
      return this.cleanName == null?(var1.cleanName == null?0:1):(var1.cleanName == null?-1:this.cleanName.compareTo(var1.cleanName));
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)Ljava/lang/String;",
      garbageValue = "2132584888"
   )
   public String method5001() {
      return this.name;
   }

   public boolean equals(Object var1) {
      if(var1 instanceof Username) {
         Username var2 = (Username)var1;
         return this.cleanName == null?var2.cleanName == null:(var2.cleanName == null?false:(this.hashCode() != var2.hashCode()?false:this.cleanName.equals(var2.cleanName)));
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.cleanName == null?0:this.cleanName.hashCode();
   }

   public String toString() {
      return this.method5001();
   }

   public int compareTo(Object var1) {
      return this.method4992((Username)var1);
   }

   public String aah() {
      return this.method5001();
   }

   public String aae() {
      return this.method5001();
   }

   public String aak() {
      return this.method5001();
   }

   public String getName() {
      return this.method5001();
   }

   @ObfuscatedName("ai")
   @ObfuscatedSignature(
      signature = "([BII)I",
      garbageValue = "-42849866"
   )
   public static int method5005(byte[] var0, int var1) {
      return LoginScreenAnimation.method1293(var0, 0, var1);
   }
}
