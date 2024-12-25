package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSVarbitDefinition;

@ObfuscatedName("id")
public class VarbitDefinition extends DualNode implements RSVarbitDefinition {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable VarbitDefinition_cached;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive VarbitDefinition_archive;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1338326949
   )
   public int endBit;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 1319012991
   )
   public int startBit;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -613999551
   )
   public int baseVar;

   static {
      VarbitDefinition_cached = new EvictingDualNodeHashTable(64);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;I)V",
      garbageValue = "1106809134"
   )
   public void method4258(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            return;
         }

         this.method4259(var1, var2);
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "1640448389"
   )
   void method4259(Buffer var1, int var2) {
      if(var2 == 1) {
         this.baseVar = var1.readUnsignedShort();
         this.startBit = var1.readUnsignedByte();
         this.endBit = var1.readUnsignedByte();
      }

   }

   public int getIndex() {
      return this.baseVar;
   }

   public int getLeastSignificantBit() {
      return this.startBit;
   }

   public int getMostSignificantBit() {
      return this.endBit;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(Lkn;Lkn;Lkn;I)V",
      garbageValue = "937788655"
   )
   static void method4260(Font var0, Font var1, Font var2) {
      Login.xPadding = (FloorDecoration.canvasWidth - 765) / 2;
      Login.loginBoxX = Login.xPadding + 202;
      GrandExchangeOfferUnitPriceComparator.loginBoxCenter = Login.loginBoxX + 180;
      int var10;
      int var12;
      int var14;
      byte var23;
      int var24;
      int var28;
      int var29;
      int var30;
      int var31;
      int var32;
      int var45;
      if(Login.worldSelectOpen) {
         int var37;
         int var39;
         if(ArchiveLoader.worldSelectBackSprites == null) {
            Archive var36 = GrandExchangeOfferAgeComparator.archive8;
            var39 = var36.method4059("sl_back");
            var37 = var36.method4039(var39, "");
            Sprite[] var35 = Canvas.method781(var36, var39, var37);
            ArchiveLoader.worldSelectBackSprites = var35;
         }

         if(Language.worldSelectFlagSprites == null) {
            Language.worldSelectFlagSprites = WorldMapIcon_0.method193(GrandExchangeOfferAgeComparator.archive8, "sl_flags", "");
         }

         if(GrandExchangeOfferTotalQuantityComparator.worldSelectArrows == null) {
            GrandExchangeOfferTotalQuantityComparator.worldSelectArrows = WorldMapIcon_0.method193(GrandExchangeOfferAgeComparator.archive8, "sl_arrows", "");
         }

         if(Frames.worldSelectStars == null) {
            Frames.worldSelectStars = WorldMapIcon_0.method193(GrandExchangeOfferAgeComparator.archive8, "sl_stars", "");
         }

         if(WorldMapIcon_0.worldSelectLeftSprite == null) {
            WorldMapIcon_0.worldSelectLeftSprite = WorldMapRectangle.method4765(GrandExchangeOfferAgeComparator.archive8, "leftarrow", "");
         }

         if(HealthBar.worldSelectRightSprite == null) {
            HealthBar.worldSelectRightSprite = WorldMapRectangle.method4765(GrandExchangeOfferAgeComparator.archive8, "rightarrow", "");
         }

         Rasterizer2D.method6469(Login.xPadding, 23, 765, 480, 0);
         Rasterizer2D.method6419(Login.xPadding, 0, 125, 23, 12425273, 9135624);
         Rasterizer2D.method6419(Login.xPadding + 125, 0, 640, 23, 5197647, 2697513);
         var0.method5332("Select a world", Login.xPadding + 62, 15, 0, -1);
         if(Frames.worldSelectStars != null) {
            Frames.worldSelectStars[1].method6320(Login.xPadding + 140, 1);
            var1.method5329("Members only world", Login.xPadding + 152, 10, 16777215, -1);
            Frames.worldSelectStars[0].method6320(Login.xPadding + 140, 12);
            var1.method5329("Free world", Login.xPadding + 152, 21, 16777215, -1);
         }

         if(GrandExchangeOfferTotalQuantityComparator.worldSelectArrows != null) {
            var45 = Login.xPadding + 280;
            if(World.World_sortOption1[0] == 0 && World.World_sortOption2[0] == 0) {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[2].method6320(var45, 4);
            } else {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[0].method6320(var45, 4);
            }

            if(World.World_sortOption1[0] == 0 && World.World_sortOption2[0] == 1) {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[3].method6320(var45 + 15, 4);
            } else {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[1].method6320(var45 + 15, 4);
            }

            var0.method5329("World", var45 + 32, 17, 16777215, -1);
            var24 = Login.xPadding + 390;
            if(World.World_sortOption1[0] == 1 && World.World_sortOption2[0] == 0) {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[2].method6320(var24, 4);
            } else {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[0].method6320(var24, 4);
            }

            if(World.World_sortOption1[0] == 1 && World.World_sortOption2[0] == 1) {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[3].method6320(var24 + 15, 4);
            } else {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[1].method6320(var24 + 15, 4);
            }

            var0.method5329("Players", var24 + 32, 17, 16777215, -1);
            var39 = Login.xPadding + 500;
            if(World.World_sortOption1[0] == 2 && World.World_sortOption2[0] == 0) {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[2].method6320(var39, 4);
            } else {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[0].method6320(var39, 4);
            }

            if(World.World_sortOption1[0] == 2 && World.World_sortOption2[0] == 1) {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[3].method6320(var39 + 15, 4);
            } else {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[1].method6320(var39 + 15, 4);
            }

            var0.method5329("Location", var39 + 32, 17, 16777215, -1);
            var37 = Login.xPadding + 610;
            if(World.World_sortOption1[0] == 3 && World.World_sortOption2[0] == 0) {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[2].method6320(var37, 4);
            } else {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[0].method6320(var37, 4);
            }

            if(World.World_sortOption1[0] == 3 && World.World_sortOption2[0] == 1) {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[3].method6320(var37 + 15, 4);
            } else {
               GrandExchangeOfferTotalQuantityComparator.worldSelectArrows[1].method6320(var37 + 15, 4);
            }

            var0.method5329("Type", var37 + 32, 17, 16777215, -1);
         }

         Rasterizer2D.method6469(Login.xPadding + 708, 4, 50, 16, 0);
         var1.method5332("Cancel", Login.xPadding + 708 + 25, 16, 16777215, -1);
         Login.hoveredWorldIndex = -1;
         if(ArchiveLoader.worldSelectBackSprites != null) {
            var23 = 88;
            byte var50 = 19;
            var39 = 765 / (var23 + 1) - 1;
            var37 = 480 / (var50 + 1);

            do {
               var28 = var37;
               var29 = var39;
               if(var37 * (var39 - 1) >= World.World_count) {
                  --var39;
               }

               if(var39 * (var37 - 1) >= World.World_count) {
                  --var37;
               }

               if(var39 * (var37 - 1) >= World.World_count) {
                  --var37;
               }
            } while(var28 != var37 || var39 != var29);

            var28 = (765 - var39 * var23) / (var39 + 1);
            if(var28 > 5) {
               var28 = 5;
            }

            var29 = (480 - var50 * var37) / (var37 + 1);
            if(var29 > 5) {
               var29 = 5;
            }

            var30 = (765 - var23 * var39 - var28 * (var39 - 1)) / 2;
            var10 = (480 - var37 * var50 - var29 * (var37 - 1)) / 2;
            var31 = (var37 + World.World_count - 1) / var37;
            Login.worldSelectPagesCount = var31 - var39;
            if(WorldMapIcon_0.worldSelectLeftSprite != null && Login.worldSelectPage > 0) {
               WorldMapIcon_0.worldSelectLeftSprite.method6320(8, WallDecoration.canvasHeight / 2 - WorldMapIcon_0.worldSelectLeftSprite.subHeight / 2);
            }

            if(HealthBar.worldSelectRightSprite != null && Login.worldSelectPage < Login.worldSelectPagesCount) {
               HealthBar.worldSelectRightSprite.method6320(FloorDecoration.canvasWidth - HealthBar.worldSelectRightSprite.subWidth - 8, WallDecoration.canvasHeight / 2 - HealthBar.worldSelectRightSprite.subHeight / 2);
            }

            var12 = var10 + 23;
            var32 = var30 + Login.xPadding;
            var14 = 0;
            boolean var15 = false;
            int var16 = Login.worldSelectPage;

            int var17;
            for(var17 = var37 * var16; var17 < World.World_count && var16 - Login.worldSelectPage < var39; ++var17) {
               World var33 = World.World_worlds[var17];
               boolean var19 = true;
               String var20 = Integer.toString(var33.population);
               if(var33.population == -1) {
                  var20 = "OFF";
                  var19 = false;
               } else if(var33.population > 1980) {
                  var20 = "FULL";
                  var19 = false;
               }

               int var22 = 0;
               byte var21;
               if(var33.method1234()) {
                  if(var33.method1212()) {
                     var21 = 7;
                  } else {
                     var21 = 6;
                  }
               } else if(var33.method1214()) {
                  var22 = 16711680;
                  if(var33.method1212()) {
                     var21 = 5;
                  } else {
                     var21 = 4;
                  }
               } else if(var33.method1247()) {
                  if(var33.method1212()) {
                     var21 = 3;
                  } else {
                     var21 = 2;
                  }
               } else if(var33.method1212()) {
                  var21 = 1;
               } else {
                  var21 = 0;
               }

               if(MouseHandler.MouseHandler_x >= var32 && MouseHandler.MouseHandler_y >= var12 && MouseHandler.MouseHandler_x < var23 + var32 && MouseHandler.MouseHandler_y < var50 + var12 && var19) {
                  Login.hoveredWorldIndex = var17;
                  ArchiveLoader.worldSelectBackSprites[var21].method6112(var32, var12, 128, 16777215);
                  var15 = true;
               } else {
                  ArchiveLoader.worldSelectBackSprites[var21].method6102(var32, var12);
               }

               if(Language.worldSelectFlagSprites != null) {
                  Language.worldSelectFlagSprites[(var33.method1212()?8:0) + var33.location].method6320(var32 + 29, var12);
               }

               var0.method5332(Integer.toString(var33.id), var32 + 15, var50 / 2 + var12 + 5, var22, -1);
               var1.method5332(var20, var32 + 60, var50 / 2 + var12 + 5, 268435455, -1);
               var12 = var12 + var29 + var50;
               ++var14;
               if(var14 >= var37) {
                  var12 = var10 + 23;
                  var32 = var32 + var23 + var28;
                  var14 = 0;
                  ++var16;
               }
            }

            if(var15) {
               var17 = var1.method5324(World.World_worlds[Login.hoveredWorldIndex].activity) + 6;
               int var18 = var1.ascent + 8;
               int var43 = MouseHandler.MouseHandler_y + 25;
               if(var18 + var43 > 480) {
                  var43 = MouseHandler.MouseHandler_y - 25 - var18;
               }

               Rasterizer2D.method6469(MouseHandler.MouseHandler_x - var17 / 2, var43, var17, var18, 16777120);
               Rasterizer2D.drawRectangle(MouseHandler.MouseHandler_x - var17 / 2, var43, var17, var18, 0);
               var1.method5332(World.World_worlds[Login.hoveredWorldIndex].activity, MouseHandler.MouseHandler_x, var43 + var1.ascent + 4, 0, -1);
            }
         }

         class30.rasterProvider.vmethod6275(0, 0);
      } else {
         WorldMapID.leftTitleSprite.method6102(Login.xPadding, 0);
         class37.rightTitleSprite.method6102(Login.xPadding + 382, 0);
         //TODO:: Modified/Removed
         //FontName.logoSprite.method6320(Login.xPadding + 382 - FontName.logoSprite.subWidth / 2, 18);
         if(Client.gameState == 0 || Client.gameState == 5) {
            var23 = 20;
            var0.method5332("RuneScape is loading - please wait...", Login.loginBoxX + 180, 245 - var23, 16777215, -1);
            var24 = 253 - var23;
            Rasterizer2D.drawRectangle(Login.loginBoxX + 180 - 152, var24, 304, 34, 9179409);
            Rasterizer2D.drawRectangle(Login.loginBoxX + 180 - 151, var24 + 1, 302, 32, 0);
            Rasterizer2D.method6469(Login.loginBoxX + 180 - 150, var24 + 2, Login.Login_loadingPercent * 3, 30, 9179409);
            Rasterizer2D.method6469(Login.Login_loadingPercent * 3 + (Login.loginBoxX + 180 - 150), var24 + 2, 300 - Login.Login_loadingPercent * 3, 30, 0);
            var0.method5332(Login.Login_loadingText, Login.loginBoxX + 180, 276 - var23, 16777215, -1);
         }

         String var8;
         String var9;
         char[] var11;
         char[] var13;
         String var25;
         String var27;
         String var40;
         short var44;
         short var46;
         if(Client.gameState == 20) {
            class19.titleboxSprite.method6320(Login.loginBoxX + 180 - class19.titleboxSprite.subWidth / 2, 271 - class19.titleboxSprite.subHeight / 2);
            var44 = 201;
            var0.method5332(Login.Login_response1, Login.loginBoxX + 180, var44, 16776960, 0);
            var45 = var44 + 15;
            var0.method5332(Login.Login_response2, Login.loginBoxX + 180, var45, 16776960, 0);
            var45 += 15;
            var0.method5332(Login.Login_response3, Login.loginBoxX + 180, var45, 16776960, 0);
            var45 += 15;
            var45 += 7;
            if(Login.loginIndex != 4) {
               var0.method5329("Login: ", Login.loginBoxX + 180 - 110, var45, 16777215, 0);
               var46 = 200;
               if(!AbstractArchive.clientPreferences.hideUsername) {
                  var25 = Login.Login_username;
               } else {
                  var8 = Login.Login_username;
                  var10 = var8.length();
                  var11 = new char[var10];

                  for(var12 = 0; var12 < var10; ++var12) {
                     var11[var12] = '*';
                  }

                  var9 = new String(var11);
                  var25 = var9;
               }

               for(var25 = var25; var0.method5324(var25) > var46; var25 = var25.substring(0, var25.length() - 1)) {
                  ;
               }

               var0.method5329(AbstractFont.method5328(var25), Login.loginBoxX + 180 - 70, var45, 16777215, 0);
               var45 += 15;
               var8 = "Password: ";
               var27 = Login.Login_password;
               var12 = var27.length();
               var13 = new char[var12];

               for(var14 = 0; var14 < var12; ++var14) {
                  var13[var14] = '*';
               }

               var40 = new String(var13);
               var0.method5329(var8 + var40, Login.loginBoxX + 180 - 108, var45, 16777215, 0);
               var45 += 15;
            }
         }

         if(Client.gameState == 10 || Client.gameState == 11) {
            class19.titleboxSprite.method6320(Login.loginBoxX, 171);
            short var5;
            if(Login.loginIndex == 0) {
               var44 = 251;
               var0.method5332("Welcome to Kronos", Login.loginBoxX + 180, var44, 16776960, 0);
               var45 = var44 + 30;
               var24 = Login.loginBoxX + 180 - 80;
               var5 = 291;
               AbstractRasterProvider.titlebuttonSprite.method6320(var24 - 73, var5 - 20);
               var0.method5333("New User", var24 - 73, var5 - 20, 144, 40, 16777215, 0, 1, 1, 0);
               var24 = Login.loginBoxX + 180 + 80;
               AbstractRasterProvider.titlebuttonSprite.method6320(var24 - 73, var5 - 20);
               var0.method5333("Existing User", var24 - 73, var5 - 20, 144, 40, 16777215, 0, 1, 1, 0);
            } else if(Login.loginIndex == 1) {
               var0.method5332(Login.Login_response0, Login.loginBoxX + 180, 201, 16776960, 0);
               var44 = 236;
               var0.method5332(Login.Login_response1, Login.loginBoxX + 180, var44, 16777215, 0);
               var45 = var44 + 15;
               var0.method5332(Login.Login_response2, Login.loginBoxX + 180, var45, 16777215, 0);
               var45 += 15;
               var0.method5332(Login.Login_response3, Login.loginBoxX + 180, var45, 16777215, 0);
               var45 += 15;
               var24 = Login.loginBoxX + 180 - 80;
               var5 = 321;
               AbstractRasterProvider.titlebuttonSprite.method6320(var24 - 73, var5 - 20);
               var0.method5332("Continue", var24, var5 + 5, 16777215, 0);
               var24 = Login.loginBoxX + 180 + 80;
               AbstractRasterProvider.titlebuttonSprite.method6320(var24 - 73, var5 - 20);
               var0.method5332("Cancel", var24, var5 + 5, 16777215, 0);
            } else if(Login.loginIndex == 2) {
               var44 = 201;
               var0.method5332(Login.Login_response1, GrandExchangeOfferUnitPriceComparator.loginBoxCenter, var44, 16776960, 0);
               var45 = var44 + 15;
               var0.method5332(Login.Login_response2, GrandExchangeOfferUnitPriceComparator.loginBoxCenter, var45, 16776960, 0);
               var45 += 15;
               var0.method5332(Login.Login_response3, GrandExchangeOfferUnitPriceComparator.loginBoxCenter, var45, 16776960, 0);
               var45 += 15;
               var45 += 7;
               var0.method5329("Login: ", GrandExchangeOfferUnitPriceComparator.loginBoxCenter - 110, var45, 16777215, 0);
               var46 = 200;
               if(!AbstractArchive.clientPreferences.hideUsername) {
                  var25 = Login.Login_username;
               } else {
                  var8 = Login.Login_username;
                  var10 = var8.length();
                  var11 = new char[var10];

                  for(var12 = 0; var12 < var10; ++var12) {
                     var11[var12] = '*';
                  }

                  var9 = new String(var11);
                  var25 = var9;
               }

               for(var25 = var25; var0.method5324(var25) > var46; var25 = var25.substring(1)) {
                  ;
               }

               var0.method5329(AbstractFont.method5328(var25) + (Login.currentLoginField == 0 & Client.cycle % 40 < 20?World.method1251(16776960) + "|":""), GrandExchangeOfferUnitPriceComparator.loginBoxCenter - 70, var45, 16777215, 0);
               var45 += 15;
               var8 = "Password: ";
               var27 = Login.Login_password;
               var12 = var27.length();
               var13 = new char[var12];

               for(var14 = 0; var14 < var12; ++var14) {
                  var13[var14] = '*';
               }

               var40 = new String(var13);
               var0.method5329(var8 + var40 + (Login.currentLoginField == 1 & Client.cycle % 40 < 20?World.method1251(16776960) + "|":""), GrandExchangeOfferUnitPriceComparator.loginBoxCenter - 108, var45, 16777215, 0);
               var45 += 15;
               var44 = 277;
               var31 = GrandExchangeOfferUnitPriceComparator.loginBoxCenter + -117;
               IndexedSprite var34 = ClientPacket.method3876(Client.Login_isUsernameRemembered, Login.field771);
               var34.method6320(var31, var44);
               var31 = var31 + var34.subWidth + 5;
               var1.method5329("Remember username", var31, var44 + 13, 16776960, 0);
               var31 = GrandExchangeOfferUnitPriceComparator.loginBoxCenter + 24;
               var34 = ClientPacket.method3876(AbstractArchive.clientPreferences.hideUsername, Login.field787);
               var34.method6320(var31, var44);
               var31 = var31 + var34.subWidth + 5;
               var1.method5329("Hide username", var31, var44 + 13, 16776960, 0);
               var45 = var44 + 15;
               var32 = GrandExchangeOfferUnitPriceComparator.loginBoxCenter - 80;
               short var42 = 321;
               AbstractRasterProvider.titlebuttonSprite.method6320(var32 - 73, var42 - 20);
               var0.method5332("Login", var32, var42 + 5, 16777215, 0);
               var32 = GrandExchangeOfferUnitPriceComparator.loginBoxCenter + 80;
               AbstractRasterProvider.titlebuttonSprite.method6320(var32 - 73, var42 - 20);
               var0.method5332("Cancel", var32, var42 + 5, 16777215, 0);
               var44 = 357;
               switch(Login.field766) {
               case 2:
                  class11.field80 = "Having trouble logging in?";
                  break;
               default:
                  class11.field80 = "Can\'t login? Click here.";
               }

               LoginScreenAnimation.field610 = new Bounds(GrandExchangeOfferUnitPriceComparator.loginBoxCenter, var44, var1.method5324(class11.field80), 11);
               class163.field1985 = new Bounds(GrandExchangeOfferUnitPriceComparator.loginBoxCenter, var44, var1.method5324("Still having trouble logging in?"), 11);
               var1.method5332(class11.field80, GrandExchangeOfferUnitPriceComparator.loginBoxCenter, var44, 16777215, 0);
            } else if(Login.loginIndex == 3) {
               var44 = 201;
               var0.method5332("Invalid credentials.", Login.loginBoxX + 180, var44, 16776960, 0);
               var45 = var44 + 20;
               var1.method5332("For accounts created after 24th November 2010, please use your", Login.loginBoxX + 180, var45, 16776960, 0);
               var45 += 15;
               var1.method5332("email address to login. Otherwise please login with your username.", Login.loginBoxX + 180, var45, 16776960, 0);
               var45 += 15;
               var24 = Login.loginBoxX + 180;
               var5 = 276;
               AbstractRasterProvider.titlebuttonSprite.method6320(var24 - 73, var5 - 20);
               var2.method5332("Try again", var24, var5 + 5, 16777215, 0);
               var24 = Login.loginBoxX + 180;
               var5 = 326;
               AbstractRasterProvider.titlebuttonSprite.method6320(var24 - 73, var5 - 20);
               var2.method5332("Forgotten password?", var24, var5 + 5, 16777215, 0);
            } else {
               String var26;
               if(Login.loginIndex == 4) {
                  var0.method5332("Authenticator", Login.loginBoxX + 180, 201, 16776960, 0);
                  var44 = 236;
                  var0.method5332(Login.Login_response1, Login.loginBoxX + 180, var44, 16777215, 0);
                  var45 = var44 + 15;
                  var0.method5332(Login.Login_response2, Login.loginBoxX + 180, var45, 16777215, 0);
                  var45 += 15;
                  var0.method5332(Login.Login_response3, Login.loginBoxX + 180, var45, 16777215, 0);
                  var45 += 15;
                  var26 = "PIN: ";
                  String var7 = DesktopPlatformInfoProvider.otp;
                  var30 = var7.length();
                  char[] var47 = new char[var30];

                  for(var31 = 0; var31 < var30; ++var31) {
                     var47[var31] = '*';
                  }

                  var8 = new String(var47);
                  var0.method5329(var26 + var8 + (Client.cycle % 40 < 20?World.method1251(16776960) + "|":""), Login.loginBoxX + 180 - 108, var45, 16777215, 0);
                  var45 -= 8;
                  var0.method5329("Trust this computer", Login.loginBoxX + 180 - 9, var45, 16776960, 0);
                  var45 += 15;
                  var0.method5329("for 30 days: ", Login.loginBoxX + 180 - 9, var45, 16776960, 0);
                  var29 = Login.loginBoxX + 180 - 9 + var0.method5324("for 30 days: ") + 15;
                  var30 = var45 - var0.ascent;
                  IndexedSprite var48;
                  if(Login.field778) {
                     var48 = class30.options_buttons_2Sprite;
                  } else {
                     var48 = class194.options_buttons_0Sprite;
                  }

                  var48.method6320(var29, var30);
                  var45 += 15;
                  var31 = Login.loginBoxX + 180 - 80;
                  short var41 = 321;
                  AbstractRasterProvider.titlebuttonSprite.method6320(var31 - 73, var41 - 20);
                  var0.method5332("Continue", var31, var41 + 5, 16777215, 0);
                  var31 = Login.loginBoxX + 180 + 80;
                  AbstractRasterProvider.titlebuttonSprite.method6320(var31 - 73, var41 - 20);
                  var0.method5332("Cancel", var31, var41 + 5, 16777215, 0);
                  var1.method5332("<u=ff>Can\'t Log In?</u>", Login.loginBoxX + 180, var41 + 36, 255, 0);
               } else {
                  short var49;
                  if(Login.loginIndex == 5) {
                     var0.method5332("Forgotten your password?", Login.loginBoxX + 180, 201, 16776960, 0);
                     var44 = 221;
                     var2.method5332(Login.Login_response1, Login.loginBoxX + 180, var44, 16776960, 0);
                     var45 = var44 + 15;
                     var2.method5332(Login.Login_response2, Login.loginBoxX + 180, var45, 16776960, 0);
                     var45 += 15;
                     var2.method5332(Login.Login_response3, Login.loginBoxX + 180, var45, 16776960, 0);
                     var45 += 15;
                     var45 += 14;
                     var0.method5329("Username/email: ", Login.loginBoxX + 180 - 145, var45, 16777215, 0);
                     var46 = 174;
                     if(!AbstractArchive.clientPreferences.hideUsername) {
                        var25 = Login.Login_username;
                     } else {
                        var8 = Login.Login_username;
                        var10 = var8.length();
                        var11 = new char[var10];

                        for(var12 = 0; var12 < var10; ++var12) {
                           var11[var12] = '*';
                        }

                        var9 = new String(var11);
                        var25 = var9;
                     }

                     for(var25 = var25; var0.method5324(var25) > var46; var25 = var25.substring(1)) {
                        ;
                     }

                     var0.method5329(AbstractFont.method5328(var25) + (Client.cycle % 40 < 20?World.method1251(16776960) + "|":""), Login.loginBoxX + 180 - 34, var45, 16777215, 0);
                     var45 += 15;
                     var28 = Login.loginBoxX + 180 - 80;
                     var49 = 321;
                     AbstractRasterProvider.titlebuttonSprite.method6320(var28 - 73, var49 - 20);
                     var0.method5332("Recover", var28, var49 + 5, 16777215, 0);
                     var28 = Login.loginBoxX + 180 + 80;
                     AbstractRasterProvider.titlebuttonSprite.method6320(var28 - 73, var49 - 20);
                     var0.method5332("Back", var28, var49 + 5, 16777215, 0);
                     var49 = 356;
                     var1.method5332("Still having trouble logging in?", GrandExchangeOfferUnitPriceComparator.loginBoxCenter, var49, 268435455, 0);
                  } else if(Login.loginIndex == 6) {
                     var44 = 201;
                     var0.method5332(Login.Login_response1, Login.loginBoxX + 180, var44, 16776960, 0);
                     var45 = var44 + 15;
                     var0.method5332(Login.Login_response2, Login.loginBoxX + 180, var45, 16776960, 0);
                     var45 += 15;
                     var0.method5332(Login.Login_response3, Login.loginBoxX + 180, var45, 16776960, 0);
                     var45 += 15;
                     var24 = Login.loginBoxX + 180;
                     var5 = 321;
                     AbstractRasterProvider.titlebuttonSprite.method6320(var24 - 73, var5 - 20);
                     var0.method5332("Back", var24, var5 + 5, 16777215, 0);
                  } else if(Login.loginIndex == 7) {
                     var44 = 216;
                     var0.method5332("Your date of birth isn\'t set.", Login.loginBoxX + 180, var44, 16776960, 0);
                     var45 = var44 + 15;
                     var2.method5332("Please verify your account status by", Login.loginBoxX + 180, var45, 16776960, 0);
                     var45 += 15;
                     var2.method5332("setting your date of birth.", Login.loginBoxX + 180, var45, 16776960, 0);
                     var45 += 15;
                     var24 = Login.loginBoxX + 180 - 80;
                     var5 = 321;
                     AbstractRasterProvider.titlebuttonSprite.method6320(var24 - 73, var5 - 20);
                     var0.method5332("Set Date of Birth", var24, var5 + 5, 16777215, 0);
                     var24 = Login.loginBoxX + 180 + 80;
                     AbstractRasterProvider.titlebuttonSprite.method6320(var24 - 73, var5 - 20);
                     var0.method5332("Back", var24, var5 + 5, 16777215, 0);
                  } else if(Login.loginIndex == 8) {
                     var44 = 216;
                     var0.method5332("Sorry, but your account is not eligible to play.", Login.loginBoxX + 180, var44, 16776960, 0);
                     var45 = var44 + 15;
                     var2.method5332("For more information, please take a look at", Login.loginBoxX + 180, var45, 16776960, 0);
                     var45 += 15;
                     var2.method5332("our privacy policy.", Login.loginBoxX + 180, var45, 16776960, 0);
                     var45 += 15;
                     var24 = Login.loginBoxX + 180 - 80;
                     var5 = 321;
                     AbstractRasterProvider.titlebuttonSprite.method6320(var24 - 73, var5 - 20);
                     var0.method5332("Privacy Policy", var24, var5 + 5, 16777215, 0);
                     var24 = Login.loginBoxX + 180 + 80;
                     AbstractRasterProvider.titlebuttonSprite.method6320(var24 - 73, var5 - 20);
                     var0.method5332("Back", var24, var5 + 5, 16777215, 0);
                  } else if(Login.loginIndex == 12) {
                     var44 = 201;
                     String var4 = "";
                     var26 = "";
                     var25 = "";
                     switch(Login.field769) {
                     case 0:
                        var4 = "Your account has been disabled.";
                        var26 = Strings.field2838;
                        var25 = "";
                        break;
                     case 1:
                        var4 = "Account locked as we suspect it has been stolen.";
                        var26 = Strings.field2828;
                        var25 = "";
                        break;
                     default:
                        TilePaint.method2911(false);
                     }

                     var0.method5332(var4, Login.loginBoxX + 180, var44, 16776960, 0);
                     var45 = var44 + 15;
                     var2.method5332(var26, Login.loginBoxX + 180, var45, 16776960, 0);
                     var45 += 15;
                     var2.method5332(var25, Login.loginBoxX + 180, var45, 16776960, 0);
                     var45 += 15;
                     var28 = Login.loginBoxX + 180;
                     var49 = 276;
                     AbstractRasterProvider.titlebuttonSprite.method6320(var28 - 73, var49 - 20);
                     var0.method5332("Support Page", var28, var49 + 5, 16777215, 0);
                     var28 = Login.loginBoxX + 180;
                     var49 = 326;
                     AbstractRasterProvider.titlebuttonSprite.method6320(var28 - 73, var49 - 20);
                     var0.method5332("Back", var28, var49 + 5, 16777215, 0);
                  } else if(Login.loginIndex == 24) {
                     var44 = 221;
                     var0.method5332(Login.Login_response1, Login.loginBoxX + 180, var44, 16777215, 0);
                     var45 = var44 + 15;
                     var0.method5332(Login.Login_response2, Login.loginBoxX + 180, var45, 16777215, 0);
                     var45 += 15;
                     var0.method5332(Login.Login_response3, Login.loginBoxX + 180, var45, 16777215, 0);
                     var45 += 15;
                     var24 = Login.loginBoxX + 180;
                     var5 = 301;
                     AbstractRasterProvider.titlebuttonSprite.method6320(var24 - 73, var5 - 20);
                     var0.method5332("Ok", var24, var5 + 5, 16777215, 0);
                  }
               }
            }
         }

         //TODO: Modified Controls Flames
         /*if(Client.gameState >= 10) {
            int[] var3 = new int[4];
            Rasterizer2D.method6412(var3);
            Rasterizer2D.method6474(Login.xPadding, 0, Login.xPadding + 765, WallDecoration.canvasHeight);
            BufferedSink.loginScreenRunesAnimation.method1267(Login.xPadding - 22, Client.cycle);
            BufferedSink.loginScreenRunesAnimation.method1267(Login.xPadding + 22 + 765 - 128, Client.cycle);
            Rasterizer2D.method6429(var3);
         }*/

         UserComparator7.title_muteSprite[AbstractArchive.clientPreferences.titleMusicDisabled?1:0].method6320(Login.xPadding + 765 - 40, 463);
         if(Client.gameState > 5 && Language.Language_EN == WorldMapLabelSize.clientLanguage) {
            if(Skeleton.field1821 != null) {
               var45 = Login.xPadding + 5;
               var46 = 463;
               byte var38 = 100;
               byte var6 = 35;
               Skeleton.field1821.method6320(var45, var46);
               var0.method5332("World" + " " + Client.worldId, var38 / 2 + var45, var6 / 2 + var46 - 2, 16777215, 0);
               if(class37.World_request != null) {
                  var1.method5332("Loading...", var38 / 2 + var45, var6 / 2 + var46 + 12, 16777215, 0);
               } else {
                  var1.method5332("Click to switch", var38 / 2 + var45, var6 / 2 + var46 + 12, 16777215, 0);
               }
            } else {
               Skeleton.field1821 = WorldMapRectangle.method4765(GrandExchangeOfferAgeComparator.archive8, "sl_button", "");
            }
         }

      }
   }
}
