package agh.ics.oop.statistics;

import java.util.ArrayList;

public class Snapshot {
    private final int date;
    private final int animalQuantity;
    private final int grassQuantity;
    private final double averageAnimalEnergy;
    private final double averageLifeSpan;
    private final double averageChildrenQuantity;
    private final ArrayList<Integer> dominantGenotype;

    public Snapshot(int date, Statistics statistics) {
        this.date = date;
        this.animalQuantity = statistics.getAnimalQuantity();
        this.grassQuantity = statistics.getGrassQuantity();
        this.averageAnimalEnergy =  statistics.getAverageAnimalEnergy();
        this.averageLifeSpan = statistics.getAverageLifeSpan();
        this.averageChildrenQuantity =statistics.getAverageChildrenQuantity();
        this.dominantGenotype = statistics.getDominantGenotype();
    }

    public int getDate() {
        return date;
    }

    public int getAnimalQuantity() {
        return animalQuantity;
    }

    public int getGrassQuantity() {
        return grassQuantity;
    }

    public double getAverageAnimalEnergy() {
        return averageAnimalEnergy;
    }

    public double getAverageLifeSpan() {
        return averageLifeSpan;
    }

    public double getAverageChildrenQuantity() {
        return averageChildrenQuantity;
    }

    public ArrayList<Integer> getDominantGenotype() {
        return dominantGenotype;
    }
}
