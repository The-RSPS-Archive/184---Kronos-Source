package net.runelite.standalone;

import java.awt.Component;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSCollisionMap;

@ObfuscatedName("fa")
public class CollisionMap implements RSCollisionMap {
   @ObfuscatedName("ad")
   public int[][] flags;
   @ObfuscatedName("al")
   @ObfuscatedGetter(
      intValue = -888202655
   )
   int ySize;
   @ObfuscatedName("ao")
   @ObfuscatedGetter(
      intValue = 1308792903
   )
   int xSize;
   @ObfuscatedName("as")
   @ObfuscatedGetter(
      intValue = -518382829
   )
   int yInset;
   @ObfuscatedName("ax")
   @ObfuscatedGetter(
      intValue = 2078354851
   )
   int xInset;

   public CollisionMap(int var1, int var2) {
      this.xInset = 0;
      this.yInset = 0;
      this.xSize = var1;
      this.ySize = var2;
      this.flags = new int[this.xSize][this.ySize];
      this.method3352();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IIIIZI)V",
      garbageValue = "1400659630"
   )
   public void method3332(int var1, int var2, int var3, int var4, boolean var5) {
      var1 -= this.xInset;
      var2 -= this.yInset;
      if(var3 == 0) {
         if(var4 == 0) {
            this.method3369(var1, var2, 128);
            this.method3369(var1 - 1, var2, 8);
         }

         if(var4 == 1) {
            this.method3369(var1, var2, 2);
            this.method3369(var1, var2 + 1, 32);
         }

         if(var4 == 2) {
            this.method3369(var1, var2, 8);
            this.method3369(var1 + 1, var2, 128);
         }

         if(var4 == 3) {
            this.method3369(var1, var2, 32);
            this.method3369(var1, var2 - 1, 2);
         }
      }

      if(var3 == 1 || var3 == 3) {
         if(var4 == 0) {
            this.method3369(var1, var2, 1);
            this.method3369(var1 - 1, var2 + 1, 16);
         }

         if(var4 == 1) {
            this.method3369(var1, var2, 4);
            this.method3369(var1 + 1, var2 + 1, 64);
         }

         if(var4 == 2) {
            this.method3369(var1, var2, 16);
            this.method3369(var1 + 1, var2 - 1, 1);
         }

         if(var4 == 3) {
            this.method3369(var1, var2, 64);
            this.method3369(var1 - 1, var2 - 1, 4);
         }
      }

      if(var3 == 2) {
         if(var4 == 0) {
            this.method3369(var1, var2, 130);
            this.method3369(var1 - 1, var2, 8);
            this.method3369(var1, var2 + 1, 32);
         }

         if(var4 == 1) {
            this.method3369(var1, var2, 10);
            this.method3369(var1, var2 + 1, 32);
            this.method3369(var1 + 1, var2, 128);
         }

         if(var4 == 2) {
            this.method3369(var1, var2, 40);
            this.method3369(var1 + 1, var2, 128);
            this.method3369(var1, var2 - 1, 2);
         }

         if(var4 == 3) {
            this.method3369(var1, var2, 160);
            this.method3369(var1, var2 - 1, 2);
            this.method3369(var1 - 1, var2, 8);
         }
      }

      if(var5) {
         if(var3 == 0) {
            if(var4 == 0) {
               this.method3369(var1, var2, 65536);
               this.method3369(var1 - 1, var2, 4096);
            }

            if(var4 == 1) {
               this.method3369(var1, var2, 1024);
               this.method3369(var1, var2 + 1, 16384);
            }

            if(var4 == 2) {
               this.method3369(var1, var2, 4096);
               this.method3369(var1 + 1, var2, 65536);
            }

            if(var4 == 3) {
               this.method3369(var1, var2, 16384);
               this.method3369(var1, var2 - 1, 1024);
            }
         }

         if(var3 == 1 || var3 == 3) {
            if(var4 == 0) {
               this.method3369(var1, var2, 512);
               this.method3369(var1 - 1, var2 + 1, 8192);
            }

            if(var4 == 1) {
               this.method3369(var1, var2, 2048);
               this.method3369(var1 + 1, var2 + 1, 32768);
            }

            if(var4 == 2) {
               this.method3369(var1, var2, 8192);
               this.method3369(var1 + 1, var2 - 1, 512);
            }

            if(var4 == 3) {
               this.method3369(var1, var2, 32768);
               this.method3369(var1 - 1, var2 - 1, 2048);
            }
         }

         if(var3 == 2) {
            if(var4 == 0) {
               this.method3369(var1, var2, 66560);
               this.method3369(var1 - 1, var2, 4096);
               this.method3369(var1, var2 + 1, 16384);
            }

            if(var4 == 1) {
               this.method3369(var1, var2, 5120);
               this.method3369(var1, var2 + 1, 16384);
               this.method3369(var1 + 1, var2, 65536);
            }

            if(var4 == 2) {
               this.method3369(var1, var2, 20480);
               this.method3369(var1 + 1, var2, 65536);
               this.method3369(var1, var2 - 1, 1024);
            }

            if(var4 == 3) {
               this.method3369(var1, var2, 81920);
               this.method3369(var1, var2 - 1, 1024);
               this.method3369(var1 - 1, var2, 4096);
            }
         }
      }

   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(IIIB)V",
      garbageValue = "-3"
   )
   void method3369(int var1, int var2, int var3) {
      this.flags[var1][var2] |= var3;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(IIIIZI)V",
      garbageValue = "1060838313"
   )
   public void method3338(int var1, int var2, int var3, int var4, boolean var5) {
      var1 -= this.xInset;
      var2 -= this.yInset;
      if(var3 == 0) {
         if(var4 == 0) {
            this.method3344(var1, var2, 128);
            this.method3344(var1 - 1, var2, 8);
         }

         if(var4 == 1) {
            this.method3344(var1, var2, 2);
            this.method3344(var1, var2 + 1, 32);
         }

         if(var4 == 2) {
            this.method3344(var1, var2, 8);
            this.method3344(var1 + 1, var2, 128);
         }

         if(var4 == 3) {
            this.method3344(var1, var2, 32);
            this.method3344(var1, var2 - 1, 2);
         }
      }

      if(var3 == 1 || var3 == 3) {
         if(var4 == 0) {
            this.method3344(var1, var2, 1);
            this.method3344(var1 - 1, var2 + 1, 16);
         }

         if(var4 == 1) {
            this.method3344(var1, var2, 4);
            this.method3344(var1 + 1, var2 + 1, 64);
         }

         if(var4 == 2) {
            this.method3344(var1, var2, 16);
            this.method3344(var1 + 1, var2 - 1, 1);
         }

         if(var4 == 3) {
            this.method3344(var1, var2, 64);
            this.method3344(var1 - 1, var2 - 1, 4);
         }
      }

      if(var3 == 2) {
         if(var4 == 0) {
            this.method3344(var1, var2, 130);
            this.method3344(var1 - 1, var2, 8);
            this.method3344(var1, var2 + 1, 32);
         }

         if(var4 == 1) {
            this.method3344(var1, var2, 10);
            this.method3344(var1, var2 + 1, 32);
            this.method3344(var1 + 1, var2, 128);
         }

         if(var4 == 2) {
            this.method3344(var1, var2, 40);
            this.method3344(var1 + 1, var2, 128);
            this.method3344(var1, var2 - 1, 2);
         }

         if(var4 == 3) {
            this.method3344(var1, var2, 160);
            this.method3344(var1, var2 - 1, 2);
            this.method3344(var1 - 1, var2, 8);
         }
      }

      if(var5) {
         if(var3 == 0) {
            if(var4 == 0) {
               this.method3344(var1, var2, 65536);
               this.method3344(var1 - 1, var2, 4096);
            }

            if(var4 == 1) {
               this.method3344(var1, var2, 1024);
               this.method3344(var1, var2 + 1, 16384);
            }

            if(var4 == 2) {
               this.method3344(var1, var2, 4096);
               this.method3344(var1 + 1, var2, 65536);
            }

            if(var4 == 3) {
               this.method3344(var1, var2, 16384);
               this.method3344(var1, var2 - 1, 1024);
            }
         }

         if(var3 == 1 || var3 == 3) {
            if(var4 == 0) {
               this.method3344(var1, var2, 512);
               this.method3344(var1 - 1, var2 + 1, 8192);
            }

            if(var4 == 1) {
               this.method3344(var1, var2, 2048);
               this.method3344(var1 + 1, var2 + 1, 32768);
            }

            if(var4 == 2) {
               this.method3344(var1, var2, 8192);
               this.method3344(var1 + 1, var2 - 1, 512);
            }

            if(var4 == 3) {
               this.method3344(var1, var2, 32768);
               this.method3344(var1 - 1, var2 - 1, 2048);
            }
         }

         if(var3 == 2) {
            if(var4 == 0) {
               this.method3344(var1, var2, 66560);
               this.method3344(var1 - 1, var2, 4096);
               this.method3344(var1, var2 + 1, 16384);
            }

            if(var4 == 1) {
               this.method3344(var1, var2, 5120);
               this.method3344(var1, var2 + 1, 16384);
               this.method3344(var1 + 1, var2, 65536);
            }

            if(var4 == 2) {
               this.method3344(var1, var2, 20480);
               this.method3344(var1 + 1, var2, 65536);
               this.method3344(var1, var2 - 1, 1024);
            }

            if(var4 == 3) {
               this.method3344(var1, var2, 81920);
               this.method3344(var1, var2 - 1, 1024);
               this.method3344(var1 - 1, var2, 4096);
            }
         }
      }

   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "-119630021"
   )
   public void method3346(int var1, int var2) {
      var1 -= this.xInset;
      var2 -= this.yInset;
      this.flags[var1][var2] |= 262144;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "-691975667"
   )
   public void method3335(int var1, int var2) {
      var1 -= this.xInset;
      var2 -= this.yInset;
      this.flags[var1][var2] |= 2097152;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(IIIIZI)V",
      garbageValue = "1277713715"
   )
   public void method3334(int var1, int var2, int var3, int var4, boolean var5) {
      int var6 = 256;
      if(var5) {
         var6 += 131072;
      }

      var1 -= this.xInset;
      var2 -= this.yInset;

      for(int var7 = var1; var7 < var3 + var1; ++var7) {
         if(var7 >= 0 && var7 < this.xSize) {
            for(int var8 = var2; var8 < var2 + var4; ++var8) {
               if(var8 >= 0 && var8 < this.ySize) {
                  this.method3369(var7, var8, var6);
               }
            }
         }
      }

   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(IIIB)V",
      garbageValue = "6"
   )
   void method3344(int var1, int var2, int var3) {
      this.flags[var1][var2] &= ~var3;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-51"
   )
   public void method3352() {
      for(int var1 = 0; var1 < this.xSize; ++var1) {
         for(int var2 = 0; var2 < this.ySize; ++var2) {
            if(var1 != 0 && var2 != 0 && var1 < this.xSize - 5 && var2 < this.ySize - 5) {
               this.flags[var1][var2] = 16777216;
            } else {
               this.flags[var1][var2] = 16777215;
            }
         }
      }

   }

   public int[][] getFlags() {
      return this.flags;
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(IIS)V",
      garbageValue = "-6856"
   )
   public void method3341(int var1, int var2) {
      var1 -= this.xInset;
      var2 -= this.yInset;
      this.flags[var1][var2] &= -262145;
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(IIIIIZI)V",
      garbageValue = "-476929644"
   )
   public void method3339(int var1, int var2, int var3, int var4, int var5, boolean var6) {
      int var7 = 256;
      if(var6) {
         var7 += 131072;
      }

      var1 -= this.xInset;
      var2 -= this.yInset;
      int var8;
      if(var5 == 1 || var5 == 3) {
         var8 = var3;
         var3 = var4;
         var4 = var8;
      }

      for(var8 = var1; var8 < var3 + var1; ++var8) {
         if(var8 >= 0 && var8 < this.xSize) {
            for(int var9 = var2; var9 < var2 + var4; ++var9) {
               if(var9 >= 0 && var9 < this.ySize) {
                  this.method3344(var8, var9, var7);
               }
            }
         }
      }

   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Ljava/awt/Component;I)V",
      garbageValue = "-1662439433"
   )
   static void method3368(Component var0) {
      var0.removeMouseListener(MouseHandler.MouseHandler_instance);
      var0.removeMouseMotionListener(MouseHandler.MouseHandler_instance);
      var0.removeFocusListener(MouseHandler.MouseHandler_instance);
      MouseHandler.MouseHandler_currentButtonVolatile = 0;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I[BLkg;I)V",
      garbageValue = "1190241493"
   )
   static void method3360(int var0, byte[] var1, ArchiveDisk var2) {
      ArchiveDiskAction var3 = new ArchiveDiskAction();
      var3.type = 0;
      var3.key = (long)var0;
      var3.data = var1;
      var3.archiveDisk = var2;
      NodeDeque var4 = ArchiveDiskActionHandler.ArchiveDiskActionHandler_requestQueue;
      synchronized(ArchiveDiskActionHandler.ArchiveDiskActionHandler_requestQueue) {
         ArchiveDiskActionHandler.ArchiveDiskActionHandler_requestQueue.method5105(var3);
      }

      Object var9 = ArchiveDiskActionHandler.ArchiveDiskActionHandler_lock;
      synchronized(ArchiveDiskActionHandler.ArchiveDiskActionHandler_lock) {
         if(ArchiveDiskActionHandler.field3167 == 0) {
            UserComparator5.ArchiveDiskActionHandler_thread = new Thread(new ArchiveDiskActionHandler());
            UserComparator5.ArchiveDiskActionHandler_thread.setDaemon(true);
            UserComparator5.ArchiveDiskActionHandler_thread.start();
            UserComparator5.ArchiveDiskActionHandler_thread.setPriority(5);
         }

         ArchiveDiskActionHandler.field3167 = 600;
      }

   }
}
