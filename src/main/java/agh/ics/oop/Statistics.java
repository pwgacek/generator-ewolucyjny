package agh.ics.oop;

import com.sun.javafx.image.IntPixelGetter;

import java.util.*;

public class Statistics {
    private int animalQuantity;
    private int grassQuantity;
    private ArrayList<Integer> genotypeDominant;
    private double averageAnimalEnergy;
    private double avarageLifeSpan;
    private double avarageChildrenQuantity;



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
            this.averageAnimalEnergy = (double)energySum/animals.size();
        }
        else{
            this.averageAnimalEnergy = 0;
        }

    }

    public double getAvarageLifeSpan() {
        return avarageLifeSpan;
    }

    public void setAvarageLifeSpan(double avarageLifeSpan) {
        this.avarageLifeSpan = avarageLifeSpan;
    }

    public double getAvarageChildrenQuantity() {
        return avarageChildrenQuantity;
    }

    public void setAvarageChildrenQuantity(double avarageChildrenQuantity) {
        this.avarageChildrenQuantity = avarageChildrenQuantity;
    }
}
