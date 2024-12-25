package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ha")
public enum StudioGame implements Enumerated {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lha;"
   )
   runescape("runescape", "RuneScape", 0),
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lha;"
   )
   stellardawn("stellardawn", "Stellar Dawn", 1),
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lha;"
   )
   game3("game3", "Game 3", 2),
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lha;"
   )
   game4("game4", "Game 4", 3),
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Lha;"
   )
   game5("game5", "Game 5", 4),
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Lha;"
   )
   oldscape("oldscape", "RuneScape 2007", 5);

   @ObfuscatedName("sy")
   @ObfuscatedSignature(
      signature = "Lib;"
   )
   public static class235 field2468;
   @ObfuscatedName("q")
   public final String name;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 275633543
   )
   final int id;

   StudioGame(String var3, String var4, int var5) {
      this.name = var3;
      this.id = var5;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(S)I",
      garbageValue = "221"
   )
   public int getId() {
      return this.id;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(II)Lig;",
      garbageValue = "-61677673"
   )
   public static InvDefinition method3908(int var0) {
      InvDefinition var1 = (InvDefinition)InvDefinition.InvDefinition_cached.method3032((long)var0);
      if(var1 != null) {
         return var1;
      } else {
         byte[] var2 = InvDefinition.InvDefinition_archive.method4020(5, var0, (short)-817);
         var1 = new InvDefinition();
         if(var2 != null) {
            var1.method4328(new Buffer(var2));
         }

         InvDefinition.InvDefinition_cached.method3034(var1, (long)var0);
         return var1;
      }
   }
}
