package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("jd")
public class Timer {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      longValue = 6292636731397039659L
   )
   long field3531;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      longValue = 5130392401661119113L
   )
   long field3535;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = 1178458711
   )
   int field3536;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      longValue = 7643347149191032091L
   )
   long field3534;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      longValue = 514076061416709505L
   )
   long field3533;
   @ObfuscatedName("v")
   public boolean field3532;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = 808032681
   )
   int field3538;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      longValue = 4462782034314037381L
   )
   long field3530;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 1068681997
   )
   int field3539;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 1318775007
   )
   int field3537;

   public Timer() {
      this.field3530 = -1L;
      this.field3531 = -1L;
      this.field3532 = false;
      this.field3533 = 0L;
      this.field3534 = 0L;
      this.field3535 = 0L;
      this.field3536 = 0;
      this.field3537 = 0;
      this.field3538 = 0;
      this.field3539 = 0;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "1"
   )
   public void method4849() {
      if(-1L != this.field3530) {
         this.field3534 = class33.method680() - this.field3530;
         this.field3530 = -1L;
      }

   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1318451763"
   )
   public void method4853() {
      this.method4851();
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(Lkl;I)V",
      garbageValue = "-1798294121"
   )
   public void method4854(Buffer var1) {
      long var2 = this.field3534;
      var2 /= 10L;
      if(var2 < 0L) {
         var2 = 0L;
      } else if(var2 > 65535L) {
         var2 = 65535L;
      }

      var1.method5481((int)var2);
      long var4 = this.field3533;
      var4 /= 10L;
      if(var4 < 0L) {
         var4 = 0L;
      } else if(var4 > 65535L) {
         var4 = 65535L;
      }

      var1.method5481((int)var4);
      long var6 = this.field3535;
      var6 /= 10L;
      if(var6 < 0L) {
         var6 = 0L;
      } else if(var6 > 65535L) {
         var6 = 65535L;
      }

      var1.method5481((int)var6);
      var1.method5481(this.field3536);
      var1.method5481(this.field3537);
      var1.method5481(this.field3538);
      var1.method5481(this.field3539);
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-832320221"
   )
   public void method4852() {
      this.field3532 = false;
      this.field3537 = 0;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1202204199"
   )
   public void method4851() {
      if(-1L != this.field3531) {
         this.field3533 = class33.method680() - this.field3531;
         this.field3531 = -1L;
      }

      ++this.field3538;
      this.field3532 = true;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-1928577406"
   )
   public void method4850(int var1) {
      this.field3531 = class33.method680();
      this.field3536 = var1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-20"
   )
   public void method4848() {
      this.field3530 = class33.method680();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IB)I",
      garbageValue = "7"
   )
   public static int method4847(int var0) {
      --var0;
      var0 |= var0 >>> 1;
      var0 |= var0 >>> 2;
      var0 |= var0 >>> 4;
      var0 |= var0 >>> 8;
      var0 |= var0 >>> 16;
      return var0 + 1;
   }
}
