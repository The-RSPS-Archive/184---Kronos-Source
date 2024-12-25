package net.runelite.standalone;

import java.util.zip.CRC32;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSArchive;

@ObfuscatedName("ie")
public class Archive extends AbstractArchive implements RSArchive {
   @ObfuscatedName("aw")
   static CRC32 Archive_crc;
   @ObfuscatedName("jn")
   @ObfuscatedSignature(
      signature = "Ldz;"
   )
   static TextureProvider textureProvider;
   @ObfuscatedName("aa")
   @ObfuscatedGetter(
      intValue = 2033402119
   )
   int indexCrc;
   @ObfuscatedName("ac")
   boolean field3138;
   @ObfuscatedName("ap")
   @ObfuscatedGetter(
      intValue = -302073813
   )
   int indexVersion;
   @ObfuscatedName("ar")
   @ObfuscatedGetter(
      intValue = -630367829
   )
   int field3146;
   @ObfuscatedName("az")
   volatile boolean[] validGroups;
   @ObfuscatedName("d")
   @ObfuscatedSignature(
      signature = "Lkg;"
   )
   ArchiveDisk masterDisk;
   @ObfuscatedName("j")
   @ObfuscatedSignature(
      signature = "Lkg;"
   )
   ArchiveDisk archiveDisk;
   @ObfuscatedName("k")
   volatile boolean field3140;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = -341991117
   )
   int index;

   static {
      Archive_crc = new CRC32();
   }

   @ObfuscatedSignature(
      signature = "(Lkg;Lkg;IZZZ)V"
   )
   public Archive(ArchiveDisk var1, ArchiveDisk var2, int var3, boolean var4, boolean var5, boolean var6) {
      super(var4, var5);
      this.field3140 = false;
      this.field3138 = false;
      this.field3146 = -1;
      this.archiveDisk = var1;
      this.masterDisk = var2;
      this.index = var3;
      this.field3138 = var6;
      int var8 = this.index;
      if(AbstractWorldMapIcon.NetCache_reference != null) {
         AbstractWorldMapIcon.NetCache_reference.offset = var8 * 8 + 5;
         int var9 = AbstractWorldMapIcon.NetCache_reference.readInt();
         int var10 = AbstractWorldMapIcon.NetCache_reference.readInt();
         this.method4269(var9, var10);
      } else {
         WorldMapDecoration.method5198((Archive)null, 255, 255, 0, (byte)0, true);
         NetCache.NetCache_archives[var8] = this;
      }

   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "15"
   )
   void vmethod4267(int var1) {
      int var2 = this.index;
      long var3 = (long)((var2 << 16) + var1);
      NetFileRequest var5 = (NetFileRequest)NetCache.NetCache_pendingWrites.method6346(var3);
      if(var5 != null) {
         NetCache.NetCache_pendingWritesQueue.method4253(var5);
      }

   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "848617854"
   )
   void vmethod4268(int var1) {
      if(this.archiveDisk != null && this.validGroups != null && this.validGroups[var1]) {
         WorldMapArea.method424(var1, this.archiveDisk, this);
      } else {
         WorldMapDecoration.method5198(this, this.index, var1, super.groupCrcs[var1], (byte)2, true);
      }

   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(II)I",
      garbageValue = "-2030453035"
   )
   int vmethod4272(int var1) {
      if(super.groups[var1] != null) {
         return 100;
      } else if(this.validGroups[var1]) {
         return 100;
      } else {
         int var3 = this.index;
         long var4 = (long)((var3 << 16) + var1);
         int var2;
         if(WorldMapEvent.NetCache_currentResponse != null && WorldMapEvent.NetCache_currentResponse.key == var4) {
            var2 = FaceNormal.NetCache_responseArchiveBuffer.offset * 99 / (FaceNormal.NetCache_responseArchiveBuffer.array.length - WorldMapEvent.NetCache_currentResponse.padding) + 1;
         } else {
            var2 = 0;
         }

         return var2;
      }
   }

   public int getIndex() {
      return this.index;
   }

   @ObfuscatedName("dc")
   @ObfuscatedSignature(
      signature = "(I[BZZI)V",
      garbageValue = "1316980907"
   )
   void method4297(int var1, byte[] var2, boolean var3, boolean var4) {
      if(var3) {
         if(this.field3140) {
            throw new RuntimeException();
         }

         if(this.masterDisk != null) {
            CollisionMap.method3360(this.index, var2, this.masterDisk);
         }

         this.method4018(var2);
         this.method4295();
      } else {
         var2[var2.length - 2] = (byte)(super.groupVersions[var1] >> 8);
         var2[var2.length - 1] = (byte)super.groupVersions[var1];
         if(this.archiveDisk != null) {
            CollisionMap.method3360(var1, var2, this.archiveDisk);
            this.validGroups[var1] = true;
         }

         if(var4) {
            super.groups[var1] = GrandExchangeEvents.method94(var2, false);
         }
      }

   }

   @ObfuscatedName("di")
   @ObfuscatedSignature(
      signature = "(Lkg;I[BZB)V",
      garbageValue = "1"
   )
   public void method4287(ArchiveDisk var1, int var2, byte[] var3, boolean var4) {
      int var5;
      if(var1 == this.masterDisk) {
         if(this.field3140) {
            throw new RuntimeException();
         }

         if(var3 == null) {
            WorldMapDecoration.method5198(this, 255, this.index, this.indexCrc, (byte)0, true);
            return;
         }

         Archive_crc.reset();
         Archive_crc.update(var3, 0, var3.length);
         var5 = (int)Archive_crc.getValue();
         if(var5 != this.indexCrc) {
            WorldMapDecoration.method5198(this, 255, this.index, this.indexCrc, (byte)0, true);
            return;
         }

         Buffer var9 = new Buffer(MusicPatch.method3750(var3));
         int var7 = var9.readUnsignedByte();
         if(var7 != 5 && var7 != 6) {
            throw new RuntimeException(var7 + "," + this.index + "," + var2);
         }

         int var8 = 0;
         if(var7 >= 6) {
            var8 = var9.readInt();
         }

         if(var8 != this.indexVersion) {
            WorldMapDecoration.method5198(this, 255, this.index, this.indexCrc, (byte)0, true);
            return;
         }

         this.method4018(var3);
         this.method4295();
      } else {
         if(!var4 && var2 == this.field3146) {
            this.field3140 = true;
         }

         if(var3 == null || var3.length <= 2) {
            this.validGroups[var2] = false;
            if(this.field3138 || var4) {
               WorldMapDecoration.method5198(this, this.index, var2, super.groupCrcs[var2], (byte)2, var4);
            }

            return;
         }

         Archive_crc.reset();
         Archive_crc.update(var3, 0, var3.length - 2);
         var5 = (int)Archive_crc.getValue();
         int var6 = ((var3[var3.length - 2] & 255) << 8) + (var3[var3.length - 1] & 255);
         if(var5 != super.groupCrcs[var2] || var6 != super.groupVersions[var2]) {
            this.validGroups[var2] = false;
            if(this.field3138 || var4) {
               WorldMapDecoration.method5198(this, this.index, var2, super.groupCrcs[var2], (byte)2, var4);
            }

            return;
         }

         this.validGroups[var2] = true;
         if(var4) {
            super.groups[var2] = GrandExchangeEvents.method94(var3, false);
         }
      }

   }

   @ObfuscatedName("dk")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "8"
   )
   void method4295() {
      this.validGroups = new boolean[super.groups.length];

      int var1;
      for(var1 = 0; var1 < this.validGroups.length; ++var1) {
         this.validGroups[var1] = false;
      }

      if(this.archiveDisk == null) {
         this.field3140 = true;
      } else {
         this.field3146 = -1;

         for(var1 = 0; var1 < this.validGroups.length; ++var1) {
            if(super.fileCounts[var1] > 0) {
               ArchiveDisk var2 = this.archiveDisk;
               ArchiveDiskAction var4 = new ArchiveDiskAction();
               var4.type = 1;
               var4.key = (long)var1;
               var4.archiveDisk = var2;
               var4.archive = this;
               NodeDeque var5 = ArchiveDiskActionHandler.ArchiveDiskActionHandler_requestQueue;
               synchronized(ArchiveDiskActionHandler.ArchiveDiskActionHandler_requestQueue) {
                  ArchiveDiskActionHandler.ArchiveDiskActionHandler_requestQueue.method5105(var4);
               }

               Object var10 = ArchiveDiskActionHandler.ArchiveDiskActionHandler_lock;
               synchronized(ArchiveDiskActionHandler.ArchiveDiskActionHandler_lock) {
                  if(ArchiveDiskActionHandler.field3167 == 0) {
                     UserComparator5.ArchiveDiskActionHandler_thread = new Thread(new ArchiveDiskActionHandler());
                     UserComparator5.ArchiveDiskActionHandler_thread.setDaemon(true);
                     UserComparator5.ArchiveDiskActionHandler_thread.start();
                     UserComparator5.ArchiveDiskActionHandler_thread.setPriority(5);
                  }

                  ArchiveDiskActionHandler.field3167 = 600;
               }

               this.field3146 = var1;
            }
         }

         if(this.field3146 == -1) {
            this.field3140 = true;
         }

      }
   }

   @ObfuscatedName("do")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "727894645"
   )
   public int method4275() {
      int var1 = 0;
      int var2 = 0;

      int var3;
      for(var3 = 0; var3 < super.groups.length; ++var3) {
         if(super.fileCounts[var3] > 0) {
            var1 += 100;
            var2 += this.vmethod4272(var3);
         }
      }

      if(var1 == 0) {
         return 100;
      } else {
         var3 = var2 * 100 / var1;
         return var3;
      }
   }

   @ObfuscatedName("dr")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "2035998705"
   )
   public boolean method4265() {
      return this.field3140;
   }

   @ObfuscatedName("ds")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "684167120"
   )
   void method4269(int var1, int var2) {
      this.indexCrc = var1;
      this.indexVersion = var2;
      if(this.masterDisk != null) {
         WorldMapArea.method424(this.index, this.masterDisk, this);
      } else {
         WorldMapDecoration.method5198(this, 255, this.index, this.indexCrc, (byte)0, true);
      }

   }

   @ObfuscatedName("du")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "1744835603"
   )
   public int method4270() {
      if(this.field3140) {
         return 100;
      } else if(super.groups != null) {
         return 99;
      } else {
         int var2 = this.index;
         long var3 = (long)(var2 + 16711680);
         int var1;
         if(WorldMapEvent.NetCache_currentResponse != null && WorldMapEvent.NetCache_currentResponse.key == var3) {
            var1 = FaceNormal.NetCache_responseArchiveBuffer.offset * 99 / (FaceNormal.NetCache_responseArchiveBuffer.array.length - WorldMapEvent.NetCache_currentResponse.padding) + 1;
         } else {
            var1 = 0;
         }

         int var5 = var1;
         if(var1 >= 100) {
            var5 = 99;
         }

         return var5;
      }
   }

   @ObfuscatedName("dx")
   @ObfuscatedSignature(
      signature = "(II)Z",
      garbageValue = "-433634856"
   )
   public boolean method4273(int var1) {
      return this.validGroups[var1];
   }

   @ObfuscatedName("dz")
   @ObfuscatedSignature(
      signature = "(II)Z",
      garbageValue = "805361618"
   )
   public boolean method4274(int var1) {
      return this.method4042(var1) != null;
   }

   @ObfuscatedName("jq")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-212508230"
   )
   static final void method4308() {
      for(int var0 = 0; var0 < Players.Players_count; ++var0) {
         Player var1 = Client.players[Players.Players_indices[var0]];
         var1.method1122();
      }

   }

   @ObfuscatedName("km")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "26372698"
   )
   static void method4280() {
      if(StudioGame.field2468 != null) {
         Client.field1113 = Client.cycle;
         StudioGame.field2468.method4231();

         for(int var0 = 0; var0 < Client.players.length; ++var0) {
            if(Client.players[var0] != null) {
               StudioGame.field2468.method4232((Client.players[var0].x >> 7) + class215.baseX, (Client.players[var0].y * 682054857 >> 7) + class304.baseY);
            }
         }
      }

   }
}
