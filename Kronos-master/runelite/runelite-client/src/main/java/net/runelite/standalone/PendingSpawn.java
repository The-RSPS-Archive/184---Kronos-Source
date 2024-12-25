package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bt")
public final class PendingSpawn extends Node {
   @ObfuscatedName("gb")
   @ObfuscatedSignature(
      signature = "Llf;"
   )
   static Sprite redHintArrowSprite;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 1562451131
   )
   int plane;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 1264128253
   )
   int field632;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -753782081
   )
   int field631;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 179665149
   )
   int y;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -1018276189
   )
   int x;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 1262583567
   )
   int type;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = 2059869293
   )
   int orientation;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 1962154215
   )
   int objectId;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = -39012303
   )
   int hitpoints;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = -201127845
   )
   int delay;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 764785127
   )
   int field626;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 481754121
   )
   int id;

   PendingSpawn() {
      this.delay = 0;
      this.hitpoints = -1;
   }
}
