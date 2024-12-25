package net.runelite.standalone;

import java.security.SecureRandom;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.GraphicsObjectCreated;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSGraphicsObject;

@ObfuscatedName("bp")
public final class GraphicsObject extends Entity implements RSGraphicsObject {
   @ObfuscatedName("ec")
   static SecureRandom secureRandom;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 781452541
   )
   int id;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -1722241771
   )
   int y;
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Lix;"
   )
   SequenceDefinition sequenceDefinition;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1240960337
   )
   int x;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -1951259679
   )
   int plane;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 34616617
   )
   int cycleStart;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = -1223841967
   )
   int frameCycle;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -725302413
   )
   int height;
   @ObfuscatedName("i")
   boolean isFinished;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 926579167
   )
   int frame;

   GraphicsObject(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.frame = 0;
      this.frameCycle = 0;
      this.isFinished = false;
      this.id = var1;
      this.plane = var2;
      this.x = var3;
      this.y = var4;
      this.height = var5;
      this.cycleStart = var7 + var6;
      int var8 = InterfaceParent.method1139(this.id).sequence;
      if(var8 != -1) {
         this.isFinished = false;
         this.sequenceDefinition = GrandExchangeOfferUnitPriceComparator.method1468(var8);
      } else {
         this.isFinished = true;
      }

      this.rl$$init();
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)Ldh;"
   )
   protected final Model vmethod3072(int var1) {
      SpotAnimationDefinition var2 = InterfaceParent.method1139(this.id);
      Model var3;
      if(!this.isFinished) {
         var3 = var2.method4392(this.frame);
      } else {
         var3 = var2.method4392(-1);
      }

      return var3 == null?null:var3;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "-72"
   )
   final void method1255(int var1) {
      if(!this.isFinished) {
         this.frameCycle += var1;

         while(this.frameCycle > this.sequenceDefinition.frameLengths[this.frame]) {
            this.frameCycle -= this.sequenceDefinition.frameLengths[this.frame];
            ++this.frame;
            if(this.frame >= this.sequenceDefinition.frameIds.length) {
               this.isFinished = true;
               break;
            }
         }

      }
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   private void rl$$init() {
      GraphicsObjectCreated var1 = new GraphicsObjectCreated(this);
      ViewportMouse.client.getCallbacks().post(GraphicsObjectCreated.class, var1);
   }

   public LocalPoint getLocation() {
      return new LocalPoint(this.getX(), this.getY());
   }

   public int getHeight() {
      return this.height;
   }

   public int getId() {
      return this.id;
   }

   public int getStartCycle() {
      return this.cycleStart;
   }

   public int getLevel() {
      return this.plane;
   }
}
