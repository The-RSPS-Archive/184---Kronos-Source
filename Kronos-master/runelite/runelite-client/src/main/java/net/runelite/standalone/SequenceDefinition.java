package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSFrames;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSSequenceDefinition;

@ObfuscatedName("ix")
public class SequenceDefinition extends DualNode implements RSSequenceDefinition {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   static AbstractArchive SequenceDefinition_animationsArchive;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable SequenceDefinition_cachedFrames;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable SequenceDefinition_cached;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   static AbstractArchive SequenceDefinition_archive;
   @ObfuscatedName("aa")
   static java.awt.Font fontHelvetica13;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = 1781199649
   )
   public int field3432;
   @ObfuscatedName("p")
   public int[] frameIds;
   @ObfuscatedName("q")
   int[] chatFrameIds;
   @ObfuscatedName("t")
   @ObfuscatedGetter(
      intValue = 1818541161
   )
   public int field3436;
   @ObfuscatedName("w")
   @ObfuscatedGetter(
      intValue = 1078760223
   )
   public int field3424;
   @ObfuscatedName("x")
   @ObfuscatedGetter(
      intValue = -1562384253
   )
   public int field3438;
   @ObfuscatedName("y")
   public int[] soundEffects;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = 75980763
   )
   public int shield;
   @ObfuscatedName("b")
   public boolean field3425;
   @ObfuscatedName("c")
   int[] field3430;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = 1392893675
   )
   public int weapon;
   @ObfuscatedName("g")
   @ObfuscatedGetter(
      intValue = 2021679627
   )
   public int field3431;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 646355869
   )
   public int frameCount;
   @ObfuscatedName("m")
   public int[] frameLengths;

   static {
      SequenceDefinition_cached = new EvictingDualNodeHashTable(64);
      SequenceDefinition_cachedFrames = new EvictingDualNodeHashTable(100);
   }

   SequenceDefinition() {
      this.frameCount = -1;
      this.field3425 = false;
      this.field3432 = 5;
      this.shield = -1;
      this.weapon = -1;
      this.field3424 = 99;
      this.field3436 = -1;
      this.field3431 = -1;
      this.field3438 = 2;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(Ldh;II)Ldh;"
   )
   public Model method4661(Model var1, int var2, int var3) {
      if(var2 >= 0) {
         return (Model)this.copy$transformActorModel(var1, var2, var3);
      } else {
         int var4 = var2 ^ Integer.MIN_VALUE;
         int var5 = var4 >> 16;
         var2 = var4 & 65535;
         int var6 = var2 + 1;
         if(var6 >= this.getFrameIDs().length) {
            var6 = -1;
         }

         int[] var7 = this.getFrameIDs();
         int var8 = var7[var2];
         RSFrames var9 = ViewportMouse.client.getFrames(var8 >> 16);
         int var10 = var8 & 65535;
         int var11 = -1;
         RSFrames var12 = null;
         if(var6 != -1) {
            int var13 = var7[var6];
            var12 = ViewportMouse.client.getFrames(var13 >> 16);
            var11 = var13 & 65535;
         }

         if(var9 == null) {
            return (Model)var1.toSharedModel(true);
         } else {
            RSModel var14 = var1.toSharedModel(!var9.getFrames()[var10].isShowing());
            var14.interpolateFrames(var9, var10, var12, var11, var5, this.getFrameLenths()[var2]);
            return (Model)var14;
         }
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(Ldh;IIB)Ldh;"
   )
   Model method4662(Model var1, int var2, int var3, byte var4) {
      if(var2 >= 0) {
         return (Model)this.copy$transformObjectModel(var1, var2, var3, var4);
      } else {
         int var5 = var2 ^ Integer.MIN_VALUE;
         int var6 = var5 >> 16;
         var2 = var5 & 65535;
         int var7 = var2 + 1;
         if(var7 >= this.getFrameIDs().length) {
            var7 = -1;
         }

         int[] var8 = this.getFrameIDs();
         int var9 = var8[var2];
         RSFrames var10 = ViewportMouse.client.getFrames(var9 >> 16);
         int var11 = var9 & 65535;
         int var12 = -1;
         RSFrames var13 = null;
         if(var7 != -1) {
            int var14 = var8[var7];
            var13 = ViewportMouse.client.getFrames(var14 >> 16);
            var12 = var14 & 65535;
         }

         if(var10 == null) {
            return (Model)var1.toSharedModel(true);
         } else {
            RSModel var15 = var1.toSharedModel(!var10.getFrames()[var11].isShowing());
            var3 &= 3;
            if(var3 == 1) {
               var15.rotateY270Ccw();
            } else if(var3 == 2) {
               var15.rotateY180Ccw();
            } else if(var3 == 3) {
               var15.rotateY90Ccw();
            }

            var15.interpolateFrames(var10, var11, var13, var12, var6, this.getFrameLenths()[var2]);
            if(var3 == 1) {
               var15.rotateY90Ccw();
            } else if(var3 == 2) {
               var15.rotateY180Ccw();
            } else if(var3 == 3) {
               var15.rotateY270Ccw();
            }

            return (Model)var15;
         }
      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(S)V",
      garbageValue = "30171"
   )
   void method4687() {
      if(this.field3436 == -1) {
         if(this.field3430 != null) {
            this.field3436 = 2;
         } else {
            this.field3436 = 0;
         }
      }

      if(this.field3431 == -1) {
         if(this.field3430 != null) {
            this.field3431 = 2;
         } else {
            this.field3431 = 0;
         }
      }

   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "-4421595"
   )
   void method4674(Buffer var1, int var2) {
      int var3;
      int var4;
      if(var2 == 1) {
         var3 = var1.readUnsignedShort();
         this.frameLengths = new int[var3];

         for(var4 = 0; var4 < var3; ++var4) {
            this.frameLengths[var4] = var1.readUnsignedShort();
         }

         this.frameIds = new int[var3];

         for(var4 = 0; var4 < var3; ++var4) {
            this.frameIds[var4] = var1.readUnsignedShort();
         }

         for(var4 = 0; var4 < var3; ++var4) {
            this.frameIds[var4] += var1.readUnsignedShort() << 16;
         }
      } else if(var2 == 2) {
         this.frameCount = var1.readUnsignedShort();
      } else if(var2 == 3) {
         var3 = var1.readUnsignedByte();
         this.field3430 = new int[var3 + 1];

         for(var4 = 0; var4 < var3; ++var4) {
            this.field3430[var4] = var1.readUnsignedByte();
         }

         this.field3430[var3] = 9999999;
      } else if(var2 == 4) {
         this.field3425 = true;
      } else if(var2 == 5) {
         this.field3432 = var1.readUnsignedByte();
      } else if(var2 == 6) {
         this.shield = var1.readUnsignedShort();
      } else if(var2 == 7) {
         this.weapon = var1.readUnsignedShort();
      } else if(var2 == 8) {
         this.field3424 = var1.readUnsignedByte();
      } else if(var2 == 9) {
         this.field3436 = var1.readUnsignedByte();
      } else if(var2 == 10) {
         this.field3431 = var1.readUnsignedByte();
      } else if(var2 == 11) {
         this.field3438 = var1.readUnsignedByte();
      } else if(var2 == 12) {
         var3 = var1.readUnsignedByte();
         this.chatFrameIds = new int[var3];

         for(var4 = 0; var4 < var3; ++var4) {
            this.chatFrameIds[var4] = var1.readUnsignedShort();
         }

         for(var4 = 0; var4 < var3; ++var4) {
            this.chatFrameIds[var4] += var1.readUnsignedShort() << 16;
         }
      } else if(var2 == 13) {
         var3 = var1.readUnsignedByte();
         this.soundEffects = new int[var3];

         for(var4 = 0; var4 < var3; ++var4) {
            this.soundEffects[var4] = var1.method5500();
         }
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;B)V",
      garbageValue = "-45"
   )
   void method4658(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            return;
         }

         this.method4674(var1, var2);
      }
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(Ldh;ILix;IB)Ldh;"
   )
   public Model method4660(Model var1, int var2, SequenceDefinition var3, int var4, byte var5) {
      int var6;
      if(var2 < 0) {
         var6 = var2 ^ Integer.MIN_VALUE;
         var2 = var6 & 65535;
      }

      if(var4 < 0) {
         var6 = var4 ^ Integer.MIN_VALUE;
         var4 = var6 & 65535;
      }

      return (Model)this.copy$applyTransformations(var1, var2, var3, var4, var5);
   }

   @ObfuscatedSignature(
      signature = "(Ldh;IIB)Ldh;"
   )
   public Model copy$transformObjectModel(Model var1, int var2, int var3, byte var4) {
      var2 = this.frameIds[var2];
      Frames var5 = class210.method3930(var2 >> 16);
      var2 &= 65535;
      if(var5 == null) {
         return var1.method2355(true);
      } else {
         Model var6 = var1.method2355(!var5.method3064(var2));
         var3 &= 3;
         if(var3 == 1) {
            var6.method2368();
         } else if(var3 == 2) {
            var6.method2367();
         } else if(var3 == 3) {
            var6.method2366();
         }

         var6.method2363(var5, var2);
         if(var3 == 1) {
            var6.method2366();
         } else if(var3 == 2) {
            var6.method2367();
         } else if(var3 == 3) {
            var6.method2368();
         }

         return var6;
      }
   }

   @ObfuscatedSignature(
      signature = "(Ldh;II)Ldh;"
   )
   public Model copy$transformSpotAnimationModel(Model var1, int var2, int var3) {
      var2 = this.frameIds[var2];
      Frames var4 = class210.method3930(var2 >> 16);
      var2 &= 65535;
      if(var4 == null) {
         return var1.method2362(true);
      } else {
         Model var5 = var1.method2362(!var4.method3064(var2));
         var5.method2363(var4, var2);
         return var5;
      }
   }

   @ObfuscatedSignature(
      signature = "(Ldh;IB)Ldh;"
   )
   public Model copy$transformWidgetModel(Model var1, int var2, byte var3) {
      int var4 = this.frameIds[var2];
      Frames var5 = class210.method3930(var4 >> 16);
      var4 &= 65535;
      if(var5 == null) {
         return var1.method2355(true);
      } else {
         Frames var6 = null;
         int var7 = 0;
         if(this.chatFrameIds != null && var2 < this.chatFrameIds.length) {
            var7 = this.chatFrameIds[var2];
            var6 = class210.method3930(var7 >> 16);
            var7 &= 65535;
         }

         Model var8;
         if(var6 != null && var7 != 65535) {
            var8 = var1.method2355(!var5.method3064(var4) & !var6.method3064(var7));
            var8.method2363(var5, var4);
            var8.method2363(var6, var7);
            return var8;
         } else {
            var8 = var1.method2355(!var5.method3064(var4));
            var8.method2363(var5, var4);
            return var8;
         }
      }
   }

   @ObfuscatedSignature(
      signature = "(Ldh;II)Ldh;"
   )
   public Model copy$transformActorModel(Model var1, int var2, int var3) {
      var2 = this.frameIds[var2];
      Frames var4 = class210.method3930(var2 >> 16);
      var2 &= 65535;
      if(var4 == null) {
         return var1.method2355(true);
      } else {
         Model var5 = var1.method2355(!var4.method3064(var2));
         var5.method2363(var4, var2);
         return var5;
      }
   }

   @ObfuscatedSignature(
      signature = "(Ldh;ILix;IB)Ldh;"
   )
   public Model copy$applyTransformations(Model var1, int var2, SequenceDefinition var3, int var4, byte var5) {
      var2 = this.frameIds[var2];
      Frames var6 = class210.method3930(var2 >> 16);
      var2 &= 65535;
      if(var6 == null) {
         return var3.method4661(var1, var4, 1973323654);
      } else {
         var4 = var3.frameIds[var4];
         Frames var7 = class210.method3930(var4 >> 16);
         var4 &= 65535;
         Model var8;
         if(var7 == null) {
            var8 = var1.method2355(!var6.method3064(var2));
            var8.method2363(var6, var2);
            return var8;
         } else {
            var8 = var1.method2355(!var6.method3064(var2) & !var7.method3064(var4));
            var8.method2419(var6, var2, var7, var4, this.field3430);
            return var8;
         }
      }
   }

   public int[] getFrameIDs() {
      return this.frameIds;
   }

   public int[] getChatFrameIds() {
      return this.chatFrameIds;
   }

   public int[] getFrameLenths() {
      return this.frameLengths;
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(Ldh;IB)Ldh;"
   )
   public Model method4669(Model var1, int var2, byte var3) {
      if(var2 >= 0) {
         return (Model)this.copy$transformWidgetModel(var1, var2, var3);
      } else {
         int var4 = var2 ^ Integer.MIN_VALUE;
         int var5 = var4 >> 16;
         var2 = var4 & 65535;
         int var6 = var2 + 1;
         if(var6 >= this.getFrameIDs().length) {
            var6 = -1;
         }

         int[] var7 = this.getFrameIDs();
         int var8 = var7[var2];
         RSFrames var9 = ViewportMouse.client.getFrames(var8 >> 16);
         int var10 = var8 & 65535;
         int var11 = -1;
         RSFrames var12 = null;
         if(var6 != -1) {
            int var13 = var7[var6];
            var12 = ViewportMouse.client.getFrames(var13 >> 16);
            var11 = var13 & 65535;
         }

         if(var9 == null) {
            return (Model)var1.toSharedModel(true);
         } else {
            RSFrames var18 = null;
            int var14 = 0;
            if(this.getChatFrameIds() != null && var2 < this.getChatFrameIds().length) {
               int var15 = this.getChatFrameIds()[var2];
               var18 = ViewportMouse.client.getFrames(var15 >> 16);
               var14 = var15 & 65535;
            }

            if(var18 != null && var14 != 65535) {
               RSFrames var21 = null;
               int var16 = -1;
               if(var6 != -1 && var6 < this.getChatFrameIds().length) {
                  int var17 = this.getChatFrameIds()[var6];
                  var21 = ViewportMouse.client.getFrames(var17 >> 16);
                  var16 = var17 & 65535;
               }

               if(var16 == 65535) {
                  var21 = null;
               }

               RSModel var20 = var1.toSharedModel(!var9.getFrames()[var10].isShowing() & !var18.getFrames()[var14].isShowing());
               var20.interpolateFrames(var9, var10, var12, var11, var5, this.getFrameLenths()[var2]);
               var20.interpolateFrames(var18, var14, var21, var16, var5, this.getFrameLenths()[var2]);
               return (Model)var20;
            } else {
               RSModel var19 = var1.toSharedModel(!var9.getFrames()[var10].isShowing());
               var19.interpolateFrames(var9, var10, var12, var11, var5, this.getFrameLenths()[var2]);
               return (Model)var19;
            }
         }
      }
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(Ldh;II)Ldh;"
   )
   Model method4663(Model var1, int var2, int var3) {
      if(var2 >= 0) {
         return (Model)this.copy$transformSpotAnimationModel(var1, var2, var3);
      } else {
         int var4 = var2 ^ Integer.MIN_VALUE;
         int var5 = var4 >> 16;
         var2 = var4 & 65535;
         int var6 = var2 + 1;
         if(var6 >= this.getFrameIDs().length) {
            var6 = -1;
         }

         int[] var7 = this.getFrameIDs();
         int var8 = var7[var2];
         RSFrames var9 = ViewportMouse.client.getFrames(var8 >> 16);
         int var10 = var8 & 65535;
         int var11 = -1;
         RSFrames var12 = null;
         if(var6 != -1) {
            int var13 = var7[var6];
            var12 = ViewportMouse.client.getFrames(var13 >> 16);
            var11 = var13 & 65535;
         }

         if(var9 == null) {
            return (Model)var1.toSharedSpotAnimModel(true);
         } else {
            RSModel var14 = var1.toSharedSpotAnimModel(!var9.getFrames()[var10].isShowing());
            var14.interpolateFrames(var9, var10, var12, var11, var5, this.getFrameLenths()[var2]);
            return (Model)var14;
         }
      }
   }

   @ObfuscatedName("et")
   static final void method4691(int var0, int var1) {
      Client.copy$forceDisconnect(var0, var1);
      if(Client.hideDisconnect && var0 == 1) {
         ViewportMouse.client.promptCredentials(true);
      }

   }
}
