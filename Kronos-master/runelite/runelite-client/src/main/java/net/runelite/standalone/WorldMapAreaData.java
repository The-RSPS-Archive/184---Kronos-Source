package net.runelite.standalone;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("an")
public class WorldMapAreaData extends WorldMapArea {
   @ObfuscatedName("o")
   HashSet worldMapData0Set;
   @ObfuscatedName("a")
   HashSet worldMapData1Set;
   @ObfuscatedName("e")
   List iconList;

   @ObfuscatedName("cg")
   @ObfuscatedSignature(
      signature = "(Lkl;ZI)V",
      garbageValue = "-317910906"
   )
   void method665(Buffer var1, boolean var2) {
      this.iconList = new LinkedList();
      int var3 = var1.readUnsignedShort();

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var1.method5507();
         Coord var6 = new Coord(var1.readInt());
         boolean var7 = var1.readUnsignedByte() == 1;
         if(var2 || !var7) {
            this.iconList.add(new WorldMapIcon_0((Coord)null, var6, var5, (WorldMapLabel)null));
         }
      }

   }

   @ObfuscatedName("cs")
   @ObfuscatedSignature(
      signature = "(Lkl;Lkl;IZI)V",
      garbageValue = "608966722"
   )
   void method666(Buffer var1, Buffer var2, int var3, boolean var4) {
      this.decode(var1, var3);
      int var5 = var2.readUnsignedShort();
      this.worldMapData0Set = new HashSet(var5);

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         WorldMapData_0 var7 = new WorldMapData_0();

         try {
            var7.method157(var2);
         } catch (IllegalStateException var12) {
            continue;
         }

         this.worldMapData0Set.add(var7);
      }

      var6 = var2.readUnsignedShort();
      this.worldMapData1Set = new HashSet(var6);

      for(int var10 = 0; var10 < var6; ++var10) {
         WorldMapData_1 var8 = new WorldMapData_1();

         try {
            var8.method508(var2);
         } catch (IllegalStateException var11) {
            continue;
         }

         this.worldMapData1Set.add(var8);
      }

      this.method665(var2, var4);
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(II)V",
      garbageValue = "-2123139883"
   )
   static void method670(int var0) {
      ItemContainer var1 = (ItemContainer)ItemContainer.itemContainers.method6346((long)var0);
      if(var1 != null) {
         var1.method3497();
      }
   }

   @ObfuscatedName("ab")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "-442086498"
   )
   static int method669(int var0, Script var1, boolean var2) {
      int var3;
      int var4;
      if(var0 == 4000) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var4 + var3;
         return 1;
      } else if(var0 == 4001) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3 - var4;
         return 1;
      } else if(var0 == 4002) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var4 * var3;
         return 1;
      } else if(var0 == 4003) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3 / var4;
         return 1;
      } else if(var0 == 4004) {
         var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = (int)(Math.random() * (double)var3);
         return 1;
      } else if(var0 == 4005) {
         var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = (int)(Math.random() * (double)(var3 + 1));
         return 1;
      } else if(var0 == 4006) {
         Interpreter.Interpreter_intStackSize -= 5;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         int var5 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 2];
         int var6 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 3];
         int var7 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 4];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3 + (var4 - var3) * (var7 - var5) / (var6 - var5);
         return 1;
      } else if(var0 == 4007) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3 + var4 * var3 / 100;
         return 1;
      } else if(var0 == 4008) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3 | 1 << var4;
         return 1;
      } else if(var0 == 4009) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3 & -1 - (1 << var4);
         return 1;
      } else if(var0 == 4010) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = (var3 & 1 << var4) != 0?1:0;
         return 1;
      } else if(var0 == 4011) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3 % var4;
         return 1;
      } else if(var0 == 4012) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         if(var3 == 0) {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
         } else {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = (int)Math.pow((double)var3, (double)var4);
         }

         return 1;
      } else if(var0 == 4013) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         if(var3 == 0) {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
            return 1;
         } else {
            switch(var4) {
            case 0:
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Integer.MAX_VALUE;
               break;
            case 1:
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3;
               break;
            case 2:
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = (int)Math.sqrt((double)var3);
               break;
            case 3:
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = (int)Math.cbrt((double)var3);
               break;
            case 4:
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = (int)Math.sqrt(Math.sqrt((double)var3));
               break;
            default:
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = (int)Math.pow((double)var3, 1.0D / (double)var4);
            }

            return 1;
         }
      } else if(var0 == 4014) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3 & var4;
         return 1;
      } else if(var0 == 4015) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var4 = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = var3 | var4;
         return 1;
      } else if(var0 == 4018) {
         Interpreter.Interpreter_intStackSize -= 3;
         long var9 = (long)Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         long var11 = (long)Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         long var13 = (long)Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 2];
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = (int)(var9 * var13 / var11);
         return 1;
      } else {
         return 2;
      }
   }

   @ObfuscatedName("ev")
   @ObfuscatedSignature(
      signature = "(Lie;Ljava/lang/String;I)V",
      garbageValue = "-1244242871"
   )
   static void method671(Archive var0, String var1) {
      ArchiveLoader var2 = new ArchiveLoader(var0, var1);
      Client.archiveLoaders.add(var2);
      Client.field1116 += var2.groupCount;
   }

   @ObfuscatedName("id")
   @ObfuscatedSignature(
      signature = "(Lho;IIIIIII)V",
      garbageValue = "-1006882337"
   )
   static final void method672(Widget var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      if(Client.field911) {
         Client.alternativeScrollbarWidth = 32;
      } else {
         Client.alternativeScrollbarWidth = 0;
      }

      Client.field911 = false;
      int var7;
      if(MouseHandler.MouseHandler_currentButton == 1 || !WorldMapIcon_1.mouseCam && MouseHandler.MouseHandler_currentButton == 4) {
         if(var5 >= var1 && var5 < var1 + 16 && var6 >= var2 && var6 < var2 + 16) {
            var0.scrollY -= 4;
            WorldMapSectionType.method116(var0);
         } else if(var5 >= var1 && var5 < var1 + 16 && var6 >= var3 + var2 - 16 && var6 < var3 + var2) {
            var0.scrollY += 4;
            WorldMapSectionType.method116(var0);
         } else if(var5 >= var1 - Client.alternativeScrollbarWidth && var5 < Client.alternativeScrollbarWidth + var1 + 16 && var6 >= var2 + 16 && var6 < var3 + var2 - 16) {
            var7 = var3 * (var3 - 32) / var4;
            if(var7 < 8) {
               var7 = 8;
            }

            int var8 = var6 - var2 - 16 - var7 / 2;
            int var9 = var3 - 32 - var7;
            var0.scrollY = var8 * (var4 - var3) / var9;
            WorldMapSectionType.method116(var0);
            Client.field911 = true;
         }
      }

      if(Client.mouseWheelRotation != 0) {
         var7 = var0.width;
         if(var5 >= var1 - var7 && var6 >= var2 && var5 < var1 + 16 && var6 <= var3 + var2) {
            var0.scrollY += Client.mouseWheelRotation * 45;
            WorldMapSectionType.method116(var0);
         }
      }

   }
}
