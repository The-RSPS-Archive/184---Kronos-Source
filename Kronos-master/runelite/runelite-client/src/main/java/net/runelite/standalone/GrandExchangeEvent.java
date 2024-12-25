package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("m")
public class GrandExchangeEvent {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      longValue = 5655370423736122045L
   )
   public final long age;
   @ObfuscatedName("r")
   String previousOfferName;
   @ObfuscatedName("u")
   String offerName;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Li;"
   )
   public final GrandExchangeOffer grandExchangeOffer;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 1358084805
   )
   public final int world;

   @ObfuscatedSignature(
      signature = "(Lkl;BI)V"
   )
   GrandExchangeEvent(Buffer var1, byte var2, int var3) {
      this.offerName = var1.readString();
      this.previousOfferName = var1.readString();
      this.world = var1.readUnsignedShort();
      this.age = var1.method5502();
      int var4 = var1.readInt();
      int var5 = var1.readInt();
      this.grandExchangeOffer = new GrandExchangeOffer();
      this.grandExchangeOffer.method4199(2);
      this.grandExchangeOffer.method4206(var2);
      this.grandExchangeOffer.unitPrice = var4;
      this.grandExchangeOffer.totalQuantity = var5;
      this.grandExchangeOffer.currentQuantity = 0;
      this.grandExchangeOffer.currentPrice = 0;
      this.grandExchangeOffer.id = var3;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)Ljava/lang/String;",
      garbageValue = "1720570985"
   )
   public String method6489() {
      return this.previousOfferName;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)Ljava/lang/String;",
      garbageValue = "963027705"
   )
   public String method6490() {
      return this.offerName;
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "33"
   )
   public static void method6483() {
      HitSplatDefinition.HitSplatDefinition_cached.method3035();
      HitSplatDefinition.HitSplatDefinition_cachedSprites.method3035();
      HitSplatDefinition.HitSplatDefinition_cachedFonts.method3035();
   }

   @ObfuscatedName("gj")
   @ObfuscatedSignature(
      signature = "(Lbt;B)V",
      garbageValue = "76"
   )
   static final void method6488(PendingSpawn var0) {
      long var1 = 0L;
      int var3 = -1;
      int var4 = 0;
      int var5 = 0;
      if(var0.type == 0) {
         var1 = PacketWriter.scene.method3140(var0.plane, var0.x, var0.y);
      }

      if(var0.type == 1) {
         var1 = PacketWriter.scene.method3265(var0.plane, var0.x, var0.y);
      }

      if(var0.type == 2) {
         var1 = PacketWriter.scene.method3142(var0.plane, var0.x, var0.y);
      }

      if(var0.type == 3) {
         var1 = PacketWriter.scene.method3143(var0.plane, var0.x, var0.y);
      }

      if(0L != var1) {
         int var6 = PacketWriter.scene.method3144(var0.plane, var0.x, var0.y, var1);
         var3 = class86.method2114(var1);
         var4 = var6 & 31;
         var5 = var6 >> 6 & 3;
      }

      var0.objectId = var3;
      var0.field631 = var4;
      var0.field632 = var5;
   }

   @ObfuscatedName("ie")
   @ObfuscatedSignature(
      signature = "(Lbi;IIII)V",
      garbageValue = "1547441010"
   )
   static final void method6487(Player var0, int var1, int var2, int var3) {
      if(class215.localPlayer != var0) {
         if(Client.menuOptionsCount < 400) {
            String var4;
            int var7;
            if(var0.skillLevel == 0) {
               String var5 = var0.actions[0] + var0.getName(true) + var0.actions[1];
               var7 = var0.combatLevel;
               int var8 = class215.localPlayer.combatLevel;
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

               var4 = var5 + var6 + " " + " (" + "level-" + var0.combatLevel + ")" + var0.actions[2];
            } else {
               var4 = var0.actions[0] + var0.username + var0.actions[1] + " " + " (" + "skill-" + var0.skillLevel + ")" + var0.actions[2];
            }

            int var10;
            if(Client.isItemSelected == 1) {
               WorldMapData_1.method519("Use", Client.selectedItemName + " " + "->" + " " + World.method1251(16777215) + var4, 14, var1, var2, var3);
            } else if(Client.isSpellSelected) {
               if((ItemContainer.selectedSpellFlags & 8) == 8) {
                  WorldMapData_1.method519(Client.selectedSpellActionName, Client.selectedSpellName + " " + "->" + " " + World.method1251(16777215) + var4, 15, var1, var2, var3);
               }
            } else {
               for(var10 = 7; var10 >= 0; --var10) {
                  if(Client.playerMenuActions[var10] != null) {
                     short var11 = 0;
                     if(Client.playerMenuActions[var10].equalsIgnoreCase("Attack")) {
                        if(AttackOption.AttackOption_hidden == Client.playerAttackOption || Client.shouldHideAttackOptionFor(var0)) {
                           continue;
                        }

                        if(AttackOption.AttackOption_alwaysRightClick == Client.playerAttackOption || Client.playerAttackOption == AttackOption.AttackOption_dependsOnCombatLevels && var0.combatLevel > class215.localPlayer.combatLevel) {
                           var11 = 2000;
                        }

                        if(class215.localPlayer.team != 0 && var0.team != 0) {
                           if(var0.team == class215.localPlayer.team) {
                              var11 = 2000;
                           } else {
                              var11 = 0;
                           }
                        }
                     } else if(Client.playerOptionsPriorities[var10]) {
                        var11 = 2000;
                     }

                     boolean var12 = false;
                     var7 = Client.playerMenuOpcodes[var10] + var11;
                     WorldMapData_1.method519(Client.playerMenuActions[var10], World.method1251(16777215) + var4, var7, var1, var2, var3);
                  }
               }
            }

            for(var10 = 0; var10 < Client.menuOptionsCount; ++var10) {
               if(Client.menuOpcodes[var10] == 23) {
                  Client.menuTargets[var10] = World.method1251(16777215) + var4;
                  break;
               }
            }

         }
      }
   }
}
