package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("lt")
public class PlatformInfo extends Node {
   @ObfuscatedName("ab")
   @ObfuscatedGetter(
      intValue = -760772209
   )
   int field3985;
   @ObfuscatedName("ad")
   @ObfuscatedGetter(
      intValue = -1530437153
   )
   int field3999;
   @ObfuscatedName("ae")
   @ObfuscatedGetter(
      intValue = -1834478057
   )
   int field4004;
   @ObfuscatedName("af")
   @ObfuscatedGetter(
      intValue = 246544057
   )
   int field4005;
   @ObfuscatedName("ag")
   String field4007;
   @ObfuscatedName("ah")
   String field4006;
   @ObfuscatedName("ai")
   boolean field4001;
   @ObfuscatedName("aj")
   String field4016;
   @ObfuscatedName("ak")
   @ObfuscatedGetter(
      intValue = 1358828589
   )
   int field4002;
   @ObfuscatedName("al")
   @ObfuscatedGetter(
      intValue = -1993401909
   )
   int field4008;
   @ObfuscatedName("an")
   @ObfuscatedGetter(
      intValue = -1258615957
   )
   int field4000;
   @ObfuscatedName("ao")
   @ObfuscatedGetter(
      intValue = -1228924445
   )
   int field3997;
   @ObfuscatedName("aq")
   @ObfuscatedGetter(
      intValue = -931926903
   )
   int field4003;
   @ObfuscatedName("as")
   @ObfuscatedGetter(
      intValue = -68737307
   )
   int field3996;
   @ObfuscatedName("at")
   @ObfuscatedGetter(
      intValue = 84742935
   )
   int field4012;
   @ObfuscatedName("au")
   @ObfuscatedGetter(
      intValue = 118098315
   )
   int field4010;
   @ObfuscatedName("av")
   @ObfuscatedGetter(
      intValue = 400152439
   )
   int field3989;
   @ObfuscatedName("ax")
   boolean field3995;
   @ObfuscatedName("ay")
   String field4014;
   @ObfuscatedName("bd")
   String field3988;
   @ObfuscatedName("bh")
   String field4009;
   @ObfuscatedName("bj")
   String field4018;
   @ObfuscatedName("bm")
   int[] field3994;
   @ObfuscatedName("bv")
   @ObfuscatedGetter(
      intValue = 1280064129
   )
   int field4017;
   @ObfuscatedName("bx")
   @ObfuscatedGetter(
      intValue = 1315916991
   )
   int field3992;

   PlatformInfo(int var1, boolean var2, int var3, int var4, int var5, int var6, int var7, boolean var8, int var9, int var10, int var11, int var12, String var13, String var14, String var15, String var16, int var17, int var18, int var19, int var20, String var21, String var22, int[] var23, int var24, String var25) {
      this.field3994 = new int[3];
      this.field3985 = var1;
      this.field3995 = var2;
      this.field3996 = var3;
      this.field3997 = var4;
      this.field4008 = var5;
      this.field3999 = var6;
      this.field4000 = var7;
      this.field4001 = var8;
      this.field4002 = var9;
      this.field4003 = var10;
      this.field4004 = var11;
      this.field3989 = var12;
      this.field4006 = var13;
      this.field4007 = var14;
      this.field4016 = var15;
      this.field4014 = var16;
      this.field4010 = var17;
      this.field4005 = var18;
      this.field4012 = var19;
      this.field3992 = var20;
      this.field4009 = var21;
      this.field3988 = var22;
      this.field3994 = var23;
      this.field4017 = var24;
      this.field4018 = var25;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "3"
   )
   public int method6373() {
      byte var1 = 39;
      int var2 = var1 + UserComparator5.method3375(this.field4006);
      var2 += UserComparator5.method3375(this.field4007);
      var2 += UserComparator5.method3375(this.field4016);
      var2 += UserComparator5.method3375(this.field4014);
      var2 += UserComparator5.method3375(this.field4009);
      var2 += UserComparator5.method3375(this.field3988);
      var2 += UserComparator5.method3375(this.field4018);
      return var2;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lkl;I)V",
      garbageValue = "1592227856"
   )
   public void method6372(Buffer var1) {
      var1.writeByte(8);
      var1.writeByte(this.field3985);
      var1.writeByte(this.field3995?1:0);
      var1.method5481(this.field3996);
      var1.writeByte(this.field3997);
      var1.writeByte(this.field4008);
      var1.writeByte(this.field3999);
      var1.writeByte(this.field4000);
      var1.writeByte(this.field4001?1:0);
      var1.method5481(this.field4002);
      var1.writeByte(this.field4003);
      var1.write24BitInteger(this.field4004);
      var1.method5481(this.field3989);
      var1.method5488(this.field4006);
      var1.method5488(this.field4007);
      var1.method5488(this.field4016);
      var1.method5488(this.field4014);
      var1.writeByte(this.field4005);
      var1.method5481(this.field4010);
      var1.method5488(this.field4009);
      var1.method5488(this.field3988);
      var1.writeByte(this.field4012);
      var1.writeByte(this.field3992);

      for(int var2 = 0; var2 < this.field3994.length; ++var2) {
         var1.writeInt(this.field3994[var2]);
      }

      var1.writeInt(this.field4017);
      var1.method5488(this.field4018);
   }
}
