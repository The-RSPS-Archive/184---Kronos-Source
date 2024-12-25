package io.ruin.model.inter.journal.presets;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.stat.StatType;

public class PresetCustom extends Preset {

    public static final PresetCustom[] ENTRIES = {
            new PresetCustom(1),
            new PresetCustom(2),
            new PresetCustom(3),
            new PresetCustom(4),
            new PresetCustom(5),
            new PresetCustom(6, PlayerGroup.SAPPHIRE, PlayerGroup.RUBY, PlayerGroup.DIAMOND, PlayerGroup.DRAGONSTONE, PlayerGroup.ONYX, PlayerGroup.ZENYTE),
            new PresetCustom(7, PlayerGroup.RUBY, PlayerGroup.DIAMOND, PlayerGroup.DRAGONSTONE, PlayerGroup.ONYX, PlayerGroup.ZENYTE),
            new PresetCustom(8, PlayerGroup.DIAMOND, PlayerGroup.DRAGONSTONE, PlayerGroup.ONYX, PlayerGroup.ZENYTE),
            new PresetCustom(9, PlayerGroup.DRAGONSTONE, PlayerGroup.ONYX, PlayerGroup.ZENYTE),
            new PresetCustom(10, PlayerGroup.ONYX, PlayerGroup.ZENYTE),
            new PresetCustom(11, PlayerGroup.ZENYTE),
    };

    public static final PresetCustom[] ECO_ENTRIES = {
            new PresetCustom(7),
            new PresetCustom(8),
            new PresetCustom(9),
            new PresetCustom(1, PlayerGroup.SAPPHIRE, PlayerGroup.RUBY, PlayerGroup.DIAMOND, PlayerGroup.DRAGONSTONE, PlayerGroup.ONYX, PlayerGroup.ZENYTE),
            new PresetCustom(2, PlayerGroup.RUBY, PlayerGroup.DIAMOND, PlayerGroup.DRAGONSTONE, PlayerGroup.ONYX, PlayerGroup.ZENYTE),
            new PresetCustom(3, PlayerGroup.DIAMOND, PlayerGroup.DRAGONSTONE, PlayerGroup.ONYX, PlayerGroup.ZENYTE),
            new PresetCustom(4, PlayerGroup.DRAGONSTONE, PlayerGroup.ONYX, PlayerGroup.ZENYTE),
            new PresetCustom(5, PlayerGroup.ONYX, PlayerGroup.ZENYTE),
            new PresetCustom(6, PlayerGroup.ZENYTE),
    };

    public static PresetCustom[] getEntries() {
        return ECO_ENTRIES;
    }

    private int id;
    private PlayerGroup[] donatorGroups;

    public PresetCustom(int id, PlayerGroup... donatorGroups) {
        this.id = id;
        this.donatorGroups = donatorGroups;
    }

    public static void check(Player player) {
        if (player.customPresets.length < getEntries().length) {
            PresetCustom[] custom = player.customPresets;
            player.customPresets = new PresetCustom[getEntries().length];
            System.arraycopy(custom, 0, player.customPresets, 0, custom.length);
        }
    }

    @Override
    public void send(Player player) {
        PresetCustom preset = player.customPresets[id - 1];
        if(preset == null)
            sendEmpty(player);
        else
            sendCustom(player, preset);
    }

    private void sendEmpty(Player player) {
        send(player, "<img=" + (getImageId()) + "> Empty", Color.BRONZE);
    }

    private void sendCustom(Player player, PresetCustom preset) {
        if(preset.equipItems.length != 14) { //todo remove
            Item[] newItems = new Item[14];
            System.arraycopy(preset.equipItems, 0, newItems, 0, 14);
            preset.equipItems = newItems;
        }
        send(player, "<img=" + getImageId() + "> " + preset.name, Color.BRONZE);
    }

    private int getImageId() {
        return donatorGroups == null || donatorGroups.length == 0 ? id + 77 : donatorGroups[0].clientImgId;
    }

