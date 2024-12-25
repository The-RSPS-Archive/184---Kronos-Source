package net.runelite.standalone;

import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("cb")
public class SecureRandomFuture {
   @ObfuscatedName("rq")
   @ObfuscatedGetter(
      intValue = -602698875
   )
   static int field746;
   @ObfuscatedName("cb")
   @ObfuscatedGetter(
      intValue = 515695907
   )
   public static int field748;
   @ObfuscatedName("n")
   Future future;
   @ObfuscatedName("z")
   ExecutorService executor;

   SecureRandomFuture() {
      this.executor = Executors.newSingleThreadExecutor();
      this.future = this.executor.submit(new SecureRandomCallable());
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-55622200"
   )
   boolean method1516() {
      return this.future.isDone();
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)Ljava/security/SecureRandom;",
      garbageValue = "311645204"
   )
   SecureRandom method1518() {
      SecureRandom var10000;
      try {
         var10000 = (SecureRandom)this.future.get();
      } catch (Exception var2) {
         return AttackOption.method2107();
      }

      return var10000;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-128121805"
   )
   void method1515() {
      this.executor.shutdown();
      this.executor = null;
   }

   @ObfuscatedName("ha")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1765847825"
   )
   static void method1521() {
      for(int var0 = 0; var0 < Client.menuOptionsCount; ++var0) {
         if(BZip2State.method5455(Client.menuOpcodes[var0])) {
            if(var0 < Client.menuOptionsCount - 1) {
               for(int var1 = var0; var1 < Client.menuOptionsCount - 1; ++var1) {
                  Client.menuActions[var1] = Client.menuActions[var1 + 1];
                  Client.menuTargets[var1] = Client.menuTargets[var1 + 1];
                  Client.menuOpcodes[var1] = Client.menuOpcodes[var1 + 1];
                  Client.menuIdentifiers[var1] = Client.menuIdentifiers[var1 + 1];
                  Client.menuArguments1[var1] = Client.menuArguments1[var1 + 1];
                  Client.menuArguments2[var1] = Client.menuArguments2[var1 + 1];
                  Client.menuShiftClick[var1] = Client.menuShiftClick[var1 + 1];
               }
            }

            --var0;
            --Client.menuOptionsCount;
            Client.onMenuOptionsChanged(-1);
         }
      }

      UserComparator6.method3507(FriendSystem.menuWidth / 2 + UrlRequester.menuX, class37.menuY);
   }
}
