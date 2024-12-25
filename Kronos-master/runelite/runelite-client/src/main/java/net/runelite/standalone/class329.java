package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("lo")
public class class329 {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -850597869
   )
   public static int SpriteBuffer_spriteWidth;
   @ObfuscatedName("p")
   public static int[] SpriteBuffer_spriteWidths;
   @ObfuscatedName("u")
   public static int[] SpriteBuffer_xOffsets;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 2036297741
   )
   static int SpriteBuffer_spriteCount;
   @ObfuscatedName("m")
   public static int[] SpriteBuffer_spritePalette;

   @ObfuscatedName("fu")
   @ObfuscatedSignature(
      signature = "(Lby;II)V",
      garbageValue = "771292121"
   )
   static final void method6315(Actor var0, int var1) {
      SequenceDefinition var2;
      int var4;
      int var11;
      int var12;
      if(var0.field686 > Client.cycle) {
         class191.method3762(var0);
      } else if(var0.field714 >= Client.cycle) {
         HealthBar.method2247(var0);
      } else {
         var0.movementSequence = var0.readySequence;
         if(var0.pathLength == 0) {
            var0.field687 = 0;
         } else {
            label548: {
               if(var0.sequence != -1 && var0.sequenceDelay == 0) {
                  var2 = GrandExchangeOfferUnitPriceComparator.method1468(var0.sequence);
                  if(var0.field726 > 0 && var2.field3436 == 0) {
                     ++var0.field687;
                     break label548;
                  }

                  if(var0.field726 <= 0 && var2.field3431 == 0) {
                     ++var0.field687;
                     break label548;
                  }
               }

               var11 = var0.x;
               var12 = var0.y * 682054857;
               var4 = var0.pathX[var0.pathLength - 1] * 128 + var0.size * 64;
               int var5 = var0.pathY[var0.pathLength - 1] * 128 + var0.size * 64;
               if(var11 < var4) {
                  if(var12 < var5) {
                     var0.orientation = 1280;
                  } else if(var12 > var5) {
                     var0.orientation = 1792;
                  } else {
                     var0.orientation = 1536;
                  }
               } else if(var11 > var4) {
                  if(var12 < var5) {
                     var0.orientation = 768;
                  } else if(var12 > var5) {
                     var0.orientation = 256;
                  } else {
                     var0.orientation = 512;
                  }
               } else if(var12 < var5) {
                  var0.orientation = 1024;
               } else if(var12 > var5) {
                  var0.orientation = 0;
               }

               byte var6 = var0.pathTraversed[var0.pathLength - 1];
               if(var4 - var11 <= 256 && var4 - var11 >= -256 && var5 - var12 <= 256 && var5 - var12 >= -256) {
                  int var7 = var0.orientation - var0.rotation & 2047;
                  if(var7 > 1024) {
                     var7 -= 2048;
                  }

                  int var8 = var0.walkBackSequence;
                  if(var7 >= -256 && var7 <= 256) {
                     var8 = var0.walkSequence;
                  } else if(var7 >= 256 && var7 < 768) {
                     var8 = var0.walkRightSequence;
                  } else if(var7 >= -768 && var7 <= -256) {
                     var8 = var0.walkLeftSequence;
                  }

                  if(var8 == -1) {
                     var8 = var0.walkSequence;
                  }

                  var0.movementSequence = var8;
                  int var9 = 4;
                  boolean var10 = true;
                  if(var0 instanceof NPC) {
                     var10 = ((NPC)var0).definition.isClickable;
                  }

                  if(var10) {
                     if(var0.rotation != var0.orientation && var0.targetIndex == -1 && var0.field720 != 0) {
                        var9 = 2;
                     }

                     if(var0.pathLength > 2) {
                        var9 = 6;
                     }

                     if(var0.pathLength > 3) {
                        var9 = 8;
                     }

                     if(var0.field687 > 0 && var0.pathLength > 1) {
                        var9 = 8;
                        --var0.field687;
                     }
                  } else {
                     if(var0.pathLength > 1) {
                        var9 = 6;
                     }

                     if(var0.pathLength > 2) {
                        var9 = 8;
                     }

                     if(var0.field687 > 0 && var0.pathLength > 1) {
                        var9 = 8;
                        --var0.field687;
                     }
                  }

                  if(var6 == 2) {
                     var9 <<= 1;
                  }

                  if(var9 >= 8 && var0.walkSequence == var0.movementSequence && var0.runSequence != -1) {
                     var0.movementSequence = var0.runSequence;
                  }

                  if(var11 != var4 || var12 != var5) {
                     if(var11 < var4) {
                        var0.x += var9;
                        if(var0.x > var4) {
                           var0.x = var4;
                        }
                     } else if(var11 > var4) {
                        var0.x -= var9;
                        if(var0.x < var4) {
                           var0.x = var4;
                        }
                     }

                     if(var12 < var5) {
                        var0.y += -944175751 * var9;
                        if(var0.y * 682054857 > var5) {
                           var0.y = var5 * -944175751;
                        }
                     } else if(var12 > var5) {
                        var0.y -= var9 * -944175751;
                        if(var0.y * 682054857 < var5) {
                           var0.y = var5 * -944175751;
                        }
                     }
                  }

                  if(var4 == var0.x && var0.y * 682054857 == var5) {
                     --var0.pathLength;
                     if(var0.field726 > 0) {
                        --var0.field726;
                     }
                  }
               } else {
                  var0.x = var4;
                  var0.y = var5 * -944175751;
                  --var0.pathLength;
                  if(var0.field726 > 0) {
                     --var0.field726;
                  }
               }
            }
         }
      }

      if(var0.x < 128 || var0.y * 682054857 < 128 || var0.x >= 13184 || var0.y * 682054857 >= 13184) {
         var0.sequence = -1;
         var0.animationChanged(-1);
         var0.spotAnimation = -1;
         var0.spotAnimationChanged(-1);
         var0.field686 = 0;
         var0.field714 = 0;
         var0.x = var0.pathX[0] * 128 + var0.size * 64;
         var0.y = var0.size * -297705920 + var0.pathY[0] * -595411840;
         var0.method1442();
      }

      if(class215.localPlayer == var0 && (var0.x < 1536 || var0.y * 682054857 < 1536 || var0.x >= 11776 || var0.y * 682054857 >= 11776)) {
         var0.sequence = -1;
         var0.animationChanged(-1);
         var0.spotAnimation = -1;
         var0.spotAnimationChanged(-1);
         var0.field686 = 0;
         var0.field714 = 0;
         var0.x = var0.pathX[0] * 128 + var0.size * 64;
         var0.y = var0.size * -297705920 + var0.pathY[0] * -595411840;
         var0.method1442();
      }

      if(var0.field720 != 0) {
         if(var0.targetIndex != -1) {
            Object var13 = null;
            if(var0.targetIndex < 32768) {
               var13 = Client.npcs[var0.targetIndex];
            } else if(var0.targetIndex >= 32768) {
               var13 = Client.players[var0.targetIndex - 32768];
            }

            if(var13 != null) {
               var12 = var0.x - ((Actor)var13).x;
               var4 = var0.y * 682054857 - ((Actor)var13).y * 682054857;
               if(var12 != 0 || var4 != 0) {
                  var0.orientation = (int)(Math.atan2((double)var12, (double)var4) * 325.949D) & 2047;
               }
            } else if(var0.false0) {
               var0.targetIndex = -1;
               var0.interactingChanged(-1);
               var0.false0 = false;
            }
         }

         if(var0.field695 != -1 && (var0.pathLength == 0 || var0.field687 > 0)) {
            var0.orientation = var0.field695;
            var0.field695 = -1;
         }

         var11 = var0.orientation - var0.rotation & 2047;
         if(var11 == 0 && var0.false0) {
            var0.targetIndex = -1;
            var0.interactingChanged(-1);
            var0.false0 = false;
         }

         if(var11 != 0) {
            ++var0.field719;
            boolean var14;
            if(var11 > 1024) {
               var0.rotation -= var0.field720;
               var14 = true;
               if(var11 < var0.field720 || var11 > 2048 - var0.field720) {
                  var0.rotation = var0.orientation;
                  var14 = false;
               }

               if(var0.readySequence == var0.movementSequence && (var0.field719 > 25 || var14)) {
                  if(var0.turnLeftSequence != -1) {
                     var0.movementSequence = var0.turnLeftSequence;
                  } else {
                     var0.movementSequence = var0.walkSequence;
                  }
               }
            } else {
               var0.rotation += var0.field720;
               var14 = true;
               if(var11 < var0.field720 || var11 > 2048 - var0.field720) {
                  var0.rotation = var0.orientation;
                  var14 = false;
               }

               if(var0.movementSequence == var0.readySequence && (var0.field719 > 25 || var14)) {
                  if(var0.turnRightSequence != -1) {
                     var0.movementSequence = var0.turnRightSequence;
                  } else {
                     var0.movementSequence = var0.walkSequence;
                  }
               }
            }

            var0.rotation &= 2047;
         } else {
            var0.field719 = 0;
         }
      }

      var0.isWalking = false;
      if(var0.movementSequence != -1) {
         var2 = GrandExchangeOfferUnitPriceComparator.method1468(var0.movementSequence);
         if(var2 != null && var2.frameIds != null) {
            ++var0.movementFrameCycle;
            if(var0.movementFrame < var2.frameIds.length && var0.movementFrameCycle > var2.frameLengths[var0.movementFrame]) {
               var0.movementFrameCycle = 1;
               ++var0.movementFrame;
               class33.method676(var2, var0.movementFrame, var0.x, var0.y * 682054857);
            }

            if(var0.movementFrame >= var2.frameIds.length) {
               var0.movementFrameCycle = 0;
               var0.movementFrame = 0;
               class33.method676(var2, var0.movementFrame, var0.x, var0.y * 682054857);
            }
         } else {
            var0.movementSequence = -1;
         }
      }

      if(var0.spotAnimation != -1 && Client.cycle >= var0.field707) {
         if(var0.spotAnimationFrame < 0) {
            var0.spotAnimationFrame = 0;
         }

         var11 = InterfaceParent.method1139(var0.spotAnimation).sequence;
         if(var11 != -1) {
            SequenceDefinition var3 = GrandExchangeOfferUnitPriceComparator.method1468(var11);
            if(var3 != null && var3.frameIds != null) {
               ++var0.spotAnimationFrameCycle;
               if(var0.spotAnimationFrame < var3.frameIds.length && var0.spotAnimationFrameCycle > var3.frameLengths[var0.spotAnimationFrame]) {
                  var0.spotAnimationFrameCycle = 1;
                  ++var0.spotAnimationFrame;
                  class33.method676(var3, var0.spotAnimationFrame, var0.x, var0.y * 682054857);
               }

               if(var0.spotAnimationFrame >= var3.frameIds.length && (var0.spotAnimationFrame < 0 || var0.spotAnimationFrame >= var3.frameIds.length)) {
                  var0.spotAnimation = -1;
                  var0.spotAnimationChanged(-1);
               }
            } else {
               var0.spotAnimation = -1;
               var0.spotAnimationChanged(-1);
            }
         } else {
            var0.spotAnimation = -1;
            var0.spotAnimationChanged(-1);
         }
      }

      if(var0.sequence != -1 && var0.sequenceDelay <= 1) {
         var2 = GrandExchangeOfferUnitPriceComparator.method1468(var0.sequence);
         if(var2.field3436 == 1 && var0.field726 > 0 && var0.field686 <= Client.cycle && var0.field714 < Client.cycle) {
            var0.sequenceDelay = 1;
            return;
         }
      }

      if(var0.sequence != -1 && var0.sequenceDelay == 0) {
         var2 = GrandExchangeOfferUnitPriceComparator.method1468(var0.sequence);
         if(var2 != null && var2.frameIds != null) {
            ++var0.sequenceFrameCycle;
            if(var0.sequenceFrame < var2.frameIds.length && var0.sequenceFrameCycle > var2.frameLengths[var0.sequenceFrame]) {
               var0.sequenceFrameCycle = 1;
               ++var0.sequenceFrame;
               class33.method676(var2, var0.sequenceFrame, var0.x, var0.y * 682054857);
            }

            if(var0.sequenceFrame >= var2.frameIds.length) {
               var0.sequenceFrame -= var2.frameCount;
               ++var0.field703;
               if(var0.field703 >= var2.field3424) {
                  var0.sequence = -1;
                  var0.animationChanged(-1);
               } else if(var0.sequenceFrame >= 0 && var0.sequenceFrame < var2.frameIds.length) {
                  class33.method676(var2, var0.sequenceFrame, var0.x, var0.y * 682054857);
               } else {
                  var0.sequence = -1;
                  var0.animationChanged(-1);
               }
            }

            var0.isWalking = var2.field3425;
         } else {
            var0.sequence = -1;
            var0.animationChanged(-1);
         }
      }

      if(var0.sequenceDelay > 0) {
         --var0.sequenceDelay;
      }

   }

   @ObfuscatedName("jc")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-2138137285"
   )
   static void method6295() {
      for(InterfaceParent var0 = (InterfaceParent)Client.interfaceParents.method6348(); var0 != null; var0 = (InterfaceParent)Client.interfaceParents.method6345()) {
         int var1 = var0.group;
         if(WorldMapData_0.method171(var1)) {
            boolean var2 = true;
            Widget[] var3 = UserComparator5.Widget_interfaceComponents[var1];

            int var4;
            for(var4 = 0; var4 < var3.length; ++var4) {
               if(var3[var4] != null) {
                  var2 = var3[var4].isIf3;
                  break;
               }
            }

            if(!var2) {
               var4 = (int)var0.key;
               Widget var5 = Canvas.getWidget(var4);
               if(var5 != null) {
                  WorldMapSectionType.method116(var5);
               }
            }
         }
      }

   }
}
