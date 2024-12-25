package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("if")
public class ParamDefinition extends DualNode {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   static EvictingDualNodeHashTable ParamDefinition_cached;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = -1829462559
   )
   static int field3155;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive ParamDefinition_archive;
   @ObfuscatedName("lv")
   @ObfuscatedSignature(
      signature = "Lcs;"
   )
   static MenuAction tempMenuAction;
   @ObfuscatedName("p")
   boolean autoDisable;
   @ObfuscatedName("r")
   public String defaultStr;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -76739099
   )
   public int defaultInt;
   @ObfuscatedName("v")
   char type;

   static {
      ParamDefinition_cached = new EvictingDualNodeHashTable(64);
   }

   ParamDefinition() {
      this.autoDisable = true;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-127"
   )
   void method4310() {
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(B)Z",
      garbageValue = "-70"
   )
   public boolean isString() {
      return this.type == 's';
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "854773770"
   )
   void method4312(Buffer var1, int var2) {
      if(var2 == 1) {
         this.type = WorldMapEvent.method683(var1.readByte());
      } else if(var2 == 2) {
         this.defaultInt = var1.readInt();
      } else if(var2 == 4) {
         this.autoDisable = false;
      } else if(var2 == 5) {
         this.defaultStr = var1.readString();
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;I)V",
      garbageValue = "271304326"
   )
   void method4311(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            return;
         }

         this.method4312(var1, var2);
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lba;B)V",
      garbageValue = "-62"
   )
   public static void method4313(ScriptEvent var0) {
      KeyHandler.method506(var0, 500000, (byte)0);
   }

   @ObfuscatedName("hg")
   @ObfuscatedSignature(
      signature = "(II)Z",
      garbageValue = "-786706549"
   )
   static final boolean method4325(int var0) {
      if(var0 < 0) {
         return false;
      } else {
         int var1 = Client.menuOpcodes[var0];
         if(var1 >= 2000) {
            var1 -= 2000;
         }

         return var1 == 1007;
      }
   }
}
