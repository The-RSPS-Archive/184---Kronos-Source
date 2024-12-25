package net.runelite.standalone;

import net.runelite.client.callback.Hooks;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSFriend;
import net.runelite.rs.api.RSUsername;

@ObfuscatedName("jy")
public class Friend extends Buddy implements RSFriend {
   @ObfuscatedName("n")
   boolean field3614;
   @ObfuscatedName("z")
   boolean field3615;

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljy;I)I",
      garbageValue = "371914259"
   )
   int method5175(Friend var1) {
      return super.world == Client.worldId && Client.worldId != var1.world?-1:(Client.worldId == var1.world && super.world != Client.worldId?1:(super.world != 0 && var1.world == 0?-1:(var1.world != 0 && super.world == 0?1:(this.field3615 && !var1.field3615?-1:(!this.field3615 && var1.field3615?1:(this.field3614 && !var1.field3614?-1:(!this.field3614 && var1.field3614?1:(super.world != 0?super.int2 - var1.int2:var1.int2 - super.int2))))))));
   }

   public int compareTo(Object var1) {
      return this.method5175((Friend)var1);
   }

   public String getName() {
      return this.getRsName().getName();
   }

   public String getPrevName() {
      RSUsername var1 = this.getRsPrevName();
      return var1 == null?null:var1.getName();
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "(Ljh;I)I",
      garbageValue = "-531306911"
   )
   public int vmethod5168(User var1) {
      return this.method5175((Friend)var1);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "530220947"
   )
   static void method5166() {
      Tiles.field540 = null;
      Tiles.field522 = null;
      DevicePcmPlayerProvider.field156 = null;
      class298.field3719 = null;
      Tiles.field527 = null;
      Tiles.field525 = null;
      DevicePcmPlayerProvider.field149 = null;
      Tiles.Tiles_hue = null;
      ArchiveLoader.Tiles_saturation = null;
      Tiles.Tiles_lightness = null;
      FontName.Tiles_hueMultiplier = null;
      Tiles.field526 = null;
   }

   @ObfuscatedName("fp")
   @ObfuscatedSignature(
      signature = "(IIIII)V",
      garbageValue = "-541695875"
   )
   static final void method5176(int var0, int var1, int var2, int var3) {
      ++Client.viewportDrawCount;
      if(class215.localPlayer.x >> 7 == Client.destinationX && class215.localPlayer.y * 682054857 >> 7 == Client.destinationY) {
         Client.destinationX = 0;
      }

      if(Client.renderSelf) {
         Players.method2146(class215.localPlayer, false);
      }

      UrlRequest.method2925();
      class8.method104(true);
      Login.method1566();
      class8.method104(false);
      DynamicObject.method1572();

      for(GraphicsObject var4 = (GraphicsObject)Client.graphicsObjects.method5103(); var4 != null; var4 = (GraphicsObject)Client.graphicsObjects.method5126()) {
         if(var4.plane == WorldMapRectangle.plane && !var4.isFinished) {
            if(Client.cycle >= var4.cycleStart) {
               var4.method1255(Client.field906);
               if(var4.isFinished) {
                  var4.method3497();
               } else {
                  PacketWriter.scene.method3125(var4.plane, var4.x, var4.y, var4.height, 60, var4, 0, -1L, false);
               }
            }
         } else {
            var4.method3497();
         }
      }

      AbstractByteArrayCopier.method3874(var0, var1, var2, var3, true);
      var0 = Client.viewportOffsetX;
      var1 = Client.viewportOffsetY;
      var2 = Client.viewportWidth;
      var3 = Client.viewportHeight;
      Rasterizer2D.method6474(var0, var1, var0 + var2, var3 + var1);
      Rasterizer3D.method2944();
      int var5;
      int var6;
      int var7;
      int var8;
      int var11;
      int var15;
      if(!Client.isCameraLocked) {
         var15 = Client.camAngleX;
         if(Client.field930 / 256 > var15) {
            var15 = Client.field930 / 256;
         }

         if(Client.field1043[4] && Client.field1091[4] + 128 > var15) {
            var15 = Client.field1091[4] + 128;
         }

         var5 = Client.camAngleY & 2047;
         var6 = ObjectSound.oculusOrbFocalPointX;
         var7 = ModelData0.field1785 * -1351160427;
         var8 = class125.oculusOrbFocalPointY;
         var11 = var15 * 3 + 600;
         NPCDefinition.method4403(var6, var7, var8, var15, var5, var11, var3);
      }

      int var9;
      int var10;
      int var12;
      int var13;
      int var14;
      if(!Client.isCameraLocked) {
         if(AbstractArchive.clientPreferences.roofsHidden) {
            var5 = WorldMapRectangle.plane;
         } else {
            label409: {
               var6 = 3;
               if(IgnoreList.cameraPitch < 310) {
                  if(Client.oculusOrbState == 1) {
                     var7 = ObjectSound.oculusOrbFocalPointX >> 7;
                     var8 = class125.oculusOrbFocalPointY >> 7;
                  } else {
                     var7 = class215.localPlayer.x >> 7;
                     var8 = class215.localPlayer.y * 682054857 >> 7;
                  }

                  var9 = GrandExchangeOfferOwnWorldComparator.cameraX >> 7;
                  var10 = WorldMapIcon_1.cameraZ >> 7;
                  if(var9 < 0 || var10 < 0 || var9 >= 104 || var10 >= 104) {
                     var5 = WorldMapRectangle.plane;
                     break label409;
                  }

                  if(var7 < 0 || var8 < 0 || var7 >= 104 || var8 >= 104) {
                     var5 = WorldMapRectangle.plane;
                     break label409;
                  }

                  if((Tiles.Tiles_renderFlags[WorldMapRectangle.plane][var9][var10] & 4) != 0) {
                     var6 = WorldMapRectangle.plane;
                  }

                  if(var7 > var9) {
                     var11 = var7 - var9;
                  } else {
                     var11 = var9 - var7;
                  }

                  if(var8 > var10) {
                     var12 = var8 - var10;
                  } else {
                     var12 = var10 - var8;
                  }

                  if(var11 > var12) {
                     var13 = var12 * 65536 / var11;
                     var14 = 32768;

                     while(var7 != var9) {
                        if(var9 < var7) {
                           ++var9;
                        } else if(var9 > var7) {
                           --var9;
                        }

                        if((Tiles.Tiles_renderFlags[WorldMapRectangle.plane][var9][var10] & 4) != 0) {
                           var6 = WorldMapRectangle.plane;
                        }

                        var14 += var13;
                        if(var14 >= 65536) {
                           var14 -= 65536;
                           if(var10 < var8) {
                              ++var10;
                           } else if(var10 > var8) {
                              --var10;
                           }

                           if((Tiles.Tiles_renderFlags[WorldMapRectangle.plane][var9][var10] & 4) != 0) {
                              var6 = WorldMapRectangle.plane;
                           }
                        }
                     }
                  } else if(var12 > 0) {
                     var13 = var11 * 65536 / var12;
                     var14 = 32768;

                     while(var8 != var10) {
                        if(var10 < var8) {
                           ++var10;
                        } else if(var10 > var8) {
                           --var10;
                        }

                        if((Tiles.Tiles_renderFlags[WorldMapRectangle.plane][var9][var10] & 4) != 0) {
                           var6 = WorldMapRectangle.plane;
                        }

                        var14 += var13;
                        if(var14 >= 65536) {
                           var14 -= 65536;
                           if(var9 < var7) {
                              ++var9;
                           } else if(var9 > var7) {
                              --var9;
                           }

                           if((Tiles.Tiles_renderFlags[WorldMapRectangle.plane][var9][var10] & 4) != 0) {
                              var6 = WorldMapRectangle.plane;
                           }
                        }
                     }
                  }
               }

               if(class215.localPlayer.x >= 0 && class215.localPlayer.y * 682054857 >= 0 && class215.localPlayer.x < 13312 && class215.localPlayer.y * 682054857 < 13312) {
                  if((Tiles.Tiles_renderFlags[WorldMapRectangle.plane][class215.localPlayer.x >> 7][class215.localPlayer.y * 682054857 >> 7] & 4) != 0) {
                     var6 = WorldMapRectangle.plane;
                  }

                  var5 = var6;
               } else {
                  var5 = WorldMapRectangle.plane;
               }
            }
         }

         var15 = var5;
      } else {
         var15 = class214.method3937();
      }

      var5 = GrandExchangeOfferOwnWorldComparator.cameraX;
      var6 = Varcs.cameraY;
      var7 = WorldMapIcon_1.cameraZ;
      var8 = IgnoreList.cameraPitch;
      var9 = WorldMapSection2.cameraYaw;

      for(var10 = 0; var10 < 5; ++var10) {
         if(Client.field1043[var10]) {
            var11 = (int)(Math.random() * (double)(Client.field1090[var10] * 2 + 1) - (double)Client.field1090[var10] + Math.sin((double)Client.field1092[var10] / 100.0D * (double)Client.field1093[var10]) * (double)Client.field1091[var10]);
            if(var10 == 0) {
               GrandExchangeOfferOwnWorldComparator.cameraX += var11;
            }

            if(var10 == 1) {
               Varcs.cameraY += var11;
            }

            if(var10 == 2) {
               WorldMapIcon_1.cameraZ += var11;
            }

            if(var10 == 3) {
               WorldMapSection2.cameraYaw = var11 + WorldMapSection2.cameraYaw & 2047;
            }

            if(var10 == 4) {
               IgnoreList.cameraPitch += var11;
               Client.onCameraPitchChanged(-1);
               if(IgnoreList.cameraPitch < 128) {
                  IgnoreList.cameraPitch = 128;
                  Client.onCameraPitchChanged(-1);
               }

               if(IgnoreList.cameraPitch > 383) {
                  IgnoreList.cameraPitch = 383;
                  Client.onCameraPitchChanged(-1);
               }
            }
         }
      }

      var10 = MouseHandler.MouseHandler_x;
      var11 = MouseHandler.MouseHandler_y;
      if(MouseHandler.MouseHandler_lastButton != 0) {
         var10 = MouseHandler.MouseHandler_lastPressedX;
         var11 = MouseHandler.MouseHandler_lastPressedY;
      }

      if(var10 >= var0 && var10 < var0 + var2 && var11 >= var1 && var11 < var3 + var1) {
         WorldMapSection1.method747(var10 - var0, var11 - var1);
      } else {
         MenuAction.method2159();
      }

      WorldMapID.method687();
      Hooks.clearColorBuffer(var0, var1, var2, var3, 0);
      WorldMapID.method687();
      var12 = Rasterizer3D.Rasterizer3D_zoom;
      Rasterizer3D.Rasterizer3D_zoom = Client.viewportZoom;
      PacketWriter.scene.method3155(GrandExchangeOfferOwnWorldComparator.cameraX, Varcs.cameraY, WorldMapIcon_1.cameraZ, IgnoreList.cameraPitch, WorldMapSection2.cameraYaw, var15);
      Rasterizer3D.Rasterizer3D_zoom = var12;
      WorldMapID.method687();
      PacketWriter.scene.method3128();
      ViewportMouse.method2303(var0, var1, var2, var3);
      Message.method876(var0, var1);
      ((TextureProvider)Rasterizer3D.Rasterizer3D_textureLoader).method2846(Client.field906);
      Client.field842 = 0;
      var13 = (class215.localPlayer.x >> 7) + class215.baseX;
      var14 = (class215.localPlayer.y * 682054857 >> 7) + class304.baseY;
      if(var13 >= 3053 && var13 <= 3156 && var14 >= 3056 && var14 <= 3136) {
         Client.field842 = 1;
      }

      if(var13 >= 3072 && var13 <= 3118 && var14 >= 9492 && var14 <= 9535) {
         Client.field842 = 1;
      }

      if(Client.field842 == 1 && var13 >= 3139 && var13 <= 3199 && var14 >= 3008 && var14 <= 3062) {
         Client.field842 = 0;
      }

      GrandExchangeOfferOwnWorldComparator.cameraX = var5;
      Varcs.cameraY = var6;
      WorldMapIcon_1.cameraZ = var7;
      IgnoreList.cameraPitch = var8;
      Client.onCameraPitchChanged(-1);
      WorldMapSection2.cameraYaw = var9;
      if(Client.isLoading && class4.method56(true, false) == 0) {
         Client.isLoading = false;
      }

      if(Client.isLoading) {
         Hooks.clearColorBuffer(var0, var1, var2, var3, 0);
         WorldMapSprite.method784("Loading - please wait.", false);
      }

   }
}
