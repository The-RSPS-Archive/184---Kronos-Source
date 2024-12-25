package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSInterfaceParent;

@ObfuscatedName("bk")
public class InterfaceParent extends Node implements RSInterfaceParent {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -1707637965
   )
   int type;
   @ObfuscatedName("v")
   boolean field499;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -2031871231
   )
   int group;

   InterfaceParent() {
      this.field499 = false;
   }

   public int getId() {
      return this.group;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "4"
   )
   public static void method1137(int var0) {
      if(var0 != -1) {
         if(ViewportMouse.Widget_loadedInterfaces[var0]) {
            Widget.Widget_archive.method4094(var0);
            if(UserComparator5.Widget_interfaceComponents[var0] != null) {
               boolean var1 = true;

               for(int var2 = 0; var2 < UserComparator5.Widget_interfaceComponents[var0].length; ++var2) {
                  if(UserComparator5.Widget_interfaceComponents[var0][var2] != null) {
                     if(UserComparator5.Widget_interfaceComponents[var0][var2].type != 2) {
                        UserComparator5.Widget_interfaceComponents[var0][var2] = null;
                     } else {
                        var1 = false;
                     }
                  }
               }

               if(var1) {
                  UserComparator5.Widget_interfaceComponents[var0] = null;
               }

               ViewportMouse.Widget_loadedInterfaces[var0] = false;
            }
         }
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1288717828"
   )
   static void method1142() {
      if(Login.Login_username == null || Login.Login_username.length() <= 0) {
         if(AbstractArchive.clientPreferences.rememberedUsername != null) {
            Login.Login_username = AbstractArchive.clientPreferences.rememberedUsername;
            Client.onUsernameChanged(-1);
            Client.Login_isUsernameRemembered = true;
         } else {
            Client.Login_isUsernameRemembered = false;
         }

      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(II)Lik;",
      garbageValue = "2057171029"
   )
   public static SpotAnimationDefinition method1139(int var0) {
      SpotAnimationDefinition var1 = (SpotAnimationDefinition)SpotAnimationDefinition.SpotAnimationDefinition_cached.method3032((long)var0);
      if(var1 != null) {
         return var1;
      } else {
         byte[] var2 = class125.SpotAnimationDefinition_archive.method4020(13, var0, (short)1520);
         var1 = new SpotAnimationDefinition();
         var1.id = var0;
         if(var2 != null) {
            var1.method4390(new Buffer(var2));
         }

         SpotAnimationDefinition.SpotAnimationDefinition_cached.method3034(var1, (long)var0);
         return var1;
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lgy;Llg;I)Lgn;",
      garbageValue = "1636799714"
   )
   public static PacketBufferNode method1140(ClientPacket var0, IsaacCipher var1) {
      PacketBufferNode var2;
      if(PacketBufferNode.PacketBufferNode_packetBufferNodeCount == 0) {
         var2 = new PacketBufferNode();
      } else {
         var2 = PacketBufferNode.PacketBufferNode_packetBufferNodes[--PacketBufferNode.PacketBufferNode_packetBufferNodeCount];
      }

      var2.clientPacket = var0;
      var2.clientPacketLength = var0.length;
      if(var2.clientPacketLength == -1) {
         var2.packetBuffer = new PacketBuffer(260);
      } else if(var2.clientPacketLength == -2) {
         var2.packetBuffer = new PacketBuffer(10000);
      } else if(var2.clientPacketLength <= 18) {
         var2.packetBuffer = new PacketBuffer(20);
      } else if(var2.clientPacketLength <= 98) {
         var2.packetBuffer = new PacketBuffer(100);
      } else {
         var2.packetBuffer = new PacketBuffer(260);
      }

      var2.packetBuffer.method5303(var1);
      var2.packetBuffer.method5275(var2.clientPacket.id);
      var2.index = 0;
      return var2;
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "2103044150"
   )
   public static void method1138() {
      ItemDefinition.ItemDefinition_cached.method3035();
      ItemDefinition.ItemDefinition_cachedModels.method3035();
      ItemDefinition.ItemDefinition_cachedSprites.method3035();
   }

   @ObfuscatedName("hk")
   @ObfuscatedSignature(
      signature = "(ZLkf;B)V",
      garbageValue = "-96"
   )
   static final void method1141(boolean var0, PacketBuffer var1) {
      Client.updateNpcs(var0, var1);
      Client.field848 = 0;
      Client.field841 = 0;
      Decimator.method2488();

      int var2;
      NPC var4;
      int var5;
      int var6;
      int var7;
      int var8;
      int var9;
      NPCDefinition var10001;
      while(var1.method5283(Client.packetWriter.serverPacketLength) >= 27) {
         var2 = var1.method5281(15);
         if(var2 == 32767) {
            break;
         }

         boolean var3 = false;
         if(Client.npcs[var2] == null) {
            Client.npcs[var2] = new NPC();
            Client.cachedNPCsChanged(var2);
            var3 = true;
         }

         var4 = Client.npcs[var2];
         Client.npcIndices[++Client.npcCount - 1] = var2;
         var4.npcCycle = Client.cycle;
         if(var0) {
            var5 = var1.method5281(8);
            if(var5 > 127) {
               var5 -= 256;
            }
         } else {
            var5 = var1.method5281(5);
            if(var5 > 15) {
               var5 -= 32;
            }
         }

         if(var0) {
            var6 = var1.method5281(8);
            if(var6 > 127) {
               var6 -= 256;
            }
         } else {
            var6 = var1.method5281(5);
            if(var6 > 15) {
               var6 -= 32;
            }
         }

         var7 = Client.defaultRotations[var1.method5281(3)];
         if(var3) {
            var4.orientation = var4.rotation = var7;
         }

         var8 = var1.method5281(1);
         if(var8 == 1) {
            Client.field889[++Client.field841 - 1] = var2;
         }

         var9 = var1.method5281(1);
         var10001 = PacketBufferNode.getNpcDefinition(var1.method5281(14));
         var4.onDefinitionChanged(var10001);
         var4.definition = var10001;
         var4.size = var4.definition.size;
         var4.field720 = var4.definition.rotation;
         if(var4.field720 == 0) {
            var4.rotation = 0;
         }

         var4.walkSequence = var4.definition.walkingAnimation;
         var4.walkBackSequence = var4.definition.walkBackSequence;
         var4.walkLeftSequence = var4.definition.walkLeftSequence;
         var4.walkRightSequence = var4.definition.walkRightSequence;
         var4.readySequence = var4.definition.standingAnimation;
         var4.turnLeftSequence = var4.definition.turnLeftSequence;
         var4.turnRightSequence = var4.definition.turnRightSequence;
         var4.method1609(class215.localPlayer.pathX[0] + var5, class215.localPlayer.pathY[0] + var6, var9 == 1);
      }

      var1.method5279();

      int var14;
      for(var2 = 0; var2 < Client.field841; ++var2) {
         var14 = Client.field889[var2];
         var4 = Client.npcs[var14];
         var5 = var1.readUnsignedByte();
         if((var5 & 16) != 0) {
            var6 = var1.readUnsignedShort();
            if(var6 == 65535) {
               var6 = -1;
            }

            var7 = var1.method5565();
            if(var6 == var4.sequence && var6 != -1) {
               var8 = GrandExchangeOfferUnitPriceComparator.method1468(var6).field3438;
               if(var8 == 1) {
                  var4.sequenceFrame = 0;
                  var4.sequenceFrameCycle = 0;
                  var4.sequenceDelay = var7;
                  var4.field703 = 0;
               }

               if(var8 == 2) {
                  var4.field703 = 0;
               }
            } else if(var6 == -1 || var4.sequence == -1 || GrandExchangeOfferUnitPriceComparator.method1468(var6).field3432 >= GrandExchangeOfferUnitPriceComparator.method1468(var4.sequence).field3432) {
               var4.sequence = var6;
               var4.animationChanged(-1);
               var4.sequenceFrame = 0;
               var4.sequenceFrameCycle = 0;
               var4.sequenceDelay = var7;
               var4.field703 = 0;
               var4.field726 = var4.pathLength;
            }
         }

         if((var5 & 1) != 0) {
            var6 = var1.method5525();
            int var10;
            int var11;
            int var12;
            if(var6 > 0) {
               for(var7 = 0; var7 < var6; ++var7) {
                  var9 = -1;
                  var10 = -1;
                  var11 = -1;
                  var8 = var1.readUnsignedSmart();
                  if(var8 == 32767) {
                     var8 = var1.readUnsignedSmart();
                     var10 = var1.readUnsignedSmart();
                     var9 = var1.readUnsignedSmart();
                     var11 = var1.readUnsignedSmart();
                  } else if(var8 != 32766) {
                     var10 = var1.readUnsignedSmart();
                  } else {
                     var8 = -1;
                  }

                  var12 = var1.readUnsignedSmart();
                  var4.method1434(var8, var10, var9, var11, Client.cycle, var12);
               }
            }

            var7 = var1.method5565();
            if(var7 > 0) {
               for(var8 = 0; var8 < var7; ++var8) {
                  var9 = var1.readUnsignedSmart();
                  var10 = var1.readUnsignedSmart();
                  if(var10 != 32767) {
                     var11 = var1.readUnsignedSmart();
                     var12 = var1.method5525();
                     int var13 = var10 > 0?var1.method5525():var12;
                     var4.method1429(var9, Client.cycle, var10, var11, var12, var13);
                  } else {
                     var4.method1430(var9);
                  }
               }
            }
         }

         if((var5 & 2) != 0) {
            var4.targetIndex = var1.readShortA();
            var4.interactingChanged(-1);
            if(var4.targetIndex == 65535) {
               var4.targetIndex = -1;
               var4.interactingChanged(-1);
            }
         }

         if((var5 & 32) != 0) {
            var10001 = PacketBufferNode.getNpcDefinition(var1.readLEShortA());
            var4.onDefinitionChanged(var10001);
            var4.definition = var10001;
            var4.size = var4.definition.size;
            var4.field720 = var4.definition.rotation;
            var4.walkSequence = var4.definition.walkingAnimation;
            var4.walkBackSequence = var4.definition.walkBackSequence;
            var4.walkLeftSequence = var4.definition.walkLeftSequence;
            var4.walkRightSequence = var4.definition.walkRightSequence;
            var4.readySequence = var4.definition.standingAnimation;
            var4.turnLeftSequence = var4.definition.turnLeftSequence;
            var4.turnRightSequence = var4.definition.turnRightSequence;
         }

         if((var5 & 4) != 0) {
            var6 = var1.readShortA();
            var7 = var1.readLEShortA();
            var8 = var4.x - (var6 - class215.baseX - class215.baseX) * 64;
            var9 = var4.y * 682054857 - (var7 - class304.baseY - class304.baseY) * 64;
            if(var8 != 0 || var9 != 0) {
               var4.field695 = (int)(Math.atan2((double)var8, (double)var9) * 325.949D) & 2047;
            }
         }

         if((var5 & 8) != 0) {
            var4.spotAnimation = var1.readLEShortA();
            var4.spotAnimationChanged(-1);
            var6 = var1.readInt1();
            var4.heightOffset = var6 >> 16;
            var4.field707 = (var6 & 65535) + Client.cycle;
            var4.spotAnimationFrame = 0;
            var4.spotAnimationFrameCycle = 0;
            if(var4.field707 > Client.cycle) {
               var4.spotAnimationFrame = -1;
            }

            if(var4.spotAnimation == 65535) {
               var4.spotAnimation = -1;
               var4.spotAnimationChanged(-1);
            }
         }

         if((var5 & 64) != 0) {
            var4.overheadText = var1.readString();
            var4.overheadTextChanged(-1);
            var4.overheadTextCyclesRemaining = 100;
         }
      }

      for(var2 = 0; var2 < Client.field848; ++var2) {
         var14 = Client.field966[var2];
         if(Client.npcs[var14].npcCycle != Client.cycle) {
            NPC var10000 = Client.npcs[var14];
            Client.npcs[var14].onDefinitionChanged(null);
            var10000.definition = null;
            Client.npcs[var14] = null;
            Client.cachedNPCsChanged(var14);
         }
      }

      if(var1.offset != Client.packetWriter.serverPacketLength) {
         throw new RuntimeException(var1.offset + "," + Client.packetWriter.serverPacketLength);
      } else {
         for(var2 = 0; var2 < Client.npcCount; ++var2) {
            if(Client.npcs[Client.npcIndices[var2]] == null) {
               throw new RuntimeException(var2 + "," + Client.npcCount);
            }
         }

      }
   }
}
