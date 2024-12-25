package net.runelite.standalone;

import java.io.File;
import java.util.Comparator;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bb")
public class GrandExchangeOfferOwnWorldComparator implements Comparator {
   @ObfuscatedName("qs")
   @ObfuscatedGetter(
      intValue = 683599059
   )
   static int field344;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive KitDefinition_modelsArchive;
   @ObfuscatedName("r")
   public static File cacheDir;
   @ObfuscatedName("dg")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   static Archive archive12;
   @ObfuscatedName("fg")
   @ObfuscatedSignature(
      signature = "Lkn;"
   )
   static Font fontPlain11;
   @ObfuscatedName("hq")
   @ObfuscatedGetter(
      intValue = 1137329779
   )
   static int cameraX;
   @ObfuscatedName("ji")
   @ObfuscatedSignature(
      signature = "Lho;"
   )
   static Widget field345;
   @ObfuscatedName("z")
   boolean filterWorlds;

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lm;Lm;I)I",
      garbageValue = "-1661947065"
   )
   int method811(GrandExchangeEvent var1, GrandExchangeEvent var2) {
      if(var2.world == var1.world) {
         return 0;
      } else {
         if(this.filterWorlds) {
            if(Client.worldId == var1.world) {
               return -1;
            }

            if(var2.world == Client.worldId) {
               return 1;
            }
         }

         return var1.world < var2.world?-1:1;
      }
   }

   public int compare(Object var1, Object var2) {
      return this.method811((GrandExchangeEvent)var1, (GrandExchangeEvent)var2);
   }

   public boolean equals(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(IS)Lbe;",
      garbageValue = "-12583"
   )
   static Message method823(int var0) {
      return (Message)Messages.Messages_hashTable.method6061((long)var0);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IB)Liz;",
      garbageValue = "53"
   )
   public static ObjectDefinition getObjectDefinition(int var0) {
      ObjectDefinition var1 = (ObjectDefinition)ObjectDefinition.ObjectDefinition_cached.method3032((long)var0);
      if(var1 != null) {
         return var1;
      } else {
         byte[] var2 = ObjectDefinition.ObjectDefinition_archive.method4020(6, var0, (short)-5157);
         var1 = new ObjectDefinition();
         var1.id = var0;
         if(var2 != null) {
            var1.method4717(new Buffer(var2));
            //TODO: Custom Object Definitions
            var1.postDecode();
         }

         var1.method4728();
         if(var1.isSolid) {
            var1.interactType = 0;
            var1.boolean1 = false;
         }

         ObjectDefinition.ObjectDefinition_cached.method3034(var1, (long)var0);
         return var1;
      }
   }
}
