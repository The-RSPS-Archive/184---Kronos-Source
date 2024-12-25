package net.runelite.standalone;

import java.io.File;
import java.util.Hashtable;
import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("fg")
public class FileSystem {
   @ObfuscatedName("n")
   public static File FileSystem_cacheDir;
   @ObfuscatedName("v")
   static Hashtable FileSystem_cacheFiles;
   @ObfuscatedName("z")
   public static boolean FileSystem_hasPermissions;

   static {
      FileSystem_hasPermissions = false;
      FileSystem_cacheFiles = new Hashtable(16);
   }
}
