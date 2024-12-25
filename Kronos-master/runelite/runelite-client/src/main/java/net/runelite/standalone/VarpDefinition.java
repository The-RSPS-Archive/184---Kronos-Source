package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("iq")
public class VarpDefinition extends DualNode {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 1786777659
   )
   public static int VarpDefinition_fileCount;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   static EvictingDualNodeHashTable VarpDefinition_cached;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive VarpDefinition_archive;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 223624365
   )
   public int type;

   static {
      VarpDefinition_cached = new EvictingDualNodeHashTable(64);
   }

   VarpDefinition() {
      this.type = 0;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;I)V",
      garbageValue = "769888015"
   )
   void method4487(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            return;
         }

         this.method4488(var1, var2);
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "217607439"
   )
   void method4488(Buffer var1, int var2) {
      if(var2 == 5) {
         this.type = var1.readUnsignedShort();
      }

   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "-1744608160"
   )
   static int method4497(int var0, Script var1, boolean var2) {
      Widget var3 = var2?GrandExchangeOfferAgeComparator.field26:KitDefinition.field3452;
      if(var0 == 1700) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.itemId;
         return 1;
      } else if(var0 == 1701) {
         if(var3.itemId != -1) {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.itemQuantity;
         } else {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
         }

         return 1;
      } else if(var0 == 1702) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.childIndex;
         return 1;
      } else {
         return 2;
      }
   }
}
