package io.ruin.model.item.attributes;

import com.google.api.client.util.Lists;
import com.google.common.collect.Maps;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.content.upgrade.ItemUpgrade;
import io.ruin.model.item.Item;
import io.ruin.utility.AttributePair;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.ruin.model.item.attributes.AttributeTypes.CHARGES;

/**
 * Helper methods for item attributes.
 * @author ReverendDread on 4/10/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public final class AttributeExtensions {

    public static final int deincrementCharges(Item item, int amount) {
        int charges = getCharges(item);
        setCharges(item, charges - amount);
        return getCharges(item);
    }

    public static int getCharges(Item item){
        return item.getAttributeInt(CHARGES.name(), 0);
    }

    public static void setCharges(Item item, int chargesTotal) {
        if (chargesTotal > 0)
            item.putAttribute(CHARGES.name(), chargesTotal);
        else
            clearCharges(item);
        item.update();
    }

    public static boolean clearCharges(Item item){
        item.clearAttribute(CHARGES.name());
        item.update();
        return true;
    }

    public static void addCharges(Item item, int toAdd) {
        int charges = getCharges(item);
        setCharges(item, charges + toAdd);
    }

    public static void putAttribute(Item item, String attribute, String value) {
        if (value.equalsIgnoreCase("0"))
            item.clearAttribute(attribute);
        else
            item.putAttribute(attribute, value);
        item.update();
    }

    public static void putAttribute(Item item, AttributeTypes attribute, Object value) {
        putAttribute(item, attribute.name(), String.valueOf(value));
    }

    public static String getAugmentType(Item item, AttributeTypes attribute) {
        return item.getAttributeString(attribute, null);
    }

    public static int hashAttributes(Map<String, String> attributes) {
        return attributes == null || attributes.isEmpty() ? 0 : Objects.hash(attributes);
    }

    public static Map<String, String> attributeMapTypes(AttributePair<AttributeTypes, Object>... attributes) {
        Map<String, String> attributeMap = Maps.newHashMap();
        for (AttributePair<AttributeTypes, Object> pair: attributes) {
            attributeMap.put(pair.getKey().name(), String.valueOf(pair.getValue()));
        }
        return attributeMap;
    }

    public static Map<String, String> attributeMapObjects(AttributePair<Object, Object>... attributes) {
        Map<String, String> attributeMap = Maps.newHashMap();
        for (AttributePair<Object, Object> pair: attributes) {
            attributeMap.put(String.valueOf(pair.getKey()), String.valueOf(pair.getValue()));
        }
        return attributeMap;
    }

    public static Map<String, String> attributeMapStrings(AttributePair<String, String>... attributes) {
        Map<String, String> attributeMap = Maps.newHashMap();
        for (AttributePair<String, String> pair: attributes) {
            attributeMap.put(pair.getKey(), String.valueOf(pair.getValue()));
        }
        return attributeMap;
    }

    public static String[] getUpgrades(Item item) {
        return new String[] {
                item.getAttributeString(AttributeTypes.UPGRADE_1, "Empty"),
                item.getAttributeString(AttributeTypes.UPGRADE_2, "Empty"),
                item.getAttributeString(AttributeTypes.UPGRADE_3, "Empty")
        };
    }

    public static String[] getUpgradesForDisplay(Item item) {
        if (item == null)
            return null;
        return new String[] {
                StringUtils.getFormattedEnumName(posOrNeg(item.getAttributeString(AttributeTypes.UPGRADE_1, "Empty"))),
                StringUtils.getFormattedEnumName(posOrNeg(item.getAttributeString(AttributeTypes.UPGRADE_2, "Empty"))),
                StringUtils.getFormattedEnumName(posOrNeg(item.getAttributeString(AttributeTypes.UPGRADE_3, "Empty")))
        };
    }

    public static void putAttributes(Item item ,Map<String, String> attributes) {
        for (Map.Entry<String, String> attribute : attributes.entrySet()) {
            item.putAttribute(attribute.getKey(), attribute.getValue());
        }
        item.update();
    }


    public static String posOrNeg(String name) {
        if (name.equalsIgnoreCase("empty"))
            return name;
        ItemEffect effect = ItemEffect.valueOf(name);
        StringBuilder sb = new StringBuilder(name);
        sb.insert(0, effect.isPositive() ? "+" : "-");
        return sb.toString();
    }

    //returns null if no upgrades
    @Nullable
    public static List<String> getEffectUpgrades(Item item) {
        if (item == null) {
            return null;
        }
        String[] upgrades = getUpgrades(item);
        List<String> upgradeList = Lists.newArrayList();
        for (String upgrade : upgrades) {
            if (!upgrade.toLowerCase().startsWith("stat_")) {
                upgradeList.add(upgrade);
            }
        }
        return upgradeList.isEmpty() ? null : upgradeList;
    }

    public static boolean hasAttribute(Item item, AttributeTypes charges) {
        return item.copyOfAttributes().containsKey(charges.name());
    }

    public static boolean hasUpgrades(Item item) {
        return getCurrentEffects(item).length > 0;
    }

    public static ItemEffect[] getCurrentEffects(Item item) {
        List<ItemEffect> effects = com.google.common.collect.Lists.newArrayList();
        for (int i = 1; i < 4; i++) {
            String value = item.getAttributeString("UPGRADE_" + i, "empty");
            if (!value.equalsIgnoreCase("empty")) {
                ItemEffect effect = ItemEffect.valueOf(value);
                effects.add(effect);
            }
        }
        return effects.toArray(new ItemEffect[0]);
    }

    public static void removeUpgrades(Item item) {
        for (int i = 1; i < 4; i++) {
            item.clearAttribute("UPGRADE_" + i);
        }
        item.update();
    }

    public static void printAttributes(Item item) {
        System.out.println(item.copyOfAttributes().toString());
    }

}
