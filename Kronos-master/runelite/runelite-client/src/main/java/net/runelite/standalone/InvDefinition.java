package net.runelite.standalone;

import net.runelite.api.events.MenuOptionClicked;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ig")
public class InvDefinition extends DualNode {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   static EvictingDualNodeHashTable InvDefinition_cached;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   static AbstractArchive InvDefinition_archive;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1122330625
   )
   public int size;

   static {
      InvDefinition_cached = new EvictingDualNodeHashTable(64);
   }

   InvDefinition() {
      this.size = 0;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "-57582576"
   )
   void method4329(Buffer var1, int var2) {
      if(var2 == 2) {
         this.size = var1.readUnsignedShort();
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;I)V",
      garbageValue = "1971272201"
   )
   void method4328(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            return;
         }

         this.method4329(var1, var2);
      }
   }

   @ObfuscatedName("hm")
   static final void method4339(int var0, int var1, int var2, int var3, String var4, String var5, int var6, int var7, int var8) {
      boolean var9 = true;
      if(var5 != null && var5.startsWith("!AUTHENTIC")) {
         var9 = false;
         var5 = var5.substring(10);
      }

      if(Client.printMenuActions && ViewportMouse.client.getLogger().isDebugEnabled()) {
         ViewportMouse.client.getLogger().debug("Menuaction: {} {} {} {} {} {} {} {} {}", new Object[]{Integer.valueOf(var0), Integer.valueOf(var1), Integer.valueOf(var2), Integer.valueOf(var3), var4, var5, Integer.valueOf(var6), Integer.valueOf(var7), Boolean.valueOf(var9)});
      }

      if(var2 >= 2000) {
         var2 -= 2000;
      }

      MenuOptionClicked var10 = new MenuOptionClicked(var4, var5, var3, var2, var0, var1, false, var9, ViewportMouse.client.getMouseCurrentButton());
      ViewportMouse.client.getCallbacks().post(MenuOptionClicked.class, var10);
      if(!var10.isConsumed()) {
         Client.copy$menuAction(var10.getParam0(), var10.getParam1(), var10.getOpcode(), var10.getIdentifier(), var10.getOption(), var10.getTarget(), var6, var7, var8);
      }
   }

   @ObfuscatedName("ig")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;Lho;I)Ljava/lang/String;",
      garbageValue = "-1374184738"
   )
   static String method4340(String var0, Widget var1) {
      if(var0.indexOf("%") != -1) {
         for(int var2 = 1; var2 <= 5; ++var2) {
            while(true) {
               int var3 = var0.indexOf("%" + var2);
               if(var3 == -1) {
                  break;
               }

               String var4 = var0.substring(0, var3);
               int var6 = class223.method4124(var1, var2 - 1);
               String var5;
               if(var6 < 999999999) {
                  var5 = Integer.toString(var6);
               } else {
                  var5 = "*";
               }

               var0 = var4 + var5 + var0.substring(var3 + 2);
            }
         }
      }

      return var0;
   }
}
