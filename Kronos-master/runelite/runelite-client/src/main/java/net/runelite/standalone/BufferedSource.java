package net.runelite.standalone;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kx")
public class BufferedSource implements Runnable {
   @ObfuscatedName("n")
   InputStream inputStream;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -589477741
   )
   int limit;
   @ObfuscatedName("q")
   IOException exception;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 496960127
   )
   int position;
   @ObfuscatedName("u")
   byte[] buffer;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1837703949
   )
   int capacity;
   @ObfuscatedName("z")
   Thread thread;

   BufferedSource(InputStream var1, int var2) {
      this.position = 0;
      this.limit = 0;
      this.inputStream = var1;
      this.capacity = var2 + 1;
      this.buffer = new byte[this.capacity];
      this.thread = new Thread(this);
      this.thread.setDaemon(true);
      this.thread.start();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "-99"
   )
   int method5810() throws IOException {
      int var10000;
      synchronized(this) {
         int var2;
         if(this.position <= this.limit) {
            var2 = this.limit - this.position;
         } else {
            var2 = this.capacity - this.position + this.limit;
         }

         if(var2 <= 0 && this.exception != null) {
            throw new IOException(this.exception.toString());
         }

         this.notifyAll();
         var10000 = var2;
      }

      return var10000;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "2069219110"
   )
   void method5801() {
      synchronized(this) {
         if(this.exception == null) {
            this.exception = new IOException("");
         }

         this.notifyAll();
      }

      try {
         this.thread.join();
      } catch (InterruptedException var3) {
         ;
      }

   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "([BIII)I",
      garbageValue = "2103598010"
   )
   int method5800(byte[] var1, int var2, int var3) throws IOException {
      if(var3 >= 0 && var2 >= 0 && var3 + var2 <= var1.length) {
         int var10000;
         synchronized(this) {
            int var5;
            if(this.position <= this.limit) {
               var5 = this.limit - this.position;
            } else {
               var5 = this.capacity - this.position + this.limit;
            }

            if(var3 > var5) {
               var3 = var5;
            }

            if(var3 == 0 && this.exception != null) {
               throw new IOException(this.exception.toString());
            }

            if(var3 + this.position <= this.capacity) {
               System.arraycopy(this.buffer, this.position, var1, var2, var3);
            } else {
               int var6 = this.capacity - this.position;
               System.arraycopy(this.buffer, this.position, var1, var2, var6);
               System.arraycopy(this.buffer, 0, var1, var6 + var2, var3 - var6);
            }

            this.position = (var3 + this.position) % this.capacity;
            this.notifyAll();
            var10000 = var3;
         }

         return var10000;
      } else {
         throw new IOException();
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "1540455812"
   )
   int method5799() throws IOException {
      int var10000;
      synchronized(this) {
         if(this.limit == this.position) {
            if(this.exception != null) {
               throw new IOException(this.exception.toString());
            }

            byte var5 = -1;
            return var5;
         }

         int var2 = this.buffer[this.position] & 255;
         this.position = (this.position + 1) % this.capacity;
         this.notifyAll();
         var10000 = var2;
      }

      return var10000;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IB)Z",
      garbageValue = "-42"
   )
   boolean method5797(int var1) throws IOException {
      if(var1 == 0) {
         return true;
      } else if(var1 > 0 && var1 < this.capacity) {
         boolean var10000;
         synchronized(this) {
            int var3;
            if(this.position <= this.limit) {
               var3 = this.limit - this.position;
            } else {
               var3 = this.capacity - this.position + this.limit;
            }

            if(var3 < var1) {
               if(this.exception != null) {
                  throw new IOException(this.exception.toString());
               }

               this.notifyAll();
               var10000 = false;
               return var10000;
            }

            var10000 = true;
         }

         return var10000;
      } else {
         throw new IOException();
      }
   }

   public void run() {
      while(true) {
         int var1;
         synchronized(this) {
            while(true) {
               if(this.exception != null) {
                  return;
               }

               if(this.position == 0) {
                  var1 = this.capacity - this.limit - 1;
               } else if(this.position <= this.limit) {
                  var1 = this.capacity - this.limit;
               } else {
                  var1 = this.position - this.limit - 1;
               }

               if(var1 > 0) {
                  break;
               }

               try {
                  this.wait();
               } catch (InterruptedException var10) {
                  ;
               }
            }
         }

         int var7;
         try {
            var7 = this.inputStream.read(this.buffer, this.limit, var1);
            if(var7 == -1) {
               throw new EOFException();
            }
         } catch (IOException var11) {
            IOException var3 = var11;
            synchronized(this) {
               this.exception = var3;
            }

            return;
         }

         synchronized(this) {
            this.limit = (var7 + this.limit) % this.capacity;
         }
      }
   }
}
