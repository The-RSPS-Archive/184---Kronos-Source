package net.runelite.standalone;

import java.applet.Applet;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("at")
public class class37 {
   @ObfuscatedName("n")
   public static String field279;
   @ObfuscatedName("z")
   public static Applet applet;
   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "Leh;"
   )
   static UrlRequest World_request;
   @ObfuscatedName("kc")
   @ObfuscatedGetter(
      intValue = -1418069311
   )
   static int menuY;
   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "Llf;"
   )
   static Sprite rightTitleSprite;

   static {
      applet = null;
      field279 = "";
   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "-150727859"
   )
   static int method727(int var0, Script var1, boolean var2) {
      Widget var3 = Canvas.getWidget(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
      if(var0 == 2500) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.x;
         return 1;
      } else if(var0 == 2501) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.y;
         return 1;
      } else if(var0 == 2502) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.width;
         return 1;
      } else if(var0 == 2503) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.height;
         return 1;
      } else if(var0 == 2504) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.isHidden?1:0;
         return 1;
      } else if(var0 == 2505) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.parentId;
         return 1;
      } else {
         return 2;
      }
   }

   @ObfuscatedName("jo")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1595444520"
   )
   static final void method728() {
      PacketBufferNode var0 = InterfaceParent.method1140(ClientPacket.field2397, Client.packetWriter.isaacCipher);
      Client.packetWriter.method1622(var0);

      for(InterfaceParent var1 = (InterfaceParent)Client.interfaceParents.method6348(); var1 != null; var1 = (InterfaceParent)Client.interfaceParents.method6345()) {
         if(var1.type == 0 || var1.type == 3) {
            FontName.method5748(var1, true);
         }
      }

      if(Client.meslayerContinueWidget != null) {
         WorldMapSectionType.method116(Client.meslayerContinueWidget);
         Client.meslayerContinueWidget = null;
      }

   }
}
