package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ge")
public class LoginPacket implements class168 {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lge;"
   )
   static final LoginPacket field2103;
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "[Lge;"
   )
   static final LoginPacket[] LoginPacket_indexedValues;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Lge;"
   )
   static final LoginPacket field2106;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lge;"
   )
   public static final LoginPacket requestWorldReconnect;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lge;"
   )
   public static final LoginPacket requestWorldLogin;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lge;"
   )
   public static final LoginPacket handshake;
   @ObfuscatedName("ff")
   @ObfuscatedSignature(
      signature = "Lfv;"
   )
   static Task socketTask;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -1737377539
   )
   public final int id;

   static {
      handshake = new LoginPacket(14, 0);
      field2103 = new LoginPacket(15, 4);
      requestWorldLogin = new LoginPacket(16, -2);
      requestWorldReconnect = new LoginPacket(18, -2);
      field2106 = new LoginPacket(27, 0);
      LoginPacket_indexedValues = new LoginPacket[32];
      LoginPacket[] var0 = class198.method3818();

      for(int var1 = 0; var1 < var0.length; ++var1) {
         LoginPacket_indexedValues[var0[var1].id] = var0[var1];
      }

   }

   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "0"
   )
   LoginPacket(int var1, int var2) {
      this.id = var1;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lbh;S)V",
      garbageValue = "21228"
   )
   static void method3724(GameShell var0) {
      int var4;
      if(Login.worldSelectOpen) {
         while(true) {
            if(!DynamicObject.method1568()) {
               if(MouseHandler.MouseHandler_lastButton != 1 && (WorldMapIcon_1.mouseCam || MouseHandler.MouseHandler_lastButton != 4)) {
                  break;
               }

               int var1 = Login.xPadding + 280;
               if(MouseHandler.MouseHandler_lastPressedX >= var1 && MouseHandler.MouseHandler_lastPressedX <= var1 + 14 && MouseHandler.MouseHandler_lastPressedY >= 4 && MouseHandler.MouseHandler_lastPressedY <= 18) {
                  class198.method3814(0, 0);
                  break;
               }

               if(MouseHandler.MouseHandler_lastPressedX >= var1 + 15 && MouseHandler.MouseHandler_lastPressedX <= var1 + 80 && MouseHandler.MouseHandler_lastPressedY >= 4 && MouseHandler.MouseHandler_lastPressedY <= 18) {
                  class198.method3814(0, 1);
                  break;
               }

               int var2 = Login.xPadding + 390;
               if(MouseHandler.MouseHandler_lastPressedX >= var2 && MouseHandler.MouseHandler_lastPressedX <= var2 + 14 && MouseHandler.MouseHandler_lastPressedY >= 4 && MouseHandler.MouseHandler_lastPressedY <= 18) {
                  class198.method3814(1, 0);
                  break;
               }

               if(MouseHandler.MouseHandler_lastPressedX >= var2 + 15 && MouseHandler.MouseHandler_lastPressedX <= var2 + 80 && MouseHandler.MouseHandler_lastPressedY >= 4 && MouseHandler.MouseHandler_lastPressedY <= 18) {
                  class198.method3814(1, 1);
                  break;
               }

               int var17 = Login.xPadding + 500;
               if(MouseHandler.MouseHandler_lastPressedX >= var17 && MouseHandler.MouseHandler_lastPressedX <= var17 + 14 && MouseHandler.MouseHandler_lastPressedY >= 4 && MouseHandler.MouseHandler_lastPressedY <= 18) {
                  class198.method3814(2, 0);
                  break;
               }

               if(MouseHandler.MouseHandler_lastPressedX >= var17 + 15 && MouseHandler.MouseHandler_lastPressedX <= var17 + 80 && MouseHandler.MouseHandler_lastPressedY >= 4 && MouseHandler.MouseHandler_lastPressedY <= 18) {
                  class198.method3814(2, 1);
                  break;
               }

               var4 = Login.xPadding + 610;
               if(MouseHandler.MouseHandler_lastPressedX >= var4 && MouseHandler.MouseHandler_lastPressedX <= var4 + 14 && MouseHandler.MouseHandler_lastPressedY >= 4 && MouseHandler.MouseHandler_lastPressedY <= 18) {
                  class198.method3814(3, 0);
                  break;
               }

               if(MouseHandler.MouseHandler_lastPressedX >= var4 + 15 && MouseHandler.MouseHandler_lastPressedX <= var4 + 80 && MouseHandler.MouseHandler_lastPressedY >= 4 && MouseHandler.MouseHandler_lastPressedY <= 18) {
                  class198.method3814(3, 1);
                  break;
               }

               if(MouseHandler.MouseHandler_lastPressedX >= Login.xPadding + 708 && MouseHandler.MouseHandler_lastPressedY >= 4 && MouseHandler.MouseHandler_lastPressedX <= Login.xPadding + 708 + 50 && MouseHandler.MouseHandler_lastPressedY <= 20) {
                  Coord.method3929();
                  break;
               }

               if(Login.hoveredWorldIndex != -1) {
                  World var5 = World.World_worlds[Login.hoveredWorldIndex];
                  class8.method101(var5);
                  Coord.method3929();
               } else {
                  if(Login.worldSelectPage > 0 && WorldMapIcon_0.worldSelectLeftSprite != null && MouseHandler.MouseHandler_lastPressedX >= 0 && MouseHandler.MouseHandler_lastPressedX <= WorldMapIcon_0.worldSelectLeftSprite.subWidth && MouseHandler.MouseHandler_lastPressedY >= WallDecoration.canvasHeight / 2 - 50 && MouseHandler.MouseHandler_lastPressedY <= WallDecoration.canvasHeight / 2 + 50) {
                     --Login.worldSelectPage;
                  }

                  if(Login.worldSelectPage < Login.worldSelectPagesCount && HealthBar.worldSelectRightSprite != null && MouseHandler.MouseHandler_lastPressedX >= FloorDecoration.canvasWidth - HealthBar.worldSelectRightSprite.subWidth - 5 && MouseHandler.MouseHandler_lastPressedX <= FloorDecoration.canvasWidth && MouseHandler.MouseHandler_lastPressedY >= WallDecoration.canvasHeight / 2 - 50 && MouseHandler.MouseHandler_lastPressedY <= WallDecoration.canvasHeight / 2 + 50) {
                     ++Login.worldSelectPage;
                  }
               }
               break;
            }

            if(SecureRandomFuture.field748 == 13) {
               Coord.method3929();
               break;
            }

            if(SecureRandomFuture.field748 == 96) {
               if(Login.worldSelectPage > 0 && WorldMapIcon_0.worldSelectLeftSprite != null) {
                  --Login.worldSelectPage;
               }
            } else if(SecureRandomFuture.field748 == 97 && Login.worldSelectPage < Login.worldSelectPagesCount && HealthBar.worldSelectRightSprite != null) {
               ++Login.worldSelectPage;
            }
         }

      } else {
         if((MouseHandler.MouseHandler_lastButton == 1 || !WorldMapIcon_1.mouseCam && MouseHandler.MouseHandler_lastButton == 4) && MouseHandler.MouseHandler_lastPressedX >= Login.xPadding + 765 - 50 && MouseHandler.MouseHandler_lastPressedY >= 453) {
            AbstractArchive.clientPreferences.titleMusicDisabled = !AbstractArchive.clientPreferences.titleMusicDisabled;
            Language.method3830();
            if(!AbstractArchive.clientPreferences.titleMusicDisabled) {
               UserComparator4.method3289(class212.archive6, "scape main", "", 255, false);
            } else {
               VertexNormal.method2466();
            }
         }

         if(Client.gameState != 5) {
            if(Login.field776 == -1L) {
               Login.field776 = class33.method680() + 1000L;
            }

            long var12 = class33.method680();
            boolean var3;
            if(Client.archiveLoaders != null && Client.archiveLoadersDone < Client.archiveLoaders.size()) {
               while(true) {
                  if(Client.archiveLoadersDone >= Client.archiveLoaders.size()) {
                     var3 = true;
                     break;
                  }

                  ArchiveLoader var15 = (ArchiveLoader)Client.archiveLoaders.get(Client.archiveLoadersDone);
                  if(!var15.method1296()) {
                     var3 = false;
                     break;
                  }

                  ++Client.archiveLoadersDone;
               }
            } else {
               var3 = true;
            }

            if(var3 && Login.field772 == -1L) {
               Login.field772 = var12;
               if(Login.field772 > Login.field776) {
                  Login.field776 = Login.field772;
               }
            }

            if(Client.gameState == 10 || Client.gameState == 11) {
               if(WorldMapLabelSize.clientLanguage == Language.Language_EN) {
                  if(MouseHandler.MouseHandler_lastButton == 1 || !WorldMapIcon_1.mouseCam && MouseHandler.MouseHandler_lastButton == 4) {
                     var4 = Login.xPadding + 5;
                     short var14 = 463;
                     byte var6 = 100;
                     byte var7 = 35;
                     if(MouseHandler.MouseHandler_lastPressedX >= var4 && MouseHandler.MouseHandler_lastPressedX <= var6 + var4 && MouseHandler.MouseHandler_lastPressedY >= var14 && MouseHandler.MouseHandler_lastPressedY <= var14 + var7) {
                        if(BufferedNetSocket.method5242()) {
                           Login.worldSelectOpen = true;
                           Login.worldSelectPage = 0;
                           Login.worldSelectPagesCount = 0;
                        }

                        return;
                     }
                  }

                  if(class37.World_request != null && BufferedNetSocket.method5242()) {
                     Login.worldSelectOpen = true;
                     Login.worldSelectPage = 0;
                     Login.worldSelectPagesCount = 0;
                  }
               }

               var4 = MouseHandler.MouseHandler_lastButton;
               int var25 = MouseHandler.MouseHandler_lastPressedX;
               int var18 = MouseHandler.MouseHandler_lastPressedY;
               if(var4 == 0) {
                  var25 = MouseHandler.MouseHandler_x;
                  var18 = MouseHandler.MouseHandler_y;
               }

               if(!WorldMapIcon_1.mouseCam && var4 == 4) {
                  var4 = 1;
               }

               int var8;
               short var9;
               if(Login.loginIndex == 0) {
                  boolean var19 = false;

                  while(DynamicObject.method1568()) {
                     if(SecureRandomFuture.field748 == 84) {
                        var19 = true;
                     }
                  }

                  var8 = GrandExchangeOfferUnitPriceComparator.loginBoxCenter - 80;
                  var9 = 291;
                  if(var4 == 1 && var25 >= var8 - 75 && var25 <= var8 + 75 && var18 >= var9 - 20 && var18 <= var9 + 20) {
                     ArchiveDiskActionHandler.method4341("https://community.kronos.rip/index.php?register/", true, false);
                  }

                  var8 = GrandExchangeOfferUnitPriceComparator.loginBoxCenter + 80;
                  if(var4 == 1 && var25 >= var8 - 75 && var25 <= var8 + 75 && var18 >= var9 - 20 && var18 <= var9 + 20 || var19) {
                     if((Client.worldProperties & 33554432) != 0) {
                        Login.Login_response0 = "";
                        Login.Login_response1 = "This is a <col=00ffff>Beta<col=ffffff> world.";
                        Login.Login_response2 = "Your normal account will not be affected.";
                        Login.Login_response3 = "";
                        Login.loginIndex = 1;
                        if(Client.Login_isUsernameRemembered && Login.Login_username != null && Login.Login_username.length() > 0) {
                           Login.currentLoginField = 1;
                        } else {
                           Login.currentLoginField = 0;
                        }
                     } else if((Client.worldProperties & 4) != 0) {
                        if((Client.worldProperties & 1024) != 0) {
                           Login.Login_response1 = "This is a <col=ffff00>High Risk <col=ff0000>PvP<col=ffffff> world.";
                           Login.Login_response2 = "Players can attack each other almost everywhere";
                           Login.Login_response3 = "and the Protect Item prayer won\'t work.";
                        } else {
                           Login.Login_response1 = "This is a <col=ff0000>PvP<col=ffffff> world.";
                           Login.Login_response2 = "Players can attack each other";
                           Login.Login_response3 = "almost everywhere.";
                        }

                        Login.Login_response0 = "Warning!";
                        Login.loginIndex = 1;
                        if(Client.Login_isUsernameRemembered && Login.Login_username != null && Login.Login_username.length() > 0) {
                           Login.currentLoginField = 1;
                        } else {
                           Login.currentLoginField = 0;
                        }
                     } else if((Client.worldProperties & 1024) != 0) {
                        Login.Login_response1 = "This is a <col=ffff00>High Risk<col=ffffff> world.";
                        Login.Login_response2 = "The Protect Item prayer will";
                        Login.Login_response3 = "not work on this world.";
                        Login.Login_response0 = "Warning!";
                        Login.loginIndex = 1;
                        if(Client.Login_isUsernameRemembered && Login.Login_username != null && Login.Login_username.length() > 0) {
                           Login.currentLoginField = 1;
                        } else {
                           Login.currentLoginField = 0;
                        }
                     } else {
                        TilePaint.method2911(false);
                     }
                  }
               } else {
                  int var20;
                  short var22;
                  if(Login.loginIndex != 1) {
                     boolean var10;
                     int var11;
                     short var21;
                     if(Login.loginIndex == 2) {
                        var21 = 201;
                        var20 = var21 + 52;
                        if(var4 == 1 && var18 >= var20 - 12 && var18 < var20 + 2) {
                           Login.currentLoginField = 0;
                        }

                        var20 += 15;
                        if(var4 == 1 && var18 >= var20 - 12 && var18 < var20 + 2) {
                           Login.currentLoginField = 1;
                        }

                        var20 += 15;
                        var21 = 361;
                        if(LoginScreenAnimation.field610 != null) {
                           var8 = LoginScreenAnimation.field610.highX / 2;
                           if(var4 == 1 && var25 >= LoginScreenAnimation.field610.lowX - var8 && var25 <= var8 + LoginScreenAnimation.field610.lowX && var18 >= var21 - 15 && var18 < var21) {
                              switch(Login.field766) {
                              case 1:
                                 PlayerType.method3939("Please enter your username.", "If you created your account after November", "2010, this will be the creation email address.");
                                 Login.loginIndex = 5;
                                 return;
                              case 2:
                                 ArchiveDiskActionHandler.method4341("https://support.kronos.rip/hc/en-gb", true, false);
                              }
                           }
                        }

                        var8 = GrandExchangeOfferUnitPriceComparator.loginBoxCenter - 80;
                        var9 = 321;
                        if(var4 == 1 && var25 >= var8 - 75 && var25 <= var8 + 75 && var18 >= var9 - 20 && var18 <= var9 + 20) {
                           Login.Login_username = Login.Login_username.trim();
                           Client.onUsernameChanged(-1);
                           if(Login.Login_username.length() == 0) {
                              PlayerType.method3939("", "Please enter your username/email address.", "");
                              return;
                           }

                           if(Login.Login_password.length() == 0) {
                              PlayerType.method3939("", "Please enter your password.", "");
                              return;
                           }

                           PlayerType.method3939("", "Connecting to server...", "");
                           PlayerType.method3947(false);
                           MouseRecorder.method1207(20);
                           return;
                        }

                        var8 = Login.loginBoxX + 180 + 80;
                        if(var4 == 1 && var25 >= var8 - 75 && var25 <= var8 + 75 && var18 >= var9 - 20 && var18 <= var9 + 20) {
                           Login.loginIndex = 0;
                           Login.Login_username = "";
                           Client.onUsernameChanged(-1);
                           Login.Login_password = "";
                           class202.field2335 = 0;
                           DesktopPlatformInfoProvider.otp = "";
                           Login.field778 = true;
                        }

                        var8 = GrandExchangeOfferUnitPriceComparator.loginBoxCenter + -117;
                        var9 = 277;
                        Login.field771 = var25 >= var8 && var25 < var8 + class78.field800 && var18 >= var9 && var18 < var9 + WorldMapCacheName.field245;
                        if(var4 == 1 && Login.field771) {
                           Client.Login_isUsernameRemembered = !Client.Login_isUsernameRemembered;
                           if(!Client.Login_isUsernameRemembered && AbstractArchive.clientPreferences.rememberedUsername != null) {
                              AbstractArchive.clientPreferences.rememberedUsername = null;
                              Language.method3830();
                           }
                        }

                        var8 = GrandExchangeOfferUnitPriceComparator.loginBoxCenter + 24;
                        var9 = 277;
                        Login.field787 = var25 >= var8 && var25 < var8 + class78.field800 && var18 >= var9 && var18 < var9 + WorldMapCacheName.field245;
                        if(var4 == 1 && Login.field787) {
                           AbstractArchive.clientPreferences.hideUsername = !AbstractArchive.clientPreferences.hideUsername;
                           if(!AbstractArchive.clientPreferences.hideUsername) {
                              Login.Login_username = "";
                              Client.onUsernameChanged(-1);
                              AbstractArchive.clientPreferences.rememberedUsername = null;
                              if(Client.Login_isUsernameRemembered && Login.Login_username != null && Login.Login_username.length() > 0) {
                                 Login.currentLoginField = 1;
                              } else {
                                 Login.currentLoginField = 0;
                              }
                           }

                           Language.method3830();
                        }

                        while(true) {
                           while(DynamicObject.method1568()) {
                              var10 = false;

                              for(var11 = 0; var11 < "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:\'@#~,<.>/?\\| ".length(); ++var11) {
                                 if(KeyHandler.field182 == "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:\'@#~,<.>/?\\| ".charAt(var11)) {
                                    var10 = true;
                                    break;
                                 }
                              }

                              if(SecureRandomFuture.field748 == 13) {
                                 Login.loginIndex = 0;
                                 Login.Login_username = "";
                                 Client.onUsernameChanged(-1);
                                 Login.Login_password = "";
                                 class202.field2335 = 0;
                                 DesktopPlatformInfoProvider.otp = "";
                                 Login.field778 = true;
                              } else if(Login.currentLoginField == 0) {
                                 if(SecureRandomFuture.field748 == 85 && Login.Login_username.length() > 0) {
                                    Login.Login_username = Login.Login_username.substring(0, Login.Login_username.length() - 1);
                                    Client.onUsernameChanged(-1);
                                 }

                                 if(SecureRandomFuture.field748 == 84 || SecureRandomFuture.field748 == 80) {
                                    Login.currentLoginField = 1;
                                 }

                                 if(var10 && Login.Login_username.length() < 320) {
                                    Login.Login_username = Login.Login_username + KeyHandler.field182;
                                    Client.onUsernameChanged(-1);
                                 }
                              } else if(Login.currentLoginField == 1) {
                                 if(SecureRandomFuture.field748 == 85 && Login.Login_password.length() > 0) {
                                    Login.Login_password = Login.Login_password.substring(0, Login.Login_password.length() - 1);
                                 }

                                 if(SecureRandomFuture.field748 == 84 || SecureRandomFuture.field748 == 80) {
                                    Login.currentLoginField = 0;
                                 }

                                 if(SecureRandomFuture.field748 == 84) {
                                    Login.Login_username = Login.Login_username.trim();
                                    Client.onUsernameChanged(-1);
                                    if(Login.Login_username.length() == 0) {
                                       PlayerType.method3939("", "Please enter your username/email address.", "");
                                       return;
                                    }

                                    if(Login.Login_password.length() == 0) {
                                       PlayerType.method3939("", "Please enter your password.", "");
                                       return;
                                    }

                                    PlayerType.method3939("", "Connecting to server...", "");
                                    PlayerType.method3947(false);
                                    MouseRecorder.method1207(20);
                                    return;
                                 }

                                 if(var10 && Login.Login_password.length() < 20) {
                                    Login.Login_password = Login.Login_password + KeyHandler.field182;
                                 }
                              }
                           }

                           return;
                        }
                     } else if(Login.loginIndex == 3) {
                        var20 = Login.loginBoxX + 180;
                        var22 = 276;
                        if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                           TilePaint.method2911(false);
                        }

                        var20 = Login.loginBoxX + 180;
                        var22 = 326;
                        if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                           PlayerType.method3939("Please enter your username.", "If you created your account after November", "2010, this will be the creation email address.");
                           Login.loginIndex = 5;
                           return;
                        }
                     } else {
                        int var24;
                        if(Login.loginIndex == 4) {
                           var20 = Login.loginBoxX + 180 - 80;
                           var22 = 321;
                           if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                              DesktopPlatformInfoProvider.otp.trim();
                              if(DesktopPlatformInfoProvider.otp.length() != 6) {
                                 PlayerType.method3939("", "Please enter a 6-digit PIN.", "");
                                 return;
                              }

                              class202.field2335 = Integer.parseInt(DesktopPlatformInfoProvider.otp);
                              DesktopPlatformInfoProvider.otp = "";
                              PlayerType.method3947(true);
                              PlayerType.method3939("", "Connecting to server...", "");
                              MouseRecorder.method1207(20);
                              return;
                           }

                           if(var4 == 1 && var25 >= Login.loginBoxX + 180 - 9 && var25 <= Login.loginBoxX + 180 + 130 && var18 >= 263 && var18 <= 296) {
                              Login.field778 = !Login.field778;
                           }

                           if(var4 == 1 && var25 >= Login.loginBoxX + 180 - 34 && var25 <= Login.loginBoxX + 34 + 180 && var18 >= 351 && var18 <= 363) {
                              ArchiveDiskActionHandler.method4341(FaceNormal.method2909("secure", true) + "m=totp-authenticator/disableTOTPRequest", true, false);
                           }

                           var20 = Login.loginBoxX + 180 + 80;
                           if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                              Login.loginIndex = 0;
                              Login.Login_username = "";
                              Client.onUsernameChanged(-1);
                              Login.Login_password = "";
                              class202.field2335 = 0;
                              DesktopPlatformInfoProvider.otp = "";
                           }

                           while(DynamicObject.method1568()) {
                              boolean var23 = false;

                              for(var24 = 0; var24 < "1234567890".length(); ++var24) {
                                 if(KeyHandler.field182 == "1234567890".charAt(var24)) {
                                    var23 = true;
                                    break;
                                 }
                              }

                              if(SecureRandomFuture.field748 == 13) {
                                 Login.loginIndex = 0;
                                 Login.Login_username = "";
                                 Client.onUsernameChanged(-1);
                                 Login.Login_password = "";
                                 class202.field2335 = 0;
                                 DesktopPlatformInfoProvider.otp = "";
                              } else {
                                 if(SecureRandomFuture.field748 == 85 && DesktopPlatformInfoProvider.otp.length() > 0) {
                                    DesktopPlatformInfoProvider.otp = DesktopPlatformInfoProvider.otp.substring(0, DesktopPlatformInfoProvider.otp.length() - 1);
                                 }

                                 if(SecureRandomFuture.field748 == 84) {
                                    DesktopPlatformInfoProvider.otp.trim();
                                    if(DesktopPlatformInfoProvider.otp.length() != 6) {
                                       PlayerType.method3939("", "Please enter a 6-digit PIN.", "");
                                       return;
                                    }

                                    class202.field2335 = Integer.parseInt(DesktopPlatformInfoProvider.otp);
                                    DesktopPlatformInfoProvider.otp = "";
                                    PlayerType.method3947(true);
                                    PlayerType.method3939("", "Connecting to server...", "");
                                    MouseRecorder.method1207(20);
                                    return;
                                 }

                                 if(var23 && DesktopPlatformInfoProvider.otp.length() < 6) {
                                    DesktopPlatformInfoProvider.otp = DesktopPlatformInfoProvider.otp + KeyHandler.field182;
                                 }
                              }
                           }
                        } else if(Login.loginIndex == 5) {
                           var20 = Login.loginBoxX + 180 - 80;
                           var22 = 321;
                           if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                              GrandExchangeOfferAgeComparator.method82();
                              return;
                           }

                           var20 = Login.loginBoxX + 180 + 80;
                           if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                              TilePaint.method2911(true);
                           }

                           var9 = 361;
                           if(class163.field1985 != null) {
                              var24 = class163.field1985.highX / 2;
                              if(var4 == 1 && var25 >= class163.field1985.lowX - var24 && var25 <= var24 + class163.field1985.lowX && var18 >= var9 - 15 && var18 < var9) {
                                 ArchiveDiskActionHandler.method4341(FaceNormal.method2909("secure", true) + "m=weblogin/g=oldscape/cant_log_in", true, false);
                              }
                           }

                           while(DynamicObject.method1568()) {
                              var10 = false;

                              for(var11 = 0; var11 < "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:\'@#~,<.>/?\\| ".length(); ++var11) {
                                 if(KeyHandler.field182 == "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:\'@#~,<.>/?\\| ".charAt(var11)) {
                                    var10 = true;
                                    break;
                                 }
                              }

                              if(SecureRandomFuture.field748 == 13) {
                                 TilePaint.method2911(true);
                              } else {
                                 if(SecureRandomFuture.field748 == 85 && Login.Login_username.length() > 0) {
                                    Login.Login_username = Login.Login_username.substring(0, Login.Login_username.length() - 1);
                                    Client.onUsernameChanged(-1);
                                 }

                                 if(SecureRandomFuture.field748 == 84) {
                                    GrandExchangeOfferAgeComparator.method82();
                                    return;
                                 }

                                 if(var10 && Login.Login_username.length() < 320) {
                                    Login.Login_username = Login.Login_username + KeyHandler.field182;
                                    Client.onUsernameChanged(-1);
                                 }
                              }
                           }
                        } else if(Login.loginIndex == 6) {
                           while(true) {
                              do {
                                 if(!DynamicObject.method1568()) {
                                    var21 = 321;
                                    if(var4 == 1 && var18 >= var21 - 20 && var18 <= var21 + 20) {
                                       TilePaint.method2911(true);
                                    }

                                    return;
                                 }
                              } while(SecureRandomFuture.field748 != 84 && SecureRandomFuture.field748 != 13);

                              TilePaint.method2911(true);
                           }
                        } else if(Login.loginIndex == 7) {
                           var20 = Login.loginBoxX + 180 - 80;
                           var22 = 321;
                           if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                              ArchiveDiskActionHandler.method4341(FaceNormal.method2909("secure", true) + "m=dob/set_dob.ws", true, false);
                              PlayerType.method3939("", "Page has opened in a new window.", "(Please check your popup blocker.)");
                              Login.loginIndex = 6;
                              return;
                           }

                           var20 = Login.loginBoxX + 180 + 80;
                           if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                              TilePaint.method2911(true);
                           }
                        } else if(Login.loginIndex == 8) {
                           var20 = Login.loginBoxX + 180 - 80;
                           var22 = 321;
                           if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                              ArchiveDiskActionHandler.method4341("https://www.jagex.com/terms/privacy/#eight", true, false);
                              PlayerType.method3939("", "Page has opened in a new window.", "(Please check your popup blocker.)");
                              Login.loginIndex = 6;
                              return;
                           }

                           var20 = Login.loginBoxX + 180 + 80;
                           if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                              TilePaint.method2911(true);
                           }
                        } else if(Login.loginIndex == 12) {
                           String var16 = "";
                           switch(Login.field769) {
                           case 0:
                              var16 = "https://support.kronos.rip/hc/en-gb/articles/115002238729-Account-Bans";
                              break;
                           case 1:
                              var16 = "https://support.kronos.rip/hc/en-gb/articles/206103939-My-account-is-locked";
                              break;
                           default:
                              TilePaint.method2911(false);
                           }

                           var8 = Login.loginBoxX + 180;
                           var9 = 276;
                           if(var4 == 1 && var25 >= var8 - 75 && var25 <= var8 + 75 && var18 >= var9 - 20 && var18 <= var9 + 20) {
                              ArchiveDiskActionHandler.method4341(var16, true, false);
                              PlayerType.method3939("", "Page has opened in a new window.", "(Please check your popup blocker.)");
                              Login.loginIndex = 6;
                              return;
                           }

                           var8 = Login.loginBoxX + 180;
                           var9 = 326;
                           if(var4 == 1 && var25 >= var8 - 75 && var25 <= var8 + 75 && var18 >= var9 - 20 && var18 <= var9 + 20) {
                              TilePaint.method2911(false);
                           }
                        } else if(Login.loginIndex == 24) {
                           var20 = Login.loginBoxX + 180;
                           var22 = 301;
                           if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                              TilePaint.method2911(false);
                           }
                        }
                     }
                  } else {
                     while(DynamicObject.method1568()) {
                        if(SecureRandomFuture.field748 == 84) {
                           TilePaint.method2911(false);
                        } else if(SecureRandomFuture.field748 == 13) {
                           Login.loginIndex = 0;
                        }
                     }

                     var20 = GrandExchangeOfferUnitPriceComparator.loginBoxCenter - 80;
                     var22 = 321;
                     if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                        TilePaint.method2911(false);
                     }

                     var20 = GrandExchangeOfferUnitPriceComparator.loginBoxCenter + 80;
                     if(var4 == 1 && var25 >= var20 - 75 && var25 <= var20 + 75 && var18 >= var22 - 20 && var18 <= var22 + 20) {
                        Login.loginIndex = 0;
                     }
                  }
               }

            }
         }
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(II)Lcu;",
      garbageValue = "-1747084073"
   )
   static Script method3723(int var0) {
      Script var1 = (Script) Script.Script_cached.method3032((long)var0);
      if(var1 != null) {
         return var1;
      } else {
         byte[] var2 = GrandExchangeOfferOwnWorldComparator.archive12.method4020(var0, 0, (short)338);
         if(var2 == null) {
            return null;
         } else {
            var1 = class11.method139(var2);
            Script.Script_cached.method3034(var1, (long)var0);
            return var1;
         }
      }
   }

   @ObfuscatedName("fi")
   @ObfuscatedSignature(
      signature = "(Lbi;III)V",
      garbageValue = "1912035221"
   )
   static void method3722(Player var0, int var1, int var2) {
      if(var0.sequence == var1 && var1 != -1) {
         int var3 = GrandExchangeOfferUnitPriceComparator.method1468(var1).field3438;
         if(var3 == 1) {
            var0.sequenceFrame = 0;
            var0.sequenceFrameCycle = 0;
            var0.sequenceDelay = var2;
            var0.field703 = 0;
         }

         if(var3 == 2) {
            var0.field703 = 0;
         }
      } else if(var1 == -1 || var0.sequence == -1 || GrandExchangeOfferUnitPriceComparator.method1468(var1).field3432 >= GrandExchangeOfferUnitPriceComparator.method1468(var0.sequence).field3432) {
         var0.sequence = var1;
         var0.animationChanged(-1);
         var0.sequenceFrame = 0;
         var0.sequenceFrameCycle = 0;
         var0.sequenceDelay = var2;
         var0.field703 = 0;
         var0.field726 = var0.pathLength;
      }

   }
}
