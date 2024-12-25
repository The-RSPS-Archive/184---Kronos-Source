package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("x")
public class WorldMapIcon_0 extends AbstractWorldMapIcon {
   @ObfuscatedName("sm")
   static short[] foundItemIds;
   @ObfuscatedName("be")
   @ObfuscatedSignature(
      signature = "Llp;"
   )
   static IndexedSprite worldSelectLeftSprite;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Law;"
   )
   final WorldMapLabel label;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 155630147
   )
   final int subHeight;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 704139389
   )
   final int subWidth;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -189436837
   )
   final int element;

   @ObfuscatedSignature(
      signature = "(Lhb;Lhb;ILaw;)V"
   )
   WorldMapIcon_0(Coord var1, Coord var2, int var3, WorldMapLabel var4) {
      super(var1, var2);
      this.element = var3;
      this.label = var4;
      WorldMapElement var5 = Decimator.method2498(this.vmethod2277());
      Sprite var6 = var5.method4369(false);
      if(var6 != null) {
         this.subWidth = var6.subWidth;
         this.subHeight = var6.subHeight;
      } else {
         this.subWidth = 0;
         this.subHeight = 0;
      }

   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)Law;",
      garbageValue = "13348956"
   )
   WorldMapLabel vmethod2273() {
      return this.label;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "806304417"
   )
   int vmethod2257() {
      return this.subHeight;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-175699296"
   )
   int vmethod2269() {
      return this.subWidth;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "-79"
   )
   public int vmethod2277() {
      return this.element;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(Lhp;Ljava/lang/String;Ljava/lang/String;I)[Llp;",
      garbageValue = "858097598"
   )
   public static IndexedSprite[] method193(AbstractArchive var0, String var1, String var2) {
      int var3 = var0.method4059(var1);
      int var4 = var0.method4039(var3, var2);
      return class313.method5840(var0, var3, var4);
   }

   @ObfuscatedName("jd")
   @ObfuscatedSignature(
      signature = "(Lho;I)V",
      garbageValue = "-1181739841"
   )
   static final void method192(Widget var0) {
      int var1 = var0.contentType;
      if(var1 == 324) {
         if(Client.field1108 == -1) {
            Client.field1108 = var0.spriteId2;
            Client.field1109 = var0.spriteId;
         }

         if(Client.playerAppearance.isFemale) {
            var0.spriteId2 = Client.field1108;
         } else {
            var0.spriteId2 = Client.field1109;
         }

      } else if(var1 == 325) {
         if(Client.field1108 == -1) {
            Client.field1108 = var0.spriteId2;
            Client.field1109 = var0.spriteId;
         }

         if(Client.playerAppearance.isFemale) {
            var0.spriteId2 = Client.field1109;
         } else {
            var0.spriteId2 = Client.field1108;
         }

      } else if(var1 == 327) {
         var0.modelAngleX = 150;
         var0.modelAngleY = (int)(Math.sin((double)Client.cycle / 40.0D) * 256.0D) & 2047;
         var0.modelType = 5;
         var0.modelId = 0;
      } else if(var1 == 328) {
         var0.modelAngleX = 150;
         var0.modelAngleY = (int)(Math.sin((double)Client.cycle / 40.0D) * 256.0D) & 2047;
         var0.modelType = 5;
         var0.modelId = 1;
      }
   }
}
