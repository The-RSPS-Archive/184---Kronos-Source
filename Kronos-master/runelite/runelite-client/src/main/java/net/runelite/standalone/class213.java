package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("hg")
public class class213 {
   @ObfuscatedName("qe")
   @ObfuscatedSignature(
      signature = "Ldv;"
   )
   static PcmPlayer pcmPlayer0;
   @ObfuscatedName("pe")
   @ObfuscatedSignature(
      signature = "Lkc;"
   )
   static PrivateChatMode privateChatMode;

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "689014870"
   )
   public static void method3933() {
      SpotAnimationDefinition.SpotAnimationDefinition_cached.method3035();
      SpotAnimationDefinition.SpotAnimationDefinition_cachedModels.method3035();
   }

   @ObfuscatedName("ak")
   @ObfuscatedSignature(
      signature = "(II)I",
      garbageValue = "412788434"
   )
   static int method3935(int var0) {
      return (int)((Math.log((double)var0) / Interpreter.field648 - 7.0D) * 256.0D);
   }

   @ObfuscatedName("am")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "1490314370"
   )
   static int method3934(int var0, Script var1, boolean var2) {
      if(var0 == 6500) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = BufferedNetSocket.method5242()?1:0;
         return 1;
      } else {
         World var3;
         if(var0 == 6501) {
            var3 = ModelData0.method3067();
            if(var3 != null) {
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.id;
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.properties;
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3.activity;
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.location;
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.population;
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3.host;
            } else {
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
            }

            return 1;
         } else if(var0 == 6502) {
            var3 = AbstractByteArrayCopier.method3875();
            if(var3 != null) {
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.id;
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.properties;
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3.activity;
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.location;
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3.population;
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3.host;
            } else {
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
            }

            return 1;
         } else {
            World var4;
            int var5;
            int id;
            if(var0 == 6506) {
               id = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               var4 = null;

               for(var5 = 0; var5 < World.World_count; ++var5) {
                  if(id == World.World_worlds[var5].id) {
                     var4 = World.World_worlds[var5];
                     break;
                  }
               }

               if(var4 != null) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var4.id;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var4.properties;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var4.activity;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var4.location;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var4.population;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var4.host;
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               }

               return 1;
            } else if(var0 == 6507) {
               Interpreter.Interpreter_intStackSize -= 4;
               id = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
               boolean var10 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1] == 1;
               var5 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 2];
               boolean var6 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 3] == 1;
               class38.method730(id, var10, var5, var6);
               return 1;
            } else if(var0 != 6511) {
               if(var0 == 6512) {
                  Client.followerOpsLowPriority = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                  return 1;
               } else {
                  int index;
                  ParamDefinition param;
                  if(var0 == 6513) {
                     Interpreter.Interpreter_intStackSize -= 2;
                     id = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                     index = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
                     param = WallDecoration.method2913(index);
                     if(param.isString()) {
                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = PacketBufferNode.getNpcDefinition(id).method4410(index, param.defaultStr);
                     } else {
                        Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = PacketBufferNode.getNpcDefinition(id).method4409(index, param.defaultInt);
                     }

                     return 1;
                  } else if(var0 == 6514) {
                     Interpreter.Interpreter_intStackSize -= 2;
                     id = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                     index = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
                     param = WallDecoration.method2913(index);
                     if(param.isString()) {
                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(id).method4748(index, param.defaultStr);
                     } else {
                        Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(id).method4725(index, param.defaultInt);
                     }

                     return 1;
                  } else if(var0 == 6515) {
                     Interpreter.Interpreter_intStackSize -= 2;
                     id = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                     index = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
                     param = WallDecoration.method2913(index);
                     if(param.isString()) {
                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = Occluder.getItemDefinition(id).getParam(index, param.defaultStr);
                     } else {
                        Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Occluder.getItemDefinition(id).getParam(index, param.defaultInt);
                     }

                     return 1;
                  } else if(var0 == 6516) {
                     Interpreter.Interpreter_intStackSize -= 2;
                     id = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
                     index = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
                     param = WallDecoration.method2913(index);
                     if(param.isString()) {
                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = JagexCache.method3408(id).method4503(index, param.defaultStr);
                     } else {
                        Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = JagexCache.method3408(id).method4505(index, param.defaultInt);
                     }

                     return 1;
                  } else if(var0 == 6518) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.onMobile?1:0;
                     return 1;
                  } else if(var0 == 6519) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.clientType & 3;
                     return 1;
                  } else if(var0 == 6520) {
                     return 1;
                  } else if(var0 == 6521) {
                     return 1;
                  } else if(var0 == 6522) {
                     --Interpreter.Interpreter_stringStackSize;
                     --Interpreter.Interpreter_intStackSize;
                     return 1;
                  } else if(var0 == 6523) {
                     --Interpreter.Interpreter_stringStackSize;
                     --Interpreter.Interpreter_intStackSize;
                     return 1;
                  } else if(var0 == 6524) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                     return 1;
                  } else if(var0 == 6525) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 1;
                     return 1;
                  } else if(var0 == 6526) {
                     Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 1;
                     return 1;
                  } else {
                     return 2;
                  }
               }
            } else {
               id = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               if(id >= 0 && id < World.World_count) {
                  var4 = World.World_worlds[id];
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var4.id;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var4.properties;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var4.activity;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var4.location;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var4.population;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var4.host;
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               }

               return 1;
            }
         }
      }
   }
}
