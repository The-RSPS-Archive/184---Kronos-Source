package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gf")
public class MusicPatchNode2 {
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = -1235370225
   )
   public static int field2119;
   @ObfuscatedName("n")
   byte[] field2111;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 1580268847
   )
   int field2115;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -1867393453
   )
   int field2116;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1372771045
   )
   int field2114;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -1109994129
   )
   int field2110;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 93349913
   )
   int field2112;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = -1262915041
   )
   int field2118;
   @ObfuscatedName("z")
   byte[] field2113;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 1316855939
   )
   int field2117;

   @ObfuscatedName("fq")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-521075601"
   )
   static void method3725(int var0) {
      if(var0 == -1 && !Client.field967) {
         VertexNormal.method2466();
      } else if(var0 != -1 && var0 != Client.field874 && Client.field969 != 0 && !Client.field967) {
         Archive var1 = class212.archive6;
         int var2 = Client.field969;
         class197.field2173 = 1;
         class197.musicTrackArchive = var1;
         class183.musicTrackGroupId = var0;
         class38.musicTrackFileId = 0;
         TileItem.field816 = var2;
         WorldMapSectionType.musicTrackBoolean = false;
         field2119 = 2;
      }

      Client.field874 = var0;
   }

   @ObfuscatedName("ic")
   @ObfuscatedSignature(
      signature = "(IB)Z",
      garbageValue = "-93"
   )
   static boolean method3726(int var0) {
      for(int var1 = 0; var1 < Client.field1065; ++var1) {
         if(Client.field1067[var1] == var0) {
            return true;
         }
      }

      return false;
   }
}
