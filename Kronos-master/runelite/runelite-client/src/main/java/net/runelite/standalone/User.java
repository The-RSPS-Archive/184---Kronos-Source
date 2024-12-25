package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSUser;
import net.runelite.rs.api.RSUsername;

@ObfuscatedName("jh")
public class User implements Comparable, RSUser {
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Ljq;"
   )
   Username previousUsername;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Ljq;"
   )
   Username username;

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "(I)Ljq;",
      garbageValue = "827230801"
   )
   public Username method4879() {
      return this.username;
   }

   public RSUsername getRsPrevName() {
      return this.previousUsername;
   }

   public RSUsername getRsName() {
      return this.username;
   }

   public int compareTo(Object var1) {
      return this.vmethod5168((User)var1);
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      signature = "(Ljq;Ljq;I)V",
      garbageValue = "-1357976341"
   )
   void method4882(Username var1, Username var2) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         this.username = var1;
         this.previousUsername = var2;
      }
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      signature = "(I)Ljava/lang/String;",
      garbageValue = "-835299696"
   )
   public String method4880() {
      return this.username == null?"":this.username.method5001();
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      signature = "(B)Ljava/lang/String;",
      garbageValue = "116"
   )
   public String method4878() {
      return this.previousUsername == null?"":this.previousUsername.method5001();
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "(Ljh;I)I",
      garbageValue = "-531306911"
   )
   public int vmethod5168(User var1) {
      return this.username.method4992(var1.username);
   }
}
