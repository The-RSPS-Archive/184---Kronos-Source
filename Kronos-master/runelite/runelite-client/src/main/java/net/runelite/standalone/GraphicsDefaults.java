package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kw")
public class GraphicsDefaults {
   @ObfuscatedName("fz")
   @ObfuscatedSignature(
      signature = "Lkn;"
   )
   static Font fontPlain12;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -245940859
   )
   public int field3784;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 1274199689
   )
   public int field3783;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = 423516137
   )
   public int field3789;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = -1090087377
   )
   public int field3787;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 936076039
   )
   public int headIconsPk;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 2030709025
   )
   public int mapScenes;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = -1828299055
   )
   public int field3791;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 1913101075
   )
   public int compass;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 1539500843
   )
   public int field3793;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 495147529
   )
   public int field3794;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 1649613365
   )
   public int field3790;

   public GraphicsDefaults() {
      this.compass = -1;
      this.field3784 = -1;
      this.mapScenes = -1;
      this.headIconsPk = -1;
      this.field3787 = -1;
      this.field3783 = -1;
      this.field3789 = -1;
      this.field3790 = -1;
      this.field3791 = -1;
      this.field3794 = -1;
      this.field3793 = -1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lhp;I)V",
      garbageValue = "1722575536"
   )
   public void method5792(AbstractArchive var1) {
      byte[] var2 = var1.method4027(DefaultsGroup.field3746.group);
      Buffer var3 = new Buffer(var2);

      while(true) {
         int var4 = var3.readUnsignedByte();
         if(var4 == 0) {
            return;
         }

         switch(var4) {
         case 1:
            var3.method5500();
            break;
         case 2:
            this.compass = var3.method5507();
            this.field3784 = var3.method5507();
            this.mapScenes = var3.method5507();
            this.headIconsPk = var3.method5507();
            this.field3787 = var3.method5507();
            this.field3783 = var3.method5507();
            this.field3789 = var3.method5507();
            this.field3790 = var3.method5507();
            this.field3791 = var3.method5507();
            this.field3794 = var3.method5507();
            this.field3793 = var3.method5507();
         }
      }
   }
}
