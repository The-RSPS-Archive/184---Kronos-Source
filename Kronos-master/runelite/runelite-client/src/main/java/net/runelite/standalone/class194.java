package net.runelite.standalone;

import java.util.HashMap;
import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("go")
public class class194 {
   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "Llp;"
   )
   static IndexedSprite options_buttons_0Sprite;

   static {
      new HashMap();
   }

   @ObfuscatedName("jz")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1399038807"
   )
   static final void method3773() {
      Iterator var0 = Messages.Messages_hashTable.iterator();

      while(var0.hasNext()) {
         Message var1 = (Message)var0.next();
         var1.method880();
      }

      if(Varps.clanChat != null) {
         Varps.clanChat.method4931();
      }

   }
}
