package net.runelite.standalone;

import java.util.LinkedList;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("f")
public abstract class AbstractWorldMapData {
   @ObfuscatedName("iz")
   @ObfuscatedGetter(
      intValue = 742897815
   )
   static int selectedItemId;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 1851853303
   )
   int regionYLow;
   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "[[[[Lk;"
   )
   WorldMapDecoration[][][][] decorations;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 593055761
   )
   int planes;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -1864196889
   )
   int groupId;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 804294653
   )
   int minPlane;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 796055175
   )
   int regionY;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -317022193
   )
   int regionX;
   @ObfuscatedName("y")
   short[][][] floorUnderlayIds;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -663874969
   )
   int regionXLow;
   @ObfuscatedName("a")
   boolean field1908;
   @ObfuscatedName("b")
   byte[][][] field1895;
   @ObfuscatedName("c")
   byte[][][] field1907;
   @ObfuscatedName("e")
   boolean field1909;
   @ObfuscatedName("i")
   short[][][] floorOverlayIds;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = -644572197
   )
   int fileId;

   AbstractWorldMapData() {
      this.groupId = -1;
      this.fileId = -1;
      new LinkedList();
      this.field1908 = false;
      this.field1909 = false;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;I)V",
      garbageValue = "1549979331"
   )
   abstract void vmethod3304(Buffer var1);

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(IILkl;II)V",
      garbageValue = "2059676399"
   )
   void method3302(int var1, int var2, Buffer var3, int var4) {
      boolean var5 = (var4 & 2) != 0;
      if(var5) {
         this.floorOverlayIds[0][var1][var2] = (short)var3.readUnsignedByte();
      }

      this.floorUnderlayIds[0][var1][var2] = (short)var3.readUnsignedByte();
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-2009466013"
   )
   int method3306() {
      return this.regionY;
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "1858482709"
   )
   boolean method3298() {
      return this.field1908 && this.field1909;
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(IILkl;IB)V",
      garbageValue = "-56"
   )
   void method3308(int var1, int var2, Buffer var3, int var4) {
      int var5 = ((var4 & 24) >> 3) + 1;
      boolean var6 = (var4 & 2) != 0;
      boolean var7 = (var4 & 4) != 0;
      this.floorUnderlayIds[0][var1][var2] = (short)var3.readUnsignedByte();
      int var8;
      int var9;
      int var11;
      if(var6) {
         var8 = var3.readUnsignedByte();

         for(var9 = 0; var9 < var8; ++var9) {
            int var10 = var3.readUnsignedByte();
            if(var10 != 0) {
               this.floorOverlayIds[var9][var1][var2] = (short)var10;
               var11 = var3.readUnsignedByte();
               this.field1907[var9][var1][var2] = (byte)(var11 >> 2);
               this.field1895[var9][var1][var2] = (byte)(var11 & 3);
            }
         }
      }

      if(var7) {
         for(var8 = 0; var8 < var5; ++var8) {
            var9 = var3.readUnsignedByte();
            if(var9 != 0) {
               WorldMapDecoration[] var14 = this.decorations[var8][var1][var2] = new WorldMapDecoration[var9];

               for(var11 = 0; var11 < var9; ++var11) {
                  int var12 = var3.method5507();
                  int var13 = var3.readUnsignedByte();
                  var14[var11] = new WorldMapDecoration(var12, var13 >> 2, var13 & 3);
               }
            }
         }
      }

   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(IILkl;B)V",
      garbageValue = "10"
   )
   void method3310(int var1, int var2, Buffer var3) {
      int var4 = var3.readUnsignedByte();
      if(var4 != 0) {
         if((var4 & 1) != 0) {
            this.method3302(var1, var2, var3, var4);
         } else {
            this.method3308(var1, var2, var3, var4);
         }

      }
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1757437769"
   )
   void method3300() {
      this.floorUnderlayIds = null;
      this.floorOverlayIds = null;
      this.field1907 = null;
      this.field1895 = null;
      this.decorations = null;
      this.field1908 = false;
      this.field1909 = false;
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-929324026"
   )
   int method3297() {
      return this.regionX;
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(Lhp;I)V",
      garbageValue = "-1207518623"
   )
   void method3299(AbstractArchive var1) {
      if(!this.method3298()) {
         byte[] var2 = var1.method4020(this.groupId, this.fileId, (short)-13517);
         if(var2 != null) {
            this.vmethod3304(new Buffer(var2));
            this.field1908 = true;
            this.field1909 = true;
         }

      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(IIIZIZI)V",
      garbageValue = "-1516072397"
   )
   static void method3327(int var0, int var1, int var2, boolean var3, int var4, boolean var5) {
      if(var0 < var1) {
         int var6 = (var0 + var1) / 2;
         int var7 = var0;
         World var8 = World.World_worlds[var6];
         World.World_worlds[var6] = World.World_worlds[var1];
         World.World_worlds[var1] = var8;

         for(int var9 = var0; var9 < var1; ++var9) {
            if(ArchiveLoader.method1302(World.World_worlds[var9], var8, var2, var3, var4, var5) <= 0) {
               World var10 = World.World_worlds[var9];
               World.World_worlds[var9] = World.World_worlds[var7];
               World.World_worlds[var7++] = var10;
            }
         }

         World.World_worlds[var1] = World.World_worlds[var7];
         World.World_worlds[var7] = var8;
         method3327(var0, var7 - 1, var2, var3, var4, var5);
         method3327(var7 + 1, var1, var2, var3, var4, var5);
      }

   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IB)Liq;",
      garbageValue = "-117"
   )
   public static VarpDefinition method3328(int var0) {
      VarpDefinition var1 = (VarpDefinition)VarpDefinition.VarpDefinition_cached.method3032((long)var0);
      if(var1 != null) {
         return var1;
      } else {
         byte[] var2 = VarpDefinition.VarpDefinition_archive.method4020(16, var0, (short)16699);
         var1 = new VarpDefinition();
         if(var2 != null) {
            var1.method4487(new Buffer(var2));
         }

         VarpDefinition.VarpDefinition_cached.method3034(var1, (long)var0);
         return var1;
      }
   }

   @ObfuscatedName("ar")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "-1561238800"
   )
   static int method3329(int var0, Script var1, boolean var2) {
      int var3;
      if(var0 == 3903) {
         var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.grandExchangeOffers[var3].method4198();
         return 1;
      } else if(var0 == 3904) {
         var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.grandExchangeOffers[var3].id;
         return 1;
      } else if(var0 == 3905) {
         var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.grandExchangeOffers[var3].unitPrice;
         return 1;
      } else if(var0 == 3906) {
         var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.grandExchangeOffers[var3].totalQuantity;
         return 1;
      } else if(var0 == 3907) {
         var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.grandExchangeOffers[var3].currentQuantity;
         return 1;
      } else if(var0 == 3908) {
         var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Client.grandExchangeOffers[var3].currentPrice;
         return 1;
      } else {
         int var12;
         if(var0 == 3910) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            var12 = Client.grandExchangeOffers[var3].method4197();
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var12 == 0?1:0;
            return 1;
         } else if(var0 == 3911) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            var12 = Client.grandExchangeOffers[var3].method4197();
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var12 == 2?1:0;
            return 1;
         } else if(var0 == 3912) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            var12 = Client.grandExchangeOffers[var3].method4197();
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var12 == 5?1:0;
            return 1;
         } else if(var0 == 3913) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            var12 = Client.grandExchangeOffers[var3].method4197();
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var12 == 1?1:0;
            return 1;
         } else {
            boolean var13;
            if(var0 == 3914) {
               var13 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
               if(TileItem.grandExchangeEvents != null) {
                  TileItem.grandExchangeEvents.method100(GrandExchangeEvents.GrandExchangeEvents_nameComparator, var13);
               }

               return 1;
            } else if(var0 == 3915) {
               var13 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
               if(TileItem.grandExchangeEvents != null) {
                  TileItem.grandExchangeEvents.method100(GrandExchangeEvents.GrandExchangeEvents_priceComparator, var13);
               }

               return 1;
            } else if(var0 == 3916) {
               Interpreter.Interpreter_intStackSize -= 2;
               var13 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize] == 1;
               boolean var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1] == 1;
               if(TileItem.grandExchangeEvents != null) {
                  Client.GrandExchangeEvents_worldComparator.filterWorlds = var4;
                  TileItem.grandExchangeEvents.method100(Client.GrandExchangeEvents_worldComparator, var13);
               }

               return 1;
            } else if(var0 == 3917) {
               var13 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
               if(TileItem.grandExchangeEvents != null) {
                  TileItem.grandExchangeEvents.method100(GrandExchangeEvents.GrandExchangeEvents_ageComparator, var13);
               }

               return 1;
            } else if(var0 == 3918) {
               var13 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
               if(TileItem.grandExchangeEvents != null) {
                  TileItem.grandExchangeEvents.method100(GrandExchangeEvents.GrandExchangeEvents_quantityComparator, var13);
               }

               return 1;
            } else if(var0 == 3919) {
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = TileItem.grandExchangeEvents == null?0:TileItem.grandExchangeEvents.events.size();
               return 1;
            } else {
               GrandExchangeEvent var11;
               if(var0 == 3920) {
                  var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var11 = (GrandExchangeEvent)TileItem.grandExchangeEvents.events.get(var3);
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var11.world;
                  return 1;
               } else if(var0 == 3921) {
                  var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var11 = (GrandExchangeEvent)TileItem.grandExchangeEvents.events.get(var3);
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var11.method6490();
                  return 1;
               } else if(var0 == 3922) {
                  var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var11 = (GrandExchangeEvent)TileItem.grandExchangeEvents.events.get(var3);
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var11.method6489();
                  return 1;
               } else if(var0 == 3923) {
                  var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var11 = (GrandExchangeEvent)TileItem.grandExchangeEvents.events.get(var3);
                  long var5 = class33.method680() - class86.field1141 - var11.age;
                  int var7 = (int)(var5 / 3600000L);
                  int var8 = (int)((var5 - (long)(var7 * 3600000)) / 60000L);
                  int var9 = (int)((var5 - (long)(var7 * 3600000) - (long)(var8 * 60000)) / 1000L);
                  String var10 = var7 + ":" + var8 / 10 + var8 % 10 + ":" + var9 / 10 + var9 % 10;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var10;
                  return 1;
               } else if(var0 == 3924) {
                  var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var11 = (GrandExchangeEvent)TileItem.grandExchangeEvents.events.get(var3);
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var11.grandExchangeOffer.totalQuantity;
                  return 1;
               } else if(var0 == 3925) {
                  var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var11 = (GrandExchangeEvent)TileItem.grandExchangeEvents.events.get(var3);
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var11.grandExchangeOffer.unitPrice;
                  return 1;
               } else if(var0 == 3926) {
                  var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
                  var11 = (GrandExchangeEvent)TileItem.grandExchangeEvents.events.get(var3);
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var11.grandExchangeOffer.id;
                  return 1;
               } else {
                  return 2;
               }
            }
         }
      }
   }

   @ObfuscatedName("ii")
   @ObfuscatedSignature(
      signature = "(IIIIIIIB)V",
      garbageValue = "35"
   )
   static final void method3325(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      if(WorldMapData_0.method171(var0)) {
         MusicPatchNode.method3844(UserComparator5.Widget_interfaceComponents[var0], -1, var1, var2, var3, var4, var5, var6);
      }
   }
}
