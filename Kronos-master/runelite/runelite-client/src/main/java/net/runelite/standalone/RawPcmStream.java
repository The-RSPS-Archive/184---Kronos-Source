package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSRawPcmStream;

@ObfuscatedName("du")
public class RawPcmStream extends PcmStream implements RSRawPcmStream {
   @ObfuscatedName("n")
   int field1568;
   @ObfuscatedName("o")
   int field1567;
   @ObfuscatedName("p")
   int field1572;
   @ObfuscatedName("q")
   int field1573;
   @ObfuscatedName("r")
   int field1580;
   @ObfuscatedName("u")
   int field1576;
   @ObfuscatedName("v")
   int field1569;
   @ObfuscatedName("y")
   int start;
   @ObfuscatedName("z")
   int field1578;
   @ObfuscatedName("a")
   int field1570;
   @ObfuscatedName("b")
   int field1575;
   @ObfuscatedName("c")
   boolean field1577;
   @ObfuscatedName("e")
   int field1581;
   @ObfuscatedName("i")
   int end;
   @ObfuscatedName("m")
   int numLoops;

   @ObfuscatedSignature(
      signature = "(Lch;II)V"
   )
   RawPcmStream(RawSound var1, int var2, int var3) {
      super.sound = var1;
      this.start = var1.start;
      this.end = var1.end;
      this.field1577 = var1.field809;
      this.field1568 = var2;
      this.field1569 = var3;
      this.field1576 = 8192;
      this.field1578 = 0;
      this.method2609();
   }

   @ObfuscatedSignature(
      signature = "(Lch;III)V"
   )
   RawPcmStream(RawSound var1, int var2, int var3, int var4) {
      super.sound = var1;
      this.start = var1.start;
      this.end = var1.end;
      this.field1577 = var1.field809;
      this.field1568 = var2;
      this.field1569 = var3;
      this.field1576 = var4;
      this.field1578 = 0;
      this.method2609();
   }

   @ObfuscatedName("o")
   synchronized void method2549(int var1) {
      this.method2550(var1, this.method2552());
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "()Ldc;"
   )
   protected PcmStream vmethod3775() {
      return null;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "()Ldc;"
   )
   protected PcmStream vmethod3794() {
      return null;
   }

   @ObfuscatedName("r")
   void method2609() {
      this.field1580 = this.field1569;
      this.field1572 = method2635(this.field1569, this.field1576);
      this.field1573 = method2541(this.field1569, this.field1576);
   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "(Z)V",
      garbageValue = "1"
   )
   public synchronized void method2554() {
      this.field1568 = (this.field1568 ^ this.field1568 >> 31) + (this.field1568 >>> 31);
      this.field1568 = -this.field1568;
   }

   @ObfuscatedName("x")
   public synchronized int method2552() {
      return this.field1576 < 0?-1:this.field1576;
   }

   @ObfuscatedName("y")
   public synchronized void vmethod3777(int[] var1, int var2, int var3) {
      if(this.field1569 == 0 && this.field1575 == 0) {
         this.vmethod3778(var3);
      } else {
         RawSound var4 = (RawSound)super.sound;
         int var5 = this.start << 8;
         int var6 = this.end << 8;
         int var7 = var4.samples.length << 8;
         int var8 = var6 - var5;
         if(var8 <= 0) {
            this.numLoops = 0;
         }

         int var9 = var2;
         var3 += var2;
         if(this.field1578 < 0) {
            if(this.field1568 <= 0) {
               this.method2670();
               this.method3497();
               return;
            }

            this.field1578 = 0;
         }

         if(this.field1578 >= var7) {
            if(this.field1568 >= 0) {
               this.method2670();
               this.method3497();
               return;
            }

            this.field1578 = var7 - 1;
         }

         if(this.numLoops < 0) {
            if(this.field1577) {
               if(this.field1568 < 0) {
                  var9 = this.method2600(var1, var2, var5, var3, var4.samples[this.start]);
                  if(this.field1578 >= var5) {
                     return;
                  }

                  this.field1578 = var5 + var5 - 1 - this.field1578;
                  this.field1568 = -this.field1568;
               }

               while(true) {
                  var9 = this.method2568(var1, var9, var6, var3, var4.samples[this.end - 1]);
                  if(this.field1578 < var6) {
                     return;
                  }

                  this.field1578 = var6 + var6 - 1 - this.field1578;
                  this.field1568 = -this.field1568;
                  var9 = this.method2600(var1, var9, var5, var3, var4.samples[this.start]);
                  if(this.field1578 >= var5) {
                     return;
                  }

                  this.field1578 = var5 + var5 - 1 - this.field1578;
                  this.field1568 = -this.field1568;
               }
            } else if(this.field1568 < 0) {
               while(true) {
                  var9 = this.method2600(var1, var9, var5, var3, var4.samples[this.end - 1]);
                  if(this.field1578 >= var5) {
                     return;
                  }

                  this.field1578 = var6 - 1 - (var6 - 1 - this.field1578) % var8;
               }
            } else {
               while(true) {
                  var9 = this.method2568(var1, var9, var6, var3, var4.samples[this.start]);
                  if(this.field1578 < var6) {
                     return;
                  }

                  this.field1578 = var5 + (this.field1578 - var5) % var8;
               }
            }
         } else {
            if(this.numLoops > 0) {
               if(this.field1577) {
                  label129: {
                     if(this.field1568 < 0) {
                        var9 = this.method2600(var1, var2, var5, var3, var4.samples[this.start]);
                        if(this.field1578 >= var5) {
                           return;
                        }

                        this.field1578 = var5 + var5 - 1 - this.field1578;
                        this.field1568 = -this.field1568;
                        if(--this.numLoops == 0) {
                           break label129;
                        }
                     }

                     do {
                        var9 = this.method2568(var1, var9, var6, var3, var4.samples[this.end - 1]);
                        if(this.field1578 < var6) {
                           return;
                        }

                        this.field1578 = var6 + var6 - 1 - this.field1578;
                        this.field1568 = -this.field1568;
                        if(--this.numLoops == 0) {
                           break;
                        }

                        var9 = this.method2600(var1, var9, var5, var3, var4.samples[this.start]);
                        if(this.field1578 >= var5) {
                           return;
                        }

                        this.field1578 = var5 + var5 - 1 - this.field1578;
                        this.field1568 = -this.field1568;
                     } while(--this.numLoops != 0);
                  }
               } else {
                  int var10;
                  if(this.field1568 < 0) {
                     while(true) {
                        var9 = this.method2600(var1, var9, var5, var3, var4.samples[this.end - 1]);
                        if(this.field1578 >= var5) {
                           return;
                        }

                        var10 = (var6 - 1 - this.field1578) / var8;
                        if(var10 >= this.numLoops) {
                           this.field1578 += var8 * this.numLoops;
                           this.numLoops = 0;
                           break;
                        }

                        this.field1578 += var8 * var10;
                        this.numLoops -= var10;
                     }
                  } else {
                     while(true) {
                        var9 = this.method2568(var1, var9, var6, var3, var4.samples[this.start]);
                        if(this.field1578 < var6) {
                           return;
                        }

                        var10 = (this.field1578 - var5) / var8;
                        if(var10 >= this.numLoops) {
                           this.field1578 -= var8 * this.numLoops;
                           this.numLoops = 0;
                           break;
                        }

                        this.field1578 -= var8 * var10;
                        this.numLoops -= var10;
                     }
                  }
               }
            }

            if(this.field1568 < 0) {
               this.method2600(var1, var9, 0, var3, 0);
               if(this.field1578 < 0) {
                  this.field1578 = -1;
                  this.method2670();
                  this.method3497();
               }
            } else {
               this.method2568(var1, var9, var7, var3, 0);
               if(this.field1578 >= var7) {
                  this.field1578 = var7;
                  this.method2670();
                  this.method3497();
               }
            }

         }
      }
   }

