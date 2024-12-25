package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("hu")
public class class227 {
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = -1634764861
   )
   static int field2769;

   @ObfuscatedName("an")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "1936251414"
   )
   static int method4175(int var0, Script var1, boolean var2) {
      if(var0 == 5630) {
         Client.logoutTimer = 250;
         return 1;
      } else {
         return 2;
      }
   }

   @ObfuscatedName("as")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "1931429415"
   )
   static int method4176(int var0, Script var1, boolean var2) {
      int var3;
      if(var0 == 4200) {
         var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = Occluder.getItemDefinition(var3).name;
         return 1;
      } else {
         int var4;
         ItemDefinition var5;
         if(var0 == 4201) {
            Interpreter.Interpreter_intStackSize -= 2;
            var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
            var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
            var5 = Occluder.getItemDefinition(var3);
            if(var4 >= 1 && var4 <= 5 && var5.groundActions[var4 - 1] != null) {
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var5.groundActions[var4 - 1];
            } else {
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
            }

            return 1;
         } else if(var0 == 4202) {
            Interpreter.Interpreter_intStackSize -= 2;
            var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
            var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
            var5 = Occluder.getItemDefinition(var3);
            if(var4 >= 1 && var4 <= 5 && var5.inventoryActions[var4 - 1] != null) {
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var5.inventoryActions[var4 - 1];
            } else {
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
            }

            return 1;
         } else if(var0 == 4203) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Occluder.getItemDefinition(var3).price;
            return 1;
         } else if(var0 == 4204) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Occluder.getItemDefinition(var3).isStackable == 1?1:0;
            return 1;
         } else {
            ItemDefinition var6;
            if(var0 == 4205) {
               var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               var6 = Occluder.getItemDefinition(var3);
               if(var6.noteTemplate == -1 && var6.note >= 0) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var6.note;
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3;
               }

               return 1;
            } else if(var0 == 4206) {
               var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               var6 = Occluder.getItemDefinition(var3);
               if(var6.noteTemplate >= 0 && var6.note >= 0) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var6.note;
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3;
               }

               return 1;
            } else if(var0 == 4207) {
               var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Occluder.getItemDefinition(var3).isMembersOnly?1:0;
               return 1;
            } else if(var0 == 4208) {
               var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               var6 = Occluder.getItemDefinition(var3);
               if(var6.placeholderTemplate == -1 && var6.placeholder >= 0) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var6.placeholder;
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3;
               }

               return 1;
            } else if(var0 == 4209) {
               var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               var6 = Occluder.getItemDefinition(var3);
               if(var6.placeholderTemplate >= 0 && var6.placeholder >= 0) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var6.placeholder;
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3;
               }

               return 1;
            } else if(var0 == 4210) {
               String var7 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               var4 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               Canvas.method778(var7, var4 == 1);
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = DevicePcmPlayerProvider.foundItemIdCount;
               return 1;
            } else if(var0 != 4211) {
               if(var0 == 4212) {
                  ReflectionCheck.foundItemIndex = 0;
                  return 1;
               } else {
                  return 2;
               }
            } else {
               if(WorldMapIcon_0.foundItemIds != null && ReflectionCheck.foundItemIndex < DevicePcmPlayerProvider.foundItemIdCount) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = WorldMapIcon_0.foundItemIds[++ReflectionCheck.foundItemIndex - 1] & '\uffff';
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
               }

               return 1;
            }
         }
      }
   }
}
