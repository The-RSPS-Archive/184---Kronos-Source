package net.runelite.standalone;

import java.io.EOFException;
import java.io.IOException;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("mm")
public class BufferedFile {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lmh;"
   )
   AccessFile accessFile;
   @ObfuscatedName("p")
   byte[] writeBuffer;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      longValue = -8408813098273597985L
   )
   long writeBufferOffset;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1845108509
   )
   int readBufferLength;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      longValue = -7294963934648719297L
   )
   long readBufferOffset;
   @ObfuscatedName("v")
   byte[] readBuffer;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      longValue = -7862153769092129291L
   )
   long offset;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      longValue = 3243026924650541047L
   )
   long fileOffset;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      longValue = 8830743471394433983L
   )
   long length;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      longValue = 3070804450108675653L
   )
   long fileLength;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 1704738159
   )
   int writeBufferLength;

   @ObfuscatedSignature(
      signature = "(Lmh;II)V"
   )
   public BufferedFile(AccessFile var1, int var2, int var3) throws IOException {
      this.readBufferOffset = -1L;
      this.writeBufferOffset = -1L;
      this.writeBufferLength = 0;
      this.accessFile = var1;
      this.length = this.fileLength = var1.method5();
      this.readBuffer = new byte[var2];
      this.writeBuffer = new byte[var3];
      this.offset = 0L;
   }

   @ObfuscatedName("n")
   public void method22(long var1) throws IOException {
      if(var1 < 0L) {
         throw new IOException("");
      } else {
         this.offset = var1;
      }
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "77"
   )
   void method28() throws IOException {
      this.readBufferLength = 0;
      if(this.offset != this.fileOffset) {
         this.accessFile.method2(this.offset);
         this.fileOffset = this.offset;
      }

      int var2;
      for(this.readBufferOffset = this.offset; this.readBufferLength < this.readBuffer.length; this.readBufferLength += var2) {
         int var1 = this.readBuffer.length - this.readBufferLength;
         if(var1 > 200000000) {
            var1 = 200000000;
         }

         var2 = this.accessFile.method6(this.readBuffer, this.readBufferLength, var1);
         if(var2 == -1) {
            break;
         }

         this.fileOffset += (long)var2;
      }

   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "([BIII)V",
      garbageValue = "2016597591"
   )
   public void method29(byte[] var1, int var2, int var3) throws IOException {
      try {
         if((long)var3 + this.offset > this.length) {
            this.length = (long)var3 + this.offset;
         }

         if(-1L != this.writeBufferOffset && (this.offset < this.writeBufferOffset || this.offset > (long)this.writeBufferLength + this.writeBufferOffset)) {
            this.method30();
         }

         if(-1L != this.writeBufferOffset && (long)var3 + this.offset > this.writeBufferOffset + (long)this.writeBuffer.length) {
            int var4 = (int)((long)this.writeBuffer.length - (this.offset - this.writeBufferOffset));
            System.arraycopy(var1, var2, this.writeBuffer, (int)(this.offset - this.writeBufferOffset), var4);
            this.offset += (long)var4;
            var2 += var4;
            var3 -= var4;
            this.writeBufferLength = this.writeBuffer.length;
            this.method30();
         }

         if(var3 > this.writeBuffer.length) {
            if(this.fileOffset != this.offset) {
               this.accessFile.method2(this.offset);
               this.fileOffset = this.offset;
            }

            this.accessFile.method14(var1, var2, var3);
            this.fileOffset += (long)var3;
            if(this.fileOffset > this.fileLength) {
               this.fileLength = this.fileOffset;
            }

            long var9 = -1L;
            long var6 = -1L;
            if(this.offset >= this.readBufferOffset && this.offset < (long)this.readBufferLength + this.readBufferOffset) {
               var9 = this.offset;
            } else if(this.readBufferOffset >= this.offset && this.readBufferOffset < this.offset + (long)var3) {
               var9 = this.readBufferOffset;
            }

            if((long)var3 + this.offset > this.readBufferOffset && (long)var3 + this.offset <= this.readBufferOffset + (long)this.readBufferLength) {
               var6 = this.offset + (long)var3;
            } else if(this.readBufferOffset + (long)this.readBufferLength > this.offset && this.readBufferOffset + (long)this.readBufferLength <= this.offset + (long)var3) {
               var6 = (long)this.readBufferLength + this.readBufferOffset;
            }

            if(var9 > -1L && var6 > var9) {
               int var8 = (int)(var6 - var9);
               System.arraycopy(var1, (int)(var9 + (long)var2 - this.offset), this.readBuffer, (int)(var9 - this.readBufferOffset), var8);
            }

            this.offset += (long)var3;
            return;
         }

         if(var3 > 0) {
            if(this.writeBufferOffset == -1L) {
               this.writeBufferOffset = this.offset;
            }

            System.arraycopy(var1, var2, this.writeBuffer, (int)(this.offset - this.writeBufferOffset), var3);
            this.offset += (long)var3;
            if(this.offset - this.writeBufferOffset > (long)this.writeBufferLength) {
               this.writeBufferLength = (int)(this.offset - this.writeBufferOffset);
            }

            return;
         }
      } catch (IOException var12) {
         this.fileOffset = -1L;
         throw var12;
      }

   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "([BIIB)V",
      garbageValue = "-41"
   )
   public void method38(byte[] var1, int var2, int var3) throws IOException {
      label142: {
         try {
            if(var3 + var2 > var1.length) {
               throw new ArrayIndexOutOfBoundsException(var3 + var2 - var1.length);
            }

            if(this.writeBufferOffset == -1L || this.offset < this.writeBufferOffset || (long)var3 + this.offset > (long)this.writeBufferLength + this.writeBufferOffset) {
               long var4 = this.offset;
               int var7 = var3;
               int var8;
               if(this.offset >= this.readBufferOffset && this.offset < this.readBufferOffset + (long)this.readBufferLength) {
                  var8 = (int)((long)this.readBufferLength - (this.offset - this.readBufferOffset));
                  if(var8 > var3) {
                     var8 = var3;
                  }

                  System.arraycopy(this.readBuffer, (int)(this.offset - this.readBufferOffset), var1, var2, var8);
                  this.offset += (long)var8;
                  var2 += var8;
                  var3 -= var8;
               }

               if(var3 > this.readBuffer.length) {
                  this.accessFile.method2(this.offset);

                  for(this.fileOffset = this.offset; var3 > 0; var3 -= var8) {
                     var8 = this.accessFile.method6(var1, var2, var3);
                     if(var8 == -1) {
                        break;
                     }

                     this.fileOffset += (long)var8;
                     this.offset += (long)var8;
                     var2 += var8;
                  }
               } else if(var3 > 0) {
                  this.method28();
                  var8 = var3;
                  if(var3 > this.readBufferLength) {
                     var8 = this.readBufferLength;
                  }

                  System.arraycopy(this.readBuffer, 0, var1, var2, var8);
                  var2 += var8;
                  var3 -= var8;
                  this.offset += (long)var8;
               }

               if(-1L == this.writeBufferOffset) {
                  break label142;
               }

               if(this.writeBufferOffset > this.offset && var3 > 0) {
                  var8 = var2 + (int)(this.writeBufferOffset - this.offset);
                  if(var8 > var3 + var2) {
                     var8 = var3 + var2;
                  }

                  while(var2 < var8) {
                     var1[var2++] = 0;
                     --var3;
                     ++this.offset;
                  }
               }

               long var13 = -1L;
               long var10 = -1L;
               if(this.writeBufferOffset >= var4 && this.writeBufferOffset < (long)var7 + var4) {
                  var13 = this.writeBufferOffset;
               } else if(var4 >= this.writeBufferOffset && var4 < this.writeBufferOffset + (long)this.writeBufferLength) {
                  var13 = var4;
               }

               if(this.writeBufferOffset + (long)this.writeBufferLength > var4 && (long)this.writeBufferLength + this.writeBufferOffset <= var4 + (long)var7) {
                  var10 = (long)this.writeBufferLength + this.writeBufferOffset;
               } else if(var4 + (long)var7 > this.writeBufferOffset && var4 + (long)var7 <= this.writeBufferOffset + (long)this.writeBufferLength) {
                  var10 = (long)var7 + var4;
               }

               if(var13 > -1L && var10 > var13) {
                  int var12 = (int)(var10 - var13);
                  System.arraycopy(this.writeBuffer, (int)(var13 - this.writeBufferOffset), var1, (int)(var13 - var4) + var2, var12);
                  if(var10 > this.offset) {
                     var3 = (int)((long)var3 - (var10 - this.offset));
                     this.offset = var10;
                  }
               }
               break label142;
            }

            System.arraycopy(this.writeBuffer, (int)(this.offset - this.writeBufferOffset), var1, var2, var3);
            this.offset += (long)var3;
         } catch (IOException var16) {
            this.fileOffset = -1L;
            throw var16;
         }

         return;
      }

      if(var3 > 0) {
         throw new EOFException();
      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "([BI)V",
      garbageValue = "588547682"
   )
   public void method31(byte[] var1) throws IOException {
      this.method38(var1, 0, var1.length);
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(S)J",
      garbageValue = "15865"
   )
   public long method25() {
      return this.length;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-55"
   )
   public void method23() throws IOException {
      this.method30();
      this.accessFile.method18();
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "928727943"
   )
   void method30() throws IOException {
      if(-1L != this.writeBufferOffset) {
         if(this.fileOffset != this.writeBufferOffset) {
            this.accessFile.method2(this.writeBufferOffset);
            this.fileOffset = this.writeBufferOffset;
         }

         this.accessFile.method14(this.writeBuffer, 0, this.writeBufferLength);
         this.fileOffset += (long)(this.writeBufferLength * -1599618673) * 1704738159L;
         if(this.fileOffset > this.fileLength) {
            this.fileLength = this.fileOffset;
         }

         long var1 = -1L;
         long var3 = -1L;
         if(this.writeBufferOffset >= this.readBufferOffset && this.writeBufferOffset < this.readBufferOffset + (long)this.readBufferLength) {
            var1 = this.writeBufferOffset;
         } else if(this.readBufferOffset >= this.writeBufferOffset && this.readBufferOffset < this.writeBufferOffset + (long)this.writeBufferLength) {
            var1 = this.readBufferOffset;
         }

         if((long)this.writeBufferLength + this.writeBufferOffset > this.readBufferOffset && (long)this.writeBufferLength + this.writeBufferOffset <= (long)this.readBufferLength + this.readBufferOffset) {
            var3 = (long)this.writeBufferLength + this.writeBufferOffset;
         } else if((long)this.readBufferLength + this.readBufferOffset > this.writeBufferOffset && this.readBufferOffset + (long)this.readBufferLength <= this.writeBufferOffset + (long)this.writeBufferLength) {
            var3 = (long)this.readBufferLength + this.readBufferOffset;
         }

         if(var1 > -1L && var3 > var1) {
            int var5 = (int)(var3 - var1);
            System.arraycopy(this.writeBuffer, (int)(var1 - this.writeBufferOffset), this.readBuffer, (int)(var1 - this.readBufferOffset), var5);
         }

         this.writeBufferOffset = -1L;
         this.writeBufferLength = 0;
      }

   }
}
