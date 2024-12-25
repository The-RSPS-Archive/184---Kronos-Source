package io.ruin.model.item.loot;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.WebTable;
import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LootTable {

    @Expose public LootItem[] guaranteed;

    @Expose public List<ItemsTable> tables;

    public double totalWeight;

    /**
     * Methods used for creating tables @ runtime.
     */

    public LootTable guaranteedItems(LootItem... items) {
        guaranteed = items;
        return this;
    }
    public List<LootItem> getLootItems() {
        List<LootItem> items = Lists.newArrayList();
        for (ItemsTable table : tables) {
            items.addAll(Arrays.asList(table.items));
        }
        return items;
    }

    public LootTable addTable(int tableWeight, LootItem... tableItems) {
        return addTable(null, tableWeight, tableItems);
    }

    public LootTable addTable(String tableName, int tableWeight, LootItem... tableItems) {
        if(tables == null)
            tables = new ArrayList<>();
        tables.add(new ItemsTable(tableName, tableWeight, tableItems));
        totalWeight += tableWeight;
        return this;
    }

    /**
     * Methods pretty much specifically for npc drop tables.
     */

    public LootTable combine(LootTable table) {
        LootTable newTable = new LootTable();

        List<LootItem> newGuaranteed = new ArrayList<>();
        if(guaranteed != null)
            Collections.addAll(newGuaranteed, guaranteed);
        if(table.guaranteed != null)
            Collections.addAll(newGuaranteed, table.guaranteed);
        newTable.guaranteed = newGuaranteed.isEmpty() ? null : newGuaranteed.toArray(new LootItem[0]);

        List<ItemsTable> newTables = new ArrayList<>();
        if(tables != null)
            newTables.addAll(tables);
        if(table.tables != null)
            newTables.addAll(table.tables);
        newTable.tables = newTables.isEmpty() ? null : newTables;

        return newTable;
    }

    public void calculateWeight() {
        totalWeight = 0;
        if(tables != null) {
            for(ItemsTable table : tables) {
                totalWeight += table.weight;
                table.totalWeight = 0;
                if(table.items != null) {
                    for(LootItem item : table.items)
                        table.totalWeight += item.weight;
                }
            }
        }
    }

    /**
     * Item selection
     */

    public Item rollItem() {
        List<Item> items = rollItems(false);
        return items == null ? null : items.get(0);
    }

    public List<Item> rollItems(boolean allowGuaranteed) {
        List<Item> items;
        if(allowGuaranteed && guaranteed != null) {
            items = new ArrayList<>(guaranteed.length + 1);
            for(LootItem item : guaranteed)
                items.add(item.toItem());
        } else {
            items = new ArrayList<>(1);
        }
        if(tables != null) {
            double tableRand = Random.get() * totalWeight;
            for(ItemsTable table : tables) {
                if((tableRand -= table.weight) <= 0) {
                    if(table.items != null) {
                        double itemsRand = Random.get() * table.totalWeight;
                        for(LootItem item : table.items) {
                            if(item.weight == 0) {
                                /* weightless item landed, add it and continue loop */
                                items.add(item.toItem());
                                continue;
                            }
                            if((itemsRand -= item.weight) <= 0) {
                                /* weighted item landed, add it and break loop */
                                items.add(item.toItem());
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        return items.isEmpty() ? null : items;
    }

    public List<Item> allItems() {
        List<Item> items = new ArrayList<>();
        if(guaranteed != null) {
            for(LootItem item : guaranteed) {
                if (item != null)
                items.add(item.toItem());
            }
        }
        if(tables != null) {
            for(ItemsTable table : tables) {
                if(table.items != null) {
                    for(LootItem item : table.items) {
                        if (item != null)
                        items.add(item.toItem());
                    }
                }
            }
        }
        return items;
    }
    /*
     * Returns the weight of an item on a loot table
     */
    public int getWeight(Item item) {
        int weight = 100;
        List<LootItem> searchTable = getLootItems();
        for (LootItem itemSearch : searchTable) {
            if (itemSearch.id == item.getId()) {
                weight = itemSearch.weight;
            }
        }
        return weight;
    }

    public void calculate(String name) {
        DecimalFormat format = new DecimalFormat("0.###");
        WebTable tablesTable = new WebTable(name + " | Tables | Total weight = " + totalWeight);
        WebTable itemsTable = new WebTable(name + " | Items");
        for (ItemsTable table : tables) {
            double tableProbability = table.weight / totalWeight;
            tablesTable.newEntry().add("name", table.name)
                    .add("weight", table.weight)
                    .add("average (1 in X)", (int)(1 / (tableProbability)))
                    .add("probability", format.format(tableProbability))
                    .add("percentage", format.format(tableProbability * 100) + "%");
            if(table.items.length == 0) {
                int itemWeight = 1;
                double probabilityInTable = itemWeight / table.totalWeight;
                itemsTable.newEntry()
                        .add("table", table.name)
                        .add("id", -1)
                        .add("name", "Nothing")
                        .add("min amount", -1)
                        .add("max amount", -1)

                        .add("overall average (1 in X)", (int)(1 / (probabilityInTable * tableProbability)))
                        .add("weight in table", itemWeight)
                        .add("overall probability", format.format(probabilityInTable * tableProbability))
                        .add("overall percentage", format.format(probabilityInTable * tableProbability * 100) + "%")

                        .add("average in table (1 in X)", (int)(1 / (probabilityInTable)))
                        .add("probability in table", format.format(probabilityInTable))
                        .add("percentage in table", format.format(probabilityInTable * 100) + "%");
            } else {
                for(LootItem item : table.items) {
                    double probabilityInTable = item.weight / table.totalWeight;
                    itemsTable.newEntry()
                            .add("table", table.name)
                            .add("id", item.id)
                            .add("name", item.getName())
                            .add("min amount", item.min)
                            .add("max amount", item.max)

                            .add("overall average (1 in X)", (int) (1 / (probabilityInTable * tableProbability)))
                            .add("weight in table", item.weight)
                            .add("overall probability", format.format(probabilityInTable * tableProbability))
                            .add("overall percentage", format.format(probabilityInTable * tableProbability * 100) + "%")

                            .add("average in table (1 in X)", (int) (1 / (probabilityInTable)))
                            .add("probability in table", format.format(probabilityInTable))
                            .add("percentage in table", format.format(probabilityInTable * 100) + "%");

                }
            }
        }
        System.out.println("Tables: " + tablesTable.getURL());
        System.out.println("Items: " + itemsTable.getURL());
    }

    /**
     * A table of items unique to this table type.
     */

    public static final class ItemsTable {


        @Expose public final String name;

        @Expose public final int weight;

        @Expose public final LootItem[] items;

        public double totalWeight;

        public ItemsTable(String name, int weight, LootItem[] items) {
            this.name = name;
            this.weight = weight;
            this.items = items;
            for(LootItem item : items) {
                totalWeight += item.weight;
                if(ItemDef.get(item.id) == null)
                    System.err.println("!!@@@@@@@@@@@@@@@@@@@@@@: " + item.id);
            }
        }


    }
}