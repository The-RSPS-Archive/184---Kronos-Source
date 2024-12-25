package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSBuddy;

@ObfuscatedName("jl")
public class Buddy extends User implements RSBuddy {
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -1761858423
   )
   public int int2;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = 1627143829
   )
   public int rank;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = -1753766973
   )
   public int world;

   Buddy() {
      this.world = -1;
   }

   @ObfuscatedName("ak")
   @ObfuscatedSignature(
      signature = "(III)V",
      garbageValue = "-736416010"
   )
   void method4957(int var1, int var2) {
      this.world = var1;
      this.int2 = var2;
   }

   @ObfuscatedName("am")
   @ObfuscatedSignature(
      signature = "(B)Z",
      garbageValue = "0"
   )
   public boolean method4959() {
      return this.world > 0;
   }

   @ObfuscatedName("aq")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-516703486"
   )
   public int method4958() {
      return this.world;
   }

   public int getRSRank() {
      return this.rank;
   }

   public int getWorld() {
      return this.method4958();
   }

   @ObfuscatedName("ap")
   @ObfuscatedSignature(
      signature = "(ILcu;ZI)I",
      garbageValue = "1426462552"
   )
   static int method4961(int var0, Script var1, boolean var2) {
      if(var0 == 3600) {
         if(Tiles.friendSystem.field406 == 0) {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -2;
         } else if(Tiles.friendSystem.field406 == 1) {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
         } else {
            Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Tiles.friendSystem.friendsList.method4800();
         }

         return 1;
      } else {
         int var3;
         if(var0 == 3601) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            if(Tiles.friendSystem.method891() && var3 >= 0 && var3 < Tiles.friendSystem.friendsList.method4800()) {
               Friend var8 = (Friend)Tiles.friendSystem.friendsList.method4778(var3);
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var8.method4880();
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var8.method4878();
            } else {
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
            }

            return 1;
         } else if(var0 == 3602) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            if(Tiles.friendSystem.method891() && var3 >= 0 && var3 < Tiles.friendSystem.friendsList.method4800()) {
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = ((Buddy)Tiles.friendSystem.friendsList.method4778(var3)).world;
            } else {
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
            }

            return 1;
         } else if(var0 == 3603) {
            var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
            if(Tiles.friendSystem.method891() && var3 >= 0 && var3 < Tiles.friendSystem.friendsList.method4800()) {
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = ((Buddy)Tiles.friendSystem.friendsList.method4778(var3)).rank;
            } else {
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
            }

            return 1;
         } else {
            String var5;
            if(var0 == 3604) {
               var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               int var6 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               NPC.method1617(var5, var6);
               return 1;
            } else if(var0 == 3605) {
               var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               Tiles.friendSystem.method898(var5);
               return 1;
            } else if(var0 == 3606) {
               var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               Tiles.friendSystem.method942(var5);
               return 1;
            } else if(var0 == 3607) {
               var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               Tiles.friendSystem.method900(var5);
               return 1;
            } else if(var0 == 3608) {
               var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               Tiles.friendSystem.method923(var5);
               return 1;
            } else if(var0 == 3609) {
               var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               var5 = WorldMapSectionType.method113(var5);
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Tiles.friendSystem.method896(new Username(var5, WorldMapSection1.loginType), false)?1:0;
               return 1;
            } else if(var0 == 3611) {
               if(Varps.clanChat != null) {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = Varps.clanChat.name;
               } else {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               }

               return 1;
            } else if(var0 == 3612) {
               if(Varps.clanChat != null) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Varps.clanChat.method4800();
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if(var0 == 3613) {
               var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               if(Varps.clanChat != null && var3 < Varps.clanChat.method4800()) {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = Varps.clanChat.method4778(var3).method4879().method5001();
               } else {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               }

               return 1;
            } else if(var0 == 3614) {
               var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               if(Varps.clanChat != null && var3 < Varps.clanChat.method4800()) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = ((Buddy)Varps.clanChat.method4778(var3)).method4958();
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if(var0 == 3615) {
               var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               if(Varps.clanChat != null && var3 < Varps.clanChat.method4800()) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = ((Buddy)Varps.clanChat.method4778(var3)).rank;
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if(var0 == 3616) {
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Varps.clanChat != null?Varps.clanChat.minKick:0;
               return 1;
            } else if(var0 == 3617) {
               var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               NetCache.method4465(var5);
               return 1;
            } else if(var0 == 3618) {
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Varps.clanChat != null?Varps.clanChat.rank:0;
               return 1;
            } else if(var0 == 3619) {
               var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               World.method1252(var5);
               return 1;
            } else if(var0 == 3620) {
               WorldMapLabel.method742();
               return 1;
            } else if(var0 == 3621) {
               if(!Tiles.friendSystem.method891()) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = -1;
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Tiles.friendSystem.ignoreList.method4800();
               }

               return 1;
            } else if(var0 == 3622) {
               var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               if(Tiles.friendSystem.method891() && var3 >= 0 && var3 < Tiles.friendSystem.ignoreList.method4800()) {
                  Ignored var4 = (Ignored)Tiles.friendSystem.ignoreList.method4778(var3);
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var4.method4880();
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var4.method4878();
               } else {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               }

               return 1;
            } else if(var0 == 3623) {
               var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               var5 = WorldMapSectionType.method113(var5);
               Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = Tiles.friendSystem.method897(new Username(var5, WorldMapSection1.loginType))?1:0;
               return 1;
            } else if(var0 == 3624) {
               var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               if(Varps.clanChat != null && var3 < Varps.clanChat.method4800() && Varps.clanChat.method4778(var3).method4879().equals(class215.localPlayer.username)) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 1;
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if(var0 == 3625) {
               if(Varps.clanChat != null && Varps.clanChat.owner != null) {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = Varps.clanChat.owner;
               } else {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               }

               return 1;
            } else if(var0 == 3626) {
               var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               if(Varps.clanChat != null && var3 < Varps.clanChat.method4800() && ((ClanMate)Varps.clanChat.method4778(var3)).method4982()) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 1;
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if(var0 != 3627) {
               if(var0 == 3628) {
                  Tiles.friendSystem.friendsList.method4788();
                  return 1;
               } else {
                  boolean var7;
                  if(var0 == 3629) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.friendsList.method4789(new UserComparator1(var7));
                     return 1;
                  } else if(var0 == 3630) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.friendsList.method4789(new UserComparator2(var7));
                     return 1;
                  } else if(var0 == 3631) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.friendsList.method4789(new UserComparator3(var7));
                     return 1;
                  } else if(var0 == 3632) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.friendsList.method4789(new UserComparator4(var7));
                     return 1;
                  } else if(var0 == 3633) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.friendsList.method4789(new UserComparator5(var7));
                     return 1;
                  } else if(var0 == 3634) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.friendsList.method4789(new UserComparator6(var7));
                     return 1;
                  } else if(var0 == 3635) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.friendsList.method4789(new UserComparator7(var7));
                     return 1;
                  } else if(var0 == 3636) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.friendsList.method4789(new UserComparator8(var7));
                     return 1;
                  } else if(var0 == 3637) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.friendsList.method4789(new UserComparator9(var7));
                     return 1;
                  } else if(var0 == 3638) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.friendsList.method4789(new UserComparator10(var7));
                     return 1;
                  } else if(var0 == 3639) {
                     Tiles.friendSystem.friendsList.method4766();
                     return 1;
                  } else if(var0 == 3640) {
                     Tiles.friendSystem.ignoreList.method4788();
                     return 1;
                  } else if(var0 == 3641) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.ignoreList.method4789(new UserComparator1(var7));
                     return 1;
                  } else if(var0 == 3642) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.ignoreList.method4789(new UserComparator2(var7));
                     return 1;
                  } else if(var0 == 3643) {
                     Tiles.friendSystem.ignoreList.method4766();
                     return 1;
                  } else if(var0 == 3644) {
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4788();
                     }

                     return 1;
                  } else if(var0 == 3645) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4789(new UserComparator1(var7));
                     }

                     return 1;
                  } else if(var0 == 3646) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4789(new UserComparator2(var7));
                     }

                     return 1;
                  } else if(var0 == 3647) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4789(new UserComparator3(var7));
                     }

                     return 1;
                  } else if(var0 == 3648) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4789(new UserComparator4(var7));
                     }

                     return 1;
                  } else if(var0 == 3649) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4789(new UserComparator5(var7));
                     }

                     return 1;
                  } else if(var0 == 3650) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4789(new UserComparator6(var7));
                     }

                     return 1;
                  } else if(var0 == 3651) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4789(new UserComparator7(var7));
                     }

                     return 1;
                  } else if(var0 == 3652) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4789(new UserComparator8(var7));
                     }

                     return 1;
                  } else if(var0 == 3653) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4789(new UserComparator9(var7));
                     }

                     return 1;
                  } else if(var0 == 3654) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4789(new UserComparator10(var7));
                     }

                     return 1;
                  } else if(var0 == 3655) {
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4766();
                     }

                     return 1;
                  } else if(var0 == 3656) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     Tiles.friendSystem.friendsList.method4789(new BuddyRankComparator(var7));
                     return 1;
                  } else if(var0 == 3657) {
                     var7 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize] == 1;
                     if(Varps.clanChat != null) {
                        Varps.clanChat.method4789(new BuddyRankComparator(var7));
                     }

                     return 1;
                  } else {
                     return 2;
                  }
               }
            } else {
               var3 = Interpreter.Interpreter_intStack[--Interpreter.Interpreter_intStackSize];
               if(Varps.clanChat != null && var3 < Varps.clanChat.method4800() && ((ClanMate)Varps.clanChat.method4778(var3)).method4976()) {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 1;
               } else {
                  Interpreter.Interpreter_intStack[++Interpreter.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            }
         }
      }
   }
}
