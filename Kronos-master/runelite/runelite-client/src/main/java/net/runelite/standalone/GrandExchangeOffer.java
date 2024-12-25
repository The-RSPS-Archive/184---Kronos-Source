package net.runelite.standalone;

import java.io.IOException;
import net.runelite.api.GrandExchangeOfferState;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSGrandExchangeOffer;

@ObfuscatedName("i")
public class GrandExchangeOffer implements RSGrandExchangeOffer {
   @ObfuscatedName("nx")
   @ObfuscatedSignature(
      signature = "[Lho;"
   )
   static Widget[] field3107;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 152227217
   )
   public int id;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 22250551
   )
   public int currentPrice;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 244433103
   )
   public int currentQuantity;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -1942003277
   )
   public int totalQuantity;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1304592967
   )
   public int unitPrice;
   @ObfuscatedName("z")
   byte state;

   public GrandExchangeOffer() {
   }

   @ObfuscatedSignature(
      signature = "(Lkl;Z)V",
      garbageValue = "0"
   )
   public GrandExchangeOffer(Buffer var1, boolean var2) {
      this.state = var1.readByte();
      this.id = var1.readUnsignedShort();
      this.unitPrice = var1.readInt();
      this.totalQuantity = var1.readInt();
      this.currentQuantity = var1.readInt();
      this.currentPrice = var1.readInt();
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "3"
   )
   void method4206(int var1) {
      this.state &= -9;
      if(var1 == 1) {
         this.state = (byte)(this.state | 8);
      }

   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "1269791927"
   )
   void method4199(int var1) {
      this.state &= -8;
      this.state = (byte)(this.state | var1 & 7);
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "-9"
   )
   public int method4198() {
      return (this.state & 8) == 8?1:0;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-719257783"
   )
   public int method4197() {
      return this.state & 7;
   }

   public byte getRSState() {
      return this.state;
   }

   public int getQuantitySold() {
      return this.currentQuantity;
   }

   public int getTotalQuantity() {
      return this.totalQuantity;
   }

   public GrandExchangeOfferState getState() {
      byte var1 = this.getRSState();
      boolean var2 = (var1 & 8) == 8;
      boolean var3 = (var1 & 4) == 4;
      return var1 == 0?GrandExchangeOfferState.EMPTY:(var3 && this.getQuantitySold() < this.getTotalQuantity()?(var2?GrandExchangeOfferState.CANCELLED_SELL:GrandExchangeOfferState.CANCELLED_BUY):(var2?(var3?GrandExchangeOfferState.SOLD:GrandExchangeOfferState.SELLING):(var3?GrandExchangeOfferState.BOUGHT:GrandExchangeOfferState.BUYING)));
   }

   public int getItemId() {
      return this.id;
   }

   public int getPrice() {
      return this.unitPrice;
   }

   public int getSpent() {
      return this.currentPrice;
   }

   @ObfuscatedName("gc")
   @ObfuscatedSignature(
      signature = "(ZI)V",
      garbageValue = "2022647645"
   )
   static final void method4218(boolean var0) {
      WorldMapID.method687();
      ++Client.packetWriter.pendingWrites;
      if(Client.packetWriter.pendingWrites >= 50 || var0) {
         Client.packetWriter.pendingWrites = 0;
         if(!Client.field938 && Client.packetWriter.method1624() != null) {
            PacketBufferNode var1 = InterfaceParent.method1140(ClientPacket.field2363, Client.packetWriter.isaacCipher);
            Client.packetWriter.method1622(var1);

            try {
               Client.packetWriter.method1619();
            } catch (IOException var3) {
               Client.field938 = true;
            }
         }

      }
   }
}
