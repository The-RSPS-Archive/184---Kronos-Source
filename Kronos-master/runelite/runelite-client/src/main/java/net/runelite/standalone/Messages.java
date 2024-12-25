package net.runelite.standalone;

import java.util.HashMap;
import java.util.Map;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ci")
public class Messages {
   @ObfuscatedName("qu")
   @ObfuscatedGetter(
      intValue = -859114081
   )
   static int field813;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Llb;"
   )
   static final IterableNodeHashTable Messages_hashTable;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 229573181
   )
   static int Messages_count;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lji;"
   )
   static final IterableDualNodeQueue Messages_queue;
   @ObfuscatedName("z")
   static final Map Messages_channels;

   static {
      Messages_channels = new HashMap();
      Messages_hashTable = new IterableNodeHashTable(1024);
      Messages_queue = new IterableDualNodeQueue();
      Messages_count = 0;
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "-1500079981"
   )
   static int method1601(int var0, Script var1, boolean var2) {
      String var3;
      if(var0 == 3100) {
         var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
         class217.sendGameMessage(0, "", var3);
         return 1;
      } else if(var0 == 3101) {
         Interpreter.Interpreter_intStackSize -= 2;
         LoginPacket.method3722(class215.localPlayer, Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize], Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1]);
         return 1;
      } else if(var0 == 3103) {
         if(!Interpreter.field646) {
            Interpreter.field645 = true;
         }

         return 1;
      } else {
         int var10;
         if(var0 == 3104) {
            var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
            var10 = 0;
            if(NetSocket.method3488(var3)) {
               var10 = TilePaint.method2912(var3);
            }

            PacketBufferNode var12 = InterfaceParent.method1140(ClientPacket.field2394, Client.packetWriter.isaacCipher);
            var12.packetBuffer.writeInt(var10);
            Client.packetWriter.method1622(var12);
            return 1;
         } else {
            PacketBufferNode var14;
            if(var0 == 3105) {
               var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               var14 = InterfaceParent.method1140(ClientPacket.field2440, Client.packetWriter.isaacCipher);
               var14.packetBuffer.writeByte(var3.length() + 1);
               var14.packetBuffer.writeString(var3);
               Client.packetWriter.method1622(var14);
               return 1;
            } else if(var0 == 3106) {
               var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               var14 = InterfaceParent.method1140(ClientPacket.field2358, Client.packetWriter.isaacCipher);
               var14.packetBuffer.writeByte(var3.length() + 1);
               var14.packetBuffer.writeString(var3);
               Client.packetWriter.method1622(var14);
               return 1;
            } else {
               String var7;
               int var15;
               if(var0 == 3107) {
                  var15 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var7 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                  WorldMapLabelSize.method3526(var15, var7);
                  return 1;
               } else if(var0 == 3108) {
                  Interpreter.Interpreter_intStackSize -= 3;
                  var15 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                  var10 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
                  int var9 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 2];
                  Widget var13 = Canvas.getWidget(var9);
                  class229.method4187(var13, var15, var10);
                  return 1;
               } else if(var0 == 3109) {
                  Interpreter.Interpreter_intStackSize -= 2;
                  var15 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                  var10 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
                  Widget var11 = var2?GrandExchangeOfferAgeComparator.field26:KitDefinition.field3452;
                  class229.method4187(var11, var15, var10);
                  return 1;
               } else if(var0 == 3110) {
                  WorldMapIcon_1.mouseCam = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                  return 1;
               } else if(var0 == 3111) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = AbstractArchive.clientPreferences.roofsHidden?1:0;
                  return 1;
               } else if(var0 == 3112) {
                  AbstractArchive.clientPreferences.roofsHidden = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                  Language.method3830();
                  return 1;
               } else if(var0 == 3113) {
                  var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                  boolean var4 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                  ArchiveDiskActionHandler.method4341(var3, var4, false);
                  return 1;
               } else if(var0 == 3115) {
                  var15 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var14 = InterfaceParent.method1140(ClientPacket.field2401, Client.packetWriter.isaacCipher);
                  var14.packetBuffer.method5481(var15);
                  Client.packetWriter.method1622(var14);
                  return 1;
               } else if(var0 == 3116) {
                  var15 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  Interpreter.Interpreter_stringStackSize -= 2;
                  var7 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize];
                  String var5 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize + 1];
                  if(var7.length() > 500) {
                     return 1;
                  } else if(var5.length() > 500) {
                     return 1;
                  } else {
                     PacketBufferNode var6 = InterfaceParent.method1140(ClientPacket.field2426, Client.packetWriter.isaacCipher);
                     var6.packetBuffer.method5481(1 + class267.method4877(var7) + class267.method4877(var5));
                     var6.packetBuffer.writeString(var7);
                     var6.packetBuffer.writeString(var5);
                     var6.packetBuffer.method5522(var15);
                     Client.packetWriter.method1622(var6);
                     return 1;
                  }
               } else if(var0 == 3117) {
                  Client.shiftClickDrop = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                  return 1;
               } else if(var0 == 3118) {
                  Client.showMouseOverText = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                  return 1;
               } else if(var0 == 3119) {
                  Client.renderSelf = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                  return 1;
               } else if(var0 == 3120) {
                  if(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1) {
                     Client.drawPlayerNames |= 1;
                  } else {
                     Client.drawPlayerNames &= -2;
                  }

                  return 1;
               } else if(var0 == 3121) {
                  if(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1) {
                     Client.drawPlayerNames |= 2;
                  } else {
                     Client.drawPlayerNames &= -3;
                  }

                  return 1;
               } else if(var0 == 3122) {
                  if(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1) {
                     Client.drawPlayerNames |= 4;
                  } else {
                     Client.drawPlayerNames &= -5;
                  }

                  return 1;
               } else if(var0 == 3123) {
                  if(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1) {
                     Client.drawPlayerNames |= 8;
                  } else {
                     Client.drawPlayerNames &= -9;
                  }

                  return 1;
               } else if(var0 == 3124) {
                  Client.drawPlayerNames = 0;
                  return 1;
               } else if(var0 == 3125) {
                  Client.showMouseCross = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                  return 1;
               } else if(var0 == 3126) {
                  Client.showLoadingMessages = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                  return 1;
               } else if(var0 == 3127) {
                  ScriptEvent.method807(Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1);
                  return 1;
               } else if(var0 == 3128) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = WorldMapSprite.method788()?1:0;
                  return 1;
               } else if(var0 == 3129) {
                  Interpreter.Interpreter_intStackSize -= 2;
                  Client.oculusOrbNormalSpeed = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                  Client.oculusOrbSlowedSpeed = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
                  return 1;
               } else if(var0 == 3130) {
                  Interpreter.Interpreter_intStackSize -= 2;
                  return 1;
               } else if(var0 == 3131) {
                  --Interpreter.Interpreter_intStackSize;
                  return 1;
               } else if(var0 == 3132) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = FloorDecoration.canvasWidth;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = WallDecoration.canvasHeight;
                  return 1;
               } else if(var0 == 3133) {
                  --Interpreter.Interpreter_intStackSize;
                  return 1;
               } else if(var0 == 3134) {
                  return 1;
               } else if(var0 == 3135) {
                  Interpreter.Interpreter_intStackSize -= 2;
                  return 1;
               } else if(var0 == 3136) {
                  Client.field1006 = 3;
                  Client.field1032 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  return 1;
               } else if(var0 == 3137) {
                  Client.field1006 = 2;
                  Client.field1032 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  return 1;
               } else if(var0 == 3138) {
                  Client.field1006 = 0;
                  return 1;
               } else if(var0 == 3139) {
                  Client.field1006 = 1;
                  return 1;
               } else if(var0 == 3140) {
                  Client.field1006 = 3;
                  Client.field1032 = var2?GrandExchangeOfferAgeComparator.field26.id:KitDefinition.field3452.id;
                  return 1;
               } else {
                  boolean var8;
                  if(var0 == 3141) {
                     var8 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     AbstractArchive.clientPreferences.hideUsername = var8;
                     Language.method3830();
                     return 1;
                  } else if(var0 == 3142) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = AbstractArchive.clientPreferences.hideUsername?1:0;
                     return 1;
                  } else if(var0 == 3143) {
                     var8 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Client.Login_isUsernameRemembered = var8;
                     if(!var8) {
                        AbstractArchive.clientPreferences.rememberedUsername = "";
                        Language.method3830();
                     }

                     return 1;
                  } else if(var0 == 3144) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.Login_isUsernameRemembered?1:0;
                     return 1;
                  } else if(var0 == 3145) {
                     return 1;
                  } else if(var0 == 3146) {
                     var8 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     if(var8 == AbstractArchive.clientPreferences.titleMusicDisabled) {
                        AbstractArchive.clientPreferences.titleMusicDisabled = !var8;
                        Language.method3830();
                     }

                     return 1;
                  } else if(var0 == 3147) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = AbstractArchive.clientPreferences.titleMusicDisabled?0:1;
                     return 1;
                  } else if(var0 == 3148) {
                     return 1;
                  } else if(var0 == 3149) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3150) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3151) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3152) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3153) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Login.Login_loadingPercent;
                     return 1;
                  } else if(var0 == 3154) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = GrandExchangeOfferTotalQuantityComparator.method203();
                     return 1;
                  } else if(var0 == 3155) {
                     --Interpreter.Interpreter_stringStackSize;
                     return 1;
                  } else if(var0 == 3156) {
                     return 1;
                  } else if(var0 == 3157) {
                     Interpreter.Interpreter_intStackSize -= 2;
                     return 1;
                  } else if(var0 == 3158) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3159) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3160) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3161) {
                     --Interpreter.Interpreter_intStackSize;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3162) {
                     --Interpreter.Interpreter_intStackSize;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3163) {
                     --Interpreter.Interpreter_stringStackSize;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3164) {
                     --Interpreter.Interpreter_intStackSize;
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     return 1;
                  } else if(var0 == 3165) {
                     --Interpreter.Interpreter_intStackSize;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3166) {
                     Interpreter.Interpreter_intStackSize -= 2;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3167) {
                     Interpreter.Interpreter_intStackSize -= 2;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3168) {
                     Interpreter.Interpreter_intStackSize -= 2;
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     return 1;
                  } else if(var0 == 3169) {
                     return 1;
                  } else if(var0 == 3170) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3171) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3172) {
                     --Interpreter.Interpreter_intStackSize;
                     return 1;
                  } else if(var0 == 3173) {
                     --Interpreter.Interpreter_intStackSize;
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else if(var0 == 3174) {
                     --Interpreter.Interpreter_intStackSize;
                     return 1;
                  } else if(var0 == 3175) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                     return 1;
                  } else {
                     return var0 == 3176?1:2;
                  }
               }
            }
         }
      }
   }
}
