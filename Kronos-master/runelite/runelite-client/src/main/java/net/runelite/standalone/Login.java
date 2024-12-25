package net.runelite.standalone;

import java.text.DecimalFormat;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("cd")
public class Login {
   @ObfuscatedName("n")
   static boolean field755;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "[Llp;"
   )
   static IndexedSprite[] runesSprite;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1085278935
   )
   static int xPadding;
   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "Llp;"
   )
   static IndexedSprite field758;
   @ObfuscatedName("aa")
   @ObfuscatedGetter(
      intValue = 1751945439
   )
   static int field769;
   @ObfuscatedName("ab")
   @ObfuscatedGetter(
      intValue = -1223310887
   )
   static int field766;
   @ObfuscatedName("ad")
   static String Login_response0;
   @ObfuscatedName("af")
   static boolean field787;
   @ObfuscatedName("ai")
   static String Login_response2;
   @ObfuscatedName("ak")
   static String Login_response3;
   @ObfuscatedName("al")
   @ObfuscatedGetter(
      intValue = -857433701
   )
   static int loginIndex;
   @ObfuscatedName("am")
   static String Login_password;
   @ObfuscatedName("an")
   static String Login_response1;
   @ObfuscatedName("aq")
   static String Login_username;
   @ObfuscatedName("au")
   static boolean field771;
   @ObfuscatedName("bb")
   @ObfuscatedGetter(
      intValue = 838409867
   )
   static int worldSelectPage;
   @ObfuscatedName("bd")
   @ObfuscatedGetter(
      intValue = -1323916819
   )
   static int currentLoginField;
   @ObfuscatedName("bh")
   static boolean field778;
   @ObfuscatedName("bi")
   @ObfuscatedGetter(
      intValue = 1218721579
   )
   static int hoveredWorldIndex;
   @ObfuscatedName("bq")
   @ObfuscatedGetter(
      longValue = 4842998671579245891L
   )
   static long field772;
   @ObfuscatedName("bs")
   static boolean worldSelectOpen;
   @ObfuscatedName("bt")
   @ObfuscatedGetter(
      intValue = -94881399
   )
   static int worldSelectPagesCount;
   @ObfuscatedName("by")
   @ObfuscatedGetter(
      longValue = 5865138585351193781L
   )
   static long field776;
   @ObfuscatedName("dn")
   @ObfuscatedSignature(
      signature = "Lfv;"
   )
   static Task js5SocketTask;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = -1258124087
   )
   static int Login_loadingPercent;
   @ObfuscatedName("h")
   @ObfuscatedGetter(
      intValue = -1106370935
   )
   static int loginBoxX;
   @ObfuscatedName("j")
   static String Login_loadingText;

   static {
      xPadding = 0;
      loginBoxX = xPadding + 202;
      Login_loadingPercent = 10;
      Login_loadingText = "";
      field769 = -1;
      field766 = 1;
      loginIndex = 0;
      Login_response0 = "";
      Login_response1 = "";
      Login_response2 = "";
      Login_response3 = "";
      Login_username = "";
      Login_password = "";
      field771 = false;
      field787 = false;
      field778 = true;
      currentLoginField = 0;
      worldSelectOpen = false;
      hoveredWorldIndex = -1;
      worldSelectPage = 0;
      worldSelectPagesCount = 0;
      new DecimalFormat("##0.00");
      new class163();
      field776 = -1L;
      field772 = -1L;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "432150558"
   )
   static int method1567(int var0, Script var1, boolean var2) {
      int var3 = -1;
      Widget var4;
      if(var0 >= 2000) {
         var0 -= 1000;
         var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         var4 = Canvas.getWidget(var3);
      } else {
         var4 = var2?GrandExchangeOfferAgeComparator.field26:KitDefinition.field3452;
      }

      if(var0 == 1000) {
         Interpreter.Interpreter_intStackSize -= 4;
         var4.rawX = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4.rawY = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         var4.xAlignment = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 2];
         var4.yAlignment = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 3];
         WorldMapSectionType.method116(var4);
         ViewportMouse.client.method1774(var4);
         if(var3 != -1 && var4.type == 0) {
            GameShell.method1005(UserComparator5.Widget_interfaceComponents[var3 >> 16], var4, false);
         }

         return 1;
      } else if(var0 == 1001) {
         Interpreter.Interpreter_intStackSize -= 4;
         var4.rawWidth = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4.rawHeight = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         var4.widthAlignment = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 2];
         var4.heightAlignment = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 3];
         WorldMapSectionType.method116(var4);
         ViewportMouse.client.method1774(var4);
         if(var3 != -1 && var4.type == 0) {
            GameShell.method1005(UserComparator5.Widget_interfaceComponents[var3 >> 16], var4, false);
         }

         return 1;
      } else if(var0 == 1003) {
         boolean var5 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
         if(var5 != var4.isHidden) {
            var4.isHidden = var5;
            var4.onHiddenChanged(-1);
            WorldMapSectionType.method116(var4);
         }

         return 1;
      } else if(var0 == 1005) {
         var4.noClickThrough = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
         return 1;
      } else if(var0 == 1006) {
         var4.noScrollThrough = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
         return 1;
      } else {
         return 2;
      }
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(III)I",
      garbageValue = "-161600604"
   )
   static final int method1552(int var0, int var1) {
      int var2 = class158.method3398(var0 - 1, var1 - 1) + class158.method3398(var0 + 1, var1 - 1) + class158.method3398(var0 - 1, 1 + var1) + class158.method3398(1 + var0, var1 + 1);
      int var3 = class158.method3398(var0 - 1, var1) + class158.method3398(var0 + 1, var1) + class158.method3398(var0, var1 - 1) + class158.method3398(var0, var1 + 1);
      int var4 = class158.method3398(var0, var1);
      return var2 / 16 + var3 / 8 + var4 / 4;
   }

   @ObfuscatedName("ge")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "8"
   )
   static void method1566() {
      int var0 = Players.Players_count;
      int[] var1 = Players.Players_indices;

      for(int var2 = 0; var2 < var0; ++var2) {
         if(var1[var2] != Client.combatTargetPlayerIndex && var1[var2] != Client.localPlayerIndex) {
            Players.method2146(Client.players[var1[var2]], true);
         }
      }

   }
}