    @Override
    public void selectStart(Player player) {
        PresetCustom preset = player.customPresets[id - 1];
        if (donatorGroups != null && donatorGroups.length > 0) {
            boolean allowed = false;
            for (PlayerGroup tier : donatorGroups) {
                if (player.isGroup(tier)) {
                    allowed = true;
                    break;
                }
            }
            if (!allowed && !player.isAdmin()) {
                player.dialogue(new MessageDialogue("Your donator rank is not high enough to use this preset slot."));
                return;
            }
        }
        if(preset == null) {
            player.dialogue(
                    new MessageDialogue("Would you like to create a custom preset?"),
                    new OptionsDialogue(
                            new Option("Yes", () -> player.nameInput("Enter desired preset name:", name -> {
                                PresetCustom newPreset = new PresetCustom(id, donatorGroups);
                                newPreset.name = name;
                                newPreset.overwrite(player);
                                player.customPresets[id - 1] = newPreset;
                                player.dialogue(new MessageDialogue("Your preset has been created."));
                                sendCustom(player, newPreset);
                            })),
                            new Option("No")
                    )
            );
        } else {
            player.dialogue(
                    new OptionsDialogue(
                            "Custom Preset: " + preset.name,
                            new Option("Select Preset", () -> preset.selectFinish(player)),
                            new Option("Rename Preset", () -> player.nameInput("Enter desired preset name:", name -> {
                                preset.name = name;
                                player.dialogue(new MessageDialogue("Your preset name has been changed."));
                                sendCustom(player, preset);
                            })),
                            new Option("Overwrite Preset", () -> player.dialogue(
                                    new MessageDialogue("Are you sure you want to overwrite this preset?"),
                                    new OptionsDialogue(
                                            new Option("Yes", () -> {
                                                preset.overwrite(player);
                                                player.dialogue(new MessageDialogue("Your preset items, stats, and spellbook have been updated."));
                                            }),
                                            new Option("No")
                                    )
                            )),
                            new Option("More Options", () -> {
                                player.dialogue(
                                        new OptionsDialogue(
                                                new Option("Delete Preset", () -> player.dialogue(
                                                        new MessageDialogue("Are you sure you want to delete this preset?"),
                                                        new OptionsDialogue(
                                                                new Option("Yes", () -> {
                                                                    player.customPresets[id - 1] = null;
                                                                    player.dialogue(new MessageDialogue("Your preset has been deleted."));
                                                                    sendEmpty(player);
                                                                }),
                                                                new Option("No")
                                                        )
                                                )),
                                                new Option("Swap Preset", () -> player.integerInput("Enter the preset number to swap with:", num -> {
                                                    if(num < 1 || num > getEntries().length) {
                                                        player.dialogue(new MessageDialogue("Preset " + num + " does not exist."));
                                                        return;
                                                    }
                                                    PresetCustom swap = player.customPresets[num - 1];
                                                    player.customPresets[id - 1] = swap;
                                                    if(swap == null)
                                                        getEntries()[id - 1].sendEmpty(player);
                                                    else
                                                        getEntries()[id - 1].sendCustom(player, swap);
                                                    player.customPresets[num - 1] = preset;
                                                    getEntries()[num - 1].sendCustom(player, preset);
                                                    player.dialogue(new MessageDialogue("Presets " + id + " has been swapped with preset " + num + "."));
                                                }))
                                        )
                                );
                            })
                    )
            );
        }
    }

    private void overwrite(Player player) {
        if (!player.getGameMode().isIronMan()) {
            attack = level(player.getStats().get(StatType.Attack));
            strength = level(player.getStats().get(StatType.Strength));
            defence = level(player.getStats().get(StatType.Defence));
            ranged = level(player.getStats().get(StatType.Ranged));
            prayer = level(player.getStats().get(StatType.Prayer));
            magic = level(player.getStats().get(StatType.Magic));
            hitpoints = level(player.getStats().get(StatType.Hitpoints));
        } else {
            attack = strength = defence = ranged = prayer = magic = hitpoints = null;
        }
        spellBook = SpellBook.values()[Config.MAGIC_BOOK.get(player)];
        copy(player.getInventory(), invItems);
        copy(player.getEquipment(), equipItems);
        if(runePouchItems != null && !player.getRunePouch().isEmpty())
            copy(player.getRunePouch(), runePouchItems);
    }

}