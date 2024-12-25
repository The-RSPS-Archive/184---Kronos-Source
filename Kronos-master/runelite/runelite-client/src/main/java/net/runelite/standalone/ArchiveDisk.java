package net.runelite.standalone;

import java.io.EOFException;
import java.io.IOException;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kg")
public final class ArchiveDisk {
   @ObfuscatedName("z")
   static byte[] ArchiveDisk_buffer;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lmm;"
   )
   BufferedFile datFile;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = -1322039527
   )
   int maxEntrySize;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 558641831
   )
   int archive;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lmm;"
   )
   BufferedFile idxFile;

   static {
      ArchiveDisk_buffer = new byte[520];
   }

   @ObfuscatedSignature(
      signature = "(ILmm;Lmm;I)V"
   )
   public ArchiveDisk(int var1, BufferedFile var2, BufferedFile var3, int var4) {
      this.datFile = null;
      this.idxFile = null;
      this.maxEntrySize = 65000;
      this.archive = var1;
      this.datFile = var2;
      this.idxFile = var3;
      this.maxEntrySize = var4;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I[BII)Z",
      garbageValue = "-1069570750"
   )
   public boolean method5312(int var1, byte[] var2, int var3) {
      BufferedFile var4 = this.datFile;
      boolean var10000;
      synchronized(this.datFile) {
         if(var3 < 0 || var3 > this.maxEntrySize) {
            throw new IllegalArgumentException("" + this.archive + ',' + var1 + ',' + var3);
         }

         boolean var5 = this.method5308(var1, var2, var3, true);
         if(!var5) {
            var5 = this.method5308(var1, var2, var3, false);
         }

         var10000 = var5;
      }

      return var10000;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I[BIZI)Z",
      garbageValue = "1005578949"
   )
   boolean method5308(int var1, byte[] var2, int var3, boolean var4) {
      BufferedFile var5 = this.datFile;
      synchronized(this.datFile) {
         boolean var10000;
         try {
            int var6;
            if(var4) {
               if(this.idxFile.method25() < (long)(var1 * 6 + 6)) {
                  var10000 = false;
                  return var10000;
               }

               this.idxFile.method22((long)(var1 * 6));
               this.idxFile.method38(ArchiveDisk_buffer, 0, 6);
               var6 = (ArchiveDisk_buffer[5] & 255) + ((ArchiveDisk_buffer[3] & 255) << 16) + ((ArchiveDisk_buffer[4] & 255) << 8);
               if(var6 <= 0 || (long)var6 > this.datFile.method25() / 520L) {
                  var10000 = false;
                  return var10000;
               }
            } else {
               var6 = (int)((this.datFile.method25() + 519L) / 520L);
               if(var6 == 0) {
                  var6 = 1;
               }
            }

            ArchiveDisk_buffer[0] = (byte)(var3 >> 16);
            ArchiveDisk_buffer[1] = (byte)(var3 >> 8);
            ArchiveDisk_buffer[2] = (byte)var3;
            ArchiveDisk_buffer[3] = (byte)(var6 >> 16);
            ArchiveDisk_buffer[4] = (byte)(var6 >> 8);
            ArchiveDisk_buffer[5] = (byte)var6;
            this.idxFile.method22((long)(var1 * 6));
            this.idxFile.method29(ArchiveDisk_buffer, 0, 6);
            int var7 = 0;
            int var8 = 0;

            while(true) {
               if(var7 < var3) {
                  label186: {
                     int var9 = 0;
                     int var10;
                     if(var4) {
                        this.datFile.method22(520L * (long)var6);
                        int var11;
                        int var12;
                        if(var1 > 65535) {
                           try {
                              this.datFile.method38(ArchiveDisk_buffer, 0, 10);
                           } catch (EOFException var17) {
                              break label186;
                           }

                           var10 = ((ArchiveDisk_buffer[1] & 255) << 16) + ((ArchiveDisk_buffer[0] & 255) << 24) + (ArchiveDisk_buffer[3] & 255) + ((ArchiveDisk_buffer[2] & 255) << 8);
                           var11 = (ArchiveDisk_buffer[5] & 255) + ((ArchiveDisk_buffer[4] & 255) << 8);
                           var9 = (ArchiveDisk_buffer[8] & 255) + ((ArchiveDisk_buffer[7] & 255) << 8) + ((ArchiveDisk_buffer[6] & 255) << 16);
                           var12 = ArchiveDisk_buffer[9] & 255;
                        } else {
                           try {
                              this.datFile.method38(ArchiveDisk_buffer, 0, 8);
                           } catch (EOFException var16) {
                              break label186;
                           }

                           var10 = (ArchiveDisk_buffer[1] & 255) + ((ArchiveDisk_buffer[0] & 255) << 8);
                           var11 = (ArchiveDisk_buffer[3] & 255) + ((ArchiveDisk_buffer[2] & 255) << 8);
                           var9 = ((ArchiveDisk_buffer[5] & 255) << 8) + ((ArchiveDisk_buffer[4] & 255) << 16) + (ArchiveDisk_buffer[6] & 255);
                           var12 = ArchiveDisk_buffer[7] & 255;
                        }

                        if(var10 != var1 || var8 != var11 || var12 != this.archive) {
                           var10000 = false;
                           return var10000;
                        }

                        if(var9 < 0 || (long)var9 > this.datFile.method25() / 520L) {
                           var10000 = false;
                           return var10000;
                        }
                     }

                     if(var9 == 0) {
                        var4 = false;
                        var9 = (int)((this.datFile.method25() + 519L) / 520L);
                        if(var9 == 0) {
                           ++var9;
                        }

                        if(var9 == var6) {
                           ++var9;
                        }
                     }

                     if(var1 > 65535) {
                        if(var3 - var7 <= 510) {
                           var9 = 0;
                        }

                        ArchiveDisk_buffer[0] = (byte)(var1 >> 24);
                        ArchiveDisk_buffer[1] = (byte)(var1 >> 16);
                        ArchiveDisk_buffer[2] = (byte)(var1 >> 8);
                        ArchiveDisk_buffer[3] = (byte)var1;
                        ArchiveDisk_buffer[4] = (byte)(var8 >> 8);
                        ArchiveDisk_buffer[5] = (byte)var8;
                        ArchiveDisk_buffer[6] = (byte)(var9 >> 16);
                        ArchiveDisk_buffer[7] = (byte)(var9 >> 8);
                        ArchiveDisk_buffer[8] = (byte)var9;
                        ArchiveDisk_buffer[9] = (byte)this.archive;
                        this.datFile.method22(520L * (long)var6);
                        this.datFile.method29(ArchiveDisk_buffer, 0, 10);
                        var10 = var3 - var7;
                        if(var10 > 510) {
                           var10 = 510;
                        }

                        this.datFile.method29(var2, var7, var10);
                        var7 += var10;
                     } else {
                        if(var3 - var7 <= 512) {
                           var9 = 0;
                        }

                        ArchiveDisk_buffer[0] = (byte)(var1 >> 8);
                        ArchiveDisk_buffer[1] = (byte)var1;
                        ArchiveDisk_buffer[2] = (byte)(var8 >> 8);
                        ArchiveDisk_buffer[3] = (byte)var8;
                        ArchiveDisk_buffer[4] = (byte)(var9 >> 16);
                        ArchiveDisk_buffer[5] = (byte)(var9 >> 8);
                        ArchiveDisk_buffer[6] = (byte)var9;
                        ArchiveDisk_buffer[7] = (byte)this.archive;
                        this.datFile.method22((long)var6 * 520L);
                        this.datFile.method29(ArchiveDisk_buffer, 0, 8);
                        var10 = var3 - var7;
                        if(var10 > 512) {
                           var10 = 512;
                        }

                        this.datFile.method29(var2, var7, var10);
                        var7 += var10;
                     }

                     var6 = var9;
                     ++var8;
                     continue;
                  }
               }

               var10000 = true;
               return var10000;
            }
         } catch (IOException var18) {
            var10000 = false;
            return var10000;
         }
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IB)[B",
      garbageValue = "1"
   )
   public byte[] method5306(int var1) {
      BufferedFile var2 = this.datFile;
      Object var10000;
      synchronized(this.datFile) {
         try {
            if(this.idxFile.method25() < (long)(var1 * 6 + 6)) {
               var10000 = null;
               return (byte[])var10000;
            }

            this.idxFile.method22((long)(var1 * 6));
            this.idxFile.method38(ArchiveDisk_buffer, 0, 6);
            int var3 = ((ArchiveDisk_buffer[0] & 255) << 16) + (ArchiveDisk_buffer[2] & 255) + ((ArchiveDisk_buffer[1] & 255) << 8);
            int var4 = (ArchiveDisk_buffer[5] & 255) + ((ArchiveDisk_buffer[3] & 255) << 16) + ((ArchiveDisk_buffer[4] & 255) << 8);
            if(var3 < 0 || var3 > this.maxEntrySize) {
               var10000 = null;
               return (byte[])var10000;
            }

            if(var4 <= 0 || (long)var4 > this.datFile.method25() / 520L) {
               var10000 = null;
               return (byte[])var10000;
            }

            byte[] var5 = new byte[var3];
            int var6 = 0;
            int var7 = 0;

            while(var6 < var3) {
               if(var4 == 0) {
                  var10000 = null;
                  return (byte[])var10000;
               }

               this.datFile.method22((long)var4 * 520L);
               int var8 = var3 - var6;
               int var9;
               int var10;
               int var11;
               int var12;
               byte var13;
               if(var1 > 65535) {
                  if(var8 > 510) {
                     var8 = 510;
                  }

                  var13 = 10;
                  this.datFile.method38(ArchiveDisk_buffer, 0, var8 + var13);
                  var9 = ((ArchiveDisk_buffer[1] & 255) << 16) + ((ArchiveDisk_buffer[0] & 255) << 24) + (ArchiveDisk_buffer[3] & 255) + ((ArchiveDisk_buffer[2] & 255) << 8);
                  var10 = (ArchiveDisk_buffer[5] & 255) + ((ArchiveDisk_buffer[4] & 255) << 8);
                  var11 = (ArchiveDisk_buffer[8] & 255) + ((ArchiveDisk_buffer[7] & 255) << 8) + ((ArchiveDisk_buffer[6] & 255) << 16);
                  var12 = ArchiveDisk_buffer[9] & 255;
               } else {
                  if(var8 > 512) {
                     var8 = 512;
                  }

                  var13 = 8;
                  this.datFile.method38(ArchiveDisk_buffer, 0, var8 + var13);
                  var9 = (ArchiveDisk_buffer[1] & 255) + ((ArchiveDisk_buffer[0] & 255) << 8);
                  var10 = (ArchiveDisk_buffer[3] & 255) + ((ArchiveDisk_buffer[2] & 255) << 8);
                  var11 = ((ArchiveDisk_buffer[5] & 255) << 8) + ((ArchiveDisk_buffer[4] & 255) << 16) + (ArchiveDisk_buffer[6] & 255);
                  var12 = ArchiveDisk_buffer[7] & 255;
               }

               if(var9 == var1 && var10 == var7 && var12 == this.archive) {
                  if(var11 >= 0 && (long)var11 <= this.datFile.method25() / 520L) {
                     int var14 = var13 + var8;

                     for(int var15 = var13; var15 < var14; ++var15) {
                        var5[var6++] = ArchiveDisk_buffer[var15];
                     }

                     var4 = var11;
                     ++var7;
                     continue;
                  }

                  var10000 = null;
                  return (byte[])var10000;
               }

               var10000 = null;
               return (byte[])var10000;
            }

            byte[] var20 = var5;
            return var20;
         } catch (IOException var18) {
            var10000 = null;
         }
      }

      return (byte[])var10000;
   }

   public String toString() {
      return "" + this.archive;
   }

   public String aak() {
      return "" + this.archive;
   }

   public String aah() {
      return "" + this.archive;
   }

   public String aae() {
      return "" + this.archive;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lkl;Ljava/lang/String;I)I",
      garbageValue = "1895940511"
   )
   public static int method5307(Buffer var0, String var1) {
      int var2 = var0.offset;
      byte[] var3 = UserComparator3.method3393(var1);
      var0.method5594(var3.length);
      var0.offset += class217.huffman.method4171(var3, 0, var3.length, var0.array, var0.offset);
      return var0.offset - var2;
   }

   @ObfuscatedName("el")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "1355807172"
   )
   static void method5318(int var0) {
      if(var0 == -3) {
         PlayerType.method3939("Connection timed out.", "Please try using a different world.", "");
      } else if(var0 == -2) {
         PlayerType.method3939("", "Error connecting to server.", "");
      } else if(var0 == -1) {
         PlayerType.method3939("No response from server.", "Please try using a different world.", "");
      } else if(var0 == 3) {
         Login.loginIndex = 3;
         Login.field766 = 1;
      } else if(var0 == 4) {
         Login.loginIndex = 12;
         Login.field769 = 0;
      } else if(var0 == 5) {
         Login.field766 = 2;
         PlayerType.method3939("Your account has not logged out from its last", "session or the server is too busy right now.", "Please try again in a few minutes.");
      } else if(var0 == 68 || !Client.onMobile && var0 == 6) {
         PlayerType.method3939("Kronos has been updated!", "Please reload this page.", "");
      } else if(var0 == 7) {
         PlayerType.method3939("This world is full.", "Please use a different world.", "");
      } else if(var0 == 8) {
         PlayerType.method3939("Unable to connect.", "Login server offline.", "");
      } else if(var0 == 9) {
         PlayerType.method3939("Login limit exceeded.", "Too many connections from your address.", "");
      } else if(var0 == 10) {
         PlayerType.method3939("Unable to connect.", "Bad session id.", "");
      } else if(var0 == 11) {
         PlayerType.method3939("We suspect someone knows your password.", "Press \'change your password\' on front page.", "");
      } else if(var0 == 12) {
         PlayerType.method3939("You need a members account to login to this world.", "Please subscribe, or use a different world.", "");
      } else if(var0 == 13) {
         PlayerType.method3939("Could not complete login.", "Please try using a different world.", "");
      } else if(var0 == 14) {
         PlayerType.method3939("The server is being updated.", "Please wait 1 minute and try again.", "");
      } else if(var0 == 16) {
         PlayerType.method3939("Too many login attempts.", "Please wait a few minutes before trying again.", "");
      } else if(var0 == 17) {
         PlayerType.method3939("You are standing in a members-only area.", "To play on this world move to a free area first", "");
      } else if(var0 == 18) {
         Login.loginIndex = 12;
         Login.field769 = 1;
      } else if(var0 == 19) {
         PlayerType.method3939("This world is running a closed Beta.", "Sorry invited players only.", "Please use a different world.");
      } else if(var0 == 20) {
         PlayerType.method3939("Invalid loginserver requested.", "Please try using a different world.", "");
      } else if(var0 == 22) {
         PlayerType.method3939("Malformed login packet.", "Please try again.", "");
      } else if(var0 == 23) {
         PlayerType.method3939("No reply from loginserver.", "Please wait 1 minute and try again.", "");
      } else if(var0 == 24) {
         PlayerType.method3939("Error loading your profile.", "Please contact customer support.", "");
      } else if(var0 == 25) {
         PlayerType.method3939("Unexpected loginserver response.", "Please try using a different world.", "");
      } else if(var0 == 26) {
         PlayerType.method3939("This computers address has been blocked", "as it was used to break our rules.", "");
      } else if(var0 == 27) {
         PlayerType.method3939("", "Service unavailable.", "");
      } else if(var0 == 31) {
         PlayerType.method3939("Your account must have a displayname set", "in order to play the game.  Please set it", "via the website, or the main game.");
      } else if(var0 == 32) {
         PlayerType.method3939("Your attempt to log into your account was", "unsuccessful.  Don\'t worry, you can sort", "this out by visiting the billing system.");
      } else if(var0 == 37) {
         PlayerType.method3939("Your account is currently inaccessible.", "Please try again in a few minutes.", "");
      } else if(var0 == 38) {
         PlayerType.method3939("You need to vote to play!", "Visit runescape.com and vote,", "and then come back here!");
      } else if(var0 == 55) {
         Login.loginIndex = 8;
      } else if (var0 == 58) {
         PlayerType.method3939("You must register before logging in.", "The registration page has been opened.", "Sign in here once complete.");
         try {
            ArchiveDiskActionHandler.method4341("https://community.kronos.rip/index.php?register/", true, false);//Is this the only custom login msg?
         } catch (Exception e) {
            e.printStackTrace();
         }
      } else {
         if(var0 == 56) {
            PlayerType.method3939("Enter the 6-digit code generated by your", "authenticator app.", "");
            MouseRecorder.method1207(11);
            return;
         }

         if(var0 == 57) {
            PlayerType.method3939("The code you entered was incorrect.", "Please try again.", "");
            MouseRecorder.method1207(11);
            return;
         }

         if(var0 == 61) {
            Login.loginIndex = 7;
         } else {
            PlayerType.method3939("Unexpected server response", "Please try using a different world.", "");
         }
      }

      MouseRecorder.method1207(10);
   }

   @ObfuscatedName("kb")
   @ObfuscatedSignature(
      signature = "(Lkl;IB)V",
      garbageValue = "16"
   )
   static void method5309(Buffer var0, int var1) {
      LoginScreenAnimation.method1277(var0.array, var1);
      SoundSystem.method2464(var0, var1);
   }
}
