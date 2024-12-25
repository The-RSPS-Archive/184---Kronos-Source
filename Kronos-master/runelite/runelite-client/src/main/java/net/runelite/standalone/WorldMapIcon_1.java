package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("d")
public class WorldMapIcon_1 extends AbstractWorldMapIcon {
   @ObfuscatedName("cp")
   static boolean mouseCam;
   @ObfuscatedName("gd")
   static byte[][] regionLandArchives;
   @ObfuscatedName("hv")
   @ObfuscatedGetter(
      intValue = -767824539
   )
   static int cameraZ;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "Laa;"
   )
   final WorldMapRegion region;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 73044895
   )
   int subHeight;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1086525415
   )
   int subWidth;
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "Law;"
   )
   WorldMapLabel label;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1981898425
   )
   int element;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -90223087
   )
   final int objectDefId;

   @ObfuscatedSignature(
      signature = "(Lhb;Lhb;ILaa;)V"
   )
   WorldMapIcon_1(Coord var1, Coord var2, int var3, WorldMapRegion var4) {
      super(var1, var2);
      this.objectDefId = var3;
      this.region = var4;
      this.method2259();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      signature = "(I)Law;",
      garbageValue = "13348956"
   )
   WorldMapLabel vmethod2273() {
      return this.label;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "806304417"
   )
   int vmethod2257() {
      return this.subHeight;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      signature = "(I)I",
      garbageValue = "-175699296"
   )
   int vmethod2269() {
      return this.subWidth;
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      signature = "(B)I",
      garbageValue = "-79"
   )
   public int vmethod2277() {
      return this.element;
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      signature = "(B)V",
      garbageValue = "-93"
   )
   void method2259() {
      this.element = GrandExchangeOfferOwnWorldComparator.getObjectDefinition(this.objectDefId).method4733().mapIconId;
      this.label = this.region.method248(Decimator.method2498(this.element));
      WorldMapElement var1 = Decimator.method2498(this.vmethod2277());
      Sprite var2 = var1.method4369(false);
      if(var2 != null) {
         this.subWidth = var2.subWidth;
         this.subHeight = var2.subHeight;
      } else {
         this.subWidth = 0;
         this.subHeight = 0;
      }

   }

   @ObfuscatedName("eb")
   @ObfuscatedSignature(
      signature = "(I)V",
      garbageValue = "-806150701"
   )
   static final void method2278() {
      FloorOverlayDefinition.FloorOverlayDefinition_cached.method3035();
      FloorUnderlayDefinition.FloorUnderlayDefinition_cached.method3035();
      KitDefinition.KitDefinition_cached.method3035();
      ObjectDefinition.ObjectDefinition_cached.method3035();
      ObjectDefinition.ObjectDefinition_cachedModelData.method3035();
      ObjectDefinition.ObjectDefinition_cachedEntities.method3035();
      ObjectDefinition.ObjectDefinition_cachedModels.method3035();
      NPCDefinition.NpcDefinition_cached.method3035();
      NPCDefinition.NpcDefinition_cachedModels.method3035();
      InterfaceParent.method1138();
      SequenceDefinition.SequenceDefinition_cached.method3035();
      SequenceDefinition.SequenceDefinition_cachedFrames.method3035();
      class213.method3933();
      VarbitDefinition.VarbitDefinition_cached.method3035();
      MusicPatch.method3761();
      GrandExchangeEvent.method6483();
      HealthBarDefinition.HealthBarDefinition_cached.method3035();
      HealthBarDefinition.HealthBarDefinition_cachedSprites.method3035();
      StructDefinition.StructDefinition_cached.method3035();
      TextureProvider.method2859();
      WorldMapElement.WorldMapElement_cachedSprites.method3035();
      UserComparator1.method6398();
      Widget.Widget_cachedSprites.method3035();
      Widget.Widget_cachedModels.method3035();
      Widget.Widget_cachedFonts.method3035();
      Widget.Widget_cachedSpriteMasks.method3035();
      ((TextureProvider)Rasterizer3D.Rasterizer3D_textureLoader).method2845();
      Script.Script_cached.method3035();
      class4.archive0.method4036();
      WorldMapLabelSize.archive1.method4036();
      BoundaryObject.archive3.method4036();
      GrandExchangeOfferAgeComparator.archive4.method4036();
      class11.archive5.method4036();
      class212.archive6.method4036();
      Language.archive7.method4036();
      GrandExchangeOfferAgeComparator.archive8.method4036();
      AttackOption.archive9.method4036();
      Client.archive10.method4036();
      ClanMate.archive11.method4036();
      GrandExchangeOfferOwnWorldComparator.archive12.method4036();
   }
}
