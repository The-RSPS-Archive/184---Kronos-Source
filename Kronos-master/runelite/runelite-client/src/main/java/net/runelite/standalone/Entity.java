package net.runelite.standalone;

import java.util.Iterator;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSEntity;
import net.runelite.rs.api.RSModel;

@ObfuscatedName("er")
public abstract class Entity extends DualNode implements RSEntity {
   @ObfuscatedName("ci")
   @ObfuscatedGetter(
      intValue = -2145101269
   )
   public int height;

   protected Entity() {
      this.height = 1000;
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)Ldh;"
   )
   protected Model vmethod3072(int var1) {
      return null;
   }

   @ObfuscatedName("cb")
   void vmethod3071(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9) {
      Model var11 = this.vmethod3072(-693706908);
      if(var11 != null) {
         this.height = var11.height;
         var11.vmethod3071(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      }

   }

   public RSModel getModel() {
      return this.vmethod3072(-62237472);
   }

   public void setModelHeight(int var1) {
      this.height = var1;
   }

   public int getModelHeight() {
      return this.height;
   }

   public void draw(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9) {
      this.vmethod3071(var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(CB)B",
      garbageValue = "41"
   )
   public static byte method3074(char var0) {
      byte var1;
      if(var0 > 0 && var0 < 128 || var0 >= 160 && var0 <= 255) {
         var1 = (byte)var0;
      } else if(var0 == 8364) {
         var1 = -128;
      } else if(var0 == 8218) {
         var1 = -126;
      } else if(var0 == 402) {
         var1 = -125;
      } else if(var0 == 8222) {
         var1 = -124;
      } else if(var0 == 8230) {
         var1 = -123;
      } else if(var0 == 8224) {
         var1 = -122;
      } else if(var0 == 8225) {
         var1 = -121;
      } else if(var0 == 710) {
         var1 = -120;
      } else if(var0 == 8240) {
         var1 = -119;
      } else if(var0 == 352) {
         var1 = -118;
      } else if(var0 == 8249) {
         var1 = -117;
      } else if(var0 == 338) {
         var1 = -116;
      } else if(var0 == 381) {
         var1 = -114;
      } else if(var0 == 8216) {
         var1 = -111;
      } else if(var0 == 8217) {
         var1 = -110;
      } else if(var0 == 8220) {
         var1 = -109;
      } else if(var0 == 8221) {
         var1 = -108;
      } else if(var0 == 8226) {
         var1 = -107;
      } else if(var0 == 8211) {
         var1 = -106;
      } else if(var0 == 8212) {
         var1 = -105;
      } else if(var0 == 732) {
         var1 = -104;
      } else if(var0 == 8482) {
         var1 = -103;
      } else if(var0 == 353) {
         var1 = -102;
      } else if(var0 == 8250) {
         var1 = -101;
      } else if(var0 == 339) {
         var1 = -100;
      } else if(var0 == 382) {
         var1 = -98;
      } else if(var0 == 376) {
         var1 = -97;
      } else {
         var1 = 63;
      }

      return var1;
   }

   @ObfuscatedName("aq")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "160012965"
   )
   static int method3070(int var0, Script var1, boolean var2) {
      if(var0 == 6200) {
         Interpreter.Interpreter_intStackSize -= 2;
         Client.field1088 = (short)PlayerAppearance.method4127(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize]);
         if(Client.field1088 <= 0) {
            Client.field1088 = 256;
         }

         Client.field1095 = (short)PlayerAppearance.method4127(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]);
         if(Client.field1095 <= 0) {
            Client.field1095 = 256;
         }

         return 1;
      } else if(var0 == 6201) {
         Interpreter.Interpreter_intStackSize -= 2;
         Client.zoomHeight = (short)Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         if(Client.zoomHeight <= 0) {
            Client.zoomHeight = 256;
         }

         Client.zoomWidth = (short)Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         if(Client.zoomWidth <= 0) {
            Client.zoomWidth = 320;
         }

         return 1;
      } else if(var0 == 6202) {
         Interpreter.Interpreter_intStackSize -= 4;
         Client.field894 = (short)Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         if(Client.field894 <= 0) {
            Client.field894 = 1;
         }

         Client.field1099 = (short)Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         if(Client.field1099 <= 0) {
            Client.field1099 = 32767;
         } else if(Client.field1099 < Client.field894) {
            Client.field1099 = Client.field894;
         }

         Client.field1100 = (short)Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 2];
         if(Client.field1100 <= 0) {
            Client.field1100 = 1;
         }

         Client.field1101 = (short)Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 3];
         if(Client.field1101 <= 0) {
            Client.field1101 = 32767;
         } else if(Client.field1101 < Client.field1100) {
            Client.field1101 = Client.field1100;
         }

         return 1;
      } else if(var0 == 6203) {
         if(Client.viewportWidget != null) {
            AbstractByteArrayCopier.method3874(0, 0, Client.viewportWidget.width, Client.viewportWidget.height, false);
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.viewportWidth;
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.viewportHeight;
         } else {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
         }

         return 1;
      } else if(var0 == 6204) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.zoomHeight;
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.zoomWidth;
         return 1;
      } else if(var0 == 6205) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = class213.method3935(Client.field1088);
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = class213.method3935(Client.field1095);
         return 1;
      } else if(var0 == 6220) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
         return 1;
      } else if(var0 == 6221) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
         return 1;
      } else if(var0 == 6222) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = FloorDecoration.canvasWidth;
         return 1;
      } else if(var0 == 6223) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = WallDecoration.canvasHeight;
         return 1;
      } else {
         return 2;
      }
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(I)Ljava/lang/String;",
      garbageValue = "67381036"
   )
   static String method3079() {
      String var0 = "";

      Message var2;
      for(Iterator var1 = Messages.Messages_hashTable.iterator(); var1.hasNext(); var0 = var0 + var2.sender + ':' + var2.text + '\n') {
         var2 = (Message)var1.next();
      }

      return var0;
   }
}
