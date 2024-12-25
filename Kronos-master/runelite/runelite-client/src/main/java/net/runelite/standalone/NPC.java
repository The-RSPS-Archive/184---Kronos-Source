package net.runelite.standalone;

import java.awt.Polygon;

import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.NpcDefinitionChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSNPC;
import net.runelite.rs.api.RSNPCDefinition;

@ObfuscatedName("ck")
public final class NPC extends Actor implements RSNPC {
   @ObfuscatedName("ej")
   @ObfuscatedGetter(
      intValue = 2095761251
   )
   static int port3;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lil;"
   )
   NPCDefinition definition;
   public int npcIndex;
   public boolean dead;

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IIZB)V",
      garbageValue = "1"
   )
   final void method1609(int var1, int var2, boolean var3) {
      if(super.sequence != -1 && GrandExchangeOfferUnitPriceComparator.method1468(super.sequence).field3431 == 1) {
         super.sequence = -1;
         this.animationChanged(-1);
      }

      if(!var3) {
         int var4 = var1 - super.pathX[0];
         int var5 = var2 - super.pathY[0];
         if(var4 >= -8 && var4 <= 8 && var5 >= -8 && var5 <= 8) {
            if(super.pathLength < 9) {
               ++super.pathLength;
            }

            for(int var6 = super.pathLength; var6 > 0; --var6) {
               super.pathX[var6] = super.pathX[var6 - 1];
               super.pathY[var6] = super.pathY[var6 - 1];
               super.pathTraversed[var6] = super.pathTraversed[var6 - 1];
            }

            super.pathX[0] = var1;
            super.pathY[0] = var2;
            super.pathTraversed[0] = 1;
            return;
         }
      }

      super.pathLength = 0;
      super.field726 = 0;
      super.field687 = 0;
      super.pathX[0] = var1;
      super.pathY[0] = var2;
      super.x = super.size * 64 + super.pathX[0] * 128;
      super.y = super.size * -297705920 + super.pathY[0] * -595411840;
   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(S)Z",
      garbageValue = "180"
   )
   final boolean vmethod1611() {
      return this.definition != null;
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)Ldh;"
   )
   protected final Model vmethod3072(int var1) {
      if(ViewportMouse.client.isInterpolateNpcAnimations() && this.getAnimation() != 6566) {
         int var2 = this.getActionFrame();
         int var3 = this.getPoseFrame();
         int var4 = this.getSpotAnimationFrame();

         Model var5;
         try {
            this.setActionFrame(Integer.MIN_VALUE | this.getActionFrameCycle() << 16 | var2);
            this.setPoseFrame(Integer.MIN_VALUE | this.getPoseFrameCycle() << 16 | var3);
            this.setSpotAnimationFrame(Integer.MIN_VALUE | this.getSpotAnimationFrameCycle() << 16 | var4);
            var5 = this.copy$getModel(var1);
         } finally {
            this.setActionFrame(var2);
            this.setPoseFrame(var3);
            this.setSpotAnimationFrame(var4);
         }

         return (Model)var5;
      } else {
         return (Model)this.copy$getModel(var1);
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IBB)V",
      garbageValue = "-90"
   )
   final void method1615(int var1, byte var2) {
      int var3 = super.pathX[0];
      int var4 = super.pathY[0];
      if(var1 == 0) {
         --var3;
         ++var4;
      }

      if(var1 == 1) {
         ++var4;
      }

      if(var1 == 2) {
         ++var3;
         ++var4;
      }

      if(var1 == 3) {
         --var3;
      }

      if(var1 == 4) {
         ++var3;
      }

      if(var1 == 5) {
         --var3;
         --var4;
      }

      if(var1 == 6) {
         --var4;
      }

      if(var1 == 7) {
         ++var3;
         --var4;
      }

      if(super.sequence != -1 && GrandExchangeOfferUnitPriceComparator.method1468(super.sequence).field3431 == 1) {
         super.sequence = -1;
         this.animationChanged(-1);
      }

      if(super.pathLength < 9) {
         ++super.pathLength;
      }

      for(int var5 = super.pathLength; var5 > 0; --var5) {
         super.pathX[var5] = super.pathX[var5 - 1];
         super.pathY[var5] = super.pathY[var5 - 1];
         super.pathTraversed[var5] = super.pathTraversed[var5 - 1];
      }

      super.pathX[0] = var3;
      super.pathY[0] = var4;
      super.pathTraversed[0] = var2;
   }

   public Polygon getConvexHull() {
      RSModel var1 = this.getModel();
      if(var1 == null) {
         return null;
      } else {
         int var2 = this.getDefinition().getSize();
         LocalPoint var3 = new LocalPoint(var2 * 64 - 64 + this.getX(), var2 * 64 - 64 + this.getY());
         int var4 = Perspective.getTileHeight(ViewportMouse.client, var3, ViewportMouse.client.getPlane());
         return var1.getConvexHull(this.getX(), this.getY(), this.getOrientation(), var4);
      }
   }

   public RSNPCDefinition getDefinition() {
      return this.definition;
   }

   public int getId() {
      RSNPCDefinition var1 = this.getDefinition();
      if(var1 != null && var1.getConfigs() != null) {
         var1 = var1.transform();
      }

      return var1 == null?-1:var1.getId();
   }

   @ObfuscatedSignature(
      signature = "(I)Ldh;"
   )
   public final Model copy$getModel(int var1) {
      if(this.definition == null) {
         return null;
      } else {
         SequenceDefinition var2 = super.sequence != -1 && super.sequenceDelay == 0?GrandExchangeOfferUnitPriceComparator.method1468(super.sequence):null;
         SequenceDefinition var3 = super.movementSequence != -1 && (super.movementSequence != super.readySequence || var2 == null)?GrandExchangeOfferUnitPriceComparator.method1468(super.movementSequence):null;
         Model var4 = this.definition.method4405(var2, super.sequenceFrame, var3, super.movementFrame);
         if(var4 == null) {
            return null;
         } else {
            var4.method2359();
            super.defaultHeight = var4.height;
            if(super.spotAnimation != -1 && super.spotAnimationFrame != -1) {
               Model var5 = InterfaceParent.method1139(super.spotAnimation).method4392(super.spotAnimationFrame);
               if(var5 != null) {
                  var5.method2423(0, -super.heightOffset, 0);
                  Model[] var6 = new Model[]{var4, var5};
                  var4 = new Model(var6, 2);
               }
            }

            if(this.definition.size == 1) {
               var4.isSingleTile = true;
            }

            return var4;
         }
      }
   }

   @Override
   public void onDefinitionChanged(net.runelite.api.NPCDefinition var1) {
      if(var1 == null) {
         ViewportMouse.client.getCallbacks().post(NpcDespawned.class, new NpcDespawned(this));
      } else if(this.getId() != -1) {
         ViewportMouse.client.getCallbacks().post(NpcDefinitionChanged.class, new NpcDefinitionChanged(this));
      }

   }

   public String getName() {
      RSNPCDefinition var1 = this.getDefinition();
      if(var1 != null && var1.getConfigs() != null) {
         var1 = var1.transform();
      }

      return var1 == null?null:var1.getName().replace(' ', ' ');
   }

   public int getCombatLevel() {
      RSNPCDefinition var1 = this.getDefinition();
      if(var1 != null && var1.getConfigs() != null) {
         var1 = var1.transform();
      }

      return var1 == null?-1:var1.getCombatLevel();
   }

   public int getIndex() {
      return this.npcIndex;
   }

   public void setIndex(int var1) {
      this.npcIndex = var1;
   }

   public net.runelite.api.NPCDefinition getTransformedDefinition() {
      RSNPCDefinition var1 = this.getDefinition();
      if(var1 != null && var1.getConfigs() != null) {
         var1 = var1.transform();
      }

      return var1;
   }

   public boolean isDead() {
      return this.dead;
   }

   public void setDead(boolean var1) {
      this.dead = var1;
   }

   public String getPrefix() {
      return "";
   }

   public String getSuffix() {
      return "";
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;II)V",
      garbageValue = "89732729"
   )
   static final void method1617(String var0, int var1) {
      PacketBufferNode var2 = InterfaceParent.method1140(ClientPacket.field2410, Client.packetWriter.isaacCipher);
      var2.packetBuffer.writeByte(class267.method4877(var0) + 1);
      var2.packetBuffer.writeByte(var1);
      var2.packetBuffer.writeString(var0);
      Client.packetWriter.method1622(var2);
   }
}
