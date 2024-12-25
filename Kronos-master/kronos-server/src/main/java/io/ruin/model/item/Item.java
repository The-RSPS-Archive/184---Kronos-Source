package io.ruin.model.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.gson.annotations.Expose;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.ItemDef;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.item.attributes.AugmentType;
import io.ruin.utility.Broadcast;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Map;

/**
 * Represents an item that is in an item container.
 */
@Slf4j
@ToString
public class Item {

    @Expose @Getter private int id, amount;
    @Expose private Map<String, String> attributes;
    @Setter @Getter private int slot = -1;

    @Setter @Getter private ItemContainerG container;
    public Broadcast lootBroadcast;

    /**
     * Creates an <code>Item</code> with the specified id and amount.
     * @param id       The item id.
     * @param amount    The amount of the item.
     */
    public Item(int id, int amount) {
        this(id, amount, null);
    }

    /**
     * Creates an <code>Item</code> with the specified id, with an amount of 1 and no attributes.
     * @param id       The item id.
     */
    public Item(int id) {
        this(id, 1, null);
    }

    /**
     * Creates an <code>Item</code> with the specified parameters.
     * @param id       The item id.
     * @param amount    The amount of the item.
     * @param attributes    The map of attributes the item has.
     */
    public Item(int id, int amount, Map<String, String> attributes) {
        this.id = id;
        this.amount = amount;
        if (attributes == null)
            this.attributes = Maps.newHashMap();
        else
            this.attributes = Maps.newHashMap(attributes);
    }

    /**
     * Copies an item with its attributes intact.
     * @return
     */
    public Item copy() {
        return new Item(id, amount, attributes);
    }

    /**
     * Copies an item without its attributes.
     * @return
     */
    public Item copyWithoutAttributes() {
        return new Item(id, amount);
    }

    /**
     * Gets the <code>ItemDef</code> for this item.
     * @return
     */
    public ItemDef getDef() {
        return ItemDef.cached.get(id);
    }

    /**
     * Sets this items id to the desired id.
     * @param id    The id.
     */
    public void setId(int id) {
        this.id = id;
        update();
    }

    /**
     * Sets this items amount to the desired amount.
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
        update();
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public String putAttribute(String key, Object value){
        return attributes.put(key, String.valueOf(value));
    }

    public boolean putAttributes(Map<String, String> attributes) {
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            putAttribute(entry.getKey(), entry.getValue());
        }
        return true;
    }

    /**
     * Adds the desired attribute to this items attributes.
     * @param key   The <code>key</code> to add.
     * @param value   The attributes' value.
     * @return  The previous value associated with the attribute type.
     */
    public String putAttribute(AttributeTypes key, Object value){
        return attributes.put(key.name(), String.valueOf(value));
    }

    /**
     * Creates a copy of this items attributes.
     * @return
     */
    public Map<String, String> copyOfAttributes() {
        return attributes == null ? Maps.newHashMap() : Maps.newHashMap(attributes);
    }

    /**
     * Gets an attribute as an int value.
     * @param key The attribute key
     * @return the attribute value, or -1 if attribute key has no value
     */
    public int getAttributeInt(String key){
        return getAttributeInt(key, -1);
    }

    /**
     * Gets an attribute as an int value.
     * @param key The attribute key
     * @return the attribute value, or -1 if attribute key has no value
     */
    public int getAttributeInt(AttributeTypes key){
        return getAttributeInt(key, -1);
    }

    /**
     * Gets an attribute as an int value.
     * @param key The attribute key
     * @return the attribute value, or <code>defaultValue</code> if attribute key has no value
     */
    public int getAttributeInt(String key, int defaultValue){
        return Integer.parseInt(attributes.getOrDefault(key, String.valueOf(defaultValue)));
    }

    /**
     * Gets an attribute as an int value.
     * @param key The attribute key
     * @return the attribute value, or <code>defaultValue</code> if attribute key has no value
     */
    public int getAttributeInt(AttributeTypes key, int defaultValue){
        return Integer.parseInt(attributes.getOrDefault(String.valueOf(key), String.valueOf(defaultValue)));
    }

    /**
     * Gets an attribute as a string value.
     * @param key   The attribute key.
     * @return  the attribute value, or "defaultValue" string if none exists.
     */
    public String getAttributeString(AttributeTypes key, String defaultValue) {
        return String.valueOf(attributes.getOrDefault(String.valueOf(key), defaultValue));
    }

    /**
     * Gets an attribute as a string value.
     * @param key   The attribute key.
     * @return  the attribute value, or "defaultValue" if none exists.
     */
    public String getAttributeString(String key, String defaultValue) {
        return String.valueOf(attributes.getOrDefault(String.valueOf(key), defaultValue));
    }

    /**
     * Gets an attribute as a boolean value.
     * @param key   The attribute key.
     * @return  the attribute value, or "defaultValue" if none is present.
     */
    public boolean getAttributeBoolean(AttributeTypes key, boolean defaultValue) {
        return Boolean.parseBoolean(attributes.getOrDefault(String.valueOf(key), String.valueOf(defaultValue)));
    }

