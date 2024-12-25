package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("cw")
public class class94 {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Ljs;"
   )
   public static IterableNodeDeque reflectionChecks;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = -2145130731
   )
   static int field1242;

   static {
      reflectionChecks = new IterableNodeDeque();
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Llb;ILjava/lang/String;I)Ljava/lang/String;",
      garbageValue = "539746719"
   )
   static String method2216(IterableNodeHashTable var0, int index, String defaultString) {
      try {
         if (var0 == null) {
            return defaultString;
         } else {
            ObjectNode var3 = (ObjectNode) var0.method6061((long) index);
            return var3 == null ? defaultString : (String) var3.obj;
         }
      } catch (Exception ex) {
         ex.printStackTrace();
         return "";
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)[Lgv;",
      garbageValue = "-50"
   )
   public static ServerPacket[] method2222() {
      return new ServerPacket[]{ServerPacket.field2267, ServerPacket.UPDATE_CONATINER_ITEMS, ServerPacket.field2246, ServerPacket.field2239, ServerPacket.field2254, ServerPacket.field2240, ServerPacket.field2242, ServerPacket.field2318, ServerPacket.field2244, ServerPacket.field2281, ServerPacket.field2273, ServerPacket.field2308, ServerPacket.field2248, ServerPacket.field2243, ServerPacket.field2250, ServerPacket.field2251, ServerPacket.field2252, ServerPacket.field2253, ServerPacket.field2286, ServerPacket.field2255, ServerPacket.field2256, ServerPacket.field2237, ServerPacket.field2280, ServerPacket.field2259, ServerPacket.field2260, ServerPacket.field2261, ServerPacket.field2236, ServerPacket.field2263, ServerPacket.field2264, ServerPacket.field2265, ServerPacket.field2245, ServerPacket.field2274, ServerPacket.field2268, ServerPacket.field2269, ServerPacket.field2258, ServerPacket.field2271, ServerPacket.field2300, ServerPacket.field2282, ServerPacket.field2262, ServerPacket.field2247, ServerPacket.field2276, ServerPacket.field2277, ServerPacket.field2278, ServerPacket.field2279, ServerPacket.field2241, ServerPacket.field2270, ServerPacket.field2315, ServerPacket.field2323, ServerPacket.field2309, ServerPacket.SEND_CONTAINER_ITEMS, ServerPacket.field2283, ServerPacket.field2287, ServerPacket.field2288, ServerPacket.field2289, ServerPacket.field2290, ServerPacket.field2291, ServerPacket.field2292, ServerPacket.field2293, ServerPacket.field2294, ServerPacket.field2295, ServerPacket.field2296, ServerPacket.field2297, ServerPacket.field2238, ServerPacket.field2299, ServerPacket.field2298, ServerPacket.field2301, ServerPacket.field2302, ServerPacket.field2303, ServerPacket.field2304, ServerPacket.field2305, ServerPacket.field2306, ServerPacket.field2257, ServerPacket.field2284, ServerPacket.field2307, ServerPacket.field2310, ServerPacket.field2311, ServerPacket.field2312, ServerPacket.field2313, ServerPacket.field2314, ServerPacket.field2324, ServerPacket.field2316, ServerPacket.field2317, ServerPacket.field2320, ServerPacket.field2319, ServerPacket.field2266, ServerPacket.field2321, ServerPacket.CLAN_CHAT_SETTINGS, ServerPacket.OPEN_URL, ServerPacket.SHOP_INTERFACE, ServerPacket.TELEPORT_INTERFACE, ServerPacket.DROP_TABLE_INTERFACE, ServerPacket.WIDGET_TIMER};
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)[Llu;",
      garbageValue = "-67"
   )
   public static FillMode[] method2223() {
      return new FillMode[]{FillMode.field4020, FillMode.SOLID, FillMode.field4021};
   }
}
