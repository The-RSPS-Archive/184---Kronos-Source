package net.runelite.standalone;

import java.awt.Component;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ae")
public final class class22 {
   @ObfuscatedName("dq")
   @ObfuscatedSignature(
      signature = "Lky;"
   )
   static AbstractSocket js5Socket;

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(II)Ljava/lang/String;",
      garbageValue = "1930428495"
   )
   static final String method456(int var0) {
      return var0 < 100000?"<col=ffff00>" + var0 + "</col>":(var0 < 10000000?"<col=ffffff>" + var0 / 1000 + "K" + "</col>":"<col=00ff80>" + var0 / 1000000 + "M" + "</col>");
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljava/awt/Component;I)V",
      garbageValue = "-416045608"
   )
   static void method455(Component var0) {
      var0.addMouseListener(MouseHandler.MouseHandler_instance);
      var0.addMouseMotionListener(MouseHandler.MouseHandler_instance);
      var0.addFocusListener(MouseHandler.MouseHandler_instance);
   }

   @ObfuscatedName("jw")
   @ObfuscatedSignature(
      signature = "(Lho;I)Z",
      garbageValue = "-1205677722"
   )
   static final boolean method457(Widget var0) {
      int var1 = var0.contentType;
      if(var1 == 205) {
         Client.logoutTimer = 250;
         return true;
      } else {
         int var2;
         int var3;
         if(var1 >= 300 && var1 <= 313) {
            var2 = (var1 - 300) / 2;
            var3 = var1 & 1;
            Client.playerAppearance.method4160(var2, var3 == 1);
         }

         if(var1 >= 314 && var1 <= 323) {
            var2 = (var1 - 314) / 2;
            var3 = var1 & 1;
            Client.playerAppearance.method4126(var2, var3 == 1);
         }

         if(var1 == 324) {
            Client.playerAppearance.method4129(false);
         }

         if(var1 == 325) {
            Client.playerAppearance.method4129(true);
         }

         if(var1 == 326) {
            PacketBufferNode var4 = InterfaceParent.method1140(ClientPacket.field2384, Client.packetWriter.isaacCipher);
            Client.playerAppearance.method4130(var4.packetBuffer);
            Client.packetWriter.method1622(var4);
            return true;
         } else {
            return false;
         }
      }
   }
}
