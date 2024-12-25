package net.runelite.standalone;

import java.io.IOException;
import java.io.OutputStream;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kv")
public class BufferedSink implements Runnable {
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "Lbr;"
   )
   static LoginScreenAnimation loginScreenRunesAnimation;
   @ObfuscatedName("n")
   OutputStream outputStream;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -1646863699
   )
   int limit;
   @ObfuscatedName("q")
   IOException exception;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 205486611
   )
   int position;
   @ObfuscatedName("u")
   byte[] buffer;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 1047270953
   )
   int capacity;
   @ObfuscatedName("z")
   Thread thread;
   @ObfuscatedName("m")
   boolean closed;

   BufferedSink(OutputStream var1, int var2) {
      this.position = 0;
      this.limit = 0;
      this.outputStream = var1;
      this.capacity = var2 + 1;
      this.buffer = new byte[this.capacity];
      this.thread = new Thread(this);
      this.thread.setDaemon(true);
      this.thread.start();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "([BIIB)V",
      garbageValue = "30"
   )
   void method5789(byte[] var1, int var2, int var3) throws IOException {
      if(var3 >= 0 && var2 >= 0 && var3 + var2 <= var1.length) {
         synchronized(this) {
            if(this.exception != null) {
               throw new IOException(this.exception.toString());
            }

            int var5;
            if(this.position <= this.limit) {
               var5 = this.capacity - this.limit + this.position - 1;
            } else {
               var5 = this.position - this.limit - 1;
            }

            if(var5 < var3) {
               throw new IOException("");
            }

            if(var3 + this.limit <= this.capacity) {
               System.arraycopy(var1, var2, this.buffer, this.limit, var3);
            } else {
               int var6 = this.capacity - this.limit;
               System.arraycopy(var1, var2, this.buffer, this.limit, var6);
               System.arraycopy(var1, var6 + var2, this.buffer, 0, var3 - var6);
            }

            this.limit = (var3 + this.limit) % this.capacity;
            this.notifyAll();
         }

      } else {
         throw new IOException();
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1627292871"
   )
   void method5783() {
      synchronized(this) {
         this.closed = true;
         this.notifyAll();
      }

      try {
         this.thread.join();
      } catch (InterruptedException var3) {
         ;
      }

   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)Z",
      garbageValue = "-57"
   )
   boolean method5775() {
      if(this.closed) {
         try {
            this.outputStream.close();
            if(this.exception == null) {
               this.exception = new IOException("");
            }
         } catch (IOException var2) {
            if(this.exception == null) {
               this.exception = new IOException(var2);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void run() {
      while(true) {
         synchronized (this) {
            while (true) {
               boolean var14 = false;

               int var1;
               try {
                  var14 = true;
                  if (this.exception != null) {
                     var14 = false;
                     return;
                  }

                  if (this.position <= this.limit) {
                     var1 = this.limit - this.position;
                  } else {
                     var1 = this.capacity - this.position + this.limit;
                  }

                  if (var1 <= 0) {
                     try {
                        this.outputStream.flush();
                     } catch (IOException var19) {
                        this.exception = var19;
                        var14 = false;
                        return;
                     }

                     if (this.method5775()) {
                        var14 = false;
                        return;
                     }

                     try {
                        this.wait();
                     } catch (InterruptedException var18) {
                        ;
                     }
                     continue;
                  }

                  var14 = false;
               } finally {
                  if (var14) {
                     ;
                  }
               }

               try {
                  if (var1 + this.position <= this.capacity) {
                     this.outputStream.write(this.buffer, this.position, var1);
                  } else {
                     int var7 = this.capacity - this.position;
                     this.outputStream.write(this.buffer, this.position, var7);
                     this.outputStream.write(this.buffer, 0, var1 - var7);
                  }
               } catch (IOException var17) {
                  IOException var2 = var17;
                  synchronized (this) {
                     this.exception = var2;
                  }

                  return;
               }

               synchronized (this) {
                  this.position = (var1 + this.position) % this.capacity;
               }

               if (!this.method5775()) {
                  break;
               }

               return;
            }
         }
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lli;I)I",
      garbageValue = "-828018974"
   )
   static final int method5790(LoginType var0) {
      if(var0 == null) {
         return 12;
      } else {
         switch(var0.field3937) {
         case 0:
            return 20;
         default:
            return 12;
         }
      }
   }

   @ObfuscatedName("fm")
   @ObfuscatedSignature(
      signature = "(Lho;IIB)V",
      garbageValue = "-112"
   )
   static final void method5774(Widget var0, int var1, int var2) {
      if(Client.minimapState == 0 || Client.minimapState == 3) {
         if(!Client.isMenuOpen && (MouseHandler.MouseHandler_lastButton == 1 || !WorldMapIcon_1.mouseCam && MouseHandler.MouseHandler_lastButton == 4)) {
            SpriteMask var3 = var0.method3975(true);
            if(var3 == null) {
               return;
            }

            int var4 = MouseHandler.MouseHandler_lastPressedX - var1;
            int var5 = MouseHandler.MouseHandler_lastPressedY - var2;
            if(var3.method4191(var4, var5)) {
               var4 -= var3.width / 2;
               var5 -= var3.height / 2;
               int var6 = Client.camAngleY & 2047;
               int var7 = Rasterizer3D.Rasterizer3D_sine[var6];
               int var8 = Rasterizer3D.Rasterizer3D_cosine[var6];
               int var9 = var5 * var7 + var4 * var8 >> 11;
               int var10 = var5 * var8 - var7 * var4 >> 11;
               int var11 = var9 + class215.localPlayer.x >> 7;
               int var12 = class215.localPlayer.y * 682054857 - var10 >> 7;
               PacketBufferNode var13 = InterfaceParent.method1140(ClientPacket.field2431, Client.packetWriter.isaacCipher);
               var13.packetBuffer.writeByte(18);
               var13.packetBuffer.method5532(var11 + class215.baseX);
               var13.packetBuffer.method5530(var12 + class304.baseY);
               var13.packetBuffer.method5522(KeyHandler.KeyHandler_pressedKeys[82]?(KeyHandler.KeyHandler_pressedKeys[81]?2:1):0);
               var13.packetBuffer.writeByte(var4);
               var13.packetBuffer.writeByte(var5);
               var13.packetBuffer.method5481(Client.camAngleY);
               var13.packetBuffer.writeByte(57);
               var13.packetBuffer.writeByte(0);
               var13.packetBuffer.writeByte(0);
               var13.packetBuffer.writeByte(89);
               var13.packetBuffer.method5481(class215.localPlayer.x);
               var13.packetBuffer.method5481(class215.localPlayer.y * 682054857);
               var13.packetBuffer.writeByte(63);
               Client.packetWriter.method1622(var13);
               Client.destinationX = var11;
               Client.destinationY = var12;
            }
         }

      }
   }
}
