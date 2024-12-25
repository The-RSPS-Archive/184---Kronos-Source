package net.runelite.standalone;

import java.util.EnumSet;
import net.runelite.api.WorldType;
import net.runelite.api.events.WorldListLoad;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSWorld;

@ObfuscatedName("bo")
public class World implements RSWorld {
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -1109260753
   )
   static int World_count;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -1029210309
   )
   static int World_listCount;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "[Lbo;"
   )
   static World[] World_worlds;
   @ObfuscatedName("y")
   static int[] World_sortOption1;
   @ObfuscatedName("en")
   @ObfuscatedGetter(
      intValue = 1902437399
   )
   static int field570;
   @ObfuscatedName("m")
   static int[] World_sortOption2;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = 997157235
   )
   int population;
   @ObfuscatedName("t")
   @ObfuscatedGetter(
      intValue = 1472418349
   )
   int index;
   @ObfuscatedName("w")
   @ObfuscatedGetter(
      intValue = 1450881291
   )
   int location;
   @ObfuscatedName("a")
   String host;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = 1702314457
   )
   int properties;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 974966281
   )
   int id;
   @ObfuscatedName("e")
   String activity;

   static {
      World_count = 0;
      World_listCount = 0;
      World_sortOption2 = new int[]{1, 1, 1, 1};
      World_sortOption1 = new int[]{0, 1, 2, 3};
   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "100459661"
   )
   boolean method1213() {
      return (8 & this.properties) != 0;
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-1262281013"
   )
   boolean method1214() {
      return (536870912 & this.properties) != 0;
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "2080738289"
   )
   boolean method1247() {
      return (4 & this.properties) != 0;
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-622240981"
   )
   boolean method1241() {
      return (2 & this.properties) != 0;
   }

   public int getMask() {
      return this.properties;
   }

   public void setMask(int var1) {
      this.properties = var1;
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public void playerCountChanged(int var1) {
      RSWorld[] var2 = ViewportMouse.client.getWorldList();
      if(var2 != null && var2.length > 0 && this == var2[var2.length - 1]) {
         WorldListLoad var3 = new WorldListLoad(var2);
         ViewportMouse.client.getCallbacks().post(WorldListLoad.class, var3);
      }

   }

   public EnumSet getTypes() {
      return WorldType.fromMask(this.getMask());
   }

   public void setTypes(EnumSet var1) {
      this.setMask(WorldType.toMask(var1));
   }

   public void setId(int var1) {
      this.id = var1;
   }

   public int getId() {
      return this.id;
   }

   public void setPlayerCount(int var1) {
      this.population = var1;
   }

   public int getPlayerCount() {
      return this.population;
   }

   public void setAddress(String var1) {
      this.host = var1;
   }

   public String getAddress() {
      return this.host;
   }

   public void setActivity(String var1) {
      this.activity = var1;
   }

   public String getActivity() {
      return this.activity;
   }

   public void setLocation(int var1) {
      this.location = var1;
   }

   public int getLocation() {
      return this.location;
   }

   public void setIndex(int var1) {
      this.index = var1;
   }

   public int getIndex() {
      return this.index;
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(B)Z",
      garbageValue = "-24"
   )
   boolean method1234() {
      return (33554432 & this.properties) != 0;
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "858641980"
   )
   boolean method1212() {
      return (1 & this.properties) != 0;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(IB)Ljava/lang/String;",
      garbageValue = "108"
   )
   static String method1251(int var0) {
      return "<col=" + Integer.toHexString(var0) + ">";
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(III)I",
      garbageValue = "-1827551520"
   )
   public static int method1249(int var0, int var1) {
      int var2 = var0 >>> 31;
      return (var0 + var2) / var1 - var2;
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "1196159395"
   )
   static int method1250(int var0, Script var1, boolean var2) {
      Widget var3 = var2?GrandExchangeOfferAgeComparator.field26:KitDefinition.field3452;
      if(var0 == 1800) {
         Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = class12.method155(class12.method148(var3));
         return 1;
      } else if(var0 != 1801) {
         if(var0 == 1802) {
            if(var3.dataText == null) {
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
            } else {
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3.dataText;
            }

            return 1;
         } else {
            return 2;
         }
      } else {
         int var4 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
         --var4;
         if(var3.actions != null && var4 < var3.actions.length && var3.actions[var4] != null) {
            Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3.actions[var4];
         } else {
            Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
         }

         return 1;
      }
   }

   @ObfuscatedName("z")
   public static String method1215(long var0) {
      if(var0 > 0L && var0 < 6582952005840035281L) {
         if(0L == var0 % 37L) {
            return null;
         } else {
            int var2 = 0;

            for(long var3 = var0; 0L != var3; var3 /= 37L) {
               ++var2;
            }

            StringBuilder var5;
            char var8;
            for(var5 = new StringBuilder(var2); var0 != 0L; var5.append(var8)) {
               long var6 = var0;
               var0 /= 37L;
               var8 = class292.base37Table[(int)(var6 - var0 * 37L)];
               if(var8 == '_') {
                  int var9 = var5.length() - 1;
                  var5.setCharAt(var9, Character.toUpperCase(var5.charAt(var9)));
                  var8 = 160;
               }
            }

            var5.reverse();
            var5.setCharAt(0, Character.toUpperCase(var5.charAt(0)));
            return var5.toString();
         }
      } else {
         return null;
      }
   }

   @ObfuscatedName("ga")
   @ObfuscatedSignature(
      signature = "(Lby;II)V",
      garbageValue = "-310218201"
   )
   static final void method1253(Actor var0, int var1) {
      PlayerAppearance.method4162(var0.x, var0.y * 682054857, var1);
   }

   @ObfuscatedName("jj")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)V",
      garbageValue = "1791021557"
   )
   static final void method1252(String var0) {
      if(!var0.equals("")) {
         PacketBufferNode var1 = InterfaceParent.method1140(ClientPacket.field2393, Client.packetWriter.isaacCipher);
         var1.packetBuffer.writeByte(class267.method4877(var0));
         var1.packetBuffer.writeString(var0);
         Client.packetWriter.method1622(var1);
      }
   }
}
