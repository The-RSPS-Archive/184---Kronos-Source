package io.ruin.utility;

/**
 * @author ReverendDread on 5/12/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class AttributePair<K, V> {

    /**
     * Key of this <code>Pair</code>.
     */
    private K key;

    /**
     * Gets the key for this pair.
     * @return key for this pair
     */
    public K getKey() { return key; }

    /**
     * Value of this this <code>Pair</code>.
     */
    private V value;

    /**
     * Gets the value for this pair.
     * @return value for this pair
     */
    public V getValue() { return value; }

    /**
     * Creates a new pair
     * @param key The key for this pair
     * @param value The value to use for this pair
     */
    public AttributePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

}
