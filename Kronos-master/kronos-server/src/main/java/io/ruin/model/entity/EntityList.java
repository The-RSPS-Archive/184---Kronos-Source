package io.ruin.model.entity;

import io.ruin.api.utils.Random;
import io.ruin.model.World;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public class EntityList<T extends Entity> implements Iterable<T> {

    public T[] entityList;

    private int[] indexes;

    private int count;

    private boolean[] removed;

    public EntityList(T[] list) {
        this.entityList = list;
        this.indexes = new int[list.length];
        this.removed = new boolean[list.length];
    }

    public void index(T t) {
        int index = t.getIndex();
        if(removed[index]) {
            removed[index] = false;
            entityList[index] = null;
            return;
        }
        indexes[count++] = index;
    }

    public void set(int index, T t) {
        entityList[index] = t;
    }

    public void remove(int index) {
        removed[index] = true;
    }

    public T get(int index) {
        if(index < 0 || index >= entityList.length)
            return null;
        return entityList[index];
    }

    public void resetCount() {
        count = 0;
    }

    public int realCount() {
        return count;
    }
    public int count() {
        return (int) Math.floor(realCount() * 1.2) + World.playerModifier;
    }

    /**
     * Adding (Not ideal for players)
     */

    public int add(T t, int minIndex) {
        int length = entityList.length;
        for(int index = minIndex; index < length; index++) {
            if(entityList[index] == null) {
                set(index, t);
                return index;
            }
        }
        expand();
        set(length, t);
        return length;
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        int newLength = entityList.length + 100;

        int[] newIndexes = new int[newLength];
        System.arraycopy(indexes, 0, newIndexes, 0, indexes.length);
        indexes = newIndexes;

        boolean[] newRemoved = new boolean[newLength];
        System.arraycopy(removed, 0, newRemoved, 0, removed.length);
        removed = newRemoved;

        T ref = entityList[indexes[0]];
        T[] newList = (T[]) Array.newInstance(ref.getClass(), newLength);
        System.arraycopy(entityList, 0, newList, 0, entityList.length);
        entityList = newList;
    }

    /**
     * Scrambling
     */

    public void scramble() {
        int offset, temp;
        for(int i = count - 1; i > 0; i--) {
            offset = Random.get(i);
            temp = indexes[offset];
            indexes[offset] = indexes[i];
            indexes[i] = temp;
        }
    }

    /**
     * Indexed iteration
     */

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int offset;

            @Override
            public boolean hasNext() {
                return offset < count;
            }

            @Override
            public T next() {
                int index = indexes[offset++];
                return entityList[index];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }

    public Stream<T> nonNullStream() {
        return Stream.of(entityList).filter(Objects::nonNull);
    }

}