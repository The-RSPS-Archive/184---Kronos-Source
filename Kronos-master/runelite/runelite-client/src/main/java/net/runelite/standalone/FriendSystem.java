package net.runelite.standalone;

import net.runelite.api.events.FriendAdded;
import net.runelite.api.events.FriendRemoved;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSFriendSystem;
import net.runelite.rs.api.RSFriendsList;
import net.runelite.rs.api.RSIgnoreList;
import net.runelite.rs.api.RSUsername;

@ObfuscatedName("bf")
public class FriendSystem implements RSFriendSystem {
   @ObfuscatedName("kp")
   @ObfuscatedGetter(
      intValue = 784647043
   )
   static int menuWidth;
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Ljw;"
   )
   public final IgnoreList ignoreList;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -154127357
   )
   int field406;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Ljz;"
   )
   public final FriendsList friendsList;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lli;"
   )
   final LoginType loginType;

   @ObfuscatedSignature(
      signature = "(Lli;)V"
   )
   FriendSystem(LoginType var1) {
      this.field406 = 0;
      this.loginType = var1;
      this.friendsList = new FriendsList(var1);
      this.ignoreList = new IgnoreList(var1);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1827488115"
   )
   final void method892() {
      this.field406 = 1;
   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(S)Z",
      garbageValue = "30910"
   )
   final boolean method921() {
      return this.ignoreList.method4822() || this.ignoreList.method4800() >= 100 && Client.field844 != 1;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Ljq;ZI)Z",
      garbageValue = "-99546473"
   )
   final boolean method896(Username var1, boolean var2) {
      return var1 == null?false:(var1.equals(class215.localPlayer.username)?true:this.friendsList.method5184(var1, var2));
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(Ljq;I)Z",
      garbageValue = "-1271048446"
   )
   final boolean method897(Username var1) {
      return var1 == null?false:this.ignoreList.method4770(var1);
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "2097335947"
   )
   final void method895() {
      this.field406 = 0;
      this.friendsList.method4767();
      this.ignoreList.method4767();
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1809100173"
   )
   final void method894() {
      for(FriendLoginUpdate var1 = (FriendLoginUpdate)this.friendsList.friendLoginUpdates.method4872(); var1 != null; var1 = (FriendLoginUpdate)this.friendsList.friendLoginUpdates.method4874()) {
         if((long)var1.field3559 < class33.method680() / 1000L - 5L) {
            if(var1.world > 0) {
               class217.sendGameMessage(5, "", var1.username + " has logged in.");
            }

            if(var1.world == 0) {
               class217.sendGameMessage(5, "", var1.username + " has logged out.");
            }

            var1.method3451();
         }
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "87159269"
   )
   final void method918(Buffer var1, int var2) {
      this.friendsList.method5182(var1, var2);
      this.field406 = 2;
      Coord.method3928();
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-537546182"
   )
   boolean method891() {
      return this.field406 == 2;
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)V",
      garbageValue = "1378951166"
   )
   final void method942(String var1) {
      this.rl$removeFriend(var1);
      if(var1 != null) {
         Username var2 = new Username(var1, this.loginType);
         if(var2.method4991()) {
            if(this.friendsList.method4802(var2)) {
               Client.field952 = Client.cycleCntr;
               PacketBufferNode var3 = InterfaceParent.method1140(ClientPacket.field2388, Client.packetWriter.isaacCipher);
               var3.packetBuffer.writeByte(class267.method4877(var1));
               var3.packetBuffer.writeString(var1);
               Client.packetWriter.method1622(var3);
            }

            Coord.method3928();
         }
      }
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)V",
      garbageValue = "1251138329"
   )
   final void method900(String var1) {
      if(var1 != null) {
         Username var2 = new Username(var1, this.loginType);
         if(var2.method4991()) {
            if(this.method921()) {
               WorldMapCacheName.method674();
            } else {
               StringBuilder var10000;
               String var4;
               if(class215.localPlayer.username.equals(var2)) {
                  var10000 = null;
                  var4 = "You can\'t add yourself to your own ignore list";
                  class217.sendGameMessage(30, "", var4);
               } else {
                  Object var10001;
                  if(this.method897(var2)) {
                     var10000 = (new StringBuilder()).append(var1);
                     var10001 = null;
                     var4 = var10000.append(" is already on your ignore list").toString();
                     class217.sendGameMessage(30, "", var4);
                  } else if(this.method896(var2, false)) {
                     var10000 = new StringBuilder();
                     var10001 = null;
                     var10000 = var10000.append("Please remove ").append(var1);
                     var10001 = null;
                     var4 = var10000.append(" from your friend list first").toString();
                     class217.sendGameMessage(30, "", var4);
                  } else {
                     PacketBufferNode var3 = InterfaceParent.method1140(ClientPacket.field2435, Client.packetWriter.isaacCipher);
                     var3.packetBuffer.writeByte(class267.method4877(var1));
                     var3.packetBuffer.writeString(var1);
                     Client.packetWriter.method1622(var3);
                  }
               }
            }
         }
      }
   }

   public void rl$removeFriend(String var1) {
      FriendRemoved var2 = new FriendRemoved(var1);
      ViewportMouse.client.getCallbacks().post(FriendRemoved.class, var2);
   }

   public void rl$addFriend(String var1) {
      FriendAdded var2 = new FriendAdded(var1);
      ViewportMouse.client.getCallbacks().post(FriendAdded.class, var2);
   }

   public RSFriendsList getFriendContainer() {
      return this.friendsList;
   }

   public RSIgnoreList getIgnoreContainer() {
      return this.ignoreList;
   }

   public boolean isFriended(RSUsername var1, boolean var2) {
      return this.method896((Username)var1, var2);
   }

   public void addFriend(String var1) {
      this.method898(var1);
   }

   public void removeFriend(String var1) {
      this.method942(var1);
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)V",
      garbageValue = "-1989545258"
   )
   final void method923(String var1) {
      if(var1 != null) {
         Username var2 = new Username(var1, this.loginType);
         if(var2.method4991()) {
            if(this.ignoreList.method4802(var2)) {
               Client.field952 = Client.cycleCntr;
               PacketBufferNode var3 = InterfaceParent.method1140(ClientPacket.field2424, Client.packetWriter.isaacCipher);
               var3.packetBuffer.writeByte(class267.method4877(var1));
               var3.packetBuffer.writeString(var1);
               Client.packetWriter.method1622(var3);
            }

            class194.method3773();
         }
      }
   }

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      signature = "(Ljq;I)Z",
      garbageValue = "869979117"
   )
   final boolean method903(Username var1) {
      Friend var2 = (Friend)this.friendsList.method4771(var1);
      return var2 != null && var2.method4959();
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-414102513"
   )
   final boolean method924() {
      return this.friendsList.method4822() || this.friendsList.method4800() >= 200 && Client.field844 != 1;
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)V",
      garbageValue = "70213064"
   )
   final void method898(String var1) {
      this.rl$addFriend(var1);
      if(var1 != null) {
         Username var2 = new Username(var1, this.loginType);
         if(var2.method4991()) {
            StringBuilder var10000;
            String var4;
            if(this.method924()) {
               var10000 = null;
               var4 = "Your friend list is full. Max of 200 for free users, and 400 for members";
               class217.sendGameMessage(30, "", var4);
            } else if(class215.localPlayer.username.equals(var2)) {
               var10000 = null;
               var4 = "You can\'t add yourself to your own friend list";
               class217.sendGameMessage(30, "", var4);
            } else {
               Object var10001;
               if(this.method896(var2, false)) {
                  var10000 = (new StringBuilder()).append(var1);
                  var10001 = null;
                  var4 = var10000.append(" is already on your friend list").toString();
                  class217.sendGameMessage(30, "", var4);
               } else if(this.method897(var2)) {
                  var10000 = new StringBuilder();
                  var10001 = null;
                  var10000 = var10000.append("Please remove ").append(var1);
                  var10001 = null;
                  var4 = var10000.append(" from your ignore list first").toString();
                  class217.sendGameMessage(30, "", var4);
               } else {
                  PacketBufferNode var3 = InterfaceParent.method1140(ClientPacket.field2420, Client.packetWriter.isaacCipher);
                  var3.packetBuffer.writeByte(class267.method4877(var1));
                  var3.packetBuffer.writeString(var1);
                  Client.packetWriter.method1622(var3);
               }
            }
         }
      }
   }
}
