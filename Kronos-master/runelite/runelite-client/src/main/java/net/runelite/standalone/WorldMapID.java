package net.runelite.standalone;

import java.applet.Applet;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;
import java.net.URL;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import netscape.javascript.JSObject;

@ObfuscatedName("ar")
public class WorldMapID {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lar;"
   )
   static final WorldMapID field264;
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Llf;"
   )
   static Sprite leftTitleSprite;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lar;"
   )
   static final WorldMapID field263;
   @ObfuscatedName("g")
   @ObfuscatedGetter(
      intValue = -81572617
   )
   static int clientTickTimeIdx;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1325901065
   )
   final int value;

   static {
      field263 = new WorldMapID(0);
      field264 = new WorldMapID(1);
   }

   WorldMapID(int var1) {
      this.value = var1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;ZLjava/lang/String;ZI)V",
      garbageValue = "-1035717036"
   )
   static void method685(String var0, boolean var1, String var2, boolean var3) {
      if(var1) {
         if(!var3 && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE)) {
            label34: {
               try {
                  Desktop.getDesktop().browse(new URI(var0));
               } catch (Exception var5) {
                  break label34;
               }

               return;
            }
         }

         if(class37.field279.startsWith("win") && !var3) {
            Interpreter.method1410(var0, 0);
            return;
         }

         if(class37.field279.startsWith("mac")) {
            method684(var0, 1, var2);
            return;
         }

         Interpreter.method1410(var0, 2);
      } else {
         Interpreter.method1410(var0, 3);
      }

   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(IIII)V",
      garbageValue = "-2144797919"
   )
   static final void method686(int var0, int var1, int var2) {
      int var3;
      for(var3 = 0; var3 < 8; ++var3) {
         for(int var4 = 0; var4 < 8; ++var4) {
            Tiles.Tiles_heights[var0][var3 + var1][var4 + var2] = 0;
         }
      }

      if(var1 > 0) {
         for(var3 = 1; var3 < 8; ++var3) {
            Tiles.Tiles_heights[var0][var1][var3 + var2] = Tiles.Tiles_heights[var0][var1 - 1][var3 + var2];
         }
      }

      if(var2 > 0) {
         for(var3 = 1; var3 < 8; ++var3) {
            Tiles.Tiles_heights[var0][var3 + var1][var2] = Tiles.Tiles_heights[var0][var3 + var1][var2 - 1];
         }
      }

      if(var1 > 0 && Tiles.Tiles_heights[var0][var1 - 1][var2] != 0) {
         Tiles.Tiles_heights[var0][var1][var2] = Tiles.Tiles_heights[var0][var1 - 1][var2];
      } else if(var2 > 0 && Tiles.Tiles_heights[var0][var1][var2 - 1] != 0) {
         Tiles.Tiles_heights[var0][var1][var2] = Tiles.Tiles_heights[var0][var1][var2 - 1];
      } else if(var1 > 0 && var2 > 0 && Tiles.Tiles_heights[var0][var1 - 1][var2 - 1] != 0) {
         Tiles.Tiles_heights[var0][var1][var2] = Tiles.Tiles_heights[var0][var1 - 1][var2 - 1];
      }

   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;ILjava/lang/String;I)Z",
      garbageValue = "-1037909601"
   )
   static boolean method684(String var0, int var1, String var2) {
      boolean var10000;
      if(var1 == 0) {
         try {
            if(!class37.field279.startsWith("win")) {
               throw new Exception();
            }

            if(!var0.startsWith("http://") && !var0.startsWith("https://")) {
               throw new Exception();
            }

            String var13 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789?&=,.%+-_#:/*";

            for(int var4 = 0; var4 < var0.length(); ++var4) {
               if(var13.indexOf(var0.charAt(var4)) == -1) {
                  throw new Exception();
               }
            }

            Runtime.getRuntime().exec("cmd /c start \"j\" \"" + var0 + "\"");
            var10000 = true;
         } catch (Throwable var12) {
            return false;
         }

         return var10000;
      } else if(var1 == 1) {
         try {
            Applet var7 = class37.applet;
            Object[] var5 = new Object[]{(new URL(class37.applet.getCodeBase(), var0)).toString()};
            Object var3 = JSObject.getWindow(var7).call(var2, var5);
            var10000 = var3 != null;
         } catch (Throwable var8) {
            return false;
         }

         return var10000;
      } else if(var1 == 2) {
         try {
            class37.applet.getAppletContext().showDocument(new URL(class37.applet.getCodeBase(), var0), "_blank");
            var10000 = true;
         } catch (Exception var9) {
            return false;
         }

         return var10000;
      } else if(var1 == 3) {
         try {
            class27.method539(class37.applet, "loggedout");
         } catch (Throwable var11) {
            ;
         }

         try {
            class37.applet.getAppletContext().showDocument(new URL(class37.applet.getCodeBase(), var0), "_top");
            var10000 = true;
         } catch (Exception var10) {
            return false;
         }

         return var10000;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedName("fy")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "95"
   )
   static final void method687() {
      if(GrandExchangeOfferWorldComparator.pcmPlayer1 != null) {
         GrandExchangeOfferWorldComparator.pcmPlayer1.method2755();
      }

      if(class213.pcmPlayer0 != null) {
         class213.pcmPlayer0.method2755();
      }

   }
}
