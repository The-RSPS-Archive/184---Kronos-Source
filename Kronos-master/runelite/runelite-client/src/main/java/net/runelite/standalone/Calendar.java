package net.runelite.standalone;

import java.util.TimeZone;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gz")
public class Calendar {
   @ObfuscatedName("n")
   public static final String[] DAYS_OF_THE_WEEK;
   @ObfuscatedName("v")
   public static java.util.Calendar Calendar_calendar;
   @ObfuscatedName("z")
   public static final String[][] MONTH_NAMES_ENGLISH_GERMAN;

   static {
      MONTH_NAMES_ENGLISH_GERMAN = new String[][]{{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}, {"Jan", "Feb", "Mär", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"}, {"jan", "fév", "mars", "avr", "mai", "juin", "juil", "août", "sept", "oct", "nov", "déc"}, {"jan", "fev", "mar", "abr", "mai", "jun", "jul", "ago", "set", "out", "nov", "dez"}, {"jan", "feb", "mrt", "apr", "mei", "jun", "jul", "aug", "sep", "okt", "nov", "dec"}, {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}, {"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"}};
      DAYS_OF_THE_WEEK = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
      java.util.Calendar.getInstance();
      Calendar_calendar = java.util.Calendar.getInstance(TimeZone.getTimeZone("GMT"));
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(Lho;I[B[BB)V",
      garbageValue = "-75"
   )
   static final void method3880(Widget var0, int var1, byte[] var2, byte[] var3) {
      if(var0.field2585 == null) {
         if(var2 == null) {
            return;
         }

         var0.field2585 = new byte[11][];
         var0.field2654 = new byte[11][];
         var0.field2619 = new int[11];
         var0.field2581 = new int[11];
      }

      var0.field2585[var1] = var2;
      if(var2 != null) {
         var0.field2652 = true;
      } else {
         var0.field2652 = false;

         for(int var4 = 0; var4 < var0.field2585.length; ++var4) {
            if(var0.field2585[var4] != null) {
               var0.field2652 = true;
               break;
            }
         }
      }

      var0.field2654[var1] = var3;
   }
}
