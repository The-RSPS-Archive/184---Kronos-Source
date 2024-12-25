package net.runelite.standalone;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gi")
public class class188 {
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "[Lmm;"
   )
   public static BufferedFile[] JagexCache_idxFiles;

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      signature = "(IIIS)I",
      garbageValue = "22050"
   )
   static int method3736(int var0, int var1, int var2) {
      return (Tiles.Tiles_renderFlags[var0][var1][var2] & 8) != 0?0:(var0 > 0 && (Tiles.Tiles_renderFlags[1][var1][var2] & 2) != 0?var0 - 1:var0);
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(Lkf;I)V",
      garbageValue = "-1032758603"
   )
   public static void method3740(PacketBuffer var0) {
      ReflectionCheck var1 = (ReflectionCheck)class94.reflectionChecks.method5044();
      if(var1 != null) {
         int var2 = var0.offset;
         var0.writeInt(var1.id);

         for(int var3 = 0; var3 < var1.size; ++var3) {
            if(var1.creationErrors[var3] != 0) {
               var0.writeByte(var1.creationErrors[var3]);
            } else {
               try {
                  int var4 = var1.operations[var3];
                  Field var5;
                  int var6;
                  if(var4 == 0) {
                     var5 = var1.field1186[var3];
                     var6 = var5.getInt((Object)null);
                     var0.writeByte(0);
                     var0.writeInt(var6);
                  } else if(var4 == 1) {
                     var5 = var1.field1186[var3];
                     var5.setInt((Object)null, var1.intReplaceValues[var3]);
                     var0.writeByte(0);
                  } else if(var4 == 2) {
                     var5 = var1.field1186[var3];
                     var6 = var5.getModifiers();
                     var0.writeByte(0);
                     var0.writeInt(var6);
                  }

                  Method var25;
                  if(var4 != 3) {
                     if(var4 == 4) {
                        var25 = var1.methods[var3];
                        var6 = var25.getModifiers();
                        var0.writeByte(0);
                        var0.writeInt(var6);
                     }
                  } else {
                     var25 = var1.methods[var3];
                     byte[][] var10 = var1.arguments[var3];
                     Object[] var7 = new Object[var10.length];

                     for(int var8 = 0; var8 < var10.length; ++var8) {
                        ObjectInputStream var9 = new ObjectInputStream(new ByteArrayInputStream(var10[var8]));
                        var7[var8] = var9.readObject();
                     }

                     Object var11 = var25.invoke((Object)null, var7);
                     if(var11 == null) {
                        var0.writeByte(0);
                     } else if(var11 instanceof Number) {
                        var0.writeByte(1);
                        var0.method5485(((Number)var11).longValue());
                     } else if(var11 instanceof String) {
                        var0.writeByte(2);
                        var0.writeString((String)var11);
                     } else {
                        var0.writeByte(4);
                     }
                  }
               } catch (ClassNotFoundException var13) {
                  var0.writeByte(-10);
               } catch (InvalidClassException var14) {
                  var0.writeByte(-11);
               } catch (StreamCorruptedException var15) {
                  var0.writeByte(-12);
               } catch (OptionalDataException var16) {
                  var0.writeByte(-13);
               } catch (IllegalAccessException var17) {
                  var0.writeByte(-14);
               } catch (IllegalArgumentException var18) {
                  var0.writeByte(-15);
               } catch (InvocationTargetException var19) {
                  var0.writeByte(-16);
               } catch (SecurityException var20) {
                  var0.writeByte(-17);
               } catch (IOException var21) {
                  var0.writeByte(-18);
               } catch (NullPointerException var22) {
                  var0.writeByte(-19);
               } catch (Exception var23) {
                  var0.writeByte(-20);
               } catch (Throwable var24) {
                  var0.writeByte(-21);
               }
            }
         }

         var0.method5482(var2);
         var1.method3497();
      }
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(II)I",
      garbageValue = "-1300861116"
   )
   public static int method3739(int var0) {
      return var0 > 0?1:(var0 < 0?-1:0);
   }

   @ObfuscatedName("eu")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-615502292"
   )
   static void method3738() {
      int var0;
      if(Client.titleLoadingStage == 0) {
         PacketWriter.scene = new Scene(4, 104, 104, Tiles.Tiles_heights);

         for(var0 = 0; var0 < 4; ++var0) {
            Client.collisionMaps[var0] = new CollisionMap(104, 104);
         }

         ObjectSound.sceneMinimapSprite = new Sprite(512, 512);
         Login.Login_loadingText = "Starting game engine...";
         Login.Login_loadingPercent = 5;
         Client.titleLoadingStage = 20;
      } else if(Client.titleLoadingStage == 20) {
         Login.Login_loadingText = "Prepared visibility map";
         Login.Login_loadingPercent = 10;
         Client.titleLoadingStage = 30;
      } else if(Client.titleLoadingStage == 30) {
         class4.archive0 = AttackOption.method2106(0, false, true, true);
         WorldMapLabelSize.archive1 = AttackOption.method2106(1, false, true, true);
         FaceNormal.archive2 = AttackOption.method2106(2, true, false, true);
         BoundaryObject.archive3 = AttackOption.method2106(3, false, true, true);
         GrandExchangeOfferAgeComparator.archive4 = AttackOption.method2106(4, false, true, true);
         class11.archive5 = AttackOption.method2106(5, true, true, true);
         class212.archive6 = AttackOption.method2106(6, true, true, true);
         Language.archive7 = AttackOption.method2106(7, false, true, true);
         GrandExchangeOfferAgeComparator.archive8 = AttackOption.method2106(8, false, true, true);
         AttackOption.archive9 = AttackOption.method2106(9, false, true, true);
         Client.archive10 = AttackOption.method2106(10, false, true, true);
         ClanMate.archive11 = AttackOption.method2106(11, false, true, true);
         GrandExchangeOfferOwnWorldComparator.archive12 = AttackOption.method2106(12, false, true, true);
         Tile.archive13 = AttackOption.method2106(13, true, false, true);
         WorldMapData_1.archive14 = AttackOption.method2106(14, false, true, true);
         Script.archive15 = AttackOption.method2106(15, false, true, true);
         Client.archive17 = AttackOption.method2106(17, true, true, true);
         VarcInt.archive18 = AttackOption.method2106(18, false, true, true);
         WorldMapLabel.archive19 = AttackOption.method2106(19, false, true, true);
         WorldMapEvent.archive20 = AttackOption.method2106(20, false, true, true);
         Login.Login_loadingText = "Connecting to update server";
         Login.Login_loadingPercent = 20;
         Client.titleLoadingStage = 40;
      } else if(Client.titleLoadingStage == 40) {
         byte var28 = 0;
         var0 = var28 + class4.archive0.method4270() * 4 / 100;
         var0 += WorldMapLabelSize.archive1.method4270() * 4 / 100;
         var0 += FaceNormal.archive2.method4270() * 2 / 100;
         var0 += BoundaryObject.archive3.method4270() * 2 / 100;
         var0 += GrandExchangeOfferAgeComparator.archive4.method4270() * 6 / 100;
         var0 += class11.archive5.method4270() * 4 / 100;
         var0 += class212.archive6.method4270() * 2 / 100;
         var0 += Language.archive7.method4270() * 56 / 100;
         var0 += GrandExchangeOfferAgeComparator.archive8.method4270() * 2 / 100;
         var0 += AttackOption.archive9.method4270() * 2 / 100;
         var0 += Client.archive10.method4270() * 2 / 100;
         var0 += ClanMate.archive11.method4270() * 2 / 100;
         var0 += GrandExchangeOfferOwnWorldComparator.archive12.method4270() * 2 / 100;
         var0 += Tile.archive13.method4270() * 2 / 100;
         var0 += WorldMapData_1.archive14.method4270() * 2 / 100;
         var0 += Script.archive15.method4270() * 2 / 100;
         var0 += WorldMapLabel.archive19.method4270() / 100;
         var0 += VarcInt.archive18.method4270() / 100;
         var0 += WorldMapEvent.archive20.method4270() / 100;
         var0 += Client.archive17.method4265() && Client.archive17.method4025()?1:0;
         if(var0 != 100) {
            if(var0 != 0) {
               Login.Login_loadingText = "Checking for updates - " + var0 + "%";
            }

            Login.Login_loadingPercent = 30;
         } else {
            WorldMapAreaData.method671(class4.archive0, "Animations");
            WorldMapAreaData.method671(WorldMapLabelSize.archive1, "Skeletons");
            WorldMapAreaData.method671(GrandExchangeOfferAgeComparator.archive4, "Sound FX");
            WorldMapAreaData.method671(class11.archive5, "Maps");
            WorldMapAreaData.method671(class212.archive6, "Music Tracks");
            WorldMapAreaData.method671(Language.archive7, "Models");
            WorldMapAreaData.method671(GrandExchangeOfferAgeComparator.archive8, "Sprites");
            WorldMapAreaData.method671(ClanMate.archive11, "Music Jingles");
            WorldMapAreaData.method671(WorldMapData_1.archive14, "Music Samples");
            WorldMapAreaData.method671(Script.archive15, "Music Patches");
            WorldMapAreaData.method671(WorldMapLabel.archive19, "World Map");
            WorldMapAreaData.method671(VarcInt.archive18, "World Map Geography");
            WorldMapAreaData.method671(WorldMapEvent.archive20, "World Map Ground");
            WorldMapData_0.spriteIds = new GraphicsDefaults();
            WorldMapData_0.spriteIds.method5792(Client.archive17);
            Login.Login_loadingText = "Loaded update list";
            Login.Login_loadingPercent = 30;
            Client.titleLoadingStage = 45;
         }
      } else {
         Archive var2;
         Archive var3;
         Archive var4;
         if(Client.titleLoadingStage == 45) {
            boolean var27 = !Client.isLowDetail;
            UrlRequest.PcmPlayer_sampleRate = 22050;
            PcmPlayer.PcmPlayer_stereo = var27;
            MenuAction.PcmPlayer_count = 2;
            MidiPcmStream var26 = new MidiPcmStream();
            var26.method3608(9, 128);
            class213.pcmPlayer0 = GrandExchangeOfferNameComparator.method798(GameShell.taskHandler, 0, 22050);
            class213.pcmPlayer0.method2703(var26);
            var2 = Script.archive15;
            var3 = WorldMapData_1.archive14;
            var4 = GrandExchangeOfferAgeComparator.archive4;
            class197.musicPatchesArchive = var2;
            SecureRandomCallable.musicSamplesArchive = var3;
            class197.soundEffectsArchive = var4;
            class38.midiPcmStream = var26;
            GrandExchangeOfferWorldComparator.pcmPlayer1 = GrandExchangeOfferNameComparator.method798(GameShell.taskHandler, 1, 2048);
            WorldMapLabelSize.pcmStreamMixer = new PcmStreamMixer();
            GrandExchangeOfferWorldComparator.pcmPlayer1.method2703(WorldMapLabelSize.pcmStreamMixer);
            Interpreter.decimator = new Decimator(22050, UrlRequest.PcmPlayer_sampleRate);
            Login.Login_loadingText = "Prepared sound engine";
            Login.Login_loadingPercent = 35;
            Client.titleLoadingStage = 50;
            UserComparator7.WorldMapElement_fonts = new Fonts(GrandExchangeOfferAgeComparator.archive8, Tile.archive13);
         } else if(Client.titleLoadingStage == 50) {
            var0 = FontName.method5750().length;
            Client.fontsMap = UserComparator7.WorldMapElement_fonts.method5758(FontName.method5750());
            if(Client.fontsMap.size() < var0) {
               Login.Login_loadingText = "Loading fonts - " + Client.fontsMap.size() * 100 / var0 + "%";
               Login.Login_loadingPercent = 40;
            } else {
               GrandExchangeOfferOwnWorldComparator.fontPlain11 = (Font)Client.fontsMap.get(FontName.FontName_plain11);
               GraphicsDefaults.fontPlain12 = (Font)Client.fontsMap.get(FontName.FontName_plain12);
               class170.fontBold12 = (Font)Client.fontsMap.get(FontName.FontName_bold12);
               class261.platformInfo = Client.platformInfoProvider.vmethod6480();
               Login.Login_loadingText = "Loaded fonts";
               Login.Login_loadingPercent = 40;
               Client.titleLoadingStage = 60;
            }
         } else {
            Archive var1;
            int var20;
            if(Client.titleLoadingStage == 60) {
               var1 = Client.archive10;
               var2 = GrandExchangeOfferAgeComparator.archive8;
               var20 = 0;
               if(var1.method4022("title.jpg", "")) {
                  ++var20;
               }

               if(var2.method4022("logo", "")) {
                  ++var20;
               }

               if(var2.method4022("logo_deadman_mode", "")) {
                  ++var20;
               }

               if(var2.method4022("titlebox", "")) {
                  ++var20;
               }

               if(var2.method4022("titlebutton", "")) {
                  ++var20;
               }

               if(var2.method4022("runes", "")) {
                  ++var20;
               }

               if(var2.method4022("title_mute", "")) {
                  ++var20;
               }

               if(var2.method4022("options_radio_buttons,0", "")) {
                  ++var20;
               }

               if(var2.method4022("options_radio_buttons,2", "")) {
                  ++var20;
               }

               if(var2.method4022("options_radio_buttons,4", "")) {
                  ++var20;
               }

               if(var2.method4022("options_radio_buttons,6", "")) {
                  ++var20;
               }

               var2.method4022("sl_back", "");
               var2.method4022("sl_flags", "");
               var2.method4022("sl_arrows", "");
               var2.method4022("sl_stars", "");
               var2.method4022("sl_button", "");
               byte var29 = 11;
               if(var20 < var29) {
                  Login.Login_loadingText = "Loading title screen - " + var20 * 100 / var29 + "%";
                  Login.Login_loadingPercent = 50;
               } else {
                  Login.Login_loadingText = "Loaded title screen";
                  Login.Login_loadingPercent = 50;
                  MouseRecorder.method1207(5);
                  Client.titleLoadingStage = 70;
               }
            } else if(Client.titleLoadingStage == 70) {
               if(!FaceNormal.archive2.method4025()) {
                  Login.Login_loadingText = "Loading config - " + FaceNormal.archive2.method4275() + "%";
                  Login.Login_loadingPercent = 60;
               } else {
                  Archive var30 = FaceNormal.archive2;
                  FloorOverlayDefinition.FloorOverlayDefinition_archive = var30;
                  var1 = FaceNormal.archive2;
                  FloorUnderlayDefinition.FloorUnderlayDefinition_archive = var1;
                  var2 = FaceNormal.archive2;
                  var3 = Language.archive7;
                  KitDefinition.KitDefinition_archive = var2;
                  GrandExchangeOfferOwnWorldComparator.KitDefinition_modelsArchive = var3;
                  DevicePcmPlayerProvider.KitDefinition_fileCount = KitDefinition.KitDefinition_archive.fileCount(3);
                  var4 = FaceNormal.archive2;
                  Archive var23 = Language.archive7;
                  boolean var6 = Client.isLowDetail;
                  ObjectDefinition.ObjectDefinition_archive = var4;
                  ObjectDefinition.ObjectDefinition_modelsArchive = var23;
                  ObjectDefinition.ObjectDefinition_isLowDetail = var6;
                  Archive var7 = FaceNormal.archive2;
                  Archive var8 = Language.archive7;
                  NPCDefinition.NpcDefinition_archive = var7;
                  NPCDefinition.NpcDefinition_modelArchive = var8;
                  Archive var9 = FaceNormal.archive2;
                  StructDefinition.StructDefinition_archive = var9;
                  WorldMapSection1.method773(FaceNormal.archive2, Language.archive7, Client.isMembersWorld, GrandExchangeOfferOwnWorldComparator.fontPlain11);
                  ItemContainer.method1461(FaceNormal.archive2, class4.archive0, WorldMapLabelSize.archive1);
                  Archive var10 = FaceNormal.archive2;
                  Archive var11 = Language.archive7;
                  class125.SpotAnimationDefinition_archive = var10;
                  SpotAnimationDefinition.SpotAnimationDefinition_modelArchive = var11;
                  GrandExchangeOfferNameComparator.method800(FaceNormal.archive2);
                  Archive var12 = FaceNormal.archive2;
                  VarpDefinition.VarpDefinition_archive = var12;
                  VarpDefinition.VarpDefinition_fileCount = VarpDefinition.VarpDefinition_archive.fileCount(16);
                  MouseRecorder.method1209(BoundaryObject.archive3, Language.archive7, GrandExchangeOfferAgeComparator.archive8, Tile.archive13);
                  KeyHandler.method484(FaceNormal.archive2);
                  Archive var13 = FaceNormal.archive2;
                  EnumDefinition.EnumDefinition_archive = var13;
                  Archive var14 = FaceNormal.archive2;
                  VarcInt.VarcInt_archive = var14;
                  Archive var15 = FaceNormal.archive2;
                  ParamDefinition.ParamDefinition_archive = var15;
                  class197.varcs = new Varcs();
                  WorldMapArea.method425(FaceNormal.archive2, GrandExchangeOfferAgeComparator.archive8, Tile.archive13);
                  ScriptFrame.method1309(FaceNormal.archive2, GrandExchangeOfferAgeComparator.archive8);
                  Archive var16 = FaceNormal.archive2;
                  Archive var17 = GrandExchangeOfferAgeComparator.archive8;
                  WorldMapElement.WorldMapElement_archive = var17;
                  if(var16.method4025()) {
                     WorldMapElement.WorldMapElement_count = var16.fileCount(35);
                     WorldMapElement.WorldMapElement_cached = new WorldMapElement[WorldMapElement.WorldMapElement_count];

                     for(int var18 = 0; var18 < WorldMapElement.WorldMapElement_count; ++var18) {
                        byte[] var19 = var16.method4020(35, var18, (short)3526);
                        WorldMapElement.WorldMapElement_cached[var18] = new WorldMapElement(var18);
                        if(var19 != null) {
                           WorldMapElement.WorldMapElement_cached[var18].method4373(new Buffer(var19));
                           WorldMapElement.WorldMapElement_cached[var18].method4368();
                        }
                     }
                  }

                  Login.Login_loadingText = "Loaded config";
                  Login.Login_loadingPercent = 60;
                  Client.titleLoadingStage = 80;
               }
            } else if(Client.titleLoadingStage == 80) {
               var0 = 0;
               if(AttackOption.compass == null) {
                  AttackOption.compass = NPCDefinition.method4417(GrandExchangeOfferAgeComparator.archive8, WorldMapData_0.spriteIds.compass, 0, -1092680498);
               } else {
                  ++var0;
               }

               if(PendingSpawn.redHintArrowSprite == null) {
                  PendingSpawn.redHintArrowSprite = NPCDefinition.method4417(GrandExchangeOfferAgeComparator.archive8, WorldMapData_0.spriteIds.field3784, 0, -1092680498);
               } else {
                  ++var0;
               }

               if(class125.mapSceneSprites == null) {
                  class125.mapSceneSprites = class313.method5840(GrandExchangeOfferAgeComparator.archive8, WorldMapData_0.spriteIds.mapScenes, 0);
               } else {
                  ++var0;
               }

               if(StructDefinition.headIconPkSprites == null) {
                  StructDefinition.headIconPkSprites = Canvas.method781(GrandExchangeOfferAgeComparator.archive8, WorldMapData_0.spriteIds.headIconsPk, 0);
                  //TODO: Modified [Custom pk icons]
                  CustomUtils.loadPkIcons();
               } else {
                  ++var0;
               }

               if(WorldMapRegion.headIconPrayerSprites == null) {
                  WorldMapRegion.headIconPrayerSprites = Canvas.method781(GrandExchangeOfferAgeComparator.archive8, WorldMapData_0.spriteIds.field3787, 0);
               } else {
                  ++var0;
               }

               if(ReflectionCheck.headIconHintSprites == null) {
                  ReflectionCheck.headIconHintSprites = Canvas.method781(GrandExchangeOfferAgeComparator.archive8, WorldMapData_0.spriteIds.field3783, 0);
               } else {
                  ++var0;
               }

               if(GameObject.mapMarkerSprites == null) {
                  GameObject.mapMarkerSprites = Canvas.method781(GrandExchangeOfferAgeComparator.archive8, WorldMapData_0.spriteIds.field3789, 0);
               } else {
                  ++var0;
               }

               if(UrlRequest.crossSprites == null) {
                  UrlRequest.crossSprites = Canvas.method781(GrandExchangeOfferAgeComparator.archive8, WorldMapData_0.spriteIds.field3790, 0);
               } else {
                  ++var0;
               }

               if(class17.mapDotSprites == null) {
                  class17.mapDotSprites = Canvas.method781(GrandExchangeOfferAgeComparator.archive8, WorldMapData_0.spriteIds.field3791, 0);
               } else {
                  ++var0;
               }

               if(class8.scrollBarSprites == null) {
                  class8.scrollBarSprites = class313.method5840(GrandExchangeOfferAgeComparator.archive8, WorldMapData_0.spriteIds.field3794, 0);
               } else {
                  ++var0;
               }

               if(WorldMapData_0.modIconSprites == null) {
                  WorldMapData_0.modIconSprites = class313.method5840(GrandExchangeOfferAgeComparator.archive8, WorldMapData_0.spriteIds.field3793, 0);
               } else {
                  ++var0;
               }

               //TODO: Modified Custom Interface Sprites
               if (CustomUtils.customInterSprites == null)
                  CustomUtils.loadCustomInterfaceSprites();
               else
                  ++var0;

               if(var0 < 12) {
                  Login.Login_loadingText = "Loading sprites - " + var0 * 100 / 13 + "%";
                  Login.Login_loadingPercent = 70;
               } else {
                  AbstractFont.AbstractFont_modIconSprites = WorldMapData_0.modIconSprites;
                  //TODO: Modified [Load custom icons]
                  try {
                     CustomUtils.loadIcons(false);
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
                  PendingSpawn.redHintArrowSprite.method6100();
                  int var24 = (int)(Math.random() * 21.0D) - 10;
                  int var25 = (int)(Math.random() * 21.0D) - 10;
                  var20 = (int)(Math.random() * 21.0D) - 10;
                  int var21 = (int)(Math.random() * 41.0D) - 20;
                  class125.mapSceneSprites[0].method6319(var21 + var24, var21 + var25, var21 + var20);
                  Login.Login_loadingText = "Loaded sprites";
                  Login.Login_loadingPercent = 70;
                  Client.titleLoadingStage = 90;
               }
            } else if(Client.titleLoadingStage == 90) {
               if(!AttackOption.archive9.method4025()) {
                  Login.Login_loadingText = "Loading textures - " + "0%";
                  Login.Login_loadingPercent = 90;
               } else {
                  Archive.textureProvider = new TextureProvider(AttackOption.archive9, GrandExchangeOfferAgeComparator.archive8, 20, 0.8D, Client.isLowDetail?64:128);
                  Rasterizer3D.method2948(Archive.textureProvider);
                  Rasterizer3D.method2949(0.8D);
                  Client.titleLoadingStage = 100;
               }
            } else if(Client.titleLoadingStage == 100) {
               var0 = Archive.textureProvider.method2839();
               if(var0 < 100) {
                  Login.Login_loadingText = "Loading textures - " + var0 + "%";
                  Login.Login_loadingPercent = 90;
               } else {
                  Login.Login_loadingText = "Loaded textures";
                  Login.Login_loadingPercent = 90;
                  Client.titleLoadingStage = 110;
               }
            } else if(Client.titleLoadingStage == 110) {
               WorldMapLabel.mouseRecorder = new MouseRecorder();
               GameShell.taskHandler.method3427(WorldMapLabel.mouseRecorder, 10);
               Login.Login_loadingText = "Loaded input handler";
               Login.Login_loadingPercent = 92;
               Client.titleLoadingStage = 120;
            } else if(Client.titleLoadingStage == 120) {
               if(!Client.archive10.method4022("huffman", "")) {
                  Login.Login_loadingText = "Loading wordpack - " + 0 + "%";
                  Login.Login_loadingPercent = 94;
               } else {
                  Huffman var22 = new Huffman(Client.archive10.getFileData("huffman", ""));
                  class217.huffman = var22;
                  Login.Login_loadingText = "Loaded wordpack";
                  Login.Login_loadingPercent = 94;
                  Client.titleLoadingStage = 130;
               }
            } else if(Client.titleLoadingStage == 130) {
               if(!BoundaryObject.archive3.method4025()) {
                  Login.Login_loadingText = "Loading interfaces - " + BoundaryObject.archive3.method4275() * 4 / 5 + "%";
                  Login.Login_loadingPercent = 96;
               } else if(!GrandExchangeOfferOwnWorldComparator.archive12.method4025()) {
                  Login.Login_loadingText = "Loading interfaces - " + (80 + GrandExchangeOfferOwnWorldComparator.archive12.method4275() / 6) + "%";
                  Login.Login_loadingPercent = 96;
               } else if(!Tile.archive13.method4025()) {
                  Login.Login_loadingText = "Loading interfaces - " + (96 + Tile.archive13.method4275() / 50) + "%";
                  Login.Login_loadingPercent = 96;
               } else {
                  Login.Login_loadingText = "Loaded interfaces";
                  Login.Login_loadingPercent = 98;
                  Client.titleLoadingStage = 140;
               }
            } else if(Client.titleLoadingStage == 140) {
               Login.Login_loadingPercent = 100;
               if(!WorldMapLabel.archive19.method4043(WorldMapCacheName.field243.name)) {
                  Login.Login_loadingText = "Loading world map - " + WorldMapLabel.archive19.method4045(WorldMapCacheName.field243.name) / 10 + "%";
               } else {
                  if(Tiles.worldMap == null) {
                     Tiles.worldMap = new WorldMap();
                     Tiles.worldMap.method5936(WorldMapLabel.archive19, VarcInt.archive18, WorldMapEvent.archive20, class170.fontBold12, Client.fontsMap, class125.mapSceneSprites);
                  }

                  Login.Login_loadingText = "Loaded world map";
                  Client.titleLoadingStage = 150;
               }
            } else if(Client.titleLoadingStage == 150) {
               MouseRecorder.method1207(10);
            }
         }
      }
   }
}
