package net.runelite.standalone;

import net.runelite.api.ClanMemberRank;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSClanMate;

@ObfuscatedName("jo")
public class ClanMate extends Buddy implements RSClanMate {
   @ObfuscatedName("dj")
   @ObfuscatedSignature(
      signature = "Lie;"
   )
   static Archive archive11;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Ljm;"
   )
   TriBool ignored;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Ljm;"
   )
   TriBool friend;

   ClanMate() {
      this.friend = TriBool.TriBool_unknown;
      this.ignored = TriBool.TriBool_unknown;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "1388018244"
   )
   public final boolean method4982() {
      if(this.friend == TriBool.TriBool_unknown) {
         this.method4972();
      }

      return this.friend == TriBool.TriBool_true;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-735499948"
   )
   void method4984() {
      this.ignored = Tiles.friendSystem.ignoreList.method4770(super.username)?TriBool.TriBool_true:TriBool.TriBool_false;
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "-1700541155"
   )
   public final boolean method4976() {
      if(this.ignored == TriBool.TriBool_unknown) {
         this.method4984();
      }

      return this.ignored == TriBool.TriBool_true;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "349082109"
   )
   void method4975() {
      this.ignored = TriBool.TriBool_unknown;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "2101732622"
   )
   void method4972() {
      this.friend = Tiles.friendSystem.friendsList.method4770(super.username)?TriBool.TriBool_true:TriBool.TriBool_false;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-407939513"
   )
   void method4973() {
      this.friend = TriBool.TriBool_unknown;
   }

   public String getUsername() {
      return this.getRsName().getName();
   }

   public ClanMemberRank getRank() {
      return ClanMemberRank.valueOf(this.getRSRank());
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lhp;Lhp;III)Lkn;",
      garbageValue = "-569680121"
   )
   public static Font method4989(AbstractArchive var0, AbstractArchive var1, int var2, int var3) {
      return !VertexNormal.method2468(var0, var2, var3)?null:class38.method731(var1.method4020(var2, var3, (short)-3362));
   }
}
