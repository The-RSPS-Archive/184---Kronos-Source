package net.runelite.standalone;

import java.io.IOException;
import java.util.Arrays;

import net.runelite.api.events.PostItemDefinition;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSItemDefinition;
import net.runelite.rs.api.RSModel;

@ObfuscatedName("it")
public class ItemDefinition extends DualNode implements RSItemDefinition {
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   static AbstractArchive ItemDefinition_archive;
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   static AbstractArchive ItemDefinition_modelArchive;
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable ItemDefinition_cachedSprites;
   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   static EvictingDualNodeHashTable ItemDefinition_cachedModels;
   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   static EvictingDualNodeHashTable ItemDefinition_cached;
   @ObfuscatedName("s")
   @ObfuscatedGetter(
      intValue = -1552364581
   )
   public int zoom2d;
   @ObfuscatedName("t")
   short[] recolorFrom;
   @ObfuscatedName("w")
   public String name;
   @ObfuscatedName("x")
   short[] retextureFrom;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = 1164476445
   )
   public int id;
   @ObfuscatedName("aa")
   public String[] groundActions;
   @ObfuscatedName("ab")
   @ObfuscatedGetter(
      intValue = -281574303
   )
   int shiftClickIndex;
   @ObfuscatedName("ac")
   @ObfuscatedGetter(
      intValue = -1898122861
   )
   public int isStackable;
   @ObfuscatedName("ad")
   @ObfuscatedGetter(
      intValue = -774987533
   )
   int femaleModel1;
   @ObfuscatedName("ae")
   @ObfuscatedGetter(
      intValue = -622073101
   )
   int femaleHeadModel;
   @ObfuscatedName("af")
   @ObfuscatedGetter(
      intValue = 1043908831
   )
   int resizeY;
   @ObfuscatedName("ag")
   int[] countco;
   @ObfuscatedName("ah")
   int[] countobj;
   @ObfuscatedName("ai")
   @ObfuscatedGetter(
      intValue = -629230839
   )
   int maleModel2;
   @ObfuscatedName("aj")
   @ObfuscatedGetter(
      intValue = 2003146835
   )
   public int note;
   @ObfuscatedName("ak")
   @ObfuscatedGetter(
      intValue = -1320032129
   )
   int femaleModel2;
   @ObfuscatedName("al")
   @ObfuscatedGetter(
      intValue = -472240431
   )
   int femaleModel;
   @ObfuscatedName("am")
   @ObfuscatedGetter(
      intValue = 1142570821
   )
   int maleHeadModel2;
   @ObfuscatedName("an")
   @ObfuscatedGetter(
      intValue = 839022773
   )
   int femaleOffset;
   @ObfuscatedName("ao")
   @ObfuscatedGetter(
      intValue = 708775895
   )
   int maleOffset;
   @ObfuscatedName("ap")
   public String[] inventoryActions;
   @ObfuscatedName("aq")
   @ObfuscatedGetter(
      intValue = 2070739727
   )
   int maleHeadModel;
   @ObfuscatedName("as")
   @ObfuscatedGetter(
      intValue = -1653428101
   )
   int maleModel1;
   @ObfuscatedName("at")
   @ObfuscatedGetter(
      intValue = -251593053
   )
   int resizeZ;
   @ObfuscatedName("au")
   @ObfuscatedGetter(
      intValue = 146352651
   )
   int resizeX;
   @ObfuscatedName("av")
   @ObfuscatedGetter(
      intValue = -1870487091
   )
   int femaleHeadModel2;
   @ObfuscatedName("aw")
   public boolean isMembersOnly;
   @ObfuscatedName("ax")
   @ObfuscatedGetter(
      intValue = -1611629547
   )
   int maleModel;
   @ObfuscatedName("ay")
   @ObfuscatedGetter(
      intValue = -922094347
   )
   public int noteTemplate;
   @ObfuscatedName("az")
   @ObfuscatedGetter(
      intValue = -187431889
   )
   public int price;
   @ObfuscatedName("bc")
   @ObfuscatedGetter(
      intValue = -1887741169
   )
   public int placeholderTemplate;
   @ObfuscatedName("bd")
   @ObfuscatedGetter(
      intValue = 850095651
   )
   public int team;
   @ObfuscatedName("bh")
   @ObfuscatedGetter(
      intValue = 807356389
   )
   public int contrast;
   @ObfuscatedName("bj")
   @ObfuscatedGetter(
      intValue = 1578768163
   )
   int unnotedId;
   @ObfuscatedName("bm")
   @ObfuscatedSignature(
      signature = "Llb;"
   )
   IterableNodeHashTable params;
   @ObfuscatedName("bs")
   @ObfuscatedGetter(
      intValue = -407908075
   )
   int notedId;
   @ObfuscatedName("bv")
   public boolean isTradable;
   @ObfuscatedName("bx")
   @ObfuscatedGetter(
      intValue = 427704773
   )
   public int ambient;
   @ObfuscatedName("bz")
   @ObfuscatedGetter(
      intValue = -1594376835
   )
   public int placeholder;
   public int shiftClickActionIndex;
   public int modelOverride;
   @ObfuscatedName("d")
   @ObfuscatedGetter(
      intValue = -1211123861
   )
   public int zan2d;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = -512556695
   )
   int model;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = 850273909
   )
   public int xan2d;
   @ObfuscatedName("g")
   short[] recolorTo;
   @ObfuscatedName("h")
   short[] retextureTo;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = 1701459647
   )
   public int yan2d;
   @ObfuscatedName("k")
   @ObfuscatedGetter(
      intValue = 992637627
   )
   public int offsetY2d;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = -973833067
   )
   public int offsetX2d;

   static {
      ItemDefinition_cached = new EvictingDualNodeHashTable(64);
      ItemDefinition_cachedModels = new EvictingDualNodeHashTable(50);
      ItemDefinition_cachedSprites = new EvictingDualNodeHashTable(200);
   }

   ItemDefinition() {
      this.name = "null";
      this.zoom2d = 2000;
      this.xan2d = 0;
      this.yan2d = 0;
      this.zan2d = 0;
      this.offsetX2d = 0;
      this.offsetY2d = 0;
      this.isStackable = 0;
      this.price = 1;
      this.isMembersOnly = false;
      this.groundActions = new String[]{null, null, "Take", null, null};
      this.inventoryActions = new String[]{null, null, null, null, "Drop"};
      this.shiftClickIndex = -2;
      this.maleModel = -1;
      this.maleModel1 = -1;
      this.maleOffset = 0;
      this.femaleModel = -1;
      this.femaleModel1 = -1;
      this.femaleOffset = 0;
      this.maleModel2 = -1;
      this.femaleModel2 = -1;
      this.maleHeadModel = -1;
      this.maleHeadModel2 = -1;
      this.femaleHeadModel = -1;
      this.femaleHeadModel2 = -1;
      this.note = -1;
      this.noteTemplate = -1;
      this.resizeX = 128;
      this.resizeY = 128;
      this.resizeZ = 128;
      this.ambient = 0;
      this.contrast = 0;
      this.team = 0;
      this.isTradable = false;
      this.unnotedId = -1;
      this.notedId = -1;
      this.placeholder = -1;
      this.placeholderTemplate = -1;
      this.rl$$init();
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Lit;Lit;B)V",
      garbageValue = "126"
   )
   void method4552(ItemDefinition var1, ItemDefinition var2) {
      this.model = var1.model;
      this.zoom2d = var1.zoom2d;
      this.xan2d = var1.xan2d;
      this.yan2d = var1.yan2d;
      this.zan2d = var1.zan2d;
      this.offsetX2d = var1.offsetX2d;
      this.offsetY2d = var1.offsetY2d;
      this.recolorFrom = var1.recolorFrom;
      this.recolorTo = var1.recolorTo;
      this.retextureFrom = var1.retextureFrom;
      this.retextureTo = var1.retextureTo;
      this.name = var2.name;
      this.isMembersOnly = var2.isMembersOnly;
      this.price = var2.price;
      this.isStackable = 1;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(Lit;Lit;S)V",
      garbageValue = "-15779"
   )
   void method4532(ItemDefinition var1, ItemDefinition var2) {
      this.model = var1.model;
      this.zoom2d = var1.zoom2d;
      this.xan2d = var1.xan2d;
      this.yan2d = var1.yan2d;
      this.zan2d = var1.zan2d;
      this.offsetX2d = var1.offsetX2d;
      this.offsetY2d = var1.offsetY2d;
      this.recolorFrom = var2.recolorFrom;
      this.recolorTo = var2.recolorTo;
      this.retextureFrom = var2.retextureFrom;
      this.retextureTo = var2.retextureTo;
      this.name = var2.name;
      this.isMembersOnly = var2.isMembersOnly;
      this.isStackable = var2.isStackable;
      this.maleModel = var2.maleModel;
      this.maleModel1 = var2.maleModel1;
      this.maleModel2 = var2.maleModel2;
      this.femaleModel = var2.femaleModel;
      this.femaleModel1 = var2.femaleModel1;
      this.femaleModel2 = var2.femaleModel2;
      this.maleHeadModel = var2.maleHeadModel;
      this.maleHeadModel2 = var2.maleHeadModel2;
      this.femaleHeadModel = var2.femaleHeadModel;
      this.femaleHeadModel2 = var2.femaleHeadModel2;
      this.team = var2.team;
      this.groundActions = var2.groundActions;
      this.inventoryActions = new String[5];
      if(var2.inventoryActions != null) {
         for(int var3 = 0; var3 < 4; ++var3) {
            this.inventoryActions[var3] = var2.inventoryActions[var3];
         }
      }

      this.inventoryActions[4] = "Discard";
      this.price = 0;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(Lkl;IB)V",
      garbageValue = "7"
   )
   void method4579(Buffer var1, int var2) {
      if(var2 == 1) {
         this.model = var1.readUnsignedShort();
      } else if(var2 == 2) {
         this.name = var1.readString();
      } else if(var2 == 4) {
         this.zoom2d = var1.readUnsignedShort();
      } else if(var2 == 5) {
         this.xan2d = var1.readUnsignedShort();
      } else if(var2 == 6) {
         this.yan2d = var1.readUnsignedShort();
      } else if(var2 == 7) {
         this.offsetX2d = var1.readUnsignedShort();
         if(this.offsetX2d > 32767) {
            this.offsetX2d -= 65536;
         }
      } else if(var2 == 8) {
         this.offsetY2d = var1.readUnsignedShort();
         if(this.offsetY2d > 32767) {
            this.offsetY2d -= 65536;
         }
      } else if(var2 == 11) {
         this.isStackable = 1;
      } else if(var2 == 12) {
         this.price = var1.readInt();
      } else if(var2 == 16) {
         this.isMembersOnly = true;
      } else if(var2 == 23) {
         this.maleModel = var1.readUnsignedShort();
         this.maleOffset = var1.readUnsignedByte();
      } else if(var2 == 24) {
         this.maleModel1 = var1.readUnsignedShort();
      } else if(var2 == 25) {
         this.femaleModel = var1.readUnsignedShort();
         this.femaleOffset = var1.readUnsignedByte();
      } else if(var2 == 26) {
         this.femaleModel1 = var1.readUnsignedShort();
      } else if(var2 >= 30 && var2 < 35) {
         this.groundActions[var2 - 30] = var1.readString();
         if(this.groundActions[var2 - 30].equalsIgnoreCase("Hidden")) {
            this.groundActions[var2 - 30] = null;
         }
      } else if(var2 >= 35 && var2 < 40) {
         this.inventoryActions[var2 - 35] = var1.readString();
      } else {
         int var3;
         int var4;
         if(var2 == 40) {
            var3 = var1.readUnsignedByte();
            this.recolorFrom = new short[var3];
            this.recolorTo = new short[var3];

            for(var4 = 0; var4 < var3; ++var4) {
               this.recolorFrom[var4] = (short)var1.readUnsignedShort();
               this.recolorTo[var4] = (short)var1.readUnsignedShort();
            }
         } else if(var2 == 41) {
            var3 = var1.readUnsignedByte();
            this.retextureFrom = new short[var3];
            this.retextureTo = new short[var3];

            for(var4 = 0; var4 < var3; ++var4) {
               this.retextureFrom[var4] = (short)var1.readUnsignedShort();
               this.retextureTo[var4] = (short)var1.readUnsignedShort();
            }
         } else if(var2 == 42) {
            this.shiftClickIndex = var1.readByte();
         } else if(var2 == 65) {
            this.isTradable = true;
         } else if(var2 == 78) {
            this.maleModel2 = var1.readUnsignedShort();
         } else if(var2 == 79) {
            this.femaleModel2 = var1.readUnsignedShort();
         } else if(var2 == 90) {
            this.maleHeadModel = var1.readUnsignedShort();
         } else if(var2 == 91) {
            this.femaleHeadModel = var1.readUnsignedShort();
         } else if(var2 == 92) {
            this.maleHeadModel2 = var1.readUnsignedShort();
         } else if(var2 == 93) {
            this.femaleHeadModel2 = var1.readUnsignedShort();
         } else if(var2 == 95) {
            this.zan2d = var1.readUnsignedShort();
         } else if(var2 == 97) {
            this.note = var1.readUnsignedShort();
         } else if(var2 == 98) {
            this.noteTemplate = var1.readUnsignedShort();
         } else if(var2 >= 100 && var2 < 110) {
            if(this.countobj == null) {
               this.countobj = new int[10];
               this.countco = new int[10];
            }

            this.countobj[var2 - 100] = var1.readUnsignedShort();
            this.countco[var2 - 100] = var1.readUnsignedShort();
         } else if(var2 == 110) {
            this.resizeX = var1.readUnsignedShort();
         } else if(var2 == 111) {
            this.resizeY = var1.readUnsignedShort();
         } else if(var2 == 112) {
            this.resizeZ = var1.readUnsignedShort();
         } else if(var2 == 113) {
            this.ambient = var1.readByte();
         } else if(var2 == 114) {
            this.contrast = var1.readByte() * 5;
         } else if(var2 == 115) {
            this.team = var1.readUnsignedByte();
         } else if(var2 == 139) {
            this.unnotedId = var1.readUnsignedShort();
         } else if(var2 == 140) {
            this.notedId = var1.readUnsignedShort();
         } else if(var2 == 148) {
            this.placeholder = var1.readUnsignedShort();
         } else if(var2 == 149) {
            this.placeholderTemplate = var1.readUnsignedShort();
         } else if(var2 == 249) {
            this.params = UserComparator5.method3374(var1, this.params);
         }
      }

   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "(IIB)I",
      garbageValue = "59"
   )
   public int getParam(int index, int defaultInt) {
      return HealthBar.getParam(this.params, index, defaultInt);
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lkl;B)V",
      garbageValue = "32"
   )
   void method4527(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            return;
         }

         this.method4579(var1, var2);
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "1"
   )
   void method4528() {
      this.post();
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "(ZB)Z",
      garbageValue = "100"
   )
   public final boolean method4556(boolean var1) {
      int var2 = this.maleHeadModel;
      int var3 = this.maleHeadModel2;
      if(var1) {
         var2 = this.femaleHeadModel;
         var3 = this.femaleHeadModel2;
      }

      if(var2 == -1) {
         return true;
      } else {
         boolean var4 = true;
         if(!ItemDefinition_modelArchive.method4024(var2, 0)) {
            var4 = false;
         }

         if(var3 != -1 && !ItemDefinition_modelArchive.method4024(var3, 0)) {
            var4 = false;
         }

         return var4;
      }
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(IB)Ldw;",
      garbageValue = "-102"
   )
   public final ModelData method4534(int var1) {
      int var3;
      if(this.countobj != null && var1 > 1) {
         int var2 = -1;

         for(var3 = 0; var3 < 10; ++var3) {
            if(var1 >= this.countco[var3] && this.countco[var3] != 0) {
               var2 = this.countobj[var3];
            }
         }

         if(var2 != -1) {
            return Occluder.getItemDefinition(var2).method4534(1);
         }
      }

      ModelData var4 = ModelData.method2823(ItemDefinition_modelArchive, this.model, 0);
      if(var4 == null) {
         return null;
      } else {
         if(this.resizeX != 128 || this.resizeY != 128 || this.resizeZ != 128) {
            var4.method2773(this.resizeX, this.resizeY, this.resizeZ);
         }

         if(this.recolorFrom != null) {
            for(var3 = 0; var3 < this.recolorFrom.length; ++var3) {
               var4.method2770(this.recolorFrom[var3], this.recolorTo[var3]);
            }
         }

         if(this.retextureFrom != null) {
            for(var3 = 0; var3 < this.retextureFrom.length; ++var3) {
               var4.method2831(this.retextureFrom[var3], this.retextureTo[var3]);
            }
         }

         return var4;
      }
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(ZI)Z",
      garbageValue = "-94789059"
   )
   public final boolean method4537(boolean var1) {
      int var2 = this.maleModel;
      int var3 = this.maleModel1;
      int var4 = this.maleModel2;
      if(var1) {
         var2 = this.femaleModel;
         var3 = this.femaleModel1;
         var4 = this.femaleModel2;
      }

      if(var2 == -1) {
         return true;
      } else {
         boolean var5 = true;
         if(!ItemDefinition_modelArchive.method4024(var2, 0)) {
            var5 = false;
         }

         if(var3 != -1 && !ItemDefinition_modelArchive.method4024(var3, 0)) {
            var5 = false;
         }

         if(var4 != -1 && !ItemDefinition_modelArchive.method4024(var4, 0)) {
            var5 = false;
         }

         return var5;
      }
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(IB)Lit;",
      garbageValue = "23"
   )
   public ItemDefinition method4559(int var1) {
      if(this.countobj != null && var1 > 1) {
         int var2 = -1;

         for(int var3 = 0; var3 < 10; ++var3) {
            if(var1 >= this.countco[var3] && this.countco[var3] != 0) {
               var2 = this.countobj[var3];
            }
         }

         if(var2 != -1) {
            return Occluder.getItemDefinition(var2);
         }
      }

      return this;
   }

   public int getIsStackable() {
      return this.isStackable;
   }

   public void post() {
      PostItemDefinition var1 = new PostItemDefinition();
      var1.setItemDefinition(this);
      ViewportMouse.client.getCallbacks().post(PostItemDefinition.class, var1);
   }

   public int copy$getShiftClickIndex(int var1) {
      return this.shiftClickIndex != -1 && this.inventoryActions != null?(this.shiftClickIndex >= 0?(this.inventoryActions[this.shiftClickIndex] != null?this.shiftClickIndex:-1):("Drop".equalsIgnoreCase(this.inventoryActions[4])?4:-1)):-1;
   }

   @ObfuscatedSignature(
      signature = "(II)Ldh;"
   )
   public final Model copy$getModel(int var1, int var2) {
      if(this.countobj != null && var1 > 1) {
         int var3 = -1;

         for(int var4 = 0; var4 < 10; ++var4) {
            if(var1 >= this.countco[var4] && this.countco[var4] != 0) {
               var3 = this.countobj[var4];
            }
         }

         if(var3 != -1) {
            return Occluder.getItemDefinition(var3).method4575(1, 1336448754);
         }
      }

      Model var6 = (Model)ItemDefinition_cachedModels.method3032((long)this.id);
      if(var6 != null) {
         return var6;
      } else {
         ModelData var7 = ModelData.method2823(ItemDefinition_modelArchive, this.model, 0);
         if(var7 == null) {
            return null;
         } else {
            if(this.resizeX != 128 || this.resizeY != 128 || this.resizeZ != 128) {
               var7.method2773(this.resizeX, this.resizeY, this.resizeZ);
            }

            int var5;
            if(this.recolorFrom != null) {
               for(var5 = 0; var5 < this.recolorFrom.length; ++var5) {
                  var7.method2770(this.recolorFrom[var5], this.recolorTo[var5]);
               }
            }

            if(this.retextureFrom != null) {
               for(var5 = 0; var5 < this.retextureFrom.length; ++var5) {
                  var7.method2831(this.retextureFrom[var5], this.retextureTo[var5]);
               }
            }

            var6 = var7.method2778(this.ambient + 64, this.contrast + 768, -50, -10, -50);
            var6.isSingleTile = true;
            ItemDefinition_cachedModels.method3034(var6, (long)this.id);
            return var6;
         }
      }
   }

   private void rl$$init() {
      this.shiftClickActionIndex = -2;
      this.modelOverride = -1;
   }

   public void setModelOverride(int var1) {
      this.modelOverride = var1;
   }

   public boolean isStackable() {
      return this.getIsStackable() != 0;
   }

   public void setShiftClickActionIndex(int var1) {
      this.shiftClickActionIndex = var1;
   }

   public void resetShiftClickActionIndex() {
      this.shiftClickActionIndex = -2;
   }

   public int getId() {
      return this.id;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public String getName() {
      return this.name;
   }

   public int getPrice() {
      return this.price;
   }

   public boolean isMembers() {
      return this.isMembersOnly;
   }

   public String[] getInventoryActions() {
      return this.inventoryActions;
   }

   public int getMaleModel() {
      return this.maleModel;
   }

   public int getLinkedNoteId() {
      return this.note;
   }

   public int getNote() {
      return this.noteTemplate;
   }

   public void setTradeable(boolean var1) {
      this.isTradable = var1;
   }

   public boolean isTradeable() {
      return this.isTradable;
   }

   public int getPlaceholderId() {
      return this.placeholder;
   }

   public int getPlaceholderTemplateId() {
      return this.placeholderTemplate;
   }

   public RSModel getModel(int var1) {
      return this.method4575(var1, 1336448754);
   }

   public int getShiftClickActionIndex() {
      return this.method4543(-1424068644);
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(ZB)Ldw;",
      garbageValue = "22"
   )
   public final ModelData method4538(boolean var1) {
      int var2 = this.maleModel;
      int var3 = this.maleModel1;
      int var4 = this.maleModel2;
      if(var1) {
         var2 = this.femaleModel;
         var3 = this.femaleModel1;
         var4 = this.femaleModel2;
      }

      if(var2 == -1) {
         return null;
      } else {
         ModelData var5 = ModelData.method2823(ItemDefinition_modelArchive, var2, 0);
         if(var3 != -1) {
            ModelData var6 = ModelData.method2823(ItemDefinition_modelArchive, var3, 0);
            if(var4 != -1) {
               ModelData var7 = ModelData.method2823(ItemDefinition_modelArchive, var4, 0);
               ModelData[] var8 = new ModelData[]{var5, var6, var7};
               var5 = new ModelData(var8, 3);
            } else {
               ModelData[] var10 = new ModelData[]{var5, var6};
               var5 = new ModelData(var10, 2);
            }
         }

         if(!var1 && this.maleOffset != 0) {
            var5.method2784(0, this.maleOffset, 0);
         }

         if(var1 && this.femaleOffset != 0) {
            var5.method2784(0, this.femaleOffset, 0);
         }

         int var9;
         if(this.recolorFrom != null) {
            for(var9 = 0; var9 < this.recolorFrom.length; ++var9) {
               var5.method2770(this.recolorFrom[var9], this.recolorTo[var9]);
            }
         }

         if(this.retextureFrom != null) {
            for(var9 = 0; var9 < this.retextureFrom.length; ++var9) {
               var5.method2831(this.retextureFrom[var9], this.retextureTo[var9]);
            }
         }

         return var5;
      }
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      signature = "(ILjava/lang/String;I)Ljava/lang/String;",
      garbageValue = "570180688"
   )
   public String getParam(int index, String defaultString) {
      return class94.method2216(this.params, index, defaultString);
   }

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      signature = "(ZI)Ldw;",
      garbageValue = "1830989293"
   )
   public final ModelData method4540(boolean var1) {
      int var2 = this.maleHeadModel;
      int var3 = this.maleHeadModel2;
      if(var1) {
         var2 = this.femaleHeadModel;
         var3 = this.femaleHeadModel2;
      }

      if(var2 == -1) {
         return null;
      } else {
         ModelData var4 = ModelData.method2823(ItemDefinition_modelArchive, var2, 0);
         if(var3 != -1) {
            ModelData var5 = ModelData.method2823(ItemDefinition_modelArchive, var3, 0);
            ModelData[] var6 = new ModelData[]{var4, var5};
            var4 = new ModelData(var6, 2);
         }

         int var7;
         if(this.recolorFrom != null) {
            for(var7 = 0; var7 < this.recolorFrom.length; ++var7) {
               var4.method2770(this.recolorFrom[var7], this.recolorTo[var7]);
            }
         }

         if(this.retextureFrom != null) {
            for(var7 = 0; var7 < this.retextureFrom.length; ++var7) {
               var4.method2831(this.retextureFrom[var7], this.retextureTo[var7]);
            }
         }

         return var4;
      }
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(II)Ldh;"
   )
   public final Model method4575(int var1, int var2) {
      return this.modelOverride == -1?(Model)this.copy$getModel(var1, var2):(Model)ViewportMouse.client.getItemDefinition(this.modelOverride).getModel(var1);
   }

   @ObfuscatedName("j")
   public int method4543(int var1) {
      return this.shiftClickActionIndex == -2?this.copy$getShiftClickIndex(var1):this.shiftClickActionIndex;
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(Lit;Lit;I)V",
      garbageValue = "-2143595462"
   )
   void method4533(ItemDefinition var1, ItemDefinition var2) {
      this.model = var1.model;
      this.zoom2d = var1.zoom2d;
      this.xan2d = var1.xan2d;
      this.yan2d = var1.yan2d;
      this.zan2d = var1.zan2d;
      this.offsetX2d = var1.offsetX2d;
      this.offsetY2d = var1.offsetY2d;
      this.recolorFrom = var1.recolorFrom;
      this.recolorTo = var1.recolorTo;
      this.retextureFrom = var1.retextureFrom;
      this.retextureTo = var1.retextureTo;
      this.isStackable = var1.isStackable;
      this.name = var2.name;
      this.price = 0;
      this.isMembersOnly = false;
      this.isTradable = false;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(B)Lbl;",
      garbageValue = "66"
   )
   static ClientPreferences method4600() {
      AccessFile var0 = null;
      ClientPreferences var1 = new ClientPreferences();

      try {
         var0 = class202.method3853("", class10.field66.name, false);
         byte[] var2 = new byte[(int)var0.method5()];

         int var4;
         for(int var3 = 0; var3 < var2.length; var3 += var4) {
            var4 = var0.method6(var2, var3, var2.length - var3);
            if(var4 == -1) {
               throw new IOException();
            }
         }

         var1 = new ClientPreferences(new Buffer(var2));
      } catch (Exception var6) {
         ;
      }

      try {
         if(var0 != null) {
            var0.method18();
         }
      } catch (Exception var5) {
         ;
      }

      return var1;
   }

   public void postDecode() {
      if(id == 2742) {
         name = "Coin Casket (small)";
         inventoryActions[1] = null;
      }

      if(id == 2744) {
         name = "Coin Casket (medium)";
         inventoryActions[1] = null;
      }

      if(id == 2746) {
         name = "Coin Casket (large)";
         inventoryActions[1] = null;
      }

      if(id == 2748) {
         name = "Coin Casket (giant)";
         inventoryActions[1] = null;
      }

      if (id == 3455 || id == 3457 || id == 3458) {
         name = "Clue key";
         inventoryActions[3] = "Check-Hint";
      }

      if(id == 13190) { //bond
         name = CustomMain.worldType.getName() + " Credit";
         isStackable = 1;
         inventoryActions[0] = "Claim";
         inventoryActions[2] = null;
      }

      if (id == 13302) {
         name = "PK Key";
      }
      if(id == 6199) {
         inventoryActions[2] = "Gift";
      }

      if(id == 290) {
         name = "Super Mystery Box";
         inventoryActions[1] = "Open";
         inventoryActions[2] = "Gift";
      }

      if(id == 6828) {
         name = "Pet Mystery Box";
         inventoryActions[1] = "Open";
         inventoryActions[2] = "Gift";
      }

      if(id == 6829) {
         name = "Voting Mystery Box";
         inventoryActions[1] = "Open";
         inventoryActions[2] = "Gift";
      }

      if(id == 6831) {
         name = "3rd Age Mystery Box";
         inventoryActions[1] = "Open";
         inventoryActions[2] = "Gift";
      }

      if(id == 1505) {
         name = "Obelisk destination scroll";
      }

      if (id == 621) {
         name = "Tournament voucher";
      }

      if(id == 4067) {
         name = "Vote ticket";
      }

      if (id == 9477) {
         name = "Coinbox";
         inventoryActions[0] = "Open";
         isStackable = 0;
      }

      if (id == 3455 || id == 3457 || id == 3458) {
         inventoryActions[3] = "Check-Hint";
      }
      if(id == 10834) {
         name = "Dice bag";
         inventoryActions[0] = "Roll 4-sided";
         inventoryActions[1] = "Roll 12-sided";
         inventoryActions[2] = "Roll 100-sided";
      }

      if (id == 1464) {
         name = "Vote Lottery Ticket";
         isStackable = 0;
      }

      if(id == 6306) {
         name = "LMS Sticks";
         isTradable = false;
      }

      if(id == 6806) {
         name = "Green Skin Scroll";
         inventoryActions[0] = "Redeem";
      }

      if(id == 6807) {
         name = "Blue Skin Scroll";
         inventoryActions[0] = "Redeem";
      }

      if(id == 6808) {
         name = "Purple Skin Scroll";
         inventoryActions[0] = "Redeem";
      }

      if(id == 19625) {
         name = "Home teleport";
      }
      if(id == 8007) {
         inventoryActions[1] = null;
      }
      if(id >= 1567 && id <= 1572)
         name = "Overgrown cat";
      /* Quick option for repairing all broken items */
      if(name.endsWith("(broken)"))
         inventoryActions[0] = "Fix";

      if(id == 11169) {
         name = CustomMain.worldType.getName() + " Herald";
         inventoryActions[0] = "View-updates";
         inventoryActions[1] = null;
         inventoryActions[4] = "Destroy";
      }
      if(id == 12746 || (id >= 12748 && id <= 12756)) {
         inventoryActions[0] = "Info";
         inventoryActions[1] = "Redeem";
      }
      if (id == 2399) {
         name = "Deadman supply key";
      }

      if(id == 21532) {
         name = "Blood Fragments";
      }

      if(id == 607) {
         name = "Rare Drop Scroll";
         inventoryActions[0] = "Activate";
      }
      if(id == 608) {
         name = "Pet Drop Scroll";
         inventoryActions[0] = "Activate";
      }
      if(id == 6758) {
         name = "Bonus Exp Scroll";
         inventoryActions[0] = "Activate";
      }

      if(id == 2730) {
         name = "Wilderness Reward Box (small)";
         inventoryActions[0] = "Open";
         inventoryActions[1] = null;
      }

      if(id == 2732) {
         name = "Wilderness Reward Box (medium)";
         inventoryActions[0] = "Open";
         inventoryActions[1] = null;
      }

      if(id == 2734) {
         name = "Wilderness Reward Box (large)";
         inventoryActions[0] = "Open";
         inventoryActions[1] = null;
      }

      if(id == 2736) {
         name = "Wilderness Reward Box (giant)";
         inventoryActions[0] = "Open";
         inventoryActions[1] = null;
      }

      if (id == 13215) {
         ItemDefinition platinumTokenClone = Occluder.getItemDefinition(13204);
         price = platinumTokenClone.price;
         femaleModel1 = platinumTokenClone.femaleModel1;
         femaleOffset = platinumTokenClone.femaleOffset;
         inventoryActions = platinumTokenClone.inventoryActions;
         model = platinumTokenClone.model;
         maleModel = platinumTokenClone.maleModel;
         isMembersOnly = platinumTokenClone.isMembersOnly;
         name = "Bloody Token";
         isStackable = platinumTokenClone.isStackable;
         xan2d = platinumTokenClone.xan2d;
         yan2d = platinumTokenClone.yan2d;
         offsetY2d = platinumTokenClone.offsetY2d;
         zoom2d = platinumTokenClone.zoom2d;
         offsetX2d = platinumTokenClone.offsetX2d;
         zan2d = platinumTokenClone.zan2d;
         recolorFrom = new short[]{5813, 9139, 26006};
         recolorTo = new short[]{947, 948, 949};
         countco = new int[]{2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
         countobj = new int[]{13216, 13217, 13218, 13218, 0, 0, 0, 0, 0, 0};
      }

      if (id == 13216) {
         ItemDefinition platinumTokenClone = Occluder.getItemDefinition(3985);
         price = platinumTokenClone.price;
         femaleModel1 = platinumTokenClone.femaleModel1;
         name = "Bloody Token";
         femaleOffset = platinumTokenClone.femaleOffset;
         inventoryActions = platinumTokenClone.inventoryActions;
         model = platinumTokenClone.model;
         maleModel = platinumTokenClone.maleModel;
         isMembersOnly = platinumTokenClone.isMembersOnly;
         isStackable = platinumTokenClone.isStackable;
         xan2d = platinumTokenClone.xan2d;
         yan2d = platinumTokenClone.yan2d;
         offsetY2d = platinumTokenClone.offsetY2d;
         zoom2d = platinumTokenClone.zoom2d;
         offsetX2d = platinumTokenClone.offsetX2d;
         zan2d = platinumTokenClone.zan2d;
         recolorFrom = new short[]{5813, 9139, 26006};
         recolorTo = new short[]{947, 948, 949};
      }

      if (id == 13217) {
         ItemDefinition platinumTokenClone = Occluder.getItemDefinition(3987);
         price = platinumTokenClone.price;
         femaleModel1 = platinumTokenClone.femaleModel1;
         name = "Bloody Token";
         femaleOffset = platinumTokenClone.femaleOffset;
         inventoryActions = platinumTokenClone.inventoryActions;
         model = platinumTokenClone.model;
         maleModel = platinumTokenClone.maleModel;
         isMembersOnly = platinumTokenClone.isMembersOnly;
         isStackable = platinumTokenClone.isStackable;
         xan2d = platinumTokenClone.xan2d;
         yan2d = platinumTokenClone.yan2d;
         offsetY2d = platinumTokenClone.offsetY2d;
         zoom2d = platinumTokenClone.zoom2d;
         offsetX2d = platinumTokenClone.offsetX2d;
         zan2d = platinumTokenClone.zan2d;
         recolorFrom = new short[]{5813, 9139, 26006};
         recolorTo = new short[]{947, 948, 949};
      }

      if (id == 13218) {
         ItemDefinition platinumTokenClone = Occluder.getItemDefinition(3989);
         price = platinumTokenClone.price;
         femaleModel1 = platinumTokenClone.femaleModel1;
         femaleOffset = platinumTokenClone.femaleOffset;
         name = "Bloody Token";
         inventoryActions = platinumTokenClone.inventoryActions;
         model = platinumTokenClone.model;
         maleModel = platinumTokenClone.maleModel;
         isMembersOnly = platinumTokenClone.isMembersOnly;
         isStackable = platinumTokenClone.isStackable;
         xan2d = platinumTokenClone.xan2d;
         yan2d = platinumTokenClone.yan2d;
         offsetY2d = platinumTokenClone.offsetY2d;
         zoom2d = platinumTokenClone.zoom2d;
         offsetX2d = platinumTokenClone.offsetX2d;
         zan2d = platinumTokenClone.zan2d;
         recolorFrom = new short[]{5813, 9139, 26006};
         recolorTo = new short[]{947, 948, 949};
      }

      if(id == 22330) {
         name = "PVP Armour Mystery Box";
         inventoryActions[0] = "Open";
         inventoryActions[1] = "Gift";
         inventoryActions[2] = null;
         inventoryActions[3] = null;
      }

      if (id == 3606 || id == 3608 || id == 7297) {
         ItemDefinition bloodyKey = Occluder.getItemDefinition(20526);

         switch (id) {
            case 3606:
               name = "Bloody key (easy)";
               break;
            case 3608:
               name = "Bloody key (medium)";
               break;
            case 7297:
               name = "Bloody key (hard)";
               break;
         }

         model = bloodyKey.model;
         xan2d = bloodyKey.xan2d;
         offsetY2d = bloodyKey.offsetY2d;
         yan2d = bloodyKey.yan2d;
         offsetX2d = bloodyKey.offsetX2d;
         zoom2d = bloodyKey.zoom2d;
      }

      if(id == 22521 || id == 22522 || id == 22523 || id == 22524) {
         String size = "";
         if(id == 22521)
            size = "(small)";
         else if(id == 22522)
            size = "(medium)";
         else if(id == 22523)
            size = "(large)";
         else if(id == 22524)
            size = "(giant)";
         name = "Blood money pouch " + size;
         recolorFrom = new short[]{6798};
         recolorTo = new short[]{948};
      }

      if(id == 2528) {
         name = "Skill Lamp";
      }

      if(id == 11918) {
         name = "Santa Outfit Box";
         inventoryActions[0] = "Open";
      }

      if(id == 12897) {
         name = "Anti-Santa Outfit Box";
         inventoryActions[0] = "Open";
      }

      if(id == 22816 || id == 22817) {
         inventoryActions[4] = "Destroy";
      }

      if(id == 21227) {
         name = "Easter Egg";
         inventoryActions[0] = "Open";
      }

      if (id == 8943 || id == 8944 || id == 8945 || id == 8946 || id == 8947 || id == 8948) {
         ItemDefinition bloodyKey = Occluder.getItemDefinition(20526);

         switch (id) {
            case 8943:
               name = "Wilderness key (1M OSRS)";
               break;
            case 8944:
               name = "Wilderness key (5M OSRS)";
               break;
            case 8945:
               name = "Wilderness key (10M OSRS)";
               break;
            case 8946:
               name = "Wilderness key (25M OSRS)";
               break;
            case 8947:
               name = "Wilderness key (50M OSRS)";
               break;
            case 8948:
               name = "Wilderness key (100M OSRS)";
               break;
         }

         inventoryActions[0] = "Information";
         inventoryActions[3] = null;
         inventoryActions[4] = "Destroy";
         model = bloodyKey.model;
         xan2d = bloodyKey.xan2d;
         offsetY2d = bloodyKey.offsetY2d;
         yan2d = bloodyKey.yan2d;
         offsetX2d = bloodyKey.offsetX2d;
         zoom2d = bloodyKey.zoom2d;
      }
   }
}
