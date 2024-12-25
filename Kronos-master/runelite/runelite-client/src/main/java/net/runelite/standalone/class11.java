package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("u")
public class class11 implements Enumerated {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lu;"
   )
   static final class11 field77;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lu;"
   )
   static final class11 field70;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lu;"
   )
   static final class11 field69;
   @ObfuscatedName("ax")
   static String field80;
   @ObfuscatedName("dk")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   static Archive archive5;
   @ObfuscatedName("p")
   final Class field73;
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Lz;"
   )
   public final class16 field75;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 689062233
   )
   public final int field71;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 1838246195
   )
   final int field74;

   static {
      field69 = new class11(1, 0, Integer.class, new class4());
      field77 = new class11(0, 1, Long.class, new class12());
      field70 = new class11(2, 2, String.class, new class8());
   }

   @ObfuscatedSignature(
      signature = "(IILjava/lang/Class;Lz;)V"
   )
   class11(int var1, int var2, Class var3, class16 var4) {
      this.field74 = var1;
      this.field71 = var2;
      this.field73 = var3;
      this.field75 = var4;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lkl;I)Ljava/lang/Object;",
      garbageValue = "-1513659890"
   )
   public Object method125(Buffer var1) {
      return this.field75.vmethod210(var1);
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(S)I",
      garbageValue = "221"
   )
   public int getId() {
      return this.field71;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/Class;B)Lu;",
      garbageValue = "-104"
   )
   public static class11 method123(Class var0) {
      class11[] var1 = method131();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         class11 var3 = var1[var2];
         if(var3.field73 == var0) {
            return var3;
         }
      }

      return null;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-2040711536"
   )
   static void method128() {
      WorldMapRegion.WorldMapRegion_cachedSprites.method3094();
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "([BB)Lcu;",
      garbageValue = "-44"
   )
   static Script method139(byte[] var0) {
      Script var1 = new Script();
      Buffer var2 = new Buffer(var0);
      var2.offset = var2.array.length - 2;
      int var3 = var2.readUnsignedShort();
      int var4 = var2.array.length - 2 - var3 - 12;
      var2.offset = var4;
      int var5 = var2.readInt();
      var1.localIntCount = var2.readUnsignedShort();
      var1.localStringCount = var2.readUnsignedShort();
      var1.intArgumentCount = var2.readUnsignedShort();
      var1.stringArgumentCount = var2.readUnsignedShort();
      int var6 = var2.readUnsignedByte();
      int var7;
      int var8;
      if(var6 > 0) {
         var1.switches = var1.method2200(var6);

         for(var7 = 0; var7 < var6; ++var7) {
            var8 = var2.readUnsignedShort();
            IterableNodeHashTable var9 = new IterableNodeHashTable(var8 > 0?Timer.method4847(var8):1);
            var1.switches[var7] = var9;

            while(var8-- > 0) {
               int var10 = var2.readInt();
               int var11 = var2.readInt();
               var9.put(new IntegerNode(var11), (long)var10);
            }
         }
      }

      var2.offset = 0;
      var2.method5504();
      var1.opcodes = new int[var5];
      var1.intOperands = new int[var5];
      var1.stringOperands = new String[var5];

      for(var7 = 0; var2.offset < var4; var1.opcodes[var7++] = var8) {
         var8 = var2.readUnsignedShort();
         if(var8 == 3) {
            var1.stringOperands[var7] = var2.readString();
         } else if(var8 < 100 && var8 != 21 && var8 != 38 && var8 != 39) {
            var1.intOperands[var7] = var2.readInt();
         } else {
            var1.intOperands[var7] = var2.readUnsignedByte();
         }
      }

      return var1;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(IIIII)V",
      garbageValue = "1231494139"
   )
   static void method141(int var0, int var1, int var2, int var3) {
      for(ObjectSound var4 = (ObjectSound)ObjectSound.objectSounds.method5103(); var4 != null; var4 = (ObjectSound)ObjectSound.objectSounds.method5126()) {
         if(var4.soundEffectId != -1 || var4.soundEffectIds != null) {
            int var5 = 0;
            if(var1 > var4.field413) {
               var5 += var1 - var4.field413;
            } else if(var1 < var4.x) {
               var5 += var4.x - var1;
            }

            if(var2 > var4.field414) {
               var5 += var2 - var4.field414;
            } else if(var2 < var4.y) {
               var5 += var4.y - var2;
            }

            if(var5 - 64 <= var4.field415 && Client.field1076 != 0 && var0 == var4.plane) {
               var5 -= 64;
               if(var5 < 0) {
                  var5 = 0;
               }

               int var6 = (var4.field415 - var5) * Client.field1076 / var4.field415;
               if(var4.stream1 == null) {
                  if(var4.soundEffectId >= 0) {
                     SoundEffect var7 = SoundEffect.method2092(GrandExchangeOfferAgeComparator.archive4, var4.soundEffectId, 0);
                     if(var7 != null) {
                        RawSound var8 = var7.method2086().method1579(Interpreter.decimator);
                        RawPcmStream var9 = RawPcmStream.method2601(var8, 100, var6);
                        var9.method2547(-1);
                        WorldMapLabelSize.pcmStreamMixer.method1475(var9);
                        var4.stream1 = var9;
                     }
                  }
               } else {
                  var4.stream1.method2548(var6);
               }

               if(var4.stream2 == null) {
                  if(var4.soundEffectIds != null && (var4.field421 -= var3) <= 0) {
                     int var11 = (int)(Math.random() * (double)var4.soundEffectIds.length);
//                     if (var4.soundEffectIds.length == 0 || var11 > var4.soundEffectIds.length)
//                        return;
//                     SoundEffect var12 = SoundEffect.method2092(GrandExchangeOfferAgeComparator.archive4, var4.soundEffectIds[var11], 0);
//                     if(var12 != null) {
//                        RawSound var13 = var12.method2086().method1579(Interpreter.decimator);
//                        RawPcmStream var10 = RawPcmStream.method2601(var13, 100, var6);
//                        var10.method2547(0);
//                        WorldMapLabelSize.pcmStreamMixer.method1475(var10);
//                        var4.stream2 = var10;
//                        var4.field421 = var4.field418 + (int)(Math.random() * (double)(var4.field424 - var4.field418));
//                     }
                  }
               } else {
                  var4.stream2.method2548(var6);
                  if(!var4.stream2.method3494()) {
                     var4.stream2 = null;
                  }
               }
            } else {
               if(var4.stream1 != null) {
                  WorldMapLabelSize.pcmStreamMixer.method1476(var4.stream1);
                  var4.stream1 = null;
               }

               if(var4.stream2 != null) {
                  WorldMapLabelSize.pcmStreamMixer.method1476(var4.stream2);
                  var4.stream2 = null;
               }
            }
         }
      }

   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)[Lu;",
      garbageValue = "-1521919568"
   )
   public static class11[] method131() {
      return new class11[]{field70, field77, field69};
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "([BIII)Ljava/lang/String;",
      garbageValue = "1159986112"
   )
   public static String method142(byte[] var0, int var1, int var2) {
      StringBuilder var3 = new StringBuilder();

      for(int var4 = var1; var4 < var2 + var1; var4 += 3) {
         int var5 = var0[var4] & 255;
         var3.append(class306.field3756[var5 >>> 2]);
         if(var4 < var2 - 1) {
            int var6 = var0[var4 + 1] & 255;
            var3.append(class306.field3756[(var5 & 3) << 4 | var6 >>> 4]);
            if(var4 < var2 - 2) {
               int var7 = var0[var4 + 2] & 255;
               var3.append(class306.field3756[(var6 & 15) << 2 | var7 >>> 6]).append(class306.field3756[var7 & 63]);
            } else {
               var3.append(class306.field3756[(var6 & 15) << 2]).append("=");
            }
         } else {
            var3.append(class306.field3756[(var5 & 3) << 4]).append("==");
         }
      }

      return var3.toString();
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-537744176"
   )
   static void method138() {
      Login.loginIndex = 24;
      PlayerType.method3939("The game servers are currently being updated.", "Please wait a few minutes and try again.", "");
   }

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      signature = "(IIIIIIILex;Lfa;S)V",
      garbageValue = "23648"
   )
   static final void method140(int var0, int var1, int var2, int var3, int var4, int var5, int var6, Scene var7, CollisionMap var8) {
      ObjectDefinition var9 = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(var4);
      int var10;
      int var11;
      if(var5 != 1 && var5 != 3) {
         var10 = var9.sizeX;
         var11 = var9.sizeY;
      } else {
         var10 = var9.sizeY;
         var11 = var9.sizeX;
      }

      int var12;
      int var13;
      if(var10 + var2 <= 104) {
         var12 = (var10 >> 1) + var2;
         var13 = var2 + (var10 + 1 >> 1);
      } else {
         var12 = var2;
         var13 = var2 + 1;
      }

      int var14;
      int var15;
      if(var3 + var11 <= 104) {
         var14 = var3 + (var11 >> 1);
         var15 = var3 + (var11 + 1 >> 1);
      } else {
         var14 = var3;
         var15 = var3 + 1;
      }

      int[][] var16 = Tiles.Tiles_heights[var1];
      int var17 = var16[var13][var15] + var16[var12][var14] + var16[var13][var14] + var16[var12][var15] >> 2;
      int var18 = (var2 << 7) + (var10 << 6);
      int var19 = (var3 << 7) + (var11 << 6);
      long var20 = class263.method4846(var2, var3, 2, var9.int1 == 0, var4);
      int var22 = (var5 << 6) + var6;
      if(var9.int3 == 1) {
         var22 += 256;
      }

      Object var23;
      if(var6 == 22) {
         if(var9.animationId == -1 && var9.transforms == null) {
            var23 = var9.method4722(22, var5, var16, var18, var17, var19);
         } else {
            var23 = new DynamicObject(var4, 22, var5, var1, var2, var3, var9.animationId, true, (Entity)null);
         }

         var7.method3235(var0, var2, var3, var17, (Entity)var23, var20, var22);
         if(var9.interactType == 1) {
            var8.method3346(var2, var3);
         }

      } else if(var6 != 10 && var6 != 11) {
         if(var6 >= 12) {
            if(var9.animationId == -1 && var9.transforms == null) {
               var23 = var9.method4722(var6, var5, var16, var18, var17, var19);
            } else {
               var23 = new DynamicObject(var4, var6, var5, var1, var2, var3, var9.animationId, true, (Entity)null);
            }

            var7.method3124(var0, var2, var3, var17, 1, 1, (Entity)var23, 0, var20, var22);
            if(var9.interactType != 0) {
               var8.method3334(var2, var3, var10, var11, var9.boolean1);
            }

         } else if(var6 == 0) {
            if(var9.animationId == -1 && var9.transforms == null) {
               var23 = var9.method4722(0, var5, var16, var18, var17, var19);
            } else {
               var23 = new DynamicObject(var4, 0, var5, var1, var2, var3, var9.animationId, true, (Entity)null);
            }

            var7.method3116(var0, var2, var3, var17, (Entity)var23, (Entity)null, Tiles.field528[var5], 0, var20, var22);
            if(var9.interactType != 0) {
               var8.method3332(var2, var3, var6, var5, var9.boolean1);
            }

         } else if(var6 == 1) {
            if(var9.animationId == -1 && var9.transforms == null) {
               var23 = var9.method4722(1, var5, var16, var18, var17, var19);
            } else {
               var23 = new DynamicObject(var4, 1, var5, var1, var2, var3, var9.animationId, true, (Entity)null);
            }

            var7.method3116(var0, var2, var3, var17, (Entity)var23, (Entity)null, Tiles.field532[var5], 0, var20, var22);
            if(var9.interactType != 0) {
               var8.method3332(var2, var3, var6, var5, var9.boolean1);
            }

         } else {
            int var29;
            if(var6 == 2) {
               var29 = var5 + 1 & 3;
               Object var24;
               Object var25;
               if(var9.animationId == -1 && var9.transforms == null) {
                  var24 = var9.method4722(2, var5 + 4, var16, var18, var17, var19);
                  var25 = var9.method4722(2, var29, var16, var18, var17, var19);
               } else {
                  var24 = new DynamicObject(var4, 2, var5 + 4, var1, var2, var3, var9.animationId, true, (Entity)null);
                  var25 = new DynamicObject(var4, 2, var29, var1, var2, var3, var9.animationId, true, (Entity)null);
               }

               var7.method3116(var0, var2, var3, var17, (Entity)var24, (Entity)var25, Tiles.field528[var5], Tiles.field528[var29], var20, var22);
               if(var9.interactType != 0) {
                  var8.method3332(var2, var3, var6, var5, var9.boolean1);
               }

            } else if(var6 == 3) {
               if(var9.animationId == -1 && var9.transforms == null) {
                  var23 = var9.method4722(3, var5, var16, var18, var17, var19);
               } else {
                  var23 = new DynamicObject(var4, 3, var5, var1, var2, var3, var9.animationId, true, (Entity)null);
               }

               var7.method3116(var0, var2, var3, var17, (Entity)var23, (Entity)null, Tiles.field532[var5], 0, var20, var22);
               if(var9.interactType != 0) {
                  var8.method3332(var2, var3, var6, var5, var9.boolean1);
               }

            } else if(var6 == 9) {
               if(var9.animationId == -1 && var9.transforms == null) {
                  var23 = var9.method4722(var6, var5, var16, var18, var17, var19);
               } else {
                  var23 = new DynamicObject(var4, var6, var5, var1, var2, var3, var9.animationId, true, (Entity)null);
               }

               var7.method3124(var0, var2, var3, var17, 1, 1, (Entity)var23, 0, var20, var22);
               if(var9.interactType != 0) {
                  var8.method3334(var2, var3, var10, var11, var9.boolean1);
               }

            } else if(var6 == 4) {
               if(var9.animationId == -1 && var9.transforms == null) {
                  var23 = var9.method4722(4, var5, var16, var18, var17, var19);
               } else {
                  var23 = new DynamicObject(var4, 4, var5, var1, var2, var3, var9.animationId, true, (Entity)null);
               }

               var7.method3131(var0, var2, var3, var17, (Entity)var23, (Entity)null, Tiles.field528[var5], 0, 0, 0, var20, var22);
            } else {
               Object var26;
               long var30;
               if(var6 == 5) {
                  var29 = 16;
                  var30 = var7.method3140(var0, var2, var3);
                  if(var30 != 0L) {
                     var29 = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(class86.method2114(var30)).int2;
                  }

                  if(var9.animationId == -1 && var9.transforms == null) {
                     var26 = var9.method4722(4, var5, var16, var18, var17, var19);
                  } else {
                     var26 = new DynamicObject(var4, 4, var5, var1, var2, var3, var9.animationId, true, (Entity)null);
                  }

                  var7.method3131(var0, var2, var3, var17, (Entity)var26, (Entity)null, Tiles.field528[var5], 0, var29 * Tiles.field530[var5], var29 * Tiles.field531[var5], var20, var22);
               } else if(var6 == 6) {
                  var29 = 8;
                  var30 = var7.method3140(var0, var2, var3);
                  if(var30 != 0L) {
                     var29 = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(class86.method2114(var30)).int2 / 2;
                  }

                  if(var9.animationId == -1 && var9.transforms == null) {
                     var26 = var9.method4722(4, var5 + 4, var16, var18, var17, var19);
                  } else {
                     var26 = new DynamicObject(var4, 4, var5 + 4, var1, var2, var3, var9.animationId, true, (Entity)null);
                  }

                  var7.method3131(var0, var2, var3, var17, (Entity)var26, (Entity)null, 256, var5, var29 * Tiles.field523[var5], var29 * Tiles.field533[var5], var20, var22);
               } else if(var6 == 7) {
                  int var32 = var5 + 2 & 3;
                  if(var9.animationId == -1 && var9.transforms == null) {
                     var23 = var9.method4722(4, var32 + 4, var16, var18, var17, var19);
                  } else {
                     var23 = new DynamicObject(var4, 4, var32 + 4, var1, var2, var3, var9.animationId, true, (Entity)null);
                  }

                  var7.method3131(var0, var2, var3, var17, (Entity)var23, (Entity)null, 256, var32, 0, 0, var20, var22);
               } else if(var6 == 8) {
                  var29 = 8;
                  var30 = var7.method3140(var0, var2, var3);
                  if(var30 != 0L) {
                     var29 = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(class86.method2114(var30)).int2 / 2;
                  }

                  int var28 = var5 + 2 & 3;
                  Object var27;
                  if(var9.animationId == -1 && var9.transforms == null) {
                     var26 = var9.method4722(4, var5 + 4, var16, var18, var17, var19);
                     var27 = var9.method4722(4, var28 + 4, var16, var18, var17, var19);
                  } else {
                     var26 = new DynamicObject(var4, 4, var5 + 4, var1, var2, var3, var9.animationId, true, (Entity)null);
                     var27 = new DynamicObject(var4, 4, var28 + 4, var1, var2, var3, var9.animationId, true, (Entity)null);
                  }

                  var7.method3131(var0, var2, var3, var17, (Entity)var26, (Entity)var27, 256, var5, var29 * Tiles.field523[var5], var29 * Tiles.field533[var5], var20, var22);
               }
            }
         }
      } else {
         if(var9.animationId == -1 && var9.transforms == null) {
            var23 = var9.method4722(10, var5, var16, var18, var17, var19);
         } else {
            var23 = new DynamicObject(var4, 10, var5, var1, var2, var3, var9.animationId, true, (Entity)null);
         }

         if(var23 != null) {
            var7.method3124(var0, var2, var3, var17, var10, var11, (Entity)var23, var6 == 11?256:0, var20, var22);
         }

         if(var9.interactType != 0) {
            var8.method3334(var2, var3, var10, var11, var9.boolean1);
         }

      }
   }

   @ObfuscatedName("ji")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "34567579"
   )
   static void method137(int var0, int var1) {
      MenuAction var2 = ParamDefinition.tempMenuAction;
      InvDefinition.method4339(var2.argument1, var2.argument2, var2.opcode, var2.argument0, var2.action, var2.action, var0, var1, 1826014571);
      ParamDefinition.tempMenuAction = null;
      Client.onTempMenuActionChanged(-1);
   }
}
