package net.runelite.standalone;

import java.applet.Applet;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSRunException;

@ObfuscatedName("mf")
public class RunException extends RuntimeException implements RSRunException {
   @ObfuscatedName("n")
   public static String localPlayerName;
   @ObfuscatedName("q")
   public static int[] SpriteBuffer_spriteHeights;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1228960845
   )
   public static int RunException_revision;
   @ObfuscatedName("z")
   public static Applet RunException_applet;
   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "Ldn;"
   )
   static SoundSystem soundSystem;
   @ObfuscatedName("p")
   Throwable throwable;
   @ObfuscatedName("r")
   String message;

   RunException(Throwable var1, String var2) {
      this.message = var2;
      this.throwable = var1;
   }

   public Throwable getParent() {
      return this.throwable;
   }
}
