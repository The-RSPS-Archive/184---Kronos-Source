package net.runelite.standalone;

import java.io.EOFException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.runelite.api.events.VarClientIntChanged;
import net.runelite.api.events.VarClientStrChanged;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSVarcs;

@ObfuscatedName("ct")
public class Varcs implements RSVarcs {
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = -1292567711
   )
   static int graphicsTickTimeIdx;
   @ObfuscatedName("ho")
   @ObfuscatedGetter(
      intValue = -2002882325
   )
   static int cameraY;
   @ObfuscatedName("p")
   boolean unwrittenChanges;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      longValue = 457115256589346479L
   )
   long field1216;
   @ObfuscatedName("r")
   String[] strings;
   @ObfuscatedName("u")
   Map map;
   @ObfuscatedName("v")
   boolean[] intsPersistence;

   Varcs() {
      this.unwrittenChanges = false;
      int var1 = FaceNormal.archive2.fileCount(19);
      this.map = new HashMap();
      this.intsPersistence = new boolean[var1];

      int var2;
      for(var2 = 0; var2 < var1; ++var2) {
         VarcInt var4 = (VarcInt)VarcInt.VarcInt_cached.method3032((long)var2);
         VarcInt var3;
         if(var4 != null) {
            var3 = var4;
         } else {
            byte[] var5 = VarcInt.VarcInt_archive.method4020(19, var2, (short)19070);
            var4 = new VarcInt();
            if(var5 != null) {
               var4.method4519(new Buffer(var5));
            }

            VarcInt.VarcInt_cached.method3034(var4, (long)var2);
            var3 = var4;
         }

         this.intsPersistence[var2] = var3.persist;
      }

      var2 = 0;
      if(FaceNormal.archive2.method4274(15)) {
         var2 = FaceNormal.archive2.fileCount(15);
      }

      this.strings = new String[var2];
      this.method2171();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IB)I",
      garbageValue = "104"
   )
   int method2170(int var1) {
      Object var2 = this.map.get(Integer.valueOf(var1));
      return var2 instanceof Integer?((Integer)var2).intValue():-1;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(IB)Ljava/lang/String;",
      garbageValue = "1"
   )
   String method2161(int var1) {
      return this.strings[var1];
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "29"
   )
   void method2169() {
      int var1;
      for(var1 = 0; var1 < this.intsPersistence.length; ++var1) {
         if(!this.intsPersistence[var1]) {
            this.map.remove(Integer.valueOf(var1));
         }
      }

      for(var1 = 0; var1 < this.strings.length; ++var1) {
         this.strings[var1] = null;
      }

   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(ILjava/lang/String;I)V",
      garbageValue = "2089409644"
   )
   void method2166(int var1, String var2) {
      this.strings[var1] = var2;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(IB)Ljava/lang/String;",
      garbageValue = "-74"
   )
   String method2165(int var1) {
      Object var2 = this.map.get(Integer.valueOf(var1));
      return var2 instanceof String?(String)var2:"";
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(ILjava/lang/String;B)V",
      garbageValue = "-65"
   )
   void method2164(int var1, String var2) {
      this.map.put(Integer.valueOf(var1), var2);
      this.onVarCStrChanged(var1, var2);
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-54"
   )
   void method2168() {
      AccessFile var1 = this.method2186(true);

      try {
         int var2 = 3;
         int var3 = 0;
         Iterator var4 = this.map.entrySet().iterator();

         while(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            int var6 = ((Integer)var5.getKey()).intValue();
            if(this.intsPersistence[var6]) {
               Object var7 = var5.getValue();
               var2 += 3;
               if(var7 instanceof Integer) {
                  var2 += 4;
               } else if(var7 instanceof String) {
                  var2 += class267.method4877((String)var7);
               }

               ++var3;
            }
         }

         Buffer var26 = new Buffer(var2);
         var26.writeByte(2);
         var26.method5481(var3);
         Iterator var27 = this.map.entrySet().iterator();

         while(var27.hasNext()) {
            Entry var15 = (Entry)var27.next();
            int var16 = ((Integer)var15.getKey()).intValue();
            if(this.intsPersistence[var16]) {
               var26.method5481(var16);
               Object var8 = var15.getValue();
               class11 var9 = class11.method123(var8.getClass());
               var26.writeByte(var9.field71);
               Class var11 = var8.getClass();
               class11 var12 = class11.method123(var11);
               if(var12 == null) {
                  throw new IllegalArgumentException();
               }

               class16 var10 = var12.field75;
               var10.vmethod213(var8, var26);
            }
         }

         var1.method14(var26.array, 0, var26.offset);
      } catch (Exception var24) {
         ;
      } finally {
         try {
            var1.method18();
         } catch (Exception var23) {
            ;
         }

      }

      this.unwrittenChanges = false;
      this.field1216 = class33.method680();
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "1021423877"
   )
   void method2190(int var1, int var2) {
      this.map.put(Integer.valueOf(var1), Integer.valueOf(var2));
      if(this.intsPersistence[var1]) {
         this.unwrittenChanges = true;
      }

      this.onVarCIntChanged(var1, var2);
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-1985614095"
   )
   boolean method2173() {
      return this.unwrittenChanges;
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-71"
   )
   void method2172() {
      if(this.unwrittenChanges && this.field1216 < class33.method680() - 60000L) {
         this.method2168();
      }

   }

   public void onVarCStrChanged(int var1, String var2) {
      ViewportMouse.client.getCallbacks().post(VarClientStrChanged.class, new VarClientStrChanged(var1));
   }

   public void onVarCIntChanged(int var1, int var2) {
      ViewportMouse.client.getCallbacks().post(VarClientIntChanged.class, new VarClientIntChanged(var1));
   }

   public Map getVarcMap() {
      return this.map;
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-71"
   )
   void method2171() {
      AccessFile var1 = this.method2186(false);

      label230: {
         try {
            byte[] var2 = new byte[(int)var1.method5()];

            int var4;
            for(int var3 = 0; var3 < var2.length; var3 += var4) {
               var4 = var1.method6(var2, var3, var2.length - var3);
               if(var4 == -1) {
                  throw new EOFException();
               }
            }

            Buffer var14 = new Buffer(var2);
            if(var14.array.length - var14.offset >= 1) {
               int var15 = var14.readUnsignedByte();
               if(var15 >= 0 && var15 <= 2) {
                  int var7;
                  int var8;
                  int var9;
                  int var16;
                  if(var15 >= 2) {
                     var16 = var14.readUnsignedShort();
                     var7 = 0;

                     while(true) {
                        if(var7 >= var16) {
                           break label230;
                        }

                        var8 = var14.readUnsignedShort();
                        var9 = var14.readUnsignedByte();
                        class11 var10 = (class11)NetSocket.getEnumeratedTypeIndex(class11.method131(), var9);
                        Object var11 = var10.method125(var14);
                        if(this.intsPersistence[var8]) {
                           this.map.put(Integer.valueOf(var8), var11);
                        }

                        ++var7;
                     }
                  } else {
                     var16 = var14.readUnsignedShort();

                     for(var7 = 0; var7 < var16; ++var7) {
                        var8 = var14.readUnsignedShort();
                        var9 = var14.readInt();
                        if(this.intsPersistence[var8]) {
                           this.map.put(Integer.valueOf(var8), Integer.valueOf(var9));
                        }
                     }

                     var7 = var14.readUnsignedShort();
                     var8 = 0;

                     while(true) {
                        if(var8 >= var7) {
                           break label230;
                        }

                        var14.readUnsignedShort();
                        var14.readString();
                        ++var8;
                     }
                  }
               }

               return;
            }
         } catch (Exception var25) {
            break label230;
         } finally {
            try {
               var1.method18();
            } catch (Exception var24) {
               ;
            }

         }

         return;
      }

      this.unwrittenChanges = false;
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(ZI)Lmh;",
      garbageValue = "1415923729"
   )
   AccessFile method2186(boolean var1) {
      return class202.method3853("2", class10.field66.name, var1);
   }
}
