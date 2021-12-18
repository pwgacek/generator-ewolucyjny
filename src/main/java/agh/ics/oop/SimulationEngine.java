package agh.ics.oop;

import agh.ics.oop.gui.MapVisualizer;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class SimulationEngine  implements  Runnable{

    private final AbstractWorldMap map;
    private final ArrayList<Vector2d> positions = new ArrayList<>();
    private final MapVisualizer observer;
    private final int moveDelay;




    public SimulationEngine(AbstractWorldMap map, ArrayList<Vector2d> initialPositions, MapVisualizer mapVisualizer, int moveDelay) {

        this.map = map;
        this.moveDelay = moveDelay;
        this.observer = mapVisualizer;

        for(Vector2d position : initialPositions){
            if(map.place(new Animal(this.map,position))) positions.add(position) ;
        }

    }



    @Override
    public void run() {

        Platform.runLater(observer::positionChanged);
        waitForRunLater();
        System.out.println(map);

        while(!map.animals.isEmpty()){


            ArrayList<Animal> animalsToRemove = new ArrayList<>();
            // usuwanie martwych zwierząt
            for(Animal animal : map.animals){
                if(animal.getEnergy() <= 0) animalsToRemove.add(animal);
            }
            for(Animal deadAnimal : animalsToRemove){
                System.out.println(deadAnimal.reports);
                deadAnimal.removeObserver(this.map);
                map.removeAnimal(deadAnimal);
            }

            // ruch albo skręt zwierzęcia
            for(Animal animal : map.animals){
                animal.move(animal.getRandomGen());
                animal.changeEnergy(-5);
            }
            //jedzenie roślin

            for(Vector2d position : map.animalsMap.keySet()){
                if(map.grassMap.containsKey(position)){
                    System.out.println("jemy na pozycji: "+position);

                    int maxEnergy = map.animalsMap.get(position).get(0).getEnergy();
                    System.out.println(" potrzebna energia: " + maxEnergy);
                    List<Animal> banqueters = map.animalsMap.get(position).stream().filter(a -> a.getEnergy() == maxEnergy).collect(Collectors.toList());
                    System.out.println(" kto je: "+banqueters.toString());
                    for(Animal animal:banqueters){
                        animal.changeEnergy(10/banqueters.size());
                    }
                    map.removeGrass(map.grassMap.get(position));
                }
            }
            // rozmnażanie zwierząt
            for(Vector2d position : map.animalsMap.keySet()){
                if(map.animalsMap.get(position).size() > 1){

                    Animal strongerParent,weakerParent;
                    int maxEnergy = map.animalsMap.get(position).get(0).getEnergy();
                    ArrayList<Animal> candidats = (ArrayList<Animal>) map.animalsMap.get(position).stream().filter(a -> a.getEnergy() == maxEnergy).collect(Collectors.toList());
                    if (candidats.size() >= 2){
                        ArrayList<Animal> parents = drawParents(candidats,2);
                        strongerParent = parents.get(0);
                        weakerParent = parents.get(1);

                    }
                    else{
                        int secondMaxEnergy = map.animalsMap.get(position).get(1).getEnergy();
                        strongerParent = candidats.get(0);
                        candidats = (ArrayList<Animal>) map.animalsMap.get(position).stream().filter(a -> a.getEnergy() == secondMaxEnergy).collect(Collectors.toList());
                        weakerParent = drawParents(candidats,1).get(0);

                    }

                    if(weakerParent.getEnergy() > 20){
                        Animal child = new Animal(map,position,strongerParent,weakerParent);
                        map.animals.add(child);
                        map.animalsMap.get(position).add(child);
                        Collections.sort(map.animalsMap.get(position));
                        child.addObserver(map);
                    }


                }
            }


            // dodanie nowych roślin
            this.map.addGrass(1);
            
            for(ArrayList<Animal> t : map.animalsMap.values()){
                System.out.println("----");
                for(Animal a : t){
                    System.out.println(a.toString() +" energy: " +a.getEnergy());
                }
            }

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

            Platform.runLater(observer::positionChanged);
            waitForRunLater();
            System.out.println(map);
        }


    }
    private ArrayList<Animal> drawParents(ArrayList <Animal> candidats,int quantity){
        Random rd = new Random();
        ArrayList <Animal> result = new ArrayList<>();
        Animal chosenOne;
        for(int i =0;i<quantity;i++){
             chosenOne= candidats.remove(rd.nextInt(candidats.size()));
            result.add(chosenOne);
        }
        return result;
    }



    public static void waitForRunLater()  {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(() -> semaphore.release());
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
