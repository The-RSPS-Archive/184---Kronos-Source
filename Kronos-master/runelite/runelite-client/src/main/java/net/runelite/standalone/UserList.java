package net.runelite.standalone;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSUser;
import net.runelite.rs.api.RSUserList;
import net.runelite.rs.api.RSUsername;

@ObfuscatedName("jb")
public abstract class UserList<T extends RSUser> implements RSUserList<T> {
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "[Ljh;"
   )
   User[] array;
   @ObfuscatedName("q")
   HashMap usernamesMap;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1842381401
   )
   int size;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 2041615959
   )
   final int capacity;
   @ObfuscatedName("y")
   Comparator comparator;
   @ObfuscatedName("m")
   HashMap previousUsernamesMap;

   UserList(int var1) {
      this.size = 0;
      this.comparator = null;
      this.capacity = var1;
      this.array = this.vmethod5186(var1);
      this.usernamesMap = new HashMap(var1 / 8);
      this.previousUsernamesMap = new HashMap(var1 / 8);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(II)[Ljh;",
      garbageValue = "951526901"
   )
   abstract User[] vmethod5186(int var1);

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "905555090"
   )
   public boolean method4822() {
      return this.capacity == this.size;
   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "(Ljq;I)Z",
      garbageValue = "396177459"
   )
   public final boolean method4802(Username var1) {
      User var2 = this.method4815(var1);
      if(var2 == null) {
         return false;
      } else {
         this.method4842(var2);
         return true;
      }
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "(Ljq;B)Ljh;",
      garbageValue = "-66"
   )
   User method4815(Username var1) {
      return !var1.method4991()?null:(User)this.usernamesMap.get(var1);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)Ljh;",
      garbageValue = "2"
   )
   abstract User vmethod5179();

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(Ljq;I)Z",
      garbageValue = "154884591"
   )
   public boolean method4770(Username var1) {
      return !var1.method4991()?false:(this.usernamesMap.containsKey(var1)?true:this.previousUsernamesMap.containsKey(var1));
   }

   @ObfuscatedName("aa")
   @ObfuscatedSignature(
      signature = "(Ljh;B)I",
      garbageValue = "4"
   )
   final int method4787(User var1) {
      for(int var2 = 0; var2 < this.size; ++var2) {
         if(this.array[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   @ObfuscatedName("ab")
   @ObfuscatedSignature(
      signature = "(Ljh;I)V",
      garbageValue = "-1752297517"
   )
   final void method4783(User var1) {
      this.usernamesMap.put(var1.username, var1);
      if(var1.previousUsername != null) {
         User var2 = (User)this.previousUsernamesMap.put(var1.previousUsername, var1);
         if(var2 != null && var2 != var1) {
            var2.previousUsername = null;
         }
      }

   }

   @ObfuscatedName("ac")
   @ObfuscatedSignature(
      signature = "(Ljh;Ljq;Ljq;B)V",
      garbageValue = "24"
   )
   final void method4780(User var1, Username var2, Username var3) {
      this.method4782(var1);
      var1.method4882(var2, var3);
      this.method4783(var1);
   }

   @ObfuscatedName("ao")
   @ObfuscatedSignature(
      signature = "(Ljava/util/Comparator;B)V",
      garbageValue = "59"
   )
   public final void method4789(Comparator var1) {
      if(this.comparator == null) {
         this.comparator = var1;
      } else if(this.comparator instanceof AbstractUserComparator) {
         ((AbstractUserComparator)this.comparator).method5007(var1);
      }

   }

   @ObfuscatedName("ap")
   @ObfuscatedSignature(
      signature = "(Ljh;I)V",
      garbageValue = "-1535395374"
   )
   final void method4782(User var1) {
      if(this.usernamesMap.remove(var1.username) == null) {
         throw new IllegalStateException();
      } else {
         if(var1.previousUsername != null) {
            this.previousUsernamesMap.remove(var1.previousUsername);
         }

      }
   }

   @ObfuscatedName("ar")
   @ObfuscatedSignature(
      signature = "(Ljh;B)V",
      garbageValue = "3"
   )
   final void method4776(User var1) {
      this.array[++this.size - 1] = var1;
   }

   @ObfuscatedName("as")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "2"
   )
   public final void method4788() {
      this.comparator = null;
   }

   @ObfuscatedName("ax")
   @ObfuscatedSignature(
      signature = "(IB)V",
      garbageValue = "36"
   )
   final void method4785(int var1) {
      --this.size;
      if(var1 < this.size) {
         System.arraycopy(this.array, var1 + 1, this.array, var1, this.size - var1);
      }

   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "377492255"
   )
   public int method4800() {
      return this.size;
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1173231376"
   )
   public void method4767() {
      this.size = 0;
      Arrays.fill(this.array, (Object)null);
      this.usernamesMap.clear();
      this.previousUsernamesMap.clear();
   }

   public void rl$add(RSUsername var1, RSUsername var2) {
   }

   public void rl$remove(RSUser var1) {
   }

   public void remove(RSUser var1) {
      this.rl$remove(var1);
   }

   public T findByName(RSUsername var1) {
      return (T) this.method4771((Username)var1);
   }

   public void add(RSUsername var1, RSUsername var2) {
      this.rl$add(var1, var2);
   }

   public T[] getNameables() {
      return (T[]) this.array;
   }

   public int getCount() {
      return this.method4800();
   }

   public boolean isMember(RSUsername var1) {
      return this.method4770((Username)var1);
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      signature = "(Ljq;Ljq;S)Ljh;",
      garbageValue = "255"
   )
   User method4777(Username var1, Username var2) {
      if(this.method4815(var1) != null) {
         throw new IllegalStateException();
      } else {
         User var3 = this.vmethod5179();
         var3.method4882(var1, var2);
         this.method4776(var3);
         this.method4783(var3);
         this.add(var1, var2);
         return var3;
      }
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(Ljq;I)Ljh;",
      garbageValue = "1786549701"
   )
   public User method4771(Username var1) {
      User var2 = this.method4815(var1);
      return var2 != null?var2:this.method4773(var1);
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      signature = "(Ljh;I)V",
      garbageValue = "-295119714"
   )
   final void method4842(User var1) {
      this.remove(var1);
      int var2 = this.method4787(var1);
      if(var2 != -1) {
         this.method4785(var2);
         this.method4782(var1);
      }
   }

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      signature = "(Ljq;I)Ljh;",
      garbageValue = "-105614622"
   )
   User method4773(Username var1) {
      return !var1.method4991()?null:(User)this.previousUsernamesMap.get(var1);
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      signature = "(Ljq;I)Ljh;",
      garbageValue = "1185457515"
   )
   User method4833(Username var1) {
      return this.method4777(var1, (Username)null);
   }

   @ObfuscatedName("k")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1739744850"
   )
   public final void method4766() {
      if(this.comparator == null) {
         Arrays.sort(this.array, 0, this.size);
      } else {
         Arrays.sort(this.array, 0, this.size, this.comparator);
      }

   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "(II)Ljh;",
      garbageValue = "1999748501"
   )
   public final User method4778(int var1) {
      if(var1 >= 0 && var1 < this.size) {
         return this.array[var1];
      } else {
         throw new ArrayIndexOutOfBoundsException(var1);
      }
   }
}
