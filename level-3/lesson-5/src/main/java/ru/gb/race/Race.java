package ru.gb.race;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Race {
    @Getter
    private final List<Stage> stages;
    @Getter
    private final CyclicBarrier cyclicBarrier;
    @Getter
    @Setter
    private volatile boolean isWon = false;
    @Getter
    @Setter
    private volatile boolean isReady = false;

    public Race(int numberOfParticipants, Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
        this.cyclicBarrier = new CyclicBarrier(numberOfParticipants);
    }
}
