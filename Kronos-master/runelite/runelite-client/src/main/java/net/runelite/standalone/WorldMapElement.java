package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSSprite;
import net.runelite.rs.api.RSWorldMapElement;

@ObfuscatedName("ij")
public class WorldMapElement extends DualNode implements RSWorldMapElement {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "[Lij;"
   )
   public static WorldMapElement[] WorldMapElement_cached;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable WorldMapElement_cachedSprites;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1558298053
   )
   public static int WorldMapElement_count;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive WorldMapElement_archive;
   @ObfuscatedName("o")
   public String menuTargetName;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -1840035745
   )
   public int sprite1;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = 1186818379
   )
   int sprite2;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1924770341
   )
   public final int objectId;
   @ObfuscatedName("s")
   int[] field3188;
   @ObfuscatedName("t")
   @ObfuscatedGetter(
      intValue = -1982173019
   )
   int field3196;
   @ObfuscatedName("w")
   @ObfuscatedGetter(
      intValue = -1206163627
   )
   int field3192;
   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "Lin;"
   )
   public HorizontalAlignment horizontalAlignment;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = 1029556521
   )
   public int field3189;
   @ObfuscatedName("a")
   int[] field3183;
   @ObfuscatedName("b")
   public String[] menuActions;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = 404495527
   )
   int field3194;
   @ObfuscatedName("f")
   byte[] field3201;
   @ObfuscatedName("g")
   @ObfuscatedGetter(
      intValue = 1981308333
   )
   int field3197;
   @ObfuscatedName("h")
   @ObfuscatedSignature(
      signature = "Lim;"
   )
   public VerticalAlignment verticalAlignment;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 1307651093
   )
   public int textSize;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = 1937119401
   )
   public int category;
   @ObfuscatedName("m")
   public String name;

   static {
      WorldMapElement_cachedSprites = new EvictingDualNodeHashTable(256);
   }

   public WorldMapElement(int var1) {
      this.sprite1 = -1;
      this.sprite2 = -1;
      this.textSize = 0;
      this.menuActions = new String[5];
      this.field3194 = Integer.MAX_VALUE;
      this.field3192 = Integer.MAX_VALUE;
      this.field3196 = Integer.MIN_VALUE;
      this.field3197 = Integer.MIN_VALUE;
      this.horizontalAlignment = HorizontalAlignment.HorizontalAlignment_centered;
      this.verticalAlignment = VerticalAlignment.VerticalAlignment_centered;
      this.category = -1;
      this.objectId = var1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;I)V",
      garbageValue = "-1143859499"
   )
   public void method4373(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            return;
         }

         this.method4367(var1, var2);
      }
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(II)Llf;",
      garbageValue = "-444494070"
   )
   Sprite method4370(int var1) {
      if(var1 < 0) {
         return null;
      } else {
         Sprite var2 = (Sprite)WorldMapElement_cachedSprites.method3032((long)var1);
         if(var2 != null) {
            return var2;
         } else {
            var2 = NPCDefinition.method4417(WorldMapElement_archive, var1, 0, -1092680498);
            if(var2 != null) {
               WorldMapElement_cachedSprites.method3034(var2, (long)var1);
            }

            return var2;
         }
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "1309666771"
   )
   public int method4371() {
      return this.objectId;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(ZB)Llf;",
      garbageValue = "-63"
   )
   public Sprite method4369(boolean var1) {
      int var2 = this.sprite1;
      return this.method4370(var2);
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "9"
   )
   public void method4368() {
      if(this.field3183 != null) {
         for(int var1 = 0; var1 < this.field3183.length; var1 += 2) {
            if(this.field3183[var1] < this.field3194) {
               this.field3194 = this.field3183[var1];
            } else if(this.field3183[var1] > this.field3196) {
               this.field3196 = this.field3183[var1];
            }

            if(this.field3183[var1 + 1] < this.field3192) {
               this.field3192 = this.field3183[var1 + 1];
            } else if(this.field3183[var1 + 1] > this.field3197) {
               this.field3197 = this.field3183[var1 + 1];
            }
         }
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "-195345385"
   )
   void method4367(Buffer var1, int var2) {
      if(var2 == 1) {
         this.sprite1 = var1.method5507();
      } else if(var2 == 2) {
         this.sprite2 = var1.method5507();
      } else if(var2 == 3) {
         this.name = var1.readString();
      } else if(var2 == 4) {
         this.field3189 = var1.method5500();
      } else if(var2 == 5) {
         var1.method5500();
      } else if(var2 == 6) {
         this.textSize = var1.readUnsignedByte();
      } else {
         int var3;
         if(var2 == 7) {
            var3 = var1.readUnsignedByte();
            if((var3 & 1) == 0) {
               ;
            }

            if((var3 & 2) == 2) {
               ;
            }
         } else if(var2 == 8) {
            var1.readUnsignedByte();
         } else if(var2 >= 10 && var2 <= 14) {
            this.menuActions[var2 - 10] = var1.readString();
         } else if(var2 == 15) {
            var3 = var1.readUnsignedByte();
            this.field3183 = new int[var3 * 2];

            int var4;
            for(var4 = 0; var4 < var3 * 2; ++var4) {
               this.field3183[var4] = var1.g2s();
            }

            var1.readInt();
            var4 = var1.readUnsignedByte();
            this.field3188 = new int[var4];

            int var5;
            for(var5 = 0; var5 < this.field3188.length; ++var5) {
               this.field3188[var5] = var1.readInt();
            }

            this.field3201 = new byte[var3];

            for(var5 = 0; var5 < var3; ++var5) {
               this.field3201[var5] = var1.readByte();
            }
         } else if(var2 != 16) {
            if(var2 == 17) {
               this.menuTargetName = var1.readString();
            } else if(var2 == 18) {
               var1.method5507();
            } else if(var2 == 19) {
               this.category = var1.readUnsignedShort();
            } else if(var2 == 21) {
               var1.readInt();
            } else if(var2 == 22) {
               var1.readInt();
            } else if(var2 == 23) {
               var1.readUnsignedByte();
               var1.readUnsignedByte();
               var1.readUnsignedByte();
            } else if(var2 == 24) {
               var1.g2s();
               var1.g2s();
            } else if(var2 == 25) {
               var1.method5507();
            } else if(var2 == 28) {
               var1.readUnsignedByte();
            } else if(var2 == 29) {
               this.horizontalAlignment = (HorizontalAlignment)NetSocket.getEnumeratedTypeIndex(PlayerAppearance.method4161(), var1.readUnsignedByte());
            } else if(var2 == 30) {
               VerticalAlignment[] var6 = new VerticalAlignment[]{VerticalAlignment.field3261, VerticalAlignment.field3259, VerticalAlignment.VerticalAlignment_centered};
               this.verticalAlignment = (VerticalAlignment)NetSocket.getEnumeratedTypeIndex(var6, var1.readUnsignedByte());
            }
         }
      }

   }

   public RSSprite getMapIcon(boolean var1) {
      int var2 = this.sprite1;

      return this.method4370(var2);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "-1979971580"
   )
   public static void method4378(int var0, int var1) {
      VarbitDefinition var3 = (VarbitDefinition)VarbitDefinition.VarbitDefinition_cached.method3032((long)var0);
      VarbitDefinition var2;
      if(var3 != null) {
         var2 = var3;
      } else {
         byte[] var8 = VarbitDefinition.VarbitDefinition_archive.method4020(14, var0, (short)-9229);
         var3 = new VarbitDefinition();
         if(var8 != null) {
            var3.method4258(new Buffer(var8));
         }

         VarbitDefinition.VarbitDefinition_cached.method3034(var3, (long)var0);
         var2 = var3;
      }

      int var4 = var2.baseVar;
      int var5 = var2.startBit;
      int var6 = var2.endBit;
      int var7 = Varps.Varps_masks[var6 - var5];
      if(var1 < 0 || var1 > var7) {
         var1 = 0;
      }

      var7 <<= var5;
      Varps.Varps_main[var4] = Varps.Varps_main[var4] & ~var7 | var1 << var5 & var7;
      Client.settingsChanged(var4);
   }
}
