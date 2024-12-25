package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSSkeleton;

@ObfuscatedName("ew")
public class Skeleton extends Node implements RSSkeleton {
   @ObfuscatedName("bn")
   @ObfuscatedSignature(
      signature = "Llp;"
   )
   static IndexedSprite field1821;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -1324463493
   )
   int count;
   @ObfuscatedName("u")
   int[][] labels;
   @ObfuscatedName("v")
   int[] transformTypes;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -1879620261
   )
   int id;

   Skeleton(int var1, byte[] var2) {
      this.id = var1;
      Buffer var3 = new Buffer(var2);
      this.count = var3.readUnsignedByte();
      this.transformTypes = new int[this.count];
      this.labels = new int[this.count][];

      int var4;
      for(var4 = 0; var4 < this.count; ++var4) {
         this.transformTypes[var4] = var3.readUnsignedByte();
      }

      for(var4 = 0; var4 < this.count; ++var4) {
         this.labels[var4] = new int[var3.readUnsignedByte()];
      }

      for(var4 = 0; var4 < this.count; ++var4) {
         for(int var5 = 0; var5 < this.labels[var4].length; ++var5) {
            this.labels[var4][var5] = var3.readUnsignedByte();
         }
      }

   }

   public int getCount() {
      return this.count;
   }

   public int[] getTypes() {
      return this.transformTypes;
   }

   public int[][] getList() {
      return this.labels;
   }
}
