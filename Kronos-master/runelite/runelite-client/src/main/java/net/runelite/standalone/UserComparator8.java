package net.runelite.standalone;

import java.io.File;
import java.io.RandomAccessFile;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ea")
public class UserComparator8 extends AbstractUserComparator {
   @ObfuscatedName("z")
   final boolean reversed;

   public UserComparator8(boolean var1) {
      this.reversed = var1;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljl;Ljl;I)I",
      garbageValue = "-2001610603"
   )
   int method2879(Buddy var1, Buddy var2) {
      if(Client.worldId == var1.world) {
         if(var2.world != Client.worldId) {
            return this.reversed?-1:1;
         }
      } else if(var2.world == Client.worldId) {
         return this.reversed?1:-1;
      }

      return this.method5015(var1, var2);
   }

   public int compare(Object var1, Object var2) {
      return this.method2879((Buddy)var1, (Buddy)var2);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)Ljava/io/File;",
      garbageValue = "-1561719392"
   )
   public static File method2885(String var0) {
      if(!FileSystem.FileSystem_hasPermissions) {
         throw new RuntimeException("");
      } else {
         File var1 = (File)FileSystem.FileSystem_cacheFiles.get(var0);
         if(var1 != null) {
            return var1;
         } else {
            File var2 = new File(FileSystem.FileSystem_cacheDir, var0);
            RandomAccessFile var3 = null;

            File var10000;
            try {
               File var4 = new File(var2.getParent());
               if(!var4.exists()) {
                  throw new RuntimeException("");
               }

               var3 = new RandomAccessFile(var2, "rw");
               int var5 = var3.read();
               var3.seek(0L);
               var3.write(var5);
               var3.seek(0L);
               var3.close();
               FileSystem.FileSystem_cacheFiles.put(var0, var2);
               var10000 = var2;
            } catch (Exception var8) {
               try {
                  if(var3 != null) {
                     var3.close();
                     var3 = null;
                  }
               } catch (Exception var7) {
                  ;
               }

               throw new RuntimeException();
            }

            return var10000;
         }
      }
   }

   @ObfuscatedName("fn")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "11"
   )
   static void method2878(int var0) {
      Client.field962 = 0L;
      if(var0 >= 2) {
         Client.isResizable = true;
         Client.resizeChanged(-1);
      } else {
         Client.isResizable = false;
         Client.resizeChanged(-1);
      }

      if(class256.method4656() == 1) {
         ViewportMouse.client.method955(765, 503, 1753820196);
      } else {
         ViewportMouse.client.method955(7680, 2160, 1859431020);
      }

      if(Client.gameState >= 25) {
         PacketBufferNode var1 = InterfaceParent.method1140(ClientPacket.field2412, Client.packetWriter.isaacCipher);
         var1.packetBuffer.writeByte(class256.method4656());
         var1.packetBuffer.method5481(FloorDecoration.canvasWidth);
         var1.packetBuffer.method5481(WallDecoration.canvasHeight);
         Client.packetWriter.method1622(var1);
      }

   }
}
