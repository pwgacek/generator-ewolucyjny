package agh.ics.oop.simulation;

import agh.ics.oop.conditions.SimulationConditions;
import agh.ics.oop.gui.map_visualization.MapHandlerGridPane;
import agh.ics.oop.gui.map_visualization.MapVisualizer;
import agh.ics.oop.map_elements.Animal;
import agh.ics.oop.map_elements.Vector2d;
import agh.ics.oop.maps.AbstractWorldMap;
import agh.ics.oop.statistics.Snapshot;
import agh.ics.oop.statistics.Statistician;
import agh.ics.oop.statistics.Statistics;
import javafx.application.Platform;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class SimulationEngine  extends Thread{

    private final AbstractWorldMap map;
    private int dayCounter;

    private final MapVisualizer observer;
    private final SimulationConditions conditions;
    private final Statistics statistics;
    private int deathCounter;
    private final Statistician statistician;
    private boolean isTerminated;
    private final MapHandlerGridPane mapHandlerGridPane;
    private int evolutionCounter;




    public SimulationEngine(MapHandlerGridPane mapHandlerGridPane, AbstractWorldMap map, MapVisualizer mapVisualizer) {

        isTerminated=false;
        this.map = map;
        this.mapHandlerGridPane = mapHandlerGridPane;
        this.observer = mapVisualizer;
        this.conditions = mapHandlerGridPane.getConditions();
        this.statistics = new Statistics();
        this.deathCounter = 0;
        this.evolutionCounter = 0;
        this.dayCounter = 0;

        ArrayList<Vector2d> positionsWithoutAnimal = new ArrayList<>();
        for(int x=0; x<=map.getWidth();x++){
            for(int y=0;y<= map.getHeight();y++){
                positionsWithoutAnimal.add(new Vector2d(x,y));
            }
        }
        for(int i=0;i<conditions.getAnimalQuantity();i++){
            map.place(new Animal(this.map,positionsWithoutAnimal.remove(new Random().nextInt((positionsWithoutAnimal.size()))),conditions.getStartEnergy()));
        }

        statistician = new Statistician(map.getMyID(),mapHandlerGridPane);

    }



    @Override
    public void  run() {

        updateStatistics();
        statistician.addSnapshot(new Snapshot(dayCounter,statistics));
        statistician.updateDominantGenotypeLabel(statistics.getDominantGenotype());


        Platform.runLater(observer::positionChanged);
        waitForRunLater();

        while(!map.getAnimals().isEmpty()){
            dayCounter++;
            if(!conditions.isRunning()){
                statistician.writeStatisticsHistoryToFile();
                suspendMe(conditions.getIsRunning());

                if(isTerminated){break;}

            }


            removeDeadAnimals();// usuwanie martwych zwierząt
            moveAnimals();// ruch albo skręt zwierzęcia
            feedAnimals();//jedzenie roślin
            breedAnimals();// rozmnażanie zwierząt
            addNewGrass();// dodanie nowych roślin


            Platform.runLater(observer::positionChanged);
            try {
                Thread.sleep(conditions.getMoveDelay());
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            waitForRunLater();


            updateStatistics();
            statistician.addSnapshot(new Snapshot(dayCounter,statistics));
            statistician.updateDominantGenotypeLabel(statistics.getDominantGenotype());


        }

        if(!isTerminated){

            statistician.writeStatisticsHistoryToFile();
            conditions.setIsRunning(false);
            Platform.runLater(this.mapHandlerGridPane::disableStopStartBtn);

        }


    }

    private void updateStatistics() {
        statistics.setAnimalQuantity(map.getAnimals().size());
        statistics.setGrassQuantity(map.getGrassInJungle().size() + map.getGrassInSavanna().size());
        statistics.setDominantGenotype(map.getAnimals());
        statistics.setAverageAnimalEnergy(map.getAnimals());
        statistics.setAverageChildrenQuantity(map.getAnimals());
    }

    private void addNewGrass() {
        map.addGrassToJungle();
        map.addGrassToSavanna();
    }

    private void breedAnimals() {

        for(Vector2d position : map.getAnimalsHashMap().keySet()){
            if(map.getAnimalsHashMap().get(position).size() > 1){

                Animal strongerParent,weakerParent;
                int maxEnergy = map.getAnimalsHashMap().get(position).get(0).getEnergy();
                ArrayList<Animal> candidates = (ArrayList<Animal>) map.getAnimalsHashMap().get(position).stream().filter(a -> a.getEnergy() == maxEnergy).collect(Collectors.toList());
                if (candidates.size() >= 2){
                    ArrayList<Animal> parents = drawParents(candidates,2);
                    strongerParent = parents.get(0);
                    weakerParent = parents.get(1);

                }
                else{
                    int secondMaxEnergy = map.getAnimalsHashMap().get(position).get(1).getEnergy();
                    strongerParent = candidates.get(0);
                    candidates = (ArrayList<Animal>) map.getAnimalsHashMap().get(position).stream().filter(a -> a.getEnergy() == secondMaxEnergy).collect(Collectors.toList());
                    weakerParent = drawParents(candidates,1).get(0);

                }

                if(weakerParent.getEnergy() >= (conditions.getStartEnergy()/2) && strongerParent.getEnergy()>0){
                    weakerParent.incrementChildrenCounter();
                    strongerParent.incrementChildrenCounter();
                    Animal child = new Animal(map,position,strongerParent,weakerParent,dayCounter,conditions.getStartEnergy());
                    map.getAnimals().add(child);
                    map.getAnimalsHashMap().get(position).add(child);
                    Collections.sort(map.getAnimalsHashMap().get(position));
                    child.addObserver(map);
                }

            }
        }
    }

    private void feedAnimals() {

        for(Vector2d position : map.getAnimalsHashMap().keySet()){
            if(map.getGrassInJungle().containsKey(position) || map.getGrassInSavanna().containsKey(position)){

                int maxEnergy = map.getAnimalsHashMap().get(position).get(0).getEnergy();
                List<Animal> banqueters = map.getAnimalsHashMap().get(position).stream().filter(a -> a.getEnergy() == maxEnergy).collect(Collectors.toList());

                for(Animal animal:banqueters){
                    animal.changeEnergy(conditions.getPlantEnergy()/banqueters.size());
                }
                if(map.getGrassInSavanna().containsKey(position))map.removeGrassFromSavanna(position);
                if(map.getGrassInJungle().containsKey(position))map.removeGrassFromJungle(position);

            }
        }
    }

    private void moveAnimals() {
        for(Animal animal : map.getAnimals()){
            animal.move(animal.getRandomGen());

        }
        for(Animal animal :map.getAnimals()){
            animal.changeEnergy(-conditions.getMoveEnergy());
        }
    }

    private void removeDeadAnimals() {
        ArrayList<Animal> animalsToRemove = new ArrayList<>();
        for(Animal animal : map.getAnimals()){
            if(animal.getEnergy()-conditions.getMoveEnergy() < 0){
                animalsToRemove.add(animal);
                statistics.setAverageLifeSpan(deathCounter,dayCounter,animal);
                deathCounter++;
            }
        }
        for(Animal deadAnimal : animalsToRemove){
            deadAnimal.removeObserver(this.map);
            map.removeAnimal(deadAnimal);
            if(conditions.isEvolutionMagical()&& evolutionCounter < 3 && map.getAnimals().size() == 5) cloneAnimals();
        }
    }

    private ArrayList<Animal> drawParents(ArrayList <Animal> candidates,int quantity){
        Random rd = new Random();
        ArrayList <Animal> result = new ArrayList<>();
        Animal chosenOne;
        for(int i =0;i<quantity;i++){
             chosenOne= candidates.remove(rd.nextInt(candidates.size()));
            result.add(chosenOne);
        }
        return result;
    }

    private void cloneAnimals(){
        ArrayList<Vector2d> positionsWithoutAnimal = new ArrayList<>();
        for(int x=0; x<=map.getWidth();x++){
            for(int y=0;y<= map.getHeight();y++){
                Vector2d position = new Vector2d(x,y);
                if(!map.getAnimalsHashMap().containsKey(position)){
                    positionsWithoutAnimal.add(position);
                }

            }
        }
        ArrayList<Animal> clones = new ArrayList<>();
        for(Animal clonedAnimal:map.getAnimals()){
            clones.add( new Animal(this.map,positionsWithoutAnimal.remove(new Random().nextInt((positionsWithoutAnimal.size()))),conditions.getStartEnergy(),clonedAnimal,dayCounter));
        }
        for(Animal clone:clones){
            map.place(clone);
        }

        showMagicalEvolutionInfo(++this.evolutionCounter);
    }




    public static void waitForRunLater()  {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(semaphore::release);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
        conditions.setIsRunning(true);
        resumeMe();
    }

    private void showMagicalEvolutionInfo(int evolutionCounter){

        Platform.runLater(() -> this.mapHandlerGridPane.showMagicalEvolutionInfo(evolutionCounter,map.getMyID()));
    }

    synchronized protected void suspendMe(AtomicBoolean isRunning){
        try {
            while(!isRunning.get()){
                wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void resumeMe(){
        notifyAll();
    }
}
