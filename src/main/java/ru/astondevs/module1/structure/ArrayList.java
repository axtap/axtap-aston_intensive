package ru.astondevs.module1.structure;

/**
 * Класс ArrayList представляет динамический массив, который может хранить элементы любого типа данных.
 * Массив автоматически увеличивается при необходимости и поддерживает операции добавления,
 * удаления и получения элементов по индексу.
 *
 * @param <T> тип элементов, хранимых в коллекции
 */
public class ArrayList<T> {
    private static final int DEFAULT_CAPACITY = 10; // Стандартная вместимость

    private int size; // Текущий размер коллекции
    private int capacity; // Вместимость внутреннего массива
    private Object[] array; // Массив для хранения элементов

    /**
     * Создает массив со стандартной вместимостью
     */
    public ArrayList() {
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
        this.array = new Object[capacity];
    }

    /**
     * Создает массив с указанной вместимостью.
     *
     * @param initialCapacity заданная вместимость
     * @throws IllegalArgumentException если указанная вместимость меньше или равна 0
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.size = 0;
            this.capacity = initialCapacity;
            this.array = new Object[initialCapacity];
        } else {
            throw new IllegalArgumentException("Initial capacity must be greater than 0");
        }
    }

    /**
     * Увеличивает вместимость массива до нового значения.
     * Новая вместимость рассчитывается по формуле {@code capacity * 3 / 2}.
     */
    private void grow() {
        capacity = capacity * 3 / 2;
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    /**
     * Добавляет элемент в конец массива.
     * Вызывает метод добавления элемента по индексу, в качестве параметра используя размер коллекции
     *
     * @param element добавляемый элемент
     */
    public void add(T element) {
        add(element, size);
    }

    /**
     * Добавляет элемент в массив по указанному индексу
     *
     * @param element добавляемый элемент
     * @param index   индекс, по которому нужно добавить элемент
     * @throws IndexOutOfBoundsException если индекс находится вне допустимого диапазона
     */
    public void add(T element, int index) {
        // Проверка индекса
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (size == capacity) {
            grow(); // Вызов метода увеличения вместимости
        }

        // Если добавляем в конец коллекции
        if (index == size) {
            array[index] = element;
        } else {
            // Сдвиг элементов вправо
            System.arraycopy(array, index, array, index + 1, size - index);
            array[index] = element;
        }
        size++;
    }

    /**
     * Добавляет элементы другой коллекции в конец текущей коллекции.
     * Вызывает метод добавления коллекции в указанную область текущей коллекции, в качестве параметра индекса используется размер коллекции.
     *
     * @param elements другая коллекция, элементы которой необходимо добавить
     */
    public void addAll(ArrayList<T> elements) {
        addAll(elements, size);
    }

    /**
     * Добавляет другую коллекцию в указанную область текущей коллекции
     *
     * @param elements другая коллекция, элементы которой нужно добавить
     * @param index    индекс, по которому необходимо добавить коллекцию
     * @throws IndexOutOfBoundsException если индекс находится вне допустимого диапазона
     */
    public void addAll(ArrayList<T> elements, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        int elementsSize = elements.size();
        // Вызов метода увеличения вместимости, пока не будет достигнута нужная вместимость
        while (size + elementsSize > capacity) {
            grow();
        }

        // Сдвиг элементов вправо
        System.arraycopy(array, index, array, index + elementsSize, size - index);

        // Копирование элементов из другой коллекции
        System.arraycopy(elements.array, 0, array, index, elementsSize);
        size += elementsSize;
    }

    /**
     * Удаляет элемент по указанному индексу.
     *
     * @param index индекс удаляемого элемента
     */
    public void remove(int index) {
        removeAll(index, index); // Вызов метода удаления по двум индексам
    }

    /**
     * Удаляет все элементы коллекции.
     * Вызывает метод удаления по двум индексам, в качестве параметров используются первый и последний индексы коллекции.
     */
    public void removeAll() {
        removeAll(0, size - 1);
    }

    /**
     * Удаляет элементы коллекции в указанном диапазоне
     *
     * @param start индекс начала удаления
     * @param end   индекс конца удаления
     * @throws IllegalStateException     если коллекция пуста
     * @throws IndexOutOfBoundsException если индексы находятся вне допустимого диапазона
     */
    public void removeAll(int start, int end) {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot remove from an empty list");
        }
        if (start < 0 || end >= size || start > end) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        int numToRemove = end - start + 1;
        // Сдвиг элементов влево
        System.arraycopy(array, end + 1, array, start, size - end - 1);
        // Очистка освободившихся ячеек
        for (int i = size - numToRemove; i < size; i++) {
            array[i] = null;
        }
        size -= numToRemove;
    }

    /**
     * Проверяет индекс на нахождение в допустимом диапазоне
     *
     * @param index индекс элемента
     * @return Возвращает {@code true}, если индекс в пределах допустимого диапазона, иначе {@code false}
     */
    private boolean checkIndex(int index) {
        return index >= 0 && index < size;
    }

    /**
     * Проверяет коллекцию на пустоту
     *
     * @return Возвращает {@code true}, если коллекция пуста, иначе {@code false}
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Возвращает элемент по указанному индексу
     *
     * @param index индекс элемента
     * @return элемент коллекции
     * @throws IndexOutOfBoundsException если индекс находится вне допустимого диапазона
     */
    public T get(int index) {
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return (T) array[index];
    }

    /**
     * Возвращает размер коллекции
     *
     * @return размер коллекции
     */
    public int size() {
        return size;
    }

    /**
     * Уменьшает вместимость внутреннего массива до текущего размера коллекции для освобождения памяти
     */
    public void trimToSize() {
        if (size < capacity) {
            //array = Arrays.copyOf(array, size);
            Object[] newArray = new Object[size];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
            capacity = size;
        }
    }

    /**
     * Сортирует элементы коллекции методом сортировки пузырьком с флагом.
     *
     * @throws IllegalStateException если коллекция пуста или не реализует интерфейс Comparable.
     */
    public void bubbleSort() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot bubble sort on an empty list");
        }

        if (!(array[0] instanceof Comparable)) {
            throw new IllegalStateException("Elements of the list must implement Comparable interface");
        }

        boolean flag;
        for (int i = 0; i < size - 1; i++) {
            flag = true;
            for (int j = 0; j < size - i - 1; j++) {
                if (((Comparable) array[j]).compareTo(array[j + 1]) > 0) {
                    Object temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    /**
     * Строковое представление коллекции
     *
     * @return возвращает строку с содержанием коллекции
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder result = new StringBuilder();
        result.append('[');
        for (int i = 0; i < size; i++) {
            result.append(array[i]);
            if (i < size - 1)
                result.append(", ");
        }
        result.append(']');
        return result.toString();
    }
}
