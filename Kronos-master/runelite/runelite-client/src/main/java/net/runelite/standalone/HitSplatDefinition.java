package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("iv")
public class HitSplatDefinition extends DualNode {
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   static EvictingDualNodeHashTable HitSplatDefinition_cachedFonts;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   static EvictingDualNodeHashTable HitSplatDefinition_cachedSprites;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Lem;"
   )
   public static EvictingDualNodeHashTable HitSplatDefinition_cached;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   static AbstractArchive HitSplatDefinition_fontsArchive;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   public static AbstractArchive HitSplatDefinition_archive;
   @ObfuscatedName("es")
   @ObfuscatedGetter(
      intValue = 290330035
   )
   static int port1;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = -296610035
   )
   public int textColor;
   @ObfuscatedName("s")
   @ObfuscatedGetter(
      intValue = -1569661547
   )
   public int field3407;
   @ObfuscatedName("t")
   @ObfuscatedGetter(
      intValue = -2011301745
   )
   int field3403;
   @ObfuscatedName("w")
   @ObfuscatedGetter(
      intValue = -591877115
   )
   int field3414;
   @ObfuscatedName("x")
   @ObfuscatedGetter(
      intValue = -87931297
   )
   public int field3405;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = 31719475
   )
   public int field3400;
   @ObfuscatedName("ac")
   @ObfuscatedGetter(
      intValue = -168749029
   )
   int transformVarp;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = -135266261
   )
   int fontId;
   @ObfuscatedName("d")
   @ObfuscatedGetter(
      intValue = 1970000523
   )
   public int field3416;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = -1509097345
   )
   int field3401;
   @ObfuscatedName("f")
   String field3398;
   @ObfuscatedName("g")
   @ObfuscatedGetter(
      intValue = 1974829897
   )
   int field3404;
   @ObfuscatedName("h")
   @ObfuscatedGetter(
      intValue = -577071813
   )
   public int field3393;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = 1618086973
   )
   public int field3409;
   @ObfuscatedName("k")
   @ObfuscatedGetter(
      intValue = 354517383
   )
   int transformVarbit;
   @ObfuscatedName("l")
   public int[] transforms;

   static {
      HitSplatDefinition_cached = new EvictingDualNodeHashTable(64);
      HitSplatDefinition_cachedSprites = new EvictingDualNodeHashTable(64);
      HitSplatDefinition_cachedFonts = new EvictingDualNodeHashTable(20);
   }

   public HitSplatDefinition() {
      this.fontId = -1;
      this.textColor = 16777215;
      this.field3400 = 70;
      this.field3401 = -1;
      this.field3414 = -1;
      this.field3403 = -1;
      this.field3404 = -1;
      this.field3405 = 0;
      this.field3393 = 0;
      this.field3407 = -1;
      this.field3398 = "";
      this.field3409 = -1;
      this.field3416 = 0;
      this.transformVarbit = -1;
      this.transformVarp = -1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(Lkl;B)V",
      garbageValue = "47"
   )
   public void method4619(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if(var2 == 0) {
            return;
         }

         this.method4620(var1, var2);
      }
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(I)Llf;",
      garbageValue = "106816510"
   )
   public Sprite method4626() {
      if(this.field3401 < 0) {
         return null;
      } else {
         Sprite var1 = (Sprite)HitSplatDefinition_cachedSprites.method3032((long)this.field3401);
         if(var1 != null) {
            return var1;
         } else {
            var1 = NPCDefinition.method4417(GrandExchangeOfferNameComparator.field321, this.field3401, 0, -1092680498);
            if(var1 != null) {
               HitSplatDefinition_cachedSprites.method3034(var1, (long)this.field3401);
            }

            return var1;
         }
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(I)Llf;",
      garbageValue = "-123360505"
   )
   public Sprite method4623() {
      if(this.field3414 < 0) {
         return null;
      } else {
         Sprite var1 = (Sprite)HitSplatDefinition_cachedSprites.method3032((long)this.field3414);
         if(var1 != null) {
            return var1;
         } else {
            var1 = NPCDefinition.method4417(GrandExchangeOfferNameComparator.field321, this.field3414, 0, -1092680498);
            if(var1 != null) {
               HitSplatDefinition_cachedSprites.method3034(var1, (long)this.field3414);
            }

            return var1;
         }
      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(IB)Ljava/lang/String;",
      garbageValue = "13"
   )
   public String method4621(int var1) {
      String var2 = this.field3398;

      while(true) {
         int var3 = var2.indexOf("%1");
         if(var3 < 0) {
            return var2;
         }

         var2 = var2.substring(0, var3) + HealthBar.method2255(var1, false) + var2.substring(var3 + 2);
      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I)Liv;",
      garbageValue = "723685"
   )
   public final HitSplatDefinition method4645() {
      int var1 = -1;
      if(this.transformVarbit != -1) {
         var1 = WorldMapSprite.method782(this.transformVarbit);
      } else if(this.transformVarp != -1) {
         var1 = Varps.Varps_main[this.transformVarp];
      }

      int var2;
      if(var1 >= 0 && var1 < this.transforms.length - 1) {
         var2 = this.transforms[var1];
      } else {
         var2 = this.transforms[this.transforms.length - 1];
      }

      if(var2 != -1) {
         HitSplatDefinition var4 = (HitSplatDefinition)HitSplatDefinition_cached.method3032((long)var2);
         HitSplatDefinition var3;
         if(var4 != null) {
            var3 = var4;
         } else {
            byte[] var5 = HitSplatDefinition_archive.method4020(32, var2, (short)-4238);
            var4 = new HitSplatDefinition();
            if(var5 != null) {
               var4.method4619(new Buffer(var5));
            }

            HitSplatDefinition_cached.method3034(var4, (long)var2);
            var3 = var4;
         }

         return var3;
      } else {
         return null;
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lkl;II)V",
      garbageValue = "1800631473"
   )
   void method4620(Buffer var1, int var2) {
      if(var2 == 1) {
         this.fontId = var1.method5507();
      } else if(var2 == 2) {
         this.textColor = var1.method5500();
      } else if(var2 == 3) {
         this.field3401 = var1.method5507();
      } else if(var2 == 4) {
         this.field3403 = var1.method5507();
      } else if(var2 == 5) {
         this.field3414 = var1.method5507();
      } else if(var2 == 6) {
         this.field3404 = var1.method5507();
      } else if(var2 == 7) {
         this.field3405 = var1.g2s();
      } else if(var2 == 8) {
         this.field3398 = var1.method5556();
      } else if(var2 == 9) {
         this.field3400 = var1.readUnsignedShort();
      } else if(var2 == 10) {
         this.field3393 = var1.g2s();
      } else if(var2 == 11) {
         this.field3407 = 0;
      } else if(var2 == 12) {
         this.field3409 = var1.readUnsignedByte();
      } else if(var2 == 13) {
         this.field3416 = var1.g2s();
      } else if(var2 == 14) {
         this.field3407 = var1.readUnsignedShort();
      } else if(var2 == 17 || var2 == 18) {
         this.transformVarbit = var1.readUnsignedShort();
         if(this.transformVarbit == 65535) {
            this.transformVarbit = -1;
         }

         this.transformVarp = var1.readUnsignedShort();
         if(this.transformVarp == 65535) {
            this.transformVarp = -1;
         }

         int var3 = -1;
         if(var2 == 18) {
            var3 = var1.readUnsignedShort();
            if(var3 == 65535) {
               var3 = -1;
            }
         }

         int var4 = var1.readUnsignedByte();
         this.transforms = new int[var4 + 2];

         for(int var5 = 0; var5 <= var4; ++var5) {
            this.transforms[var5] = var1.readUnsignedShort();
            if(this.transforms[var5] == 65535) {
               this.transforms[var5] = -1;
            }
         }

         this.transforms[var4 + 1] = var3;
      }

   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)Llf;",
      garbageValue = "750571201"
   )
   public Sprite method4648() {
      if(this.field3404 < 0) {
         return null;
      } else {
         Sprite var1 = (Sprite)HitSplatDefinition_cachedSprites.method3032((long)this.field3404);
         if(var1 != null) {
            return var1;
         } else {
            var1 = NPCDefinition.method4417(GrandExchangeOfferNameComparator.field321, this.field3404, 0, -1092680498);
            if(var1 != null) {
               HitSplatDefinition_cachedSprites.method3034(var1, (long)this.field3404);
            }

            return var1;
         }
      }
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(I)Lkn;",
      garbageValue = "-1418238828"
   )
   public Font method4622() {
      if(this.fontId == -1) {
         return null;
      } else {
         Font var1 = (Font)HitSplatDefinition_cachedFonts.method3032((long)this.fontId);
         if(var1 != null) {
            return var1;
         } else {
            var1 = ClanMate.method4989(GrandExchangeOfferNameComparator.field321, HitSplatDefinition_fontsArchive, this.fontId, 0);
            if(var1 != null) {
               HitSplatDefinition_cachedFonts.method3034(var1, (long)this.fontId);
            }

            return var1;
         }
      }
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(B)Llf;",
      garbageValue = "88"
   )
   public Sprite method4624() {
      if(this.field3403 < 0) {
         return null;
      } else {
         Sprite var1 = (Sprite)HitSplatDefinition_cachedSprites.method3032((long)this.field3403);
         if(var1 != null) {
            return var1;
         } else {
            var1 = NPCDefinition.method4417(GrandExchangeOfferNameComparator.field321, this.field3403, 0, -1092680498);
            if(var1 != null) {
               HitSplatDefinition_cachedSprites.method3034(var1, (long)this.field3403);
            }

            return var1;
         }
      }
   }
}