   @ObfuscatedName("a")
   synchronized void method2550(int var1, int var2) {
      this.field1569 = var1;
      this.field1576 = var2;
      this.field1575 = 0;
      this.method2609();
   }

   @ObfuscatedName("ae")
   public boolean method2562() {
      return this.field1575 != 0;
   }

   @ObfuscatedName("ag")
   int method2600(int[] var1, int var2, int var3, int var4, int var5) {
      while(true) {
         if(this.field1575 > 0) {
            int var6 = var2 + this.field1575;
            if(var6 > var4) {
               var6 = var4;
            }

            this.field1575 += var2;
            if(this.field1568 == -256 && (this.field1578 & 255) == 0) {
               if(PcmPlayer.PcmPlayer_stereo) {
                  var2 = method2540(0, ((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1572, this.field1573, this.field1570, this.field1581, 0, var6, var3, this);
               } else {
                  var2 = method2581(((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1580, this.field1567, 0, var6, var3, this);
               }
            } else if(PcmPlayer.PcmPlayer_stereo) {
               var2 = method2615(0, 0, ((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1572, this.field1573, this.field1570, this.field1581, 0, var6, var3, this, this.field1568, var5);
            } else {
               var2 = method2585(0, 0, ((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1580, this.field1567, 0, var6, var3, this, this.field1568, var5);
            }

            this.field1575 -= var2;
            if(this.field1575 != 0) {
               return var2;
            }

            if(!this.method2570()) {
               continue;
            }

            return var4;
         }

         if(this.field1568 == -256 && (this.field1578 & 255) == 0) {
            if(PcmPlayer.PcmPlayer_stereo) {
               return method2574(0, ((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1572, this.field1573, 0, var4, var3, this);
            }

            return method2573(((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1580, 0, var4, var3, this);
         }

         if(PcmPlayer.PcmPlayer_stereo) {
            return method2597(0, 0, ((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1572, this.field1573, 0, var4, var3, this, this.field1568, var5);
         }

         return method2611(0, 0, ((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1580, 0, var4, var3, this, this.field1568, var5);
      }
   }

   @ObfuscatedName("ah")
   int vmethod2542() {
      int var1 = this.field1580 * 3 >> 6;
      var1 = (var1 ^ var1 >> 31) + (var1 >>> 31);
      if(this.numLoops == 0) {
         var1 -= var1 * this.field1578 / (((RawSound)super.sound).samples.length << 8);
      } else if(this.numLoops >= 0) {
         var1 -= var1 * this.start / ((RawSound)super.sound).samples.length;
      }

      return var1 > 255?255:var1;
   }

   @ObfuscatedName("ai")
   public synchronized int method2560() {
      return this.field1568 < 0?-this.field1568:this.field1568;
   }

   @ObfuscatedName("aj")
   boolean method2570() {
      int var1 = this.field1569;
      int var2;
      int var3;
      if(var1 == Integer.MIN_VALUE) {
         var3 = 0;
         var2 = 0;
         var1 = 0;
      } else {
         var2 = method2635(var1, this.field1576);
         var3 = method2541(var1, this.field1576);
      }

      if(var1 == this.field1580 && var2 == this.field1572 && var3 == this.field1573) {
         if(this.field1569 == Integer.MIN_VALUE) {
            this.field1569 = 0;
            this.field1573 = 0;
            this.field1572 = 0;
            this.field1580 = 0;
            this.method3497();
            return true;
         } else {
            this.method2609();
            return false;
         }
      } else {
         if(this.field1580 < var1) {
            this.field1567 = 1;
            this.field1575 = var1 - this.field1580;
         } else if(this.field1580 > var1) {
            this.field1567 = -1;
            this.field1575 = this.field1580 - var1;
         } else {
            this.field1567 = 0;
         }

         if(this.field1572 < var2) {
            this.field1570 = 1;
            if(this.field1575 == 0 || this.field1575 > var2 - this.field1572) {
               this.field1575 = var2 - this.field1572;
            }
         } else if(this.field1572 > var2) {
            this.field1570 = -1;
            if(this.field1575 == 0 || this.field1575 > this.field1572 - var2) {
               this.field1575 = this.field1572 - var2;
            }
         } else {
            this.field1570 = 0;
         }

         if(this.field1573 < var3) {
            this.field1581 = 1;
            if(this.field1575 == 0 || this.field1575 > var3 - this.field1573) {
               this.field1575 = var3 - this.field1573;
            }
         } else if(this.field1573 > var3) {
            this.field1581 = -1;
            if(this.field1575 == 0 || this.field1575 > this.field1573 - var3) {
               this.field1575 = this.field1573 - var3;
            }
         } else {
            this.field1581 = 0;
         }

         return false;
      }
   }

   @ObfuscatedName("ak")
   public boolean method2561() {
      return this.field1578 < 0 || this.field1578 >= ((RawSound)super.sound).samples.length << 8;
   }

   @ObfuscatedName("av")
   int method2568(int[] var1, int var2, int var3, int var4, int var5) {
      while(true) {
         if(this.field1575 > 0) {
            int var6 = var2 + this.field1575;
            if(var6 > var4) {
               var6 = var4;
            }

            this.field1575 += var2;
            if(this.field1568 == 256 && (this.field1578 & 255) == 0) {
               if(PcmPlayer.PcmPlayer_stereo) {
                  var2 = method2580(0, ((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1572, this.field1573, this.field1570, this.field1581, 0, var6, var3, this);
               } else {
                  var2 = method2579(((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1580, this.field1567, 0, var6, var3, this);
               }
            } else if(PcmPlayer.PcmPlayer_stereo) {
               var2 = method2613(0, 0, ((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1572, this.field1573, this.field1570, this.field1581, 0, var6, var3, this, this.field1568, var5);
            } else {
               var2 = method2650(0, 0, ((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1580, this.field1567, 0, var6, var3, this, this.field1568, var5);
            }

            this.field1575 -= var2;
            if(this.field1575 != 0) {
               return var2;
            }

            if(!this.method2570()) {
               continue;
            }

            return var4;
         }

         if(this.field1568 == 256 && (this.field1578 & 255) == 0) {
            if(PcmPlayer.PcmPlayer_stereo) {
               return method2572(0, ((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1572, this.field1573, 0, var4, var3, this);
            }

            return method2571(((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1580, 0, var4, var3, this);
         }

         if(PcmPlayer.PcmPlayer_stereo) {
            return method2576(0, 0, ((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1572, this.field1573, 0, var4, var3, this, this.field1568, var5);
         }

         return method2575(0, 0, ((RawSound)super.sound).samples, var1, this.field1578, var2, this.field1580, 0, var4, var3, this, this.field1568, var5);
      }
   }

   @ObfuscatedName("b")
   public synchronized void method2548(int var1) {
      this.method2550(var1 << 6, this.method2552());
   }

   @ObfuscatedName("c")
   public synchronized void vmethod3778(int var1) {
      if(this.field1575 > 0) {
         if(var1 >= this.field1575) {
            if(this.field1569 == Integer.MIN_VALUE) {
               this.field1569 = 0;
               this.field1573 = 0;
               this.field1572 = 0;
               this.field1580 = 0;
               this.method3497();
               var1 = this.field1575;
            }

            this.field1575 = 0;
            this.method2609();
         } else {
            this.field1580 += this.field1567 * var1;
            this.field1572 += this.field1570 * var1;
            this.field1573 += this.field1581 * var1;
            this.field1575 -= var1;
         }
      }

      RawSound var2 = (RawSound)super.sound;
      int var3 = this.start << 8;
      int var4 = this.end << 8;
      int var5 = var2.samples.length << 8;
      int var6 = var4 - var3;
      if(var6 <= 0) {
         this.numLoops = 0;
      }

      if(this.field1578 < 0) {
         if(this.field1568 <= 0) {
            this.method2670();
            this.method3497();
            return;
         }

         this.field1578 = 0;
      }

      if(this.field1578 >= var5) {
         if(this.field1568 >= 0) {
            this.method2670();
            this.method3497();
            return;
         }

         this.field1578 = var5 - 1;
      }

      this.field1578 += this.field1568 * var1;
      if(this.numLoops < 0) {
         if(!this.field1577) {
            if(this.field1568 < 0) {
               if(this.field1578 >= var3) {
                  return;
               }

               this.field1578 = var4 - 1 - (var4 - 1 - this.field1578) % var6;
            } else {
               if(this.field1578 < var4) {
                  return;
               }

               this.field1578 = var3 + (this.field1578 - var3) % var6;
            }

         } else {
            if(this.field1568 < 0) {
               if(this.field1578 >= var3) {
                  return;
               }

               this.field1578 = var3 + var3 - 1 - this.field1578;
               this.field1568 = -this.field1568;
            }

            while(this.field1578 >= var4) {
               this.field1578 = var4 + var4 - 1 - this.field1578;
               this.field1568 = -this.field1568;
               if(this.field1578 >= var3) {
                  return;
               }

               this.field1578 = var3 + var3 - 1 - this.field1578;
               this.field1568 = -this.field1568;
            }

         }
      } else {
         if(this.numLoops > 0) {
            if(this.field1577) {
               label126: {
                  if(this.field1568 < 0) {
                     if(this.field1578 >= var3) {
                        return;
                     }

                     this.field1578 = var3 + var3 - 1 - this.field1578;
                     this.field1568 = -this.field1568;
                     if(--this.numLoops == 0) {
                        break label126;
                     }
                  }

                  do {
                     if(this.field1578 < var4) {
                        return;
                     }

                     this.field1578 = var4 + var4 - 1 - this.field1578;
                     this.field1568 = -this.field1568;
                     if(--this.numLoops == 0) {
                        break;
                     }

                     if(this.field1578 >= var3) {
                        return;
                     }

                     this.field1578 = var3 + var3 - 1 - this.field1578;
                     this.field1568 = -this.field1568;
                  } while(--this.numLoops != 0);
               }
            } else {
               int var7;
               if(this.field1568 < 0) {
                  if(this.field1578 >= var3) {
                     return;
                  }

                  var7 = (var4 - 1 - this.field1578) / var6;
                  if(var7 < this.numLoops) {
                     this.field1578 += var6 * var7;
                     this.numLoops -= var7;
                     return;
                  }

                  this.field1578 += var6 * this.numLoops;
                  this.numLoops = 0;
               } else {
                  if(this.field1578 < var4) {
                     return;
                  }

                  var7 = (this.field1578 - var3) / var6;
                  if(var7 < this.numLoops) {
                     this.field1578 -= var6 * var7;
                     this.numLoops -= var7;
                     return;
                  }

                  this.field1578 -= var6 * this.numLoops;
                  this.numLoops = 0;
               }
            }
         }

         if(this.field1568 < 0) {
            if(this.field1578 < 0) {
               this.field1578 = -1;
               this.method2670();
               this.method3497();
            }
         } else if(this.field1578 >= var5) {
            this.field1578 = var5;
            this.method2670();
            this.method3497();
         }

      }
   }

   public void setNumLoops(int var1) {
      this.method2547(var1);
   }

   @ObfuscatedName("d")
   public synchronized void method2557(int var1, int var2, int var3) {
      if(var1 == 0) {
         this.method2550(var2, var3);
      } else {
         int var4 = method2635(var2, var3);
         int var5 = method2541(var2, var3);
         if(var4 == this.field1572 && var5 == this.field1573) {
            this.field1575 = 0;
         } else {
            int var6 = var2 - this.field1580;
            if(this.field1580 - var2 > var6) {
               var6 = this.field1580 - var2;
            }

            if(var4 - this.field1572 > var6) {
               var6 = var4 - this.field1572;
            }

            if(this.field1572 - var4 > var6) {
               var6 = this.field1572 - var4;
            }

            if(var5 - this.field1573 > var6) {
               var6 = var5 - this.field1573;
            }

            if(this.field1573 - var5 > var6) {
               var6 = this.field1573 - var5;
            }

            if(var1 > var6) {
               var1 = var6;
            }

            this.field1575 = var1;
            this.field1569 = var2;
            this.field1576 = var3;
            this.field1567 = (var2 - this.field1580) / var1;
            this.field1570 = (var4 - this.field1572) / var1;
            this.field1581 = (var5 - this.field1573) / var1;
         }
      }
   }

   @ObfuscatedName("e")
   public synchronized int method2551() {
      return this.field1569 == Integer.MIN_VALUE?0:this.field1569;
   }

   @ObfuscatedName("f")
   void method2670() {
      if(this.field1575 != 0) {
         if(this.field1569 == Integer.MIN_VALUE) {
            this.field1569 = 0;
         }

         this.field1575 = 0;
         this.method2609();
      }

   }

   @ObfuscatedName("h")
   public synchronized void method2610(int var1) {
      int var2 = ((RawSound)super.sound).samples.length << 8;
      if(var1 < -1) {
         var1 = -1;
      }

      if(var1 > var2) {
         var1 = var2;
      }

      this.field1578 = var1;
   }

   @ObfuscatedName("i")
   public synchronized void method2547(int var1) {
      this.numLoops = var1;
   }

   @ObfuscatedName("j")
   public synchronized void method2556(int var1, int var2) {
      this.method2557(var1, var2, this.method2552());
   }

   @ObfuscatedName("k")
   public synchronized void method2559(int var1) {
      if(this.field1568 < 0) {
         this.field1568 = -var1;
      } else {
         this.field1568 = var1;
      }

   }

   @ObfuscatedName("l")
   public synchronized void method2558(int var1) {
      if(var1 == 0) {
         this.method2549(0);
         this.method3497();
      } else if(this.field1572 == 0 && this.field1573 == 0) {
         this.field1575 = 0;
         this.field1569 = 0;
         this.field1580 = 0;
         this.method3497();
      } else {
         int var2 = -this.field1580;
         if(this.field1580 > var2) {
            var2 = this.field1580;
         }

         if(-this.field1572 > var2) {
            var2 = -this.field1572;
         }

         if(this.field1572 > var2) {
            var2 = this.field1572;
         }

         if(-this.field1573 > var2) {
            var2 = -this.field1573;
         }

         if(this.field1573 > var2) {
            var2 = this.field1573;
         }

         if(var1 > var2) {
            var1 = var2;
         }

         this.field1575 = var1;
         this.field1569 = Integer.MIN_VALUE;
         this.field1567 = -this.field1580 / var1;
         this.field1570 = -this.field1572 / var1;
         this.field1581 = -this.field1573 / var1;
      }
   }

   @ObfuscatedName("m")
   protected int vmethod3787() {
      return this.field1569 == 0 && this.field1575 == 0?0:1;
   }

   @ObfuscatedName("n")
   static int method2541(int var0, int var1) {
      return var1 < 0?-var0:(int)((double)var0 * Math.sqrt((double)var1 * 1.220703125E-4D) + 0.5D);
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(Lch;III)Ldu;"
   )
   public static RawPcmStream method2577(RawSound var0, int var1, int var2, int var3) {
      return var0.samples != null && var0.samples.length != 0?new RawPcmStream(var0, var1, var2, var3):null;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(Lch;II)Ldu;"
   )
   public static RawPcmStream method2601(RawSound var0, int var1, int var2) {
      return var0.samples != null && var0.samples.length != 0?new RawPcmStream(var0, (int)((long)var0.sampleRate * 256L * (long)var1 / (long)(UrlRequest.PcmPlayer_sampleRate * 100)), var2 << 6):null;
   }

   @ObfuscatedName("z")
   static int method2635(int var0, int var1) {
      return var1 < 0?var0:(int)((double)var0 * Math.sqrt((double)(16384 - var1) * 1.220703125E-4D) + 0.5D);
   }

   @ObfuscatedName("af")
   @ObfuscatedSignature(
      signature = "([B[IIIIIIILdu;)I"
   )
   static int method2573(byte[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, RawPcmStream var8) {
      var2 >>= 8;
      var7 >>= 8;
      var4 <<= 2;
      if((var5 = var3 + var2 - (var7 - 1)) > var6) {
         var5 = var6;
      }

      int var10001;
      for(var5 -= 3; var3 < var5; var1[var10001] += var0[var2--] * var4) {
         var10001 = var3++;
         var1[var10001] += var0[var2--] * var4;
         var10001 = var3++;
         var1[var10001] += var0[var2--] * var4;
         var10001 = var3++;
         var1[var10001] += var0[var2--] * var4;
         var10001 = var3++;
      }

      for(var5 += 3; var3 < var5; var1[var10001] += var0[var2--] * var4) {
         var10001 = var3++;
      }

      var8.field1578 = var2 << 8;
      return var3;
   }

   @ObfuscatedName("at")
   @ObfuscatedSignature(
      signature = "(I[B[IIIIIIIILdu;)I"
   )
   static int method2574(int var0, byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, RawPcmStream var10) {
      var3 >>= 8;
      var9 >>= 8;
      var5 <<= 2;
      var6 <<= 2;
      if((var7 = var3 + var4 - (var9 - 1)) > var8) {
         var7 = var8;
      }

      var4 <<= 1;
      var7 <<= 1;

      int var10001;
      byte var11;
      for(var7 -= 6; var4 < var7; var2[var10001] += var11 * var6) {
         var11 = var1[var3--];
         var10001 = var4++;
         var2[var10001] += var11 * var5;
         var10001 = var4++;
         var2[var10001] += var11 * var6;
         var11 = var1[var3--];
         var10001 = var4++;
         var2[var10001] += var11 * var5;
         var10001 = var4++;
         var2[var10001] += var11 * var6;
         var11 = var1[var3--];
         var10001 = var4++;
         var2[var10001] += var11 * var5;
         var10001 = var4++;
         var2[var10001] += var11 * var6;
         var11 = var1[var3--];
         var10001 = var4++;
         var2[var10001] += var11 * var5;
         var10001 = var4++;
      }

      for(var7 += 6; var4 < var7; var2[var10001] += var11 * var6) {
         var11 = var1[var3--];
         var10001 = var4++;
         var2[var10001] += var11 * var5;
         var10001 = var4++;
      }

      var10.field1578 = var3 << 8;
      return var4 >> 1;
   }

   @ObfuscatedName("au")
   @ObfuscatedSignature(
      signature = "(I[B[IIIIIIIILdu;)I"
   )
   static int method2572(int var0, byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, RawPcmStream var10) {
      var3 >>= 8;
      var9 >>= 8;
      var5 <<= 2;
      var6 <<= 2;
      if((var7 = var4 + var9 - var3) > var8) {
         var7 = var8;
      }

      var4 <<= 1;
      var7 <<= 1;

      int var10001;
      byte var11;
      for(var7 -= 6; var4 < var7; var2[var10001] += var11 * var6) {
         var11 = var1[var3++];
         var10001 = var4++;
         var2[var10001] += var11 * var5;
         var10001 = var4++;
         var2[var10001] += var11 * var6;
         var11 = var1[var3++];
         var10001 = var4++;
         var2[var10001] += var11 * var5;
         var10001 = var4++;
         var2[var10001] += var11 * var6;
         var11 = var1[var3++];
         var10001 = var4++;
         var2[var10001] += var11 * var5;
         var10001 = var4++;
         var2[var10001] += var11 * var6;
         var11 = var1[var3++];
         var10001 = var4++;
         var2[var10001] += var11 * var5;
         var10001 = var4++;
      }

      for(var7 += 6; var4 < var7; var2[var10001] += var11 * var6) {
         var11 = var1[var3++];
         var10001 = var4++;
         var2[var10001] += var11 * var5;
         var10001 = var4++;
      }

      var10.field1578 = var3 << 8;
      return var4 >> 1;
   }

   @ObfuscatedName("ay")
   @ObfuscatedSignature(
      signature = "([B[IIIIIIILdu;)I"
   )
   static int method2571(byte[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, RawPcmStream var8) {
      var2 >>= 8;
      var7 >>= 8;
      var4 <<= 2;
      if((var5 = var3 + var7 - var2) > var6) {
         var5 = var6;
      }

      int var10001;
      for(var5 -= 3; var3 < var5; var1[var10001] += var0[var2++] * var4) {
         var10001 = var3++;
         var1[var10001] += var0[var2++] * var4;
         var10001 = var3++;
         var1[var10001] += var0[var2++] * var4;
         var10001 = var3++;
         var1[var10001] += var0[var2++] * var4;
         var10001 = var3++;
      }

      for(var5 += 3; var3 < var5; var1[var10001] += var0[var2++] * var4) {
         var10001 = var3++;
      }

      var8.field1578 = var2 << 8;
      return var3;
   }

   @ObfuscatedName("ba")
   @ObfuscatedSignature(
      signature = "(II[B[IIIIIIIILdu;II)I"
   )
   static int method2585(int var0, int var1, byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, RawPcmStream var11, int var12, int var13) {
      var11.field1572 -= var11.field1570 * var5;
      var11.field1573 -= var11.field1581 * var5;
      if(var12 == 0 || (var8 = var5 + (var10 + 256 - var4 + var12) / var12) > var9) {
         var8 = var9;
      }

      int var10001;
      while(var5 < var8) {
         var1 = var4 >> 8;
         byte var14 = var2[var1 - 1];
         var10001 = var5++;
         var3[var10001] += ((var14 << 8) + (var2[var1] - var14) * (var4 & 255)) * var6 >> 6;
         var6 += var7;
         var4 += var12;
      }

      if(var12 == 0 || (var8 = var5 + (var10 - var4 + var12) / var12) > var9) {
         var8 = var9;
      }

      var0 = var13;

      for(var1 = var12; var5 < var8; var4 += var1) {
         var10001 = var5++;
         var3[var10001] += ((var0 << 8) + (var2[var4 >> 8] - var0) * (var4 & 255)) * var6 >> 6;
         var6 += var7;
      }

      var11.field1572 += var11.field1570 * var5;
      var11.field1573 += var11.field1581 * var5;
      var11.field1580 = var6;
      var11.field1578 = var4;
      return var5;
   }

   @ObfuscatedName("bc")
   @ObfuscatedSignature(
      signature = "(II[B[IIIIIIIILdu;II)I"
   )
   static int method2650(int var0, int var1, byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, RawPcmStream var11, int var12, int var13) {
      var11.field1572 -= var11.field1570 * var5;
      var11.field1573 -= var11.field1581 * var5;
      if(var12 == 0 || (var8 = var5 + (var10 - var4 + var12 - 257) / var12) > var9) {
         var8 = var9;
      }

      byte var14;
      int var10001;
      while(var5 < var8) {
         var1 = var4 >> 8;
         var14 = var2[var1];
         var10001 = var5++;
         var3[var10001] += ((var14 << 8) + (var2[var1 + 1] - var14) * (var4 & 255)) * var6 >> 6;
         var6 += var7;
         var4 += var12;
      }

      if(var12 == 0 || (var8 = var5 + (var10 - var4 + var12 - 1) / var12) > var9) {
         var8 = var9;
      }

      for(var1 = var13; var5 < var8; var4 += var12) {
         var14 = var2[var4 >> 8];
         var10001 = var5++;
         var3[var10001] += ((var14 << 8) + (var1 - var14) * (var4 & 255)) * var6 >> 6;
         var6 += var7;
      }

      var11.field1572 += var11.field1570 * var5;
      var11.field1573 += var11.field1581 * var5;
      var11.field1580 = var6;
      var11.field1578 = var4;
      return var5;
   }

   @ObfuscatedName("bd")
   @ObfuscatedSignature(
      signature = "(II[B[IIIIIIILdu;II)I"
   )
   static int method2611(int var0, int var1, byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var8, int var9, RawPcmStream var10, int var11, int var12) {
      if(var11 == 0 || (var7 = var5 + (var11 + (var9 + 256 - var4)) / var11) > var8) {
         var7 = var8;
      }

      int var10001;
      while(var5 < var7) {
         var1 = var4 >> 8;
         byte var13 = var2[var1 - 1];
         var10001 = var5++;
         var3[var10001] += ((var13 << 8) + (var2[var1] - var13) * (var4 & 255)) * var6 >> 6;
         var4 += var11;
      }

      if(var11 == 0 || (var7 = var5 + (var11 + (var9 - var4)) / var11) > var8) {
         var7 = var8;
      }

      var0 = var12;

      for(var1 = var11; var5 < var7; var4 += var1) {
         var10001 = var5++;
         var3[var10001] += ((var0 << 8) + (var2[var4 >> 8] - var0) * (var4 & 255)) * var6 >> 6;
      }

      var10.field1578 = var4;
      return var5;
   }

   @ObfuscatedName("bh")
   @ObfuscatedSignature(
      signature = "(II[B[IIIIIIIILdu;II)I"
   )
   static int method2576(int var0, int var1, byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, RawPcmStream var11, int var12, int var13) {
      if(var12 == 0 || (var8 = var5 + (var10 - var4 + var12 - 257) / var12) > var9) {
         var8 = var9;
      }

      var5 <<= 1;

      byte var14;
      int var10001;
      for(var8 <<= 1; var5 < var8; var4 += var12) {
         var1 = var4 >> 8;
         var14 = var2[var1];
         var0 = (var14 << 8) + (var4 & 255) * (var2[var1 + 1] - var14);
         var10001 = var5++;
         var3[var10001] += var0 * var6 >> 6;
         var10001 = var5++;
         var3[var10001] += var0 * var7 >> 6;
      }

      if(var12 == 0 || (var8 = (var5 >> 1) + (var10 - var4 + var12 - 1) / var12) > var9) {
         var8 = var9;
      }

      var8 <<= 1;

      for(var1 = var13; var5 < var8; var4 += var12) {
         var14 = var2[var4 >> 8];
         var0 = (var14 << 8) + (var1 - var14) * (var4 & 255);
         var10001 = var5++;
         var3[var10001] += var0 * var6 >> 6;
         var10001 = var5++;
         var3[var10001] += var0 * var7 >> 6;
      }

      var11.field1578 = var4;
      return var5 >> 1;
   }

   @ObfuscatedName("bj")
   @ObfuscatedSignature(
      signature = "(I[B[IIIIIIIIIILdu;)I"
   )
   static int method2580(int var0, byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, RawPcmStream var12) {
      var3 >>= 8;
      var11 >>= 8;
      var5 <<= 2;
      var6 <<= 2;
      var7 <<= 2;
      var8 <<= 2;
      if((var9 = var11 + var4 - var3) > var10) {
         var9 = var10;
      }

      var12.field1580 += var12.field1567 * (var9 - var4);
      var4 <<= 1;
      var9 <<= 1;

      byte var13;
      int var10001;
      for(var9 -= 6; var4 < var9; var6 += var8) {
         var13 = var1[var3++];
         var10001 = var4++;
         var2[var10001] += var13 * var5;
         var5 += var7;
         var10001 = var4++;
         var2[var10001] += var13 * var6;
         var6 += var8;
         var13 = var1[var3++];
         var10001 = var4++;
         var2[var10001] += var13 * var5;
         var5 += var7;
         var10001 = var4++;
         var2[var10001] += var13 * var6;
         var6 += var8;
         var13 = var1[var3++];
         var10001 = var4++;
         var2[var10001] += var13 * var5;
         var5 += var7;
         var10001 = var4++;
         var2[var10001] += var13 * var6;
         var6 += var8;
         var13 = var1[var3++];
         var10001 = var4++;
         var2[var10001] += var13 * var5;
         var5 += var7;
         var10001 = var4++;
         var2[var10001] += var13 * var6;
      }

      for(var9 += 6; var4 < var9; var6 += var8) {
         var13 = var1[var3++];
         var10001 = var4++;
         var2[var10001] += var13 * var5;
         var5 += var7;
         var10001 = var4++;
         var2[var10001] += var13 * var6;
      }

      var12.field1572 = var5 >> 2;
      var12.field1573 = var6 >> 2;
      var12.field1578 = var3 << 8;
      return var4 >> 1;
   }

   @ObfuscatedName("bk")
   @ObfuscatedSignature(
      signature = "(II[B[IIIIIIIIIILdu;II)I"
   )
   static int method2613(int var0, int var1, byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, RawPcmStream var13, int var14, int var15) {
      var13.field1580 -= var5 * var13.field1567;
      if(var14 == 0 || (var10 = var5 + (var12 - var4 + var14 - 257) / var14) > var11) {
         var10 = var11;
      }

      var5 <<= 1;

      byte var16;
      int var10001;
      for(var10 <<= 1; var5 < var10; var4 += var14) {
         var1 = var4 >> 8;
         var16 = var2[var1];
         var0 = (var16 << 8) + (var4 & 255) * (var2[var1 + 1] - var16);
         var10001 = var5++;
         var3[var10001] += var0 * var6 >> 6;
         var6 += var8;
         var10001 = var5++;
         var3[var10001] += var0 * var7 >> 6;
         var7 += var9;
      }

      if(var14 == 0 || (var10 = (var5 >> 1) + (var12 - var4 + var14 - 1) / var14) > var11) {
         var10 = var11;
      }

      var10 <<= 1;

      for(var1 = var15; var5 < var10; var4 += var14) {
         var16 = var2[var4 >> 8];
         var0 = (var16 << 8) + (var1 - var16) * (var4 & 255);
         var10001 = var5++;
         var3[var10001] += var0 * var6 >> 6;
         var6 += var8;
         var10001 = var5++;
         var3[var10001] += var0 * var7 >> 6;
         var7 += var9;
      }

      var5 >>= 1;
      var13.field1580 += var13.field1567 * var5;
      var13.field1572 = var6;
      var13.field1573 = var7;
      var13.field1578 = var4;
      return var5;
   }

   @ObfuscatedName("bm")
   @ObfuscatedSignature(
      signature = "(II[B[IIIIIIIILdu;II)I"
   )
   static int method2597(int var0, int var1, byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, RawPcmStream var11, int var12, int var13) {
      if(var12 == 0 || (var8 = var5 + (var10 + 256 - var4 + var12) / var12) > var9) {
         var8 = var9;
      }

      var5 <<= 1;

      int var10001;
      for(var8 <<= 1; var5 < var8; var4 += var12) {
         var1 = var4 >> 8;
         byte var14 = var2[var1 - 1];
         var0 = (var2[var1] - var14) * (var4 & 255) + (var14 << 8);
         var10001 = var5++;
         var3[var10001] += var0 * var6 >> 6;
         var10001 = var5++;
         var3[var10001] += var0 * var7 >> 6;
      }

      if(var12 == 0 || (var8 = (var5 >> 1) + (var10 - var4 + var12) / var12) > var9) {
         var8 = var9;
      }

      var8 <<= 1;

      for(var1 = var13; var5 < var8; var4 += var12) {
         var0 = (var1 << 8) + (var4 & 255) * (var2[var4 >> 8] - var1);
         var10001 = var5++;
         var3[var10001] += var0 * var6 >> 6;
         var10001 = var5++;
         var3[var10001] += var0 * var7 >> 6;
      }

      var11.field1578 = var4;
      return var5 >> 1;
   }

   @ObfuscatedName("bn")
   @ObfuscatedSignature(
      signature = "(II[B[IIIIIIIIIILdu;II)I"
   )
   static int method2615(int var0, int var1, byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, RawPcmStream var13, int var14, int var15) {
      var13.field1580 -= var5 * var13.field1567;
      if(var14 == 0 || (var10 = var5 + (var12 + 256 - var4 + var14) / var14) > var11) {
         var10 = var11;
      }

      var5 <<= 1;

      int var10001;
      for(var10 <<= 1; var5 < var10; var4 += var14) {
         var1 = var4 >> 8;
         byte var16 = var2[var1 - 1];
         var0 = (var2[var1] - var16) * (var4 & 255) + (var16 << 8);
         var10001 = var5++;
         var3[var10001] += var0 * var6 >> 6;
         var6 += var8;
         var10001 = var5++;
         var3[var10001] += var0 * var7 >> 6;
         var7 += var9;
      }

      if(var14 == 0 || (var10 = (var5 >> 1) + (var12 - var4 + var14) / var14) > var11) {
         var10 = var11;
      }

      var10 <<= 1;

      for(var1 = var15; var5 < var10; var4 += var14) {
         var0 = (var1 << 8) + (var4 & 255) * (var2[var4 >> 8] - var1);
         var10001 = var5++;
         var3[var10001] += var0 * var6 >> 6;
         var6 += var8;
         var10001 = var5++;
         var3[var10001] += var0 * var7 >> 6;
         var7 += var9;
      }

      var5 >>= 1;
      var13.field1580 += var13.field1567 * var5;
      var13.field1572 = var6;
      var13.field1573 = var7;
      var13.field1578 = var4;
      return var5;
   }

   @ObfuscatedName("bs")
   @ObfuscatedSignature(
      signature = "([B[IIIIIIIILdu;)I"
   )
   static int method2581(byte[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, RawPcmStream var9) {
      var2 >>= 8;
      var8 >>= 8;
      var4 <<= 2;
      var5 <<= 2;
      if((var6 = var3 + var2 - (var8 - 1)) > var7) {
         var6 = var7;
      }

      var9.field1572 += var9.field1570 * (var6 - var3);
      var9.field1573 += var9.field1581 * (var6 - var3);

      int var10001;
      for(var6 -= 3; var3 < var6; var4 += var5) {
         var10001 = var3++;
         var1[var10001] += var0[var2--] * var4;
         var4 += var5;
         var10001 = var3++;
         var1[var10001] += var0[var2--] * var4;
         var4 += var5;
         var10001 = var3++;
         var1[var10001] += var0[var2--] * var4;
         var4 += var5;
         var10001 = var3++;
         var1[var10001] += var0[var2--] * var4;
      }

      for(var6 += 3; var3 < var6; var4 += var5) {
         var10001 = var3++;
         var1[var10001] += var0[var2--] * var4;
      }

      var9.field1580 = var4 >> 2;
      var9.field1578 = var2 << 8;
      return var3;
   }

   @ObfuscatedName("bv")
   @ObfuscatedSignature(
      signature = "([B[IIIIIIIILdu;)I"
   )
   static int method2579(byte[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, RawPcmStream var9) {
      var2 >>= 8;
      var8 >>= 8;
      var4 <<= 2;
      var5 <<= 2;
      if((var6 = var3 + var8 - var2) > var7) {
         var6 = var7;
      }

      var9.field1572 += var9.field1570 * (var6 - var3);
      var9.field1573 += var9.field1581 * (var6 - var3);

      int var10001;
      for(var6 -= 3; var3 < var6; var4 += var5) {
         var10001 = var3++;
         var1[var10001] += var0[var2++] * var4;
         var4 += var5;
         var10001 = var3++;
         var1[var10001] += var0[var2++] * var4;
         var4 += var5;
         var10001 = var3++;
         var1[var10001] += var0[var2++] * var4;
         var4 += var5;
         var10001 = var3++;
         var1[var10001] += var0[var2++] * var4;
      }

      for(var6 += 3; var3 < var6; var4 += var5) {
         var10001 = var3++;
         var1[var10001] += var0[var2++] * var4;
      }

      var9.field1580 = var4 >> 2;
      var9.field1578 = var2 << 8;
      return var3;
   }

   @ObfuscatedName("bx")
   @ObfuscatedSignature(
      signature = "(II[B[IIIIIIILdu;II)I"
   )
   static int method2575(int var0, int var1, byte[] var2, int[] var3, int var4, int var5, int var6, int var7, int var8, int var9, RawPcmStream var10, int var11, int var12) {
      if(var11 == 0 || (var7 = var5 + (var11 + (var9 - var4) - 257) / var11) > var8) {
         var7 = var8;
      }

      byte var13;
      int var10001;
      while(var5 < var7) {
         var1 = var4 >> 8;
         var13 = var2[var1];
         var10001 = var5++;
         var3[var10001] += ((var13 << 8) + (var2[var1 + 1] - var13) * (var4 & 255)) * var6 >> 6;
         var4 += var11;
      }

      if(var11 == 0 || (var7 = var5 + (var11 + (var9 - var4) - 1) / var11) > var8) {
         var7 = var8;
      }

      for(var1 = var12; var5 < var7; var4 += var11) {
         var13 = var2[var4 >> 8];
         var10001 = var5++;
         var3[var10001] += ((var13 << 8) + (var1 - var13) * (var4 & 255)) * var6 >> 6;
      }

      var10.field1578 = var4;
      return var5;
   }

   @ObfuscatedName("bz")
   @ObfuscatedSignature(
      signature = "(I[B[IIIIIIIIIILdu;)I"
   )
   static int method2540(int var0, byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, RawPcmStream var12) {
      var3 >>= 8;
      var11 >>= 8;
      var5 <<= 2;
      var6 <<= 2;
      var7 <<= 2;
      var8 <<= 2;
      if((var9 = var3 + var4 - (var11 - 1)) > var10) {
         var9 = var10;
      }

      var12.field1580 += var12.field1567 * (var9 - var4);
      var4 <<= 1;
      var9 <<= 1;

      byte var13;
      int var10001;
      for(var9 -= 6; var4 < var9; var6 += var8) {
         var13 = var1[var3--];
         var10001 = var4++;
         var2[var10001] += var13 * var5;
         var5 += var7;
         var10001 = var4++;
         var2[var10001] += var13 * var6;
         var6 += var8;
         var13 = var1[var3--];
         var10001 = var4++;
         var2[var10001] += var13 * var5;
         var5 += var7;
         var10001 = var4++;
         var2[var10001] += var13 * var6;
         var6 += var8;
         var13 = var1[var3--];
         var10001 = var4++;
         var2[var10001] += var13 * var5;
         var5 += var7;
         var10001 = var4++;
         var2[var10001] += var13 * var6;
         var6 += var8;
         var13 = var1[var3--];
         var10001 = var4++;
         var2[var10001] += var13 * var5;
         var5 += var7;
         var10001 = var4++;
         var2[var10001] += var13 * var6;
      }

      for(var9 += 6; var4 < var9; var6 += var8) {
         var13 = var1[var3--];
         var10001 = var4++;
         var2[var10001] += var13 * var5;
         var5 += var7;
         var10001 = var4++;
         var2[var10001] += var13 * var6;
      }

      var12.field1572 = var5 >> 2;
      var12.field1573 = var6 >> 2;
      var12.field1578 = var3 << 8;
      return var4 >> 1;
   }
}
