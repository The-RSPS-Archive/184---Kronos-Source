package net.runelite.standalone;

import net.runelite.api.MessageNode;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSChatChannel;
import net.runelite.rs.api.RSDualNode;
import net.runelite.rs.api.RSMessage;

@ObfuscatedName("cc")
public class ChatChannel implements RSChatChannel {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "[Lbe;"
   )
   Message[] messages;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -864101903
   )
   int count;

   ChatChannel() {
      this.messages = new Message[100];
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(II)Lbe;",
      garbageValue = "601749225"
   )
   Message method1531(int var1) {
      return var1 >= 0 && var1 < this.count?this.messages[var1]:null;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-135495526"
   )
   int method1525() {
      return this.count;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;B)Lbe;",
      garbageValue = "56"
   )
   Message method1523(int var1, String var2, String var3, String var4) {
      Message var5 = this.messages[99];

      for(int var6 = this.count; var6 > 0; --var6) {
         if(var6 != 100) {
            this.messages[var6] = this.messages[var6 - 1];
         }
      }

      if(var5 == null) {
         var5 = new Message(var1, var2, var4, var3);
      } else {
         var5.method3497();
         var5.method3491();
         var5.method861(var1, var2, var4, var3);
      }

      this.messages[0] = var5;
      if(this.count < 100) {
         ++this.count;
      }

      return var5;
   }

   public RSMessage[] getLines() {
      return this.messages;
   }

   public int getLength() {
      return this.count;
   }

   public void setLength(int var1) {
      this.count = var1;
   }

   public void removeMessageNode(MessageNode var1) {
      RSMessage[] var2 = this.getLines();
      int var3 = this.getLength();
      int var4 = -1;

      int var5;
      for(var5 = 0; var5 < var3; ++var5) {
         if(var2[var5] == var1) {
            var4 = var5;
            break;
         }
      }

      if(var4 != -1) {
         for(var5 = var4; var5 < var3 - 1; ++var5) {
            var2[var5] = var2[var5 + 1];
         }

         var2[var3 - 1] = null;
         this.setLength(var3 - 1);
         RSDualNode var6 = (RSDualNode)var1;
         var6.unlink();
         var6.unlinkDual();
      }
   }

   @ObfuscatedName("ft")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "247037730"
   )
   static final void method1532() {
      int var0 = class212.field2499 * 128 + 64;
      int var1 = Clock.field2041 * 128 + 64;
      int var2 = MusicPatchPcmStream.method3798(var0, var1, WorldMapRectangle.plane) - GrandExchangeOfferOwnWorldComparator.field344;
      if(GrandExchangeOfferOwnWorldComparator.cameraX < var0) {
         GrandExchangeOfferOwnWorldComparator.cameraX = (var0 - GrandExchangeOfferOwnWorldComparator.cameraX) * Messages.field813 / 1000 + GrandExchangeOfferOwnWorldComparator.cameraX + MouseRecorder.field543;
         if(GrandExchangeOfferOwnWorldComparator.cameraX > var0) {
            GrandExchangeOfferOwnWorldComparator.cameraX = var0;
         }
      }

      if(GrandExchangeOfferOwnWorldComparator.cameraX > var0) {
         GrandExchangeOfferOwnWorldComparator.cameraX -= Messages.field813 * (GrandExchangeOfferOwnWorldComparator.cameraX - var0) / 1000 + MouseRecorder.field543;
         if(GrandExchangeOfferOwnWorldComparator.cameraX < var0) {
            GrandExchangeOfferOwnWorldComparator.cameraX = var0;
         }
      }

      if(Varcs.cameraY < var2) {
         Varcs.cameraY = (var2 - Varcs.cameraY) * Messages.field813 / 1000 + Varcs.cameraY + MouseRecorder.field543;
         if(Varcs.cameraY > var2) {
            Varcs.cameraY = var2;
         }
      }

      if(Varcs.cameraY > var2) {
         Varcs.cameraY -= Messages.field813 * (Varcs.cameraY - var2) / 1000 + MouseRecorder.field543;
         if(Varcs.cameraY < var2) {
            Varcs.cameraY = var2;
         }
      }

      if(WorldMapIcon_1.cameraZ < var1) {
         WorldMapIcon_1.cameraZ = (var1 - WorldMapIcon_1.cameraZ) * Messages.field813 / 1000 + WorldMapIcon_1.cameraZ + MouseRecorder.field543;
         if(WorldMapIcon_1.cameraZ > var1) {
            WorldMapIcon_1.cameraZ = var1;
         }
      }

      if(WorldMapIcon_1.cameraZ > var1) {
         WorldMapIcon_1.cameraZ -= Messages.field813 * (WorldMapIcon_1.cameraZ - var1) / 1000 + MouseRecorder.field543;
         if(WorldMapIcon_1.cameraZ < var1) {
            WorldMapIcon_1.cameraZ = var1;
         }
      }

      var0 = GameShell.field464 * 128 + 64;
      var1 = SecureRandomFuture.field746 * 128 + 64;
      var2 = MusicPatchPcmStream.method3798(var0, var1, WorldMapRectangle.plane) - class125.field1658;
      int var3 = var0 - GrandExchangeOfferOwnWorldComparator.cameraX;
      int var4 = var2 - Varcs.cameraY;
      int var5 = var1 - WorldMapIcon_1.cameraZ;
      int var6 = (int)Math.sqrt((double)(var5 * var5 + var3 * var3));
      int var7 = (int)(Math.atan2((double)var4, (double)var6) * 325.949D) & 2047;
      int var8 = (int)(Math.atan2((double)var3, (double)var5) * -325.949D) & 2047;
      if(var7 < 128) {
         var7 = 128;
      }

      if(var7 > 383) {
         var7 = 383;
      }

      if(IgnoreList.cameraPitch < var7) {
         IgnoreList.cameraPitch = (var7 - IgnoreList.cameraPitch) * ScriptEvent.field339 / 1000 + IgnoreList.cameraPitch + class93.field1241;
         Client.onCameraPitchChanged(-1);
         if(IgnoreList.cameraPitch > var7) {
            IgnoreList.cameraPitch = var7;
            Client.onCameraPitchChanged(-1);
         }
      }

      if(IgnoreList.cameraPitch > var7) {
         IgnoreList.cameraPitch -= ScriptEvent.field339 * (IgnoreList.cameraPitch - var7) / 1000 + class93.field1241;
         Client.onCameraPitchChanged(-1);
         if(IgnoreList.cameraPitch < var7) {
            IgnoreList.cameraPitch = var7;
            Client.onCameraPitchChanged(-1);
         }
      }

      int var9 = var8 - WorldMapSection2.cameraYaw;
      if(var9 > 1024) {
         var9 -= 2048;
      }

      if(var9 < -1024) {
         var9 += 2048;
      }

      if(var9 > 0) {
         WorldMapSection2.cameraYaw = WorldMapSection2.cameraYaw + class93.field1241 + var9 * ScriptEvent.field339 / 1000;
         WorldMapSection2.cameraYaw &= 2047;
      }

      if(var9 < 0) {
         WorldMapSection2.cameraYaw -= class93.field1241 + -var9 * ScriptEvent.field339 / 1000;
         WorldMapSection2.cameraYaw &= 2047;
      }

      int var10 = var8 - WorldMapSection2.cameraYaw;
      if(var10 > 1024) {
         var10 -= 2048;
      }

      if(var10 < -1024) {
         var10 += 2048;
      }

      if(var10 < 0 && var9 > 0 || var10 > 0 && var9 < 0) {
         WorldMapSection2.cameraYaw = var8;
      }

   }

   @ObfuscatedName("ja")
   @ObfuscatedSignature(
      signature = "([Lho;II)V",
      garbageValue = "559815398"
   )
   static final void method1533(Widget[] var0, int var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         Widget var3 = var0[var2];
         if(var3 != null && var3.parentId == var1 && (!var3.isIf3 || !WorldMapSectionType.method118(var3))) {
            int var5;
            if(var3.type == 0) {
               if(!var3.isIf3 && WorldMapSectionType.method118(var3) && var3 != NetSocket.mousedOverWidgetIf1) {
                  continue;
               }

               method1533(var0, var3.id);
               if(var3.children != null) {
                  method1533(var3.children, var3.id);
               }

               InterfaceParent var7 = (InterfaceParent)Client.interfaceParents.method6346((long)var3.id);
               if(var7 != null) {
                  var5 = var7.group;
                  if(WorldMapData_0.method171(var5)) {
                     method1533(UserComparator5.Widget_interfaceComponents[var5], -1);
                  }
               }
            }

            if(var3.type == 6) {
               if(var3.sequenceId != -1 || var3.sequenceId2 != -1) {
                  boolean var4 = Projectile.method2244(var3);
                  if(var4) {
                     var5 = var3.sequenceId2;
                  } else {
                     var5 = var3.sequenceId;
                  }

                  if(var5 != -1) {
                     SequenceDefinition var6 = GrandExchangeOfferUnitPriceComparator.method1468(var5);

                     for(var3.modelFrameCycle += Client.field906; var3.modelFrameCycle > var6.frameLengths[var3.modelFrame]; WorldMapSectionType.method116(var3)) {
                        var3.modelFrameCycle -= var6.frameLengths[var3.modelFrame];
                        ++var3.modelFrame;
                        if(var3.modelFrame >= var6.frameIds.length) {
                           var3.modelFrame -= var6.frameCount;
                           if(var3.modelFrame < 0 || var3.modelFrame >= var6.frameIds.length) {
                              var3.modelFrame = 0;
                           }
                        }
                     }
                  }
               }

               if(var3.field2634 != 0 && !var3.isIf3) {
                  int var8 = var3.field2634 >> 16;
                  var5 = var3.field2634 << 16 >> 16;
                  var8 *= Client.field906;
                  var5 *= Client.field906;
                  var3.modelAngleX = var8 + var3.modelAngleX & 2047;
                  var3.modelAngleY = var5 + var3.modelAngleY & 2047;
                  WorldMapSectionType.method116(var3);
               }
            }
         }
      }

   }

   @ObfuscatedName("jx")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "73"
   )
   static void method1524(int var0) {
      ParamDefinition.tempMenuAction = new MenuAction();
      Client.onTempMenuActionChanged(-1);
      ParamDefinition.tempMenuAction.argument1 = Client.menuArguments1[var0];
      ParamDefinition.tempMenuAction.argument2 = Client.menuArguments2[var0];
      ParamDefinition.tempMenuAction.opcode = Client.menuOpcodes[var0];
      ParamDefinition.tempMenuAction.argument0 = Client.menuIdentifiers[var0];
      ParamDefinition.tempMenuAction.action = Client.menuActions[var0];
   }
}
