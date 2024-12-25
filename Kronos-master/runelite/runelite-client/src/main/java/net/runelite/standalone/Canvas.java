package net.runelite.standalone;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.FocusListener;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSCanvas;

@ObfuscatedName("ay")
public final class Canvas extends java.awt.Canvas implements RSCanvas {
   public static boolean shouldNotHaveFocus;
   @ObfuscatedName("z")
   Component component;

   Canvas(Component var1) {
      this.component = var1;
   }

   //TODO: Modified Added Method For Custom Interface Edits
   public static Widget get(int parentId, int childId) {
      return getWidget(parentId << 16 | childId);
   }

    public final void update(Graphics var1) {
      this.component.update(var1);
   }

   public final void paint(Graphics var1) {
      this.component.paint(var1);
   }

   public void removeFocusListener(FocusListener var1) {
      super.removeFocusListener(var1);
      shouldNotHaveFocus = !this.hasFocus();
   }

   public void requestFocus() {
      if(!shouldNotHaveFocus) {
         this.requestFocusInWindow();
      }

   }

   public void setSize(int var1, int var2) {
      if(ViewportMouse.client.isStretchedEnabled()) {
         super.setSize(ViewportMouse.client.getStretchedDimensions().width, ViewportMouse.client.getStretchedDimensions().height);
      } else {
         super.setSize(var1, var2);
      }

   }

   public void setLocation(int var1, int var2) {
      if(ViewportMouse.client.isStretchedEnabled()) {
         super.setLocation((this.getParent().getWidth() - ViewportMouse.client.getStretchedDimensions().width) / 2, 0);
      } else {
         super.setLocation(var1, var2);
      }

   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(II)Lho;",
      garbageValue = "313560965"
   )
   public static Widget getWidget(int hash) {
      int parent = hash >> 16;
      int child = hash & 0xffff;
      if(UserComparator5.Widget_interfaceComponents[parent] == null || UserComparator5.Widget_interfaceComponents[parent][child] == null) {
         boolean var3 = WorldMapData_0.method171(parent);
         if(!var3) {
            return null;
         }
      }

      return UserComparator5.Widget_interfaceComponents[parent][child];
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lhp;III)[Llf;",
      garbageValue = "-2024454786"
   )
   public static Sprite[] method781(AbstractArchive var0, int var1, int var2) {
      return !VertexNormal.method2468(var0, var1, var2)?null:AbstractWorldMapIcon.method720();
   }

   @ObfuscatedName("al")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "952621968"
   )
   static int method780(int var0, Script var1, boolean var2) {
      if(var0 == 5306) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = class256.method4656();
         return 1;
      } else {
         int var3;
         if(var0 == 5307) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            if(var3 == 1 || var3 == 2) {
               UserComparator8.method2878(var3);
            }

            return 1;
         } else if(var0 == 5308) {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = AbstractArchive.clientPreferences.windowMode;
            return 1;
         } else if(var0 != 5309) {
            if(var0 == 5310) {
               --Interpreter.Interpreter_intStackSize;
               return 1;
            } else {
               return 2;
            }
         } else {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            if(var3 == 1 || var3 == 2) {
               AbstractArchive.clientPreferences.windowMode = var3;
               Language.method3830();
            }

            return 1;
         }
      }
   }

   @ObfuscatedName("kn")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;ZI)V",
      garbageValue = "328041460"
   )
   static void method778(String var0, boolean var1) {
      var0 = var0.toLowerCase();
      short[] var2 = new short[16];
      int var3 = 0;

      for(int var4 = 0; var4 < JagexCache.ItemDefinition_fileCount; ++var4) {
         ItemDefinition var5 = Occluder.getItemDefinition(var4);
         if((!var1 || var5.isTradable) && var5.noteTemplate == -1 && var5.name.toLowerCase().indexOf(var0) != -1) {
            if(var3 >= 250) {
               DevicePcmPlayerProvider.foundItemIdCount = -1;
               WorldMapIcon_0.foundItemIds = null;
               return;
            }

            if(var3 >= var2.length) {
               short[] var6 = new short[var2.length * 2];

               for(int var7 = 0; var7 < var3; ++var7) {
                  var6[var7] = var2[var7];
               }

               var2 = var6;
            }

            var2[var3++] = (short)var4;
         }
      }

      WorldMapIcon_0.foundItemIds = var2;
      ReflectionCheck.foundItemIndex = 0;
      DevicePcmPlayerProvider.foundItemIdCount = var3;
      String[] var8 = new String[DevicePcmPlayerProvider.foundItemIdCount];

      for(int var9 = 0; var9 < DevicePcmPlayerProvider.foundItemIdCount; ++var9) {
         var8[var9] = Occluder.getItemDefinition(var2[var9]).name;
      }

      short[] var10 = WorldMapIcon_0.foundItemIds;
      GrandExchangeOfferNameComparator.method799(var8, var10, 0, var8.length - 1);
   }
}
