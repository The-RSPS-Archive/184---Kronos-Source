package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("hd")
public class class210 {
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)[Lhj;",
      garbageValue = "133309082"
   )
   //TODO: Modified Added new ranks to array
   public static PlayerType[] getPlayerTypes() {
      return new PlayerType[]{PlayerType.PlayerType_jagexModerator, PlayerType.PlayerType_normal, PlayerType.PlayerType_playerModerator, PlayerType.PlayerType_ironman,
              PlayerType.PlayerType_hardcoreIronman, PlayerType.PlayerType_ultimateIronman, PlayerType.DEVELOPER, PlayerType.COMMUNITY_MANAGER, PlayerType.SUPPORT,
              PlayerType.SAPPHIRE, PlayerType.EMERALD, PlayerType.RUBY, PlayerType.DIAMOND, PlayerType.DRAGONSTONE, PlayerType.ONYX, PlayerType.ZENYTE, PlayerType.GROUP_IRONMAN,
              PlayerType.YOUTUBER
      };
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(II)Lep;",
      garbageValue = "1928253690"
   )
   static Frames method3930(int var0) {
      Frames var1 = (Frames)SequenceDefinition.SequenceDefinition_cachedFrames.method3032((long)var0);
      if(var1 != null) {
         return var1;
      } else {
         AbstractArchive var3 = SequenceDefinition.SequenceDefinition_animationsArchive;
         AbstractArchive var4 = class183.SequenceDefinition_skeletonsArchive;
         boolean var5 = true;
         int[] var6 = var3.method4042(var0);

         for(int var7 = 0; var7 < var6.length; ++var7) {
            byte[] var8 = var3.method4028(var0, var6[var7]);
            if(var8 == null) {
               var5 = false;
            } else {
               int var9 = (var8[0] & 255) << 8 | var8[1] & 255;
               byte[] var10 = var4.method4028(var9, 0);
               if(var10 == null) {
                  var5 = false;
               }
            }
         }

         Frames var2;
         if(!var5) {
            var2 = null;
         } else {
            try {
               var2 = new Frames(var3, var4, var0, false);
            } catch (Exception var12) {
               var2 = null;
            }
         }

         if(var2 != null) {
            SequenceDefinition.SequenceDefinition_cachedFrames.method3034(var2, (long)var0);
         }

         return var2;
      }
   }
}
