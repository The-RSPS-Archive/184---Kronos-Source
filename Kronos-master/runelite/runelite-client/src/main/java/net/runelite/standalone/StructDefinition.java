package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ir")
public class StructDefinition extends DualNode {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable StructDefinition_cached;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive StructDefinition_archive;
   @ObfuscatedName("gc")
   @ObfuscatedSignature(
      signature = "[Llf;"
   )
   static Sprite[] headIconPkSprites;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Llb;"
   )
   IterableNodeHashTable params;

   static {
      StructDefinition_cached = new EvictingDualNodeHashTable(64);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "37"
   )
   void method4502() {
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(ILjava/lang/String;I)Ljava/lang/String;",
      garbageValue = "127947396"
   )
   public String method4503(int var1, String var2) {
      return class94.method2216(this.params, var1, var2);
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(III)I",
      garbageValue = "-1632755305"
   )
   public int method4505(int var1, int var2) {
      return HealthBar.getParam(this.params, var1, var2);
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "-464396075"
   )
   void method4501(Buffer var1, int var2) {
      if(var2 == 249) {
         this.params = UserComparator5.method3374(var1, this.params);
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;I)V",
      garbageValue = "-593456946"
   )
   void method4500(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            return;
         }

         this.method4501(var1, var2);
      }
   }
}
