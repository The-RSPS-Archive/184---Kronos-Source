package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSLoginType;

@ObfuscatedName("li")
public class LoginType implements RSLoginType {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lli;"
   )
   static final LoginType field3930;
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Lli;"
   )
   static final LoginType field3931;
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Lli;"
   )
   static final LoginType field3928;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Lli;"
   )
   static final LoginType field3932;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lli;"
   )
   static final LoginType field3929;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lli;"
   )
   static final LoginType field3933;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "Lli;"
   )
   public static final LoginType field3936;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lli;"
   )
   public static final LoginType oldscape;
   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "Lli;"
   )
   static final LoginType field3935;
   @ObfuscatedName("c")
   final String field3938;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 1653780659
   )
   final int field3937;

   static {
      oldscape = new LoginType(7, 0, "", "");
      field3930 = new LoginType(5, 1, "", "");
      field3933 = new LoginType(6, 2, "", "");
      field3929 = new LoginType(2, 3, "", "");
      field3932 = new LoginType(4, 4, "", "");
      field3931 = new LoginType(0, 5, "", "");
      field3928 = new LoginType(8, 6, "", "");
      field3935 = new LoginType(3, 7, "", "");
      field3936 = new LoginType(1, -1, "", "", true, new LoginType[]{oldscape, field3930, field3933, field3932, field3929});
   }

   LoginType(int var1, int var2, String var3, String var4) {
      this.field3937 = var1;
      this.field3938 = var4;
   }

   @ObfuscatedSignature(
      signature = "(IILjava/lang/String;Ljava/lang/String;Z[Lli;)V"
   )
   LoginType(int var1, int var2, String var3, String var4, boolean var5, LoginType[] var6) {
      this.field3937 = var1;
      this.field3938 = var4;
   }

   public String aak() {
      return this.field3938;
   }

   public String toString() {
      return this.field3938;
   }

   public String aah() {
      return this.field3938;
   }

   public String aae() {
      return this.field3938;
   }
}
