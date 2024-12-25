package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSMouseRecorder;

@ObfuscatedName("bn")
public class MouseRecorder implements Runnable, RSMouseRecorder {
   @ObfuscatedName("qc")
   @ObfuscatedGetter(
      intValue = -749855997
   )
   static int field543;
   @ObfuscatedName("n")
   Object lock;
   @ObfuscatedName("p")
   long[] millis;
   @ObfuscatedName("r")
   int[] ys;
   @ObfuscatedName("u")
   int[] xs;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -395886809
   )
   int index;
   @ObfuscatedName("z")
   boolean isRunning;

   MouseRecorder() {
      this.isRunning = true;
      this.lock = new Object();
      this.index = 0;
      this.xs = new int[500];
      this.ys = new int[500];
      this.millis = new long[500];
   }

   public void run() {
      while(this.isRunning) {
         Object var1 = this.lock;
         synchronized(this.lock) {
            if(this.index < 500) {
               this.xs[this.index] = MouseHandler.MouseHandler_x;
               this.ys[this.index] = MouseHandler.MouseHandler_y;
               this.millis[this.index] = MouseHandler.MouseHandler_millis;
               ++this.index;
            }
         }

         long var4 = 49L;

         try {
            Thread.sleep(var4);
         } catch (InterruptedException var7) {
            ;
         }

         try {
            Thread.sleep(1L);
         } catch (InterruptedException var6) {
            ;
         }
      }

   }

   public int getIndex() {
      return this.index;
   }

   public int[] getXs() {
      return this.xs;
   }

   public int[] getYs() {
      return this.ys;
   }

   public long[] getMillis() {
      return this.millis;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/CharSequence;IZI)Z",
      garbageValue = "1781175129"
   )
   static boolean method1204(CharSequence var0, int var1, boolean var2) {
      if(var1 >= 2 && var1 <= 36) {
         boolean var3 = false;
         boolean var4 = false;
         int var5 = 0;
         int var6 = var0.length();

         for(int var7 = 0; var7 < var6; ++var7) {
            char var8 = var0.charAt(var7);
            if(var7 == 0) {
               if(var8 == '-') {
                  var3 = true;
                  continue;
               }

               if(var8 == '+') {
                  continue;
               }
            }

            int var10;
            if(var8 >= '0' && var8 <= '9') {
               var10 = var8 - '0';
            } else if(var8 >= 'A' && var8 <= 'Z') {
               var10 = var8 - '7';
            } else {
               if(var8 < 'a' || var8 > 'z') {
                  return false;
               }

               var10 = var8 - 'W';
            }

            if(var10 >= var1) {
               return false;
            }

            if(var3) {
               var10 = -var10;
            }

            int var9 = var10 + var5 * var1;
            if(var9 / var1 != var5) {
               return false;
            }

            var5 = var9;
            var4 = true;
         }

         return var4;
      } else {
         throw new IllegalArgumentException("" + var1);
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lhp;Lhp;Lhp;Lhp;I)V",
      garbageValue = "757656787"
   )
   public static void method1209(AbstractArchive var0, AbstractArchive var1, AbstractArchive var2, AbstractArchive var3) {
      Widget.Widget_archive = var0;
      TaskHandler.Widget_modelsArchive = var1;
      ClientPreferences.Widget_spritesArchive = var2;
      class12.Widget_fontsArchive = var3;
      UserComparator5.Widget_interfaceComponents = new Widget[Widget.Widget_archive.method4033()][];
      ViewportMouse.Widget_loadedInterfaces = new boolean[Widget.Widget_archive.method4033()];
   }

   @ObfuscatedName("ej")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-1180818017"
   )
   static void method1207(int var0) {
      if(var0 != Client.gameState) {
         if(Client.gameState == 0) {
            ViewportMouse.client.method1037();
         }

         if(var0 == 20 || var0 == 40 || var0 == 45) {
            Client.loginState = 0;
            Client.field877 = 0;
            Client.field878 = 0;
            Client.timer.method4850(var0);
            if(var0 != 20) {
               PlayerType.method3947(false);
            }
         }

         if(var0 != 20 && var0 != 40 && class33.field251 != null) {
            class33.field251.vmethod5821();
            class33.field251 = null;
         }

         if(Client.gameState == 25) {
            Client.field901 = 0;
            Client.field897 = 0;
            Client.field898 = 1;
            Client.field899 = 0;
            Client.field900 = 1;
         }

         if(var0 != 5 && var0 != 10) {
            if(var0 == 20) {
               WorldMapSprite.method786(Client.archive10, GrandExchangeOfferAgeComparator.archive8, true, Client.gameState == 11?4:0);
            } else if(var0 == 11) {
               WorldMapSprite.method786(Client.archive10, GrandExchangeOfferAgeComparator.archive8, false, 4);
            } else if(Login.field755) {
               class19.titleboxSprite = null;
               AbstractRasterProvider.titlebuttonSprite = null;
               Login.runesSprite = null;
               WorldMapID.leftTitleSprite = null;
               class37.rightTitleSprite = null;
               FontName.logoSprite = null;
               UserComparator7.title_muteSprite = null;
               class194.options_buttons_0Sprite = null;
               class30.options_buttons_2Sprite = null;
               ArchiveLoader.worldSelectBackSprites = null;
               Language.worldSelectFlagSprites = null;
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows = null;
               Frames.worldSelectStars = null;
               Skeleton.field1821 = null;
               BufferedSink.loginScreenRunesAnimation.method1266();
               class197.field2173 = 1;
               class197.musicTrackArchive = null;
               class183.musicTrackGroupId = -1;
               class38.musicTrackFileId = -1;
               TileItem.field816 = 0;
               WorldMapSectionType.musicTrackBoolean = false;
               MusicPatchNode2.field2119 = 2;
               DirectByteArrayCopier.method3744(true);
               Login.field755 = false;
            }
         } else {
            WorldMapSprite.method786(Client.archive10, GrandExchangeOfferAgeComparator.archive8, true, 0);
         }

         Client.gameState = var0;
         Client.gameStateChanged(-1);
      }
   }

   @ObfuscatedName("fb")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "861469651"
   )
   static final void method1208() {
      if(Client.logoutTimer > 0) {
         DynamicObject.method1570();
      } else {
         Client.timer.method4849();
         method1207(40);
         class33.field251 = Client.packetWriter.method1624();
         Client.packetWriter.method1637();
      }
   }

   @ObfuscatedName("jv")
   @ObfuscatedSignature(
      signature = "([Lho;II)V",
      garbageValue = "-1695750792"
   )
   static final void method1206(Widget[] var0, int var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         Widget var3 = var0[var2];
         if(var3 != null) {
            if(var3.type == 0) {
               if(var3.children != null) {
                  method1206(var3.children, var1);
               }

               InterfaceParent var4 = (InterfaceParent)Client.interfaceParents.method6346((long)var3.id);
               if(var4 != null) {
                  class28.method588(var4.group, var1);
               }
            }

            ScriptEvent var5;
            if(var1 == 0 && var3.onDialogAbort != null) {
               var5 = new ScriptEvent();
               var5.widget = var3;
               var5.args = var3.onDialogAbort;
               ParamDefinition.method4313(var5);
            }

            if(var1 == 1 && var3.onSubChange != null) {
               if(var3.childIndex >= 0) {
                  Widget var6 = Canvas.getWidget(var3.id);
                  if(var6 == null || var6.children == null || var3.childIndex >= var6.children.length || var3 != var6.children[var3.childIndex]) {
                     continue;
                  }
               }

               var5 = new ScriptEvent();
               var5.widget = var3;
               var5.args = var3.onSubChange;
               ParamDefinition.method4313(var5);
            }
         }
      }

   }
}
