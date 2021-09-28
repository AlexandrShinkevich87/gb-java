package ru.gb.box;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public abstract class Fruit {
    @Getter
    private final float weight;

    protected Fruit(float weight) {
        this.weight = weight;
    }
}
