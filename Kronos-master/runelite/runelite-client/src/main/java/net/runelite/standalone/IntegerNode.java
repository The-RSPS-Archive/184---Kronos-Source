package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.rs.api.RSIntegerNode;

@ObfuscatedName("fj")
public class IntegerNode extends Node implements RSIntegerNode {
   @ObfuscatedName("z")
   public int integer;

   public IntegerNode(int var1) {
      this.integer = var1;
   }

   public void setValue(int var1) {
      this.integer = var1;
   }

   public int getValue() {
      return this.integer;
   }
}
