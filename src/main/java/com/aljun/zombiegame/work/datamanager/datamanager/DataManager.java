package com.aljun.zombiegame.work.datamanager.datamanager;

import com.aljun.zombiegame.work.keyset.KeySet;
import net.minecraft.nbt.*;

public class DataManager {
    public static <V> V getOrDefault(CompoundTag tag, String key, V value) {

        if (!tag.contains(key)) {
            return value;
        }

        V v;

        if (value instanceof Integer) {

            v = (V) Integer.valueOf(tag.getInt(key));

        } else if (value instanceof Boolean) {
            v = (V) Boolean.valueOf(tag.getBoolean(key));
        } else if (value instanceof String) {
            v = (V) tag.getString(key);
        } else if (value instanceof Short) {
            v = (V) Short.valueOf(tag.getShort(key));
        } else if (value instanceof Double) {
            v = (V) Double.valueOf(tag.getDouble(key));
        } else if (value instanceof Float) {
            v = (V) Float.valueOf(tag.getFloat(key));
        } else if (value instanceof Long) {
            v = (V) Long.valueOf(tag.getLong(key));
        } else {
            throw new IllegalArgumentException("getOrCreate<V>(CompoundTag tag, String key, V value) : \""
                                               + value.getClass().getName()
                                               + "\" is "
                                               + "not allowed.\n"
                                               + "Integer, Short, Long, Float, Double, Boolean and String are "
                                               + "permitted.");
        }
        return v;
    }

    public static <V> V getOrDefault(CompoundTag tag, KeySet<V> keySet) {
        return getOrDefault(tag, keySet.KEY, keySet.DEFAULT_VALUE);
    }

    public static <V> V getOrCreate(CompoundTag tag, String key, V value) {

        checkToCreate(tag, key, value);
        V v;

        if (value instanceof Integer) {

            v = (V) Integer.valueOf(tag.getInt(key));

        } else if (value instanceof Boolean) {
            v = (V) Boolean.valueOf(tag.getBoolean(key));
        } else if (value instanceof String) {
            v = (V) tag.getString(key);
        } else if (value instanceof Short) {
            v = (V) Short.valueOf(tag.getShort(key));
        } else if (value instanceof Double) {
            v = (V) Double.valueOf(tag.getDouble(key));
        } else if (value instanceof Float) {
            v = (V) Float.valueOf(tag.getFloat(key));
        } else if (value instanceof Long) {
            v = (V) Long.valueOf(tag.getLong(key));
        } else {
            throw new IllegalArgumentException("getOrCreate<V>(CompoundTag tag, String key, V value) : \""
                                               + value.getClass().getName()
                                               + "\" is "
                                               + "not allowed.\n"
                                               + "Integer, Short, Long, Float, Double, Boolean and String are "
                                               + "permitted.");
        }
        return v;
    }

    public static <V> V getOrCreate(CompoundTag tag, KeySet<V> keySet) {
        return getOrCreate(tag, keySet.KEY, keySet.DEFAULT_VALUE);
    }

    public static <V> void set(CompoundTag tag, String key, V value) {
        tag.put(key, toTag(value));
    }

    public static <V> void set(CompoundTag tag, KeySet<V> keySet, V value) {
        set(tag, keySet.KEY, value);
    }

    public static <V> void checkToCreate(CompoundTag tag, String key, V value) {
        if (!tag.contains(key)) {
            set(tag, key, value);
        }
    }

    public static <V> void checkToCreate(CompoundTag tag, KeySet<V> keySet) {
        checkToCreate(tag, keySet.KEY, keySet.DEFAULT_VALUE);
    }

    public static <V> Tag toTag(V value) {
        if (value instanceof Integer in) {
            return IntTag.valueOf(in);
        } else if (value instanceof Boolean bo) {
            return ByteTag.valueOf(bo);
        } else if (value instanceof String str) {
            return StringTag.valueOf(str);
        } else if (value instanceof Short sh) {
            return ShortTag.valueOf(sh);
        } else if (value instanceof Double dou) {
            return DoubleTag.valueOf(dou);
        } else if (value instanceof Float flo) {
            return FloatTag.valueOf(flo);
        } else if (value instanceof Long lo) {
            return LongTag.valueOf(lo);
        } else {
            throw new IllegalArgumentException("toTag(V value) : \""
                                               + value.getClass().getName()
                                               + "\" is not allowed.\n"
                                               + "Integer, Short, Long,"
                                               + " Float, Double, Boolean and String are permitted.");
        }
    }
}