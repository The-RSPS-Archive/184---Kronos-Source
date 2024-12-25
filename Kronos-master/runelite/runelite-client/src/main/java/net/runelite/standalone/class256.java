package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("iw")
public class class256 {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(II)Ljava/lang/String;",
      garbageValue = "508491282"
   )
   static String method4655(int var0) {
      return "<img=" + var0 + ">";
   }

   @ObfuscatedName("fw")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "1170068749"
   )
   static int method4656() {
      return Client.isResizable?2:1;
   }

   @ObfuscatedName("iu")
   @ObfuscatedSignature(
      signature = "(IIIZB)V",
      garbageValue = "-73"
   )
   static final void method4654(int var0, int var1, int var2, boolean var3) {
      if(WorldMapData_0.method171(var0)) {
         FaceNormal.method2907(UserComparator5.Widget_interfaceComponents[var0], -1, var1, var2, var3);
      }
   }
}
