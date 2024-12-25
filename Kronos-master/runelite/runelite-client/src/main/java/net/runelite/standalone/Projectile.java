package net.runelite.standalone;

import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.ProjectileMoved;
import net.runelite.api.events.ProjectileSpawned;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSNPC;
import net.runelite.rs.api.RSPlayer;
import net.runelite.rs.api.RSProjectile;

@ObfuscatedName("cy")
public final class Projectile extends Entity implements RSProjectile {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 373161727
   )
   int plane;
   @ObfuscatedName("o")
   double x;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 396414685
   )
   int endHeight;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = 655131925
   )
   int cycleStart;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = -1560824427
   )
   int sourceZ;
   @ObfuscatedName("s")
   @ObfuscatedGetter(
      intValue = 797140937
   )
   int yaw;
   @ObfuscatedName("t")
   double speedY;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 1525037115
   )
   int sourceY;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -2017111693
   )
   int sourceX;
   @ObfuscatedName("w")
   double speedX;
   @ObfuscatedName("x")
   double speedZ;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = -1319621727
   )
   int slope;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -704302089
   )
   int id;
   @ObfuscatedName("a")
   double y;
   @ObfuscatedName("b")
   boolean isMoving;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = -216257617
   )
   int targetIndex;
   @ObfuscatedName("d")
   @ObfuscatedGetter(
      intValue = 1931929967
   )
   int frame;
   @ObfuscatedName("e")
   double z;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = -1677081341
   )
   int pitch;
   @ObfuscatedName("g")
   double speed;
   @ObfuscatedName("h")
   double accelerationZ;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = -1718323581
   )
   int startHeight;
   @ObfuscatedName("j")
   @ObfuscatedSignature(
      signature = "Lix;"
   )
   SequenceDefinition sequenceDefinition;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = -1977187681
   )
   int frameCycle;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = -673682431
   )
   int cycleEnd;

   Projectile(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
      this.isMoving = false;
      this.frame = 0;
      this.frameCycle = 0;
      this.id = var1;
      this.plane = var2;
      this.sourceX = var3;
      this.sourceY = var4;
      this.sourceZ = var5;
      this.cycleStart = var6;
      this.cycleEnd = var7;
      this.slope = var8;
      this.startHeight = var9;
      this.targetIndex = var10;
      this.endHeight = var11;
      this.isMoving = false;
      int var12 = InterfaceParent.method1139(this.id).sequence;
      if(var12 != -1) {
         this.sequenceDefinition = GrandExchangeOfferUnitPriceComparator.method1468(var12);
      } else {
         this.sequenceDefinition = null;
      }

      this.rl$$init();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IS)V",
      garbageValue = "-22789"
   )
   final void method2236(int var1) {
      this.isMoving = true;
      this.x += this.speedX * (double)var1;
      this.y += this.speedY * (double)var1;
      this.z += (double)var1 * (double)var1 * 0.5D * this.accelerationZ + (double)var1 * this.speedZ;
      this.speedZ += this.accelerationZ * (double)var1;
      this.yaw = (int)(Math.atan2(this.speedX, this.speedY) * 325.949D) + 1024 & 2047;
      this.pitch = (int)(Math.atan2(this.speedZ, this.speed) * 325.949D) & 2047;
      if(this.sequenceDefinition != null) {
         this.frameCycle += var1;

         while(true) {
            do {
               do {
                  if(this.frameCycle <= this.sequenceDefinition.frameLengths[this.frame]) {
                     return;
                  }

                  this.frameCycle -= this.sequenceDefinition.frameLengths[this.frame];
                  ++this.frame;
               } while(this.frame < this.sequenceDefinition.frameIds.length);

               this.frame -= this.sequenceDefinition.frameCount;
            } while(this.frame >= 0 && this.frame < this.sequenceDefinition.frameIds.length);

            this.frame = 0;
         }
      }
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)Ldh;"
   )
   protected final Model vmethod3072(int var1) {
      SpotAnimationDefinition var2 = InterfaceParent.method1139(this.id);
      Model var3 = var2.method4392(this.frame);
      if(var3 == null) {
         return null;
      } else {
         var3.method2369(this.pitch);
         return var3;
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IIIII)V",
      garbageValue = "1126340593"
   )
   final void method2237(int var1, int var2, int var3, int var4) {
      this.projectileMoved(var1, var2, var3, var4);
      double var5;
      if(!this.isMoving) {
         var5 = (double)(var1 - this.sourceX);
         double var7 = (double)(var2 - this.sourceY);
         double var9 = Math.sqrt(var7 * var7 + var5 * var5);
         this.x = (double)this.startHeight * var5 / var9 + (double)this.sourceX;
         this.y = (double)this.startHeight * var7 / var9 + (double)this.sourceY;
         this.z = (double)this.sourceZ;
      }

      var5 = (double)(this.cycleEnd + 1 - var4);
      this.speedX = ((double)var1 - this.x) / var5;
      this.speedY = ((double)var2 - this.y) / var5;
      this.speed = Math.sqrt(this.speedX * this.speedX + this.speedY * this.speedY);
      if(!this.isMoving) {
         this.speedZ = -this.speed * Math.tan(0.02454369D * (double)this.slope);
      }

      this.accelerationZ = 2.0D * ((double)var3 - this.z - var5 * this.speedZ) / (var5 * var5);
   }

   public int getRsInteracting() {
      return this.targetIndex;
   }

   public void projectileMoved(int var1, int var2, int var3, int var4) {
      LocalPoint var5 = new LocalPoint(var1, var2);
      ProjectileMoved var6 = new ProjectileMoved();
      var6.setProjectile(this);
      var6.setPosition(var5);
      var6.setZ(var3);
      ViewportMouse.client.getCallbacks().post(ProjectileMoved.class, var6);
   }

   public int getEndCycle() {
      return this.cycleEnd;
   }

   private void rl$$init() {
      ProjectileSpawned var1 = new ProjectileSpawned();
      var1.setProjectile(this);
      ViewportMouse.client.getCallbacks().post(ProjectileSpawned.class, var1);
   }

   public int getRemainingCycles() {
      int var1 = ViewportMouse.client.getGameCycle();
      return this.getEndCycle() - var1;
   }

   public net.runelite.api.Actor getInteracting() {
      int var1 = this.getRsInteracting();
      if(var1 == 0) {
         return null;
      } else {
         int var2;
         if(var1 > 0) {
            var2 = var1 - 1;
            RSNPC[] var4 = ViewportMouse.client.getCachedNPCs();
            return var4[var2];
         } else {
            var2 = -var1 - 1;
            if(var2 == ViewportMouse.client.getLocalInteractingIndex()) {
               return ViewportMouse.client.getLocalPlayer();
            } else {
               RSPlayer[] var3 = ViewportMouse.client.getCachedPlayers();
               return var3[var2];
            }
         }
      }
   }

   public int getId() {
      return this.id;
   }

   public int getFloor() {
      return this.plane;
   }

   public int getX1() {
      return this.sourceX;
   }

   public int getY1() {
      return this.sourceY;
   }

   public int getHeight() {
      return this.sourceZ;
   }

   public int getEndHeight() {
      return this.endHeight;
   }

   public int getStartMovementCycle() {
      return this.cycleStart;
   }

   public int getSlope() {
      return this.slope;
   }

   public int getStartHeight() {
      return this.startHeight;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public double getVelocityX() {
      return this.speedX;
   }

   public double getVelocityY() {
      return this.speedY;
   }

   public double getScalar() {
      return this.speed;
   }

   public double getVelocityZ() {
      return this.speedZ;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(IB)I",
      garbageValue = "106"
   )
   static int method2238(int var0) {
      ChatChannel var1 = (ChatChannel)Messages.Messages_channels.get(Integer.valueOf(var0));
      return var1 == null?0:var1.method1525();
   }

   @ObfuscatedName("ir")
   @ObfuscatedSignature(
      signature = "(Lho;I)Z",
      garbageValue = "-1182032943"
   )
   static final boolean method2244(Widget var0) {
      if(var0.cs1Comparisons == null) {
         return false;
      } else {
         for(int var1 = 0; var1 < var0.cs1Comparisons.length; ++var1) {
            int var2 = class223.method4124(var0, var1);
            int var3 = var0.cs1ComparisonValues[var1];
            if(var0.cs1Comparisons[var1] == 2) {
               if(var2 >= var3) {
                  return false;
               }
            } else if(var0.cs1Comparisons[var1] == 3) {
               if(var2 <= var3) {
                  return false;
               }
            } else if(var0.cs1Comparisons[var1] == 4) {
               if(var2 == var3) {
                  return false;
               }
            } else if(var3 != var2) {
               return false;
            }
         }

         return true;
      }
   }
}
