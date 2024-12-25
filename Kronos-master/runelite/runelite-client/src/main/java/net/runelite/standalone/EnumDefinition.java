package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSEnumDefinition;

@ObfuscatedName("ia")
public class EnumDefinition extends DualNode implements RSEnumDefinition {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   static EvictingDualNodeHashTable EnumDefinition_cached;
   @ObfuscatedName("t")
   @ObfuscatedSignature(
      signature = "Laq;"
   )
   static WorldMapEvent worldMapEvent;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive EnumDefinition_archive;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 213418595
   )
   public int defaultInt;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -1372083607
   )
   public int outputCount;
   @ObfuscatedName("r")
   public String defaultStr;
   @ObfuscatedName("u")
   public char outputType;
   @ObfuscatedName("v")
   public char inputType;
   @ObfuscatedName("y")
   public int[] intVals;
   @ObfuscatedName("i")
   public String[] strVals;
   @ObfuscatedName("m")
   public int[] keys;

   static {
      EnumDefinition_cached = new EvictingDualNodeHashTable(64);
   }

   EnumDefinition() {
      this.defaultStr = "null";
      this.outputCount = 0;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;I)V",
      garbageValue = "-1933011246"
   )
   void method4220(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            return;
         }

         this.method4221(var1, var2);
      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1423753567"
   )
   public int method4219() {
      return this.outputCount;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "1574226398"
   )
   void method4221(Buffer var1, int var2) {
      if(var2 == 1) {
         this.inputType = (char)var1.readUnsignedByte();
      } else if(var2 == 2) {
         this.outputType = (char)var1.readUnsignedByte();
      } else if(var2 == 3) {
         this.defaultStr = var1.readString();
      } else if(var2 == 4) {
         this.defaultInt = var1.readInt();
      } else {
         int var3;
         if(var2 == 5) {
            this.outputCount = var1.readUnsignedShort();
            this.keys = new int[this.outputCount];
            this.strVals = new String[this.outputCount];

            for(var3 = 0; var3 < this.outputCount; ++var3) {
               this.keys[var3] = var1.readInt();
               this.strVals[var3] = var1.readString();
            }
         } else if(var2 == 6) {
            this.outputCount = var1.readUnsignedShort();
            this.keys = new int[this.outputCount];
            this.intVals = new int[this.outputCount];

            for(var3 = 0; var3 < this.outputCount; ++var3) {
               this.keys[var3] = var1.readInt();
               this.intVals[var3] = var1.readInt();
            }
         }
      }

   }

   public int[] getKeys() {
      return this.keys;
   }

   public String getDefaultString() {
      return this.defaultStr;
   }

   public int getDefaultInt() {
      return this.defaultInt;
   }

   public int[] getIntVals() {
      return this.intVals;
   }

   public String[] getStringVals() {
      return this.strVals;
   }

   public int getIntValue(int var1) {
      int[] var2 = this.getKeys();
      if(var2 == null) {
         return this.getDefaultInt();
      } else {
         for(int var3 = 0; var3 < var2.length; ++var3) {
            if(var2[var3] == var1) {
               int[] var4 = this.getIntVals();
               return var4[var3];
            }
         }

         return this.getDefaultInt();
      }
   }

   public String getStringValue(int var1) {
      int[] var2 = this.getKeys();
      if(var2 == null) {
         return this.getDefaultString();
      } else {
         for(int var3 = 0; var3 < var2.length; ++var3) {
            if(var2[var3] == var1) {
               String[] var4 = this.getStringVals();
               return var4[var3];
            }
         }

         return this.getDefaultString();
      }
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;B)V",
      garbageValue = "54"
   )
   static final void method4225(String var0) {
      class217.sendGameMessage(30, "", var0);
   }
}
