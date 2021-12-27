package agh.ics.oop.conditions;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimulationConditions {

    private final int moveDelay;
    private final AtomicBoolean isRunning;
    private final int startEnergy;
    private final int moveEnergy;
    private final int plantEnergy;
    private final int animalQuantity;
    private final boolean evolutionIsMagical;

    public SimulationConditions(int moveDelay, boolean isRunning, int startEnergy, int moveEnergy, int plantEnergy, int animalQuantity,boolean evolutionIsMagical) {
        this.moveDelay = moveDelay;
        this.isRunning= new AtomicBoolean(isRunning);
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.animalQuantity = animalQuantity;
        this.evolutionIsMagical = evolutionIsMagical;
    }

    public int getMoveDelay() {
        return moveDelay;
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public AtomicBoolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning){
        this.isRunning.set(isRunning);
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getAnimalQuantity() {
        return animalQuantity;
    }

    public boolean isEvolutionMagical() {
        return evolutionIsMagical;
    }
}
