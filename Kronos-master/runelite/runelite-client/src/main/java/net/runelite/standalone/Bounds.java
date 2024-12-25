package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ll")
public class Bounds {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 724783571
   )
   public int lowY;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -1354833317
   )
   public int highY;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 1669121479
   )
   public int highX;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -479130629
   )
   public int lowX;

   public Bounds(int var1, int var2, int var3, int var4) {
      this.method6251(var1, var2);
      this.method6252(var3, var4);
   }

   public Bounds(int var1, int var2) {
      this(0, 0, var1, var2);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "1712715973"
   )
   public void method6252(int var1, int var2) {
      this.highX = var1;
      this.highY = var2;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "769635987"
   )
   int method6257() {
      return this.lowX + this.highX;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "59"
   )
   int method6253() {
      return this.highY + this.lowY;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(Lll;Lll;I)V",
      garbageValue = "-1654040739"
   )
   void method6255(Bounds var1, Bounds var2) {
      var2.lowY = this.lowY;
      var2.highY = this.highY;
      if(this.lowY < var1.lowY) {
         var2.highY -= var1.lowY - this.lowY;
         var2.lowY = var1.lowY;
      }

      if(var2.method6253() > var1.method6253()) {
         var2.highY -= var2.method6253() - var1.method6253();
      }

      if(var2.highY < 0) {
         var2.highY = 0;
      }

   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lll;Lll;B)V",
      garbageValue = "-1"
   )
   void method6266(Bounds var1, Bounds var2) {
      var2.lowX = this.lowX;
      var2.highX = this.highX;
      if(this.lowX < var1.lowX) {
         var2.highX -= var1.lowX - this.lowX;
         var2.lowX = var1.lowX;
      }

      if(var2.method6257() > var1.method6257()) {
         var2.highX -= var2.method6257() - var1.method6257();
      }

      if(var2.highX < 0) {
         var2.highX = 0;
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lll;Lll;I)V",
      garbageValue = "2079875928"
   )
   public void method6254(Bounds var1, Bounds var2) {
      this.method6266(var1, var2);
      this.method6255(var1, var2);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "166241873"
   )
   public void method6251(int var1, int var2) {
      this.lowX = var1;
      this.lowY = var2;
   }

   public String toString() {
      return null;
   }

   public String aak() {
      return null;
   }

   public String aae() {
      return null;
   }

   public String aah() {
      return null;
   }
}
