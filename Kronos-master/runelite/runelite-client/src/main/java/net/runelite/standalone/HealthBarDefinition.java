package net.runelite.standalone;

import net.runelite.api.events.PostHealthBar;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSBuffer;
import net.runelite.rs.api.RSHealthBarDefinition;
import net.runelite.rs.api.RSSprite;

@ObfuscatedName("ip")
public class HealthBarDefinition extends DualNode implements RSHealthBarDefinition {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   static AbstractArchive HitSplatDefinition_spritesArchive;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable HealthBarDefinition_cachedSprites;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable HealthBarDefinition_cached;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive HealthBarDefinition_archive;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = -1546985657
   )
   int frontSpriteID;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = -2020710967
   )
   public int field3296;
   @ObfuscatedName("w")
   @ObfuscatedGetter(
      intValue = 1482399655
   )
   public int widthPadding;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = 1672807375
   )
   public int int2;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = -1484247529
   )
   int backSpriteID;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = -2056406209
   )
   public int int5;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 1160994935
   )
   public int int4;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = 2061624127
   )
   public int width;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = -1245763213
   )
   public int int3;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 2073172143
   )
   public int int1;

   static {
      HealthBarDefinition_cached = new EvictingDualNodeHashTable(64);
      HealthBarDefinition_cachedSprites = new EvictingDualNodeHashTable(64);
   }

   public HealthBarDefinition() {
      this.int1 = 255;
      this.int2 = 255;
      this.int3 = -1;
      this.int4 = 1;
      this.int5 = 70;
      this.frontSpriteID = -1;
      this.backSpriteID = -1;
      this.width = 30;
      this.widthPadding = 0;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;I)V",
      garbageValue = "-1504288957"
   )
   public void method4469(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            this.onRead(var1);
            return;
         }

         this.method4472(var1, var2);
      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(B)Llf;",
      garbageValue = "-62"
   )
   public Sprite method4485() {
      if(this.backSpriteID < 0) {
         return null;
      } else {
         Sprite var1 = (Sprite)HealthBarDefinition_cachedSprites.method3032((long)this.backSpriteID);
         if(var1 != null) {
            return var1;
         } else {
            var1 = NPCDefinition.method4417(HitSplatDefinition_spritesArchive, this.backSpriteID, 0, -1092680498);
            if(var1 != null) {
               HealthBarDefinition_cachedSprites.method3034(var1, (long)this.backSpriteID);
            }

            return var1;
         }
      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I)Llf;",
      garbageValue = "445043084"
   )
   public Sprite method4471() {
      if(this.frontSpriteID < 0) {
         return null;
      } else {
         Sprite var1 = (Sprite)HealthBarDefinition_cachedSprites.method3032((long)this.frontSpriteID);
         if(var1 != null) {
            return var1;
         } else {
            var1 = NPCDefinition.method4417(HitSplatDefinition_spritesArchive, this.frontSpriteID, 0, -1092680498);
            if(var1 != null) {
               HealthBarDefinition_cachedSprites.method3034(var1, (long)this.frontSpriteID);
            }

            return var1;
         }
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;IB)V",
      garbageValue = "2"
   )
   void method4472(Buffer var1, int var2) {
      if(var2 == 1) {
         var1.readUnsignedShort();
      } else if(var2 == 2) {
         this.int1 = var1.readUnsignedByte();
      } else if(var2 == 3) {
         this.int2 = var1.readUnsignedByte();
      } else if(var2 == 4) {
         this.int3 = 0;
      } else if(var2 == 5) {
         this.int5 = var1.readUnsignedShort();
      } else if(var2 == 6) {
         var1.readUnsignedByte();
      } else if(var2 == 7) {
         this.frontSpriteID = var1.method5507();
      } else if(var2 == 8) {
         this.backSpriteID = var1.method5507();
      } else if(var2 == 11) {
         this.int3 = var1.readUnsignedShort();
      } else if(var2 == 14) {
         this.width = var1.readUnsignedByte();
      } else if(var2 == 15) {
         this.widthPadding = var1.readUnsignedByte();
      }

   }

   public void onRead(RSBuffer var1) {
      PostHealthBar var2 = new PostHealthBar();
      var2.setHealthBar(this);
      ViewportMouse.client.getCallbacks().post(PostHealthBar.class, var2);
   }

   public int getHealthBarFrontSpriteId() {
      return this.frontSpriteID;
   }

   public int getHealthScale() {
      return this.width;
   }

   public void setPadding(int var1) {
      this.widthPadding = var1;
   }

   public RSSprite getHealthBarFrontSprite() {
      return this.method4471();
   }

   public RSSprite getHealthBarBackSprite() {
      return this.method4485();
   }
}
