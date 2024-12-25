package io.ruin.model.skills.farming;

import com.google.gson.annotations.Expose;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.skills.herblore.Herb;

import static io.ruin.model.skills.farming.patch.impl.AllotmentPatch.WATERING_CAN_IDS;

public class ToolStorage {

    enum Tool {//order should be the same as on the interface
        RAKE(5341, Config.STORAGE_RAKE),
        SEED_DIBBER(5343, Config.STORAGE_SEED_DIBBER),
        SPADE(952, Config.STORAGE_SPADE),
        SECATEURS(5329, Config.STORAGE_SECATEURS) {
            @Override
            public boolean accept(int item) {
                return item == 5329 || item == 7409;
            }

            @Override
            public void onDeposit(Player player, int item) {
                Config.STORAGE_SECATEURS_TYPE.set(player, item == 7409 ? 1 : 0);
            }

            @Override
            public int addItem(Player player, int amount) {
                int added = player.getInventory().add(Config.STORAGE_SECATEURS_TYPE.get(player) == 1 ? 7409 : 5329, amount);
                if (added > 0)
                    Config.STORAGE_SECATEURS_TYPE.set(player, 0);
                return added;
            }
        },
        WATERING_CAN(5331, Config.STORAGE_WATERING_CAN) {
            @Override
            public boolean accept(int item) {
                return WATERING_CAN_IDS.contains(item);
            }

            @Override
            public void onDeposit(Player player, int item) {
                player.getFarming().getStorage().wateringCanCharges = WATERING_CAN_IDS.indexOf(item);
            }

            @Override
            public int addItem(Player player, int amount) {
                return player.getInventory().add(WATERING_CAN_IDS.get(player.getFarming().getStorage().wateringCanCharges), amount);
            }
        },
        GARDENING_TROWEL(5325, Config.STORAGE_TROWEL),
        PLANT_CURE(6036, Config.STORAGE_PLANT_CURE),
        BOTTOMLESS_BUCKET(22997, Config.STORAGE_BOTTOMLESS_COMPOST) {
            @Override
            public int get(Player player) {
                int stored = super.get(player);
                if (stored == 0)
                    return 0;
                return player.getFarming().getStorage().emptyBottomlessBucket ? 1 : 2;
            }

            @Override
            public boolean accept(int item) {
                return item == 22994 || item == 22997;
            }

            @Override
            public void onDeposit(Player player, int item) {
                player.getFarming().getStorage().emptyBottomlessBucket = item == 22994;
            }

            @Override
            public int addItem(Player player, int amount) {
                return player.getInventory().add(player.getFarming().getStorage().emptyBottomlessBucket ? 22994 : 22997, 1);
            }
        },
        EMPTY_BUCKET(1925, null) {
            @Override
            public int get(Player player) {
                return (Config.STORAGE_EMPTY_BUCKET_2.get(player) << 5) | Config.STORAGE_EMPTY_BUCKET_1.get(player);
            }

            @Override
            public void update(Player player, int amount) {
                int newValue = get(player) + amount;
                Config.STORAGE_EMPTY_BUCKET_1.set(player, newValue & 31);
                Config.STORAGE_EMPTY_BUCKET_2.set(player, (newValue >> 5) & 7);
            }
        },
        COMPOST(6032, null) {
            @Override
            public int get(Player player) {
                return (Config.STORAGE_COMPOST_2.get(player) << 8) | Config.STORAGE_COMPOST_1.get(player);
            }

            @Override
            public void update(Player player, int amount) {
                int newValue = get(player) + amount;
                Config.STORAGE_COMPOST_1.set(player, newValue & 255);
                Config.STORAGE_COMPOST_2.set(player, (newValue >> 8) & 3);
            }
        },
        SUPERCOMPOST(6034, null) {
            @Override
            public int get(Player player) {
                return (Config.STORAGE_SUPERCOMPOST_2.get(player) << 8) | Config.STORAGE_SUPERCOMPOST_1.get(player);
            }

            @Override
            public void update(Player player, int amount) {
                int newValue = get(player) + amount;
                Config.STORAGE_SUPERCOMPOST_1.set(player, newValue & 255);
                Config.STORAGE_SUPERCOMPOST_2.set(player, (newValue >> 8) & 3);
            }
        },
        ULTRACOMPOST(21483, Config.STORAGE_ULTRACOMPOST);

        Tool(int itemId, Config config) {
            this.config = config;
            this.itemId = itemId;
            name = this.toString().toLowerCase().replace("_", " ");
        }

        private Config config;
        private int itemId;
        private String name;

        public boolean accept(int item) {
            return item == itemId;
        }

        public void update(Player player, int amount) {
            config.set(player, get(player) + amount);
        }

        public int get(Player player) {
            return config.get(player);
        }

