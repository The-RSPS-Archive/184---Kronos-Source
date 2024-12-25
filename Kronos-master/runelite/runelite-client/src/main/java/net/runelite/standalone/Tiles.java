package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bm")
public final class Tiles {
   @ObfuscatedName("rv")
   @ObfuscatedSignature(
      signature = "Lbf;"
   )
   public static FriendSystem friendSystem;
   @ObfuscatedName("sv")
   @ObfuscatedSignature(
      signature = "Lla;"
   )
   static WorldMap worldMap;
   @ObfuscatedName("n")
   static byte[][][] Tiles_renderFlags;
   @ObfuscatedName("r")
   static byte[][][] field522;
   @ObfuscatedName("s")
   static final int[] field530;
   @ObfuscatedName("u")
   static byte[][][] field540;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 380288965
   )
   static int Tiles_minPlane;
   @ObfuscatedName("x")
   static final int[] field528;
   @ObfuscatedName("z")
   static int[][][] Tiles_heights;
   @ObfuscatedName("a")
   static int[] field526;
   @ObfuscatedName("b")
   static int[] Tiles_lightness;
   @ObfuscatedName("d")
   static final int[] field533;
   @ObfuscatedName("e")
   static int[][][] field527;
   @ObfuscatedName("f")
   static final int[] field531;
   @ObfuscatedName("fy")
   @ObfuscatedGetter(
      longValue = 5157755524987291675L
   )
   static long field534;
   @ObfuscatedName("h")
   static final int[] field532;
   @ObfuscatedName("i")
   static int[] Tiles_hue;
   @ObfuscatedName("j")
   static final int[] field523;
   @ObfuscatedName("k")
   @ObfuscatedGetter(
      intValue = 1017991539
   )
   static int field535;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = 832386391
   )
   static int field521;
   @ObfuscatedName("m")
   static byte[][][] field525;

   static {
      Tiles_heights = new int[4][105][105];
      Tiles_renderFlags = new byte[4][104][104];
      Tiles_minPlane = 99;
      field528 = new int[]{1, 2, 4, 8};
      field532 = new int[]{16, 32, 64, 128};
      field530 = new int[]{1, 0, -1, 0};
      field531 = new int[]{0, -1, 0, 1};
      field523 = new int[]{1, -1, -1, 1};
      field533 = new int[]{-1, -1, 1, 1};
      field521 = (int)(Math.random() * 17.0D) - 8;
      field535 = (int)(Math.random() * 33.0D) - 16;
   }

   @ObfuscatedName("ao")
   @ObfuscatedSignature(
      signature = "(ILcu;ZB)I",
      garbageValue = "59"
   )
   static int method1201(int var0, Script var1, boolean var2) {
      if(var0 == 5000) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.publicChatMode;
         return 1;
      } else if(var0 == 5001) {
         Interpreter.Interpreter_intStackSize -= 3;
         Client.publicChatMode = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         class213.privateChatMode = Fonts.method5757(Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]);
         if(class213.privateChatMode == null) {
            class213.privateChatMode = PrivateChatMode.field3632;
         }

         Client.tradeChatMode = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 2];
         PacketBufferNode var18 = InterfaceParent.method1140(ClientPacket.field2389, Client.packetWriter.isaacCipher);
         var18.packetBuffer.writeByte(Client.publicChatMode);
         var18.packetBuffer.writeByte(class213.privateChatMode.field3634);
         var18.packetBuffer.writeByte(Client.tradeChatMode);
         Client.packetWriter.method1622(var18);
         return 1;
      } else {
         String var3;
         int var4;
         if(var0 == 5002) {
            var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
            Interpreter.Interpreter_intStackSize -= 2;
            var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
            int var5 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
            PacketBufferNode var6 = InterfaceParent.method1140(ClientPacket.field2432, Client.packetWriter.isaacCipher);
            var6.packetBuffer.writeByte(class267.method4877(var3) + 2);
            var6.packetBuffer.writeString(var3);
            var6.packetBuffer.writeByte(var4 - 1);
            var6.packetBuffer.writeByte(var5);
            Client.packetWriter.method1622(var6);
            return 1;
         } else {
            int var10;
            if(var0 == 5003) {
               Interpreter.Interpreter_intStackSize -= 2;
               var10 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
               var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
               Message var15 = HealthBarUpdate.method1263(var10, var4);
               if(var15 != null) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var15.count;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var15.cycle;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var15.sender != null?var15.sender:"";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var15.prefix != null?var15.prefix:"";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var15.text != null?var15.text:"";
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var15.method865()?1:(var15.method866()?2:0);
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if(var0 == 5004) {
               var10 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               Message var16 = GrandExchangeOfferOwnWorldComparator.method823(var10);
               if(var16 != null) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var16.type;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var16.cycle;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var16.sender != null?var16.sender:"";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var16.prefix != null?var16.prefix:"";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var16.text != null?var16.text:"";
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var16.method865()?1:(var16.method866()?2:0);
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if(var0 == 5005) {
               if(class213.privateChatMode == null) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = class213.privateChatMode.field3634;
               }

               return 1;
            } else if(var0 == 5008) {
               var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               var4 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               String var14 = var3.toLowerCase();
               byte var17 = 0;
               if(var14.startsWith("yellow:")) {
                  var17 = 0;
                  var3 = var3.substring("yellow:".length());
               } else if(var14.startsWith("red:")) {
                  var17 = 1;
                  var3 = var3.substring("red:".length());
               } else if(var14.startsWith("green:")) {
                  var17 = 2;
                  var3 = var3.substring("green:".length());
               } else if(var14.startsWith("cyan:")) {
                  var17 = 3;
                  var3 = var3.substring("cyan:".length());
               } else if(var14.startsWith("purple:")) {
                  var17 = 4;
                  var3 = var3.substring("purple:".length());
               } else if(var14.startsWith("white:")) {
                  var17 = 5;
                  var3 = var3.substring("white:".length());
               } else if(var14.startsWith("flash1:")) {
                  var17 = 6;
                  var3 = var3.substring("flash1:".length());
               } else if(var14.startsWith("flash2:")) {
                  var17 = 7;
                  var3 = var3.substring("flash2:".length());
               } else if(var14.startsWith("flash3:")) {
                  var17 = 8;
                  var3 = var3.substring("flash3:".length());
               } else if(var14.startsWith("glow1:")) {
                  var17 = 9;
                  var3 = var3.substring("glow1:".length());
               } else if(var14.startsWith("glow2:")) {
                  var17 = 10;
                  var3 = var3.substring("glow2:".length());
               } else if(var14.startsWith("glow3:")) {
                  var17 = 11;
                  var3 = var3.substring("glow3:".length());
               } else if(WorldMapLabelSize.clientLanguage != Language.Language_EN) {
                  if(var14.startsWith("yellow:")) {
                     var17 = 0;
                     var3 = var3.substring("yellow:".length());
                  } else if(var14.startsWith("red:")) {
                     var17 = 1;
                     var3 = var3.substring("red:".length());
                  } else if(var14.startsWith("green:")) {
                     var17 = 2;
                     var3 = var3.substring("green:".length());
                  } else if(var14.startsWith("cyan:")) {
                     var17 = 3;
                     var3 = var3.substring("cyan:".length());
                  } else if(var14.startsWith("purple:")) {
                     var17 = 4;
                     var3 = var3.substring("purple:".length());
                  } else if(var14.startsWith("white:")) {
                     var17 = 5;
                     var3 = var3.substring("white:".length());
                  } else if(var14.startsWith("flash1:")) {
                     var17 = 6;
                     var3 = var3.substring("flash1:".length());
                  } else if(var14.startsWith("flash2:")) {
                     var17 = 7;
                     var3 = var3.substring("flash2:".length());
                  } else if(var14.startsWith("flash3:")) {
                     var17 = 8;
                     var3 = var3.substring("flash3:".length());
                  } else if(var14.startsWith("glow1:")) {
                     var17 = 9;
                     var3 = var3.substring("glow1:".length());
                  } else if(var14.startsWith("glow2:")) {
                     var17 = 10;
                     var3 = var3.substring("glow2:".length());
                  } else if(var14.startsWith("glow3:")) {
                     var17 = 11;
                     var3 = var3.substring("glow3:".length());
                  }
               }

               var14 = var3.toLowerCase();
               byte var7 = 0;
               if(var14.startsWith("wave:")) {
                  var7 = 1;
                  var3 = var3.substring("wave:".length());
               } else if(var14.startsWith("wave2:")) {
                  var7 = 2;
                  var3 = var3.substring("wave2:".length());
               } else if(var14.startsWith("shake:")) {
                  var7 = 3;
                  var3 = var3.substring("shake:".length());
               } else if(var14.startsWith("scroll:")) {
                  var7 = 4;
                  var3 = var3.substring("scroll:".length());
               } else if(var14.startsWith("slide:")) {
                  var7 = 5;
                  var3 = var3.substring("slide:".length());
               } else if(Language.Language_EN != WorldMapLabelSize.clientLanguage) {
                  if(var14.startsWith("wave:")) {
                     var7 = 1;
                     var3 = var3.substring("wave:".length());
                  } else if(var14.startsWith("wave2:")) {
                     var7 = 2;
                     var3 = var3.substring("wave2:".length());
                  } else if(var14.startsWith("shake:")) {
                     var7 = 3;
                     var3 = var3.substring("shake:".length());
                  } else if(var14.startsWith("scroll:")) {
                     var7 = 4;
                     var3 = var3.substring("scroll:".length());
                  } else if(var14.startsWith("slide:")) {
                     var7 = 5;
                     var3 = var3.substring("slide:".length());
                  }
               }

               PacketBufferNode var8 = InterfaceParent.method1140(ClientPacket.field2372, Client.packetWriter.isaacCipher);
               var8.packetBuffer.writeByte(0);
               int var9 = var8.packetBuffer.offset;
               var8.packetBuffer.writeByte(var4);
               var8.packetBuffer.writeByte(var17);
               var8.packetBuffer.writeByte(var7);
               ArchiveDisk.method5307(var8.packetBuffer, var3);
               var8.packetBuffer.method5493(var8.packetBuffer.offset - var9);
               Client.packetWriter.method1622(var8);
               return 1;
            } else if(var0 == 5009) {
               Interpreter.Interpreter_stringStackSize -= 2;
               var3 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize];
               String var12 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize + 1];
               PacketBufferNode var11 = InterfaceParent.method1140(ClientPacket.field2349, Client.packetWriter.isaacCipher);
               var11.packetBuffer.method5481(0);
               int var13 = var11.packetBuffer.offset;
               var11.packetBuffer.writeString(var3);
               ArchiveDisk.method5307(var11.packetBuffer, var12);
               var11.packetBuffer.method5492(var11.packetBuffer.offset - var13);
               Client.packetWriter.method1622(var11);
               return 1;
            } else if(var0 != 5015) {
               if(var0 == 5016) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.tradeChatMode;
                  return 1;
               } else if(var0 == 5017) {
                  var10 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Projectile.method2238(var10);
                  return 1;
               } else if(var0 == 5018) {
                  var10 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = class17.method216(var10);
                  return 1;
               } else if(var0 == 5019) {
                  var10 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Clock.method3519(var10);
                  return 1;
               } else if(var0 == 5020) {
                  var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                  WorldMapSection0.method3906(var3);
                  return 1;
               } else if(var0 == 5021) {
                  Client.field1115 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize].toLowerCase().trim();
                  return 1;
               } else if(var0 == 5022) {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = Client.field1115;
                  return 1;
               } else if(var0 == 5023) {
                  var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                  System.out.println(var3);
                  return 1;
               } else {
                  return 2;
               }
            } else {
               if(class215.localPlayer != null && class215.localPlayer.username != null) {
                  var3 = class215.localPlayer.username.method5001();
               } else {
                  var3 = "";
               }

               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3;
               return 1;
            }
         }
      }
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(ILcu;ZB)I",
      garbageValue = "-18"
   )
   static int method1155(int var0, Script var1, boolean var2) {
      Widget var3;
      if(var0 >= 2000) {
         var0 -= 1000;
         var3 = Canvas.getWidget(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
      } else {
         var3 = var2?GrandExchangeOfferAgeComparator.field26:KitDefinition.field3452;
      }

      String var4 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
      int[] var5 = null;
      if(var4.length() > 0 && var4.charAt(var4.length() - 1) == 'Y') {
         int var6 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         if(var6 > 0) {
            for(var5 = new int[var6]; var6-- > 0; var5[var6] = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]) {
               ;
            }
         }

         var4 = var4.substring(0, var4.length() - 1);
      }

      Object[] var8 = new Object[var4.length() + 1];

      int var7;
      for(var7 = var8.length - 1; var7 >= 1; --var7) {
         if(var4.charAt(var7 - 1) == 's') {
            var8[var7] = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
         } else {
            var8[var7] = new Integer(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize]);
         }
      }

      var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
      if(var7 != -1) {
         var8[0] = new Integer(var7);
      } else {
         var8 = null;
      }

      if(var0 == 1400) {
         var3.onClick = var8;
      } else if(var0 == 1401) {
         var3.onHold = var8;
      } else if(var0 == 1402) {
         var3.onRelease = var8;
      } else if(var0 == 1403) {
         var3.onMouseOver = var8;
      } else if(var0 == 1404) {
         var3.onMouseLeave = var8;
      } else if(var0 == 1405) {
         var3.onDrag = var8;
      } else if(var0 == 1406) {
         var3.onTargetLeave = var8;
      } else if(var0 == 1407) {
         var3.onVarTransmit = var8;
         var3.varTransmitTriggers = var5;
      } else if(var0 == 1408) {
         var3.onTimer = var8;
      } else if(var0 == 1409) {
         var3.onOp = var8;
      } else if(var0 == 1410) {
         var3.onDragComplete = var8;
      } else if(var0 == 1411) {
         var3.onClickRepeat = var8;
      } else if(var0 == 1412) {
         var3.onMouseRepeat = var8;
      } else if(var0 == 1414) {
         var3.onInvTransmit = var8;
         var3.invTransmitTriggers = var5;
      } else if(var0 == 1415) {
         var3.onStatTransmit = var8;
         var3.statTransmitTriggers = var5;
      } else if(var0 == 1416) {
         var3.onTargetEnter = var8;
      } else if(var0 == 1417) {
         var3.onScroll = var8;
      } else if(var0 == 1418) {
         var3.onChatTransmit = var8;
      } else if(var0 == 1419) {
         var3.onKey = var8;
      } else if(var0 == 1420) {
         var3.onFriendTransmit = var8;
      } else if(var0 == 1421) {
         var3.onClanTransmit = var8;
      } else if(var0 == 1422) {
         var3.onMiscTransmit = var8;
      } else if(var0 == 1423) {
         var3.onDialogAbort = var8;
      } else if(var0 == 1424) {
         var3.onSubChange = var8;
      } else if(var0 == 1425) {
         var3.onStockTransmit = var8;
      } else if(var0 == 1426) {
         var3.field2695 = var8;
      } else {
         if(var0 != 1427) {
            return 2;
         }

         var3.onResize = var8;
      }

      var3.hasListener = true;
      return 1;
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "([BB)V",
      garbageValue = "36"
   )
   public static void method1200(byte[] var0) {
      Buffer var1 = new Buffer(var0);
      var1.offset = var0.length - 2;
      class329.SpriteBuffer_spriteCount = var1.readUnsignedShort();
      class329.SpriteBuffer_xOffsets = new int[class329.SpriteBuffer_spriteCount];
      MusicPatchPcmStream.SpriteBuffer_yOffsets = new int[class329.SpriteBuffer_spriteCount];
      class329.SpriteBuffer_spriteWidths = new int[class329.SpriteBuffer_spriteCount];
      RunException.SpriteBuffer_spriteHeights = new int[class329.SpriteBuffer_spriteCount];
      PacketBufferNode.SpriteBuffer_pixels = new byte[class329.SpriteBuffer_spriteCount][];
      var1.offset = var0.length - 7 - class329.SpriteBuffer_spriteCount * 8;
      class329.SpriteBuffer_spriteWidth = var1.readUnsignedShort();
      Frames.SpriteBuffer_spriteHeight = var1.readUnsignedShort();
      int var2 = (var1.readUnsignedByte() & 255) + 1;

      int var3;
      for(var3 = 0; var3 < class329.SpriteBuffer_spriteCount; ++var3) {
         class329.SpriteBuffer_xOffsets[var3] = var1.readUnsignedShort();
      }

      for(var3 = 0; var3 < class329.SpriteBuffer_spriteCount; ++var3) {
         MusicPatchPcmStream.SpriteBuffer_yOffsets[var3] = var1.readUnsignedShort();
      }

      for(var3 = 0; var3 < class329.SpriteBuffer_spriteCount; ++var3) {
         class329.SpriteBuffer_spriteWidths[var3] = var1.readUnsignedShort();
      }

      for(var3 = 0; var3 < class329.SpriteBuffer_spriteCount; ++var3) {
         RunException.SpriteBuffer_spriteHeights[var3] = var1.readUnsignedShort();
      }

      var1.offset = var0.length - 7 - class329.SpriteBuffer_spriteCount * 8 - (var2 - 1) * 3;
      class329.SpriteBuffer_spritePalette = new int[var2];

      for(var3 = 1; var3 < var2; ++var3) {
         class329.SpriteBuffer_spritePalette[var3] = var1.method5500();
         if(class329.SpriteBuffer_spritePalette[var3] == 0) {
            class329.SpriteBuffer_spritePalette[var3] = 1;
         }
      }

      var1.offset = 0;

      for(var3 = 0; var3 < class329.SpriteBuffer_spriteCount; ++var3) {
         int var4 = class329.SpriteBuffer_spriteWidths[var3];
         int var5 = RunException.SpriteBuffer_spriteHeights[var3];
         int var6 = var4 * var5;
         byte[] var7 = new byte[var6];
         PacketBufferNode.SpriteBuffer_pixels[var3] = var7;
         int var8 = var1.readUnsignedByte();
         int var9;
         if(var8 == 0) {
            for(var9 = 0; var9 < var6; ++var9) {
               var7[var9] = var1.readByte();
            }
         } else if(var8 == 1) {
            for(var9 = 0; var9 < var4; ++var9) {
               for(int var10 = 0; var10 < var5; ++var10) {
                  var7[var9 + var4 * var10] = var1.readByte();
               }
            }
         }
      }

   }
}
