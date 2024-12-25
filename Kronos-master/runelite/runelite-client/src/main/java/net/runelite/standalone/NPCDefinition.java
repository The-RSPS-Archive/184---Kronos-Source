package net.runelite.standalone;

import net.runelite.api.HeadIcon;
import net.runelite.api.events.NpcActionChanged;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSNPCDefinition;
import net.runelite.rs.api.RSSprite;

@ObfuscatedName("il")
public class NPCDefinition extends DualNode implements RSNPCDefinition {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive NpcDefinition_modelArchive;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable NpcDefinition_cachedModels;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable NpcDefinition_cached;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive NpcDefinition_archive;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = 1951589919
   )
   public int walkingAnimation;
   @ObfuscatedName("p")
   public String name;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -441203939
   )
   public int size;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 795033679
   )
   public int id;
   @ObfuscatedName("s")
   public String[] actions;
   @ObfuscatedName("t")
   short[] colors;
   @ObfuscatedName("w")
   @ObfuscatedGetter(
      intValue = -2041030831
   )
   public int walkRightSequence;
   @ObfuscatedName("x")
   short[] retextureFrom;
   @ObfuscatedName("y")
   int[] additionalModels;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = 1113665339
   )
   public int walkBackSequence;
   @ObfuscatedName("aa")
   @ObfuscatedGetter(
      intValue = 950956241
   )
   public int rotation;
   @ObfuscatedName("ab")
   @ObfuscatedGetter(
      intValue = -313433817
   )
   int transformVarp;
   @ObfuscatedName("ac")
   @ObfuscatedGetter(
      intValue = 1963706097
   )
   int ambient;
   @ObfuscatedName("al")
   @ObfuscatedSignature(
      signature = "Llb;"
   )
   IterableNodeHashTable params;
   @ObfuscatedName("ao")
   public boolean isFollower;
   @ObfuscatedName("ap")
   public int[] transforms;
   @ObfuscatedName("ar")
   @ObfuscatedGetter(
      intValue = 1759904707
   )
   int transformVarbit;
   @ObfuscatedName("as")
   public boolean isClickable;
   @ObfuscatedName("aw")
   @ObfuscatedGetter(
      intValue = 246799047
   )
   public int headIconPrayer;
   @ObfuscatedName("ax")
   public boolean isInteractable;
   @ObfuscatedName("az")
   @ObfuscatedGetter(
      intValue = -1785698549
   )
   int contrast;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = -394985883
   )
   public int turnRightSequence;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = -1669190657
   )
   public int turnLeftSequence;
   @ObfuscatedName("d")
   @ObfuscatedGetter(
      intValue = 758770497
   )
   int widthScale;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = -1204590857
   )
   public int walkLeftSequence;
   @ObfuscatedName("f")
   public boolean drawMapDot;
   @ObfuscatedName("g")
   short[] modifiedColors;
   @ObfuscatedName("h")
   short[] retextureTo;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = -182220755
   )
   public int standingAnimation;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = 1360683279
   )
   public int combatLevel;
   @ObfuscatedName("k")
   public boolean isVisible;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = -1757828027
   )
   int heightScale;
   @ObfuscatedName("m")
   int[] models;

   static {
      NpcDefinition_cached = new EvictingDualNodeHashTable(64);
      NpcDefinition_cachedModels = new EvictingDualNodeHashTable(50);
   }

   NPCDefinition() {
      this.name = "null";
      this.size = 1;
      this.standingAnimation = -1;
      this.turnLeftSequence = -1;
      this.turnRightSequence = -1;
      this.walkingAnimation = -1;
      this.walkBackSequence = -1;
      this.walkLeftSequence = -1;
      this.walkRightSequence = -1;
      this.actions = new String[5];
      this.actionsHook(-1);
      this.drawMapDot = true;
      this.combatLevel = -1;
      this.widthScale = 128;
      this.heightScale = 128;
      this.isVisible = false;
      this.ambient = 0;
      this.contrast = 0;
      this.headIconPrayer = -1;
      this.rotation = 32;
      this.transformVarbit = -1;
      this.transformVarp = -1;
      this.isInteractable = true;
      this.isClickable = true;
      this.isFollower = false;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-117"
   )
   void method4402() {
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(B)Ldw;",
      garbageValue = "58"
   )
   public final ModelData method4406() {
      if(this.transforms != null) {
         NPCDefinition var1 = this.method4407();
         return var1 == null?null:var1.method4406();
      } else if(this.additionalModels == null) {
         return null;
      } else {
         boolean var5 = false;

         for(int var2 = 0; var2 < this.additionalModels.length; ++var2) {
            if(!NpcDefinition_modelArchive.method4024(this.additionalModels[var2], 0)) {
               var5 = true;
            }
         }

         if(var5) {
            return null;
         } else {
            ModelData[] var6 = new ModelData[this.additionalModels.length];

            for(int var3 = 0; var3 < this.additionalModels.length; ++var3) {
               var6[var3] = ModelData.method2823(NpcDefinition_modelArchive, this.additionalModels[var3], 0);
            }

            ModelData var7;
            if(var6.length == 1) {
               var7 = var6[0];
            } else {
               var7 = new ModelData(var6, var6.length);
            }

            int var4;
            if(this.colors != null) {
               for(var4 = 0; var4 < this.colors.length; ++var4) {
                  var7.method2770(this.colors[var4], this.modifiedColors[var4]);
               }
            }

            if(this.retextureFrom != null) {
               for(var4 = 0; var4 < this.retextureFrom.length; ++var4) {
                  var7.method2831(this.retextureFrom[var4], this.retextureTo[var4]);
               }
            }

            return var7;
         }
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(I)Lil;",
      garbageValue = "1423511184"
   )
   public final NPCDefinition method4407() {
      int var1 = -1;
      if(this.transformVarbit != -1) {
         var1 = WorldMapSprite.method782(this.transformVarbit);
      } else if(this.transformVarp != -1) {
         var1 = Varps.Varps_main[this.transformVarp];
      }

      int var2;
      if(var1 >= 0 && var1 < this.transforms.length - 1) {
         var2 = this.transforms[var1];
      } else {
         var2 = this.transforms[this.transforms.length - 1];
      }

      return var2 != -1?PacketBufferNode.getNpcDefinition(var2):null;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(Lix;ILix;IS)Ldh;",
      garbageValue = "-1424"
   )
   public final Model method4405(SequenceDefinition var1, int var2, SequenceDefinition var3, int var4) {
      if(this.transforms != null) {
         NPCDefinition var12 = this.method4407();
         return var12 == null?null:var12.method4405(var1, var2, var3, var4);
      } else {
         Model var5 = (Model)NpcDefinition_cachedModels.method3032((long)this.id);
         if(var5 == null) {
            boolean var6 = false;

            for(int var7 = 0; var7 < this.models.length; ++var7) {
               if(!NpcDefinition_modelArchive.method4024(this.models[var7], 0)) {
                  var6 = true;
               }
            }

            if(var6) {
               return null;
            }

            ModelData[] var8 = new ModelData[this.models.length];

            int var9;
            for(var9 = 0; var9 < this.models.length; ++var9) {
               var8[var9] = ModelData.method2823(NpcDefinition_modelArchive, this.models[var9], 0);
            }

            ModelData var11;
            if(var8.length == 1) {
               var11 = var8[0];
            } else {
               var11 = new ModelData(var8, var8.length);
            }

            if(this.colors != null) {
               for(var9 = 0; var9 < this.colors.length; ++var9) {
                  var11.method2770(this.colors[var9], this.modifiedColors[var9]);
               }
            }

            if(this.retextureFrom != null) {
               for(var9 = 0; var9 < this.retextureFrom.length; ++var9) {
                  var11.method2831(this.retextureFrom[var9], this.retextureTo[var9]);
               }
            }

            var5 = var11.method2778(this.ambient + 64, this.contrast + 850, -30, -50, -30);
            NpcDefinition_cachedModels.method3034(var5, (long)this.id);
         }

         Model var10;
         if(var1 != null && var3 != null) {
            var10 = var1.method4660(var5, var2, var3, var4, (byte)-35);
         } else if(var1 != null) {
            var10 = var1.method4661(var5, var2, 338377454);
         } else if(var3 != null) {
            var10 = var3.method4661(var5, var4, 805280683);
         } else {
            var10 = var5.method2355(true);
         }

         if(this.widthScale != 128 || this.heightScale != 128) {
            var10.method2402(this.widthScale, this.heightScale, this.widthScale);
         }

         return var10;
      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "-1943278683"
   )
   void method4424(Buffer var1, int var2) {
      int var3;
      int var4;
      if(var2 == 1) {
         var3 = var1.readUnsignedByte();
         this.models = new int[var3];

         for(var4 = 0; var4 < var3; ++var4) {
            this.models[var4] = var1.readUnsignedShort();
         }
      } else if(var2 == 2) {
         this.name = var1.readString();
      } else if(var2 == 12) {
         this.size = var1.readUnsignedByte();
      } else if(var2 == 13) {
         this.standingAnimation = var1.readUnsignedShort();
      } else if(var2 == 14) {
         this.walkingAnimation = var1.readUnsignedShort();
      } else if(var2 == 15) {
         this.turnLeftSequence = var1.readUnsignedShort();
      } else if(var2 == 16) {
         this.turnRightSequence = var1.readUnsignedShort();
      } else if(var2 == 17) {
         this.walkingAnimation = var1.readUnsignedShort();
         this.walkBackSequence = var1.readUnsignedShort();
         this.walkLeftSequence = var1.readUnsignedShort();
         this.walkRightSequence = var1.readUnsignedShort();
      } else if(var2 >= 30 && var2 < 35) {
         this.actions[var2 - 30] = var1.readString();
         this.actionsHook(var2 - 30);
         if(this.actions[var2 - 30].equalsIgnoreCase("Hidden")) {
            this.actions[var2 - 30] = null;
            this.actionsHook(var2 - 30);
         }
      } else if(var2 == 40) {
         var3 = var1.readUnsignedByte();
         this.colors = new short[var3];
         this.modifiedColors = new short[var3];

         for(var4 = 0; var4 < var3; ++var4) {
            this.colors[var4] = (short)var1.readUnsignedShort();
            this.modifiedColors[var4] = (short)var1.readUnsignedShort();
         }
      } else if(var2 == 41) {
         var3 = var1.readUnsignedByte();
         this.retextureFrom = new short[var3];
         this.retextureTo = new short[var3];

         for(var4 = 0; var4 < var3; ++var4) {
            this.retextureFrom[var4] = (short)var1.readUnsignedShort();
            this.retextureTo[var4] = (short)var1.readUnsignedShort();
         }
      } else if(var2 == 60) {
         var3 = var1.readUnsignedByte();
         this.additionalModels = new int[var3];

         for(var4 = 0; var4 < var3; ++var4) {
            this.additionalModels[var4] = var1.readUnsignedShort();
         }
      } else if(var2 == 93) {
         this.drawMapDot = false;
      } else if(var2 == 95) {
         this.combatLevel = var1.readUnsignedShort();
      } else if(var2 == 97) {
         this.widthScale = var1.readUnsignedShort();
      } else if(var2 == 98) {
         this.heightScale = var1.readUnsignedShort();
      } else if(var2 == 99) {
         this.isVisible = true;
      } else if(var2 == 100) {
         this.ambient = var1.readByte();
      } else if(var2 == 101) {
         this.contrast = var1.readByte() * 5;
      } else if(var2 == 102) {
         this.headIconPrayer = var1.readUnsignedShort();
      } else if(var2 == 103) {
         this.rotation = var1.readUnsignedShort();
      } else if(var2 != 106 && var2 != 118) {
         if(var2 == 107) {
            this.isInteractable = false;
         } else if(var2 == 109) {
            this.isClickable = false;
         } else if(var2 == 111) {
            this.isFollower = true;
         } else if(var2 == 249) {
            this.params = UserComparator5.method3374(var1, this.params);
         }
      } else {
         this.transformVarbit = var1.readUnsignedShort();
         if(this.transformVarbit == 65535) {
            this.transformVarbit = -1;
         }

         this.transformVarp = var1.readUnsignedShort();
         if(this.transformVarp == 65535) {
            this.transformVarp = -1;
         }

         var3 = -1;
         if(var2 == 118) {
            var3 = var1.readUnsignedShort();
            if(var3 == 65535) {
               var3 = -1;
            }
         }

         var4 = var1.readUnsignedByte();
         this.transforms = new int[var4 + 2];

         for(int var5 = 0; var5 <= var4; ++var5) {
            this.transforms[var5] = var1.readUnsignedShort();
            if(this.transforms[var5] == 65535) {
               this.transforms[var5] = -1;
            }
         }

         this.transforms[var4 + 1] = var3;
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;B)V",
      garbageValue = "-41"
   )
   void method4429(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            return;
         }

         this.method4424(var1, var2);
      }
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(III)I",
      garbageValue = "-1804168798"
   )
   public int method4409(int var1, int var2) {
      return HealthBar.getParam(this.params, var1, var2);
   }

   public int getRsOverheadIcon() {
      return this.headIconPrayer;
   }

   public void actionsHook(int var1) {
      NpcActionChanged var2 = new NpcActionChanged();
      var2.setNpcDefinition(this);
      var2.setIdx(var1);
      ViewportMouse.client.getCallbacks().post(NpcActionChanged.class, var2);
   }

   public HeadIcon getOverheadIcon() {
      switch(this.getRsOverheadIcon()) {
      case 0:
         return HeadIcon.MELEE;
      case 1:
         return HeadIcon.RANGED;
      case 2:
         return HeadIcon.MAGIC;
      case 3:
      case 4:
      case 5:
      default:
         return null;
      case 6:
         return HeadIcon.RANGE_MAGE;
      }
   }

   public int getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public int getSize() {
      return this.size;
   }

   public int[] getModels() {
      return this.models;
   }

   public String[] getActions() {
      return this.actions;
   }

   public boolean isMinimapVisible() {
      return this.drawMapDot;
   }

   public int getCombatLevel() {
      return this.combatLevel;
   }

   public boolean isVisible() {
      return this.isVisible;
   }

   public int[] getConfigs() {
      return this.transforms;
   }

   public boolean isClickable() {
      return this.isClickable;
   }

   public RSNPCDefinition transform() {
      return this.method4407();
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(ILjava/lang/String;I)Ljava/lang/String;",
      garbageValue = "-1270482233"
   )
   public String method4410(int var1, String var2) {
      return class94.method2216(this.params, var1, var2);
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(B)Z",
      garbageValue = "-43"
   )
   public boolean method4408() {
      if(this.transforms == null) {
         return true;
      } else {
         int var1 = -1;
         if(this.transformVarbit != -1) {
            var1 = WorldMapSprite.method782(this.transformVarbit);
         } else if(this.transformVarp != -1) {
            var1 = Varps.Varps_main[this.transformVarp];
         }

         return var1 >= 0 && var1 < this.transforms.length?this.transforms[var1] != -1:this.transforms[this.transforms.length - 1] != -1;
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lhp;III)Llf;"
   )
   public static Sprite method4417(AbstractArchive var0, int var1, int var2, int var3) {
      net.runelite.api.Sprite var4 = (net.runelite.api.Sprite)Client.spriteOverrides.get(Integer.valueOf(var1));
      return var4 != null?(Sprite)((RSSprite)var4):(Sprite)Client.copy$SpriteBuffer_getSprite(var0, var1, var2, var3);
   }

   @ObfuscatedName("gr")
   @ObfuscatedSignature(
      signature = "(IIIIIIII)V",
      garbageValue = "474931921"
   )
   static final void method4403(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int var8 = var6 - 334;
      if(var8 < 0) {
         var8 = 0;
      } else if(var8 > 100) {
         var8 = 100;
      }

      int var9 = (Client.zoomWidth - Client.zoomHeight) * var8 / 100 + Client.zoomHeight;
      int var7 = var5 * var9 / 256;
      var8 = 2048 - var3 & 2047;
      var9 = 2048 - var4 & 2047;
      int var10 = 0;
      int var11 = 0;
      int var12 = var7;
      int var13;
      int var14;
      int var15;
      if(var8 != 0) {
         var13 = Rasterizer3D.Rasterizer3D_sine[var8];
         var14 = Rasterizer3D.Rasterizer3D_cosine[var8];
         var15 = var11 * var14 - var7 * var13 >> 16;
         var12 = var14 * var7 + var13 * var11 >> 16;
         var11 = var15;
      }

      if(var9 != 0) {
         var13 = Rasterizer3D.Rasterizer3D_sine[var9];
         var14 = Rasterizer3D.Rasterizer3D_cosine[var9];
         var15 = var10 * var14 + var13 * var12 >> 16;
         var12 = var12 * var14 - var10 * var13 >> 16;
         var10 = var15;
      }

      GrandExchangeOfferOwnWorldComparator.cameraX = var0 - var10;
      Varcs.cameraY = var1 - var11;
      WorldMapIcon_1.cameraZ = var2 - var12;
      IgnoreList.cameraPitch = var3;
      Client.onCameraPitchChanged(-1);
      WorldMapSection2.cameraYaw = var4;
      if(Client.oculusOrbState == 1 && Client.staffModLevel >= 2 && Client.cycle % 50 == 0 && (ObjectSound.oculusOrbFocalPointX >> 7 != class215.localPlayer.x >> 7 || class125.oculusOrbFocalPointY >> 7 != class215.localPlayer.y * 682054857 >> 7)) {
         var13 = class215.localPlayer.plane;
         var14 = (ObjectSound.oculusOrbFocalPointX >> 7) + class215.baseX;
         var15 = (class125.oculusOrbFocalPointY >> 7) + class304.baseY;
         class298.method5476(var14, var15, var13, true);
      }
   }

   void postDecode() {
      if (id == 5527) {
         /* Twiggy O'Korn */
         actions[2] = "Rewards";
      } else if (id == 315) {
         actions[0] = "Talk-to";
         actions[1] = "Set-skull";
         actions[2] = "Reset-kdr";
         actions[3] = null;
         actions[4] = null;
      } else if (id == 1815) {
         name = "Vote Manager";
         actions[0] = "Trade";
         actions[2] = "Cast-votes";
         actions[3] = "Claim-votes";
         actions[4] = "Lottery-info";
      } else if (id == 2108) {
         name = "Credit Manager";
         actions[0] = "Open-Shop";
         actions[2] = "Claim-purchases";
      } else if (id == 306) {
         name = CustomMain.worldType.getName() + " Expert";
         actions[0] = "Talk-to";
         actions[2] = "View-help";
         actions[3] = "View-guide";
         actions[4] = "Task-rewards";
      } else if (id == 5442) {
         name = "Security Advisor";
         actions[2] = "Check Pin Settings";
         actions[3] = "Check 2FA Settings";
      } else if (id == 535) {
         /* Horvik */
         actions[0] = "Repair-items";
         actions[2] = "Upgrade-items";
      } else if (id == 3894) {
         /* Sigmund the Merchant */
         actions[0] = "Buy-items";
         actions[2] = "Sell-items";
         actions[3] = "Sets";
         actions[4] = null;
      } else if (id == 5523) {
         /* Gambling man */
         name = CustomMain.worldType.getName() + " Gambler";
         actions[0] = null;
         actions[2] = "Trade";
      } else if (id == 4398) {
         /* ECO Wizard */
         name = CustomMain.worldType.getName() + " Wizard";
         actions[0] = "Teleport";
         actions[2] = "Teleport-previous";
      } else if (id == 4159) {
         /* PVP Wizard */
         name = CustomMain.worldType.getName() + " Wizard";
         actions[0] = "Teleport";
         actions[2] = "Teleport-previous";
      } else if (id == 2462) {
         /* Shanomi */
         actions[2] = "Trade";
      } else if (id == 3343) {
         name = CustomMain.worldType.getName() + " Nurse";
      } else if (id == 1603) {
         /* Kolodion */
         name = "Battle Point Exchange";
         actions[2] = "Trade";
         actions[3] = "Check-points";
      } else if (id == 3278) {
         name = "Construction Worker";
      } else if(id == 3189) {
         name = "Trade Manager";
         actions[0] = null;
         actions[1] = "Exchange";
         actions[2] = "Coffer";
         actions[3] = "Guide";
      } else if (id == 1307) {
         actions[0] = "Change-looks";
         actions[2] = "Skin-unlocks";
         actions[3] = "Title-unlocks";
         actions[4] = null;
      } else if (id == 4225) {
         name = "Shop";
         actions[0] = "Untradeable";
      } else if (id == 1199) {
         name = "Shop";
         actions[0] = "Consumable";
      } else if (id == 5081) {
         name = "Shop";
         actions[0] = "Magic";
      } else if (id == 2153) {
         name = "Shop";
         actions[0] = "Melee";
      } else if (id == 4579) {
         name = "Shop";
         actions[0] = "Misc";
      } else if (id == 2668) {
         name = "Max hit dummy";
         actions[2] = null;
         actions[3] = null;
         actions[4] = null;
      } else if (id == 6118) {
         name = "Elvarg";
         combatLevel = 280;
      } else if (id == 3358) {
         name = "Ket'ian";
         combatLevel = 420;
         widthScale *= 2;
         heightScale *= 2;
         size = 2;
      } else if (id == 3329) {
         name = "Sapphires Champion";
         combatLevel = 600;
         widthScale *= 2;
         heightScale *= 2;
         size = 2;
      } else if (id == 5906) {
         actions[2] = null;
      } else if ("Pick-up".equals(actions[0]) && "Talk-to".equals(actions[2]) && "Chase".equals(actions[3]) && "Interact".equals(actions[4])) {
         actions[3] = "Age";
         actions[4] = null;
      } else if (id == 1849) {
         name = "Loyalty Fairy";
         actions[0] = "About";
      } else if (id == 7759) {
         actions[0] = actions[2] = null;
      } else if (id == 7316) {
         name = "Tournament Manager";
         actions[0] = "Sign-up";
         actions[1] = "Rewards";
      } else if (id == 7941) {
         actions[2] = actions[3] = actions[4] = null;
      } else if (id == 3080) { // man at home. remove attack option so people can't be assholes to newbies that are just starting out
         actions[1] = null;
         combatLevel = 0;
      } else if (id == 307) { // second guide npc (with no options); originally dr jekyll
         name = CustomMain.worldType.getName() + " Expert";
         actions[0] = null;
         actions[4] = null;
         models = new int[]{214, 250, 5674, 5668, 5672, 7127, 7123};
         additionalModels = new int[]{52, 82};
         colors = new short[]{6466, 5541, 6457, 8844, 6798};
         modifiedColors = new short[]{127, -24254, 127, 6478, -11139};
         standingAnimation = 813;
         walkingAnimation = 1205;
      } else if(id == 311) {
         name = "Ironman";
         actions[1] = "Open-shop";
         actions[2] = null;
      } else if(id == 7951) {
         name = "PvP Event Manager";
         actions[0] = "Join-event";
         actions[1] = "Create-event";
      } else if(id == 8009) {
         actions[3] = "Metamorphosis";
      } else if(id == 8507) {
         name = "Bloody Merchant";
         actions[0] = "Trade";
      } else if(id == 7297) { // Skotizo (For eco world)
         // Replaces Mistag
         copy(7286);
      } else if(id == 8407) {
         actions[1] = "View Trading Post";
      } else if(id == 6002) {
         name = CustomMain.worldType.getName() + " Caretaker";
         actions[0] = "Trade";
      } else if(id == 8500) {
         name = "Old man";
         actions[1] = "Trade";
      } else if (id == 7411) {
         name = "Exodia the Forbidden one";
      } else if(id == 7412) { //Nechryarch Melee
         copy(7411);
         headIconPrayer = 0;
         name = "Exodia the Forbidden one";
      } else if(id == 7414) { //Nechryarch Range
         copy(7411);
         headIconPrayer = 1;
         name = "Exodia the Forbidden one";
      } else if(id == 7415) { //Nechryarch Mage
         copy(7411);
         headIconPrayer = 2;
         name = "Exodia the Forbidden one";
      } else if(id == 8300) { //Ranalph Devere Melee
         copy(3966);
         headIconPrayer = 0;
      } else if(id == 8301) { //Ranalph Devere Range
         copy(3966);
         headIconPrayer = 1;
      } else if(id == 8302) { //Ranalph Devere Mage
         copy(3966);
         headIconPrayer = 2;
      } else if (id == 7317) {
         actions[3] = "Trade";
      }
   }

   void copy(int otherId) {
      NPCDefinition otherDef = PacketBufferNode.getNpcDefinition(otherId);

      drawMapDot = otherDef.drawMapDot;
      standingAnimation = otherDef.standingAnimation;
      size = otherDef.size;
      walkBackSequence = otherDef.walkBackSequence;
      name = otherDef.name;
      turnLeftSequence = otherDef.turnLeftSequence;
      turnRightSequence = otherDef.turnRightSequence;
      walkingAnimation = otherDef.walkingAnimation;
      walkRightSequence = otherDef.walkRightSequence;
      walkLeftSequence = otherDef.walkLeftSequence;
      isFollower = otherDef.isFollower;
      actions = otherDef.actions == null ? null : otherDef.actions.clone();
      combatLevel = otherDef.combatLevel;
      isVisible = otherDef.isVisible;
      headIconPrayer = otherDef.headIconPrayer;
      rotation = otherDef.rotation;
      transforms = otherDef.transforms == null ? null : otherDef.transforms.clone();
      isClickable = otherDef.isClickable;
      isInteractable = otherDef.isInteractable;
      retextureFrom = otherDef.retextureFrom == null ? null : otherDef.retextureFrom.clone();
      retextureTo = otherDef.retextureTo == null ? null : otherDef.retextureTo.clone();
      transformVarp = otherDef.transformVarp;
      models = otherDef.models;
      colors = otherDef.colors == null ? null : otherDef.colors.clone();
      additionalModels = otherDef.additionalModels == null ? null : otherDef.additionalModels.clone();
      transformVarbit = otherDef.transformVarbit;
      modifiedColors = otherDef.modifiedColors == null ? null : otherDef.modifiedColors.clone();
      widthScale = otherDef.widthScale;
      heightScale = otherDef.heightScale;
      ambient = otherDef.ambient;
      contrast = otherDef.contrast;
   }
}
