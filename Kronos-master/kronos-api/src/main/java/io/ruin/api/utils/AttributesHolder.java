package io.ruin.api.utils;

import java.util.HashMap;

public class AttributesHolder {

    /**
     * Stored attributes
     */

    private HashMap<Object, Object> attributes;

    /**
     * Destroys attributes
     */

    protected void destroyAttributes() {
        if(attributes == null)
            return;
        attributes.clear();
        attributes = null;
    }

    /**
     * Stores the given attribute with the given key.
     * @param key   The unique key.
     * @param value The value to be stored.
     */

    public void set(Object key, Object value) {
        if(attributes == null)
            attributes = new HashMap<>();
        attributes.put(key, value);
    }

    /**
     * Removes an attribute stored with the given key.
     * @param key The unique key.
     * @param <T> The class (value) type.
     * @return The removed attribute - null if none.
     */

    public <T> T remove(Object key) {
        if(attributes == null)
            return null;
        Object value = attributes.remove(key);
        return value == null ? null : (T) value;
    }

    /**
     * Retrieves an attribute stored with the given key.
     * @param key The unique key.
     * @param <T> The class (value) type.
     * @return The retrieved attribute - null if none.
     */

    public <T> T get(Object key) {
        if(attributes == null)
            return null;
        Object value = attributes.get(key);
        return value == null ? null : (T) value;
    }

    /**
     * Retrieves an attribute stored with the given key.
     * If null, returns the given nullValue.
     * @param key The unique key.
     * @param nullValue The value to return if no attribute is found.
     * @param <T> The class (value) type.
     * @return The retrieved attribute - nullValue if none.
     */

    public <T> T get(Object key, T nullValue) {
        T attribute = get(key);
        return attribute == null ? nullValue : attribute;
    }

}