package agh.ics.oop;

import agh.ics.oop.gui.MapVisualizer;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SimulationEngine  implements  Runnable{
    private ArrayList<MoveDirection> moves;
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
                if(animal.getEnergy() == 0) animalsToRemove.add(animal);
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
            for(Animal animal : map.animals){
                if(map.grassMap.containsKey(animal.getPosition())){
                    animal.changeEnergy(10);
                    map.removeGrass(map.grassMap.get(animal.getPosition()));

                }
            }

            // dodanie nowych roślin
            this.map.addGrass(1);



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
