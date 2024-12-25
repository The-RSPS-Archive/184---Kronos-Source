package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("fb")
public class UserComparator5 extends AbstractUserComparator {
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "[[Lho;"
   )
   public static Widget[][] Widget_interfaceComponents;
   @ObfuscatedName("r")
   static Thread ArchiveDiskActionHandler_thread;
   @ObfuscatedName("z")
   final boolean reversed;

   public UserComparator5(boolean var1) {
      this.reversed = var1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljl;Ljl;I)I",
      garbageValue = "-1400201048"
   )
   int method3370(Buddy var1, Buddy var2) {
      if(var1.world != 0) {
         if(var2.world == 0) {
            return this.reversed?-1:1;
         }
      } else if(var2.world != 0) {
         return this.reversed?1:-1;
      }

      return this.method5015(var1, var2);
   }

   public int compare(Object var1, Object var2) {
      return this.method3370((Buddy)var1, (Buddy)var2);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lkl;Llb;I)Llb;",
      garbageValue = "1757701513"
   )
   static final IterableNodeHashTable method3374(Buffer var0, IterableNodeHashTable var1) {
      int var2 = var0.readUnsignedByte();
      int var3;
      if(var1 == null) {
         var3 = Timer.method4847(var2);
         var1 = new IterableNodeHashTable(var3);
      }

      for(var3 = 0; var3 < var2; ++var3) {
         boolean var4 = var0.readUnsignedByte() == 1;
         int var5 = var0.method5500();
         Object var6;
         if(var4) {
            var6 = new ObjectNode(var0.readString());
         } else {
            var6 = new IntegerNode(var0.readInt());
         }

         var1.put((Node)var6, (long)var5);
      }

      return var1;
   }

   @ObfuscatedName("af")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;B)I",
      garbageValue = "103"
   )
   public static int method3375(String var0) {
      return var0.length() + 2;
   }
}
