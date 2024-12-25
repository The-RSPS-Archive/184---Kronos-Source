package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("hy")
public class ArchiveDiskAction extends Node {
   @ObfuscatedName("n")
   public byte[] data;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   public Archive archive;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lkg;"
   )
   public ArchiveDisk archiveDisk;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -1673038183
   )
   int type;
}
