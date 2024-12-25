package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("h")
public class WorldMapSection0 implements WorldMapSection {
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -1930448807
   )
   int newZ;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = -1947965675
   )
   int newChunkXHigh;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -386033045
   )
   int newY;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -1004209975
   )
   int oldChunkXLow;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 543857047
   )
   int newX;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -542760431
   )
   int oldY;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 686443425
   )
   int oldX;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = -478007643
   )
   int oldChunkXHigh;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 1037587117
   )
   int oldZ;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = 627288657
   )
   int newChunkYHigh;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = 1495103355
   )
   int newChunkYLow;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 1473412629
   )
   int newChunkXLow;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 940484663
   )
   int oldChunkYHigh;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 645797773
   )
   int oldChunkYLow;

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IIII)Z",
      garbageValue = "-327883108"
   )
   public boolean vmethod5843(int var1, int var2, int var3) {
      return var1 >= this.oldZ && var1 < this.oldZ + this.newZ?var2 >= (this.oldX << 6) + (this.oldChunkXLow << 3) && var2 <= (this.oldX << 6) + (this.oldChunkXHigh << 3) + 7 && var3 >= (this.oldY << 6) + (this.oldChunkYLow << 3) && var3 <= (this.oldY << 6) + (this.oldChunkYHigh << 3) + 7:false;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Lkl;B)V",
      garbageValue = "-99"
   )
   public void decode(Buffer var1) {
      this.oldZ = var1.readUnsignedByte();
      this.newZ = var1.readUnsignedByte();
      this.oldX = var1.readUnsignedShort();
      this.oldChunkXLow = var1.readUnsignedByte();
      this.oldChunkXHigh = var1.readUnsignedByte();
      this.oldY = var1.readUnsignedShort();
      this.oldChunkYLow = var1.readUnsignedByte();
      this.oldChunkYHigh = var1.readUnsignedByte();
      this.newX = var1.readUnsignedShort();
      this.newChunkXLow = var1.readUnsignedByte();
      this.newChunkXHigh = var1.readUnsignedByte();
      this.newY = var1.readUnsignedShort();
      this.newChunkYLow = var1.readUnsignedByte();
      this.newChunkYHigh = var1.readUnsignedByte();
      this.method3888();
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1221797110"
   )
   void method3888() {
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(III)Lhb;",
      garbageValue = "1439979703"
   )
   public Coord vmethod5846(int var1, int var2) {
      if(!this.vmethod5844(var1, var2)) {
         return null;
      } else {
         int var3 = this.oldX * 64 - this.newX * 64 + (this.oldChunkXLow * 8 - this.newChunkXLow * 8) + var1;
         int var4 = this.oldY * 64 - this.newY * 64 + var2 + (this.oldChunkYLow * 8 - this.newChunkYLow * 8);
         return new Coord(this.oldZ, var3, var4);
      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(IIII)[I",
      garbageValue = "-1322751923"
   )
   public int[] vmethod5845(int var1, int var2, int var3) {
      if(!this.vmethod5843(var1, var2, var3)) {
         return null;
      } else {
         int[] var4 = new int[]{this.newX * 64 - this.oldX * 64 + var2 + (this.newChunkXLow * 8 - this.oldChunkXLow * 8), var3 + (this.newY * 64 - this.oldY * 64) + (this.newChunkYLow * 8 - this.oldChunkYLow * 8)};
         return var4;
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(III)Z",
      garbageValue = "828411560"
   )
   public boolean vmethod5844(int var1, int var2) {
      return var1 >= (this.newX << 6) + (this.newChunkXLow << 3) && var1 <= (this.newX << 6) + (this.newChunkXHigh << 3) + 7 && var2 >= (this.newY << 6) + (this.newChunkYLow << 3) && var2 <= (this.newY << 6) + (this.newChunkYHigh << 3) + 7;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lac;I)V",
      garbageValue = "2098672163"
   )
   public void vmethod5850(WorldMapArea var1) {
      if(var1.regionLowX > this.newX) {
         var1.regionLowX = this.newX;
      }

      if(var1.regionHighX < this.newX) {
         var1.regionHighX = this.newX;
      }

      if(var1.regionLowY > this.newY) {
         var1.regionLowY = this.newY;
      }

      if(var1.regionHighY < this.newY) {
         var1.regionHighY = this.newY;
      }

   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(II)Z",
      garbageValue = "1193049266"
   )
   public static boolean method3907(int var0) {
      return (var0 >> 29 & 1) != 0;
   }

   @ObfuscatedName("fh")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)V",
      garbageValue = "1698246852"
   )
   static final void method3906(String var0) {
      if(var0.equalsIgnoreCase("toggleroof")) {
         AbstractArchive.clientPreferences.roofsHidden = !AbstractArchive.clientPreferences.roofsHidden;
         Language.method3830();
         if(AbstractArchive.clientPreferences.roofsHidden) {
            class217.sendGameMessage(99, "", "Roofs are now all hidden");
         } else {
            class217.sendGameMessage(99, "", "Roofs will only be removed selectively");
         }
      }

      if(var0.equalsIgnoreCase("displayfps")) {
         Client.displayFps = !Client.displayFps;
      }

      if(var0.equalsIgnoreCase("renderself")) {
         Client.renderSelf = !Client.renderSelf;
      }

      if(var0.equalsIgnoreCase("mouseovertext")) {
         Client.showMouseOverText = !Client.showMouseOverText;
      }

      if(Client.staffModLevel >= 2) {
         if(var0.equalsIgnoreCase("errortest")) {
            throw new RuntimeException();
         }

         if(var0.equalsIgnoreCase("showcoord")) {
            Tiles.worldMap.showCoord = !Tiles.worldMap.showCoord;
         }

         if(var0.equalsIgnoreCase("fpson")) {
            Client.displayFps = true;
         }

         if(var0.equalsIgnoreCase("fpsoff")) {
            Client.displayFps = false;
         }

         if(var0.equalsIgnoreCase("gc")) {
            System.gc();
         }

         if(var0.equalsIgnoreCase("clientdrop")) {
            MouseRecorder.method1208();
         }
      }

      PacketBufferNode var1 = InterfaceParent.method1140(ClientPacket.field2357, Client.packetWriter.isaacCipher);
      var1.packetBuffer.writeByte(var0.length() + 1);
      var1.packetBuffer.writeString(var0);
      Client.packetWriter.method1622(var1);
   }
}
