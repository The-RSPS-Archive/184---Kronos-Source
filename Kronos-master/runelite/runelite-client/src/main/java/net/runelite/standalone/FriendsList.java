package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSFriend;
import net.runelite.rs.api.RSFriendsList;

@ObfuscatedName("jz")
public class FriendsList extends UserList<RSFriend> implements RSFriendsList {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lli;"
   )
   final LoginType loginType;
   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "Lje;"
   )
   public LinkDeque friendLoginUpdates;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 348884647
   )
   int field3618;

   @ObfuscatedSignature(
      signature = "(Lli;)V"
   )
   public FriendsList(LoginType var1) {
      super(400);
      this.field3618 = 1;
      this.friendLoginUpdates = new LinkDeque();
      this.loginType = var1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(II)[Ljh;",
      garbageValue = "951526901"
   )
   User[] vmethod5186(int var1) {
      return new Friend[var1];
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Ljq;ZB)Z",
      garbageValue = "-80"
   )
   public boolean method5184(Username var1, boolean var2) {
      Friend var3 = (Friend)this.method4771(var1);
      return var3 == null?false:!var2 || var3.world != 0;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)Ljh;",
      garbageValue = "2"
   )
   User vmethod5179() {
      return new Friend();
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "-1601651584"
   )
   public void method5182(Buffer var1, int var2) {
      while(true) {
         if(var1.offset < var2) {
            boolean var3 = var1.readUnsignedByte() == 1;
            Username var4 = new Username(var1.readString(), this.loginType);
            Username var5 = new Username(var1.readString(), this.loginType);
            int var6 = var1.readUnsignedShort();
            int var7 = var1.readUnsignedByte();
            int var8 = var1.readUnsignedByte();
            boolean var9 = (var8 & 2) != 0;
            boolean var10 = (var8 & 1) != 0;
            if(var6 > 0) {
               var1.readString();
               var1.readUnsignedByte();
               var1.readInt();
            }

            var1.readString();
            if(var4 != null && var4.method4991()) {
               Friend var11 = (Friend)this.method4815(var4);
               if(var3) {
                  Friend var12 = (Friend)this.method4815(var5);
                  if(var12 != null && var11 != var12) {
                     if(var11 != null) {
                        this.method4842(var12);
                     } else {
                        var11 = var12;
                     }
                  }
               }

               if(var11 != null) {
                  this.method4780(var11, var4, var5);
                  if(var6 != var11.world) {
                     boolean var14 = true;

                     for(FriendLoginUpdate var13 = (FriendLoginUpdate)this.friendLoginUpdates.method4872(); var13 != null; var13 = (FriendLoginUpdate)this.friendLoginUpdates.method4874()) {
                        if(var13.username.equals(var4)) {
                           if(var6 != 0 && var13.world == 0) {
                              var13.method3451();
                              var14 = false;
                           } else if(var6 == 0 && var13.world != 0) {
                              var13.method3451();
                              var14 = false;
                           }
                        }
                     }

                     if(var14) {
                        this.friendLoginUpdates.method4871(new FriendLoginUpdate(var4, var6));
                     }
                  }
               } else {
                  if(this.method4800() >= 400) {
                     continue;
                  }

                  var11 = (Friend)this.method4777(var4, var5);
               }

               if(var6 != var11.world) {
                  var11.int2 = ++this.field3618 - 1;
                  if(var11.world == -1 && var6 == 0) {
                     var11.int2 = -(var11.int2 * 1171982777) * -1761858423;
                  }

                  var11.world = var6;
               }

               var11.rank = var7;
               var11.field3615 = var9;
               var11.field3614 = var10;
               continue;
            }

            throw new IllegalStateException();
         }

         this.method4766();
         return;
      }
   }
}
