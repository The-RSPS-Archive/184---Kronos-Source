package net.runelite.standalone;

import net.runelite.api.ChatMessageType;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSClanChat;
import net.runelite.rs.api.RSMessage;
import net.runelite.rs.api.RSUsername;

@ObfuscatedName("be")
public class Message extends DualNode implements RSMessage {
   @ObfuscatedName("eh")
   @ObfuscatedGetter(
      intValue = -1928576619
   )
   static int field398;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -1483460383
   )
   int cycle;
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Ljm;"
   )
   TriBool isFromFriend0;
   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "Ljm;"
   )
   TriBool isFromIgnored0;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Ljq;"
   )
   Username senderUsername;
   @ObfuscatedName("u")
   String sender;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -991694021
   )
   int type;
   @ObfuscatedName("y")
   String text;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 36313413
   )
   int count;
   public String runeLiteFormatMessage;
   public int rl$timestamp;
   @ObfuscatedName("m")
   String prefix;

   Message(int var1, String var2, String var3, String var4) {
      this.isFromFriend0 = TriBool.TriBool_unknown;
      this.isFromIgnored0 = TriBool.TriBool_unknown;
      this.method861(var1, var2, var3, var4);
      this.rl$$init();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "10"
   )
   void method884() {
      this.isFromFriend0 = TriBool.TriBool_unknown;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-759719202"
   )
   final boolean method866() {
      if(this.isFromIgnored0 == TriBool.TriBool_unknown) {
         this.method867();
      }

      return this.isFromIgnored0 == TriBool.TriBool_true;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1328990032"
   )
   void method867() {
      this.isFromIgnored0 = Tiles.friendSystem.ignoreList.method4770(this.senderUsername)?TriBool.TriBool_true:TriBool.TriBool_false;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "671441692"
   )
   void method880() {
      this.isFromIgnored0 = TriBool.TriBool_unknown;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1169950855"
   )
   void method862() {
      this.isFromFriend0 = Tiles.friendSystem.friendsList.method4770(this.senderUsername)?TriBool.TriBool_true:TriBool.TriBool_false;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-1794465193"
   )
   final boolean method865() {
      if(this.isFromFriend0 == TriBool.TriBool_unknown) {
         this.method862();
      }

      return this.isFromFriend0 == TriBool.TriBool_true;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;B)V",
      garbageValue = "29"
   )
   void method861(int var1, String var2, String var3, String var4) {
      int var5 = ++Messages.Messages_count - 1;
      this.count = var5;
      this.cycle = Client.cycle;
      this.type = var1;
      this.sender = var2;
      this.method868();
      this.prefix = var3;
      this.text = var4;
      this.method884();
      this.method880();
      this.setMessage(var1, var2, var3, var4);
   }

   public int getRSType() {
      return this.type;
   }

   public RSUsername getSenderUsername() {
      return this.senderUsername;
   }

   private void rl$$init() {
      this.rl$timestamp = (int)(System.currentTimeMillis() / 1000L);
   }

   public void setMessage(int var1, String var2, String var3, String var4) {
      this.runeLiteFormatMessage = null;
      this.rl$timestamp = (int)(System.currentTimeMillis() / 1000L);
   }

   public ChatMessageType getType() {
      return ChatMessageType.of(this.getRSType());
   }

   public String getRuneLiteFormatMessage() {
      return this.runeLiteFormatMessage;
   }

   public void setRuneLiteFormatMessage(String var1) {
      this.runeLiteFormatMessage = var1;
   }

   public int getTimestamp() {
      return this.rl$timestamp;
   }

   public void setTimestamp(int var1) {
      this.rl$timestamp = var1;
   }

   public boolean isFromClanMate() {
      RSClanChat var1 = ViewportMouse.client.getClanMemberManager();
      return var1 != null && var1.isMember(this.getSenderUsername());
   }

   public int getId() {
      return this.count;
   }

   public void setName(String var1) {
      this.sender = var1;
   }

   public String getName() {
      return this.sender;
   }

   public void setSender(String var1) {
      this.prefix = var1;
   }

   public String getSender() {
      return this.prefix;
   }

   public void setValue(String var1) {
      this.text = var1;
   }

   public String getValue() {
      return this.text;
   }

   public boolean isFromFriend() {
      return this.method865();
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1885223886"
   )
   final void method868() {
      if(this.sender != null) {
         this.senderUsername = new Username(WorldMapSectionType.method113(this.sender), WorldMapSection1.loginType);
      } else {
         this.senderUsername = null;
      }

   }

   @ObfuscatedName("fk")
   @ObfuscatedSignature(
      signature = "(IIII)V",
      garbageValue = "2017032545"
   )
   static void method888(int var0, int var1, int var2) {
      if(Client.soundEffectVolume != 0 && var1 != 0 && Client.soundEffectCount < 50) {
         Client.soundEffectIds[Client.soundEffectCount] = var0;
         Client.queuedSoundEffectLoops[Client.soundEffectCount] = var1;
         Client.queuedSoundEffectDelays[Client.soundEffectCount] = var2;
         Client.soundEffects[Client.soundEffectCount] = null;
         Client.soundLocations[Client.soundEffectCount] = 0;
         ++Client.soundEffectCount;
         Client.queuedSoundEffectCountChanged(-1);
      }

   }

   @ObfuscatedName("gi")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "200131202"
   )
   static final void method876(int var0, int var1) {
      if(Client.hintArrowType == 2) {
         PlayerAppearance.method4162((Client.hintArrowX - class215.baseX << 7) + Client.hintArrowSubX * 1732957465, (Client.hintArrowY - class304.baseY << 7) + Client.hintArrowSubY * 739749845, Client.hintArrowHeight * 2);
         if(Client.viewportTempX > -1 && Client.cycle % 20 < 10) {
            ReflectionCheck.headIconHintSprites[0].method6159(var0 + Client.viewportTempX - 12, Client.viewportTempY + var1 - 28);
         }

      }
   }
}
