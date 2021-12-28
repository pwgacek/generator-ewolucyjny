package agh.ics.oop.statistics;


public class Snapshot {
    private final int date;
    private final int animalQuantity;
    private final int grassQuantity;
    private final double averageAnimalEnergy;
    private final double averageLifeSpan;
    private final double averageChildrenQuantity;


    public Snapshot(int date, Statistics statistics) {
        this.date = date;
        animalQuantity = statistics.getAnimalQuantity();
        grassQuantity = statistics.getGrassQuantity();
        averageAnimalEnergy =  statistics.getAverageAnimalEnergy();
        averageLifeSpan = statistics.getAverageLifeSpan();
        averageChildrenQuantity =statistics.getAverageChildrenQuantity();

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


}
