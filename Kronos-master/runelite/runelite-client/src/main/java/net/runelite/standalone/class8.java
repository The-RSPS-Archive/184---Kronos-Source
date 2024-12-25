package net.runelite.standalone;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Iterator;
import net.runelite.api.InventoryID;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("r")
final class class8 implements class16 {
   @ObfuscatedName("gj")
   @ObfuscatedSignature(
      signature = "[Llp;"
   )
   static IndexedSprite[] scrollBarSprites;

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;B)Ljava/lang/Object;",
      garbageValue = "-93"
   )
   public Object vmethod210(Buffer var1) {
      return var1.readString();
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;Lkl;I)V",
      garbageValue = "-1135540783"
   )
   void method102(String var1, Buffer var2) {
      var2.writeString(var1);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/Object;Lkl;B)V",
      garbageValue = "0"
   )
   public void vmethod213(Object var1, Buffer var2) {
      this.method102((String)var1, var2);
   }

   @ObfuscatedName("u")
   static void setContainerItem(int container, int index, int itemId, int amount, String[] attributes, int attributeCount) {
      ItemContainer.copy$itemContainerSetItem(container, index, itemId, amount, attributes, attributeCount);
      int lastUpdateCycle = ViewportMouse.client.getGameCycle();
      if(lastUpdateCycle != ItemContainer.rl$lastCycle || container != ItemContainer.rl$lastContainer) {
         InventoryID var6 = InventoryID.getValue(container);
         if(var6 != null) {
            ItemContainer.rl$lastCycle = lastUpdateCycle;
            ItemContainer.rl$lastContainer = container;
            ItemContainerChanged var7 = new ItemContainerChanged(container, ViewportMouse.client.getItemContainer(var6));
            ViewportMouse.client.getCallbacks().postDeferred(ItemContainerChanged.class, var7);
         }
      }
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(Lbo;I)V",
      garbageValue = "688122635"
   )
   static void method101(World var0) {
      if(var0.method1212() != Client.isMembersWorld) {
         Client.isMembersWorld = var0.method1212();
         class4.method67(var0.method1212());
      }
      class158.worldHost = var0.host.split(":")[0];
      Client.worldId = var0.id;
      Client.worldProperties = var0.properties;
      HitSplatDefinition.port1 = Client.gameBuild == 0?43594:var0.id + 40000;
      BZip2State.port2 = Client.gameBuild == 0?443:var0.id + 50000;
      NPC.port3 = HitSplatDefinition.port1;
   }

   @ObfuscatedName("aq")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-1757923424"
   )
   protected static int method111() {
      int var0 = 0;
      if(AbstractUserComparator.garbageCollector == null || !AbstractUserComparator.garbageCollector.isValid()) {
         try {
            Iterator var1 = ManagementFactory.getGarbageCollectorMXBeans().iterator();

            while(var1.hasNext()) {
               GarbageCollectorMXBean var2 = (GarbageCollectorMXBean)var1.next();
               if(var2.isValid()) {
                  AbstractUserComparator.garbageCollector = var2;
                  GameShell.garbageCollectorLastCheckTimeMs = -1L;
                  GameShell.garbageCollectorLastCollectionTime = -1L;
               }
            }
         } catch (Throwable var11) {
            ;
         }
      }

      if(AbstractUserComparator.garbageCollector != null) {
         long var9 = class33.method680();
         long var3 = AbstractUserComparator.garbageCollector.getCollectionTime();
         if(GameShell.garbageCollectorLastCollectionTime != -1L) {
            long var5 = var3 - GameShell.garbageCollectorLastCollectionTime;
            long var7 = var9 - GameShell.garbageCollectorLastCheckTimeMs;
            if(0L != var7) {
               var0 = (int)(var5 * 100L / var7);
            }
         }

         GameShell.garbageCollectorLastCollectionTime = var3;
         GameShell.garbageCollectorLastCheckTimeMs = var9;
      }

      return var0;
   }

   @ObfuscatedName("gg")
   @ObfuscatedSignature(
      signature = "(ZB)V",
      garbageValue = "57"
   )
   static final void method104(boolean var0) {
      for(int var1 = 0; var1 < Client.npcCount; ++var1) {
         NPC var2 = Client.npcs[Client.npcIndices[var1]];
         if(var2 != null && var2.vmethod1611() && var2.definition.isVisible == var0 && var2.definition.method4408()) {
            int var3 = var2.x >> 7;
            int var4 = var2.y * 682054857 >> 7;
            if(var3 >= 0 && var3 < 104 && var4 >= 0 && var4 < 104) {
               if(var2.size == 1 && (var2.x & 127) == 64 && (var2.y * 682054857 & 127) == 64) {
                  if(Client.tileLastDrawnActor[var3][var4] == Client.viewportDrawCount) {
                     continue;
                  }

                  Client.tileLastDrawnActor[var3][var4] = Client.viewportDrawCount;
               }

               long var5 = class263.method4846(0, 0, 1, !var2.definition.isInteractable, Client.npcIndices[var1]);
               var2.playerCycle = Client.cycle;
               PacketWriter.scene.method3125(WorldMapRectangle.plane, var2.x, var2.y * 682054857, MusicPatchPcmStream.method3798(var2.size * 64 - 64 + var2.x, var2.size * 64 - 64 + var2.y * 682054857, WorldMapRectangle.plane), var2.size * 64 - 64 + 60, var2, var2.rotation, var5, var2.isWalking);
            }
         }
      }

   }
}
