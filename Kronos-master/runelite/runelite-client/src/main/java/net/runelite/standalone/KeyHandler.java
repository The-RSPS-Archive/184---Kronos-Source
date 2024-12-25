package net.runelite.standalone;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.widgets.JavaScriptCallback;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSKeyHandler;

@ObfuscatedName("ah")
public final class KeyHandler implements KeyListener, FocusListener, RSKeyHandler {
   @ObfuscatedName("oc")
   @ObfuscatedSignature(
      signature = "Lft;"
   )
   static MouseWheel mouseWheel;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lah;"
   )
   public static KeyHandler KeyHandler_instance;
   @ObfuscatedName("ca")
   @ObfuscatedGetter(
      intValue = -1429227913
   )
   public static int field181;
   @ObfuscatedName("cc")
   public static int[] field177;
   @ObfuscatedName("ce")
   static int[] field176;
   @ObfuscatedName("cf")
   @ObfuscatedGetter(
      intValue = -844580135
   )
   public static volatile int KeyHandler_idleCycles;
   @ObfuscatedName("ci")
   @ObfuscatedGetter(
      intValue = -939573745
   )
   public static int field174;
   @ObfuscatedName("cj")
   public static char field182;
   @ObfuscatedName("cl")
   @ObfuscatedGetter(
      intValue = 1675440071
   )
   public static int field162;
   @ObfuscatedName("cq")
   @ObfuscatedGetter(
      intValue = 900188775
   )
   public static int field172;
   @ObfuscatedName("cr")
   public static int[] field166;
   @ObfuscatedName("ct")
   @ObfuscatedGetter(
      intValue = 1575072845
   )
   public static int field173;
   @ObfuscatedName("cu")
   static char[] field175;
   @ObfuscatedName("cw")
   @ObfuscatedGetter(
      intValue = -1686544141
   )
   public static int field179;
   @ObfuscatedName("cx")
   static int[] KeyHandler_keyCodes;
   @ObfuscatedName("cy")
   public static boolean[] KeyHandler_pressedKeys;

   static {
      KeyHandler_instance = new KeyHandler();
      KeyHandler_pressedKeys = new boolean[112];
      field166 = new int[128];
      field173 = 0;
      field174 = 0;
      field175 = new char[128];
      field176 = new int[128];
      field177 = new int[128];
      field162 = 0;
      field179 = 0;
      field172 = 0;
      field181 = 0;
      KeyHandler_idleCycles = 0;
      KeyHandler_keyCodes = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, 85, 80, 84, -1, 91, -1, -1, -1, 81, 82, 86, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1, -1, 83, 104, 105, 103, 102, 96, 98, 97, 99, -1, -1, -1, -1, -1, -1, -1, 25, 16, 17, 18, 19, 20, 21, 22, 23, 24, -1, -1, -1, -1, -1, -1, -1, 48, 68, 66, 50, 34, 51, 52, 53, 39, 54, 55, 56, 70, 69, 40, 41, 32, 35, 49, 36, 38, 67, 33, 65, 37, 64, -1, -1, -1, -1, -1, 228, 231, 227, 233, 224, 219, 225, 230, 226, 232, 89, 87, -1, 88, 229, 90, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, -1, -1, -1, 101, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 100, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
   }

   public void onFocusLost(FocusEvent var1) {
      FocusChanged var2 = new FocusChanged();
      var2.setFocused(false);
      ViewportMouse.client.getCallbacks().post(FocusChanged.class, var2);
   }

   public final synchronized void copy$keyPressed(KeyEvent var1) {
      if(KeyHandler_instance != null) {
         int var2 = var1.getKeyCode();
         if(var2 >= 0 && var2 < KeyHandler_keyCodes.length) {
            var2 = KeyHandler_keyCodes[var2];
            if((var2 & 128) != 0) {
               var2 = -1;
            }
         } else {
            var2 = -1;
         }

         if(field174 >= 0 && var2 >= 0) {
            field166[field174] = var2;
            field174 = field174 + 1 & 127;
            if(field173 == field174) {
               field174 = -1;
            }
         }

         int var3;
         if(var2 >= 0) {
            var3 = field172 + 1 & 127;
            if(var3 != field179) {
               field176[field172] = var2;
               field175[field172] = 0;
               field172 = var3;
            }
         }

         var3 = var1.getModifiers();
         if((var3 & 10) != 0 || var2 == 85 || var2 == 10) {
            var1.consume();
         }
      }

   }

   public final synchronized void copy$keyReleased(KeyEvent var1) {
      if(KeyHandler_instance != null) {
         int var2 = var1.getKeyCode();
         if(var2 >= 0 && var2 < KeyHandler_keyCodes.length) {
            var2 = KeyHandler_keyCodes[var2] & -129;
         } else {
            var2 = -1;
         }

         if(field174 >= 0 && var2 >= 0) {
            field166[field174] = ~var2;
            field174 = field174 + 1 & 127;
            if(field174 == field173) {
               field174 = -1;
            }
         }
      }

      var1.consume();
   }

   public final void copy$keyTyped(KeyEvent var1) {
      if(KeyHandler_instance != null) {
         char var2 = var1.getKeyChar();
         if(var2 != 0 && var2 != '\uffff' && LoginScreenAnimation.method1288(var2)) {
            int var3 = field172 + 1 & 127;
            if(var3 != field179) {
               field176[field172] = -1;
               field175[field172] = var2;
               field172 = var3;
            }
         }
      }

      var1.consume();
   }

   public final synchronized void keyReleased(KeyEvent var1) {
      ViewportMouse.client.getCallbacks().keyReleased(var1);
      if(!var1.isConsumed()) {
         this.copy$keyReleased(var1);
      }

   }

   public final void focusGained(FocusEvent var1) {
   }

   public final synchronized void focusLost(FocusEvent var1) {
      this.onFocusLost(var1);
      if(KeyHandler_instance != null) {
         field174 = -1;
      }

   }

   public final synchronized void keyPressed(KeyEvent var1) {
      ViewportMouse.client.getCallbacks().keyPressed(var1);
      if(!var1.isConsumed()) {
         this.copy$keyPressed(var1);
      }

   }

   public final void keyTyped(KeyEvent var1) {
      ViewportMouse.client.getCallbacks().keyTyped(var1);
      if(!var1.isConsumed()) {
         this.copy$keyTyped(var1);
      }

   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lba;IB)V"
   )
   static void method506(ScriptEvent var0, int var1, byte var2) {
      Object[] var3 = var0.getArguments();
      if(var3 != null && var3.length > 0 && var3[0] instanceof JavaScriptCallback) {
         try {
            ((JavaScriptCallback)var3[0]).run(var0);
         } catch (Exception var8) {
            ViewportMouse.client.getLogger().error("Error in JavaScriptCallback", var8);
         }

      } else {
         try {
            Client.copy$runScript(var0, var1, var2);
         } finally {
            Client.currentScript = null;
         }

      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "96"
   )
   static void method504() {
      Iterator var0 = Messages.Messages_hashTable.iterator();

      while(var0.hasNext()) {
         Message var1 = (Message)var0.next();
         var1.method884();
      }

   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lhp;I)V",
      garbageValue = "1410088025"
   )
   public static void method484(AbstractArchive var0) {
      InvDefinition.InvDefinition_archive = var0;
   }

   @ObfuscatedName("gk")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "133309082"
   )
   static final void method482() {
      GrandExchangeOffer.method4218(false);
      Client.field897 = 0;
      boolean var0 = true;

      int var1;
      for(var1 = 0; var1 < WorldMapIcon_1.regionLandArchives.length; ++var1) {
         if(class197.regionMapArchiveIds[var1] != -1 && WorldMapIcon_1.regionLandArchives[var1] == null) {
            WorldMapIcon_1.regionLandArchives[var1] = class11.archive5.method4020(class197.regionMapArchiveIds[var1], 0, (short)-7724);
            if(WorldMapIcon_1.regionLandArchives[var1] == null) {
               var0 = false;
               ++Client.field897;
            }
         }

         if(class47.regionLandArchiveIds[var1] != -1 && ArchiveLoader.regionMapArchives[var1] == null) {
            ArchiveLoader.regionMapArchives[var1] = class11.archive5.method4021(class47.regionLandArchiveIds[var1], 0, class289.xteaKeys[var1]);
            if(ArchiveLoader.regionMapArchives[var1] == null) {
               var0 = false;
               ++Client.field897;
            }
         }
      }

      if(!var0) {
         Client.field901 = 1;
      } else {
         Client.field899 = 0;
         var0 = true;

         int var3;
         int var4;
         Buffer var8;
         int var9;
         int var10;
         int var11;
         int var13;
         int var14;
         int var15;
         int var16;
         int var17;
         int var18;
         for(var1 = 0; var1 < WorldMapIcon_1.regionLandArchives.length; ++var1) {
            byte[] var2 = ArchiveLoader.regionMapArchives[var1];
            if(var2 != null) {
               var3 = (MouseHandler.regions[var1] >> 8) * 64 - class215.baseX;
               var4 = (MouseHandler.regions[var1] & 255) * 64 - class304.baseY;
               if(Client.isInInstance) {
                  var3 = 10;
                  var4 = 10;
               }

               boolean var7 = true;
               var8 = new Buffer(var2);
               var9 = -1;

               label1329:
               while(true) {
                  var10 = var8.method5511();
                  if(var10 == 0) {
                     var0 &= var7;
                     break;
                  }

                  var9 += var10;
                  var11 = 0;
                  boolean var12 = false;

                  while(true) {
                     while(!var12) {
                        var13 = var8.readUnsignedSmart();
                        if(var13 == 0) {
                           continue label1329;
                        }

                        var11 += var13 - 1;
                        var14 = var11 & 63;
                        var15 = var11 >> 6 & 63;
                        var16 = var8.readUnsignedByte() >> 2;
                        var17 = var3 + var15;
                        var18 = var4 + var14;
                        if(var17 > 0 && var18 > 0 && var17 < 103 && var18 < 103) {
                           ObjectDefinition var19 = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(var9);
                           if(var16 != 22 || !Client.isLowDetail || var19.int1 != 0 || var19.interactType == 1 || var19.boolean2) {
                              if(!var19.method4721()) {
                                 ++Client.field899;
                                 var7 = false;
                              }

                              var12 = true;
                           }
                        }
                     }

                     var13 = var8.readUnsignedSmart();
                     if(var13 == 0) {
                        break;
                     }

                     var8.readUnsignedByte();
                  }
               }
            }
         }

         if(!var0) {
            Client.field901 = 2;
         } else {
            if(Client.field901 != 0) {
               WorldMapSprite.method784("Loading - please wait." + "<br>" + " (" + 100 + "%" + ")", true);
            }

            WorldMapID.method687();
            PacketWriter.scene.method3141();

            for(var1 = 0; var1 < 4; ++var1) {
               Client.collisionMaps[var1].method3352();
            }

            int var46;
            for(var1 = 0; var1 < 4; ++var1) {
               for(var46 = 0; var46 < 104; ++var46) {
                  for(var3 = 0; var3 < 104; ++var3) {
                     Tiles.Tiles_renderFlags[var1][var46][var3] = 0;
                  }
               }
            }

            WorldMapID.method687();
            TextureProvider.method2870();
            var1 = WorldMapIcon_1.regionLandArchives.length;
            ClientPacket.method3879();
            GrandExchangeOffer.method4218(true);
            int var5;
            int var20;
            int var21;
            int var51;
            int var66;
            if(!Client.isInInstance) {
               byte[] var47;
               for(var46 = 0; var46 < var1; ++var46) {
                  var3 = (MouseHandler.regions[var46] >> 8) * 64 - class215.baseX;
                  var4 = (MouseHandler.regions[var46] & 255) * 64 - class304.baseY;
                  var47 = WorldMapIcon_1.regionLandArchives[var46];
                  if(var47 != null) {
                     WorldMapID.method687();
                     class28.method579(var47, var3, var4, ServerPacket.field2272 * 8 - 48, MusicPatchNode.field2216 * 8 - 48, Client.collisionMaps);
                  }
               }

               for(var46 = 0; var46 < var1; ++var46) {
                  var3 = (MouseHandler.regions[var46] >> 8) * 64 - class215.baseX;
                  var4 = (MouseHandler.regions[var46] & 255) * 64 - class304.baseY;
                  var47 = WorldMapIcon_1.regionLandArchives[var46];
                  if(var47 == null && MusicPatchNode.field2216 < 800) {
                     WorldMapID.method687();
                     class33.method675(var3, var4, 64, 64);
                  }
               }

               GrandExchangeOffer.method4218(true);

               for(var46 = 0; var46 < var1; ++var46) {
                  byte[] var52 = ArchiveLoader.regionMapArchives[var46];
                  if(var52 != null) {
                     var4 = (MouseHandler.regions[var46] >> 8) * 64 - class215.baseX;
                     var5 = (MouseHandler.regions[var46] & 255) * 64 - class304.baseY;
                     WorldMapID.method687();
                     Scene var53 = PacketWriter.scene;
                     CollisionMap[] var54 = Client.collisionMaps;
                     var8 = new Buffer(var52);
                     var9 = -1;

                     while(true) {
                        var10 = var8.method5511();
                        if(var10 == 0) {
                           break;
                        }

                        var9 += var10;
                        var11 = 0;

                        while(true) {
                           var66 = var8.readUnsignedSmart();
                           if(var66 == 0) {
                              break;
                           }

                           var11 += var66 - 1;
                           var13 = var11 & 63;
                           var14 = var11 >> 6 & 63;
                           var15 = var11 >> 12;
                           var16 = var8.readUnsignedByte();
                           var17 = var16 >> 2;
                           var18 = var16 & 3;
                           var51 = var14 + var4;
                           var20 = var5 + var13;
                           if(var51 > 0 && var20 > 0 && var51 < 103 && var20 < 103) {
                              var21 = var15;
                              if((Tiles.Tiles_renderFlags[1][var51][var20] & 2) == 2) {
                                 var21 = var15 - 1;
                              }

                              CollisionMap var55 = null;
                              if(var21 >= 0) {
                                 var55 = var54[var21];
                              }

                              GrandExchangeOfferAgeComparator.method80(var15, var51, var20, var9, var18, var17, var53, var55);
                           }
                        }
                     }
                  }
               }
            }

            int var6;
            int var22;
            int var26;
            int var27;
            int var28;
            int var29;
            int var32;
            int var34;
            int var35;
            int var36;
            int var48;
            int var65;
            if(Client.isInInstance) {
               for(var46 = 0; var46 < 4; ++var46) {
                  WorldMapID.method687();

                  for(var3 = 0; var3 < 13; ++var3) {
                     for(var4 = 0; var4 < 13; ++var4) {
                        boolean var64 = false;
                        var6 = Client.instanceChunkTemplates[var46][var3][var4];
                        if(var6 != -1) {
                           var65 = var6 >> 24 & 3;
                           var48 = var6 >> 1 & 3;
                           var9 = var6 >> 14 & 1023;
                           var10 = var6 >> 3 & 2047;
                           var11 = (var9 / 8 << 8) + var10 / 8;

                           for(var66 = 0; var66 < MouseHandler.regions.length; ++var66) {
                              if(MouseHandler.regions[var66] == var11 && WorldMapIcon_1.regionLandArchives[var66] != null) {
                                 byte[] var49 = WorldMapIcon_1.regionLandArchives[var66];
                                 var14 = var3 * 8;
                                 var15 = var4 * 8;
                                 var16 = (var9 & 7) * 8;
                                 var17 = (var10 & 7) * 8;
                                 CollisionMap[] var50 = Client.collisionMaps;

                                 for(var51 = 0; var51 < 8; ++var51) {
                                    for(var20 = 0; var20 < 8; ++var20) {
                                       if(var14 + var51 > 0 && var51 + var14 < 103 && var15 + var20 > 0 && var20 + var15 < 103) {
                                          var50[var46].flags[var14 + var51][var15 + var20] &= -16777217;
                                       }
                                    }
                                 }

                                 Buffer var67 = new Buffer(var49);

                                 for(var20 = 0; var20 < 4; ++var20) {
                                    for(var21 = 0; var21 < 64; ++var21) {
                                       for(var22 = 0; var22 < 64; ++var22) {
                                          if(var20 == var65 && var21 >= var16 && var21 < var16 + 8 && var22 >= var17 && var22 < var17 + 8) {
                                             var27 = var21 & 7;
                                             var28 = var22 & 7;
                                             var29 = var48 & 3;
                                             if(var29 == 0) {
                                                var26 = var27;
                                             } else if(var29 == 1) {
                                                var26 = var28;
                                             } else if(var29 == 2) {
                                                var26 = 7 - var27;
                                             } else {
                                                var26 = 7 - var28;
                                             }

                                             var32 = var14 + var26;
                                             var35 = var21 & 7;
                                             var36 = var22 & 7;
                                             int var37 = var48 & 3;
                                             if(var37 == 0) {
                                                var34 = var36;
                                             } else if(var37 == 1) {
                                                var34 = 7 - var35;
                                             } else if(var37 == 2) {
                                                var34 = 7 - var36;
                                             } else {
                                                var34 = var35;
                                             }

                                             GrandExchangeOfferWorldComparator.method91(var67, var46, var32, var34 + var15, 0, 0, var48);
                                          } else {
                                             GrandExchangeOfferWorldComparator.method91(var67, 0, -1, -1, 0, 0, 0);
                                          }
                                       }
                                    }
                                 }

                                 var64 = true;
                                 break;
                              }
                           }
                        }

                        if(!var64) {
                           WorldMapID.method686(var46, var3 * 8, var4 * 8);
                        }
                     }
                  }
               }

               for(var46 = 0; var46 < 13; ++var46) {
                  for(var3 = 0; var3 < 13; ++var3) {
                     var4 = Client.instanceChunkTemplates[0][var46][var3];
                     if(var4 == -1) {
                        class33.method675(var46 * 8, var3 * 8, 8, 8);
                     }
                  }
               }

               GrandExchangeOffer.method4218(true);

               for(var46 = 0; var46 < 4; ++var46) {
                  WorldMapID.method687();

                  for(var3 = 0; var3 < 13; ++var3) {
                     for(var4 = 0; var4 < 13; ++var4) {
                        var5 = Client.instanceChunkTemplates[var46][var3][var4];
                        if(var5 != -1) {
                           var6 = var5 >> 24 & 3;
                           var65 = var5 >> 1 & 3;
                           var48 = var5 >> 14 & 1023;
                           var9 = var5 >> 3 & 2047;
                           var10 = (var48 / 8 << 8) + var9 / 8;

                           for(var11 = 0; var11 < MouseHandler.regions.length; ++var11) {
                              if(MouseHandler.regions[var11] == var10 && ArchiveLoader.regionMapArchives[var11] != null) {
                                 class93.method2209(ArchiveLoader.regionMapArchives[var11], var46, var3 * 8, var4 * 8, var6, (var48 & 7) * 8, (var9 & 7) * 8, var65, PacketWriter.scene, Client.collisionMaps);
                                 break;
                              }
                           }
                        }
                     }
                  }
               }
            }

            GrandExchangeOffer.method4218(true);
            WorldMapID.method687();
            Scene var63 = PacketWriter.scene;
            CollisionMap[] var75 = Client.collisionMaps;

            for(var4 = 0; var4 < 4; ++var4) {
               for(var5 = 0; var5 < 104; ++var5) {
                  for(var6 = 0; var6 < 104; ++var6) {
                     if((Tiles.Tiles_renderFlags[var4][var5][var6] & 1) == 1) {
                        var65 = var4;
                        if((Tiles.Tiles_renderFlags[1][var5][var6] & 2) == 2) {
                           var65 = var4 - 1;
                        }

                        if(var65 >= 0) {
                           var75[var65].method3335(var5, var6);
                        }
                     }
                  }
               }
            }

            Tiles.field521 += (int)(Math.random() * 5.0D) - 2;
            if(Tiles.field521 < -8) {
               Tiles.field521 = -8;
            }

            if(Tiles.field521 > 8) {
               Tiles.field521 = 8;
            }

            Tiles.field535 += (int)(Math.random() * 5.0D) - 2;
            if(Tiles.field535 < -16) {
               Tiles.field535 = -16;
            }

            if(Tiles.field535 > 16) {
               Tiles.field535 = 16;
            }

            for(var4 = 0; var4 < 4; ++var4) {
               byte[][] var71 = Tiles.field525[var4];
               var11 = (int)Math.sqrt(5100.0D);
               var66 = var11 * 768 >> 8;

               for(var13 = 1; var13 < 103; ++var13) {
                  for(var14 = 1; var14 < 103; ++var14) {
                     var15 = Tiles.Tiles_heights[var4][var14 + 1][var13] - Tiles.Tiles_heights[var4][var14 - 1][var13];
                     var16 = Tiles.Tiles_heights[var4][var14][var13 + 1] - Tiles.Tiles_heights[var4][var14][var13 - 1];
                     var17 = (int)Math.sqrt((double)(var16 * var16 + var15 * var15 + 65536));
                     var18 = (var15 << 8) / var17;
                     var51 = 65536 / var17;
                     var20 = (var16 << 8) / var17;
                     var21 = (var18 * -50 + var20 * -50 + var51 * -10) / var66 + 96;
                     var22 = (var71[var14][var13 + 1] >> 3) + (var71[var14 - 1][var13] >> 2) + (var71[var14][var13 - 1] >> 2) + (var71[var14 + 1][var13] >> 3) + (var71[var14][var13] >> 1);
                     DevicePcmPlayerProvider.field149[var14][var13] = var21 - var22;
                  }
               }

               for(var13 = 0; var13 < 104; ++var13) {
                  Tiles.Tiles_hue[var13] = 0;
                  ArchiveLoader.Tiles_saturation[var13] = 0;
                  Tiles.Tiles_lightness[var13] = 0;
                  FontName.Tiles_hueMultiplier[var13] = 0;
                  Tiles.field526[var13] = 0;
               }

               for(var13 = -5; var13 < 109; ++var13) {
                  for(var14 = 0; var14 < 104; ++var14) {
                     var15 = var13 + 5;
                     if(var15 >= 0 && var15 < 104) {
                        var16 = Tiles.field540[var4][var15][var14] & 255;
                        if(var16 > 0) {
                           FloorUnderlayDefinition var56 = UserComparator9.method3029(var16 - 1);
                           Tiles.Tiles_hue[var14] += var56.hue;
                           ArchiveLoader.Tiles_saturation[var14] += var56.saturation;
                           Tiles.Tiles_lightness[var14] += var56.lightness;
                           FontName.Tiles_hueMultiplier[var14] += var56.hueMultiplier;
                           ++Tiles.field526[var14];
                        }
                     }

                     var16 = var13 - 5;
                     if(var16 >= 0 && var16 < 104) {
                        var17 = Tiles.field540[var4][var16][var14] & 255;
                        if(var17 > 0) {
                           FloorUnderlayDefinition var74 = UserComparator9.method3029(var17 - 1);
                           Tiles.Tiles_hue[var14] -= var74.hue;
                           ArchiveLoader.Tiles_saturation[var14] -= var74.saturation;
                           Tiles.Tiles_lightness[var14] -= var74.lightness;
                           FontName.Tiles_hueMultiplier[var14] -= var74.hueMultiplier;
                           --Tiles.field526[var14];
                        }
                     }
                  }

                  if(var13 >= 1 && var13 < 103) {
                     var14 = 0;
                     var15 = 0;
                     var16 = 0;
                     var17 = 0;
                     var18 = 0;

                     for(var51 = -5; var51 < 109; ++var51) {
                        var20 = var51 + 5;
                        if(var20 >= 0 && var20 < 104) {
                           var14 += Tiles.Tiles_hue[var20];
                           var15 += ArchiveLoader.Tiles_saturation[var20];
                           var16 += Tiles.Tiles_lightness[var20];
                           var17 += FontName.Tiles_hueMultiplier[var20];
                           var18 += Tiles.field526[var20];
                        }

                        var21 = var51 - 5;
                        if(var21 >= 0 && var21 < 104) {
                           var14 -= Tiles.Tiles_hue[var21];
                           var15 -= ArchiveLoader.Tiles_saturation[var21];
                           var16 -= Tiles.Tiles_lightness[var21];
                           var17 -= FontName.Tiles_hueMultiplier[var21];
                           var18 -= Tiles.field526[var21];
                        }

                        if(var51 >= 1 && var51 < 103 && (!Client.isLowDetail || (Tiles.Tiles_renderFlags[0][var13][var51] & 2) != 0 || (Tiles.Tiles_renderFlags[var4][var13][var51] & 16) == 0)) {
                           if(var4 < Tiles.Tiles_minPlane) {
                              Tiles.Tiles_minPlane = var4;
                           }

                           var22 = Tiles.field540[var4][var13][var51] & 255;
                           int var57 = Tiles.field522[var4][var13][var51] & 255;
                           if(var22 > 0 || var57 > 0) {
                              int var24 = Tiles.Tiles_heights[var4][var13][var51];
                              int var25 = Tiles.Tiles_heights[var4][var13 + 1][var51];
                              var26 = Tiles.Tiles_heights[var4][var13 + 1][var51 + 1];
                              var27 = Tiles.Tiles_heights[var4][var13][var51 + 1];
                              var28 = DevicePcmPlayerProvider.field149[var13][var51];
                              var29 = DevicePcmPlayerProvider.field149[var13 + 1][var51];
                              int var58 = DevicePcmPlayerProvider.field149[var13 + 1][var51 + 1];
                              int var31 = DevicePcmPlayerProvider.field149[var13][var51 + 1];
                              var32 = -1;
                              int var33 = -1;
                              if(var22 > 0) {
                                 var34 = var14 * 256 / var17;
                                 var35 = var15 / var18;
                                 var36 = var16 / var18;
                                 var32 = GrandExchangeOfferAgeComparator.method71(var34, var35, var36);
                                 var34 = var34 + Tiles.field521 & 255;
                                 var36 += Tiles.field535;
                                 if(var36 < 0) {
                                    var36 = 0;
                                 } else if(var36 > 255) {
                                    var36 = 255;
                                 }

                                 var33 = GrandExchangeOfferAgeComparator.method71(var34, var35, var36);
                              }

                              FloorOverlayDefinition var59;
                              if(var4 > 0) {
                                 boolean var69 = true;
                                 if(var22 == 0 && DevicePcmPlayerProvider.field156[var4][var13][var51] != 0) {
                                    var69 = false;
                                 }

                                 if(var57 > 0) {
                                    var36 = var57 - 1;
                                    var59 = (FloorOverlayDefinition)FloorOverlayDefinition.FloorOverlayDefinition_cached.method3032((long)var36);
                                    FloorOverlayDefinition var60;
                                    if(var59 != null) {
                                       var60 = var59;
                                    } else {
                                       byte[] var62 = FloorOverlayDefinition.FloorOverlayDefinition_archive.method4020(4, var36, (short)12221);
                                       var59 = new FloorOverlayDefinition();
                                       if(var62 != null) {
                                          var59.method4357(new Buffer(var62), var36);
                                       }

                                       var59.method4365();
                                       FloorOverlayDefinition.FloorOverlayDefinition_cached.method3034(var59, (long)var36);
                                       var60 = var59;
                                    }

                                    if(!var60.hideUnderlay) {
                                       var69 = false;
                                    }
                                 }

                                 if(var69 && var24 == var25 && var24 == var26 && var27 == var24) {
                                    Tiles.field527[var4][var13][var51] |= 2340;
                                 }
                              }

                              var34 = 0;
                              if(var33 != -1) {
                                 var34 = Rasterizer3D.Rasterizer3D_colorPalette[class198.method3817(var33, 96)];
                              }

                              if(var57 == 0) {
                                 var63.method3156(var4, var13, var51, 0, 0, -1, var24, var25, var26, var27, class198.method3817(var32, var28), class198.method3817(var32, var29), class198.method3817(var32, var58), class198.method3817(var32, var31), 0, 0, 0, 0, var34, 0);
                              } else {
                                 var35 = DevicePcmPlayerProvider.field156[var4][var13][var51] + 1;
                                 byte var70 = class298.field3719[var4][var13][var51];
                                 int var38 = var57 - 1;
                                 FloorOverlayDefinition var39 = (FloorOverlayDefinition)FloorOverlayDefinition.FloorOverlayDefinition_cached.method3032((long)var38);
                                 if(var39 != null) {
                                    var59 = var39;
                                 } else {
                                    byte[] var61 = FloorOverlayDefinition.FloorOverlayDefinition_archive.method4020(4, var38, (short)-2718);
                                    var39 = new FloorOverlayDefinition();
                                    if(var61 != null) {
                                       var39.method4357(new Buffer(var61), var38);
                                    }

                                    var39.method4365();
                                    FloorOverlayDefinition.FloorOverlayDefinition_cached.method3034(var39, (long)var38);
                                    var59 = var39;
                                 }

                                 int var40 = var59.texture;
                                 int var41;
                                 int var42;
                                 int var43;
                                 int var44;
                                 if(var40 >= 0) {
                                    var42 = Rasterizer3D.Rasterizer3D_textureLoader.vmethod2926(var40);
                                    var41 = -1;
                                 } else if(var59.primaryRgb == 16711935) {
                                    var41 = -2;
                                    var40 = -1;
                                    var42 = -2;
                                 } else {
                                    var41 = GrandExchangeOfferAgeComparator.method71(var59.hue, var59.saturation, var59.lightness);
                                    var43 = var59.hue + Tiles.field521 & 255;
                                    var44 = var59.lightness + Tiles.field535;
                                    if(var44 < 0) {
                                       var44 = 0;
                                    } else if(var44 > 255) {
                                       var44 = 255;
                                    }

                                    var42 = GrandExchangeOfferAgeComparator.method71(var43, var59.saturation, var44);
                                 }

                                 var43 = 0;
                                 if(var42 != -2) {
                                    var43 = Rasterizer3D.Rasterizer3D_colorPalette[WorldMapEvent.method681(var42, 96)];
                                 }

                                 if(var59.secondaryRgb != -1) {
                                    var44 = var59.secondaryHue + Tiles.field521 & 255;
                                    int var45 = var59.secondaryLightness + Tiles.field535;
                                    if(var45 < 0) {
                                       var45 = 0;
                                    } else if(var45 > 255) {
                                       var45 = 255;
                                    }

                                    var42 = GrandExchangeOfferAgeComparator.method71(var44, var59.secondarySaturation, var45);
                                    var43 = Rasterizer3D.Rasterizer3D_colorPalette[WorldMapEvent.method681(var42, 96)];
                                 }

                                 var63.method3156(var4, var13, var51, var35, var70, var40, var24, var25, var26, var27, class198.method3817(var32, var28), class198.method3817(var32, var29), class198.method3817(var32, var58), class198.method3817(var32, var31), WorldMapEvent.method681(var41, var28), WorldMapEvent.method681(var41, var29), WorldMapEvent.method681(var41, var58), WorldMapEvent.method681(var41, var31), var34, var43);
                              }
                           }
                        }
                     }
                  }
               }

               for(var13 = 1; var13 < 103; ++var13) {
                  for(var14 = 1; var14 < 103; ++var14) {
                     var63.method3198(var4, var14, var13, class188.method3736(var4, var14, var13));
                  }
               }

               Tiles.field540[var4] = null;
               Tiles.field522[var4] = null;
               DevicePcmPlayerProvider.field156[var4] = null;
               class298.field3719[var4] = null;
               Tiles.field525[var4] = null;
            }

            var63.method3139(-50, -10, -50);

            for(var4 = 0; var4 < 104; ++var4) {
               for(var5 = 0; var5 < 104; ++var5) {
                  if((Tiles.Tiles_renderFlags[1][var4][var5] & 2) == 2) {
                     var63.method3220(var4, var5);
                  }
               }
            }

            var4 = 1;
            var5 = 2;
            var6 = 4;

            for(var65 = 0; var65 < 4; ++var65) {
               if(var65 > 0) {
                  var4 <<= 3;
                  var5 <<= 3;
                  var6 <<= 3;
               }

               for(var48 = 0; var48 <= var65; ++var48) {
                  for(var9 = 0; var9 <= 104; ++var9) {
                     for(var10 = 0; var10 <= 104; ++var10) {
                        short var68;
                        if((Tiles.field527[var48][var10][var9] & var4) != 0) {
                           var11 = var9;
                           var66 = var9;
                           var13 = var48;

                           for(var14 = var48; var11 > 0 && (Tiles.field527[var48][var10][var11 - 1] & var4) != 0; --var11) {
                              ;
                           }

                           while(var66 < 104 && (Tiles.field527[var48][var10][var66 + 1] & var4) != 0) {
                              ++var66;
                           }

                           label901:
                           while(var13 > 0) {
                              for(var15 = var11; var15 <= var66; ++var15) {
                                 if((Tiles.field527[var13 - 1][var10][var15] & var4) == 0) {
                                    break label901;
                                 }
                              }

                              --var13;
                           }

                           label890:
                           while(var14 < var65) {
                              for(var15 = var11; var15 <= var66; ++var15) {
                                 if((Tiles.field527[var14 + 1][var10][var15] & var4) == 0) {
                                    break label890;
                                 }
                              }

                              ++var14;
                           }

                           var15 = (var66 - var11 + 1) * (var14 + 1 - var13);
                           if(var15 >= 8) {
                              var68 = 240;
                              var17 = Tiles.Tiles_heights[var14][var10][var11] - var68;
                              var18 = Tiles.Tiles_heights[var13][var10][var11];
                              Scene.method3117(var65, 1, var10 * 128, var10 * 128, var11 * 128, var66 * 128 + 128, var17, var18);

                              for(var51 = var13; var51 <= var14; ++var51) {
                                 for(var20 = var11; var20 <= var66; ++var20) {
                                    Tiles.field527[var51][var10][var20] &= ~var4;
                                 }
                              }
                           }
                        }

                        if((Tiles.field527[var48][var10][var9] & var5) != 0) {
                           var11 = var10;
                           var66 = var10;
                           var13 = var48;

                           for(var14 = var48; var11 > 0 && (Tiles.field527[var48][var11 - 1][var9] & var5) != 0; --var11) {
                              ;
                           }

                           while(var66 < 104 && (Tiles.field527[var48][var66 + 1][var9] & var5) != 0) {
                              ++var66;
                           }

                           label954:
                           while(var13 > 0) {
                              for(var15 = var11; var15 <= var66; ++var15) {
                                 if((Tiles.field527[var13 - 1][var15][var9] & var5) == 0) {
                                    break label954;
                                 }
                              }

                              --var13;
                           }

                           label943:
                           while(var14 < var65) {
                              for(var15 = var11; var15 <= var66; ++var15) {
                                 if((Tiles.field527[var14 + 1][var15][var9] & var5) == 0) {
                                    break label943;
                                 }
                              }

                              ++var14;
                           }

                           var15 = (var14 + 1 - var13) * (var66 - var11 + 1);
                           if(var15 >= 8) {
                              var68 = 240;
                              var17 = Tiles.Tiles_heights[var14][var11][var9] - var68;
                              var18 = Tiles.Tiles_heights[var13][var11][var9];
                              Scene.method3117(var65, 2, var11 * 128, var66 * 128 + 128, var9 * 128, var9 * 128, var17, var18);

                              for(var51 = var13; var51 <= var14; ++var51) {
                                 for(var20 = var11; var20 <= var66; ++var20) {
                                    Tiles.field527[var51][var20][var9] &= ~var5;
                                 }
                              }
                           }
                        }

                        if((Tiles.field527[var48][var10][var9] & var6) != 0) {
                           var11 = var10;
                           var66 = var10;
                           var13 = var9;

                           for(var14 = var9; var13 > 0 && (Tiles.field527[var48][var10][var13 - 1] & var6) != 0; --var13) {
                              ;
                           }

                           while(var14 < 104 && (Tiles.field527[var48][var10][var14 + 1] & var6) != 0) {
                              ++var14;
                           }

                           label1007:
                           while(var11 > 0) {
                              for(var15 = var13; var15 <= var14; ++var15) {
                                 if((Tiles.field527[var48][var11 - 1][var15] & var6) == 0) {
                                    break label1007;
                                 }
                              }

                              --var11;
                           }

                           label996:
                           while(var66 < 104) {
                              for(var15 = var13; var15 <= var14; ++var15) {
                                 if((Tiles.field527[var48][var66 + 1][var15] & var6) == 0) {
                                    break label996;
                                 }
                              }

                              ++var66;
                           }

                           if((var14 - var13 + 1) * (var66 - var11 + 1) >= 4) {
                              var15 = Tiles.Tiles_heights[var48][var11][var13];
                              Scene.method3117(var65, 4, var11 * 128, var66 * 128 + 128, var13 * 128, var14 * 128 + 128, var15, var15);

                              for(var16 = var11; var16 <= var66; ++var16) {
                                 for(var17 = var13; var17 <= var14; ++var17) {
                                    Tiles.field527[var48][var16][var17] &= ~var6;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            GrandExchangeOffer.method4218(true);
            var4 = Tiles.Tiles_minPlane;
            if(var4 > WorldMapRectangle.plane) {
               var4 = WorldMapRectangle.plane;
            }

            if(var4 < WorldMapRectangle.plane - 1) {
               var4 = WorldMapRectangle.plane - 1;
            }

            if(Client.isLowDetail) {
               PacketWriter.scene.method3115(Tiles.Tiles_minPlane);
            } else {
               PacketWriter.scene.method3115(0);
            }

            for(var5 = 0; var5 < 104; ++var5) {
               for(var6 = 0; var6 < 104; ++var6) {
                  WorldMapCacheName.method673(var5, var6);
               }
            }

            WorldMapID.method687();

            for(PendingSpawn var72 = (PendingSpawn)Client.pendingSpawns.method5103(); var72 != null; var72 = (PendingSpawn)Client.pendingSpawns.method5126()) {
               if(var72.hitpoints == -1) {
                  var72.delay = 0;
                  GrandExchangeEvent.method6488(var72);
               } else {
                  var72.method3497();
               }
            }

            ObjectDefinition.ObjectDefinition_cachedModelData.method3035();
            PacketBufferNode var73;
            if(ViewportMouse.client.method987()) {
               var73 = InterfaceParent.method1140(ClientPacket.field2364, Client.packetWriter.isaacCipher);
               var73.packetBuffer.writeInt(1057001181);
               Client.packetWriter.method1622(var73);
            }

            if(!Client.isInInstance) {
               var5 = (ServerPacket.field2272 - 6) / 8;
               var6 = (ServerPacket.field2272 + 6) / 8;
               var65 = (MusicPatchNode.field2216 - 6) / 8;
               var48 = (MusicPatchNode.field2216 + 6) / 8;

               for(var9 = var5 - 1; var9 <= var6 + 1; ++var9) {
                  for(var10 = var65 - 1; var10 <= var48 + 1; ++var10) {
                     if(var9 < var5 || var9 > var6 || var10 < var65 || var10 > var48) {
                        class11.archive5.method4044("m" + var9 + "_" + var10);
                        class11.archive5.method4044("l" + var9 + "_" + var10);
                     }
                  }
               }
            }

            MouseRecorder.method1207(30);
            WorldMapID.method687();
            Friend.method5166();
            var73 = InterfaceParent.method1140(ClientPacket.field2385, Client.packetWriter.isaacCipher);
            Client.packetWriter.method1622(var73);
            WorldMapDecoration.method5191();
         }
      }
   }

   @ObfuscatedName("hr")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "819665124"
   )
   static void method505() {
      ScriptEvent.method808();
      Client.menuActions[0] = "Cancel";
      Client.menuTargets[0] = "";
      Client.menuOpcodes[0] = 1006;
      Client.menuShiftClick[0] = false;
      Client.menuOptionsCount = 1;
      Client.onMenuOptionsChanged(-1);
   }
}
