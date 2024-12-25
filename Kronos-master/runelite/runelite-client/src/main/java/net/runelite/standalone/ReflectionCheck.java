package net.runelite.standalone;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSReflectionCheck;

@ObfuscatedName("cq")
public class ReflectionCheck extends Node implements RSReflectionCheck {
   @ObfuscatedName("sf")
   @ObfuscatedGetter(
      intValue = 1327402685
   )
   static int foundItemIndex;
   @ObfuscatedName("gp")
   @ObfuscatedSignature(
      signature = "[Llf;"
   )
   static Sprite[] headIconHintSprites;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 198207431
   )
   int size;
   @ObfuscatedName("p")
   int[] intReplaceValues;
   @ObfuscatedName("q")
   Method[] methods;
   @ObfuscatedName("r")
   Field[] field1186;
   @ObfuscatedName("u")
   int[] creationErrors;
   @ObfuscatedName("v")
   int[] operations;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 85422493
   )
   int id;
   @ObfuscatedName("m")
   byte[][][] arguments;

   public Field[] getFields() {
      return this.field1186;
   }

   public Method[] getMethods() {
      return this.methods;
   }

   public byte[][][] getArgs() {
      return this.arguments;
   }

   @ObfuscatedName("k")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "-884073904"
   )
   static int method2145(int var0, Script var1, boolean var2) {
      if(var0 == 3200) {
         Interpreter.Interpreter_intStackSize -= 3;
         Message.method888(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize], Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1], Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 2]);
         return 1;
      } else if(var0 == 3201) {
         MusicPatchNode2.method3725(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
         return 1;
      } else if(var0 == 3202) {
         Interpreter.Interpreter_intStackSize -= 2;
         ClientPacket.method3878(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize], Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]);
         return 1;
      } else {
         return 2;
      }
   }
}
