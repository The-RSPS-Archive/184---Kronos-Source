package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kj")
public final class BZip2State {
   @ObfuscatedName("eg")
   @ObfuscatedGetter(
      intValue = -1555084527
   )
   static int port2;
   @ObfuscatedName("n")
   final int field3705;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = -1053795843
   )
   int field3678;
   @ObfuscatedName("p")
   final int field3695;
   @ObfuscatedName("q")
   byte[] inputArray;
   @ObfuscatedName("r")
   final int field3682;
   @ObfuscatedName("s")
   @ObfuscatedGetter(
      intValue = 1764835615
   )
   int su_ch2;
   @ObfuscatedName("t")
   @ObfuscatedGetter(
      intValue = 2034972657
   )
   int bsLive;
   @ObfuscatedName("u")
   final int field3694;
   @ObfuscatedName("v")
   final int field3714;
   @ObfuscatedName("w")
   @ObfuscatedGetter(
      intValue = -1870654053
   )
   int bsBuff;
   @ObfuscatedName("x")
   @ObfuscatedGetter(
      intValue = -2121773995
   )
   int originalPointer;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = 19255173
   )
   int nextBit_unused;
   @ObfuscatedName("z")
   final int field3688;
   @ObfuscatedName("a")
   byte out_char;
   @ObfuscatedName("aa")
   byte[] ll8;
   @ObfuscatedName("ab")
   byte[] selectorMtf;
   @ObfuscatedName("ac")
   boolean[] inUse;
   @ObfuscatedName("ad")
   int[] minLens;
   @ObfuscatedName("al")
   int[][] perm;
   @ObfuscatedName("an")
   @ObfuscatedGetter(
      intValue = 648150601
   )
   int field3715;
   @ObfuscatedName("ao")
   int[][] base;
   @ObfuscatedName("ap")
   int[] getAndMoveToFrontDecode_yy;
   @ObfuscatedName("ar")
   byte[] selector;
   @ObfuscatedName("as")
   int[][] limit;
   @ObfuscatedName("aw")
   byte[] seqToUnseq;
   @ObfuscatedName("ax")
   byte[][] temp_charArray2d;
   @ObfuscatedName("az")
   boolean[] inUse16;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = -1492713953
   )
   int outputLength;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 577933795
   )
   int next_out;
   @ObfuscatedName("d")
   int[] cftab;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = 423882031
   )
   int su_rNToGo;
   @ObfuscatedName("f")
   int[] unzftab;
   @ObfuscatedName("g")
   @ObfuscatedGetter(
      intValue = -1857353977
   )
   int blockSize100k;
   @ObfuscatedName("h")
   @ObfuscatedGetter(
      intValue = -1375440771
   )
   int field3697;
   @ObfuscatedName("i")
   byte[] outputArray;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = 1747033841
   )
   int nblocks_used;
   @ObfuscatedName("k")
   @ObfuscatedGetter(
      intValue = 1244375945
   )
   int nInUse;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = -1460237959
   )
   int nextByte;

   BZip2State() {
      this.field3688 = 4096;
      this.field3705 = 16;
      this.field3714 = 258;
      this.field3694 = 6;
      this.field3682 = 50;
      this.field3695 = 18002;
      this.nextByte = 0;
      this.next_out = 0;
      this.unzftab = new int[256];
      this.cftab = new int[257];
      this.inUse = new boolean[256];
      this.inUse16 = new boolean[16];
      this.seqToUnseq = new byte[256];
      this.ll8 = new byte[4096];
      this.getAndMoveToFrontDecode_yy = new int[16];
      this.selector = new byte[18002];
      this.selectorMtf = new byte[18002];
      this.temp_charArray2d = new byte[6][258];
      this.limit = new int[6][258];
      this.base = new int[6][258];
      this.perm = new int[6][258];
      this.minLens = new int[6];
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)[Lkc;",
      garbageValue = "-127"
   )
   static PrivateChatMode[] method5454() {
      return new PrivateChatMode[]{PrivateChatMode.field3633, PrivateChatMode.field3631, PrivateChatMode.field3632};
   }

   @ObfuscatedName("hy")
   @ObfuscatedSignature(
      signature = "(II)Z",
      garbageValue = "157596925"
   )
   static boolean method5455(int var0) {
      return var0 == 57 || var0 == 58 || var0 == 1007 || var0 == 25 || var0 == 30;
   }
}
