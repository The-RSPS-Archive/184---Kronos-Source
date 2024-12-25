package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("lj")
public class WorldMapArchiveLoader {
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   AbstractArchive archive;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -1787474783
   )
   int percentLoaded;
   @ObfuscatedName("r")
   String cacheName;
   @ObfuscatedName("m")
   boolean loaded;

   @ObfuscatedSignature(
      signature = "(Lhp;)V"
   )
   WorldMapArchiveLoader(AbstractArchive var1) {
      this.percentLoaded = 0;
      this.loaded = false;
      this.archive = var1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "1015665534"
   )
   int method6238() {
      if(this.percentLoaded < 33) {
         if(!this.archive.method4022(WorldMapCacheName.field248.name, this.cacheName)) {
            return this.percentLoaded;
         }

         this.percentLoaded = 33;
      }

      if(this.percentLoaded == 33) {
         if(this.archive.method4108(WorldMapCacheName.field242.name, this.cacheName) && !this.archive.method4022(WorldMapCacheName.field242.name, this.cacheName)) {
            return this.percentLoaded;
         }

         this.percentLoaded = 66;
      }

      if(this.percentLoaded == 66) {
         if(!this.archive.method4022(this.cacheName, WorldMapCacheName.field246.name)) {
            return this.percentLoaded;
         }

         this.percentLoaded = 100;
         this.loaded = true;
      }

      return this.percentLoaded;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1707041754"
   )
   int method6241() {
      return this.percentLoaded;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "1749947467"
   )
   boolean method6240() {
      return this.loaded;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;B)V",
      garbageValue = "98"
   )
   void method6239(String var1) {
      if(var1 != null && !var1.isEmpty()) {
         if(var1 != this.cacheName) {
            this.cacheName = var1;
            this.percentLoaded = 0;
            this.loaded = false;
            this.method6238();
         }
      }
   }
}
