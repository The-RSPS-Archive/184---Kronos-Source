package net.runelite.standalone;

import java.util.concurrent.Callable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bj")
public class SecureRandomCallable implements Callable {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive musicSamplesArchive;

   public Object call() {
      return AttackOption.method2107();
   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(Ldh;IIII)Z",
      garbageValue = "810507745"
   )
   static final boolean method1133(Model var0, int var1, int var2, int var3) {
      boolean var4 = ViewportMouse.ViewportMouse_isInViewport;
      if(!var4) {
         return false;
      } else {
         int var5;
         int var6;
         int var7;
         int var8;
         int var11;
         int var12;
         int var13;
         int var16;
         int var17;
         if(!ViewportMouse.ViewportMouse_false0) {
            var5 = Scene.Scene_cameraPitchSine;
            var6 = Scene.Scene_cameraPitchCosine;
            var7 = Scene.Scene_cameraYawSine;
            var8 = Scene.Scene_cameraYawCosine;
            byte var9 = 50;
            short var10 = 3500;
            var11 = (ViewportMouse.ViewportMouse_x - Rasterizer3D.Rasterizer3D_clipMidX) * var9 / Rasterizer3D.Rasterizer3D_zoom;
            var12 = (ViewportMouse.ViewportMouse_y - Rasterizer3D.Rasterizer3D_clipMidY) * var9 / Rasterizer3D.Rasterizer3D_zoom;
            var13 = (ViewportMouse.ViewportMouse_x - Rasterizer3D.Rasterizer3D_clipMidX) * var10 / Rasterizer3D.Rasterizer3D_zoom;
            int var14 = (ViewportMouse.ViewportMouse_y - Rasterizer3D.Rasterizer3D_clipMidY) * var10 / Rasterizer3D.Rasterizer3D_zoom;
            int var15 = Rasterizer3D.method2966(var12, var9, var6, var5);
            var16 = Rasterizer3D.method2958(var12, var9, var6, var5);
            var12 = var15;
            var15 = Rasterizer3D.method2966(var14, var10, var6, var5);
            var17 = Rasterizer3D.method2958(var14, var10, var6, var5);
            var14 = var15;
            var15 = Rasterizer3D.method2964(var11, var16, var8, var7);
            var16 = Rasterizer3D.method2965(var11, var16, var8, var7);
            var11 = var15;
            var15 = Rasterizer3D.method2964(var13, var17, var8, var7);
            var17 = Rasterizer3D.method2965(var13, var17, var8, var7);
            ViewportMouse.field1309 = (var15 + var11) / 2;
            ViewportMouse.field1310 = (var12 + var14) / 2;
            ViewportMouse.field1316 = (var16 + var17) / 2;
            class94.field1242 = (var15 - var11) / 2;
            ViewportMouse.field1320 = (var14 - var12) / 2;
            ViewportMouse.field1313 = (var17 - var16) / 2;
            class227.field2769 = Math.abs(class94.field1242);
            UserComparator3.field1948 = Math.abs(ViewportMouse.field1320);
            ParamDefinition.field3155 = Math.abs(ViewportMouse.field1313);
         }

         var5 = var0.xMid + var1;
         var6 = var2 + var0.yMid;
         var7 = var3 + var0.zMid;
         var8 = var0.xMidOffset;
         var16 = var0.yMidOffset;
         var17 = var0.zMidOffset;
         var11 = ViewportMouse.field1309 - var5;
         var12 = ViewportMouse.field1310 - var6;
         var13 = ViewportMouse.field1316 - var7;
         return Math.abs(var11) > var8 + class227.field2769?false:(Math.abs(var12) > var16 + UserComparator3.field1948?false:(Math.abs(var13) > var17 + ParamDefinition.field3155?false:(Math.abs(var13 * ViewportMouse.field1320 - var12 * ViewportMouse.field1313) > var16 * ParamDefinition.field3155 + var17 * UserComparator3.field1948?false:(Math.abs(var11 * ViewportMouse.field1313 - var13 * class94.field1242) > var17 * class227.field2769 + var8 * ParamDefinition.field3155?false:Math.abs(var12 * class94.field1242 - var11 * ViewportMouse.field1320) <= var16 * class227.field2769 + var8 * UserComparator3.field1948))));
      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(S)I",
      garbageValue = "19776"
   )
   public static int method1136() {
      return KeyHandler.KeyHandler_idleCycles;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(IIIB)J",
      garbageValue = "0"
   )
   static long method1135(int var0, int var1, int var2) {
      return (long)(var2 << 16 | var0 << 8 | var1);
   }
}
