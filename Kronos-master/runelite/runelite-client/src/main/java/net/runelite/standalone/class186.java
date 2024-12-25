package net.runelite.standalone;

import net.runelite.api.events.WidgetLoaded;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSWidget;

@ObfuscatedName("gg")
public class class186 {
   @ObfuscatedName("av")
   static void method3728(int var0, int var1) {
      Client.copy$runWidgetOnLoadListener(var0, var1);
      RSWidget[][] var2 = ViewportMouse.client.getWidgets();
      boolean var3 = var2 != null && var2[var0] != null;
      if(var3) {
         WidgetLoaded var4 = new WidgetLoaded();
         var4.setGroupId(var0);
         ViewportMouse.client.getCallbacks().post(WidgetLoaded.class, var4);
      }

   }

   @ObfuscatedName("ih")
   @ObfuscatedSignature(
      signature = "(Lil;IIII)V",
      garbageValue = "1588588018"
   )
   static final void method3727(NPCDefinition var0, int var1, int var2, int var3) {
      if(Client.menuOptionsCount < 400) {
         if(var0.transforms != null) {
            var0 = var0.method4407();
         }

         if(var0 != null) {
            if(var0.isInteractable) {
               if(!var0.isFollower || Client.followerIndex == var1) {
                  String var4 = var0.name;
                  int var7;
                  int var8;
                  if(var0.combatLevel != 0) {
                     var7 = var0.combatLevel;
                     var8 = class215.localPlayer.combatLevel;
                     int var9 = var8 - var7;
                     String var6;
                     if(var9 < -9) {
                        var6 = World.method1251(16711680);
                     } else if(var9 < -6) {
                        var6 = World.method1251(16723968);
                     } else if(var9 < -3) {
                        var6 = World.method1251(16740352);
                     } else if(var9 < 0) {
                        var6 = World.method1251(16756736);
                     } else if(var9 > 9) {
                        var6 = World.method1251(65280);
                     } else if(var9 > 6) {
                        var6 = World.method1251(4259584);
                     } else if(var9 > 3) {
                        var6 = World.method1251(8453888);
                     } else if(var9 > 0) {
                        var6 = World.method1251(12648192);
                     } else {
                        var6 = World.method1251(16776960);
                     }

                     var4 = var4 + var6 + " " + " (" + "level-" + var0.combatLevel + ")";
                  }

                  if(var0.isFollower && Client.followerOpsLowPriority) {
                     WorldMapData_1.method519("Examine", World.method1251(16776960) + var4, 1003, var1, var2, var3);
                  }

                  if(Client.isItemSelected == 1) {
                     WorldMapData_1.method519("Use", Client.selectedItemName + " " + "->" + " " + World.method1251(16776960) + var4, 7, var1, var2, var3);
                  } else if(Client.isSpellSelected) {
                     if((ItemContainer.selectedSpellFlags & 2) == 2) {
                        WorldMapData_1.method519(Client.selectedSpellActionName, Client.selectedSpellName + " " + "->" + " " + World.method1251(16776960) + var4, 8, var1, var2, var3);
                     }
                  } else {
                     int var10 = var0.isFollower && Client.followerOpsLowPriority?2000:0;
                     String[] var11 = var0.actions;
                     if(var11 != null) {
                        for(var7 = 4; var7 >= 0; --var7) {
                           if(var11[var7] != null && !var11[var7].equalsIgnoreCase("Attack")) {
                              var8 = 0;
                              if(var7 == 0) {
                                 var8 = var10 + 9;
                              }

                              if(var7 == 1) {
                                 var8 = var10 + 10;
                              }

                              if(var7 == 2) {
                                 var8 = var10 + 11;
                              }

                              if(var7 == 3) {
                                 var8 = var10 + 12;
                              }

                              if(var7 == 4) {
                                 var8 = var10 + 13;
                              }

                              WorldMapData_1.method519(var11[var7], World.method1251(16776960) + var4, var8, var1, var2, var3);
                           }
                        }
                     }

                     if(var11 != null) {
                        for(var7 = 4; var7 >= 0; --var7) {
                           if(var11[var7] != null && var11[var7].equalsIgnoreCase("Attack")) {
                              short var12 = 0;
                              if(Client.npcAttackOption != AttackOption.AttackOption_hidden) {
                                 if(AttackOption.AttackOption_alwaysRightClick == Client.npcAttackOption || AttackOption.AttackOption_dependsOnCombatLevels == Client.npcAttackOption && var0.combatLevel > class215.localPlayer.combatLevel) {
                                    var12 = 2000;
                                 }

                                 var8 = 0;
                                 if(var7 == 0) {
                                    var8 = var12 + 9;
                                 }

                                 if(var7 == 1) {
                                    var8 = var12 + 10;
                                 }

                                 if(var7 == 2) {
                                    var8 = var12 + 11;
                                 }

                                 if(var7 == 3) {
                                    var8 = var12 + 12;
                                 }

                                 if(var7 == 4) {
                                    var8 = var12 + 13;
                                 }

                                 WorldMapData_1.method519(var11[var7], World.method1251(16776960) + var4, var8, var1, var2, var3);
                              }
                           }
                        }
                     }

                     if(!var0.isFollower || !Client.followerOpsLowPriority) {
                        WorldMapData_1.method519("Examine", World.method1251(16776960) + var4, 1003, var1, var2, var3);
                     }
                  }

               }
            }
         }
      }
   }
}
