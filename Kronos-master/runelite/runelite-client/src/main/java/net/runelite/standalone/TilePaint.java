package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSTilePaint;

@ObfuscatedName("ef")
public final class TilePaint implements RSTilePaint {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -721233201
   )
   int seColor;
   @ObfuscatedName("p")
   boolean isFlat;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = 1661774659
   )
   int rgb;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 2026223793
   )
   int texture;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -100726041
   )
   int nwColor;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1592287523
   )
   int neColor;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 968490533
   )
   int swColor;
   public int rl$paintModelBufferOffset;
   public int rl$paintModelUvBufferOffset;
   public int rl$paintModelBufferLen;

   TilePaint(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      this.isFlat = true;
      this.swColor = var1;
      this.seColor = var2;
      this.neColor = var3;
      this.nwColor = var4;
      this.texture = var5;
      this.rgb = var6;
      this.isFlat = var7;
   }

   public int getBufferOffset() {
      return this.rl$paintModelBufferOffset;
   }

   public void setBufferOffset(int var1) {
      this.rl$paintModelBufferOffset = var1;
   }

   public int getUvBufferOffset() {
      return this.rl$paintModelUvBufferOffset;
   }

   public void setUvBufferOffset(int var1) {
      this.rl$paintModelUvBufferOffset = var1;
   }

   public int getBufferLen() {
      return this.rl$paintModelBufferLen;
   }

   public void setBufferLen(int var1) {
      this.rl$paintModelBufferLen = var1;
   }

   public int getSwColor() {
      return this.swColor;
   }

   public int getSeColor() {
      return this.seColor;
   }

   public int getNeColor() {
      return this.neColor;
   }

   public int getNwColor() {
      return this.nwColor;
   }

   public int getTexture() {
      return this.texture;
   }

   public int getRBG() {
      return this.rgb;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(ZI)V",
      garbageValue = "-2065502658"
   )
   static void method2911(boolean var0) {
      Login.Login_response1 = "";
      Login.Login_response2 = "Enter your username/email & password.";
      Login.Login_response3 = "";
      Login.loginIndex = 2;
      if(var0) {
         Login.Login_password = "";
      }

      InterfaceParent.method1142();
      if(Client.Login_isUsernameRemembered && Login.Login_username != null && Login.Login_username.length() > 0) {
         Login.currentLoginField = 1;
      } else {
         Login.currentLoginField = 0;
      }

   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/CharSequence;I)I",
      garbageValue = "800355488"
   )
   public static int method2912(CharSequence var0) {
      return SoundCache.method2476(var0, 10, true);
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;II)Ljava/lang/String;",
      garbageValue = "-964205547"
   )
   static String method2910(Buffer var0, int var1) {
      String var10000;
      try {
         int var2 = var0.readUnsignedSmart();
         if(var2 > var1) {
            var2 = var1;
         }

         byte[] var3 = new byte[var2];
         var0.offset += class217.huffman.method4167(var0.array, var0.offset, var3, 0, var2);
         String var4 = ArchiveLoader.method1297(var3, 0, var2);
         var10000 = var4;
      } catch (Exception var6) {
         return "Cabbage";
      }

      return var10000;
   }
}
