package net.runelite.standalone;

import java.lang.management.GarbageCollectorMXBean;
import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("jr")
public abstract class AbstractUserComparator implements Comparator {
   @ObfuscatedName("ag")
   static GarbageCollectorMXBean garbageCollector;
   @ObfuscatedName("n")
   Comparator nextComparator;

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(Ljh;Ljh;B)I",
      garbageValue = "-35"
   )
   protected final int method5015(User var1, User var2) {
      return this.nextComparator == null?0:this.nextComparator.compare(var1, var2);
   }

   public boolean equals(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(Ljava/util/Comparator;B)V",
      garbageValue = "0"
   )
   final void method5007(Comparator var1) {
      if(this.nextComparator == null) {
         this.nextComparator = var1;
      } else if(this.nextComparator instanceof AbstractUserComparator) {
         ((AbstractUserComparator)this.nextComparator).method5007(var1);
      }

   }
}
