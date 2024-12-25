package net.runelite.standalone;

import java.awt.Image;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("co")
public class class86 {
   @ObfuscatedName("sp")
   @ObfuscatedGetter(
      longValue = -8179620885535776663L
   )
   static long field1141;
   @ObfuscatedName("y")
   public static byte[][][] ByteArrayPool_arrays;
   @ObfuscatedName("as")
   static Image field1133;

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(ILcu;ZB)I",
      garbageValue = "116"
   )
   static int method2112(int var0, Script var1, boolean var2) {
      int var4 = -1;
      Widget var3;
      if(var0 >= 2000) {
         var0 -= 1000;
         var4 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         var3 = Canvas.getWidget(var4);
      } else {
         var3 = var2?GrandExchangeOfferAgeComparator.field26:KitDefinition.field3452;
      }

      if(var0 == 1100) {
         Interpreter.Interpreter_intStackSize -= 2;
         var3.scrollX = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         if(var3.scrollX > var3.scrollWidth - var3.width) {
            var3.scrollX = var3.scrollWidth - var3.width;
         }

         if(var3.scrollX < 0) {
            var3.scrollX = 0;
         }

         var3.scrollY = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         if(var3.scrollY > var3.scrollHeight - var3.height) {
            var3.scrollY = var3.scrollHeight - var3.height;
         }

         if(var3.scrollY < 0) {
            var3.scrollY = 0;
         }

         WorldMapSectionType.method116(var3);
         return 1;
      } else if(var0 == 1101) {
         var3.color = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         WorldMapSectionType.method116(var3);
         return 1;
      } else if(var0 == 1102) {
         var3.fill = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
         WorldMapSectionType.method116(var3);
         return 1;
      } else if(var0 == 1103) {
         var3.transparencyTop = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         WorldMapSectionType.method116(var3);
         return 1;
      } else if(var0 == 1104) {
         var3.lineWid = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         WorldMapSectionType.method116(var3);
         return 1;
      } else if(var0 == 1105) {
         var3.spriteId2 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         WorldMapSectionType.method116(var3);
         return 1;
      } else if(var0 == 1106) {
         var3.spriteAngle = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         WorldMapSectionType.method116(var3);
         return 1;
      } else if(var0 == 1107) {
         var3.spriteTiling = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
         WorldMapSectionType.method116(var3);
         return 1;
      } else if(var0 == 1108) {
         var3.modelType = 1;
         var3.modelId = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         WorldMapSectionType.method116(var3);
         return 1;
      } else if(var0 == 1109) {
         Interpreter.Interpreter_intStackSize -= 6;
         var3.modelOffsetX = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
         var3.modelOffsetY = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
         var3.modelAngleX = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 2];
         var3.modelAngleY = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 3];
         var3.modelAngleZ = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 4];
         var3.modelZoom = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 5];
         WorldMapSectionType.method116(var3);
         return 1;
      } else {
         int var8;
         if(var0 == 1110) {
            var8 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            if(var8 != var3.sequenceId) {
               var3.sequenceId = var8;
               var3.modelFrame = 0;
               var3.modelFrameCycle = 0;
               WorldMapSectionType.method116(var3);
            }

            return 1;
         } else if(var0 == 1111) {
            var3.modelOrthog = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
            WorldMapSectionType.method116(var3);
            return 1;
         } else if(var0 == 1112) {
            String var7 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
            if(!var7.equals(var3.text)) {
               var3.text = var7;
               WorldMapSectionType.method116(var3);
            }

            return 1;
         } else if(var0 == 1113) {
            var3.fontId = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            WorldMapSectionType.method116(var3);
            return 1;
         } else if(var0 == 1114) {
            Interpreter.Interpreter_intStackSize -= 3;
            var3.textXAlignment = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
            var3.textYAlignment = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
            var3.textLineHeight = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 2];
            WorldMapSectionType.method116(var3);
            return 1;
         } else if(var0 == 1115) {
            var3.textShadowed = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
            WorldMapSectionType.method116(var3);
            return 1;
         } else if(var0 == 1116) {
            var3.outline = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            WorldMapSectionType.method116(var3);
            return 1;
         } else if(var0 == 1117) {
            var3.spriteShadow = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            WorldMapSectionType.method116(var3);
            return 1;
         } else if(var0 == 1118) {
            var3.spriteFlipV = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
            WorldMapSectionType.method116(var3);
            return 1;
         } else if(var0 == 1119) {
            var3.spriteFlipH = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
            WorldMapSectionType.method116(var3);
            return 1;
         } else if(var0 == 1120) {
            Interpreter.Interpreter_intStackSize -= 2;
            var3.scrollWidth = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize];
            var3.scrollHeight = Interpreter.Interpreter_intStack[Interpreter.Interpreter_intStackSize + 1];
            //TODO: Added Method Call
            CustomShopInterface.open(var3);
            WorldMapSectionType.method116(var3);
            if(var4 != -1 && var3.type == 0) {
               GameShell.method1005(UserComparator5.Widget_interfaceComponents[var4 >> 16], var3, false);
            }

            return 1;
         } else if(var0 == 1121) {
            Clock.method3518(var3.id, var3.childIndex);
            Client.meslayerContinueWidget = var3;
            WorldMapSectionType.method116(var3);
            return 1;
         } else if(var0 == 1122) {
            var3.spriteId = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            WorldMapSectionType.method116(var3);
            return 1;
         } else if(var0 == 1123) {
            var3.color2 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            WorldMapSectionType.method116(var3);
            return 1;
         } else if(var0 == 1124) {
            var3.transparencyBot = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            WorldMapSectionType.method116(var3);
            return 1;
         } else if(var0 == 1125) {
            var8 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            FillMode var6 = (FillMode)NetSocket.getEnumeratedTypeIndex(class94.method2223(), var8);
            if(var6 != null) {
               var3.fillMode = var6;
               WorldMapSectionType.method116(var3);
            }

            return 1;
         } else {
            boolean var5;
            if(var0 == 1126) {
               var5 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
               var3.field2612 = var5;
               return 1;
            } else if(var0 == 1127) {
               var5 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
               var3.modelTransparency = var5;
               return 1;
            } else {
               return 2;
            }
         }
      }
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(II)I",
      garbageValue = "550144839"
   )
   public static int method2113(int var0) {
      return Client.method2043(ViewportMouse.ViewportMouse_entityTags[var0]);
   }

   @ObfuscatedName("m")
   public static int method2114(long var0) {
      return (int)(var0 >>> 17 & 4294967295L);
   }
}
