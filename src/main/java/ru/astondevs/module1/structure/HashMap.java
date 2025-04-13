package ru.astondevs.module1.structure;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Класс HashMap представляет собой структуру данных, которая хранит пары ключ-значение и позволяет быстро находить значение по ключу.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16; // Начальный размер массива
    private static final double LOAD_FACTOR = 0.75; // Коэффициент загрузки для resize

    private LinkedList<Entry<K, V>>[] buckets; // Массив связных списков (бакетов)
    private int size; // Количество элементов в HashMap

    /**
     * Внутренний класс для хранения пар ключ-значение.
     */
    private static class Entry<K, V> {
        final K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Создает HashMap с начальной вместимостью по умолчанию.
     */
    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Создает HashMap с указанной начальной вместимостью.
     *
     * @param capacity начальная вместимость
     * @throws IllegalArgumentException если capacity <= 0
     */
    public HashMap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.buckets = new LinkedList[capacity];
        this.size = 0;
    }

    /**
     * Вычисляет хэш ключа и определяет индекс бакета.
     *
     * @param key ключ
     * @return индекс бакета
     */
    private int getBucketIndex(K key) {
        return Math.abs(Objects.hashCode(key)) % buckets.length;
    }

    /**
     * Добавляет пару ключ-значение в HashMap.
     * Если ключ уже существует, его значение обновляется.
     *
     * @param key ключ
     * @param value значение
     * @return предыдущее значение (если ключ существовал), иначе null
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        // Если бакет пуст, создаем новый список
        if (bucket == null) {
            bucket = new LinkedList<>();
            buckets[bucketIndex] = bucket;
        }

        // Проверяем, есть ли уже такой ключ в бакете
        for (Entry<K, V> entry : bucket) {
            if (Objects.equals(entry.key, key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }

        // Если ключа нет, добавляем новую Entry
        bucket.add(new Entry<>(key, value));
        size++;

        // Проверяем, нужно ли увеличивать размер
        if ((double) size / buckets.length > LOAD_FACTOR) {
            resize();
        }

        return null;
    }

    /**
     * Возвращает значение по ключу.
     *
     * @param key ключ
     * @return значение или null, если ключ не найден
     */
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        if (bucket != null) {
            for (Entry<K, V> entry : bucket) {
                if (Objects.equals(entry.key, key)) {
                    return entry.value;
                }
            }
        }

        return null;
    }

    /**
     * Удаляет пару ключ-значение по ключу.
     *
     * @param key ключ
     * @return удаленное значение или null, если ключ не найден
     */
    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        if (bucket != null) {
            for (Entry<K, V> entry : bucket) {
                if (Objects.equals(entry.key, key)) {
                    bucket.remove(entry);
                    size--;
                    return entry.value;
                }
            }
        }

        return null;
    }

    /**
     * Увеличивает размер HashMap в 2 раза и перераспределяет элементы.
     */
    private void resize() {
        LinkedList<Entry<K, V>>[] oldBuckets = buckets;
        buckets = new LinkedList[oldBuckets.length * 2];
        size = 0;

        for (LinkedList<Entry<K, V>> bucket : oldBuckets) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    put(entry.key, entry.value);
                }
            }
        }
    }

    /**
     * Возвращает количество элементов в HashMap.
     *
     * @return размер HashMap
     */
    public int size() {
        return size;
    }
}