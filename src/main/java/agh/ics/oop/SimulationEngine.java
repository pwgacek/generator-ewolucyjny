package agh.ics.oop;

import agh.ics.oop.gui.MapVisualizer;
import javafx.application.Platform;

import java.util.ArrayList;

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
        System.out.println(map);
        int positionIterator = 0;
        while(!map.animals.isEmpty()){
            ArrayList<Animal> animalsToRemove = new ArrayList<>();
            for(Animal animal : map.animals){
                if(animal.getEnergy() > 0) {
                    animal.move(animal.getRandomGen());
                    animal.loseEnergy(5);

                }
                else{
                    animalsToRemove.add(animal);
                }

            }
            for(Animal animal : animalsToRemove){
                map.removeAnimal(animal);
            }

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

            Platform.runLater(observer::positionChanged);
            System.out.println(map);
        }



    }

}
