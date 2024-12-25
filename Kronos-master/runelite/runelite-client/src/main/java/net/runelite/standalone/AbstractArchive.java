package net.runelite.standalone;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.runelite.api.overlay.OverlayIndex;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSAbstractArchive;
import net.runelite.rs.api.RSArchive;
import org.slf4j.Logger;

@ObfuscatedName("hp")
public abstract class AbstractArchive implements RSAbstractArchive {
   @ObfuscatedName("so")
   @ObfuscatedSignature(
      signature = "Lbl;"
   )
   static ClientPreferences clientPreferences;
   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "Llr;"
   )
   static GZipDecompressor gzipDecompressor;
   @ObfuscatedName("g")
   @ObfuscatedGetter(
      intValue = 949229889
   )
   static int field2738;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -2001153217
   )
   int groupCount;
   @ObfuscatedName("o")
   Object[][] files;
   @ObfuscatedName("p")
   int[] groupCrcs;
   @ObfuscatedName("q")
   int[] groupVersions;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Llk;"
   )
   IntHashTable groupNameHashTable;
   @ObfuscatedName("t")
   boolean shallowFiles;
   @ObfuscatedName("u")
   int[] groupNameHashes;
   @ObfuscatedName("v")
   int[] groupIds;
   @ObfuscatedName("w")
   boolean releaseGroups;
   @ObfuscatedName("y")
   int[][] fileIds;
   @ObfuscatedName("b")
   Object[] groups;
   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "[Llk;"
   )
   IntHashTable[] fileNameHashTables;
   public boolean overlayOutdated;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = 194523033
   )
   public int hash;
   @ObfuscatedName("i")
   int[][] fileNameHashes;
   @ObfuscatedName("m")
   int[] fileCounts;

   static {
      gzipDecompressor = new GZipDecompressor();
      field2738 = 0;
   }

   AbstractArchive(boolean var1, boolean var2) {
      this.releaseGroups = var1;
      this.shallowFiles = var2;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "15"
   )
   void vmethod4267(int var1) {
   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "848617854"
   )
   void vmethod4268(int var1) {
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(II)Z",
      garbageValue = "-812316387"
   )
   public boolean method4031(int var1) {
      if(this.files.length == 1) {
         return this.method4024(0, var1);
      } else if(this.files[var1].length == 1) {
         return this.method4024(var1, 0);
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(II)Z",
      garbageValue = "-1524074926"
   )
   public boolean method4046(int var1) {
      if(this.groups[var1] != null) {
         return true;
      } else {
         this.vmethod4268(var1);
         return this.groups[var1] != null;
      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(III)Z",
      garbageValue = "2002507609"
   )
   public boolean method4024(int var1, int var2) {
      if(var1 >= 0 && var1 < this.files.length && this.files[var1] != null && var2 >= 0 && var2 < this.files[var1].length) {
         if(this.files[var1][var2] != null) {
            return true;
         } else if(this.groups[var1] != null) {
            return true;
         } else {
            this.vmethod4268(var1);
            return this.groups[var1] != null;
         }
      } else {
         return false;
      }
   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "1926198322"
   )
   public void method4094(int var1) {
      for(int var2 = 0; var2 < this.files[var1].length; ++var2) {
         this.files[var1][var2] = null;
      }

   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(II[II)[B",
      garbageValue = "723953864"
   )
   public byte[] method4021(int var1, int var2, int[] var3) {
      if(var1 >= 0 && var1 < this.files.length && this.files[var1] != null && var2 >= 0 && var2 < this.files[var1].length) {
         if(this.files[var1][var2] == null) {
            boolean var4 = this.method4095(var1, var3);
            if(!var4) {
               this.vmethod4268(var1);
               var4 = this.method4095(var1, var3);
               if(!var4) {
                  return null;
               }
            }
         }

         byte[] var5 = GrandExchangeOfferUnitPriceComparator.method1473(this.files[var1][var2], false);
         if(this.shallowFiles) {
            this.files[var1][var2] = null;
         }

         return var5;
      } else {
         return null;
      }
   }

   @ObfuscatedName("v")
   public byte[] method4020(int var1, int var2, short var3) {
      byte[] var4 = this.copy$takeFile(var1, var2, var3);
      RSArchive var5 = (RSArchive)this;
      if(!OverlayIndex.hasOverlay(var5.getIndex(), var1)) {
         return var4;
      } else {
         Logger var6 = ViewportMouse.client.getLogger();
         InputStream var7 = this.getClass().getResourceAsStream("/runelite/" + var5.getIndex() + "/" + var1);
         if(var7 == null) {
            var6.warn("Missing overlay data for {}/{}", Integer.valueOf(var5.getIndex()), Integer.valueOf(var1));
            return var4;
         } else {
            InputStream var8 = this.getClass().getResourceAsStream("/runelite/" + var5.getIndex() + "/" + var1 + ".hash");
            byte[] var10000;
            if(var4 == null) {
               if(var8 != null) {
                  var6.warn("Hash file for non existing archive {}/{}", Integer.valueOf(var5.getIndex()), Integer.valueOf(var1));
                  return null;
               } else {
                  var6.debug("Adding archive {}/{}", Integer.valueOf(var5.getIndex()), Integer.valueOf(var1));

                  try {
                     var10000 = ByteStreams.toByteArray(var7);
                  } catch (IOException var12) {
                     var6.warn("error loading archive replacement", var12);
                     return null;
                  }

                  return var10000;
               }
            } else if(var8 == null) {
               var6.warn("Missing hash file for {}/{}", Integer.valueOf(var5.getIndex()), Integer.valueOf(var1));
               return var4;
            } else {
               HashCode var9 = Hashing.sha256().hashBytes(var4);
               String var10 = BaseEncoding.base16().encode(var9.asBytes());

               try {
                  String var11 = CharStreams.toString(new InputStreamReader(var8));
                  if(var11.equals(var10)) {
                     var6.debug("Replacing archive {}/{}", Integer.valueOf(var5.getIndex()), Integer.valueOf(var1));
                     var10000 = ByteStreams.toByteArray(var7);
                     return var10000;
                  }

                  var6.warn("Mismatch in overlaid cache archive hash for {}/{}: {} != {}", new Object[]{Integer.valueOf(var5.getIndex()), Integer.valueOf(var1), var11, var10});
                  this.overlayOutdated = true;
               } catch (IOException var13) {
                  var6.warn("error checking hash", var13);
               }

               return var4;
            }
         }
      }
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "-101"
   )
   public int method4033() {
      return this.files.length;
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(II)I",
      garbageValue = "-2030453035"
   )
   int vmethod4272(int var1) {
      return this.groups[var1] != null?100:0;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "([BS)V",
      garbageValue = "4095"
   )
   void method4018(byte[] var1) {
      this.hash = Username.method5005(var1, var1.length);
      Buffer var2 = new Buffer(MusicPatch.method3750(var1));
      int var3 = var2.readUnsignedByte();
      if(var3 >= 5 && var3 <= 7) {
         if(var3 >= 6) {
            var2.readInt();
         }

         int var4 = var2.readUnsignedByte();
         if(var3 >= 7) {
            this.groupCount = var2.method5512();
         } else {
            this.groupCount = var2.readUnsignedShort();
         }

         int var5 = 0;
         int var6 = -1;
         this.groupIds = new int[this.groupCount];
         int var7;
         if(var3 >= 7) {
            for(var7 = 0; var7 < this.groupCount; ++var7) {
               this.groupIds[var7] = var5 += var2.method5512();
               if(this.groupIds[var7] > var6) {
                  var6 = this.groupIds[var7];
               }
            }
         } else {
            for(var7 = 0; var7 < this.groupCount; ++var7) {
               this.groupIds[var7] = var5 += var2.readUnsignedShort();
               if(this.groupIds[var7] > var6) {
                  var6 = this.groupIds[var7];
               }
            }
         }

         this.groupCrcs = new int[var6 + 1];
         this.groupVersions = new int[var6 + 1];
         this.fileCounts = new int[var6 + 1];
         this.fileIds = new int[var6 + 1][];
         this.groups = new Object[var6 + 1];
         this.files = new Object[var6 + 1][];
         if(var4 != 0) {
            this.groupNameHashes = new int[var6 + 1];

            for(var7 = 0; var7 < this.groupCount; ++var7) {
               this.groupNameHashes[this.groupIds[var7]] = var2.readInt();
            }

            this.groupNameHashTable = new IntHashTable(this.groupNameHashes);
         }

         for(var7 = 0; var7 < this.groupCount; ++var7) {
            this.groupCrcs[this.groupIds[var7]] = var2.readInt();
         }

         for(var7 = 0; var7 < this.groupCount; ++var7) {
            this.groupVersions[this.groupIds[var7]] = var2.readInt();
         }

         for(var7 = 0; var7 < this.groupCount; ++var7) {
            this.fileCounts[this.groupIds[var7]] = var2.readUnsignedShort();
         }

         int var8;
         int var9;
         int var10;
         int var11;
         int var12;
         if(var3 >= 7) {
            for(var7 = 0; var7 < this.groupCount; ++var7) {
               var8 = this.groupIds[var7];
               var9 = this.fileCounts[var8];
               var5 = 0;
               var10 = -1;
               this.fileIds[var8] = new int[var9];

               for(var11 = 0; var11 < var9; ++var11) {
                  var12 = this.fileIds[var8][var11] = var5 += var2.method5512();
                  if(var12 > var10) {
                     var10 = var12;
                  }
               }

               this.files[var8] = new Object[var10 + 1];
            }
         } else {
            for(var7 = 0; var7 < this.groupCount; ++var7) {
               var8 = this.groupIds[var7];
               var9 = this.fileCounts[var8];
               var5 = 0;
               var10 = -1;
               this.fileIds[var8] = new int[var9];

               for(var11 = 0; var11 < var9; ++var11) {
                  var12 = this.fileIds[var8][var11] = var5 += var2.readUnsignedShort();
                  if(var12 > var10) {
                     var10 = var12;
                  }
               }

               this.files[var8] = new Object[var10 + 1];
            }
         }

         if(var4 != 0) {
            this.fileNameHashes = new int[var6 + 1][];
            this.fileNameHashTables = new IntHashTable[var6 + 1];

            for(var7 = 0; var7 < this.groupCount; ++var7) {
               var8 = this.groupIds[var7];
               var9 = this.fileCounts[var8];
               this.fileNameHashes[var8] = new int[this.files[var8].length];

               for(var10 = 0; var10 < var9; ++var10) {
                  this.fileNameHashes[var8][this.fileIds[var8][var10]] = var2.readInt();
               }

               this.fileNameHashTables[var8] = new IntHashTable(this.fileNameHashes[var8]);
            }
         }

      } else {
         throw new RuntimeException("");
      }
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(IB)[I",
      garbageValue = "-60"
   )
   public int[] method4042(int var1) {
      return var1 >= 0 && var1 < this.fileIds.length?this.fileIds[var1]:null;
   }

   @ObfuscatedName("aa")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;Ljava/lang/String;B)Z",
      garbageValue = "-68"
   )
   public boolean method4022(String var1, String var2) {
      var1 = var1.toLowerCase();
      var2 = var2.toLowerCase();
      int var3 = this.groupNameHashTable.method6248(WorldMapData_0.method172(var1));
      int var4 = this.fileNameHashTables[var3].method6248(WorldMapData_0.method172(var2));
      return this.method4024(var3, var4);
   }

   @ObfuscatedName("ab")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)I",
      garbageValue = "-2026426136"
   )
   public int method4045(String var1) {
      var1 = var1.toLowerCase();
      int var2 = this.groupNameHashTable.method6248(WorldMapData_0.method172(var1));
      return this.vmethod4272(var2);
   }

   @ObfuscatedName("ac")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;Ljava/lang/String;B)[B",
      garbageValue = "0"
   )
   public byte[] getFileData(String archive, String file) {
      archive = archive.toLowerCase();
      file = file.toLowerCase();
      int var3 = this.groupNameHashTable.method6248(WorldMapData_0.method172(archive));
      int var4 = this.fileNameHashTables[var3].method6248(WorldMapData_0.method172(file));
      return this.method4020(var3, var4, (short)-19147);
   }

   @ObfuscatedName("ap")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)Z",
      garbageValue = "1601927711"
   )
   public boolean method4043(String var1) {
      var1 = var1.toLowerCase();
      int var2 = this.groupNameHashTable.method6248(WorldMapData_0.method172(var1));
      return this.method4046(var2);
   }

   @ObfuscatedName("ar")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;B)V",
      garbageValue = "-63"
   )
   public void method4044(String var1) {
      var1 = var1.toLowerCase();
      int var2 = this.groupNameHashTable.method6248(WorldMapData_0.method172(var1));
      if(var2 >= 0) {
         this.vmethod4267(var2);
      }
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(IB)[B",
      garbageValue = "96"
   )
   public byte[] method4029(int var1) {
      if(this.files.length == 1) {
         return this.method4028(0, var1);
      } else if(this.files[var1].length == 1) {
         return this.method4028(var1, 0);
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(III)[B",
      garbageValue = "1923776823"
   )
   public byte[] method4028(int var1, int var2) {
      if(var1 >= 0 && var1 < this.files.length && this.files[var1] != null && var2 >= 0 && var2 < this.files[var1].length) {
         if(this.files[var1][var2] == null) {
            boolean var3 = this.method4095(var1, (int[])null);
            if(!var3) {
               this.vmethod4268(var1);
               var3 = this.method4095(var1, (int[])null);
               if(!var3) {
                  return null;
               }
            }
         }

         byte[] var4 = GrandExchangeOfferUnitPriceComparator.method1473(this.files[var1][var2], false);
         return var4;
      } else {
         return null;
      }
   }

   public byte[] copy$takeFile(int var1, int var2, short var3) {
      return this.method4021(var1, var2, (int[])null);
   }

   public boolean isOverlayOutdated() {
      return this.overlayOutdated;
   }

   public byte[] getConfigData(int var1, int var2) {
      return this.method4020(var1, var2, (short)3526);
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)I",
      garbageValue = "1617316917"
   )
   public int method4059(String var1) {
      var1 = var1.toLowerCase();
      return this.groupNameHashTable.method6248(WorldMapData_0.method172(var1));
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(II)I",
      garbageValue = "-2047505439"
   )
   public int fileCount(int var1) {
      return this.files[var1].length;
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1263344370"
   )
   public void method4036() {
      for(int var1 = 0; var1 < this.files.length; ++var1) {
         if(this.files[var1] != null) {
            for(int var2 = 0; var2 < this.files[var1].length; ++var2) {
               this.files[var1][var2] = null;
            }
         }
      }

   }

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1193777199"
   )
   public void method4034() {
      for(int var1 = 0; var1 < this.groups.length; ++var1) {
         this.groups[var1] = null;
      }

   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(II)[B",
      garbageValue = "39289660"
   )
   public byte[] method4027(int var1) {
      if(this.files.length == 1) {
         return this.method4020(0, var1, (short)4079);
      } else if(this.files[var1].length == 1) {
         return this.method4020(var1, 0, (short)15515);
      } else {
         throw new RuntimeException();
      }
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      signature = "(I[II)Z",
      garbageValue = "1408640769"
   )
   boolean method4095(int var1, int[] var2) {
      if(this.groups[var1] == null) {
         return false;
      } else {
         int var3 = this.fileCounts[var1];
         int[] var4 = this.fileIds[var1];
         Object[] var5 = this.files[var1];
         boolean var6 = true;

         for(int var7 = 0; var7 < var3; ++var7) {
            if(var5[var4[var7]] == null) {
               var6 = false;
               break;
            }
         }

         if(var6) {
            return true;
         } else {
            byte[] var18;
            if(var2 == null || var2[0] == 0 && var2[1] == 0 && var2[2] == 0 && var2[3] == 0) {
               var18 = GrandExchangeOfferUnitPriceComparator.method1473(this.groups[var1], false);
            } else {
               var18 = GrandExchangeOfferUnitPriceComparator.method1473(this.groups[var1], true);
               Buffer var8 = new Buffer(var18);
               var8.method5489(var2, 5, var8.array.length);
            }

            byte[] var20 = MusicPatch.method3750(var18);
            if(this.releaseGroups) {
               this.groups[var1] = null;
            }

            if(var3 > 1) {
               int var9 = var20.length;
               --var9;
               int var10 = var20[var9] & 255;
               var9 -= var10 * var3 * 4;
               Buffer var11 = new Buffer(var20);
               int[] var12 = new int[var3];
               var11.offset = var9;

               int var14;
               int var15;
               for(int var13 = 0; var13 < var10; ++var13) {
                  var14 = 0;

                  for(var15 = 0; var15 < var3; ++var15) {
                     var14 += var11.readInt();
                     var12[var15] += var14;
                  }
               }

               byte[][] var19 = new byte[var3][];

               for(var14 = 0; var14 < var3; ++var14) {
                  var19[var14] = new byte[var12[var14]];
                  var12[var14] = 0;
               }

               var11.offset = var9;
               var14 = 0;

               for(var15 = 0; var15 < var10; ++var15) {
                  int var16 = 0;

                  for(int var17 = 0; var17 < var3; ++var17) {
                     var16 += var11.readInt();
                     System.arraycopy(var20, var14, var19[var17], var12[var17], var16);
                     var12[var17] += var16;
                     var14 += var16;
                  }
               }

               for(var15 = 0; var15 < var3; ++var15) {
                  if(!this.shallowFiles) {
                     var5[var4[var15]] = GrandExchangeEvents.method94(var19[var15], false);
                  } else {
                     var5[var4[var15]] = var19[var15];
                  }
               }
            } else if(!this.shallowFiles) {
               var5[var4[0]] = GrandExchangeEvents.method94(var20, false);
            } else {
               var5[var4[0]] = var20;
            }

            return true;
         }
      }
   }

   @ObfuscatedName("k")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;Ljava/lang/String;S)Z",
      garbageValue = "8717"
   )
   public boolean method4108(String var1, String var2) {
      var1 = var1.toLowerCase();
      var2 = var2.toLowerCase();
      int var3 = this.groupNameHashTable.method6248(WorldMapData_0.method172(var1));
      if(var3 < 0) {
         return false;
      } else {
         int var4 = this.fileNameHashTables[var3].method6248(WorldMapData_0.method172(var2));
         return var4 >= 0;
      }
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "(ILjava/lang/String;I)I",
      garbageValue = "-1445606076"
   )
   public int method4039(int var1, String var2) {
      var2 = var2.toLowerCase();
      return this.fileNameHashTables[var1].method6248(WorldMapData_0.method172(var2));
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "304263263"
   )
   public boolean method4025() {
      boolean var1 = true;

      for(int var2 = 0; var2 < this.groupIds.length; ++var2) {
         int var3 = this.groupIds[var2];
         if(this.groups[var3] == null) {
            this.vmethod4268(var3);
            if(this.groups[var3] == null) {
               var1 = false;
            }
         }
      }

      return var1;
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(CI)Z",
      garbageValue = "-925642832"
   )
   public static boolean method4111(char var0) {
      return var0 >= '0' && var0 <= '9' || var0 >= 'A' && var0 <= 'Z' || var0 >= 'a' && var0 <= 'z';
   }
}
