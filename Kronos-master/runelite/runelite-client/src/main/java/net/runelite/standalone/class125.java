package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("e")
public class class125 {
   @ObfuscatedName("ry")
   @ObfuscatedGetter(
      intValue = 1308893635
   )
   static int field1658;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive SpotAnimationDefinition_archive;
   @ObfuscatedName("gu")
   @ObfuscatedSignature(
      signature = "[Llp;"
   )
   static IndexedSprite[] mapSceneSprites;
   @ObfuscatedName("hy")
   @ObfuscatedGetter(
      intValue = -1228592435
   )
   static int oculusOrbFocalPointY;

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/Throwable;Ljava/lang/String;)Lmf;"
   )
   public static RunException method2877(Throwable var0, String var1) {
      RunException var2;
      if(var0 instanceof RunException) {
         var2 = (RunException)var0;
         var2.message = var2.message + ' ' + var1;
      } else {
         var2 = new RunException(var0, var1);
      }

      return var2;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(IIILfi;Lfa;B)Z",
      garbageValue = "-110"
   )
   static final boolean method2873(int var0, int var1, int var2, RouteStrategy var3, CollisionMap var4) {
      int var5 = var0;
      int var6 = var1;
      byte var7 = 64;
      byte var8 = 64;
      int var9 = var0 - var7;
      int var10 = var1 - var8;
      class170.directions[var7][var8] = 99;
      class170.distances[var7][var8] = 0;
      byte var11 = 0;
      int var12 = 0;
      class170.bufferX[var11] = var0;
      int var20 = var11 + 1;
      class170.bufferY[var11] = var1;
      int[][] var13 = var4.flags;

      while(true) {
         label303:
         while(true) {
            int var14;
            int var15;
            int var16;
            int var17;
            int var18;
            int var19;
            do {
               do {
                  do {
                     label280:
                     do {
                        if(var12 == var20) {
                           UserComparator10.field1808 = var5;
                           class170.field2003 = var6;
                           return false;
                        }

                        var5 = class170.bufferX[var12];
                        var6 = class170.bufferY[var12];
                        var12 = var12 + 1 & 4095;
                        var18 = var5 - var9;
                        var19 = var6 - var10;
                        var14 = var5 - var4.xInset;
                        var15 = var6 - var4.yInset;
                        if(var3.vmethod3410(var2, var5, var6, var4)) {
                           UserComparator10.field1808 = var5;
                           class170.field2003 = var6;
                           return true;
                        }

                        var16 = class170.distances[var18][var19] + 1;
                        if(var18 > 0 && class170.directions[var18 - 1][var19] == 0 && (var13[var14 - 1][var15] & 19136782) == 0 && (var13[var14 - 1][var15 + var2 - 1] & 19136824) == 0) {
                           var17 = 1;

                           while(true) {
                              if(var17 >= var2 - 1) {
                                 class170.bufferX[var20] = var5 - 1;
                                 class170.bufferY[var20] = var6;
                                 var20 = var20 + 1 & 4095;
                                 class170.directions[var18 - 1][var19] = 2;
                                 class170.distances[var18 - 1][var19] = var16;
                                 break;
                              }

                              if((var13[var14 - 1][var15 + var17] & 19136830) != 0) {
                                 break;
                              }

                              ++var17;
                           }
                        }

                        if(var18 < 128 - var2 && class170.directions[var18 + 1][var19] == 0 && (var13[var14 + var2][var15] & 19136899) == 0 && (var13[var14 + var2][var15 + var2 - 1] & 19136992) == 0) {
                           var17 = 1;

                           while(true) {
                              if(var17 >= var2 - 1) {
                                 class170.bufferX[var20] = var5 + 1;
                                 class170.bufferY[var20] = var6;
                                 var20 = var20 + 1 & 4095;
                                 class170.directions[var18 + 1][var19] = 8;
                                 class170.distances[var18 + 1][var19] = var16;
                                 break;
                              }

                              if((var13[var14 + var2][var17 + var15] & 19136995) != 0) {
                                 break;
                              }

                              ++var17;
                           }
                        }

                        if(var19 > 0 && class170.directions[var18][var19 - 1] == 0 && (var13[var14][var15 - 1] & 19136782) == 0 && (var13[var14 + var2 - 1][var15 - 1] & 19136899) == 0) {
                           var17 = 1;

                           while(true) {
                              if(var17 >= var2 - 1) {
                                 class170.bufferX[var20] = var5;
                                 class170.bufferY[var20] = var6 - 1;
                                 var20 = var20 + 1 & 4095;
                                 class170.directions[var18][var19 - 1] = 1;
                                 class170.distances[var18][var19 - 1] = var16;
                                 break;
                              }

                              if((var13[var17 + var14][var15 - 1] & 19136911) != 0) {
                                 break;
                              }

                              ++var17;
                           }
                        }

                        if(var19 < 128 - var2 && class170.directions[var18][var19 + 1] == 0 && (var13[var14][var15 + var2] & 19136824) == 0 && (var13[var14 + var2 - 1][var15 + var2] & 19136992) == 0) {
                           var17 = 1;

                           while(true) {
                              if(var17 >= var2 - 1) {
                                 class170.bufferX[var20] = var5;
                                 class170.bufferY[var20] = var6 + 1;
                                 var20 = var20 + 1 & 4095;
                                 class170.directions[var18][var19 + 1] = 4;
                                 class170.distances[var18][var19 + 1] = var16;
                                 break;
                              }

                              if((var13[var14 + var17][var15 + var2] & 19137016) != 0) {
                                 break;
                              }

                              ++var17;
                           }
                        }

                        if(var18 > 0 && var19 > 0 && class170.directions[var18 - 1][var19 - 1] == 0 && (var13[var14 - 1][var15 - 1] & 19136782) == 0) {
                           var17 = 1;

                           while(true) {
                              if(var17 >= var2) {
                                 class170.bufferX[var20] = var5 - 1;
                                 class170.bufferY[var20] = var6 - 1;
                                 var20 = var20 + 1 & 4095;
                                 class170.directions[var18 - 1][var19 - 1] = 3;
                                 class170.distances[var18 - 1][var19 - 1] = var16;
                                 break;
                              }

                              if((var13[var14 - 1][var17 + (var15 - 1)] & 19136830) != 0 || (var13[var17 + (var14 - 1)][var15 - 1] & 19136911) != 0) {
                                 break;
                              }

                              ++var17;
                           }
                        }

                        if(var18 < 128 - var2 && var19 > 0 && class170.directions[var18 + 1][var19 - 1] == 0 && (var13[var14 + var2][var15 - 1] & 19136899) == 0) {
                           var17 = 1;

                           while(true) {
                              if(var17 >= var2) {
                                 class170.bufferX[var20] = var5 + 1;
                                 class170.bufferY[var20] = var6 - 1;
                                 var20 = var20 + 1 & 4095;
                                 class170.directions[var18 + 1][var19 - 1] = 9;
                                 class170.distances[var18 + 1][var19 - 1] = var16;
                                 break;
                              }

                              if((var13[var14 + var2][var17 + (var15 - 1)] & 19136995) != 0 || (var13[var17 + var14][var15 - 1] & 19136911) != 0) {
                                 break;
                              }

                              ++var17;
                           }
                        }

                        if(var18 > 0 && var19 < 128 - var2 && class170.directions[var18 - 1][var19 + 1] == 0 && (var13[var14 - 1][var15 + var2] & 19136824) == 0) {
                           for(var17 = 1; var17 < var2; ++var17) {
                              if((var13[var14 - 1][var17 + var15] & 19136830) != 0 || (var13[var17 + (var14 - 1)][var15 + var2] & 19137016) != 0) {
                                 continue label280;
                              }
                           }

                           class170.bufferX[var20] = var5 - 1;
                           class170.bufferY[var20] = var6 + 1;
                           var20 = var20 + 1 & 4095;
                           class170.directions[var18 - 1][var19 + 1] = 6;
                           class170.distances[var18 - 1][var19 + 1] = var16;
                        }
                     } while(var18 >= 128 - var2);
                  } while(var19 >= 128 - var2);
               } while(class170.directions[var18 + 1][var19 + 1] != 0);
            } while((var13[var14 + var2][var15 + var2] & 19136992) != 0);

            for(var17 = 1; var17 < var2; ++var17) {
               if((var13[var17 + var14][var15 + var2] & 19137016) != 0 || (var13[var14 + var2][var15 + var17] & 19136995) != 0) {
                  continue label303;
               }
            }

            class170.bufferX[var20] = var5 + 1;
            class170.bufferY[var20] = var6 + 1;
            var20 = var20 + 1 & 4095;
            class170.directions[var18 + 1][var19 + 1] = 12;
            class170.distances[var18 + 1][var19 + 1] = var16;
         }
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(CI)C",
      garbageValue = "-2022718130"
   )
   static char method2876(char var0) {
      return (char)(var0 == 198?69:(var0 == 230?101:(var0 == 223?115:(var0 == 338?69:(var0 == 339?'e':'\u0000')))));
   }

   @ObfuscatedName("il")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-259743374"
   )
   static boolean method2875() {
      return Client.tapToDrop || KeyHandler.KeyHandler_pressedKeys[81];
   }
}