    /**
     * Gets an attribute as a boolean value.
     * @param key   The attribute key.
     * @return  the attribute value, or "defaultValue" if none is present.
     */
    public boolean getAttributeBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(attributes.getOrDefault(String.valueOf(key), String.valueOf(defaultValue)));
    }

    /**
     * Clears an attribute from the map
     * @param key The attribute key to remove
     * @return true if the attribute map had the key
     */
    public boolean clearAttribute(String key){
        return attributes.remove(key) != null;
    }

    /**
     * Clears an attribute from the map
     * @param key The attribute key to remove
     * @return true if the attribute map had the key
     */
    public boolean clearAttribute(AttributeTypes key){
        return attributes.remove(key.name()) != null;
    }

    /**
     * Adds the desired amount to this item's quantity.
     * @param amount
     */
	public void incrementAmount(long amount) {
        long newAmount = (long) this.amount + amount;
        if(newAmount <= 0) {
            this.amount = 0;
            remove();
        } else {
            this.amount = (int) Math.min(newAmount, Integer.MAX_VALUE);
            update();
        }
    }

    /**
     * Gets this items unique hash for what its attributes hold.
     * @return
     */
    public int getAttributeHash() {
        return AttributeExtensions.hashAttributes(attributes);
    }

    /**
     * Removes this item from the container that its in.
     */
    public void remove() {
        if(container != null)
            container.set(slot, null);
    }

    /**
     * Removes a the specified amount from this items quantity.
     * @param amount    The amount to remove.
     * @return  The amount that was removed from the item.
     */
    public int remove(int amount) {
        if(container == null)
            return 0;
        if(getDef().stackable) {
            int temp = getAmount();
            incrementAmount(-amount);
            return temp - getAmount();
        }
        return container.remove(id, amount);
    }

    /**
     * Moves this item to the specified container.
     * @param addId The item id.
     * @param amount The amount to move.
     * @param toContainer   The container to be moved to.
     * @return  The item amount that was moved.
     */
    public int move(int addId, int amount, ItemContainerG toContainer) {
        if (container == null || amount <= 0)
            return 0;
        boolean stack;
        int moved;
        int hash = getAttributeHash();
        if (hash == 0) {
            if ((stack = container.forceStack || getDef().stackable))
                amount = Math.min(amount, this.amount);
            else
                amount = Math.min(amount, container.count(id));
            moved = toContainer.add(addId, amount, attributes);
        } else {
            //TODO Need to change this
            //Never allow more than one to be moved at a time. This should only ever apply to banking.
            //Example: Removing "All" blowpipes when you have 3 in your bank with all different charges.
            //Since they all have their own unique value, they'll never stack in the bank. So withdrawing
            //just 1 at a time kind of makes sense anyways.. (And making it work the other way = slower code!)
            stack = false;
            moved = toContainer.add(addId, 1, attributes); //old way
        }
        if (moved <= 0) {
            /* failed to move to new container */
            return moved;
        }
        if (stack) {
            incrementAmount(-moved);
            return moved;
        }
        if (moved == 1) {
            remove();
            return 1;
        }
        return container.remove(new Item(id, moved, attributes));
    }

    /**
     * Gets the amount of this item, in the items container.
     * @return
     */
    public int count() {
        if(container == null)
            return 0;
        if(getDef().stackable)
            return amount;
        return container.count(id);
    }

    /**
     * Updates this item in its container.
     */
    public void update() {
        if(container != null)
            container.update(slot);
    }

    /**
     * Sends the player this items examine message.
     * @param player    The player to send the message to.
     */
    public void examine(Player player) {
        ItemDef def = ItemDef.get(id);
        if (def == null)
            return;
        player.sendMessage(def.examine == null ? "This item has no examine" : def.examine);
        ItemEffect[] effects = AttributeExtensions.getCurrentEffects(this);
        if (effects.length > 0) {
            int index = 1;
            for (ItemEffect effect : effects) {
                player.sendMessage("Upgrade " + index++ + " = " + effect.getName());
            }
        }
        if (AttributeExtensions.hasAttribute(this, AttributeTypes.AUGMENTED)) {
            String augment = AttributeExtensions.getAugmentType(this, AttributeTypes.AUGMENTED);
            if (augment != null) {
                AugmentType type = AugmentType.valueOf(augment);
                player.sendMessage("This item is augmented with a " + StringUtils.getFormattedEnumName(type) + " augment.");
            }
        }
        if (player.debug) {
            player.sendMessage("Attributes: " + Joiner.on(",").withKeyValueSeparator("=").join(attributes));
            player.sendMessage("Attribute hash: " + getAttributeHash());
        }
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public int getSlot() {
        return slot;
    }

    public static void examine(Player player, int id) {
        examine(player, id, 1);
    }

    public static void examine(Player player, int id, int amount) {
        ItemDef def = ItemDef.get(id);
        if(def == null)
            return;
        player.sendMessage(def.examine == null ? "This item has no examine" : def.examine);
    }

    public boolean hasAttributes() {
        return AttributeExtensions.hashAttributes(attributes) != 0;
    }

    public void clearAttributes() {
        attributes.clear();
    }

}