        public int addItem(Player player, int amount) {
            return player.getInventory().add(itemId, amount);
        }

        public void onDeposit(Player player, int item) {
            /* memes */
        }

    }

    void setPlayer(Player player) {
        this.player = player;
    }

    public void open(Player player) {
        player.openInterface(InterfaceType.MAIN, 125);
        player.openInterface(InterfaceType.INVENTORY, 126);
    }

    public void withdraw(Tool tool, int amount) {
        amount = Math.min(amount, tool.get(player));
        if (amount == 0) {
            player.sendMessage("Your " + tool.name + " storage is empty.");
            return;
        }
        int added = tool.addItem(player, amount);
        if (added == 0) {
            player.sendMessage("Not enough space in your inventory.");
            return;
        }
        tool.update(player, -added);
    }

    public void deposit(Tool tool, int amount) {
        int maxAmount = getMaxAmount(tool);
        if (tool.get(player) >= maxAmount) {
            player.sendMessage("Your " + tool.name + " storage is full.");
            return;
        }
        if (maxAmount == 1) {
            for (Item item : player.getInventory().getItems()) {
                if (item == null)
                    continue;
                if (tool.accept(item.getId())) {
                    item.remove();
                    tool.onDeposit(player, item.getId());
                    tool.update(player, 1);
                    return;
                }
            }
            player.sendMessage("You don't have any of that to deposit.");
        } else {
            amount = Math.min(amount, Math.min(player.getInventory().getAmount(tool.itemId), maxAmount - tool.get(player)));
            if (amount == 0) {
                player.sendMessage("You don't have any of that to deposit.");
                return;
            }
            player.getInventory().remove(tool.itemId, amount);
            tool.update(player, amount);
            tool.onDeposit(player, tool.itemId); // just in case
        }
    }

    private int getMaxAmount(Tool tool) {
        if (tool == Tool.EMPTY_BUCKET || tool == Tool.COMPOST || tool == Tool.SUPERCOMPOST || tool == Tool.ULTRACOMPOST || tool == Tool.PLANT_CURE)
            return 1000;
        return 1;
    }

    static {
        InterfaceHandler.register(125, h -> {
            for (int i = 8; i < 20; i++) {
                final int slot = i - 8;
                h.actions[i] = (OptionAction) (player, option) -> {
                    if (option == 1 || slot < 6)
                        player.getFarming().getStorage().withdraw(Tool.values()[slot], 1);
                    else
                        switch (option) {
                            case 2:
                                player.getFarming().getStorage().withdraw(Tool.values()[slot], 5);
                                break;
                            case 3:
                                player.integerInput("Enter amount:", amt -> player.getFarming().getStorage().withdraw(Tool.values()[slot], amt));
                                break;
                            case 4:
                                player.getFarming().getStorage().withdraw(Tool.values()[slot], Integer.MAX_VALUE);
                                break;
                        }
                };
            }
        });

        InterfaceHandler.register(126, h -> {
            for (int i = 1; i < 13; i++) {
                final int slot = i - 1;
                h.actions[i] = (OptionAction) (player, option) -> {
                    if (option == 1 || slot < 6)
                        player.getFarming().getStorage().deposit(Tool.values()[slot], 1);
                    else
                        switch (option) {
                            case 2:
                                player.getFarming().getStorage().deposit(Tool.values()[slot], 5);
                                break;
                            case 3:
                                player.integerInput("Enter amount:", amt -> player.getFarming().getStorage().deposit(Tool.values()[slot], amt));
                                break;
                            case 4:
                                player.getFarming().getStorage().deposit(Tool.values()[slot], Integer.MAX_VALUE);
                                break;
                        }
                };
            }
        });
        NPCAction.register("tool leprechaun", "talk-to", (player, npc) -> {
            player.dialogue(new NPCDialogue(npc, "Ah, 'tis a foine day to be sure! Were yez wantin' me to<br>" +
                    "store yer tools, or maybe ye might be wantin' yer stuff<br>back from me?"));
        });
        NPCAction.register("tool leprechaun", "exchange", (player, npc) -> player.getFarming().getStorage().open(player));
        ItemNPCAction.register("tool leprechaun", (player, item, npc) -> {
            if (item.getDef().notedId > 0 && (item.getDef().produceOf != null || Herb.get(item.getId()) != null)) {
                int amount = player.getInventory().getAmount(item.getId());
                player.getInventory().remove(item.getId(), amount);
                player.getInventory().add(item.getDef().notedId, amount);
                player.dialogue(new ItemDialogue().one(item.getId(), "The leprechaun exchanges your item for bank notes."));
            } else {
                player.dialogue(new MessageDialogue("The leprechaun only has bank notes for farming produce."));
            }
        });
    }

    @Expose private int wateringCanCharges;
    @Expose private boolean emptyBottomlessBucket;

    private Player player;
}
