package net.runelite.standalone;

import java.io.File;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("fh")
public class JagexCache {
   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "Lmm;"
   )
   public static BufferedFile JagexCache_dat2File;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 34524443
   )
   public static int idxCount;
   @ObfuscatedName("u")
   static File JagexCache_locationFile;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = -424456649
   )
   public static int ItemDefinition_fileCount;
   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "Lmm;"
   )
   public static BufferedFile JagexCache_idx255File;
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "Lmm;"
   )
   public static BufferedFile JagexCache_randomDat;
   @ObfuscatedName("j")
   public static String userHomeDirectory;

   static {
      JagexCache_randomDat = null;
      JagexCache_dat2File = null;
      JagexCache_idx255File = null;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IB)Lir;",
      garbageValue = "62"
   )
   public static StructDefinition method3408(int var0) {
      StructDefinition var1 = (StructDefinition)StructDefinition.StructDefinition_cached.method3032((long)var0);
      if(var1 != null) {
         return var1;
      } else {
         byte[] var2 = StructDefinition.StructDefinition_archive.method4020(34, var0, (short)12139);
         var1 = new StructDefinition();
         if(var2 != null) {
            var1.method4500(new Buffer(var2));
         }

         var1.method4502();
         StructDefinition.StructDefinition_cached.method3034(var1, (long)var0);
         return var1;
      }
   }
}
