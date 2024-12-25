package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("di")
public class VorbisMapping {
   @ObfuscatedName("n")
   int mappingMux;
   @ObfuscatedName("u")
   int[] submapResidue;
   @ObfuscatedName("v")
   int[] submapFloor;
   @ObfuscatedName("z")
   int submaps;

   VorbisMapping() {
      VorbisSample.method2135(16);
      this.submaps = VorbisSample.method2126() != 0?VorbisSample.method2135(4) + 1:1;
      if(VorbisSample.method2126() != 0) {
         VorbisSample.method2135(8);
      }

      VorbisSample.method2135(2);
      if(this.submaps > 1) {
         this.mappingMux = VorbisSample.method2135(4);
      }

      this.submapFloor = new int[this.submaps];
      this.submapResidue = new int[this.submaps];

      for(int var1 = 0; var1 < this.submaps; ++var1) {
         VorbisSample.method2135(8);
         this.submapFloor[var1] = VorbisSample.method2135(8);
         this.submapResidue[var1] = VorbisSample.method2135(8);
      }

   }
}
