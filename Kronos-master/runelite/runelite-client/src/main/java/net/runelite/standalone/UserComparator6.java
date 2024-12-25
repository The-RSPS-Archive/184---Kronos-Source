package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("fy")
public class UserComparator6 extends AbstractUserComparator {
   @ObfuscatedName("z")
   final boolean reversed;

   public UserComparator6(boolean var1) {
      this.reversed = var1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljl;Ljl;I)I",
      garbageValue = "-1158182029"
   )
   int method3508(Buddy var1, Buddy var2) {
      return var1.world != 0 && var2.world != 0?(this.reversed?var1.method4879().method4992(var2.method4879()):var2.method4879().method4992(var1.method4879())):this.method5015(var1, var2);
   }

   public int compare(Object var1, Object var2) {
      return this.method3508((Buddy)var1, (Buddy)var2);
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "([BIII)Ljava/lang/String;",
      garbageValue = "1774248129"
   )
   public static String method3506(byte[] var0, int var1, int var2) {
      char[] var3 = new char[var2];
      int var4 = 0;
      int var5 = var1;

      int var8;
      for(int var6 = var2 + var1; var5 < var6; var3[var4++] = (char)var8) {
         int var7 = var0[var5++] & 255;
         if(var7 < 128) {
            if(var7 == 0) {
               var8 = 65533;
            } else {
               var8 = var7;
            }
         } else if(var7 < 192) {
            var8 = 65533;
         } else if(var7 < 224) {
            if(var5 < var6 && (var0[var5] & 192) == 128) {
               var8 = (var7 & 31) << 6 | var0[var5++] & 63;
               if(var8 < 128) {
                  var8 = 65533;
               }
            } else {
               var8 = 65533;
            }
         } else if(var7 < 240) {
            if(var5 + 1 < var6 && (var0[var5] & 192) == 128 && (var0[var5 + 1] & 192) == 128) {
               var8 = (var7 & 15) << 12 | (var0[var5++] & 63) << 6 | var0[var5++] & 63;
               if(var8 < 2048) {
                  var8 = 65533;
               }
            } else {
               var8 = 65533;
            }
         } else if(var7 < 248) {
            if(var5 + 2 < var6 && (var0[var5] & 192) == 128 && (var0[var5 + 1] & 192) == 128 && (var0[var5 + 2] & 192) == 128) {
               var8 = (var7 & 7) << 18 | (var0[var5++] & 63) << 12 | (var0[var5++] & 63) << 6 | var0[var5++] & 63;
               if(var8 >= 65536 && var8 <= 1114111) {
                  var8 = 65533;
               } else {
                  var8 = 65533;
               }
            } else {
               var8 = 65533;
            }
         } else {
            var8 = 65533;
         }
      }

      return new String(var3, 0, var4);
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "-1182264787"
   )
   static int method3505(int var0, Script var1, boolean var2) {
      Widget var3 = var2?GrandExchangeOfferAgeComparator.field26:KitDefinition.field3452;
      if(var0 == 1600) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.scrollX;
         return 1;
      } else if(var0 == 1601) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.scrollY;
         return 1;
      } else if(var0 == 1602) {
         Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3.text;
         return 1;
      } else if(var0 == 1603) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.scrollWidth;
         return 1;
      } else if(var0 == 1604) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.scrollHeight;
         return 1;
      } else if(var0 == 1605) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.modelZoom;
         return 1;
      } else if(var0 == 1606) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.modelAngleX;
         return 1;
      } else if(var0 == 1607) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.modelAngleZ;
         return 1;
      } else if(var0 == 1608) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.modelAngleY;
         return 1;
      } else if(var0 == 1609) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.transparencyTop;
         return 1;
      } else if(var0 == 1610) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.transparencyBot;
         return 1;
      } else if(var0 == 1611) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.color;
         return 1;
      } else if(var0 == 1612) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.color2;
         return 1;
      } else if(var0 == 1613) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.fillMode.getId();
         return 1;
      } else if(var0 == 1614) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.modelTransparency?1:0;
         return 1;
      } else {
         return 2;
      }
   }

   @ObfuscatedName("ae")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "600807723"
   )
   static int method3500(int var0, Script var1, boolean var2) {
      int var3;
      if(var0 == 6600) {
         var3 = WorldMapRectangle.plane;
         int var9 = (class215.localPlayer.x >> 7) + class215.baseX;
         int var5 = (class215.localPlayer.y * 682054857 >> 7) + class304.baseY;
         Decimator.method2485().method5881(var3, var9, var5, true);
         return 1;
      } else {
         WorldMapArea var11;
         if(var0 == 6601) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            String var16 = "";
            var11 = Decimator.method2485().method5899(var3);
            if(var11 != null) {
               var16 = var11.method357();
            }

            Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var16;
            return 1;
         } else if(var0 == 6602) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            Decimator.method2485().method5882(var3);
            return 1;
         } else if(var0 == 6603) {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Decimator.method2485().method5896();
            return 1;
         } else if(var0 == 6604) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            Decimator.method2485().method5893(var3);
            return 1;
         } else if(var0 == 6605) {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Decimator.method2485().method5898()?1:0;
            return 1;
         } else {
            Coord var15;
            if(var0 == 6606) {
               var15 = new Coord(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
               Decimator.method2485().method6000(var15.x, var15.y);
               return 1;
            } else if(var0 == 6607) {
               var15 = new Coord(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
               Decimator.method2485().method5875(var15.x, var15.y);
               return 1;
            } else if(var0 == 6608) {
               var15 = new Coord(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
               Decimator.method2485().method5902(var15.plane, var15.x, var15.y);
               return 1;
            } else if(var0 == 6609) {
               var15 = new Coord(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
               Decimator.method2485().method5903(var15.plane, var15.x, var15.y);
               return 1;
            } else if(var0 == 6610) {
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Decimator.method2485().method5904();
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Decimator.method2485().method5905();
               return 1;
            } else {
               WorldMapArea var13;
               if(var0 == 6611) {
                  var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var13 = Decimator.method2485().method5899(var3);
                  if(var13 == null) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                  } else {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var13.method367().method3913();
                  }

                  return 1;
               } else if(var0 == 6612) {
                  var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var13 = Decimator.method2485().method5899(var3);
                  if(var13 == null) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                  } else {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = (var13.method410() - var13.method360() + 1) * 64;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = (var13.method363() - var13.method362() + 1) * 64;
                  }

                  return 1;
               } else if(var0 == 6613) {
                  var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var13 = Decimator.method2485().method5899(var3);
                  if(var13 == null) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                  } else {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var13.method360() * 64;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var13.method362() * 64;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var13.method410() * 64 + 64 - 1;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var13.method363() * 64 + 64 - 1;
                  }

                  return 1;
               } else if(var0 == 6614) {
                  var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var13 = Decimator.method2485().method5899(var3);
                  if(var13 == null) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                  } else {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var13.method359();
                  }

                  return 1;
               } else if(var0 == 6615) {
                  var15 = Decimator.method2485().method5906();
                  if(var15 == null) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                  } else {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var15.x;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var15.y;
                  }

                  return 1;
               } else if(var0 == 6616) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Decimator.method2485().method5883();
                  return 1;
               } else if(var0 == 6617) {
                  var15 = new Coord(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
                  var13 = Decimator.method2485().method5884();
                  if(var13 == null) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                     return 1;
                  } else {
                     int[] var14 = var13.method369(var15.plane, var15.x, var15.y);
                     if(var14 == null) {
                        Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                        Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                     } else {
                        Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var14[0];
                        Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var14[1];
                     }

                     return 1;
                  }
               } else {
                  Coord var7;
                  if(var0 == 6618) {
                     var15 = new Coord(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
                     var13 = Decimator.method2485().method5884();
                     if(var13 == null) {
                        Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                        Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                        return 1;
                     } else {
                        var7 = var13.method352(var15.x, var15.y);
                        if(var7 == null) {
                           Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                        } else {
                           Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var7.method3913();
                        }

                        return 1;
                     }
                  } else {
                     Coord var12;
                     if(var0 == 6619) {
                        Interpreter.Interpreter_intStackSize -= 2;
                        var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                        var12 = new Coord(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]);
                        WorldMapLabel.method743(var3, var12, false);
                        return 1;
                     } else if(var0 == 6620) {
                        Interpreter.Interpreter_intStackSize -= 2;
                        var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                        var12 = new Coord(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]);
                        WorldMapLabel.method743(var3, var12, true);
                        return 1;
                     } else if(var0 == 6621) {
                        Interpreter.Interpreter_intStackSize -= 2;
                        var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                        var12 = new Coord(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]);
                        var11 = Decimator.method2485().method5899(var3);
                        if(var11 == null) {
                           Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                           return 1;
                        } else {
                           Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var11.method361(var12.plane, var12.x, var12.y)?1:0;
                           return 1;
                        }
                     } else if(var0 == 6622) {
                        Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Decimator.method2485().method6009();
                        Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Decimator.method2485().method5967();
                        return 1;
                     } else if(var0 == 6623) {
                        var15 = new Coord(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
                        var13 = Decimator.method2485().method5918(var15.plane, var15.x, var15.y);
                        if(var13 == null) {
                           Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                        } else {
                           Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var13.method354();
                        }

                        return 1;
                     } else if(var0 == 6624) {
                        Decimator.method2485().method5909(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
                        return 1;
                     } else if(var0 == 6625) {
                        Decimator.method2485().method5910();
                        return 1;
                     } else if(var0 == 6626) {
                        Decimator.method2485().method5921(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
                        return 1;
                     } else if(var0 == 6627) {
                        Decimator.method2485().method5912();
                        return 1;
                     } else {
                        boolean var10;
                        if(var0 == 6628) {
                           var10 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                           Decimator.method2485().method6047(var10);
                           return 1;
                        } else if(var0 == 6629) {
                           var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                           Decimator.method2485().method5908(var3);
                           return 1;
                        } else if(var0 == 6630) {
                           var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                           Decimator.method2485().method5915(var3);
                           return 1;
                        } else if(var0 == 6631) {
                           Decimator.method2485().method5916();
                           return 1;
                        } else if(var0 == 6632) {
                           var10 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                           Decimator.method2485().method5917(var10);
                           return 1;
                        } else {
                           boolean var4;
                           if(var0 == 6633) {
                              Interpreter.Interpreter_intStackSize -= 2;
                              var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                              var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1] == 1;
                              Decimator.method2485().method6010(var3, var4);
                              return 1;
                           } else if(var0 == 6634) {
                              Interpreter.Interpreter_intStackSize -= 2;
                              var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                              var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1] == 1;
                              Decimator.method2485().method5996(var3, var4);
                              return 1;
                           } else if(var0 == 6635) {
                              Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Decimator.method2485().method5950()?1:0;
                              return 1;
                           } else if(var0 == 6636) {
                              var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                              Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Decimator.method2485().method5907(var3)?1:0;
                              return 1;
                           } else if(var0 == 6637) {
                              var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                              Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Decimator.method2485().method5922(var3)?1:0;
                              return 1;
                           } else if(var0 == 6638) {
                              Interpreter.Interpreter_intStackSize -= 2;
                              var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                              var12 = new Coord(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]);
                              var7 = Decimator.method2485().method5925(var3, var12);
                              if(var7 == null) {
                                 Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                              } else {
                                 Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var7.method3913();
                              }

                              return 1;
                           } else {
                              AbstractWorldMapIcon var8;
                              if(var0 == 6639) {
                                 var8 = Decimator.method2485().method5927();
                                 if(var8 == null) {
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                                 } else {
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var8.vmethod2277();
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var8.coord2.method3913();
                                 }

                                 return 1;
                              } else if(var0 == 6640) {
                                 var8 = Decimator.method2485().method5928();
                                 if(var8 == null) {
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                                 } else {
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var8.vmethod2277();
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var8.coord2.method3913();
                                 }

                                 return 1;
                              } else {
                                 WorldMapElement var6;
                                 if(var0 == 6693) {
                                    var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                                    var6 = Decimator.method2498(var3);
                                    if(var6.name == null) {
                                       Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                                    } else {
                                       Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var6.name;
                                    }

                                    return 1;
                                 } else if(var0 == 6694) {
                                    var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                                    var6 = Decimator.method2498(var3);
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var6.textSize;
                                    return 1;
                                 } else if(var0 == 6695) {
                                    var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                                    var6 = Decimator.method2498(var3);
                                    if(var6 == null) {
                                       Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                                    } else {
                                       Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var6.category;
                                    }

                                    return 1;
                                 } else if(var0 == 6696) {
                                    var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                                    var6 = Decimator.method2498(var3);
                                    if(var6 == null) {
                                       Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                                    } else {
                                       Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var6.sprite1;
                                    }

                                    return 1;
                                 } else if(var0 == 6697) {
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = EnumDefinition.worldMapEvent.mapElement;
                                    return 1;
                                 } else if(var0 == 6698) {
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = EnumDefinition.worldMapEvent.coord1.method3913();
                                    return 1;
                                 } else if(var0 == 6699) {
                                    Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = EnumDefinition.worldMapEvent.coord2.method3913();
                                    return 1;
                                 } else {
                                    return 2;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedName("hv")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "2147289809"
   )
   static void method3507(int var0, int var1) {
      int var2 = class170.fontBold12.method5324("Choose Option");

      int var3;
      int var4;
      for(var3 = 0; var3 < Client.menuOptionsCount; ++var3) {
         var4 = class170.fontBold12.method5324(class28.method591(var3));
         if(var4 > var2) {
            var2 = var4;
         }
      }

      var2 += 8;
      var3 = Client.menuOptionsCount * 15 + 22;
      var4 = var0 - var2 / 2;
      if(var4 + var2 > FloorDecoration.canvasWidth) {
         var4 = FloorDecoration.canvasWidth - var2;
      }

      if(var4 < 0) {
         var4 = 0;
      }

      int var5 = var1;
      if(var1 + var3 > WallDecoration.canvasHeight) {
         var5 = WallDecoration.canvasHeight - var3;
      }

      if(var5 < 0) {
         var5 = 0;
      }

      UrlRequester.menuX = var4;
      class37.menuY = var5;
      FriendSystem.menuWidth = var2;
      WorldMapDecoration.menuHeight = Client.menuOptionsCount * 15 + 22;
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      signature = "(ILcu;ZB)I",
      garbageValue = "126"
   )
   static int method3509(int var0, Script var1, boolean var2) {
      Widget var3;
      if(var0 == 2700) {
         var3 = Canvas.getWidget(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.itemId;
         return 1;
      } else if(var0 == 2701) {
         var3 = Canvas.getWidget(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
         if(var3.itemId != -1) {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.itemQuantity;
         } else {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
         }

         return 1;
      } else if(var0 == 2702) {
         int var5 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         InterfaceParent var4 = (InterfaceParent)Client.interfaceParents.method6346((long)var5);
         if(var4 != null) {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 1;
         } else {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
         }

         return 1;
      } else if(var0 == 2706) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.rootInterface;
         return 1;
      } else {
         return 2;
      }
   }
}
