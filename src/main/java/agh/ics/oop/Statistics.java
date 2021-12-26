package agh.ics.oop;

import java.util.*;

public class Statistics {
    private int animalQuantity;
    private int grassQuantity;
    private ArrayList<Integer> genotypeDominant;
    private double averageAnimalEnergy;
    private double averageLifeSpan = 0;
    private double averageChildrenQuantity;



    public int getAnimalQuantity() {
        return animalQuantity;
    }

    public void setAnimalQuantity(int animalQuantity) {
        this.animalQuantity = animalQuantity;
    }

    public int getGrassQuantity() {
        return grassQuantity;
    }

    public void setGrassQuantity(int grassQuantity) {
        this.grassQuantity = grassQuantity;
    }

    public ArrayList<Integer> getGenotypeDominant() {
        return genotypeDominant;
    }

    public void setGenotypeDominant(ArrayList<Animal> animals) {
        Map<ArrayList<Integer>, Integer> genotypeCount = new HashMap<>();
        for(Animal animal : animals){

            if(genotypeCount.containsKey(animal.getGenotype())){
                int num = genotypeCount.get(animal.getGenotype());
                genotypeCount.put(animal.getGenotype(),num+1);
            }
            else{
                genotypeCount.put(animal.getGenotype(),1);
            }
        }

        HashMap.Entry<ArrayList<Integer>,Integer> maxEntry = null;

        for (Map.Entry<ArrayList<Integer>,Integer> entry : genotypeCount.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        if(maxEntry!=null) System.out.println(maxEntry.getValue()+ " zwierząt ma dominujący genotyp");

        if(maxEntry!=null)this.genotypeDominant = maxEntry.getKey();
        else{

            this.genotypeDominant = null;
        }

    }

    public double getAverageAnimalEnergy() {
        return averageAnimalEnergy;
    }

    public void setAverageAnimalEnergy(ArrayList<Animal> animals) {
        if(animals.size()>0){
            int energySum = 0;
            for(Animal animal :animals){
                energySum+=animal.getEnergy();
            }
            double result = (double)energySum/animals.size();
            this.averageAnimalEnergy = Math.round(result*100.0)/100.0;
        }
        else{
            this.averageAnimalEnergy = 0;
        }

    }

    public double getAverageLifeSpan() {
        return averageLifeSpan;
    }

    public void setAverageLifeSpan(int deathCounter, int date, Animal animal) {
        int lifeSpan = date-animal.getDateOfBirth();
        double tmp = deathCounter* getAverageLifeSpan() + lifeSpan;
        double result = tmp/(deathCounter+1);
        this.averageLifeSpan = Math.round(result*100.0)/100.0;
    }

    public double getAverageChildrenQuantity() {
        return averageChildrenQuantity;
    }

    public void setAverageChildrenQuantity(ArrayList<Animal> animals) {
        if(animals.size() > 0){
            int sumOfChildren = 0;
            for(Animal animal:animals){
                sumOfChildren+=animal.getChildrenCounter();
            }
            double result = (double)sumOfChildren/animals.size();
            averageChildrenQuantity =Math.round(result*100.0)/100.0;
        }
        else{
            averageChildrenQuantity = 0;
        }
    }

}
