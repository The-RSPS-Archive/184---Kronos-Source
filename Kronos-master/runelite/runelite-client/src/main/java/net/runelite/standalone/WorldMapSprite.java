package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("az")
public final class WorldMapSprite {
   @ObfuscatedName("n")
   final int[] tileColors;

   WorldMapSprite() {
      this.tileColors = new int[4096];
   }

   WorldMapSprite(int[] var1) {
      this.tileColors = var1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IIS)I",
      garbageValue = "-9220"
   )
   final int method783(int var1, int var2) {
      return this.tileColors[var2 * 64 + var1];
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IB)I",
      garbageValue = "-104"
   )
   public static int method782(int var0) {
      VarbitDefinition var2 = (VarbitDefinition)VarbitDefinition.VarbitDefinition_cached.method3032((long)var0);
      VarbitDefinition var1;
      if(var2 != null) {
         var1 = var2;
      } else {
         byte[] var7 = VarbitDefinition.VarbitDefinition_archive.method4020(14, var0, (short)-7830);
         var2 = new VarbitDefinition();
         if(var7 != null) {
            var2.method4258(new Buffer(var7));
         }

         VarbitDefinition.VarbitDefinition_cached.method3034(var2, (long)var0);
         var1 = var2;
      }

      int var3 = var1.baseVar;
      int var4 = var1.startBit;
      int var5 = var1.endBit;
      int var6 = Varps.Varps_masks[var5 - var4];
      return Varps.Varps_main[var3] >> var4 & var6;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lhp;Lhp;ZII)V",
      garbageValue = "257961128"
   )
   static void method786(AbstractArchive var0, AbstractArchive var1, boolean var2, int var3) {
      if(Login.field755) {
         if(var3 == 4) {
            Login.loginIndex = 4;
         }

      } else {
         Login.loginIndex = var3;
         Rasterizer2D.reset();
         /*byte[] var4 = var0.method4083("title.jpg", "");
         WorldMapID.leftTitleSprite = class28.method577(var4);
         class37.rightTitleSprite = WorldMapID.leftTitleSprite.method6097();
         if((Client.worldProperties & 536870912) != 0) {
            FontName.logoSprite = WorldMapRectangle.method4765(var1, "logo_deadman_mode", "");
         } else {
            FontName.logoSprite = WorldMapRectangle.method4765(var1, "logo", "");
         }*/

         //TODO: Modified [Custom Client Background]
         CustomUtils.loadClientBackground(ViewportMouse.client.canvas);

         class19.titleboxSprite = WorldMapRectangle.method4765(var1, "titlebox", "");
         AbstractRasterProvider.titlebuttonSprite = WorldMapRectangle.method4765(var1, "titlebutton", "");
         Login.runesSprite = WorldMapIcon_0.method193(var1, "runes", "");
         UserComparator7.title_muteSprite = WorldMapIcon_0.method193(var1, "title_mute", "");
         class194.options_buttons_0Sprite = WorldMapRectangle.method4765(var1, "options_radio_buttons,0", "");
         ClientPreferences.field517 = WorldMapRectangle.method4765(var1, "options_radio_buttons,4", "");
         class30.options_buttons_2Sprite = WorldMapRectangle.method4765(var1, "options_radio_buttons,2", "");
         Login.field758 = WorldMapRectangle.method4765(var1, "options_radio_buttons,6", "");
         class78.field800 = class194.options_buttons_0Sprite.subWidth;
         WorldMapCacheName.field245 = class194.options_buttons_0Sprite.subHeight;
         BufferedSink.loginScreenRunesAnimation = new LoginScreenAnimation(Login.runesSprite);
         if(var2) {
            Login.Login_username = "";
            Client.onUsernameChanged(-1);
            Login.Login_password = "";
         }

         class202.field2335 = 0;
         DesktopPlatformInfoProvider.otp = "";
         Login.field778 = true;
         Login.worldSelectOpen = false;
         if(!AbstractArchive.clientPreferences.titleMusicDisabled) {
            WorldMapData_0.method173(2, class212.archive6, "scape main", "", 255, false);
         } else {
            class197.field2173 = 1;
            class197.musicTrackArchive = null;
            class183.musicTrackGroupId = -1;
            class38.musicTrackFileId = -1;
            TileItem.field816 = 0;
            WorldMapSectionType.musicTrackBoolean = false;
            MusicPatchNode2.field2119 = 2;
         }

         DirectByteArrayCopier.method3744(false);
         Login.field755 = true;
         Login.xPadding = (FloorDecoration.canvasWidth - 765) / 2;
         Login.loginBoxX = Login.xPadding + 202;
         GrandExchangeOfferUnitPriceComparator.loginBoxCenter = Login.loginBoxX + 180;
         WorldMapID.leftTitleSprite.method6102(Login.xPadding, 0);
         class37.rightTitleSprite.method6102(Login.xPadding + 382, 0);

         //TODO:: Modified/Removed
         //FontName.logoSprite.method6320(Login.xPadding + 382 - FontName.logoSprite.subWidth / 2, 18);
      }
   }

   @ObfuscatedName("fx")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;ZB)V",
      garbageValue = "-87"
   )
   static final void method784(String var0, boolean var1) {
      if(Client.showLoadingMessages) {
         byte var2 = 4;
         int var3 = var2 + 6;
         int var4 = var2 + 6;
         int var5 = GraphicsDefaults.fontPlain12.method5389(var0, 250);
         int var6 = GraphicsDefaults.fontPlain12.method5327(var0, 250) * 13;
         Rasterizer2D.method6469(var3 - var2, var4 - var2, var2 + var5 + var2, var2 + var6 + var2, 0);
         Rasterizer2D.drawRectangle(var3 - var2, var4 - var2, var2 + var5 + var2, var2 + var2 + var6, 16777215);
         GraphicsDefaults.fontPlain12.method5333(var0, var3, var4, var5, var6, 16777215, -1, 1, 1, 0);
         int var7 = var3 - var2;
         int var8 = var4 - var2;
         int var9 = var5 + var2 + var2;
         int var10 = var2 + var6 + var2;

         int var11;
         for(var11 = 0; var11 < Client.rootWidgetCount; ++var11) {
            if(Client.rootWidgetXs[var11] + Client.rootWidgetWidths[var11] > var7 && Client.rootWidgetXs[var11] < var9 + var7 && Client.rootWidgetHeights[var11] + Client.rootWidgetYs[var11] > var8 && Client.rootWidgetYs[var11] < var8 + var10) {
               Client.field1049[var11] = true;
            }
         }

         if(var1) {
            class30.rasterProvider.vmethod6275(0, 0);
         } else {
            var11 = var3;
            int var12 = var4;
            int var13 = var5;
            int var14 = var6;

            for(int var15 = 0; var15 < Client.rootWidgetCount; ++var15) {
               if(Client.rootWidgetXs[var15] + Client.rootWidgetWidths[var15] > var11 && Client.rootWidgetXs[var15] < var11 + var13 && Client.rootWidgetYs[var15] + Client.rootWidgetHeights[var15] > var12 && Client.rootWidgetYs[var15] < var14 + var12) {
                  Client.field1050[var15] = true;
               }
            }
         }

      }
   }

   @ObfuscatedName("in")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-240794843"
   )
   static boolean method788() {
      return Client.tapToDrop;
   }
}
