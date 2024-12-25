package net.runelite.standalone;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.ImageObserver;
import java.net.URL;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSGameShell;

@ObfuscatedName("bh")
public abstract class GameShell extends Applet implements Runnable, FocusListener, WindowListener, RSGameShell {
   @ObfuscatedName("rx")
   @ObfuscatedGetter(
      intValue = -387184163
   )
   static int field464;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Lbh;"
   )
   static GameShell gameShell;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = 1528666585
   )
   static int gameCyclesToDo;
   @ObfuscatedName("r")
   static boolean isKilled;
   @ObfuscatedName("t")
   static long[] clientTickTimes;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      longValue = -6691432732698375703L
   )
   static long stopTimeMs;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 1510935923
   )
   static int GameShell_redundantStartThreadCount;
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "Lfm;"
   )
   protected static TaskHandler taskHandler;
   @ObfuscatedName("a")
   static long[] graphicsTickTimes;
   @ObfuscatedName("aj")
   @ObfuscatedGetter(
      longValue = -3573600455328537839L
   )
   static long garbageCollectorLastCollectionTime;
   @ObfuscatedName("ao")
   @ObfuscatedGetter(
      intValue = -1408016773
   )
   static int field452;
   @ObfuscatedName("av")
   static volatile boolean volatileFocus;
   @ObfuscatedName("ay")
   @ObfuscatedGetter(
      longValue = -7014184548821540431L
   )
   static long garbageCollectorLastCheckTimeMs;
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "Lfz;"
   )
   static Clock clock;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 1818100921
   )
   protected static int fps;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = -1198168777
   )
   static int fiveOrOne;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 1545406367
   )
   static int cycleDurationMillis;
   @ObfuscatedName("p")
   boolean hasErrored;
   @ObfuscatedName("s")
   @ObfuscatedGetter(
      intValue = -1513929399
   )
   protected int contentHeight;
   @ObfuscatedName("ab")
   java.awt.Canvas canvas;
   @ObfuscatedName("ac")
   @ObfuscatedGetter(
      intValue = -569680121
   )
   int field446;
   @ObfuscatedName("ad")
   volatile boolean isCanvasInvalid;
   @ObfuscatedName("ae")
   final EventQueue eventQueue;
   @ObfuscatedName("al")
   boolean resizeCanvasNextFrame;
   @ObfuscatedName("am")
   Clipboard clipboard;
   @ObfuscatedName("an")
   @ObfuscatedGetter(
      longValue = -4063357795447176907L
   )
   volatile long field455;
   @ObfuscatedName("aq")
   @ObfuscatedSignature(
      signature = "Lav;"
   )
   MouseWheelHandler mouseWheelHandler;
   @ObfuscatedName("ar")
   Frame frame;
   @ObfuscatedName("aw")
   @ObfuscatedGetter(
      intValue = -587207601
   )
   int maxCanvasHeight;
   @ObfuscatedName("ax")
   volatile boolean field429;
   @ObfuscatedName("az")
   @ObfuscatedGetter(
      intValue = -1457123993
   )
   int maxCanvasWidth;
   public Thread thread;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = 618502281
   )
   int canvasX;
   @ObfuscatedName("h")
   @ObfuscatedGetter(
      intValue = -1930363707
   )
   protected int contentWidth;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = -1744199249
   )
   int canvasY;
   @ObfuscatedName("k")
   @ObfuscatedGetter(
      intValue = 2108655365
   )
   int field445;

   static {
      gameShell = null;
      GameShell_redundantStartThreadCount = 0;
      stopTimeMs = 0L;
      isKilled = false;
      cycleDurationMillis = 20;
      fiveOrOne = 1;
      fps = 0;
      graphicsTickTimes = new long[32];
      clientTickTimes = new long[32];
      field452 = 500;
      volatileFocus = true;
      garbageCollectorLastCollectionTime = -1L;
      garbageCollectorLastCheckTimeMs = -1L;
   }

   protected GameShell() {
      this.hasErrored = false;
      this.canvasX = 0;
      this.canvasY = 0;
      this.field429 = true;
      this.resizeCanvasNextFrame = false;
      this.isCanvasInvalid = false;
      this.onReplaceCanvasNextFrameChanged(-1);
      this.field455 = 0L;
      EventQueue var1 = null;

      try {
         var1 = Toolkit.getDefaultToolkit().getSystemEventQueue();
      } catch (Throwable var3) {
         ;
      }

      this.eventQueue = var1;
      WorldMapDecoration.method5193(new DevicePcmPlayerProvider());
   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1270672810"
   )
   void method998() {
      int var1 = this.canvasX;
      int var2 = this.canvasY;
      int var3 = this.contentWidth - FloorDecoration.canvasWidth - var1;
      int var4 = this.contentHeight - WallDecoration.canvasHeight - var2;
      if(var1 > 0 || var3 > 0 || var2 > 0 || var4 > 0) {
         try {
            Container var5 = this.method985();
            int var6 = 0;
            int var7 = 0;
            if(var5 == this.frame) {
               Insets var8 = this.frame.getInsets();
               var6 = var8.left;
               var7 = var8.top;
            }

            Graphics var10 = var5.getGraphics();
            var10.setColor(Color.black);
            if(var1 > 0) {
               var10.fillRect(var6, var7, var1, this.contentHeight);
            }

            if(var2 > 0) {
               var10.fillRect(var6, var7, this.contentWidth, var2);
            }

            if(var3 > 0) {
               var10.fillRect(var6 + this.contentWidth - var3, var7, var3, this.contentHeight);
            }

            if(var4 > 0) {
               var10.fillRect(var6, var7 + this.contentHeight - var4, this.contentWidth, var4);
            }
         } catch (Exception var9) {
            ;
         }
      }

   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(I)Lft;",
      garbageValue = "-133806221"
   )
   protected MouseWheel method957() {
      if(this.mouseWheelHandler == null) {
         this.mouseWheelHandler = new MouseWheelHandler();
         this.mouseWheelHandler.method734(this.canvas);
      }

      return this.mouseWheelHandler;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-2040782454"
   )
   protected void method1039() {
      this.clipboard = this.getToolkit().getSystemClipboard();
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/Object;I)V",
      garbageValue = "669071767"
   )
   final void method956(Object var1) {
      this.onPost(var1);
      if(this.eventQueue != null) {
         for(int var2 = 0; var2 < 50 && this.eventQueue.peekEvent() != null; ++var2) {
            try {
               Thread.sleep(1L);
            } catch (InterruptedException var4) {
               ;
            }
         }

         if(var1 != null) {
            this.eventQueue.postEvent(new ActionEvent(var1, 1001, "dummy"));
         }

      }
   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1775618326"
   )
   void method976() {
      long var1 = class33.method680();
      long var3 = clientTickTimes[WorldMapID.clientTickTimeIdx];
      clientTickTimes[WorldMapID.clientTickTimeIdx] = var1;
      WorldMapID.clientTickTimeIdx = WorldMapID.clientTickTimeIdx + 1 & 31;
      if(0L != var3 && var1 > var3) {
         ;
      }

      synchronized(this) {
         TaskHandler.hasFocus = volatileFocus;
      }

      this.vmethod1937();
   }

   @ObfuscatedName("u")
   protected final void method955(int var1, int var2, int var3) {
      if(!ViewportMouse.client.isStretchedEnabled() || !ViewportMouse.client.isResized()) {
         this.copy$setMaxCanvasSize(var1, var2, var3);
      }
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "(S)V",
      garbageValue = "338"
   )
   final synchronized void method966() {
      Container var1 = this.method985();
      if(this.canvas != null) {
         this.canvas.removeFocusListener(this);
         var1.remove(this.canvas);
      }

      FloorDecoration.canvasWidth = Math.max(var1.getWidth(), this.field445);
      Client.canvasWidthChanged(-1);
      WallDecoration.canvasHeight = Math.max(var1.getHeight(), this.field446);
      Client.canvasHeightChanged(-1);
      Insets var2;
      if(this.frame != null) {
         var2 = this.frame.getInsets();
         FloorDecoration.canvasWidth -= var2.right + var2.left;
         Client.canvasWidthChanged(-1);
         WallDecoration.canvasHeight -= var2.bottom + var2.top;
         Client.canvasHeightChanged(-1);
      }

      this.canvas = new Canvas(this);
      var1.setBackground(Color.BLACK);
      var1.setLayout((LayoutManager)null);
      var1.add(this.canvas);
      this.canvas.setSize(FloorDecoration.canvasWidth, WallDecoration.canvasHeight);
      this.canvas.setVisible(true);
      this.canvas.setBackground(Color.BLACK);
      if(var1 == this.frame) {
         var2 = this.frame.getInsets();
         this.canvas.setLocation(var2.left + this.canvasX, this.canvasY + var2.top);
      } else {
         this.canvas.setLocation(this.canvasX, this.canvasY);
      }

      this.canvas.addFocusListener(this);
      this.canvas.requestFocus();
      this.field429 = true;
      if(class30.rasterProvider != null && FloorDecoration.canvasWidth == class30.rasterProvider.width && WallDecoration.canvasHeight == class30.rasterProvider.height) {
         ((RasterProvider)class30.rasterProvider).method1421(this.canvas);
         class30.rasterProvider.vmethod6275(0, 0);
      } else {
         class30.rasterProvider = new RasterProvider(FloorDecoration.canvasWidth, WallDecoration.canvasHeight, this.canvas);
      }

      this.isCanvasInvalid = false;
      this.onReplaceCanvasNextFrameChanged(-1);
      this.field455 = class33.method680();
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "1635814939"
   )
   protected final void method959() {
      Skills.method3958();
      java.awt.Canvas var1 = this.canvas;
      var1.setFocusTraversalKeysEnabled(false);
      var1.addKeyListener(KeyHandler.KeyHandler_instance);
      var1.addFocusListener(KeyHandler.KeyHandler_instance);
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(S)V",
      garbageValue = "147"
   )
   final void method1083() {
      class78.method1577(this.canvas);
      CollisionMap.method3368(this.canvas);
      if(this.mouseWheelHandler != null) {
         this.mouseWheelHandler.method733(this.canvas);
      }

      this.method966();
      java.awt.Canvas var1 = this.canvas;
      var1.setFocusTraversalKeysEnabled(false);
      var1.addKeyListener(KeyHandler.KeyHandler_instance);
      var1.addFocusListener(KeyHandler.KeyHandler_instance);
      class22.method455(this.canvas);
      if(this.mouseWheelHandler != null) {
         this.mouseWheelHandler.method734(this.canvas);
      }

      this.method968();
   }

   @ObfuscatedName("ab")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-60"
   )
   protected abstract void vmethod1937();

   @ObfuscatedName("ad")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;B)V",
      garbageValue = "0"
   )
   protected void method984(String var1) {
      if(!this.hasErrored) {
         this.hasErrored = true;
         System.out.println("error_game_" + var1);

         try {
            this.getAppletContext().showDocument(new URL(this.getCodeBase(), "error_game_" + var1 + ".ws"), "_self");
         } catch (Exception var3) {
            ;
         }

      }
   }

   @ObfuscatedName("ai")
   @ObfuscatedSignature(
      signature = "(B)Lll;",
      garbageValue = "0"
   )
   Bounds method986() {
      Container var1 = this.method985();
      int var2 = Math.max(var1.getWidth(), this.field445);
      int var3 = Math.max(var1.getHeight(), this.field446);
      if(this.frame != null) {
         Insets var4 = this.frame.getInsets();
         var2 -= var4.left + var4.right;
         var3 -= var4.top + var4.bottom;
      }

      return new Bounds(var2, var3);
   }

   @ObfuscatedName("ak")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "97221829"
   )
   protected final boolean method987() {
      return this.frame != null;
   }

   @ObfuscatedName("al")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "2129811981"
   )
   protected final void method1037() {
      class86.field1133 = null;
      SequenceDefinition.fontHelvetica13 = null;
      FloorOverlayDefinition.loginScreenFontMetrics = null;
   }

   @ObfuscatedName("am")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-2052837160"
   )
   protected abstract void vmethod1643();

   @ObfuscatedName("an")
   @ObfuscatedSignature(
      signature = "(B)Ljava/awt/Container;",
      garbageValue = "-44"
   )
   Container method985() {
      return (Container)(this.frame != null?this.frame:this);
   }

   @ObfuscatedName("ao")
   @ObfuscatedSignature(
      signature = "(ILjava/lang/String;ZB)V",
      garbageValue = "-67"
   )
   protected final void method1082(int var1, String var2, boolean var3) {
      try {
         Graphics var4 = this.canvas.getGraphics();
         if(SequenceDefinition.fontHelvetica13 == null) {
            SequenceDefinition.fontHelvetica13 = new java.awt.Font("Helvetica", 1, 13);
            FloorOverlayDefinition.loginScreenFontMetrics = this.canvas.getFontMetrics(SequenceDefinition.fontHelvetica13);
         }

         if(var3) {
            var4.setColor(Color.black);
            var4.fillRect(0, 0, FloorDecoration.canvasWidth, WallDecoration.canvasHeight);
         }

         Color var5 = new Color(140, 17, 17);

         try {
            if(class86.field1133 == null) {
               class86.field1133 = this.canvas.createImage(304, 34);
            }

            Graphics var6 = class86.field1133.getGraphics();
            var6.setColor(var5);
            var6.drawRect(0, 0, 303, 33);
            var6.fillRect(2, 2, var1 * 3, 30);
            var6.setColor(Color.black);
            var6.drawRect(1, 1, 301, 31);
            var6.fillRect(var1 * 3 + 2, 2, 300 - var1 * 3, 30);
            var6.setFont(SequenceDefinition.fontHelvetica13);
            var6.setColor(Color.white);
            var6.drawString(var2, (304 - FloorOverlayDefinition.loginScreenFontMetrics.stringWidth(var2)) / 2, 22);
            var4.drawImage(class86.field1133, FloorDecoration.canvasWidth / 2 - 152, WallDecoration.canvasHeight / 2 - 18, (ImageObserver)null);
         } catch (Exception var9) {
            int var7 = FloorDecoration.canvasWidth / 2 - 152;
            int var8 = WallDecoration.canvasHeight / 2 - 18;
            var4.setColor(var5);
            var4.drawRect(var7, var8, 303, 33);
            var4.fillRect(var7 + 2, var8 + 2, var1 * 3, 30);
            var4.setColor(Color.black);
            var4.drawRect(var7 + 1, var8 + 1, 301, 31);
            var4.fillRect(var1 * 3 + var7 + 2, var8 + 2, 300 - var1 * 3, 30);
            var4.setFont(SequenceDefinition.fontHelvetica13);
            var4.setColor(Color.white);
            var4.drawString(var2, var7 + (304 - FloorOverlayDefinition.loginScreenFontMetrics.stringWidth(var2)) / 2, var8 + 22);
         }
      } catch (Exception var10) {
         this.canvas.repaint();
      }

   }

   @ObfuscatedName("ar")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "16"
   )
   protected abstract void vmethod1646();

   @ObfuscatedName("as")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "87"
   )
   protected abstract void vmethod1699();

   @ObfuscatedName("ax")
   @ObfuscatedSignature(
      signature = "(ZI)V",
      garbageValue = "-1679134607"
   )
   protected abstract void vmethod1732(boolean var1);

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "2027797509"
   )
   protected abstract void vmethod1819();

   @ObfuscatedName("c")
   final void method961(int var1) {
      if(ViewportMouse.client.isStretchedEnabled()) {
         ViewportMouse.client.invalidateStretching(false);
         if(ViewportMouse.client.isResized()) {
            Dimension var2 = ViewportMouse.client.getRealDimensions();
            this.setMaxCanvasWidth(var2.width);
            this.setMaxCanvasHeight(var2.height);
         }
      }

      this.copy$resizeCanvas(var1);
   }

   public final void destroy() {
      if(this == gameShell && !isKilled) {
         stopTimeMs = class33.method680();
         long var1 = 4999L;

         try {
            Thread.sleep(var1);
         } catch (InterruptedException var5) {
            ;
         }

         try {
            Thread.sleep(1L);
         } catch (InterruptedException var4) {
            ;
         }

         this.method972();
      }
   }

   public final synchronized void paint(Graphics var1) {
      if(this == gameShell && !isKilled) {
         this.field429 = true;
         if(class33.method680() - this.field455 > 1000L) {
            Rectangle var2 = var1.getClipBounds();
            if(var2 == null || var2.width >= FloorDecoration.canvasWidth && var2.height >= WallDecoration.canvasHeight) {
               this.isCanvasInvalid = true;
               this.onReplaceCanvasNextFrameChanged(-1);
            }
         }

      }
   }

   public void onRun() {
      this.thread = Thread.currentThread();
      this.thread.setName("Client");
   }

   public void onFocusGained(FocusEvent var1) {
      FocusChanged var2 = new FocusChanged();
      var2.setFocused(true);
      ViewportMouse.client.getCallbacks().post(FocusChanged.class, var2);
   }

   public void onPost(Object var1) {
      DrawCallbacks var2 = ViewportMouse.client.getDrawCallbacks();
      if(var2 != null) {
         var2.draw();
      }

   }

   public boolean isClientThread() {
      return this.thread == Thread.currentThread();
   }

   public java.awt.Canvas getCanvas() {
      return this.canvas;
   }

   public final void copy$setMaxCanvasSize(int var1, int var2, int var3) {
      label15: {
         if(this.maxCanvasWidth == var1) {
            if(var2 == this.maxCanvasHeight) {
               break label15;
            }

            if(var3 <= 1748453277) {
               return;
            }
         }

         this.method968();
      }

      this.maxCanvasWidth = var1;
      this.maxCanvasHeight = var2;
   }

   public final void copy$resizeCanvas(int var1) {
      Container var2 = this.method985();
      if(var2 != null) {
         Bounds var3 = this.method986();
         this.contentWidth = Math.max(var3.highX, this.field445);
         this.contentHeight = Math.max(var3.highY, this.field446);
         if(this.contentWidth <= 0) {
            this.contentWidth = 1;
         }

         if(this.contentHeight <= 0) {
            this.contentHeight = 1;
         }

         FloorDecoration.canvasWidth = Math.min(this.contentWidth, this.maxCanvasWidth);
         Client.canvasWidthChanged(-1);
         WallDecoration.canvasHeight = Math.min(this.contentHeight, this.maxCanvasHeight);
         Client.canvasHeightChanged(-1);
         this.canvasX = (this.contentWidth - FloorDecoration.canvasWidth) / 2;
         this.canvasY = 0;
         this.canvas.setSize(FloorDecoration.canvasWidth, WallDecoration.canvasHeight);
         class30.rasterProvider = new RasterProvider(FloorDecoration.canvasWidth, WallDecoration.canvasHeight, this.canvas);
         if(var2 == this.frame) {
            Insets var4 = this.frame.getInsets();
            this.canvas.setLocation(var4.left + this.canvasX, this.canvasY + var4.top);
         } else {
            this.canvas.setLocation(this.canvasX, this.canvasY);
         }

         this.field429 = true;
         this.vmethod1819();
      }
   }

   public boolean isReplaceCanvasNextFrame() {
      return this.isCanvasInvalid;
   }

   public void setReplaceCanvasNextFrame(boolean var1) {
      this.isCanvasInvalid = var1;
   }

   public void setResizeCanvasNextFrame(boolean var1) {
      this.resizeCanvasNextFrame = var1;
   }

   public void setMaxCanvasWidth(int var1) {
      this.maxCanvasWidth = var1;
   }

   public void setMaxCanvasHeight(int var1) {
      this.maxCanvasHeight = var1;
   }

   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1"
   )
   public void onReplaceCanvasNextFrameChanged(int var1) {
      if(ViewportMouse.client != null && ViewportMouse.client.isGpu() && this.isReplaceCanvasNextFrame()) {
         this.setReplaceCanvasNextFrame(false);
         this.setResizeCanvasNextFrame(true);
      }

   }

   public void run() {
      this.onRun();

      try {
         if(TaskHandler.javaVendor != null) {
            String var1 = TaskHandler.javaVendor.toLowerCase();
            if(var1.indexOf("sun") != -1 || var1.indexOf("apple") != -1) {
               String var2 = TaskHandler.javaVersion;
               if(var2.equals("1.1") || var2.startsWith("1.1.") || var2.equals("1.2") || var2.startsWith("1.2.") || var2.equals("1.3") || var2.startsWith("1.3.") || var2.equals("1.4") || var2.startsWith("1.4.") || var2.equals("1.5") || var2.startsWith("1.5.") || var2.equals("1.6.0")) {
                  this.method984("wrongjava");
                  return;
               }

               if(var2.startsWith("1.6.0_")) {
                  int var3;
                  for(var3 = 6; var3 < var2.length() && AbstractWorldMapIcon.method710(var2.charAt(var3)); ++var3) {
                     ;
                  }

                  String var4 = var2.substring(6, var3);
                  if(NetSocket.method3488(var4) && TilePaint.method2912(var4) < 10) {
                     this.method984("wrongjava");
                     return;
                  }
               }

               fiveOrOne = 5;
            }
         }

         this.setFocusCycleRoot(true);
         this.method966();
         this.vmethod1646();
         clock = ItemContainer.method1451();

         while(0L == stopTimeMs || class33.method680() < stopTimeMs) {
            gameCyclesToDo = clock.vmethod3511(cycleDurationMillis, fiveOrOne);

            for(int var5 = 0; var5 < gameCyclesToDo; ++var5) {
               this.method976();
            }

            this.method969();
            this.method956(this.canvas);
         }
      } catch (Exception var6) {
         class19.method342((String)null, var6);
         this.method984("crash");
      }

      this.method972();
   }

   public final void start() {
      if(this == gameShell && !isKilled) {
         stopTimeMs = 0L;
      }
   }

   public final void stop() {
      if(this == gameShell && !isKilled) {
         stopTimeMs = class33.method680() + 4000L;
      }
   }

   public final void update(Graphics var1) {
      this.paint(var1);
   }

   public final void focusLost(FocusEvent var1) {
      volatileFocus = false;
   }

   public final void windowClosed(WindowEvent var1) {
   }

   public final void windowDeiconified(WindowEvent var1) {
   }

   public final void windowIconified(WindowEvent var1) {
   }

   public final void windowOpened(WindowEvent var1) {
   }

   public abstract void init();

   public final void windowClosing(WindowEvent var1) {
      this.destroy();
   }

   public final void windowActivated(WindowEvent var1) {
   }

   public final void windowDeactivated(WindowEvent var1) {
   }

   public final void focusGained(FocusEvent var1) {
      this.onFocusGained(var1);
      volatileFocus = true;
      this.field429 = true;
   }

   public Thread getClientThread() {
      return this.thread;
   }

   public boolean isResizeCanvasNextFrame() {
      return this.resizeCanvasNextFrame;
   }

   public void post(Object var1) {
      this.method956(var1);
   }

   public void resizeCanvas() {
      this.method961(684720685);
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-1142296629"
   )
   final void method968() {
      this.resizeCanvasNextFrame = true;
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(IIII)V",
      garbageValue = "-2112987351"
   )
   protected final void method965(int var1, int var2, int var3) {
      try {
         if(gameShell != null) {
            ++GameShell_redundantStartThreadCount;
            if(GameShell_redundantStartThreadCount >= 3) {
               this.method984("alreadyloaded");
               return;
            }

            this.getAppletContext().showDocument(this.getDocumentBase(), "_self");
            return;
         }

         gameShell = this;
         FloorDecoration.canvasWidth = var1;
         Client.canvasWidthChanged(-1);
         WallDecoration.canvasHeight = var2;
         Client.canvasHeightChanged(-1);
         RunException.RunException_revision = var3;
         RunException.RunException_applet = this;
         if(taskHandler == null) {
            taskHandler = new TaskHandler();
         }

         taskHandler.method3427(this, 1);
      } catch (Exception var5) {
         class19.method342((String)null, var5);
         this.method984("crash");
      }

   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-2138228896"
   )
   void method969() {
      Container var1 = this.method985();
      long var2 = class33.method680();
      long var4 = graphicsTickTimes[Varcs.graphicsTickTimeIdx];
      graphicsTickTimes[Varcs.graphicsTickTimeIdx] = var2;
      Varcs.graphicsTickTimeIdx = Varcs.graphicsTickTimeIdx + 1 & 31;
      if(var4 != 0L && var2 > var4) {
         int var6 = (int)(var2 - var4);
         fps = ((var6 >> 1) + 32000) / var6;
      }

      if(++field452 - 1 > 50) {
         field452 -= 50;
         this.field429 = true;
         this.canvas.setSize(FloorDecoration.canvasWidth, WallDecoration.canvasHeight);
         this.canvas.setVisible(true);
         if(var1 == this.frame) {
            Insets var7 = this.frame.getInsets();
            this.canvas.setLocation(this.canvasX + var7.left, var7.top + this.canvasY);
         } else {
            this.canvas.setLocation(this.canvasX, this.canvasY);
         }
      }

      if(this.isCanvasInvalid) {
         this.method1083();
      }

      this.method970();
      this.vmethod1732(this.field429);
      if(this.field429) {
         this.method998();
      }

      this.field429 = false;
   }

   @ObfuscatedName("h")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "1598778852"
   )
   protected final boolean method967() {
      return true;
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "2"
   )
   protected final void method960() {
      class22.method455(this.canvas);
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "26"
   )
   final void method970() {
      Bounds var1 = this.method986();
      if(var1.highX != this.contentWidth || this.contentHeight != var1.highY || this.resizeCanvasNextFrame) {
         this.method961(684720685);
         this.resizeCanvasNextFrame = false;
      }

   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "65280"
   )
   final synchronized void method972() {
      if(!isKilled) {
         isKilled = true;

         try {
            this.canvas.removeFocusListener(this);
         } catch (Exception var5) {
            ;
         }

         try {
            this.vmethod1699();
         } catch (Exception var4) {
            ;
         }

         if(this.frame != null) {
            try {
               System.exit(0);
            } catch (Throwable var3) {
               ;
            }
         }

         if(taskHandler != null) {
            try {
               taskHandler.method3416();
            } catch (Exception var2) {
               ;
            }
         }

         this.vmethod1643();
      }
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      signature = "(Ljava/lang/String;I)V",
      garbageValue = "-23270963"
   )
   protected void method963(String var1) {
      this.clipboard.setContents(new StringSelection(var1), (ClipboardOwner)null);
   }

   @ObfuscatedName("if")
   @ObfuscatedSignature(
      signature = "(IIIIII)V",
      garbageValue = "1376272907"
   )
   static final void method971(int var0, int var1, int var2, int var3, int var4) {
      class8.scrollBarSprites[0].method6320(var0, var1);
      class8.scrollBarSprites[1].method6320(var0, var3 + var1 - 16);
      Rasterizer2D.method6469(var0, var1 + 16, 16, var3 - 32, Client.field907);
      int var5 = var3 * (var3 - 32) / var4;
      if(var5 < 8) {
         var5 = 8;
      }

      int var6 = (var3 - 32 - var5) * var2 / (var4 - var3);
      Rasterizer2D.method6469(var0, var6 + var1 + 16, 16, var5, Client.field908);
      Rasterizer2D.method6426(var0, var6 + var1 + 16, var5, Client.field910);
      Rasterizer2D.method6426(var0 + 1, var6 + var1 + 16, var5, Client.field910);
      Rasterizer2D.method6424(var0, var6 + var1 + 16, 16, Client.field910);
      Rasterizer2D.method6424(var0, var6 + var1 + 17, 16, Client.field910);
      Rasterizer2D.method6426(var0 + 15, var6 + var1 + 16, var5, Client.field909);
      Rasterizer2D.method6426(var0 + 14, var6 + var1 + 17, var5 - 1, Client.field909);
      Rasterizer2D.method6424(var0, var6 + var5 + var1 + 15, 16, Client.field909);
      Rasterizer2D.method6424(var0 + 1, var5 + var6 + var1 + 14, 15, Client.field909);
   }

   @ObfuscatedName("ik")
   @ObfuscatedSignature(
      signature = "([Lho;Lho;ZI)V",
      garbageValue = "-55146374"
   )
   static void method1005(Widget[] var0, Widget var1, boolean var2) {
      int var3 = var1.scrollWidth != 0?var1.scrollWidth:var1.width;
      int var4 = var1.scrollHeight != 0?var1.scrollHeight:var1.height;
      FaceNormal.method2907(var0, var1.id, var3, var4, var2);
      if(var1.children != null) {
         FaceNormal.method2907(var1.children, var1.id, var3, var4, var2);
      }

      InterfaceParent var5 = (InterfaceParent)Client.interfaceParents.method6346((long)var1.id);
      if(var5 != null) {
         class256.method4654(var5.group, var3, var4, var2);
      }

      if(var1.contentType == 1337) {
         ;
      }

   }
}
