package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSPcmStream;
import net.runelite.rs.api.RSPcmStreamMixer;

@ObfuscatedName("ca")
public class PcmStreamMixer extends PcmStream implements RSPcmStreamMixer {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Ljv;"
   )
   NodeDeque field743;
   @ObfuscatedName("u")
   int field742;
   @ObfuscatedName("v")
   int field744;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Ljv;"
   )
   NodeDeque subStreams;

   public PcmStreamMixer() {
      this.subStreams = new NodeDeque();
      this.field743 = new NodeDeque();
      this.field744 = 0;
      this.field742 = -1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Ldc;)V"
   )
   public final synchronized void method1476(PcmStream var1) {
      var1.method3497();
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "()Ldc;"
   )
   protected PcmStream vmethod3775() {
      return (PcmStream)this.subStreams.method5103();
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "()Ldc;"
   )
   protected PcmStream vmethod3794() {
      return (PcmStream)this.subStreams.method5126();
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(Lde;)V"
   )
   void method1478(PcmStreamMixerListener var1) {
      var1.method3497();
      var1.method2334();
      Node var2 = this.field743.sentinel.previous;
      if(var2 == this.field743.sentinel) {
         this.field742 = -1;
      } else {
         this.field742 = ((PcmStreamMixerListener)var2).field1350;
      }

   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lfx;Lde;)V"
   )
   void method1500(Node var1, PcmStreamMixerListener var2) {
      while(this.field743.sentinel != var1 && ((PcmStreamMixerListener)var1).field1350 <= var2.field1350) {
         var1 = var1.previous;
      }

      NodeDeque.method5130(var2, var1);
      this.field742 = ((PcmStreamMixerListener)this.field743.sentinel.previous).field1350;
   }

   @ObfuscatedName("v")
   void method1477() {
      if(this.field744 > 0) {
         for(PcmStreamMixerListener var1 = (PcmStreamMixerListener)this.field743.method5103(); var1 != null; var1 = (PcmStreamMixerListener)this.field743.method5126()) {
            var1.field1350 -= this.field744;
         }

         this.field742 -= this.field744;
         this.field744 = 0;
      }

   }

   @ObfuscatedName("y")
   public final synchronized void vmethod3777(int[] var1, int var2, int var3) {
      do {
         if(this.field742 < 0) {
            this.method1484(var1, var2, var3);
            return;
         }

         if(var3 + this.field744 < this.field742) {
            this.field744 += var3;
            this.method1484(var1, var2, var3);
            return;
         }

         int var4 = this.field742 - this.field744;
         this.method1484(var1, var2, var4);
         var2 += var4;
         var3 -= var4;
         this.field744 += var4;
         this.method1477();
         PcmStreamMixerListener var5 = (PcmStreamMixerListener)this.field743.method5103();
         synchronized(var5) {
            int var7 = var5.method2337();
            if(var7 < 0) {
               var5.field1350 = 0;
               this.method1478(var5);
            } else {
               var5.field1350 = var7;
               this.method1500(var5.previous, var5);
            }
         }
      } while(var3 != 0);

   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ldc;)V"
   )
   public final synchronized void method1475(PcmStream var1) {
      this.subStreams.method5106(var1);
   }

   @ObfuscatedName("b")
   void method1486(int var1) {
      for(PcmStream var2 = (PcmStream)this.subStreams.method5103(); var2 != null; var2 = (PcmStream)this.subStreams.method5126()) {
         var2.vmethod3778(var1);
      }

   }

   @ObfuscatedName("c")
   public final synchronized void vmethod3778(int var1) {
      do {
         if(this.field742 < 0) {
            this.method1486(var1);
            return;
         }

         if(this.field744 + var1 < this.field742) {
            this.field744 += var1;
            this.method1486(var1);
            return;
         }

         int var2 = this.field742 - this.field744;
         this.method1486(var2);
         var1 -= var2;
         this.field744 += var2;
         this.method1477();
         PcmStreamMixerListener var3 = (PcmStreamMixerListener)this.field743.method5103();
         synchronized(var3) {
            int var5 = var3.method2337();
            if(var5 < 0) {
               var3.field1350 = 0;
               this.method1478(var3);
            } else {
               var3.field1350 = var5;
               this.method1500(var3.previous, var3);
            }
         }
      } while(var1 != 0);

   }

   public void addSubStream(RSPcmStream var1) {
      this.method1475((PcmStream)var1);
   }

   @ObfuscatedName("i")
   void method1484(int[] var1, int var2, int var3) {
      for(PcmStream var4 = (PcmStream)this.subStreams.method5103(); var4 != null; var4 = (PcmStream)this.subStreams.method5126()) {
         var4.method2327(var1, var2, var3);
      }

   }

   @ObfuscatedName("m")
   protected int vmethod3787() {
      return 0;
   }
}
