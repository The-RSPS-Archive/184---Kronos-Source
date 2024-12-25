package net.runelite.standalone;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSClientPreferences;

@ObfuscatedName("bl")
public class ClientPreferences implements RSClientPreferences {
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -1035730115
   )
   static int ClientPreferences_optionCount;
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "Llp;"
   )
   static IndexedSprite field517;
   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   static AbstractArchive Widget_spritesArchive;
   @ObfuscatedName("p")
   String rememberedUsername;
   @ObfuscatedName("q")
   boolean hideUsername;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1970428567
   )
   int windowMode;
   @ObfuscatedName("u")
   boolean titleMusicDisabled;
   @ObfuscatedName("v")
   boolean roofsHidden;
   @ObfuscatedName("m")
   LinkedHashMap parameters;

   static {
      ClientPreferences_optionCount = 6;
   }

   ClientPreferences() {
      this.windowMode = 1;
      this.rememberedUsername = null;
      this.hideUsername = false;
      this.parameters = new LinkedHashMap();
      this.method1144(true);
   }

   @ObfuscatedSignature(
      signature = "(Lkl;)V"
   )
   ClientPreferences(Buffer var1) {
      this.windowMode = 1;
      this.rememberedUsername = null;
      this.hideUsername = false;
      this.parameters = new LinkedHashMap();
      if(var1 != null && var1.array != null) {
         int var2 = var1.readUnsignedByte();
         if(var2 >= 0 && var2 <= ClientPreferences_optionCount) {
            if(var1.readUnsignedByte() == 1) {
               this.roofsHidden = true;
            }

            if(var2 > 1) {
               this.titleMusicDisabled = var1.readUnsignedByte() == 1;
            }

            if(var2 > 3) {
               this.windowMode = var1.readUnsignedByte();
            }

            if(var2 > 2) {
               int var3 = var1.readUnsignedByte();

               for(int var4 = 0; var4 < var3; ++var4) {
                  int var5 = var1.readInt();
                  int var6 = var1.readInt();
                  this.parameters.put(Integer.valueOf(var5), Integer.valueOf(var6));
               }
            }

            if(var2 > 4) {
               this.rememberedUsername = var1.method5504();
            }

            if(var2 > 5) {
               this.hideUsername = var1.method5503();
            }
         } else {
            this.method1144(true);
         }
      } else {
         this.method1144(true);
      }

   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)Lkl;",
      garbageValue = "-630561096"
   )
   Buffer method1145() {
      Buffer var1 = new Buffer(100);
      var1.writeByte(ClientPreferences_optionCount);
      var1.writeByte(this.roofsHidden?1:0);
      var1.writeByte(this.titleMusicDisabled?1:0);
      var1.writeByte(this.windowMode);
      var1.writeByte(this.parameters.size());
      Iterator var2 = this.parameters.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         var1.writeInt(((Integer)var3.getKey()).intValue());
         var1.writeInt(((Integer)var3.getValue()).intValue());
      }

      var1.writeString(this.rememberedUsername != null?this.rememberedUsername:"");
      var1.method5486(this.hideUsername);
      return var1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(ZI)V",
      garbageValue = "864937253"
   )
   void method1144(boolean var1) {
   }

   public void setRememberedUsername(String var1) {
      this.rememberedUsername = var1;
   }

   public String getRememberedUsername() {
      return this.rememberedUsername;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-70"
   )
   static void method1154() {
      Players.Players_count = 0;

      for(int var0 = 0; var0 < 2048; ++var0) {
         Players.field1192[var0] = null;
         Players.field1191[var0] = 1;
      }

   }

   @ObfuscatedName("jb")
   @ObfuscatedSignature(
      signature = "(Lho;IIII)V",
      garbageValue = "1173230125"
   )
   static final void method1146(Widget var0, int var1, int var2, int var3) {
      WorldMapID.method687();
      SpriteMask var4 = var0.method3975(false);
      if(var4 != null) {
         Rasterizer2D.method6474(var1, var2, var4.width + var1, var2 + var4.height);
         if(Client.minimapState != 2 && Client.minimapState != 5) {
            int var5 = Client.camAngleY & 2047;
            int var6 = class215.localPlayer.x / 32 + 48;
            int var7 = 464 - class215.localPlayer.y * 682054857 / 32;
            ObjectSound.sceneMinimapSprite.method6205(var1, var2, var4.width, var4.height, var6, var7, var5, 256, var4.xStarts, var4.xWidths);

            int var8;
            int var9;
            int var10;
            for(var8 = 0; var8 < Client.mapIconCount; ++var8) {
               var9 = Client.mapIconXs[var8] * 4 + 2 - class215.localPlayer.x / 32;
               var10 = Client.mapIconYs[var8] * 4 + 2 - class215.localPlayer.y * 682054857 / 32;
               class214.method3936(var1, var2, var9, var10, Client.mapIcons[var8], var4);
            }

            int var11;
            int var12;
            for(var8 = 0; var8 < 104; ++var8) {
               for(var9 = 0; var9 < 104; ++var9) {
                  NodeDeque var15 = Client.groundItems[WorldMapRectangle.plane][var8][var9];
                  if(var15 != null) {
                     var11 = var8 * 4 + 2 - class215.localPlayer.x / 32;
                     var12 = var9 * 4 + 2 - class215.localPlayer.y * 682054857 / 32;
                     class214.method3936(var1, var2, var11, var12, class17.mapDotSprites[0], var4);
                  }
               }
            }

            for(var8 = 0; var8 < Client.npcCount; ++var8) {
               NPC var16 = Client.npcs[Client.npcIndices[var8]];
               if(var16 != null && var16.vmethod1611()) {
                  NPCDefinition var18 = var16.definition;
                  if(var18 != null && var18.transforms != null) {
                     var18 = var18.method4407();
                  }

                  if(var18 != null && var18.drawMapDot && var18.isInteractable) {
                     var11 = var16.x / 32 - class215.localPlayer.x / 32;
                     var12 = var16.y * 682054857 / 32 - class215.localPlayer.y * 682054857 / 32;
                     class214.method3936(var1, var2, var11, var12, class17.mapDotSprites[1], var4);
                  }
               }
            }

            var8 = Players.Players_count;
            int[] var19 = Players.Players_indices;

            for(var10 = 0; var10 < var8; ++var10) {
               Player var17 = Client.players[var19[var10]];
               if(var17 != null && var17.vmethod1611() && !var17.isHidden && var17 != class215.localPlayer) {
                  var12 = var17.x / 32 - class215.localPlayer.x / 32;
                  int var13 = var17.y * 682054857 / 32 - class215.localPlayer.y * 682054857 / 32;
                  boolean var14 = false;
                  if(class215.localPlayer.team != 0 && var17.team != 0 && var17.team == class215.localPlayer.team) {
                     var14 = true;
                  }

                  if(var17.method1090()) {
                     class214.method3936(var1, var2, var12, var13, class17.mapDotSprites[3], var4);
                  } else if(var14) {
                     class214.method3936(var1, var2, var12, var13, class17.mapDotSprites[4], var4);
                  } else if(var17.method1093()) {
                     class214.method3936(var1, var2, var12, var13, class17.mapDotSprites[5], var4);
                  } else {
                     class214.method3936(var1, var2, var12, var13, class17.mapDotSprites[2], var4);
                  }
               }
            }

            if(Client.hintArrowType != 0 && Client.cycle % 20 < 10) {
               if(Client.hintArrowType == 1 && Client.hintArrowNpcIndex >= 0 && Client.hintArrowNpcIndex < Client.npcs.length) {
                  NPC var20 = Client.npcs[Client.hintArrowNpcIndex];
                  if(var20 != null) {
                     var11 = var20.x / 32 - class215.localPlayer.x / 32;
                     var12 = var20.y * 682054857 / 32 - class215.localPlayer.y * 682054857 / 32;
                     FloorDecoration.method2432(var1, var2, var11, var12, GameObject.mapMarkerSprites[1], var4);
                  }
               }

               if(Client.hintArrowType == 2) {
                  var10 = Client.hintArrowX * 4 - class215.baseX * 4 + 2 - class215.localPlayer.x / 32;
                  var11 = Client.hintArrowY * 4 - class304.baseY * 4 + 2 - class215.localPlayer.y * 682054857 / 32;
                  FloorDecoration.method2432(var1, var2, var10, var11, GameObject.mapMarkerSprites[1], var4);
               }

               if(Client.hintArrowType == 10 && Client.hintArrowPlayerIndex >= 0 && Client.hintArrowPlayerIndex < Client.players.length) {
                  Player var21 = Client.players[Client.hintArrowPlayerIndex];
                  if(var21 != null) {
                     var11 = var21.x / 32 - class215.localPlayer.x / 32;
                     var12 = var21.y * 682054857 / 32 - class215.localPlayer.y * 682054857 / 32;
                     FloorDecoration.method2432(var1, var2, var11, var12, GameObject.mapMarkerSprites[1], var4);
                  }
               }
            }

            if(Client.destinationX != 0) {
               var10 = Client.destinationX * 4 + 2 - class215.localPlayer.x / 32;
               var11 = Client.destinationY * 4 + 2 - class215.localPlayer.y * 682054857 / 32;
               class214.method3936(var1, var2, var10, var11, GameObject.mapMarkerSprites[0], var4);
            }

            if(!class215.localPlayer.isHidden) {
               Rasterizer2D.method6469(var4.width / 2 + var1 - 1, var4.height / 2 + var2 - 1, 3, 3, 16777215);
            }
         } else {
            Rasterizer2D.method6430(var1, var2, 0, var4.xStarts, var4.xWidths);
         }

         Client.field1050[var3] = true;
      }
   }
}
