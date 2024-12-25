package io.ruin.model.skills.construction.actions;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.entity.player.KillCounter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.PrayerAltar;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Construction;
import io.ruin.model.skills.magic.SpellBook;

import java.util.Arrays;

import static io.ruin.model.skills.construction.Buildable.*;
import static io.ruin.model.skills.construction.Construction.forHouseOwnerOnly;

public class AchievementGallery {

    private static final String[] BOX_NAMES = {"Basic", "Fancy", "Ornate"};

    private static void openJewelleryBox(Player player, int level) {
        if (level < 1 || level > BOX_NAMES.length) {
            return;
        }
        player.openInterface(InterfaceType.MAIN, 590);
        player.getPacketSender().sendClientScript(1685,"isi",
                level,
                BOX_NAMES[level - 1] + " Jewellery Box",
                15 // toggling bits in this parameter "unlocks" certain restricted teleports like tears of guthix, by default we just unlock them all
        );
        player.set("JEWBOX_LEVEL", level);
    }

    static {
        for (Buildable b : Arrays.asList(BASIC_JEWELLERY_BOX, FANCY_JEWELLERY_BOX, ORNATE_JEWELLERY_BOX)) {
            ObjectAction.register(b.getBuiltObjects()[0], "teleport", (player, obj) -> {
                openJewelleryBox(player, b.getBuiltObjects()[0] - 29153);
            });
        }
    }

    public enum JewelleryTeleport { // order is the same as slots in the interface!
        //Ring of dueling
        DUEL_ARENA(3316,3234,0),
        CASTLE_WARS(2441,3089,0),
        CLAN_WARS(3365, 3162, 0),

        //Games necklace
        BURTHORPE(2898,3545,0),
        BARBARIAN_OUTPOST(2518,3570,0),
        CORPOREAL_BEAST(2965,4382,2),
        TEARS_OF_GUTHIX(3251,9516,2),
        WINTERTODT_CAMP(1631,3945,0),

        //Combat bracelet
        WARRIORS_GUILD(2878,3546,0),
        CHAMPIONS_GUILD(3191,3364,0),
        MONASTERY(3051,3499,0),
        RANGING_GUILD(2656,3439,0),

        //Skills necklace
        FISHING_GUILD(2611,3392,0),
        MINING_GUILD(3016,3339,0),
        CRAFTING_GUILD(2933,3290,0),
        COOKING_GUILD(3143,3442,0),
        WOODCUTTING_GUILD(1659,3504,0),
        FARMING_GUILD(1248, 3718, 0),

        //Ring of wealth
        MISCELLANIA(2527,3859,0),
        GRAND_EXCHANGE(3164,3464,0),
        FALADOR_PARK(2995,3374,0),
        DONDAKANS_ROCK(2819,10155,0),

        //Amulet of glory
        EDGEVILLE(3088,3490,0),
        KARAMJA(2912,3171,0),
        DRAYNOR_VILLAGE(3104,3249,0),
        AL_KHARID(3292,3163,0);

        private Position position;

        JewelleryTeleport(int x, int y, int z) {
            this.position = new Position(x,y,z);
        }

        public void teleport(Player player) {
            player.getMovement().startTeleport(event -> {
                player.animate(714);
                player.graphics(111, 92, 0);
                player.publicSound(200);
                event.delay(3);
                player.getMovement().teleport(position.getX(), position.getY(), position.getZ());
            });
        }
        static {
            InterfaceHandler.register(590, h -> {
                h.actions[0] = (SlotAction) (p, slot) -> {
                    p.closeInterfaces();
                    if (slot < 0 || slot >= values().length)
                        return;
                    int level = p.get("JEWBOX_LEVEL", 1);
                    p.remove("JEWBOX_LEVEL");
                    if ((slot >= 8 && level < 2)
                            || (slot >= 17 && level < 3)) // anti-cheaters i guess...
                        return;
                    values()[slot].teleport(p);
                };
            });
        }
    }

    static {
        for (Buildable b: Arrays.asList(MAHOGANY_ADVENTURE_LOG, MARBLE_ADVENTURE_LOG, GILDED_ADVENTURE_LOG)) {
            ObjectAction.register(b.getBuiltObjects()[0], "read", Construction.forCurrentHouse((player, house) -> {
                readAchievementLog(player, house.getOwner());
            }));
        }
    }

    private static void readAchievementLog(Player player, Player owner) {
        if (owner == null) {
            return;
        }
        player.dialogue(new OptionsDialogue(
                new Option("Boss Kill Log", () -> KillCounter.openBoss(player, owner)),
                new Option("Slayer Kill Log", () -> KillCounter.openSlayer(player, owner)),
                new Option("Cancel")
        ));
    }

    /* Altar */
    static {
        ObjectAction.register(ANCIENT_ALTAR.getBuiltObjects()[0], "upgrade", forHouseOwnerOnly((player, house) -> sendFurnitureCreation(player, player.getCurrentRoom(), 0, OCCULT_ALTAR_FROM_ANCIENT)));
        ObjectAction.register(LUNAR_ALTAR.getBuiltObjects()[0], "upgrade", forHouseOwnerOnly((player, house) -> sendFurnitureCreation(player, player.getCurrentRoom(), 0, OCCULT_ALTAR_FROM_LUNAR)));
        ObjectAction.register(DARK_ALTAR.getBuiltObjects()[0], "upgrade", forHouseOwnerOnly((player, house) -> sendFurnitureCreation(player, player.getCurrentRoom(), 0, OCCULT_ALTAR_FROM_DARK)));

        ObjectAction.register(ANCIENT_ALTAR.getBuiltObjects()[0], "venerate", (player, obj) -> switchDialogue(player, SpellBook.ANCIENT, SpellBook.MODERN));
        ObjectAction.register(LUNAR_ALTAR.getBuiltObjects()[0], "venerate", (player, obj) -> switchDialogue(player, SpellBook.LUNAR, SpellBook.MODERN));
        ObjectAction.register(DARK_ALTAR.getBuiltObjects()[0], "venerate", (player, obj) -> switchDialogue(player, SpellBook.ARCEUUS, SpellBook.MODERN));
        ObjectAction.register(OCCULT_ALTAR.getBuiltObjects()[0], "venerate", (player, obj) -> switchDialogue(player, SpellBook.ANCIENT, SpellBook.LUNAR, SpellBook.ARCEUUS, SpellBook.MODERN));
    }

    private static void switchDialogue(Player player, SpellBook... books) {
        player.dialogue(new OptionsDialogue("Choose a spellbook",
                Arrays.stream(books).map(book -> new Option(StringUtils.getFormattedEnumName(book), () -> PrayerAltar.switchBook(player, book, true))).toArray(Option[]::new)
        ));
    }



}
