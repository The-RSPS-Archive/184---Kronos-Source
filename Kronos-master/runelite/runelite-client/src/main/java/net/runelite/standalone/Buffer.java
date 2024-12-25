package net.runelite.standalone;

import java.math.BigInteger;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSBuffer;

@ObfuscatedName("kl")
public class Buffer extends Node implements RSBuffer {
   public static BigInteger exponent;
   @ObfuscatedName("i")
   static long[] crc64Table;
   @ObfuscatedName("m")
   static int[] crc32Table;
   @ObfuscatedName("p")
   public byte[] array;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -757431099
   )
   public int offset;

   static {
      crc32Table = new int[256];

      int var2;
      for(int var1 = 0; var1 < 256; ++var1) {
         int var0 = var1;

         for(var2 = 0; var2 < 8; ++var2) {
            if((var0 & 1) == 1) {
               var0 = var0 >>> 1 ^ -306674912;
            } else {
               var0 >>>= 1;
            }
         }

         crc32Table[var1] = var0;
      }

      crc64Table = new long[256];

      for(var2 = 0; var2 < 256; ++var2) {
         long var4 = (long)var2;

         for(int var3 = 0; var3 < 8; ++var3) {
            if((var4 & 1L) == 1L) {
               var4 = var4 >>> 1 ^ -3932672073523589310L;
            } else {
               var4 >>>= 1;
            }
         }

         crc64Table[var2] = var4;
      }

      rl$$clinit();
   }

   public Buffer(int var1) {
      this.array = class33.method679(var1);
      this.offset = 0;
   }

   public Buffer(byte[] var1) {
      this.array = var1;
      this.offset = 0;
   }

   @ObfuscatedName("ae")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "-77"
   )
   public void write24BitInteger(int var1) {
      this.array[++this.offset - 1] = (byte)(var1 >> 16);
      this.array[++this.offset - 1] = (byte)(var1 >> 8);
      this.array[++this.offset - 1] = (byte)var1;
   }

   @ObfuscatedName("ag")
   public void method5485(long var1) {
      this.array[++this.offset - 1] = (byte)((int)(var1 >> 56));
      this.array[++this.offset - 1] = (byte)((int)(var1 >> 48));
      this.array[++this.offset - 1] = (byte)((int)(var1 >> 40));
      this.array[++this.offset - 1] = (byte)((int)(var1 >> 32));
      this.array[++this.offset - 1] = (byte)((int)(var1 >> 24));
      this.array[++this.offset - 1] = (byte)((int)(var1 >> 16));
      this.array[++this.offset - 1] = (byte)((int)(var1 >> 8));
      this.array[++this.offset - 1] = (byte)((int)var1);
   }

   @ObfuscatedName("ah")
   public void method5484(long var1) {
      this.array[++this.offset - 1] = (byte)((int)(var1 >> 40));
      this.array[++this.offset - 1] = (byte)((int)(var1 >> 32));
      this.array[++this.offset - 1] = (byte)((int)(var1 >> 24));
      this.array[++this.offset - 1] = (byte)((int)(var1 >> 16));
      this.array[++this.offset - 1] = (byte)((int)(var1 >> 8));
      this.array[++this.offset - 1] = (byte)((int)var1);
   }

   @ObfuscatedName("aj")
   @ObfuscatedSignature(
      signature = "(ZI)V",
      garbageValue = "246663976"
   )
   public void method5486(boolean var1) {
      this.writeByte(var1?1:0);
   }

   @ObfuscatedName("ak")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-44"
   )
   public void method5479() {
      if(this.array != null) {
         class93.method2214(this.array);
      }

      this.array = null;
   }

   @ObfuscatedName("am")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "2090177372"
   )
   public void method5481(int var1) {
      this.array[++this.offset - 1] = (byte)(var1 >> 8);
      this.array[++this.offset - 1] = (byte)var1;
   }

   @ObfuscatedName("aq")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "939885766"
   )
   public void writeByte(int var1) {
      this.array[++this.offset - 1] = (byte)var1;
   }

   @ObfuscatedName("at")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;B)V",
      garbageValue = "-30"
   )
   public void method5488(String var1) {
      int var2 = var1.indexOf(0);
      if(var2 >= 0) {
         throw new IllegalArgumentException("");
      } else {
         this.array[++this.offset - 1] = 0;
         this.offset += ServerBuild.method4164(var1, 0, var1.length(), this.array, this.offset);
         this.array[++this.offset - 1] = 0;
      }
   }

   @ObfuscatedName("au")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)V",
      garbageValue = "-1991648587"
   )
   public void writeString(String var1) {
      int var2 = var1.indexOf(0);
      if(var2 >= 0) {
         throw new IllegalArgumentException("");
      } else {
         this.offset += ServerBuild.method4164(var1, 0, var1.length(), this.array, this.offset);
         this.array[++this.offset - 1] = 0;
      }
   }

   @ObfuscatedName("av")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "110"
   )
   public void writeInt(int var1) {
      this.array[++this.offset - 1] = (byte)(var1 >> 24);
      this.array[++this.offset - 1] = (byte)(var1 >> 16);
      this.array[++this.offset - 1] = (byte)(var1 >> 8);
      this.array[++this.offset - 1] = (byte)var1;
   }

   @ObfuscatedName("ba")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-349808050"
   )
   public int g2s() {
      this.offset += 2;
      int var1 = (this.array[this.offset - 1] & 255) + ((this.array[this.offset - 2] & 255) << 8);
      if(var1 > 32767) {
         var1 -= 65536;
      }

      return var1;
   }

   @ObfuscatedName("bb")
   @ObfuscatedSignature(
      signature = "(I)Ljava/lang/String;",
      garbageValue = "-2144032387"
   )
   public String method5504() {
      if(this.array[this.offset] == 0) {
         ++this.offset;
         return null;
      } else {
         return this.readString();
      }
   }

   @ObfuscatedName("bc")
   @ObfuscatedSignature(
      signature = "(I)B",
      garbageValue = "1910091347"
   )
   public byte readByte() {
      return this.array[++this.offset - 1];
   }

   @ObfuscatedName("bd")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "2127950431"
   )
   public void method5491(int var1) {
      if(var1 < 0) {
         throw new IllegalArgumentException();
      } else {
         this.array[this.offset - var1 - 4] = (byte)(var1 >> 24);
         this.array[this.offset - var1 - 3] = (byte)(var1 >> 16);
         this.array[this.offset - var1 - 2] = (byte)(var1 >> 8);
         this.array[this.offset - var1 - 1] = (byte)var1;
      }
   }

   @ObfuscatedName("be")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "562340943"
   )
   public int readInt() {
      this.offset += 4;
      return ((this.array[this.offset - 3] & 255) << 16) + (this.array[this.offset - 1] & 255) + ((this.array[this.offset - 2] & 255) << 8) + ((this.array[this.offset - 4] & 255) << 24);
   }

   @ObfuscatedName("bf")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1994836425"
   )
   public int method5511() {
      int var1 = 0;

      int var2;
      for(var2 = this.readUnsignedSmart(); var2 == 32767; var2 = this.readUnsignedSmart()) {
         var1 += 32767;
      }

      var1 += var2;
      return var1;
   }

   @ObfuscatedName("bg")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "1856744569"
   )
   public int method5512() {
      return this.array[this.offset] < 0?this.readInt() & Integer.MAX_VALUE:this.readUnsignedShort();
   }

   @ObfuscatedName("bh")
   @ObfuscatedSignature(
      signature = "([BIII)V",
      garbageValue = "-1646863699"
   )
   public void writeBytes(byte[] var1, int var2, int var3) {
      for(int var4 = var2; var4 < var3 + var2; ++var4) {
         this.array[++this.offset - 1] = var1[var4];
      }

   }

   @ObfuscatedName("bi")
   @ObfuscatedSignature(
      signature = "(B)Z",
      garbageValue = "27"
   )
   public boolean method5503() {
      return (this.readUnsignedByte() & 1) == 1;
   }

   @ObfuscatedName("bj")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-2079556313"
   )
   public void method5594(int var1) {
      if(var1 >= 0 && var1 < 128) {
         this.writeByte(var1);
      } else if(var1 >= 0 && var1 < 32768) {
         this.method5481(var1 + 32768);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedName("bk")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "81074739"
   )
   public int readUnsignedShort() {
      this.offset += 2;
      return (this.array[this.offset - 1] & 255) + ((this.array[this.offset - 2] & 255) << 8);
   }

   @ObfuscatedName("bl")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1038515838"
   )
   public int readUnsignedSmart() {
      int var1 = this.array[this.offset] & 255;
      return var1 < 128?this.readUnsignedByte():this.readUnsignedShort() - 32768;
   }

   @ObfuscatedName("bm")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "15"
   )
   public void method5492(int var1) {
      if(var1 >= 0 && var1 <= 65535) {
         this.array[this.offset - var1 - 2] = (byte)(var1 >> 8);
         this.array[this.offset - var1 - 1] = (byte)var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedName("bn")
   @ObfuscatedSignature(
      signature = "(S)I",
      garbageValue = "20427"
   )
   public int method5500() {
      this.offset += 3;
      return ((this.array[this.offset - 3] & 255) << 16) + (this.array[this.offset - 1] & 255) + ((this.array[this.offset - 2] & 255) << 8);
   }

   @ObfuscatedName("bo")
   @ObfuscatedSignature(
      signature = "([BIIB)V",
      garbageValue = "17"
   )
   public void method5572(byte[] var1, int var2, int var3) {
      for(int var4 = var2; var4 < var3 + var2; ++var4) {
         var1[var4] = this.array[++this.offset - 1];
      }

   }

   @ObfuscatedName("bp")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1177667309"
   )
   public int method5514() {
      byte var1 = this.array[++this.offset - 1];

      int var2;
      for(var2 = 0; var1 < 0; var1 = this.array[++this.offset - 1]) {
         var2 = (var2 | var1 & 127) << 7;
      }

      return var2 | var1;
   }

   @ObfuscatedName("bq")
   @ObfuscatedSignature(
      signature = "(S)Ljava/lang/String;",
      garbageValue = "27564"
   )
   public String method5478() {
      byte var1 = this.array[++this.offset - 1];
      if(var1 != 0) {
         throw new IllegalStateException("");
      } else {
         int var2 = this.method5514();
         if(var2 + this.offset > this.array.length) {
            throw new IllegalStateException("");
         } else {
            String var3 = UserComparator6.method3506(this.array, this.offset, var2);
            this.offset += var2;
            return var3;
         }
      }
   }

   @ObfuscatedName("br")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "990550889"
   )
   public int method5509() {
      int var1 = this.array[this.offset] & 255;
      return var1 < 128?this.readUnsignedByte() - 64:this.readUnsignedShort() - 49152;
   }

   @ObfuscatedName("bs")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "831943486"
   )
   public void method5495(int var1) {
      if((var1 & -128) != 0) {
         if((var1 & -16384) != 0) {
            if((var1 & -2097152) != 0) {
               if((var1 & -268435456) != 0) {
                  this.writeByte(var1 >>> 28 | 128);
               }

               this.writeByte(var1 >>> 21 | 128);
            }

            this.writeByte(var1 >>> 14 | 128);
         }

         this.writeByte(var1 >>> 7 | 128);
      }

      this.writeByte(var1 & 127);
   }

   @ObfuscatedName("bt")
   @ObfuscatedSignature(
      signature = "(I)Ljava/lang/String;",
      garbageValue = "2000277562"
   )
   public String readString() {
      int var1 = this.offset;

      while(this.array[++this.offset - 1] != 0) {
         ;
      }

      int var2 = this.offset - var1 - 1;
      return var2 == 0?"":ArchiveLoader.method1297(this.array, var1, var2);
   }

   @ObfuscatedName("bu")
   @ObfuscatedSignature(
      signature = "(I)J",
      garbageValue = "1896849386"
   )
   public long method5502() {
      long var1 = (long)this.readInt() & 4294967295L;
      long var3 = (long)this.readInt() & 4294967295L;
      return var3 + (var1 << 32);
   }

   @ObfuscatedName("bv")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "554833635"
   )
   public void method5493(int var1) {
      if(var1 >= 0 && var1 <= 255) {
         this.array[this.offset - var1 - 1] = (byte)var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedName("bw")
   @ObfuscatedSignature(
      signature = "(S)I",
      garbageValue = "-29498"
   )
   public int method5507() {
      if(this.array[this.offset] < 0) {
         return this.readInt() & Integer.MAX_VALUE;
      } else {
         int var1 = this.readUnsignedShort();
         return var1 == 32767?-1:var1;
      }
   }

   @ObfuscatedName("bx")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/CharSequence;I)V",
      garbageValue = "1234483841"
   )
   public void method5497(CharSequence var1) {
      int var2 = class170.method3448(var1);
      this.array[++this.offset - 1] = 0;
      this.method5495(var2);
      this.offset += AbstractWorldMapIcon.method718(this.array, this.offset, var1);
   }

   @ObfuscatedName("by")
   @ObfuscatedSignature(
      signature = "(I)Ljava/lang/String;",
      garbageValue = "-2099106824"
   )
   public String method5556() {
      byte var1 = this.array[++this.offset - 1];
      if(var1 != 0) {
         throw new IllegalStateException("");
      } else {
         int var2 = this.offset;

         while(this.array[++this.offset - 1] != 0) {
            ;
         }

         int var3 = this.offset - var2 - 1;
         return var3 == 0?"":ArchiveLoader.method1297(this.array, var2, var3);
      }
   }

   @ObfuscatedName("bz")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "14"
   )
   public int readUnsignedByte() {
      return this.array[++this.offset - 1] & 255;
   }

   @ObfuscatedName("ca")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "2084520877"
   )
   public int readShortA() {
      this.offset += 2;
      return (this.array[this.offset - 1] - 128 & 255) + ((this.array[this.offset - 2] & 255) << 8);
   }

   @ObfuscatedName("cb")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-108626677"
   )
   public int readByteA() {
      return this.array[++this.offset - 1] - 128 & 255;
   }

   @ObfuscatedName("cc")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-900775149"
   )
   public void method5530(int var1) {
      this.array[++this.offset - 1] = (byte)var1;
      this.array[++this.offset - 1] = (byte)(var1 >> 8);
   }

   @ObfuscatedName("cd")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "1145773072"
   )
   public void method5521(int var1) {
      this.array[++this.offset - 1] = (byte)(var1 + 128);
   }

   @ObfuscatedName("ce")
   @ObfuscatedSignature(
      signature = "(I)B",
      garbageValue = "-598630050"
   )
   public byte method5529() {
      return (byte)(128 - this.array[++this.offset - 1]);
   }

   @ObfuscatedName("cf")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "1941268038"
   )
   public int readLEShortA() {
      this.offset += 2;
      return ((this.array[this.offset - 1] & 255) << 8) + (this.array[this.offset - 2] - 128 & 255);
   }

   @ObfuscatedName("cg")
   @ObfuscatedSignature(
      signature = "(II)I",
      garbageValue = "-123385623"
   )
   public int method5482(int var1) {
      int var2 = LoginScreenAnimation.method1293(this.array, var1, this.offset);
      this.writeInt(var2);
      return var2;
   }

   @ObfuscatedName("ch")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-1821828851"
   )
   public void method5539(int var1) {
      this.array[++this.offset - 1] = (byte)var1;
      this.array[++this.offset - 1] = (byte)(var1 >> 8);
      this.array[++this.offset - 1] = (byte)(var1 >> 16);
   }

   @ObfuscatedName("ci")
   @ObfuscatedSignature(
      signature = "(I)B",
      garbageValue = "-961755482"
   )
   public byte method5527() {
      return (byte)(this.array[++this.offset - 1] - 128);
   }

   @ObfuscatedName("cj")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "-38"
   )
   public void method5515(int var1) {
      this.array[++this.offset - 1] = (byte)(128 - var1);
   }

   @ObfuscatedName("ck")
   @ObfuscatedSignature(
      signature = "([II)V",
      garbageValue = "1768480831"
   )
   public void method5516(int[] var1) {
      int var2 = this.offset / 8;
      this.offset = 0;

      for(int var3 = 0; var3 < var2; ++var3) {
         int var4 = this.readInt();
         int var5 = this.readInt();
         int var6 = -957401312;
         int var7 = -1640531527;

         for(int var8 = 32; var8-- > 0; var4 -= var5 + (var5 << 4 ^ var5 >>> 5) ^ var6 + var1[var6 & 3]) {
            var5 -= var4 + (var4 << 4 ^ var4 >>> 5) ^ var1[var6 >>> 11 & 3] + var6;
            var6 -= var7;
         }

         this.offset -= 8;
         this.writeInt(var4);
         this.writeInt(var5);
      }

   }

   @ObfuscatedName("cl")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "1894775304"
   )
   public void method5659(int var1) {
      this.array[++this.offset - 1] = (byte)(var1 >> 8);
      this.array[++this.offset - 1] = (byte)(var1 + 128);
   }

   public void copy$encryptRsa(BigInteger var1, BigInteger var2, int var3) {
      int var4 = this.offset;
      this.offset = 0;
      byte[] var5 = new byte[var4];
      this.method5572(var5, 0, var4);
      BigInteger var6 = new BigInteger(var5);
      BigInteger var7 = var6.modPow(var1, var2);
      byte[] var8 = var7.toByteArray();
      this.offset = 0;
      this.method5481(var8.length);
      this.writeBytes(var8, 0, var8.length);
   }

   public byte[] getPayload() {
      return this.array;
   }

   public int getOffset() {
      return this.offset;
   }

   @ObfuscatedName("cm")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "649688108"
   )
   public int method5558() {
      this.offset += 2;
      int var1 = (this.array[this.offset - 1] - 128 & 255) + ((this.array[this.offset - 2] & 255) << 8);
      if(var1 > 32767) {
         var1 -= 65536;
      }

      return var1;
   }

   @ObfuscatedName("cn")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-1463177032"
   )
   public boolean method5520() {
      this.offset -= 4;
      int var1 = LoginScreenAnimation.method1293(this.array, 0, this.offset);
      int var2 = this.readInt();
      return var2 == var1;
   }

   @ObfuscatedName("co")
   @ObfuscatedSignature(
      signature = "([IIIB)V",
      garbageValue = "97"
   )
   public void method5489(int[] var1, int var2, int var3) {
      int var4 = this.offset;
      this.offset = var2;
      int var5 = (var3 - var2) / 8;

      for(int var6 = 0; var6 < var5; ++var6) {
         int var7 = this.readInt();
         int var8 = this.readInt();
         int var9 = -957401312;
         int var10 = -1640531527;

         for(int var11 = 32; var11-- > 0; var7 -= var8 + (var8 << 4 ^ var8 >>> 5) ^ var9 + var1[var9 & 3]) {
            var8 -= var7 + (var7 << 4 ^ var7 >>> 5) ^ var1[var9 >>> 11 & 3] + var9;
            var9 -= var10;
         }

         this.offset -= 8;
         this.writeInt(var7);
         this.writeInt(var8);
      }

      this.offset = var4;
   }

   @ObfuscatedName("cp")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1593001312"
   )
   public int method5538() {
      this.offset += 2;
      int var1 = ((this.array[this.offset - 1] & 255) << 8) + (this.array[this.offset - 2] - 128 & 255);
      if(var1 > 32767) {
         var1 -= 65536;
      }

      return var1;
   }

   @ObfuscatedName("cq")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-509396216"
   )
   public int readLEShort() {
      this.offset += 2;
      return ((this.array[this.offset - 1] & 255) << 8) + (this.array[this.offset - 2] & 255);
   }

   @ObfuscatedName("cr")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-313206497"
   )
   public int method5525() {
      return 0 - this.array[++this.offset - 1] & 255;
   }

   @ObfuscatedName("cs")
   public void createSecureBuffer(BigInteger var1, BigInteger var2, int var3) {
      this.copy$encryptRsa(exponent, ViewportMouse.client.getModulus(), var3);
   }

   @ObfuscatedName("ct")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1489408789"
   )
   public int method5565() {
      return 128 - this.array[++this.offset - 1] & 255;
   }

   @ObfuscatedName("cu")
   @ObfuscatedSignature(
      signature = "(I)B",
      garbageValue = "-675073982"
   )
   public byte method5528() {
      return (byte)(0 - this.array[++this.offset - 1]);
   }

   @ObfuscatedName("cv")
   @ObfuscatedSignature(
      signature = "([IIII)V",
      garbageValue = "-1919695403"
   )
   public void method5635(int[] var1, int var2, int var3) {
      int var4 = this.offset;
      this.offset = var2;
      int var5 = (var3 - var2) / 8;

      for(int var6 = 0; var6 < var5; ++var6) {
         int var7 = this.readInt();
         int var8 = this.readInt();
         int var9 = 0;
         int var10 = -1640531527;

         for(int var11 = 32; var11-- > 0; var8 += var7 + (var7 << 4 ^ var7 >>> 5) ^ var1[var9 >>> 11 & 3] + var9) {
            var7 += var8 + (var8 << 4 ^ var8 >>> 5) ^ var9 + var1[var9 & 3];
            var9 += var10;
         }

         this.offset -= 8;
         this.writeInt(var7);
         this.writeInt(var8);
      }

      this.offset = var4;
   }

   @ObfuscatedName("cw")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "921627728"
   )
   public void method5532(int var1) {
      this.array[++this.offset - 1] = (byte)(var1 + 128);
      this.array[++this.offset - 1] = (byte)(var1 >> 8);
   }

   @ObfuscatedName("cx")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1363651075"
   )
   public int method5540() {
      this.offset += 2;
      int var1 = ((this.array[this.offset - 1] & 255) << 8) + (this.array[this.offset - 2] & 255);
      if(var1 > 32767) {
         var1 -= 65536;
      }

      return var1;
   }

   @ObfuscatedName("cy")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "8"
   )
   public void method5522(int var1) {
      this.array[++this.offset - 1] = (byte)(0 - var1);
   }

   @ObfuscatedName("cz")
   @ObfuscatedSignature(
      signature = "([IB)V",
      garbageValue = "0"
   )
   public void method5683(int[] var1) {
      int var2 = this.offset / 8;
      this.offset = 0;

      for(int var3 = 0; var3 < var2; ++var3) {
         int var4 = this.readInt();
         int var5 = this.readInt();
         int var6 = 0;
         int var7 = -1640531527;

         for(int var8 = 32; var8-- > 0; var5 += var4 + (var4 << 4 ^ var4 >>> 5) ^ var1[var6 >>> 11 & 3] + var6) {
            var4 += var5 + (var5 << 4 ^ var5 >>> 5) ^ var6 + var1[var6 & 3];
            var6 += var7;
         }

         this.offset -= 8;
         this.writeInt(var4);
         this.writeInt(var5);
      }

   }

   @ObfuscatedName("de")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "5"
   )
   public int method5546() {
      this.offset += 4;
      return ((this.array[this.offset - 1] & 255) << 8) + ((this.array[this.offset - 4] & 255) << 16) + (this.array[this.offset - 2] & 255) + ((this.array[this.offset - 3] & 255) << 24);
   }

   @ObfuscatedName("df")
   @ObfuscatedSignature(
      signature = "([BIII)V",
      garbageValue = "641640947"
   )
   public void method5547(byte[] var1, int var2, int var3) {
      for(int var4 = var2; var4 < var3 + var2; ++var4) {
         var1[var4] = (byte)(this.array[++this.offset - 1] - 128);
      }

   }

   @ObfuscatedName("dm")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "74"
   )
   public void method5684(int var1) {
      this.array[++this.offset - 1] = (byte)(var1 >> 8);
      this.array[++this.offset - 1] = (byte)var1;
      this.array[++this.offset - 1] = (byte)(var1 >> 24);
      this.array[++this.offset - 1] = (byte)(var1 >> 16);
   }

   @ObfuscatedName("dn")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-1057607662"
   )
   public void method5543(int var1) {
      this.array[++this.offset - 1] = (byte)(var1 >> 16);
      this.array[++this.offset - 1] = (byte)(var1 >> 24);
      this.array[++this.offset - 1] = (byte)var1;
      this.array[++this.offset - 1] = (byte)(var1 >> 8);
   }

   @ObfuscatedName("dq")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "338391955"
   )
   public int readLEInt() {
      this.offset += 4;
      return (this.array[this.offset - 4] & 255) + ((this.array[this.offset - 3] & 255) << 8) + ((this.array[this.offset - 2] & 255) << 16) + ((this.array[this.offset - 1] & 255) << 24);
   }

   @ObfuscatedName("dr")
   @ObfuscatedSignature(
      signature = "([BIII)V",
      garbageValue = "666802900"
   )
   public void method5548(byte[] var1, int var2, int var3) {
      for(int var4 = var3 + var2 - 1; var4 >= var2; --var4) {
         var1[var4] = (byte)(this.array[++this.offset - 1] - 128);
      }

   }

   @ObfuscatedName("dt")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-715044381"
   )
   public int method5542() {
      this.offset += 3;
      return (this.array[this.offset - 3] & 255) + ((this.array[this.offset - 2] & 255) << 8) + ((this.array[this.offset - 1] & 255) << 16);
   }

   @ObfuscatedName("dv")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "-35"
   )
   public void method5595(int var1) {
      this.array[++this.offset - 1] = (byte)var1;
      this.array[++this.offset - 1] = (byte)(var1 >> 8);
      this.array[++this.offset - 1] = (byte)(var1 >> 16);
      this.array[++this.offset - 1] = (byte)(var1 >> 24);
   }

   @ObfuscatedName("dy")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "311573145"
   )
   public int readInt1() {
      this.offset += 4;
      return ((this.array[this.offset - 2] & 255) << 24) + ((this.array[this.offset - 4] & 255) << 8) + (this.array[this.offset - 3] & 255) + ((this.array[this.offset - 1] & 255) << 16);
   }

   private static void rl$$clinit() {
      exponent = new BigInteger("10001", 16);
   }
}
