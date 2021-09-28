package ru.gb.box;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Box<T extends Fruit> implements Comparable<Box<? extends Fruit>> {
    private List<T> fruits;

    public void add(T fruit) {
        if (this.fruits == null) {
            this.fruits = new ArrayList<>();
        }
        this.fruits.add(fruit);
    }

    public void add(List<T> fruits) {
        if (this.fruits == null) {
            this.fruits = new ArrayList<>();
        }
        this.fruits.addAll(fruits);
    }

    float getWeight() {
        if (fruits == null) {
            return 0;
        }
        return this.fruits.get(0).getWeight() * this.fruits.size();
    }

    @Override
    public int compareTo(Box<? extends Fruit> other) {
        return Float.compare(this.getWeight(), other.getWeight());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box<?> box = (Box<?>) o;
        return Objects.equals(fruits, box.fruits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruits);
    }
}
